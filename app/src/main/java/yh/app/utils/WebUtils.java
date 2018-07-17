package yh.app.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WebUtils {
	private LinearLayout layout;
	private WebView webView;

	@SuppressLint("SetJavaScriptEnabled")
	public WebUtils(Context context) {

		webView = new WebView(context);
		
	}
	
	public void onWebLoad(LinearLayout layout, String url){
		layout.addView(webView);
		WebSettings webSettings = webView.getSettings();// 获得webview设置
		webSettings.setUseWideViewPort(false);// 是否支持任意缩放
		webSettings.setLoadWithOverviewMode(true);// 适配
		webSettings.setJavaScriptEnabled(true);// 支持js
		//使用缓存
//		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		//不缓存
		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
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
	
	/**
	 * 返回当前webview
	 * @return
	 */
	public WebView getWebView(){
		return webView;
	}
	/**
	 * 返回上一页
	 */
	public void goBack(){
		webView.goBack();
	}
	/**
	 * web暂停
	 */
	public void webPause() {
		if (webView != null) {
			webView.onPause();
		}
	}

	/**
	 * web重启
	 */
	public void webResume() {
		if (webView != null) {
			webView.onResume();
		}
	}

	/**
	 * web销毁
	 */
	public void webDestroy() {
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
