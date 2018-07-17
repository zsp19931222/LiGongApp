package yh.app.quanzitool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import yh.app.tool.MD5;
import 云华.智慧校园.工具._链接地址导航;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

public class QuanziImage extends AsyncTask<String, Bitmap, Bitmap>
{
	private String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/yhdownload/contacts/";
	private String url = "";
	private ImageView image;
	private String imagename;

	public QuanziImage(ImageView image, /* String url, */String imagename)
	{
		// this.url = url;
		this.image = image;
		this.imagename = MD5.MD5(_链接地址导航.addString + imagename);
	}

	@Override
	protected Bitmap doInBackground(String... p)
	{

		String fn = imagename + ".png";
		url = "http://202.202.144.12:8080/DC/contatcts/" + fn;
		Bitmap mBitmap = null;
		if (FileIsExist(fn))
		{
			try
			{
				mBitmap = BitmapFactory.decodeFile(ALBUM_PATH + fn);
			} catch (Exception e)
			{
				mBitmap = null;
			}
		} else
		{
			try
			{
				InputStream is = new java.net.URL(url).openStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				saveFile(bitmap, fn);
				is.close();
			} catch (Exception e)
			{
				mBitmap = null;
			}
		}
		return mBitmap;

	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onPostExecute(Bitmap result)
	{
		if (result == null)
		{
			return;
		}
		Drawable mDrawable = new BitmapDrawable(result);
		image.setBackground(mDrawable);
	}

	private boolean FileIsExist(String FileName)
	{
		File file = new File(ALBUM_PATH + FileName);
		if (file.exists())
		{
			return true;
		} else
			return false;
	}

	public void saveFile(Bitmap bm, String fileName) throws IOException
	{
		File dirFile = new File(ALBUM_PATH);
		if (!dirFile.exists())
		{
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ALBUM_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
		bos.flush();
		bos.close();
	}

}
