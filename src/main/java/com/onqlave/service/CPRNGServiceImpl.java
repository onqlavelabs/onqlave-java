package com.onqlave.service;

import java.io.Reader;

public class CPRNGServiceImpl implements CPRNGService {
    public CPRNGServiceImpl() {
    }

    @Override
    public byte[] GetRandomBytes(int numberBytes) {
        return new byte[0];
    }

    @Override
    public int GetRandomInt() {
        return 0;
    }

    @Override
    public Reader GetRandomReader() {
        return null;
    }
}
