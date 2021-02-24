package com.EasyArch.TSDB.Message;


public class ReceivingMessage {

    public static byte[] DDLMessage(byte agreement, byte edition, byte[] name) {//一报文类型，二版本号，三新建数据名

        int endSize = Agreement_Head.END_SIZE;//结束符长度
        int nofixedsize = name.length + 1 + endSize;//计算不定长报文段的长度
        byte[] nofixed = new byte[nofixedsize];//新建不定长报文段

        nofixed[0] = edition;//将版本号赋值给0位

        System.arraycopy(name, 0, nofixed, 1, name.length);//将创建的用户名或者表名库名赋值给后几位

        System.arraycopy(Agreement_Head.END, 0, nofixed, name.length + 1, endSize);//将结束符插入数据尾

        /*String Messagelen = Integer.toBinaryString(nofixedsize);//将长数据长度转换为二进制字符串
        int size = toLen(Messagelen);//计算数据总长度

        int xxx;
        if (size > 4) {//如果字节数大与4,则为long型
            bytes = longToBytes(nofixedsize);
            xxx = 8;
        } else {//否则为int型

            xxx = 4;
        }*/

        int messageSize = 1020;//不定报文的操作长度

        int message_num = outSize(nofixedsize, messageSize);//需要几条1024字节的报文

        byte[] fixed = new byte[4];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 2, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] b = new byte[message_num * 1024];//定义返回的报文byte数组

        int i = 0;//用来循环处理多条定长报文

        for (int j = 0; j < message_num; j++) {//需要几条报文，则循环几次

            int Size = messageSize;//处理数据长度

            if (j + 1 == message_num) {//如果是最后一条报文
                Size = nofixedsize - (messageSize * j);//则需要处理的长度就是剩余的报文长度，而不是1024
            }

            byte[] lens = new byte[1];//获取数据长度

            byte[] bytes;//将长度转换为byte的暂存数组
            bytes = intToBytes(Size);

            System.arraycopy(bytes, 3, lens, 0, 1);//将暂存的长度存储到lens里

            System.arraycopy(lens, 0, fixed, 1, 1);//将数据长度赋值给定长部分


            System.arraycopy(fixed, 0, b, j * 1024, fixed.length);//将定长头赋值给前半段  ||  将fixed数组从弟0为复制到b数组从j*1024开始复制fixed.length位
            System.arraycopy(nofixed, i, b, fixed.length + j * 1024, Size);//将不定长头赋值给后半段

            i += messageSize;//插入数据的地址
        }


        //System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        //System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段

        return b;
    }

    public static byte[] DMLMessage(byte agreement, String data, byte edition, byte[] tablename) {//一报文类型，二数据，三版本号，四操作表名

        /*int endSize = Agreement_Head.END_SIZE;//结束符长度
        int nofixedsize = name.length + 1 + endSize;//计算不定长报文段的长度
        byte[] nofixed = new byte[nofixedsize];//新建不定长报文段*/

        int endSize = Agreement_Head.END_SIZE;//结束符长度
        byte[] payload = data.getBytes();//将数据转成byte数组
        int len = payload.length;//计算数据长度
        int nofixedsize = len + tablename.length + 1 + endSize + 1;//计算不定长报文段的长度,1为表名和数据之间的分隔符
        byte[] nofixed = new byte[nofixedsize];//定义不定长报文段


        nofixed[0] = edition;//将版本号赋值给第0位
        System.arraycopy(tablename, 0, nofixed, 1, tablename.length);//将操作的表的名字赋值给后几位

        nofixed[tablename.length] = Agreement_Head.MID;

        System.arraycopy(payload, 0, nofixed, tablename.length + 2, len);//将数据赋值给不定长段

        System.arraycopy(Agreement_Head.END, 0, nofixed, tablename.length + len + 2, endSize);//将结束符插入数据尾



        /*String Messagelen = Integer.toBinaryString(nofixed.length);//将长数据长度转换为二进制字符串
        int size = toLen(Messagelen);//计算数据总长度

        byte[] bytes;//将长度转换为byte的暂存数组

        int xxx;
        if (size > 4) {//如果字节数大与4,则为long型
            bytes = longToBytes(nofixedsize);
            xxx = 8;
        } else {//否则为int型
            bytes = intToBytes(nofixedsize);
            xxx = 4;
        }*/

        int messageSize = 1020;//不定报文的操作长度

        int message_num = outSize(nofixedsize, messageSize);//需要几条1024字节的报文

        byte[] fixed = new byte[4];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 2, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] b = new byte[message_num * 1024];//定义返回的报文byte数组

        int i = 0;//用来循环处理多条定长报文

        for (int j = 0; j < message_num; j++) {//需要几条报文，则循环几次

            int Size = messageSize;//处理数据长度

            if (j + 1 == message_num) {//如果是最后一条报文
                Size = nofixedsize - (messageSize * j);//则需要处理的长度就是剩余的报文长度，而不是1024
            }

            byte[] lens = new byte[1];//获取数据长度

            byte[] bytes;//将长度转换为byte的暂存数组
            bytes = intToBytes(Size);

            System.arraycopy(bytes, 3, lens, 0, 1);//将暂存的长度存储到lens里

            System.arraycopy(lens, 0, fixed, 1, 1);//将数据长度赋值给定长部分

            byte[] fixed1=new byte[4];
            for(int p=0;p<4;p++){
                fixed1[p]= (byte) (fixed[p]^Agreement_Head.KEY);
            }


            System.arraycopy(fixed1, 0, b, j * 1024, fixed.length);//将定长头赋值给前半段  ||  将fixed数组从弟0为复制到b数组从j*1024开始复制fixed.length位

            System.arraycopy(nofixed, i, b, fixed.length + i, Size);//将不定长头赋值给后半段

            i += messageSize;//插入数据的地址
        }

        return b;
    }

    public static byte[] ClientMessage(byte agreement, byte edition, byte[] usernamepassword, byte code) {//一报文类型，二版本号，三用户名密码，四编码格式

        int endSize = Agreement_Head.END_SIZE;//结束符长度
        int len = usernamepassword.length;//计算用户名密码长度
        byte[] nofixed = new byte[len + 2 + endSize];//定义不定长报文段
        nofixed[0] = edition;//将版本号赋值给第0位
        nofixed[1] = code;//将编码格式赋值给第1位
        System.arraycopy(usernamepassword, 0, nofixed, 2, len);//将用户名密码赋值给不定长段
        System.arraycopy(Agreement_Head.END, 0, nofixed, len + 2, endSize);//将结束符插入数据尾
        int nofixedsize = nofixed.length;


        int messageSize = 1020;//不定报文的操作长度

        int message_num = outSize(nofixedsize, messageSize);//需要几条1024字节的报文

        byte[] fixed = new byte[4];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 2, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] b = new byte[message_num * 1024];//定义返回的报文byte数组

        int i = 0;//用来循环处理多条定长报文

        for (int j = 0; j < message_num; j++) {//需要几条报文，则循环几次

            int Size = messageSize;//处理数据长度

            if (j + 1 == message_num) {//如果是最后一条报文
                Size = nofixedsize - (messageSize * j);//则需要处理的长度就是剩余的报文长度，而不是1024
            }

            byte[] lens = new byte[1];//获取数据长度

            byte[] bytes;//将长度转换为byte的暂存数组
            bytes = intToBytes(Size);

            System.arraycopy(bytes, 3, lens, 0, 1);//将暂存的长度存储到lens里

            System.arraycopy(lens, 0, fixed, 1, 1);//将数据长度赋值给定长部分

            byte[] fixed1=new byte[4];
            for(int p=0;p<4;p++){
                fixed1[p]= (byte) (fixed[p]^Agreement_Head.KEY);
            }

            System.arraycopy(fixed1, 0, b, j * 1024, fixed.length);//将定长头赋值给前半段  ||  将fixed数组从弟0为复制到b数组从j*1024开始复制fixed.length位

            System.arraycopy(nofixed, i, b, fixed.length + i, Size);//将不定长头赋值给后半段

            i += messageSize;//插入数据的地址
        }
        /*String Messagelen = Integer.toBinaryString(nofixed.length);//将长数据长度转换为二进制字符串
        int size = toLen(Messagelen);//计算数据总长度

        byte[] bytes;//将长度转换为byte的暂存数组

        int xxx;
        if (size > 4) {//如果字节数大与4,则为long型
            bytes = longToBytes(nofixed.length);
            xxx = 8;
        } else {//否则为int型
            bytes = intToBytes(nofixed.length);
            xxx = 4;
        }

        int message_num = outSize(nofixed.length, size);//需要几条1024字节的报文

        int messageSize = 1024 - 3 - size;//不定报文的操作长度


        byte[] fixed = new byte[3 + size];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 1 + size, 2);//将crc16校验码赋值给定长部分的后两位

        byte[] lens = new byte[size];//获取数据长度

        System.arraycopy(bytes, xxx - size, lens, 0, size);//将暂存的长度存储到lens里

        System.arraycopy(lens, 0, fixed, 1, size);//将数据长度赋值给定长部分

        byte[] b = new byte[message_num * 1024];//定义返回的报文byte数组

        int i = 0;//用来循环处理多条定长报文

        for (int j = 0; j < message_num; j++) {//需要几条报文，则循环几次
            int Size = messageSize;//处理数据长度

            if (j + 1 == message_num) {//如果是最后一条报文
                Size = nofixed.length - (messageSize * j);//则需要处理的长度就是剩余的报文长度，而不是1024
            }

            System.arraycopy(fixed, 0, b, j * 1024, fixed.length);//将定长头赋值给前半段  ||  将fixed数组从弟0为复制到b数组从j*1024开始复制fixed.length位
            System.arraycopy(nofixed, i, b, fixed.length + i, Size);//将不定长头赋值给后半段

            i += messageSize;//插入数据的地址
        }*/

        /*byte[] b = new byte[fixed.length + nofixed.length];//定义返回的报文byte数组
        System.arraycopy(fixed, 0, b, 0, fixed.length);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, fixed.length, nofixed.length);//将不定长头赋值给后半段*/

        return b;
    }

    public static byte[] PingMessage(byte agreement, byte edition, byte result) {//一报文类型，二版本号，三响应结果
        byte[] nofixed = new byte[]{edition, result};//将版本号赋值给0位，将返回结果赋值给1位


        /*String Messagelen = Integer.toBinaryString(nofixed.length + 3);//将长数据长度转换为二进制字符串
        int l = toLen(Messagelen);//计算没有算长度为的总长度*/


        byte[] fixed = new byte[4];//定义定长报文头

        fixed[0] = agreement;//将报文类型赋值给第0位

        byte[] crc16 = Crc16.getCRC(nofixed);//计算crc16校验码
        System.arraycopy(crc16, 0, fixed, 2, 2);//将crc16校验码赋值给定长部分的后两位

        byte lens = 0x01;//获取数据长度
        fixed[1] = lens;//将数据长度赋值给定长部分

        byte[] fixed1=new byte[4];
        for(int p=0;p<4;p++){
            fixed1[p]= (byte) (fixed[p]^Agreement_Head.KEY);
        }

        byte[] b = new byte[1024];//定义返回的报文byte数组
        System.arraycopy(fixed1, 0, b, 0, 4);//将定长头赋值给前半段
        System.arraycopy(nofixed, 0, b, 4, 2);//将不定长头赋值给后半段

        return b;
    }

    /*private static int toLen(String Messagelen) {//传过来一个二进制值字符串
        int len = Messagelen.length();//判断他的长度
        int size;//大小
        if (len % 8 == 0) {//如果能被8整除
            size = len / 8;//则他数据长度所占的字节就是len/8
        } else {
            size = len / 8 + 1;//否则他数据长度所占的字节就是len/8+1
        }

        return size;
    }*/

    private static int outSize(int size, int lensize) {//数据长度   固定报文头数据大小长度

        int message_num = 1;//需要几个报文

        while ((size = size - lensize) > 0) {//判断需要多少条报文
            message_num++;
        }
        return message_num;
    }

    private static byte[] intToBytes(int i) {
        byte[] result = new byte[4];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3] = (byte) (i & 0xFF);
        return result;
    }

    private static byte[] longToBytes(long values) {
        byte[] buffer = new byte[8];
        for (int i = 0; i < 8; i++) {
            int offset = 64 - (i + 1) * 8;
            buffer[i] = (byte) ((values >> offset) & 0xff);
        }
        return buffer;
    }
}
