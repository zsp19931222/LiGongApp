package yh.app.quanzi;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import org.androidpn.push.Constants;
import yh.app.quanzitool.InitMrfz;
import yh.app.quanzitool.mrfzAT;
import yh.app.tool.SqliteHelper;

public class initMRFZ
{
	private LinearLayout layout;
	private Context mContext;

	public initMRFZ(LinearLayout layout, Context mContext)
	{
		this.layout = layout;
		this.mContext = mContext;
	}

	public void doit()
	{
		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor c = db.rawQuery("select count(*) from mrfz where userid=?", new String[]
		{
				Constants.number
		});
		c.moveToFirst();
		if (c.getInt(0) == 0)
		{
			mrfzAT at = new mrfzAT(handler);
			at.execute();
		} else
		{
			Message msg = new Message();
			msg.what = 1;
			handler.handleMessage(msg);
		}
		db.close();
	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			switch (msg.what)
			{
			case 1:
				new InitMrfz(layout, mContext).doInit(null);
				break;
			default:
				break;
			}
		};
	};
}
