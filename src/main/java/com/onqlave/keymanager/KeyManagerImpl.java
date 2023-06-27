package com.onqlave.keymanager;

import com.onqlave.connection.Connection;
import com.onqlave.contract.Configuration;
import com.onqlave.service.CPRNGService;
import com.onqlave.types.WrappingKeyOperation;
import org.javatuples.Tuple;

import java.util.Map;

public class KeyManagerImpl implements KeyManager {
    public Connection keyManager;
    public Configuration configuration;

    public Map<String, WrappingKeyOperation> operation;

    //constructor
    public KeyManagerImpl(Configuration configuration, CPRNGService randomService) {
        //TODO: implement constructor
        this.configuration = configuration;

    }

    @Override
    public Tuple FetchEncryptionKey() throws Exception {
        return null;
    }

    @Override
    public Tuple FetchDecryptionKey() throws Exception {
        return null;
    }
}
