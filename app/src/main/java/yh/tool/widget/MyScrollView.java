package yh.tool.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * 自定义ScrollView解决onScrollChanged()方法不能调用的问题
 * 
 * @author LENOVO
 * 
 */
public class MyScrollView extends ScrollView
{
	private ScrollViewListener scrollViewListener = null;
	public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	public MyScrollView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyScrollView(Context context)
	{
		super(context);
	}

	

	public void setScrollViewListener(ScrollViewListener scrollViewListener)
	{
		this.scrollViewListener = scrollViewListener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy)
	{
		super.onScrollChanged(x, y, oldx, oldy);
		if (scrollViewListener != null)
		{
			scrollViewListener.onScrollChanged(x, y, oldx, oldy);
		}
	}

	public interface ScrollViewListener
	{
		void onScrollChanged(int x, int y, int oldx, int oldy);
	}
	
	
	
	
	
}