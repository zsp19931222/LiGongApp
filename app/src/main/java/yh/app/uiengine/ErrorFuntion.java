package yh.app.uiengine;

import com.example.jpushdemo.ExampleApplication;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
@SuppressWarnings("unused")
public class ErrorFuntion
{
	private Context context;

	public ErrorFuntion(Context context)
	{
		this.context = context;
	}


	private boolean hasErrorFunction()
	{
//		Constants.error_function.add("测试1");
//		return Constants.error_function.size() > 0;
		return false;
	}

	public void wealErrorFunction()
	{
		dialog();
	}

	private void dialog()
	{
		AlertDialog.Builder builder = new Builder(context);
		builder.setMessage("功能异常，点击确定尝试修复");
		builder.setTitle("功能异常");
		builder.setPositiveButton("确认", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				ExampleApplication.DeleteWJJALL();
			}
		});
		builder.setNegativeButton("取消", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
}