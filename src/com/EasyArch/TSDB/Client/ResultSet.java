package com.EasyArch.TSDB.Client;

public class ResultSet {
    private byte[] result;

    public ResultSet(byte[] result) {
        this.result = new byte[result.length];
        System.arraycopy(result, 0, this.result, 0, result.length);
    }
}
