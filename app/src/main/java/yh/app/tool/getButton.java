//package yh.app.tool;
//
//import java.io.IOException;
//import yh.app.db.MyImdb;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.androidpn.push.Constants;
//import org.apache.http.HttpResponse;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import android.database.sqlite.SQLiteDatabase;
//import android.os.AsyncTask;
//import android.os.Handler;
//
//public class getButton extends AsyncTask<String, Integer, String>
//{
//
//	private Handler mhandler = null;
//
//	public getButton(Handler mhandler)
//	{
//		this.mhandler = mhandler;
//	}
//
//	@Override
//	protected String doInBackground(String... params)
//	{
//		// TODO Auto-generated method stub
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		getFunction(db);
//		return null;
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
//			getFunction(db);
//			db.close();
//		}
//		android.os.Message msg = new android.os.Message();
//		msg.what = what;
//		mhandler.sendMessage(msg);
//	}
//
//	private void getFunction(SQLiteDatabase db)
//	{
//		String result = "";
//		String ticket = MD5.MD5(_链接地址导航.addString+"11108990938" + MD5.MD5("178716"));
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
//			db.execSQL("replace into button values(?,?,?,?,?,?,?)", new Object[] { functionList.get(i).get("function_id"), functionList.get(i).get("function_name"), functionList.get(i).get("function_type"), functionList.get(i).get("class_name"), functionList.get(i).get("package_name"), functionList.get(i).get("integerate_key"), functionList.get(i).get("function_face") });
//		}
//		db.execSQL("delete button where " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=? or " + "function_id=?", new String[] { "20150108", "20150114", "20150115", "20150117", "20150118", "20150119", "20150120", "20150122", "20150123" });
//	}
//
//	// private String getSql(int num){
//	// String sql = "";
//	// return sql;
//	// }
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
//}
