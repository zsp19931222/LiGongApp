package yh.app.baiduMap;

import java.util.HashMap;
import java.util.Map;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.yhkj.cqgyxy.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import yh.app.function.CampusMap;
import yh.app.tool.ToastShow;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.工具.ActivityHelper;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.PopupWindowTools;

public class NearBus extends ActivityPortrait
{
	private Spinner mSpinner;
	private String[] xq_array;
	private boolean loadDate = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.near_bus_main);
		initDate();
		initView();
		if (IsNull.isNotNull(xq_array))
		{
			loadDate = true;
		} else
		{
			new ToastShow().show(this, "暂无校区信息");
			xq_array = new String[]
			{
					"暂无校区信息"
			};
		}
		setSpinner();
	}

	private void initDate()
	{
		xq_array = new String[CampusMap.xqzb.size()];
		for (int i = 0; i < CampusMap.xqzb.size(); i++)
		{
			xq_array[i] = CampusMap.xqzb.get(i).get("MAP_NAME");
		}
	}

	private void initView()
	{
		final TopBarHelper tbh = new TopBarHelper(this, findViewById(R.id.topbar_layout));
		tbh.setOnClickLisener(new OnClickLisener()
		{

			@Override
			public void setRightOnClick()
			{
				new ActivityHelper().goHomeActivity(NearBus.this);
			}

			@Override
			public void setLeftOnClick()
			{
				finish();
			}

			@Override
			public void setExtraOnclick()
			{

			}
		}).setTitle("公交查询");
		mSpinner = (Spinner) findViewById(R.id.Spinner);
	}

	private void setSpinner()
	{
		mSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.spiner_check_style, xq_array));
		mSpinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				if (loadDate)
				{
					setViewClick(position);
				}
				mSpinner.setSelection(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent)
			{
				// TODO Auto-generated method stub

			}
		});
	}

	private void setViewClick(int i)
	{
		busStateList = new HashMap<String, String>();
		Map<String, String> map = CampusMap.xqzb.get(i);
		new POITools(this).nearly(2000, "公交车站", 0, new LatLng(Double.valueOf(map.get("MAP_LAT")), Double.valueOf(map.get("MAP_LNG"))), new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				if (msg == null)
					return;
				PoiResult pr = (PoiResult) msg.obj;
				((LinearLayout) findViewById(R.id.result_layout)).removeAllViews();
				for (int i = 0; i < pr.getAllPoi().size(); i++)
				{
					try
					{
						getResultView(pr.getAllPoi().get(i));
					} catch (Exception e)
					{
					}
				}
			}
		});

	}

	private void getResultView(PoiInfo info)
	{
		if (!(busStateList.get(info.name) == null))
			return;
		else
			busStateList.put(info.name, "1");
		LinearLayout layout = (LinearLayout) findViewById(R.id.result_layout);
		View view = LayoutInflater.from(this).inflate(R.layout.map_poi_detail, layout, false);
		((TextView) view.findViewById(R.id.name)).setText(info.name);
		((TextView) view.findViewById(R.id.address)).setHint(info.address);
		((TextView) view.findViewById(R.id.type)).setVisibility(View.GONE);
		view.findViewById(R.id.pj).setVisibility(View.GONE);
		view.setTag(info);
		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				PoiInfo pi = (PoiInfo) v.getTag();
				showPopwindow(pi.address.split(";"));
			}
		});
		layout.addView(view);
	}

	public void showPopwindow(String[] busList)
	{
		new PopupWindowTools().showAtLocation(this, getPopView(busList), (LinearLayout) findViewById(R.id.layout), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0, 0, Gravity.CENTER);
	}

	private LinearLayout layout;

	public View getPopView(String[] busList)
	{
		View view = LayoutInflater.from(this).inflate(R.layout.pop_linearlayout_v, (LinearLayout) findViewById(R.id.layout), false);
		layout = (LinearLayout) view.findViewById(R.id.pop_linearlayout_v);
		for (int i = 0; i < busList.length; i++)
		{
			getDetail(busList[i]);
		}
		return view;
	}

	public View getPopViewSub(ViewGroup parent, String busNum, String from, String to)
	{
		View view = LayoutInflater.from(this).inflate(R.layout.map_bus_list_layout_sub, parent, false);
		((TextView) view.findViewById(R.id.name)).setText(busNum);
		((TextView) view.findViewById(R.id.from)).setText(from);
		((TextView) view.findViewById(R.id.to)).setText(to);
		return view;
	}

	Map<String, String> busStateList = new HashMap<String, String>();

	public void getDetail(final String busNum)
	{
		new POITools(this).city(busNum, 0, "重庆", new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				PoiResult pr = (PoiResult) msg.obj;
				for (int i = 0; i < pr.getAllPoi().size(); i++)
				{
					if (pr.getAllPoi().get(i).type == PoiInfo.POITYPE.BUS_LINE || pr.getAllPoi().get(i).type == PoiInfo.POITYPE.SUBWAY_LINE)
						new POITools(NearBus.this).detail(pr.getAllPoi().get(i).uid, new Handler()
						{
							@Override
							public void handleMessage(Message msg)
							{
								try
								{
									PoiDetailResult pdr = (PoiDetailResult) msg.obj;
									String result = pdr.getName();
									String name = result.split("\\(")[0];
									String[] fromAndTo = result.split("\\(")[1].replace(")", "").split("-");
									layout.addView(getPopViewSub(layout, name, fromAndTo[0], fromAndTo[1]));
								} catch (Exception e)
								{
								}
							};
						});
				}
			}
		});
	}

}
