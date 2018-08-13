package yh.app.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;import com.yhkj.cqgyxy.R;
import yh.app.utils.TopBarHelper.OnClickLisener;

public class DefaultTopBar
{
	private Context context;
	private TopBarHelper tbh;

	public DefaultTopBar(Context context)
	{
		this.context = context;
		tbh = new TopBarHelper(context, ((Activity) context).findViewById(R.id.topbar_layout));
	}

	public TopBarHelper doit(String name)
	{
		setOnClickListener();
		if (name != null && !name.equals(""))
			setName(name);
		return tbh;
	}

	private TopBarHelper setName(String name)
	{
		tbh.setTitle(name);
		return tbh;
	}

	private TopBarHelper setOnClickListener()
	{
		tbh.setOnClickLisener(new OnClickLisener()
		{
			@Override
			public void setRightOnClick()
			{
//				Intent intent = new Intent();
//				intent.setAction("com.example.app3.HomePageActivity");
//				intent.setPackage(context.getPackageName());
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);
				((Activity) context).finish();
			}

			@Override
			public void setLeftOnClick()
			{
				((Activity) context).finish();
			}

			@Override
			public void setExtraOnclick()
			{

			}
		});
		return tbh;
	}

}