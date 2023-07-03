package com.onqlave.types;

import java.nio.ByteBuffer;
import java.util.Date;

public class TypeResolverImpl implements TypeResolver {
  @Override
  public byte[] Serialise(String name, Object input) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    Object i = input;
    Class<?> inputClass = input.getClass();

    if (inputClass == Boolean.class) {
      boolean b = (boolean) i;
      buffer.put((byte) 1);
      buffer.put((byte) (b ? 1 : 0));
    } else if (inputClass == Byte.class) {
      byte v = (byte) i;
      buffer.put((byte) 2);
      buffer.put(v);
    } else if (inputClass == Short.class) {
      short v = (short) i;
      buffer.put((byte) 3);
      buffer.putShort(v);
    } else if (inputClass == Integer.class) {
      int v = (int) i;
      buffer.put((byte) 4);
      buffer.putInt(v);
    } else if (inputClass == Long.class) {
      long v = (long) i;
      buffer.put((byte) 5);
      buffer.putLong(v);
    } else if (inputClass == Float.class) {
      float v = (float) i;
      buffer.put((byte) 6);
      buffer.putFloat(v);
    } else if (inputClass == Double.class) {
      double v = (double) i;
      buffer.put((byte) 7);
      buffer.putDouble(v);
    } else if (inputClass == Date.class) {
      long v = ((Date) i).getTime();
      buffer.put((byte) 8);
      buffer.putLong(v);
    } else if (inputClass == String.class) {
      String v = (String) i;
      byte[] bytes = v.getBytes();
      buffer.put((byte) 9);
      buffer.putInt(bytes.length);
      buffer.put(bytes);
    } else if (inputClass == byte[].class) {
      byte[] v = (byte[]) i;
      buffer.put((byte) 10);
      buffer.putInt(v.length);
      buffer.put(v);
    } else {
      throw new Exception(name + ": unsupported type: " + inputClass.getSimpleName());
    }

    buffer.flip();
    byte[] result = new byte[buffer.remaining()];
    buffer.get(result);
    return result;
  }

  @Override
  public Object Deserialise(String name, byte[] input) throws Exception {
    ByteBuffer buffer = ByteBuffer.wrap(input);
    byte kind = buffer.get();
    Object val;

    switch (kind) {
      case 1: // Bool
        byte b = buffer.get();
        val = b == 1;
        break;
      case 2: // Byte
        val = buffer.get();
        break;
      case 3: // Int16
        val = buffer.getShort();
        break;
      case 4: // Int32
        val = buffer.getInt();
        break;
      case 5: // Int64
        val = buffer.getLong();
        break;
      case 6: // Float
        int floatBits = buffer.getInt();
        val = Float.intBitsToFloat(floatBits);
        break;
      case 7: // Double
        long doubleBits = buffer.getLong();
        val = Double.longBitsToDouble(doubleBits);
        break;
      case 8: // Time
        long unixTime = buffer.getLong();
        val = new Date(unixTime * 1000);
        break;
      case 9: // Struct
        int strLength = buffer.getInt();
        byte[] strBytes = new byte[strLength];
        buffer.get(strBytes);
        val = new String(strBytes);
        break;
      case 10: // Slice
        int sliceLength = buffer.getInt();
        byte[] sliceBytes = new byte[sliceLength];
        buffer.get(sliceBytes);
        val = sliceBytes;
        break;
      default:
        throw new Exception(name + ": unsupported type: " + kind);
    }

    return val;
  }
}
