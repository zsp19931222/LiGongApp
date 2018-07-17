package yh.app.tool;

import android.os.Build;

/**
 * 设备获取
 * @author anmin
 *
 */
public class Devicename_Tool {
	//获得手机
   public static String getDevicenameBrand(){
	   return Build.BRAND;
   }
   //获得手机型号
   public static String getDevicenameModel(){
	   return Build.MODEL;
   }
}
