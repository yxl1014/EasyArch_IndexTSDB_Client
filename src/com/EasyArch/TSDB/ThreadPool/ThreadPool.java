package com.EasyArch.TSDB.ThreadPool;

import java.util.HashMap;
import java.util.Map;
/*
* bug很多，勿用，还没完工的阑尾代码
* */
public class ThreadPool {
    private int poolSize=20;
    private Map<String,Thread> threadpool;//使用中的线程池
    private Map<String,Boolean> using;//线程是否在使用
    private Map<String,Thread> otherthread;//等死线程池
    private int name=0;//primary key
    private int maxsize=poolSize+30;//最大允许存活线程

    public ThreadPool(){
        init();
    }

    public ThreadPool(int poolSize){
        this.poolSize=poolSize;
        this.maxsize=poolSize+30;
        init();
    }

    public void setPoolSize(int poolSize){
        this.poolSize=poolSize;
        this.maxsize=poolSize+30;
        init();
        KillMoreThread();
    }

    private void KillMoreThread(){//若超过最大允许存活线程数,则无条件杀死
        int more=this.poolSize+this.otherthread.size()-this.maxsize;
        if(more>0){
            int i=0;
            for (String thread:this.otherthread.keySet()){
                if(i>=more) break;
                this.otherthread.get(thread).stop();
                this.otherthread.remove(thread);
                i++;
            }
        }
    }

    private void init(){
        synchronized (this.using){
            Map<String,Thread> newmap=new HashMap<>();
            Map<String,Boolean> newusing=new HashMap<>();
            if(this.threadpool!=null){// 如果线程池已存在,则需要将原
                int oldsize=this.threadpool.size();
                if(poolSize<oldsize){//若新设置的线程池大小小于原线程池大小
                    for(String s:this.using.keySet()){//查看每个线程是否被引用
                        if(this.using.get(s)){//若被使用了
                            if(newusing.size()<poolSize){//若新的线程池没有满
                                newmap.put(s,this.threadpool.get(s));//将线程挪到新的线程里
                                newusing.put(s,true);
                            }else {//若已经满了
                                if(otherthread==null) otherthread=new HashMap<>();//若等死线程第一次用则new
                                otherthread.put(s,this.threadpool.get(s));//放入等死线程池里
                            }
                        }
                    }
                    if(newmap.size()<poolSize){//如果新的线程池没有慢
                        int more=poolSize-newmap.size();//距离目标还差多少
                        for(int i=0;i<more;i++){//剩余的补齐
                            String threadname="Thread"+name;
                            this.name++;
                            newmap.put(threadname,null);
                            newusing.put(threadname,false);
                        }
                    }
                }else{//若大小大于原始线程池大小
                    for(String s:this.using.keySet()){//查看每个线程是否被引用
                        if(this.using.get(s)){//若这个线程在使用,则将这个线程挪到新的线程池中
                            newmap.put(s,this.threadpool.get(s));//将线程挪到新的线程里
                            newusing.put(s,true);
                        }
                    }
                    int more=poolSize-oldsize;//距离目标还差多少
                    for(int i=0;i<more;i++){//剩余的补齐
                        String threadname="Thread"+name;
                        this.name++;
                        newmap.put(threadname,null);
                        newusing.put(threadname,false);
                    }
                }
            }else {//若线程池为空,直接新建线程池
                for(int i=0;i<this.poolSize;i++){
                    String threadname="Thread"+name;
                    this.name++;
                    newmap.put(threadname,null);
                    newusing.put(threadname,false);
                }
            }
            this.threadpool=newmap;
            this.using=newusing;
        }
    }

    public Thread getThread(Runnable runnable){
        Thread thread = null;
        synchronized (this.using){
            fff:
            for(;;){
                for (String name:this.using.keySet()){
                    if(!this.using.get(name)){//若没有被使用
                        this.using.put(name,true);//则将using设置成使用中
                        thread=new Thread(runnable,name);
                        this.threadpool.put(name,thread);//将线程池中放入
                        break fff;//退出外层循环
                    }
                }
            }
        }
        return thread;
    }

    public void backThread(Thread thread){
        //TODO:这里还没有写完,没有判断线程是否死掉,然后归还线程
        String name=thread.getName();
        synchronized (this.using){
            if(this.using.containsKey(name)){//这个线程using里有没有,有说明在使用中线程池中,若没有说明在等死线程池中
                this.using.put(name,false);//将using设置成没有被使用
                this.threadpool.put(name,null);//将线程归还
            }else{
                this.otherthread.remove(name);//直接将该线程杀死
            }
        }
    }
}
