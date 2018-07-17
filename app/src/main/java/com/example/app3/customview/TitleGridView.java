package com.example.app3.customview;

import android.content.Context;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;


import yh.app.appstart.lg.R;

import yh.app.tool.DpPx;

public class TitleGridView
{
    private TextView titleView;
    private GridView girdView;
    private View titleGirdView;
    private Context context;

    public TitleGridView(final Context context, View titleGirdView)
    {
	this.context = context;
	this.titleGirdView = titleGirdView;
	this.girdView = (GridView) titleGirdView.findViewById(R.id.gridview_home_item_gridview);
	this.titleView = (TextView) titleGirdView.findViewById(R.id.txt_home_item_titleview);
	girdView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
	{
	    @Override
	    public void onGlobalLayout()
	    {

		if (girdView.getNumColumns() == 0)
		{
		    TitleGridView.this.titleGirdView.setVisibility(View.GONE);
		    return;
		}
		if (girdView.getAdapter() == null)
		{
		    return;
		}
		int height = new DpPx(context).getDpToPx(90) * ((girdView.getAdapter().getCount() - 1) / girdView.getNumColumns() + 1);
		if (girdView.getHeight() == height)
		{
		    return;
		}
		LinearLayout.LayoutParams lp = (LayoutParams) girdView.getLayoutParams();
		lp.height = height;
		girdView.setLayoutParams(lp);

	    }
	});
    }

    public TitleGridView setTitle(String title)
    {
	titleView.setText(title);
	return this;
    }

    public TitleGridView setAdapter(BaseAdapter adapter)
    {
	girdView.setAdapter(adapter);
	return this;
    }

    public TitleGridView setOnItemViewClick(OnItemClickListener listener)
    {
	girdView.setOnItemClickListener(listener);
	return this;
    }

    public TitleGridView setNumColumns(int numColumns)
    {
	getGirdView().setNumColumns(numColumns);
	return this;
    }

    public GridView getGirdView()
    {
	return girdView;
    }

    public View getTitleGirdView()
    {
	return titleGirdView;
    }

    public void fresh(String title, boolean isShowRedPoint)
    {
	titleView.setText(title);
	if (isShowRedPoint)
	{
	    titleGirdView.findViewById(R.id.gridview_home_item_img_xyd).setVisibility(View.VISIBLE);
	} else
	{
	    titleGirdView.findViewById(R.id.gridview_home_item_img_xyd).setVisibility(View.INVISIBLE);
	}
    }
}