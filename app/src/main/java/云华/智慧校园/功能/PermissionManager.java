package 云华.智慧校园.功能;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

/**
 * Created by Administrator on 2017/9/21.
 */

public class PermissionManager {

    public boolean checkPermission(Context context, String permissionString) {
        if (Build.VERSION.SDK_INT >= 23)
            return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, permissionString);
        else {
            return true;
        }
    }

    /**
     * 判断是否有网络权限
     *
     * @param context
     * @return
     */
    public boolean checkPermissionOfINTERNET(Context context) {
        return checkPermission(context, Manifest.permission.INTERNET);
    }

    /**
     * 判断是否有存储权限
     *
     * @param context
     * @return
     */
    public boolean checkPermissionOfWRITE_EXTERNAL_STORAGE(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 授予权限接口回调
     */
    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }

    /**
     * 提示用户为什么要开启该权限
     *
     * @param activity
     * @param requestCode
     * @param requestPermission
     */
    private static void shouldShowRationale(final Activity activity, final int requestCode, final String requestPermission) {
        showMessageOKCancel(activity, "该功能运行需要一些权限，如禁止会导致功能不能正常使用", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{requestPermission},
                        requestCode);
            }
        });
    }

    /**
     * 提示框
     *
     * @param context
     * @param message
     * @param okListener
     */
    private static void showMessageOKCancel(final Activity context, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * 打开权限设置界面
     *
     * @param activity
     * @param message
     */
    private static void openSettingActivity(final Activity activity, String message) {
        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                activity.startActivity(intent);
            }
        });
    }

}