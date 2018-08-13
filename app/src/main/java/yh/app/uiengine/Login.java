package yh.app.uiengine;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import com.example.jpushdemo.ExampleApplication;

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
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.AT_old;import com.yhkj.cqgyxy.R;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.自定义控件.SQ_AdaptImageView;

/********************************************************
 * 包 名:yh.app.uiengine 类 名:yh.app.uiengine.Login 功 能:
 * 
 * @author 云华科技
 * @date 2015/7/28
 * @version 1.0
 *******************************************************/
public class Login extends ActivityPortrait
{
	private TextView tishi, ykdl;
	private EditText edit_account;
	private EditText edit_code;
	private Button button;
	private AT_old at;
	private int action = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initActivity();
		initView();
		initDate();
		initAction();
	}

	private void initDate()
	{
		SQLiteDatabase ddbb = null;
		Cursor cc = null;
		try
		{
			ddbb = new SqliteHelper().getWrite();
			cc = ddbb.rawQuery("select * from usertype", null);
			if (cc.moveToFirst())
				Constants.usertype = cc.getInt(1);
		} catch (Exception e)
		{
		} finally
		{
			try
			{
				new SqliteDBCLose(ddbb, cc).close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void initAction()
	{
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (Constants.isNetworkAvailable(Login.this)) {
					return;
				}
				boolean login = true;
				if (edit_account.getText().toString().equals(""))
				{
					tishi.setText("用户名不能为空");
					login = false;
					return;
				} else if (edit_code.getText().toString().equals("") && login)
				{
					tishi.setText("密码不能为空");
					login = false;
					return;
				} else if (!Constants.isNetworkAvailable(Login.this))
				{
					tishi.setText("网络异常");
					login = false;
					return;
				}
				Constants.number = edit_account.getText().toString(); // 将用户的学号写入静态变量
				Constants.code = edit_code.getText().toString();
				// Constants.code = MD5.MD5(edit_code.getText().toString()); //
				// 将用户密码写入静态变量
				if (login)
				{
					action = 1;
					at = new AT_old(action, Login.this, mhandler, true);
					at.executeOnExecutor(Executors.newCachedThreadPool(), Constants.code);
				}
			}
		});
		ykdl.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				action = 3;
				Constants.number = "yunhuakeji";
				Constants.code = "AEBB0392EE7E99A72AED9A7D0FD84337";
				at = new AT_old(action, Login.this, mhandler, false);
				at.executeOnExecutor(Executors.newCachedThreadPool(), Constants.number, "AEBB0392EE7E99A72AED9A7D0FD84337");
			}
		});

		edit_account.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				edit_code.clearFocus();
			}
		});
		edit_code.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				edit_account.clearFocus();
				edit_code.setFocusable(true);
			}
		});
	}

	private void initView()
	{
		edit_account = (EditText) findViewById(R.id.login_account_text);
		edit_code = (EditText) findViewById(R.id.login_code_text);
		// edit_account.setText("20141029");
		// edit_code.setText("123456");
		ykdl = (TextView) findViewById(R.id.ykdl);
		edit_account.setSingleLine();
		edit_code.setSingleLine();
		edit_code.setInputType(0x81);
		tishi = (TextView) findViewById(R.id.login_tishi);
		button = (Button) findViewById(R.id.login_button);
	}

	private void initActivity()
	{
		// 去掉标题栏
		setContentView(R.layout.login_lg);
		SQ_AdaptImageView s = ((SQ_AdaptImageView) findViewById(R.id.login_logo));
		s.setBackgroundResource(R.drawable.xxdly);
	}

	/** 获取返回按键事件 */
	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if ((System.currentTimeMillis() - exitTime) > 2000)
			{
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else
			{
				try
				{
					finish();
					home.homeAct.finish();
				} catch (Exception e)
				{
				}
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler mhandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				tishi.setText("网络异常,请重试");
				break;
			case 2:
				tishi.setText("用户名或密码错误,请重新输入");
				break;
			case 1:
				_login();
				break;
			}
			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					clear_text.sendMessage(new Message());
				}
			}, 3 * 1000);
		}
	};
	private Handler clear_text = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				tishi.setText("");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		};
	};

	private void _login()
	{
		if (action != 3)
		{
			SQLiteDatabase db1 = null;
			try
			{
				db1 = new SqliteHelper().getWrite();
				db1.execSQL("delete from userp");
				db1.execSQL("insert into userp values('" + Constants.number + "','" + RSAApi.getEncryptSecurity(Constants.code) + "')");
			} catch (Exception e)
			{
			} finally
			{
				new SqliteDBCLose(db1, null).close();
			}
		}
//		Intent intent = new Intent();
//		intent.setAction("com.example.app3.HomePageActivity");
//		intent.setPackage(ExampleApplication.getAppPackage());
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
		finish();
	}
}
