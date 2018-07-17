package yh.app.quanzitool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import yh.app.appstart.lg.R;
@SuppressLint("InflateParams")
public class Add_quanzi_group_hand
{
	private Context context;

	public Add_quanzi_group_hand(Context context)
	{
		this.context = context;
	}

	public View add(String group_name)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.quanzi_group_hand, null);
		TextView t = (TextView) view.findViewById(R.id.quanzi_group_hand);
		t.setHint(group_name);
		return view;
	}
}
