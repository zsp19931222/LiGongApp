package com.yunhuakeji.app.utils;

import java.util.HashMap;
import java.util.Map;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu.Builder;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class FloatViewHelper implements View.OnClickListener
{
	private Context context;

	private int statusBarHeight1 = 0;

	public static final int STATE_LEFT   = 0x00;
	public static final int STATE_RIGHT  = 0x01;

	public static final int STATE_TOP    = 0x00;
	public static final int STATE_BUTTOM = 0x10;

	/**
	 * 左上右下
	 */
	public int CURRENT_STATE = STATE_BUTTOM | STATE_RIGHT;

	private FloatingActionButton rightLowerButton;

	private FloatingActionMenu rightLowerMenu;

	private Builder builder;

	private ImageView fabIconNew;

	private LayoutParams mLayoutParams1;

	private static int[] screenSize = new int[]
			{
					0, 0
			};

	private int fabIconNewDrawable;
	private int size;
	private String[] textArr;
	private int[] imageArr;

	private Map<Integer, View> viewMap = new HashMap<>();

	public FloatViewHelper(Context context)
	{
		this.context = context;
		statusBarHeight1 = ScreenHelper.getStatusHeight(context);
		screenSize = ScreenHelper.getScreenSize(context);
	}

	public void addFloatView(int fabIconNewDrawable, int size, String[] textArr, int[] imageArr)
	{
		// Set up the white button on the lower right corner
		// more or less with default parameter
		this.fabIconNewDrawable = fabIconNewDrawable;
		this.size = size;
		this.textArr = textArr;
		this.imageArr = imageArr;

		setRightLowerButton();

		setRightLowerMenu();
	}

	int buttonX = 0, buttonY = 0;

	int lastX, lastY;
	int originX, originY;
	private OnClickListener listener;

	public void setOnClickListener(OnClickListener listener)
	{
		this.listener = listener;
	}

	public void setRightLowerMenu()
	{
		rightLowerMenu = builder.build();
		// Listen menu open and close events to animate the button content view
		rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener()
		{
			@Override
			public void onMenuOpened(FloatingActionMenu menu)
			{
				// Rotate the icon of rightLowerButton 45 degrees clockwise
				fabIconNew.setRotation(0);
				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				animation.start();
			}

			@Override
			public void onMenuClosed(FloatingActionMenu menu)
			{
				// Rotate the icon of rightLowerButton 45 degrees counter-clockwise
				startAngle += 90;
				endAngle   += 90;
				builder.setStartAngle(startAngle).setEndAngle(endAngle);
				fabIconNew.setRotation(45);
				PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
				ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, pvhR);
				animation.start();
			}
		});
	}

	public void setRightLowerButton()
	{
		fabIconNew = new ImageView(context);
		fabIconNew.setBackgroundResource(fabIconNewDrawable);
		rightLowerButton = new FloatingActionButton.Builder(context).setContentView(fabIconNew).build();
		SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(context);
		builder = new FloatingActionMenu.Builder(context);
		for (int i = 0; i < size; i++)
		{
			TextView textView = new TextView(context);

			if (null != textArr && textArr.length > i)
			{
				textView.setText(textArr[i]);
			}

			textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
			textView.setGravity(Gravity.CENTER);

			SubActionButton tcSub1 = rLSubBuilder.setContentView(textView).build();

			if (null != imageArr && imageArr.length > i)
			{
				textView.setBackgroundResource(imageArr[i]);
			}
			viewMap.put(i, tcSub1);
			tcSub1.setOnClickListener(this);
			builder.addSubActionView(tcSub1);
		}

		// Build the menu with default options: light theme, 90 degrees, 72dp radius.
		// Set 4 default SubActionButtons
		builder.setStartAngle(startAngle).setEndAngle(endAngle).attachTo(rightLowerButton).setRadius(120);
		if (null != mLayoutParams1)
		{
			rightLowerButton.setLayoutParams(mLayoutParams1);
		}

//		rightLowerButton.setOnTouchListener(new OnTouchListener()
//		{
//			@Override
//			public boolean onTouch(View view, MotionEvent event)
//			{
//
//				int ea = event.getAction();
//				switch (ea)
//				{
//				case MotionEvent.ACTION_DOWN:
//					lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
//					lastY = (int) event.getRawY();
//					originX = lastX;
//					originY = lastY;
//					break;
//				case MotionEvent.ACTION_MOVE:
//					int dx = (int) event.getRawX() - lastX;
//					int dy = (int) event.getRawY() - lastY;
//					int l = rightLowerButton.getLeft() + dx;
//					int b = rightLowerButton.getBottom() + dy;
//					int r = rightLowerButton.getRight() + dx;
//					int t = rightLowerButton.getTop() + dy;
//					// 下面判断移动是否超出屏幕
//					if (l < 0)
//					{
//						l = 0;
//					}
//					if (t < 0)
//					{
//						t = 0;
//					}
//					if (l > screenSize[0] - rightLowerButton.getWidth())
//					{
//						l = screenSize[0] - rightLowerButton.getWidth();
//					}
//					if (t > screenSize[1] - rightLowerButton.getHeight() - statusBarHeight1)
//					{
//						t = screenSize[1] - rightLowerButton.getHeight() - statusBarHeight1;
//					}
//
//					LayoutParams mLayoutParams = (LayoutParams) rightLowerButton.getLayoutParams();
//
//					buttonX = l;
//					buttonY = t;
//
//					mLayoutParams.setMargins(l, t, r, b);
//
//					rightLowerButton.setLayoutParams(mLayoutParams);
//
//					lastX = (int) event.getRawX();
//					lastY = (int) event.getRawY();
//					break;
//				case MotionEvent.ACTION_UP:
//					int distance = (int) event.getRawX() - originX + (int) event.getRawY() - originY;
//					mLayoutParams1 = (LayoutParams) rightLowerButton.getLayoutParams();
//					judgeLocation(mLayoutParams1);
////
//					setButtonLocation();
//					if (((LayoutParams) rightLowerButton.getLayoutParams()).leftMargin != 0 && ((LayoutParams) rightLowerButton.getLayoutParams()).rightMargin != 0)
//					{
//						AnimationSet animationSet = new AnimationSet(true);
//
////						CURRENT_STATE = mLayoutParams1.leftMargin > (screenSize[0] - rightLowerButton.getWidth()) / 2 ? STATE_RIGHT : STATE_LEFT;
//
//						int dx2 = CURRENT_STATE != STATE_LEFT ? screenSize[0] - mLayoutParams1.leftMargin - rightLowerButton.getWidth() : 0 - mLayoutParams1.leftMargin;
//						TranslateAnimation translateAnimation = new TranslateAnimation(0, dx2, 0, 0);
//						translateAnimation.setDuration(200);
//						animationSet.addAnimation(translateAnimation);
//
//						animationSet.setAnimationListener(new AnimationListener()
//						{
//							@Override
//							public void onAnimationStart(Animation animation)
//							{
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onAnimationRepeat(Animation animation)
//							{
//								// TODO Auto-generated method stub
//
//							}
//
//							@Override
//							public void onAnimationEnd(Animation animation)
//							{
//								// TODO Auto-generated method stub
//								mLayoutParams1.leftMargin = mLayoutParams1.leftMargin > (screenSize[0] - rightLowerButton.getWidth()) / 2 ? screenSize[0] - rightLowerButton.getWidth() : 0;
//								Log.e("touch", "" + mLayoutParams1.leftMargin);
//								rightLowerButton.setLayoutParams(mLayoutParams1);
//							}
//						});
//						rightLowerButton.startAnimation(animationSet);
//					}
//
//					if (Math.abs(distance) < 20)
//					{
//						// 当变化太小的时候什么都不做 OnClick执行
//					} else
//					{
//						return true;
//					}
//					break;
//				}
//				return false;
//
//			}
//		});
	}
	int startAngle = 0, endAngle = 90;
	/**
	 * 设置悬浮圆点位置
	 * @param state
	 */
	private void setButtonLocation()
	{

		if(CURRENT_STATE == (STATE_LEFT | STATE_TOP))
		{
			// TODO 左上
			startAngle = 90;
			endAngle = 180;
		} else if(CURRENT_STATE == (STATE_LEFT | STATE_BUTTOM))
		{
			// TODO 左下
			startAngle = 0;
			endAngle = 90;
		} else if(CURRENT_STATE == (STATE_RIGHT | STATE_TOP))
		{
			// TODO 右上
			startAngle = 180;
			endAngle = 270;
		} else if(CURRENT_STATE == (STATE_RIGHT | STATE_BUTTOM))
		{
			// TODO 右下
			startAngle = 270;
			endAngle = 260;
		}
		rightLowerMenu.removeViewFromCurrentContainer(rightLowerButton);
		addFloatView(startAngle, endAngle, textArr, imageArr);

//		rightLowerMenu.removeViewFromCurrentContainer(rightLowerButton);
//
//		builder.setStartAngle(startAngle);
//		builder.setEndAngle(endAngle);
//		rightLowerMenu = builder.build();

	}

	/**
	 * 判断悬浮圆点位置
	 */
	private void judgeLocation(LayoutParams mLayoutParams)
	{
		CURRENT_STATE = CURRENT_STATE | (mLayoutParams.topMargin < rightLowerButton.getTop() ? STATE_TOP : STATE_BUTTOM);

		CURRENT_STATE = CURRENT_STATE | (mLayoutParams.leftMargin > (screenSize[0] - rightLowerButton.getWidth()) / 2 ? STATE_RIGHT : STATE_LEFT);
	}

	public interface OnClickListener
	{
		void setItemOnClickListener(View view, int position);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		for (int i = 0; i < viewMap.size(); i++)
		{
			if (v == viewMap.get(i))
			{
				listener.setItemOnClickListener(v, i);
				return;
			}
		}
	}
}