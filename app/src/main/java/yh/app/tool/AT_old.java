package yh.app.tool;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.androidpn.push.Constants;
import yh.app.progressdialog.CustomProgressDialog;
import yh.app.tool.MD5;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.RSAEncrypt;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;

/**
 * 包 名:yh.app.tool 类 名:yh.app.tool.AT 功 能:该类主要是负责从网络获取数据,并且存放到sqlite
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015/7/29
 */
@SuppressWarnings(
{
		"unused", "deprecation"
})
public class AT_old extends AsyncTask<String, Integer, String>
{
	private String functionString;
	private String key = "";
	private String function_id = "20150101";
	private int action = 0;
	private Context context = null;
	private CustomProgressDialog c = null;
	private ProgressDialog mProgressDialog = null;
	private Handler mHandler = null;
	private int db_version = 0;
	private Activity activity;
	private boolean login_sucesses = false, function_sucesses = false;
	private boolean isShowProgressDialog;

	public AT_old(int action, Context context, Handler handler, boolean isShowProgressDialog)
	{
		this.action = action;
		this.context = context;
		this.mHandler = handler;
		this.isShowProgressDialog = isShowProgressDialog;
	}

	public boolean setAction(int action, Context context, Handler mHandler)
	{
		this.action = action;
		this.context = context;
		this.mHandler = mHandler;
		return true;
	}

	@Override
	protected void onPreExecute()
	{
		if (isShowProgressDialog)
		{
			mProgressDialog = new ProgressDialog(context);
			mProgressDialog.setCancelable(true);
			mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
			{
				@Override
				public void onCancel(DialogInterface dialog)
				{
					cancel(true);
				}
			});
			mProgressDialog.show();
		}
	}

	/** 获取网络数据 ,并返回数据 */
	@Override
	protected String doInBackground(String... params)
	{
		String result = null;
		try
		{
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			String url = "";

			if (action == 1)
			{
				Constants.code = MD5.MD5(Constants.code);
			} else if (action == 2)
			{
			} else if (action == 3)
			{
				Constants.number = params[0];
				Constants.code = params[1];
			}
			String a = String.format(Constants.number + "<@.%s.@>" + Constants.code, new Object[]
			{
					new Random().nextInt()
			});
			String s = new JSONArray(new RSAHelper().encrypt(RSAHelper.getDivLines(a, 50))).toString();
			parames.add(new BasicNameValuePair("a", URLEncoder.encode(s, "utf-8")));
			url = _链接地址导航.UIA.登录.getUrl();
			// url = Constants.loginurl;
			HttpClient hc = null;
			HttpParams httpParameters = new BasicHttpParams();
			hc = new DefaultHttpClient(httpParameters);
			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			// 设置连接超时
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
			// 设置响应超时
			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
			HttpResponse hr = hc.execute(hp);
			if (hr.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(hr.getEntity());
			}
			hc.getConnectionManager().shutdown();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		SQLiteDatabase db = null;
		try
		{
			getFunction(db);
			db = new SqliteHelper().getWrite();
			db.close();
		} catch (Exception e)
		{
			if (db.isOpen())
				db.close();
		}
		return result;
	}

	@Override
	protected void onProgressUpdate(Integer... progresses)
	{
		
	}

	/**
	 * 对数据进行解析
	 * 
	 * @param jsonstr
	 *            网络JSON数据字符串
	 * @param db
	 *            操作的数据库对象
	 * @return 返回登录信息
	 */
	private int login(String jsonstr, SQLiteDatabase db)
	{
		JSONArray array = null;
		int result = 1;
		try
		{
			array = new JSONArray(jsonstr);
			boolean iFlogin = false;
			JSONObject str;
			str = array.getJSONObject(0);
			Constants.usertype = str.getInt("usertype");
			try
			{
				db.execSQL("replace into usertype values('" + Constants.number + "'," + str.getInt("usertype") + ");");
			} catch (Exception e)
			{
			}
			String st = null;
			String s;
			try
			{
				st = str.getString("islogin");
			} catch (Exception e)
			{

			}
			if (st.equals("true"))
			{
				iFlogin = true;
			}
			if (iFlogin)
			{
				result = 3;
				db.execSQL("update user set state=?", new Object[]
				{
						"logined"
				});
				int Identity = array.getJSONObject(0).getInt("usertype");
				Constants.user_type = "" + Identity;
				if (Identity == 1)
				{
					JSONObject info = array.getJSONObject(0).getJSONObject("userinfo");
					if (info.equals(null))
					{
						Cursor result_user = db.rawQuery("select * from user ", null);
						if (result_user.getCount() == 0)
						{
							db.execSQL("replace into user (userid,name,birthday,sex,ethnic,sfz_number,bj,qq,email,telphone,zy,bm,nj,faceaddress,sch,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
							{
									Constants.number, "", "", "", "", "", "", "", "", "", "" + "" + "" + "" + "" + "" + "" + "" + "" + "", "", "", "", "", "logined"
							});
						}
						result_user.close();
					} else
					{
						JsonHelper js = new JsonHelper(info);
						db.execSQL("replace into user (userid,name,birthday,sex,ethnic,sfz_number,bj,qq,email,telphone,zy,bm,nj,faceaddress,sch,state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
						{
								Constants.number, js.GetValue("STUDENT_NAME"), js.GetValue("BIRTHDAY"), js.GetValue("SEX_NAME"), js.GetValue("ETHNIC_ID"), js.GetValue("SFZ_NUMBER"), js.GetValue("BJ_ID"), js.GetValue("QQ"), js.GetValue("EMAIL"), js.GetValue("TELPHONO"), js.GetValue("MAJOR_NAME"), js.GetValue("DEPARTMENT_NAME"), js.GetValue("NJDM"), "", js.GetValue("SCHOOL_ID"), "logined"
						});
					}
				} else if (Identity == 2)
				{
					JSONObject info = (JSONObject) array.get(0);
					JsonHelper js = new JsonHelper(info);
					db.execSQL("replace into user (userid,name,bm,state) values(?,?,?,?)", new Object[]
					{
							js.GetDate("TEACHER_ID"), js.GetDate("TEACHER_NAME"), js.GetDate("DEPARTMENT_ID"), "logined"
					});
				} else
				{
					JSONObject info = (JSONObject) array.get(0);
					JsonHelper js = new JsonHelper(info);
					db.execSQL("replace into user (userid,name,telphone,state) values(?,?,?,?)", new Object[]
					{
							js.GetValue("Parent_ID"), js.GetValue("Parent_Name"), js.GetValue("Parent_Phone"), "logined"
					});
				}
				login_sucesses = true;
			} else
			{
				result = 2;
			}
		} catch (JSONException e)
		{
			login_sucesses = false;

		} catch (Exception e)
		{
			login_sucesses = false;

		}
		return result;
	}

	private void _login(String jsonstr)
	{
		Object[] array = null;
		JSONArray jsa = null;
		try
		{
			jsa = new JSONArray(jsonstr);
			if (jsa.getJSONObject(0).getString("islogin").equals("true"))
			{
				login_sucesses = true;
			}
			String[] zdmc = new String[]
			{
					"USERNAME", "DH", "QQ", "TXDZ", "NC", "ZYMC", "XBMC", "BMMC", "BJDM", "SR"
			};
			String result = "";
			for (int i = 0; i < jsa.getJSONObject(0).getJSONArray("userinfo").length(); i++)
			{
				String code = jsa.getJSONObject(0).getJSONArray("userinfo").get(i).toString();
				String en = new String(RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(code.getBytes("utf-8"), Base64.DEFAULT)));
				result += en.trim();
			}
			result = URLDecoder.decode(result);
			array = JsonTools.getString(new JSONObject(result), zdmc);
			for (int i = 0; i < array.length; i++)
			{
				array[i] = unicodeToUtf8(array[i].toString());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			new SqliteHelper().execSQL("delete from user");
			new SqliteHelper().execSQL("insert into user(userid,name,telphone,qq,faceaddress,nc,zy,sex,bm,bj,birthday) values('" + Constants.number + "',?,?,?,?,?,?,?,?,?,?)", array);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		try
		{
			new SqliteHelper().execSQL(String.format("insert into usertype(userid,usertype) values('%s',%s)", new Object[]
			{
					Constants.number, jsa.getJSONObject(0).getString("usertype")
			}));
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 功 能:根据登录信息判断用户登录状态:what=3登录成功,否则失败
	 * 
	 * @param result
	 * @return void
	 */
	@Override
	protected void onPostExecute(String result1)
	{

		try
		{
			if (isShowProgressDialog)
				mProgressDialog.dismiss();
			int what = 0;
			if (result1 == null || result1.equals(""))
			{
				android.os.Message msg = new android.os.Message();
				msg.what = what;
				mHandler.sendMessage(msg);
				return;
			}
			try
			{
				JSONArray jso = new JSONArray(result1);
				String islogin = jso.getJSONObject(0).getString("islogin");
				if (islogin.equals("true"))
				{
					login_sucesses = true;
					_login(result1);
					setResult(functionString);
					if (login_sucesses && function_sucesses)
						what = 1;
					else
						what = 2;
				} else
				{
					what = 2;
				}
				Message msg = new Message();
				msg.what = what;
				mHandler.sendMessage(msg);
			} catch (Exception e)
			{
				Message msg = new Message();
				msg.what = 2;
				mHandler.sendMessage(msg);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void getFunction(SQLiteDatabase db)
	{
		try
		{
			String result = "";
			String url = null;
			if (action != 3)
			{
				url = _链接地址导航.UIA.功能列表.getUrl() + "?userid=" + Constants.number + "&ticket=" + MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code) + "&Version=1";
				// url = Constants.functionurl + Constants.number + "&ticket=" +
				// MD5.MD5(_链接地址导航.addString+Constants.number + Constants.code)
				// + "&Version=1";
			} else
			{
				url = _链接地址导航.UIA.功能列表.getUrl() + "?userid=yunhuakeji&ticket=" + MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code) + "&Version=1";
			}
			HttpParams httpParameters = new BasicHttpParams();
			HttpClient hc = new DefaultHttpClient(httpParameters);
			HttpPost hp = new HttpPost(url);
			// 设置连接超时
			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
			// 设置响应超时
			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
			HttpResponse hr = null;
			try
			{
				hr = hc.execute(hp);
			} catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			int num = 0;
			try
			{
				num = hr.getStatusLine().getStatusCode();
			} catch (Exception e)
			{
				num = 500;
			}
			if (num == 200)
			{ //
				try
				{
					functionString = EntityUtils.toString(hr.getEntity());
				} catch (ParseException e)
				{

					result = "";
				} catch (IOException e)
				{

					result = "";
				}
			}
			hc.getConnectionManager().shutdown();
		} catch (Exception e)
		{

		}
	}

	private boolean islogin = true;

	private void setResult(String result)
	{
		if (!IsNull.isNotNull(result))
		{
			islogin = false;
			return;
		}
		try
		{
			JSONObject array = null;
			try
			{
				array = new JSONObject(result);
				new SqliteHelper().execSQL("delete from button");
			} catch (JSONException e1)
			{
			}
			List<Map<String, String>> functionList = new ArrayList<Map<String, String>>();
			for (int i = 0; i < array.length(); i++)
			{
				Map<String, String> function = new HashMap<String, String>();
				if (getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != null && getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != "")
				{
					function.put("function_id", getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID"));
					function.put("function_name", getFunctionDate(array, "functionrow_" + i, "FUNCTION_NAME"));
					function.put("function_type", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYPE"));
					function.put("class_name", getFunctionDate(array, "functionrow_" + i, "CLASS_NAME"));
					function.put("package_name", getFunctionDate(array, "functionrow_" + i, "PACKAGE_NAME"));
					function.put("integerate_key", new JSONArray(getFunctionDate(array, "functionrow_" + i, "INTEGRATE_KEY")).get(0).toString());
					function.put("function_face", getFunctionDate(array, "functionrow_" + i, "FUNCTION_FACE"));
					function.put("px", getFunctionDate(array, "functionrow_" + i, "PX"));
					function.put("function_tybj", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYBJ"));
					functionList.add(function);
				}
			}
			for (int i = 0; i < functionList.size(); i++)
			{
				try
				{
					new SqliteHelper().execSQL("insert into button(FunctionID , name ,type ,cls ,pkg , key  ,face ,px ,function_tybj ) values(?,?,?,?,?,?,?,?,?)", new Object[]
					{
							functionList.get(i).get("function_id"), functionList.get(i).get("function_name"), functionList.get(i).get("function_type"), functionList.get(i).get("class_name"), functionList.get(i).get("package_name"), functionList.get(i).get("integerate_key"), functionList.get(i).get("function_face"), Integer.valueOf(functionList.get(i).get("px")), functionList.get(i).get("function_tybj")
					});
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			sendmessage(3);
		} catch (Exception e)
		{
			sendmessage(4);
		}
	}

	private void sendmessage(int what)
	{
		if (what == 3)
			function_sucesses = true;
		else if (what == 4)
			function_sucesses = false;
	}

	private String getFunctionDate(JSONObject JSobject, String rownum, String ziduan)
	{
		String str = "";
		try
		{
			str = JSobject.getJSONObject(rownum).getString(ziduan);
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 
	 * @param theString
	 * @return String
	 */
	public static String unicodeToUtf8(String theString)
	{
		char aChar;
		if (theString == null)
		{
			return "";
		}
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;)
		{
			aChar = theString.charAt(x++);
			if (aChar == '\\')
			{
				aChar = theString.charAt(x++);
				if (aChar == 'u')
				{
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++)
					{
						aChar = theString.charAt(x++);
						switch (aChar)
						{
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException("Malformed encoding.");
						}
					}
					outBuffer.append((char) value);
				} else
				{
					if (aChar == 't')
						aChar = 't';
					else if (aChar == 'r')
						aChar = 'r';
					else if (aChar == 'n')
						aChar = 'n';
					else if (aChar == 'f')
						aChar = 'f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
}