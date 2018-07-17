package com.example.app3.utils;


import com.example.app3.presenter.VersionUpdateImpl;

public class VersionUpdate {

    /**
     * 请求服务器，检查版本是否可以更新
     *
     * @param versionUpdate
     */
     public static void checkVersion(final VersionUpdateImpl versionUpdate) {
         //从网络请求获取到的APK下载路径
         versionUpdate.bindService("");
     }
}
