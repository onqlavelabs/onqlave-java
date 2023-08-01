package com.onqlave.contract;

import com.onqlave.types.AEAD;
import com.onqlave.types.AlgorithmSeriliser;

public record EncryptOperation(AlgorithmSeriliser seriliser, AEAD method) {
}
