package yh.tool.widget;


import android.support.v4.app.FragmentActivity;

import com.yhkj.cqgyxy.R;

import yh.app.utils.StatusBarUtil;

public class IntegrationActivity extends FragmentActivity {
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		StatusBarUtil.setColor(this,
				getResources().getColor(R.color.button), 1);
	}
}
