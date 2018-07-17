package yh.app.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MyWebViewClient extends WebViewClient
{
	private Context context;
	private boolean isHome;
	private String home_url;

	public MyWebViewClient(Context context, boolean isHome, String home_url)
	{
		this.context = context;
		this.isHome = isHome;
		this.home_url = home_url;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		if (!isHome)
		{
			home_url = url;
			isHome = true;
		}
		if (url.startsWith("tel:"))
		{
			try
			{
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				context.startActivity(intent);
			} catch (Exception e)
			{
				Toast.makeText(context.getApplicationContext(), "该卖家没有留下联系方式!!!", Toast.LENGTH_SHORT).show();
			}
		} else
		{
			view.loadUrl(url);
		}
		return true;
	}
}
