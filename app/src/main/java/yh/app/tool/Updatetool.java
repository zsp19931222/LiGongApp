package yh.app.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import yh.app.appstart.lg.R;
/**
 * 
 * 包 名:yh.app.tool 类 名:Updatetool.java 功 能:升级程序处理
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class Updatetool
{

	private Context mContext;
	// 提示语
	private String updateMsg = "有最新的软件包哦，亲快下载吧~";

	// 返回的安装包url
	private String apkUrl = "";

	private Dialog noticeDialog;

	private Dialog downloadDialog;
	/* 下载包安装路径 */
	private static final String savePath = "/sdcard/updateyhszxy/";

	private static final String saveFileName = savePath + "UpdateyhszxyRelease.apk";

	/* 进度条与通知ui刷新的handler和msg常量 */
	private ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private int progress;

	private Thread downLoadThread;

	private boolean interceptFlag = false;

	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:
				downloadDialog.dismiss();
				installApk();
				break;
			default:
				break;
			}
		};
	};

	public Updatetool(Context context, String posturl)
	{
		this.mContext = context;
		apkUrl = posturl;
	}

	// 外部接口让主Activity调用
	public void checkUpdateInfo()
	{
		// showNoticeDialog();
		String path = CheckVersion();
	}

	private String CheckVersion()
	{
		HttpPost request = new HttpPost(apkUrl);
		List<NameValuePair> parames = new ArrayList<NameValuePair>();
		parames.add(new BasicNameValuePair("userName", getVersion()));

		HttpParams httpParameters = new BasicHttpParams();
		HttpClient hc = new DefaultHttpClient(httpParameters);

		try
		{
			request.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			HttpConnectionParams.setConnectionTimeout(httpParameters, 3 * 1000);
			request.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
			// 设置连接超时
			HttpConnectionParams.setConnectionTimeout(httpParameters, 3 * 1000);
			// 设置响应超时
			HttpConnectionParams.setSoTimeout(httpParameters, 5 * 1000);
			try
			{
				HttpResponse hr = hc.execute(request);
				String result = null;
				if (hr.getStatusLine().getStatusCode() == 200)
				{ //
					result = EntityUtils.toString(hr.getEntity());
				}
				if (hc != null)
				{
					hc.getConnectionManager().shutdown();
				}
				return result;
			} catch (ClientProtocolException e)
			{
				return "";
			} catch (IOException e)
			{
				return "";
			}
		} catch (UnsupportedEncodingException e)
		{
			return "";
		}
	}

	private void showNoticeDialog()
	{
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(updateMsg);
		builder.setPositiveButton("下载", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// dialog.dismiss();
				showDownloadDialog();
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		noticeDialog = builder.create();
		noticeDialog.show();
	}

	private void showDownloadDialog()
	{
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");

		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		downloadApk();
	}

	private Runnable mdownApkRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();

				File file = new File(savePath);
				if (!file.exists())
				{
					file.mkdir();
				}
				String apkFile = saveFileName;
				File ApkFile = new File(apkFile);
				FileOutputStream fos = new FileOutputStream(ApkFile);

				int count = 0;
				byte buf[] = new byte[1024];

				do
				{
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0)
					{
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

				fos.close();
				is.close();
			} catch (MalformedURLException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	};

	/**
	 * 下载apk
	 * 
	 */

	private void downloadApk()
	{
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * 安装apk
	 * 
	 */
	private void installApk()
	{
		File apkfile = new File(saveFileName);
		if (!apkfile.exists())
		{
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		mContext.startActivity(i);

	}

	public String getVersion()
	{
		try
		{
			PackageManager manager = this.mContext.getPackageManager();
			PackageInfo info = manager.getPackageInfo(this.mContext.getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
	}
}