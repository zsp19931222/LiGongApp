package yh.app.quanzi;

import org.json.JSONObject;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.quanzi.lxr.AddFriendHelper;import yh.app.appstart.lg.R;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class sftjhy extends ActivityPortrait implements OnClickListener
{
	private String fjxx;
	private String fqrid;
	private String fqrmc;
	private Button ty;
	private Button jj;
	private TextView name;
	private TextView fjxx_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quanzi_sftjhy);
		initView();
	}

	private void initView()
	{
		ty = (Button) findViewById(R.id.quanzi_sftjhy_ty);
		jj = (Button) findViewById(R.id.quanzi_sftjhy_jj);
		name = (TextView) findViewById(R.id.quanzi_sftjhy_name);
		fjxx_tv = (TextView) findViewById(R.id.quanzi_sftjhy_fjxx);
		Intent intent = getIntent();
		fqrid = intent.getStringExtra("friend_id");
		fjxx = intent.getStringExtra("message");
		fqrmc = intent.getStringExtra("name");
		name.setText(fqrmc);
		fjxx_tv.setText(fjxx);
		ty.setOnClickListener(this);
		jj.setOnClickListener(this);
	}

	private String sfty;

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.quanzi_sftjhy_ty:
			sfty = _社区_好友状态.正常.getCode() + "";
			break;
		case R.id.quanzi_sftjhy_jj:
			sfty = _社区_好友状态.不同意.getCode() + "";
			break;
		default:
			break;
		}
		setEffic(sfty, v);
		finish();
	}

	public void setEffic(String yn, View v)
	{
		v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
		v.getBackground().clearColorFilter();
		saveConstants(fqrid, fqrmc);
		new ThreadPoolManage().addPostTask(_链接地址导航.DC.是否同意添加好友.getUrl(), MapTools.buildMap(new String[][]
		{
				// 参数
				{
						"userid", Constants.number
				},
				{
						"ticket", Ticket.getFunctionTicket("20150120")
				},
				{
						"function_id", "20150120"
				},
				{
						"hybh", fqrid
				},
				{
						"hyzt", sfty
				}
		}), new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				try
				{
					if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean") && sfty.equals(_社区_好友状态.正常.getCode() + ""))
					{
						new AddFriendHelper().addFriend(fqrid, fqrmc, pinyin.getSpells(fqrmc), Constants.number);
						new ToastShow().show(sftjhy.this, "添加好友成功");
						return;
					} else if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean") && sfty.equals(_社区_好友状态.不同意.getCode() + ""))
					{
						new AddFriendHelper().addFriend(fqrid, fqrmc, pinyin.getSpells(fqrmc), Constants.number);
						new ToastShow().show(sftjhy.this, "拒绝成功");
						return;
					}
				} catch (Exception e)
				{
					new ToastShow().show(sftjhy.this, "数据异常");
				}
			}
		});
	}

	private void saveConstants(String fqr, String name)
	{
		try
		{
			SQLiteDatabase db = new SqliteHelper().getWrite();
			db.execSQL("insert into friend values('" + fqr + "','" + name + "','" + pinyin.getSpells(name) + "','" + Constants.number + "')");
			db.close();
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		return true;
	}

	public enum _社区_好友状态
	{
		正常
		{
			public int getCode()
			{
				return 1;
			}
		},
		不同意
		{
			public int getCode()
			{
				return 4;
			}
		};
		public abstract int getCode();
	}
}
