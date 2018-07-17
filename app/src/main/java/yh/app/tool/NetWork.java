package yh.app.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * 包 名:yh.app.tool 
 * 类 名:NetWord.java 
 * 功 能:判断是否有网络
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class NetWork
{
	public static final int NET_TYPE_MOBILE = ConnectivityManager.TYPE_MOBILE;
	public static final int NET_TYPE_WIFI = ConnectivityManager.TYPE_WIFI;
	public static final int NET_UNABLE = 10086;

	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
		{
			return false;
		} else
		{
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable())
			{
				return true;
			}
			// 1、判断是否有3G网络
			if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)
			{
				return true;
			}
			// 2、判断是否有wifi连接
			if (networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI)
			{
				return true;
			}
		}
		return false;
	}

	public static int getNetWorkType(Context context)
	{

		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm == null)
		{
			return NET_UNABLE;
		} else
		{
			// 如果仅仅是用来判断网络连接
			// 则可以使用 cm.getActiveNetworkInfo().isAvailable();
			// wifi返回1 移动网络返回0
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isAvailable())
			{
				return networkInfo.getType();
			}
			return NET_UNABLE;
			// // 1、判断是否有3G网络
			// if (networkInfo != null && networkInfo.getType() ==
			// ConnectivityManager.TYPE_MOBILE)
			// {
			//
			// return networkInfo.getTypeName();
			// }
			// // 2、判断是否有wifi连接
			// if (networkInfo != null && networkInfo.getType() ==
			// ConnectivityManager.TYPE_WIFI)
			// {
			// return networkInfo.getTypeName();
			// }
			// }
			// return false;
			//
		}
	}

}
