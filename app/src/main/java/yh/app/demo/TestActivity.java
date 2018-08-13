package yh.app.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import yh.app.acticity.CreateTargetAvtivity;
import yh.app.acticity.MubiaokuActivity;
import yh.app.fragment.AimFragment;
import yh.app.fragment.AimHallFragment;
import yh.app.fragment.EvaluationFragment;
import yh.app.model.DAModel;
import yh.app.utils.AnimationUtil;
import yh.app.utils.ImageAt;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.FloatingDraftButton;
import yh.tool.widget.SlideShowView;
import 云华.智慧校园.功能.ViewFlipperAdapter;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具._链接地址导航;

public class TestActivity extends Activity implements OnClickListener
{
	// 悬浮按钮
	private FloatingDraftButton floatingDraftButtonmain;
	private Button btn_chuanjianmubiao, btn_mubiaoku;
	private ViewFlipper flipper;
	private TextView txt_home_aim;// 我的目标
	private TextView txt_home_aimhall;// 目标广场
	private TextView txt_home_evaluation;// 职业测评

	private AimFragment aimfragment;// 我的目标
	private AimHallFragment aimHallfragment; // 目标广场
	private EvaluationFragment evaluationfragment;// 职业测评

	private FragmentTransaction transaction;
	private android.app.FragmentManager fragmentManager;

	private List<DAModel.ContentBean> dalist;
	private DAModel damoel;
	private SlideShowView da_viwe;
	private List<String> listda;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		initView();
		initFragment(0);
		initDA();
	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		damoel = new DAModel();
		listda = new ArrayList<>();
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
		txt_home_aim = (TextView) findViewById(R.id.txt_home_aim);
		txt_home_aim.setOnClickListener(this);
		txt_home_aimhall = (TextView) findViewById(R.id.txt_home_aimhall);
		txt_home_aimhall.setOnClickListener(this);
		txt_home_evaluation = (TextView) findViewById(R.id.txt_home_evaluation);
		txt_home_evaluation.setOnClickListener(this);

		da_viwe = (SlideShowView) findViewById(R.id.da_view);

		// 悬浮按钮
		floatingDraftButtonmain = (FloatingDraftButton) findViewById(R.id.btn_menls);

		btn_chuanjianmubiao = (Button) findViewById(R.id.btn_chuanjianmubiao);
		btn_mubiaoku = (Button) findViewById(R.id.btn_mubiaoku);
		floatingDraftButtonmain.registerButton(btn_chuanjianmubiao);
		floatingDraftButtonmain.registerButton(btn_mubiaoku);

		floatingDraftButtonmain.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				// 弹出动态Button
				AnimationUtil.slideButtons(TestActivity.this, floatingDraftButtonmain);

			}
		});
		/**
		 * 创建目标
		 */
		btn_chuanjianmubiao.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Intent intentmubiao = new Intent(TestActivity.this, CreateTargetAvtivity.class);
				startActivity(intentmubiao);
			}
		});

		/**
		 * 目标库
		 */
		btn_mubiaoku.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				Intent intentMubiaoku = new Intent(TestActivity.this, MubiaokuActivity.class);
				startActivity(intentMubiaoku);
			}
		});
	}

	/**
	 * 根据游标切换相应的界面
	 */
	private void initFragment(int i)
	{
		fragmentManager = this.getFragmentManager();
		transaction = fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch (i)
		{
		case 0:
			if (aimfragment == null)
			{
				aimfragment = new AimFragment();

				transaction.add(R.id.fl_message, aimfragment);
			} else
			{
				transaction.show(aimfragment);
			}
			break;
		case 1:
			if (aimHallfragment == null)
			{
				aimHallfragment = new AimHallFragment();
				transaction.add(R.id.fl_message, aimHallfragment);
			} else
			{
				transaction.show(aimHallfragment);
			}
			break;
		case 2:
			if (evaluationfragment == null)
			{
				evaluationfragment = new EvaluationFragment();
				transaction.add(R.id.fl_message, evaluationfragment);
			} else
			{
				transaction.show(evaluationfragment);
			}
			break;
		}
		transaction.commit();
	}

	/***
	 * 隐藏Fragment
	 */
	private void hideFragment(FragmentTransaction transaction)
	{
		if (aimfragment != null)
		{
			transaction.hide(aimfragment);
		}
		if (aimHallfragment != null)
		{
			transaction.hide(aimHallfragment);
		}
		if (evaluationfragment != null)
		{
			transaction.hide(evaluationfragment);
		}

	}

	/**
	 * 改变底部按钮状态
	 */
	private void onChangerState(int state)
	{
		onRecover();
		switch (state)
		{
		case 0:
			txt_home_aim.setBackgroundColor(getResources().getColor(R.color.color_gray));
			break;
		case 1:
			txt_home_aimhall.setBackgroundColor(getResources().getColor(R.color.color_gray));
			break;
		case 2:
			txt_home_evaluation.setBackgroundColor(getResources().getColor(R.color.color_gray));
			break;
		}
	}

	/**
	 * 广告轮播
	 */
	private void initDA()
	{

		VolleyRequest.RequestGet(_链接地址导航.DC.获取广告栏.getUrl(), new VolleyInterface()
		{

			@Override
			public void onMySuccess(String result)
			{
				// TODO Auto-generated method stub
				if (null != result)
				{
					damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
					dalist = damoel.getContent();
					da_viwe.setImageUris(dalist);
				}
			}

			@Override
			public void onMyError(VolleyError error)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		onRecover();
		switch (arg0.getId())
		{

		case R.id.txt_home_aim:
			// 我的目标
			// vp_home.setCurrentItem(0);
			initFragment(0);
			onChangerState(0);
			txt_home_aim.setBackgroundColor(getResources().getColor(R.color.color_gray));
			break;
		case R.id.txt_home_aimhall:
			// 目标广场
			// vp_home.setCurrentItem(1);
			initFragment(1);
			onChangerState(1);
			txt_home_aimhall.setBackgroundColor(getResources().getColor(R.color.color_gray));
			break;
		case R.id.txt_home_evaluation:
			// vp_home.setCurrentItem(2);
			initFragment(2);
			onChangerState(2);
			txt_home_evaluation.setBackgroundColor(getResources().getColor(R.color.color_gray));
			// 职业测评
			break;

		default:
			break;
		}
	}

	/**
	 * 恢复按钮样式
	 */
	private void onRecover()
	{

		txt_home_aim.setBackgroundColor(getResources().getColor(R.color.color_transparent));

		txt_home_aimhall.setBackgroundColor(getResources().getColor(R.color.color_transparent));

		txt_home_evaluation.setBackgroundColor(getResources().getColor(R.color.color_transparent));
	}

}
