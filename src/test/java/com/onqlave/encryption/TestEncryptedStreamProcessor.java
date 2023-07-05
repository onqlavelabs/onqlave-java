package com.onqlave.encryption;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.onqlave.types.Algorithm;
import com.onqlave.types.AlgorithmDeserialiser;

public class TestEncryptedStreamProcessor {
  @Mock
  private Algorithm algorithm;

  private byte[] cipherData;
  private byte[] inputData = {1, 2, 3, 4, 5, 6};

  public TestEncryptedStreamProcessor() {
    MockitoAnnotations.openMocks(this);
  }

  @Before
  public void prepareTest() throws Exception {
    Algorithm algo = new Algorithm(0, "aes-gcm-128", inputData);

    ByteArrayOutputStream cipherStream = new ByteArrayOutputStream();
    PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
    processor.WriteHeader(algo);
    processor.WritePacket(inputData);

    cipherData = cipherStream.toByteArray();
  }

  @Test
  public void TestWriteHeader() throws Exception {
    ByteArrayInputStream cipherStream = new ByteArrayInputStream(cipherData);
    EncryptedStreamProcessor processor = new EncryptedStreamProcessorImpl(cipherStream);
    AlgorithmDeserialiser algo = processor.ReadHeader();

    assertNotNull(algo);
    assertEquals(algo.Version(), 0);
    assertArrayEquals(algo.Key(), inputData);
    assertEquals(algo.GetAlgorithm(), "aes-gcm-128");
  }

  @Test
  public void TestWritePacket() {

  }
}
