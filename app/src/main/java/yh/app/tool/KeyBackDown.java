package yh.app.tool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
public class KeyBackDown extends Activity
{
	/** @param cls
	 *            界面名称
	 * @param pkg
	 *            程序包名
	 * @param type
	 *            返回界面类型 0->空 1->clearTop */
	public void setKeyBackDown(String cls, String pkg, int type, Context mContext)
	{
		Intent intent = new Intent();
		intent.setAction(cls);
		intent.setPackage(pkg);
		if (type == Intent.FLAG_ACTIVITY_CLEAR_TOP)
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mContext.startActivity(intent);
	}
}
