package yh.app.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
 
import com.example.jpushdemo.body.BodyAdd;
import com.example.jpushdemo.body.BodyChat;
import com.example.jpushdemo.body.BodyPush;
import com.yunhuakeji.app.utils.MapTools;
import yh.app.appstart.lg.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidpn.push.Constants;
import yh.app.function.liaotianjiemian;
import yh.app.mymessage.SchoolMessageHome;
import yh.app.quanzi.MyClass;
import yh.app.quanzi.NewFriend;
import yh.app.quanzitool.InflaterView;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import com.example.app3.HomePageActivity;

import yh.app.uiengine.home;
import yh.app.utils.ImageAt;
import yh.app.utils.MessageUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.DragPointView;
import yh.tool.widget.MyScrollView;
import yh.tool.widget.activitys;
import yh.tool.widget.DragPointView.OnDragListencer;
import 云华.智慧校园.工具.DateTools;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.NetResultHelper;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具.XiaoYuanDianHelper;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件._侧边导航栏;

/**
 * 消息
 * 
 * @author anmin
 *
 */
public class MessageFragment extends Fragment implements OnClickListener, OnDragListencer {
	private Context context;
	private ViewGroup parent;
	private View push_type_main;
	private MyScrollView friend_ScrollView;
	private List<Map<String, String>> maplist;
	private TextView center_szm;
	private Map<String, Integer> wordsLocals = new HashMap<>();
	private Map<View, String> wordsViewLocals = new HashMap<>();
	private View contactsView;
	private LinearLayout messageViewLayout, contactsViewLayout, sidebar;

	private String sidebarSZM = "";
	private int[] p = new int[2];
	private int sidebarHeight;
	private SqliteHelper sqliteHelper = ExampleApplication.getInstance().getSqliteHelper();

	
	private String nowSZM = "";
	private int pageNum = 0, pageSize = 10000;
	private Map<View, String> map = new HashMap<>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		push_type_main=inflater.inflate(R.layout.push_type_main, container,false);
		return push_type_main;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stubd
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		initView();
		initData();
		onProcessor();
		setView(0);
	}

	private void initView() {

		maplist = sqliteHelper.rawQuery("select * from friend where userid=? order by szm ASC", Constants.number);
		if (messageViewLayout == null) {
			messageViewLayout = new LinearLayout(context);
			messageViewLayout.setMinimumWidth(500);
			
		}
		if (contactsView == null) {
			contactsView = ViewTools.getView(context, R.layout.message_stu_contacts, parent);
		}
		
	

		contactsViewLayout = (LinearLayout) contactsView.findViewById(R.id.layout);
		sidebar = (LinearLayout) contactsView.findViewById(R.id.sidebar);
		center_szm = (TextView) contactsView.findViewById(R.id.center_szm);
		center_szm.getBackground().setAlpha(100);
		friend_ScrollView = (MyScrollView) contactsView.findViewById(R.id.friend_ScrollView);
		friend_ScrollView.setVerticalScrollBarEnabled(false);
	
		
		
	}
    /**
     * 处理器
     */
	private void onProcessor() {
		categoryMessageViewUI();
		categoryContactsViewUI();
		
	}
	

	/**
	 * 获得推送数据
	 */
	private void initData() {
		if (messageViewLayout == null)
		{
			messageViewLayout = new LinearLayout(context);
			messageViewLayout.setOrientation(LinearLayout.VERTICAL);
		}
		if (contactsView == null)
		{
			contactsView = ViewTools.getView(context, R.layout.message_stu_contacts, parent);
		}

		contactsViewLayout = (LinearLayout) contactsView.findViewById(R.id.layout);
		sidebar = (LinearLayout) contactsView.findViewById(R.id.sidebar);
		center_szm = (TextView) contactsView.findViewById(R.id.center_szm);
		center_szm.getBackground().setAlpha(100);
		friend_ScrollView = (MyScrollView) contactsView.findViewById(R.id.friend_ScrollView);
		friend_ScrollView.setVerticalScrollBarEnabled(false);
		((EditText) contactsView.findViewById(R.id.seach)).setFocusable(false);
		setListener();
		setTitle(push_type_main);
		new ThreadPoolManage().addNewPostTask(_链接地址导航.GroupChat.getXSBJLB.getUrl(), MapTools.buildMap(new String[][]
		{
				{
						"userid", Constants.number
				},
				{
						"usertype", Constants.user_type
				}
		}), new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				try
				{
					JSONObject jso = new JSONObject(msg.getData().getString("msg"));
					List<Object[]> parames = new ArrayList<>();
					if (jso.getString("code").equals("40001"))
					{
						JSONArray jsa = jso.getJSONArray("content");
						for (int i = 0; i < jsa.length(); i++)
						{
							Object[] parObjects = new Object[]
							{
									jsa.getJSONObject(i).getString("id"), jsa.getJSONObject(i).getString("kcmc"), jsa.getJSONObject(i).getInt("count"), Constants.number
							};
							parames.add(parObjects);
						}
						new SqliteHelper().execSQL("replace into classlist(id,classname,count,userid) values(?,?,?,?)", parames);
					}
				} catch (Exception e)
				{
				}
			}
		});
	}

	private String getSidebarSZM(int local)
	{
		try
		{
			return _侧边导航栏.words[local / (sidebarHeight / 28)];
		} catch (Exception e)
		{
			return "";
		}
	}

	public void categoryMessageViewUI()
	{
		messageViewLayout.removeAllViews();
		addItem(messageViewLayout, getItemList());
		sidebar.addView(new _侧边导航栏().show(context, parent));
		sidebar.setOnTouchListener(new OnTouchListener()
		{
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				int Y = (int) event.getRawY();
				String szm = getSidebarSZM(Y - p[1]);
				if (!IsNull.isNotNull(szm))
				{
					sidebarSZM = "";
					center_szm.setVisibility(View.INVISIBLE);
					sidebar.setBackgroundColor(0x00ababab);
					return true;
				} else
				{
					center_szm.setVisibility(View.VISIBLE);
					sidebar.setBackgroundColor(0xe7ababab);
				}
				center_szm.setText(szm);
				if (Y > p[1] && Y < p[1] + v.getHeight())
				{
					if (wordsLocals.get(szm) != -1 && !szm.equals(sidebarSZM))
					{
						sidebarSZM = szm;
						friend_ScrollView.scrollTo(0, wordsLocals.get(szm));
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN)
					{
						center_szm.setVisibility(View.VISIBLE);
						sidebar.setBackgroundColor(0xe7ababab);
					} else if (event.getAction() == MotionEvent.ACTION_UP)
					{
						sidebarSZM = "";
						center_szm.setVisibility(View.INVISIBLE);
						sidebar.setBackgroundColor(0x00ababab);
					}
				} else
				{
					sidebarSZM = "";
					center_szm.setVisibility(View.INVISIBLE);
					sidebar.setBackgroundColor(0x00ababab);
				}
				return true;
			}
		});
	}
	public void categoryContactsViewUI()
	{
		nowSZM = "";
		pageNum = 0;
		pageSize = 10000;
		wordsViewLocals = new HashMap<>();
		wordsLocals = new HashMap<>();
		maplist = new SqliteHelper().rawQuery("select * from friend where userid=? order by szm ASC", Constants.number);
		contactsViewLayout.removeAllViews();
		String[] arr = new String[]
		{
				"新的好友", "我的班级", "学校通知"
		}, packages = new String[]
		{
				NewFriend.class.getName(), MyClass.class.getName(), SchoolMessageHome.class.getName()
		};
		int[] icons = new int[]
		{
				R.drawable.new_friend, R.drawable.my_class, R.drawable.school_notification_icon
		};
		for (int i = 1; i <= 3; i++)
		{
			switch (i)
			{
			case 3:
				setJSQ(i);
				break;
			case 2:
				setJSQ(i);
				break;
			case 1:
				setJSQ(i);
				break;
			default:
				break;
			}
			((TextView) contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).findViewById(R.id.item_name)).setText(arr[i - 1]);
			contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).findViewById(R.id.img).setBackgroundResource(icons[i - 1]);
			map.put(contactsView.findViewById(new RHelper().getId(context, "sorts" + i)), packages[i - 1]);
			contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					click(v);
				}
			});
		}
		setFriend();
	}

	public void setJSQ(int i)
	{
		String temp = null;
		try
		{
			switch (i)
			{
			case 1:
				temp = new SqliteHelper().rawQuery("select count(*) as count from addFriend where userid=? and isread = 'false'", new String[]
				{
						Constants.number
				}).get(0).get("count");
				break;
			case 2:
				temp = new SqliteHelper().rawQuery("select count(*) as count from tzgg as t1 where t1.userid = ? and t1.isread = 'false' and t1.func_id in (select id from classlist as t2 where t2.userid = ?)", new String[]
				{
						Constants.number, Constants.number
				}).get(0).get("count");
				break;
			case 3:
				temp = new SqliteHelper().rawQuery("select count(*) as count from tzgg as t1 where t1.userid = ? and t1.isread = 'false' and t1.func_id in (select t2.functionid from button as t2 where t2.function_tybj = '1' and t2.userid = ?)", new String[]
				{
						Constants.number, Constants.number
				}).get(0).get("count");
				break;
			default:
				break;
			}
		} catch (Exception e)
		{
		}
		if (temp == null || temp.equals("0"))
		{
			contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).findViewById(R.id.jsq).setVisibility(View.INVISIBLE);
		} else
		{
			contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).findViewById(R.id.jsq).setVisibility(View.VISIBLE);
			((TextView) contactsView.findViewById(new RHelper().getId(context, "sorts" + i)).findViewById(R.id.jsq)).setText(temp);
		}
	}

	public void click(View v) {
		Intent intent = new Intent();
		intent.setAction(map.get(v));
		intent.setPackage(context.getPackageName());
		context.startActivity(intent);
	}

	public void setFriend() {
		for (int i = pageNum * pageSize, length = pageNum * pageSize + pageSize, listSize = maplist.size(); i < length
				&& i < listSize; i++) {
			InflaterView iv = new InflaterView(context, maplist.get(i).get("name"), maplist.get(i).get("FRIEND_ID"),maplist.get(i).get("userid"));
			String SZM = maplist.get(i).get("szm");
			if (SZM.equals("") || !SZM.equals(nowSZM)) {
				nowSZM = SZM;
				View szmView = iv.addTITLEInflater(nowSZM, contactsViewLayout);
				wordsViewLocals.put(szmView, SZM);
				contactsViewLayout.addView(szmView);
			}
			contactsViewLayout.addView(iv.addLIXInflater(contactsViewLayout,maplist.get(i).get("handimg")));
		}
		pageNum++;
		contactsViewLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				sidebar.getLocationOnScreen(p);
				sidebarHeight = sidebar.getHeight();
				wordsLocals = new _侧边导航栏().getWordsMap();
				Iterator<View> keys = wordsViewLocals.keySet().iterator();
				while (keys.hasNext()) {
					View name = keys.next();
					wordsLocals.put(wordsViewLocals.get(name),
							name.getTop() + contactsView.findViewById(R.id.title_layout).getHeight());
				}
			}
		});
	}
	
	public void setSordsLocals()
	{
		sidebar.getLocationOnScreen(p);
		sidebarHeight = sidebar.getHeight();
		wordsLocals = new _侧边导航栏().getWordsMap();
		Iterator<View> keys = wordsViewLocals.keySet().iterator();
		while (keys.hasNext())
		{
			View name = keys.next();
			wordsLocals.put(wordsViewLocals.get(name), name.getTop() + contactsView.findViewById(R.id.title_layout).getHeight());
		}
	}
	public void addItem(LinearLayout parentView, List<Map<String, String>> list)
	{
		
		for (int i = 0; i < list.size(); i++)
		{
			String wdxxString = getMessageNum(list.get(i).get("code"), list.get(i).get("function_id"));
			View v = ViewTools.getView(context, R.layout.push_type_item, parent);
			setImg(list.get(i).get("face"), v.findViewById(R.id.img), list.get(i).get("n_title"));
			((TextView) v.findViewById(R.id.message)).setText(list.get(i).get("n_message"));
			((TextView) v.findViewById(R.id.date)).setText(DateTools.getDayString(DateTools.StringToDateYMDHMS(list.get(i).get("n_send_time")), new Date()));
			parentView.addView(v);
			((TextView) v.findViewById(R.id.text)).setText(list.get(i).get("n_title"));
			DragPointView func_xxjsq = (DragPointView) v.findViewById(R.id.xxjsq);
			XiaoYuanDianHelper.setText(func_xxjsq, wdxxString);
			v.setTag(list.get(i));
			func_xxjsq.setTag(list.get(i));
			func_xxjsq.setDragListencer(this);
			v.setOnClickListener(new OnClickListener()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void onClick(View view)
				{
					Map<String, String> data = (Map<String, String>) view.getTag();
					Intent intent = new Intent();
					switch (data.get("code"))
					{
					case BodyPush.PUSH_URL:
						intent.setAction(yh.app.mymessage.Message.class.getName());
						intent.putExtra("function_id", data.get("function_id"));
						break;
					case BodyPush.PUSH_TEXT:
						intent.setAction(yh.app.mymessage.Message.class.getName());
						intent.putExtra("function_id", data.get("function_id"));
						break;
					case BodyPush.PUSH_TEACH:
						intent.setAction(yh.app.mymessage.Message.class.getName());
						intent.putExtra("function_id", data.get("function_id"));
						break;
					case BodyAdd.ADD:
						intent.setAction(NewFriend.class.getName());
						break;
					case BodyChat.CHAT_TEXT:
						intent.setAction(liaotianjiemian.class.getName());
						intent.putExtra("friend_id", data.get("function_id"));
						break;
					default:
						break;
					}
					intent.setPackage(context.getPackageName());
					context.startActivity(intent);
				}
			});
		}
	}

	
	private String getMessageNum(String code, String function_id)
	{
		try
		{
			switch (code)
			{
			case BodyPush.PUSH_TEXT:
				return new SqliteHelper().rawQuery("select count(*) as num from client_notice where function_id=? and userid=? and read='false'", function_id, Constants.number).get(0).get("num");
			case BodyPush.PUSH_URL:
				return new SqliteHelper().rawQuery("select count(*) as num from client_notice where function_id=? and userid=? and read='false'", function_id, Constants.number).get(0).get("num");
			case BodyAdd.ADD:
				return new SqliteHelper().rawQuery("select count(*) as num from addFriend where userid=? and isread='false'", Constants.number).get(0).get("num");
			case BodyChat.CHAT_TEXT:
				return new SqliteHelper().rawQuery("select count(*) as num from lt where userid=? and friend_id = ? and isread='false'", Constants.number, function_id).get(0).get("num");
			case BodyPush.PUSH_TEACH:
				return new SqliteHelper().rawQuery("select count(*) as num from client_notice where function_id=? and userid=? and read='false'", function_id, Constants.number).get(0).get("num");
				
			default:
				return "0";
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "0";
	}
	


	private void setImg(String url, View view, String name)
	{
		ImageAt at = new ImageAt(url, view, context, name, R.drawable.default_function_icon);
		at.executeOnExecutor(Executors.newCachedThreadPool());
	}

	public List<Map<String, String>> getItemList()
	{
		return new SqliteHelper().rawQuery("select t1.* from client_notice_newest as t1 where t1.userid=? order by n_send_time desc", new String[]
		{
				Constants.number
		});
	}

	private TextView home_message_title_right, home_message_title_left;

	public void freshSchoolNotificationNum()
	{
		String school_notification_num = new SqliteHelper().rawQuery("select count(*) as count from tzgg as t1 where t1.userid = ? and t1.isread = 'false' and t1.func_id in (select t2.functionid from button as t2 where t2.function_tybj = '1' and t2.userid = ?)", new String[]
		{
				Constants.number, Constants.number
		}).get(0).get("count");
		if (school_notification_num.equals("0"))
		{
			contactsView.findViewById(new RHelper().getId(context, "sorts" + 3)).findViewById(R.id.jsq).setVisibility(View.INVISIBLE);
		} else
		{
			contactsView.findViewById(new RHelper().getId(context, "sorts" + 3)).findViewById(R.id.jsq).setVisibility(View.VISIBLE);
			((TextView) contactsView.findViewById(new RHelper().getId(context, "sorts" + 3)).findViewById(R.id.jsq)).setText(school_notification_num);
		}
	}

	private void setTitle(View view)
	{
		home_message_title_left = ((TextView) view.findViewById(R.id.home_message_title_left));
		home_message_title_left.setOnClickListener(this);
		home_message_title_right = ((TextView) view.findViewById(R.id.home_message_title_right));
		home_message_title_right.setOnClickListener(this);
	}

	/*
	 * 消息选项卡
	 */
	public void setView(int i)
	{
		((LinearLayout) push_type_main.findViewById(R.id.main_layout)).removeAllViews();
		switch (i)
		{
		case 0:
			((LinearLayout) push_type_main.findViewById(R.id.main_layout)).addView(messageViewLayout);
			break;
		case 1:
			((LinearLayout) push_type_main.findViewById(R.id.main_layout)).addView(contactsView);
			break;
		default:
			break;
		}
	}

	public void setListener()
	{
		// friend_ScrollView.setScrollViewListener(new ScrollViewListener()
		// {
		// @Override
		// public void onScrollChanged(int x, int y, int oldx, int oldy)
		// {
		// if (y >= contactsViewLayout.getHeight() -
		// friend_ScrollView.getHeight() - 100)
		// {
		// setFriend();
		// }
		// }
		// });
		//添加好友
		push_type_main.findViewById(R.id.add_friend).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					Intent intent = new Intent();
					intent.setAction("yh.app.quanzi.tjhy.Quanzi_UI_tjhy");
					intent.setPackage(context.getPackageName());
					context.startActivity(intent);
				} catch (Exception e)
				{
				}
			}
		});
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.home_message_title_left:
			home_message_title_left.setBackgroundResource(R.drawable.button_switch_white_tl_bl_corners3dp_pressed);
			home_message_title_left.setTextColor(0xff393939);
			home_message_title_right.setBackgroundResource(R.drawable.button_switch_white_tr_br_corners3dp_nopress);
			home_message_title_right.setTextColor(0xffdadada);
			setView(0);
			break;
		case R.id.home_message_title_right:
			home_message_title_left.setBackgroundResource(R.drawable.button_switch_white_tl_bl_corners3dp_nopress);
			home_message_title_left.setTextColor(0xffdadada);
			home_message_title_right.setBackgroundResource(R.drawable.button_switch_white_tr_br_corners3dp_pressed);
			home_message_title_right.setTextColor(0xff393939);
			setView(1);
			break;
		default:
			break;
		}
	}

	@Override
	public void onDragOut(View view) {
		// TODO Auto-generated method stub

		Map<String, String> data = (Map<String, String>) view.getTag();
		if (BodyAdd.ADD.equals(data.get("code")))
		{
			new SqliteHelper().execSQL("update addfriend set isread='true' where userid=?", Constants.number);
		} else if (BodyChat.CHAT_READD.equals(data.get("code")))
		{
			new SqliteHelper().execSQL("update addfriend set isread='true' where userid=?", Constants.number);
		} else if (BodyPush.PUSH_TEXT.equals(data.get("code")) || BodyPush.PUSH_URL.equals(data.get("code")) || BodyPush.PUSH_TEACH.equals(data.get("code")))
		{
			new SqliteHelper().execSQL("update client_notice set read='true' where userid=? and function_id=?", Constants.number, data.get("function_id"));
		} else if (BodyChat.CHAT_TEXT.equals(data.get("code")))
		{
			new SqliteHelper().execSQL("update lt set isread='true' where userid=? and friend_id=?", Constants.number, data.get("function_id"));
		}
		try {
			((home) context).setXiaoYuanDian();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	

	

}
