package com.example.app4.util;

import android.util.Log;

import com.example.app4.tool.UserMessageTool;

import java.net.URLEncoder;

import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/6/7 0007.
 */

public class MD5Util {
    public static String getTicket(String function_key){
        String ticket;
        Log.d("zsp", "getTicket: "+UserMessageTool.getPassWordUnencrypted()+"--"+_链接地址导航.addString+"--"+ UserMessageTool.getUserId()+"--"+function_key);
        try {
            ticket=MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() +  UserMessageTool.getPassWordUnencrypted() + function_key);
        }catch (Exception ignored){
            ticket="";
        }
        return  ticket;
    }
}
