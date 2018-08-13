package yh.app.acticity;


import android.os.Bundle;
import android.view.View;

import com.yhkj.cqgyxy.R;

import yh.tool.widget.IntegrationActivity;
/**
 * 修改密码
 * @author 云华科技
 * @date 2017年5月4日
 */
public class UpdataPasswordActivity extends IntegrationActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updatapassword);
	}
	public void back(View v){
		finish();
	}
}
