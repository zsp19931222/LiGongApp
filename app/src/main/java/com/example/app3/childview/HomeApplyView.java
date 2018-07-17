package com.example.app3.childview;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.app3.PullableLinearLayout;
import com.example.app3.activity.AllMessageList;
import com.example.app3.activity.DetailActivity;
import com.example.app3.activity.ListActivity;
import com.example.app3.activity.PushBrowserActivity;
import com.example.app3.adapter.HomeApplyCenterAdater;
import com.example.app3.adapter.dragrecyclear.bean.DragBean;
import com.example.app3.customview.TitleGridView;
import com.example.app3.entity.PersonEntity;
import com.example.app3.listener.HomeApplyFunctionOnclickListener;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.NetImageLoadHolder;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.api.GetAppUrl;
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.presenter.HomePageFragmentPresenter;
import com.example.jpushdemo.ApnsStart;
import com.example.jpushdemo.ExampleApplication;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tbruyelle.rxpermissions.RxPermissions;
import yh.app.appstart.lg.R;
import com.zxing.activity.CaptureActivity;

import org.androidpn.push.Constants;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import rx.Observer;
import yh.app.model.DAModel;
import yh.app.tool.FunctionAT;
import yh.app.tool.FunctionAT.RequestStateLisener;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DensityUtil;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.JsonReaderHelper;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.RandomTools;
import 云华.智慧校园.工具._链接地址导航;

@SuppressLint("InflateParams")
public class HomeApplyView {
    private View HomeApplyView;
    private Context context;
    private ListView listView;
    private boolean canDown = true;
    private List<Map<String, String>> message_list;
    private HomeApplyCenterAdater homeAdater;
    // 是否首页展示代码
    /**
     * 首页展示
     */
    public static final String FUNCTION_DISPLAY_HOMEPAGE_YES = "1";
    /**
     * 首页不展示
     */
    public static final String FUNCTION_DISPLAY_HOMEPAGE_NO = "0";
    /**
     * 消息类型：大图
     */
    private static final String MESSAGE_TYPE_LARGE_PIC = "0";
    /**
     * 消息类型：小图
     */
    private static final String MESSAGE_TYPE_SMALL_PIC = "1";
    /**
     * 消息类型：无图
     */
    private static final String MESSAGE_TYPE_NO_PIC = "2";
    /**
     * 消息类型：LinearLayout
     */
    private static final String MESSAGE_TYPE_Lin = "3";
    /**
     * 首页listview的header，包含banner和应用中心
     */
    private View view_home_apply_header, view_home_apply_foot;
    private LinearLayout view_home_apply_noMessage;
    /**
     *
     */
//    private PullToRefreshLayout refresh_content_layout;
    private SmartRefreshLayout refresh_content_layout;
    private TextView refresh_head_text;
    private ImageView refresh_head_iamge;
    /**
     * banner计数的小圆点线性布局
     */
//    private LinearLayout home_apply_linear_banner_point;
    /**
     * 下拉刷新布局
     */
    private PullableLinearLayout home_apply_linear_content_view;
    /**
     * banner轮播展示空间，用ViewFlipper实现
     */
//    private ViewFlipper flipper;

    private ConvenientBanner convenientBanner;//banner


    /**
     * 应用中心帮助类
     */
    private TitleGridView gridview_home_item_gridview;

    private TextView txt_home_item_titleview;
    /**
     * 首页标题栏
     */
    private TextView home_apply_txt_title;
    /**
     * 应用中心小圆点
     */
    private ImageView gridview_home_item_img_xyd;

    /**
     * 二维码
     */
    private RelativeLayout home_apply_img_ewm;

    /**
     * 应用中心数据
     */
    private List<Map<String, String>> applyList, client_noticeList, new_push_functionList;
    private List<DragBean> dragList;
    private AnimationDrawable refreshingAnimation;

    private String showString;//刷新提示
    private int showStringIndex = 0;


    private String getRandomString() {

        try {
//            if (IsNull.isNotNull(showString)) {
//                return showString;
//            }
            JSONArray jsa = new JSONArray(JsonReaderHelper.getJosn(context, "MJRefreshState.json"));
            showStringIndex = RandomTools.getRandomNumber(jsa.length(), showStringIndex);
            showString = jsa.getString(showStringIndex);
        } catch (Exception e) {
            showString = context.getResources().getString(R.string.refreshing);
        }
        return showString;
    }

    public HomeApplyView(Context context, View HomeApplyView) {
        this.context = context;
        this.HomeApplyView = HomeApplyView;
    }

    public void initView() {
//        refresh_content_layout = (PullToRefreshLayout) HomeApplyView.findViewById(R.id.refresh_view);
        refresh_content_layout = HomeApplyView.findViewById(R.id.refresh_view);
        refresh_head_text = HomeApplyView.findViewById(R.id.refresh_head_text);
        refresh_head_iamge = (ImageView) HomeApplyView.findViewById(R.id.refresh_head_iamge);
//        refresh_content_layout.setEnableRefresh(true);
        refresh_content_layout.setEnableLoadmore(false);

        refresh_content_layout.setOnRefreshListener(new DropRefreshListener());
        home_apply_linear_content_view = HomeApplyView.findViewById(R.id.content_view);
        listView = (ListView) HomeApplyView.findViewById(R.id.home_apply_lv);
        home_apply_txt_title = HomeApplyView.findViewById(R.id.home_apply_txt_title);
        home_apply_img_ewm = (RelativeLayout) HomeApplyView.findViewById(R.id.home_apply_img_ewm);

        view_home_apply_foot = LayoutInflater.from(context).inflate(R.layout.view_home_apply_foot, null, false);
        view_home_apply_noMessage = view_home_apply_foot.findViewById(R.id.home_linear_noMessage);
        view_home_apply_header = LayoutInflater.from(context).inflate(R.layout.view_home_apply_header, null, false);
//        home_apply_linear_banner_point =  view_home_apply_header.findViewById(R.id.home_apply_linear_banner_point);
//        flipper = (ViewFlipper) view_home_apply_header.findViewById(R.id.home_apply_vf_banner);
        convenientBanner = (ConvenientBanner) view_home_apply_header.findViewById(R.id.convenientBanner);

        gridview_home_item_gridview = new TitleGridView(context, view_home_apply_header.findViewById(R.id.home_apply_titleGirdView));

        gridview_home_item_gridview.setTitle(context.getResources().getString(R.string.str_home_apply_center));
        home_apply_txt_title.setText(Constants.xxmc);
        gridview_home_item_gridview.setNumColumns(4);

        home_apply_img_ewm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Tool.isFastDoubleClick()) {
                    new RxPermissions((Activity) context).request(Manifest.permission.CAMERA).subscribe(observer);
                }
            }
        });
        home_apply_linear_content_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                convenientBanner.getParent().requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        initPush();
    }

    /**
     * 提示权限
     */
    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent3 = new Intent(context, CaptureActivity.class);
                ((Activity) context).startActivityForResult(intent3, 0);
            } else {
                Toast.makeText(context, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
    private List<Map<String, String>> kindMap = new ArrayList<>();//根据function_id分类
    private boolean isHave;
    private Map<String, String> subscibeList = new HashMap<>();//订阅消息
    private Map<String, String> appList = new HashMap<>();//应用消息

    private List<Map<String, String>> appNumList;
    //    private List<BannerBean> bannerList = new ArrayList<>();
    private List<DAModel.ContentBean> bannerList = new ArrayList<>();


    public void initData() {
        VolleyRequest.RequestGet(_链接地址导航.DC.获取广告栏.getUrl(),
                new VolleyInterface() {

                    @Override
                    public void onMySuccess(String result) {
                        // TODO Auto-generated method stub
                        try {
                            DAModel damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
                            bannerList.addAll(damoel.getContent());
                            setBanner();
                        } catch (Exception ignored) {
                            defaultBanner();
                        }
                    }

                    @Override
                    public void onMyError(VolleyError error) {
                        // TODO Auto-generated method stub
                        defaultBanner();
                    }
                });
        initAppData();
        message_list = new ArrayList<>();
        pushData();
        getGrzxUrl();
    }

    /**
     * 设置默认banner
     */
    private void defaultBanner() {
        for (int i = 0; i < 3; i++) {
            bannerList.add(new DAModel.ContentBean("", "", "", R.drawable.xxhome1, "", "", 0, "", "", "", null));
        }
        setBanner();
    }

    /**
     * 推送消息的数据获取
     */
    private void pushData() {
        if (client_noticeList != null) {
            client_noticeList.clear();
        }
        if (kindMap != null) {
            kindMap.clear();
            isHave = false;
        }
        if (message_list != null) {
            message_list.clear();
        }
        kindMap = new SqliteHelper().rawQuery("select * from client_notice GROUP BY function_id ORDER by n_send_time asc");//去重分类
        for (int i = 0; i < kindMap.size(); i++) {
            if (client_noticeList != null) {
                client_noticeList.clear();
            }
            client_noticeList = new SqliteHelper().rawQuery("select * from client_notice where function_id=?", kindMap.get(i).get("function_id"));//根据function_id查询相关数据
            for (int j = 0; j < client_noticeList.size(); j++) {
                new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=" + kindMap.get(i).get("function_id"));//得到分组数据
                try {
                    if (!"".equals(new_push_functionList.get(0).get("ts_group"))) {//分组消息
                        if (CodeManage.APP_Messsage_Group_ID.equals(new_push_functionList.get(0).get("ts_group"))) {//应用消息
                            new SqliteHelper().execSQL("insert or replace into homeMessageList(m_type,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_id,m_function_id) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                                    MESSAGE_TYPE_SMALL_PIC,
                                    "group",
                                    "应用消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(j).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "HH-mm"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id"));
                        } else {//订阅消息
                            new SqliteHelper().execSQL("insert or replace into homeMessageList(m_type,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_id,m_function_id) values(?,?,?,?,?,?,?,?,?,?,?,?)"
                                    , MESSAGE_TYPE_NO_PIC,
                                    "group",
                                    "订阅消息",
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(j).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "HH-mm"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id")
                            );
                        }
                    } else {//功能消息
                        if (!"".equals(client_noticeList.get(j).get("n_picpath"))) {//功能有图消息
                            new SqliteHelper().execSQL("insert or replace into homeMessageList(m_type,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_id,m_function_id) values(?,?,?,?,?,?,?,?,?,?,?,?)"
                                    , MESSAGE_TYPE_LARGE_PIC,
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(j).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "HH-mm"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id")
                            );
                        } else {//功能无图消息
                            new SqliteHelper().execSQL("insert or replace into homeMessageList(m_type,m_classify,m_group,m_code,m_read,m_from,m_time,m_image,m_title,m_message,m_id,m_function_id) values(?,?,?,?,?,?,?,?,?,?,?,?)"
                                    , MESSAGE_TYPE_NO_PIC,
                                    "function",
                                    new_push_functionList.get(0).get("ts_name"),
                                    client_noticeList.get(j).get("code"),
                                    client_noticeList.get(j).get("read"),
                                    "",
                                    TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(client_noticeList.get(j).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "HH-mm"),
                                    client_noticeList.get(j).get("n_picpath"),
                                    client_noticeList.get(j).get("n_title"),
                                    client_noticeList.get(j).get("n_message"),
                                    client_noticeList.get(j).get("n_id"),
                                    client_noticeList.get(j).get("function_id")
                            );
                        }
                    }
                } catch (Exception e) {
                    android.util.Log.d("zsp", "pushData: "+e);
                }
            }
            if (message_list == null) {
                message_list = new ArrayList<>();
            } else {
                message_list.clear();
            }
        }
        message_list.addAll(new SqliteHelper().rawQuery("select * from homeMessageList order by m_time desc"));
        for (int j = 0; j <new SqliteHelper().rawQuery("select * from homepage_widget_json").size() ; j++) {
            HomePageWidgetEntity widgetEntity = GsonImpl.get().toObject(new SqliteHelper().rawQuery("select * from homepage_widget_json").get(j).get("json"), HomePageWidgetEntity.class);
            for (int i = 0; i < widgetEntity.getContent().size(); i++) {
                Map<String, String> map1 = new HashMap<>();
                map1.put("m_type", MESSAGE_TYPE_Lin);
                map1.put("m_classify", MESSAGE_TYPE_Lin);
                map1.put("m_group", MESSAGE_TYPE_Lin);
                map1.put("m_code", MESSAGE_TYPE_Lin);
                map1.put("m_read", "false");
                map1.put("m_from", MESSAGE_TYPE_Lin);
                map1.put("m_time", MESSAGE_TYPE_Lin);
                map1.put("m_image", MESSAGE_TYPE_Lin);
                map1.put("m_title", MESSAGE_TYPE_Lin);
                map1.put("m_message", MESSAGE_TYPE_Lin);
                map1.put("m_id", MESSAGE_TYPE_Lin);
                map1.put("m_function_id", MESSAGE_TYPE_Lin);
                message_list.add(0, map1);
                contentBeans.addAll(widgetEntity.getContent());
            }
        }
        if (message_list.size() == 0) {
            view_home_apply_noMessage.setVisibility(View.VISIBLE);
        } else {
            view_home_apply_noMessage.setVisibility(View.GONE);
        }
    }

    public MessageAdapter messageAdapter;

    public void initAction() {
        home_apply_linear_content_view.setCanDown(canDown);
        listView.setOnScrollListener(new OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstVisibleItemView = listView.getChildAt(0);
                    if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                        home_apply_linear_content_view.setCanDown(true);
                    } else {
                        home_apply_linear_content_view.setCanDown(false);
                    }
                } else {
                    home_apply_linear_content_view.setCanDown(false);
                }
            }
        });
        listView.addHeaderView(view_home_apply_header);
        listView.addFooterView(view_home_apply_foot);
        messageAdapter = new MessageAdapter();
        listView.setAdapter(messageAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    if (!Tool.isFastDoubleClick()) {
                        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", message_list.get(position - 1).get("m_id"));
                        new SqliteHelper().rawQuery("update client_notice_messagelist set read=? where n_id=?", "true", message_list.get(position - 1).get("m_id"));
                        Intent intent = null;
                        switch (message_list.get(position - 1).get("m_code")) {
                            case CodeManage.TEXT_PUSH:
                                intent = new Intent(context, DetailActivity.class);
                                intent.putExtra("id", message_list.get(position - 1).get("m_id"));
                                break;
                            case CodeManage.URL_PUSH:
                                List<Map<String, String>> maps = new SqliteHelper().rawQuery("select n_url from client_notice where n_id=?", message_list.get(position - 1).get("m_id"));
                                intent = new Intent(context, PushBrowserActivity.class);
                                intent.putExtra("url", maps.get(0).get("n_url"));
                                intent.putExtra("title", "消息");
                                break;
                        }
                        if (intent != null) {
                            context.startActivity(intent);
                        }
                    }
                } catch (Exception e) {

                }

            }
        });
        if (applyList != null) {
            homeAdater = new HomeApplyCenterAdater(gridview_home_item_gridview.getGirdView(), applyList, context);
            gridview_home_item_gridview.setAdapter(homeAdater);
            gridview_home_item_gridview.setOnItemViewClick(new HomeApplyFunctionOnclickListener(context, applyList));
        }
        freshApplYList();
        Constants.pushHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

            }
        };
        getWidget();
    }

    private int lastX, lastY;

    /**
     * banner设置
     */
    private void setBanner() {
//        convenientBanner.setPages(new CBViewHolderCreator<NetImageLoadHolder>() {
//            @Override
//            public NetImageLoadHolder createHolder() {
//                return new NetImageLoadHolder();
//            }
//        }, bannerList)
//                //设置指示器是否可见
//                .setPointViewVisible(true)
//                //设置自动切换（同时设置了切换时间间隔）
//                .startTurning(5000)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.c1_select, R.drawable.c1_selected})
//                //设置指示器的方向（左、中、右）
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
//                //设置点击监听事件
//                .setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        if (bannerList.get(position).getImg() != null)
//                            if (!"".equals(bannerList.get(position).getImg())) {
//                                Intent intent = new Intent(context, PushBrowserActivity.class);
//                                intent.putExtra("url", (String)bannerList.get(position).getImg());
//                                intent.putExtra("title", "");
//                                context.startActivity(intent);
//                            }
//                    }
//                })
//                //设置手动影响（设置了该项无法手动切换）
//                .setManualPageable(true);
//
//        convenientBanner.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//
//            public boolean onTouch(View v, MotionEvent event) {
//                convenientBanner.getParent().requestDisallowInterceptTouchEvent(true);
//                int x = (int) event.getRawX();
//                int y = (int) event.getRawY();
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        lastX = x;
//                        lastY = y;
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        int deltaY = y - lastY;
//                        int deltaX = x - lastX;
//                        if (Math.abs(deltaX) < Math.abs(deltaY)) {
//                            convenientBanner.getParent().requestDisallowInterceptTouchEvent(false);
//                        } else {
//                            convenientBanner.getParent().requestDisallowInterceptTouchEvent(true);
//                        }
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
    }

    private class MessageAdapter extends BaseAdapter {

        @Override
        public synchronized View getView(final int position, View convertView, ViewGroup parent) {
            // FIXME 为了解决内容错乱问题，删除了ViewHolder，有时间再看
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.home_apply_item_message_vector, parent, false);
            }

            switch (message_list.get(position).get("m_type")) {
                case MESSAGE_TYPE_LARGE_PIC:
                    convertView.findViewById(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.VISIBLE);

                    ((TextView) convertView.findViewById(R.id.large_txt_home_item_titleview)).setText(message_list.get(position).get("m_group"));
                    if ("false".equals(message_list.get(position).get("m_read"))) {
                        ((ImageView) convertView.findViewById(R.id.large_gridview_home_item_img_xyd)).setVisibility(View.VISIBLE);
                    } else {
                        ((ImageView) convertView.findViewById(R.id.large_gridview_home_item_img_xyd)).setVisibility(View.INVISIBLE);
                    }

                    ((TextView) convertView.findViewById(R.id.large_txt_home_item_from)).setText(message_list.get(position).get("m_from"));
                    ((TextView) convertView.findViewById(R.id.large_txt_home_item_date)).setText(message_list.get(position).get("m_time"));

                    ((TextView) convertView.findViewById(R.id.large_pic_txt_title)).setText(message_list.get(position).get("m_title"));
                    ((TextView) convertView.findViewById(R.id.large_pic_txt_message)).setText(message_list.get(position).get("m_message"));

                    GlideLoadUtils.getInstance().glideLoad(context, message_list.get(position).get("m_image"), ((ImageView) convertView.findViewById(R.id.large_pic_image)), R.drawable.frist_pushdefaule);

                    ((TextView) convertView.findViewById(R.id.large_pic_text_more)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = null;
                            switch (message_list.get(position).get("m_classify")) {
                                case "group":
                                    intent = new Intent(context, ListActivity.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                                case "function":
                                    intent = new Intent(context, AllMessageList.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                            }
                            if (intent != null) {
                                context.startActivity(intent);
                            }

                        }
                    });
                    convertView.findViewById(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_linear).setVisibility(View.GONE);
                    break;
                case MESSAGE_TYPE_SMALL_PIC:
                    convertView.findViewById(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.VISIBLE);

                    ((TextView) convertView.findViewById(R.id.small_txt_home_item_titleview)).setText(message_list.get(position).get("m_group"));
                    if ("false".equals(message_list.get(position).get("m_read"))) {
                        ((ImageView) convertView.findViewById(R.id.small_gridview_home_item_img_xyd)).setVisibility(View.VISIBLE);
                    } else {
                        ((ImageView) convertView.findViewById(R.id.small_gridview_home_item_img_xyd)).setVisibility(View.INVISIBLE);
                    }

                    ((TextView) convertView.findViewById(R.id.small_txt_home_item_from)).setText(message_list.get(position).get("m_from"));
                    ((TextView) convertView.findViewById(R.id.small_txt_home_item_date)).setText(message_list.get(position).get("m_time"));

                    ((TextView) convertView.findViewById(R.id.small_pic_txt_title)).setText(message_list.get(position).get("m_title"));
                    ((TextView) convertView.findViewById(R.id.small_pic_txt_message)).setText(message_list.get(position).get("m_message"));

                    GlideLoadUtils.getInstance().glideLoad(context, message_list.get(position).get("m_image"), ((ImageView) convertView.findViewById(R.id.small_pic_image)), R.drawable.pushlist_default);


                    ((TextView) convertView.findViewById(R.id.small_pic_text_more)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = null;
                            switch (message_list.get(position).get("m_classify")) {
                                case "group":
                                    intent = new Intent(context, ListActivity.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                                case "function":
                                    intent = new Intent(context, AllMessageList.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                            }
                            if (intent != null) {
                                context.startActivity(intent);
                            }
                        }
                    });

                    convertView.findViewById(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_linear).setVisibility(View.GONE);
                    break;
                case MESSAGE_TYPE_NO_PIC:
                    convertView.findViewById(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.VISIBLE);

                    ((TextView) convertView.findViewById(R.id.txt_home_item_titleview)).setText(message_list.get(position).get("m_group"));
                    if ("false".equals(message_list.get(position).get("m_read"))) {
                        ((ImageView) convertView.findViewById(R.id.gridview_home_item_img_xyd)).setVisibility(View.VISIBLE);
                    } else {
                        ((ImageView) convertView.findViewById(R.id.gridview_home_item_img_xyd)).setVisibility(View.INVISIBLE);
                    }

                    ((TextView) convertView.findViewById(R.id.txt_home_item_from)).setText(message_list.get(position).get("m_from"));
                    ((TextView) convertView.findViewById(R.id.txt_home_item_date)).setText(message_list.get(position).get("m_time"));

                    ((TextView) convertView.findViewById(R.id.no_pic_txt_title)).setText(message_list.get(position).get("m_title"));
                    ((TextView) convertView.findViewById(R.id.no_pic_txt_message)).setText(message_list.get(position).get("m_message"));

                    ((TextView) convertView.findViewById(R.id.no_pic_text_more)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = null;
                            switch (message_list.get(position).get("m_classify")) {
                                case "group":
                                    intent = new Intent(context, ListActivity.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                                case "function":
                                    intent = new Intent(context, AllMessageList.class);
                                    intent.putExtra("function_id", message_list.get(position).get("m_function_id"));
                                    break;
                            }
                            if (intent != null) {
                                context.startActivity(intent);
                            }
                        }
                    });
                    convertView.findViewById(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_linear).setVisibility(View.GONE);
                    break;
                case MESSAGE_TYPE_Lin:
                    convertView.findViewById(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.GONE);
                    convertView.findViewById(R.id.view_home_apply_message_item_linear).setVisibility(View.VISIBLE);

                    LinearLayout linearLayout = convertView.findViewById(R.id.view_home_apply_message_item_linear).findViewById(R.id.item_linearlayout);
                    linearLayout.setVisibility(View.VISIBLE);
                    linearLayout.removeAllViews();
                    if (contentBeans.get(position).getLx().size() == 1) {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(DensityUtil.dip2px(context, 10), 0, 0, 0);
                        Activity activity = (Activity) context;
                        WindowManager wm1 = activity.getWindowManager();
                        params.width = wm1.getDefaultDisplay().getWidth();
                        ImageView imageView = new ImageView(context);
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        linearLayout.addView(imageView);
                        GlideLoadUtils.getInstance().glideLoad(context, contentBeans.get(position).getLx().get(0).getImg(), imageView, R.drawable.pushlist_default);
                    } else {
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(DensityUtil.dip2px(context, 10), 0, 0, 0);
                        for (int i = 0; i < contentBeans.get(position).getLx().size(); i++) {
                            ImageView imageView = new ImageView(context);
                            imageView.setLayoutParams(params);
                            params.weight = 1;
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            linearLayout.addView(imageView);
                            GlideLoadUtils.getInstance().glideLoad(context, contentBeans.get(position).getLx().get(i).getImg(), imageView, R.drawable.pushlist_default);
                            final int finalI = i;
                        }
                    }
                    break;
            }
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return message_list == null ? 0 : message_list.size();
        }

        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount();
        }
    }

    public boolean isFirst = true;

    public void freshApplYList() {
//        Constants.number = "20141029";
//        Constants.code = MD5.MD5("123456");

        if (appNumList != null) {
            appNumList.clear();
            applyList.clear();
            initAppData();
            ((BaseAdapter) gridview_home_item_gridview.getGirdView().getAdapter()).notifyDataSetChanged();
            isFirst = false;

        }
    }

    /**
     * 获取应用数据
     */
    private void initAppData() {
        if (applyList == null) {
            applyList = new ArrayList<>();
            appNumList = new ArrayList<>();
        } else {
            applyList.clear();
            appNumList.clear();
        }
        appNumList.addAll(new SqliteHelper().rawQuery("select function_id as FunctionID, function_name from new_apply_function where function_display_homepage=? order by cast(function_display_homepage_px as number) asc", FUNCTION_DISPLAY_HOMEPAGE_YES));
        if (appNumList.size() == 0) {
            applyList.addAll(new SqliteHelper().rawQuery("select function_id as FunctionID,function_icon as FUNCTION_FACE,function_name as FUNCTION_NAME,function_type as function_type,function_cls as cls, function_name as name from new_apply_function where  function_id=? order by cast(function_display_homepage_px as number) asc", "90000000"));
            if (applyList.size() == 0) {
                FunctionAT.getAllFunction_v3(new RequestStateLisener() {
                    @Override
                    public void getRequestState(boolean isSuccess) {
                        if (isSuccess) {
                            FunctionAT.getUserCYYYFunction(new RequestStateLisener() {
                                @Override
                                public void getRequestState(boolean isSuccess) {
                                    if (isSuccess) {
                                        applyList.clear();
                                        applyList.addAll(new SqliteHelper().rawQuery("select function_id as FunctionID, function_icon as FUNCTION_FACE,function_name as FUNCTION_NAME,function_type as function_type,function_cls as cls, function_name as name from new_apply_function where function_display_homepage=? order by cast(function_display_homepage_px as number) asc", FUNCTION_DISPLAY_HOMEPAGE_YES));
                                        ((BaseAdapter) gridview_home_item_gridview.getGirdView().getAdapter()).notifyDataSetChanged();
                                    } else {
                                        ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                                    }
                                }
                            });
                        } else {
                            ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                        }
                    }
                });
            }
        }


        if (appNumList.size() <= 12) {
            applyList.addAll(new SqliteHelper().rawQuery("select function_id as FunctionID,function_icon as FUNCTION_FACE,function_name as FUNCTION_NAME,function_type as function_type,function_cls as cls, function_name as name from new_apply_function where function_display_homepage=? order by cast(function_display_homepage_px as number) asc", FUNCTION_DISPLAY_HOMEPAGE_YES));
        } else {
            for (int i = 0; i < 11; i++) {
                Map<String, String> map = new HashMap<>();
                map.put("FUNCTION_FACE", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_icon"));
                map.put("FUNCTION_NAME", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_name"));
                map.put("function_type", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_type"));
                map.put("cls", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_cls"));
                map.put("name", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_name"));
                map.put("FunctionID", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(i).get("function_name")).get(0).get("function_id"));
                applyList.add(map);
            }
            Map<String, String> map = new HashMap<>();
            map.put("FUNCTION_FACE", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_icon"));
            map.put("FUNCTION_NAME", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_name"));
            map.put("function_type", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_type"));
            map.put("cls", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_cls"));
            map.put("name", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_name"));
            map.put("FunctionID", new SqliteHelper().rawQuery("select * from new_apply_function where function_name=?", appNumList.get(appNumList.size() - 1).get("function_name")).get(0).get("function_id"));
            applyList.add(map);
        }
    }

    private void initPush() {
        ApnsStart apnsStart = new ApnsStart(context.getApplicationContext());
        apnsStart.start();
        JPushInterface.setDebugMode(false);
        JPushInterface.resumePush(context);
        JPushInterface.init(context.getApplicationContext());
    }

    public void freshMessageList() {
        pushData();
        messageAdapter.notifyDataSetChanged();
    }

    public View getHomeApplyView() {
        return HomeApplyView;
    }

    /**
     * 刷新
     */
    public class DropRefreshListener implements OnRefreshListener {


        @Override
        public void onRefresh(final RefreshLayout refreshlayout) {
            refresh_head_text.setText(getRandomString());
            refreshingAnimation = (AnimationDrawable) refresh_head_iamge.getBackground();
            refreshingAnimation.start();
            setBanner();
            FunctionAT.getAllFunction_v3(new RequestStateLisener() {
                @Override
                public void getRequestState(boolean isSuccess) {
                    if (isSuccess) {
                        FunctionAT.getUserCYYYFunction(new RequestStateLisener() {
                            @Override
                            public void getRequestState(boolean isSuccess) {
                                if (isSuccess) {
                                    applyList.clear();
                                    applyList.addAll(new SqliteHelper().rawQuery("select function_id as FunctionID, function_icon as FUNCTION_FACE,function_name as FUNCTION_NAME,function_type as function_type,function_cls as cls, function_name as name from new_apply_function where function_display_homepage=? order by cast(function_display_homepage_px as number) asc", FUNCTION_DISPLAY_HOMEPAGE_YES));
                                    ((BaseAdapter) gridview_home_item_gridview.getGirdView().getAdapter()).notifyDataSetChanged();
                                    VolleyRequest.RequestGet(_链接地址导航.DC.获取广告栏.getUrl(),
                                            new VolleyInterface() {

                                                @Override
                                                public void onMySuccess(String result) {
                                                    // TODO Auto-generated method stub
                                                    try {
                                                        DAModel damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
                                                        bannerList.clear();
                                                        bannerList.addAll(damoel.getContent());
                                                        setBanner();
                                                    } catch (Exception e) {

                                                    }

                                                }

                                                @Override
                                                public void onMyError(VolleyError error) {
                                                    // TODO Auto-generated method stub

                                                }
                                            });
                                } else {
                                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                                }
                            }
                        });
                    } else {
                        ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                    }
                    refreshlayout.finishRefresh();
                }
            });

        }
    }

    public void fresh(List<Map<String, String>> applyList) {
        this.applyList.clear();
        this.applyList.addAll(applyList);
        ((BaseAdapter) gridview_home_item_gridview.getGirdView().getAdapter()).notifyDataSetChanged();
    }


    private List<PersonEntity.ContentBean> content;

    public void getGrzxUrl() {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        }

                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.获取个人中心URL.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    PersonEntity entity = GsonImpl.get().toObject(result, PersonEntity.class);
                    content = entity.getContent();
                    for (int i = 0; i < content.size(); i++) {
                        if ("1".equals(content.get(i).getNATIVE_ACCESS())) {//有链接
                            new SqliteHelper().execSQL("insert or replace into getGrzxUrl(name, url) values(?, ?)", new Object[]
                                    {
                                            content.get(i).getFUNCTION_GROUP_NAME(), content.get(i).getPAGE_ADDRESS()
                                    });
                        }
                    }
                } else {
                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    private List<HomePageWidgetEntity.ContentBean> contentBeans = new ArrayList<>();

    private void getWidget() {
        Map<String, String> map = new HashMap<>();
        map.put("xxbh", "3EAA14419A88D63BB2F32EC68F7BA913");
        map.put("type", "1");
        VolleyRequest.RequestPost(GetAppUrl.Show.widgetHomePage.getUrl(), map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    new HomePageFragmentPresenter(context).saveWidget(result);
                    pushData();
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (message_list.size() == 0) {
                    view_home_apply_noMessage.setVisibility(View.VISIBLE);
                } else {
                    view_home_apply_noMessage.setVisibility(View.GONE);
                }
            }
        });
    }
}
