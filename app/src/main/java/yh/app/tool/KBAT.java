package yh.app.tool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;

import org.androidpn.push.Constants;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具._链接地址导航;

@SuppressWarnings("unused")
public class KBAT extends AsyncTask<String, Integer, String>
{
	private String XN = "";
	private int XQ;
	private Handler mHandler;
    private LoadDiaog diaog;
	private Context context;

	public boolean setAction(int action)
	{
		return true;
	}

	public KBAT(String number, String xN, int xQ, Handler mhHandler,Context context)
	{
		this.XN = xN;
		this.XQ = xQ;
		this.mHandler = mhHandler;
		this.context = context;
		diaog=new LoadDiaog(context);
		diaog.setTitle("课表加载中...");
		diaog.show();
	}

	@Override
	protected void onPreExecute()
	{
//		if (c != null)
//			c.show();
	}

	@Override
	protected String doInBackground(String... params)
	{
		String result = null;
		try
		{
			HttpClient hc = new DefaultHttpClient();
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("userid", Constants.number));
			parames.add(new BasicNameValuePair("xn", XN));
			parames.add(new BasicNameValuePair("xq", String.valueOf(XQ)));
			parames.add(new BasicNameValuePair("function_id", "20150105"));
			parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150105")));
			HttpPost hp = new HttpPost(_链接地址导航.DC.课表查询.getUrl());
			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			// 读取超时
			hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10 * 1000);
			HttpResponse hr = hc.execute(hp);
			if (hr.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(hr.getEntity());
			}
			if (hc != null)
			{
				hc.getConnectionManager().shutdown();
			}
			if (diaog.isShowing()) {
				diaog.dismiss();
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

	@Override
	protected void onPostExecute(String result)
	{
		if (result == null || result.equals("null") || result.equals(""))
		{
			if (diaog.isShowing()) {
				diaog.dismiss();
			}
//			if (c != null)
//				c.cancel();
			return;
		}
		try
		{
			String backlogJsonStrTmp = result.replace("\\", "");
			String jsonstr = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
			HashMap<String, Integer> colors = new HashMap<String, Integer>();
			int currentcolor = 0;
			JSONArray array = null;
			SQLiteDatabase db = new SqliteHelper().getWrite();
			if (result != null)
			{
				db.execSQL("delete from KC where XH=? and XN=? and XQ=?", new Object[]
				{
						Constants.number, XN, XQ
				});
			}
			try
			{
				array = new JSONArray(jsonstr);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			for (int i = 0; i < array.length(); i++)
			{
				try
				{
					int color = 0;
					JSONObject temp = (JSONObject) array.get(i);
					if (colors.get(temp.getString("XKKH")) == null)
					{
						color = currentcolor++;
						colors.put(temp.getString("XKKH"), color);
					} else
					{
						color = colors.get(temp.getString("XKKH"));
					}
					Object[] o = new Object[]
					{
							temp.getString("XKKH"), Constants.number, XN, XQ, temp.getString("JSXM"), JsonTools.getString(temp, new String[]
							{
									"JSMC"
							})[0], temp.getString("KCMC"), temp.getInt("XQJ"), temp.getInt("KSSJ"), temp.getInt("JSSJ"), temp.getString("DSZ"), temp.getInt("QSZ"), temp.getInt("JSZ"), color
					};
					db.execSQL("insert into KC(KCID,XH,XN,XQ,JSXM,JSMC,COURSE,XQJ,SJD,JSSJD,DSZ,QSZ,JSZ,COLOR) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)", o);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
			db.close();
			android.os.Message msg = new android.os.Message();
			if (mHandler != null)
			{
				msg.what = 1;
				mHandler.sendMessage(msg);
			}
		} catch (Exception e)
		{
			return;
		}
	}

	private String getDate(JSONObject jso, String name)
	{
		try
		{
			return jso.getString(name) != null ? jso.getString(name) : "暂无教室信息";
		} catch (Exception e)
		{
			return "暂无教室信息";
		}
	}

	@Override
	protected void onCancelled()
	{
	}
}
