//package yh.app.lostFound;
//
//import org.androidpn.push.Constants;
//
//import yh.app.activitytool.ActivityPortrait;
//import  com.yhkj.cqswzyxy.R;
//
//import android.os.Bundle;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.view.Menu;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
///**
// * 
// * 包	名:yh.app.lostFound
// * 类	名:LostFoundActivity.java
// * 功	能:失物招领,跳转到web
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//@SuppressLint("SetJavaScriptEnabled")
//@SuppressWarnings("unused")
//public class LostFoundActivity extends ActivityPortrait {
//
//	String user_id;
//	WebView webview;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_web_view);
//		
//		user_id = Constants.number;
//		webview = (WebView) findViewById(R.id.webView2);
//		webview.getSettings().setJavaScriptEnabled(true);
//		webview.getSettings().setBlockNetworkImage(false);
//		webview.getSettings().setSupportZoom(true);
//		webview.setWebViewClient(new MyWebViewClient()); 
//		if (user_id.equals("") || user_id == null) {
//			webview.loadUrl(Constants.lostFoundListurl);
//		} else {
//			String str= Constants.lostFoundListurl + user_id;
//			webview.loadUrl(str);
//		}
//		
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
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.lost_found, menu);
//		return true;
//	}
//
//}
