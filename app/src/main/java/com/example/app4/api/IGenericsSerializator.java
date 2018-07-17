package com.example.app4.api;

/**
 * Created by zsp on 2016/6/23.
 */
public interface IGenericsSerializator {
    <T> T transform(String response, Class<T> classOfT);
}
