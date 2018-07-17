package yh.tool.widget;

import java.util.ArrayList;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import yh.app.utils.ScreenUtil;

public class FloatingDraftButton extends Button implements View.OnTouchListener {
	int lastX, lastY;
	int originX, originY;
	private final int screenWidth;
	private final int screenHeight;

	private ArrayList<Button> floatingActionButtons = new ArrayList<>();

	public FloatingDraftButton(Context context) {
		this(context, null);
	}

	public FloatingDraftButton(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FloatingDraftButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		screenWidth = ScreenUtil.getScreenWidth(context);
		screenHeight = ScreenUtil.getContentHeight(context);
		setOnTouchListener(this);
		

	}

	// 注册归属的FloatingActionButton
	public void registerButton(Button floatingActionButton) {
		floatingActionButtons.add(floatingActionButton);
	}

	public ArrayList<Button> getButtons() {
		return floatingActionButtons;
	}

	public int getButtonSize() {
		return floatingActionButtons.size();
	}

	// 是否可拖拽 一旦展开则不允许拖拽
	public boolean isDraftable() {
		for (Button btn : floatingActionButtons) {
			//如果按钮处于显示状态我们就不允许拖到它
			if (btn.getVisibility() == View.VISIBLE) {
				return false;
			}
		}
		return true;
	}

	// 当被拖拽后其所属的FloatingActionButton 也要改变位置
	private void slideButton(int l, int t, int r, int b) {
		for (Button floatingActionButton : floatingActionButtons) {
			floatingActionButton.layout(l, t, r, b);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		if (!isDraftable()) {
			return false;
		}
		int ea = event.getAction();
		switch (ea) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
			lastY = (int) event.getRawY();
			originX = lastX;
			originY = lastY;
			setAlpha(1f);
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = (int) event.getRawX() - lastX;
			int dy = (int) event.getRawY() - lastY;
			int l = v.getLeft() + dx;
			int b = v.getBottom() + dy;
			int r = v.getRight() + dx;
			int t = v.getTop() + dy;
			// 下面判断移动是否超出屏幕
			if (l < 0) {
				l = 0;
				r = l + v.getWidth();
			}
			if (t < 0) {
				t = 0;
				b = t + v.getHeight();
			}
			if (r > screenWidth) {
				r = screenWidth;
				l = r - v.getWidth();
			}
			if (b > screenHeight - 200) {
				b = screenHeight - 200;
				t = b - v.getHeight();
			}
			v.layout(l, t, r, b);
			slideButton(l, t, r, b);
			lastX = (int) event.getRawX();
			lastY = (int) event.getRawY();
			v.postInvalidate();
			break;
		case MotionEvent.ACTION_UP:
			int distance = (int) event.getRawX() - originX + (int) event.getRawY() - originY;
			Log.e("DIstance", distance + "");
			if (Math.abs(distance) < 20) {
				// 当变化太小的时候什么都不做 OnClick执行
			} else {
				return true;
			}
			break;
		}
		return false;

	}

}
