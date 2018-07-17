package com.example.smartclass.eventbus;

import java.util.List;

/**
 * Created by Toommi Leahy on 2017/8/8 0008.
 */

public class MessageEvent {

    private String Tag;
    private Object message;
    private List<Object> msgArray;


    public MessageEvent(String Tag, Object message) {
        this.Tag = Tag;
        this.message = message;

    }

    public MessageEvent(String Tag, List<Object> msgArray) {
        this.Tag = Tag;
        this.msgArray = msgArray;

    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }


    public List<Object> getMsgArray() {
        return msgArray;
    }

    public void setMsgArray(List<Object> msgArray) {
        this.msgArray = msgArray;
    }
}
