package yh.app.appstart.lg;
//package  com.yhkj.cqswzyxy;
//
//import org.androidpn.push.Constants;
//
//import  com.yhkj.cqswzyxy.R;
//
//import yh.app.activitytool.ActivityPortrait;
//
//
//import android.os.Bundle;
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.view.Menu;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
///**
// * 
// * 包	名: com.yhkj.cqswzyxy
// * 类	名:NewView.java
// * 功	能:新闻界面,跳转到web
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//public class NewView extends ActivityPortrait {
//	WebView webview = null;
//	String  newid;
//
//	@SuppressLint("SetJavaScriptEnabled")
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_new_view);
//
//		Intent intent = getIntent();
//		newid = intent.getExtras().getString("id");
//		webview = (WebView) findViewById(R.id.webView1);
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setBlockNetworkImage(false);
//		webview.getSettings().setSupportZoom(true);
//
//		webview.setWebViewClient(new MyWebViewClient());
////		webview.setWebChromeClient(new WebChromeClient());
//		
//
//		if (newid.equals("") || newid == null) {
//			webview.loadUrl(Constants.newindexsurl);
////			 webview.loadUrl("http://www.baidu.com");
//		} else {
//			webview.loadUrl(Constants.newurl + newid);
//			// webview.loadUrl("www.baidu.com");
//		}
//	}
//
//	private class MyWebViewClient extends WebViewClient {
//		@Override
//		public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			view.getSettings().setBlockNetworkImage(false);
//			// Load the site into the default browser
//			view.loadUrl(url);
//			return true;
//		}
//
//	}
//	
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.new_view, menu);
//		return true;
//	}
//
//}
