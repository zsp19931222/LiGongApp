package yh.app.activitytool;

import android.content.pm.ActivityInfo;
import android.support.v4.app.FragmentActivity;
/**
 * 
 * 包	名:yh.app.activitytool
 * 类	名:FragmentActivityPortrait.java
 * 功	能:继承于FragmentActivity,但强制设置为竖屏
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class FragmentActivityPortrait extends FragmentActivity {
	@Override
	protected void onResume() {
	 /**
	  * 设置为竖屏
	  */
	 if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
	  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	 }
	 super.onResume();
	}
}
