package yh.app.tool;

import java.io.File;
import java.nio.charset.Charset;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import org.androidpn.push.Constants;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具._链接地址导航;

public class SettingTools extends AsyncTask<String, Void, String>
{
	private File file;
	private Handler handler;
	private Context context;

	public SettingTools(File file, Handler handler, Context context)
	{
		this.context = context;
		this.file = file;
		this.handler = handler;
	}

	private ProgressDialog mProgressDialog;

	@Override
	protected void onPreExecute()
	{
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
			}
		});
		mProgressDialog.show();
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			String result = null;
			// HttpPost post = new
			// HttpPost("http://202.202.144.194:8080/UIA/user/updateuser.action");
			HttpPost post = new HttpPost(_链接地址导航.UIA.修改资料.getUrl());
			MultipartEntity multipart = new MultipartEntity();
			multipart.addPart("userid", new StringBody(Constants.number, Charset.forName("UTF-8")));
			multipart.addPart("ticket", new StringBody(MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code), Charset.forName("UTF-8")));
//			multipart.addPart("nc", new StringBody(params[0], Charset.forName("UTF-8")));
//			multipart.addPart("sr", new StringBody("", Charset.forName("UTF-8")));
//			multipart.addPart("dh", new StringBody(params[1], Charset.forName("UTF-8")));
//			multipart.addPart("qq", new StringBody(params[2], Charset.forName("UTF-8")));
			try
			{
				multipart.addPart("userface", new FileBody(file));
			} catch (Exception e)
			{
			}
			HttpClient client = new DefaultHttpClient();
			post.setEntity(multipart);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200)
			{
				result = EntityUtils.toString(response.getEntity());
			}
			client.getConnectionManager().shutdown();
			return result;
		} catch (Exception e)
		{
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result)
	{
		if (IsNull.isNotNull(result))
		{
			if (mProgressDialog != null)
				mProgressDialog.cancel();
			Message msg = new Message();
			result = result.substring(1, result.length() - 1);
			result = result.replace("\\", "");
			Bundle b = new Bundle();
			b.putString("msg", result);
			msg.setData(b);
			handler.handleMessage(msg);
		}
	}

}

// package yh.app.tool;
//
// import java.io.File;
// import java.nio.charset.Charset;
// import org.androidpn.push.Constants;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.entity.mime.MultipartEntity;
// import org.apache.http.entity.mime.content.FileBody;
// import org.apache.http.entity.mime.content.StringBody;
// import org.apache.http.impl.client.DefaultHttpClient;
// import org.apache.http.util.EntityUtils;
//
// import android.app.ProgressDialog;
// import android.content.Context;
// import android.content.DialogInterface;
// import android.os.Bundle;
// import android.os.Handler;
// import android.os.Message;
// import 云华.智慧校园.工具.IsNull;
// import 云华.智慧校园.工具._链接地址导航;
//
// public class SettingTools
// {
// private File file;
// private Handler handler;
// private Context context;
//
// public SettingTools(File file, Handler handler, Context context)
// {
// this.context = context;
// this.file = file;
// this.handler = handler;
// }
//
// private String result;
//
// public void execute(final String... params)
// {
// new Thread(new Runnable()
// {
// @Override
// public void run()
// {
// onPreExecute();
// result = doInBackground(params);
// try
// {
// send.sendMessage(new Message());
// } catch (Exception e)
// {
// e.printStackTrace();
// }
// }
// }).start();
// }
//
// private Handler send = new Handler()
// {
// @Override
// public void handleMessage(Message msg)
// {
// if (mProgressDialog != null)
// mProgressDialog.cancel();
// onPostExecute(result);
// };
// };
//
// private ProgressDialog mProgressDialog;
//
// // @Override
// protected void onPreExecute()
// {
// mProgressDialog = new ProgressDialog(context);
// mProgressDialog.setCancelable(true);
// mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
// {
// @Override
// public void onCancel(DialogInterface dialog)
// {
// }
// });
// mProgressDialog.show();
// }
//
// // @Override
// protected String doInBackground(String... params)
// {
// try
// {
// String result = null;
// // HttpPost post = new
// // HttpPost("http://202.202.144.194:8080/UIA/user/updateuser.action");
// HttpPost post = new HttpPost(_链接地址导航.UIA.修改资料.getUrl());
// MultipartEntity multipart = new MultipartEntity();
// multipart.addPart("userid", new StringBody(Constants.number,
// Charset.forName("UTF-8")));
// multipart.addPart("ticket", new StringBody(MD5.MD5(_链接地址导航.addString +
// Constants.number + Constants.code), Charset.forName("UTF-8")));
// multipart.addPart("nc", new StringBody(params[0], Charset.forName("UTF-8")));
// multipart.addPart("sr", new StringBody("", Charset.forName("UTF-8")));
// multipart.addPart("dh", new StringBody(params[1], Charset.forName("UTF-8")));
// multipart.addPart("qq", new StringBody(params[2], Charset.forName("UTF-8")));
// try
// {
// multipart.addPart("userface", new FileBody(file));
// } catch (Exception e)
// {
// }
// HttpClient client = new DefaultHttpClient();
// post.setEntity(multipart);
// HttpResponse response = client.execute(post);
// if (response.getStatusLine().getStatusCode() == 200)
// {
// result = EntityUtils.toString(response.getEntity());
// }
// client.getConnectionManager().shutdown();
// return result;
// } catch (Exception e)
// {
// return null;
// }
// }
//
// // @Override
// protected void onPostExecute(String result)
// {
// if (IsNull.isNotNull(result))
// {
// Message msg = new Message();
// result = result.substring(1, result.length() - 1);
// result = result.replace("\\", "");
// Bundle b = new Bundle();
// b.putString("msg", result);
// msg.setData(b);
// handler.handleMessage(msg);
// }
// }
//
// }
