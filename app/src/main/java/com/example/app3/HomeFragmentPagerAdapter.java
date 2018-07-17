package com.example.app3;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class HomeFragmentPagerAdapter extends FragmentPagerAdapter
{
    private List<?> list;

    public HomeFragmentPagerAdapter(FragmentManager fm, List<?> list)
    {
	super(fm);
	// TODO Auto-generated constructor stub
	this.list = list;
    }

    @Override
    public Fragment getItem(int position)
    {
	return null;
    }

    @Override
    public int getCount()
    {
	return list != null ? list.size() : 0;
    }

}
