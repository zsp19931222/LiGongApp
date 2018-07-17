package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import yh.app.activitytool.ActivityPortrait;
import yh.app.utils.DefaultTopBar;import yh.app.appstart.lg.R;

public class zhkt_jsxzt extends ActivityPortrait
{
	private String username, userid, ktbh;
	private Button fs;
	private ListView listView;
	private EditText input;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhkt_jsxzt);
		init();
		initTopbar();
		initView();
		initAction();
		getZT();
	}

	private void getZT()
	{
		// TODO Auto-generated method stub
//		_链接地址导航
	}

	private void initAction()
	{
		fs.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

			}
		});
	}

	private void initView()
	{
		this.listView = (ListView) findViewById(R.id.zhkt_zt_layout);
		this.input = (EditText) findViewById(R.id.quanzi_liaotian_input);
		this.fs = (Button) findViewById(R.id.quanzi_lt_fsbutton);

		listView.setAdapter(new MyAdapter(null));
	}

	private void init()
	{
		this.ktbh = getIntent().getStringExtra("ktbh");
		this.userid = getIntent().getStringExtra("userid");
		this.username = getIntent().getStringExtra("username");
	}

	private void initTopbar()
	{
		new DefaultTopBar(this).doit(this.username + "的纸条");
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			
		};
	};

	class MyAdapter extends BaseAdapter
	{
		private List<View> list = new ArrayList<View>();

		public MyAdapter(List<View> list)
		{
			this.list = list;
		}

		@Override
		public int getCount()
		{
			return list.size();
		}

		@Override
		public Object getItem(int position)
		{
			return list.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return list.get(position).getId();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			return (View) getItem(position);
		}

	}
}
