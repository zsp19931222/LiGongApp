package 云华.智慧校园.自定义控件;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import yh.app.appstart.lg.R;
public class ZHKTTeacherChoose
{
	private StringBuffer teacher_name;
	PopupWindow popupWindow;

	public void start(Context context, View view, List<Map<String, String>> list, StringBuffer teacher_name)
	{
		this.teacher_name = teacher_name;
		ViewGroup parent = ((LinearLayout) ((Activity) context).findViewById(R.id.zhkt_home));
		View child = LayoutInflater.from(context).inflate(R.layout.zhkt_choose_teacher, parent);
		popupWindow = new PopupWindow(child, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return false;
			}
		});
		setPopupwindow(context, child, parent, list);
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.touming));
		popupWindow.showAtLocation(view, Gravity.TOP | Gravity.LEFT, 0, 0);
		// try
		// {
		// popupWindow.showAsDropDown(clickView, -new DpPx(context).getDpToPx(40
		// - 24), 0);
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
	}

	private void setPopupwindow(Context context, View child, ViewGroup parent, List<Map<String, String>> list)
	{
		LinearLayout layout = (LinearLayout) child.findViewById(R.id.layout);
		for (int i = 0; i < list.size(); i++)
		{
			for (int j = 0; j < 5; j++)
			{
				View v = LayoutInflater.from(context).inflate(R.layout.zhkt_choose_list_item, layout, false);
				((TextView) v.findViewById(R.id.jsmc)).setText(list.get(i).get("JSXM") + j);
				((TextView) v.findViewById(R.id.jsmc)).setTag(list.get(i).get("JSZGH"));
				((TextView) v.findViewById(R.id.jsmc)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						teacher_name.append(((TextView) v).getText().toString()).append(";sss");
						popupWindow.dismiss();
					}
				});
				layout.addView(v);
			}
		}
	}
}
