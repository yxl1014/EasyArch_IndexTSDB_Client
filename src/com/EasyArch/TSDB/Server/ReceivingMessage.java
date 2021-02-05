package com.EasyArch.TSDB.Server;

public class ReceivingMessage {

    public byte[] DDLMessage(byte agreement, byte edition, byte[] name) {//一报文类型，二版本号，三新建数据名

        int nofixedsize = name.length + 1;//计算不定长报文段的长度
        byte[] nofixed = new byte[nofixedsize];//新建不定长报文段

        nofixed[0] = edition;//将版本号赋值给0位

        System.arraycopy(name, 0, nofixed, 1, name.length);//将创建的用户名或者表名库名赋值给后几位

        String Messagelen = Integer.toBinaryString(nofixed.length + 3);//将长数据长度转换为二进制字符串
        int l = this.toLen(Messagelen);//计算没有算长度为的总长度
        String M = Integer.toBinaryString(nofixed.length + 3 + l);//将长数据长度转换为二进制字符串
        int size = this.toLen(M);//计算 算长度为的总长度


        byte[] fixed = new byte[3 + size];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 1 + size, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] lens = M.getBytes();//获取数据长度
        System.arraycopy(lens, 0, fixed, 1, size);//将数据长度赋值给定长部分

        byte[] b = new byte[fixed.length + nofixed.length];//定义返回的报文byte数组
        System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段

        return b;
    }

    public byte[] DMLMessage(byte agreement, String data, byte edition, byte[] tablename) {//一报文类型，二数据，三版本号，四操作表名

        byte[] payload = data.getBytes();//将数据转成byte数组
        int nofixedsize = payload.length + tablename.length + 1;//计算不定长报文段的长度
        byte[] nofixed = new byte[nofixedsize];//定义不定长报文段

        nofixed[0] = edition;//将版本号赋值给第0位
        System.arraycopy(tablename, 0, nofixed, 1, tablename.length);//将操作的表的名字赋值给后几位
        int len = payload.length;//计算数据长度
        System.arraycopy(payload, 0, nofixed, nofixedsize - len, len);//将数据赋值给不定长段


        String Messagelen = Integer.toBinaryString(nofixed.length + 3);//将长数据长度转换为二进制字符串
        int l = this.toLen(Messagelen);//计算没有算长度为的总长度
        String M = Integer.toBinaryString(nofixed.length + 3 + l);//将长数据长度转换为二进制字符串
        int size = this.toLen(M);//计算 算长度为的总长度


        byte[] fixed = new byte[3 + size];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 1 + size, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] lens = M.getBytes();//获取数据长度
        System.arraycopy(lens, 0, fixed, 1, size);//将数据长度赋值给定长部分

        byte[] b = new byte[fixed.length + nofixed.length];//定义返回的报文byte数组
        System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段

        return b;
    }

    public byte[] PingMessage(byte agreement, byte edition, byte result) {//一报文类型，二版本号，三响应结果
        byte[] nofixed = new byte[]{edition, result};//将版本号赋值给0位，将返回结果赋值给1位


        String Messagelen = Integer.toBinaryString(nofixed.length + 3);//将长数据长度转换为二进制字符串
        int l = this.toLen(Messagelen);//计算没有算长度为的总长度
        String M = Integer.toBinaryString(nofixed.length + 3 + l);//将长数据长度转换为二进制字符串
        int size = this.toLen(M);//计算 算长度为的总长度


        byte[] fixed = new byte[3 + size];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 1 + size, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] lens = M.getBytes();//获取数据长度
        System.arraycopy(lens, 0, fixed, 1, size);//将数据长度赋值给定长部分

        byte[] b = new byte[fixed.length + nofixed.length];//定义返回的报文byte数组
        System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段

        return b;
    }

    public byte[] ClientMessage(byte agreement, byte edition, byte[] usernamepassword, byte code) {//一报文类型，二版本号，三用户名密码，四编码格式

        byte[] nofixed = new byte[usernamepassword.length + 2];//定义不定长报文段
        nofixed[0] = edition;//将版本号赋值给第0位
        nofixed[1] = code;//将编码格式赋值给第1位
        int len = usernamepassword.length;//计算用户名密码长度
        System.arraycopy(usernamepassword, 0, nofixed, 2, len);//将用户名密码赋值给不定长段


        String Messagelen = Integer.toBinaryString(nofixed.length + 3);//将长数据长度转换为二进制字符串
        int l = this.toLen(Messagelen);//计算没有算长度为的总长度
        String M = Integer.toBinaryString(nofixed.length + 3 + l);//将长数据长度转换为二进制字符串
        int size = this.toLen(M);//计算 算长度为的总长度


        byte[] fixed = new byte[3 + size];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 1 + size, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] lens = M.getBytes();//获取数据长度
        System.arraycopy(lens, 0, fixed, 1, size);//将数据长度赋值给定长部分

        byte[] b = new byte[fixed.length + nofixed.length];//定义返回的报文byte数组
        System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段

        return b;
    }

    private int toLen(String Messagelen) {
        int len = Messagelen.length();
        int size;
        if (len % 8 == 0) {
            size = len / 8;
        } else {
            size = len / 8 + 1;
        }

        return size;
    }
}
