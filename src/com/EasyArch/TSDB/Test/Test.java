package com.EasyArch.TSDB.Test;

import com.EasyArch.TSDB.Message.Agreement_Head;
import com.EasyArch.TSDB.Message.ReceivingMessage;

public class Test {
    private static int toLen(String Messagelen) {
        int len = Messagelen.length();
        int size;
        if (len % 8 == 0) {
            size = len / 8;
        } else {
            size = len / 8 + 1;
        }

        return size;
    }

    public static void main(String[] args) {
        /*String fixed1 = "abcdefg";
        byte[] fixed = fixed1.getBytes();
        String no = "1234567";
        byte[] nofixd = no.getBytes();
        byte[] b = new byte[fixed.length + nofixd.length];
        System.arraycopy(fixed, 0, b, 0, fixed.length);
        System.arraycopy(nofixd, 0, b, fixed.length, nofixd.length);
        System.out.println(new String(b));


        String Messagelen = Integer.toBinaryString(255);
        int l = toLen(Messagelen);
        System.out.println(Messagelen);
        System.out.println(l);


        String M = Integer.toBinaryString(255 + l);
        int size = toLen(M);
        System.out.println(M);

        System.out.println(Integer.parseInt(M,2));
        System.out.println(size);


        byte[] bb = M.getBytes();
        System.out.println(new String(bb));
        System.out.println(Agreement_Head.DDL_CLEARCACHE);

        byte[] result=new byte[3];
        System.out.println(result[0]);*/

        //DDLMessage(byte agreement, byte edition, byte[] name)

        byte edition=0x01;

        byte[] bytes= ReceivingMessage.DDLMessage(Agreement_Head.DDL_CREATETABLE,edition,"users".getBytes());
        for (byte b:bytes){
            System.out.print((int) b+" ");
        }
        System.out.println();

        System.out.println(Agreement_Head.DDL_CREATETABLE);
        System.out.println(new String(bytes).length());

        System.out.println(new String(Agreement_Head.END));
        System.out.println(Agreement_Head.END.length);
       /* byte[] size=LongToBytes(10L);
        for (byte b:size){
            System.out.print(b);
        }*/

    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }
    public static byte[] LongToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }
}
