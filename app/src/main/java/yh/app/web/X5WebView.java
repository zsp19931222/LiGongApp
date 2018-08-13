//package yh.app.web;
//
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.util.AttributeSet;
//import android.view.View;
//import android.webkit.ValueCallback;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//import yh.app.tool.QRCodeHelper;
//import 云华.智慧校园.工具.IsNull;
//
//import java.util.Map;
//
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebSettings;
//import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
//import com.yhkj.cqgyxy.R;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
//@SuppressLint("SetJavaScriptEnabled")
//public class X5WebView extends WebView
//{
//    private TextView title;
//    private String defaultTitleString = null;
//    private Context context;
//    private ProgressBar web_bar;
//
//    private class MyWebChromeClient extends WebChromeClient
//    {
//	@SuppressWarnings("unused")
//	public void openFileChooser(ValueCallback<Uri> uploadMsg)
//	{
//	    Web.mUploadMessage = uploadMsg;
//	    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//	    i.addCategory(Intent.CATEGORY_OPENABLE);
//	    i.setType("*/*");
//	    ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Chooser"), Web.FILECHOOSER_RESULTCODE);
//	}
//
//	@SuppressWarnings("unused")
//	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType)
//	{
//	    Web. mUploadMessage = uploadMsg;
//	    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//	    i.addCategory(Intent.CATEGORY_OPENABLE);
//	    i.setType("*/*");
//	    ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Browser"), Web.FILECHOOSER_RESULTCODE);
//	}
//
//	@SuppressWarnings("unused")
//	public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
//	{
//	    Web. mUploadMessage = uploadMsg;
//	    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//	    i.addCategory(Intent.CATEGORY_OPENABLE);
//	    i.setType("*/*");
//	    ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Browser"), Web.FILECHOOSER_RESULTCODE);
//	}
//
//	// For Android 5.0+
//	public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
//	{
//	    Web. mUploadCallbackAboveL = filePathCallback;
//	    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
//	    i.addCategory(Intent.CATEGORY_OPENABLE);
//	    i.setType("*/*");
//	    ((Activity) context).startActivityForResult(Intent.createChooser(i, "File Browser"), Web.FILECHOOSER_RESULTCODE);
//	    return true;
//	}
//
//	@Override
//	public void onReceivedTitle(WebView arg0, String arg1)
//	{
//	    // TODO Auto-generated method stub
//	    super.onReceivedTitle(arg0, arg1);
//	    if (arg1.contains("/") || arg1.contains("?") || arg1.contains("&"))
//	    {
//		title.setText(defaultTitleString);
//	    } else if (IsNull.isNotNull(arg1))
//	    {
//		title.setText(arg1);
//	    } else
//	    {
//
//	    }
//	}
//
//	@Override
//	public void onProgressChanged(WebView view, int newProgress)
//	{
//	    if (web_bar != null)
//	    {// 进度条
//		if (newProgress == 100)
//		{
//		    web_bar.setVisibility(View.GONE);
//		} else
//		{
//		    if (View.GONE == web_bar.getVisibility())
//		    {
//			web_bar.setVisibility(View.VISIBLE);
//		    }
//		}
//		web_bar.setProgress(newProgress + 5);
//	    }
//	    super.onProgressChanged(view, newProgress);
//	}
//    }
//
//    private WebViewClient client = new WebViewClient()
//    {
//
//	/**
//	 * 防止加载网页时调起系统浏览器
//	 */
//	public boolean shouldOverrideUrlLoading(WebView view, String url)
//	{
//
//	    if (!IsNull.isNotNull(url))
//	    {
//		return true;
//	    }
//	    // 理工迎新系统
//	    // 问卷调查关闭链接
//	    if (url.equals("http://www.close.com/"))
//	    {
//		((Activity) context).finish();
//		return true;
//	    } else if (url.startsWith("tel:"))
//	    {
//		try
//		{
//		    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//		    context.startActivity(intent);
//		} catch (Exception e)
//		{
//		    Toast.makeText(context, "该联系方式匹配失败！！！", Toast.LENGTH_SHORT).show();
//		}
//		return true;
//	    } else if (url.startsWith("map:"))
//	    {
//		// 地图webAPI
//		return true;
//	    } else if (url.startsWith("yhqrcode://"))
//	    {
//		QRCodeHelper helper = new QRCodeHelper();
//		helper.scanQRCode(context);
//		return true;
//	    } else
//	    {
//		view.loadUrl(url);
//		return false;
//	    }
//	}
//
//	@Override
//	public void onPageFinished(WebView arg0, String arg1)
//	{
//	    // TODO Auto-generated method stub
//	    super.onPageFinished(arg0, arg1);
//	}
//
//    };
//
//    public X5WebView(Context arg0, AttributeSet arg1, int arg2, boolean arg3)
//    {
//	super(arg0, arg1, arg2, arg3);
//	// TODO Auto-generated constructor stub
//	init(arg0);
//    }
//
//    public X5WebView(Context arg0, AttributeSet arg1, int arg2, Map<String, Object> arg3, boolean arg4)
//    {
//	super(arg0, arg1, arg2, arg3, arg4);
//	// TODO Auto-generated constructor stub
//	init(arg0);
//    }
//
//    public X5WebView(Context arg0, AttributeSet arg1, int arg2)
//    {
//	super(arg0, arg1, arg2);
//	// TODO Auto-generated constructor stub
//	init(arg0);
//    }
//
//    public X5WebView(Context arg0, AttributeSet arg1)
//    {
//	super(arg0, arg1);
//	init(arg0);
//    }
//
//    private void init(Context arg0)
//    {
//	this.context = arg0;
//	this.setWebViewClient(client);
//	this.setWebChromeClient(new MyWebChromeClient());
//	// WebStorage webStorage = WebStorage.getInstance();
//	initWebViewSettings();
//	// this.getView().setClickable(true);
//    }
//
//    private void initWebViewSettings()
//    {
////	WebSettings webSetting = this.getSettings();
////	webSetting.setJavaScriptEnabled(true);
////	webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
////	webSetting.setAllowFileAccess(true);
////	webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
////	webSetting.setSupportZoom(true);
////	webSetting.setBuiltInZoomControls(true);
////	webSetting.setUseWideViewPort(true);
////	webSetting.setSupportMultipleWindows(true);
////	webSetting.setAppCacheEnabled(true);
////	webSetting.setDomStorageEnabled(true);
////	webSetting.setGeolocationEnabled(true);
////	webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
////	if (android.os.Build.VERSION.SDK_INT >= 21)
////	{
////	    webSetting.setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
////	}
////	webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
////	webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
//	WebSettings settings = getSettings();
//	settings.setUseWideViewPort(true);
//	settings.setLoadWithOverviewMode(true);
//	settings.setJavaScriptEnabled(true);
//	settings.setSupportZoom(true);
//	setVerticalScrollBarEnabled(true);
//setHorizontalScrollBarEnabled(true);
//    }
//
//    // @Override
//    // protected boolean drawChild(Canvas canvas, View child, long drawingTime)
//    // {
//    // boolean ret = super.drawChild(canvas, child, drawingTime);
//    // canvas.save();
//    // Paint paint = new Paint();
//    // paint.setColor(0x7fff0000);
//    // paint.setTextSize(24.f);
//    // paint.setAntiAlias(true);
//    // if (getX5WebViewExtension() != null) {
//    // canvas.drawText(this.getContext().getPackageName() + "-pid:"
//    // + android.os.Process.myPid(), 10, 50, paint);
//    // canvas.drawText(
//    // "X5 Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//    // 100, paint);
//    // } else {
//    // canvas.drawText(this.getContext().getPackageName() + "-pid:"
//    // + android.os.Process.myPid(), 10, 50, paint);
//    // canvas.drawText("Sys Core", 10, 100, paint);
//    // }
//    // canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//    // canvas.drawText(Build.MODEL, 10, 200, paint);
//    // canvas.restore();
//    // return ret;
//    // }
//
//    public X5WebView(Context arg0)
//    {
//	super(arg0);
//	init(arg0);
//    }
//
//    public void setWeb_bar(ProgressBar web_bar)
//    {
//	this.web_bar = web_bar;
//    }
//
//    public void setTitle(TextView title)
//    {
//	this.title = title;
//    }
//
//}
