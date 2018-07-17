package com.yunhuakeji.app.dao.impl;

import java.util.Map;

import android.view.View;
import android.view.View.OnClickListener;

public interface IDailyHelper
{
	/**
	 *
	 * 设置top栏
	 *
	 */
	public void setTop(String txtLeft, String txtRight);

	/**
	 * 设置可以签到时间
	 *
	 * @param signTime
	 *            显示内容，为空则不显示
	 */
	public void setCanSignTime(String signTime);

	/**
	 * 设置中部圆圈控件
	 *
	 * @param RroundView
	 *            圆圈控件
	 */
	public void setRroundView(View RroundView);

	/**
	 * 设置中部按钮点击事件
	 *
	 * @param listener
	 */
	public void setRroundViewOnclickListener(OnClickListener listener);

	/**
	 * 设置文本描述
	 *
	 * @param text1
	 * @param text2
	 */
	public void setTextDescription(String text1, String text2);

	/**
	 * 设置温馨提示
	 *
	 * @param title
	 * @param message
	 */
	public void setPrompt(String title, String message);

	/**
	 * 获取当前界面所有控件
	 *
	 * @return
	 */
	public Map<Integer, View> getViews();
}
