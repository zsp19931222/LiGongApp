package 云华.智慧校园.自定义控件;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;

public class DiaLogOkAndCancel
{
	public void buldeDialog(Context context, String title, String message, String btn_string1, String btn_string2, final OnButtonClickLisener lisener)
	{
		android.app.AlertDialog.Builder builder = new Builder(context).setMessage(message).setTitle(title);
		if (btn_string1 != null)
			builder.setPositiveButton(btn_string1, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					lisener.setButton1ClickLisener(dialog, which);
				}
			});
		if (btn_string2 != null)
			builder.setNegativeButton(btn_string2, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					lisener.setButton2ClickLisener(dialog, which);
				}
			});
		builder.setCancelable(false).setOnKeyListener(new DialogInterface.OnKeyListener()
		{
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event)
			{
				if (keyCode == KeyEvent.KEYCODE_SEARCH)
				{
					return true;
				} else
				{
					return false;
				}
			}
		});
		builder.show();
	}

	public interface OnButtonClickLisener
	{
		void setButton1ClickLisener(DialogInterface dialog, int which);

		void setButton2ClickLisener(DialogInterface dialog, int which);
	}
}
