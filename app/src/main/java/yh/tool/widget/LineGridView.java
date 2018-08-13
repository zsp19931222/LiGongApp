package yh.tool.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

import com.yhkj.cqgyxy.R;

/**
 * 分割线gridView
 */

public class LineGridView extends GridView {
	private Context context;

	public LineGridView(Context context) {
		super(context);
		this.context = context;
	}

	public LineGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public LineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	/**
	 * 重写计算gridview的高度 解决于listivew嵌套只显示一行问题
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec + 20);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		int childCount = 0;
		int column = 0;
		try {
			View localView1 = getChildAt(0);
			column = getWidth() / localView1.getWidth();
			childCount = getChildCount();
			
			Paint localPaint;
			localPaint = new Paint();
			localPaint.setStyle(Paint.Style.STROKE);
			localPaint.setColor(getResources().getColor(R.color.color_persona_bggray));
			localPaint.setStrokeWidth(20);
			for (int i = 0; i < childCount; i++) {

				View cellView = getChildAt(i);
				if ((i + 1) % column == 0) {
					canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
							localPaint);
				} else if ((i + 1) > (childCount - (childCount % column))) {
					// 绘制右边纵向线
					// canvas.drawLine(cellView.getRight(), cellView.getTop(),
					// cellView.getRight(), cellView.getBottom(), localPaint);
				} else {
					// canvas.drawLine(cellView.getRight(), cellView.getTop(),
					// cellView.getRight(), cellView.getBottom(), localPaint);
					canvas.drawLine(cellView.getLeft(), cellView.getBottom(), cellView.getRight(), cellView.getBottom(),
							localPaint);
				}
			}
			if (childCount % column != 0) {
				for (int j = 0; j < (column - childCount % column); j++) {
					View lastView = getChildAt(childCount - 1);
					// canvas.drawLine(lastView.getRight() + lastView.getWidth() *
					// j, lastView.getTop(), lastView.getRight() +
					// lastView.getWidth()* j, lastView.getBottom(), localPaint);
				}
			}
		} catch (Exception e) {
		}

	
	}

}
