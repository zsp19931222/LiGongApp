package yh.app.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jpushdemo.ExampleApplication;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import android.content.Intent;import yh.app.appstart.lg.R;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class WantToDo extends ActivityPortrait
{

	private ListView list;
	@SuppressWarnings("unused")
	private ImageView image_back;
	private List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 // 标题栏去除
		setContentView(R.layout.dbsy_list);

		// 测试
		Map<String, String> map = new HashMap<String, String>();
		map.put("content", "2015年大学生创业策划");
		map.put("date", "2015-02-15");
		mapList.add(map);

		initViews();
		setViews();
		setViewsThing();
	}

	private void setViewsThing()
	{
		list.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();
				intent.setAction("yh.app.wanttodo.dbsy_content");
				intent.setPackage(ExampleApplication.getAppPackage());
				startActivity(intent);
				finish();
			}
		});
	}

	private void setViews()
	{
		SimpleAdapter sad = new SimpleAdapter(this, mapList, R.layout.dbsy_sub, new String[] { "content", "date" }, new int[] { R.id.dbsy_content, R.id.dbsy_date });
		list.setAdapter(sad);
	}

	private void initViews()
	{
		list = (ListView) findViewById(R.id.dbsy_list);
		image_back = (ImageView) findViewById(R.id.dbsy_list_back);
	}
}