package com.example.app3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.example.app3.base.BaseActivity;
import com.example.app4.activity.MainActivity;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;

import yh.app.logTool.Log;
import yh.app.tool.DpPx;
import yh.app.tool.LoginAT;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具._链接地址导航;

@SuppressLint("HandlerLeak")
public class Login extends BaseActivity implements View.OnClickListener, OnEditorActionListener {

    private EditText userid_et, code_et;
    private TextView guest, error;
    private ImageView userid_clear, pwd_clear, logo;

    private Button login;

    private String childSecret = null;
    private String appid = null;
    private String userid = null;

    @Override
    protected void initView() {
        userid_et = findViewById(R.id.login_txt_userid);
        code_et = findViewById(R.id.login_txt_code);
        guest = findViewById(R.id.login_txt_guest);
        error = findViewById(R.id.login_txt_error);
        login = findViewById(R.id.login_btn_login);
        userid_clear = findViewById(R.id.login_img_userid_clear);
        pwd_clear = findViewById(R.id.login_img_pwd_clear);
        logo = findViewById(R.id.login_img_logo);
    }

    @Override
    protected void initActivityView() {
        setContentView(R.layout.activity_login);
        // setContentView(R.layout.activity_myself_feedback);
    }

    @Override
    protected void initData() {
        logo.setImageBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.getBitmap(this, R.drawable.xxtb), new DpPx(this).getDpToPx(12)));

        userid = getIntent().getStringExtra("userid");
        childSecret = getIntent().getStringExtra("childSecret");
        appid = getIntent().getStringExtra("appid");
    }

    @Override
    protected void initAction() {
        login.setOnClickListener(this);
        guest.setOnClickListener(this);
        userid_clear.setOnClickListener(this);
        pwd_clear.setOnClickListener(this);

        userid_et.setOnEditorActionListener(this);
        code_et.setOnEditorActionListener(this);

        if (IsNull.isNotNull(userid, appid, childSecret)) {
            new LoginAT(handler, this, true).doLoginOther(userid, appid, childSecret);
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
//                Intent intent = new Intent(HomePageActivity.class.getName());
                Intent intent=new Intent(Login.this,MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                errorTextUtil(((String[]) msg.obj)[0], 2 * 1000);
            }
        }
    };

    public Handler handler_error = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            error.setText("");
        }
    };

    private void errorTextUtil(String msg, int keepTime) {
        try {
            error.setText(msg);
        } catch (Exception e) {
            error.setText("账号或密码错误");
        }
        handler_error.sendEmptyMessageDelayed(0, keepTime);
    }

    private void doLogin() {
        String user = userid_et.getText() != null ? userid_et.getText().toString() : "", pwd = code_et.getText() != null ? code_et.getText().toString() : "";
        if (!IsNull.isNotNull(user, pwd)) {
            errorTextUtil(this.getResources().getString(R.string.error_userid_and_pw_not_empty), 2 * 1000);
        } else {
            Log.d("zsp", _链接地址导航.UIA.登录2.getUrl());
            LoginAT at = new LoginAT(handler, this, true);
            at.doLoginSec(user, pwd);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                doLogin();
                break;

            case R.id.login_txt_guest:
                Intent intent_guest = new Intent();
//                intent_guest.setAction(HomePageActivity.class.getName());
                intent_guest.setAction(MainActivity.class.getName());
                startActivity(intent_guest);
                Constants.number = new SqliteHelper().rawQuery("select * from usertype").get(0).get("userid");
                Constants.code = MD5.MD5("123456");
                finish();
                break;
            case R.id.login_img_userid_clear:
                userid_et.setText("");
                break;
            case R.id.login_img_pwd_clear:
                code_et.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        switch (v.getId()) {
            case R.id.login_txt_userid:
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    code_et.setFocusable(true);
                    code_et.setFocusableInTouchMode(true);
                    code_et.requestFocus();
                }
                break;
            case R.id.login_txt_code:
                if (actionId == EditorInfo.IME_ACTION_SEND || actionId == EditorInfo.IME_ACTION_DONE || event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
                    doLogin();
                }
                break;

            default:
                break;
        }
        return true;
    }
}