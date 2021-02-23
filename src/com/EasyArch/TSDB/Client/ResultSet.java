package com.EasyArch.TSDB.Client;
import java.util.ArrayList;
import java.util.List;

public class ResultSet {
    private List<byte[]> results=new ArrayList<>();//报文的集合

    private List<String> datas=new ArrayList<>();//处理完返回的数据的集合

    public ResultSet() {
    }

    public boolean getResult(byte[] result){//获取报文，拼报文
        if(result==null)//报文不为空
            return false;

        results.add(result);//加到链表里
        return true;
    }

    public void ResultToData(){//将报文集合里的数据处理到数据集合里

    }
}
