package com.yunhuakeji.app.utils;

import com.yunhuakeji.app.R;
import com.yunhuakeji.app.dao.impl.IMDailyHelper;

import android.content.Context;
import android.view.View;

public class DefaultDailyHelper extends IMDailyHelper
{

	
	public DefaultDailyHelper(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void doDefaultHelper(String yh_txt_daily_djz, String yh_txt_daily_time, String yh_txt_daily_can_sign_time, View yh_linearlayout_daily_sign, String yh_txt_daily_sign_time, String yh_txt_daily_sign_msg, String yh_txt_daily_title, String yh_txt_daily_message)
	{
		setTop(yh_txt_daily_djz, yh_txt_daily_time);
		setCanSignTime(yh_txt_daily_can_sign_time);
		setRroundView(yh_linearlayout_daily_sign);
		setTextDescription(yh_txt_daily_sign_time, yh_txt_daily_sign_msg);
		setPrompt(yh_txt_daily_title, yh_txt_daily_message);
	}

	public void setOnclickListener(final OnClickListener listener)
	{
		getViews().get(R.id.yh_relativelayout_daily_sign).setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				listener.setOnClickListener(v);
			}
		});
	}

	public interface OnClickListener
	{
		void setOnClickListener(View view);
	}
}