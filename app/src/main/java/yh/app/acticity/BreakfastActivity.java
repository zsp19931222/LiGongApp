package yh.app.acticity;



import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.fragment.JianKangZaoChanFragment;
import yh.app.fragment.ZaoCanBaoGaoFragment;

/**
 *健康早餐
 * 
 * @author anmin
 *
 */
@SuppressLint("HandlerLeak")
public class BreakfastActivity extends YhActivity {

	private TextView txt_zaocanqiandao,txt_zaocanbaogao;
	private ZaoCanBaoGaoFragment zaocanbaogaoFragment;
	private JianKangZaoChanFragment jiankanzaocanFragment;
	private android.app.FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	@Override
	protected void initActivity() {
		// TODO Auto-generated method stub
		setContentView(R.layout.yh_home_breakfast_activity);
		initFragment(0);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		txt_zaocanbaogao=(TextView) findViewById(R.id.txt_zaocanbaogao);
		txt_zaocanqiandao=(TextView) findViewById(R.id.txt_zaocanqiandao);
		
		txt_zaocanbaogao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onQianDaoBaoGaoClick();
			}
		});
		
		txt_zaocanqiandao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onKeChengQianDaoClick();
			}
		});
		
	}
	/**
	 * 早餐报告点击事件
	 */
	private void onQianDaoBaoGaoClick() {
		txt_zaocanqiandao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_blue));
		txt_zaocanqiandao.setTextColor(getResources().getColor(R.color.color_white));
		txt_zaocanbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_white));
		txt_zaocanbaogao.setTextColor(getResources().getColor(R.color.color_back));
		initFragment(1);
	}

	/**
	 *  早餐签到点击事件
	 */
	private void onKeChengQianDaoClick() {
		txt_zaocanbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_blue));
		txt_zaocanbaogao.setTextColor(getResources().getColor(R.color.color_white));
		
		txt_zaocanqiandao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_white));
		txt_zaocanqiandao.setTextColor(getResources().getColor(R.color.color_back));
		initFragment(0);
	}
	@Override
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initAction() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 根据游标切换相应的界面
	 */
	private void initFragment(int i) {
		fragmentManager = this.getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			if (jiankanzaocanFragment == null) {
				jiankanzaocanFragment = new JianKangZaoChanFragment();

				transaction.add(R.id.fl_message, jiankanzaocanFragment);
			} else {
				transaction.show(jiankanzaocanFragment);
			}
			break;
		case 1:
			if (zaocanbaogaoFragment == null) {
				zaocanbaogaoFragment = new ZaoCanBaoGaoFragment();
				transaction.add(R.id.fl_message, zaocanbaogaoFragment);
			} else {
				transaction.show(zaocanbaogaoFragment);
			}
			break;
		}
		transaction.commit();
	}

	/***
	 * 隐藏Fragment
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (jiankanzaocanFragment != null) {
			transaction.hide(jiankanzaocanFragment);
		}
		if (zaocanbaogaoFragment != null) {
			transaction.hide(zaocanbaogaoFragment);
		}
	}
	
}