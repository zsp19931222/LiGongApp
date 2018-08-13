package yh.app.diary;

import yh.app.tool.SqliteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import yh.app.activitytool.ActivityPortrait;
import yh.app.calendar.Canlendar;
//import yh.app.calendar.CalendarActivity;
//import yh.app.calendar.CalendarView;
import org.androidpn.push.Constants;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
 
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.yhkj.cqgyxy.R;

/**
 *
 * 包 名:yh.app.diary 类 名:DiaryDetailActivity.java 功 能:个人日志编辑界面
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class DiaryDetailActivity extends ActivityPortrait
{

	EditText etModifyTitle = null; // 显示日志标题的EditText
	EditText etModifyContent = null; // 显示日志内容的EditText
	TextView tvdttm = null; // 显示日志创建时间的TextView
	int status = -1; // 0表示添加日志，1表示修改日志，
	int id = -1; // 记录当前显示的日志id
	String[] diaryInfo = null; // 记录日志信息
	DateFormat df = null; // 显示日期时间的格式
	MyOpenHelper myHelper; // 声明一个MyOpenHelper对象
	String diaryDate = null;
	private int diaryType = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarydetail);
		// 启动activity时不自动弹出软键盘
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		etModifyTitle = (EditText) findViewById(R.id.etModifyTitle); // 获得标题的EditText
		etModifyContent = (EditText) findViewById(R.id.etModifyDiary); // 获得内容的EditText
		tvdttm = (TextView) findViewById(R.id.dttm); // 获得日期时间的TextView

		// myHelper = new MyOpenHelper(this, MyOpenHelper.DB_NAME, null, 1);
		// Log.v("error1", "获取intent");
		// Intent intent = getIntent();
		// status = intent.getExtras().getInt("cmd"); // 读取命令类型
		// Log.v("error1", "获取intent结束");
		diaryDate = getIntent().getStringExtra("date");
		if (diaryDate == null)
		{
			diaryDate = "1";
		}
		diaryType = getIntent().getIntExtra("diaryType", 0);
		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor c = db.rawQuery("select * from myDiary where date=?", new String[] { diaryDate });

		if (c.getCount() == 0)
		{ // 没有查询到指定的日志
			// 显示没有找到指定的日志信息
		} else
		{ // 查询到了这条日志
			c.moveToFirst(); // 移动到第一条记录
			etModifyTitle.setText(c.getString(1));
			etModifyContent.setText(c.getString(2));
			tvdttm.setText(c.getString(0));
		}
		c.close();
		db.close();

		TextView btnSaveDiary = (TextView) findViewById(R.id.save_diary); // 保存
		btnSaveDiary.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				String modifyTitle = etModifyTitle.getEditableText().toString().trim();
				// 取出EditText中标题的内容
				String modifyContent = etModifyContent.getEditableText().toString().trim();
				if (modifyContent.equals("") || modifyTitle.equals(""))
				{ // 如果标题或内容为空则提示
					// 提示需要将标题或内容填写完整,是否放弃编辑
					Toast.makeText(DiaryDetailActivity.this, "请填写标题或内容", Toast.LENGTH_SHORT).show();
					return;
				}

				df = new android.text.format.DateFormat(); // 定义一个日期格式对象
				tvdttm.setText(DateFormat.format("yyyy-MM-dd hh:mm:ss", new java.util.Date()));// 取当前的日期
				Intent intent1 = getIntent();
				status = intent1.getIntExtra("diaryType", 0);
				switch (status)
				{ // 判断当前的状态

				case 1: // 编辑已有日志时
					insertDiary(); // 调用插入日志方法
					break;

				case 2: // 新建日志时
					updateDiary(); // 调用更新日志方法
					break;
				}
				// 返回到日志列表Activity
				Intent intent = new Intent(DiaryDetailActivity.this, Canlendar.class);
				// CalendarActivity.downDate = new
				// Date(System.currentTimeMillis());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("date", diaryDate);
				startActivity(intent);
			}
		});
		ImageButton btnModifyBack = (ImageButton) findViewById(R.id.return_diary); // 获得返回按钮
		btnModifyBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(DiaryDetailActivity.this, Canlendar.class);
				// CalendarActivity.downDate = new
				// Date(System.currentTimeMillis());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("date", diaryDate);
				startActivity(intent);
			}
		});
	}

	// 方法：更新某个日志
	public void updateDiary()
	{
		SQLiteDatabase db = new SqliteHelper().getWrite(); // 获得数据库对象

		ContentValues values = new ContentValues();
		values.put("date", tvdttm.getText().toString());
		values.put("title", etModifyTitle.getText().toString());
		values.put("content", etModifyContent.getText().toString());
		db.update("myDiary", values, "date" + "=?", new String[] { diaryDate });// 更新数据库
		db.close();
	}

	// 方法：添加日志
	public void insertDiary()
	{
		SQLiteDatabase db = new SqliteHelper().getWrite(); // 获得数据库对象
		ContentValues values = new ContentValues();
		values.put("date", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
		values.put("title", etModifyTitle.getText().toString());
		values.put("content", etModifyContent.getText().toString());
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
		String title = etModifyTitle.getText().toString();
		String content = etModifyContent.getText().toString();
		String sql = "";
		try
		{
			sql = "insert into myDiary values('" + date + "','" + title + "','" + content + "','" + Constants.number + "');";
			db.execSQL(sql);
		} catch (Exception e)
		{
			// TODO: handle exception
			Toast.makeText(this, "日志添加失败.", Toast.LENGTH_SHORT).show();
		}
		db.close();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent(DiaryDetailActivity.this, Canlendar.class);
			// CalendarActivity.downDate = new Date(System.currentTimeMillis());
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}