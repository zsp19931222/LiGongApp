package com.example.app3;

import android.view.View;

public class TitlePagerViewModel
{
	private View pager;
	private View title;

	public TitlePagerViewModel(View pager, View title)
	{
	    this.pager = pager;
	    this.title = title;
	}

	public View getPager()
	{
	    return pager;
	}

	public View getTitle()
	{
	    return title;
	}

}