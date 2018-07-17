package yh.app.acticity;

import android.content.Context;
import android.os.Bundle;
import yh.tool.widget.IntegrationActivity;

public abstract class YhActivity extends IntegrationActivity
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