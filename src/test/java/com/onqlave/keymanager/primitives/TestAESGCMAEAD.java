package com.onqlave.keymanager.primitives;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.onqlave.service.CPRNGService;
import com.onqlave.service.CPRNGServiceImpl;
import com.onqlave.types.Key;
import com.onqlave.types.KeyData;

public class TestAESGCMAEAD {
  @Mock
  private Key key;

  @Mock
  private KeyData keydata;

  public TestAESGCMAEAD() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void TestEncryptDecrypt() throws Exception {
    CPRNGService randomService = new CPRNGServiceImpl();
    byte[] keyVal = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16};

    when(key.Data()).thenReturn(keydata);
    when(keydata.GetValue()).thenReturn(keyVal);

    AesGcmAead encryptor = new AesGcmAead(key, randomService);

    String plainText = "this is a test plaintext";
    byte[] cipherText = encryptor.Encrypt(plainText.getBytes(), null);

    byte[] decryptedText = encryptor.Decrypt(cipherText, null);

    assertArrayEquals(plainText.getBytes(), decryptedText);
  }
}
