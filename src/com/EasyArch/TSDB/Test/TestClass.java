package com.EasyArch.TSDB.Test;

public class TestClass {
    private int port = 0;

    public TestClass(int port) {
        this.port = port;
    }

    {
        System.out.println(this.port);
    }
}
