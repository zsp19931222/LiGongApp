package 云华.智慧校园.功能;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class _截图
{
	public Bitmap captrueScreen(Activity context)
	{
		// 获取窗口的顶层视图对象
		View v = context.getWindow().getDecorView();
		v.setDrawingCacheEnabled(true);
		v.buildDrawingCache();
		// 第一步:获取保存屏幕图像的Bitmap对象
		Bitmap srcBitmap = v.getDrawingCache();
		Rect frame = new Rect();
		// decorView是window中的最顶层view，可以从window中获取到decorView，然后decorView有个getWindowVisibleDisplayFrame方法可以获取到程序显示的区域，包括标题栏，但不包括状态栏。
		context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		// 第二步 得到状态栏的高度
		int statusHeight = frame.top;
		// 第三步 获取屏幕图像的高度
		Point outSize = new Point();
		context.getWindowManager().getDefaultDisplay().getSize(outSize);
		int width = outSize.x;
		int height = outSize.y;
		// 第四步 创建新的Bitmap对象 并截取除了状态栏的其他区域
		Bitmap bitmap = Bitmap.createBitmap(srcBitmap, 0, statusHeight, width, height - statusHeight);
		v.destroyDrawingCache();
		return bitmap;
	}
}
