package com.onqlave.service;

import java.util.UUID;

public class IDServiceImpl implements IDService {
    private CPRNGService randomService;

    public IDServiceImpl(CPRNGService randomService) {
        this.randomService = randomService;
    }

    @Override
    public String NewStringID() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int NewKeyID() {
        return randomService.GetRandomInt32Bytes();
    }
}
