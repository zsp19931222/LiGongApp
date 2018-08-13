package yh.app.diary;
import static yh.app.diary.MyOpenHelper.*;

import yh.app.diary.MyOpenHelper;
import com.example.app3.HomePageActivity;
import yh.app.activitytool.ActivityPortrait;
import yh.app.uiengine.home;

import org.androidpn.push.Constants;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
 
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.cqgyxy.R;

/** 包 名:yh.app.diary 类 名:DiaryListActivity.java 功 能:展示个人日志列表
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29 */
@SuppressWarnings("unused")
public class DiaryListActivity extends ActivityPortrait
{
	MyOpenHelper myHelper; // 声明MyOpenHelper对象
	final int DIALOG_DELETE = 0; // 确认删除对话框的ID
	String[] diarysTitle; // 声明用于存放日志标题的数组
	String[] diarysDatetime; // 声明用于存放日志日期的数组
	String[] diarysContent; // 声明用于存放日志内容的数组
	int[] diarysId; // 声明用于存放日志id的数组
	int pos = -1; // ListView对象中的当前项位置
	ListView lv; // 声明ListView对象
	ImageView add_diary = null;
	ImageButton return_diary = null;
	DiaryBaseAdapter myAdapter = null;
	class DiaryBaseAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;
		public DiaryBaseAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount()
		{
			if (diarysTitle != null)
			{ // 如果日志数组不为空
				return diarysTitle.length;
			}
			else
			{
				return 0; // 如果日志数组为空则返回0
			}
		}
		@Override
		public Object getItem(int arg0)
		{
			return null;
		}
		@Override
		public long getItemId(int arg0)
		{
			return 0;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			final int p = position;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.diarylist_subitem, null);
				holder.id = (TextView) convertView.findViewById(R.id.diarylist_iddd);
				holder.title = (TextView) convertView.findViewById(R.id.diarylist_sub_title);
				holder.time = (TextView) convertView.findViewById(R.id.diarylist_sub_time);
				holder.content = (TextView) convertView.findViewById(R.id.diarylist_sub_content);
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.id.setText(String.valueOf(diarysId[position]));
			holder.time.setText(diarysDatetime[position]);
			if (diarysTitle[position].length() > 10)
			{
				holder.title.setText(diarysTitle[position].substring(0, 8) + "...");
			}
			else
			{
				holder.title.setText(diarysTitle[position]);
			}
			if (diarysContent[position].length() > 20)
			{
				holder.content.setText(diarysContent[position].substring(0, 15) + "...");
			}
			else
			{
				holder.content.setText(diarysContent[position]);
			}
			return convertView;
		}
	};
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarylist);
		myAdapter = new DiaryBaseAdapter(this);
		myHelper = new MyOpenHelper(this, DB_NAME, null, 1);// 打开数据表库表
		lv = (ListView) findViewById(R.id.listDiary); // 获得ListView对象的引用
		add_diary = (ImageView) findViewById(R.id.add_diary);
		return_diary = (ImageButton) findViewById(R.id.return_pre);
		return_diary.setOnClickListener(listenerToReturn);
		lv.setAdapter(myAdapter);
		add_diary.setOnClickListener(listenerToAdd);
		lv.setOnItemClickListener(chakanClickListener);
		lv.setOnItemLongClickListener(deleteLongClickListener);
	}
	@Override
	protected void onResume()
	{
		getBasicInfo(myHelper); // 重新获取数据库信息
		myAdapter.notifyDataSetChanged(); // 刷新ListView
		super.onResume();
	}
	// 方法：获取数据表中所有记录部分列的数据内容
	public void getBasicInfo(MyOpenHelper helper)
	{
		SQLiteDatabase db = helper.getWritableDatabase(); // 获取数据库连接
		Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where d_uno=?", new String[] { Constants.number });
		// Cursor c = db.query(TABLE_NAME, new String[] { ID, TITLE, DATETIME,
		// CONTENT }, null, null, null, null, ID);
		int idIndex = c.getColumnIndex(ID);
		int titleIndex = c.getColumnIndex(TITLE); // 获得标题列的列号
		int dtIndex = c.getColumnIndex(DATETIME); // 获得日期时间列的序号
		int contentIndex = c.getColumnIndex(CONTENT); // 获得日期时间列的序号
		diarysId = new int[c.getCount()]; // 创建存放id的int数组对象
		diarysTitle = new String[c.getCount()]; // 创建存放标题的String数组对象
		diarysDatetime = new String[c.getCount()]; // 创建存放日期时间的数组对象
		diarysContent = new String[c.getCount()]; // 创建存放内容的数组对象
		int i = 0; // 声明一个计数器
		for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext())
		{
			diarysId[i] = c.getInt(idIndex);
			diarysTitle[i] = c.getString(titleIndex); // 将标题添加到String数组中
			diarysDatetime[i] = c.getString(dtIndex); // 将日期时间添加到String数组中
			diarysContent[i] = c.getString(contentIndex); // 将日期时间添加到String数组中
			i++;
		}
		c.close(); // 关闭Cursor对象
		db.close(); // 关闭SQLiteDatabase对象
	}
	// 点击listview中一项，跳转查看界面
	OnItemClickListener chakanClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub
			RelativeLayout parent = (RelativeLayout) arg1;
			TextView tempid = (TextView) parent.getChildAt(0);
			pos = Integer.parseInt(tempid.getText().toString());
			Intent intent = new Intent(DiaryListActivity.this, DiaryDetailChakan.class);
			intent.putExtra("date", pos);
			startActivity(intent);
		}
	};
	// 长按删除
	OnItemLongClickListener deleteLongClickListener = new OnItemLongClickListener()
	{
		@SuppressWarnings("deprecation")
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub
			RelativeLayout parent = (RelativeLayout) arg1;
			TextView tempid = (TextView) parent.getChildAt(0);
			pos = Integer.parseInt(tempid.getText().toString());
			showDialog(DIALOG_DELETE); // 显示确认删除对话框，并实施相应操作
			return false;
		}
	};
	// 增加新的笔记
	View.OnClickListener listenerToAdd = new View.OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(DiaryListActivity.this, DiaryDetailActivity.class);
			intent.putExtra("cmd", 0);
			startActivity(intent);
		}
	};
	// 返回前一个主界面
	View.OnClickListener listenerToReturn = new View.OnClickListener()
	{
		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(DiaryListActivity.this, home.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
	};
	// 创建对话框
	@Override
	protected Dialog onCreateDialog(int id)
	{
		Dialog dialog = null;
		switch (id) { // 对对话框ID进行判断
		case DIALOG_DELETE: // 创建删除确认对话框
			Builder b = new AlertDialog.Builder(this);
			b.setIcon(R.drawable.dialog_delete); // 设置对话框图标
			b.setTitle("提示"); // 设置对话框标题
			b.setMessage(R.string.dialog_message); // 设置对话框内容
			b.setPositiveButton(R.string.btnOk, new OnClickListener()
			{ // 按下对话框的确认按钮
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
							deleteDiary(pos); // 调用删除处理方法
						}
					});
			b.setNegativeButton(R.string.btnCancel, new OnClickListener()
			{ // 按下对话框的取消按钮
						@Override
						public void onClick(DialogInterface dialog, int which)
						{
						}
					});
			dialog = b.create();
			break;
		}
		return dialog;
	}
	// 方法：删除指定的日志记录
	public void deleteDiary(int id)
	{ // id为要删除记录的id
		SQLiteDatabase db = myHelper.getWritableDatabase(); // 获得数据库对象
		try
		{
			int count = db.delete(TABLE_NAME, "" + ID + "=? and " + USERNO + "=?", new String[] { String.valueOf(id), Constants.number });
			db.close();
			if (count == 1)
			{ // 删除成功
				Toast.makeText(DiaryListActivity.this, "删除日志成功！", Toast.LENGTH_SHORT).show();
				getBasicInfo(myHelper);
				myAdapter.notifyDataSetChanged();
			}
			else
			{ // 删除失败
				Toast.makeText(DiaryListActivity.this, "删除失败，请重试！", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			Intent intent = new Intent(DiaryListActivity.this, home.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("page", 2);
			startActivity(intent);
			return true;
		}
		else
			return super.onKeyDown(keyCode, event);
	}
}