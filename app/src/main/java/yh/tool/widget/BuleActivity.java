package yh.tool.widget;

import android.app.Activity;

import com.yhkj.cqgyxy.R;

import yh.app.utils.StatusBarUtil;

public class BuleActivity extends Activity {
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		StatusBarUtil.setColor(this,
				getResources().getColor(R.color.color_bleu), 1);
	}
}
