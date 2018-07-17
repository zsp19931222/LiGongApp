package 云华.智慧校园.工具;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;

public class NetDataHelper extends AsyncTask<String, Integer, String>
{
	private String url;
	private Map<String, String> params;
	private OnPostExecuteListener executeListener;

	public NetDataHelper(String url, Map<String, String> params, OnPostExecuteListener executeListener)
	{
		this.url = url;
		this.params = params;
		this.executeListener = executeListener;
	}
	
	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			setParams(this.params);
			String result = null;
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(this.parames, HTTP.UTF_8));
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
		}
		return "";
	}

	private List<BasicNameValuePair> parames = new ArrayList<>();

	private void setParams(Map<String, String> params)
	{
		if (params == null)
			return;
		Set<String> keys = params.keySet();
		Iterator<String> key = keys.iterator();
		while (key.hasNext())
		{
			String keyString = key.next();

			parames.add(new BasicNameValuePair(keyString, params.get(keyString)));
		}
	}
	
	public interface OnPostExecuteListener
	{
		public void postExecute(String result);
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (executeListener != null)
		{
			executeListener.postExecute(result);
		}
	}

}
