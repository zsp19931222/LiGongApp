package com.yunhuakeji.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public abstract class YhActivity extends Activity
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initActivity();
		initView();
		initData();
		initAction();
	}

	public Context getContext()
	{
		return this;
	}

	protected abstract void initActivity();
	
	protected abstract void initView();
	
	protected abstract void initData();
	
	protected abstract void initAction();
}