package com.EasyArch.TSDB.Result;

import com.EasyArch.TSDB.Message.Agreement_Head;

public class Data {
    private String name;


    public void spliteData(String datass, byte agreement) {
        String[] strs = datass.split(String.valueOf(Agreement_Head.MID));//result数据的表名或用户名与数据之间的分隔符为|，所以拆分字符串

        this.name = strs[0];//获取用户名

        String data = "";

        for (int i = 1; i < strs.length; i++) {//若数据中有|也会被拆分，所以用循环将数据给拼成一个字符串
            data += strs[i];
            if (i + 1 != strs.length) {//如果不是被拆分的最后一个字符串，则需要加上|
                data += "|";
            }
        }
        //TODO 这里需要加上条件判断为什么语句的返回数据来获取具体数据
        getDataBySelect(data);
    }

    private void getDataBySelect(String data){

    }
}
