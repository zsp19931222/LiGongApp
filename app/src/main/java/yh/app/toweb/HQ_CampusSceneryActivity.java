package yh.app.toweb;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import yh.app.appstart.lg.R;
import android.view.Menu;

//校园风光
/**
 * 
 * 包	名:yh.app.toweb
 * 类	名:HQ_CampusSceneryActivity.java
 * 功	能:校园风光
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class HQ_CampusSceneryActivity  extends ActivityPortrait{
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
