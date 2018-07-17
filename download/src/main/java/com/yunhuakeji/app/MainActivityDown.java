package com.yunhuakeji.app;

import java.net.URI;
import java.net.URL;

import com.yunhuakeji.app.utils.Constants;
import com.yunhuakeji.app.utils.FloatViewHelper;
import com.yunhuakeji.app.utils.FloatViewHelper.OnClickListener;
import com.yunhuakeji.app.utils.HomePageHepler1;
import com.yunhuakeji.app.utils.HoverScrollView;
import com.yunhuakeji.app.utils.JsonReaderHelper;
import com.yunhuakeji.app.utils.JsonTools;
import com.yunhuakeji.app.utils.LocalDateFileHelper;
import com.yunhuakeji.app.utils.MapTools;
import com.yunhuakeji.app.utils.PostHelper;
import com.yunhuakeji.app.utils.TicketHelper;
import com.yunhuakeji.app.utils.PostHelper.IPostExecute;
import com.yunhuakeji.app.utils.StepCounterService;
import com.yunhuakeji.app.utils.StepDetector;
import com.yunhuakeji.app.utils.ViewFlipperAdapter;
import com.yunhuakeji.app.utils.HoverScrollView.OnScrollListener;
import com.yunhuakeji.app.utils.URLAssist;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ViewFlipper;

@SuppressLint("UseSparseArrays")
public class MainActivityDown extends YhActivity implements View.OnClickListener
{
	private HoverScrollView mHsv;
	private View mHoverLayoutTop;
	
	private int size;
	private LinearLayout linearlayout_home1_main_layout, linearlayout_home_title_hover_invisability, linearlayout_home_title_hover_visability;
	private View[] linearlayout_home_title_hover_invisability_item = new View[3];
	private View[] linearlayout_home_title_hover_visability_item   = new View[3];

	
	private void initViewData()
	{
		try
		{
			new HomePageHepler1().setPublicView(this, linearlayout_home1_main_layout, JsonReaderHelper.getJosn(this, "home1.json"));

			PostHelper post = new PostHelper("http://192.168.1.101/UIA/function/list.action", MapTools.buildMap(new String[][]
					{
						{ "userid", "11204050220" },
						{ "Version", "1" }
					}));
					post.doPostExecute(new IPostExecute()
					{
						@Override
						public void onPostExecute(Object result)
						{
							TicketHelper.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()), new String[] { "ticket" })[0]);

							PostHelper post = new PostHelper(URLAssist.TEST._自主目标.getUrl(), MapTools.buildMap(new String[][]
							{
									{ "userid", Constants.number },
									{ "ticket", Constants.ticket }
							}));
							post.doPostExecute(new IPostExecute()
							{
								@Override
								public void onPostExecute(Object result)
								{
									try
									{
										TicketHelper.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()), new String[] { "ticket" })[0]);
										new HomePageHepler1().setPrivateView(getContext(), linearlayout_home1_main_layout, result.toString());
									} catch (Exception e)
									{
										e.printStackTrace();
									}
								}
							});
						}
					});			
			
//			PostHelper post = new PostHelper(URLAssist.TEST.获取票据.getUrl(), MapTools.buildMap(new String[][]
//			{
//				{ "userid", "11204050220" }
//			}));
//			post.doPostExecute(new IPostExecute()
//			{
//				@Override
//				public void onPostExecute(Object result)
//				{
//					TicketHelper.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()), new String[] { "ticket" })[0]);
//
//					PostHelper post = new PostHelper(URLAssist.TEST._自主目标.getUrl(), MapTools.buildMap(new String[][]
//					{
//							{ "userid", Constants.number },
//							{ "ticket", Constants.ticket }
//					}));
//					post.doPostExecute(new IPostExecute()
//					{
//						@Override
//						public void onPostExecute(Object result)
//						{
//							try
//							{
//								TicketHelper.setTicket(JsonTools.getString(JsonTools.createJsonObject(result.toString()), new String[] { "ticket" })[0]);
//								new HomePageHepler1().setPrivateView(getContext(), linearlayout_home1_main_layout, result.toString());
//							} catch (Exception e)
//							{
//								e.printStackTrace();
//							}
//						}
//					});
//				}
//			});
		} catch (Exception e)
		{
		}

	}

	@Override
	public void onClick(View v)
	{
		homeViewClickUI(v);
	}

	public void homeViewClickUI(View v)
	{
		for (int i = 0; i < 3; i++)
		{
			if (v.getId() == linearlayout_home_title_hover_visability_item[i].getId() || v.getId() == linearlayout_home_title_hover_invisability_item[i].getId())
			{
				linearlayout_home_title_hover_visability_item[i].setBackgroundColor(this.getResources().getColor(R.color.touming_dark_gray));
				linearlayout_home_title_hover_invisability_item[i].setBackgroundColor(this.getResources().getColor(R.color.touming_dark_gray));
			} else
			{
				linearlayout_home_title_hover_visability_item[i].setBackgroundColor(this.getResources().getColor(R.color.touming));
				linearlayout_home_title_hover_invisability_item[i].setBackgroundColor(this.getResources().getColor(R.color.touming));
			}
		}
	}

	public void initActivity()
	{
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_home_page2);
		Constants.App_Context = this.getApplicationContext();
	}

	private ViewFlipper flipper;

	public void initView()
	{
		// TODO Auto-generated method stub
		mHsv = (HoverScrollView) findViewById(R.id.hsv);
		mHoverLayoutTop = findViewById(R.id.view1);
		flipper = (ViewFlipper) findViewById(R.id.viewFlipper);

		linearlayout_home1_main_layout = (LinearLayout) findViewById(R.id.linearlayout_home1_main_layout);
		linearlayout_home_title_hover_invisability = (LinearLayout) findViewById(R.id.linearlayout_home_title_hover_invisability);
		linearlayout_home_title_hover_visability = (LinearLayout) findViewById(R.id.linearlayout_home_title_hover_visability);

		for (int i = 0; i < 3; i++)
		{
			linearlayout_home_title_hover_visability_item[i] = linearlayout_home_title_hover_visability.getChildAt(i);
			linearlayout_home_title_hover_invisability_item[i] = linearlayout_home_title_hover_invisability.getChildAt(i);

			linearlayout_home_title_hover_visability_item[i].setOnClickListener(this);
			linearlayout_home_title_hover_visability_item[i].setId(i);

			linearlayout_home_title_hover_invisability_item[i].setOnClickListener(this);
			linearlayout_home_title_hover_invisability_item[i].setId(i + 10);
		}

		homeViewClickUI(linearlayout_home_title_hover_visability_item[0]);
	
	}
	
	public Context getContext()
	{
		return this;
	}

	public void initData()
	{
		// TODO Auto-generated method stub
		new ViewFlipperAdapter(flipper, MainActivityDown.this, new int[]
		{
				R.drawable.xxhome1, R.drawable.xxhome2
		}).doit();
		mHoverLayoutTop.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout()
			{
				LayoutParams lp = findViewById(R.id.viewFlipper).getLayoutParams();
				lp.height = MainActivityDown.this.getWindowManager().getDefaultDisplay().getWidth() / 2;
				lp.width = LayoutParams.MATCH_PARENT;
				findViewById(R.id.viewFlipper).setLayoutParams(lp);
				size = findViewById(R.id.viewFlipper).getHeight();
				mHsv.setOnScrollListener(new OnScrollListener()
				{
					@Override
					public void onScroll(int scrollY)
					{
						int height = size - findViewById(R.id.home_topbar).getHeight() - findViewById(R.id.linearlayout_home_title_hover_invisability).getHeight();
						if (scrollY >= height)
						{
							mHoverLayoutTop.findViewById(R.id.linearlayout_home_title_hover_invisability).setVisibility(View.VISIBLE);
						} else
						{
							mHoverLayoutTop.findViewById(R.id.linearlayout_home_title_hover_invisability).setVisibility(View.INVISIBLE);
						}
						if (scrollY >= height)
						{
							findViewById(R.id.home_topbar).setAlpha(1);
						} else
						{
							findViewById(R.id.home_topbar).setAlpha((float) scrollY / height);
						}
					}
				});
			}
		});
		initViewData();

		StepDetector.CURRENT_SETP = Integer.valueOf(new LocalDateFileHelper(this, HealthActivity.FILE_NAME).getLocalString("0"));

		startService(new Intent(this.getApplicationContext(), StepCounterService.class));
	}

	public void initAction()
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class ContentFragment extends Fragment
	{

		private ListView demosListView;

		public ContentFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_demo, container, false);

			String[] items =
			{
					"Menu with FloatingActionButton", "Menu attached to custom button", "Menu with custom animation", "Menu in ScrollView", "Menu as system overlay", "Test float view activitu"
			};
			ArrayAdapter<String> simpleAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
			demosListView = (ListView) rootView.findViewById(R.id.demosListView);
			demosListView.setAdapter(simpleAdapter);
			return rootView;
		}
	}
}