package com.onqlave.encryption;

import org.junit.Test;

public class TestCase  {
    @Test
    public void encryptAes128Test() {
        String accessKey = System.getenv("AES_128_ACCESS_KEY");
        String arxURL = System.getenv("AES_128_ARX_URL");
        String signingKey = System.getenv("AES_128_SIGNING_KEY");
        String secretKey = System.getenv("AES_128_SECRET_KEY");

        new TestEncryption(accessKey, arxURL, signingKey, secretKey);
    }
    @Test
    public void encryptAes256Test() {
        String accessKey = System.getenv("AES_256_ACCESS_KEY");
        String arxURL = System.getenv("AES_256_ARX_URL");
        String signingKey = System.getenv("AES_256_SIGNING_KEY");
        String secretKey = System.getenv("AES_256_SECRET_KEY");

        new TestEncryption(accessKey, arxURL, signingKey, secretKey);
    }
    @Test
    public void encryptXchachaTest(){
        String accessKey = System.getenv("XCHACHA_ACCESS_KEY");
        String arxURL = System.getenv("XCHACHA_ARX_URL");
        String signingKey = System.getenv("XCHACHA_SIGNING_KEY");
        String secretKey = System.getenv("XCHACHA_SECRET_KEY");

        new TestEncryption(accessKey, arxURL, signingKey, secretKey);
    }

}
