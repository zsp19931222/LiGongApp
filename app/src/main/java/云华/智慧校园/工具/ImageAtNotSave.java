package 云华.智慧校园.工具;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import yh.app.appstart.lg.R;
public class ImageAtNotSave extends AsyncTask<String, Bitmap, Bitmap>
{
	private ImageView view;
	private String urlface;
	private Context context;

	public ImageAtNotSave(Context context, ImageView view, String urlface)
	{
		this.view = view;
		this.urlface = urlface;
		this.context = context;
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		try
		{
			InputStream is = new java.net.URL(urlface).openStream();
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			is.close();
			return bitmap;
		} catch (Exception e)
		{
		} finally
		{
		}
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		if (result != null)
			view.setBackground(new BitmapDrawable(context.getResources(), result));
		else
			view.setBackgroundResource(R.drawable.q1);
	}

}
