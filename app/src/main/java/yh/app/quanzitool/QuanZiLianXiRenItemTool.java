package yh.app.quanzitool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import yh.app.appstart.lg.R;

public class QuanZiLianXiRenItemTool
{
	private View view;

	public QuanZiLianXiRenItemTool(Context context, ViewGroup parent)
	{
		view = LayoutInflater.from(context).inflate(R.layout.quanzi_layout_lianxiren_item, parent, false);
		parent.addView(view);
	}

	public interface onClickListener
	{
		void onClick(View v);
	}

	public QuanZiLianXiRenItemTool setImage(int id)
	{
		view.findViewById(R.id.head).setBackgroundResource(id);
		return this;
	}

	public QuanZiLianXiRenItemTool setButton(String name, boolean clickable)
	{
		Button button = ((Button) view.findViewById(R.id.button));
		button.setText(name);
		if (clickable)
		{
			button.setBackgroundResource(R.drawable.biankuang4);
			button.setTextColor(0xFF27b0c4);
		} else
		{
			button.setBackgroundColor(0x00000000);
			button.setTextColor(0xFF969696);
		}
		return this;
	}

	public QuanZiLianXiRenItemTool setOnClickListener(final onClickListener listener)
	{
		view.findViewById(R.id.head).setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				listener.onClick(v);
			}
		});
		return this;
	}

	public QuanZiLianXiRenItemTool setName(String name)
	{
		((TextView) view.findViewById(R.id.name)).setText(name);
		return this;
	}
}