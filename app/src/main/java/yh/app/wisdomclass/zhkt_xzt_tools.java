package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
import org.androidpn.push.Constants;
import yh.app.tool.AllATSSS;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.自定义适配器.MyChatAdapter;
import 云华.智慧校园.自定义适配器.MyChatAdapter.ChatViewHolder;;

public class zhkt_xzt_tools
{
	private Context context;
	private ViewGroup parent;
	private ListView listView;
	private String teacher_name;

	/** 增加的消息数 */
	private int addMessageNum;

	public zhkt_xzt_tools(Context context, ListView listView, ViewGroup parent, String teacher_name)
	{
		this.teacher_name = teacher_name;
		this.context = context;
		this.listView = listView;
		this.parent = parent;
	}

	private MyChatAdapter ada;

	public void dsfds()
	{
		ada = new MyChatAdapter(new ChatViewHolder()
		{
			@Override
			public List<View> getViewHolders()
			{
				return getHodlers(getList());
			}
		});
		listView.setAdapter(ada);
		listView.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				// 当不滚动时
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
				{
					// 判断是否滚动到底部
					if (view.getLastVisiblePosition() == view.getCount() - 1)
					{
						// 加载更多功能的代码
						// ---------------------测试-------------------
						List<View> list = new ArrayList<View>();
						list.add(LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_js, parent, false));
						ada.addLastView(list);
						ada.notifyDataSetChanged();
						// ---------------------测试-------------------
					} // 判断是否滚动到顶部
					else if (view.getFirstVisiblePosition() == 0)
					{
						// 加载更多功能的代码
						// ---------------------测试-------------------
						List<View> viewList = getHodlers(getList());
						ada.addFirstView(getHodlers(getList()));
						addMessageNum = viewList.size();
						ada.notifyDataSetChanged();
						listView.setSelection(addMessageNum);
						// ---------------------测试-------------------
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
			}
		});
	}

	public List<Map<String, String>> getList()
	{
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i = 0; i < 10; i++)
		{
			Map<String, String> map = new HashMap<String, String>();
			if (i % 2 == 0)
			{
				map.put("FSRBH", "123456");
			} else
			{
				map.put("FSRBH", Constants.number);
			}
			map.put("JSBH", "123456");
			map.put("FSSJ", "2015-06-12 11:30");
			map.put("ZTNR", "Android Json转Map Json转List" + i);
			list.add(map);
		}
		return list;
	}

	/**
	 * 获取到的数据，包括fsr,jsr,fssj,message,xztid
	 * 
	 * @param list
	 */
	public void addXZT(List<Map<String, String>> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).get("JSBH").equals(list.get(i).get("FSRBH")))
			{
				// 教师
				addLSXZT(list.get(i));
			} else
			{
				// 学生
				addXSXZT(list.get(i));
			}
		}
	}

	private void addXSXZT(Map<String, String> map)
	{
		View item = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_xs, parent, false);
		((TextView) item.findViewById(R.id.nr)).setText(map.get("ZTNR"));
		((TextView) item.findViewById(R.id.rq)).setHint(map.get("FSSJ"));
		((TextView) item.findViewById(R.id.mc)).setText("学生");
		parent.addView(item);
	}

	private void addLSXZT(Map<String, String> map)
	{
		View item = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_js, parent, false);
		((TextView) item.findViewById(R.id.nr)).setText(map.get("ZTNR"));
		((TextView) item.findViewById(R.id.rq)).setText(map.get("FSSJ"));
		((TextView) item.findViewById(R.id.mc)).setText("老师");
		parent.addView(item);
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				addXZT(JsonTools.getListMapBtJsonArray(new JSONArray(msg.getData().getString("msg"))));
			} catch (JSONException e)
			{
			}
		};
	};

	public void getXztByInternet()
	{
		AllATSSS at = new AllATSSS("", handler, new HashMap<String, String>(), AllATSSS.POST);
		at.execute();
	}

	public List<View> getHodlers(List<Map<String, String>> data)
	{
		List<View> viewHolders = new ArrayList<View>();
		for (int i = 0; i < data.size(); i++)
		{
			View holder;
			// 观察convertView随ListView滚动情况
			if (data.get(i).get("JSBH").equals(data.get(i).get("FSRBH")))
			{
				holder = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_js, parent, false);
				((TextView) holder.findViewById(R.id.mc)).setText(teacher_name.split(";")[0]);
			} else
			{
				holder = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_xs, parent, false);
				((TextView) holder.findViewById(R.id.mc)).setText(Constants.name);
			}
			/** 得到各个控件的对象 */
			((TextView) holder.findViewById(R.id.mc)).setText(data.get(i).get("FSRBH"));
			((TextView) holder.findViewById(R.id.nr)).setText(data.get(i).get("ZTNR"));
			((TextView) holder.findViewById(R.id.rq)).setHint(data.get(i).get("FSSJ"));
			viewHolders.add(holder);
		}
		return viewHolders;
	}

}