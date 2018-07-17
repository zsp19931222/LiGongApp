package yh.app.tool;

import java.util.Properties;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * 
 * 包 名:yh.app.tool 类 名:GetConfig.java 功 能:读取配置文件(res/raw/Properties)
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class GetConfig
{
	private Context mContext = null;

	public GetConfig(Context context)
	{
		this.mContext = context;
	}

	public Properties loadProperties()
	{
		Properties props = new Properties();
		try
		{
			int id = this.mContext.getResources().getIdentifier("androidpn", "raw", this.mContext.getPackageName());
			props.load(this.mContext.getResources().openRawResource(id));
		} catch (Exception e)
		{

			e.printStackTrace();
		}
		return props;
	}

	public String getVersion()
	{
		try
		{
			PackageManager manager = this.mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.mContext.getPackageName(), 0);
			String version = info.versionCode + "";
			return version;
		} catch (Exception e)
		{
			Log.e("123", "getVersion: ", e);
			e.printStackTrace();
			return "";
		}
	}
}
