package yh.tool.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import yh.app.appstart.lg.R;

import yh.app.utils.Utils;
import 云华.智慧校园.工具.IsNull;

/**
 * 自定义进度条
 */

@SuppressLint("DrawAllocation")
public class ProgressView extends View
{

    /**
     *        * 进度条画笔的宽度（dp）       
     */
    private int paintProgressWidth = 2;
    /**
     *        * 文字百分比的字体大小（sp）       
     */
    private int paintTextSize = 8;

    /**
     *        * 左侧已完成进度条的颜色       
     */
    private int paintLeftColor = 0xff67aae4;
    /**
     *        * 右侧未完成进度条的颜色       
     */
    private int paintRightColor = 0xffaaaaaa;

    /**
     *        * 百分比文字的颜色       
     */
    private int paintTextColor = 0xffff0077;
    /**
     *        * Contxt       
     */
    private Context context;

    /**
     *        * 主线程传过来进程 0 - 100       
     */
    private int progress;
    /**
     *        * 得到自定义视图的宽度       
     */
    private int viewWidth;

    /**
     *        * 得到自定义视图的Y轴中心点       
     */
    private int viewCenterY;
    /**
     *        * 画左边已完成进度条的画笔       
     */
    private Paint paintleft = new Paint();

    /**
     *        * 画右边未完成进度条的画笔       
     */
    private Paint paintRight = new Paint();
    private LinearGradient linearGradient;

    /**
     *        * 画中间的百分比文字的画笔       
     */
    private Paint paintText = new Paint();

    /**
     * 画上边图片画笔
     */
    private Paint paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     *        * 要画的文字的宽度       
     */
    private int textWidth;

    /**
     *        * 画文字时底部的坐标       
     */
    private float textBottomY;

    /**
     *        * 包裹文字的矩形       
     */
    private Rect rect = new Rect();

    /**
     *        * 文字总共移动的长度（即从0%到100%文字左侧移动的长度）       
     */
    private int totalMovedLength;
    /**
     * 进度条上面坐标图片
     */
    private Bitmap mBitmap;
    private int paintProgressWidthPx;

    private String maxp = "0";

    public ProgressView(Context context)
    {
	super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs)
    {
	super(context, attrs);
	this.context = context;
	initData();
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
    }

    /**
     *        * 初始化数据       
     */
    private void initData()
    {

	// 设置进度条画笔的宽度  
	paintProgressWidthPx = Utils.dip2px(context, paintProgressWidth);

	// 设置百分比文字的尺寸  
	int paintTextSizePx = Utils.sp2px(context, paintTextSize);

	paintRight.setColor(paintRightColor);
	paintRight.setStrokeWidth(paintProgressWidthPx);
	paintRight.setAntiAlias(true);
	paintRight.setStyle(Paint.Style.FILL);

	//  百分比文字画笔的属性  
	paintText.setColor(getResources().getColor(R.color.color_bleu));
	paintText.setTextSize(paintTextSizePx);
	paintText.setAntiAlias(true);
	paintText.setTypeface(Typeface.DEFAULT_BOLD);

	// 画bitmap画笔属性
	paintBitmap.setFilterBitmap(true);
	paintBitmap.setDither(true);
	mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ico_progress);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
	super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	getWidthAndHeight();
    }

    /**
     *        * 得到视图等的高度宽度尺寸数据       
     */
    private void getWidthAndHeight()
    {
	textWidth = mBitmap.getWidth();
	textBottomY = viewCenterY + rect.height() / 2;

	// 得到自定义视图的高度  
	int viewHeight = getMeasuredHeight();
	viewWidth = getMeasuredWidth();
	viewCenterY = viewHeight / 2;
	totalMovedLength = viewWidth - textWidth;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
	super.onDraw(canvas);
	// 得到float型进度  
	float progressFloat = progress / 100.0f;
	int dipy = context.getResources().getInteger(R.integer.pressdip);
	// 当前文字移动的长度  
	float currentMovedLentgh = totalMovedLength * progressFloat;

	//  已完成进度条画笔的属性  
	paintleft.setColor(paintLeftColor);
	paintleft.setStrokeWidth(paintProgressWidthPx);
	paintleft.setAntiAlias(true);
	paintleft.setStyle(Paint.Style.FILL);

	// 未完成进度条画笔的属性 
	linearGradient = new LinearGradient(0, 0, currentMovedLentgh + textWidth * 0.5f, 0, getResources().getColor(R.color.color_bleu_sheng), getResources().getColor(R.color.color_bleu_qian), Shader.TileMode.CLAMP);
	paintleft.setShader(linearGradient);

	// 画左侧已经完成的进度条，长度为从Veiw左端到文字的左侧  
	canvas.drawLine(0, viewCenterY, currentMovedLentgh + 50, viewCenterY, paintleft);
	// 未完成进度条画笔的属性 

	// 画右侧未完成的进度条，这个进度条的长度不是严格按照百分比来缩放的，因为文字的长度会变化，所以它的长度缩放比例也会变化  

	if (progress < 10)
	{
	    canvas.drawLine(currentMovedLentgh + textWidth * 0.5f, viewCenterY, viewWidth, viewCenterY, paintRight);
	    canvas.drawText(progress / 100 + "/" + maxp, currentMovedLentgh, textBottomY + 30, paintText);
	    canvas.drawBitmap(mBitmap, currentMovedLentgh, textBottomY - dipy, paintBitmap);
	} else if (progress < 100)
	{
	    canvas.drawLine(currentMovedLentgh + textWidth * 0.75f, viewCenterY, viewWidth, viewCenterY, paintRight);
	    canvas.drawText((IsNull.isNotNull(maxp) && !maxp.equals("0") ? (progress * Integer.valueOf(maxp)  / 100)+"" : "0") + "/" + maxp, currentMovedLentgh, textBottomY + 30, paintText);
	    canvas.drawBitmap(mBitmap, currentMovedLentgh, textBottomY - dipy, paintBitmap);
	} else
	{
	    canvas.drawLine(currentMovedLentgh + textWidth, viewCenterY, viewWidth + textWidth, viewCenterY, paintRight);
	    // 画文字(注意：文字要最后画，因为文字和进度条可能会有重合部分，所以要最后画文字，用文字盖住重合的部分)  
	    canvas.drawText(maxp + "/" + maxp, currentMovedLentgh - 30, textBottomY + 30, paintText);
	    canvas.drawBitmap(mBitmap, currentMovedLentgh, textBottomY - dipy, paintBitmap);
	}

    }

    /**
     *        * @param progress 外部传进来的当前进度       
     */
    public void setProgress(int progress)
    {
	if (progress >= 100)
	{
	    progress = 100;
	}
	this.progress = progress;
	postInvalidate();
    }

    public void setmaxProgress(String maxp)
    {
	this.maxp = maxp;
    }

}
