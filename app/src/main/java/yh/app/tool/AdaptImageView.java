package yh.app.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AdaptImageView extends ImageView
{
	public AdaptImageView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public AdaptImageView(Context context)
	{
		super(context);
		this.context = context;
	}

	public AdaptImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
	}

	private Context context;

	// @Override
	// public void setBackgroundResource(int resid)
	// {
	// super.setBackgroundResource(resid);
	// Bitmap btm = BitmapFactory.decodeResource(context.getResources(), resid);
	// float bitmapWH[] = new float[] { (float) btm.getWidth(), (float)
	// btm.getHeight() };
	// float screenW = getScreenW();
	// System.out.println("w:" + screenW + " H:" + (screenW * bitmapWH[1] /
	// bitmapWH[0]));
	// int viewWH[] = new int[] { (int) screenW - 60, (int) (screenW *
	// bitmapWH[1] / bitmapWH[0]) };
	// setLayoutParams(new LinearLayout.LayoutParams(viewWH[0], viewWH[01]));
	// }

	public int[] setMyBackgroundResource(int resid)
	{
		super.setBackgroundResource(resid);
		Bitmap btm = BitmapFactory.decodeResource(context.getResources(), resid);
		float bitmapWH[] = new float[]
		{
				btm.getWidth(), btm.getHeight()
		};
		float screenW = getScreenW();
		int viewWH[] = new int[]
		{
				(int) screenW - 60, (int) (screenW * bitmapWH[1] / bitmapWH[0])
		};
		setLayoutParams(new LinearLayout.LayoutParams(viewWH[0], viewWH[01]));
		return viewWH;
	}

	@Override
	public void setLayoutParams(LayoutParams params)
	{
		super.setLayoutParams(params);
	}

	private float getScreenW()
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
