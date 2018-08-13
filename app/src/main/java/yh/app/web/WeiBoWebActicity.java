package yh.app.web;

import java.io.File;

import com.example.app4.tool.UserMessageTool;
import com.example.jpushdemo.ExampleApplication;
import com.yunhuakeji.app.utils.FileHelper;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;
import yh.app.contacts.UserDetail;import com.yhkj.cqgyxy.R;
import yh.app.notification.Notification1;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MessageDataBaseFresh;
import 云华.智慧校园.工具._功能跳转;
import 云华.智慧校园.工具._空白填页;

@SuppressLint("SetJavaScriptEnabled")
@SuppressWarnings("deprecation")
public class WeiBoWebActicity extends ActivityPortrait
{
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private final static int FILECHOOSER_RESULTCODE = 1;
    private static Context context;
    private TextView title;
    static WebView webview;
    private ProgressBar web_bar;
    public final static String WBFileName = "cookieWeiBo";

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

	super.onCreate(savedInstanceState);
	setContentView(R.layout.web);

	initTopBar();
	initView();
	setWebView();
	if (_功能跳转.weiboURL.equals(getIntent().getStringExtra("url")))
	{
	    String cookieString = FileHelper.getFileContent(new File(WeiBoWebActicity.this.getFilesDir().getPath(), WBFileName + UserMessageTool.getUserId()));
	    CookieManager.getInstance().removeAllCookie();
	    if (IsNull.isNotNull(cookieString))
	    {
		String[] cookieStrings = cookieString.split(";");
		for (int i = 0; i < cookieStrings.length; i++)
		{
		    ExampleApplication.cookieManager.setCookie(getIntent().getStringExtra("url"), cookieStrings[i]);
		}

	    }
	}
	webview.loadUrl(getIntent().getStringExtra("url"));
    }

    private TopBarHelper tbh;

    private void initTopBar()
    {
	tbh = new TopBarHelper(this, findViewById(R.id.layout_web)).setTitle("微博").setOnClickLisener(new OnClickLisener()
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
		    webview.goBack();
		else
		    CloseActivity();
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
	    @Override
	    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
	    {
		// TODO Auto-generated method stub
		handler.proceed(); // 接受所有网站的证书
	    }

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
	    public WebResourceResponse shouldInterceptRequest(WebView view, String url)
	    {
		// TODO Auto-generated method stub
		if (url.startsWith("tel:"))
		{

		}
		return super.shouldInterceptRequest(view, url);
	    }

	    @Override
	    public void onPageFinished(WebView view, String url)
	    {
		// TODO Auto-generated method stub
		if (_功能跳转.weiboURL.equals(url))
		{
		    String cookieString = ExampleApplication.cookieManager.getCookie(url);
		    File file = FileHelper.createFile(WeiBoWebActicity.this.getFilesDir().getPath(), WBFileName + UserMessageTool.getUserId());
		    FileHelper.write(file, cookieString);
		}
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
		new _空白填页().addView(context, findViewById(R.id.parent), (ViewGroup) findViewById(R.id.layout_web), R.drawable.no_network, "网络堵车了");
	    }
	});
	webview.setWebChromeClient(new WebChromeClient()
	{
	    @Override
	    public void onShowCustomView(View view, CustomViewCallback callback)
	    {
		// TODO Auto-generated method stub
		super.onShowCustomView(view, callback);
	    }

	    @Override
	    public void onProgressChanged(WebView view, int newProgress)
	    {
		// 进度条
		if (newProgress == 100)
		{
		    web_bar.setVisibility(View.GONE);
		} else
		{
		    if (View.GONE == web_bar.getVisibility())
		    {
			web_bar.setVisibility(View.VISIBLE);
		    }
		}
		web_bar.setProgress(newProgress + 5);
		super.onProgressChanged(view, newProgress);
	    }

	    @Override
	    public void onReceivedTitle(WebView view, String title)
	    {
		// TODO Auto-generated method stub
		super.onReceivedTitle(view, title);
		if (title.contains("/") || title.contains("?") || title.contains("&"))
		    tbh.setTitle(Ticket.getFunctionName(getIntent().getStringExtra("function_id")));
		else
		    tbh.setTitle(title);
	    }

	    @SuppressWarnings("unused")
	    public void openFileChooser(ValueCallback<Uri> uploadMsg)
	    {
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		WeiBoWebActicity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
	    }

	    @SuppressWarnings("unused")
	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
	    {
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		WeiBoWebActicity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
	    }

	    @SuppressWarnings("unused")
	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
	    {
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		WeiBoWebActicity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), WeiBoWebActicity.FILECHOOSER_RESULTCODE);
	    }

	    // For Android 5.0+
	    @Override
	    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
	    {
		mUploadCallbackAboveL = filePathCallback;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		WeiBoWebActicity.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
		return true;
	    }
	});
    }

    private void initView()
    {
	webview = (WebView) findViewById(R.id.webView12312);
	title = (TextView) findViewById(R.id.topbar_title);
	// 进度条
	web_bar = (ProgressBar) findViewById(R.id.web_bar);
	context = this;
    }

    public void setTvTitle(String title)
    {
	getTvTitle().setText(title);
    }

    private TextView getTvTitle()
    {
	return title;
    }

//    @Override
//    public void onClick(View v)
//    {
//	switch (v.getId())
//	{
//	case R.id.web_back:
//	    BackWeb();
//	    break;
//	case R.id.web_tohome:
//	    CloseActivity();
//	    break;
//	default:
//	    break;
//	}
//    }

    private void BackWeb()
    {
	if (webview.canGoBack())
	    webview.goBack();
	else
	    finish();
    }

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
	((LinearLayout) findViewById(R.id.parent)).removeAllViews();
    }

}
