package yh.tool.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MViewPage extends ViewPager {
	private boolean scrollble = true;

	public MViewPage(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MViewPage(Context context) {
		super(context);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		if (scrollble) {
			return super.onTouchEvent(arg0);
		} else {
			return false;
		}

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		if (scrollble) {
			return super.onInterceptTouchEvent(arg0);
		} else {
			return false;
		}

	}

	@Override
	public void scrollTo(int x, int y) {
		if (scrollble) {
			super.scrollTo(x, y);
		}

	}

	public boolean isScrollble() {
		return scrollble;
	}

	public void setScrollble(boolean scrollble) {
		this.scrollble = scrollble;
	}

}
