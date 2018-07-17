package yh.app.tool;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * vatty *
 *
 * hongshengpeng.com
 * 
 */
/**
 * 
 * 包	名:yh.app.tool
 * 类	名:MyFragmentPagerAdapter.java
 * 功	能:自定义FragmentPager适配器
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	private ArrayList<Fragment> fragmentsList;

	public MyFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	public MyFragmentPagerAdapter(FragmentManager fm,
			ArrayList<Fragment> fragments) {
		super(fm);
		this.fragmentsList = fragments;
	}

	@Override
	public int getCount() {
		return fragmentsList.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragmentsList.get(arg0);
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

}
