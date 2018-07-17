package yh.app.mydiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;import yh.app.appstart.lg.R;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class edit_diary extends ActivityPortrait
{
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.diary_edit);
		context = this;
		show();
	}

	/** 显示当月天 */
	private TextView day_tv;
	/** 显示星期几 */
	private TextView week_tv;
	/** 显示年月 */
	private TextView ym_tv;
	/** 日志内容 */
	private EditText content_tv;
	/** 返回按钮 */
	private ImageView back;
	/** 显示的日期 */
	private String date = "";
	/** 操作类型 */
	private String type;

	/** 绘制界面 */
	private void show()
	{
		final SQLiteDatabase db = new SqliteHelper().getWrite();
		new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle("编辑日志").setOnClickLisener(new OnClickLisener()
		{

			@Override
			public void setRightOnClick()
			{
//				Intent intent = new Intent();
//				intent.setAction("com.example.app3.HomePageActivity");
//				intent.setPackage(context.getPackageName());
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);
				((Activity) context).finish();
			}

			@Override
			public void setLeftOnClick()
			{
				safeDiary();
				finish();
			}

			@Override
			public void setExtraOnclick()
			{
				// TODO Auto-generated method stub

			}
		});
		// back = (ImageView) findViewById(R.id.diary_back_edit);
		// back.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v)
		// {
		// safeDiary();
		// finish();
		// }
		// });
		// 获取要显示的日志主键
		Intent intent = getIntent();
		date = intent.getStringExtra("date");
		if (date == null || date.equals("--") || date.equals(""))
		{
			date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		}
		type = intent.getStringExtra("diaryType");
		Date selectDate = new Date();
		try
		{
			selectDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(selectDate);
		int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		int month = calendar.get(java.util.Calendar.MONTH) + 1;
		int year = calendar.get(java.util.Calendar.YEAR);
		int week = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		day_tv = (TextView) findViewById(R.id.diary_day_edit);
		week_tv = (TextView) findViewById(R.id.diary_week_edit);
		ym_tv = (TextView) findViewById(R.id.diary_year_month_edit);
		content_tv = (EditText) findViewById(R.id.diary_content_edit);
		content_tv.setGravity(Gravity.TOP);
		day_tv.setText(String.valueOf(day));
		week_tv.setText("星期" + String.valueOf(week));
		ym_tv.setText(year + "." + month);
		if (type.equals("2"))
		{
			Cursor c = db.rawQuery("select content from myDiary where date=?", new String[]
			{
					date
			});
			c.moveToFirst();
			try
			{
				content_tv.setText(c.getString(0));
			} catch (Exception e)
			{
				// TODO: handle exception
				Toast.makeText(getApplicationContext(), "获取日志失败", Toast.LENGTH_SHORT).show();
			}
			c.close();
		}
		db.close();
	}

	public void safeDiary()
	{
		SQLiteDatabase db = new SqliteHelper().getWrite();
		int dhfhd = content_tv.getText().toString().length();
		if (dhfhd != 0)
		{
			if (type.equals("1"))
			{
				date = date + " " + new SimpleDateFormat("HH:mm:ss").format(new Date(System.currentTimeMillis()));
				// db.execSQL("insert into myDiary values('" + date + "','" +
				// content_tv.getText().toString() + "','test','" +
				// Constants.number + "')");
				db.execSQL("insert into myDiary values(?,?,?,?)", new String[]
				{
						date, content_tv.getText().toString(), "test", Constants.number
				});
			} else if (type.equals("2"))
			{
				// db.execSQL("update myDiary set content='" +
				// content_tv.getText().toString() + "' where date='" + date +
				// "'");
				db.execSQL("update myDiary set content=? where date=?", new String[]
				{
						content_tv.getText().toString(), date
				});
			}
		}
		db.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			safeDiary();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 获取屏幕宽度
	 * 
	 * @return
	 */
	public int getScreenWidth()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		return width;
	}

	/**
	 * 获取屏幕高度
	 * 
	 * @return
	 */
	public int getScreenHeight()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		return height;
	}
}
