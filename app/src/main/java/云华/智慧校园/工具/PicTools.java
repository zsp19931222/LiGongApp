package 云华.智慧校园.工具;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executors;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.uiengine.HomeSetting;

public class PicTools
{
	private Context context;

	public PicTools(Context context)
	{
		this.context = context;
	}

	public static Bitmap getImage(String path)
	{
		try
		{
			return BitmapFactory.decodeStream(new FileInputStream(new File(path)));
		} catch (FileNotFoundException e)
		{
			return BitmapFactory.decodeResource(Constants.App_Context.getResources(), R.drawable.q1);
		}
	}

	public void setImageViewBackground(ImageView image, String filePath)
	{
		new InternetImage(image, filePath).executeOnExecutor(Executors.newCachedThreadPool());
	}

	class InternetImage extends AsyncTask<String, Integer, Bitmap>
	{
		private ImageView image;
		private String filePath;
		private final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/zhxy/";

		public InternetImage(ImageView image, String filePath)
		{
			this.image = image;
			this.filePath = filePath;
		}

		@Override
		protected Bitmap doInBackground(String... params)
		{
			// 获取网络图片
			Bitmap tmpBitmap = null;
			SQLiteDatabase db = null;
			Cursor c = null;
			String url = null;
			try
			{
				url = new SqliteHelper().rawQuery("select faceaddress from user where userid='" + Constants.number + "'").get(0).get("faceaddress");
			} catch (Exception e)
			{
				url = null;
			}
			String name = getName(url);
			try
			{

				if (name != null && FileIsExist(filePath, name))// 图片存在
				{
					try
					{
						tmpBitmap = BitmapFactory.decodeFile(ALBUM_PATH + filePath + "/" + name);
					} catch (Exception e)
					{
						tmpBitmap = null;
					}
				} else
				{
					try
					{
						InputStream is = new java.net.URL(url).openStream();
						Bitmap bitmap = BitmapFactory.decodeStream(is);
						tmpBitmap = bitmap;
						saveFile(bitmap, name);
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
			if (result != null)
				image.setImageBitmap(result);
			else
				image.setImageBitmap(((BitmapDrawable) (context.getResources().getDrawable(R.drawable.q1))).getBitmap());
			HomeSetting.tx = image.getDrawingCache();
		}

		private String getName(String url)
		{

			try
			{
				String[] arr = url.split("/");
				return arr[arr.length - 1];
			} catch (Exception e)
			{
				return null;
			}
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
			dirFile = null;
			myCaptureFile = null;
		}

		private boolean FileIsExist(String path, String FileName)
		{
			File file = new File(ALBUM_PATH + path + "/" + FileName);
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
}
