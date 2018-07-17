package yh.app.quanzi;

import org.json.JSONArray;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdater_quanzi_home extends BaseAdapter
{
	@SuppressWarnings("unused")
	private LayoutInflater mInflater;
	private JSONArray mJSONArray;

	public MyAdater_quanzi_home(Context context, JSONArray mJSONArray)
	{
		this.mInflater = LayoutInflater.from(context);
		this.mJSONArray = mJSONArray;
	}

	// private MyAdater_quanzi_home()
	// {
	//
	// }

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return mJSONArray.length();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		// TODO Auto-generated method stub
		// 获取子item
		return null;
	}

}
