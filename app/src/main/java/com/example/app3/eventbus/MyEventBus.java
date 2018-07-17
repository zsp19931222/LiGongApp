package com.example.app3.eventbus;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class MyEventBus {
    private Object mMsg;
    public MyEventBus(Object msg) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
    }
    public Object getMsg(){
        return mMsg;
    }
}
