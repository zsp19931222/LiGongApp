package yh.app.contacts;

import yh.app.activitytool.ActivityPortrait;
import android.net.Uri;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

/**
 * 
 * 包 名:yh.app.contacts 类 名:Contact_Call_Activity.java 功 能:
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class Contact_Call_Activity extends ActivityPortrait
{

	private TextView d_name_tv = null;// 部门信息
	private TextView sj_tv = null;// 手机号

	private TextView zj_tv = null;// 刷座机号
	private TextView call_phone_zj = null;// 座机号拨号
	private TextView call_phone_sj = null;// 手机号拨号

	private ImageButton return_pre;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.contact_sub);

		Intent intent = getIntent();
		String department_name = intent.getExtras().getString("department_name");
		String sj = intent.getExtras().getString("sj");
		String zj = intent.getExtras().getString("zj");

		if (sj.equals("null"))
		{
			sj = "-";
		}
		if (zj.equals("null"))
		{
			zj = "-";
		}

		d_name_tv = (TextView) findViewById(R.id.dapartment_name);
		sj_tv = (TextView) findViewById(R.id.contact_sj);
		zj_tv = (TextView) findViewById(R.id.contact_zj);
		call_phone_zj = (TextView) findViewById(R.id.call_phone_zj);
		call_phone_sj = (TextView) findViewById(R.id.call_phone_sj);

		return_pre = (ImageButton) findViewById(R.id.return_pre);// 返回

		d_name_tv.setText(department_name);
		sj_tv.setText(sj);
		zj_tv.setText(zj);

		call_phone_zj.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				call_phone(zj_tv.getText().toString());

			}
		});

		call_phone_sj.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				call_phone(sj_tv.getText().toString());

			}
		});

		return_pre.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				finish();
			}
		});

	}

	public void call_phone(String call_number)
	{

		String number = call_number;
		Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.contact__call_, menu);
	// return true;
	// }

}
