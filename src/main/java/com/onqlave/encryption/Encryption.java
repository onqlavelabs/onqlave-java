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
    private final KeyManager keyManager;
    private final Map<String, KeyOperation> operations;
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
        KeyFactory xChaCha20Factory = new Xchacha20Poly1305Factory(idService, randomService);

        Map<String, KeyOperation> operations = new HashMap<>();
        operations.put(AlgorithmTypeValue.AES_GCM_128, new Aes128GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.AES_GCM_256, new Aes256GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.XCHA_CHA_20_POLY_1305, new Xchacha20Poly1305Operation(xChaCha20Factory));

        this.keyManager = keyManager;
        this.operations = operations;
    }

    private EncryptOperation initEncryptOperation(String operation) throws Exception {
        try {
            EncryptionKey ek = this.keyManager.fetchEncryptionKey();

            byte[] edk = ek.getB64EDK();
            byte[] dk = ek.getB4DK();
            String algo = ek.getAlgorithm();

            KeyOperation ops = this.operations.get(algo);
            KeyFactory factory = ops.getFactory();
            Key key = factory.newKeyFromData(ops, dk);
            AEAD primitive = factory.primitive(key);
            AlgorithmSeriliser algorithm = new Algorithm(0, algo, edk);

            return new EncryptOperation(algorithm, primitive);
        } catch (Exception e) {
            LOGGER.error(String.format("[onqlave] SDK: %s - Failed encrypting plain data", operation));
            throw e;
        }
    }

    private AEAD initDecryptOperation(String operation, AlgorithmDeserialiser algo) throws Exception {
        try {
            byte[] dk = this.keyManager.fetchDecryptionKey(algo.key());
            KeyOperation ops = this.operations.get(algo.getAlgorithm());
            KeyFactory factory = ops.getFactory();
            Key key = factory.newKeyFromData(ops, dk);
            return factory.primitive(key);
        } catch (Exception e) {
            LOGGER.error(String.format("[onqlave] SDK: %s - Failed decrypting cipher data", operation));
            throw e;
        }
    }

    public byte[] encrypt(byte[] planData, byte[] associatedData) throws Exception {
        String operation = "Encrypt";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypting plain data", operation));
        EncryptOperation eo = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = eo.seriliser();
        AEAD primitive = eo.method();
        byte[] cipherData = primitive.encrypt(planData, associatedData);
        ByteArrayOutputStream cipherStream = new ByteArrayOutputStream();
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        processor.writeHeader(header);
        processor.writePacket(cipherData);
        String duration = DurationFormatter.durationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypted plain data: operation took %s", operation, duration));
        return cipherStream.toByteArray();
    }

    public byte[] decrypt(byte[] cipherData, byte[] associatedData) throws Exception {
        String operation = "Decrypt";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypting cipher data", operation));
        InputStream cipherStream = new ByteArrayInputStream(cipherData);
        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.readHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);
        byte[] cipher = processor.readPacket();
        String duration = DurationFormatter.durationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypted cipher data: operation took %s", operation, duration));
        return primitive.decrypt(cipher, associatedData);
    }

    public void encryptStream(InputStream plainStream, OutputStream cipherStream, byte[] associatedData) throws Exception {
        String operation = "EncryptStream";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypting plain data", operation));
        EncryptOperation eo = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = eo.seriliser();
        AEAD primitive = eo.method();
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        processor.writeHeader(header);

        byte[] tempBuffer = new byte[32 * 1024];
        while (true) {
            try {
                int bytesRead = plainStream.read(tempBuffer);
                if (bytesRead == -1) {
                    break;
                }
                String bufferStr = new String(tempBuffer);
                byte[] cipherText = primitive.encrypt(Arrays.copyOfRange(bufferStr.getBytes(), 0, bytesRead), associatedData);
                processor.writePacket(cipherText);
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                LOGGER.error(String.format("[onqlave] SDK: %s - Failed encrypting plain data", operation));
                return;
            }
        }

        String duration = DurationFormatter.durationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Encrypted plain data: operation took %s", operation, duration));
    }

    public void decryptStream(InputStream cipherStream, OutputStream plainStream, byte[] associatedData) throws Exception {
        String operation = "DecryptStream";
        Instant start = Instant.now();
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypting cipher data", operation));

        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.readHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);

        while (true) {
            try {
                byte[] cipher = processor.readPacket();
                byte[] plainData = primitive.decrypt(cipher, associatedData);
                plainStream.write(plainData);
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                LOGGER.error(String.format("[onqlave] SDK: %s - Failed decrypting plain data", operation));
                return;
            }
        }

        String duration = DurationFormatter.durationBetween(start, Instant.now());
        LOGGER.debug(String.format("[onqlave] SDK: %s - Decrypted cipher data: operation took %s", operation, duration));
    }

    private void enableDebugLevel(Boolean debug) {
        if (debug) {
            Configurator.setRootLevel(Level.TRACE);
        } else {
            Configurator.setRootLevel(Level.ERROR);
        }
    }
}
