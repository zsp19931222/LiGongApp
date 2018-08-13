package yh.app.toweb;

import java.util.List;

import yh.app.activitytool.ActivityPortrait;
import yh.tool.widget.MyWebViewClient;
import yh.tool.widget.keyValue;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebView;import com.yhkj.cqgyxy.R;

//一卡通查询
/**
 * 
 * 包	名:yh.app.toweb
 * 类	名:CW_CampusCardActivity.java
 * 功	能:一卡通查询
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class CW_CampusCardActivity extends ActivityPortrait {
	WebView webview=null;
	String user_id="";
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_view);
		Intent intent = this.getIntent();
		List<keyValue> parm= (List<keyValue>)intent.getSerializableExtra("parm");
		webview = (WebView) findViewById(R.id.webView1);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setBlockNetworkImage(false);
		webview.getSettings().setSupportZoom(true);
		webview.setWebViewClient(new MyWebViewClient()); 
		String url=parm.get(0).getValue()+"?";
		for (int i = 1; i < parm.size(); i++) {
			url+=parm.get(i).getKey()+"="+parm.get(i).getValue()+"&";
		}
		url=url.substring(0, url.length()-1);
		webview.loadUrl(url);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}
	
}
