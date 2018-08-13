package yh.app.quanzi;

import yh.app.activitytool.ActivityPortrait;
import yh.app.utils.DefaultTopBar;import com.yhkj.cqgyxy.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 添加好友信息反馈
 */
public class tjhyxxfk extends ActivityPortrait
{
	private TextView name;
	private TextView message;

	private String friend_id;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quanzi_tjhyxxfk);
		initView();
	}

	private void initView()
	{
		// TODO Auto-generated method stub
		name = (TextView) findViewById(R.id.quanzi_tjhyxxfk_name);
		message = (TextView) findViewById(R.id.quanzi_tjhyxxfk_fjxx);

		new DefaultTopBar(this).doit("添加好友");

		Intent intent = getIntent();
		name.setText(intent.getStringExtra("name"));
		friend_id = intent.getStringExtra("friend_id");
		message.setText(intent.getStringExtra("message"));

		findViewById(R.id.start_chat).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startChat();
			}
		});
	}

	public void startChat()
	{
		Intent intent = new Intent();
		intent.setAction("yh.app.function.liaotianjiemian");
		intent.setPackage(getPackageName());
		intent.putExtra("friend_id", friend_id);
		intent.putExtra("name", name.getText().toString());
		startActivity(intent);
	}
}
