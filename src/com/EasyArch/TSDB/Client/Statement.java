package com.EasyArch.TSDB.Client;

import java.net.Socket;

public class Statement {

    private String userid;//用户id
    private byte protocolversion;//协议版本
    private byte code;//编码格式
    private byte timeout;//心跳间隔时间
    private Socket socket;//http连接


    public Statement(String userid, byte protocolversion, byte code, byte timeout, Socket socket) {
        this.userid = userid;
        this.protocolversion = protocolversion;
        this.code = code;
        this.timeout = timeout;
        this.socket = socket;
    }
}
