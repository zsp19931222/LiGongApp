package yh.app.baiduMap;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.yhkj.cqgyxy.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import yh.app.function.CampusMap;
import yh.app.tool.ToastShow;
import yh.app.utils.DefaultTopBar;
import 云华.智慧校园.工具.DecimalTools;

@SuppressWarnings("unused")
public class UI_xianlu extends ActivityPortrait implements View.OnClickListener
{
	public static ReadWriteLock o = new ReentrantReadWriteLock();
	private EditText my_local, other_local;
	private LinearLayout result_layout;
	private Button start;
	private LatLng current_local;
	private int resultCode = CampusMap.XIANLU_BUS;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_xianlu);
		initView();
		initAction();
	}

	private void initAction()
	{
		// TODO Auto-generated method stub
		my_local.addTextChangedListener(new MyTextWatcher(my_local));
		other_local.addTextChangedListener(new MyTextWatcher(other_local));
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (my_local.getTag() == null)
				{
					new ToastShow().show(UI_xianlu.this, "请选择起始地");
				} else if (other_local.getTag() == null)
				{
					new ToastShow().show(UI_xianlu.this, "请选择起始地");
				} else
				{
					Intent data = new Intent();
					data.putExtra("lat1", ((LatLng) my_local.getTag()).latitude);
					data.putExtra("lng1", ((LatLng) my_local.getTag()).longitude);
					data.putExtra("lat2", ((LatLng) other_local.getTag()).latitude);
					data.putExtra("lng2", ((LatLng) other_local.getTag()).longitude);
					setResult(resultCode, data);
					finish();
				}
			}
		});
	}

	class MyTextWatcher implements TextWatcher
	{
		private long time;
		private Timer timer;
		private EditText dt;

		public MyTextWatcher(EditText dt)
		{
			// TODO Auto-generated constructor stub
			this.dt = dt;
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after)
		{
			// 改变前
		}

		@Override
		public void onTextChanged(final CharSequence s, int start, int before, int count)
		{
			// 改变后
			try
			{
				timer.cancel();
			} catch (Exception e)
			{
			}
			timer = new Timer();
			timer.schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					Message msg = new Message();
					msg.what = 123123;
					msg.obj = "" + s.toString();
					handler.sendMessage(msg);
				}
			}, 0);
		}

		@Override
		public void afterTextChanged(Editable s)
		{
			// 改变后的内容
		}

		Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case CampusMap.XIANLU:
					PoiResult trr = (PoiResult) msg.obj;
					trr.getAllPoi();
					for (int i = 0; i < trr.getAllPoi().size(); i++)
					{
						new POITools(UI_xianlu.this).detail(trr.getAllPoi().get(i).uid, handler);
					}
					break;
				case 123123:
					result_layout.removeAllViews();
					try
					{

						// new POITools(UI_xianlu.this).nearly(2500,
						// msg.obj.toString(), 8, ll, handler);
						new POITools(UI_xianlu.this).city(msg.obj.toString(), 0, getResources().getString(R.string.str_meadder), handler);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
					break;
				case CampusMap.XIANLU_DETAIL:
					// PoiDetailResult pdr = (PoiDetailResult) msg.obj;
					// pdr.getFavoriteNum();
					// pdr.getDetailUrl();
					addView((PoiDetailResult) msg.obj);
					break;
				default:
					break;
				}
				// if (msg.what == CampusMap.XIANLU)
				// {
				//
				// } else if (msg.what == 123123)
				// {
				//
				// // new RoutePlanTools().setListener().bus(handler);
				// }
			};
		};

		public void addView(final PoiDetailResult pdr)
		{
			View view = LayoutInflater.from(UI_xianlu.this).inflate(R.layout.map_poi_detail, result_layout, false);
			((TextView) view.findViewById(R.id.name)).setText(pdr.getName());
			((TextView) view.findViewById(R.id.type)).setHint(pdr.getType());
			((TextView) view.findViewById(R.id.address)).setHint(pdr.getAddress());
			((TextView) view.findViewById(R.id.evaluation_number)).setHint(pdr.getCommentNum() + "人评价");
			((TextView) view.findViewById(R.id.rate_of_praise)).setHint("好评" + DecimalTools.getPercentage(pdr.getOverallRating() / 5, 1));
			view.setTag(pdr);
			view.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					// Intent intent = new Intent();
					// intent.setAction("yh.app.web.Web");
					// intent.setPackage(UI_xianlu.this.getPackageName());
					// intent.putExtra("url", ((PoiDetailResult)
					// v.getTag()).getDetailUrl());
					// startActivity(intent);
					dt.setTag(((PoiDetailResult) v.getTag()).getLocation());
					dt.setText(pdr.getName());
					result_layout.removeAllViews();
				}
			});
			result_layout.addView(view);
		}
	}

	public void fd()
	{
		// TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
		// mBaiduMap.setOnMarkerClickListener(overlay);
		// result.getRouteLines();
		// overlay.setData(result.getRouteLines().get(0));
		// overlay.addToMap();
		// overlay.zoomToSpan();
		// List<TransitStep> step = result.getRouteLines().get(0).getAllStep();
		// for (int i = 0; i < step.size(); i++)
		// {
		// TextView tv = new TextView(context);
		// tv.setText((i + 1) + "、" + step.get(i).getInstructions());
		// ((LinearLayout) ((Activity)
		// context).findViewById(R.id.xlxq_layout)).addView(tv);
		// }
	}

	private void initView()
	{
		new DefaultTopBar(this).doit("线路");
		current_local = new LatLng(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("lng", 0));
		my_local = (EditText) findViewById(R.id.qsd);
		my_local.setTag(current_local);
		other_local = (EditText) findViewById(R.id.jsd);
		result_layout = (LinearLayout) findViewById(R.id.result_layout);
		start = (Button) findViewById(R.id.start);
		findViewById(R.id.bus).setOnClickListener(this);
		findViewById(R.id.walk).setOnClickListener(this);
		findViewById(R.id.driving).setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		reset();
		v.setBackgroundResource(R.drawable.biankuang_white);
		switch (v.getId())
		{
		case R.id.bus:
			resultCode = CampusMap.XIANLU_BUS;
			break;
		case R.id.walk:
			resultCode = CampusMap.XIANLU_WALK;
			break;
		case R.id.driving:
			resultCode = CampusMap.XIANLU_DRIVER;
			break;

		default:
			break;
		}
	}

	public void reset()
	{
		findViewById(R.id.bus).setBackgroundResource(R.drawable.touming);
		findViewById(R.id.walk).setBackgroundResource(R.drawable.touming);
		findViewById(R.id.driving).setBackgroundResource(R.drawable.touming);
	}
}
