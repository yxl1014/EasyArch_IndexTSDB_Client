package com.EasyArch.TSDB.Server;

import com.EasyArch.TSDB.ThreadPool.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class UserServer {
    private int port=18686;//服务端口号,默认18686
    private ThreadPool threadPool=new ThreadPool();

    public UserServer(){}

    public UserServer(int port){
        this.port=port;
    }

    public boolean start(){
        boolean isexist = false;
        try {
            ServerSocket serversocket=new ServerSocket(port);
            while(true){
                Socket socket=serversocket.accept();
                doSomething(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
            isexist= false;
        }finally {
            return isexist;
        }
    }

    private void doSomething(Socket socket){
        //TODO:需要做什么在run方法中写
        Thread t=this.threadPool.getThread(new Runnable() {
            @Override
            public void run() {

            }
        });
        t.start();
        this.threadPool.backThread(t);
    }
}
