package com.onqlave.encryption;

import com.onqlave.keymanager.KeyManager;
import com.onqlave.types.AlgorithmDeserialiser;
import com.onqlave.types.KeyOperation;
import com.onqlave.types.OnqlaveStructure;
import org.javatuples.Tuple;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public class Encryption {
    private KeyManager keyManager;
    //private Logger logger;

    private Map<String, KeyOperation> operations;

    // constructor
    public Encryption() {

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
