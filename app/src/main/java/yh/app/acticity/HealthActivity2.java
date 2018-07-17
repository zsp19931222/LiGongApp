package yh.app.acticity;

import com.yunhuakeji.app.R;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import yh.app.fragment.JinRiBuShuFragement;
import yh.app.fragment.YunDongBaoGaoFragment;
/**
 * 坚持运动
 * @author anmin
 *
 */
@SuppressLint("HandlerLeak")
public class HealthActivity2 extends YhActivity
{

	private TextView txt_jinribushu,txt_jiankangbaogao;
	private JinRiBuShuFragement jinribushuFragment;
	private YunDongBaoGaoFragment yundongbaogaoFragment;
	private android.app.FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	@Override
	protected void initActivity() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_jiankangyundong);
		initFragment(0);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub
		txt_jiankangbaogao=(TextView) findViewById(R.id.txt_jiankangbaogao);
		txt_jinribushu=(TextView) findViewById(R.id.txt_jinribushu);
		
		txt_jiankangbaogao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onJianKangDaoBaoGaoClick();
			}
		});
		
		txt_jinribushu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onJinRiBuShuQianDaoClick();
			}
		});
		
	}
	/**
	 * 健康报告点击事件
	 */
	private void onJianKangDaoBaoGaoClick() {
		txt_jinribushu.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_blue));
		txt_jinribushu.setTextColor(getResources().getColor(R.color.color_white));
		txt_jiankangbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_white));
		txt_jiankangbaogao.setTextColor(getResources().getColor(R.color.color_back));
		initFragment(1);
	}

	/**
	 *  今日步数签到点击事件
	 */
	private void onJinRiBuShuQianDaoClick() {
		txt_jiankangbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_blue));
		txt_jiankangbaogao.setTextColor(getResources().getColor(R.color.color_white));
		
		txt_jinribushu.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_white));
		txt_jinribushu.setTextColor(getResources().getColor(R.color.color_back));
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
			if (jinribushuFragment == null) {
				//传递步数
				jinribushuFragment = new JinRiBuShuFragement();

				transaction.add(R.id.fl_message, jinribushuFragment);
			} else {
				transaction.show(jinribushuFragment);
			}
			break;
		case 1:
			if (yundongbaogaoFragment == null) {
				yundongbaogaoFragment = new YunDongBaoGaoFragment();
				transaction.add(R.id.fl_message, yundongbaogaoFragment);
			} else {
				transaction.show(yundongbaogaoFragment);
			}
			break;
		}
		transaction.commit();
	}

	/***
	 * 隐藏Fragment
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (jinribushuFragment != null) {
			transaction.hide(jinribushuFragment);
		}
		if (yundongbaogaoFragment != null) {
			transaction.hide(yundongbaogaoFragment);
		}
	}
	
}