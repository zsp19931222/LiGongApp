package yh.app.wanttodo;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import com.yhkj.cqgyxy.R;

public class dbsy_content extends ActivityPortrait
{
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // 标题栏去除
		setContentView(R.layout.dbsy_content);
	}
}