package yh.app.tool;

import java.io.File;

import android.os.Environment;

public class DeleteFile
{
	private final String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/";

	public void DeleteWJJALL(String path)
	{
		File file = new File(ALBUM_PATH + path);
		File[] fileList = file.listFiles();
		try
		{
			if (fileList != null)
				for (int i = 0; i < fileList.length; i++)
				{
					fileList[i].delete();
				}
		} catch (Exception e)
		{
		}

	}

	public void DeleteWJ(String path)
	{

	}
}
