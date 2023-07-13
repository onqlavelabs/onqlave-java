package com.onqlave.encryption;

import com.onqlave.contract.*;
import com.onqlave.keymanager.KeyManager;
import com.onqlave.keymanager.KeyManagerImpl;
import com.onqlave.keymanager.factories.AesGcmFactory;
import com.onqlave.keymanager.factories.Xchacha20Poly1305Factory;
import com.onqlave.keymanager.operations.Aes128GcmOperation;
import com.onqlave.keymanager.operations.Aes256GcmOperation;
import com.onqlave.keymanager.operations.Xchacha20Poly1305Operation;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.CPRNGServiceImpl;
import com.onqlave.service.IDService;
import com.onqlave.service.IDServiceImpl;
import com.onqlave.types.*;
import com.onqlave.utils.DurationFormatter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.bouncycastle.util.Arrays;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Encryption {
    private KeyManager keyManager;
    private Map<String, KeyOperation> operations;
    private static final Logger LOGGER = LogManager.getLogger();

    public Encryption(Credential credential, RetrySettings retrySettings, String ArxURL, Boolean debug) {
        enableDebugLevel(debug);
        Configuration configuration = new Configuration(credential, retrySettings, ArxURL, debug);
        CPRNGService randomService;
        try {
            randomService = new CPRNGServiceImpl();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        IDService idService = new IDServiceImpl(randomService);
        KeyManager keyManager = new KeyManagerImpl(configuration, randomService);
        KeyFactory aesGcmKeyFactory = new AesGcmFactory(idService, randomService);
        KeyFactory xchacha20Factory = new Xchacha20Poly1305Factory(idService, randomService);

        Map<String, KeyOperation> operations = new HashMap<String, KeyOperation>();
        operations.put(AlgorithmTypeValue.AES_GCM_128, new Aes128GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.AES_GCM_256, new Aes256GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.XCHA_CHA_20_POLY_1305, (KeyOperation) new Xchacha20Poly1305Operation(xchacha20Factory));

        this.keyManager = keyManager;
        this.operations = operations;
    }

    public void close() {
        this.keyManager = null;
    }

    private EncryptOperation initEncryptOperation(String operation) throws Exception {
        try {
            EncryptionKey ek = this.keyManager.FetchEncryptionKey();

            byte[] edk = ek.getB64EDK();
            byte[] dk = ek.getB4DK();
            String algo = ek.getAlgorithm();

            KeyOperation ops = this.operations.get(algo);
            KeyFactory factory = ops.GetFactory();
            Key key = factory.NewKeyFromData(ops, dk);
            AEAD primitive = factory.Primitive(key);
            AlgorithmSeriliser algorithm = new Algorithm(0, algo, edk);

            return new EncryptOperation(algorithm, primitive);
        } catch (Exception e) {
            LOGGER.error(String.format("[onqlave] SDK: %s - Failed encrypting plain data", operation));
            throw e;
        }
    }

    private AEAD initDecryptOperation(String operation, AlgorithmDeserialiser algo) throws Exception {
        try {
            byte[] dk = this.keyManager.FetchDecryptionKey(algo.Key());
            KeyOperation ops = this.operations.get(algo.GetAlgorithm());
            KeyFactory factory = ops.GetFactory();
            Key key = factory.NewKeyFromData(ops, dk);
            AEAD primitive = factory.Primitive(key);
            return primitive;
        } catch (Exception e) {
            LOGGER.error(String.format("[onqlave] SDK: %s - Failed decrypting cipher data", operation));
            throw e;
        }
    }

    public byte[] Encrypt(byte[] planData, byte[] associatedData) throws Exception {
        String operation = "Encrypt";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypting plain data", operation));
        EncryptOperation eo = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = eo.getSeriliser();
        AEAD primitive = eo.getMethod();
        byte[] cipherData = primitive.Encrypt(planData, associatedData);
        ByteArrayOutputStream cipherStream = new ByteArrayOutputStream();
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        processor.WriteHeader(header);
        processor.WritePacket(cipherData);
        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypted plain data: operation took %s", operation, duration));
        return cipherStream.toByteArray();
    }

    public byte[] Decrypt(byte[] cipherData, byte[] associatedData) throws Exception {
        String operation = "Decrypt";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypting cipher data", operation));
        InputStream cipherStream = new ByteArrayInputStream(cipherData);
        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.ReadHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);
        byte[] cipher = processor.ReadPacket();
        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypted cipher data: operation took %s", operation, duration));
        return primitive.Decrypt(cipher, associatedData);
    }

    @Deprecated
    public void EncryptStream(InputStream plainStream, OutputStream cipherStream, byte[] associatedData) throws Exception {
        String operation = "EncryptStream";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypting plain data", operation));
        EncryptOperation eo = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = eo.getSeriliser();
        AEAD primitive = eo.getMethod();
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        processor.WriteHeader(header);

        byte[] tempBuffer = new byte[32 * 1024];
        while (true) {
            try {
                int datalen = plainStream.read(tempBuffer);
                if (datalen == -1) {
                    break;
                }
                String bufferStr = new String(tempBuffer);
                byte[] cipherText = primitive.Encrypt(Arrays.copyOfRange(bufferStr.getBytes(), 0, datalen), associatedData);
                processor.WritePacket(cipherText);
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                LOGGER.error(String.format("[onqlave] SDK: %s - Failed encrypting plain data", operation));
                return;
            }
        }

        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypted plain data: operation took %s", operation, duration));
    }

    @Deprecated
    public void DecryptStream(InputStream cipherStream, OutputStream plainStream, byte[] associatedData) throws Exception {
        String operation = "DecryptStream";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypting cipher data", operation));

        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.ReadHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);

        while (true) {
            try {
                byte[] cipher = processor.ReadPacket();
                byte[] plainData = primitive.Decrypt(cipher, associatedData);
                plainStream.write(plainData);
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                LOGGER.error(String.format("[onqlave] SDK: %s - Failed decrypting plain data", operation));
                return;
            }
        }

        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypted cipher data: operation took %s", operation, duration));
    }

    public OnqlaveStructure EncryptStructure(Map<String, Object> plainStructure, byte[] associatedData) throws Exception {
        String operation = "EncryptStructure";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypting plain data", operation));

        EncryptOperation eo = this.initEncryptOperation(operation);
        AlgorithmSeriliser algo = eo.getSeriliser();
        AEAD primitive = eo.getMethod();
        byte[] header = algo.Serialise();
        Map<String, byte[]> result = new HashMap<>();
        TypeResolver resolver = new TypeResolverImpl();
        for (Map.Entry<String, Object> e : plainStructure.entrySet()) {
            byte[] plainData = resolver.Serialise(e.getKey(), e.getValue());
            byte[] cipherData = primitive.Encrypt(plainData, associatedData);
            result.put(e.getKey(), cipherData);
        }

        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypted plain data: operation took %s", operation, duration));
        return new OnqlaveStructure(header, result);
    }

    public Map<String, Object> DecryptStructure(OnqlaveStructure cipherStructure, byte[] associatedData) throws Exception {
        String operation = "EncryptStructure";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypting cipher data", operation));

        InputStream cipherStream = new ByteArrayInputStream(cipherStructure.getEdk());
        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.ReadHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);
        TypeResolver resolver = new TypeResolverImpl();
        Map<String, Object> result = new HashMap<String, Object>();
        for (Map.Entry<String, byte[]> e : cipherStructure.getEmbedded().entrySet()) {
            byte[] plainData = primitive.Decrypt(e.getValue(), associatedData);
            Object plainValue = resolver.Deserialise(e.getKey(), plainData);
            result.put(e.getKey(), plainValue);
        }

        String duration = DurationFormatter.DurationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypted cipher data: operation took %s", operation, duration));
        return result;
    }

    public void enableDebugLevel(Boolean debug) {
        if (debug) {
            Configurator.setRootLevel(Level.TRACE);
        } else {
            Configurator.setRootLevel(Level.ERROR);
        }
    }
}
