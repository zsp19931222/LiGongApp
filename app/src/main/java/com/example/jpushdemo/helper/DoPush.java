package com.example.jpushdemo.helper;

import android.content.Context;
import android.content.DialogInterface;
import org.androidpn.push.Constants;
import yh.app.tool.Ticket;
import 云华.智慧校园.功能.LoginOut;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel.OnButtonClickLisener;

public class DoPush
{
	public void chat(String code)
	{

	}

	public void push(String code)
	{

	}

	public void force(String ticket, final Context context, String title, String message)
	{
		if (!ticket.equals(Ticket.getLoginTicket(Constants.number, Constants.code)))
		{
			new DiaLogOkAndCancel().buldeDialog(context, title, message, "确定", null, new OnButtonClickLisener()
			{
				@Override
				public void setButton2ClickLisener(DialogInterface dialog, int which)
				{
				}

				@Override
				public void setButton1ClickLisener(DialogInterface dialog, int which)
				{
					// Intent intent = new Intent();
					// intent.setAction("yh.app.uiengine.Login");
					// intent.setPackage(context.getPackageName());
					// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					// ((Activity) context).startActivity(intent);
					// ((Activity) context).finish();
					new LoginOut().doLoginOut(context);
				}
			});

		}
	}

}
