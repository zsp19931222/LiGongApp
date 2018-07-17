package yh.tool.widget;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 
 * 包 名:yh.tool.widget 类 名:MyWebViewClient.java 功 能:Web控件
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class MyWebViewClient extends WebViewClient
{
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		view.getSettings().setBlockNetworkImage(false);
		view.loadUrl(url);
		return true;
	}
}
