package yh.app.quanzitool;

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
import org.json.JSONObject;

import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具._链接地址导航.DC;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

public class mrfzAT extends AsyncTask<String, Integer, String>
{
	private Handler handler;

	public mrfzAT(Handler handler)
	{
		this.handler = handler;
	}

	@Override
	protected String doInBackground(String... params)
	{
		String url = DC.圈子默认列表.getUrl();
		String result = null;
		try
		{
			HttpClient hc = new DefaultHttpClient();
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("userid", Constants.number));
			parames.add(new BasicNameValuePair("function_id", "20150120"));
			parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150120")));
			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			// 连接超时
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
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
			return result;
		}
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (result == null || result.equals(""))
		{
			return;
		}
		result = result.replace("\\", "");
		result = result.substring(1, result.length() - 1);
		try
		{
			Message msg = new Message();
			new SqliteHelper().execSQL("delete from mrfz");
			JSONArray jsa = new JSONArray(result);
			for (int i = 0; i < jsa.length(); i++)
			{
				JSONObject jso = jsa.getJSONObject(i);
				new SqliteHelper().execSQL("replace into mrfz values(?,?,?,?)", new Object[]
				{
						jso.getString("ID"), jso.getString("NAME"), pinyin.getSpells(jso.getString("NAME").substring(0, 1)), Constants.number
				});
			}
			msg.what = 1;
			handler.handleMessage(msg);
		} catch (Exception e)
		{
			Message msg = new Message();
			msg.what = 0;
			handler.handleMessage(msg);
		}
	}
}