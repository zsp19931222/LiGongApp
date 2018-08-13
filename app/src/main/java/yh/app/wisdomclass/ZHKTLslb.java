package yh.app.wisdomclass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.AllATSSS;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件.ZHKTTeacherChoose;
import 云华.智慧校园.自定义适配器._无限加载_ListView;

public class ZHKTLslb
{
	private List<Map<String, String>> list;
	private Context context;
	private StringBuffer teacher_name;
	private ListView listView;
	private ViewGroup parent;
	private View view;
	private String usertype;
	private _无限加载_ListView wxjz;

	public ZHKTLslb(Context context, StringBuffer teacher_name, ListView listView, ViewGroup parent, View view, String usertype)
	{
		this.usertype = usertype;
		this.view = view;
		this.listView = listView;
		this.parent = parent;
		this.context = context;
		this.teacher_name = teacher_name;
	}

	private String ktbh;

	public void getJslb(String ktbh)
	{
		this.ktbh = ktbh;
		Map<String, String> map = new HashMap<String, String>();
		map.put("xsbh", Constants.number);
		map.put("function_id", "20150116");
		map.put("ticket", Ticket.getFunctionTicket("20150116"));
		// map.put("ktbh", ktbh);
		map.put("ktbh", ktbh);
		new AllATSSS(_链接地址导航.DC.教师列表.getUrl(), handler, map, AllATSSS.POST).execute();
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			
			try
			{
				list = (JsonTools.getListMapBtJsonArray(new JSONArray(msg.getData().getString("msg"))));
				if (list.size() > 1)
				{
					new ZHKTTeacherChoose().start(context, ((Activity) context).findViewById(R.id.topbar_layout), list, teacher_name);
				} else
				{
					teacher_name.append(list.get(0).get("JSXM") + ";" + list.get(0).get("JSZGH")).toString();
					// new zhkt_xzt_tools(context, listView, parent,
					// teacher_name.toString()).dsfds();
					if (usertype.equals("1"))
					{
						// new _无限加载_ListView(context, listView, parent,
						// teacher_name.toString()).start();
						wxjz = new _无限加载_ListView(context, listView, parent, teacher_name.toString(), ktbh, list.get(0).get("JSZGH"), Constants.number, Constants.name);
						wxjz.start();
					}
					// setListView(listView, list);
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		};
	};
	

	public void addItem(View view)
	{
		if (list.size()>0) {
			//教师列表数据为空
			wxjz.add(view);
		}
	}

	public String getLSBH()
	{
		if (list.size()>0) {
			//教师列表数据为空
			return teacher_name.toString().split(";")[1];
		}
		return null;
	}
}
