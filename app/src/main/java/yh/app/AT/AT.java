//package yh.app.AT;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.androidpn.push.Constants;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpConnectionParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import 云华.智慧校园.工具._链接地址导航;
//
//public class AT extends AsyncTask<String, Integer, String>
//{
//	private Handler handler;
//	private Map<String, String> params;
//
//	public AT(Handler handler, Map<String, String> params)
//	{
//		this.handler = handler;
//		this.params = params;
//	}
//
//	@Override
//	protected String doInBackground(String... params)
//	{
//		String result = null;
//		try
//		{
//			List<NameValuePair> parames = new ArrayList<NameValuePair>();
//			String url = "";
//			Object[][] obj = doMapToObjArray(this.params);
//			for (int i = 0; i < params.length; i++)
//			{
//				parames.add(new BasicNameValuePair(obj[i][0].toString(), obj[i][1].toString()));
//			}
//			url = _链接地址导航.UIA.登录.getUrl();
////			url = Constants.loginurl;
//			HttpClient hc = null;
//			HttpParams httpParameters = new BasicHttpParams();
//			hc = new DefaultHttpClient(httpParameters);
//			HttpPost hp = new HttpPost(url);
//			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
//			// 设置连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//			// 设置响应超时
//			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//			HttpResponse hr = hc.execute(hp);
//			if (hr.getStatusLine().getStatusCode() == 200)
//			{
//				result = EntityUtils.toString(hr.getEntity());
//			}
//			hc.getConnectionManager().shutdown();
//		} catch (Exception e)
//		{
//		}
//		return result;
//	}
//
//	@Override
//	protected void onPostExecute(String result)
//	{
//		if (null == result || "".equals(result) || "null".equals(result))
//		{
//			return;
//		}
//		Bundle bundle = new Bundle();
//		result = result.replace("\\", "");
//		result = result.substring(1, result.length() - 1);
//		bundle.putString("msg", result);
//		Message msg = new Message();
//		msg.setData(bundle);
//		handler.sendMessage(msg);
//	}
//
//	private Object[][] doMapToObjArray(Map<String, String> map)
//	{
//		Object[][] obj = new Object[map.size()][2];
//		Set<String> key = map.keySet();
//		Iterator<String> it = key.iterator();
//		for (int i = 0; it.hasNext(); i++)
//		{
//			// 设置key
//			obj[i][0] = it.next();
//			// 设置value
//			obj[i][1] = map.get(obj[i][0]);
//		}
//		return obj;
//
//	}
//}