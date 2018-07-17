package com.yunhuakeji.app.utils;

import java.util.Timer;
import java.util.TimerTask;

import com.yunhuakeji.app.R;

import android.accounts.NetworkErrorException;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.view.WindowManager;

/**
 * 悬浮窗
 *
 * @author Administrator
 */
public class FloatView implements OnTouchListener
{
	private Context context;

	private WindowManager mWindowManager;

	private static View desktopView;

	private android.view.WindowManager.LayoutParams mLayout;

	public FloatView(Context context)
	{
		this.context = context;

		// 取得系统窗体
		mWindowManager = (WindowManager) context.getApplicationContext().getSystemService("window");

		// 窗体的布局样式
		mLayout = new WindowManager.LayoutParams();

		// 设置窗体显示类型——TYPE_SYSTEM_ALERT(系统提示)
		mLayout.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

		// 设置窗体焦点及触摸：
		// FLAG_NOT_FOCUSABLE(不能获得按键输入焦点)
		mLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

		// 设置显示的模式
		mLayout.format = PixelFormat.RGBA_8888;

		// 设置对齐的方法
		mLayout.gravity = Gravity.TOP | Gravity.START;

		// 设置窗体宽度和高度
		mLayout.width  = android.view.WindowManager.LayoutParams.WRAP_CONTENT;
		mLayout.height = android.view.WindowManager.LayoutParams.WRAP_CONTENT;

		mLayout.x = new DpPx(context).getDpToPx(80);
		mLayout.x = new DpPx(context).getDpToPx(80);
		if (desktopView == null)
		{
			desktopView = createDesktopView();
		}

		handler.sendEmptyMessage(1);

		setOnTouchListener();
	}

	private View createDesktopView()
	{
		return LayoutInflater.from(context).inflate(R.layout.float_view_small, null, true);
	}

	public void show()
	{
		mWindowManager.addView(desktopView, mLayout);
	}

	private void setOnTouchListener()
	{
		desktopView.setOnTouchListener(this);
	}

	public void removeView()
	{
		mWindowManager.removeView(desktopView);
	}

	private Timer timer;

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		v.performClick();

		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				timer = new Timer();
				handler.sendEmptyMessage(1);
				break;
			case MotionEvent.ACTION_MOVE:

				break;
			case MotionEvent.ACTION_UP:
				timer.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						// TODO Auto-generated method stub
						handler.sendEmptyMessage(0);
					}
				}, 5 * 1000);
				break;

			default:
				break;
		}

		mLayout.x = (int) event.getRawX();
		mLayout.y = (int) event.getRawY();

		mWindowManager.updateViewLayout(desktopView, mLayout);

		return true;
	}

	private static Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg)
		{
			if (msg.what == 1)
			{
				desktopView.setAlpha(1);
//				desktopView.getBackground().setAlpha(255);
			} else if (msg.what == 0)
			{
				desktopView.setAlpha(0.1f);
//				desktopView.getBackground().setAlpha(50);
			}

		};
	};
}