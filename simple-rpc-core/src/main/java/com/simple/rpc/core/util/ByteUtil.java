package com.simple.rpc.core.util;

import java.nio.ByteOrder;

/**
 * 项目: simple-rpc
 * <p>
 * 功能描述: 字节处理类
 *
 * @author: WuChengXing
 * @create: 2022-04-27 19:00
 **/
public class ByteUtil {
    public ByteUtil() {
    }

    public static byte intToByte(int intValue) {
        return (byte) intValue;
    }

    public static int byteToUnsignedInt(byte byteValue) {
        return byteValue & 255;
    }

    public static short bytesToShort(byte[] bytes) {
        return bytesToShort(bytes, ByteOrder.LITTLE_ENDIAN);
    }

    public static short bytesToShort(byte[] bytes, ByteOrder byteOrder) {
        return ByteOrder.LITTLE_ENDIAN == byteOrder ? (short) (bytes[1] & 255 | (bytes[0] & 255) << 8) : (short) (bytes[0] & 255 | (bytes[1] & 255) << 8);
    }

    public static byte[] shortToBytes(short shortValue) {
        return shortToBytes(shortValue, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] shortToBytes(short shortValue, ByteOrder byteOrder) {
        byte[] b = new byte[2];
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            b[1] = (byte) (shortValue & 255);
            b[0] = (byte) (shortValue >> 8 & 255);
        } else {
            b[0] = (byte) (shortValue & 255);
            b[1] = (byte) (shortValue >> 8 & 255);
        }

        return b;
    }

    public static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, ByteOrder.LITTLE_ENDIAN);
    }

    public static int bytesToInt(byte[] bytes, ByteOrder byteOrder) {
        return ByteOrder.LITTLE_ENDIAN == byteOrder ? bytes[3] & 255 | (bytes[2] & 255) << 8 | (bytes[1] & 255) << 16 | (bytes[0] & 255) << 24 : bytes[0] & 255 | (bytes[1] & 255) << 8 | (bytes[2] & 255) << 16 | (bytes[3] & 255) << 24;
    }

    public static byte[] intToBytes(int intValue) {
        return intToBytes(intValue, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] intToBytes(int intValue, ByteOrder byteOrder) {
        return ByteOrder.LITTLE_ENDIAN == byteOrder ? new byte[]{(byte) (intValue >> 24 & 255), (byte) (intValue >> 16 & 255), (byte) (intValue >> 8 & 255), (byte) (intValue & 255)} : new byte[]{(byte) (intValue & 255), (byte) (intValue >> 8 & 255), (byte) (intValue >> 16 & 255), (byte) (intValue >> 24 & 255)};
    }

    public static byte[] longToBytes(long longValue) {
        return longToBytes(longValue, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] longToBytes(long longValue, ByteOrder byteOrder) {
        byte[] result = new byte[8];
        int i;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (i = result.length - 1; i >= 0; --i) {
                result[i] = (byte) ((int) (longValue & 255L));
                longValue >>= 8;
            }
        } else {
            for (i = 0; i < result.length; ++i) {
                result[i] = (byte) ((int) (longValue & 255L));
                longValue >>= 8;
            }
        }

        return result;
    }

    public static long bytesToLong(byte[] bytes) {
        return bytesToLong(bytes, ByteOrder.LITTLE_ENDIAN);
    }

    public static long bytesToLong(byte[] bytes, ByteOrder byteOrder) {
        long values = 0L;
        int i;
        if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
            for (i = 0; i < 8; ++i) {
                values <<= 8;
                values |= (long) (bytes[i] & 255);
            }
        } else {
            for (i = 7; i >= 0; --i) {
                values <<= 8;
                values |= (long) (bytes[i] & 255);
            }
        }

        return values;
    }

    public static byte[] doubleToBytes(double doubleValue) {
        return doubleToBytes(doubleValue, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] doubleToBytes(double doubleValue, ByteOrder byteOrder) {
        return longToBytes(Double.doubleToLongBits(doubleValue), byteOrder);
    }

    public static double bytesToDouble(byte[] bytes) {
        return bytesToDouble(bytes, ByteOrder.LITTLE_ENDIAN);
    }

    public static double bytesToDouble(byte[] bytes, ByteOrder byteOrder) {
        return Double.longBitsToDouble(bytesToLong(bytes, byteOrder));
    }

    public static byte[] numberToBytes(Number number) {
        return numberToBytes(number, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] numberToBytes(Number number, ByteOrder byteOrder) {
        if (number instanceof Double) {
            return doubleToBytes((Double) number, byteOrder);
        } else if (number instanceof Long) {
            return longToBytes((Long) number, byteOrder);
        } else if (number instanceof Integer) {
            return intToBytes((Integer) number, byteOrder);
        } else {
            return number instanceof Short ? shortToBytes((Short) number, byteOrder) : doubleToBytes(number.doubleValue(), byteOrder);
        }
    }
}
