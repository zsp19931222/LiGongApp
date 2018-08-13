package yh.app.contacts;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具._链接地址导航;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.cqgyxy.R;

/**
 * 包 名:yh.app.contacts 类 名:ContactMain.java 功 能:通讯录主页
 * 
 * @author 阙海林
 * @version 1.0
 * @date 2015-7-29
 */
public class ContactMain extends ActivityPortrait
{
	ListView list;
	// String contactUrl = Constants.tongxunluurl;
	private MyOpenHelper dbmanger = new MyOpenHelper(this, "mycontacts.db", null, 1);
	private AT mTask;
	private String[] id = null; // 声明用于存放日志日期的数组
	private String[] name = null; // 声明用于存放日志内容的数组
	private String[] pid = null;// 声明用于存放日志内容的数组
	private Handler mHandler;// 消息分发机制
	private ContactBaseAdapter adapter;
	private static int layer = 1;
	private TextView refresh = null;// 刷新按钮
	@SuppressWarnings("unused")
	private EditText searchView = null;// 搜索框
	private ImageButton return_pre;
	private static ArrayList<String> idList = new ArrayList<String>();
	private LinearLayout refresh_layout = null;
	private static String pid1;// 用来标识当前id，用于跳转
	private static boolean isleaf = false;
	private static boolean isRefresh = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 标题栏去除
		setContentView(R.layout.contact_main);
		try
		{
			list = (ListView) findViewById(R.id.listContact);// listView
			refresh = (TextView) findViewById(R.id.refresh_contact);// 刷新按钮
			return_pre = (ImageButton) findViewById(R.id.return_pre);// 返回
			searchView = (EditText) findViewById(R.id.search_view);// 搜索框，暂时未用
			refresh_layout = (LinearLayout) findViewById(R.id.refresh_ProgressBar);// 刷新布局
			idList.add("null"); // 第0层为null，根节点
			SQLiteDatabase db = dbmanger.getWritableDatabase();
			if (!(isleaf))
			{
				Cursor result = db.rawQuery("select * from contacts where department_pid='null'", null);
				// result.moveToFirst();
				if (result.getCount() == 0)
				{
					mTask = new AT();
					mTask.execute();
				} else
				{
					// 查找第一层
					searchJiguan(result);
				}
				result.close();
				db.close();
			} else
			{
				// refresh.setVisibility(8);
				Cursor result = db.rawQuery("select * from contacts where department_pid='" + pid1 + "'", null);
				searchJiguan(result);
				result.close();
				db.close();
			}
			adapter = new ContactBaseAdapter(this);
			// adapter1 = new ArrayAdapter<String>(this,
			// android.R.layout.simple_list_item_1, name);
			refresh.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					isRefresh = true;
					mTask = new AT();
					mTask.execute();
				}
			});
			list.setAdapter(adapter);
			list.setOnItemClickListener(new OnItemClickListener()
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
				{
					String leaf = "false";
					String zj = null, sj = null, department_name = null;
					// refresh.setVisibility(8);// GONE:8 意思是不可见的，不占用原来的布局空间
					LinearLayout parent = (LinearLayout) arg1;
					String id = ((TextView) parent.getChildAt(2)).getText().toString();
					pid1 = idList.get((layer - 1));
					SQLiteDatabase db = dbmanger.getWritableDatabase();
					Cursor isleafCursor = db.rawQuery("select * from contacts where id='" + id + "'", null);
					isleafCursor.moveToFirst();
					for (isleafCursor.moveToFirst(); !(isleafCursor.isAfterLast()); isleafCursor.moveToNext())
					{
						leaf = isleafCursor.getString(5);
						zj = isleafCursor.getString(2);
						sj = isleafCursor.getString(1);
						department_name = isleafCursor.getString(3);
					}
					isleafCursor.close();
					if (!(leaf.equals("true")))
					{// 不是叶子节点
						idList.add(id);
						Cursor result = db.rawQuery("select * from contacts where department_pid='" + id + "'", null);
						searchJiguan(result);
						result.close();
						db.close();
						adapter.notifyDataSetChanged();
						layer++;
					} else
					{// 叶子节点
						isleaf = true;
						idList.add(id);
						Intent intent = new Intent();
						intent.setClass(ContactMain.this, Contact_Call_Activity.class);
						intent.putExtra("department_name", department_name);// 部门名称
						intent.putExtra("sj", sj);// 部门名称
						intent.putExtra("zj", zj);// 部门名称
						startActivity(intent);
					}
				}
			});
			return_pre.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					layer--;
					SQLiteDatabase db = dbmanger.getWritableDatabase();
					if (layer == 1)
					{
						// refresh.setVisibility(0);
						Cursor result = db.rawQuery("select * from contacts where department_pid='null'", null);
						searchJiguan(result);
						result.close();
						adapter.notifyDataSetChanged();
						idList.clear();
						idList.add("null"); // 第0层为null，根节点
					} else if (layer == 0)
					{
						finish();
						layer = 1;
						isleaf = false;
					} else
					{
						// refresh.setVisibility(8);
						String d_pid = idList.get((layer - 1));
						Cursor result = db.rawQuery("select * from contacts where department_pid='" + d_pid + "'", null);
						searchJiguan(result);
						result.close();
						adapter.notifyDataSetChanged();
					}
					db.close();
				}
			});
			// searchView.setOnClickListener(new OnClickListener() {
			//
			// @Override
			// public void onClick(View arg0) {
			// Intent intent = new Intent(Test.this, SearchActivity.class);
			// startActivity(intent);
			//
			// }
			// });
			mHandler = new Handler()
			{
				@Override
				public void handleMessage(android.os.Message msg)
				{
					super.handleMessage(msg);
					switch (msg.what)
					{
					case 1:
						adapter.notifyDataSetChanged();
						break;
					case 2:
						mTask = new AT();
						break;
					}
				}
			};
		} catch (Exception e)
		{
		}
	}

	class ContactBaseAdapter extends BaseAdapter
	{
		private LayoutInflater inflater;

		public ContactBaseAdapter(Context context)
		{
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount()
		{
			if (id != null)
			{ // 如果日志数组不为空
				return id.length;
			} else
			{
				return 0; // 如果日志数组为空则返回0
			}
		}

		@Override
		public Object getItem(int arg0)
		{
			return null;
		}

		@Override
		public long getItemId(int arg0)
		{
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.contact_list_sub, null);
				holder.id = (TextView) convertView.findViewById(R.id.department_id);
				holder.name = (TextView) convertView.findViewById(R.id.department_name);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			// if (isleaf) {
			// addListener(tell_sj[position], tell_zj[position]);
			// }
			holder.id.setText(id[position]);
			holder.name.setText(name[position]);
			return convertView;
		}
	};

	/**
	 * 包 名:yh.app.contacts 类 名:ContactMain.java 功 能:开启UI线程的子线程,处理网络任务
	 * 
	 * @author 阙海林
	 * @version 1.0
	 * @date 2015-7-29
	 */
	class AT extends AsyncTask<String, Integer, String>
	{
		@Override
		protected void onPreExecute()
		{
			if (isRefresh)
			{
				// VISIBLE:0 意思是可见的
				// INVISIBILITY:4 意思是不可见的，但还占着原来的空间
				// GONE:8 意思是不可见的，不占用原来的布局空间
				refresh_layout.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected String doInBackground(String... params)
		{
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("userid", Constants.number));
			parames.add(new BasicNameValuePair("function_id", "20150121"));
			parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150121")));
			String result = null;
			try
			{
				HttpClient hc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(_链接地址导航.DC.通讯录.getUrl());
				hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
				// 响应超时
				hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
				// 读取超时
				hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
				HttpResponse hr = hc.execute(hp);//
				if (hr.getStatusLine().getStatusCode() == 200)
				{
					result = EntityUtils.toString(hr.getEntity());
				}
				if (hc != null)
				{
					hc.getConnectionManager().shutdown();
				}
				return result;
			} catch (Exception e)
			{
				e.printStackTrace();
				return result;
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progresses)
		{
		}

		@Override
		protected void onPostExecute(String result)
		{
			refresh_layout.setVisibility(View.GONE);
			SQLiteDatabase db = dbmanger.getWritableDatabase();
			if (result == null || result.equals("null") || result.equals(""))
			{
				Toast.makeText(getApplicationContext(), "网络异常，请刷新试试!", Toast.LENGTH_SHORT).show();
				return;
			} else
			{
				try
				{
					// 提示刷新成功
					Toast toast = Toast.makeText(getApplicationContext(), "刷新成功", Toast.LENGTH_SHORT);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
					String backlogJsonStrTmp = result.replace("\\", "");
					String jsonstr = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
					JSONArray array = null;
					try
					{
						array = new JSONArray(jsonstr);
					} catch (JSONException e)
					{
						e.printStackTrace();
					}
					if (array.length() > 0)
					{
						db.execSQL("delete from contacts");
					}
					for (int i = 0; i < array.length(); i++)
					{
						try
						{
							JSONObject temp = (JSONObject) array.get(i);
							Object[] o = new Object[]
							{
									getData(temp, "TXL_ID"), getData(temp, "TXL_SJH"), getData(temp, "TXL_ZJHM"), getData(temp, "TXL_NAME"), getData(temp, "TXL_PID"), getData(temp, "ISLEAF"),
							};
							db.execSQL("insert into contacts(id,tell_sj,tell_zj,department_name,department_pid,isleaf) values(?,?,?,?,?,?)", o);
						} catch (JSONException e)
						{
							e.printStackTrace();
						}
					}
					db.close();
					search();
					android.os.Message msg = new android.os.Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
				} catch (Exception e)
				{
					Toast.makeText(getApplicationContext(), "网络异常，请刷新试试!", Toast.LENGTH_SHORT).show();
					return;
				}
			}
		}
	}

	public String getData(JSONObject temp, String ziduan)
	{
		String str = "";
		if (ziduan.equals("ISLEAF"))
		{
			try
			{
				String s = getData(temp, "TXL_ZJHM");
				if (s == null || s.equals("") || s.equals("null"))
				{
					return "false";
				}
				return "true";
			} catch (Exception e)
			{
				return "false";
			}
		} else
		{
			try
			{
				str = temp.getString(ziduan).toString();
			} catch (Exception e)
			{
				str = "true";
			}
		}
		return str;
	}

	// 查找父id为空的节点，即第一层
	public void search()
	{
		SQLiteDatabase db = dbmanger.getWritableDatabase();
		Cursor result = db.rawQuery("select * from contacts where department_pid='null'", null);
		searchJiguan(result);
		result.close();
		db.close();
	}

	public void searchJiguan(Cursor result)
	{
		id = new String[result.getCount()];
		name = new String[result.getCount()];
		pid = new String[result.getCount()];
		int i = 0; // 声明一个计数器
		for (result.moveToFirst(); !(result.isAfterLast()); result.moveToNext())
		{
			id[i] = result.getString(0);
			name[i] = result.getString(3); // 将标题添加到String数组中
			pid[i] = result.getString(4);
			i++;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
		{
			layer--;
			SQLiteDatabase db = dbmanger.getWritableDatabase();
			if (layer == 1)
			{
				// refresh.setVisibility(0);
				Cursor result = db.rawQuery("select * from contacts where department_pid='null'", null);
				searchJiguan(result);
				result.close();
				adapter.notifyDataSetChanged();
				idList.clear();
				idList.add("null"); // 第0层为null，根节点
			} else if (layer == 0)
			{
				finish();
				layer = 1;
				isleaf = false;
			} else
			{
				// refresh.setVisibility(8);
				String d_pid = idList.get((layer - 1));
				Cursor result = db.rawQuery("select * from contacts where department_pid='" + d_pid + "'", null);
				searchJiguan(result);
				result.close();
				adapter.notifyDataSetChanged();
			}
			db.close();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}
}
