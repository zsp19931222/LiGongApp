package yh.app.tool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;

public class AllATSSS extends AsyncTask<String, String, String>
{
	public static int POST = 1;
	public static int GET = 2;
	private String url = "";
	private Handler handler;
	private Map<String, String> map;
	private int type;

	public AllATSSS(String url, Handler handler, Map<String, String> map, int type)
	{
		this.type = type;
		this.map = map;
		this.handler = handler;
		this.url = url;
	}

	private List<NameValuePair> parames = new ArrayList<NameValuePair>();

	@Override
	protected String doInBackground(String... params)
	{
		if (POST == type)
		{
			try
			{
				String result = null;
				HttpClient hc = new DefaultHttpClient();
				HttpPost hp = new HttpPost(url);
				cs(map);
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
			}
		} else if (GET == type)
		{
			try
			{
				return getRequest(url);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (result == null || result.equals("null") || result.equals("[]") || result.equals(""))
		{
			return;
		}
		Message msg = new Message();
		Bundle bundle = new Bundle();
		if (result.subSequence(result.length() - 1, result.length()).equals("\""))
			result = result.substring(1, result.length() - 1);
		result = result.replace("\\", "");
		bundle.putString("msg", result);
		msg.setData(bundle);
		handler.sendMessage(msg);
	}

	public void cs(Map<String, String> map)
	{
		if (map == null)
			return;
		Set<String> keys = map.keySet();
		Iterator<String> key = keys.iterator();
		while (key.hasNext())
		{
			String keyString = key.next();
			parames.add(new BasicNameValuePair(keyString, map.get(keyString)));
		}
	}

	private String getRequest(final String url) throws Exception
	{
		try
		{
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
			HttpProtocolParams.setUseExpectContinue(params, true);
			params.setParameter("userid", Constants.name);
			// 创建HttpGet对象。
			HttpGet get = new HttpGet(url);
			get.setParams(params);
			// 发送GET请求
			HttpResponse httpResponse = new DefaultHttpClient().execute(get);
			// 如果服务器成功地返回响应
			String result = "";
			if (httpResponse.getStatusLine().getStatusCode() == 200)
			{
				// 获取服务器响应字符串
				result = EntityUtils.toString(httpResponse.getEntity());
			}
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}

}
