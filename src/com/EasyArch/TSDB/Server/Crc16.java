package com.EasyArch.TSDB.Server;

public class Crc16 {

    public static byte[] getCRC(byte[] bytes) {
        short CRC = 255;
        short POLYNOMIAL = 161;

        for(int i = 0; i < bytes.length; ++i) {
            CRC = (short)(CRC ^ bytes[i] & 255);

            for(int j = 0; j < 8; ++j) {
                if ((CRC & 1) != 0) {
                    CRC = (short)(CRC >> 1);
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC = (short)(CRC >> 1);
                }
            }
        }

        return shortToByte(CRC);
    }

    public static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];

        for(int i = 0; i < b.length; ++i) {
            b[i] = (new Integer(temp & 255)).byteValue();
            temp >>= 8;
        }

        return b;
    }
}
