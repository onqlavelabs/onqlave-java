package com.onqlave.encryption;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.onqlave.types.Algorithm;

public class TestEncryptedStreamProcessor {
  @Mock
  private Algorithm algorithm;

  public TestEncryptedStreamProcessor() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void TestWriteHeader() throws Exception{
    byte[] inputData = {1, 2, 3};
    when(algorithm.Serialise()).thenReturn(inputData);

    ByteArrayOutputStream cipherStream = new ByteArrayOutputStream();
    PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
    processor.WriteHeader(algorithm);

    byte[] writtenArray = cipherStream.toByteArray();

    assertArrayEquals(inputData, writtenArray);

    verify(algorithm, times(1)).Serialise();
  }

  @Test
  public void TestWritePacket() throws Exception {
    byte[] inputData = {1, 2, 3, 4, 5, 6};
    ByteArrayOutputStream cipherStream = new ByteArrayOutputStream();
    PlainStreamProcessor processor = new PlainStreamProcessorImpl(cipherStream);
    processor.WritePacket(inputData);

    byte[] writtenArray = cipherStream.toByteArray();

    assertEquals(inputData.length + 4, writtenArray.length);
  }
}
