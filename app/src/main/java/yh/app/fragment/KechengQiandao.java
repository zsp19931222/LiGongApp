package yh.app.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yunhuakeji.app.utils.Home1ItemAdapter;
import com.yunhuakeji.app.utils.JsonReaderHelper;
import yh.app.appstart.lg.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.adapter.KeChenQianDaoAdapter;
import org.androidpn.push.Constants;

import yh.app.model.AnShiGuiQingModel.ContentBean.MessageBean;
import yh.app.model.KeChenQianDaoModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

public class KechengQiandao extends Fragment implements OnClickListener {
	private ListView listView;
	private TextView djz;
	private TextView time;// 日期
	private KeChenQianDaoModel kechenqiandaoModel;
	private List<KeChenQianDaoModel.ContentBean.MessageBean.DataBean> list;
	private KeChenQianDaoAdapter keChenQianDaoAdapter;

	private JSONObject jso;

	Map<Integer, View> mapView = new HashMap<>();

	private Activity activity;

	private String ks, js, xkkh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragemtn_kechengqiandao, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();

		initData();
	}

	private void initView() {
		listView = (ListView) getView().findViewById(R.id.listview_course_activity_listview);
		djz = (TextView) getView().findViewById(R.id.djz);
		time = (TextView) getView().findViewById(R.id.time);
	}

	// 签到
	private void onSubmit(String ks, String js, String xkkh) {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		params.put("ks", ks);
		params.put("js", js);
		params.put("xkkh", xkkh);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.sumitqiandao.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub

				kechenqiandaoModel = ExampleApplication.getInstance().getGson().fromJson(result,
						KeChenQianDaoModel.class);
				Constants.ticket = kechenqiandaoModel.getContent().getTicket();
				if (kechenqiandaoModel.getContent().getStatus().equals("true")) {
					Toast.makeText(activity, "签到成功", Toast.LENGTH_SHORT).show();
					initData();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "签到失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 获得课程列表
	 * 
	 * @param ticket
	 */
	public void initData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.kechengqiandaoinfo.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
			

				kechenqiandaoModel = ExampleApplication.getInstance().getGson().fromJson(result,
						KeChenQianDaoModel.class);
				//数据为空不进行绑定
				if (kechenqiandaoModel.getContent().getMessage().getDjz()!=null) {
					djz.setText(kechenqiandaoModel.getContent().getMessage().getDjz());
					time.setText(kechenqiandaoModel.getContent().getMessage().getXqj()
							+ kechenqiandaoModel.getContent().getMessage().getRq());
					Constants.ticket = kechenqiandaoModel.getContent().getTicket();

					bindData();
				}else{
					Toast.makeText(activity, "当前没有可签到课程", Toast.LENGTH_SHORT).show();
				}
					

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "课程获取失败", Toast.LENGTH_SHORT).show();
			}
		});

		// try
		// {
		// jso = new JSONObject(JsonReaderHelper.getJosn(activity,
		// "kcqdlb.json"));
		//
		// if ("40001".equals(jso.getString("code")))
		// {
		// JSONArray jsa = jso.optJSONObject("content").getJSONArray("data");
		// for (int i = 0; i < jsa.length(); i++)
		// {
		// View view =
		// LayoutInflater.from(activity).inflate(R.layout.yh_home_course_activity_item,
		// null, false);
		//
		// mapView.put(i, view);
		// }
		// }
		// listView.setAdapter(new Home1ItemAdapter(mapView));
		//
		// } catch (Exception e)
		// {
		// }
		// listView.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		// long arg3) {
		// // TODO Auto-generated method stub
		// Toast.makeText(activity, arg2, 0).show();
		// }
		// });

	}

	private void bindData() {
		list = kechenqiandaoModel.getContent().getMessage().getData();
		keChenQianDaoAdapter = new KeChenQianDaoAdapter(activity, list);

		listView.setAdapter(keChenQianDaoAdapter);
		keChenQianDaoAdapter.setOnQianDao(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Object tag = v.getTag();
		switch (v.getId()) {
		case R.id.btn_kcqd:
			if (null != tag && tag instanceof Integer) {
				int posin = (int) tag;
				ks = list.get(posin).getKs();
				js = list.get(posin).getJs();
				xkkh = list.get(posin).getXkkh();
				if (list.get(posin).getOperate().equals("0")) {
					onSubmit(ks, js, xkkh);
				} else {
					Toast.makeText(activity, "现在还不能签到", Toast.LENGTH_SHORT).show();
				}

			}
			break;

		}
	}

}
