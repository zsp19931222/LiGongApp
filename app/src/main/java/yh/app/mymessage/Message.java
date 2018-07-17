package yh.app.mymessage;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyPush;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.DateString;import yh.app.appstart.lg.R;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Tiaozhuan;
import yh.app.tool.ToastShow;
import yh.app.tool.image;
import yh.app.utils.PopWindowHelper;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import yh.app.web.WebActivity;
import 云华.智慧校园.工具.DateTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.MessageDataBaseFresh;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.自定义控件.GifHelper;

/**
 * 包 名:yh.app.mydiary 类 名:yh.app.mydiary.Message.java 功 能:通知公告
 * 
 * @author 阙海林
 * @version 1.0
 * @date 2015-8-6
 */
@SuppressWarnings("unchecked")
public class Message extends ActivityPortrait {
	public LinearLayout wybs_list, no_message_layout;
	private List<View> ViewList = new ArrayList<View>();
	private List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
	private Context context;
	private View Gif;
	@SuppressWarnings("unused")
	private int width, height;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.tzgg);
		initView();
		initAction();
		setfunction_id(getIntent().getStringExtra("function_id"));
		new SqliteHelper().execSQL("update client_notice set read='true' where userid=? and function_id=?",
				Constants.number, getIntent().getStringExtra("function_id"));
		new SqliteHelper().execSQL("update client_notice_messagelist set read='true' where userid=? and function_id=?",
				Constants.number, getIntent().getStringExtra("function_id"));
		// new SqliteHelper().execSQL("update newest_message set id='0' where
		// userid=? and id=?", Constants.number,
		// getIntent().getStringExtra("function_id"));
	};

	private TopBarHelper tbh;
	private String usertype = "1";
	private String classcount = "0";

	private void initAction() {
		tbh = new TopBarHelper(this, findViewById(R.id.topbar_layout));
		try {
			classcount = new SqliteHelper().rawQuery("select count(*) as count from classlist where userid=? and id=?",
					Constants.number, getIntent().getStringExtra("function_id")).get(0).get("count");
			usertype = new SqliteHelper().rawQuery("select usertype from usertype where userid=?", Constants.number)
					.get(0).get("usertype").toString();
		} catch (Exception e) {
		}
		if (usertype.equals("2") && !classcount.equals("0")) {
			tbh.getButton(TopBarHelper.BUTTON_RIGHT).setBackgroundResource(0);
			tbh.getRightView().setBackgroundResource(R.drawable.qzdetail);
			tbh.getExtraView().setBackgroundResource(R.drawable.editing);
		} else if (usertype.equals("1") && !classcount.equals("0")) {
			tbh.getButton(TopBarHelper.BUTTON_RIGHT).setBackgroundResource(0);
			tbh.getRightView().setBackgroundResource(R.drawable.qzdetail);
		}
		tbh.setTitle(getTitle(getIntent().getStringExtra("function_id"))).setOnClickLisener(new OnClickLisener() {
			@Override
			public void setRightOnClick() {
				if (!classcount.equals("0")) {
					// 学生
					Intent intent = new Intent();
					intent.setAction("yh.app.contacts.WDBJXQJM");
					intent.setPackage(context.getPackageName());
					intent.putExtra("function_id", getIntent().getStringExtra("function_id"));
					context.startActivity(intent);
					return;
				} else {
//					Intent intent = new Intent();
//					intent.setAction("com.example.app3.HomePageActivity");
//					intent.setPackage(context.getPackageName());
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					context.startActivity(intent);
					((Activity) context).finish();
				}
			}

			@Override
			public void setLeftOnClick() {
				finish();
			}

			@Override
			public void setExtraOnclick() {
				if (usertype.equals("2") && !classcount.equals("0")) {
					// 教师
					Intent intent = new Intent();
					intent.setAction(yh.app.contacts.SendNotificationByTeacher.class.getName());
					intent.setPackage(context.getPackageName());
					intent.putExtra("function_id", getIntent().getStringExtra("function_id"));
					context.startActivity(intent);
					return;
				}
			}
		});
		getPush();
	}

	private String getTitle(String function_id) {
		List<Map<String, String>> list = new SqliteHelper().rawQuery(
				"select name from (SELECT name,functionid FROM  button union SELECT classname,id FROM classlist) as t where functionid=?",
				function_id);
		if (list != null && list.size() > 0) {
			return list.get(0).get("name");
		} else
			return "消息列表";
	}

	@SuppressLint("HandlerLeak")
	public void initView() {
		wybs_list = (LinearLayout) findViewById(R.id.inner);
		context = this;
		no_message_layout = (LinearLayout) findViewById(R.id.no_message_layout);
		wybs_list.removeAllViews();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		width = outMetrics.widthPixels;
		show();
	}

	private String function_id;

	public static Handler getPush;

	public void getPush() {
		getPush = new Handler() {
			@Override
			public void handleMessage(android.os.Message msg) {
				no_message_layout.setVisibility(View.GONE);
				View view = setItem(MapTools.getMapToBundle(msg.getData()));
				wybs_list.addView(view, 0);
				ViewList.add(0, view);
				maplist.add(0, getData().get(0));
			};
		};
	}

	private List<Map<String, String>> getData() {
		return new SqliteHelper().rawQuery(
				"select n_id as tzggid,n_message as message,n_send_time as fssj,read as isread,code as type,n_url as url,function_id as func_id,n_title as title,n_picpath as picpath from client_notice where userid=? and function_id=? order by n_send_time",
				new String[] { Constants.number, function_id });
		// return new SqliteHelper().rawQuery(String.format("select
		// tzggid,message,fssj,isread,type,url,func_id ,bjzd ,fqbm,title from
		// tzgg where userid='%s' and func_id='%s' order by fssj", new Object[]
		// {
		// Constants.number, function_id
		// }));
	}

	public void show() {
		function_id = getIntent().getStringExtra("function_id");
		maplist = getData();
		for (int i = 0; i < maplist.size(); i++) {
			wybs_list.addView(setItem(maplist.get(i)));
			no_message_layout.setVisibility(View.GONE);
		}
		if (wybs_list.getChildCount() == 0) {
			Gif = new GifHelper().addGif(no_message_layout, context);
			no_message_layout.setVisibility(View.VISIBLE);
		}
	}

	public View setItem(Map<String, String> map) {

		final View mView = ViewTools.getView(context, R.layout.test, wybs_list);
		Map<String, String> data = map;
		mView.setTag(data);
		ViewList.add(mView);

		mView.findViewById(R.id.layout).setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v1) {
				sc_height = sc.getY();
				new PopWindowHelper(context, wybs_list, mView, ((Map<String, String>) mView.getTag()).get("tzggid"))
						.show(function_id);
				return true;
			}
		});
		TextView wybs_title = (TextView) mView.findViewById(R.id.wybs_list_sub_title);
		TextView wybs_message = (TextView) mView.findViewById(R.id.txt_message_content);
		TextView wybs_date = (TextView) mView.findViewById(R.id.wybs_list_sub_date);

		// 消息详情图片
		ImageView wybs_imgage = (ImageView) mView.findViewById(R.id.img_messagedetails);
		
		LayoutParams lp = wybs_imgage.getLayoutParams();
		lp.width =width;
		lp.height=LayoutParams.WRAP_CONTENT;
		wybs_imgage.setLayoutParams(lp);
		wybs_imgage.setMaxWidth(width);
		wybs_imgage.setMaxHeight(width+20);
		
		
		// wybs_imgage.getViewTreeObserver().addOnGlobalLayoutListener(new
		// OnGlobalLayoutListener() {
		//
		// @Override
		// public void onGlobalLayout() {
		// // 获得标题高度
		// height = wybs_imgage.getWidth()/2;
		// }
		// });
		// LayoutParams params=wybs_imgage.getLayoutParams();
		// params.height=height;
		// wybs_imgage.setLayoutParams(params);

		// 加载消息图片
		String pciurl;
		try {
			pciurl = URLDecoder.decode(map.get("picpath"), "utf-8");
			GlideLoadUtils.getInstance().glideLoad(context, pciurl,wybs_imgage,R.drawable.np);

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		wybs_title.setText(map.get("title"));
		wybs_message.setText(map.get("message"));
		String fssj = map.get("fssj");
		// 消息时间
		wybs_date.setHint(DateTools.getTime(fssj));
		context.getPackageName();
		mView.setClickable(false);
		// 点击打开消息
		mView.findViewById(R.id.layout).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v1) {
				Map<String, String> map = null;
				try {
					map = (Map<String, String>) mView.getTag();
				} catch (Exception e) {
					new ToastShow().show(context, "打开失败");
					return;
				}
				new SqliteHelper().execSQL("UPDATE tzgg SET isread='true' WHERE tzggid=? and userid=?",
						new Object[] { map.get("tzggid"), Constants.number });
				MessageDataBaseFresh.freshPush(function_id);
				try {
					String message = ((TextView) mView.findViewById(R.id.message)).getText().toString();
					((TextView) mView.findViewById(R.id.message)).setText("");
					((TextView) mView.findViewById(R.id.message)).setHint(message);
					v1.invalidate();
				} catch (Exception e) {
				}
				String code = map.get("type");
				if (code.equals("101") || code.equals(BodyPush.PUSH_TEACH)) {
					new Tiaozhuan(context, "yh.app.mymessage.tzggxq", ExampleApplication.getAppPackage(),
							Intent.FLAG_ACTIVITY_CLEAR_TOP, new String[][] { { "id", map.get("tzggid") } });
				} else if (code.equals("102")) {
					new Tiaozhuan(context, WebActivity.class.getName(), ExampleApplication.getAppPackage(),
							Intent.FLAG_ACTIVITY_CLEAR_TOP, new String[][] { { "id", map.get("tzggid") } });
				}
			}
		});
		if (map.get("isread").toString().equals("true")) {
			wybs_message.setTextColor(0xFF969696);
		}
		return mView;
	}

	// private String showTime(String fssj)
	// {
	// try
	// {
	// Date d = new DateString("yyyy-MM-dd HH:mm:ss").StringToDate(fssj);
	// int time = DateTools.getBetweenDays(d, new Date());
	// if (time > 1)
	// return new DateString("MM-dd").DateToString(d);
	// else
	// return DateTools.getDayString(d, new Date()) + " " + new
	// DateString("HH:mm").DateToString(d);
	// } catch (Exception e)
	// {
	// }
	// return fssj;
	// }

	@Override
	protected void onRestart() {
		super.onRestart();
		initView();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		return super.onKeyDown(keyCode, event);
	}
}
