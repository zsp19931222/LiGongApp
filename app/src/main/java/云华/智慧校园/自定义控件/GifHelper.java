package 云华.智慧校园.自定义控件;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import yh.app.appstart.lg.R;
public class GifHelper
{
	public View addGif(ViewGroup parent, Context context)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.no_message, parent, false);
		WebView web = (WebView) view.findViewById(R.id.gif);
		// web.loadUrl("file://android_asset/no_message.gif");width='100%' height='100%'
		web.loadDataWithBaseURL(null, "<HTML margin:0px; padding:0px; height:100%;><body bgcolor='#FFFFFF' ></div><div align=center ><IMG src='file:///android_asset/no_message.gif' width='60%' /></div></body></html>", "text/html", "UTF-8", null);
		parent.addView(view);
		return view;
	}
}
