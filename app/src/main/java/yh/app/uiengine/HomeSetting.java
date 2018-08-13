package yh.app.uiengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import java.text.SimpleDateFormat;

import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.sina.weibo.sdk.demo.WBAuthActivity;
import 云华.智慧校园.功能.LoginOut;
import 云华.智慧校园.工具.PicTools;

@SuppressLint(
{
		"InflateParams", "SimpleDateFormat"
})
public class HomeSetting
{
	private LinearLayout layout;
	private static Context context;
	private String usertype;

	public HomeSetting(LinearLayout layout, Context context, String usertype)
	{
		HomeSetting.context = context;
		this.layout = layout;
		this.usertype = usertype;
	}

	private TextView xm;
	private static TextView nc;
	private TextView sr;
	private TextView bm;
	private TextView zy;
	private static TextView dh;
	private static TextView qq;
	private Button exit_account;
	private LinearLayout bjzl;
	private static ImageView tx_img;
	public static Bitmap tx;

	public void setting()
	{
			LayoutInflater inflater = LayoutInflater.from(context);
			View mView = inflater.inflate(R.layout.home_setting, null);
			exit_account = (Button) mView.findViewById(R.id.home_zxdl);
			initView(mView);
			initDate();
			initAction(mView);
			setTX();
			layout.addView(mView, android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
	}

	private void setTX()
	{
		new PicTools(context).setImageViewBackground(tx_img, "face");
	}

	public static void resultData(Intent data)
	{
		try
		{
			qq.setText(data.getStringExtra("qq"));
			dh.setText(data.getStringExtra("dh"));
			nc.setText(data.getStringExtra("nc"));
			tx_img.setImageBitmap((Bitmap) data.getParcelableExtra("img"));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void initAction(View mView)
	{
		exit_account.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new LoginOut().doLoginOut(context);
			}
		});
		//修改个人资料点击事件
		bjzl.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context,ChangeSetting.class);
				context.startActivity(intent);
			}
		});
		mView.findViewById(R.id.home_ewm).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context,QRCode.class);
				((Activity) context).startActivity(intent);
			}
		});
		mView.findViewById(R.id.sina_weibo).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(context,WBAuthActivity.class);
				((Activity) context).startActivity(intent);
			}
		});
	}

	private void initDate()
	{
		xm.setText(getDate("name"));
		Constants.name = xm.getText().toString();
		nc.setText(getDate("nc"));
		sr.setText(getDate("birthday"));
		bm.setText(getDate("bm"));
		zy.setText(getDate("zy"));
		dh.setText(getDate("telphone"));
		qq.setText(getDate("qq"));
	}

	private void initView(View mView)
	{
		xm = (TextView) mView.findViewById(R.id.home_xm);
		nc = (TextView) mView.findViewById(R.id.home_nc);
		sr = (TextView) mView.findViewById(R.id.home_sr);
		bm = (TextView) mView.findViewById(R.id.home_bm);
		zy = (TextView) mView.findViewById(R.id.home_zy);
		dh = (TextView) mView.findViewById(R.id.home_dh);
		qq = (TextView) mView.findViewById(R.id.home_qq);
		//修改个人资料
		bjzl = (LinearLayout) mView.findViewById(R.id.setting_edit);
		tx_img = (ImageView) mView.findViewById(R.id.tx_img);
	}

	private String getDate(String zd)
	{
		String date = "";
		try
		{
			SQLiteDatabase db = null;
			Cursor c = null;
			try
			{
				db = new SqliteHelper().getRead();
				c = db.rawQuery("select " + zd + " from user where userid='" + Constants.number + "'", null);
				while (c.moveToNext())
				{
					if (zd.equals("birthday"))
					{
						date = new SimpleDateFormat("yyyy年MM月dd日").format(new SimpleDateFormat("yyyyMMdd").parse(c.getString(0).toString()));
						String array[] = c.getString(0).toString().split("/");
						String n = array[0];
						String y = array[1];
						String r = array[2];
						date = n + "年" + y + "月" + r + "日";
					} else
						date = c.getString(0).toString();
				}
			} catch (Exception e)
			{
			} finally
			{
				try
				{
					new SqliteDBCLose(db, c).close();
				} catch (SQLException e)
				{
					e.printStackTrace();
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			if (date == null || date.equals("-"))
			{
				date = "";
			}
		} catch (Exception e)
		{
			date = "-";
		}
		return date;
	}
}
