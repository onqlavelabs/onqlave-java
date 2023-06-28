package com.onqlave.service;

public class IDServiceImpl  implements IDService{
    private CPRNGService randomService;

    public IDServiceImpl(CPRNGService randomService) {
        this.randomService = randomService;
    }

    public CPRNGService getRandomService() {
        return randomService;
    }

    public void setRandomService(CPRNGService randomService) {
        this.randomService = randomService;
    }

    @Override
    public String NewStringID() {
        return null;
    }

    @Override
    public int NewKeyID() {
        return 0;
    }
}
