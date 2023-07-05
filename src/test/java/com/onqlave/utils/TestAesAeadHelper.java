package com.onqlave.utils;

import com.onqlave.service.CPRNGServiceImpl;
import org.junit.Test;

public class TestAesAeadHelper {
    @Test
    public void TestAes() throws Exception {
        String plainText = "this is a plain string";
        CPRNGServiceImpl random = new CPRNGServiceImpl();
        byte[] key = random.GetRandomBytes(32);
        byte[] iv = random.GetRandomBytes(12);
        byte []ciphertext = AESAEADHelper.Encrypt(plainText.getBytes(), new byte[0], key, iv);

        byte []plaintext = AESAEADHelper.Decrypt(ciphertext, new byte[0], key, iv);

        System.out.println(new String(plaintext));
    }
}
