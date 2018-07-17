package yh.tool.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class DATextView extends TextView implements Runnable{
	private int currentScrollX;// 当前滚动的位置
	private boolean isStop = false;
	private int textWidth;
	private boolean isMeasure = false;

	public DATextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public DATextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DATextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if (!isMeasure) {// 文字宽度只需获取一次就可以了
			getTextWidth();
			isMeasure = true;
		}
	}

	/**
	 * 获取文字宽度
	 */
	private void getTextWidth() {
		Paint paint = this.getPaint();
		String str = this.getText().toString();
		textWidth = (int) paint.measureText(str);
	}

	@Override
	public void run() {
		currentScrollX -= 1;// 滚动速度
		scrollTo(currentScrollX, 0);
		if (isStop) {
			return;
		}
		if (getScrollX() <= -(this.getWidth())) {
			scrollTo(textWidth, 0);
			currentScrollX = textWidth;
			// return;
		}
		postDelayed(this, 10);
	}

	// 开始滚动
	public void startScroll() {
		isStop = false;
		this.removeCallbacks(this);
		post(this);
	}

	// 停止滚动
	public void stopScroll() {
		isStop = true;
	}

	// 从头开始滚动
	public void startFor0() {
		currentScrollX = 0;
		startScroll();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(text, type);
		startScroll();
	}

	@Override
	public void destroyDrawingCache() {
		// TODO Auto-generated method stub
		super.destroyDrawingCache();

	}

}
