package com.example.app4.api;


import com.alibaba.fastjson.JSONObject;


/**
 * Created by zsp on 2016/6/23.
 */
public class JsonGenericsSerializator implements IGenericsSerializator {
    @Override
    public <T> T transform(String response, Class<T> classOfT) {

        return JSONObject.parseObject(response, classOfT);
    }


}
