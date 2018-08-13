package yh.app.uiengine;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import java.util.List;

import yh.app.fragment.AimFragment;
import yh.app.fragment.AimHallFragment;
import yh.app.fragment.EvaluationFragment;
import yh.app.model.DAModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.MonitorScrollView;
import yh.tool.widget.MonitorScrollView.OnScrollListener;
import yh.tool.widget.SlideShowView;
import 云华.智慧校园.工具._链接地址导航;

public class ZhuYeActivity implements OnClickListener,OnScrollListener {
	
	private ViewFlipper flipper;
	private TextView txt_home_aim;// 我的目标
	private TextView txt_home_aimhall;// 目标广场
	private TextView txt_home_evaluation;// 职业测评

	private AimFragment aimfragment;// 我的目标
	private AimHallFragment aimHallfragment; // 目标广场
	private EvaluationFragment evaluationfragment;// 职业测评

	private FragmentTransaction transaction;
	private android.app.FragmentManager fragmentManager;
    //广告
	private List<DAModel.ContentBean> dalist;
	private DAModel damoel;
	private SlideShowView da_viwe;
    //当前上下文对象 视图
	private Activity context;
	private View mainView;
	
	private MonitorScrollView msv_zhuye;
	//主页标题父布局
	private LinearLayout ly_zhuye_title;
	private int titleHeight = 0;//记录标题高度
	//标题弹出动画
	private TranslateAnimation mHiddenAction, mShowAction;

	public ZhuYeActivity(Activity context) {
		this.context = context;
//		mainView = LayoutInflater.from(context).inflate(R.layout.zhuye_layout, null, false);
//		//初始化视图
//		initView();
//		//初始化主页功能模块
//		initFragment(0);
//		//初始化广告
//		initDA();
//		//初始化动画
//		Hidden();
//		ShowView();
	}

	public View getMainView() {
		return mainView;
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		damoel = new DAModel();
		da_viwe = (SlideShowView) mainView.findViewById(R.id.da_view);

		txt_home_aim = (TextView) mainView.findViewById(R.id.txt_home_aim);
		txt_home_aim.setOnClickListener(this);
		txt_home_aimhall = (TextView) mainView.findViewById(R.id.txt_home_aimhall);
		txt_home_aimhall.setOnClickListener(this);
		txt_home_evaluation = (TextView) mainView.findViewById(R.id.txt_home_evaluation);
		txt_home_evaluation.setOnClickListener(this);
		
		msv_zhuye=(MonitorScrollView) mainView.findViewById(R.id.msv_zhuye);
		ly_zhuye_title=(LinearLayout) mainView.findViewById(R.id.ly_zhuye_title);
		ly_zhuye_title.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// 获得标题高度
				titleHeight = ly_zhuye_title.getHeight();
			}
		});
	}

	/**
	 * 根据游标切换相应的界面
	 */
	private void initFragment(int i) {
		fragmentManager = context.getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (i) {
		case 0:
			if (aimfragment == null) {
				aimfragment = new AimFragment();

				transaction.add(R.id.fl_message, aimfragment);
			} else {
				transaction.show(aimfragment);
			}
			break;
		case 1:
			if (aimHallfragment == null) {
				aimHallfragment = new AimHallFragment();
				transaction.add(R.id.fl_message, aimHallfragment);
			} else {
				transaction.show(aimHallfragment);
			}
			break;
		case 2:
			if (evaluationfragment == null) {
				evaluationfragment = new EvaluationFragment();
				transaction.add(R.id.fl_message, evaluationfragment);
			} else {
				transaction.show(evaluationfragment);
			}
			break;
		}
		transaction.commit();
	}

	/***
	 * 隐藏Fragment
	 */
	private void hideFragment(FragmentTransaction transaction) {
		if (aimfragment != null) {
			transaction.hide(aimfragment);
		}
		if (aimHallfragment != null) {
			transaction.hide(aimHallfragment);
		}
		if (evaluationfragment != null) {
			transaction.hide(evaluationfragment);
		}

	}

	/**
	 * 改变底部按钮状态
	 */
	private void onChangerState(int state) {
		onRecover();
		switch (state) {
		case 0:
			txt_home_aim.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			break;
		case 1:
			txt_home_aimhall.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			break;
		case 2:
			txt_home_evaluation.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			break;
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
		
		msv_zhuye.post(new Runnable() {
			
			@Override
			public void run() {
			msv_zhuye.fullScroll(ScrollView.FOCUS_UP);	
			}
		});
	}

	

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		onRecover();
		switch (arg0.getId()) {

		case R.id.txt_home_aim:
			// 我的目标
			// vp_home.setCurrentItem(0);
			initFragment(0);
			onChangerState(0);
			txt_home_aim.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			break;
		case R.id.txt_home_aimhall:
			// 目标广场
			// vp_home.setCurrentItem(1);
			initFragment(1);
			onChangerState(1);
			txt_home_aimhall.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			break;
		case R.id.txt_home_evaluation:
			// vp_home.setCurrentItem(2);
			initFragment(2);
			onChangerState(2);
			txt_home_evaluation.setBackgroundColor(context.getResources().getColor(R.color.color_gray));
			// 职业测评
			break;

		default:
			break;
		}
	}

	/**
	 * 恢复按钮样式
	 */
	private void onRecover() {

		txt_home_aim.setBackgroundColor(context.getResources().getColor(R.color.color_transparent));

		txt_home_aimhall.setBackgroundColor(context.getResources().getColor(R.color.color_transparent));

		txt_home_evaluation.setBackgroundColor(context.getResources().getColor(R.color.color_transparent));
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
			if (ly_zhuye_title.getVisibility() == View.INVISIBLE) {
				ly_zhuye_title.startAnimation(mShowAction);
				ly_zhuye_title.setVisibility(View.VISIBLE);
			}

		} else if (y < titleHeight) {
			// 隐藏标题
			if (ly_zhuye_title.getVisibility() == View.VISIBLE) {
				ly_zhuye_title.setAnimation(mHiddenAction);
				ly_zhuye_title.setVisibility(View.INVISIBLE);
			}

		}
	}

}
