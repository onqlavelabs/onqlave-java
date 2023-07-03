package com.onqlave.types;

public interface TypeResolver {
  byte[] Serialise(String name, Object input) throws Exception;
  Object Deserialise(String name, byte[] input) throws Exception;
}
