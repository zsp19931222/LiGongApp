package com.yunhuakeji.app.utils;

import java.io.File;

import android.content.Context;

public class LocalDateFileHelper
{
	private String FILE_NAME;
	private Context context;

	public LocalDateFileHelper(Context context, String fileName)
	{
		this.context = context;
		this.FILE_NAME = fileName;
	}

	public File getFile()
	{
		if (new File(context.getFilesDir(), FILE_NAME).exists())
		{
			return new File(context.getFilesDir(), FILE_NAME);
		} else
		{
			return getFileAndCreateNewFile();
		}
	}

	private File getFileAndCreateNewFile()
	{
		try
		{
			new File(context.getFilesDir(), FILE_NAME).createNewFile();
		} catch (Exception e)
		{
		}
		return new File(context.getFilesDir(), FILE_NAME);
	}

	public void setLocalString(String content)
	{
		if (getFile().exists())
		{
			FileHelper.write(getFile(), content);
		} else
		{
			FileHelper.write(getFile(), content);
		}
	}

	public String getLocalString(String defaultString)
	{
		if (getFile().exists() && null != FileHelper.getFileContent(getFile()) && !"".equals(FileHelper.getFileContent(getFile())))
		{
			return FileHelper.getFileContent(getFile());
		} else
		{
			return defaultString;
		}
	}

	public boolean exists(String dir, String fileName)
	{
		return new File(dir, fileName).exists();
	}
}
