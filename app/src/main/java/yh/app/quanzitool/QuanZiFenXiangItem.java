package yh.app.quanzitool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
public class QuanZiFenXiangItem
{
	private View view;

	public QuanZiFenXiangItem(Context context, ViewGroup parent)
	{
		view = LayoutInflater.from(context).inflate(R.layout.quanzi_layout_fenxiang_item, parent, false);
		parent.addView(view);
	}

	public QuanZiFenXiangItem setHead(Drawable drawable)
	{
		view.findViewById(R.id.head).setBackground(drawable);
		return this;
	}

	public QuanZiFenXiangItem setHead(int id)
	{
		view.findViewById(R.id.head).setBackgroundResource(id);
		return this;
	}

	public QuanZiFenXiangItem setName(String name)
	{
		((TextView) view.findViewById(R.id.name)).setText(name);
		return this;
	}

	public void setOnClickLisener(final OnClickLisener onClickLisener)
	{
		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				onClickLisener.onClick();
			}
		});
	}

	public interface OnClickLisener
	{
		void onClick();
	}
}
