package com.EasyArch.TSDB.Client;

import com.EasyArch.TSDB.Message.Agreement_Head;
import com.EasyArch.TSDB.Message.ReceivingMessage;
import com.EasyArch.TSDB.Message.SplitMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Connection {

    private String userid;//用户id
    private String username;//账号
    private String password;//密码


    private byte protocolversion;//协议版本
    //private byte Cacheversion;//缓存版本
    private byte code;//编码格式
    private byte timeout;//心跳间隔时间

    private Statement statement;//操作类


    Socket socket;//http连接

    public Connection(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void init(int port) {
        try {
            socket = new Socket("127.0.0.1", port);//连接服务器


            InputStream in = socket.getInputStream();//获取输入流
            int len = 0;//计算读取数据的长度
            StringBuffer buffer = new StringBuffer();//新建一个报文承载类
            byte[] bb = new byte[1024];//读入数据数组
            while ((len = in.read(bb)) != -1) {//读到没有为止
                buffer.append(new String(bb));
            }


            byte[] result = SplitMessage.ServerMessage(buffer.toString().getBytes());//拆解初始化报文，并且获取返回值

            if (result[0] == Agreement_Head.SERVER_REQUESTANSWER) {//判断报文是不是请求连接报文
                this.protocolversion = result[1];//获取协议版本
                this.code = result[2];//获取编码格式
                this.timeout = result[3];//获取心跳间隔时间
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            OutputStream out = socket.getOutputStream();
            byte[] message = ReceivingMessage.ClientMessage(Agreement_Head.SERVER_REQUESTANSWER, protocolversion, (username + password).getBytes(), code);
            out.write(message);
            out.flush();


            InputStream in = socket.getInputStream();//获取输入流
            int len = 0;//计算读取数据的长度
            StringBuffer buffer = new StringBuffer();//新建一个报文承载类
            byte[] bb = new byte[1024];//读入数据数组
            while ((len = in.read(bb)) != -1) {//读到没有为止
                buffer.append(new String(bb));
            }


            byte[] result = SplitMessage.resultMessage(buffer.toString().getBytes());//拆解初始化报文，并且获取返回值
            //TODO:这里需要拆取userid,需要讨论
            this.userid = String.valueOf(result[1]);


            this.statement = new Statement(userid, protocolversion, code, timeout, socket);//新建操作类


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Statement getStatement() {
        return this.statement;
    }
}
