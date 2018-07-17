package 云华.智慧校园.工具;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import yh.app.tool.DpPx;

public class ViewTools
{
	public static View getView(Context context, int id,ViewGroup parent)
	{
		return LayoutInflater.from(context).inflate(id, parent, false);
	}
	
	/***
	 * 作用：测量 View的宽和高.
	 * 
	 * @param child
	 */

	public int[] getViewWidthAndHeigh(Context context, View view)
	{
		measureView(context, view);
		return new int[]
		{
				view.getMeasuredWidth(), view.getMeasuredHeight()
		};
	}

	private static void measureView(Context context, View child)
	{
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null)
		{
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, new DpPx(context).getDpToPx(99));
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0)
		{
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else
		{
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
}
