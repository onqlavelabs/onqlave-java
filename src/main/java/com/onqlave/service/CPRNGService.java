package com.onqlave.service;

import java.io.Reader;

public interface CPRNGService {
    public byte[] GetRandomBytes( int numberBytes);
    public int GetRandomInt();
    public Reader GetRandomReader();
}
