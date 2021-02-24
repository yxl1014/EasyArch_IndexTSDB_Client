package com.EasyArch.TSDB.Client;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {
    private List<byte[]> results = new ArrayList<>();//报文的集合

    private List<String> datas = new ArrayList<>();//处理完返回的数据的集合

    /*
     * 1、error=1,空指针异常
     * 2、error=2,数据执行异常
     * 3、error=3,权限异常
     * 4、error=0,执行成功
     * */
    private int error;//错误

    private String errors;//错误信息

    private byte agreement;//报文类型

    //pang
    private byte edition;//版本号
    private boolean pangResult;//pang结果

    //server
    private byte protocolversion;//协议版本
    private byte code;//编码格式
    private byte timeout;//心跳间隔时间

    public boolean getResult(byte[] result) {//获取报文，拼报文
        if (result == null)//报文不为空
            return false;

        this.results.add(result);//加到链表里
        return true;
    }

    public void ResultToDataIsPang() {//将报文集合里的数据处理到数据集合里
        int len = 0;
        for (byte[] bytes : results) {
            len += bytes.length;
        }
        byte[] datas = new byte[len];
        len = 0;
        for (byte[] bytes : results) {
            System.arraycopy(bytes, 0, datas, len, bytes.length);
            len += bytes.length;
        }

        this.edition = datas[0];
        this.pangResult = datas[1] == 1;

    }

    public void ResultToDataIsServer() {
        int len = 0;
        for (byte[] bytes : results) {
            len += bytes.length;
        }
        byte[] datas = new byte[len];
        len = 0;
        for (byte[] bytes : results) {
            System.arraycopy(bytes, 0, datas, len, bytes.length);
            len += bytes.length;
        }

        this.protocolversion=datas[0];
        this.code=datas[1];
        this.timeout=datas[2];
    }

    public void ResultToDataIsResult() {

    }

    public List<String> getDatas() {
        return datas;
    }

    public int getError() {
        return error;
    }

    public byte getAgreement() {
        return agreement;
    }

    public String getErrors() {
        return errors;
    }

    public byte getEdition() {
        return edition;
    }

    public boolean isPangResult() {
        return pangResult;
    }


    public void setAgreement(byte agreement) {
        this.agreement = agreement;
    }
}
