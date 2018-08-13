package yh.app.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

public class TopBarHelper
{
	private Context context;
	private LinearLayout layout;
	private TextView title;
	public static final int BUTTON_LEFT = R.id.left, BUTTON_RIGHT = R.id.right, BUTTON_EXTRA = R.id.extra;

	public interface OnClickLisener
	{
		void setLeftOnClick();

		void setExtraOnclick();

		void setRightOnClick();
	}

	private View left, right, extra;

	public TopBarHelper(Context context, View view)
	{
		this.extra = view.findViewById(R.id.topbar_extra);
		this.context = context;
		this.left = view.findViewById(R.id.topbar_left);
		this.right = view.findViewById(R.id.topbar_right);
		this.title = (TextView) view.findViewById(R.id.topbar_title);
		this.layout = (LinearLayout) view.findViewById(R.id.topbar_layout);
	}

	public TopBarHelper setOnClickLisener(final OnClickLisener clickLisener)
	{
		left.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				clickLisener.setLeftOnClick();
			}
		});
		right.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLisener.setRightOnClick();
			}
		});
		extra.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				clickLisener.setExtraOnclick();
			}
		});
		return this;
	}

	private TextView getTitle()
	{
		try
		{
			return this.title;
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public TopBarHelper setTitle(String title)
	{
		try
		{
			getTitle().setText(title);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return this;
	}

	public TopBarHelper setExtra(int background, boolean isShow)
	{
		((LinearLayout) extra).getChildAt(0).setBackgroundResource(background);
		if (isShow)
		{
			((LinearLayout) extra).getChildAt(0).setVisibility(View.VISIBLE);
			extra.setVisibility(View.VISIBLE);
		} else
		{
			((LinearLayout) extra).getChildAt(0).setVisibility(View.INVISIBLE);
			extra.setVisibility(View.INVISIBLE);
		}
		return this;
	}

	public View getLeftView()
	{
		return left;
	}

	public View getRightView()
	{
		return right;
	}

	public View getExtraView()
	{
		return extra;
	}

	public LinearLayout getLayout()
	{
		return layout;
	}

	public Button getButton(int buttonID)
	{
		return (Button) ((Activity) context).findViewById(buttonID);
	}
}