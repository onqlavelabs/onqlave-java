package com.onqlave.utils;

import com.onqlave.service.CPRNGServiceImpl;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

public class TestChaCha20Poly1305Helper {

    @Test
    public void TestEncDecChaChaPoly() throws Exception {
        String plainText = "this is a plain text";
        CPRNGServiceImpl random = new CPRNGServiceImpl();
        byte[] key = random.GetRandomBytes(32);
        byte[] none = random.GetRandomBytes(12);


        byte[] encData = ChaCha20Poly1305Helper.Encrypt(plainText.getBytes(), key, none);


        System.out.println(encData.toString());

        System.out.println(new String(none));
        byte[] pt = ChaCha20Poly1305Helper.Decrypt(encData, key);


        System.out.println(new String(pt));
    }
}
