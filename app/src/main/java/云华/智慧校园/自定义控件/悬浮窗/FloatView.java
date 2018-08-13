package 云华.智慧校园.自定义控件.悬浮窗;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import yh.app.tool.DpPx;import com.yhkj.cqgyxy.R;

/**
 * 悬浮窗
 * 
 * @author Administrator
 */
public abstract class FloatView
{
	private Context context;

	private WindowManager mWindowManager;

	private View desktopView;

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
		mLayout.gravity = Gravity.CENTER_VERTICAL | Gravity.END;

		// 设置窗体宽度和高度
		mLayout.width = new DpPx(context).getDpToPx(48);
		mLayout.height = new DpPx(context).getDpToPx(48);

		desktopView = createDesktopView();
		
		setOnTouchListener();
	}

	private View createDesktopView()
	{
		return LayoutInflater.from(context).inflate(R.layout.float_view_small, null, false);
	}
	
	public void show()
	{
		mWindowManager.addView(desktopView, mLayout);
	}

	private void setOnTouchListener()
	{
		desktopView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return false;
			}
		});
	}

}