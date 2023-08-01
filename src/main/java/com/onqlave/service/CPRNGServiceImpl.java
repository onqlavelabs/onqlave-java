package com.onqlave.service;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class CPRNGServiceImpl implements CPRNGService {
    private SecureRandom random;
    public CPRNGServiceImpl() throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstanceStrong();
    }

    @Override
    public byte[] getRandomBytes(int numberBytes) {
        byte[] buf = new byte[numberBytes];
        random.nextBytes(buf);
        return buf;
    }

    @Override
    public int getRandomInt32Bytes() {
        byte[] buf = new byte[4];
        this.random.nextBytes(buf);
        return ByteBuffer.wrap(buf).getInt();
    }

    @Override
    public InputStream getRandomReader() {
        return new InputStream() {
            public int read() {
                byte[] buf = new byte[1];
                random.nextBytes(buf);
                return buf[0];
            }
        };
    }
}
