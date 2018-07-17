package yh.app.fragment;

import yh.app.adapter.MoreZhiZhuMuBiaoCompletedAdapter;
import yh.app.adapter.MoreZhiZhuMuBiaoUnderwayAdapter;
import org.androidpn.push.Constants;

import yh.app.model.MoreZhiZhuMuBiaoModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 * 自主目标进行中
 * @author 云华科技
 * @date 2017年4月28日
 */
public class UnderwayFragment extends Fragment {
	private Activity activity;
	private List<MoreZhiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list;
	private MoreZhiZhuMuBiaoModel completeModel;
	private MoreZhiZhuMuBiaoUnderwayAdapter underwaydapter;
	private ListView listview_fragment;
         @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        	// TODO Auto-generated method stub
        	return inflater.inflate(R.layout.fragment_listview, container,false);
        }

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		initView(); 
		initData();
	}
	
	private void initView(){
		listview_fragment=(ListView) getView().findViewById(R.id.listview_fragment);
		listview_fragment.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
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
					if (completeModel.getContent().getMessage().getOngoing().size()>0) {
						list=completeModel.getContent().getMessage().getOngoing();
						underwaydapter=new MoreZhiZhuMuBiaoUnderwayAdapter(activity, list);
						listview_fragment.setAdapter(underwaydapter);
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
