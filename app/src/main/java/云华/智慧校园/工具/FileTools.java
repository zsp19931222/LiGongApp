package 云华.智慧校园.工具;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;

public class FileTools
{
	private static final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/YHImage/";

	public static File saveFile(Bitmap bm, String fileName, String wjj)
	{
		File myCaptureFile = null;
		try
		{
			File dirFile = new File(ALBUM_PATH);
			if (!dirFile.exists())
			{
				dirFile.mkdir();
			}
			File dirFile1 = new File(ALBUM_PATH + "/" + wjj + "/");
			if (!dirFile1.exists())
			{
				dirFile1.mkdir();
			}
			myCaptureFile = new File(ALBUM_PATH + "/" + wjj + "/" + fileName);
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
			bm.compress(Bitmap.CompressFormat.PNG, 80, bos);
			bos.flush();
			bos.close();
			dirFile1 = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return myCaptureFile;
	}

	public static String getFilePathByUri(Uri myUri, Context context)
	{
		String res = null;
		String[] proj =
		{
				MediaColumns.DATA
		};
		Cursor cursor = context.getContentResolver().query(myUri, proj, null, null, null);
		if (cursor.moveToFirst())
		{
			int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
			res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}

	public static Uri getUriByFilePath(String filePath, Context context)
	{
		Uri mUri = Uri.parse("content://media/external/images/media");
		Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			String data = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
			if (filePath.equals(data))
			{
				int ringtoneID = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));
				return Uri.withAppendedPath(mUri, "" + ringtoneID);
			}
			cursor.moveToNext();
		}
		return null;

	}
}
