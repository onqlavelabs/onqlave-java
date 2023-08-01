package com.onqlave.utils;

import com.onqlave.service.CPRNGServiceImpl;
import org.junit.Test;

public class TestAesAeadHelper {
    @Test
    public void TestAes() throws Exception {
        String plainText = "this is a plain string";
        CPRNGServiceImpl random = new CPRNGServiceImpl();
        byte[] key = random.getRandomBytes(32);
        byte[] iv = random.getRandomBytes(12);
        byte []ciphertext = AESAEADHelper.encrypt(plainText.getBytes(), new byte[0], key, iv);

        byte []plaintext = AESAEADHelper.decrypt(ciphertext, new byte[0], key, iv);

        System.out.println(new String(plaintext));
    }
}
