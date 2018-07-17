package com.example.app3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

@SuppressLint("DrawAllocation")
public class RoundImageView extends ImageView
{

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
    }

    public RoundImageView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
    }

    public RoundImageView(Context context)
    {
	super(context);
	// TODO Auto-generated constructor stub
    }

    private Paint mPaintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mRawBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix = new Matrix();

    @Override
    protected void onDraw(Canvas canvas)
    {
	Bitmap rawBitmap = getBitmap(getDrawable());
	if (rawBitmap != null)
	{
	    int viewWidth = getWidth();
	    int viewHeight = getHeight();
	    int viewMinSize = Math.min(viewWidth, viewHeight);
	    float dstWidth = viewMinSize;
	    float dstHeight = viewMinSize;
	    if (mShader == null || !rawBitmap.equals(mRawBitmap))
	    {
		mRawBitmap = rawBitmap;
		mShader = new BitmapShader(mRawBitmap, TileMode.CLAMP, TileMode.CLAMP);
	    }
	    if (mShader != null)
	    {
		mMatrix.setScale(dstWidth / rawBitmap.getWidth(), dstHeight / rawBitmap.getHeight());
		mShader.setLocalMatrix(mMatrix);
	    }
	    mPaintBitmap.setShader(mShader);
	    float radius = viewMinSize / 2.0f;
	    canvas.drawCircle(radius, radius, radius, mPaintBitmap);
	} else
	{
	    super.onDraw(canvas);
	}
    }

    /**
     * 获取圆角图片
     * 
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)
    {
	Bitmap output = null;
	try
	{
	    if (bitmap == null)
		return null;
	    output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
	    Canvas canvas = new Canvas(output);

	    final Paint paint = new Paint();
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
	    final RectF rectF = new RectF(rect);

	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
	    canvas.drawBitmap(bitmap, rect, rect, paint);
	} catch (OutOfMemoryError e)
	{
	    System.gc();
	    output = null;
	}
	return output;
    }

    private float Radius = 24;

    private Bitmap getBitmap(Drawable drawable)
    {
	if (drawable instanceof BitmapDrawable)
	{
	    return ((BitmapDrawable) drawable).getBitmap();
	} else if (drawable instanceof ColorDrawable)
	{
	    Rect rect = drawable.getBounds();
	    int width = rect.right - rect.left;
	    int height = rect.bottom - rect.top;
	    int color = ((ColorDrawable) drawable).getColor();
	    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap);
	    canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
	    return bitmap;
	} else
	{
	    return null;
	}
    }
}
