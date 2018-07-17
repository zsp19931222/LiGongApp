package yh.app.update;

import java.util.concurrent.Executors;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import yh.app.activitytool.BreakApp;
 
import yh.app.tool.NetWork;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel.OnButtonClickLisener;

public class AppUpdateManager extends AsyncTask<String, Integer, JSONObject> {
	/************************************
	 * onPreExecute()完成后立即执行 * 用于执行较为费时的操作 * 此方法将接收输入参数和返回计算结果 *
	 ************************************/
	private Handler mHandler;
	private Button button;
	private ProgressDialog mProgressDialog;
	private Context context;

	public AppUpdateManager(Handler mHandler, Button button, ProgressDialog mProgressDialog, Context context) {
		this.context = context;
		this.button = button;
		this.mProgressDialog = mProgressDialog;
		this.mHandler = mHandler;
	}
	
//	public void excute(String... params){
//	    
//	}

	@Override
	protected JSONObject doInBackground(String... params) {
		return getUpdateJson(params[0]);
	}

	@Override
	protected void onPostExecute(JSONObject result) {
		try {
			if (result.get("update").equals(true)) {
				String url = result.get("url").toString();
				initUpdateUI(button, mProgressDialog, url, true);
			} else {
				mHandler.sendMessage(new Message());
			}
		} catch (JSONException e) {
			mHandler.sendMessage(new Message());
		} catch (Exception e) {
			mHandler.sendMessage(new Message());
		}

	}

	private JSONObject getUpdateJson(String url) {
		String result = null;
		try {
			HttpClient hc = new DefaultHttpClient();
			HttpGet hg = new HttpGet(url);
			hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 500);
			result = hc.execute(hg, new BasicResponseHandler());
			hc.getConnectionManager().shutdown();
			if (result != null && !result.equals("") && !result.equals("null")) {
				result = result.replace("\\", "");
				result = result.substring(1, result.length() - 1);
				return new JSONObject(result);
			} else
				return null;
		} catch (Exception e) {
			return null;
		}
	}

	private void initUpdateUI(Button button, final ProgressDialog mProgressDialog, final String url,
			final boolean isForceUpdate) {
		button.setVisibility(0);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// if(true)
				if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_MOBILE)
					new DiaLogOkAndCancel().buldeDialog(context, "提示", "当前为移动网络，是否继续下载", "继续下载", "取消",
							new OnButtonClickLisener() {
						@Override
						public void setButton1ClickLisener(DialogInterface dialog, int which) {
							dialog.dismiss();
							AppUpdater updater = new AppUpdater(mProgressDialog, url, context);
							updater.executeOnExecutor(Executors.newCachedThreadPool());
						}

						@Override
						public void setButton2ClickLisener(DialogInterface dialog, int which) {
							dialog.dismiss();
							if (!isForceUpdate)
								mHandler.sendMessage(new Message());
							else {
								((Activity) context).finish();
								BreakApp.closeAPP();
							}
						}

					});
				else if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_WIFI) {
					AppUpdater updater = new AppUpdater(mProgressDialog, url, context);
					updater.executeOnExecutor(Executors.newCachedThreadPool());
				}
			}
		});
	}
}
