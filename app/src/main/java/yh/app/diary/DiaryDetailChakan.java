package yh.app.diary;

import java.sql.Date;
import yh.app.tool.SqliteHelper;

import yh.app.activitytool.ActivityPortrait;
import yh.app.calendar.Canlendar;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.cqgyxy.R;

/**
 * 
 * 包 名:yh.app.diary 类 名:DiaryDetailChakan.java 功 能:查看某个日志的详细信息
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class DiaryDetailChakan extends ActivityPortrait
{
	TextView title = null; // 显示日志标题的TextView
	TextView content = null; // 显示日志内容的TextView
	TextView tvdttm = null; // 显示日志创建时间的TextView
	TextView edit = null;

	int id = -1; // 记录当前显示的日志id
	DateFormat df = null; // 显示日期时间的格式
	Date downDate;
	String diaryDate; // 当前日志的日期和事件
	String diaryTitle; // 当前日志的标题
	/**
	 * 日志操作类型 0-查看 1-增加 2-修改
	 */
	private static int diaryType = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 // 标题栏去除
		setContentView(R.layout.diary_detail_chakan);

		Intent intent1 = getIntent();
		diaryDate = intent1.getStringExtra("date");
		title = (TextView) findViewById(R.id.setDiaryTitle); // 获得标题的EditText
		content = (TextView) findViewById(R.id.setContentDiary);// 获得内容的EditText
		tvdttm = (TextView) findViewById(R.id.setDiaryTime); // 获得日期时间的TextView

		// Intent intent = getIntent();
		// id = intent.getExtras().getInt("id"); // 获得要显示的日志的id

		ImageButton return_diarylist = (ImageButton) findViewById(R.id.return_diarylist); // 获得返回按钮
		return_diarylist.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DiaryDetailChakan.this, Canlendar.class);
				// CalendarActivity.downDate = new
				// Date(System.currentTimeMillis());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		edit = (TextView) findViewById(R.id.diary_more);
		edit.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DiaryDetailChakan.this, DiaryDetailActivity.class);
				diaryType = 2;
				intent.putExtra("diaryType", diaryType);
				intent.putExtra("date", diaryDate);
				startActivity(intent);
			}
		});

		// 查看日志的详细信息

		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor c = db.rawQuery("select * from myDiary where date=?", new String[] { diaryDate });
		try
		{
			c.moveToFirst(); // 移动到第一条记录
			title.setText(c.getString(1));
			content.setText(c.getString(2));
			tvdttm.setText(c.getString(0));
		} catch (Exception e)
		{
			// TODO: handle exception
			Toast.makeText(this, "没有找到该日志", Toast.LENGTH_SHORT).show();
		}
		// if (c.getCount() == 0) { // 没有查询到指定的日志
		// // 显示没有找到指定的日志信息
		// System.out.println("没有找到指定的日志信息");
		// } else { // 查询到了这条日志
		//
		// System.out.println("找到指定的日志信息");
		//
		// c.moveToFirst(); // 移动到第一条记录
		//
		// title.setText(c.getString(0));
		// content.setText(c.getString(1));
		// tvdttm.setText(c.getString(2));
		// }
		c.close();
		db.close();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent(DiaryDetailChakan.this, Canlendar.class);
			// CalendarActivity.downDate = new Date(System.currentTimeMillis());
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}
