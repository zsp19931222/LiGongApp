package yh.app.contacts;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.utils.GlideLoadUtils;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.activitytool.ActivityPortrait;
import com.yhkj.cqgyxy.R;
import yh.app.quanzitool.pinyin;
import yh.app.tool.DpPx;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具._链接地址导航;

public class WDBJXQJM extends ActivityPortrait
{
	private GridView gridView;

	private String title;
	private LoadDiaog diaog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wdbjxqjm);
		gridView = (GridView) findViewById(R.id.gridView);
		diaog=new LoadDiaog(this);
		title = new SqliteHelper().rawQuery("select classname from classlist where userid=? and id=?", Constants.number, getIntent().getStringExtra("function_id")).get(0).get("classname");
		new DefaultTopBar(this).doit(title);
		
		 initData();
//		new ThreadPoolManage().addNewPostTask(_链接地址导航.PushServer.getXSBJLBXQ.getUrl(), MapTools.buildMap(new String[][]
//		{
//				{
//						"kcid", getIntent().getStringExtra("function_id")
//				},
//				{
//						"userid", Constants.number
//				}
//		}), handler);
	}
	private void  initData(){
		diaog.show();
		Map<String, String> params=new HashMap<>();
		params.put("kcid", getIntent().getStringExtra("function_id"));
		params.put("userid", Constants.number);
		VolleyRequest.RequestPost(_链接地址导航.GroupChat.getXSBJLBXQ.getUrl(), params, new VolleyInterface() {
			
			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				try
				{
					JSONObject jso = new JSONObject(result);
					JSONArray userlist = jso.getJSONObject("content").getJSONArray("userlist");
					JSONArray detaillist = jso.getJSONObject("content").getJSONArray("detaillist");
					gridView.setAdapter(new BJXQAdapter(userlist));
					setListViewHeightBasedOnChildren(gridView);
					setClassDetail(detaillist);
					List<Object[]> parames = new ArrayList<>();
					for (int i = 0; i < userlist.length(); i++)
					{
						parames.add(new Object[]
						{
								getIntent().getStringExtra("function_id"), userlist.getJSONObject(i).getString("userid"), userlist.getJSONObject(i).getString("name"), userlist.getJSONObject(i).getString("faceaddress"), userlist.getJSONObject(i).getString("usertype"), userlist.getJSONObject(i).getString("sex_id"), pinyin.getAllLetter(userlist.getJSONObject(i).getString("name"))
						});

					}
					new SqliteHelper().execSQL("delete from classmember");
					new SqliteHelper().insert("insert into classmember(kcid ,userid ,name ,faceaddress ,usertype ,sex_id ,pinyin) values(?,?,?,?,?,?,?)", parames);
					gridView.setOnItemClickListener(new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id)
						{
							Intent intent = new Intent();
							if (position == parent.getAdapter().getCount() - 1)
							{
								intent.setAction(yh.app.contacts.CantactClassMemberAll.class.getName());
								intent.putExtra("kcid", getIntent().getStringExtra("function_id"));
								intent.putExtra("title", title);
							} else
							{
								intent.setAction(yh.app.contacts.UserDetail.class.getName());
								intent.putExtra("hybh", view.getTag().toString());
							}
							intent.setPackage(WDBJXQJM.this.getPackageName());
							WDBJXQJM.this.startActivity(intent);
						}
					});
				} catch (Exception e)
				{
				}
			}
			
			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				if (diaog.isShowing()) {
					diaog.dismiss();
				}
			}
		});
	}

//	private Handler handler = new Handler()
//	{
//		@Override
//		public void handleMessage(android.os.Message msg)
//		{
//			try
//			{
//				JSONObject jso = new JSONObject(msg.getData().getString("msg"));
//				JSONArray userlist = jso.getJSONObject("content").getJSONArray("userlist");
//				JSONArray detaillist = jso.getJSONObject("content").getJSONArray("detaillist");
//				gridView.setAdapter(new BJXQAdapter(userlist));
//				setListViewHeightBasedOnChildren(gridView);
//				setClassDetail(detaillist);
//				List<Object[]> parames = new ArrayList<>();
//				for (int i = 0; i < userlist.length(); i++)
//				{
//					parames.add(new Object[]
//					{
//							getIntent().getStringExtra("function_id"), userlist.getJSONObject(i).getString("userid"), userlist.getJSONObject(i).getString("name"), userlist.getJSONObject(i).getString("faceaddress"), userlist.getJSONObject(i).getString("usertype"), userlist.getJSONObject(i).getString("sex_id"), pinyin.getAllLetter(userlist.getJSONObject(i).getString("name"))
//					});
//
//				}
//				new SqliteHelper().execSQL("delete from classmember");
//				new SqliteHelper().insert("insert into classmember(kcid ,userid ,name ,faceaddress ,usertype ,sex_id ,pinyin) values(?,?,?,?,?,?,?)", parames);
//				gridView.setOnItemClickListener(new OnItemClickListener()
//				{
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//					{
//						Intent intent = new Intent();
//						if (position == parent.getAdapter().getCount() - 1)
//						{
//							intent.setAction(yh.app.contacts.CantactClassMemberAll.class.getName());
//							intent.putExtra("kcid", getIntent().getStringExtra("function_id"));
//							intent.putExtra("title", title);
//						} else
//						{
//							intent.setAction(yh.app.contacts.UserDetail.class.getName());
//							intent.putExtra("hybh", view.getTag().toString());
//						}
//						intent.setPackage(WDBJXQJM.this.getPackageName());
//						WDBJXQJM.this.startActivity(intent);
//					}
//				});
//			} catch (Exception e)
//			{
//			}
//		}
//
//		
//	};
	
	
	
	private void setClassDetail(JSONArray detaillist)
	{
		if (detaillist == null)
			return;
		LinearLayout layout = (LinearLayout) findViewById(R.id.wdbjxq_layout);
		for (int i = 0; i < detaillist.length(); i++)
		{
			try
			{
				View item = ViewTools.getView(WDBJXQJM.this, R.layout.wdbjxqjm_item, layout);
				((TextView) item.findViewById(R.id.name)).setText(detaillist.getJSONObject(i).getString("name"));
				((TextView) item.findViewById(R.id.value)).setText(detaillist.getJSONObject(i).getString("value"));
				layout.addView(item);
			} catch (Exception e)
			{
			}
		}
	}

	public void setListViewHeightBasedOnChildren(GridView gridView)
	{

		// 获取listview的adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null)
		{
			return;
		}
		// 固定列宽，有多少列
		int totalHeight = ((listAdapter.getCount() - 1) / gridView.getNumColumns() + 1) * new DpPx(WDBJXQJM.this).getDpToPx(80) + new DpPx(WDBJXQJM.this).getDpToPx(gridView.getNumColumns() * 5 + 5);

		// 获取listview的布局参数
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		// 设置高度
		params.height = totalHeight;
		// 设置参数
		gridView.setLayoutParams(params);
	}

	private class BJXQAdapter extends BaseAdapter
	{
		private JSONArray jsa = new JSONArray();
		private Map<Integer, View> viewlist = new HashMap<>();

		public BJXQAdapter(JSONArray jsa)
		{
			for (int i = 0; i < 20 && i < jsa.length(); i++)
				try
				{
					this.jsa.put(jsa.get(i));
				} catch (Exception e)
				{
				}
		}

		public int getCount()
		{
			return jsa.length();
		}

		public Object getItem(int position)
		{
			return null;
		}

		public long getItemId(int position)
		{
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent)
		{
			if (convertView == null)
			{
				convertView = getContentItem(position, convertView, parent);
			}
			try
			{
				if (viewlist.get(position) == null)
					return getContentItem(position, convertView, parent);
				else
					return viewlist.get(position);
			} catch (Exception e)
			{
				return getContentItem(position, convertView, parent);
			}

		}

		public View getContentItem(int position, View convertView, ViewGroup parent)
		{
			if (viewlist.get(position) == null)
			{
				convertView = ViewTools.getView(WDBJXQJM.this, R.layout.wdbjxqjm_user_item, parent);
				try
				{
					if (position == getCount() - 1)
					{
						convertView.findViewById(R.id.face).setBackgroundResource(R.drawable.more_syc);
						((TextView) convertView.findViewById(R.id.name)).setText("更多");
					} else
					{
						if (jsa.getJSONObject(position).getString("usertype").equals("2"))
						{
							((ImageView) convertView.findViewById(R.id.face)).setImageBitmap(BitmapFactory.decodeResource(WDBJXQJM.this.getResources(), R.drawable.jsbj));
						}
						convertView.setTag(jsa.getJSONObject(position).getString("userid"));
						((TextView) convertView.findViewById(R.id.name)).setText(jsa.getJSONObject(position).getString("name"));

						GlideLoadUtils.getInstance().glideLoad(WDBJXQJM.this, jsa.getJSONObject(position).getString("faceaddress"),((ImageView) convertView.findViewById(R.id.face)), R.drawable.ico_load_little);

//						ImageAtNotSave at = new ImageAtNotSave(WDBJXQJM.this, (ImageView) convertView.findViewById(R.id.face), jsa.getJSONObject(position).getString("faceaddress"));
//						at.execute();
						if (diaog.isShowing()) {
							diaog.dismiss();
						}
					}
				} catch (JSONException e)
				{
					convertView.findViewById(R.id.face).setBackgroundResource(R.drawable.q1);
				}
			}
			viewlist.put(position, convertView);
			return convertView;
		}
	}
}