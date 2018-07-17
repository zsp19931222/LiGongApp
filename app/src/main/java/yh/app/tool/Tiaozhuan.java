package yh.app.tool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class Tiaozhuan extends Activity
{
	public Tiaozhuan(Context context, String cls, String pkg, int type, String[][] Extra)
	{
		try
		{
			Intent intent = new Intent();
			intent.setAction(cls);
			intent.setPackage(pkg);
			try
			{
				for (int i = 0; i < Extra.length; i++)
					intent.putExtra(Extra[i][0], Extra[i][1]);
			} catch (Exception e)
			{
			}
			intent.setFlags(type);
			start(context, intent);
		} catch (Exception e)
		{
			Intent intent = new Intent();
			intent.setAction(cls);
			intent.setPackage(pkg);
			try
			{
				for (int i = 0; i < Extra.length; i++)
					intent.putExtra(Extra[i][0], Extra[i][1]);
			} catch (Exception e1)
			{
			}
			start(context, intent);
		}
	}
	private void start(Context context, Intent intent)
	{
		try
		{
			context.startActivity(intent);
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "程序异常,请重试", Toast.LENGTH_SHORT).show();
		}
	}
}
