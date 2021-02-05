package com.EasyArch.TSDB.Server;

public class Crc16 {

    public static byte[] getCRC(byte[] bytes) {
        short CRC = 0x00ff;
        short POLYNOMIAL = 0x00a1;

        int i, j;
        for (i = 0; i < bytes.length; i++) {
            CRC ^= ((int) bytes[i] & 0x00ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x0001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
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
