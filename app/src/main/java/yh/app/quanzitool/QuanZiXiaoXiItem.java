package yh.app.quanzitool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;

public class QuanZiXiaoXiItem
{
	private View view;
	private Context context;
	private String friend_id;

	public QuanZiXiaoXiItem(Context context, ViewGroup parent, String friend_id)
	{
		this.friend_id = friend_id;
		this.context = context;
		view = LayoutInflater.from(context).inflate(R.layout.quanzi_layout_xiaoxi_item, parent, false);
		view.setTag(friend_id);
		parent.addView(view);
		setOnClick();
	}

	private String name;

	public QuanZiXiaoXiItem setName()
	{
		((TextView) view.findViewById(R.id.xm)).setText(getName());
		return this;
	}

	public QuanZiXiaoXiItem setWDTSBJ()
	{
		String num = new SqliteHelper().rawQuery("select count(*) num from lt where userid=? and friend_id=? and isread='false'", new String[]
		{
				Constants.number, friend_id
		}).get(0).get("num");
		if (!num.equals("0"))
		{
			view.findViewById(R.id.wdtsbj).setVisibility(View.VISIBLE);
			((TextView) view.findViewById(R.id.wdtsbj)).setText(num);
		}
		return this;
	}

	public QuanZiXiaoXiItem setContent(String content)
	{
		((TextView) view.findViewById(R.id.nr)).setHint(content);
		return this;
	}

	public QuanZiXiaoXiItem setDate(String date)
	{
		((TextView) view.findViewById(R.id.sj)).setHint(date);
		return this;
	}

	public void setOnClick()
	{
		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent();
				intent.setAction("yh.app.function.liaotianjiemian");
				intent.setPackage(context.getPackageName());
				intent.putExtra("friend_id", friend_id);
				intent.putExtra("name", name);
				new SqliteHelper().execSQL("update lt set isread='true' where userid=? and friend_id=?", new Object[]
				{
						Constants.number, friend_id
				});
				context.startActivity(intent);
			}
		});
	}

	private String getName()
	{
		try
		{
			name = new SqliteHelper().rawQuery("select name from friend where friend_id=? and userid =?", new String[]
			{
					friend_id, Constants.number
			}).get(0).get("name");
		} catch (Exception e)
		{
			try
			{
				name = new SqliteHelper().rawQuery("select name from mrfz where friend_id=? and userid =?", new String[]
				{
						friend_id, Constants.number
				}).get(0).get("name");
			} catch (Exception e2)
			{
			}
		}
		return name;
	}
}
