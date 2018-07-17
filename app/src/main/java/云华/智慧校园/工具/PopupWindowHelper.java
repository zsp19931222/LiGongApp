package 云华.智慧校园.工具;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import yh.app.appstart.lg.R;

public class PopupWindowHelper
{
	private PopupWindow mPopupWindow;
	private View contentView;

	public PopupWindowHelper setPopupWindow(Context context, View contentView, int width, int height)
	{
		this.contentView = contentView;
		mPopupWindow = new PopupWindow(contentView, width, height);
		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setFocusable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
		mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		mPopupWindow.setAnimationStyle(R.style.PopupAnimation);
		return this;
	}

	public View getContentView()
	{
		return contentView;
	}

	public PopupWindowHelper dismiss()
	{
		mPopupWindow.dismiss();
		return this;
	}

	public PopupWindowHelper showAsDropDown(View anchor, int xoff, int yoff)
	{
		mPopupWindow.showAsDropDown(anchor, xoff, yoff);
		return this;
	}

	public PopupWindowHelper showAtLocation(View parent, int gravity, int x, int y)
	{
		mPopupWindow.showAtLocation(parent, gravity, x, y);
		return this;
	}
}
