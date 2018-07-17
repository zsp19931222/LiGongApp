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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;
public class SSHYAT extends AsyncTask<String, Integer, String>
{
	private Handler handler;
	public SSHYAT(Handler handler)
	{
		this.handler = handler;
	}
	@Override
	protected String doInBackground(String... params)
	{
		String result = null;
		try
		{
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			String url = "";
			parames.add(new BasicNameValuePair("userid", Constants.number));
			parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150120")));
			parames.add(new BasicNameValuePair("function_id", "20150120"));
			parames.add(new BasicNameValuePair("tj", params[0]));
			parames.add(new BasicNameValuePair("pagenum", params[1]));
			parames.add(new BasicNameValuePair("pagesize", "10"));
			url ="";
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
		// result = s;
		return result;
	}
	@Override
	protected void onPostExecute(String result)
	{
		Message msg = new Message();
		if (result == null || result.equals("") || result.equals("null") || result.equals("\"[]\""))
		{
			msg.what = 0;
			handler.sendMessage(msg);
			return;
		}
		result = result.replace("\\", "");
		result = result.substring(1, result.length());
		try
		{
			Bundle b = new Bundle();
			b.putString("sshylb", result);
			msg.what = 1;
			msg.setData(b);
			handler.sendMessage(msg);
		} catch (Exception e)
		{
		}
	}
}