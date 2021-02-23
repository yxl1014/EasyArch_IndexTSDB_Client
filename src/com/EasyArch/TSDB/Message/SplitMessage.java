package com.EasyArch.TSDB.Message;

public class SplitMessage {
    public static byte[] resultMessage(byte[] message) {

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
        int nofixedsize = message.length - 4;//计算不定长数据长度

        byte result = message[fixedsize];//获取执行结果

        byte[] data = new byte[nofixedsize - 1];//定义数据
        System.arraycopy(message, fixedsize + 1, data, 0, nofixedsize - 1);//获取数据


        byte[] result1 = new byte[nofixedsize];
        result1[0] = result;
        System.arraycopy(data, 0, result1, 1, data.length);
        return result1;
    }

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

        byte protocolversion = message[fixedsize];//获取协议版本
        byte code = message[fixedsize + 1];//获取编码格式
        byte timeout = message[fixedsize + 2];//获取心跳间隔时间


        byte[] result1 = new byte[4];//定义返回byte数组

        result1[0] = edition;//报文类型
        result1[1] = protocolversion;//协议版本
        result1[2] = code;//编码格式
        result1[3] = timeout;//心跳间隔时间
        return result1;
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


        byte protocolversion = message[fixedsize];//获取协议版本
        byte response = message[fixedsize + 1];//获取响应结果

        byte[] result1 = new byte[3];//定义返回byte数组

        result1[0] = edition;//报文类型
        result1[1] = protocolversion;//协议版本
        result1[2] = response;//响应结果
        return result1;
    }

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
