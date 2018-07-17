package yh.app.acticity;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import yh.app.appstart.lg.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * 修改用户昵称
 */
public class UpdateNickNameActivity extends IntegrationActivity {

    TextView txtUserinfoSave;
   
    ClearEditText edtUserinfoNickname;
    String nickname = null;
    private final int NICKNAME_CODE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_nickname);
        nickname = getIntent().getStringExtra("nickename");
        initView();
        initProcesso();
    }
 private void initView(){
	 txtUserinfoSave=(TextView) findViewById(R.id.txt_userinfo_save);
	 edtUserinfoNickname=(ClearEditText) findViewById(R.id.edt_userinfo_nickname);
 }
    /**
     * 处理器
     */
    private void initProcesso() {
        edtUserinfoNickname.setText(nickname);
        txtUserinfoSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  nickname = edtUserinfoNickname.getText().toString();
				  Toast.makeText(UpdateNickNameActivity.this, nickname, Toast.LENGTH_SHORT).show();
	                if (!nickname.isEmpty()) {
	                    submitNickName(nickname);
	                } else {
	                    edtUserinfoNickname.setShakeAnimation();
	                    Toast.makeText(UpdateNickNameActivity.this,"不能为空",Toast.LENGTH_SHORT).show();
	                }
			}
		});
    }

    /**
     * 提交修改昵称
     * 提交数据
     */
    private void submitNickName(final String nickname) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        map.put("ticket", Constants.ticket);
        map.put("petname", nickname);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.updataNickName.getUrl(), map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
//             成功
            	Toast.makeText(UpdateNickNameActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Log.e("修改昵称",result);
                Intent intentnickname=new Intent(UpdateNickNameActivity.this,UserInfoActivity.class);
                intentnickname.putExtra("nickename",nickname);
                setResult(NICKNAME_CODE,intentnickname);
                finish();
            }

            @Override
            public void onMyError(VolleyError error) {
            	//请求失败
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
