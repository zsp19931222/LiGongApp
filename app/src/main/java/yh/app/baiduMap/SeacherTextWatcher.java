package yh.app.baiduMap;

import java.util.Timer;
import java.util.TimerTask;

import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.yhkj.cqgyxy.R;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.function.CampusMap;
import 云华.智慧校园.工具.DecimalTools;

public class SeacherTextWatcher implements TextWatcher
{
	private Timer timer;
	private EditText dt;
	private Context context;
	private LinearLayout result_layout;

	public SeacherTextWatcher(Context context, EditText dt, LinearLayout result_layout)
	{
		this.result_layout = result_layout;
		this.context = context;
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
		if (isFresh)
		{
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
		} else
			isFresh = true;
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
					new POITools(context).detail(trr.getAllPoi().get(i).uid, handler);
				}
				break;
			case 123123:
				result_layout.removeAllViews();
				try
				{
					new POITools(context).city(msg.obj.toString(), 0, context.getResources().getString(R.string.str_meadder), handler);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
				break;
			case CampusMap.XIANLU_DETAIL:
				addView((PoiDetailResult) msg.obj);
				break;
			default:
				break;
			}
		};
	};
	private boolean isFresh = true;

	public void addView(final PoiDetailResult pdr)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.map_poi_detail, result_layout, false);
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
				isFresh = false;
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
