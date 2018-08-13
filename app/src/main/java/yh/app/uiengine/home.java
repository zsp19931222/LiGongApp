package yh.app.uiengine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.example.jpushdemo.ApnsStart;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.helper.Receiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.data.JPushLocalNotification;
import yh.app.acticity.AppManage;
import yh.app.acticity.CreateTargetAvtivity;
import yh.app.acticity.MubiaokuActivity;
import yh.app.activitytool.ActivityPortrait;
import yh.app.adapter.OtherApplyAdapter;

import com.yhkj.cqgyxy.R;
import yh.app.model.ApplyModel;
import yh.app.model.DAModel;
import yh.app.notification.Notification1;
import yh.app.quanzitool.pinyin;
import yh.app.tool.FunctionAT;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Term;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.utils.AnimationUtil;
import yh.app.utils.DataCleanManger;
import yh.app.utils.JsonUtils;
import yh.app.utils.LogUtils;
import yh.app.utils.MessageUtils;
import yh.app.utils.NotificationManager;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.utils.WebUtils;
import yh.tool.widget.DragPointView;
import yh.tool.widget.FloatingDraftButton;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.DragPointView.OnDragListencer;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.MonitorScrollView.OnScrollListener;
import yh.tool.widget.MyListview;
import yh.tool.widget.SlideShowView;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.NetResultHelper;
import 云华.智慧校园.工具.SystemUtils;
import 云华.智慧校园.工具.XiaoYuanDianHelper;
import 云华.智慧校园.工具._链接地址导航;

public class home extends ActivityPortrait implements View.OnClickListener, OnScrollListener {
    // 首页
    private LinearLayout home1_shouye_layout;
    private ImageView home1_shouye_img;
    private TextView home1_shouye_text;
    // 应用
    private LinearLayout home1_yingyong_layout;
    private ImageView home1_yingyong_img;
    private TextView home1_yingyong_text;
    // 消息
    private LinearLayout home1_tongzhi_layout;
    private ImageView home1_tongzhi_img;
    private TextView home1_tongzhi_text;
    // 个人中心
    private LinearLayout home1_shezhi_layout;
    private ImageView home1_shezhi_img;
    private TextView home1_shezhi_text;
    // 数据报告
    private LinearLayout home1_shujubaogao_layout;
    private ImageView home1_shujubaogao_img;
    private TextView home1_shujubaogao_text;
    // 用于装应用数据
    private MyListview list_apply_otherapp;

    private List<View> Home_ViewList;
    private LinearLayout home1_function_layout;
    private RelativeLayout parent;

    private String usertype;
    private Context context;
    public static ApnsStart apnsStart;
    public DragPointView xxjsq;
    public static int currentView = 0;
    private long firstTime = 01;
    public static int homeViewCount;

    private LoadDiaog loadDiaog;
    public static Activity homeAct = null;
    private View view1; // 报告
    private View view2; // 应用
    private View view3; // 首页
    public View view4; // 消息
    private View view5; // 个人中心

    private LinearLayout home3_quanzi_layout;
    private int quanzisl = 0;
    private RequestQueue requesqeue;

    private OtherApplyAdapter otherappdatapter;
    private ApplyModel appmodel;
    private List<ApplyModel.OTHERAPPBean> otherlist;// 推荐应用集合
    private android.app.FragmentManager fragmentManager;
    // 数据报告
    private LinearLayout weblayout;
    private WebUtils webutil;

    // 应用
    private MonitorScrollView monitorScrollView;
    private RelativeLayout rlayout_title;
    TranslateAnimation mHiddenAction, mShowAction;
    private TextView txt_appmanage;
    private int titleHeight = 0;// 应用标题栏高度

    // 悬浮按钮
    private FloatingDraftButton floatingDraftButtonmain;
    private Button btn_chuanjianmubiao, btn_mubiaoku;
    private RelativeLayout ry_float_button;

    /**
     * 广告
     */
    private List<DAModel.ContentBean> dalist;
    private DAModel damoel;
    private SlideShowView da_viwe;
    private List<String> listda;
    private ViewFlipper home1_viewpager;

    // 工具类
    private boolean isCarryout = false;// 避免获取好友列表失败时报错
    private SqliteHelper sqhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//	if (IsNull.isNotNull(org.androidpn.push.Constants.xxmc))
//	{
//	    ExampleApplication.init();
//	}

        initActivity();

        // 控件视图
        initView();
        // 主页视图
        initHomeViewPager();
        if (Constants.isNetworkAvailable(context)) {

            initFunctionData();
        } else {
            initThisApplyData();
        }
        initAction();

        homeAct = this;

        new Term().doit();
        // 数据报告
        // initWebView();
        ViewPager(1);

        // 应用页
        Hidden();
        ShowView();
        /**
         * 初始化通讯录
         */
        initContants();
        // 加载广告
        initDA();

    }

    private void initContants() {
        // TODO Auto-generated method stub
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", Ticket.getFunctionTicket("20150120")
                        }
                });

        VolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    if (IsNull.isNotNull(result)) {
                        result = NetResultHelper.dealHJJResult(result);
                        JSONObject jso = new JSONObject(result);
                        if (jso.getBoolean("boolean")) {
                            JSONArray jsonArray = jso.getJSONArray("message");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                sqhelper.execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)", new Object[]
                                        {
                                                jsonArray.getJSONObject(i).getString("HYBH"), jsonArray.getJSONObject(i).getString("HYBZ"), IsNull.isNotNull(jsonArray.getJSONObject(i).getString("HYBZ")) ? pinyin.getAllLetter(jsonArray.getJSONObject(i).getString("HYBZ")).substring(0, 1) : "#", Constants.number
                                        });
                            }
                        }
                    }
                    MU.categoryContactsViewUI();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
    }

    /**
     * 广告轮播
     */
    private void initDA() {

        VolleyRequest.RequestGet(_链接地址导航.DC.获取广告栏.getUrl(), new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                if (null != result) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(result);
                        if (jsonObject.getBoolean("boolean")) {
                            damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
                            dalist = damoel.getContent();
                            if (dalist.size() > 0) {
                                da_viwe.setImageUris(dalist);
                            }
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void initFriend() {
        String ticke = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code + "96C02A18AB9D4A53AE1B210E300D72FC");
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", ticke
                        }
                });

        VolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                if (result == null) {
                    return;
                }
                try

                {
                    result = result.substring(1, result.length() - 1).replace("\\", "");

                    if (new JSONObject(result).getBoolean("boolean")) {
                        List<Map<String, String>> SQ_HYLB = JsonTools.getListMapBtJsonArray(new JSONObject(result).getJSONArray("message"));
                        new SqliteHelper().execSQL("delete from friend");
                        for (int i = 0; i < SQ_HYLB.size(); i++) {
                            if (IsNull.isNotNull(SQ_HYLB.get(i).get("HYBZ"))) {
                                new SqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid,handimg) values(?, ?, ?, ?,?)", new Object[]
                                        {
                                                SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBZ"), pinyin.getSpells(SQ_HYLB.get(i).get("HYBZ")).substring(0, 1), Constants.number, SQ_HYLB.get(i).get("FACEADDRESS")
                                        });
                            } else {
                                new SqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid,handimg) values(?, ?, ?, ?,?)", new Object[]
                                        {
                                                SQ_HYLB.get(i).get("HYBH"), SQ_HYLB.get(i).get("HYBH"), pinyin.getSpells(SQ_HYLB.get(i).get("HYBH")).substring(0, 1), Constants.number, SQ_HYLB.get(i).get("FACEADDRESS")
                                        });
                            }
                        }
                        MU = new MessageUtils(context, parent);
                        view4 = MU.getPushTypeMain();// 消息
                        isCarryout = true;
                        Home_ViewList.set(3, view4);

                        // 消息选项
                        VolleyRequest.RequestPost(_链接地址导航.PUSH.离线接口.getUrl(), Receiver.getCallBackParames(Constants.number, ""), new VolleyInterface() {
                            @Override
                            public void onMySuccess(String result) {

                                new NotificationManager().dealOffine(result);
                                MU.categoryMessageViewUI();
                                try {
                                    setXiaoYuanDian();
                                } catch (Exception e) {
                                    DataCleanManger.cleanDatabases(context);
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onMyError(VolleyError error) {
                            }
                        });
                        MU.categoryContactsViewUI();
                        MU.setView(0);
                    } else {
                        new ToastShow().show(context, "好友列表获取失败，请稍后重试");
                    }
                } catch (Exception e) {
                    new ToastShow().show(context, "好友列表获取失败，请稍后重试");
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private void initAction() {

        Constants.pushHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                MU.categoryMessageViewUI();
                MU.categoryContactsViewUI();
                for (int i = 1; i <= 3; i++) {
                    MU.setJSQ(i);
                }
                try {
                    setXiaoYuanDian();
                } catch (Exception e) {
                    DataCleanManger.cleanDatabases(context);
                }

            }
        };
        xxjsq.setDragListencer(new OnDragListencer() {
            @Override
            public void onDragOut(View view) {
                sqhelper.execSQL("update client_notice set read='true' where userid=?", Constants.number);
                sqhelper.execSQL("update client_notice_messagelist set read='true' where userid=?", Constants.number);
                sqhelper.execSQL("update addfriend set isread='true' where userid=?", Constants.number);
                sqhelper.execSQL("update lt set isread='true' where userid=?", Constants.number);
                MU.categoryMessageViewUI();
                MU.categoryContactsViewUI();
            }
        });
    }

    // 当前视图布局
    private void initActivity() {
        setContentView(R.layout.home);
    }

    /**
     * 极光推送
     */
    private void initPush() {
        if (!"4".equals(usertype)) {
            apnsStart = new ApnsStart(getApplicationContext());
            apnsStart.start();
            JPushInterface.setDebugMode(false);
            JPushInterface.resumePush(context);
            JPushInterface.init(getApplicationContext());
        }
    }

    public static int getHomeViewCount() {
        return homeViewCount;
    }

    /**
     * 初始化所有视图
     */
    private void initHomeViewPager() {
        LayoutInflater inFlater = LayoutInflater.from(this);
        view1 = inFlater.inflate(R.layout.layout_shujubaogao, parent, false);// 数据报告
        view2 = inFlater.inflate(R.layout.home1_, parent, false);// 应用
        // 主页
        view3 = new ZhuYeActivity(this).getMainView();
        // 个人中心
        view5 = new GeRenActivity(this).getMainView();
        // 应用
        list_apply_otherapp = (MyListview) view2.findViewById(R.id.list_apply_otherapp);
        da_viwe = (SlideShowView) view2.findViewById(R.id.da_view);
        // 自定义纵向滑动计算弹出标题
        monitorScrollView = (MonitorScrollView) view2.findViewById(R.id.srrolview_apply);
        monitorScrollView.setOnScrollListener(this);
        // 应用页标题
        rlayout_title = (RelativeLayout) view2.findViewById(R.id.rlayout_title);
        txt_appmanage = (TextView) view2.findViewById(R.id.txt_appmanage);

        // 应用管理
        txt_appmanage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳转到app管理
                Intent intentappmanage = new Intent(home.this, AppManage.class);
                startActivity(intentappmanage);
            }
        });
        rlayout_title.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 获得标题高度
                titleHeight = rlayout_title.getHeight();
            }
        });

        // 消息
        MU = new MessageUtils(context, parent);
        view4 = MU.getPushTypeMain();// 消息
        // 消息选项
        MU.categoryMessageViewUI();
        MU.categoryContactsViewUI();
        MU.setView(0);// 消息默认在消息页

        // 数据报告
        weblayout = (LinearLayout) view1.findViewById(R.id.web_shujubaogao);

        // 逻辑模块
        Home_ViewList = new ArrayList<View>();
        Home_ViewList.add(view1);
        Home_ViewList.add(view2);
        Home_ViewList.add(view3);
        Home_ViewList.add(view4);
        Home_ViewList.add(view5);
    }

    /**
     * 加载应用网络数据
     */
    private void initFunctionData() {
        loadDiaog.show();
        // doSuccess(JsonUtils.getJosn(context, "appdata.json"));

        String ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
        final Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        map.put("ticket", ticket);
        map.put("Version", "0");

        VolleyRequest.RequestPost(_链接地址导航.UIA.功能列表.getUrl(), map, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {

                String json = null;
                // 替换反斜杠
                json = result.replace("\\", "");
                // 取掉引号
                json = json.substring(1, json.length() - 1);

                doSuccess(json);
                // // FIXME 获取推送功能列表
                new FunctionAT(null).getFunctionList();
                // 极光推送
                initPush();
                // 获得好友
                initFriend();

                // 小圆点
                try {
                    setXiaoYuanDian();
                } catch (Exception e) {
                    DataCleanManger.cleanDatabases(context);
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                initThisApplyData();
            }
        });

    }

    /**
     * 加载本地网络数据
     */
    private void initThisApplyData() {
        List<Map<String, String>> listmap = sqhelper.rawQuery("select  appjson from yhappmanagpublic where userid=?", new String[]
                {
                        Constants.number
                });
        if (listmap.size() > 0) {

            String appljson = listmap.get(0).get("appjson");
            if (null != appljson) {

                try {
                    doSuccess(appljson);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 加载数据报告暂时未用
     */
    private void initWebView() {
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String web_ticket = jsonObject.getString("ticket");
                    if (null != web_ticket) {
                        // 数据报告
                        webutil.onWebLoad(weblayout, _链接地址导航.WDDXSERVER.weburl_shujubaogao.getUrl() + Constants.number + "&ticket=" + web_ticket + "");
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }

        });
    }

    // 应用数据绑定
    public void doSuccess(String s) {
        try {
            appmodel = ExampleApplication.getInstance().getGson().fromJson(s, ApplyModel.class);
            otherlist = appmodel.getOTHER_APP();

            if (otherlist != null) {

                otherappdatapter = new OtherApplyAdapter(this, otherlist);
                list_apply_otherapp.setAdapter(otherappdatapter);

                // 添加我的应用数据
                // sqhelper.execSQL("delete from yhappmanagpublic where
                // userid=?",
                // new Object[] { Constants.number });
                sqhelper.execSQL("replace into yhappm`anagpublic(appjson,userid) values(?,?)", new Object[]
                        {
                                s, Constants.number
                        });

                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }

                // // 异步执行避免视图还没绘制完成就调用
                // monitorScrollView.post(new Runnable() {
                //
                // @Override
                // public void run() {
                // // 设置scrollview回到顶部解决应用页打开不是顶部问题
                // monitorScrollView.fullScroll(ScrollView.FOCUS_UP);
                // //刷新当页
                // setHomeView(currentView);
                // }
                // });

                // 功能列表为空时显示标题方便用户进入管理页面
                if (otherlist.size() <= 0) {
                    if (rlayout_title.getVisibility() == View.INVISIBLE) {
                        rlayout_title.startAnimation(mShowAction);
                        rlayout_title.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (rlayout_title.getVisibility() == View.VISIBLE) {
                        rlayout_title.setAnimation(mHiddenAction);
                        rlayout_title.setVisibility(View.INVISIBLE);
                    }
                }

            } else {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }

            }

        } catch (Exception e) {
            if (loadDiaog.isShowing()) {
                loadDiaog.dismiss();
            }

        }

    }

    /**
     * 初始化主页底部按钮视图
     */
    private void initView() {
        context = this;
        sqhelper = ExampleApplication.getInstance().getSqliteHelper();
        // 展示view
        parent = (RelativeLayout) findViewById(R.id.home_parent);

        // 首页
        home1_shouye_layout = (LinearLayout) findViewById(R.id.home_shouye_layout);
        home1_shouye_layout.setOnClickListener(this);
        home1_shouye_img = (ImageView) findViewById(R.id.home_shouye_img);
        home1_shouye_text = (TextView) findViewById(R.id.home_shouye_text);
        home1_shouye_img.setBackgroundResource(R.drawable.nav_hover1);
        // 应用
        home1_yingyong_layout = (LinearLayout) findViewById(R.id.home_yingyong_layout);
        home1_yingyong_layout.setOnClickListener(this);
        home1_yingyong_img = (ImageView) findViewById(R.id.home_yingyong_img);
        home1_yingyong_text = (TextView) findViewById(R.id.home_yingyong_text);
        // 消息
        home1_tongzhi_layout = (LinearLayout) findViewById(R.id.home_tongzhi_layout);
        home1_tongzhi_layout.setOnClickListener(this);
        home1_tongzhi_img = (ImageView) findViewById(R.id.home_tongzhi_img);
        home1_tongzhi_text = (TextView) findViewById(R.id.home_tongzhi_text);
        xxjsq = (DragPointView) findViewById(R.id.xxjsq);// 消息圆点

        // 个人中心
        home1_shezhi_layout = (LinearLayout) findViewById(R.id.home_shezhi_layout);
        home1_shezhi_layout.setOnClickListener(this);
        home1_shezhi_img = (ImageView) findViewById(R.id.home_shezhi_img);
        home1_shezhi_text = (TextView) findViewById(R.id.home_shezhi_text);
        // 数据报告
        home1_shujubaogao_layout = (LinearLayout) findViewById(R.id.home_shujubaogao_layout);
        home1_shujubaogao_layout.setOnClickListener(this);
        home1_shujubaogao_img = (ImageView) findViewById(R.id.home_shujubaogao_img);
        home1_shujubaogao_text = (TextView) findViewById(R.id.home_shujubaogao_txt);
        // web工具类
        webutil = new WebUtils(context);
        // 悬浮按钮
        floatingDraftButtonmain = (FloatingDraftButton) findViewById(R.id.btn_menls);
        ry_float_button = (RelativeLayout) findViewById(R.id.ry_float_button);
        btn_chuanjianmubiao = (Button) findViewById(R.id.btn_chuanjianmubiao);
        btn_mubiaoku = (Button) findViewById(R.id.btn_mubiaoku);
        floatingDraftButtonmain.registerButton(btn_chuanjianmubiao);
        floatingDraftButtonmain.registerButton(btn_mubiaoku);

        floatingDraftButtonmain.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                // 弹出动态Button
                AnimationUtil.slideButtons(context, floatingDraftButtonmain);

            }
        });
        /**
         * 创建目标
         */
        btn_chuanjianmubiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intentmubiao = new Intent(context, CreateTargetAvtivity.class);
                context.startActivity(intentmubiao);
            }
        });

        /**
         * 目标库
         */
        btn_mubiaoku.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intentMubiaoku = new Intent(context, MubiaokuActivity.class);
                context.startActivity(intentMubiaoku);
            }
        });

        loadDiaog = new LoadDiaog(context);
        loadDiaog.setTitle("正在加载中....");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_shujubaogao_layout:
                // 数据报告
                ViewPager(0);
                break;

            case R.id.home_yingyong_layout:
                // 应用
                ViewPager(1);
                break;
            case R.id.home_shouye_layout:
                // 主页
                ViewPager(2);
                break;
            case R.id.home_tongzhi_layout:
                // 通知
                ViewPager(3);
                break;
            case R.id.home_shezhi_layout:
                // 个人中心
                ViewPager(4);
                break;
            default:
                break;
        }
    }

    /**
     * 点击其它页如果悬浮按钮展开就将他关闭
     */
    private void floatButtonDismess() {
        if (!floatingDraftButtonmain.isDraftable()) {

            AnimationUtil.slideButtons(context, floatingDraftButtonmain);
            setHomeView(currentView);
        } // 刷新视图

    }

    /**
     * 改变主页底部按钮状态颜色
     *
     * @param i
     */
    private void ViewPager(int i) {
        reset();
        switch (i) {
            case 0:
                // 数据报告
                home1_shujubaogao_img.setBackgroundResource(R.drawable.ico_report_pressed);
                home1_shujubaogao_text.setTextColor(this.getResources().getColor(R.color.button));
                currentView = 0;
                setHomeView(currentView);
                floatButtonDismess();
                break;
            case 1:
                // 应用
                home1_yingyong_img.setBackgroundResource(R.drawable.ico_apply_presse);
                home1_yingyong_text.setTextColor(this.getResources().getColor(R.color.button));
                currentView = 1;
                setHomeView(currentView);
                floatButtonDismess();
                break;
            case 2:
                // 主页
                home1_shouye_img.setBackgroundResource(R.drawable.nav_hover1);
                home1_shouye_text.setTextColor(this.getResources().getColor(R.color.button));
                currentView = 2;
                // 显示悬浮按钮
                if (ry_float_button.getVisibility() == View.GONE) {
                    ry_float_button.setVisibility(View.VISIBLE);
                } else {
                    ry_float_button.setVisibility(View.GONE);
                }

                setHomeView(currentView);
                break;
            case 3:
                // 消息
                home1_tongzhi_img.setBackgroundResource(R.drawable.nav_hover2);
                home1_tongzhi_text.setTextColor(this.getResources().getColor(R.color.button));
                currentView = 3;
                setHomeView(currentView);
                floatButtonDismess();
                break;
            case 4:
                // 个人中心
                home1_shezhi_img.setBackgroundResource(R.drawable.ico_personal_presse);
                home1_shezhi_text.setTextColor(this.getResources().getColor(R.color.button));
                currentView = 4;
                setHomeView(currentView);
                floatButtonDismess();
                break;
            default:
                break;
        }
    }

    /**
     * 点击切换主页视图
     *
     * @param i 视图游标
     */
    public void setHomeView(int i) {
        ((LinearLayout) findViewById(R.id.home_layout)).removeAllViews();
        View view = Home_ViewList.get(i);
        ((LinearLayout) findViewById(R.id.home_layout)).addView(Home_ViewList.get(i));

    }

    /**
     * 恢复图片初始值
     */
    private void reset() {

        // 数据报告
        home1_shujubaogao_img.setBackgroundResource(R.drawable.ico_report_normal);
        home1_shujubaogao_text.setTextColor(0xFF696969);
        // 应用
        home1_yingyong_img.setBackgroundResource(R.drawable.ico_apply_normal);
        home1_yingyong_text.setTextColor(0xFF696969);
        // 首页
        home1_shouye_img.setBackgroundResource(R.drawable.nav1);
        home1_shouye_text.setTextColor(0xFF696969);
        // 消息
        home1_tongzhi_img.setBackgroundResource(R.drawable.nav2);
        home1_tongzhi_text.setTextColor(0xFF696969);
        // 个人中心
        home1_shezhi_img.setBackgroundResource(R.drawable.ico_personal_normal);
        home1_shezhi_text.setTextColor(0xFF696969);

        // 隐藏悬浮按钮
        if (ry_float_button.getVisibility() == View.VISIBLE) {
            ry_float_button.setVisibility(View.GONE);
        }

    }

    /**
     * 点击两次推出当前应用
     */

    @Override

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // web端
        if (keyCode == KeyEvent.KEYCODE_BACK && webutil.getWebView().canGoBack()) {
            // 返回前一个页面
            webutil.goBack();
            return true;
        }

        if (currentView != 1) {
            ViewPager(1);
            return true;
        }

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1 * 1000) {
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();

                firstTime = secondTime;
                return true;
            } else {

                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                finishActivity(0);
                finish();
                // n关闭应用
                // System.exit(0);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public static boolean isForeground = false;

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录.
    // @SuppressWarnings("unused")
    // private void inits() {
    // JPushInterface.init(this);
    // }

    public static MessageUtils MU;

    public void setXiaoYuanDian() throws Exception {
        // XiaoYuanDianHelper.setText(xxjsq, new SqliteHelper().rawQuery("select
        // count(*)", "").get(0).get("num"));
        String noticeNum = sqhelper.rawQuery("select count(*) num from client_notice where read='false' and userid=?", Constants.number).get(0).get("num");
        String addNum = sqhelper.rawQuery("select count(*) num from addfriend where isread='false' and userid=?", Constants.number).get(0).get("num");
        String ltNum = sqhelper.rawQuery("select count(*) num from lt where isread='false' and userid=?", Constants.number).get(0).get("num");
        XiaoYuanDianHelper.setText(xxjsq, (Integer.valueOf(noticeNum) + Integer.valueOf(addNum) + Integer.valueOf(ltNum)) + "");
    }

    @Override
    protected void onRestart() {

        if (isCarryout) {
            MU.categoryContactsViewUI();
            MU.categoryMessageViewUI();
            for (int i = 1; i <= 3; i++) {
                MU.setJSQ(i);
            }
        }
        try {
            setXiaoYuanDian();
        } catch (Exception e) {
            DataCleanManger.cleanDatabases(context);
            e.printStackTrace();
        }
        initPush();
        // 刷新功能列表

        initFunctionData();
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // HomeSetting.resultData(data);
    }

    @Override
    protected void onResume() {
        isForeground = true;
        webutil.webResume();
        JPushInterface.onResume(context);

        super.onResume();
    }

    @Override
    protected void onPause() {
        isForeground = false;
        webutil.webPause();
        JPushInterface.onPause(context);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        // unregisterReceiver(apnsStart.getMessageReceiver());
        Notification1.cancel(Notification1.NOTIFICATION_ALL);
        webutil.webDestroy();
        // 清空
        mHiddenAction.cancel();
        super.onDestroy();

    }

    /**
     * 隐藏标题栏动画
     */
    private void Hidden() {
        mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
    }

    /**
     * 显示标题栏动画
     */
    private void ShowView() {
        mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(500);

    }

    /**
     * 滚动的回调方法，当滚动的Y距离大于或者等于 标题布局距离父类布局顶部的位置，就显示标题的悬浮框 当滚动的Y的距离小于
     * 标题布局距离父类布局顶部的位置加上标题布局的高度就移除购买的悬浮框
     */
    @Override
    public void onScroll(MonitorScrollView view, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub

        if (y > titleHeight) {
            // 显示标题栏
            if (rlayout_title.getVisibility() == View.INVISIBLE) {
                rlayout_title.startAnimation(mShowAction);
                rlayout_title.setVisibility(View.VISIBLE);
            }

        } else if (y < titleHeight) {
            // 隐藏标题
            if (rlayout_title.getVisibility() == View.VISIBLE) {
                rlayout_title.setAnimation(mHiddenAction);
                rlayout_title.setVisibility(View.INVISIBLE);
            }

        }
    }

}