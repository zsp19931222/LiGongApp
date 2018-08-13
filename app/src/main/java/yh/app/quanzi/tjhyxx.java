package yh.app.quanzi;

import com.example.jpushdemo.ExampleApplication;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.ViewClickEffect;import com.yhkj.cqgyxy.R;
import yh.app.utils.DefaultTopBar;

public class tjhyxx extends ActivityPortrait
{
	private TextView xh;
	private TextView xm;
	private TextView xb;
	private TextView sr;
	private TextView bm;
	private TextView qq;
	private TextView em;
	private Context context;
	private Button qr;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quanzi_friend_add);
		context = this;
		initView();
		initAction();
	}

	private void initAction()
	{
		qr.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewClickEffect.doEffect(v, 200, context, "yh.app.quanzi.sfyz", ExampleApplication.getAppPackage(), new String[][]
				{
						{
								"xh", xh.getText().toString()
						},
						{
								"xm", xm.getText().toString()
						}
				});
			}
		});
	}

	private void initView()
	{
		new DefaultTopBar(context).doit("好友信息");

		xh = (TextView) findViewById(R.id.xh);
		xm = (TextView) findViewById(R.id.xm);
		xb = (TextView) findViewById(R.id.xb);
		sr = (TextView) findViewById(R.id.sr);
		bm = (TextView) findViewById(R.id.bm);
		qq = (TextView) findViewById(R.id.qq);
		em = (TextView) findViewById(R.id.em);
		qr = (Button) findViewById(R.id.qr);

		// String xmString = getIntent().getStringExtra("xm");
		// String xbString = getIntent().getStringExtra("xm");
		// String srString = getIntent().getStringExtra("xm");
		// String bmString = getIntent().getStringExtra("xm");
		// String qqString = getIntent().getStringExtra("xm");
		// String emString = getIntent().getStringExtra("xm");

		xh.setText(getString(getIntent().getStringExtra("userid")));
		xm.setText(getString(getIntent().getStringExtra("xm")));
		xb.setText(getString(getIntent().getStringExtra("xb")));
		sr.setText(getString(getIntent().getStringExtra("sr")));
		bm.setText(getString(getIntent().getStringExtra("bm")));
		qq.setText(getString(getIntent().getStringExtra("qq")));
		em.setText(getString(getIntent().getStringExtra("em")));
	}

	private String getString(String s)
	{
		if (s == null || s.equals("") || s.equals("null"))
			return "";
		else
			return s;
	}
}
