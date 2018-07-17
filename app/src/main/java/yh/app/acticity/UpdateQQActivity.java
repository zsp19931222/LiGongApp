package yh.app.acticity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import yh.app.appstart.lg.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具._链接地址导航;

public class UpdateQQActivity extends IntegrationActivity {

	TextView txtUserinfoSave;
	ClearEditText edtUserinfoQq;
	private String qq = null;
	


	private final int QQ_CODE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_qq);
		qq = getIntent().getStringExtra("qq");
		initView();
		initProcesso();
	}

	private void initView() {
		edtUserinfoQq = (ClearEditText) findViewById(R.id.edt_userinfo_qq);
		txtUserinfoSave = (TextView) findViewById(R.id.txt_userinfo_save);
	}

	/**
	 * 处理器
	 */
	private void initProcesso() {
		edtUserinfoQq.setText(qq);
		txtUserinfoSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				qq = edtUserinfoQq.getText().toString();
				if (!qq.isEmpty()) {
					submitNickName(qq);
				} else {
					edtUserinfoQq.setShakeAnimation();
					Toast.makeText(UpdateQQActivity.this,"不能为空", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	/**
	 * 提交修改QQ 提交数据
	 */
	private void submitNickName(final String qq) {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		map.put("QQ", qq);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.updataQQ.getUrl(),map,new VolleyInterface() {
					@Override
					public void onMySuccess(String result) {
						// 成功
						Intent intentqq = new Intent();
						intentqq.putExtra("qq",qq);
						setResult(QQ_CODE, intentqq);
						finish();
					}

					@Override
					public void onMyError(VolleyError error) {
						// 失败
						Toast.makeText(UpdateQQActivity.this,"修改失败", Toast.LENGTH_SHORT).show();
					}
				});
	}

	/**
	 * 退出当前
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	
}
