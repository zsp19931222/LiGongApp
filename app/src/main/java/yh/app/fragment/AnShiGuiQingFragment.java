package yh.app.fragment;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidpn.push.Constants;
import yh.app.model.AnShiGuiQingModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 按时归寝
 * 
 * @author anmin
 *
 */
public class AnShiGuiQingFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private RelativeLayout rlyout_guiqianqiandao;
	private ImageView img_anshigiqi;// 未签到是的图标
	private LinearLayout layout_wc;// 签到是要显示的事情
	private TextView txt_guiqinshijian;// 归寝时间
	private TextView txt_anshiguiqing_zhou;// 第几周
	private TextView txt_anshiguiqin_time;// 今天日期
	private TextView txt_anshiguiqing_state;// 签到状态
	private TextView yh_txt_daily_message;//提示内容
	private TextView yh_txt_daily_title;//提示标题
	private AnShiGuiQingModel anshiguiqinModel;
	private String bz;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anshiguiqing, container, false);

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
		// 点击签到
		rlyout_guiqianqiandao = (RelativeLayout) getView().findViewById(R.id.rlyout_guiqianqiandao);
		rlyout_guiqianqiandao.setOnClickListener(this);

		// 未签订显示图片
		img_anshigiqi = (ImageView) getView().findViewById(R.id.img_anshigiqi);
		// 已签到显示
		layout_wc = (LinearLayout) getView().findViewById(R.id.layout_wc);
		// 归寝时间
		txt_guiqinshijian = (TextView) getView().findViewById(R.id.txt_guiqinshijian);
		// 学期第几周
		txt_anshiguiqing_zhou = (TextView) getView().findViewById(R.id.txt_anshiguiqing_zhou);
		// 今日日期
		txt_anshiguiqin_time = (TextView) getView().findViewById(R.id.txt_anshiguiqin_time);
		// 签到状态
		txt_anshiguiqing_state = (TextView) getView().findViewById(R.id.txt_anshiguiqing_state);
		//提示标题
		yh_txt_daily_title=(TextView) getView().findViewById(R.id.yh_txt_daily_title);
		//提示内容
		yh_txt_daily_message=(TextView) getView().findViewById(R.id.yh_txt_daily_message);

	}

	// 初始化数据
	private void initData() {

		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.guiqindata.getUrl(), map, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "获得数据成功" + result, Toast.LENGTH_SHORT).show();
				
				anshiguiqinModel = ExampleApplication.getInstance().getGson().fromJson(result, AnShiGuiQingModel.class);
				if (anshiguiqinModel.getContent().getMessage().getDjz()!=null) {
					bindData(anshiguiqinModel.getContent().getMessage());
				}else{
					Toast.makeText(activity, "暂无归寝数据", Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub

				Toast.makeText(activity, "获得数据失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void bindData(AnShiGuiQingModel.ContentBean.MessageBean ben){
		txt_anshiguiqing_zhou.setText("签到时间"+ben.getStarttime()+"-"+ben.getEndtime());//签到时间段
		txt_anshiguiqin_time.setText(ben.getDjz()+ben.getRq());//显示日期
		yh_txt_daily_title.setText(ben.getTitle());//显示提示标题
		yh_txt_daily_message.setText(ben.getDescribe());//提示内容
		txt_anshiguiqing_state.setText(ben.getStatus());//签到状态
		bz=ben.getBz();
		if (bz.equals("0")) {
			 img_anshigiqi.setBackgroundResource(R.drawable.breakwc);
		}else{
			if (bz.equals("-1")) {
				 img_anshigiqi.setBackgroundResource(R.drawable.breakwc);
			}else{
				 img_anshigiqi.setBackgroundResource(R.drawable.breakyc);
			}
		}
		
	}

	/**
	 * 提交归寝签到
	 */
	private void subMitGuiQing() {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.guiqinqiandao.getUrl(), map, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "签到成功"+result, Toast.LENGTH_SHORT).show();
				img_anshigiqi.setVisibility(View.GONE);
				layout_wc.setVisibility(View.VISIBLE);
				txt_anshiguiqing_state.setText("获得20积分");
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "签到失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rlyout_guiqianqiandao:
			subMitGuiQing();
			
			break;

		default:
			break;
		}

	}

}
