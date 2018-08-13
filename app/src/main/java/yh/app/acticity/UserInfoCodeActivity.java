package yh.app.acticity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.tool.MD5;
import yh.app.tool.Ticket;
import yh.app.utils.CountDownUtil;
import yh.app.utils.Utils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

public class UserInfoCodeActivity extends WhiteActivity implements View.OnClickListener {

	private ClearEditText ctUserinfocodePhonenumber;
	private TextView txtUserinfocodeSendcode;
	private ClearEditText ctUserinfocodeCode;
	private Button btnDatainputNext;
	private TextView txtBack;
	private String phonenumber;
	private String yzm;
	private LoadDiaog diaog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info_code);
		
		initView();
	}

	
	
	private void initView() {
		ctUserinfocodePhonenumber = (ClearEditText) findViewById(R.id.ct_userinfocode_phonenumber);
		txtUserinfocodeSendcode = (TextView) findViewById(R.id.txt_userinfocode_sendcode);
		txtUserinfocodeSendcode.setOnClickListener(this);
		ctUserinfocodeCode = (ClearEditText) findViewById(R.id.ct_userinfocode_code);
		btnDatainputNext = (Button) findViewById(R.id.btn_datainput_next);
		btnDatainputNext.setOnClickListener(this);
		txtBack = (TextView) findViewById(R.id.txt_back);
		txtBack.setOnClickListener(this);
		diaog=new LoadDiaog(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.txt_userinfocode_sendcode:
//			setPhoneNumber();
			sendCode();

			break;
		case R.id.btn_datainput_next:
			jianyanCode();
//			startActivity(new Intent(UserInfoCodeActivity.this, DataInputActivity.class));

			break;
		case R.id.txt_back:
			finish();
			break;
		}
	}

	/**
	 * 发送验证码
	 */
	private void sendCode() {
		phonenumber = ctUserinfocodePhonenumber.getText().toString();
		if (TextUtils.isEmpty(phonenumber)) {
			ctUserinfocodePhonenumber.setShakeAnimation();
			Toast.makeText(UserInfoCodeActivity.this, "电话号码不能未空",Toast.LENGTH_SHORT).show();
			return;
		}
		diaog.show();
		String ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", ticket);
		params.put("mob", phonenumber);
		VolleyRequest.RequestPost("http://mv2.app.cqut.edu.cn/TicketService/sms/send2SMS", params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						new CountDownUtil(UserInfoCodeActivity.this, 60 * 1000, 1 * 1000, "重新发送验证码",
								txtUserinfocodeSendcode).start();
						
						txtUserinfocodeSendcode.setTextColor(getResources().getColor(R.color.color_gray));
						dismess();
					}else{
						Toast.makeText(UserInfoCodeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
						dismess();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					dismess();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				dismess();
			}
		});
	}

	/**
	 * 检验验证
	 */
	private void jianyanCode() {
		yzm = ctUserinfocodeCode.getText().toString();
		if (TextUtils.isEmpty(yzm)) {
			ctUserinfocodeCode.setShakeAnimation(); 
			Toast.makeText(UserInfoCodeActivity.this, "验证码不能未空",Toast.LENGTH_SHORT).show();
			return;
		}
		String ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", ticket);
		params.put("yzm", yzm);
		params.put("mob", phonenumber);
	
		VolleyRequest.RequestPost("http://mv2.app.cqut.edu.cn/TicketService/sms/check2SMS", params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						Toast.makeText(UserInfoCodeActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
						String time=Utils.getTodayDate();
						ExampleApplication.getInstance().getSqliteHelper().execSQL("replace into rxzbgn(userid,tagid,gndate,endtime,staretime) values(?,?,?,?,?)",
								new Object[] { Constants.number, null, time,null,null });
						
						startActivity(new Intent(UserInfoCodeActivity.this, DataInputActivity.class));
						 
						
						finish();
					}else{
						Toast.makeText(UserInfoCodeActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
						dismess();
					}
				} catch (JSONException e) {
 					e.printStackTrace();
 					dismess();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(UserInfoCodeActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
				dismess();
			}
		});
	}
	
	
	

	private void dismess(){
		if (diaog.isShowing()) {
			diaog.dismiss();
		}
	}
	/**
	 * 采集电话号码
	 */
	private void setPhoneNumber(){
		
		String ticket = Ticket.getFunctionTicket("20170601");
		
		phonenumber = ctUserinfocodePhonenumber.getText().toString();
		if (TextUtils.isEmpty(phonenumber)) {
			ctUserinfocodePhonenumber.setShakeAnimation();
			Toast.makeText(UserInfoCodeActivity.this, "电话号码不能未空",Toast.LENGTH_SHORT).show();
			return;
		}
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", ticket);
		params.put("params", phonenumber);
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.UPDATAPHONENUMBER.getUrl(), params, new VolleyInterface() {
			
			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						sendCode();
					}else{
						Toast.makeText(UserInfoCodeActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
						dismess();
					}
				} catch (JSONException e) {
 					e.printStackTrace();
				}
			}
			
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(UserInfoCodeActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
				dismess();
			}
		});
	}
}
