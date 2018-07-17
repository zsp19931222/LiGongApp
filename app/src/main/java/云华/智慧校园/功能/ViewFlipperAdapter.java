package 云华.智慧校园.功能;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.example.app3.activity.BrowserActivity;
import yh.app.appstart.lg.R;

import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.自定义控件.SQ_AdaptImageView;

public class ViewFlipperAdapter implements AnimationListener

{
    private ViewFlipper viewFlipper;
    private Animation leftInAnimation, leftOutAnimation, rightInAnimation, rightOutAnimation;
    private Timer timer;
    private Context context;
    private int length;

    public ViewFlipperAdapter(ViewFlipper viewFlipper, Context context, View[] Views)
    {
	viewFlipper.removeAllViews();
	this.viewFlipper = viewFlipper;
	this.context = context;
	leftInAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in);
	leftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out);
	rightInAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in);
	rightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out);
	for (int i = 0, length = Views.length; i < length; i++)
	{
	    this.viewFlipper.addView(Views[i]);
	}
	this.length = Views.length;
    }

    public ViewFlipperAdapter(ViewFlipper viewFlipper, Context context, Integer[] drawables)
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
	this.length = drawables.length;
    }

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
	this.length = drawables.length;
    }

    public ViewFlipperAdapter(ViewFlipper viewFlipper, Context context, Drawable[] drawables)
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
	this.length = drawables.length;
    }

    public void doit()
    {
	leftInAnimation.setAnimationListener(this);
	rightInAnimation.setAnimationListener(this);
	viewFlipper.setOnTouchListener(new OnTouchListener()
	{
	    @Override
	    public boolean onTouch(View v, MotionEvent event)
	    {
		if (length <= 1)
		{
		}
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
		    } else if (Math.abs(e1 - event.getX()) < 30)
		    {
			Intent intent = new Intent();
			intent.setAction(BrowserActivity.class.getName());
			intent.setPackage(context.getPackageName());
			try
			{
			    View view = ((ViewFlipper) v).getCurrentView();
			    Object tag = view.getTag();
			    if (IsNull.isNotNull(tag.toString()))
			    {
				intent.putExtra("url", tag.toString());
				context.startActivity(intent);
			    }
			} catch (Exception e)
			{
			    e.printStackTrace();
			}
		    }
		}
		return true;
	    }
	});
	setThread();
    }

    private ImageView getImageView(int id)
    {
	SQ_AdaptImageView imageView = new SQ_AdaptImageView(context);
	imageView.setBackground(id);
	return imageView;
    }

    private ImageView getImageView(Drawable id)
    {
	SQ_AdaptImageView imageView = new SQ_AdaptImageView(context);
	imageView.setBackground(id);
	return imageView;
    }

    public void next()
    {
	setThread();
	if (viewFlipper.getInAnimation() == null)
	{
	    viewFlipper.setInAnimation(leftInAnimation);
	}
	if (viewFlipper.getOutAnimation() == null)
	{
	    viewFlipper.setOutAnimation(leftOutAnimation);
	}
	viewFlipper.showNext();// 向右滑动
    }

    public void previous()
    {
	setThread();
	if (viewFlipper.getInAnimation() == null)
	{
	    viewFlipper.setInAnimation(rightInAnimation);
	}
	if (viewFlipper.getOutAnimation() == null)
	{
	    viewFlipper.setOutAnimation(rightOutAnimation);
	}
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
	if (length <= 1)
	    return;
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

    private OnPageChangeListener changeListener;

    public void setOnPageChangeListener(OnPageChangeListener changeListener)
    {
	this.changeListener = changeListener;
    }

    public interface OnPageChangeListener
    {
	/**
	 * 
	 * @param index
	 *            滑动完成后页面的游标
	 */
	public void onPageChangeListener(int index);
    }

    private float e1;

    @Override
    public void onAnimationStart(Animation animation)
    {
	// TODO Auto-generated method stub

    }

    @Override
    public void onAnimationEnd(Animation animation)
    {
	// TODO Auto-generated method stub
	if (changeListener != null)
	{
	    changeListener.onPageChangeListener(viewFlipper.getDisplayedChild());
	}
    }

    @Override
    public void onAnimationRepeat(Animation animation)
    {
	// TODO Auto-generated method stub

    }
}
