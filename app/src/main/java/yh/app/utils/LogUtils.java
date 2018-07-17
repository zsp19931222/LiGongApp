package yh.app.utils;

import yh.app.logTool.Log;

public class LogUtils {

	public static boolean isLog=false;
	public static void LogShow(String tag,String e){
		if (isLog) {
			Log.e(tag, e);
		}
		
	}
	
	
	
}
