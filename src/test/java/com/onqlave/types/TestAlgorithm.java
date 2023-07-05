package com.onqlave.types;

import org.junit.Test;

import java.util.HexFormat;

public class TestAlgorithm {
    @Test
    public void TestSerialise() throws Exception {
        Algorithm algo = new Algorithm((byte) 0, "aes-gcm-128", HexFormat.of().parseHex("12312123123123123123123456"));
        byte[] value = algo.Serialise();
        System.out.println(value);
    }
}
