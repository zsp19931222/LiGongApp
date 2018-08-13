package yh.app.toweb;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import com.yhkj.cqgyxy.R;
import android.view.Menu;
import android.webkit.WebView;
//跳蚤市场
/**
 * 
 * 包	名:yh.app.toweb
 * 类	名:HQ_FleamarketActivity.java
 * 功	能:跳蚤市场
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class HQ_FleamarketActivity extends ActivityPortrait {
	WebView webview=null;
	String user_id="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_crcle_group);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
		return true;
	}
}
