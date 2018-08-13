package yh.app.maptools;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
@SuppressLint({
		"InflateParams", "ClickableViewAccessibility"
})
public class MapPopupWindow extends Activity
{
	private String name, imageurl;
	private String content;
	private Context context;
	private FrameLayout layout;
	private PopupWindow popupWindow;

	public MapPopupWindow(FrameLayout layout, Context context, String name, String content, String imageurl)
	{
		this.context = context;
		this.content = content;
		this.layout = layout;
		this.name = name;
		this.imageurl = imageurl;
	}

	private ImageView image;

	public void doit()
	{
		View mView = LayoutInflater.from(context).inflate(R.layout.map_popupwindow, null);
		TextView name = (TextView) mView.findViewById(R.id.map_didian_name);
		TextView content = (TextView) mView.findViewById(R.id.map_didian_content);
		image = (ImageView) mView.findViewById(R.id.map_didian_img);
		At at = new At();
		at.executeOnExecutor(Executors.newCachedThreadPool());
		name.setText(this.name);
		content.setText(this.content);
		popupWindow = new PopupWindow(mView, LayoutParams.MATCH_PARENT, getHeigth() / 3, true);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttom_biankuang));
		popupWindow.showAtLocation(layout, Gravity.BOTTOM, 0, 0);
	}

	public int getHeigth()
	{
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels;
	}

	class At extends AsyncTask<String, Bitmap, Bitmap>
	{
		@Override
		protected Bitmap doInBackground(String... params)
		{
			Bitmap result = null;
			InputStream is;
			try
			{
				is = new java.net.URL(imageurl).openStream();
				result = BitmapFactory.decodeStream(is);
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onPostExecute(Bitmap result)
		{
			try
			{
				image.setImageBitmap(result);
			} catch (Exception e)
			{
				e.printStackTrace();
			}

		}

	}
}
