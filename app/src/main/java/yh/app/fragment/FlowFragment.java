package yh.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.acticity.ErWeimaActivity;
import yh.app.acticity.PersnaldataDetailsActivity;
import yh.app.adapter.WangShangAdapter;
import yh.app.adapter.YXXCAdapter;
import yh.app.listeners.DialogOnClickListener;
import yh.app.model.RxzbUserInfoModel;
import yh.app.model.WangShangModel;
import yh.app.model.YXXCModel;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.Utils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.view.WarnDialog;
import yh.app.view.WarnDialog.HideType;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.MyListview;
import yh.tool.widget.ProgressView;
import yh.tool.widget.PullToRefreshView;
import yh.tool.widget.ScrollGridview;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 报道流程
 */
public class FlowFragment extends Fragment implements View.OnClickListener
{

    private ImageView imgFlowErweima;
    private ImageView imgFlowHand;
    private TextView txtFlowName;
    private TextView txtFlowSchool;
    private TextView txtFlowMajor;

    private ImageView imgFlowQuiz;
    private MyListview lvFlowWangshang;
    private ScrollGridview gvXianchangReady;

    /**
     * 报到进度
     */
    private ProgressView pvFlowProgress;
    protected static final int WHAT_INCREASE = 1;
    private int progress;
    private int maxprogress;
    /**
     * 迎新现场
     */
    private YXXCAdapter yxxcAdapter;
    private List<YXXCModel.ContentBean> yxxcModels;
    private YXXCModel yxxcModel;
    private TextView textWelcomstudentTitle;
    private MonitorScrollView msvFlow;

    private WarnDialog warnDialog, hintWarnDialog;
    private Activity activity;

    /**
     * 用户信息
     */
    private String FunctionID;
    private RxzbUserInfoModel userInfoModel;
    private String ewmContent;
    private Drawable nav_right;
    /**
     * 网上迎新
     */
    private WangShangAdapter wangShangAdapter;
    private WangShangModel wangShangModel;
    private List<WangShangModel.ContentBean> wangShangModels;
    private SqliteHelper sqhelper;

    private LoadDiaog diaog;
    private Handler handler = new Handler()
    {
	public void handleMessage(Message msg)
	{
	    if (maxprogress == 0)
	    {
		return;
	    }
	    progress += 10;
	    pvFlowProgress.setProgress(progress);
	    handler.sendEmptyMessageDelayed(WHAT_INCREASE, Utils.getRadomNumber(50, 200));
	    if (progress >= maxprogress)
	    {
		handler.removeMessages(WHAT_INCREASE);
	    }
	}
    };
    private PullToRefreshView pullRefreshview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	return inflater.inflate(R.layout.fragment_flow, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
	super.onActivityCreated(savedInstanceState);
	activity = getActivity();
	initView();

	if (Constants.isNetworkAvailable(activity))
	{

	    onLister();
	    initData();
	    // initWangShangData();
	    getUseinfo();
	}
    }

    private void initView()
    {
	FunctionID = activity.getIntent().getStringExtra("FunctionID");
	sqhelper = ExampleApplication.getInstance().getSqliteHelper();
	// 二维码
	imgFlowErweima = (ImageView) getView().findViewById(R.id.img_flow_erweima);
	imgFlowErweima.setOnClickListener(this);
	// 头像
	imgFlowHand = (ImageView) getView().findViewById(R.id.img_flow_hand);
	imgFlowHand.setOnClickListener(this);
	// 用户名
	txtFlowName = (TextView) getView().findViewById(R.id.txt_flow_name);
	// 学校名称
	txtFlowSchool = (TextView) getView().findViewById(R.id.txt_flow_school);
	// 专业
	txtFlowMajor = (TextView) getView().findViewById(R.id.txt_flow_major);
	// 进度
	pvFlowProgress = (ProgressView) getView().findViewById(R.id.pv_flow_progress);
	// 问题
	imgFlowQuiz = (ImageView) getView().findViewById(R.id.img_flow_quiz);
	imgFlowQuiz.setOnClickListener(this);
	// 网上迎新列表
	lvFlowWangshang = (MyListview) getView().findViewById(R.id.lv_flow_wangshang);

	// 迎新现场列表
	gvXianchangReady = (ScrollGridview) getView().findViewById(R.id.gv_xianchang_ready);
	msvFlow = (MonitorScrollView) getView().findViewById(R.id.msv_flow);

	pullRefreshview = (PullToRefreshView) getView().findViewById(R.id.pull_refreshview);

	pullRefreshview.setOnHeaderRefreshListener(new PullToRefreshView.OnHeaderRefreshListener()
	{
	    @Override
	    public void onHeaderRefresh(PullToRefreshView view)
	    {
		getUseinfo();
		initData();
		// initWangShangData();

	    }
	});

	pullRefreshview.setOnFooterRefreshListener(new PullToRefreshView.OnFooterRefreshListener()
	{
	    @Override
	    public void onFooterRefresh(PullToRefreshView view)
	    {
		getUseinfo();
		initData();

	    }
	});

	diaog = new LoadDiaog(activity);
    }

    /**
     * 加载本地用户数据网络数据
     */
    private void initThisUserinfoData()
    {
	List<Map<String, String>> listmap = sqhelper.rawQuery("select  userinfodata from userinfo where userid=?", new String[]
	{
		Constants.number
	});
	if (listmap.size() > 0)
	{

	    String appljson = listmap.get(0).get("userinfodata");
	    if (null != appljson)
	    {
		try
		{
		    userInfoModel = ExampleApplication.getInstance().getGson().fromJson(appljson, RxzbUserInfoModel.class);
		    bindUserInfoData(userInfoModel.getContent());
		} catch (Exception e)
		{

		}
	    }
	}

    }

    /**
     * 关闭对话框
     */
    private void dismss()
    {
	if (diaog.isShowing())
	{
	    diaog.dismiss();
	}
    }

    /**
     * 获得学生信息
     */
    private void getUseinfo()
    {

	if (FunctionID == null)
	{
	    return;
	}
	Map<String, String> params = new HashMap<>();
	params.put("userid", Constants.number);
	params.put("ticket", Ticket.getFunctionTicket(FunctionID));
	VolleyRequest.RequestPost(_链接地址导航.Ydyx.STUDENTINFO.getUrl(), params, new VolleyInterface()
	{

	    @Override
	    public void onMySuccess(String result)
	    {
		try
		{

		    userInfoModel = ExampleApplication.getInstance().getGson().fromJson(result, RxzbUserInfoModel.class);
		    bindUserInfoData(userInfoModel.getContent());

		} catch (Exception e)
		{
		    pullRefreshview.onHeaderRefreshComplete();
		    initThisUserinfoData();
		}

	    }

	    @Override
	    public void onMyError(VolleyError error)
	    {
		pullRefreshview.onHeaderRefreshComplete();
		initThisUserinfoData();

	    }
	});
    }

    /**
     * 
     * 绑定用户数据
     * 
     * @param infoModel
     */
    private void bindUserInfoData(RxzbUserInfoModel.ContentBean infoModel) throws Exception
    {
	ewmContent = infoModel.getEwm();
	// 修改用户性别图标
	if (infoModel.getXbdm().equals("2"))
	{
	    nav_right = getResources().getDrawable(R.drawable.ico_woman);
	} else
	{
	    nav_right = getResources().getDrawable(R.drawable.ico_man);
	}

	nav_right.setBounds(0, 0, nav_right.getMinimumWidth(), nav_right.getMinimumHeight());
	// 左上右下
	txtFlowName.setCompoundDrawables(null, null, nav_right, null);
	txtFlowName.setText(infoModel.getXm());
	txtFlowSchool.setText(infoModel.getXymc());
	txtFlowMajor.setText(infoModel.getZymc());
	maxprogress = Integer.valueOf(infoModel.getYwc()) * 10;
	pvFlowProgress.setmaxProgress(infoModel.getZsl());
	int zsl = Integer.valueOf(infoModel.getZsl());
	int ywc = Integer.valueOf(infoModel.getYwc());
	pvFlowProgress.setProgress(ywc * 100 / zsl);
	// increase();
	pullRefreshview.onHeaderRefreshComplete();

    }

    String cls;

    private void onLister()
    {
	lvFlowWangshang.setOnItemClickListener(new OnItemClickListener()
	{

	    @Override
	    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    {

		// 原生跳转
		if (wangShangModels.get(arg2).getIos_notice().equals("PushPerfectPage"))
		{

		    switch (wangShangModels.get(arg2).getCjzt())
		    {
		    case "0":

		    case "1":
			Intent intent = new Intent(activity, PersnaldataDetailsActivity.class);
			startActivity(intent);
			break;
		    default:
			break;

		    }

		} else if (wangShangModels.get(arg2).getIos_notice().equals("PushWebPage"))
		{
		    // web 跳转
		    toWeb(wangShangModels.get(arg2).getLj());
		} else
		{
		    hintWarnDialog.showWindow(lvFlowWangshang);
		}

	    }
	});

	warnDialog = new WarnDialog(activity);
	warnDialog.setDialogButton(WarnDialog.HideType.CANCEL);
	warnDialog.setDialogOnClickListener(new DialogOnClickListener()
	{

	    @Override
	    public void Submit()
	    {
		warnDialog.dismiss();
	    }

	    @Override
	    public void Cancel()
	    {
		warnDialog.dismiss();
	    }
	});

	hintWarnDialog = new WarnDialog(activity);
	hintWarnDialog.setDialogButton(HideType.CANCEL);
	hintWarnDialog.setContent("请前往服务网完成当前环节");
	hintWarnDialog.setDialogOnClickListener(new DialogOnClickListener()
	{

	    @Override
	    public void Submit()
	    {
		hintWarnDialog.dismiss();
	    }

	    @Override
	    public void Cancel()
	    {
		hintWarnDialog.dismiss();
	    }
	});

    }

    /**
     * 跳转web
     * 
     * @param url
     */
    private void toWeb(String url)
    {
	String ticket = Ticket.getFunctionTicket(FunctionID);

	Intent intent = new Intent(activity, BrowserActivity.class);
	intent.putExtra("url", url + "?userid=" + Constants.number + "&ticket=" + ticket);
	startActivity(intent);

    }

    private void initData()
    {
	Map<String, String> params = new HashMap<>();
	params.put("userid", Constants.number);
	params.put("ticket", Ticket.getFunctionTicket(FunctionID));
	VolleyRequest.RequestPost(_链接地址导航.Ydyx.YXXC.getUrl(), params, new VolleyInterface()
	{

	    @Override
	    public void onMySuccess(String result)
	    {
		try
		{
		    if (CodeManage.NET_SUCCESS.equals(new JSONObject(result).getString("code")))
		    {

			yxxcModel = ExampleApplication.getInstance().getGson().fromJson(result, YXXCModel.class);
			yxxcModels = yxxcModel.getContent();
			yxxcAdapter = new YXXCAdapter(activity, yxxcModels);
			gvXianchangReady.setAdapter(yxxcAdapter);
			pullRefreshview.onFooterRefreshComplete();
		    }
		} catch (Exception e)
		{
		    pullRefreshview.onFooterRefreshComplete();
		}

	    }

	    @Override
	    public void onMyError(VolleyError error)
	    {
		Toast.makeText(activity, "系统异常", Toast.LENGTH_SHORT).show();
		pullRefreshview.onFooterRefreshComplete();
	    }
	});

    }

    private void increase()
    {
	progress = 0;
	pvFlowProgress.setProgress(0);
	handler.removeMessages(WHAT_INCREASE);
	handler.sendEmptyMessage(WHAT_INCREASE);
    }

    /**
     * 网上迎新
     */
    private void initWangShangData()
    {
	diaog.show();
	Map<String, String> params = new HashMap<>();
	params.put("userid", Constants.number);
	params.put("ticket", Ticket.getFunctionTicket(FunctionID));
	VolleyRequest.RequestPost(_链接地址导航.Ydyx.WSYX.getUrl(), params, new VolleyInterface()
	{

	    @Override
	    public void onMySuccess(String result)
	    {
		try
		{
		    wangShangModel = ExampleApplication.getInstance().getGson().fromJson(result, WangShangModel.class);
		    wangShangModels = wangShangModel.getContent();
		    wangShangAdapter = new WangShangAdapter(activity, wangShangModels);
		    lvFlowWangshang.setAdapter(wangShangAdapter);
		    pullRefreshview.onHeaderRefreshComplete();
		    // sqhelper.execSQL("replace into wsyx(wsyxdata,userid)
		    // values(?,?)",
		    // new Object[] { result, Constants.number });
		    dismss();
		} catch (Exception e)
		{
		    pullRefreshview.onHeaderRefreshComplete();
		    // initThisRXZBData();
		    dismss();
		}

	    }

	    @Override
	    public void onMyError(VolleyError error)
	    {
		pullRefreshview.onHeaderRefreshComplete();
		// initThisRXZBData();
		dismss();
	    }
	});

    }

    /**
     * 加载本地网络数据
     */
    private void initThisRXZBData()
    {
	List<Map<String, String>> listmap = sqhelper.rawQuery("select  wsyxdata from wsyx where userid=?", new String[]
	{
		Constants.number
	});
	if (listmap.size() > 0)
	{

	    String appljson = listmap.get(0).get("wsyxdata");
	    if (null != appljson)
	    {
		try
		{
		    wangShangModel = ExampleApplication.getInstance().getGson().fromJson(appljson, WangShangModel.class);
		    wangShangModels = wangShangModel.getContent();
		    wangShangAdapter = new WangShangAdapter(activity, wangShangModels);
		    lvFlowWangshang.setAdapter(wangShangAdapter);
		    pullRefreshview.onHeaderRefreshComplete();
		    dismss();
		} catch (Exception e)
		{
		    pullRefreshview.onHeaderRefreshComplete();
		    dismss();
		}

	    }
	}

    }

    @Override
    public void onClick(View v)
    {
	switch (v.getId())
	{
	case R.id.img_flow_erweima:
	    // 二维码
	    Intent intentewm = new Intent(activity, ErWeimaActivity.class);
	    intentewm.putExtra("ewm", ewmContent);
	    startActivity(intentewm);
	    break;
	case R.id.img_flow_hand:
	    // 头像
	    break;
	case R.id.img_flow_quiz:
	    warnDialog.showWindow(imgFlowQuiz);
	    break;
	}
    }

    @Override
    public void onResume()
    {
	super.onResume();
	initData();
	// initWangShangData();
	getUseinfo();
    }
}
