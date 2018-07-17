package 云华.智慧校园.自定义控件;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyDiaLog
{
	ProgressDialog mProgressDialog;

	public void show(Context context)
	{
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
			}
		});
		mProgressDialog.show();
	}

	public void cancel()
	{
		mProgressDialog.dismiss();
	}
}