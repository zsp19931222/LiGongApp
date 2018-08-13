package yh.app.coursetable;

import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

/**
 * 
 * 包 名:yh.app.coursetable 类 名:coureActivity.java 功 能:课程详细信息
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class coureActivity extends ActivityPortrait
{

	TextView course_name = null; // 课程名称
	TextView course_teacher = null; // 老师
	TextView course_palace = null; // 上课教室
	TextView course_time = null; // 上课时间
	TextView course_week = null; // 上课周数

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 // 标题栏去除
		setContentView(R.layout.course_detail);
		course_name = (TextView) findViewById(R.id.course_name1);
		course_teacher = (TextView) findViewById(R.id.course_teacher1);
		course_palace = (TextView) findViewById(R.id.course_palace1);
		course_time = (TextView) findViewById(R.id.course_time1);
		course_week = (TextView) findViewById(R.id.course_week1);

		Intent intent = getIntent();
		String name = intent.getExtras().getString("course_name");
		String palece = intent.getExtras().getString("course_palece");
		String teacher = intent.getExtras().getString("course_teacher");
		String time = intent.getExtras().getString("course_time");
		String week = intent.getExtras().getString("course_week");

		course_name.setText(name);

		course_palace.setText(palece);

		course_teacher.setText(teacher);

		course_time.setText(time);

		course_week.setText(week);

		// ImageButton return_diarylist = (ImageButton)
		// findViewById(R.id.return_pre); // 获得返回按钮
		// return_diarylist.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// Intent intent = new Intent(coureActivity.this,
		// TableDemoActivity.class);
		// startActivity(intent);
		// }
		// });
		ImageButton btnModifyBack = (ImageButton) findViewById(R.id.return_pre);
		btnModifyBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

}
