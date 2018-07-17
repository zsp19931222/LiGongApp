package yh.app.coursetable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import yh.app.appstart.lg.R;

@SuppressLint({
		"ClickableViewAccessibility", "InflateParams"
})
@SuppressWarnings("deprecation")
public class zskb
{
	private PopupWindow popupWindow;

	public void doit(Context context, LinearLayout layout, Intent intent)
	{
		View contentView = LayoutInflater.from(context).inflate(R.layout.kbzs, null);
		popupWindow = new PopupWindow(contentView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				popupWindow.dismiss();
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
		TextView course_name = (TextView) contentView.findViewById(R.id.course_name1);
		TextView course_teacher = (TextView) contentView.findViewById(R.id.course_teacher1);
		TextView course_palace = (TextView) contentView.findViewById(R.id.course_palace1);
		TextView course_time = (TextView) contentView.findViewById(R.id.course_time1);
		TextView course_week = (TextView) contentView.findViewById(R.id.course_week1);
		String name = intent.getExtras().getString("course_name");
		String palece = intent.getExtras().getString("course_palece");
		String teacher = intent.getExtras().getString("course_teacher");
		String time = intent.getExtras().getString("course_time");
		String week = intent.getExtras().getString("course_week");
		course_name.setText(name);
		course_palace.setText(palece);
		course_teacher.setText(teacher);
		course_time.setText(time);
		course_week.setText(week);
	}
}
