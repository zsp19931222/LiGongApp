package yh.app.tool;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;
import 云华.智慧校园.工具._链接地址导航;

public class MAP_AT extends AsyncTask<String, String, String>
{
	private Handler handler;

	public MAP_AT(Handler handler)
	{
		this.handler = handler;
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
			parames.add(new BasicNameValuePair("function_id", "20150124"));
			parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150124")));
			HttpPost hp = new HttpPost(_链接地址导航.DC.校内地图.getUrl());
			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10 * 1000);
			// 读取超时6
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
		if (result == null || result.equals("null") || result.equals(""))
		{
			return;
		}
//		result = result.replace("\\", "");
//		result = result.substring(1, result.length() - 1);
		try
		{
			Bundle b = new Bundle();
			b.putString("date", result);
			Message msg = new Message();
			msg.setData(b);
			msg.what = 1;
			handler.sendMessage(msg);
		} catch (Exception e)
		{
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	}
}
