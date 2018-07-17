package yh.app.tool;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

/**
 * 版本管理
 */

public class PackAganmanger {
     private static Context context;

    public PackAganmanger(Context context) {
        this.context=context;
    }

    // 获得当前应用名称
    public static String getAppName() {
        String versionName = "";
        PackageManager packageManager=null;
        ApplicationInfo appInfo=null;
        try {
            packageManager = context.getPackageManager();
            appInfo = packageManager.getApplicationInfo(context.getPackageName(),0);
            versionName = (String) packageManager.getApplicationLabel(appInfo);
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    // 获得当前应用版本名称
    public static String getAppVersionName() {
        String versionName = null;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    // 获得当前应用版本号
    public static int getAppVersionCode() {
        int versionNamecode = -1;

        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionNamecode = packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionNamecode;
    }


    // 获得当前应用图标
    public static Drawable getAppIcon() {

        PackageManager packageManager = null;
        ApplicationInfo appInfo = null;
        try {
            packageManager = context.getPackageManager();
            appInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return appInfo.loadIcon(packageManager);

    }
}
