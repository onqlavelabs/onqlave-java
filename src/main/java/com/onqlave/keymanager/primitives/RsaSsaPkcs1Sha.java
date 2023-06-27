package com.onqlave.keymanager.primitives;

import com.onqlave.service.CPRNGService;
import com.onqlave.types.Unwrapping;

public class RsaSsaPkcs1Sha implements Unwrapping {
    private  CPRNGService randomService;
    private  HashFunction hashFunc;
    private  int hashID;

    public RsaSsaPkcs1Sha(CPRNGService randomService, HashFunction hashFunc, int hashID) {
        this.randomService = randomService;
        this.hashFunc = hashFunc;
        this.hashID = hashID;
    }

    @Override
    public byte[] UnwrapKey(byte[] wdk, byte[] epk, byte[] fp, byte[] password) throws Exception {
        return new byte[0];
    }
}



