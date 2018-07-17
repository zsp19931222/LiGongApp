package yh.app.score;
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
import android.widget.TextView;import yh.app.appstart.lg.R;
@SuppressLint({
		"InflateParams", "ClickableViewAccessibility"
})
public class cjzs
{
	private PopupWindow popupWindow;
	@SuppressWarnings("deprecation")
	public void doit(Context context, LinearLayout layout, Intent intent)
	{
		View contentView = LayoutInflater.from(context).inflate(R.layout.cjzs, null);
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
//		TextView course_id = (TextView) ((Activity) context).findViewById(R.id.course_id);
//		TextView course_name = (TextView) ((Activity) context).findViewById(R.id.course_name);
//		TextView course_xingzhi = (TextView) ((Activity) context).findViewById(R.id.course_xingzhi);
//		TextView course_xuefen = (TextView) ((Activity) context).findViewById(R.id.course_xuefen);
//		TextView course_grade = (TextView) ((Activity) context).findViewById(R.id.course_grade);
//		TextView c_GPA = (TextView) ((Activity) context).findViewById(R.id.c_GPA);
//		TextView c_rebuiltScore = (TextView) ((Activity) context).findViewById(R.id.c_rebuiltScore);
		
		TextView c_id = (TextView)contentView. findViewById(R.id.course_id);
		TextView c_name = (TextView) contentView.findViewById(R.id.course_name);
		TextView c_grade = (TextView)contentView. findViewById(R.id.course_grade);
		TextView c_xuefen = (TextView) contentView.findViewById(R.id.course_xuefen);
		TextView c_xingzhi = (TextView)contentView. findViewById(R.id.course_xingzhi);
		TextView c_GPA = (TextView) contentView.findViewById(R.id.c_GPA);
		TextView c_rebuiltScore = (TextView) contentView.findViewById(R.id.c_rebuiltScore);
		c_id.setText(intent.getExtras().getString("c_id"));
		c_name.setText(intent.getExtras().getString("c_name"));
		c_grade.setText(intent.getExtras().getString("c_grade"));
		c_xuefen.setText(intent.getExtras().getString("c_xuefen"));
		c_xingzhi.setText(intent.getExtras().getString("c_xingzhi"));
		if (intent.getExtras().getString("c_GPA").equals(""))
		{
			c_GPA.setText("无");
		}
		else
		{
			c_GPA.setText(intent.getExtras().getString("c_GPA"));
		}
		c_rebuiltScore.setText(intent.getExtras().getString("c_scoreType"));
	}
}
