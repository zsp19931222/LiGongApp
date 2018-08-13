package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class ZHKTJSZTtools
{
	private Context context;
	private LinearLayout layout;

	public ZHKTJSZTtools(Context context, LinearLayout layout)
	{
		this.context = context;
		this.layout = layout;
	}

	private String ktbh;

	public void getStudentList(String ktbh)
	{
		this.ktbh = ktbh;
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", Constants.number);
		map.put("function_id", "20150116");
		map.put("ticket", Ticket.getFunctionTicket("20150116"));
		map.put("ktbh", ktbh);
		// new AllATSSS(_链接地址导航.DC.教师小纸条学生名单.getUrl(), handler, map,
		// AllATSSS.POST).executeOnExecutor(ThreadPoolManage., params)
		new ThreadPoolManage().addPostTask(_链接地址导航.DC.教师小纸条学生名单.getUrl(), map, handler);
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			try
			{
				JSONArray jsa = new JSONArray(msg.getData().getString("msg"));
				addStudentListView(layout, setView(jsa));
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		};
	};

	private List<View> setView(JSONArray jsa)
	{
		List<View> viewList = new ArrayList<View>();
		for (int i = 0; i < jsa.length(); i++)
		{
			try
			{
				View view = LayoutInflater.from(context).inflate(R.layout.double_textview, layout, false);
				((TextView) view.findViewById(R.id.xsxm)).setText(jsa.getJSONObject(i).getString("XSXM"));
				((TextView) view.findViewById(R.id.xsbh)).setText(jsa.getJSONObject(i).getString("XSBH"));
				view.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						Intent intent = new Intent();
						intent.setAction("yh.app.wisdomclass.zhkt_jsxztxqzs");
						intent.setPackage(context.getPackageName());
						intent.putExtra("xsbh", ((TextView) v.findViewById(R.id.xsbh)).getText());
						intent.putExtra("xsxm", ((TextView) v.findViewById(R.id.xsxm)).getText());
						intent.putExtra("ktbh", ktbh);
						intent.putExtra("jsbh", Constants.number);
						context.startActivity(intent);
					}
				});
				viewList.add(view);
			} catch (Exception e)
			{
			}
		}
		return viewList;
	}

	public void addStudentListView(LinearLayout layout, List<View> viewList)
	{
		for (int i = 0; i < viewList.size(); i++)
		{
			layout.addView(viewList.get(i));
		}
	}
}