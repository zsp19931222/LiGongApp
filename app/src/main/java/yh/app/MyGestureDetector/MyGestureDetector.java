package yh.app.MyGestureDetector;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class MyGestureDetector implements OnGestureListener
{

	@Override
	public boolean onDown(MotionEvent e)
	{
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e)
	{

	}

	/***
	 * 点击松开执行
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e)
	{
		return false;
	}

	/***
	 * e1 是起点，e2是终点，如果distanceX=e1.x-e2.x>0说明向左滑动。反之亦如此.
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
	{
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e)
	{

	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY)
	{
		return false;
	}

}