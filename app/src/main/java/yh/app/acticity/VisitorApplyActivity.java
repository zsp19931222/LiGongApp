package yh.app.acticity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import yh.app.adapter.VisitorApplyAdapter;
import org.androidpn.push.Constants;
import yh.app.model.ApplyModel;
import yh.app.model.DAModel;
import yh.app.tool.FunctionAT;
import yh.app.tool.MD5;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.MonitorScrollView.OnScrollListener;
import yh.tool.widget.MyListview;
import yh.tool.widget.SlideShowView;
import yh.tool.widget.swipebacklayout.SwipeBackActivity;
import yh.tool.widget.swipebacklayout.SwipeBackLayout;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 游客登录应用列表
 * 
 * @author 云华科技
 * @date 2017年5月15日
 */

public class VisitorApplyActivity extends SwipeBackActivity implements OnScrollListener{
	// 用于装应用数据
	private MyListview list_apply_otherapp;
	private int titleHeight = 0;
	// 应用
	private MonitorScrollView monitorScrollView;
	private RelativeLayout rlayout_title;
	private TranslateAnimation mHiddenAction, mShowAction;
	private TextView txt_appmanage;
	
	private VisitorApplyAdapter visitorApplyAdapter;
	private ApplyModel appmodel;
	private List<ApplyModel.OTHERAPPBean> otherlist;// 推荐应用集合
	private android.app.FragmentManager fragmentManager;
	/**
	 * 广告
	 */
	private List<DAModel.ContentBean> dalist;
	private DAModel damoel;
	private SlideShowView da_viwe;
	private List<String> listda;
	private LoadDiaog diaog;
	
	//滑动退出
	private SwipeBackLayout mSwipeBackLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home1_);
		initView();
		initFunctionData();
		Hidden();
		ShowView();
		initDA();
	}

	private void initView() {
		// 应用
		list_apply_otherapp = (MyListview) findViewById(R.id.list_apply_otherapp);
		da_viwe = (SlideShowView) findViewById(R.id.da_view);

		monitorScrollView = (MonitorScrollView) findViewById(R.id.srrolview_apply);
		monitorScrollView.setOnScrollListener(this);
		
		rlayout_title = (RelativeLayout) findViewById(R.id.rlayout_title);
		txt_appmanage = (TextView) findViewById(R.id.txt_appmanage);

		// 应用管理
		txt_appmanage.setVisibility(View.GONE);
        //游客账号弃用
		rlayout_title.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 获得标题高度
				titleHeight = rlayout_title.getHeight();
			}
		});
		
		diaog=new LoadDiaog(this);
		diaog.show();
		
		mSwipeBackLayout=getSwipeBackLayout();
		 //设置可以滑动的区域，推荐用屏幕像素的一半来指定
        mSwipeBackLayout.setEdgeSize(400);
        //设定滑动关闭的方向，SwipeBackLayout.EDGE_ALL表示向下、左、右滑动均可。EDGE_LEFT，EDGE_RIGHT，EDGE_BOTTOM
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        
        
        
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
					new FunctionAT(null).getFunctionList();
					
				}
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});

		// new FunctionAT(null).getFunctionList();
	}
	
	
	// 数据绑定
		public void doSuccess(String s) {

			appmodel = ExampleApplication.getInstance().getGson().fromJson(s, ApplyModel.class);
			otherlist = appmodel.getOTHER_APP();
			visitorApplyAdapter = new VisitorApplyAdapter(this, otherlist);
			list_apply_otherapp.setAdapter(visitorApplyAdapter);

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
			
			if (diaog.isShowing()) {
				diaog.dismiss();
			}
			

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
						if(jsonObject.getBoolean("boolean")){
							damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
							
							dalist = damoel.getContent();
							if (dalist.size()>0) {
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
	

	
	
	
}
