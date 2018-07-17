package yh.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.widget.ImageView;

public class MyGifView extends ImageView
{
	public MyGifView(Context context)
	{
		super(context);
	}

	public void setImage(int image_id)
	{
		movie = Movie.decodeStream(getResources().openRawResource(image_id));
	}

	private long movieStart;
	private Movie movie;

	// 此处必须重写该构造方法
	// public MyGifView(Context context, AttributeSet attributeSet)
	// {
	// super(context, attributeSet);
	// // 以文件流（InputStream）读取进gif图片资源
	// movie = Movie.decodeStream(getResources().openRawResource(image_id));
	// }

	@Override
	protected void onDraw(Canvas canvas)
	{
		long curTime = android.os.SystemClock.uptimeMillis();
		// 第一次播放
		if (movieStart == 0)
		{
			movieStart = curTime;
		}
		if (movie != null)
		{
			int duraction = movie.duration();
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);
			// 强制重绘
			invalidate();
		}
		super.onDraw(canvas);
	}
}