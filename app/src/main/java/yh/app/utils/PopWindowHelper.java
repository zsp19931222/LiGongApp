package yh.app.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.mymessage.Message;
import yh.app.tool.DpPx;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.MessageDataBaseFresh;

public class PopWindowHelper
{
	private Context context;
	private ViewGroup viewGroup;
	private View parents;
	private String id;

	public PopWindowHelper(Context context, ViewGroup viewGroup, View parents, String id)
	{
		this.context = context;
		this.viewGroup = viewGroup;
		this.id = id;
		this.parents = parents;
	}

	private PopupWindow popupWindow;

	public void show(final String func_id)
	{
		// 一个自定义的布局，作为显示的内容
		final View mView = LayoutInflater.from(context).inflate(R.layout.tzgg_delete, viewGroup, false);
		popupWindow = new PopupWindow(mView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		// popupWindow = new PopupWindow(mView);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_UP:
					v.performClick();
					break;
				default:
					break;
				}
				return false;
			}
		});
		mView.findViewById(R.id.delete).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new SqliteHelper().execSQL("delete from client_notice where n_id=?", id);
				new SqliteHelper().execSQL("delete from client_notice_newest where n_id=?", id);
				new SqliteHelper().execSQL("insert into client_notice_newest select * from client_notice where n_send_time = (select max(n_send_time) from client_notice where code = (select code from client_notice where n_id = ? and userid=?))", id, Constants.number);
				MessageDataBaseFresh.freshPush(func_id);
				viewGroup.removeView(parents);
				popupWindow.dismiss();
				float f = ((Message) context).sc_height;
				((Message) context).sc.setY(f);
			}
		});
		int[] location = new int[2];
		parents.getLocationInWindow(location);
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touming));
		popupWindow.showAtLocation(parents, Gravity.NO_GRAVITY, (new DpPx(context).getWH()[0] - new DpPx(context).getDpToPx(60)) / 2, location[1] - new DpPx(context).getDpToPx(10));
	}
}
