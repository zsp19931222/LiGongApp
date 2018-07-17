package com.example.app3.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

    public class LogUtils {

        public static boolean APP_DBG = false; // 是否是debug模式

        public static void init(Context context){
            APP_DBG = isApkDebugable(context);
        }

        /**
         * @param context
         * @return true-debug
         */
        public static boolean isApkDebugable(Context context) {
            try {
                ApplicationInfo info= context.getApplicationInfo();
                return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
            } catch (Exception e) {

            }
            return false;
        }

    }
