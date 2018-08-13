package yh.app.acticity;

import java.util.ArrayList;
import java.util.List;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.adapter.ZiZhuMuBiaoFragmentPagerAdapter;
import yh.app.fragment.CompletedFragment;
import yh.app.fragment.UnderwayFragment;

/**
 * 更多自主目标
 * 
 * @author 云华科技
 * @date 2017年4月28日
 */
public class ZiZhuMuBiaoMoreActivity extends FragmentActivity implements OnClickListener {
	private ZiZhuMuBiaoFragmentPagerAdapter pageadapter;
	private List<Fragment> fragmentlist;
	private CompletedFragment completedfragment;
	private UnderwayFragment underwayfragment;
	private ViewPager vp_mubiao;

	private TextView txt_underway;// 进行中
	private TextView txt_completed;// 已完成

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zizhumubiaomore);


		initoView();
		initProcessor();
	}

	private void initoView() {
		vp_mubiao = (ViewPager) findViewById(R.id.vp_mubiao);
		txt_underway = (TextView) findViewById(R.id.txt_underway);
		txt_underway.setOnClickListener(this);
		txt_completed = (TextView) findViewById(R.id.txt_completed);
        txt_completed.setOnClickListener(this);
        
		completedfragment = new CompletedFragment();
		underwayfragment = new UnderwayFragment();
		
		fragmentlist = new ArrayList<>();
		fragmentlist.add(completedfragment);
		fragmentlist.add(underwayfragment);
		
		vp_mubiao.setOffscreenPageLimit(2);// 设置缓存多少个
		pageadapter = new ZiZhuMuBiaoFragmentPagerAdapter(getSupportFragmentManager(), fragmentlist);
		vp_mubiao.setAdapter(pageadapter);
		vp_mubiao.setCurrentItem(0);// 默认显示页

	}
	/**
	 * 退出当前
	 * @param v
	 */
	public void back(View v){
		finish();
	}
	
	private void ViewPager(int i) {
		initDefault();
		switch (i) {
		case 0:
			// 进行中
			txt_underway.setTextColor(this.getResources().getColor(R.color.button));
			vp_mubiao.setCurrentItem(0);// 默认显示页
			break;
		case 1:
			// 完成
			txt_completed.setTextColor(getResources().getColor(R.color.button));
			vp_mubiao.setCurrentItem(1);// 默认显示页
			break;
		
		}
	}
    /**
     * 分页处理
     */
	private void initProcessor() {

		vp_mubiao.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				ViewPager(arg0);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	/**
	 * 默认值
	 */
	private void initDefault() {
		txt_underway.setTextColor(getResources().getColor(R.color.color_back));
		txt_completed.setTextColor(getResources().getColor(R.color.color_back));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		initDefault();
		switch (v.getId()) {
		case R.id.txt_underway:
			txt_underway.setTextColor(getResources().getColor(R.color.button));
			vp_mubiao.setCurrentItem(0);// 默认显示页
			break;

		case R.id.txt_completed:
			txt_completed.setTextColor(getResources().getColor(R.color.button));
			vp_mubiao.setCurrentItem(1);// 默认显示页
			break;

		default:
			break;
		}
	}

}
