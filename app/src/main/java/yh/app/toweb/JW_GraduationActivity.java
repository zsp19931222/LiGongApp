package yh.app.toweb;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import yh.app.appstart.lg.R;
import android.view.Menu;

//离校
public class JW_GraduationActivity extends ActivityPortrait{
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
