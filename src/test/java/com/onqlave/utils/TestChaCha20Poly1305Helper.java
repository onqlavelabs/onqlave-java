package com.onqlave.utils;

import com.onqlave.service.CPRNGServiceImpl;
import org.junit.Test;

public class TestChaCha20Poly1305Helper {

    @Test
    public void TestXChaCha20Poly1305() throws Exception {
        String plainText = "this is a plain text";
        CPRNGServiceImpl random = new CPRNGServiceImpl();
        byte[] key = random.getRandomBytes(32);
        byte[] none = random.getRandomBytes(12);


        byte[] encData = XChaCha20Poly1305Helper.encrypt(key, plainText.getBytes(), new byte[0]);


        System.out.println(encData.toString());

        System.out.println(new String(none));
        byte[] pt = XChaCha20Poly1305Helper.decrypt(key, encData, new byte[0]);


        System.out.println(new String(pt));
    }
}
