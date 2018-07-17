package 云华.智慧校园.自定义适配器;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.tool.AllATSSS;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具._链接地址导航;

public class _无限加载_ListView
{
	private Context context;
	private ViewGroup parent;
	private ListView listView;
	private int nowPage = 0, pageSize = 10;
	private List<View> nowViewList = new ArrayList<View>();

	public Context getContext()
	{
		return context;
	}

	public ViewGroup getParent()
	{
		return parent;
	}

	public ListView getListView()
	{
		return listView;
	}

	// public MyChatAdapter getAda()
	// {
	// return ada;
	// }

	private String teacher_name;
	private String ktbh, jsbh, xsbh;

	public _无限加载_ListView(Context context, ListView listView, ViewGroup parent, String teacher_name, String ktbh, String jsbh, String xsbh, String student_name)
	{
		this.student_name = student_name;
		this.jsbh = jsbh;
		this.xsbh = xsbh;
		this.ktbh = ktbh;
		this.teacher_name = teacher_name;
		this.context = context;
		this.listView = listView;
		this.parent = parent;
	}

	// private MyChatAdapter ada;

	public void start()
	{
		getList();
	}

	public _无限加载_ListView setAdapter(final List<Map<String, String>> list)
	{
		// ada = new MyChatAdapter(new ChatViewHolder()
		// {
		// @Override
		// public List<View> getViewHolders()
		// {
		// return getHodlers(list);
		// }
		// });
		ViewList = getViewList(list);
		getLoadViewList(0);
		listView.setAdapter(new myAda());
		listView.setSelection(listView.getBottom());
		return this;
	}

	public _无限加载_ListView setOnScrollListener(final UpAndDownLisener lisener, List<View> list)
	{
		listView.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				List<View> list = new ArrayList<View>();
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
					if (view.getLastVisiblePosition() == view.getCount() - 1)
					{
						try
						{
							lisener.setDownLisener(list);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					} else if (view.getFirstVisiblePosition() == 0)
					{
						try
						{
							lisener.setUpLisener(list);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{

			}
		});
		return this;
	}

	private Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				List<Map<String, String>> list = JsonTools.getListMapBtJsonArray(new JSONArray(msg.getData().getString("msg")));
				setAdapter(list).setOnScrollListener(new UpAndDownLisener()
				{
					@Override
					public void setUpLisener(List<View> list)
					{
						getLoadViewList(nowPage);
						listView.setSelection(addNum);
						// getAda().addFirstView(nowViewList);
						// getAda().notifyDataSetChanged();
					}

					@Override
					public void setDownLisener(List<View> list)
					{
						// getAda().addLastView(list);
						// getAda().notifyDataSetChanged();
					}
				}, null);
			} catch (JSONException e1)
			{
				e1.printStackTrace();
			} catch (Exception e2)
			{
				e2.printStackTrace();
			}
		};
	};

	// 测试
	public void getList()
	{
		Map<String, String> cs = new HashMap<String, String>();
		cs.put("ktbh", ktbh);
		cs.put("xsbh", xsbh);
		cs.put("jsbh", jsbh);
		cs.put("fssj", "2014-02-02 01:01:01");// new DateString("yyyy-MM-dd
												// hh:mm:ss").DateToString(new
												// Date())
		cs.put("ticket", Ticket.getFunctionTicket("20150116"));
		cs.put("function_id", "20150116");
		cs.put("user_id", Constants.number);
		new AllATSSS(_链接地址导航.DC.学生对话框内容.getUrl(), handler, cs, AllATSSS.POST).executeOnExecutor(Executors.newCachedThreadPool());
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
				((TextView) holder.findViewById(R.id.mc)).setText(student_name);
			}
			/** 得到各个控件的对象 */

			((TextView) holder.findViewById(R.id.nr)).setText(data.get(i).get("ZTNR"));
			((TextView) holder.findViewById(R.id.rq)).setHint(data.get(i).get("FSSJ"));
			viewHolders.add(holder);
		}
		return viewHolders;
	}

	public interface UpAndDownLisener
	{
		void setUpLisener(List<View> list) throws Exception;

		void setDownLisener(List<View> list) throws Exception;
	}

	private List<View> ViewList;

	public void setListView(ListView listView, List<Map<String, String>> data)
	{
		ViewList = getViewList(data);
		listView.setAdapter(new BaseAdapter()
		{

			@Override
			public View getView(int position, View convertView, ViewGroup parent)
			{
				return null;
			}

			@Override
			public long getItemId(int position)
			{
				return ViewList.get(position).getId();
			}

			@Override
			public Object getItem(int position)
			{
				return ViewList.get(position);
			}

			@Override
			public int getCount()
			{
				return ViewList.size();
			}
		});
	}

	class myAda extends BaseAdapter
	{

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			return nowViewList.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return nowViewList.get(position).getId();
		}

		@Override
		public Object getItem(int position)
		{
			return nowViewList.get(position);
		}

		@Override
		public int getCount()
		{
			return nowViewList.size();
		}
	}

	private String student_name;

	public List<View> getViewList(List<Map<String, String>> data)
	{
		List<View> viewHolders = new ArrayList<View>();
		for (int i = 0; i < data.size(); i++)
		{
			View holder;
			// 观察convertView随ListView滚动情况
			if (data.get(i).get("JSBH").equals(data.get(i).get("FSRBH")))
			{
				holder = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_js, parent, false);
				((TextView) holder.findViewById(R.id.mc)).setText(teacher_name.toString().split(";")[0]);
			} else
			{
				holder = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_xs, parent, false);
				((TextView) holder.findViewById(R.id.mc)).setText(student_name);
			}
			/** 得到各个控件的对象 */

			((TextView) holder.findViewById(R.id.nr)).setText(data.get(i).get("ZTNR"));
			((TextView) holder.findViewById(R.id.rq)).setHint(data.get(i).get("FSSJ"));
			viewHolders.add(holder);
		}
		return viewHolders;
	}

	public void getLoadViewList(int pageNum)
	{
		List<View> list = new ArrayList<View>();
		// for (int i = ViewList.size() - 1 - pageSize * pageNum; i >= 0 && i >
		// ViewList.size() - 1 - pageSize * pageNum - 10; i--)
		// {
		// list.add(ViewList.get(i));
		// }

		for (int i = pageSize; i >= 0; i--)
		{
			if (ViewList.size() - 1 - pageSize * (pageNum + 1) + i >= 0)
			{
				list.add(ViewList.get(ViewList.size() - 1 - pageSize * (pageNum + 1) + i));
			}
		}
		addNum = list.size();
		nowPage++;
		nowViewList.addAll(list);
	}

	private int addNum;

	public void add(View view)
	{
		nowViewList.add(view);
		listView.setSelection(1);
	}
}