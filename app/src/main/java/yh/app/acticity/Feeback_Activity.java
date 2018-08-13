package yh.app.acticity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.yhkj.cqgyxy.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具._链接地址导航;

public class Feeback_Activity extends IntegrationActivity {
	private RadioGroup rg_feedback_rdmanage;
	private String phonenumber;// 反馈电话号码
	private String grade = "非常满意";// 评分
	private String suggest = "";// 建议
	private EditText et_feedback_idea;// 建议编辑框
	private EditText et_feedback_phone;// 电话号码
	private Button btn_feedback_submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feed_back);
		initView();
		initProcessor();
	}

	public void back(View view) {
		finish();
	}

	/**
	 * 初始化数据
	 */
	private void initView() {
		rg_feedback_rdmanage = (RadioGroup) findViewById(R.id.rg_feedback_rdmanage);
		et_feedback_idea = (EditText) findViewById(R.id.et_feedback_idea);
		et_feedback_phone = (EditText) findViewById(R.id.et_feedback_phone);
		btn_feedback_submit = (Button) findViewById(R.id.btn_feedback_submit);
		btn_feedback_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				phonenumber = et_feedback_phone.getText().toString();
				suggest = et_feedback_idea.getText().toString();
				
				setSubmit();
			}
		});
	}

	private void initProcessor() {
		rg_feedback_rdmanage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				int rbId = group.getCheckedRadioButtonId();// 取得获取焦点的RadionButton控件Id
				RadioButton radioButton = (RadioButton) findViewById(rbId);// 获得相应的RadioButton控件
				grade = radioButton.getText().toString();
			}
		});
	}

	/**
	 * 提交反馈
	 */
	private void setSubmit() {

		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		map.put("phonenumber", phonenumber);
		map.put("degreeofsatisfaction", grade);
		map.put("suggestions", suggest);

		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.submitider.getUrl(), map, new VolleyInterface() {
			@Override
			public void onMySuccess(String result) {
				//
				
				try {
					JSONObject jsonObject = new JSONObject(result);
					Constants.ticket = jsonObject.getJSONObject("content").getString("ticket");
					
					if (jsonObject.getJSONObject("content").getBoolean("status")) {
						Toast.makeText(Feeback_Activity.this, R.string.str_feedback_succeed, Toast.LENGTH_SHORT).show();
						finish();
					} else {
						Toast.makeText(Feeback_Activity.this, R.string.str_feedback_error, Toast.LENGTH_SHORT).show();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(Feeback_Activity.this, R.string.str_feedback_error, Toast.LENGTH_SHORT).show();
			}
		});
	}
}
