//package yh.app.uiengine;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.androidpn.push.Constants;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
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
//import android.content.Context;
//import android.database.SQLException;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.os.Handler;
//import android.os.Message;
//import yh.app.tool.MD5;
//import yh.app.tool.SqliteDBCLose;
//import yh.app.tool.SqliteHelper;
//import 云华.智慧校园.工具._链接地址导航;
//import yh.app.activitytool.ActivityPortrait;
//
//public class LoginTools extends ActivityPortrait
//{
//	private String userid, password;
//	private Handler mHandler;
//
//	public LoginTools(String userid, String password, Handler mHandler, Context context)
//	{
//		this.mHandler = mHandler;
//		this.userid = userid;
//		this.password = password;
//	}
//
//	public void doit()
//	{
//		AT at = new AT(1);
//		at.execute(userid, password);
//	}
//
//	Handler handler = new Handler()
//	{
//		@Override
//		public void handleMessage(Message msg)
//		{
//			super.handleMessage(msg);
//			AT at = new AT(2);
//			at.execute();
//		}
//	};
//
//	class AT extends AsyncTask<String, Integer, String>
//	{
//		private int action;
//
//		public AT(int action)
//		{
//			this.action = action;
//		}
//
//		@Override
//		protected String doInBackground(String... params)
//		{
//
//			String result = null;
//			try
//			{
//				List<NameValuePair> parames = new ArrayList<NameValuePair>();
//				String url = "";
//				if (action == 1)
//				{
//					url = Constants.loginurl;
//					parames.add(new BasicNameValuePair("userid", Constants.number));
//					parames.add(new BasicNameValuePair("password", Constants.code));
//				} else if (action == 2)// 获取function
//				{
//					url = Constants.functionurl + Constants.number + "&ticket=" + MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code) + "&Version=1";
//				}
//
//				HttpClient hc = null;
//				HttpParams httpParameters = new BasicHttpParams();
//				hc = new DefaultHttpClient(httpParameters);
//				HttpPost hp = new HttpPost(url);
//				hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
//				// 设置连接超时
//				HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//				// 设置响应超时
//				HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//				HttpResponse hr = hc.execute(hp);
//				if (hr.getStatusLine().getStatusCode() == 200)
//				{
//					result = EntityUtils.toString(hr.getEntity());
//				}
//				hc.getConnectionManager().shutdown();
//			} catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//			return result;
//
//		}
//
//		@Override
//		protected void onPostExecute(String result)
//		{
//			if (result == null || result.isEmpty())
//			{
//				Message msg = new Message();
//				msg.what = 0;
//				mHandler.sendMessage(msg);
//				return;
//			} else if (action == 1)
//			{
//				try
//				{
//					login(result);
//				} catch (Exception e)
//				{
//					Message msg = new Message();
//					msg.what = 0;
//					mHandler.sendMessage(msg);
//				}
//			} else if (action == 2)
//			{
//				try
//				{
//					function(result);
//				} catch (Exception e)
//				{
//					Message msg = new Message();
//					msg.what = 0;
//					mHandler.sendMessage(msg);
//				}
//			}
//		}
//
//		private void function(String result) throws Exception
//		{
//			result = result.replace("\\", "");
//			result = result.substring(1, result.length() - 1);
//			JSONObject array = new JSONObject(result);
//			SQLiteDatabase db = null;
//			try
//			{
//				db = new SqliteHelper().getWrite();
//				db.execSQL("delete from button");
//			} catch (SQLException e)
//			{
//
//			} catch (Exception e)
//			{
//				// TODO: handle exception
//			}
//
//			try
//			{
//				array = new JSONObject(result);
//				if (array.getString("error") != null || !array.getString("error").isEmpty())
//				{
//					Message msg = new Message();
//					msg.what = 0;
//					mHandler.sendMessage(msg);
//					return;
//				}
//			} catch (JSONException e1)
//			{
//
//			} catch (Exception e)
//			{
//			}
//			List<Map<String, String>> functionList = new ArrayList<Map<String, String>>();
//			for (int i = 0; i < array.length(); i++)
//			{
//				Map<String, String> function = new HashMap<String, String>();
//				if (getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != null && getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != "")
//				{
//					function.put("function_id", getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID"));
//					function.put("function_name", getFunctionDate(array, "functionrow_" + i, "FUNCTION_NAME"));
//					function.put("function_type", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYPE"));
//					function.put("class_name", getFunctionDate(array, "functionrow_" + i, "CLASS_NAME"));
//					function.put("package_name", getFunctionDate(array, "functionrow_" + i, "PACKAGE_NAME"));
//					function.put("integerate_key", getFunctionDate(array, "functionrow_" + i, "INTEGRATE_KEY"));
//					function.put("function_face", getFunctionDate(array, "functionrow_" + i, "FUNCTION_FACE"));
//					function.put("px", getFunctionDate(array, "functionrow_" + i, "PX"));
//					functionList.add(function);
//				}
//			}
//			try
//			{
//				db.execSQL("delete from button");
//			} catch (SQLException e)
//			{
//			} catch (Exception e)
//			{
//				// TODO: handle exception
//			}
//
//			for (int i = 0; i < functionList.size(); i++)
//			{
//				db.execSQL("replace into button values(?,?,?,?,?,?,?,?)", new Object[]
//				{
//						functionList.get(i).get("function_id"), functionList.get(i).get("function_name"), functionList.get(i).get("function_type"), functionList.get(i).get("class_name"), functionList.get(i).get("package_name"), functionList.get(i).get("integerate_key"), functionList.get(i).get("function_face"), Integer.valueOf(functionList.get(i).get("px"))
//				});
//			}
//			try
//			{
//				new SqliteDBCLose(db, null).close();
//			} catch (SQLException e)
//			{
//			} catch (Exception e)
//			{
//			}
//			Message msg = new Message();
//			msg.what = 1;
//			mHandler.sendMessage(msg);
//		}
//
//		private String getFunctionDate(JSONObject JSobject, String rownum, String ziduan)
//		{
//			String str = "";
//			try
//			{
//				str = JSobject.getJSONObject(rownum).getString(ziduan);
//			} catch (JSONException e)
//			{
//			}
//			return str;
//		}
//
//		private void login(String result) throws Exception
//		{
//			result = result.replace("\\", "");
//			result = result.substring(1, result.length() - 1);
//			JSONArray jsa = getJsonArray(result);
//			if (jsa == null)
//			{
//				return;
//			} else
//			{
//				if (jsa.getJSONObject(0).getString("islogin").equals("true"))
//				{
//					SQLiteDatabase db = null;
//					try
//					{
//						db = new SqliteHelper().getWrite();
//						JSONObject jso = jsa.getJSONObject(0).getJSONObject("userinfo");
//						if (jsa.getJSONObject(0).getInt("usertype") == 1)
//						{
//							db.execSQL("insert into user(userid,name,birthday,sex,ethnic,sfz_number,bj,qq,email,telphone,zy,bm,nj,faceaddress,sch,state) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new String[]
//							{});
//						} else if (jsa.getJSONObject(0).getInt("usertype") == 2)
//						{
//
//							db.execSQL("insert into user(userid,name,birthday,sex,ethnic,sfz_number,bj,qq,email,telphone,zy,bm,nj,faceaddress,sch,state) " + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new String[]
//							{
//									Constants.number, getJsonDate(jso, "STUDENT_NAME"), getJsonDate(jso, "BIRTHDAY"), getJsonDate(jso, "SEX_ID"), getJsonDate(jso, "ETHNIC_ID"), getJsonDate(jso, "SFZ_NUMBER"), getJsonDate(jso, "BJ_ID"), getJsonDate(jso, "QQ"), getJsonDate(jso, "EMAIL"), getJsonDate(jso, "TELPHONO"), getJsonDate(jso, "ZYDM"), getJsonDate(jso, "BMDM"), getJsonDate(jso, "NJDM"), getJsonDate(jso, "FACEADDRESS"), getJsonDate(jso, "SCHOOL_ID"), jsa.getJSONObject(0).getString("islogin")
//							});
//						}
//					} catch (Exception e)
//					{
//						// TODO: handle exception
//					} finally
//					{
//						try
//						{
//							new SqliteDBCLose(db, null).close();
//						} catch (SQLException e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (Exception e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					Message msg = new Message();
//					msg.what = 1;
//					handler.sendMessage(msg);
//				}
//			}
//		}
//
//		private String getJsonDate(JSONObject jso, String zd)
//		{
//			try
//			{
//				String re = jso.getString(zd);
//				if (re == null)
//					return "-";
//				else
//					return re;
//			} catch (Exception e)
//			{
//				return "-";
//			}
//		}
//
//		private JSONArray getJsonArray(String result)
//		{
//			try
//			{
//				return new JSONArray(result);
//			} catch (JSONException e)
//			{
//				return null;
//			}
//		}
//	}
//}
