package com.onqlave.encryption;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.onqlave.types.Algorithm;

public class TestPlainStreamProcessor {
  @Mock
  private Algorithm algorithm;

  public TestPlainStreamProcessor() {
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
