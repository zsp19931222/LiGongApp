package yh.app.logTool;

public class Log
{
    public static boolean isPrintLog = false;
    public static String DEFAULT_TAG = "yunhuakeji";

    public static void e(String tag, String msg)
    {
	if (isPrintLog)
	    android.util.Log.e(tag, msg);
    }

    public static void w(String tag, String msg)
    {
	if (isPrintLog)
	    android.util.Log.w(tag, msg);
    }

    public static void i(String tag, String msg)
    {
	if (isPrintLog)
	    android.util.Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
	if (isPrintLog)
	    android.util.Log.d(tag, msg);
    }

    public static void v(String tag, String msg)
    {
	if (isPrintLog)
	    Log.v(tag, msg);
    }
    
    
    public static void e(String msg)
    {
	if (isPrintLog)
	    android.util.Log.e(DEFAULT_TAG, msg);
    }

    public static void w(String msg)
    {
	if (isPrintLog)
	    android.util.Log.w(DEFAULT_TAG, msg);
    }

    public static void i(String msg)
    {
	if (isPrintLog)
	    android.util.Log.i(DEFAULT_TAG, msg);
    }

    public static void d(String msg)
    {
	if (isPrintLog)
	    android.util.Log.d(DEFAULT_TAG, msg);
    }

    public static void v(String msg)
    {
	if (isPrintLog)
	    Log.v(DEFAULT_TAG, msg);
    }
}
