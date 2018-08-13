package yh.app.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import yh.app.adapter.MoreZhiZhuMuBiaoCompletedAdapter;
import org.androidpn.push.Constants;

import yh.app.model.MoreZhiZhuMuBiaoModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;
/**
 * 已完成任务
 * @author 云华科技
 * @date 2017年4月28日
 */
public class CompletedFragment extends Fragment {
	private Activity activity;
	private List<MoreZhiZhuMuBiaoModel.ContentBean.MessageBean.CompletedBean> list;
	private MoreZhiZhuMuBiaoModel completeModel;
	private MoreZhiZhuMuBiaoCompletedAdapter completeadapter;
	private ListView listview_fragment;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_listview, container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		listview_fragment=(ListView) getView().findViewById(R.id.listview_fragment);
		initData();
	}
	/**
	 * 初始化数据
	 */
	private void initData(){
		Map<String,String> params=new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.MoreZhiZhuMuBiao.getUrl(), params, new VolleyInterface() {
			
			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				if (null!=result) {
					completeModel=ExampleApplication.getInstance().getGson().fromJson(result,MoreZhiZhuMuBiaoModel.class);
					if (completeModel.getContent().getMessage().getCompleted().size()>0) {
						list=completeModel.getContent().getMessage().getCompleted();
						completeadapter=new MoreZhiZhuMuBiaoCompletedAdapter(activity, list);
						listview_fragment.setAdapter(completeadapter);
					}else{
						Toast.makeText(activity, "暂无任务", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
			
			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
