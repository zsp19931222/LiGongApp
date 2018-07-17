package yh.app.uiengine;

import com.yunhuakeji.app.HealthActivity;
import com.yunhuakeji.app.utils.Constants;
import com.yunhuakeji.app.utils.HomePageHepler1;
import com.yunhuakeji.app.utils.HoverScrollView;
import com.yunhuakeji.app.utils.HoverScrollView.OnScrollListener;
import com.yunhuakeji.app.utils.JsonReaderHelper;
import com.yunhuakeji.app.utils.JsonTools;
import com.yunhuakeji.app.utils.LocalDateFileHelper;
import com.yunhuakeji.app.utils.MapTools;
import com.yunhuakeji.app.utils.PostHelper;
import com.yunhuakeji.app.utils.PostHelper.IPostExecute;
import com.yunhuakeji.app.utils.StepCounterService;
import com.yunhuakeji.app.utils.StepDetector;
import com.yunhuakeji.app.utils.TicketHelper;
import com.yunhuakeji.app.utils.URLAssist;
import com.yunhuakeji.app.utils.ViewFlipperAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;
import yh.app.utils.AnimationUtil;import yh.app.appstart.lg.R;
import yh.tool.widget.FloatingDraftButton;

@SuppressLint("UseSparseArrays")
public class MainActivity implements View.OnClickListener {
	private HoverScrollView mHsv;
	private View mHoverLayoutTop;
	private ViewFlipper flipper;
	private int size;
	private LinearLayout linearlayout_home1_main_layout, linearlayout_home_title_hover_invisability,
			linearlayout_home_title_hover_visability;
	private View[] linearlayout_home_title_hover_invisability_item = new View[3];
	private View[] linearlayout_home_title_hover_visability_item = new View[3];

	private Context context;

	private View mainView;
	private ViewGroup parent;
	private Button btn_chuangjianmubiao, btn_mubiaoku;
	private FloatingDraftButton floatbutton;

	public MainActivity(Context context, ViewGroup parent) {
		this.context = context;
		this.parent = parent;
		initActivity();
		initView();
		initData();
		initAction();
	}

	public View getMainView() {
		return mainView;
	}

	private void initViewData() {
		try {
			new HomePageHepler1().setPublicView(context, linearlayout_home1_main_layout,
					JsonReaderHelper.getJosn(context, "home1.json"));
			
			PostHelper post = new PostHelper(URLAssist.TEST.获取票据.getUrl(),
					MapTools.buildMap(new String[][] { { "userid", "11204050220" } }));
			
			post.doPostExecute(new IPostExecute() {
				@Override
				public void onPostExecute(Object result) {
					TicketHelper.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()),
							new String[] { "ticket" })[0]);

					PostHelper post = new PostHelper(URLAssist.TEST._自主目标.getUrl(), MapTools.buildMap(
							new String[][] { { "userid", Constants.number }, { "ticket", Constants.ticket } }));
					post.doPostExecute(new IPostExecute() {
						@Override
						public void onPostExecute(Object result) {
							try {
								TicketHelper
										.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()),
												new String[] { "ticket" })[0]);
								new HomePageHepler1().setPrivateView(getContext(), linearlayout_home1_main_layout,
										result.toString());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
		} catch (Exception e) {
		}

	}

	@Override
	public void onClick(View v) {
		homeViewClickUI(v);
	}

	public void homeViewClickUI(View v) {
		for (int i = 0; i < 3; i++) {
			if (v.getId() == linearlayout_home_title_hover_visability_item[i].getId()
					|| v.getId() == linearlayout_home_title_hover_invisability_item[i].getId()) {
				linearlayout_home_title_hover_visability_item[i]
						.setBackgroundColor(context.getResources().getColor(R.color.touming_dark_gray));
				linearlayout_home_title_hover_invisability_item[i]
						.setBackgroundColor(context.getResources().getColor(R.color.touming_dark_gray));
			} else {
				linearlayout_home_title_hover_visability_item[i]
						.setBackgroundColor(context.getResources().getColor(R.color.touming));
				linearlayout_home_title_hover_invisability_item[i]
						.setBackgroundColor(context.getResources().getColor(R.color.touming));
			}
		}
	}

	public void initActivity() {
		// TODO Auto-generated method stub
		mainView = LayoutInflater.from(context).inflate(R.layout.activity_home_page2, parent, false);
		Constants.App_Context = context.getApplicationContext();
	}

	public void initView() {
		// TODO Auto-generated method stub
		mHsv = (HoverScrollView) mainView.findViewById(R.id.hsv);
		mHoverLayoutTop = mainView.findViewById(R.id.view1);
		flipper = (ViewFlipper) mainView.findViewById(R.id.viewFlipper);

		// 悬浮按钮
		floatbutton = (FloatingDraftButton) mainView.findViewById(R.id.btn_menls);
		btn_chuangjianmubiao = (Button) mainView.findViewById(R.id.btn_chuanjianmubiao);
		btn_mubiaoku = (Button) mainView.findViewById(R.id.btn_mubiaoku);
		floatbutton.registerButton(btn_chuangjianmubiao);
		floatbutton.registerButton(btn_mubiaoku);
		floatbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				// 弹出动态Button
				AnimationUtil.slideButtons(context, floatbutton);
				handler.sendEmptyMessageDelayed(0, 5 * 1000);

			}
		});
		
		btn_chuangjianmubiao.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
				Intent intent=new Intent(context,ZhuYeActivity.class);
				context.startActivity(intent);

			}
		});
		/**
		 * 我要毕业 创建目标展示
		 */
		linearlayout_home1_main_layout = (LinearLayout) mainView.findViewById(R.id.linearlayout_home1_main_layout);
		// 目标功能按钮1
		linearlayout_home_title_hover_invisability = (LinearLayout) mainView
				.findViewById(R.id.linearlayout_home_title_hover_invisability);
		// 目标功能按钮1
		linearlayout_home_title_hover_visability = (LinearLayout) mainView
				.findViewById(R.id.linearlayout_home_title_hover_visability);

		for (int i = 0; i < 3; i++) {
			linearlayout_home_title_hover_visability_item[i] = linearlayout_home_title_hover_visability.getChildAt(i);
			linearlayout_home_title_hover_invisability_item[i] = linearlayout_home_title_hover_invisability
					.getChildAt(i);

			linearlayout_home_title_hover_visability_item[i].setOnClickListener(this);
			linearlayout_home_title_hover_visability_item[i].setId(i);

			linearlayout_home_title_hover_invisability_item[i].setOnClickListener(this);
			linearlayout_home_title_hover_invisability_item[i].setId(i + 10);
		}

		homeViewClickUI(linearlayout_home_title_hover_visability_item[0]);

	}

	public Context getContext() {
		return context;
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressLint("NewApi")
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				AnimationUtil.slideButtons(context, floatbutton);
				floatbutton.setAlpha(0.1f);
				break;
			case 1:

				break;

			}
		}
	};

	

	

	public void initData() {
		// TODO Auto-generated method stub
		new ViewFlipperAdapter(flipper, context, new int[] { R.drawable.xxhome1, R.drawable.xxhome2 }).doit();
		mHoverLayoutTop.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				LayoutParams lp = mainView.findViewById(R.id.viewFlipper).getLayoutParams();
				lp.height = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth() / 2;
				lp.width = LayoutParams.MATCH_PARENT;
				mainView.findViewById(R.id.viewFlipper).setLayoutParams(lp);
				size = mainView.findViewById(R.id.viewFlipper).getHeight();
				mHsv.setOnScrollListener(new OnScrollListener() {
					@Override
					public void onScroll(int scrollY) {
						int height = size - mainView.findViewById(R.id.home_topbar).getHeight()
								- mainView.findViewById(R.id.linearlayout_home_title_hover_invisability).getHeight();
						if (scrollY >= height) {
							mHoverLayoutTop.findViewById(R.id.linearlayout_home_title_hover_invisability)
									.setVisibility(View.VISIBLE);
						} else {
							mHoverLayoutTop.findViewById(R.id.linearlayout_home_title_hover_invisability)
									.setVisibility(View.INVISIBLE);
						}
						if (scrollY >= height) {
							mainView.findViewById(R.id.home_topbar).setAlpha(1);
						} else {
							mainView.findViewById(R.id.home_topbar).setAlpha((float) scrollY / height);
						}
					}
				});
			}
		});
		initViewData();

		StepDetector.CURRENT_SETP = Integer
				.valueOf(new LocalDateFileHelper(context, HealthActivity.FILE_NAME).getLocalString("0"));

		context.startService(new Intent(context.getApplicationContext(), StepCounterService.class));
	}

	public void initAction() {
		// TODO Auto-generated method stub
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ContentFragment extends Fragment {

		private ListView demosListView;

		public ContentFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_demo, container, false);
			String[] items = { "Menu with FloatingActionButton", "Menu attached to custom button",
					"Menu with custom animation", "Menu in ScrollView", "Menu as system overlay",
					"Test float view activitu" };
			ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, items);
			demosListView = (ListView) rootView.findViewById(R.id.demosListView);
			demosListView.setAdapter(simpleAdapter);
			return rootView;
		}
	}
}