package com.onqlave.service;

import java.io.InputStream;

public interface CPRNGService {
    byte[] getRandomBytes(int numberBytes);
    int getRandomInt32Bytes();
    InputStream getRandomReader();
}
