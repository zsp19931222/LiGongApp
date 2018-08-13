package yh.app.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;
import com.yunhuakeji.app.utils.IsNull;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.acticity.DataInputActivity;
import yh.app.acticity.ErWeimaActivity;
import yh.app.acticity.PersnaldataDetailsActivity;
import yh.app.acticity.UserInfoCodeActivity;
import yh.app.adapter.WelcomeStudentAdapter;
import yh.app.adapter.WelcomeStudentDaLiBaoAdapter;
import yh.app.listeners.DialogOnClickListener;
import yh.app.model.RxzbUserInfoModel;
import yh.app.model.UtilsModel;
import yh.app.model.WelcomeStudentDaLiBaoModel;
import yh.app.model.WelcomeStudentModel;
import yh.app.model.WelcomeStudentNotice;
import yh.app.stu_info.RoundImageView;
import yh.app.tool.SqliteHelper;
import yh.app.tool.StringUtils;
import yh.app.tool.Ticket;
import yh.app.utils.Utils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.view.WarnDialog;
import yh.app.view.WarnDialog.HideType;
import yh.tool.widget.LineGridView;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.ProgressView;
import yh.tool.widget.PullToRefreshView;
import 云华.智慧校园.工具._功能跳转;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 掌上迎新
 */
public class WelcomeStudentFragment extends Fragment implements View.OnClickListener, MonitorScrollView.OnScrollListener {
    private static String STATE_GNZT_FINISH = "1";
    boolean canScroll = true;
    private String wwcgnmc = null, wwcgncls = null, wwcgnurl = null;
    private static String JKFWBS_Y = "1";

    private TextView txtBack;
    private ImageView imgWelcomestudentErweima;
    private RoundImageView imgWelcomestudentHand;
    private TextView txtWelcomestudentNoticeBook;
    private TextView txtWelcomestudentName;
    private TextView txtWelcomestudentSchool;
    private TextView txtWelcomestudentMajor;
    private TextView txtWelcomestudentPayment;
    private TextView txtWelcomestudentDorm;
    private TextView txtWelcomestudentEms;
    private ImageView imgWelcomestudentQuiz;
    private LinearLayout lyWelcomestudentNotice;
    private TextView txtWelcomestudentHotreview;
    private TextView txtWelcomestudentReadyprocess;
    private LineGridView lvWelcomestudentReady;
    private TextView txtWelcomestudentNotice;
    private LineGridView lvWelcomestudentDalibao;
    private LinearLayout lyWelcomestudentDalibao;
    private TextView txtWelcomestudentHelper;

    private RelativeLayout rlWelcomstudentTitle;
    private int titleHeight = 0;// 应用标题栏高度
    private MonitorScrollView monitorScrollView;
    // 上拉刷新下拉刷新
    private PullToRefreshView pullWelcomeStudentRefreshview;
    /**
     * 自定义进度条
     */
    private ProgressView pvWelcomestudentProgress;
    protected static final int WHAT_INCREASE = 1;
    private int progress;
    private int maxprogress;

    /**
     * 自定义对话框
     */
    private WarnDialog warnDialog;
    private WarnDialog handDialog;// 提示消息未完成
    private WarnDialog hindDialog;

    private Activity activity;
    /**
     * 入学准备
     */
    private WelcomeStudentAdapter welcomeStudentAdapter;
    private List<WelcomeStudentModel.ContentBean> welcomeStudentModels;
    private WelcomeStudentModel welcomeStudentModel;
    private TextView textWelcomstudentTitle;
    private SqliteHelper sqhelper;

    /**
     * 新生大礼包
     */
    private WelcomeStudentDaLiBaoAdapter liBaoAdapter;
    private WelcomeStudentDaLiBaoModel daLiBaoModel;
    private List<WelcomeStudentDaLiBaoModel> liBaoModels;
    private List<String> yxjlist = new ArrayList<>();
    private List<String> yxjlists;
    private int yxj;
    /**
     * 用户信息
     */
    private String FunctionID;
    private Drawable nav_right, pay_right_state, pay_right_complete;
    private RxzbUserInfoModel userInfoModel;
    private String ewmContent;
    private String isover;// 判断个人信息是否完成

    /**
     * 通知公告
     */
    private WelcomeStudentNotice studentNotice;
    private String noticeurl;

    /**
     * 加载对话框
     */
    private LoadDiaog loadDiaog;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (maxprogress == 0) {
                return;
            }
            progress += 5;
            pvWelcomestudentProgress.setProgress(progress);
            handler.sendEmptyMessageDelayed(WHAT_INCREASE, Utils.getRadomNumber(50, 200));
            if (progress >= maxprogress) {
                handler.removeMessages(WHAT_INCREASE);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_student, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        initView();

        if (Constants.isNetworkAvailable(activity)) {

            getUseinfo();
            getNotice();
            initData();
            initProcessor();
        } else {
            Toast.makeText(activity, "你的网络出了一些问题", Toast.LENGTH_SHORT).show();
            // initThisRXZBData();
            initThisUserinfoData();
        }
        if (StringUtils.inString(Constants.xx, "lg", "yhkj")) {
            getXinShenDaLiBao();
            lyWelcomestudentDalibao.setVisibility(View.VISIBLE);

        }

    }

    private void initView() {
        FunctionID = activity.getIntent().getStringExtra("FunctionID");
        loadDiaog = new LoadDiaog(activity);
        yxjlists = new ArrayList<>();
        sqhelper = ExampleApplication.getInstance().getSqliteHelper();
        // 标题
        textWelcomstudentTitle = getView().findViewById(R.id.text_welcomstudent_title);
        rlWelcomstudentTitle = getView().findViewById(R.id.rl_welcomstudent_title);

        txtBack = getView().findViewById(R.id.txt_back);
        // 退出当前
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChooseAreaTag, ""));
            }
        });
        // 二维码
        imgWelcomestudentErweima = getView().findViewById(R.id.img_welcomestudent_erweima);
        imgWelcomestudentErweima.setOnClickListener(this);
        // 用户头像
        imgWelcomestudentHand = getView().findViewById(R.id.img_welcomestudent_hand);
        imgWelcomestudentHand.setOnClickListener(this);
        // 电子通知书
        txtWelcomestudentNoticeBook = getView().findViewById(R.id.txt_welcomestudent_noticebook);
        txtWelcomestudentNoticeBook.setText(Html.fromHtml("<u>" + this.getResources().getString(R.string.str_welcomestudent_noticebook) + "<u/>"));
        txtWelcomestudentNoticeBook.setOnClickListener(this);
        // 帮助
        txtWelcomestudentHelper = getView().findViewById(R.id.txt_welcomestudent_helper);
        txtWelcomestudentHelper.setText(Html.fromHtml("<u>" + this.getResources().getString(R.string.help) + "<u/>"));
        txtWelcomestudentHelper.setOnClickListener(this);

        // 用户名称
        txtWelcomestudentName = getView().findViewById(R.id.txt_welcomestudent_name);
        // 学校名称
        txtWelcomestudentSchool = getView().findViewById(R.id.txt_welcomestudent_school);
        // 专业
        txtWelcomestudentMajor = getView().findViewById(R.id.txt_welcomestudent_major);

        // 缴费信息
        txtWelcomestudentPayment = getView().findViewById(R.id.txt_welcomestudent_payment);
        txtWelcomestudentPayment.setOnClickListener(this);
        // 寝室号
        txtWelcomestudentDorm = getView().findViewById(R.id.txt_welcomestudent_dorm);
        txtWelcomestudentDorm.setOnClickListener(this);
        // EMS 邮件
        txtWelcomestudentEms = getView().findViewById(R.id.txt_welcomestudent_ems);
        txtWelcomestudentEms.setOnClickListener(this);
        // 提问
        imgWelcomestudentQuiz = getView().findViewById(R.id.img_welcomestudent_quiz);
        imgWelcomestudentQuiz.setOnClickListener(this);
        // 通知公告
        lyWelcomestudentNotice = getView().findViewById(R.id.ly_welcomestudent_notice);
        lyWelcomestudentNotice.setOnClickListener(this);
        // 热评
        txtWelcomestudentHotreview = getView().findViewById(R.id.txt_welcomestudent_hotreview);

        // 通知
        txtWelcomestudentNotice = getView().findViewById(R.id.txt_welcomestudent_notice);
        // 入学准备进度
        txtWelcomestudentReadyprocess = getView().findViewById(R.id.txt_welcomestudent_readyprocess);
        // 入学准备列表
        lvWelcomestudentReady = getView().findViewById(R.id.lv_welcomestudent_ready);
        // 新生大礼包
        lvWelcomestudentDalibao = getView().findViewById(R.id.lv_welcomestudent_dalibao);
        // 大礼包布局父控件
        lyWelcomestudentDalibao = getView().findViewById(R.id.ly_welcometstudent_dalibao);
        lyWelcomestudentDalibao.setVisibility(View.GONE);
        // 自定义纵向滑动计算弹出标题
        monitorScrollView = getView().findViewById(R.id.msv_welcomestudent);
        monitorScrollView.setOnScrollListener(this);
        // 任务总进度
        pvWelcomestudentProgress = getView().findViewById(R.id.pv_welcomestudent_progress);

        warnDialog = new WarnDialog(activity);
        hindDialog = new WarnDialog(activity);
        pullWelcomeStudentRefreshview = getView().findViewById(R.id.pull__welcome_student_refreshview);

    }

    /**
     * 获得学生信息
     */
    private void getUseinfo() {
        if (FunctionID == null) {
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("ticket", Ticket.getFunctionTicket(FunctionID));
        VolleyRequest.RequestPost(_链接地址导航.Ydyx.STUDENTINFO.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                System.out.println(result);
                try {

                    userInfoModel = ExampleApplication.getInstance().getGson().fromJson(result, RxzbUserInfoModel.class);
                    bindUserInfoData(userInfoModel.getContent());
                    sqhelper.execSQL("replace into userinfo(userinfobdata,userid) values(?,?)", result, Constants.number);
                } catch (Exception e) {
                    initThisUserinfoData();
                    pullWelcomeStudentRefreshview.onHeaderRefreshComplete();

                }

            }

            @Override
            public void onMyError(VolleyError error) {
                initThisUserinfoData();
                pullWelcomeStudentRefreshview.onFooterRefreshComplete();
            }
        });
    }

    /**
     * 绑定用户数据
     *
     * @param infoModel
     */
    private void bindUserInfoData(RxzbUserInfoModel.ContentBean infoModel) throws Exception {

        ewmContent = infoModel.getEwm();
        // 修改用户性别图标
        if (infoModel.getXbdm().equals("2")) {
            nav_right = getResources().getDrawable(R.drawable.ico_woman);
        } else {
            nav_right = getResources().getDrawable(R.drawable.ico_man);
        }

        nav_right.setBounds(0, 0, nav_right.getMinimumWidth(), nav_right.getMinimumHeight());
        // 左上右下
        txtWelcomestudentName.setCompoundDrawables(null, null, nav_right, null);
        txtWelcomestudentName.setText(infoModel.getXm());
        txtWelcomestudentSchool.setText(infoModel.getXymc());
        txtWelcomestudentMajor.setText(infoModel.getZymc());

        if (infoModel.getJfzt().equals("1")) {
            setPayComplete(txtWelcomestudentPayment);
            txtWelcomestudentPayment.setText("缴费信息:" + infoModel.getJf());
        } else {
            txtWelcomestudentPayment.setText("缴费信息:" + infoModel.getJf());
        }
        if (infoModel.getQszt().equals("1")) {
            setPayComplete(txtWelcomestudentDorm);
            txtWelcomestudentDorm.setText("寝室:" + infoModel.getQs());
        } else {
            txtWelcomestudentDorm.setText("寝室:" + infoModel.getQs());
        }
        if (!infoModel.getEmszt().equals("998")) {
            setPayComplete(txtWelcomestudentEms);
            txtWelcomestudentEms.setText("EMS:" + infoModel.getEms());
        } else {
            txtWelcomestudentEms.setText("EMS:" + infoModel.getEms());
        }
        // 缴费信息小模块

        txtWelcomestudentReadyprocess.setText(infoModel.getJd());
        // int max = Integer.valueOf((int) ((Float.valueOf(infoModel.getYwc()) /
        // Float.valueOf(infoModel.getZsl())) * 10));

        // 刷新进度条
        maxprogress = Integer.valueOf(infoModel.getYwc()) * 10;
        pvWelcomestudentProgress.setmaxProgress(infoModel.getZsl());
        int zsl = Integer.valueOf(infoModel.getZsl());
        int ywc = Integer.valueOf(infoModel.getYwc());
        pvWelcomestudentProgress.setProgress(ywc * 100 / zsl);
        // increase();

        pullWelcomeStudentRefreshview.onHeaderRefreshComplete();
    }

    /**
     * 缴费信息完成状态图标
     */
    private void setPayComplete(TextView view) {
        pay_right_complete = getResources().getDrawable(R.drawable.ico_welcomestudent_fulfill);
        pay_right_complete.setBounds(0, 0, pay_right_complete.getMinimumWidth(), pay_right_complete.getMinimumHeight());
        view.setCompoundDrawables(null, null, pay_right_complete, null);
    }

    /**
     * 缴费信息未完成状态
     */
    private void setPayTBD(TextView view) {
        pay_right_state = getResources().getDrawable(R.drawable.ico_welcomestudent_undone);
        pay_right_state.setBounds(0, 0, pay_right_state.getMinimumWidth(), pay_right_state.getMinimumHeight());
        view.setCompoundDrawables(null, null, pay_right_complete, null);
    }

    /**
     * 获得通知公告信息
     */
    private void getNotice() {
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("ticket", Ticket.getFunctionTicket(FunctionID));
        VolleyRequest.RequestPost(_链接地址导航.Ydyx.NOTICE.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                try {
                    studentNotice = ExampleApplication.getInstance().getGson().fromJson(result, WelcomeStudentNotice.class);
                    noticeurl = studentNotice.getContent().getUrl();
                    txtWelcomestudentHotreview.setText(studentNotice.getContent().getList().get(0).getBt());
                    txtWelcomestudentNotice.setText(studentNotice.getContent().getList().get(1).getBt());

                } catch (Exception e) {

                }

            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

    private boolean canScroll(JSONArray jsa) throws Exception {
        canScroll = true;
        wwcgnmc = null;
        wwcgncls = null;
        wwcgnurl = null;
        for (int i = 0; jsa != null && i < jsa.length(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            if (jso.has("gnyxj") && jso.has("gnzt") && Integer.valueOf(jso.getString("gnyxj")) < 1) {
                if (STATE_GNZT_FINISH.equals(jso.getString("gnzt"))) {
                    canScroll = canScroll && true;
                } else {
                    if (!IsNull.isNotNull(wwcgnmc, wwcgncls, wwcgnurl)) {
                        wwcgnmc = jso.getString("gnmc");
                        wwcgncls = jso.getString("cls");
                        wwcgnurl = jso.getString("url");
                    }
                    canScroll = canScroll && false;
                }
            }
        }
        return canScroll;
    }

    /**
     * 入学准备功能列表数据
     */
    String ticite;

    @SuppressLint("ShowToast")
    private void initData() {
        loadDiaog.show();
        ticite = Ticket.getFunctionTicket(FunctionID);
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("ticket", Ticket.getFunctionTicket(FunctionID));
        VolleyRequest.RequestPost(_链接地址导航.Ydyx.RXZBLB.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                try {
                    welcomeStudentModel = ExampleApplication.getInstance().getGson().fromJson(result, WelcomeStudentModel.class);
                    welcomeStudentModels = welcomeStudentModel.getContent();
                    isover = welcomeStudentModels.get(1).getGnzt();
                    // 控制主页是否可以滑动
                    // if (canScroll(new
                    // JSONObject(result).getJSONArray("content")))
                    // {
                    // Constants.ISOVER = canScroll(new
                    // JSONObject(result).getJSONArray("content"));
                    //
                    // } else
                    // {

                    Constants.ISOVER = canScroll(new JSONObject(result).getJSONArray("content"));
                    // }

                    sqhelper.execSQL("replace into rxzb(rxzbdata,userid) values(?,?)", new Object[]
                            {
                                    result, Constants.number
                            });

                    welcomeStudentAdapter = new WelcomeStudentAdapter(activity, welcomeStudentModels);
                    lvWelcomestudentReady.setAdapter(welcomeStudentAdapter);
                    pullWelcomeStudentRefreshview.onFooterRefreshComplete();
                    dimess();
                } catch (Exception e) {
                    pullWelcomeStudentRefreshview.onFooterRefreshComplete();
                    // initThisRXZBData();
                    dimess();

                }

            }

            @Override
            public void onMyError(VolleyError error) {
                pullWelcomeStudentRefreshview.onFooterRefreshComplete();
                // initThisRXZBData();
                dimess();
            }
        });

    }

    /**
     * 加载本地用户数据网络数据
     */
    private void initThisUserinfoData() {
        List<Map<String, String>> listmap = sqhelper.rawQuery("select  userinfodata from userinfo where userid=?", new String[]
                {
                        Constants.number
                });
        if (listmap.size() > 0) {

            String appljson = listmap.get(0).get("userinfodata");
            if (null != appljson) {
                try {
                    userInfoModel = ExampleApplication.getInstance().getGson().fromJson(appljson, RxzbUserInfoModel.class);
                    bindUserInfoData(userInfoModel.getContent());
                } catch (Exception e) {

                }
            }
        }

    }

    /**
     * 加载本地网络数据
     */
    // private void initThisRXZBData()
    // {
    // List<Map<String, String>> listmap = sqhelper.rawQuery("select rxzbdata
    // from rxzb where userid=?", new String[]
    // {
    // Constants.number
    // });
    // if (listmap.size() > 0)
    // {
    //
    // String appljson = listmap.get(0).get("rxzbdata");
    // if (null != appljson)
    // {
    // try
    // {
    // welcomeStudentModel =
    // ExampleApplication.getInstance().getGson().fromJson(appljson,
    // WelcomeStudentModel.class);
    // welcomeStudentModels = welcomeStudentModel.getContent();
    // isover = welcomeStudentModels.get(0).getGnzt();
    // // 控制主页是否可以滑动
    // if (welcomeStudentModels.get(0).getGnzt().equals("1"))
    // {
    // Constants.ISOVER = true;
    //
    // } else
    // {
    //
    // Constants.ISOVER = false;
    // }
    // for (int i = 0; i < welcomeStudentModels.size(); i++)
    // {
    // if (Integer.valueOf(welcomeStudentModels.get(i).getGnyxj()) < 1)
    // {
    // yxjlist.add(welcomeStudentModels.get(i).getGnyxj());
    // }
    //
    // }
    //
    // welcomeStudentAdapter = new WelcomeStudentAdapter(activity,
    // welcomeStudentModels);
    // lvWelcomestudentReady.setAdapter(welcomeStudentAdapter);
    // pullWelcomeStudentRefreshview.onFooterRefreshComplete();
    // dimess();
    // } catch (Exception e)
    // {
    // pullWelcomeStudentRefreshview.onFooterRefreshComplete();
    // initThisRXZBData();
    // dimess();
    //
    // }
    // }
    // }
    //
    // }

    /**
     * 获取新生大礼包数据
     */
    private void getXinShenDaLiBao() {
        liBaoModels = new ArrayList<>();
        for (int i = 0; i < UtilsModel.DALIBAONAME.length; i++) {
            daLiBaoModel = new WelcomeStudentDaLiBaoModel();
            daLiBaoModel.setFunction_name(UtilsModel.DALIBAONAME[i]);
            daLiBaoModel.setFunction_url(UtilsModel.DALIBAOURL[i]);
            daLiBaoModel.setFunction_img(UtilsModel.DALIBAOIMG[i]);
            liBaoModels.add(daLiBaoModel);
        }
        liBaoAdapter = new WelcomeStudentDaLiBaoAdapter(activity, liBaoModels);
        lvWelcomestudentDalibao.setAdapter(liBaoAdapter);

    }

    /**
     * 关闭加载对话框
     */
    private void dimess() {
        if (loadDiaog.isShowing()) {
            loadDiaog.dismiss();
        }
    }

    /**
     * 跳转web
     *
     * @param url
     */
    private void toWeb(String url) {
        String ticket = Ticket.getFunctionTicket(FunctionID);
        try {
            Intent intent = new Intent(activity, BrowserActivity.class);
            String addString = "?";
            if (url.contains("?")) {
                addString = "&";
            }
            intent.putExtra("url", url + addString + "userid=" + Constants.number + "&ticket=" + ticket);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "系统出错了", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 处理器处理
     */
    private void initProcessor() {

        pullWelcomeStudentRefreshview.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefreshView view) {
                // 下拉刷新
                getUseinfo();
                getNotice();
                initData();

            }
        });

        pullWelcomeStudentRefreshview.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefreshView view) {
                // 上拉刷新
                getUseinfo();
                getNotice();
                initData();

            }
        });
        // 入学准备点击事件
        lvWelcomestudentReady.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // FIXME 增加完成接口调用
                if (JKFWBS_Y.equals(welcomeStudentModels.get(position).getJkfwbs())) {
                    setRXZBLB(welcomeStudentModels.get(position).getGnid());
                }

                if (welcomeStudentModels.get(position).getIos_notice().equals("PushPerfectPage")) {

                    if (!welcomeStudentModels.get(position).getGnzt().equals("3")) {
                        // 如果功能完成就跳到个人详情
                        if (welcomeStudentModels.get(position).getGnzt().equals("1")) {
                            initGNData();
                            //
                        } else {
                            Map<String, String> parames = new HashMap<>();
                            parames.put("cls", welcomeStudentModels.get(position).getCls());
                            parames.put("FunctionID", welcomeStudentModels.get(position).getGnid());
                            parames.put("name", welcomeStudentModels.get(position).getGnmc());
                            parames.put("function_type", "1");
                            new _功能跳转().Jump(activity, parames);
                        }

                    } else {
                        Toast.makeText(activity, "该功能未解锁", Toast.LENGTH_SHORT).show();
                    }

                } else if (welcomeStudentModels.get(position).getIos_notice().equals("PushWebPage")) {

                    if (!welcomeStudentModels.get(position).getGnzt().equals("3")) {
                        if (!welcomeStudentModels.get(position).getGnzt().equals("1")) {
                            toWeb(welcomeStudentModels.get(position).getUrl());
                        } else {
                            toWeb(welcomeStudentModels.get(position).getUrl());
                        }
                    } else {
                        Toast.makeText(activity, "该功能未解锁", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

        // 新生大礼包列表点击事件

        lvWelcomestudentDalibao.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    for (int i = 0; i < welcomeStudentModels.size(); i++) {
                        if (Integer.valueOf(welcomeStudentModels.get(i).getGnyxj()) < 1) {
                            yxjlist.add(welcomeStudentModels.get(i).getGnyxj());
                        }

                    }
                    for (int i = 0; i < welcomeStudentModels.size(); i++) {
                        // 将优先级高的完成的找出来 与总共有多少个优先级的对比如果优先级的全部完成就可以允许用户点击
                        if (canScroll) {
                            ++yxj;
                            if (yxjlist.size() == yxj) {
                                toWeb(liBaoModels.get(position).getFunction_url());
                                // 满足要求结束当前循环
                                break;
                            }
                            // 不符合要求跳转到下次循环
                            continue;
                        } else {
                            // 当应用未完成我们提示用户去完成
                            if (!welcomeStudentModels.get(i).getGnzt().equals("1")) {
                                if (welcomeStudentModels.get(i).getIos_notice().equals("PushWebPage")) {
                                    // 跳转web
                                    hindDiog(welcomeStudentModels.get(i).getGnzt(), welcomeStudentModels.get(i).getGnmc(), welcomeStudentModels.get(i).getUrl(), welcomeStudentModels.get(i).getIos_notice(), lvWelcomestudentDalibao);
                                    break;
                                } else {
                                    // 跳转原生
                                    hindDiog(welcomeStudentModels.get(i).getGnzt(), welcomeStudentModels.get(i).getGnmc(), welcomeStudentModels.get(i).getCls(), welcomeStudentModels.get(i).getIos_notice(), lvWelcomestudentDalibao);
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(activity, "请完善入学准备相关功能", Toast.LENGTH_SHORT).show();
                }

            }

        });

        rlWelcomstudentTitle.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                // 获得标题高度
                titleHeight = rlWelcomstudentTitle.getHeight();
            }
        });

        warnDialog.setDialogOnClickListener(new DialogOnClickListener() {
            @Override
            public void Cancel() {
                warnDialog.dismiss();
            }

            @Override
            public void Submit() {
                warnDialog.dismiss();
            }
        });
        // 隐藏取消按钮
        warnDialog.setDialogButton(WarnDialog.HideType.CANCEL);

    }

    private void setRXZBLB(String gnid) {
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("gnid", gnid);
        params.put("ticket", Ticket.getFunctionTicket(FunctionID));
        VolleyRequest.RequestPost(_链接地址导航.Ydyx.OVERTASK.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {

            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });

    }

    private void initGNData() {
        List<Map<String, String>> listmap = ExampleApplication.getInstance().getSqliteHelper().rawQuery("select  gndate from rxzbgn where userid=?", new String[]
                {
                        Constants.number
                });
        if (listmap.size() > 0) {
            String date = listmap.get(0).get("gndate");
            if (Utils.getTodayDate().equals(date)) {
                startActivity(new Intent(activity, DataInputActivity.class));
            } else {
                Intent intent = new Intent(activity, PersnaldataDetailsActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(activity, PersnaldataDetailsActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 提示跳转到相应的类
     *
     * @param state
     * @param tois
     */
    private void hindDiog(String state, final String tois, final String classname, final String isweb, View v) {
        hindDialog.setlConfirmTextStyle("完善" + wwcgnmc, 0, 0);
        hindDialog.setDialogButton(HideType.CANCEL);
        hindDialog.setContent("还没有完善" + wwcgnmc);
        hindDialog.showWindow(v);
        hindDialog.setDialogOnClickListener(new DialogOnClickListener() {

            @Override
            public void Submit() {

                // if (isweb.equals("PushPerfectPage"))
                // {
                // // 原生
                // Map<String, String> parames = new HashMap<>();
                // parames.put("cls", classname);
                // parames.put("name", tois);
                // parames.put("function_type", "1");
                // new _功能跳转().Jump(activity, parames);
                // } else
                // {
                // // web
                toWeb(classname);
                // }

                hindDialog.dismiss();
            }

            @Override
            public void Cancel() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_welcomestudent_erweima:
                // 二维码
                try {
                    getToApp(userInfoModel.getContent(), 4);
                } catch (Exception e1) {

                    e1.printStackTrace();
                }
                // Intent intentewm = new Intent(activity, ErWeimaActivity.class);
                // intentewm.putExtra("ewm", ewmContent);
                // startActivity(intentewm);
                break;
            case R.id.txt_welcomestudent_helper:
                // 说明
                Intent intentewm = new Intent(activity, BrowserActivity.class);
                intentewm.putExtra("url", "http://ljxq.cqut.edu.cn/zsyxsm.htm");
                startActivity(intentewm);
                break;
            case R.id.img_welcomestudent_quiz:
                // 问题提示框
                warnDialog.showWindow(imgWelcomestudentQuiz);
                break;
            case R.id.ly_welcomestudent_notice:
                // 通知通告
                try {
                    getToApp(userInfoModel.getContent(), 6);
                } catch (Exception e1) {

                    e1.printStackTrace();
                }
                // Intent intentweb = new Intent(activity, Web.class);
                // intentweb.putExtra("url", noticeurl);
                // startActivity(intentweb);
                break;
            case R.id.img_welcomestudent_hand:
                if (Constants.isNetworkAvailable(activity)) {
                    if (Constants.ISOVER) {
                        Intent intentuserdetails = new Intent(activity, PersnaldataDetailsActivity.class);
                        startActivity(intentuserdetails);

                    } else {
                        handDialog = new WarnDialog(activity);
                        handDialog.setDialogButton(WarnDialog.HideType.CANCEL);
                        // warnDialog.setContent("哈哈");
                        // warnDialog.setDialogTitleImage(R.mipmap.ico_userhand);
                        // warnDialog.setCancelTextStyle(null,R.color.color_red,R.color.color_bleu);
                        try {
                            if (!canScroll) {

                                handDialog.setDialogButton(HideType.CANCEL);
                                handDialog.setlConfirmTextStyle("完善" + wwcgnmc, 0, 0);
                                handDialog.setContent("还没有完善" + wwcgnmc);
                            }

                            handDialog.setDialogOnClickListener(new DialogOnClickListener() {

                                @Override
                                public void Submit() {

                                    if (!welcomeStudentModels.get(0).getGnzt().equals("1")) {
                                        // 跳转web
                                        toWeb(welcomeStudentModels.get(0).getUrl());
                                        handDialog.dismiss();
                                    } else if (!welcomeStudentModels.get(1).getGnzt().equals("1")) {
                                        startActivity(new Intent(activity, UserInfoCodeActivity.class));
                                        handDialog.dismiss();
                                    } else {
                                        startActivity(new Intent(activity, PersnaldataDetailsActivity.class));
                                        handDialog.dismiss();
                                    }

                                }

                                @Override
                                public void Cancel() {

                                }
                            });
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        handDialog.showWindow(imgWelcomestudentHand);
                    }
                }

                break;

            case R.id.txt_welcomestudent_payment:
                // 缴费信息
                try {
                    getToApp(userInfoModel.getContent(), 2);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case R.id.txt_welcomestudent_dorm:
                // 寝室信息
                try {
                    getToApp(userInfoModel.getContent(), 3);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case R.id.txt_welcomestudent_ems:
                // ems
                try {
                    getToApp(userInfoModel.getContent(), 1);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case R.id.txt_welcomestudent_noticebook:

                try {
                    getToApp(userInfoModel.getContent(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;

        }
    }

    private void getToApp(RxzbUserInfoModel.ContentBean bean, int c) throws Exception {
        // for (int i = 0; i < welcomeStudentModels.size(); i++)
        // {
        // if (Integer.valueOf(welcomeStudentModels.get(i).getGnyxj()) < 1)
        // {
        // yxjlists.add(welcomeStudentModels.get(i).getGnyxj());
        // continue;
        // }
        //
        // }
        //
        // for (int i = 0; i < welcomeStudentModels.size(); i++)
        // {
        //
        // // 将优先级高的完成的找出来 与总共有多少个优先级的对比如果优先级的全部完成就可以允许用户点击
        // if (welcomeStudentModels.get(i).getGnzt().equals("1") &&
        // Integer.valueOf(welcomeStudentModels.get(i).getGnyxj()) < 1)
        // {
        // ++yxjs;
        if (canScroll) {
            switch (c) {
                case 0:
                    toWeb(bean.getDztzscx());
                    break;
                case 1:
                    toWeb(bean.getEmscx());
                    break;
                case 2:
                    toWeb(bean.getXfcx());
                    break;

                case 3:
                    toWeb(bean.getQscx());
                    break;

                case 4:
                    Intent intentewm = new Intent(activity, ErWeimaActivity.class);
                    intentewm.putExtra("ewm", bean.getEwm());
                    startActivity(intentewm);
                    break;
                case 6:
                    if (!TextUtils.isEmpty(noticeurl)) {
                        Intent intentweb = new Intent(activity, BrowserActivity.class);

                        intentweb.putExtra("url", noticeurl);
                        startActivity(intentweb);
                    }

                    break;

            }
            // // 不符合要求跳转到下次循环
            // continue;
        } else

        {
            hindDiog("", "", "", "", lvWelcomestudentDalibao);
            // 当应用未完成我们提示用户去完成
            // if (!welcomeStudentModels.get(i).getGnzt().equals("1"))
            // {
            // if
            // (welcomeStudentModels.get(i).getIos_notice().equals("PushWebPage"))
            // {
            //
            // // 跳转web
            // hindDiog(welcomeStudentModels.get(i).getGnzt(),
            // welcomeStudentModels.get(i).getGnmc(),
            // welcomeStudentModels.get(i).getUrl(),
            // welcomeStudentModels.get(i).getIos_notice(),
            // lvWelcomestudentDalibao);
            // break;
            //
            // } else
            // {
            // // 跳转原生
            // hindDiog(welcomeStudentModels.get(i).getGnzt(),
            // welcomeStudentModels.get(i).getGnmc(),
            // welcomeStudentModels.get(i).getCls(),
            // welcomeStudentModels.get(i).getIos_notice(),
            // lvWelcomestudentDalibao);
            // break;
            // }
            // }
        }

        //
        // }
    }

    @Override
    public void onResume() {

        super.onResume();
        getUseinfo();
        getNotice();
        initData();
    }

    @Override
    public void onScroll(MonitorScrollView view, int x, int y, int oldx, int oldy) {
        // if (y > titleHeight) {
        // // 显示标题栏
        // Drawable
        // drawable=ContextCompat.getDrawable(getContext(),R.mipmap.ico_left);
        // drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        // rlWelcomstudentTitle.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_rectangular_border));
        // textWelcomstudentTitle.setTextColor(ContextCompat.getColor(getContext(),R.color.color_black));
        // txtBack.setTextColor(ContextCompat.getColor(getContext(),R.color.color_black));
        // txtBack.setCompoundDrawables(drawable,null,null,null);
        //
        // } else if (y < titleHeight) {
        // // 隐藏标题
        // Drawable
        // drawable=ContextCompat.getDrawable(getContext(),R.mipmap.back);
        // drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        // rlWelcomstudentTitle.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.color_white_gray));
        // textWelcomstudentTitle.setTextColor(ContextCompat.getColor(getContext(),R.color.color_white));
        // txtBack.setTextColor(ContextCompat.getColor(getContext(),R.color.color_white));
        // txtBack.setCompoundDrawables(drawable,null,null,null);
        //
        // }
    }

}
