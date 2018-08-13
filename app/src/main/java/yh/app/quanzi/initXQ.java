package yh.app.quanzi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.jpushdemo.ExampleApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.tool.ViewClickEffect;import com.yhkj.cqgyxy.R;

@SuppressLint("InflateParams")
public class initXQ
{
	private LinearLayout layout;
	private Context context;

	public initXQ(Context context, LinearLayout layout)
	{
		this.context = context;
		this.layout = layout;
		doit();
	}

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	private void doit()
	{
		for (int i = 0; i < 10; i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("qzmc", "测试兴趣组" + i);
			map.put("qzrs", "" + (new Random().nextInt(50) + 1));
			map.put("qzxxs", "" + (new Random().nextInt(88) + 1));
			list.add(map);
		}
		for (int i = 0; i < list.size(); i++)
		{
			setSubView(0, list.get(i).get("qzmc"), list.get(i).get("qzrs"), list.get(i).get("qzxxs"));
		}
	}

	private void setSubView(int qztp, String qzmc, String qzrs, String qzxxs)
	{
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.quanzi_wode_sub, null);
		@SuppressWarnings("unused")
		ImageView qztp_p = (ImageView) view.findViewById(R.id.qztp);
		TextView qzmc_t = (TextView) view.findViewById(R.id.qzmc);
		TextView qzrs_t = (TextView) view.findViewById(R.id.qzrs);
		TextView qzxxs_t = (TextView) view.findViewById(R.id.qzxxs);
		qzmc_t.setText(qzmc);
		qzrs_t.setHint(qzrs);
		qzxxs_t.setHint(qzxxs);
		view.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewClickEffect.doEffect(v, 200, context, "yh.app.quanzi.xqzs", ExampleApplication.getAppPackage(), new String[][]
				{});
			}
		});
		layout.addView(view);
	}
}
