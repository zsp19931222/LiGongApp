package com.example.app3.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;
import android.widget.ListView;
/**
 * 未完成
 * @author Administrator
 *
 */
public class DropDeleteListView extends ListView
{
    private Context context;

    public DropDeleteListView(Context context, AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
	this.context = context;
    }

    public DropDeleteListView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
	// TODO Auto-generated constructor stub
	this.context = context;
    }

    public DropDeleteListView(Context context)
    {
	super(context);
	// TODO Auto-generated constructor stub
	this.context = context;
    }

    @Override
    public void setAdapter(ListAdapter adapter)
    {
	// TODO Auto-generated method stub
	super.setAdapter(adapter);
    }

}
