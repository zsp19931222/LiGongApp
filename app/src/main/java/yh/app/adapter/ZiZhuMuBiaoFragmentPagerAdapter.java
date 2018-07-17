package yh.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by liufatao on 2017/3/13.
 */

public class ZiZhuMuBiaoFragmentPagerAdapter extends FragmentPagerAdapter {
     private List<Fragment> fragmentList;
    public ZiZhuMuBiaoFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
