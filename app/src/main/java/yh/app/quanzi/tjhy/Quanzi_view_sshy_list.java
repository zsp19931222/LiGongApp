package yh.app.quanzi.tjhy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.androidpn.push.Constants;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yh.app.appstart.lg.R;
import yh.app.contacts.UserDetail;
import yh.app.tool.InternetImage;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.ThreadPoolManage;

public class Quanzi_view_sshy_list
{
	private Context context;
	private int parent;
	private ListView list;
	private List<Map<String, String>> Maplist = new ArrayList<Map<String, String>>();
	private Quanzi_tools_添加好友 tools;
	private String cxtj;

	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				unLoading();
				if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean"))
				{
					// initListView();
					Maplist.addAll(JsonTools.getListMapBtJsonArray(new JSONObject(msg.getData().getString("msg")).getJSONArray("message")));
					((MySimpleAdater) list.getAdapter()).fresh();
				} else
				{
					new ToastShow().showImageToast(context, "找不到人啦，换个姿势试试吧", parent);
				}
			} catch (Exception e)
			{
				new ToastShow().showImageToast(context, "找不到人啦，换个姿势试试吧", parent);
			}
		};
	};

	public Quanzi_view_sshy_list(Context context, int parent, ListView list, Quanzi_tools_添加好友 tools, String cxtj)
	{

		this.cxtj = cxtj;
		this.tools = tools;
		this.list = list;
		this.parent = parent;
		this.context = context;
		initListView();
		Loading();
	}

	public void unLoading()
	{
		Loadding = false;
		((Activity) context).findViewById(R.id.zzjz).setVisibility(View.GONE);
		((TextView) ((Activity) context).findViewById(R.id.load_state)).setHint("加载完成");
	}

	public void Loading()
	{
		Loadding = true;
		((Activity) context).findViewById(R.id.zzjz).setVisibility(View.VISIBLE);
		((TextView) ((Activity) context).findViewById(R.id.load_state)).setHint("正在加载");
	}

	public void doit()
	{
		tools._搜索好友(Constants.number, "20150120", Ticket.getFunctionTicket("20150120"), cxtj, 1, handler);
	}

	private MySimpleAdater msa;
	private boolean Loadding = false;

	private void initListView()
	{
		msa = new MySimpleAdater(null, null, 0, null, null);
		list.setAdapter(msa);
		list.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				if (firstVisibleItem + visibleItemCount + 2 >= totalItemCount && !Loadding)
				{
					Loading();
					tools._搜索下一页好友(Constants.number, "20150120", Ticket.getFunctionTicket("20150120"), cxtj, handler);
				}
			}
		});
		//点击事件
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				//跳转到好友详情
				Map<String, String> map = Maplist.get(position);
				Intent intent = new Intent(context,UserDetail.class);
				intent.putExtra("hybh", map.get("USERID"));
//				intent.putExtra("xm", map.get("XM"));
//				intent.putExtra("xb", map.get("XB"));
//				intent.putExtra("sr", map.get("BIRTHDAY")); 
//				intent.putExtra("bm", map.get("BMMC"));
//				intent.putExtra("qq", map.get("QQ"));
//				intent.putExtra("em", map.get("EM"));
				context.startActivity(intent);
			}
		});
	}

	private List<View> Viewlist = new ArrayList<View>();

	class MySimpleAdater extends SimpleAdapter
	{
//		"USERTYPE"
		public MySimpleAdater(Context context1, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)
		{
			super(context, Maplist, R.layout.sshy_sub, new String[]
			{
				"BMMC","USERTYPE","XM"
			}, new int[]
			{
				R.id.sshy_bmmc,R.id.sshy_name, R.id.sshy_nc
			});
		}

		public void fresh()
		{
			notifyDataSetChanged();
		}
	}

	class MyBaseAdapter extends BaseAdapter
	{

		public MyBaseAdapter(List<Map<String, String>> hylb)
		{
			Viewlist = getViewList(hylb);
		}

		@Override
		public int getCount()
		{
			return Viewlist.size();
		}

		@Override
		public Object getItem(int position)
		{
			try
			{
				return Viewlist.get(position);
			} catch (Exception e)
			{
			}
			return null;
		}

		@Override
		public long getItemId(int position)
		{
			try
			{
				return Viewlist.get(position).getId();
			} catch (Exception e)
			{
				return 0;
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			try
			{
				return (View) getItem(position);
			} catch (Exception e)
			{
			}
			return null;
		}

		private List<View> getViewList(List<Map<String, String>> hylb)
		{
			Viewlist = new ArrayList<View>();
			for (int i = 0; i < hylb.size(); i++)
			{
				try
				{
					Viewlist.add(getView1(hylb.get(i)));
				} catch (Exception e)
				{
				}
			}
			return Viewlist;
		}

		private View getView1(Map<String, String> hyxx)
		{
			View view = LayoutInflater.from(context).inflate(R.layout.sshy_sub, (ViewGroup) ((Activity) context).findViewById(parent), false);
			((TextView) view.findViewById(R.id.sshy_bmmc)).setText(hyxx.get("BMMC"));
			((TextView) view.findViewById(R.id.sshy_nc)).setText(hyxx.get("USERTYPE"));
			((TextView) view.findViewById(R.id.sshy_name)).setText(hyxx.get("XM"));
			view.setTag(hyxx);
			new InternetImage(((ImageView) view.findViewById(R.id.sshy_tx)), hyxx.get("FACEADDRESS")).executeOnExecutor(ThreadPoolManage.executor, "");
			view.setOnClickListener(new OnClickListener()
			{
				@SuppressWarnings("unchecked")
				@Override
				public void onClick(View v)
				{
					Map<String, String> map = (Map<String, String>) v.getTag();
					Intent intent = new Intent();
					intent.setAction("yh.app.quanzi.tjhyxx");
					intent.setPackage(context.getPackageName());
					intent.putExtra("userid", map.get("USERID"));
					intent.putExtra("xm", map.get("XM"));
					intent.putExtra("xb", map.get("XB"));
					intent.putExtra("sr", map.get("BIRTHDAY"));
					intent.putExtra("bm", map.get("BMMC"));
					intent.putExtra("qq", map.get("QQ"));
					intent.putExtra("em", map.get("EM"));
					context.startActivity(intent);
				}
			});
			return view;
		}

		public void add(List<Map<String, String>> mapList)
		{
			Viewlist.addAll(getViewList(mapList));
			list.deferNotifyDataSetChanged();
		}
	}
}
