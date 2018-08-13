package yh.app.web;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.example.app3.view.MyTitleView;
import com.example.jpushdemo.helper.Receiver;
import com.example.jpushdemo.helper.Receiver.IGetMessage;
import com.yunhuakeji.app.utils.TopBarHelper;
import com.yunhuakeji.app.utils.TopBarHelper.OnClickLisener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.IsNull;

@SuppressLint("SetJavaScriptEnabled")
public class WebActivity extends Activity
{
	//推送专用web
	private ValueCallback<Uri> mUploadMessage;
	private ValueCallback<Uri[]> mUploadCallbackAboveL;
	private final static int FILECHOOSER_RESULTCODE = 1;
//	private TextView title;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == FILECHOOSER_RESULTCODE)
		{
			if (null == mUploadMessage && null == mUploadCallbackAboveL)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			if (mUploadCallbackAboveL != null)
			{
				onActivityResultAboveL(requestCode, resultCode, intent);
			} else if (mUploadMessage != null)
			{
				mUploadMessage.onReceiveValue(result);
				mUploadMessage = null;
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void onActivityResultAboveL(int requestCode, int resultCode, Intent data)
	{
		if (requestCode != FILECHOOSER_RESULTCODE || mUploadCallbackAboveL == null)
		{
			return;
		}
		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK)
		{
			if (data == null)
			{
			} else
			{
				String dataString = data.getDataString();
				ClipData clipData = data.getClipData();

				if (clipData != null)
				{
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++)
					{
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}
				if (dataString != null)
					results = new Uri[]
					{
							Uri.parse(dataString)
					};
			}
		}
		mUploadCallbackAboveL.onReceiveValue(results);
		mUploadCallbackAboveL = null;
		return;
	}

	private WebView webview;
	private MyTitleView titleView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		Intent intent = getIntent();
		final String id = intent.getStringExtra("id");
		final String userid = Constants.number;
		final String ticket = intent.getStringExtra("ticket");

//		initTopBar();
		initView();
		setWebView();

		if (id == null || id.isEmpty())
		{
			Toast.makeText(getContext(), "消息获取失败", Toast.LENGTH_SHORT).show();
		} else
		{
			String n_url = getUrl(id);
			if (n_url == null || n_url.isEmpty())
			{
				new Receiver().getMessageByID(getContext(), buildJsonObject(id, userid, ticket), new IGetMessage()
				{
					@Override
					public void callBack(JSONObject data)
					{
						try
						{
							
							String n_url = getUrl(id);
							if (IsNull.isNotNull(n_url))
							{
								webview.loadUrl(n_url);
								return;
							}
							Toast.makeText(getContext(), "消息获取失败", Toast.LENGTH_SHORT).show();
						} catch (Exception e)
						{
							Toast.makeText(getContext(), "消息获取失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
			} else
			{
				webview.loadUrl(n_url);
			}
		}
		new SqliteHelper().execSQL("update client_notice set read='true' where userid=? and n_id=?", Constants.number,id);
		new SqliteHelper().execSQL("update client_notice_messagelist set read='true' where userid=? and n_id=?", Constants.number,id);
	}

	private JSONObject buildJsonObject(String id, String userid, String ticket)
	{
		try
		{
			return new JSONObject().accumulate("id", id).accumulate("userid", userid).accumulate("ticket", ticket).accumulate("code", "102");
		} catch (Exception e)
		{
			return new JSONObject();
		}
	}

	private String getUrl(String id)
	{
		try
		{
			return new SqliteHelper().rawQuery("select n_url from client_notice where n_id=?", id).get(0).get("n_url");
		} catch (Exception e)
		{
			return "";
		}
	}

	private TopBarHelper tbh;

	private void initTopBar()
	{
		tbh = new TopBarHelper(this, findViewById(R.id.layout_web)).setTitle("").setOnClickLisener(new OnClickLisener()
		{
			@Override
			public void setRightOnClick()
			{
				finish();
			}

			@Override
			public void setLeftOnClick()
			{
				if (webview.canGoBack())
				{
					webview.goBack();
				} else
				{
					CloseActivity();
				}
			}

			@Override
			public void setExtraOnclick()
			{
			}
		});
	}

	private void setWebView()
	{
		WebSettings settings = webview.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setBlockNetworkImage(false);
		settings.setSupportZoom(true);
		settings.setUseWideViewPort(true);// 设定支持viewport
		settings.setLoadWithOverviewMode(true);
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);// 设定支持缩放
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setAppCacheEnabled(true);
		webview.setDownloadListener(new MyWebViewDownLoadListener(this));
		webview.setWebViewClient(new WebViewClient()
		{
			// 点击超链接的时候重新在原来进程上加载URL
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{

				if (url.startsWith("tel:"))
				{
					try
					{
						Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
						startActivity(intent);
					} catch (Exception e)
					{
						Toast.makeText(getApplicationContext(), "该联系方式匹配失败！！！", Toast.LENGTH_SHORT).show();
					}
					return true;
				} else if (url.startsWith("map:"))
				{
					// 地图webAPI
					return true;
				} else
				{
					view.loadUrl(url);
					return false;
				}

			};

			@Override
			public void onPageFinished(WebView view, String url)
			{
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		webview.setWebChromeClient(new WebChromeClient()
		{
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title)
			{
				super.onReceivedTitle(view, title);
				titleView.setTitle(title,getContext());
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg)
			{
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				WebActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
			{
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				WebActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
			{
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				WebActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), WebActivity.FILECHOOSER_RESULTCODE);
			}

			// For Android 5.0+
			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
			{
				mUploadCallbackAboveL = filePathCallback;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				WebActivity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
				return true;
			}
		});
	}

	private void initView()
	{
		webview = (WebView) findViewById(R.id.web_webview);
//		title = (TextView) findViewById(R.id.topbar_title);
		titleView= (MyTitleView) findViewById(R.id.web_title);
		titleView.setLeftListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (webview.canGoBack())
				{
					webview.goBack();
				} else
				{
					CloseActivity();
				}
			}
		});
	}

//	public void setTvTitle(String title)
//	{
//		getTvTitle().setText(title);
//	}
//
//	private TextView getTvTitle()
//	{
//		return title;
//	}

	private void CloseActivity()
	{
		finish();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
		webview.destroy();
//		((LinearLayout) findViewById(R.id.parent)).removeAllViews();
	}

	private Context getContext()
	{
		return this;
	}

}
