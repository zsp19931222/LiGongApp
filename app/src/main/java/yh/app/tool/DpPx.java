package yh.app.tool;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

public class DpPx
{
	private Context context;

	public DpPx(Context context)
	{
		this.context = context;
	}

	private int getDPI()
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int DPI = dm.densityDpi;
		return DPI;
	}

	public int getDpToPx(int dp)
	{
		return (int) (dp * getDPI() / 160.00);
	}

	public double getDp_Px()
	{
		return getDPI() / 160.00;
	}

	public int[] getWH()
	{
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int wh[] = new int[] { dm.widthPixels, dm.heightPixels };
		return wh;
	}

	public double getScreenSizeOfDevice()
	{
		Point point = new Point();
		((Activity) context).getWindowManager().getDefaultDisplay().getRealSize(point);
		DisplayMetrics dm = ((Activity) context).getResources().getDisplayMetrics();
		double x = Math.pow(point.x / dm.xdpi, 2);
		double y = Math.pow(point.y / dm.ydpi, 2);
		return Math.sqrt(x + y);
	}
}
