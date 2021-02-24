package com.EasyArch.TSDB.Message;

public class SplitMessage {


    public static byte[] resultMessage(byte[] message) {

        if (message == null) {//报文不为空
            return null;
        }

        byte agreement = message[0];//获取报文类型

        byte size = (byte) (message[1]^Agreement_Head.KEY);//数据长度

        byte[] testcrc16 = Crc16.getCRC(message);//计算校验码
        byte[] crc16 = new byte[2];//新建校验码
        System.arraycopy(message, 2, crc16, 0, 2);//赋值
        crc16[0]= (byte) (crc16[0]^Agreement_Head.KEY);
        crc16[1]= (byte) (crc16[1]^Agreement_Head.KEY);
        if (!new String(testcrc16).equals(new String(crc16))) {//对比校验码
            return null;//如果不等返回null
        }


        int fixedsize = 4;//计算定长数据长度
        //int nofixedsize = message.length - 4;//计算不定长数据长度

        /*byte result = message[fixedsize];//获取执行结果

        byte[] data = new byte[nofixedsize - 1];//定义数据
        System.arraycopy(message, fixedsize + 1, data, 0, nofixedsize - 1);//获取数据*/

        /*if(nofixedsize!=size){//判断数据是否完整
            return null;
        }*/

        byte[] result = new byte[size];
        /*result1[0] = result;*/
        System.arraycopy(message, fixedsize, result, 0, size);

        return result;
    }
/*
    public static byte[] ServerMessage(byte[] message) {

        if (message == null) {//报文不为空
            return null;
        }
        byte edition = message[0];//获取报文类型

        byte size = message[1];//数据长度

        byte[] testcrc16 = Crc16.getCRC(message);//计算校验码
        byte[] crc16 = new byte[2];//新建校验码
        System.arraycopy(message, 2, crc16, 0, 2);//赋值
        if (!new String(testcrc16).equals(new String(crc16))) {//对比校验码
            return null;//如果不等返回null
        }


        int fixedsize = 4;//计算定长数据长度

        *//*byte protocolversion = message[fixedsize];//获取协议版本
        byte code = message[fixedsize + 1];//获取编码格式
        byte timeout = message[fixedsize + 2];//获取心跳间隔时间*//*



        byte[] result = new byte[size];

        System.arraycopy(message, fixedsize, result, 0, size);

        return result;
    }

    public static byte[] PangMessage(byte[] message) {
        if (message == null) {//报文不为空
            return null;
        }

        byte edition = message[0];//获取报文类型
        byte size = message[1];//数据长度

        byte[] testcrc16 = Crc16.getCRC(message);//计算校验码
        byte[] crc16 = new byte[2];//新建校验码
        System.arraycopy(message, 2, crc16, 0, 2);//赋值
        if (!new String(testcrc16).equals(new String(crc16))) {//对比校验码
            return null;//如果不等返回null
        }


        int fixedsize = 4;//计算定长数据长度


        byte[] result = new byte[size];

        System.arraycopy(message, fixedsize, result, 0, size);

        return result;
    }*/

/*    private static int getSize(byte[] message) {
        int size = 0;
        int length = message.length;
        for (int i = 1; i < length; i++) {
            byte[] len = new byte[i];
            System.arraycopy(message, 1, len, 0, i);
            if (Integer.parseInt(new String(len), 2) == length) {
                size = i;
                break;
            }
        }
        return size;
    }*/
}
