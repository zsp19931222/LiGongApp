package com.yunhuakeji.app.utils;

import java.util.Map;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/**
 *
 * @author 云华科技
 * @date 2017年2月22日
 */
public class Home1ItemAdapter extends BaseAdapter
{
	private Map<Integer, View> viewMap;

	public Home1ItemAdapter(Map<Integer, View> viewMap)
	{
		// TODO Auto-generated constructor stub
		this.viewMap = viewMap;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return viewMap.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return viewMap.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return ((View) getItem(position)).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return (View) getItem(position);
	}
}