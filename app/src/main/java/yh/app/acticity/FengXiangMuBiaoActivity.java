package yh.app.acticity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import java.util.HashMap;
import java.util.Map;

import yh.app.tool.Devicename_Tool;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 分享目标
 * 
 * @author anmin
 *
 */
public class FengXiangMuBiaoActivity extends IntegrationActivity {
	private EditText et_mubiao_content;// 提交内容
	private Button btn_fengxiang_mubiao;// 提交按钮
	private String targetID;
	private String shar_content;
	private String Devicename;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fengxiangmubiao);
		initView();
	}

	/*
	 * 初始化视图
	 */
	private void initView() {
		targetID=getIntent().getStringExtra("targetID");
		et_mubiao_content = (EditText) findViewById(R.id.et_mubiao_content);
		btn_fengxiang_mubiao = (Button) findViewById(R.id.btn_fengxiang_mubiao);
		//获得设备名称
		Devicename=Devicename_Tool.getDevicenameBrand()+" "+Devicename_Tool.getDevicenameModel();
		btn_fengxiang_mubiao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
            shar_content=et_mubiao_content.getText().toString();
            
            if (!TextUtils.isEmpty(shar_content)) {
            	submiet(shar_content);
			}else{
				Toast.makeText(FengXiangMuBiaoActivity.this, R.string.str_null, Toast.LENGTH_SHORT).show();
			}
            
			}
		});
	}
	/**
	 * 提交分享目标
	 */
	private void submiet(String content){
		Map<String, String> params=new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		params.put("username", Constants.name);
		params.put("targetID", targetID);
		params.put("Devicename", Devicename);
		params.put("content", content);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.FengXinagMuBiao.getUrl(), params, new VolleyInterface() {
			
			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				Toast.makeText(FengXiangMuBiaoActivity.this, R.string.str_fengxiang_chenggong, Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(FengXiangMuBiaoActivity.this, R.string.str_fengxiang_shibai, Toast.LENGTH_SHORT).show();
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
