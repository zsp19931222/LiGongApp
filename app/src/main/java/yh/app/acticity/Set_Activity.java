package yh.app.acticity;

import yh.app.uiengine.Password;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.yhkj.cqgyxy.R;

import yh.tool.widget.IntegrationActivity;

public class Set_Activity extends IntegrationActivity implements OnClickListener {

    private RelativeLayout rl_set_updatepwd;// 修改密码
    private RelativeLayout rl_set_feedback;// 意见反馈
    private RelativeLayout rl_set_about;// 关于

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    private void initView() {
        rl_set_updatepwd = (RelativeLayout) findViewById(R.id.rl_set_updatepwd);
        rl_set_updatepwd.setOnClickListener(this);

        rl_set_feedback = (RelativeLayout) findViewById(R.id.rl_set_feedback);
        rl_set_feedback.setOnClickListener(this);

        rl_set_about = (RelativeLayout) findViewById(R.id.rl_set_about);
        rl_set_about.setOnClickListener(this);
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onClick(View arg0) {
        // FIXME 农大修改密码写死
        switch (arg0.getId()) {
            case R.id.rl_set_updatepwd:
                Intent intentchengepwd = new Intent(this, Password.class);
                startActivity(intentchengepwd);
//			if ("nd".equals(Constants.xx))
//			{
//				
////				Intent intentchengepwd = new Intent(this, Web.class);
////				intentchengepwd.putExtra("url", "http://authserver.sicau.edu.cn/authserver/login");
////				startActivity(intentchengepwd);
//			} else
//			{
//				Intent intentchengepwd = new Intent(this, Password.class);
//				startActivity(intentchengepwd);
//			}
                break;
            case R.id.rl_set_feedback:
                Intent intentfeeback = new Intent(this, Feeback_Activity.class);
                startActivity(intentfeeback);
                break;
            case R.id.rl_set_about:
                Intent intentabout = new Intent(this, AboutActity.class);
                startActivity(intentabout);
                break;
        }
    }

}
