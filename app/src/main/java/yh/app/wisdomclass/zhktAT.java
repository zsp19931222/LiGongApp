package yh.app.wisdomclass;

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

import org.androidpn.push.Constants;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具._链接地址导航;
import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

/**
 * 包 名:yh.app.wisdomclass 类 名:yh.app.wisdomclass.zhktAT.java 功 能:智慧课堂获取网络数据封装包
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-9-18
 */
public class zhktAT extends AsyncTask<String, integer, String>
{
	private int action = 0;
	private String url = null;
	private Handler mHandler;
	private int djc;

	public zhktAT(int action, Handler mHandler)
	{
//		new MyProgressbar(context).show(title, message);
		this.action = action;
		this.mHandler = mHandler;
	}

	public zhktAT(int action, Handler mHandler, int djc)
	{
//		new MyProgressbar(context).show(title, message);
		this.action = action;
		this.mHandler = mHandler;
		this.djc = djc;
	}

	@Override
	protected String doInBackground(String... params)
	{
		String result = "";
		List<NameValuePair> parames = null;
		parames = new ArrayList<NameValuePair>();
		parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150116")));
		parames.add(new BasicNameValuePair("function_id", "20150116"));
		if (action == 2) // 老师发起点名
		{
			url = _链接地址导航.DC.发起点名.getUrl();
			parames.add(new BasicNameValuePair("xkkh", params[0]));
			parames.add(new BasicNameValuePair("djz", params[1]));
			parames.add(new BasicNameValuePair("xqj", params[2]));
			parames.add(new BasicNameValuePair("djj", params[3]));
			parames.add(new BasicNameValuePair("dmcs", params[4]));
			parames.add(new BasicNameValuePair("jsbh", params[5]));
		} else if (action == 1)// 学生点名
		{
			url = _链接地址导航.DC.学生点名.getUrl();
			parames.add(new BasicNameValuePair("xh", params[0]));
			parames.add(new BasicNameValuePair("xkkh", params[1]));
		} else if (action == 3) // 获取课堂学生列表
		{
			url = _链接地址导航.DC.课堂学生列表.getUrl();
			parames.add(new BasicNameValuePair("xkkh", params[0]));
			parames.add(new BasicNameValuePair("jsbh", Constants.number));
		} else if (action == 4) // 已点名学生列表
		{
			url = _链接地址导航.DC.已点名学生列表.getUrl();
			parames.add(new BasicNameValuePair("jsbh", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));
		} else if (action == 5) // 提交点名
		{
			url = _链接地址导航.DC.提交点名.getUrl();
			parames.add(new BasicNameValuePair("jsbh", Constants.number));
			parames.add(new BasicNameValuePair("ktdmid", params[0]));
			parames.add(new BasicNameValuePair("tjlist", params[1]));
		} else if (action == 6) // 获取点名次数
		{
			url = _链接地址导航.DC.点名次数.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));
			parames.add(new BasicNameValuePair("djz", params[1]));
			parames.add(new BasicNameValuePair("xqj", params[2]));
			parames.add(new BasicNameValuePair("djj", params[3]));
		} else if (action == 7)
		{
			url = _链接地址导航.DC.点名班级.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));
		} else if (action == 8)
		{
			url = _链接地址导航.DC.教师小纸条学生名单.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
		} else if (action == 9)
		{
			url = _链接地址导航.DC.教师对话框内容.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
			parames.add(new BasicNameValuePair("xsbh", params[1]));
		} else if (action == 10)
		{
			url = _链接地址导航.DC.学生对话框内容.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
			parames.add(new BasicNameValuePair("xsbh", params[1]));
		} else if (action == 11)
		{
			url = _链接地址导航.DC.学生评教.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
			parames.add(new BasicNameValuePair("pjbz1", params[1]));
			parames.add(new BasicNameValuePair("pjbz2", params[2]));
			parames.add(new BasicNameValuePair("pjbz3", params[3]));
			parames.add(new BasicNameValuePair("pjbz4", params[4]));
			parames.add(new BasicNameValuePair("xsjy", params[5]));
			parames.add(new BasicNameValuePair("sfnm", "1"));
			parames.add(new BasicNameValuePair("bz", params[6]));
		} else if (action == 12)
		{
			url = _链接地址导航.DC.获取学生评教.getUrl();
			parames.add(new BasicNameValuePair("xsbh", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
		} else if (action == 13)
		{
			url = _链接地址导航.DC.获取教师评教.getUrl();
			parames.add(new BasicNameValuePair("jsbh", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
		} else if (action == 14)
		{
			url = _链接地址导航.DC.发送纸条.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("ktbh", params[0]));
			parames.add(new BasicNameValuePair("jsbh", params[1]));
			parames.add(new BasicNameValuePair("xsbh", params[2]));
			parames.add(new BasicNameValuePair("ztnr", params[3]));
		} else if (action == 15)
		{
			url = _链接地址导航.DC.所有学生点名结果.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));//选课号 
			parames.add(new BasicNameValuePair("djz", params[1]));
			parames.add(new BasicNameValuePair("xqj", params[2]));
			parames.add(new BasicNameValuePair("djj", params[3]));
		} else if (action == 16)
		{
			url = _链接地址导航.DC.点名次数.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));
			parames.add(new BasicNameValuePair("djz", params[1]));
			parames.add(new BasicNameValuePair("xqj", params[2]));
			parames.add(new BasicNameValuePair("djj", params[3]));//第几节
			parames.add(new BasicNameValuePair("xh", params[4]));
		}

		else if (action == 999)
		{
			url = _链接地址导航.DC.保存课堂信息.getUrl();
			parames.add(new BasicNameValuePair("user_id", Constants.number));
			parames.add(new BasicNameValuePair("xkkh", params[0]));
			parames.add(new BasicNameValuePair("djz", params[1]));
			parames.add(new BasicNameValuePair("xqj", params[2]));
			parames.add(new BasicNameValuePair("djj", params[3]));
		}
		try
		{
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			// 读取超时
			hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			HttpResponse hr = hc.execute(hp);//
			if (hr.getStatusLine().getStatusCode() == 200)
			{ //
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
	protected void onPostExecute(String result)
	{
		android.os.Message msg = new android.os.Message();
		msg.what = 0;
		try
		{
			if (result == null || result.equals("") || result.equals("null"))
			{
				msg.what = 0;
				mHandler.sendMessage(msg);
				return;
			}
			msg.what = action;
			result = result.replace("\\", "");
			result = result.substring(1, result.length() - 1);
			if (action == 1) // 学生
			{
				if (result.equals("false"))
				{
					msg.what = 0;
				} else
				{
					msg.what = 1;
				}
			} else if (action == 2) // 老师
			{
				try
				{
					JSONObject jsO = new JSONObject(result);
					if (jsO.getString("ktdmid") == null || jsO.getString("ktdmid").equals("") || jsO.getString("ktdmid").equals("null"))
					{
						msg.what = 0;
					}
					Bundle b = new Bundle();
					b.putString("ktdmid", jsO.getString("ktdmid"));
					msg.setData(b);
				} catch (JSONException e)
				{
					msg.what = 0;
				}
			} else if (action == 3) // 课堂学生列表
			{
				try
				{
					Bundle mBundle = new Bundle();
					mBundle.putString("ktxslb", result);
					msg.setData(mBundle);
				} catch (Exception e)
				{
					msg.what = 0;
				}

			} else if (action == 4)
			{
				try
				{
					Bundle mBundle = new Bundle();
					mBundle.putString("yyydmxs", result);
					msg.setData(mBundle);
				} catch (Exception e)
				{
					msg.what = 0;
				}
			} else if (action == 5)
			{
				JSONObject jso = new JSONObject(result);
				if (jso.getBoolean("boolean"))
				{
					msg.what = 5;
				} else
					msg.what = 0;
			} else if (action == 6)
			{
				msg.what = new JSONArray(result).getJSONObject(0).getInt("COUNT");
			} else if (action == 7)
			{
				try
				{
					msg.what = 1;
					JSONObject jso = new JSONObject(result);
					Bundle b = new Bundle();
					b.putString("ktdmid", jso.getString("ktdmid"));
					b.putString("sysj", jso.getString("sysj"));
					msg.setData(b);
				} catch (Exception e)
				{
					msg.what = 0;
				}
			} else if (action == 999)
			{
				try
				{
					JSONObject jso = new JSONObject(result);
					if (jso.getBoolean("boolean"))
					{
						Bundle b = new Bundle();
						b.putString("ktbh", jso.getString("message"));
						msg.setData(b);
					} else
					{
						msg.what = 0;
					}
				} catch (Exception e)
				{
					msg.what = 0;
				}
			} else if (action == 15)
			{
				msg.what = djc;
				Bundle mBundle = new Bundle();
				mBundle.putString("msg", result);
				msg.setData(mBundle);
			} else if (action == 16)
			{

			} else
			{
				Bundle mBundle = new Bundle();
				mBundle.putString("msg", result);
				msg.setData(mBundle);
			}

		} catch (Exception e)
		{
			msg.what = 0;
		}
		mHandler.sendMessage(msg);
		msg = null;
	}
}