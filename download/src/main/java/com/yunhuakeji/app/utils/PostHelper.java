package com.yunhuakeji.app.utils;

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

public class PostHelper extends AsyncTask<Object, Integer, Object>
{
	private Map<String, String> params;

	private String url;

	public PostHelper(String url, Map<String, String> params)
	{
		this.url = url;
		this.params = params;
	}

	@Override
	protected Object doInBackground(Object... arg0)
	{
		try
		{

			String result = null;
			HttpClient hc = new DefaultHttpClient();
			HttpPost hp = new HttpPost(url);
			hp.setEntity(new UrlEncodedFormEntity(setParams(params), HTTP.UTF_8));
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3 * 1000);
			hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3 * 1000);
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

	private IPostExecute execute;

	public void doPostExecute(IPostExecute execute)
	{
		this.execute = execute;
		this.execute();
	}

	@Override
	protected void onPostExecute(Object result)
	{
		execute.onPostExecute(result);
	}

	private List<BasicNameValuePair> setParams(Map<String, String> params)
	{
		if (params == null)
			return null;
		Set<String> keys = params.keySet();
		Iterator<String> key = keys.iterator();
		List<BasicNameValuePair> parames = new ArrayList<>();
		while (key.hasNext())
		{
			String keyString = key.next();
			parames.add(new BasicNameValuePair(keyString, params.get(keyString)));
		}
		return parames;
	}

	public interface IPostExecute
	{
		public void onPostExecute(Object result);
	}
}
