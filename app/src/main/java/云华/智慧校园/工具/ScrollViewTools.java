package 云华.智慧校园.工具;

import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.ListView;
import android.widget.ScrollView;

public class ScrollViewTools
{
	public static void inputScrollViewDown(final ScrollView sc, final View inner)
	{
		try
		{
			sc.setVerticalScrollBarEnabled(false);
			sc.addOnLayoutChangeListener(new OnLayoutChangeListener()
			{
				@Override
				public void onLayoutChange(View scroll, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
				{
					if (inner == null || scroll == null)
					{
						return;
					}
					int offset = inner.getMeasuredHeight() - scroll.getHeight();
					if (offset < 0)
					{
						offset = 0;
					}
					scroll.scrollTo(0, offset);
				}
			});
		} catch (Exception e)
		{
		}
	}

	public static void inputListViewDown(final ListView lv)
	{
		try
		{
			lv.setVerticalScrollBarEnabled(false);
			lv.addOnLayoutChangeListener(new OnLayoutChangeListener()
			{
				@Override
				public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
				{
					lv.setSelection(lv.getCount() - 1);
				}
			});
		} catch (Exception e)
		{
		}
	}
}