package yh.app.acticity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;


import yh.app.appstart.lg.R;

import yh.app.fragment.AnShiGuiQingFragment;
import yh.app.fragment.GuiQinBaoGaoFragment;
import yh.tool.widget.IntegrationActivity;


public class AnShiGuiQingActivity extends IntegrationActivity {
	private TextView txt_anshiguiqing,txt_guiqingbaogao;
	private AnShiGuiQingFragment anshiguiqingFragment;
	private GuiQinBaoGaoFragment guiqingbaogaoFragment;
	private android.app.FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anshiguiqing);
		initView();
		initFragment(0);
	}
	
	private void initView(){
		txt_guiqingbaogao=(TextView) findViewById(R.id.txt_guiqingbaogao);
		txt_anshiguiqing=(TextView) findViewById(R.id.txt_anshiguiqing);
		
		txt_guiqingbaogao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onQianDaoBaoGaoClick();
			}
		});
		
		txt_anshiguiqing.setOnClickListener(new OnClickListener() {
			
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
		txt_anshiguiqing.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_blue));
		txt_anshiguiqing.setTextColor(getResources().getColor(R.color.color_white));
		txt_guiqingbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_white));
		txt_guiqingbaogao.setTextColor(getResources().getColor(R.color.color_back));
		initFragment(1);
	}

	/**
	 *  早餐签到点击事件
	 */
	private void onKeChengQianDaoClick() {
		txt_guiqingbaogao.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_right_blue));
		txt_guiqingbaogao.setTextColor(getResources().getColor(R.color.color_white));
		
		txt_anshiguiqing.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_radius_border_left_white));
		txt_anshiguiqing.setTextColor(getResources().getColor(R.color.color_back));
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
			if (anshiguiqingFragment == null) {
				anshiguiqingFragment = new AnShiGuiQingFragment();

				transaction.add(R.id.fl_message, anshiguiqingFragment);
			} else {
				transaction.show(anshiguiqingFragment);
			}
			break;
		case 1:
			if (guiqingbaogaoFragment == null) {
				guiqingbaogaoFragment = new GuiQinBaoGaoFragment();
				transaction.add(R.id.fl_message, guiqingbaogaoFragment);
			} else {
				transaction.show(guiqingbaogaoFragment);
			}
			break;
		}
		transaction.commit();
	}

	/***
	 * 隐藏Fragment
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (anshiguiqingFragment != null) {
			transaction.hide(anshiguiqingFragment);
		}
		if (guiqingbaogaoFragment != null) {
			transaction.hide(guiqingbaogaoFragment);
		}
	}
}
