package 云华.智慧校园.工具;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import yh.app.tool.ToastShow;

public class AllAPPListHelper
{
	public static final int TYPE_SYSTEM_APP = 0;
	public static final int TYPE_USER_APP = 1;
	private Context context;

	public AllAPPListHelper(Context context)
	{
		this.context = context;
	}

	public List<PackageInfo> getAllAPPList()
	{
		ArrayList<AppInfo> appList = new ArrayList<AppInfo>(); // 用来存储获取的应用信息数据
		PackageManager pkm = context.getPackageManager();
		List<PackageInfo> packages = pkm.getInstalledPackages(0);
		for (int i = 0; i < packages.size(); i++)
		{
			PackageInfo packageInfo = packages.get(i);
			AppInfo tmpInfo = new AppInfo();
			tmpInfo.appName = packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
			tmpInfo.packageName = packageInfo.packageName;
			tmpInfo.versionName = packageInfo.versionName;
			tmpInfo.versionCode = packageInfo.versionCode;
			tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
			appList.add(tmpInfo);
			if (getAppName(packageInfo.packageName).contains("微博"))
				Log.e("智慧校园", "name=" + getAppName(packageInfo.packageName) + "    packageName = " + packageInfo.packageName + "    versionName = " + packageInfo.versionName + "    versionCode = " + packageInfo.versionCode);
		}
		new ToastShow().show(context, "完成");

		return packages;
	}

	public String getAppName(String pkg)
	{
		try
		{
			return (String) context.getPackageManager().getApplicationLabel(context.getPackageManager().getApplicationInfo(pkg, 0));
		} catch (Exception e)
		{
			return null;
		}
	}

	public List<PackageInfo> getAppsByType(int type)
	{
		List<PackageInfo> Apps = new ArrayList<PackageInfo>();
		List<PackageInfo> packages = getAllAPPList();
		for (int i = 0; i < packages.size(); i++)

			if (TYPE_USER_APP == type && (packages.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
			{
				Apps.add(packages.get(i));
			} else if (TYPE_SYSTEM_APP == type && !((packages.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0))
			{
				Apps.add(packages.get(i));
			}
		return Apps;
	}
}
