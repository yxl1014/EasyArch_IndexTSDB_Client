package com.EasyArch.TSDB.Message;

public class Agreement_Head {

    //报文类型
    public static byte TYPE_DDL = 0x00;//DDL
    public static byte TYPE_DML = 0x40;//DML
    public static byte TYPE_SERVER = (byte) 0x80;//server/client
    public static byte TYPE_RESULT = (byte) 0xc0;//result

    //DDL
    public static byte DDL_ADDUSER;//添加用户
    public static byte DDL_DELETEUSER;//删除用户
    public static byte DDL_UPDATEPOWER;//修该用户权限
    public static byte DDL_CREATEDATABASE;//创建数据库
    public static byte DDL_DELETEDATABASE;//删除数据库
    public static byte DDL_CREATETABLE;//新建表
    public static byte DDL_DELETETABLE;//删除表
    public static byte DDL_SHOWTABLE;//查询表信息
    public static byte DDL_SHOWDATABASE;//查询数据库信息
    public static byte DDL_SWITCHDATABASE;//切换数据库
    public static byte DDL_CLEARCACHE;//清除缓存

    //DML
    public static byte DML_BATCHDATA;//批处理数据
    public static byte DML_SELECTSQL;//查询数据库
    public static byte DML_INSERTSQL;//插入语句
    public static byte DML_UPDATESQL;//修改语句
    public static byte DML_DELETESQL;//删除语句

    //server/client
    public static byte SERVER_REQUESTCONNECTION;//请求连接
    public static byte SERVER_REQUESTANSWER;//请求应答
    public static byte SERVER_CONNECTIONSUCCESS;//连接成功，发送数据
    public static byte SERVER_PINGREQUEST;//ping请求
    public static byte SERVER_PINGRESPONSE;//ping响应
    public static byte SERVER_CONNECTIONOK;//数据传送完毕完毕，断开连接
    public static byte SERVER_RECONNECTION;//重新连接，发送数据
    public static byte SERVER_CONNECTIONERROR;//连接失败

    //result
    public static byte RESULT_DDLOK;//DDL成功响应
    public static byte RESULT_DMLOK;//DML成功响应
    public static byte RESULT_DDLERROR;//DDL响应失败
    public static byte RESULT_DMLERROR;//DML响应失败
    public static byte RESULT_DDLBACKDATAOK;//DDL成功响应并返回信息
    public static byte RESULT_DMLBACKDATAOK;//DML成功响应并返回信息

    static {


        DDL_ADDUSER = (byte) (TYPE_DDL | 0);//0000 0000
        DDL_DELETEUSER = (byte) (TYPE_DDL | 0x01);//0000 0001
        DDL_UPDATEPOWER = (byte) (TYPE_DDL | 0x02);//0000 0010
        DDL_CREATEDATABASE = (byte) (TYPE_DDL | 0x03);//0000 0011
        DDL_DELETEDATABASE = (byte) (TYPE_DDL | 0x04);//0000 0100
        DDL_CREATETABLE = (byte) (TYPE_DDL | 0x05);//0000 0101
        DDL_DELETETABLE = (byte) (TYPE_DDL | 0x06);//0000 0110
        DDL_SHOWTABLE = (byte) (TYPE_DDL | 0x07);//0000 0111
        DDL_SHOWDATABASE = (byte) (TYPE_DDL | 0x08);//0000 1000
        DDL_SWITCHDATABASE = (byte) (TYPE_DDL | 0x09);//0000 1001
        DDL_CLEARCACHE = (byte) (TYPE_DDL | 0x0b);//0000 1011


        DML_BATCHDATA = (byte) (TYPE_DML | 0x00);//0100 0000
        DML_SELECTSQL = (byte) (TYPE_DML | 0x01);//0100 0001
        DML_INSERTSQL = (byte) (TYPE_DML | 0x02);//0100 0010
        DML_UPDATESQL = (byte) (TYPE_DML | 0x03);//0100 0011
        DML_DELETESQL = (byte) (TYPE_DML | 0x04);//0100 0100


        SERVER_REQUESTCONNECTION = (byte) (TYPE_SERVER | 0x00);//1000 0000
        SERVER_REQUESTANSWER = (byte) (TYPE_SERVER | 0x01);//1000 0001
        SERVER_CONNECTIONSUCCESS = (byte) (TYPE_SERVER | 0x03);//1000 0011
        SERVER_PINGREQUEST = (byte) (TYPE_SERVER | 0x04);//1000 0100
        SERVER_PINGRESPONSE = (byte) (TYPE_SERVER | 0x05);//1000 0101
        SERVER_CONNECTIONOK = (byte) (TYPE_SERVER | 0x07);//1000 0111
        SERVER_RECONNECTION = (byte) (TYPE_SERVER | 0x08);//1000 1000
        SERVER_CONNECTIONERROR = (byte) (TYPE_SERVER | 0x09);//1000 1001


        RESULT_DDLOK = (byte) (TYPE_RESULT | 0x00);//1100 0000
        RESULT_DMLOK = (byte) (TYPE_RESULT | 0x01);//1100 0001
        RESULT_DDLERROR = (byte) (TYPE_RESULT | 0x03);//1100 0011
        RESULT_DMLERROR = (byte) (TYPE_RESULT | 0x04);//1100 0100
        RESULT_DDLBACKDATAOK = (byte) (TYPE_RESULT | 0x05);//1100 0101
        RESULT_DMLBACKDATAOK = (byte) (TYPE_RESULT | 0x06);//1100 0110
    }
}
