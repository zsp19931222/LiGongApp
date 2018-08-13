package yh.app.toweb;

import java.util.ArrayList;
import java.util.List;

import yh.app.activitytool.ActivityPortrait;
import yh.tool.widget.GetActivity;import com.yhkj.cqgyxy.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

//财务主模块
/**
 * 
 * 包	名:yh.app.toweb
 * 类	名:CW_MainActivity.java
 * 功	能:财务主模块
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class CW_MainActivity extends ActivityPortrait {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		TextView txt= (TextView)findViewById(R.id.ComponentTitle);
		txt.setText("财务");
		final LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.VERTICAL);
		List<Button> btnlist=new GetActivity().GetButtonByKey(GetKeys(), this);
		for (int i = 0; i < btnlist.size(); i++) {
			layout2.addView(btnlist.get(i));
		}
		setContentView(layout2);
	}
	
	private List<String> GetKeys()
	{
		List<String> list= new ArrayList<String>();
		list.add("CampusCard");
		list.add("Wage");
		return list;
	}
}
