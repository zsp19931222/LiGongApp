package yh.app.acticity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
import yh.app.adapter.MuBiaoKuContentAdapter;
import yh.app.adapter.MuBiaoKuTitleListAdapter;
import org.androidpn.push.Constants;
import yh.app.model.MuBiaoContent;
import yh.app.model.MuBiaoKuModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.HorizontialListView;
import yh.tool.widget.IntegrationActivity;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 目标库
 * 
 * @author Administrator
 *
 */
public class MubiaokuActivity extends IntegrationActivity implements OnScrollListener {
	// 目标库标题
	private HorizontialListView list_mubiaoku;
	private MuBiaoKuTitleListAdapter mubiaokutitleAdapter;
	private List<MuBiaoKuModel.ContentBean.MessageBean> list;// 目标库标题
	private MuBiaoKuModel mubiaokuModel;// 目标库标题

	// 目标库任务内容
	private ListView list_mubiaoku_content;
	private MuBiaoContent mubiaokuContentModel;
	private List<MuBiaoContent.ContentBean.MessageBean.RowsBean> listMubiaokucontent;
	private MuBiaoKuContentAdapter mubiaokucontentAdapter;

	// 上拉刷新
	public LayoutInflater footinflater;
	public int last_index;
	public int total_index;
	public boolean isLoading = false;// 表示是否正处于加载状态
	private View footloadView;// 底部加载视图
	private LoadDiaog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mubiaoku);
		footinflater = LayoutInflater.from(this);
		initView();
		initData();
		getMubiaokuContent("1");

	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		mubiaokuModel = new MuBiaoKuModel();
		mubiaokuContentModel = new MuBiaoContent();
		list_mubiaoku = (HorizontialListView) findViewById(R.id.list_mubiaoku);
		list_mubiaoku_content = (ListView) findViewById(R.id.list_mubiaokucontent);
		dialog = new LoadDiaog(this);
		dialog.show();

		footloadView = footinflater.inflate(R.layout.footer_layout, null, false);// 得到刷新视图
		list_mubiaoku_content.setOnScrollListener(this);
		list_mubiaoku_content.addFooterView(footloadView, null, false);// 添加刷新视图到listView底部
		footloadView.setVisibility(View.GONE);// 设置刷新视图默认情况下是不可见的

		list_mubiaoku.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(MubiaokuActivity.this, list.get(arg2).getName(), 0).show();
				mubiaokutitleAdapter.setChengeTextColor(arg2);
				mubiaokutitleAdapter.notifyDataSetChanged();
				getMubiaokuContent(list.get(arg2).getType());
			}
		});

		list_mubiaoku_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				String bz = listMubiaokucontent.get(arg2).getTarget_operate();
				if (bz.equals("0")) {
					bz = "1";
				} else {
					bz = bz;
				}
				Intent intentmubiaomu = new Intent(MubiaokuActivity.this, TargetDetailActivity.class);
				intentmubiaomu.putExtra("userid", Constants.number);
				intentmubiaomu.putExtra("ticket", Constants.ticket);
				intentmubiaomu.putExtra("targetID", listMubiaokucontent.get(arg2).getTarget_id());
				intentmubiaomu.putExtra("bz", bz);
				startActivity(intentmubiaomu);
			}
		});
	}

	/**
	 * 初始化视图
	 */
	private void initData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getmubiaokutitle.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				if (null != result) {
					JSONObject jsonObject;
					String code = null;
					try {
						jsonObject = new JSONObject(result);
						code = jsonObject.getString("code");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(code)) {
						// 请求成功
						mubiaokuModel = ExampleApplication.getInstance().getGson().fromJson(result,
								MuBiaoKuModel.class);
						// 装入集合
						list = mubiaokuModel.getContent().getMessage();
						// 更新票据
						Constants.ticket = mubiaokuModel.getContent().getTicket();
						mubiaokutitleAdapter = new MuBiaoKuTitleListAdapter(MubiaokuActivity.this, list);
						list_mubiaoku.setAdapter(mubiaokutitleAdapter);

					}

				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub

			}
		});

	}

	/**
	 * 获得目标任务
	 * 
	 * @param type
	 */
	private void getMubiaokuContent(String type) {
		dialog.show();
		if (listMubiaokucontent != null) {
			listMubiaokucontent.clear();
		}
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		map.put("type", type);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getmubiaocontent.getUrl(), map, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub

				if (null != result) {
					JSONObject jsonObject;
					String code = null;
					try {
						jsonObject = new JSONObject(result);
						code = jsonObject.getString("code");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(code)) {
						// 请求成功
						mubiaokuContentModel = ExampleApplication.getInstance().getGson().fromJson(result,
								MuBiaoContent.class);
						// 装入集合
						listMubiaokucontent = mubiaokuContentModel.getContent().getMessage().getRows();
						// 更新票据
						Constants.ticket = mubiaokuModel.getContent().getTicket();
						mubiaokucontentAdapter = new MuBiaoKuContentAdapter(MubiaokuActivity.this, listMubiaokucontent);
						list_mubiaoku_content.setAdapter(mubiaokucontentAdapter);
						if (dialog.isShowing()) {
							dialog.dismiss();
						}

					}

				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(MubiaokuActivity.this, "目标获取失败", Toast.LENGTH_SHORT).show();
			}
		});

	}

	// 退出当前
	public void back(View view) {
		finish();
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		last_index = arg1 + arg2;
		total_index = arg3;

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// TODO Auto-generated method stub
		// 滑动到最后一条时做刷新操作
		if (last_index == total_index && (arg1 == AbsListView.OnScrollListener.SCROLL_STATE_IDLE)) {
			// 表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
			if (!isLoading) {
				// 不处于加载状态的话对其进行加载
				isLoading = true;
				// 设置刷新界面可见
				footloadView.setVisibility(View.VISIBLE);
				onLoad();
			}
		}

	}

	/**
	 * 刷新加载 数据
	 * 
	 */
	public void onLoad() {
		try {
			// 延时加载避免加载频率过快
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/**
		 * 加载下一页 更新数据操作
		 */
		loadOver();// 刷新结束
	}

	/**
	 * 加载完成
	 */
	public void loadOver() {
		footloadView.setVisibility(View.GONE);// 设置刷新界面不可见
		isLoading = false;// 设置正在刷新标志位false
		this.invalidateOptionsMenu();
		list_mubiaoku_content.removeFooterView(footloadView);// 加载到最后一一页移除刷新视图
	}

}
