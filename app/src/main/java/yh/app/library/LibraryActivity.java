//package yh.app.library;
//
//import org.androidpn.push.Constants;
//
//import yh.app.activitytool.ActivityPortrait;
//
//import  com.yhkj.cqswzyxy.R;
//
//import android.os.Bundle;
//import android.view.Menu;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
///**
// * 
// * 包	名:yh.app.library
// * 类	名:LibraryActivity.java
// * 功	能:图书馆界面,跳转到web端
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//public class LibraryActivity extends ActivityPortrait {
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
//			webview.loadUrl(Constants.newindexsurl);
//		} else {
//			String str= Constants.libraryurl + user_id;
//			webview.loadUrl(str);
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
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.library, menu);
//		return true;
//	}
//
//}
//
