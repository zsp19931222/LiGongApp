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
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class sftjhyAT extends AsyncTask<String, Integer, String>
{
	private Context mContext;

	public sftjhyAT(Context mContext)
	{
		this.mContext = mContext;
	}

	@Override
	protected String doInBackground(String... params)
	{
		String result = null;
		String url = "";
		try
		{
			HttpClient hc = new DefaultHttpClient();
			List<NameValuePair> parames = new ArrayList<NameValuePair>();
			parames.add(new BasicNameValuePair("fqr", params[0]));
			parames.add(new BasicNameValuePair("jsr", params[1]));
			parames.add(new BasicNameValuePair("sfty", params[2]));
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

		if (result == null || result.equals("") || result.equals("null"))
		{
			((Activity) mContext).finish();
			Toast.makeText(mContext, "发送失败,请重试", Toast.LENGTH_SHORT).show();
			return;
		}

	}
}
