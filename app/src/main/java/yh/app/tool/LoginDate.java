//package yh.app.tool;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.androidpn.push.Constants;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import yh.app.db.MyImdb;
//import yh.app.progressdialog.CustomProgressDialog;
//import 云华.智慧校园.工具._链接地址导航;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.net.ParseException;
//import android.os.AsyncTask;
//import android.os.Handler;
//
//public class LoginDate extends AsyncTask<String, Integer, String>
//{
//
//	private int action;
//	private Context context;
//	CustomProgressDialog c;
//	private Handler mHandler;
//
//	public LoginDate(int action, Context context, Handler handler)
//	{
//		this.action = action;
//		this.context = context;
//		this.mHandler = handler;
//	}
//
//	@Override
//	protected String doInBackground(String... params)
//	{
//		// getFunction();
//		HttpClient hc = null;
//		String result = null;
//		try
//		{
//
//			List<NameValuePair> parames = new ArrayList<NameValuePair>();
//			String url = "";
//
//			switch (action)
//			{
//			case 0:
//				if (params.length > 1)
//				{
//					parames.add(new BasicNameValuePair("userName", params[0]));
//					parames.add(new BasicNameValuePair("password", params[1]));
//					// passwrod = MD5.MD5(params[1]);
//					parames.add(new BasicNameValuePair("getVersion", params[2]));
//					parames.add(new BasicNameValuePair("getVersionCode", params[3]));
//				} else
//				{
//					parames.add(new BasicNameValuePair("error", "ffdf"));
//				}
//				url = Constants.loginurl;
//				break;
//			case 1:
//				if (params.length > 1)
//				{
//				} else
//				{
//				}
//				url = Constants.newurllist;
//				break;
//			default:
//				break;
//			}
//
//			HttpParams httpParameters = new BasicHttpParams();
//			hc = new DefaultHttpClient(httpParameters);
//
//			HttpPost hp = new HttpPost(url);
//			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
//			// 设置连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//			// 设置响应超时
//			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//			HttpResponse hr = hc.execute(hp);//
//			// if (hc != null) {
//			// hc.getConnectionManager().shutdown();
//			// }
//
//			if (hr.getStatusLine().getStatusCode() == 200)
//			{ //
//				result = EntityUtils.toString(hr.getEntity());
//			}
//			return result;
//
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//			return result;
//		} finally
//		{
//			hc.getConnectionManager().shutdown();
//		}
//	}
//
//	private void getFunction(SQLiteDatabase db)
//	{
//		// SQLiteDatabase bd = Constants.
//		String result = "";
//		String ticket = MD5.MD5(_链接地址导航.addString + "11108990938" + MD5.MD5("178716"));
//		int dateVersion = 1;
//		String url = "http://202.202.144.194:8080/DC/function/list.action?userid=" + "11108990938" + "&ticket=" + ticket + "&Version=" + dateVersion;
//		;
//		/*
//		 * String url =
//		 * "http://202.202.144.194:8080/DC/function/list.action?userid=" +
//		 * Constants.number + "&ticket=" + ticket + "&Version=" + dateVersion;
//		 */
//
//		HttpParams httpParameters = new BasicHttpParams();
//		HttpClient hc = new DefaultHttpClient(httpParameters);
//
//		HttpPost hp = new HttpPost(url);
//
//		// 设置连接超时
//		HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//		// 设置响应超时
//		HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//		HttpResponse hr = null;
//
//		try
//		{
//			hr = hc.execute(hp);
//		} catch (ClientProtocolException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		if (hr.getStatusLine().getStatusCode() == 200)
//		{ //
//
//			try
//			{
//				result = EntityUtils.toString(hr.getEntity());
//			} catch (ParseException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e)
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//		}
//		result = result.replace("\\", "");
//		result = result.substring(1, result.length() - 1);
//		JSONObject array = null;
//		try
//		{
//			array = new JSONObject(result);
//		} catch (JSONException e1)
//		{
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		List<Map<String, String>> functionList = new ArrayList<Map<String, String>>();
//		for (int i = 0; i < array.length(); i++)
//		{
//			Map<String, String> function = new HashMap<String, String>();
//			function.put("function_id", getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID"));
//			function.put("function_name", getFunctionDate(array, "functionrow_" + i, "FUNCTION_NAME"));
//			function.put("function_type", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYPE"));
//			function.put("class_name", getFunctionDate(array, "functionrow_" + i, "CLASS_NAME"));
//			function.put("package_name", getFunctionDate(array, "functionrow_" + i, "PACKAGE_NAME"));
//			function.put("integerate_key", getFunctionDate(array, "functionrow_" + i, "INTEGRATE_KEY"));
//			function.put("function_face", getFunctionDate(array, "functionrow_" + i, "FUNCTION_FACE"));
//			functionList.add(function);
//		}
//		for (int i = 0; i < functionList.size(); i++)
//		{
//			db.execSQL("replace into button values(?,?,?,?,?,?,?)", new Object[]
//			{
//					functionList.get(i).get("function_id"), functionList.get(i).get("function_name"), functionList.get(i).get("function_type"), functionList.get(i).get("class_name"), functionList.get(i).get("package_name"), functionList.get(i).get("integerate_key"), functionList.get(i).get("function_face")
//			});
//		}
//	}
//
//	private String getSql(int num)
//	{
//		String sql = "";
//		return sql;
//	}
//
//	private String getFunctionDate(JSONObject JSobject, String rownum, String ziduan)
//	{
//		String str = "";
//		try
//		{
//			str = JSobject.getJSONObject(rownum).getString(ziduan);
//			return str;
//		} catch (JSONException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "-";
//		}
//
//	}
//
//	@Override
//	protected void onPostExecute(String result)
//	{
//		if (result == null)
//		{
//			return;
//		}
//		int what = 1;
//		if (result != null)
//		{
//			String backlogJsonStrTmp = result.replace("\\", "");
//			String jsonstr = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
//			SQLiteDatabase db = new SqliteHelper().getWrite();
//			// getFunction(db);
//			what = login(jsonstr, db) + updatenews(jsonstr, db);
//			db.close();
//		}
//		android.os.Message msg = new android.os.Message();
//		msg.what = what;
//		mHandler.sendMessage(msg);
//	}
//
//	private int updatenews(String jsonstr, SQLiteDatabase db)
//	{
//		JSONArray array = null;
//		db.execSQL("delete from news");
//		int result = 1;
//		try
//		{
//			array = new JSONArray(jsonstr);
//			for (int i = 0; i < array.length(); i++)
//			{
//				JSONObject temp = (JSONObject) array.get(i);
//				db.execSQL("insert into news(newsid,title,context,datetime,imgurl) values(?,?,?,?,?)", new Object[]
//				{
//						temp.getString("articleId"), temp.getString("title"), "", temp.getString("time"), temp.getString("face")
//				});
//				result = 1;
//			}
//		} catch (JSONException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			result = 0;
//		} finally
//		{
//			return result;
//		}
//	}
//
//	@SuppressWarnings("finally")
//	private int login(String jsonstr, SQLiteDatabase db)
//	{
//		JSONArray array = null;
//		int result = 1;
//		try
//		{
//			array = new JSONArray(jsonstr);
//			Boolean iFlogin = (Boolean) array.get(0);
//			if (iFlogin)
//			{
//				result = 3;
//				Integer Identity = (Integer) array.get(1);
//				Constants.user_type = String.valueOf(Identity);
//				if (Identity == 1)
//				{ //
//
//					JSONArray term = (JSONArray) array.get(3);
//
//					if (term.equals(null))
//					{
//
//						Cursor result_term = db.rawQuery("select * from term ", null);
//						if (result_term.getCount() == 0)
//						{
//							db.execSQL("replace into term (termid,xq,xn,ifnow,starttime,endtime,userid) values(?,?,?,?,?,?,?)", new Object[]
//							{
//									Constants.number, "", "", "false", "", "", Constants.number
//							});
//						}
//
//					} else
//					{
//						for (int i = 0; i < term.length(); i++)
//						{
//							JSONObject temp = (JSONObject) term.get(i);
//							JsonHelper js = new JsonHelper(temp);
//							try
//							{
//								db.execSQL("replace into term (termid,xq,xn,ifnow,starttime,endtime,userid) values(?,?,?,?,?,?,?)", new Object[]
//								{
//										Constants.number + js.GetValue("XN") + js.GetValue("XQ"), js.GetValue("XQ"), js.GetValue("XN"), "false", js.GetValue("STARTTIME"), js.GetValue("ENDTIME"), Constants.number
//								});
//							} catch (Exception e)
//							{
//								db.execSQL("replace into term (termid,xq,xn,ifnow,userid) values(?,?,?,?,?)", new Object[]
//								{
//										Constants.number + js.GetValue("XN") + js.GetValue("XQ"), js.GetValue("XQ"), js.GetValue("XN"), "false", Constants.number
//								});
//							}
//						}
//					}
//					JSONObject nowterm = (JSONObject) array.get(4);
//					if (nowterm != null)
//					{
//						JsonHelper js = new JsonHelper(nowterm);
//						db.execSQL("replace into term (termid,xq,xn,ifnow,starttime,endtime,userid) values(?,?,?,?,?,?,?)", new Object[]
//						{
//								Constants.number + js.GetValue("YEAR") + js.GetValue("TERM"), js.GetValue("TERM"), js.GetValue("YEAR"), "true", js.GetValue("STARTTIME"), js.GetValue("ENDTIME"), Constants.number
//						});
//					}
//				}
//			} else
//			{
//				result = 2;
//			}
//		} catch (JSONException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			result = 0;
//		} finally
//		{
//			return result;
//		}
//	}
//
//}
