package com.EasyArch.TSDB.Client;

import com.EasyArch.TSDB.Result.Data;

import java.util.ArrayList;
import java.util.List;

public class ResultSet {
    private List<byte[]> results = new ArrayList<>();//报文的集合

    //private List<String> datas = new ArrayList<>();//处理完返回的数据的集合

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

    //result
    private byte result;//执行结果
    private final Data datas=new Data();//数据


    public boolean getResult(byte[] result) {//获取报文，拼报文
        if (result == null)//报文不为空
            return false;

        this.results.add(result);//加到链表里
        return true;
    }

    public void ResultToDataIsPang() {//将报文集合里的数据处理到数据集合里
        int len = 0;//获取总数据的长度
        for (byte[] bytes : results) {//统计报文list里的所有报文的长度
            len += bytes.length;
        }
        byte[] datas = new byte[len];//定义一个一整个数据的数组
        len = 0;
        for (byte[] bytes : results) {//将报文list里的所有报文按顺序存入新的数组
            System.arraycopy(bytes, 0, datas, len, bytes.length);
            len += bytes.length;
        }

        this.edition = datas[0];//获取版本号
        this.pangResult = datas[1] == 1;//获取pang响应结果

    }

    public void ResultToDataIsServer() {
        int len = 0;//获取总数据的长度
        for (byte[] bytes : results) {//统计报文list里的所有报文的长度
            len += bytes.length;
        }
        byte[] datas = new byte[len];//定义一个一整个数据的数组
        len = 0;
        for (byte[] bytes : results) {//将报文list里的所有报文按顺序存入新的数组
            System.arraycopy(bytes, 0, datas, len, bytes.length);
            len += bytes.length;
        }

        this.protocolversion=datas[0];//获取协议版本号
        this.code=datas[1];//获取编码格式
        this.timeout=datas[2];//获取心跳时间
    }

    public void ResultToDataIsResult() {
        int len = 0;//获取总数据的长度
        for (byte[] bytes : results) {//统计报文list里的所有报文的长度
            len += bytes.length;
        }
        byte[] datas = new byte[len];//定义一个一整个数据的数组
        len = 0;
        for (byte[] bytes : results) {//将报文list里的所有报文按顺序存入新的数组
            System.arraycopy(bytes, 0, datas, len, bytes.length);
            len += bytes.length;
        }


        this.edition=datas[0];//获取版本号
        byte[] datass=new byte[len-1];//获取数据段

        System.arraycopy(datas,1,datass,0,len-1);
        String data=new String(datass);
        this.datas.spliteData(data,this.agreement);
    }

    public byte getResult() {
        return result;
    }

    public Data getDatas() {
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
