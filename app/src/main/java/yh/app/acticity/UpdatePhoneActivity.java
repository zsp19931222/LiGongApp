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
import yh.app.tool.StringUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具._链接地址导航;

public class UpdatePhoneActivity extends IntegrationActivity {

    TextView txtUserinfoSave;
    ClearEditText edtUserinfoPhonenumber;
    private String phonenumber = null;

    private final int PHONENUMBER_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        phonenumber = getIntent().getStringExtra("phonenumber");
        initView();
        initProcesso();
        
    }
  private void  initView(){
	  txtUserinfoSave=(TextView) findViewById(R.id.txt_userinfo_save);
	  edtUserinfoPhonenumber=(ClearEditText) findViewById(R.id.edt_userinfo_phonenumber);
  }
    /**
     * 处理器
     */
    private void initProcesso() {
        edtUserinfoPhonenumber.setText(phonenumber);
        txtUserinfoSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  phonenumber = edtUserinfoPhonenumber.getText().toString();
	                if (StringUtils.isPhoneNumber(phonenumber)){
	                    submitNickName(phonenumber);
	                }else {
	                    //晃动窗体
	                    edtUserinfoPhonenumber.setShakeAnimation();
	                    Toast.makeText(UpdatePhoneActivity.this,"电话格式错误",Toast.LENGTH_SHORT).show();
	                }
			}
		});
    }

    /**
     * 提交修改电话号码
     * 提交数据
     */
    private void submitNickName(final String phonenumber) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        map.put("ticket", Constants.ticket);
        map.put("phonenumber", phonenumber);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.updataPhoneNumber.getUrl(), map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
            	System.out.print(result);
//              成功
                Intent intentphonenumber=new Intent();
                intentphonenumber.putExtra("phonenumber",phonenumber);
                setResult(PHONENUMBER_CODE,intentphonenumber);
                finish();
            }
            @Override
            public void onMyError(VolleyError error) {
//                      失败
            	Toast.makeText(UpdatePhoneActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
            	  
            }
        });
    }
    
  

    /**
     * 退出当前
     * @param view
     */
    public void back(View view) {
        finish();
    }

   
}
