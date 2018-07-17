package yh.app.utils;

import java.io.BufferedOutputStream;

import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.自定义控件.SQ_AdaptImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.jpushdemo.ExampleApplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class SQ_Image extends AsyncTask<String, Bitmap, Bitmap>
{
	private String imageurl;
	private SQ_AdaptImageView image;
	private Context context;
	private int defaultImage;

	public SQ_Image(String imageurl, SQ_AdaptImageView image, Context context, String function_name, int defaultImage)
	{
		this.context = context;
		this.image = image;
		this.imageurl = imageurl;
		this.defaultImage = defaultImage;
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		// 获取网络图片
		Bitmap tmpBitmap = null;
		SQLiteDatabase db = null;
		Cursor c = null;
		try
		{
			db = new SqliteHelper().getWrite();
			c = db.rawQuery("select FunctionID from button where face=?", new String[]
			{
					imageurl
			});
			String fn = getFileName(imageurl, c);
			if (FileIsExist(fn))// 图片存在
			{
				try
				{
					tmpBitmap = BitmapFactory.decodeFile(ExampleApplication.IMAGE_FUNCTION_PATH + fn);
				} catch (Exception e)
				{
					tmpBitmap = null;
				}
			} else
			{
				try
				{
					InputStream is = new java.net.URL(imageurl).openStream();
					Bitmap bitmap = BitmapFactory.decodeStream(is);
					tmpBitmap = bitmap;
					saveFile(bitmap, fn);
					is.close();
				} catch (Exception e)
				{
				}
			}
		} catch (Exception e)
		{
		} finally
		{
			try
			{
				new SqliteDBCLose(db, c).close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		if (tmpBitmap != null)
			return tmpBitmap;
		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		if (result == null)
		{
			image.setBackgroundResource(defaultImage);
			/** 错误处理机制 */
			// Constants.error_function.add(function_name);
			// if (!isDialogOpen)
			// new ErrorFuntion(context).wealErrorFunction();
			return;
		}
		image.setBackground(result);
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
		File dirFile = new File(ExampleApplication.IMAGE_FUNCTION_PATH);
		if (!dirFile.exists())
		{
			dirFile.mkdir();
		}
		File myCaptureFile = new File(ExampleApplication.IMAGE_FUNCTION_PATH + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
		bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
		bos.flush();
		bos.close();
		dirFile = null;
		myCaptureFile = null;
	}

	private String getFileName(String url, Cursor c)
	{
		String FileName = "";
		while (c.moveToNext())
		{
			FileName = c.getString(0).toString() + ".png";
		}
		return FileName;
	}

	private boolean FileIsExist(String FileName)
	{
		File file = new File(ExampleApplication.IMAGE_FUNCTION_PATH + FileName);
		if (file.exists())
		{
			file = null;
			return true;
		} else
		{
			file = null;
			return false;
		}
	}
}