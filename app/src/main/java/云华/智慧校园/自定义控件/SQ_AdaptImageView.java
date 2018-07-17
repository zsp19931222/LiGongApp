package 云华.智慧校园.自定义控件;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SQ_AdaptImageView extends ImageView
{
	public SQ_AdaptImageView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		this.context = context;
	}

	public SQ_AdaptImageView(Context context)
	{
		super(context);
		this.context = context;
	}

	public SQ_AdaptImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.context = context;
	}

	private Context context;

	@Override
	public void setBackgroundResource(int resid)
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
				(int) screenW + 3, (int) (screenW * bitmapWH[1] / bitmapWH[0])
		};
		setLayoutParams(new LinearLayout.LayoutParams(viewWH[0], viewWH[01]));
	}

	public int[] setBackground(int resid)
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
				(int) screenW + 3, (int) (screenW * bitmapWH[1] / bitmapWH[0])
		};
		setLayoutParams(new LinearLayout.LayoutParams(viewWH[0], viewWH[01]));
		return viewWH;
	}
	
	public void setBackground(Bitmap background)
	{
		super.setBackground(new BitmapDrawable(context.getResources(), background));
		float bitmapWH[] = new float[]
		{
				background.getWidth(), background.getHeight()
		};
		float screenW = getScreenW();
		int viewWH[] = null;
		if (bitmapWH[0] >= screenW / 2)
		{
			viewWH = new int[]
			{
					(int) screenW / 2, (int) (screenW / 2 * bitmapWH[1] / bitmapWH[0])
			};
		} else
		{
			viewWH = new int[]
			{
					(int) bitmapWH[0], (int) bitmapWH[1]
			};
		}
		setLayoutParams(new LinearLayout.LayoutParams(viewWH[0], viewWH[01]));
	};

	// @Override
	// public void setLayoutParams(LayoutParams params)
	// {
	// super.setLayoutParams(params);
	// }

	private float getScreenW()
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}
}
