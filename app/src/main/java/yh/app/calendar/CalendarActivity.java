//package yh.app.calendar;
//import  com.yhkj.cqswzyxy.R;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import org.androidpn.push.Constants;
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.calendar.CalendarView;
//import yh.app.calendar.CalendarView.OnItemClickListener;
//import yh.app.diary.DiaryDetailActivity;
//import yh.app.diary.DiaryDetailChakan;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.util.DisplayMetrics;
// 
//import android.view.Gravity;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup.LayoutParams;
//import android.view.Window;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
///** 包 名:yh.app.calendar 类 名:CalendarActivity.java 功 能:显示校历
// * 
// * @author 云华科技
// * @version 1.0
// * @date 2015-7-29 */
//public class CalendarActivity extends ActivityPortrait
//{
//	private CalendarView calendar;
//	private ImageButton calendarLeft;
//	private TextView calendarCenter;
//	private ImageButton calendarRight;
//	private SimpleDateFormat format;
//	private static ListView diaryList;
//	public static Date downDate;
//	public static Context context;
//	private String title;
//	private String date;
//	static String allDate;
//	private Button addDiary;
//	private TextView diaryNumber;
//	private boolean move = false;
//	/** 日志操作类型 0-查看 1-增加 2-修改 */
//	private static int diaryType = -1;
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		 // ������ȥ��
//		setContentView(R.layout.nactivity_calendar);
//		downDate = new Date(System.currentTimeMillis());
//		context = this;
//		diaryList = (ListView) findViewById(R.id.listDiary1);
//		diaryList.setOnItemClickListener(chakanClickListener);
//		diaryList.setOnItemLongClickListener(new android.widget.AdapterView.OnItemLongClickListener()
//		{
//			@Override
//			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
//			{
//				// TODO Auto-generated method stub
//				showPopWindows(arg1, arg2);
//				return true;
//			}
//		});
//		addDiary = (Button) findViewById(R.id.addDiary);
//		diaryNumber = (TextView) findViewById(R.id.diary_number);
//		addDiary.setBackgroundResource(R.drawable.button_blue);
//		addDiary.setOnTouchListener(new OnTouchListener()
//		{
//			@Override
//			public boolean onTouch(View v, MotionEvent event)
//			{
//				// TODO Auto-generated method stub
//				if (event.getAction() == MotionEvent.ACTION_DOWN)
//				{
//					v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
//				}
//				else if (event.getAction() == MotionEvent.ACTION_MOVE && move == false)
//				{
//					move = true;
//				}
//				else if (event.getAction() == MotionEvent.ACTION_UP && move == true)
//				{
//					v.getBackground().clearColorFilter();
//					move = false;
//				}
//				if ((event.getAction() == MotionEvent.ACTION_UP && move == false))
//				{
//					v.getBackground().clearColorFilter();
//					Intent intent = new Intent(CalendarActivity.this, DiaryDetailActivity.class);
//					intent.putExtra("", "");
//					diaryType = 1;
//					intent.putExtra("diaryType", diaryType);
//					startActivity(intent);
//				}
//				return true;
//			}
//		});
//		format = new SimpleDateFormat("yyyy-MM-dd");
//		calendar = (CalendarView) findViewById(R.id.calendar);
//		calendar.setSelectMore(false);
//		calendarLeft = (ImageButton) findViewById(R.id.calendarLeft);
//		calendarCenter = (TextView) findViewById(R.id.calendarCenter);
//		calendarRight = (ImageButton) findViewById(R.id.calendarRight);
//		try
//		{
//			Date date = format.parse("2015-01-01");
//			calendar.setCalendarData(date);
//		} catch (ParseException e)
//		{
//			e.printStackTrace();
//		}
//		String[] ya = calendar.getYearAndmonth().split("-");
//		calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
//		calendarLeft.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				String leftYearAndmonth = calendar.clickLeftMonth();
//				String[] ya = leftYearAndmonth.split("-");
//				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
//			}
//		});
//		calendarRight.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				String rightYearAndmonth = calendar.clickRightMonth();
//				String[] ya = rightYearAndmonth.split("-");
//				calendarCenter.setText(ya[0] + "年" + ya[1] + "月");
//			}
//		});
//		calendar.setOnItemClickListener(new OnItemClickListener()
//		{
//			@Override
//			public void OnItemClick(Date selectedStartDate, Date selectedEndDate, Date downDate)
//			{
//				if (calendar.isSelectMore())
//				{
//				}
//				else
//				{
//				}
//			}
//		});
//		SimpleAdapter simpleAdater = new SimpleAdapter(CalendarActivity.context, getData(downDate),// 从数据库读取那一天日志信息
//				R.layout.diary_iterm, new String[] { "title", "date", "content", "number" }, new int[] { R.id.diary_info_item, R.id.diary_date_item,/*
//																																					 * R
//																																					 * .
//																																					 * id
//																																					 * .
//																																					 * diary_info_item
//																																					 * ,
//																																					 */R.id.diary_number });
//		diaryList.setAdapter(simpleAdater);
//	}
//	android.widget.AdapterView.OnItemClickListener chakanClickListener = new android.widget.AdapterView.OnItemClickListener()
//	{
//		@SuppressWarnings("unused")
//		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
//		{
//			// TODO Auto-generated method stub
//			Log.v("error1", "点击listview中一项，跳转查看界面");
//			Intent intent = new Intent(CalendarActivity.this, DiaryDetailChakan.class);
//			allDate = zhujian.get(arg2);
//			intent.putExtra("date", allDate);
//			diaryType = 0;
//			intent.putExtra("diaryType", diaryType);
//			System.out.println("当前点击查看的是   " + title);
//			System.out.println("当前点击查看的是   " + date);
//			Log.v("error1", "点击listview中一项，跳转查看界面");
//			startActivity(intent);
//		}
//	};
//	// android.widget.AdapterView.OnItemLongClickListener selectLongClick = new
//	// android.widget.AdapterView.OnItemLongClickListener() {
//	//
//	// @Override
//	// public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//	// int arg2, long arg3) {
//	// // TODO Auto-generated method stub
//	// String date = "1";
//	// new SimpleDateFormat()
//	// .format(getData(downDate).get(arg2).get("date"));
//	// showPopWindows(arg1,arg2,date);
//	//
//	// return true;
//	// }
//	// };
//	public void showPopWindows(View v, final int Postion/* , final String date */)
//	{
//		LinearLayout layout = new LinearLayout(this);
//		layout.setBackgroundResource(R.drawable.qipao);
//		Button tv = new Button(this);
//		tv.setBackgroundColor(getResources().getColor(R.color.delete_text_gray));
//		final PopupWindow popupWindow = new PopupWindow(layout, 240,/*
//																	 * LinearLayout.
//																	 * LayoutParams
//																	 * .
//																	 * WRAP_CONTENT
//																	 */120);
//		tv.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				// TODO Auto-generated method stub
//				String downDate = new SimpleDateFormat("yyyy-MM-dd").format(CalendarActivity.this.downDate);
//				SQLiteDatabase db = new SqliteHelper().getWrite();
//				Cursor c = db.rawQuery("select * from myDiary", null);
//				for (int i = 0; i < c.getCount(); i++)
//				{
//					c.moveToNext();
//					if (i == Postion)
//					{
//						try
//						{
//							String ds = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(c.getString(0)));
//							if (ds.equals(downDate))
//							{
//								String sql = "delete from myDiary where date='" + c.getString(0) + "'";
//								db.execSQL(sql);
//								showDiary();
//								if (popupWindow != null && popupWindow.isShowing())
//								{
//									popupWindow.dismiss();
//								}
//								break;
//							}
//						} catch (ParseException e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//				}
//				c.close();
//				db.close();
//			}
//		});
//		tv.setLayoutParams(new LayoutParams(240, 100));
//		tv.setText("删除");
//		tv.setTextColor(Color.WHITE);
//		layout.addView(tv);
//		popupWindow.setFocusable(true);
//		popupWindow.setOutsideTouchable(true);
//		popupWindow.setBackgroundDrawable(new BitmapDrawable());
//		int[] location = new int[2];
//		v.getLocationInWindow(location);
//		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, getScreenWidth() / 2 - 120, location[1] - popupWindow.getHeight() + 50);
//	}
//	/** 获取屏幕宽度
//	 * 
//	 * @return */
//	public int getScreenWidth()
//	{
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//		int width = dm.widthPixels;
//		dm = null;
//		return width;
//	}
//	// 显示日志
//	public static void showDiary()
//	{
//		// 获取点击的日期
//		downDate = CalendarView.getDownDate();
//		allDate = new SimpleDateFormat().format(downDate);
//		SimpleAdapter simpleAdater = new SimpleAdapter(CalendarActivity.context, getData(downDate),// 从数据库读取那一天日志信息
//				R.layout.diary_iterm, new String[] { "title", "date", "content", "number" }, new int[] { R.id.diary_info_item, R.id.diary_date_item/*
//																																					 * ,
//																																					 * R
//																																					 * .
//																																					 * id
//																																					 * .
//																																					 * diary_info_item
//																																					 * ,
//																																					 */, R.id.diary_number });
//		diaryList.setAdapter(simpleAdater);
//	}
//	static List<String> zhujian;
//	private static List<Map<String, Object>> getData(Date downDate)
//	{
//		zhujian = new ArrayList<String>();
//		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor myDiary = db.query("myDiary", null, null, null, null, null, null);
//		int i = 1;
//		while (myDiary.moveToNext())
//		{
//			map = new HashMap<String, Object>();
//			String str = "";
//			try
//			{
//				str = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(myDiary.getString(0)));
//			} catch (ParseException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String s = new SimpleDateFormat("yyyy-MM-dd").format(downDate);
//			if (str.equals(s))
//			{
//				map.put("title", myDiary.getString(1));
//				map.put("content", myDiary.getString(2));
//				try
//				{
//					map.put("date", new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(myDiary.getString(0))));
//				} catch (ParseException e)
//				{
//					e.printStackTrace();
//					map.put("date", null);
//				}
//				if (i < 10)
//				{
//					map.put("number", "0" + i);
//				}
//				else
//					map.put("number", i);
//				map.put("", "");
//				i++;
//				list.add(map);
//				zhujian.add(myDiary.getString(0));
//			}
//		}
//		db.close();
//		return list;
//	}
//	// public class Arraryadapter extends Activity {
//	// private ListView listView;
//	//
//	// public void onCreate(Bundle savedInstanceState) {
//	// super.onCreate(savedInstanceState);
//	// setContentView(R.layout.main);
//	// listView = new ListView(this);
//	// listView.setAdapter(new ArrayAdapter<String>(this,
//	// android.R.layout.simple_expandable_list_item_1, getData()));
//	// // 注意一定要匹配数据类型
//	// setContentView(listView);
//	// }
//	//
//	// private List<String> getData() {
//	// List<String> data = new ArrayList<String>();
//	// data.add("测试数据1");
//	// data.add("测试数据2");
//	// data.add("测试数据3");
//	// data.add("测试数据4");
//	// return data;
//	// }
//	// }
//	public Date getDate(String date)
//	{
//		Date ymd;
//		try
//		{
//			ymd = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//			return ymd;
//		} catch (ParseException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return null;
//		}
//	}
//	public String getStringDate(Date date)
//	{
//		String ymd = null;
//		try
//		{
//			ymd = new SimpleDateFormat("yyyy-MM-dd").format(date);
//			return ymd;
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//			return null;
//		}
//	}
//}
