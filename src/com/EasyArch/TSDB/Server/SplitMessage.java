package com.EasyArch.TSDB.Server;

public class SplitMessage {
    public byte[] resultMessage(byte[] message) {
        byte edition = message[0];

        int length = getSize(message);
        byte[] size = new byte[length];
        System.arraycopy(message, 1, size, 0, length);

        byte[] crc16 = new byte[2];
        System.arraycopy(message, 1 + length, crc16, 0, 2);

        int fixedsize = length + 3;
        int nofixedsize = message.length - length;

        byte result = message[fixedsize];

        byte[] data = new byte[nofixedsize - 1];
        System.arraycopy(message, fixedsize + 1, data, 0, nofixedsize - 1);

        return null;
    }

    private int getSize(byte[] message) {
        int size = 0;
        int length = message.length;
        for (int i = 1; i < length; i++) {
            byte[] len = new byte[i];
            System.arraycopy(message, 1, len, 0, i);
            if (Integer.parseInt(new String(len), 2) == length) {
                size = i;
                break;
            }
        }
        return size;
    }
}
