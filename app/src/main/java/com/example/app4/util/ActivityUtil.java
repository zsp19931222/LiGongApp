package com.example.app4.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.smartclass.base.BaseRecyclerViewActivity;

import java.util.List;

/**
 * Created by Administrator on 2018/4/8 0008.
 */

public class ActivityUtil {
    /**
     * 获得栈中最顶层的Activity
     *
     * @param context
     * @return
     */
    public static String getTopActivity(Context context) {
        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);

        if (runningTaskInfos != null) {
            return (runningTaskInfos.get(0).topActivity.getClassName());
        } else
            return null;
    }

    /**
     * 判断某个Activity 界面是否在前台
     *
     * @param context
     * @param className 某个界面名称
     * @return
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;

    }

    /**
     * 获取activity栈
     */
    public static List<ActivityManager.RunningTaskInfo> getActivityList(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        return list;
    }

    public static boolean isForeground(Context context) {
        return ActivityUtil.isForeground(context, context.getClass().getName());
    }
}
