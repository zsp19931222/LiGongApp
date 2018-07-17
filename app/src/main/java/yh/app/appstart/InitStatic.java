package yh.app.appstart.lg;
//package  com.yhkj.cqswzyxy;
//
//import java.util.Properties;
//import org.androidpn.push.Constants;
//import android.content.Context;
// 
//
//public class InitStatic
//{
//	/**
//	 * 初始化全局变量
//	 * 
//	 * @param context
//	 */
//	public static void initStatic(Context context)
//	{
//		Properties props = loadProperties(context);
//
//		Constants.fqdmurl = props.getProperty("fqdmurl", "");
//		Constants.hylburl = props.getProperty("hylb", "");
//		Constants.xsdmurl = props.getProperty("xsdmurl", "");
//		Constants.addString = props.getProperty("addString", "");
//		Constants.newindexsurl = props.getProperty("newindexsurl", "");
//		Constants.newurl = props.getProperty("newurl", "");
//		Constants.newurllist = props.getProperty("newurllist", "");
//		Constants.scoreurl = props.getProperty("scoreurl", "");
//		Constants.curriculumurl = props.getProperty("curriculumurl", "");
//		Constants.loginurl = props.getProperty("loginurl", "");
//		Constants.tongxunluurl = props.getProperty("tongxunluurl", "");
//		Constants.lostFoundAddurl = props.getProperty("lostFoundAddurl", "");
//		Constants.lostFoundListurl = props.getProperty("lostFoundListurl", "");
//		Constants.libraryurl = props.getProperty("libraryurl", "");
//		Constants.termurl = props.getProperty("termurl", "");
//		Constants.nowterm = props.getProperty("nowterm", "");
//		Constants.webviewurl = props.getProperty("webviewurl", "");
//		Constants.kbxxurl = props.getProperty("kbcxurl", "");
//		Constants.ktxslb = props.getProperty("ktxslburl", "");
//		Constants.ydmxslb = props.getProperty("ydmxslburl", "");
//		Constants.tjdmurl = props.getProperty("tjdmurl", "");
//		Constants.hylburl = props.getProperty("hylb", "");
//		Constants.hyxxurl = props.getProperty("hyxx", "");
//		Constants.sshyurl = props.getProperty("sshy", "");
//		Constants.mrfzurl = props.getProperty("mrfz", "");
//		Constants.mapurl = props.getProperty("mapurl", "");
//		Constants.ltKey = props.getProperty("ltkey", "");
//		Constants.xgurl = props.getProperty("xgurl", "");
//		Constants.dmbjurl = props.getProperty("dmbjurl", "");
//		Constants.qrtjhyurl = props.getProperty("qrtjhyurl", "");
//		Constants.functionurl = props.getProperty("functionurl", "");
//		Constants.qrtjhyxxflkurl = props.getProperty("qrtjhyxxflkurl", "");
//		Constants.jsxztxsmd = props.getProperty("jsxztxsmd", "");
//		Constants.jsztnr = props.getProperty("jsztnr", "");
//		Constants.xsztnr = props.getProperty("xsztnr", "");
//		Constants.xspj = props.getProperty("xspj", "");
//		Constants.getxspj = props.getProperty("getxspj", "");
//		Constants.getjspj = props.getProperty("getjspj", "");
//		Constants.ktxx = props.getProperty("saveKtxx", "");
//		Constants.fszt = props.getProperty("fszt", "");
//		Constants.syxsdmjg = props.getProperty("syxsdmjg", "");
//		Constants.push_receive_url = props.getProperty("pushreceive", "");
//		Constants.push_offine_url = props.getProperty("pushoffine", "");
//		Constants.dmcsurl = props.getProperty("dmcsurl", "");
//		Constants.lturl = props.getProperty("lturl", "");
//		Constants.saveZtnr = props.getProperty("saveZtnr", "");
//		Constants.settingurl = props.getProperty("setting", "");
//		Constants.passwordurl = props.getProperty("password", "");
//		// Cursor c = new SqliteHelper().getRead().rawQuery("select usertype
//		// from usertype where userid=?", new String[]
//		// {
//		// Constants.number
//		// });
//		// if (c.moveToFirst())
//		// Constants.usertype = c.getInt(0);
//	}
//
//	private static Properties loadProperties(Context context)
//	{
//		Properties props = new Properties();
//		try
//		{
//			int id = context.getResources().getIdentifier("androidpn", "raw", context.getPackageName());
//			props.load(context.getResources().openRawResource(id));
//		} catch (Exception e)
//		{
//			Log.e("initStatic", e.getMessage());
//		}
//		return props;
//	}
//}
