package yh.app.toweb;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import com.yhkj.cqgyxy.R;
import android.view.Menu;
import android.webkit.WebView;
//课表
public class JW_CourseActivity extends ActivityPortrait {
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
