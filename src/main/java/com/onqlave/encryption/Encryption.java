package com.onqlave.encryption;

import com.onqlave.contract.Configuration;
import com.onqlave.contract.Credential;
import com.onqlave.contract.RetrySettings;
import com.onqlave.keymanager.KeyManager;
import com.onqlave.keymanager.KeyManagerImpl;
import com.onqlave.keymanager.factories.AesGcmFactory;
import com.onqlave.keymanager.factories.Xchacha20Poly1305Factory;
import com.onqlave.keymanager.keys.AesGcmKey;
import com.onqlave.keymanager.operations.Aes128GcmOperation;
import com.onqlave.keymanager.operations.Aes256GcmOperation;
import com.onqlave.keymanager.operations.Xchacha20Poly1305Operation;
import com.onqlave.service.CPRNGService;
import com.onqlave.service.CPRNGServiceImpl;
import com.onqlave.service.IDService;
import com.onqlave.service.IDServiceImpl;
import com.onqlave.types.*;
import org.javatuples.Tuple;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
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

    private Tuple initEncryptOperation(String operation) throws  Exception {
        return null;
    }

    private Tuple initDecryptOperation(String operation, AlgorithmDeserialiser algo)throws  Exception  {
        return null;
    }

    public Tuple Encrypt(byte[] planData, byte[] associateData)throws  Exception  {
        return null;
    }

    public Tuple Decrypt(byte[] planData, byte[] associateData) throws  Exception {
        return null;
    }

    public Tuple EncryptStream(InputStream plainStream, OutputStream cipherStream, byte[] associatedData)throws  Exception  {
        return null;
    }

    public Tuple DecryptStream(InputStream plainStream, OutputStream cipherStream, byte[] associatedData) throws  Exception {
        return null;
    }
    public Tuple EncryptStructure(Map<String, Object> plainStructure, byte[] associatedData)throws  Exception  {
        return null;
    }

    public Tuple DecryptStructure(OnqlaveStructure cipherStructure , byte[] associatedData)throws  Exception  {
        return null;
    }


}
