package yh.app.tool;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import 云华.智慧校园.工具.CodeManage;import yh.app.appstart.lg.R;

public class InternetImage extends AsyncTask<String, Integer, Bitmap>
{
	private ImageView image;
	private String urlPath;
	private String fileName;
	private final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/";

	/** 图片保存路径 */
	public InternetImage(ImageView image, String urlPath)
	{
		this.image = image;
		this.urlPath = urlPath;
		this.fileName = getBitmepName(urlPath);
	}

	@Override
	protected void onPreExecute()
	{
	}

	@Override
	protected Bitmap doInBackground(String... urls)
	{
		File file = new File(CodeManage.PATH_MAIN);
		if (!file.exists())
			file.mkdir();
		file = new File(CodeManage.path_resources);
		if (!file.exists())
			file.mkdir();
		file = new File(CodeManage.path_resources_image);
		if (!file.exists())
			file.mkdir();

		if (FileIsExist(CodeManage.path_resources_image + fileName))// 图片存在
		{
			try
			{
				return BitmapFactory.decodeFile(CodeManage.path_resources_image + fileName);
			} catch (Exception e)
			{
				return null;
			}
		} else
		{
			try
			{
				InputStream is = new java.net.URL(urlPath).openStream();
				Bitmap bitmap = BitmapFactory.decodeStream(is);
				saveFile(bitmap, CodeManage.path_resources_image + fileName);
				is.close();
				return bitmap;
			} catch (Exception e)
			{
				return null;
			}
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values)
	{
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void onPostExecute(Bitmap result)
	{
		try
		{
			if (result != null)
			{
				Drawable bd = new BitmapDrawable(result);
				image.setBackground(bd);
			} else
			{
				image.setBackgroundResource(R.drawable.q1);
			}
		} catch (Exception e)
		{
			image.setBackgroundResource(R.drawable.q1);
		}
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

	/**
	 * 保存文件
	 * 
	 * @param bm
	 * @param fileName
	 * @throws IOException
	 */
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

	private String getBitmepName(String url)
	{
		// https://222.180.23.213/index.php/om
		try
		{
			String[] arr = url.split("/");
			return arr[arr.length - 1];
		} catch (Exception e)
		{
			return null;
		}
	}
}
