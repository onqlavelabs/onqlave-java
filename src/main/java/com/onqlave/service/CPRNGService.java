package com.onqlave.service;

import java.io.InputStream;

public interface CPRNGService {
    byte[] GetRandomBytes(int numberBytes);
    int GetRandomInt32Bytes();
    InputStream GetRandomReader();
}
