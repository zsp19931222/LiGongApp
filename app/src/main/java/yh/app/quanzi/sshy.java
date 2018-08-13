package yh.app.quanzi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.jpushdemo.ExampleApplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.SSHYAT;import com.yhkj.cqgyxy.R;

public class sshy extends ActivityPortrait
{
	private LinearLayout layout;
	private Button sshy;
	private EditText ssText;
	private Button syy;
	private Button xyy;
	private int nowpage = 1;
	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == 0)
			{
				xyy.setVisibility(8);
				nowpage--;
				return;
			}
			setSslb(msg);
		}
	};

	private void setSslb(Message msg)
	{
		layout.removeAllViews();
		String b = msg.getData().getString("sshylb");
		try
		{
			JSONArray jsa = new JSONArray(b);
			for (int i = 0; i < jsa.length(); i++)
			{
				JSONObject jso = jsa.getJSONObject(i);
				addLayoutInflater(jso.getString("USER_ID"), jso.getString("USER_NAME"));
			}
			if (jsa.length() > 0)
			{
				if (nowpage > 1)
				{
					syy.setVisibility(0);
					xyy.setVisibility(0);
				} else if (nowpage == 1)
				{
					syy.setVisibility(8);
					xyy.setVisibility(0);
				}
			}
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sshy);
		initView();
		initAction();
	}

	private void initAction()
	{
		sshy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (ssText != null || !ssText.getText().toString().equals(""))
				{
					nowpage = 1;
					gethhlb(1);
				}
			}
		});
		syy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				nowpage--;
				gethhlb(nowpage);
			}
		});
		xyy.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				nowpage++;
				gethhlb(nowpage);
			}
		});
	}

	private void initView()
	{
		layout = (LinearLayout) findViewById(R.id.sshy_list_layout);
		sshy = (Button) findViewById(R.id.sshy_ss_button);
		ssText = (EditText) findViewById(R.id.sshy_ss_text);
		syy = (Button) findViewById(R.id.sshy_syy);
		xyy = (Button) findViewById(R.id.sshy_xyy);
	}

	private void addLayoutInflater(final String xh, String name)
	{
		LayoutInflater inFlater = LayoutInflater.from(this);
		View mView = inFlater.inflate(R.layout.sshy_sub, null);
		mView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (xh.equals(Constants.number))
				{
					Toast.makeText(getApplicationContext(), "不能添加自己", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent();
				intent.setAction("yh.app.quanzi.tjhyxx");
				intent.setPackage(ExampleApplication.getAppPackage());
				intent.putExtra("xh", xh);
				startActivity(intent);
			}
		});
		TextView t1 = (TextView) mView.findViewById(R.id.sshy_nc);
		TextView t2 = (TextView) mView.findViewById(R.id.sshy_name);
		t1.setText(xh);
		t2.setText(name);
		layout.addView(mView);
	}

	private void gethhlb(int nowpage)
	{
		SSHYAT at = new SSHYAT(handler);
		at.execute(ssText.getText().toString(), String.valueOf(nowpage));
	}

	public void close(View v)
	{
		finish();
	}
}
