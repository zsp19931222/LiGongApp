package yh.tool.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 重写ScrollGridview解决嵌套Listview问题
 * 
 * @author lft
 *
 */
public class ScrollGridview extends GridView
{

	public ScrollGridview(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 重写计算gridview的高度 解决于listivew嵌套只显示一行问题
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, expandSpec + 20);
	}

}
