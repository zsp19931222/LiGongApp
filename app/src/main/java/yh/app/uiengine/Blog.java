package yh.app.uiengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.appstart.lg.R;
@SuppressLint("InflateParams")
public class Blog extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.blog_type);
		setView();
		initBlogType();
	}

	private final String[] BlogType = new String[]
	{
			"新浪微博", "网页微博", "搜狐微博", "腾讯微博"
	};

	private void initBlogType()
	{
		for (int i = 0; i < BlogType.length; i++)
		{
			((LinearLayout) findViewById(R.id.blog_type_layout)).addView(setBlogTypeItem(i));
		}
	}

	private View setBlogTypeItem(int i)
	{
		View view = LayoutInflater.from(this).inflate(R.layout.share_type_item, null);
		switch (i)
		{
		case 0:
			((LinearLayout) view).getChildAt(0).setBackgroundResource(R.drawable.blog_sina);
			break;
		case 1:
			((LinearLayout) view).getChildAt(0).setBackgroundResource(R.drawable.blog_netease);
			break;
		case 2:
			((LinearLayout) view).getChildAt(0).setBackgroundResource(R.drawable.blog_sohu);
			break;
		case 3:
			((LinearLayout) view).getChildAt(0).setBackgroundResource(R.drawable.blog_tencent);
			break;
		default:
			break;
		}
		((TextView) ((LinearLayout) view).getChildAt(1)).setText(BlogType[i]);
		return view;
	}

	private void setView()
	{
		((TextView) this.findViewById(R.id.topbar_title)).setText("微博绑定");
		this.findViewById(R.id.topbar_left).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		this.findViewById(R.id.topbar_right).setVisibility(View.INVISIBLE);
	}
}
