package yh.app.uiengine;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.Login;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;

import java.util.Timer;
import java.util.TimerTask;

import yh.app.acticity.VisitorApplyActivity;
import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.LoginAT;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.自定义控件.SQ_AdaptImageView;

/********************************************************
 * 包 名:yh.app.uiengine 类 名:yh.app.uiengine.Login 功 能:
 * 
 * @author 云华科技
 * @date 2015/7/28
 * @version 1.0
 *******************************************************/
public class Login1 extends ActivityPortrait {
	private TextView tishi, ykdl;
	private EditText edit_account;
	private EditText edit_code;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(this,Login.class);
		startActivity(intent);
		finish();
//		initActivity();
//		initView();
//		initDate();
//		initAction();
	}

	private void initDate() {
		SQLiteDatabase ddbb = null;
		Cursor cc = null;
		try {
			ddbb = new SqliteHelper().getWrite();
			cc = ddbb.rawQuery("select * from usertype", null);
			if (cc.moveToFirst())
				Constants.usertype = cc.getInt(1);
		} catch (Exception e) {
		} finally {
			try {
				new SqliteDBCLose(ddbb, cc).close();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void initAction() {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoginAT(mhandler, Login1.this, true).doLoginSec(edit_account.getText().toString(),
						edit_code.getText().toString());
			}
		});
		// 游客登陆
		ykdl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Constants.number = "yunhuakeji";
				Constants.code = "AEBB0392EE7E99A72AED9A7D0FD84337";
				new LoginAT(mhandler, Login1.this, true).doLoginSec(Constants.number, "551414");
//				if (!StringUtils.inString(Constants.xx, "nd", "yhkj")) {
//					new LoginAT(mhandler, Login1.this, true).doLoginSec(Constants.number, Constants.code);
//				} else {
//					
//				}
			}
		});

		edit_account.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit_code.clearFocus();
			}
		});
		edit_code.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				edit_account.clearFocus();
				edit_code.setFocusable(true);
			}
		});
	}

	private void initView() {
		edit_account = (EditText) findViewById(R.id.login_account_text);
		edit_code = (EditText) findViewById(R.id.login_code_text);
		ykdl = (TextView) findViewById(R.id.ykdl);
		edit_account.setSingleLine();
		edit_code.setSingleLine();
		edit_code.setInputType(0x81);
		tishi = (TextView) findViewById(R.id.login_tishi);
		button = (Button) findViewById(R.id.login_button);
	}

	private void initActivity() {
		// 去掉标题栏
		setContentView(R.layout.login_lg);
		SQ_AdaptImageView s = ((SQ_AdaptImageView) findViewById(R.id.login_logo));
		s.setBackgroundResource(R.drawable.xxdly);
	}

	/** 获取返回按键事件 */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 1000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finishActivity(0);
				finish();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler mhandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				_login();
			} else if(msg.what==2){
				Intent intent=new Intent(Login1.this,VisitorApplyActivity.class);
				startActivity(intent);
				Login1.this.overridePendingTransition(R.anim.activity_open,0);
			}else{
				tishi.setText(((String[]) msg.obj)[0].toString());
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						clear_text.sendMessage(new Message());
					}
				}, 2 * 1000);
			}
		}
	};
	private Handler clear_text = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			try {
				tishi.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};

	private void _login() {
		Intent intent = new Intent(this, home.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
}
