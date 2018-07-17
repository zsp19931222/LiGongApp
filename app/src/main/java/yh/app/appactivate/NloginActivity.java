package yh.app.appactivate;
//package  com.yhkj.cqswzyxy;
//
//import java.util.Properties;
//
//import org.androidpn.push.Constants;
//
//import yh.app.Visitor.Visitor;
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.db.MyImdb;
//import yh.app.tool.AT;
//import yh.app.tool.Updatetool;
//
//import  com.yhkj.cqswzyxy.R;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.app.AlertDialog;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.Menu;
//import android.view.View;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
///**
// * 类功能:主要负责登录界面的事件 登录成功,跳转到主页 不成功,指出错误信息 游客登录,
// */
//public class NloginActivity extends ActivityPortrait {
//	private AT mTask = null;
//	private Updatetool mUpdatetool;
//	private Properties props = null;
//	/** 用户名输入 */
//	EditText usename = null;
//	/** 密码输入 */
//	EditText password = null;
//	/** 登录按钮 */
//	Button button = null;
//	/** 忘记密码 */
//	TextView forgetps = null;
//	/** 游客浏览 */
//	TextView youke_liulan_tv = null;
//
//	Handler mhandler = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		 // 标题栏去除
//		setContentView(R.layout.nlogin);
//		props = loadProperties();
//
//		Constants.hostnumber = props.getProperty("xmppHost", "127.0.0.1");
//
//		Constants.hostnumber_push = props.getProperty("xmppHost_push", "");
//
//		Constants.newindexsurl = props.getProperty("newindexsurl", "");
//
//		Constants.newurl = props.getProperty("newurl", "");
//
//		Constants.newurllist = props.getProperty("newurllist", "");
//
//		Constants.scoreurl = props.getProperty("scoreurl", "");
//
//		Constants.curriculumurl = props.getProperty("curriculumurl", "");
//
//		Constants.loginurl = props.getProperty("loginurl", "");
//
//		Constants.tongxunluurl = props.getProperty("tongxunluurl", "");
//
//		Constants.lostFoundAddurl = props.getProperty("lostFoundAddurl", "");
//
//		Constants.lostFoundListurl = props.getProperty("lostFoundListurl", "");
//
//		Constants.libraryurl = props.getProperty("libraryurl", "");
//
////		Constants.login = this;
//
//		Constants.mydbmanager = new SqliteHelper("mydb.db", 21);
//
//		// mUpdatetool=new Updatetool(this,props.getProperty("updateAPPurl"));
//		// //初始化updatetool更新工具
//		//
//		// mUpdatetool.checkUpdateInfo();
//		// 检查是否有更新
//
//		// 查询是否有登录
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor result = db.rawQuery(
//				"select * from user where state='logined' ", null);
//		result.moveToFirst();
//		while (!result.isAfterLast()) {
//			Constants.number = result.getString(0);
//			Intent intent = new Intent();
//			intent.setClass(NloginActivity.this, MainActivity.class);
//			startActivity(intent);
//			result.moveToNext();
//		}
//		db.close();
//
//		forgetps = (TextView) findViewById(R.id.forgetpd);
//
//		youke_liulan_tv = (TextView) findViewById(R.id.youke_liulan_tv);
//
//		usename = (EditText) findViewById(R.id.username);
//
//		password = (EditText) findViewById(R.id.userpwd);
//
//		button = (Button) findViewById(R.id.login_button);
//
//		forgetps.setOnClickListener(new MyOnClickListener(1));
//		youke_liulan_tv.setOnClickListener(new MyOnClickListener(2));
//		button.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//
//				boolean login = true;
//				if (usename.getText().toString().equals("")) {
//					alert("错误ʾ", "用户名不能为空");
//					login = false;
//				}
//				if (password.getText().toString().equals("") && login) {
//					alert("错误ʾ", "密码不能为空");
//					login = false;
//				}
//				if (!Constants.isNetworkAvailable(NloginActivity.this)) {
//					alert("错误ʾ", "网络异常");
//					login = false;
//				}
//				if (login) {
//					Constants.number = usename.getText().toString();// 将用户的学号写入静态变量
//					mTask = new AT(0, NloginActivity.this, mhandler);
//					mTask.execute(Constants.number,
//							password.getText().toString(), 
//							Constants.getVersion(NloginActivity.this), 
//							Constants.getVersionCode(NloginActivity.this));
//				}
//			}
//		});
//		mhandler = new Handler() {
//			public void handleMessage(android.os.Message msg) {
//				super.handleMessage(msg);
//				switch (msg.what) {
//				case 1:
//					alert("错误ʾ","没有该用户");
//					break;
//				case 2:
//					alert("错误","用户名或密码错误");
//					break;
//				case 3:
//					Intent intent1 = new Intent();
//					intent1.setClass(NloginActivity.this, MainActivity.class);
//					startActivity(intent1);
//					break;
//				case 4:
//					alert("错误ʾ", "网络异常");
//					break;
//				}
//			}
//		};
//	}
//
//	private void alert(String title, String message) {
//		new AlertDialog.Builder(NloginActivity.this).setTitle(title)
//				.setMessage(message).setPositiveButton("提示", null).show();
//	}
//
//	/** 按钮事件监听 */
//	public class MyOnClickListener implements View.OnClickListener {
//		/**
//		 * 判断按下的按钮 1.忘记密码 2.游客浏览
//		 */
//		private int index = 0;
//
//		public MyOnClickListener(int i) {
//			index = i;
//		}
//
//		@Override
//		public void onClick(View v) {
//			if (index == 1) {
//				new AlertDialog.Builder(NloginActivity.this).setTitle("忘记密码 ？")
//						.setMessage("请登录网络教学中心找回密码")
//						.setPositiveButton("确定", null).show();
//			} else if (index == 2) {
//				Intent intent = new Intent();
//				intent.setClass(NloginActivity.this, Visitor.class);
//				startActivity(intent);
//			}
//		}
//	};
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.nlogin, menu);
//		return true;
//	}
//
//	private Properties loadProperties() {
//		Properties props = new Properties();
//		try {
//			int id = this.getResources().getIdentifier("androidpn", "raw",
//					this.getPackageName());
//			props.load(this.getResources().openRawResource(id));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return props;
//	}
//}
