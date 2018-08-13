package com.example.app3;

import android.content.Intent;

import com.example.app3.base.BaseActivity;
import com.yhkj.cqgyxy.R;
import com.zxing.activity.CaptureActivity;

import 云华.智慧校园.工具.IsNull;

public class ActivityTeacherWelcomeStudent extends BaseActivity
{

    @Override
    protected void initActivityView()
    {
	// TODO Auto-generated method stub
	setContentView(R.layout.activity_welcome_teacher);
    }

    @Override
    protected void initView()
    {
	// TODO Auto-generated method stub

    }

    @Override
    protected void initData()
    {
	// TODO Auto-generated method stub

    }

    @Override
    protected void initAction()
    {
	// TODO Auto-generated method stub
	Intent intent = new Intent(this, CaptureActivity.class);
	startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	try
	{
	    // 获取二维码内容
	    String id = data.getExtras().getString("result");
	    if (IsNull.isNotNull(id))
	    {

	    } else
	    {
		finish();
	    }
	} catch (Exception e)
	{
	    finish();
	}
    }
}