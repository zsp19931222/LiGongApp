package yh.app.wisdomclass;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.utils.DefaultTopBar;import com.yhkj.cqgyxy.R;
import 云华.智慧校园.自定义适配器._无限加载_ListView;

public class zhkt_jsxztxqzs extends ActivityPortrait
{
	private ListView listView;
	private LinearLayout parent;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhkt_js_xzt_xq);
		listView = (ListView) findViewById(R.id.zhkt_zt_layout);
		parent = (LinearLayout) findViewById(R.id.layout);
		
		_无限加载_ListView	wxjz = new _无限加载_ListView(
				this, 
				listView, 
				parent, 
				Constants.name, 
				getIntent().getStringExtra("ktbh"), 
				getIntent().getStringExtra("jsbh"), 
				getIntent().getStringExtra("xsbh"),
				getIntent().getStringExtra("xsxm"));
		wxjz.start();
		new DefaultTopBar(this).doit(getIntent().getStringExtra("xsxm"));
	}
}
