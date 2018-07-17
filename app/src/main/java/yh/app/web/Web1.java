package yh.app.web;

import yh.app.appstart.lg.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web1 extends Activity
{

    private final static String TAG = "MainActivity";

    private String TMP_URL = "http://up.saymagic.cn";
    private WebView webview;

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;

    private final static int FILECHOOSER_RESULTCODE = 1;

    private void assignViews()
    {
	webview = (WebView) findViewById(R.id.webView12312);
	WebSettings settings = webview.getSettings();
	settings.setUseWideViewPort(true);
	settings.setLoadWithOverviewMode(true);
	settings.setJavaScriptEnabled(true);
	settings.setSupportZoom(true);
	webview.setVerticalScrollBarEnabled(true);
	webview.setHorizontalScrollBarEnabled(true);
	webview.setWebChromeClient(new WebChromeClient()
	{
	    // For Android 3.0+
	    public void openFileChooser(ValueCallback<Uri> uploadMsg)
	    {
		Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg)");
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		Web1.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
	    }

	    // For Android 3.0+
	    public void openFileChooser(ValueCallback uploadMsg, String acceptType)
	    {
		Log.d(TAG, "openFileChoose( ValueCallback uploadMsg, String acceptType )");
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		Web1.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
	    }

	    // For Android 4.1
	    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
	    {
		Log.d(TAG, "openFileChoose(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
		mUploadMessage = uploadMsg;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		Web1.this.startActivityForResult(Intent.createChooser(i, "File Browser"), Web1.FILECHOOSER_RESULTCODE);
	    }

	    // For Android 5.0+
	    @Override
	    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
	    {
		Log.d(TAG, "onShowFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)");
		mUploadCallbackAboveL = filePathCallback;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		Web1.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
		return true;
	    }
	});
	webview.setWebViewClient(new WebViewClient()
	{
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url)
	    {
		Log.i("wocaonima", "shouldOverrideUrlLoading--url:" + url);

		// 拨打电话
		if (url.startsWith("tel:"))
		{
		    Intent in_sys_call = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
		    startActivity(in_sys_call);
		}
		else
		{
		    view.loadUrl(url);
		}
		return true;
	    }
	});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.web1);
	webview = (WebView) findViewById(R.id.webView12312);
	assignViews();
	webview.loadUrl(getIntent().getStringExtra("url"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode == FILECHOOSER_RESULTCODE)
	{
	    if (null == mUploadMessage && null == mUploadCallbackAboveL)
		return;
	    Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
	    if (mUploadCallbackAboveL != null)
	    {
		onActivityResultAboveL(requestCode, resultCode, data);
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
}