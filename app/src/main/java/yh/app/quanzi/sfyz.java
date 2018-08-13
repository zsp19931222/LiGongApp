package yh.app.quanzi;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.Ticket;import com.yhkj.cqgyxy.R;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.NetResultHelper;
import 云华.智慧校园.工具._链接地址导航;

public class sfyz extends ActivityPortrait
{
	private TextView name;
	private TextView xh;
	private EditText message;
	private TextView fs;
	private ImageView back;
	private Context context;
	private TextView zs;
	private LoadDiaog diaog;
	private String str_xh;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quanzi_qrtjhy_message);
		context = this;
		diaog=new LoadDiaog(context);
		initView();
		initAction();
	}

	private void initAction()
	{
		// TODO Auto-generated method stub
		fs.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
//				AT at = new AT();
//				at.execute();
				diaog.setTitle("发起请求中...");
				diaog.show();
				String ticket=Ticket.getFunctionTicket("20150120");
				Map<String, String> map=new HashMap<>();
				map.put("userid", Constants.number);
				map.put("ticket",ticket);
				map.put("function_id", "20150120");
				map.put("hybh", str_xh);//好友编号
				map.put("fjxx", message.getText().toString());//验证消息
				VolleyRequest.RequestPost(_链接地址导航.DC.添加好友.getUrl(), map, new VolleyInterface() {

					@Override
					public void onMySuccess(String result) {
						System.out.println(result);
						try
						{
							if (result == null || result.equals("") || result.equals("null") || result.equals("false") || !new JSONObject(NetResultHelper.dealHJJResult(result)).getBoolean("boolean"))
							{
								Toast.makeText(getApplicationContext(), "发送失败,请重试", Toast.LENGTH_SHORT).show();
								return;
							}
						} catch (Exception e)
						{
							Toast.makeText(getApplicationContext(), "发送失败,请重试", Toast.LENGTH_SHORT).show();
							return;
						}
						if (diaog.isShowing()) {
							diaog.dismiss();
						}
						Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
						((Activity) context).finish();

					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		back.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				((Activity) context).finish();
			}
		});
		message.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				zs.setHint("最大输入100字(" + message.getText().toString().length() + "/100)");
			}
		});
	}

	private void initView()
	{
		name = (TextView) findViewById(R.id.quanzi_qrtjhy_sfyz_xm);
		xh = (TextView) findViewById(R.id.quanzi_qrtjhy_sfyz_xh);
		message = (EditText) findViewById(R.id.quanzi_qrtjhy_sfyz_message);
		fs = (TextView) findViewById(R.id.quanzi_qrtjhy_sfyz_fs);
		zs = (TextView) findViewById(R.id.quanzi_qrtjhy_sfyz_zs);
		back = (ImageView) findViewById(R.id.quanzi_qrtjhy_sfyz_back);

		Intent intent = getIntent();
		xh.setText(intent.getStringExtra("xh"));
		name.setText(intent.getStringExtra("xm"));
		str_xh=xh.getText().toString();
	}

	class AT extends AsyncTask<String, String, String>
	{
		
		@Override
		protected void onPreExecute()
		{
			diaog.setTitle("发起请求中...");
			diaog.show();
		}

		@Override
		protected String doInBackground(String... params)
		{
			String ticket=Ticket.getFunctionTicket("20150120");
			final String results = null;	
			Map<String, String> map=new HashMap<>();
			map.put("userid", Constants.number);
			map.put("ticket",ticket);
			map.put("function_id", "20150120");
			map.put("hybh", str_xh);//好友编号
			map.put("fjxx", message.getText().toString());//验证消息
			VolleyRequest.RequestPost(_链接地址导航.DC.添加好友.getUrl(), map, new VolleyInterface() {
				
				@Override
				public void onMySuccess(String result) {
					System.out.println(result);
					try
					{
						if (result == null || result.equals("") || result.equals("null") || result.equals("false") || !new JSONObject(NetResultHelper.dealHJJResult(result)).getBoolean("boolean"))
						{
							Toast.makeText(getApplicationContext(), "发送失败,请重试", Toast.LENGTH_SHORT).show();
							return;
						}
					} catch (Exception e)
					{
						Toast.makeText(getApplicationContext(), "发送失败,请重试", Toast.LENGTH_SHORT).show();
						return;
					}
					if (diaog.isShowing()) {
						diaog.dismiss();
					}
					Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
					((Activity) context).finish();
					
				}
				
				@Override
				public void onMyError(VolleyError error) {
					// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "发送失败", Toast.LENGTH_SHORT).show();
				}
			});
			
			
			return results;
		}

		@Override
		protected void onPostExecute(String result)
		{
			
			

//			if (new SqliteHelper().rawQuery("select count(*) as num from client_notice_newest where userid=? and function_id = ? and n_send_time > ?", Constants.number, body.getFqr(), body.getFssj()).get(0).get("num").equals("0"))
//			{
//				new SqliteHelper().execSQL("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
//				{
//						body.getId(), Constants.number, BodyAdd.DEAL_NOREAD, "好友消息", body.getFjnr(), BodyAdd.DEAL_FUNCTION_ID, "", body.getFssj(), body.getCode(), body.getTicket()
//				});
//			}
//			new SqliteHelper().execSQL("replace into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
//			{
//					body.getId(), Constants.number, body.getCode(), body.getFqr(), body.getFqrname(), Constants.number, "", body.getFjnr(), body.getFssj(), "", BodyAdd.DEAL_NO, BodyAdd.DEAL_NOREAD
//			});
//			Toast.makeText(getApplicationContext(), "发送成功", Toast.LENGTH_SHORT).show();
//			((Activity) context).finish();
		}
	}
}
