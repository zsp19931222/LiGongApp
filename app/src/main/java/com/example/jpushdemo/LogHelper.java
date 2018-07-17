package com.example.jpushdemo;

import android.util.Log;

public class LogHelper
{
	private final static String TAG = "Test";

	public static void e(String message)
	{
		Log.e(TAG, message);
	}

	public static void e(String message, Exception e)
	{
		Log.e(TAG, message, e);
	}
}
