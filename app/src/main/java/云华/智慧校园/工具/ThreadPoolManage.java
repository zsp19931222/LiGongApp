package 云华.智慧校园.工具;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;

import yh.app.logTool.Log;
import 云华.智慧校园.自定义控件.MyProgressbar;

public class ThreadPoolManage
{
	public MyProgressbar customProgressDialog;
	public static int POOL_SIZE = 16;
	private MultipartEntity multipart = new MultipartEntity();
	private List<NameValuePair> parames = new ArrayList<NameValuePair>();
	public static ExecutorService executor = Executors.newFixedThreadPool(POOL_SIZE);

	public void addCustomThread(Runnable runnable)
	{
		executor.execute(runnable);
	}

	public void addPostTask(final String url, final Map<String, String> params, final Handler handler)
	{
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				if (Constants.isNetworkAvailable(Constants.App_Context))
				{
					Message msg = new Message();
					Bundle bundle = new Bundle();
					String result = defaultDoInBackground(url, params);
					if (result == null || result.length() == 0 || result.equals("") || result.equals("null") || result.equals("[]") || result.equals("{}"))
					{
						bundle.putString("msg", result);
						msg.setData(bundle);
						handler.sendMessage(msg);
					} else
					{
						if (result.substring(0, 1).equals("\""))
							result = result.substring(1, result.length() - 1);
						result = result.replace("\\\"", "\"");
						bundle.putString("msg", result);
						msg.setData(bundle);
						handler.sendMessage(msg);
					}
				} else
				{
					Message msg = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("msg", "网络异常");
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
				closeCustomProgressDialog();
			}

		};
		addCustomThread(runnable);
	}

	public void setCustomProgressDialog(Context context)
	{
		customProgressDialog = new MyProgressbar(context, "", "");
		customProgressDialog.show();
	}

	public void closeCustomProgressDialog()
	{
		if (null != customProgressDialog)
		{
			customProgressDialog.cancel();
		}
	}

	public void addNewPostTask(final String url, final Map<String, String> params, final Handler handler)
	{
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				Message msg = new Message();
				Bundle bundle = new Bundle();
				String result = defaultDoInBackground(url, params);

				if (result == null || result.length() == 0 || result.equals("") || result.equals("null") || result.equals("[]") || result.equals("{}"))
				{
					bundle.putString("msg", "");
				}
				bundle.putString("msg", result);
				msg.setData(bundle);
				handler.sendMessage(msg);
				closeCustomProgressDialog();
			}
		};
		addCustomThread(runnable);
	}

	private void setUpLoadParams(File[] file, String[] fileName, Map<String, String> params)
	{
		if (file != null && fileName != null)
			for (int i = 0; i < file.length; i++)
			{
				try
				{
					multipart.addPart(fileName[i], new FileBody(file[i]));
				} catch (Exception e)
				{
				}
			}
		if (params != null)
		{
			Iterator<String> keys = MapTools.getMapKeys(params);
			while (keys.hasNext())
			{
				String name = keys.next();
				try
				{
					multipart.addPart(name, new StringBody(params.get(name), Charset.forName("UTF-8")));
				} catch (Exception e)
				{
				}
			}
		}
	}

	public void addUploadTask(final String url, final File[] file, final String[] fileName, final Map<String, String> params, final Handler handler)
	{
		Runnable runnable = new Runnable()
		{
			@Override
			public void run()
			{
				Message msg = new Message();
				Bundle bundle = new Bundle();
				bundle.putString("msg", upLoadFile(url, file, fileName, params));
				msg.setData(bundle);
				handler.sendMessage(msg);
				closeCustomProgressDialog();
			}
		};
		addCustomThread(runnable);
	}

	public String upLoadFile(String url, File[] file, String[] fileName, Map<String, String> params)
	{
		try
		{
			String result = null;
			HttpPost post = new HttpPost(url);
			setUpLoadParams(file, fileName, params);
			HttpClient client = new DefaultHttpClient();
			post.setEntity(multipart);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(response.getEntity());
			}
			client.getConnectionManager().shutdown();
			closeCustomProgressDialog();
			return result;
		} catch (Exception e)
		{
			return null;
		}

	}

	private String defaultDoInBackground(String url, Map<String, String> params)
	{

		try
		{

			setParams(params);
			String result = null;
			HttpClient hc = new DefaultHttpClient();
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
				Log.d("zsp",result);
			}
			if (hc != null)
			{
				hc.getConnectionManager().shutdown();
			}
			closeCustomProgressDialog();
			return result;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}

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
}