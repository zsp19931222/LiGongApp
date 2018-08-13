package yh.app.quanzi;

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
import org.json.JSONArray;
import org.json.JSONObject;
import yh.app.activitytool.ActivityPortrait;
import android.os.AsyncTask;import com.yhkj.cqgyxy.R;
import android.os.Bundle;
import android.widget.Toast;

public class ssjm extends ActivityPortrait
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quanzi_sshy);
	}

	class AT extends AsyncTask<String, Integer, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			String result = null;
			try
			{
				List<NameValuePair> parames = new ArrayList<NameValuePair>();
				String url = "";
				parames.add(new BasicNameValuePair("tj", "刘"));
				url = "";
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
			return result;
		}

		@SuppressWarnings("unused")
		@Override
		protected void onPostExecute(String result)
		{
			if (result == null || result.equals(""))
			{
				Toast.makeText(getApplicationContext(), "查无此人,请重新输入", Toast.LENGTH_SHORT).show();
				return;
			}
			result = result.replace("\\", "");
			result = result.substring(1, result.length());
			try
			{
				JSONArray jsa = new JSONArray(result);
				JSONObject jso = jsa.getJSONObject(0);
			} catch (Exception e)
			{
			}
		}
	}
}
