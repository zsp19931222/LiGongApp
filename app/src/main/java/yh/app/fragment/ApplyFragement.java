package yh.app.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import yh.app.acticity.AppManage;
import yh.app.adapter.OtherApplyAdapter;
import org.androidpn.push.Constants;


import yh.app.model.ApplyModel;
import yh.app.model.DAModel;
import yh.app.tool.FunctionAT;
import yh.app.tool.MD5;
import yh.app.tool.Term;
import com.example.app3.HomePageActivity;
import yh.app.utils.ImageAt;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.MonitorScrollView.OnScrollListener;
import 云华.智慧校园.功能.ViewFlipperAdapter;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具._链接地址导航;
import yh.tool.widget.MyListview;
import yh.tool.widget.SlideShowView;

/**
 * 应用模块
 * 
 * @author anmin
 *
 */
public class ApplyFragement extends Fragment implements OnScrollListener {
	private Activity activity;
	private View applyview;
	// 用于装应用数据
	private MyListview list_apply_otherapp;
	private ViewFlipper apply_viewpager;// 广告
	private View[] adView;// 用于装广告图片
	// 应用
	private MonitorScrollView monitorScrollView;
	private RelativeLayout rlayout_title;// 标题
	private TextView txt_appmanage;//管理按钮

	// 数据
	private OtherApplyAdapter otherappdatapter;
	private ApplyModel appmodel;
	private List<ApplyModel.OTHERAPPBean> otherlist;// 推荐应用集合

	private TranslateAnimation mHiddenAction, mShowAction;
	private int titleHeight = 0;
	/**
	 * 广告
	 */
	private List<DAModel.ContentBean> dalist;
	private DAModel damoel;
	private SlideShowView da_viwe;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		applyview = inflater.inflate(R.layout.home1_, container, false);
		return applyview;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
		if (Constants.isNetworkAvailable(activity)) {
			initFunctionData();
		} else {
			initThisApplyData();
		}
		new Term().doit();
		initDA();

		// 应用页动画
		Hidden();
		ShowView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		list_apply_otherapp = (MyListview) applyview.findViewById(R.id.list_apply_otherapp);
		apply_viewpager = (ViewFlipper) applyview.findViewById(R.id.viewFlipper);
		monitorScrollView = (MonitorScrollView) applyview.findViewById(R.id.srrolview_apply);
		monitorScrollView.setOnScrollListener(this);
		rlayout_title = (RelativeLayout) applyview.findViewById(R.id.rlayout_title);
		da_viwe = (SlideShowView) applyview.findViewById(R.id.da_view);
		rlayout_title = (RelativeLayout) applyview.findViewById(R.id.rlayout_title);
		rlayout_title.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 获得标题高度
				titleHeight = rlayout_title.getHeight();
			}
		});
		txt_appmanage = (TextView) applyview.findViewById(R.id.txt_appmanage);

		// 应用管理
		txt_appmanage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentappmanage = new Intent(activity, AppManage.class);
				startActivity(intentappmanage);
			}
		});
	}

	/**
	 * 加载应用网络数据
	 */
	private void initFunctionData() {
		String ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
		final Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", ticket);
		map.put("Version", "0");

		VolleyRequest.RequestPost(_链接地址导航.UIA.功能列表.getUrl(), map, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				if (null != result) {

					String json = null;
					// 替换反斜杠
					json = result.replace("\\", "");
					// 取掉引号
					json = json.substring(1, json.length() - 1);
					doSuccess(json);

				}
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				initThisApplyData();
			}
		});

	}

	/**
	 * 加载本地网络数据
	 */
	private void initThisApplyData() {
		List<Map<String, String>> listmap = ExampleApplication.getInstance().getSqliteHelper()
				.rawQuery("select  appjson from yhappmanagpublic where userid=?", new String[] { Constants.number });
		if (listmap.size() > 0) {
			String appljson = listmap.get(0).get("appjson");
			if (null != appljson) {
				doSuccess(appljson);
			}
		}

	}

	// 数据绑定
	public void doSuccess(String s) {

		appmodel = ExampleApplication.getInstance().getGson().fromJson(s, ApplyModel.class);
		otherlist = appmodel.getOTHER_APP();
		otherappdatapter = new OtherApplyAdapter(activity, otherlist);
		list_apply_otherapp.setAdapter(otherappdatapter);

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
		// 异步执行避免视图还没绘制完成就调用
		monitorScrollView.post(new Runnable() {

			@Override
			public void run() {
				// 设置scrollview回到顶部解决应用页打开不是顶部问题
				monitorScrollView.fullScroll(ScrollView.FOCUS_UP);
			}
		});
		// 添加我的应用数据
		ExampleApplication.getInstance().getSqliteHelper().execSQL(
				"replace into yhappmanagpublic(appjson,userid) values(?,?)", new Object[] { s, Constants.number });

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
					damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
					dalist = damoel.getContent();
					da_viwe.setImageUris(dalist);
				}
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * 隐藏
	 */
	private void Hidden() {
		mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
	}

	/**
	 * 显示
	 */
	private void ShowView() {
		mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
		mShowAction.setDuration(500);

	}

	@Override
	public void onScroll(MonitorScrollView view, int x, int y, int oldx, int oldy) {
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
