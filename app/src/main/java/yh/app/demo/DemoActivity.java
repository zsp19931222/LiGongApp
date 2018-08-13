//package yh.app.demo;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import com.android.volley.VolleyError;
//import com.example.jpushdemo.ApnsStart;
//import com.example.jpushdemo.ExampleApplication;
//import com.example.jpushdemo.helper.Receiver;
//import com.yhkj.cqgyxy.R;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import cn.jpush.android.api.JPushInterface;
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.adapter.HomeFragmentPagerAdapter;
//import org.androidpn.push.Constants;
//
//import yh.app.fragment.ApplyFragement;
//import yh.app.fragment.DataReportFragment;
//import yh.app.fragment.MainFragment;
//import yh.app.fragment.MessageFragment;
//import yh.app.fragment.PersonalFragment;
//
//import yh.app.quanzitool.pinyin;
//import yh.app.tool.FunctionAT;
//import yh.app.tool.MD5;
//import yh.app.tool.SqliteHelper;
//import yh.app.tool.Term;
//import yh.app.tool.Ticket;
//import yh.app.tool.ToastShow;
//import yh.app.utils.NotificationManager;
//import yh.app.utils.VolleyInterface;
//import yh.app.utils.BaseVolleyRequest;
//import yh.tool.widget.DragPointView;
//import yh.tool.widget.DragPointView.OnDragListencer;
//import yh.tool.widget.MonitorScrollView;
//import yh.tool.widget.MonitorScrollView.OnScrollListener;
//import 云华.智慧校园.工具.IsNull;
//import 云华.智慧校园.工具.JsonTools;
//import 云华.智慧校园.工具.MapTools;
//import 云华.智慧校园.工具.NetResultHelper;
//import 云华.智慧校园.工具.XiaoYuanDianHelper;
//import 云华.智慧校园.工具._链接地址导航;
//
///**
// * 测试专用类
// *
// * @author anmin
// *
// */
//public class DemoActivity extends ActivityPortrait implements OnClickListener,OnScrollListener{
//	private LinearLayout home_shujubaogao_layout;// 数据报告
//	private ImageView home_shujubaogao_img;
//	private TextView home_shujubaogao_txt;
//
//	private LinearLayout home_yingyong_layout;// 应用
//	private ImageView home_yingyong_img;
//	private TextView home_yingyong_text;
//
//	private LinearLayout home_shouye_layout;// 主页
//	private ImageView home_shouye_img;
//	private TextView home_shouye_text;
//
//	private LinearLayout home_tongzhi_layout;// 消息
//	private ImageView home_tongzhi_img;
//	private TextView home_tongzhi_text;
//	private DragPointView xxjsq;
//
//	private LinearLayout home_shezhi_layout;// 个人中心
//	private ImageView home_shezhi_img;
//	private TextView home_shezhi_text;
//
//	private ViewPager vp_home;
//	private HomeFragmentPagerAdapter homepageadapter;
//	private DataReportFragment dataReportFragment;// 数据报告
//	private ApplyFragement applyFragment;// 应用
//	private MainFragment mainFragment;// 主页
//	private MessageFragment messageFragment;// 消息
//	private PersonalFragment personalFragment;// 个人中心
//	private List<Fragment> fragmentList;
//
//
//	private SqliteHelper sqhelper;//数据库
//	private Context content;
//	private String usertype;
//	public static ApnsStart apnsStart;
//
//	private boolean isCarryout = false;// 避免获取好友列表失败时报错
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_demo);
//		initView();
//		//获得票据
//		getTicket();
//		initFragment();
//		onFragmentProcess();
//		initAction();
//		new Term().doit();
//		initFunctionData();
//		//通讯录数据
//		initContants();
//	}
//
//	/**
//	 * 加载应用网络数据
//	 */
//	private void initFunctionData() {
//		String ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
//		final Map<String, String> map = new HashMap<>();
//		map.put("userid", Constants.number);
//		map.put("ticket", ticket);
//		map.put("Version", "0");
//
//		BaseVolleyRequest.RequestPost(_链接地址导航.UIA.功能列表.getUrl(), map, new VolleyInterface() {
//
//			@Override
//			public void onMySuccess(String result) {
//				if (null != result) {
//
//					String json = null;
//					// 替换反斜杠
//					json = result.replace("\\", "");
//					// 取掉引号
//					json = json.substring(1, json.length() - 1);
//
//					// // FIXME 获取推送功能列表
//					new FunctionAT(null).getFunctionList();
//					// 小圆点
//					setXiaoYuanDian();
//					// 极光推送
//					initPush();
//					// 获得好友
//					initFriend();
//				}
//			}
//
//			@Override
//			public void onMyError(VolleyError error) {
//				// TODO Auto-generated method stub
//			}
//		});
//
////		new FunctionAT(null).getFunctionList();
//	}
//
//
//	/**
//	 * 初始化通讯录数据
//	 */
//	private void initContants() {
//		// TODO Auto-generated method stub
//		Map<String, String> params = MapTools.buildMap(new String[][] { { "userid", Constants.number },
//				{ "function_id", "20150120" }, { "ticket", Ticket.getFunctionTicket("20150120") } });
//		BaseVolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {
//			@Override
//			public void onMySuccess(String result) {
//				try {
//					if (IsNull.isNotNull(result)) {
//						result = NetResultHelper.dealHJJResult(result);
//						JSONObject jso = new JSONObject(result);
//						if (jso.getBoolean("boolean")) {
//							JSONArray jsonArray = jso.getJSONArray("message");
//							for (int i = 0; i < jsonArray.length(); i++) {
//								sqhelper.execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)",
//										new Object[] { jsonArray.getJSONObject(i).getString("HYBH"),
//												jsonArray.getJSONObject(i).getString("HYBZ"),
//												IsNull.isNotNull(jsonArray.getJSONObject(i).getString("HYBZ")) ? pinyin
//														.getAllLetter(jsonArray.getJSONObject(i).getString("HYBZ"))
//														.substring(0, 1) : "#",
//												Constants.number });
//							}
//						}
//					}
////					categoryContactsViewUI();
//				} catch (Exception e) {
//					// TODO: handle exception
//				}
//			}
//
//			@Override
//			public void onMyError(VolleyError error) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//	/**
//	 * 初始化视图
//	 */
//	private void initView() {
//		sqhelper = ExampleApplication.getInstance().getSqliteHelper();
//		home_shujubaogao_layout = (LinearLayout) findViewById(R.id.home_shujubaogao_layout);
//		home_shujubaogao_layout.setOnClickListener(this);
//		home_shujubaogao_img = (ImageView) findViewById(R.id.home_shujubaogao_img);
//		home_shujubaogao_txt = (TextView) findViewById(R.id.home_shujubaogao_txt);
//
//		home_yingyong_layout = (LinearLayout) findViewById(R.id.home_yingyong_layout);
//		home_yingyong_layout.setOnClickListener(this);
//		home_yingyong_img = (ImageView) findViewById(R.id.home_yingyong_img);
//		home_yingyong_text = (TextView) findViewById(R.id.home_yingyong_text);
//
//		home_shouye_layout = (LinearLayout) findViewById(R.id.home_shouye_layout);
//		home_shouye_layout.setOnClickListener(this);
//		home_shouye_img = (ImageView) findViewById(R.id.home_shouye_img);
//		home_shouye_text = (TextView) findViewById(R.id.home_shouye_text);
//
//		home_tongzhi_layout = (LinearLayout) findViewById(R.id.home_tongzhi_layout);
//		home_tongzhi_layout.setOnClickListener(this);
//		home_tongzhi_img = (ImageView) findViewById(R.id.home_tongzhi_img);
//		home_tongzhi_text = (TextView) findViewById(R.id.home_tongzhi_text);
//
//		home_shezhi_layout = (LinearLayout) findViewById(R.id.home_shezhi_layout);
//		home_shezhi_layout.setOnClickListener(this);
//		home_shezhi_img = (ImageView) findViewById(R.id.home_shezhi_img);
//		home_shezhi_text = (TextView) findViewById(R.id.home_shezhi_text);
//
//		vp_home = (ViewPager) findViewById(R.id.vp_home);
//
//		//消息小圆点
//		xxjsq=(DragPointView) findViewById(R.id.xxjsq);
//
//		content=this;
//		usertype=Constants.user_type;
//
//
//
//	}
//
//	private void initFragment() {
//		dataReportFragment = new DataReportFragment();
//		applyFragment = new ApplyFragement();
//		mainFragment = new MainFragment();
//		messageFragment = new MessageFragment();
//		personalFragment = new PersonalFragment();
//
//		fragmentList = new ArrayList<>();
//		fragmentList.add(dataReportFragment);
//		fragmentList.add(applyFragment);
//		fragmentList.add(mainFragment);
//		fragmentList.add(messageFragment);
//		fragmentList.add(personalFragment);
//
//		homepageadapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
//		vp_home.setOffscreenPageLimit(3);// 设置缓存多少个
//		vp_home.setAdapter(homepageadapter);
//		vp_home.setCurrentItem(2,false);// 默认显示页
//
//
////		// 消息选项
////		messageFragment.categoryMessageViewUI();
////		messageFragment.categoryContactsViewUI();
//	}
//
//
//
//	private void onFragmentProcess() {
//		vp_home.addOnPageChangeListener(new OnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int arg0) {
//				// TODO Auto-generated method stub
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2) {
//				// TODO Auto-generated method stub
//				ViewPager(arg0);
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//
//	/**
//	 * 恢复图片初始值
//	 */
//	private void reset() {
//		// 数据报告
//		home_shujubaogao_img.setBackgroundResource(R.drawable.ico_report_normal);
//		home_shujubaogao_txt.setTextColor(getResources().getColor(R.color.color_black));
//		// 应用
//		home_yingyong_img.setBackgroundResource(R.drawable.ico_apply_normal);
//		home_yingyong_text.setTextColor(getResources().getColor(R.color.color_black));
//		// 首页
//		home_shouye_img.setBackgroundResource(R.drawable.nav1);
//		home_shouye_text.setTextColor(getResources().getColor(R.color.color_black));
//		// 消息
//		home_tongzhi_img.setBackgroundResource(R.drawable.nav2);
//		home_tongzhi_text.setTextColor(getResources().getColor(R.color.color_black));
//		// 个人中心
//		home_shezhi_img.setBackgroundResource(R.drawable.ico_personal_normal);
//		home_shezhi_text.setTextColor(getResources().getColor(R.color.color_black));
//	}
//
//	/**
//	 * 改变主页底部按钮状态颜色
//	 *
//	 * @param i
//	 */
//	private void ViewPager(int i) {
//		reset();
//		switch (i) {
//		case 0:
//			// 数据报告
//			home_shujubaogao_img.setBackgroundResource(R.drawable.ico_report_pressed);
//			home_shujubaogao_txt.setTextColor(this.getResources().getColor(R.color.button));
//			break;
//		case 1:
//			// 应用
//			home_yingyong_img.setBackgroundResource(R.drawable.ico_apply_normal);
//			home_yingyong_text.setTextColor(getResources().getColor(R.color.button));
//			break;
//		case 2:
//			// 主页
//			home_shouye_img.setBackgroundResource(R.drawable.nav1);
//			home_shouye_text.setTextColor(getResources().getColor(R.color.button));
//			break;
//		case 3:
//			// 消息
//			home_tongzhi_img.setBackgroundResource(R.drawable.nav2);
//			home_tongzhi_text.setTextColor(getResources().getColor(R.color.button));
//			break;
//		case 4:
//			// 个人中心
//			home_shezhi_img.setBackgroundResource(R.drawable.ico_personal_normal);
//			home_shezhi_text.setTextColor(getResources().getColor(R.color.color_black));
//			break;
//		default:
//			break;
//		}
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//		reset();
//		switch (arg0.getId()) {
//		case R.id.home_shujubaogao_layout:
//			// 点击数据报告
//			home_shujubaogao_img.setBackgroundResource(R.drawable.ico_report_normal);
//			home_shujubaogao_txt.setTextColor(getResources().getColor(R.color.button));
//			vp_home.setCurrentItem(0,false);
//			break;
//		case R.id.home_yingyong_layout:
//			// 点击应用
//			home_yingyong_img.setBackgroundResource(R.drawable.ico_apply_normal);
//			home_yingyong_text.setTextColor(getResources().getColor(R.color.button));
//			vp_home.setCurrentItem(1,false);
//			break;
//		case R.id.home_shouye_layout:
//			// 点击主页
//			home_shouye_img.setBackgroundResource(R.drawable.nav1);
//			home_shouye_text.setTextColor(getResources().getColor(R.color.button));
//			vp_home.setCurrentItem(2,false);
//			break;
//		case R.id.home_tongzhi_layout:
//			// 点击消息
//			home_tongzhi_img.setBackgroundResource(R.drawable.nav2);
//			home_tongzhi_text.setTextColor(getResources().getColor(R.color.button));
//			vp_home.setCurrentItem(3,false);
//			break;
//		case R.id.home_shezhi_layout:
//			// 点击个人中心
//			home_shezhi_img.setBackgroundResource(R.drawable.ico_personal_normal);
//			home_shezhi_text.setTextColor(getResources().getColor(R.color.color_black));
//			vp_home.setCurrentItem(4,false);
//			break;
//		default:
//			break;
//		}
//
//	}
//	/**
//	 * 推送聊天信息
//	 */
//	private void initPush() {
//		if (!"4".equals(usertype)) {
//			apnsStart = new ApnsStart(getApplicationContext());
//			apnsStart.start();
//			JPushInterface.setDebugMode(false);
//			JPushInterface.resumePush(content);
//			JPushInterface.init(getApplicationContext());
//		}
//	}
//	/**
//	 * 加载好友列表
//	 */
//	private void initFriend() {
//		String ticke = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code + Constants.code);
//		Map<String, String> params = MapTools.buildMap(
//				new String[][] { { "userid", Constants.number }, { "function_id", "20150120" }, { "ticket", ticke } });
//
//		BaseVolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {
//
//			@Override
//			public void onMySuccess(String result) {
//				if (result == null) {
//					return;
//				}
//				try
//
//				{
//					result = result.substring(1, result.length() - 1).replace("\\", "");
//					if (new JSONObject(result).getBoolean("boolean")) {
//						List<Map<String, String>> SQ_HYLB = JsonTools
//								.getListMapBtJsonArray(new JSONObject(result).getJSONArray("message"));
//						new SqliteHelper().execSQL("delete from friend");
//						for (int i = 0; i < SQ_HYLB.size(); i++) {
//							if (IsNull.isNotNull(SQ_HYLB.get(i).get("HYBZ"))) {
//								new SqliteHelper().execSQL(
//										"replace into friend(FRIEND_ID,name,szm,userid) values(?, ?, ?, ?)",
//										new Object[] { SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBZ"),
//												pinyin.getSpells(SQ_HYLB.get(i).get("HYBZ")).substring(0, 1),
//												Constants.number });
//							} else {
//								new SqliteHelper().execSQL(
//										"replace into friend(FRIEND_ID,name,szm,userid) values(?, ?, ?, ?)",
//										new Object[] { SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBH"),
//												pinyin.getSpells(SQ_HYLB.get(i).get("HYBH")).substring(0, 1),
//												Constants.number });
//							}
//						}
//					  isCarryout = true;
////						view4 = MU.getPushTypeMain();// 消息
//
////						Home_ViewList.set(3, view4);
//
//						// 消息选项
//						BaseVolleyRequest.RequestPost(_链接地址导航.PUSH.离线接口.getUrl(),
//								Receiver.getCallBackParames(Constants.number, ""), new VolleyInterface() {
//							@Override
//							public void onMySuccess(String result) {
//								// TODO Auto-generated method stub
//								new NotificationManager().dealOffine(result);
//								messageFragment.categoryMessageViewUI();
//								setXiaoYuanDian();
//							}
//
//							@Override
//							public void onMyError(VolleyError error) {
//								// TODO Auto-generated method stub
//
//							}
//						});
////						MU.categoryContactsViewUI();
////						MU.setView(0);
//					} else {
//						new ToastShow().show(content, "好友列表获取失败，请稍后重试");
//					}
//				} catch (Exception e) {
//					new ToastShow().show(content, "好友列表获取失败，请稍后重试");
//				}
//			}
//
//			@Override
//			public void onMyError(VolleyError error) {
//				// TODO Auto-generated method stub
//
//			}
//		});
//	}
//
//	public void setXiaoYuanDian() {
//		// XiaoYuanDianHelper.setText(xxjsq, new SqliteHelper().rawQuery("select
//		// count(*)", "").get(0).get("num"));
//		String noticeNum = sqhelper
//				.rawQuery("select count(*) num from client_notice where read='false' and userid=?", Constants.number)
//				.get(0).get("num");
//		String addNum = sqhelper
//				.rawQuery("select count(*) num from addfriend where isread='false' and userid=?", Constants.number)
//				.get(0).get("num");
//		String ltNum = sqhelper
//				.rawQuery("select count(*) num from lt where isread='false' and userid=?", Constants.number).get(0)
//				.get("num");
//		XiaoYuanDianHelper.setText(xxjsq,
//				(Integer.valueOf(noticeNum) + Integer.valueOf(addNum) + Integer.valueOf(ltNum)) + "");
//	}
//
//	@SuppressLint("HandlerLeak")
//	private void initAction() {
//
//		Constants.pushHandler = new Handler() {
//			@Override
//			public void handleMessage(android.os.Message msg) {
//				messageFragment.categoryMessageViewUI();
//				messageFragment.categoryContactsViewUI();
//				for (int i = 1; i <= 3; i++) {
//					messageFragment.setJSQ(i);
//				}
//				setXiaoYuanDian();
//			}
//		};
//		xxjsq.setDragListencer(new OnDragListencer() {
//			@Override
//			public void onDragOut(View view) {
//				sqhelper.execSQL("update client_notice set read='true' where userid=?", Constants.number);
//				sqhelper.execSQL("update addfriend set isread='true' where userid=?", Constants.number);
//				sqhelper.execSQL("update lt set isread='true' where userid=?", Constants.number);
//				messageFragment.categoryMessageViewUI();
//				messageFragment.categoryContactsViewUI();
//			}
//		});
//	}
//	/**
//	 * 获得票据
//	 */
//	private void getTicket() {
//		Map<String, String> map = new HashMap<>();
//		map.put("userid", Constants.number);
//		BaseVolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.appticketurl.getUrl(), map, new VolleyInterface() {
//
//			@Override
//			public void onMySuccess(String result) {
//			}
//
//			@Override
//			public void onMyError(VolleyError error) {
//				// TODO Auto-generated method stub
//			}
//		});
//	}
//
//	@Override
//	protected void onStart() {
//		// TODO Auto-generated method stub
//
//
//		super.onStart();
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		if (isCarryout) {
//			messageFragment.categoryContactsViewUI();
//			messageFragment.categoryMessageViewUI();
//			for (int i = 1; i <= 3; i++) {
//				messageFragment.setJSQ(i);
//			}
//		}
//		setXiaoYuanDian();
//		initPush();
//		super.onResume();
//	}
//
//
//	@Override
//	public void onScroll(MonitorScrollView view, int x, int y, int oldx, int oldy) {
//		// TODO Auto-generated method stub
//
//	}
//}
