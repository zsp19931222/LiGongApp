package yh.app.appstart.lg;
//package  com.yhkj.cqswzyxy;
//import java.util.ArrayList;
//import java.util.Calendar;
//import org.androidpn.client.ServiceManager;
//import org.androidpn.push.Constants;
//import yh.app.calendar.CalendarActivity;
//import yh.app.contacts.ContactMain;
//import yh.app.coursetable.TableDemoActivity;
//import yh.app.db.MyImdb;
//import yh.app.diary.DiaryListActivity;
//import yh.app.imfra.friendsFragment;
//import yh.app.library.LibraryActivity;
//import yh.app.lostFound.LostFoundActivity;
//import yh.app.score.Stu_Grade_Query_Activity;
//import yh.app.shake.ShakeActivity;
//import yh.app.stu_info.Stu_info_setup;
//import yh.app.tool.MyFragmentPagerAdapter;
//import yh.app.viwcom.APPFragment;
//import yh.app.viwcom.CrcleFragment;
//import yh.app.viwcom.HomeFragment;
//import yh.app.viwcom.messageFragment;
//import  com.yhkj.cqswzyxy.R;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.AlertDialog.Builder;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.DialogInterface.OnClickListener;
//import android.content.pm.ActivityInfo;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//public class MainActivity extends FragmentActivity
//{
//	@Override
//	protected void onResume()
//	{
//		/** 设置为横屏 */
//		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
//		{
//			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		}
//		super.onResume();
//	}
//	private MyImdb dbmanger = Constants.mydbmanager;
//	/** 主页中首页按钮布局 */
//	LinearLayout button1 = null;
//	/** 主页中通知按钮布局 */
//	LinearLayout button2 = null;
//	/** 主页中应用按钮布局 */
//	LinearLayout button3 = null;
//	/** 主页中圈子按钮布局 */
//	LinearLayout button4 = null;
//	private ListView mDrawerList; // 侧边栏
//	private String[] mPlanetTitles; // 设置 退出
//	/** 主页中首页按钮图片 */
//	ImageView imageview1 = null;
//	/** 主页中通知按钮图片 */
//	ImageView imageview2 = null;
//	/** 主页中应用按钮图片 */
//	ImageView imageview3 = null;
//	/** 主页中圈子按钮图片 */
//	ImageView imageview4 = null;
//	/** 主页中首页按钮文本 */
//	TextView textview1 = null;
//	/** 主页中通知按钮文本 */
//	TextView textview2 = null;
//	/** 主页中应用按钮文本 */
//	TextView textview3 = null;
//	/** 主页中圈子按钮文本 */
//	TextView textview4 = null;
//	/***************** 侧边栏文本信息 * *****************/
//	/** 侧边栏文本:名字 */
//	TextView info_name = null; // 名字
//	/** 侧边栏文本:性别 */
//	TextView info_sex = null; // 性别
//	/** 侧边栏文本:学院 */
//	TextView info_xy = null; // 学院
//	/** 侧边栏文本:专业 */
//	TextView info_zy = null; // 专业
//	/** 侧边栏文本:qq */
//	TextView info_qq = null; // qq
//	/** 侧边栏文本:心情 */
//	TextView info_xq = null; // 心情
//	ImageView mTabImg = null;
//	Fragment appfragment = null;
//	Fragment crclefragment = null;
//	Fragment messagefragment = null;
//	private ArrayList<Fragment> fragmentsList;
//	private ViewPager mTabPager;
//	private static int currentFragment;
//	Fragment stu = null;
//	LinearLayout con = null;
//	// org.androidpn.push.ServiceManager serviceManager_push = null;
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		 // 标题栏去除
//		setContentView(R.layout.main);
//		ActivityManager.mactivity = this;
//		// con = (LinearLayout) findViewById(R.id.someFragment);
//		mPlanetTitles = getResources().getStringArray(R.array.planets_array);
//		mDrawerList = (ListView) findViewById(R.id.left_drawer);
//		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mPlanetTitles));
//		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//		// mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
//		// serviceManager_push = new org.androidpn.push.ServiceManager(this);
//		// serviceManager_push.setNotificationIcon(R.drawable.notification);
//		// serviceManager_push.startService();
//		// serviceManager = new ServiceManager(this);
//		// serviceManager.setNotificationIcon(R.drawable.notification);
//		// serviceManager.startService();
//		imageview1 = (ImageView) findViewById(R.id.img_index);
//		imageview2 = (ImageView) findViewById(R.id.img_message);
//		imageview3 = (ImageView) findViewById(R.id.img_application);
//		imageview4 = (ImageView) findViewById(R.id.img_cycle);
//		// imageview1.setOnClickListener(new MyOnClickListener(0));
//		// imageview2.setOnClickListener(new MyOnClickListener(1));
//		// imageview3.setOnClickListener(new MyOnClickListener(2));
//		// imageview4.setOnClickListener(new MyOnClickListener(3));
//		textview1 = (TextView) findViewById(R.id.text_index);
//		textview2 = (TextView) findViewById(R.id.text_message);
//		textview3 = (TextView) findViewById(R.id.text_application);
//		textview4 = (TextView) findViewById(R.id.text_cycle);
//		info_name = (TextView) findViewById(R.id.info_name);
//		info_sex = (TextView) findViewById(R.id.info_sex);
//		info_xy = (TextView) findViewById(R.id.info_xy);
//		info_zy = (TextView) findViewById(R.id.info_zy);
//		info_qq = (TextView) findViewById(R.id.info_qq);
//		info_xq = (TextView) findViewById(R.id.info_xq);
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor result = db.rawQuery("select * from user where userid='" + Constants.number + "'", null);
//		result.moveToFirst();
//		while (!result.isAfterLast())
//		{
//			info_name.setText(result.getString(1));
//			info_sex.setText(result.getString(3));
//			info_xy.setText(result.getString(11));
//			info_zy.setText(result.getString(10));
//			info_qq.setText(result.getString(7));
//			info_xq.setText("好心情");
//			result.moveToNext();
//		}
//		db.close();
//		// mTabImg = (ImageView) findViewById(R.id.img_tab_now);
//		appfragment = new APPFragment();
//		crclefragment = new CrcleFragment();
//		messagefragment = new messageFragment();
//		stu = new HomeFragment(this);
//		mTabPager = (ViewPager) findViewById(R.id.main_viewpager);
//		mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
//		fragmentsList = new ArrayList<Fragment>();
//		fragmentsList.add(stu);
//		fragmentsList.add(messagefragment);
//		fragmentsList.add(appfragment);
//		fragmentsList.add(crclefragment);
//		mTabPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
//		mTabPager.setCurrentItem(currentFragment);
//		// mTabPager.setCurrentItem(currentindex);
//		button1 = (LinearLayout) findViewById(R.id.main_index);
//		button2 = (LinearLayout) findViewById(R.id.main_message);
//		button3 = (LinearLayout) findViewById(R.id.main_application);
//		button4 = (LinearLayout) findViewById(R.id.main_cycle);
//		button1.setOnClickListener(new MyOnClickListener(0));
//		button2.setOnClickListener(new MyOnClickListener(1));
//		button3.setOnClickListener(new MyOnClickListener(2));
//		button4.setOnClickListener(new MyOnClickListener(3));
//	}
//	public void appclick(View view)
//	{
//		new AlertDialog.Builder(MainActivity.this).setTitle("功能").setMessage(view.getId()).setPositiveButton("确定", null).show();
//	}
//	// 提示对话框
//	private void alert(String title, String message)
//	{
//		new AlertDialog.Builder(MainActivity.this).setTitle(title).setMessage(message).setPositiveButton("确定", null).show();
//	}
//	public void rlonclick(View view)
//	{
//		SQLiteDatabase db = dbmanger.getWritableDatabase();
//		Cursor result = db.rawQuery("select * from term where userid='" + Constants.number + "' and ifnow='true'", null);// 查询开始时间结束时间，算出周数
//		result.moveToFirst();
//		if (result.getCount() == 0)
//		{
//			alert("错误", "数据未获取，请重新登录更新数据");
//		}
//		else
//		{
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, CalendarActivity.class);
//			startActivity(intent);
//		}
//	}
//	public void skclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, ShakeActivity.class);
//		startActivity(intent);
//	}
//	// 课表
//	public void kbonclick(View view)
//	{
//		SQLiteDatabase db = dbmanger.getWritableDatabase();
//		Cursor result = db.rawQuery("select * from term where userid='" + Constants.number + "' and ifnow='true'", null);
//		result.moveToFirst();
//		if (result.getCount() == 0)
//		{
//			alert("错误", "数据未获取，请重新登录更新数据");
//		}
//		else
//		{
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, TableDemoActivity.class);
//			startActivity(intent);
//		}
//	}
//	private String getDate()
//	{
//		Calendar c = Calendar.getInstance();
//		String year = String.valueOf(c.get(Calendar.YEAR));
//		String month = String.valueOf(c.get(Calendar.MONTH));
//		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//		String mins = String.valueOf(c.get(Calendar.MINUTE));
//		StringBuffer sbBuffer = new StringBuffer();
//		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins);
//		return sbBuffer.toString();
//	}
//	public void messageclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, MessageListActivity.class);
//		startActivity(intent);
//	}
//	public void grclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, MessageListActivity.class);
//		startActivity(intent);
//	}
//	// 新闻
//	public void newclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.putExtra("id", "");
//		intent.setClass(MainActivity.this, NewView.class);
//		startActivity(intent);
//	}
//	// 个人日志
//	public void rzclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, DiaryListActivity.class);
//		startActivity(intent);
//	}
//	// 通讯录
//	public void contactclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, ContactMain.class);
//		startActivity(intent);
//	}
//	// 图书馆
//	public void libraryclick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, LibraryActivity.class);
//		startActivity(intent);
//	}
//	// 失物招领
//	public void lostFoundClick(View view)
//	{
//		Intent intent = new Intent();
//		intent.setClass(MainActivity.this, LostFoundActivity.class);
//		startActivity(intent);
//	}
//	// 成绩
//	public void cjclick(View view)
//	{
//		SQLiteDatabase db = dbmanger.getWritableDatabase();
//		Cursor result = db.rawQuery("select * from term where userid='" + Constants.number + "'", null);// 查询数据库中该学生的学年
//		result.moveToFirst();
//		if (result.getCount() == 0)
//		{
//			alert("错误", "数据未获取，请重新登录更新数据");
//		}
//		else
//		{
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, Stu_Grade_Query_Activity.class);
//			startActivity(intent);
//		}
//	}
//	// 添加
//	public void tjclick(View view)
//	{
//		new AlertDialog.Builder(MainActivity.this).setTitle("功能").setMessage("添加新的功能").setPositiveButton("确定", null).show();
//	}
//	// 设置以及退出按钮监听
//	private class DrawerItemClickListener implements ListView.OnItemClickListener
//	{
//		@Override
//		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//		{
//			selectItem(position);
//		}
//	}
//	// 设置以及退出按钮监听处理事件
//	private void selectItem(int position)
//	{
//		// update the main content by replacing fragments
//		switch (position) {
//		case 0:
//			Intent intent = new Intent();
//			intent.setClass(MainActivity.this, Stu_info_setup.class);
//			startActivity(intent);
//			break;
//		case 1:
//			try
//			{
//				// logout l = new logout();
//				// l.execute();
//			} catch (Exception e)
//			{
//			}
//		default:
//			break;
//		}
//	}
//	@Override
//	protected Dialog onCreateDialog(int id)
//	{
//		Dialog dialog = null;
//		Builder b = new AlertDialog.Builder(ActivityManager.mactivity);
//		b.setIcon(R.drawable.dialog_delete);
//		b.setTitle("警告");
//		switch (id) {
//		case 0:
//			b.setMessage(R.string.dialog_tonzhi);
//			b.setPositiveButton(R.string.btnOk, new OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//					((messageFragment) messagefragment).deleteDiary();
//				}
//			});
//			b.setNegativeButton(R.string.btnCancel, new OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//				}
//			});
//			dialog = b.create();
//			break;
//		case 1:
//			b.setMessage(R.string.dialog_friend);
//			b.setPositiveButton(R.string.btnOk, new OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//					((friendsFragment) ((CrcleFragment) crclefragment).GetfriendsFragment()).deleteDiary();
//				}
//			});
//			b.setNegativeButton(R.string.btnCancel, new OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//				}
//			});
//			dialog = b.create();
//			break;
//		}
//		return dialog;
//	}
//	public class MyOnClickListener implements View.OnClickListener
//	{
//		private int index = 0;
//		public MyOnClickListener(int i)
//		{
//			index = i;
//		}
//		@Override
//		public void onClick(View v)
//		{
//			mTabPager.setCurrentItem(index);
//			currentFragment = index;
//		}
//	};
//	public class MyOnPageChangeListener implements OnPageChangeListener
//	{
//		@Override
//		public void onPageSelected(int arg0)
//		{
//			switch (arg0) {
//			case 0:
//				currentFragment = 0;
//				imageview1.setImageDrawable(getResources().getDrawable(R.drawable.main_shouye_press));
//				textview1.setTextColor(Color.rgb(0, 100, 0));
//				imageview2.setImageDrawable(getResources().getDrawable(R.drawable.main_gonggao_normal));
//				textview2.setTextColor(Color.rgb(0, 0, 0));
//				imageview3.setImageDrawable(getResources().getDrawable(R.drawable.main_application_normal));
//				textview3.setTextColor(Color.rgb(0, 0, 0));
//				imageview4.setImageDrawable(getResources().getDrawable(R.drawable.main_quanzi_normal));
//				textview4.setTextColor(Color.rgb(0, 0, 0));
//				break;
//			case 1:
//				currentFragment = 1;
//				imageview2.setImageDrawable(getResources().getDrawable(R.drawable.main_gonggao_press));
//				textview2.setTextColor(Color.rgb(0, 100, 0));
//				imageview1.setImageDrawable(getResources().getDrawable(R.drawable.main_shouye_normal));
//				textview1.setTextColor(Color.rgb(0, 0, 0));
//				imageview3.setImageDrawable(getResources().getDrawable(R.drawable.main_application_normal));
//				textview3.setTextColor(Color.rgb(0, 0, 0));
//				imageview4.setImageDrawable(getResources().getDrawable(R.drawable.main_quanzi_normal));
//				textview4.setTextColor(Color.rgb(0, 0, 0));
//				break;
//			case 2:
//				currentFragment = 2;
//				imageview3.setImageDrawable(getResources().getDrawable(R.drawable.main_application_press));
//				textview3.setTextColor(Color.rgb(0, 100, 0));
//				imageview1.setImageDrawable(getResources().getDrawable(R.drawable.main_shouye_normal));
//				textview1.setTextColor(Color.rgb(0, 0, 0));
//				imageview2.setImageDrawable(getResources().getDrawable(R.drawable.main_gonggao_normal));
//				textview2.setTextColor(Color.rgb(0, 0, 0));
//				imageview4.setImageDrawable(getResources().getDrawable(R.drawable.main_quanzi_normal));
//				textview4.setTextColor(Color.rgb(0, 0, 0));
//				break;
//			case 3:
//				currentFragment = 3;
//				imageview4.setImageDrawable(getResources().getDrawable(R.drawable.main_quanzi_press));
//				textview4.setTextColor(Color.rgb(0, 100, 0));
//				imageview1.setImageDrawable(getResources().getDrawable(R.drawable.main_shouye_normal));
//				textview1.setTextColor(Color.rgb(0, 0, 0));
//				imageview2.setImageDrawable(getResources().getDrawable(R.drawable.main_gonggao_normal));
//				textview2.setTextColor(Color.rgb(0, 0, 0));
//				imageview3.setImageDrawable(getResources().getDrawable(R.drawable.main_application_normal));
//				textview3.setTextColor(Color.rgb(0, 0, 0));
//				break;
//			}
//			currentFragment = arg0;
//		}
//		@Override
//		public void onPageScrollStateChanged(int arg0)
//		{
//			// TODO Auto-generated method stub
//		}
//		@Override
//		public void onPageScrolled(int arg0, float arg1, int arg2)
//		{
//			// TODO Auto-generated method stub
//		}
//	}
//	/*
//	 * @Override protected void onDestroy() { super.onDestroy();
//	 * serviceManager_push.stopService(); System.exit(0); }
//	 */
//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
//		{
//			try
//			{
//				Intent home = new Intent(Intent.ACTION_MAIN);
//				home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				home.addCategory(Intent.CATEGORY_HOME);
//				startActivity(home);
//				return true;
//			} catch (Exception e)
//			{
//				return false;
//			}
//		}
//		else
//			return super.onKeyDown(keyCode, event);
//	}
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		if (requestCode == 0 && resultCode == RESULT_OK)
//		{
//			finish();
//		}
//	}
//	public CrcleFragment getCrcleFragment()
//	{
//		return (CrcleFragment) crclefragment;
//	}
//	/*
//	 * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
//	 * menu; this adds items to the action bar if it is present.
//	 * getMenuInflater().inflate(R.menu.main, menu); return true; }
//	 */
//	// public class logout extends AsyncTask<Void, Void, Void>
//	// {
//	//
//	// @Override
//	// protected Void doInBackground(Void... arg0)
//	// {
//	// SQLiteDatabase db = new SqliteHelper().getWrite();
//	// db.execSQL("update user set state=? ", new Object[] { "nologin" });
//	// db.close();
//	// serviceManager_push.stopService();
//	// finish();
//	// return null;
//	// }
//	//
//	// }
//}
