package yh.app.wisdomclass;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.yhkj.cqgyxy.R;
public class ZHKTState
{

	private Activity mActivity = null;
	private int Stu = 0;
	private int state = 0;
	private LinearLayout parent = null;

	public ZHKTState(Activity mActivity, int Stu, LinearLayout parent)
	{
		this.Stu = Stu;
		this.mActivity = mActivity;
		this.parent = parent;
	}

	public  void showPopupWindow(int stu)
	{
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(mActivity).inflate(R.layout.zhkt_pop, null);
		
		final PopupWindow popupWindow = new PopupWindow(contentView, LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, true);

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener()
		{

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return false;
			}
		});

		// 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
		// 我觉得这里是API的一个bug
		popupWindow.setBackgroundDrawable(null);

		// 设置好参数之后再show
		popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);

	}
}
