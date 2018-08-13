package yh.app.score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.android.volley.VolleyError;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;
import com.yhkj.cqgyxy.R;
import yh.app.progressdialog.CustomProgressDialog;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._空白填页;
import 云华.智慧校园.工具._链接地址导航;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 包 名:yh.app.score 类 名:Stu_Grade_Query_Activity.java 功 能:分数列表显示界面
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class Stu_Grade_Query_Activity extends ActivityPortrait
{
	private LinearLayout cjcx;
	private Stu_Grade_Adapter adapter;// 分数显示适配器
	@SuppressWarnings("unused")
	private String XN, XQ = null;// 需要查询学生分数的学年与学期
	Spinner spiner = null;// 下拉控件
	private ListView lv;// 成绩列表的显示控件
	private List<Map<String, Object>> allGrades = null;// 存放所有当前成绩查询的信息
	private AT mTask = null;// 网络连接任务
	private Handler mHandler;// 消息分发机制
	private String[] a;
	CustomProgressDialog c = null;// 等待动画
	private Context mContext;
	private View kbtyView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 标题栏去除
		setContentView(R.layout.student_grade_query);
		new DefaultTopBar(this).doit(Ticket.getFunctionName(getIntent().getStringExtra("function_id")));
		SQLiteDatabase db1 = null;
		Cursor c1 = null;
		try
		{
			db1 = new SqliteHelper().getRead();
			c1 = db1.rawQuery("select * from nowterm", null);
			if (c1.getCount() == 0)
			{
				Toast.makeText(this, "暂无时间信息,请重试", Toast.LENGTH_SHORT).show();
				finish();
				return;
			}
		} catch (Exception e)
		{

		} finally
		{
			new SqliteDBCLose(db1, c1);
		}
		mContext = this;
		cjcx = (LinearLayout) findViewById(R.id.cjcx);
		allGrades = new ArrayList<Map<String, Object>>();
		// 初始化动画进度条
		c = CustomProgressDialog.createDialog(Stu_Grade_Query_Activity.this);
		c.setTitile("网络连接");
		c.setMessage("数据获取中。。。");
		c.onWindowFocusChanged(true);
		c.setCancelable(false);
		// 获取数据库
		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor result = db.rawQuery("select * from term", null);// 查询数据库中该学生的学年
		Cursor nowXN = db.rawQuery("select * from nowterm", null);
		if (result.getCount() == 0)
		{
			a = new String[]
			{
					"暂无成绩"
			};
		} else
			a = new String[result.getCount()];// 获取返回结果数量
		int i = 0, defalut = 0;
		nowXN.moveToFirst();
		if (result.moveToNext())
			do
			{
				a[i] = result.getString(0) + "," + result.getString(1);
				if ((result.getString(0) + result.getString(1)).equals(nowXN.getString(1) + nowXN.getString(0)))// 如果该学期是当前的学期
				{
					defalut = i;// 默认的学期等于当前
				}
				i++;
			} while (result.moveToNext());
		else
		{
			defalut = 0;
		}
		db.close();
		// 对下拉框进行设置
		spiner = (Spinner) findViewById(R.id.term);
		ArrayAdapter<String> Arrayadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, xuanze())
		{
		};
		Arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spiner.setAdapter(Arrayadapter);// 将下拉的项目适配进入该下拉控件中
		spiner.setSelection(defalut, true);// 设置默认项为当前学期
		//成绩查询下拉列表点击事件
		spiner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
		{
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				// TODO Auto-generated method stub
				arg0.setVisibility(View.VISIBLE);
				android.os.Message msg = new android.os.Message();
				msg.what = 2;
				mHandler.sendMessage(msg); // 发送消息刷新界面
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0)
			{
				// TODO Auto-generated method stub
			}
		});
		
		lv = (ListView) findViewById(R.id.student_grade_query_eL); // 获得ListView对象的引用
		lv.setOnItemClickListener(chakanClickListener); // 为成绩设置点击事件
		adapter = new Stu_Grade_Adapter(this, allGrades); // 为ListView放一个内容适配对象
		lv.setAdapter(adapter); // 设置内容适配对象
		if (Constants.isNetworkAvailable(Stu_Grade_Query_Activity.this))
		{
			// 网络连接正常的情况下开始连接任务
			mTask = new AT();
			try
			{
				String[] b = a[spiner.getSelectedItemPosition()].split(",");
				mTask.execute(Constants.number, b[0], b[1]);
			} catch (Exception e)
			{
				// TODO: handle exception
			}

		} else
		{
			Toast.makeText(getApplicationContext(), "网络环境异常，请检查网络连接！", Toast.LENGTH_SHORT).show();
		}
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
					if (Constants.isNetworkAvailable(Stu_Grade_Query_Activity.this))
					{
						try
						{
							String[] b = a[spiner.getSelectedItemPosition()].split(",");
							mTask.execute(Constants.number, b[0], b[1]);
						} catch (Exception e)
						{
						}

					} else
					{
						Toast.makeText(getApplicationContext(), "网络环境异常，请检查网络连接！", Toast.LENGTH_SHORT).show();
					}
					break;
				}
			}
		};
	}

	// 点击listview中一项，跳转查看界面
	OnItemClickListener chakanClickListener = new OnItemClickListener()
	{
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			// TODO Auto-generated method stub
			// Log.v("error1", "点击listview中一项，跳转查看界面");
			Intent intent = new Intent(Stu_Grade_Query_Activity.this, Stu_Grade_Query_Detail_Activity.class);
			intent.putExtra("c_id", GetString(allGrades.get(arg2), "c_id"));
			intent.putExtra("c_name", GetString(allGrades.get(arg2), "c_name"));
			intent.putExtra("c_grade", GetString(allGrades.get(arg2), "c_grade"));
			intent.putExtra("c_xingzhi", GetString(allGrades.get(arg2), "c_xingzhi"));
			intent.putExtra("c_xuefen", GetString(allGrades.get(arg2), "c_xuefen"));
			intent.putExtra("c_GPA", GetString(allGrades.get(arg2), "c_GPA"));// 绩点
			intent.putExtra("c_scoreType", GetString(allGrades.get(arg2), "c_scoreType"));// 绩点
			new cjzs().doit(mContext, cjcx, intent);
		}

		private String GetString(Map<String, Object> map, String key)
		{
			Object obj = null;
			try
			{
				obj = map.get(key);
				return obj.toString();
			} catch (Exception e)
			{
				return "-";
			}
		}
	};

	public String[] xuanze()
	{
		String[] newterms = null;
		try
		{
			newterms = new String[a.length];
			for (int i = 0; i < a.length; i++)
			{
				String[] temp = a[i].split(",");
				newterms[i] = temp[0] + "学年" + "第" + temp[1] + "学期";
			}
		} catch (Exception e)
		{
			newterms = new String[]
			{
					"暂无数据"
			};
		}

		return newterms;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stu__grade__query_, menu);
		return true;
	}
	String rt = null;
	class AT extends AsyncTask<String, Integer, String>
	{
		@SuppressWarnings("unused")
		private int action = 0;

		public boolean setAction(int action)
		{
			this.action = action;
			return true;
		}

		@Override
		protected void onPreExecute()
		{
		}

		@Override
		protected String doInBackground(String... params)
		{
			
//			Map<String, String> map=new HashMap<>();
//			map.put("userid", Constants.number);
//			map.put("function_id", "20150106");
//			map.put("ticket", Ticket.getFunctionTicket("20150106"));
//			map.put("xn", params[1]);
//			map.put("xq", params[2]);
//			BaseVolleyRequest.RequestPost(_链接地址导航.DC.成绩查询.getUrl(), map,new VolleyInterface() {
//				
//				@Override
//				public void onMySuccess(String result) {
//					rt=result;
//				}
//				
//				@Override
//				public void onMyError(VolleyError error) {
//					Toast.makeText(mContext, "成绩查询失败", Toast.LENGTH_SHORT).show();
//				}
//			});
//			
//			return rt;
			
			
			String result="";
			try
			{
				HttpClient hc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(_链接地址导航.DC.成绩查询.getUrl());
				List<NameValuePair> parames = new ArrayList<NameValuePair>();
				if (params.length > 1)
				{
					parames.add(new BasicNameValuePair("userid", Constants.number));
					parames.add(new BasicNameValuePair("function_id", "20150106"));
					parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150106")));
					parames.add(new BasicNameValuePair("xn", params[1]));
					parames.add(new BasicNameValuePair("xq", params[2]));
					// parames.add(new BasicNameValuePair("ticket",
					// Ticket.getFunctionTicket("20150106")));
				} else
				{
					parames.add(new BasicNameValuePair("error", "ffdf"));
				}
				hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
				// 响应超时
				hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
				// 读取超时
				hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
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
				return result;
			}
		}

		@Override
		protected void onProgressUpdate(Integer... progresses)
		{
		}

		private void kbty()
		{
			if (kbtyView == null)
			{
				kbtyView = new _空白填页().addView(mContext, ((Activity) mContext).findViewById(R.id.content), cjcx, R.drawable.cjcx_kby, "成绩还未公布呢，默默祈祷吧~~~");
			}
			kbtyView.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onPostExecute(String result)
		{
			if (result == null || result.equals("null") || result.equals(""))
			{
				c.cancel();
				kbty();
				return;
			}
			try
			{
				allGrades.clear();
				String backlogJsonStrTmp = result.replace("\\", "");
				String jsonstr = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
				JSONArray array = null;
				try
				{
					array = new JSONObject(jsonstr).getJSONArray("message");
					if (array == null || array.length() == 0)
					{
						kbty();
						return;
					}
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
				if (kbtyView != null)
					kbtyView.setVisibility(View.GONE);
				((Activity) mContext).findViewById(R.id.content).setVisibility(View.VISIBLE);
				for (int i = 0; i < array.length(); i++)
				{
					Map<String, Object> oneGrade = new HashMap<String, Object>();
					try
					{
						// 存放所有成绩查询的信息
						JSONObject temp = (JSONObject) array.get(i);
						oneGrade.put("c_id", temp.getString("courseId"));
						oneGrade.put("c_name", temp.getString("courseName"));
						if (temp.getString("courseScore") == null || temp.getString("courseScore").equals("") || temp.getString("courseScore").equals("null"))
						{
							oneGrade.put("c_grade", "0");
						} else
							oneGrade.put("c_grade", temp.getString("courseScore"));
						oneGrade.put("c_xingzhi", temp.getString("courseNature"));
						oneGrade.put("c_xuefen", temp.getString("courseCredit"));
						oneGrade.put("c_GPA", temp.getString("GPA")); // 平均绩点
						oneGrade.put("c_scoreType", temp.getString("scoreType"));
					} catch (JSONException e)
					{
					}
					allGrades.add(oneGrade);
				}
				android.os.Message msg = new android.os.Message();
				msg.what = 1;
				mHandler.sendMessage(msg);
			} catch (Exception e)
			{
				kbty();
			}
		}

		@Override
		protected void onCancelled()
		{
		}
	}
}
