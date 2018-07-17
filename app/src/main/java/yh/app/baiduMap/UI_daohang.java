package yh.app.baiduMap;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.baidu.mapapi.model.LatLng;
import com.baidu.navi.sdkdemo.BNDemoMainActivity;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import yh.app.appstart.lg.R;

import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.ToastShow;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;

public class UI_daohang extends ActivityPortrait
{
	private EditText qsd, jsd;
	private Button start;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_daohang);
		initView();
		init();
	}

	private void init()
	{
		qsd.setTag(new LatLng(getIntent().getDoubleExtra("lat", 0), getIntent().getDoubleExtra("lng", 0)));
	}

	private void initView()
	{
		TopBarHelper tbh = new TopBarHelper(this, findViewById(R.id.topbar_layout));
		tbh.setOnClickLisener(new OnClickLisener()
		{

			@Override
			public void setRightOnClick()
			{
//				Intent intent = new Intent();
//				intent.setAction("com.example.app3.HomePageActivity");
//				intent.setPackage(getPackageName());
//				startActivity(intent);
				finish();
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
		}).setTitle("导航");
		qsd = (EditText) findViewById(R.id.qsd);
		jsd = (EditText) findViewById(R.id.jsd);
		qsd.addTextChangedListener(new SeacherTextWatcher(this, qsd, (LinearLayout) findViewById(R.id.result_layout)));
		jsd.addTextChangedListener(new SeacherTextWatcher(this, jsd, (LinearLayout) findViewById(R.id.result_layout)));
		start = (Button) findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (qsd.getTag() == null)
					new ToastShow().show(UI_daohang.this, "请输入出发地");
				else if (jsd.getTag() == null)
					new ToastShow().show(UI_daohang.this, "请输入目的地");
				else
				{
					new BNDemoMainActivity(UI_daohang.this, new BNRoutePlanNode(((LatLng) qsd.getTag()).longitude, ((LatLng) qsd.getTag()).latitude, getIntent().getStringExtra("name1"), null, CoordinateType.BD09LL), new BNRoutePlanNode(((LatLng) jsd.getTag()).longitude, ((LatLng) jsd.getTag()).latitude, getIntent().getStringExtra("name1"), null, CoordinateType.BD09LL)).doit();
				}
			}
		});
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		finish();
	}
}