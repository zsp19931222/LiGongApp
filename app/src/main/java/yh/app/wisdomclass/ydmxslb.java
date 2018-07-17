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
import yh.app.tool.Ticket;
import 云华.智慧校园.工具._链接地址导航;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
public class ydmxslb extends AsyncTask<String, String, String>
{
	Handler handler;
	public ydmxslb(Handler handler)
	{
		this.handler = handler;
	}
	@Override
	protected String doInBackground(String... params)
	{
		String result = "";
		String url = null;
		List<NameValuePair> parames = null;
		url = _链接地址导航.DC.已点名学生列表.getUrl();
//		url = Constants.ydmxslb;
		parames = new ArrayList<NameValuePair>();
		parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket("20150116")));
		parames.add(new BasicNameValuePair("function_id", "20150116"));
		parames.add(new BasicNameValuePair("xkkh", params[0]));
		parames.add(new BasicNameValuePair("jsbh", params[1]));
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
			e.printStackTrace();
			return result;
		}
	}
	@Override
	protected void onPostExecute(String result)
	{
		Message msg = new Message();
		if (result == null || result.equals("") || result.equals("null"))
		{
			msg.what = 0;
			handler.sendMessage(msg);
			return;
		}
		result = result.replace("\\", "");
		result = result.substring(1, result.length() - 1);
		msg.what = 1;
		Bundle b = new Bundle();
		b.putString("ydmxslb", result);
		msg.setData(b);
		handler.sendMessage(msg);
	}
}
