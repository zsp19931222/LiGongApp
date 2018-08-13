package yh.app.score;
import yh.app.activitytool.ActivityPortrait;
import android.os.Bundle;import com.yhkj.cqgyxy.R;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
/** 包 名:yh.app.score 类 名:Stu_Grade_Query_Detail_Activity.java 功 能:成绩详细信息查看界面
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29 */
public class Stu_Grade_Query_Detail_Activity extends ActivityPortrait
{
	ImageButton return_pre = null;
	TextView c_id, c_name, c_grade, c_xuefen, c_xingzhi;
	TextView c_GPA, c_rebuiltScore;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		 // 标题栏去除
		setContentView(R.layout.stu_grade_detail);
		c_id = (TextView) findViewById(R.id.course_id);
		c_name = (TextView) findViewById(R.id.course_name);
		c_grade = (TextView) findViewById(R.id.course_grade);
		c_xuefen = (TextView) findViewById(R.id.course_xuefen);
		c_xingzhi = (TextView) findViewById(R.id.course_xingzhi);
		c_GPA = (TextView) findViewById(R.id.c_GPA);
		c_rebuiltScore = (TextView) findViewById(R.id.c_rebuiltScore);
		return_pre = (ImageButton) findViewById(R.id.return_pre); //
		return_pre.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(Stu_Grade_Query_Detail_Activity.this, Stu_Grade_Query_Activity.class);
				startActivity(intent);
			}
		});
		Intent intent = getIntent();
		c_id.setText(intent.getExtras().getString("c_id"));
		c_name.setText(intent.getExtras().getString("c_name"));
		c_grade.setText(intent.getExtras().getString("c_grade"));
		c_xuefen.setText(intent.getExtras().getString("c_xuefen"));
		c_xingzhi.setText(intent.getExtras().getString("c_xingzhi"));
		if (intent.getExtras().getString("c_GPA").equals(""))
		{
			c_GPA.setText("无");
		}
		else
		{
			c_GPA.setText(intent.getExtras().getString("c_GPA"));
		}
		c_rebuiltScore.setText(intent.getExtras().getString("c_scoreType"));// 考试类型
		ImageButton btnModifyBack = (ImageButton) findViewById(R.id.return_pre); // ��÷��ذ�ť
		btnModifyBack.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stu__grade__query__detail_, menu);
		return true;
	}
}
