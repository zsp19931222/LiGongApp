package yh.app.quanzi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyAdd;
import com.yunhuakeji.app.utils.MapTools;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.quanzitool.pinyin;import yh.app.appstart.lg.R;
import yh.app.tool.ImageAtNotSave;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.NetResultHelper;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具._链接地址导航;

public class NewFriend extends ActivityPortrait implements View.OnClickListener
{
	private LinearLayout layout;

	private static final String LOADING = "12315";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_friend);
		new DefaultTopBar(this).doit("新的好友");
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new MyAdapter(new SqliteHelper().rawQuery("select fqr,fqrname,faceaddress,deal,fjnr from addfriend where userid=? order by fssj desc", Constants.number)));
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setAction(yh.app.contacts.UserDetail.class.getName());
				intent.putExtra("hybh", view.getTag().toString());
				intent.putExtra("fjnr", ((TextView) view.findViewById(R.id.fjnr)).getText().toString());
				intent.setPackage(NewFriend.this.getPackageName());
				NewFriend.this.startActivity(intent);
			}
		});
		new SqliteHelper().execSQL("update addFriend set isread='true' where userid=?", Constants.number);
	}

	public class MyAdapter extends BaseAdapter
	{
		private List<Map<String, String>> list;

		Map<Integer, View> viewList = new HashMap<>();

		public MyAdapter(List<Map<String, String>> list)
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
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			convertView = viewList.get(position);
			if (convertView == null)
			{
				convertView = getMyView(position, parent);
			}
			return convertView;
		}

		public View getMyView(int position, ViewGroup parent)
		{
			View convertView = ViewTools.getView(NewFriend.this, R.layout.new_friend_item, layout);
			((TextView) convertView.findViewById(R.id.name)).setText(list.get(position).get("fqrname"));
			LinearLayout agree = (LinearLayout) convertView.findViewById(R.id.yh_linearlayout_agree_txt);
			((TextView) convertView.findViewById(R.id.fjnr)).setText(list.get(position).get("fjnr"));
			setAgreeTextView(list.get(position).get("deal"), agree);
			// if (list.get(position).get("deal").equals(BodyAdd.DEAL_AGREE))
			// {
			// agree.setHint(NewFriend.this.getResources().getString(R.string.str_added));
			// agree.setBackgroundResource(R.drawable.biankuang4);
			// } else if (list.get(position).get("deal").equals(BodyAdd.DEAL_DISAGREE))
			// {
			// agree.setHint(NewFriend.this.getResources().getString(R.string.str_disagreed));
			// agree.setBackgroundResource(R.drawable.biankuang4);
			// } else if (list.get(position).get("deal").equals("nodeal"))
			// {
			// agree.setBackgroundResource(R.drawable.button_click);
			// agree.setText(NewFriend.this.getResources().getString(R.string.str_agreed));
			// agree.setTextColor(NewFriend.this.getResources().getColor(android.R.color.white));
			// agree.setOnClickListener(NewFriend.this);
			// }
			agree.setTag(list.get(position).get("fqr"));
			convertView.setTag(list.get(position).get("fqr"));
			new ImageAtNotSave(NewFriend.this, (ImageView) convertView.findViewById(R.id.face), list.get(position).get("faceaddress")).execute();
			viewList.put(position, convertView);
			return convertView;
		}
	}

	public void setAgreeTextView(String mode, LinearLayout agreeView)
	{
		if (mode.equals(BodyAdd.DEAL_AGREE))
		{
			((TextView) agreeView.getChildAt(0)).setText(null);
			((TextView) agreeView.getChildAt(0)).setHint(NewFriend.this.getResources().getString(R.string.str_added));
			agreeView.setBackgroundResource(R.drawable.biankuang4);
			agreeView.getChildAt(0).setVisibility(View.VISIBLE);
			agreeView.getChildAt(1).setVisibility(View.GONE);
		} else if (mode.equals(BodyAdd.DEAL_DISAGREE))
		{
			((TextView) agreeView.getChildAt(0)).setHint(NewFriend.this.getResources().getString(R.string.str_disagreed));
			agreeView.setBackgroundResource(R.drawable.biankuang4);
			agreeView.getChildAt(0).setVisibility(View.VISIBLE);
			agreeView.getChildAt(1).setVisibility(View.GONE);
		} else if (mode.equals("nodeal"))
		{
			agreeView.setBackgroundResource(R.drawable.button_click);
			((TextView) agreeView.getChildAt(0)).setText(NewFriend.this.getResources().getString(R.string.str_agreed));
			((TextView) agreeView.getChildAt(0)).setTextColor(NewFriend.this.getResources().getColor(android.R.color.white));
			agreeView.setOnClickListener(NewFriend.this);
			agreeView.getChildAt(1).setVisibility(View.GONE);
			agreeView.getChildAt(0).setVisibility(View.VISIBLE);
		} else if (LOADING.equals(mode))
		{
			agreeView.setBackgroundResource(R.drawable.button_click);
			agreeView.getChildAt(0).setVisibility(View.GONE);
			agreeView.setOnClickListener(NewFriend.this);
			agreeView.getChildAt(1).setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onClick(final View v)
	{
		final String hybh = v.getTag().toString();

		List<Map<String, String>> dataResult = new SqliteHelper().rawQuery("select fqrname from addFriend where fqr = ? and userid = ?", hybh, Constants.number);

		final String fqrname = dataResult == null || dataResult.size() == 0 ? hybh : dataResult.get(0).get("fqrname");

		setAgreeTextView(LOADING, (LinearLayout) v);
		
		Map<String, String> param = MapTools.buildMap(new String[][]
		{
				{
						"userid", Constants.number
				},
				{
						"function_id", "20150120"
				},
				{
						"ticket", Ticket.getFunctionTicket("20150120")
				},
				{
						"hybh", hybh
				},
				{
						"hyzt", BodyAdd.DEAL_SFTY_YES
				}
		});
		VolleyRequest.RequestPost(_链接地址导航.DC.是否同意添加好友.getUrl(), param, new VolleyInterface()
		{
			@Override
			public void onMySuccess(String result)
			{
				try
				{
					if (IsNull.isNotNull(result))
					{
						result = NetResultHelper.dealHJJResult(result);
						JSONObject jso = new JSONObject(result);
						if (jso.getBoolean("boolean"))
						{
							ExampleApplication.getInstance().getSqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)", new Object[]
							{
									hybh, fqrname, IsNull.isNotNull(fqrname) ? pinyin.getAllLetter(fqrname).substring(0, 1) : "#", Constants.number
							});
							ExampleApplication.getInstance().getSqliteHelper().execSQL("update addFriend set deal=? where fqr=? and userid=?", new Object[]
							{
									BodyAdd.DEAL_AGREE, hybh, Constants.number
							});
						}
						setAgreeTextView(BodyAdd.DEAL_AGREE, (LinearLayout) v);
					} else
					{
						new ToastShow().show(NewFriend.this, NewFriend.this.getResources().getString(R.string.str_error_data));
					}
				} catch (Exception e)
				{
					new ToastShow().show(NewFriend.this, NewFriend.this.getResources().getString(R.string.str_error_data));
				}
			}

			@Override
			public void onMyError(VolleyError error)
			{
				// TODO Auto-generated method stub

			}
		});
	}
}