package com.yunhuakeji.app.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.yunhuakeji.app.R;
import com.yunhuakeji.app.utils.IsNull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("UseSparseArrays")
public class IMDailyHelper implements IDailyHelper
{
	private Context context;

	private Map<Integer, View> mapView = new HashMap<Integer, View>();

	public IMDailyHelper(Context context)
	{
		this.context = context;
		initView();
	}

	@Override
	public void setTop(String txtLeft, String txtRight)
	{
		((TextView) getViews().get(R.id.yh_txt_daily_djz)).setText(txtLeft);
		((TextView) getViews().get(R.id.yh_txt_daily_time)).setText(txtRight);
	}

	@Override
	public void setCanSignTime(String signTime)
	{
		if (IsNull.isNotNull(signTime))
		{
			((TextView) getViews().get(R.id.yh_txt_daily_can_sign_time)).setText(signTime);
		} else
		{
			getViews().get(R.id.yh_txt_daily_can_sign_time).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void setRroundView(View RoundView)
	{
		((RelativeLayout) getViews().get(R.id.yh_relativelayout_daily_sign)).removeAllViews();
		((RelativeLayout) getViews().get(R.id.yh_relativelayout_daily_sign)).addView(RoundView);
	}

	@Override
	public void setRroundViewOnclickListener(OnClickListener listener)
	{
		getViews().get(R.id.yh_relativelayout_daily_sign).setOnClickListener(listener);
	}

	@Override
	public void setTextDescription(String time, String msg)
	{
		if (IsNull.isNotNull(time))
		{
			((TextView) getViews().get(R.id.yh_txt_daily_sign_time)).setText(time);
		} else
		{
			getViews().get(R.id.yh_txt_daily_sign_time).setVisibility(View.INVISIBLE);
		}
		
		if (IsNull.isNotNull(msg))
		{
			((TextView) getViews().get(R.id.yh_txt_daily_sign_msg)).setText(msg);
		} else
		{
			getViews().get(R.id.yh_txt_daily_sign_msg).setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void setPrompt(String title, String message)
	{
		((TextView) getViews().get(R.id.yh_txt_daily_title)).setText(title);
		((TextView) getViews().get(R.id.yh_txt_daily_message)).setText(message);
	}

	@Override
	public Map<Integer, View> getViews()
	{
		return mapView;
	}

	private void initView()
	{
		mapView.put(R.id.yh_txt_daily_can_sign_time, ((Activity) context).findViewById(R.id.yh_txt_daily_can_sign_time));
		mapView.put(R.id.yh_txt_daily_djz, ((Activity) context).findViewById(R.id.yh_txt_daily_djz));
		mapView.put(R.id.yh_txt_daily_time, ((Activity) context).findViewById(R.id.yh_txt_daily_time));
		mapView.put(R.id.yh_relativelayout_daily_sign, ((Activity) context).findViewById(R.id.yh_relativelayout_daily_sign));
		mapView.put(R.id.yh_txt_daily_sign_time, ((Activity) context).findViewById(R.id.yh_txt_daily_sign_time));
		mapView.put(R.id.yh_txt_daily_sign_msg, ((Activity) context).findViewById(R.id.yh_txt_daily_sign_msg));
		mapView.put(R.id.yh_txt_daily_title, ((Activity) context).findViewById(R.id.yh_txt_daily_title));
		mapView.put(R.id.yh_txt_daily_message, ((Activity) context).findViewById(R.id.yh_txt_daily_message));
	}
}