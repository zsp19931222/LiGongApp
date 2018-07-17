//package yh.app.tool;
//
//import java.util.ArrayList;
//import java.util.List;
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
//import android.os.Handler;
//
//public class DM extends AsyncTask<String, Integer, String>
//{
//
//	private int action = 2;
//	private Handler mHandler = null;
//	private String xkkh = null;
//	private String djz = null;
//	private String djj = null;
//	private String xqj = null;
//	private String xh = null;
//
//	// 学生点名构造函数
//	public DM(String xh,String xkkh, String djz, String djj, String xqj, int action, Handler mHandler)
//	{
//		this.xh = xh;
//		this.xkkh = xkkh;
//		this.djz = djz;
//		this.djj = djj;
//		this.xqj = xqj;
//		this.action = action;
//		this.mHandler = mHandler;
//	}
//
//	// 老是发起点名构造函数
//	public DM(String xkkh, String djz, String djj, String xqj, int action, Handler mHandler)
//	{
//		this.xkkh = xkkh;
//		this.djz = djz;
//		this.djj = djj;
//		this.xqj = xqj;
//		this.action = action;
//		this.mHandler = mHandler;
//	}
//
//	@Override
//	protected String doInBackground(String... params)
//	{
//		// TODO Auto-generated method stub
//		String result = null;
//		try
//		{
//			HttpClient hc = null;
//			HttpParams httpParameters = new BasicHttpParams();
//			hc = new DefaultHttpClient(httpParameters);
//			HttpPost hp;
//			if (action == 1)
//			{
//				hp = new HttpPost(Constants.fqdmurl);
//				hp.setEntity(new UrlEncodedFormEntity(fqdm(), HTTP.UTF_8));
//			} else
//			{
//				hp = new HttpPost(Constants.xsdmurl);
//				hp.setEntity(new UrlEncodedFormEntity(xsdm(), HTTP.UTF_8));
//			}
//			// 设置连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//			// 设置响应超时
//			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//			HttpResponse hr = hc.execute(hp);
//
//			if (hr.getStatusLine().getStatusCode() == 200)
//			{
//				result = EntityUtils.toString(hr.getEntity());
//			}
//			hc.getConnectionManager().shutdown();
//			return result;
//		} catch (Exception e)
//		{
//			// TODO: handle exception
//			return null;
//		}
//	}
//
//	public List<NameValuePair> fqdm()
//	{
//		List<NameValuePair> parames = new ArrayList<NameValuePair>();
//		parames.add(new BasicNameValuePair("xkkh", xkkh));
//		parames.add(new BasicNameValuePair("djz", djz));
//		parames.add(new BasicNameValuePair("djj", djj));
//		parames.add(new BasicNameValuePair("xqj", xqj));
//		return parames;
//	}
//
//	public List<NameValuePair> xsdm()
//	{
//		List<NameValuePair> parames = new ArrayList<NameValuePair>();
//		parames.add(new BasicNameValuePair("xh", xh));
//		parames.add(new BasicNameValuePair("xkkh", xkkh));
//		parames.add(new BasicNameValuePair("djz", djz));
//		parames.add(new BasicNameValuePair("djj", djj));
//		parames.add(new BasicNameValuePair("xqj", xqj));
//		return parames;
//	}
//
//	@Override
//	protected void onPostExecute(String result)
//	{
//		int what = 0;
//		android.os.Message msg = new android.os.Message();
//		if (result == null || result.equals(""))
//		{
//			msg.what = what;
//			mHandler.sendMessage(msg);
//			return;
//		}
//
//		if (action == 2)
//		{
//			result = result.replace("\\", "");
//			if (result.equals("true"))
//			{
//				msg.what = 2;
//				mHandler.sendMessage(msg);
//			}
//			else 
//			{
//				msg.what = what;
//				mHandler.sendMessage(msg);
//			}
//		}
//	}
//}
