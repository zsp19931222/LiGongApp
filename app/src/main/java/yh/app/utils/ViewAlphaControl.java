package yh.app.utils;

import yh.tool.widget.MyScrollView;
import yh.tool.widget.MyScrollView.ScrollViewListener;
import android.view.View;
import android.view.ViewGroup;

public class ViewAlphaControl
{
	public int currentHeight;

	public void toUpdate(MyScrollView sc, final int max, final ViewGroup view)
	{
		sc.setScrollViewListener(new ScrollViewListener()
		{
			@Override
			public void onScrollChanged(int x, int y, int oldx, int oldy)
			{
				currentHeight = oldy;
				if (oldy >= max)
				{
					view.getBackground().setAlpha(255);
				} else if (oldy < 0)
				{
					view.getBackground().setAlpha(0);
				} else
				{
					view.getBackground().setAlpha(255 * oldy / max);
				}
			}
		});
	}

	public void toUpdate(MyScrollView sc, final int max, final View view)
	{
		sc.setScrollViewListener(new ScrollViewListener()
		{
			@Override
			public void onScrollChanged(int x, int y, int oldx, int oldy)
			{
				currentHeight = oldy;
				if (oldy >= max)
				{
					view.getBackground().setAlpha(255);
				} else if (oldy < 0)
				{
					view.getBackground().setAlpha(0);
				} else
				{
					view.getBackground().setAlpha(255 * oldy / max);
				}
			}
		});
	}

	public void setAlpha(ViewGroup view, int old, int max)
	{
		if (old >= max)
		{
			view.getBackground().setAlpha(255);
		} else if (old < 0)
		{
			view.getBackground().setAlpha(0);
		} else
		{
			view.getBackground().setAlpha(255 * old / max);
		}
	}

	public void setAlpha(View view, int old, int max)
	{
		if (old >= max)
		{
			view.getBackground().setAlpha(255);
		} else if (old < 0)
		{
			view.getBackground().setAlpha(0);
		} else
		{
			view.getBackground().setAlpha(255 * old / max);
		}
	}
}
