package com.example.app3.tool;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 防止过快点击
 * 
 * */

public class Tool {
	private static long lastClickTime=0;
	static String imei;
	String imsi;
	static String mtype;
	String numer;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		if (time - lastClickTime <800) {
			return true;
		}
		lastClickTime = time;
		return false;
	}
	/**
	 * 动态设置宽度（Gridview）
	 * */
	public static void setListViewWidthBasedOnChildren(GridView gridView) {
	    if(gridView == null) return;
	    ListAdapter listAdapter = gridView.getAdapter();
	    if (listAdapter == null) {
	        // pre-condition
	        return;
	    }
	    int totalWidth = 0;
	    for (int i = 0; i < listAdapter.getCount(); i++) {
	        View listItem = listAdapter.getView(i, null, gridView);
	        listItem.measure(0, 0);
	        totalWidth += listItem.getMeasuredWidth();
	    }
	    ViewGroup.LayoutParams params = gridView.getLayoutParams();
	    params.width = (int) ((totalWidth + (gridView.getWidth()* (listAdapter.getCount() - 1)))/2.5);
	    gridView.setLayoutParams(params);
	}
	
	/**
	 * 去掉省、市、区、县、盟、旗等字段
	 * */
	public static String deleteString(String string) {
		String[] province_strings = string.split("");
		StringBuffer province_sb = new StringBuffer(256);
		for (int i = 0; i < province_strings.length; i++) {
			if (province_strings[i].equals("省")
					|| province_strings[i].equals("市")
					|| province_strings[i].equals("区")
					|| province_strings[i].equals("县")
					|| province_strings[i].equals("盟")
					|| province_strings[i].equals("旗")) {
				province_strings[i] = "";
			}
			province_sb.append(province_strings[i]);
		}
		return province_sb.toString();
	}
	
	/** 
	 * 获取手机ip地址 
	 *  
	 * @return 
	 */  
	public static String getPhoneIp() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (Exception e) {
	    }  
	    return "";  
	}  
	/** 
	 * 获取手机ip型号
	 *  
	 * @return 
	 */  
	public static String getInfo() {
		return android.os.Build.MODEL;
	}
}