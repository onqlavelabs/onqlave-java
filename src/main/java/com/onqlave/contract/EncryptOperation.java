package com.onqlave.contract;

import com.onqlave.types.AEAD;
import com.onqlave.types.AlgorithmSeriliser;

public class EncryptOperation {
    private AlgorithmSeriliser seriliser;
    private AEAD method;

    public EncryptOperation(AlgorithmSeriliser seriliser, AEAD method) {
        this.seriliser = seriliser;
        this.method = method;
    }

    public AlgorithmSeriliser getSeriliser() {
        return seriliser;
    }

    public void setSeriliser(AlgorithmSeriliser seriliser) {
        this.seriliser = seriliser;
    }

    public AEAD getMethod() {
        return method;
    }

    public void setMethod(AEAD method) {
        this.method = method;
    }
}
