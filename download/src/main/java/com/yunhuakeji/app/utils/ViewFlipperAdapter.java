package com.yunhuakeji.app.utils;

import java.util.Timer;
import java.util.TimerTask;
import com.yunhuakeji.app.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

@SuppressLint("HandlerLeak")
public class ViewFlipperAdapter
{
	private ViewFlipper viewFlipper;
	private Animation leftInAnimation, leftOutAnimation, rightInAnimation, rightOutAnimation;
	private Timer timer;
	private Context context;

	public ViewFlipperAdapter(ViewFlipper viewFlipper, Context context, int[] drawables)
	{
		this.viewFlipper = viewFlipper;
		this.context = context;
		leftInAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out);
		rightInAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out);
		for (int i = 0, length = drawables.length; i < length; i++)
		{
			this.viewFlipper.addView(getImageView(drawables[i]));
		}
	}

	public void doit()
	{
		viewFlipper.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				v.performClick();
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					e1 = event.getX();
				} else if (event.getAction() == MotionEvent.ACTION_UP)
				{
					if (e1 - event.getX() > 120)
					{
						next();
					} else if (e1 - event.getX() < -120)
					{
						previous();
					}
				}
				return true;
			}
		});
		setThread();
	}

	private ImageView getImageView(int id)
	{
		ImageView imageView = new ImageView(context);
		imageView.setBackgroundResource(id);
		try
		{
			ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//			lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//			lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
			imageView.setLayoutParams(lp);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return imageView;
	}

	public void next()
	{
		setThread();
		viewFlipper.setInAnimation(leftInAnimation);
		viewFlipper.setOutAnimation(leftOutAnimation);
		viewFlipper.showNext();// 向右滑动
	}

	public void previous()
	{
		setThread();
		viewFlipper.setInAnimation(rightInAnimation);
		viewFlipper.setOutAnimation(rightOutAnimation);
		viewFlipper.showPrevious();// 向左滑动
	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			next();
		}
	};

	public void setThread()
	{
		try
		{
			timer.cancel();
		} catch (Exception e)
		{
		}
		timer = new Timer();
		timer.schedule(new TimerTask()
		{

			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(0);
			}
		}, 4500, 4500);
	}

	private float e1;
}
