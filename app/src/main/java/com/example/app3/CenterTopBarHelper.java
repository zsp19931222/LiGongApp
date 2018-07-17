package com.example.app3;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import yh.app.appstart.lg.R;

public class CenterTopBarHelper
{
    private TextView titleView;
    private View left;

    public CenterTopBarHelper(final Context context)
    {
	titleView = (TextView) ((Activity) context).findViewById(R.id.topbar_txt_title);
	left = ((Activity) context).findViewById(R.id.topbar_left);
	left.setOnClickListener(new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		// TODO Auto-generated method stub
		((Activity) context).finish();
	    }
	});
    }
    
    public void setTitle(String title)
    {
	titleView.setText(title);
    }
}