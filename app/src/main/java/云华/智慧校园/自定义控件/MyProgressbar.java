package 云华.智慧校园.自定义控件;

import android.content.Context;
import yh.app.progressdialog.CustomProgressDialog;

public class MyProgressbar
{
	private CustomProgressDialog c;

	private Context context;
	private String title, message;

	public MyProgressbar(Context context, String title, String message)
	{
		this.title = title;
		this.message = message;
		this.context = context;
	}

	private MyProgressbar build()
	{
		try
		{
			c = CustomProgressDialog.createDialog(context);
			c.setTitile(title);
			c.setMessage(message);
			c.onWindowFocusChanged(true);
			c.setCancelable(false);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return this;
	}

	public MyProgressbar show()
	{
		try
		{
			build();
			c.show();
		} catch (Exception e)
		{
		}
		return this;
	}

	public MyProgressbar cancel()
	{
		try
		{
			c.cancel();
		} catch (Exception e)
		{
		}
		return this;
	}
}
