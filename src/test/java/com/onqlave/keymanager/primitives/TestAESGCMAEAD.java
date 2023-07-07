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
  public void TestEncryptDecrypt_16ByteKeySize() throws Exception {
    CPRNGService randomService = new CPRNGServiceImpl();
    byte[] keyVal = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16};

    when(key.Data()).thenReturn(keydata);
    when(keydata.GetValue()).thenReturn(keyVal);

    AesGcmAead encryptor = new AesGcmAead(key, randomService);

    String plainText = "this is a test plaintext";
    String associatedData = "this is associated data";
    byte[] cipherText = encryptor.Encrypt(plainText.getBytes(), associatedData.getBytes());

    byte[] decryptedText = encryptor.Decrypt(cipherText, associatedData.getBytes());

    assertArrayEquals(plainText.getBytes(), decryptedText);
  }

  @Test
  public void TestEncryptDecrypt_32ByteKeySize() throws Exception {
    CPRNGService randomService = new CPRNGServiceImpl();
    byte[] keyVal = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16, 1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16};

    when(key.Data()).thenReturn(keydata);
    when(keydata.GetValue()).thenReturn(keyVal);

    AesGcmAead encryptor = new AesGcmAead(key, randomService);

    String plainText = "this is a test plaintext";
    String associatedData = "this is associated data";
    byte[] cipherText = encryptor.Encrypt(plainText.getBytes(), associatedData.getBytes());

    byte[] decryptedText = encryptor.Decrypt(cipherText, associatedData.getBytes());

    assertArrayEquals(plainText.getBytes(), decryptedText);
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestEncryptDecrypt_NullPlainText() throws Exception {
    CPRNGService randomService = new CPRNGServiceImpl();
    byte[] keyVal = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16};

    when(key.Data()).thenReturn(keydata);
    when(keydata.GetValue()).thenReturn(keyVal);

    AesGcmAead encryptor = new AesGcmAead(key, randomService);

    String associatedData = "this is associated data";

    // throw new IllegalArgumentException
    byte[] cipherText = encryptor.Encrypt(null, associatedData.getBytes());
  }

  @Test (expected = IllegalArgumentException.class)
  public void TestEncryptDecrypt_NullAssociatedData() throws Exception {
    CPRNGService randomService = new CPRNGServiceImpl();
    byte[] keyVal = {1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13 , 14, 15, 16};

    when(key.Data()).thenReturn(keydata);
    when(keydata.GetValue()).thenReturn(keyVal);

    AesGcmAead encryptor = new AesGcmAead(key, randomService);

    String plainText = "this is a test plaintext";

    // throw new IllegalArgumentException
    byte[] cipherText = encryptor.Encrypt(plainText.getBytes(), null);
  }

}
