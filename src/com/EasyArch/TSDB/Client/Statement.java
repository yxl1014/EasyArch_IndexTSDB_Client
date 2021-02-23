package com.EasyArch.TSDB.Client;

import com.EasyArch.TSDB.Message.Agreement_Head;
import com.EasyArch.TSDB.Message.ReceivingMessage;
import com.EasyArch.TSDB.Result.First;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

    public ResultSet query() {
        ResultSet set = new ResultSet();//定义返回数据的set
        try {
            InputStream read = socket.getInputStream();//获取输入流
            OutputStream write = socket.getOutputStream();//获取输出流

            //TODO:这里调用马哥的接口来拼发送报文
            byte[] writeData = ReceivingMessage.DDLMessage(Agreement_Head.DDL_CREATETABLE, (byte) 1, "yxl".getBytes());//获取需要发送的报文
            int write_num = writeData.length / 1024;//查看需要发送几个1024长度的报文
            byte[] data=new byte[1024];//定义一个发送报文的数组

            for(int i=0;i<write_num;i++){//循环发送报文
                System.arraycopy(writeData,i*1024,data,0,1024);//把一整个报文按1024字节赋值给data数组
                write.write(data);//发送报文
                write.flush();
            }

            int len;//定义长度
            byte[] message=new byte[1024];//定义返回定长报文头的载体
            while ((len=read.read(message))!=-1){//读取数据

                if(len!=1024){//如果读取的长度不是1024,说明没有发送完整，重新接收
                    continue;
                }

                byte[] data1=First.Squirtle(data);//分析报文，将报文存到data1中

                if(data1!=null){//data1不为null
                    set.getResult(data1);//将数据存到ResultSet里
                }

            }

            set.ResultToData();//将报文转换为数据

        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }


}
