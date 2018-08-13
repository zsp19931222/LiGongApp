package yh.app.toweb;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import com.yhkj.cqgyxy.R;
import android.view.Menu;

//网络报修
/**
 * 
 * 包	名:yh.app.toweb
 * 类	名:HQ_RepairActivity.java
 * 功	能:网络报修
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class HQ_RepairActivity extends ActivityPortrait  {
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
