package com.onqlave.encryption;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
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

import org.bouncycastle.util.Arrays;
import org.javatuples.Pair;
import org.javatuples.Triplet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Encryption {
    private KeyManager keyManager;
    //private Logger logger;

    private Map<String, KeyOperation> operations;

    // constructor
    public Encryption(Credential credential, RetrySettings retrySettings, String ArxURL, Boolean debug ) {

        Configuration configuration = new Configuration(credential,retrySettings,ArxURL, debug);
        CPRNGService randomService ;
        try {
             randomService = new CPRNGServiceImpl();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        IDService idService = new IDServiceImpl(randomService);
        KeyManager keyManager = new KeyManagerImpl(configuration, randomService);
        KeyFactory aesGcmKeyFactory = new AesGcmFactory(idService,randomService);
        KeyFactory xchacha20Factory = new Xchacha20Poly1305Factory(idService, randomService);

        Map<String, KeyOperation> operations = new HashMap<String, KeyOperation>();
        operations.put(AlgorithmTypeValue.AES_GCM_128, new Aes128GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.AES_GCM_256, new Aes256GcmOperation(aesGcmKeyFactory));
        operations.put(AlgorithmTypeValue.XCHA_CHA_20_POLY_1305, (KeyOperation) new Xchacha20Poly1305Operation(xchacha20Factory));

    }

    public void close() {
        this.keyManager = null;
    }

    private Pair<AlgorithmSeriliser, AEAD> initEncryptOperation(String operation) throws  Exception {
        Pair<AlgorithmSeriliser, AEAD> result = new Pair<AlgorithmSeriliser,AEAD>(null, null);
        try {
            Triplet<byte[], byte[], String> trip = this.keyManager.FetchEncryptionKey();
            
            byte[] edk = trip.getValue0();
            byte[] dk = trip.getValue1();
            String algo = trip.getValue2();

            KeyOperation ops = this.operations.get(algo);
            KeyFactory factory = ops.GetFactory();
            Key key = factory.NewKeyFromData(ops, dk);
            AEAD primitive = factory.Primitive(key);
            AlgorithmSeriliser algorithm = new Algorithm(0, algo, edk);

            result.setAt0(algorithm);
            result.setAt1(primitive);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    private AEAD initDecryptOperation(String operation, AlgorithmDeserialiser algo)throws  Exception  {
        try {
            byte[] dk = this.keyManager.FetchDecryptionKey(algo.Key());
            KeyOperation ops = this.operations.get(algo.GetAlgorithm());
            KeyFactory factory = ops.GetFactory();
            Key key = factory.NewKeyFromData(ops, dk);
            AEAD primitive = factory.Primitive(key);
            
            return primitive;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public byte[] Encrypt(byte[] planData, byte[] associateData)throws  Exception  {
        String operation = "Encrypt";
        Instant start = Instant.now();

        Pair<AlgorithmSeriliser, AEAD> result = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = result.getValue0();
        AEAD primitive = result.getValue1();

        byte[] cipherData = primitive.Encrypt(planData, associateData);

        Writer cipherStream = new StringWriter();
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        processor.WriteHeader(header);
        processor.WritePacket(cipherData);

        return cipherStream.toString().getBytes();
    }

    public byte[] Decrypt(byte[] cipherData, byte[] associateData) throws  Exception {
        String operation = "Decrypt";
        Instant start = Instant.now();
        Reader cipherStream = new StringReader(cipherData.toString());
        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.ReadHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);
        byte[] cipher = processor.ReadPacket();
        byte[] plainData = primitive.Decrypt(cipher, associateData);

        return plainData.toString().getBytes();
    }

    public void EncryptStream(Reader plainStream, Writer cipherStream, byte[] associatedData)throws  Exception  {
        String operation = "EncryptStream";
        Instant start = Instant.now();

        Pair<AlgorithmSeriliser, AEAD> result = this.initEncryptOperation(operation);
        AlgorithmSeriliser header = result.getValue0();
        AEAD primitive = result.getValue1();
        
        PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
        
        char[] tempBuffer = new char[32*1024];
        while (true) {
            try {
                int datalen = plainStream.read(tempBuffer);

                String bufferStr = new String(tempBuffer);
                byte[] cipherText = primitive.Encrypt(Arrays.copyOfRange(bufferStr.getBytes(), 0, datalen), associatedData);
                processor.WritePacket(cipherText);
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                return ;
            }
        }
        return;
    }

    public void DecryptStream(Writer plainStream, Reader cipherStream, byte[] associatedData) throws  Exception {
        String operation = "DecryptStream";
        Instant start = Instant.now();

        EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
        AlgorithmDeserialiser algo = processor.ReadHeader();
        AEAD primitive = this.initDecryptOperation(operation, algo);

        while (true) {
            try {
                byte[] cipher = processor.ReadPacket();
                byte[] plainData = primitive.Decrypt(cipher, associatedData);
                plainStream.write(plainData.toString());
            } catch (IOException e) {
                break;
            } catch (Exception e) {
                return ;
            }
        }
        return;
    }

    public OnqlaveStructure EncryptStructure(Map<String, Object> plainStructure, byte[] associatedData)throws  Exception  {
        String operation = "EncryptStructure";
        Instant start = Instant.now();

        Pair<AlgorithmSeriliser, AEAD> tup = this.initEncryptOperation(operation);
        AlgorithmSeriliser algo = tup.getValue0();
        AEAD primitive = tup.getValue1();

        byte[] header = algo.Serialise();
        Map<String, byte[]> result = new HashMap<>();
        TypeResolver resolver = new TypeResolverImpl();
        for(Map.Entry<String, byte[]> e : result.entrySet()) {
            byte[] plainData = resolver.Serialise(e.getKey(), e.getValue());

            byte[] cipherData = primitive.Encrypt(plainData, associatedData);

            result.put(e.getKey(), cipherData);
        }

        return new OnqlaveStructure(header, result);
    }

    public Map<String, Object> DecryptStructure(OnqlaveStructure cipherStructure , byte[] associatedData)throws  Exception  {
        String operation = "EncryptStructure";
        Instant start = Instant.now();

        BufferedReader cipherStream = new BufferedReader(null);
        // InputStream cipherStream = new ByteArrayInputStream(cipherStructure.getEdk());
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

        return result;
    }
}
