package yh.app.acticity;


import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.fragment.KeChengQianDaoBaoGaoFragment;
import yh.app.fragment.KechengQiandao;
import yh.tool.widget.IntegrationActivity;

/**
 * 课程签到
 * 
 * @author anmin
 *
 */
@SuppressLint("InflateParams")
public class CourseSignActivity extends IntegrationActivity {
	// 课程签到课程报告
		private TextView txt_kechengqiandao, txt_qiandaobaogao;
		private KechengQiandao kechengqiandaoFragemnt;// 课程签到
		private KeChengQianDaoBaoGaoFragment kechengqiandaobaogaoFragment;// 签到报告
		private android.app.FragmentManager fragmentManager;
		private FragmentTransaction transaction;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.yh_home_course_activity);
			initView();
			initFragment(0);
		}

		public void initView() {

			// 课程签到
			txt_kechengqiandao = (TextView) findViewById(R.id.txt_kechengqiandao);

			// 签到报告
			txt_qiandaobaogao = (TextView) findViewById(R.id.txt_qiandaobaogao);

			txt_kechengqiandao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					onKeChengQianDaoClick();

				}
			});

			txt_qiandaobaogao.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub

					onQianDaoBaoGaoClick();
				}
			});

		}

		/**
		 * 签到报告点击事件
		 */
		private void onQianDaoBaoGaoClick() {
			txt_kechengqiandao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_blue));
			txt_kechengqiandao.setTextColor(getResources().getColor(R.color.color_white));
			txt_qiandaobaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_white));
			txt_qiandaobaogao.setTextColor(getResources().getColor(R.color.color_back));
			initFragment(1);
		}

		// 课程签到点击事件
		private void onKeChengQianDaoClick() {
			txt_qiandaobaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_blue));
			txt_kechengqiandao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_white));
			txt_qiandaobaogao.setTextColor(getResources().getColor(R.color.color_white));
			txt_kechengqiandao.setTextColor(getResources().getColor(R.color.color_back));
			initFragment(0);
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
				if (kechengqiandaoFragemnt == null) {
					kechengqiandaoFragemnt = new KechengQiandao();

					transaction.add(R.id.fl_message, kechengqiandaoFragemnt);
				} else {
					transaction.show(kechengqiandaoFragemnt);
				}
				break;
			case 1:
				if (kechengqiandaobaogaoFragment == null) {
					kechengqiandaobaogaoFragment = new KeChengQianDaoBaoGaoFragment();
					transaction.add(R.id.fl_message, kechengqiandaobaogaoFragment);
				} else {
					transaction.show(kechengqiandaobaogaoFragment);
				}
				break;
			}
			transaction.commit();
		}

		/***
		 * 隐藏Fragment
		 */
		private void hideFragment(FragmentTransaction transaction) {
			if (kechengqiandaoFragemnt != null) {
				transaction.hide(kechengqiandaoFragemnt);
			}
			if (kechengqiandaobaogaoFragment != null) {
				transaction.hide(kechengqiandaobaogaoFragment);
			}
		}

}
