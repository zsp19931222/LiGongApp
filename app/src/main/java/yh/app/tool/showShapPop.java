package yh.app.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.yhkj.cqgyxy.R;
public class showShapPop extends Activity
{
	RelativeLayout layout;
	ProgressBar pgb;
	PopupWindow popupWindow;

	public void showPopupWindow(LinearLayout rel, Context mContext)
	{

		// 一个自定义的布局，作为显示的内容
		layout = new RelativeLayout(mContext);
		pgb = new ProgressBar(mContext);
		layout.addView(pgb, new RelativeLayout.LayoutParams(200, 200));
		layout.setGravity(Gravity.CENTER);
		layout.setLayoutParams(new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		layout.setBackgroundResource(R.drawable.bg);
		layout.getBackground().setAlpha(50);
		// 设置按钮的点击事件
		// PopupWindow popupWindow = new PopupWindow(layout);

		popupWindow = new PopupWindow(layout, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener()
		{

			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				layout.removeAllViews();
				popupWindow.dismiss();
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		ColorDrawable dw = new ColorDrawable(-00000);
		popupWindow.setBackgroundDrawable(dw);

		// 设置好参数之后再show
		// popupWindow.showAsDropDown(view);
		popupWindow.showAtLocation(rel, Gravity.CENTER, 0, 0);

	}
}
