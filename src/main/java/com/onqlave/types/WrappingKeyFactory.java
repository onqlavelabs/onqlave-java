package com.onqlave.types;

public interface WrappingKeyFactory {
    Unwrapping primitive(WrappingKeyOperation operation) throws Exception;
}
