package com.EasyArch.TSDB.Test;

import com.EasyArch.TSDB.Server.Agreement_Head;

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
        String fixed1 = "abcdefg";
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
    }
}
