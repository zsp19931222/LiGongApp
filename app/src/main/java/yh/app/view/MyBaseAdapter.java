package yh.app.view;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
public class MyBaseAdapter extends BaseAdapter
{
	private int count;

	public MyBaseAdapter(int count)
	{
		this.count = count;
	}

	@Override
	public int getCount()
	{
		return count;
	}

	@Override
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	ImageView img;
	TextView name;

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TextView txt = null;
		// if (convertView == null)
		// txt = new TextView(mCtx);
		// txt.setTextSize(30);
		// txt.setText("ID=" + position);
		// txt.setTextColor(Color.RED);
		// txt.setBackgroundColor(position == crt ? Color.BLACK : Color.YELLOW);
		if (convertView != null)
		{
			img = (ImageView) convertView.findViewById(R.id.zhkt_dmxszt_sub_img);
			name = (TextView) convertView.findViewById(R.id.zhkt_dmxszt_sub_button);
			convertView.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					img.setBackgroundResource(R.drawable.dianming_yes);
				}
			});
		}
		return convertView;
	}

}
