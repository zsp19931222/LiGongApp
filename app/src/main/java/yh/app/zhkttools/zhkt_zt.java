package yh.app.zhkttools;

import org.json.JSONArray;

import android.content.Context;
import android.view.View;

public class zhkt_zt
{
	private Context context;
	private JSONArray jsonArray;
	private View v;

	public zhkt_zt(Context context, View v, JSONArray jsonArray)
	{
		this.context = context;
		this.jsonArray = jsonArray;
		this.v = v;
	}
	
	public zhkt_zt_interface getZTInterface()
	{
		return new zhkt_zt_interface()
		{
			
			@Override
			public void xszt()
			{
			}
			
			@Override
			public void jsztxs()
			{
			}
			
			@Override
			public void jszt()
			{
				
			}
		};
	}

}
