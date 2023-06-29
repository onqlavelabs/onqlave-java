package com.onqlave.service;

import java.io.Reader;

public interface CPRNGService {
    byte[] GetRandomBytes(int numberBytes);

    int GetRandomInt();

    Reader GetRandomReader();
}
