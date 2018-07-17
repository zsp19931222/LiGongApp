package yh.app.PostTools;

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
import yh.app.tool.MD5;

public class ResultStringAsyncTask
{
	private Handler handler;

	public ResultStringAsyncTask(Handler handler)
	{
		this.handler = handler;
	}

	class AT extends AsyncTask<String, Integer, String>
	{
		private String url = "";

		public AT(String url)
		{
			this.url = url;
		}

		@Override
		protected String doInBackground(String... params)
		{
			String result = null;
			try
			{
				List<NameValuePair> parames = new ArrayList<NameValuePair>();
				parames.add(new BasicNameValuePair("userid", Constants.number));
				parames.add(new BasicNameValuePair("password", MD5.MD5(Constants.code)));
				Constants.code = MD5.MD5(Constants.code);
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
			}
			return result;

		}

		@Override
		protected void onProgressUpdate(Integer... values)
		{
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result)
		{
			Message msg = new Message();
			Bundle bundle = new Bundle();
			result = result.replace("\\", "");
			result = result.substring(1, result.length() - 1);
			bundle.putString("msg", result);
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	}

}
