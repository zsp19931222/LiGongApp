package 云华.智慧校园.工具;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liangzili on 15/8/3.
 */
public class SystemUtils
{
    /**
     * 判断应用是否已经启动
     * 
     * @param context
     *            一个context
     * @param packageName
     *            要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName)
    {
	ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
	List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
	for (int i = 0; i < processInfos.size(); i++)
	{
	    if (processInfos.get(i).processName.equals(packageName))
	    {
		return true;
	    }
	}
	return false;
    }

    // public static String getAPPID()
    // {
    // String re = android.os.Process.myPid() + "";
    // return re;
    // }
    public static String getSignature(Context context, String pkgname)
    {
	PackageManager manager = context.getPackageManager();
	StringBuilder builder = new StringBuilder();
	if (!IsNull.isNotNull(pkgname))
	{
	    Toast.makeText(context, "应用程序的包名不能为空！", Toast.LENGTH_SHORT).show();
	} else
	{
	    try
	    {
		/** 通过包管理器获得指定包名包含签名的包信息 **/
		PackageInfo packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
		/******* 通过返回的包信息获得签名数组 *******/
		Signature[] signatures = packageInfo.signatures;
		/******* 循环遍历签名数组拼接应用签名 *******/
		for (Signature signature : signatures)
		{
		    builder.append(signature.toCharsString());
		}
		/************** 得到应用签名 **************/
		return builder.toString();
	    } catch (Exception e)
	    {
	    }
	}
	return null;
    }
}
