package yh.sina.weibo.sdk.demo; 

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import com.yhkj.cqgyxy.R;
public class Blog extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.blog_type);
		setBlogTypeItem();
	}

	private void setBlogTypeItem()
	{
		View view = LayoutInflater.from(this).inflate(R.layout.share_type_item, (LinearLayout) findViewById(R.id.blog_type_layout), true);
		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(Blog.this, WBAuthActivity.class));
			}
		});
	}
}
