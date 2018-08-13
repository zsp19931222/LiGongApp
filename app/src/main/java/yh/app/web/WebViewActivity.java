package yh.app.web;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import com.yhkj.cqgyxy.R;
public class WebViewActivity extends Activity {
	private LinearLayout layout;
	private WebView webView;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webview);
		initView();
		if (url != null) {
			onWebLoad(layout, url);
		}

	}

	@SuppressWarnings("unused")
	private void initView() {
		url=getIntent().getStringExtra("url");
		webView = new WebView(this);
		layout = (LinearLayout) findViewById(R.id.ly_webview);

	}

	public void onWebLoad(LinearLayout layout, String url) {
		layout.addView(webView);
		WebSettings webSettings = webView.getSettings();// 获得webview设置
		webSettings.setUseWideViewPort(false);// 是否支持任意缩放
		webSettings.setLoadWithOverviewMode(true);// 适配
		webSettings.setJavaScriptEnabled(true);// 支持js
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBlockNetworkImage(false);
		// 使用缓存
		 webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// 不缓存
//		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.loadUrl(url);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				// 在当前webview显示网页
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				// TODO Auto-generated method stub
				// 加载失败
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});
		// 获得加载标题
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {

				// 获得价值标题
			}
		});
	}
	@Override
	protected void onResume() {
		
		super.onResume();
		if (webView != null) {
			webView.onResume();
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (webView != null) {
			webView.onPause();
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if (webView != null) {
			webView.clearCache(true);// 清空缓存
			if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				if (layout != null) {
					layout.removeView(webView);
				}
				webView.removeAllViews();
				webView.destroy();
			}
		} else {
			webView.removeAllViews();
			webView.destroy();
			if (layout != null) {
				layout.removeView(webView);
			}

		}
		webView = null;
	}
}
