package yh.app.mydiary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.SqliteHelper;import com.yhkj.cqgyxy.R;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class ShowDiary extends ActivityPortrait
{
	/** 选择显示日志的日期 */
	String date = "";
	String[] week7 = new String[] { "一", "二", "三", "四", "五", "六", "日" };
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.diary_chakan);
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
	private TextView content_tv;
	/** 返回按钮 */
	ImageView back;
	/** 编辑按钮 */
	TextView edit_button;

	/** 绘制界面 */
	private void show()
	{
		new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle("个人日志").setOnClickLisener(new OnClickLisener()
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
				finish();
			}
			
			@Override
			public void setExtraOnclick()
			{
			}
		});
		// 获取要显示的日志主键
		Intent intent = getIntent();
		date = intent.getStringExtra("date");
		Date selectDate = new Date();
		try
		{
			selectDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "获取日期失败", Toast.LENGTH_SHORT).show();
		}
		java.util.Calendar calendar = java.util.Calendar.getInstance();
		calendar.setTime(selectDate);
		int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);
		int month = calendar.get(java.util.Calendar.MONTH) + 1;
		int year = calendar.get(java.util.Calendar.YEAR);
		int week = calendar.get(java.util.Calendar.DAY_OF_WEEK);
		day_tv = (TextView) findViewById(R.id.diary_day);
		week_tv = (TextView) findViewById(R.id.diary_week);
		ym_tv = (TextView) findViewById(R.id.diary_year_month);
		content_tv = (TextView) findViewById(R.id.diary_content);
		day_tv.setText(String.valueOf(day));
		week_tv.setText("星期" + String.valueOf(week));
		ym_tv.setText(year + "." + month);
		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor c = db.rawQuery("select content from myDiary where date=?", new String[] { date });
		c.moveToFirst();
		try
		{
			content_tv.setText(c.getString(0));
		} catch (Exception e)
		{
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), "获取日志失败", Toast.LENGTH_SHORT).show();
		}
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
