package yh.app.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.google.gson.JsonObject;
import com.yunhuakeji.app.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.model.YunDongModel;
import yh.app.utils.JiBuUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

public class JinRiBuShuFragement extends Fragment{
	private Activity activity;

	private View bushuView;
	private TextView txt_jinribushu;// 今日步数
	private TextView txt_jinribushu_state;// 任务完成状态
	private TextView yh_txt_daily_djz;// 第几周
	private TextView yh_txt_daily_time;// 日期
	private TextView yh_txt_daily_title;// 提示标题
	private TextView yh_txt_daily_message;// 提示内容
	private TextView yh_txt_daily_can_sign_time;// 签到时间段
	private RelativeLayout yh_relativelayout_daily_sign;
	private String step;
	private YunDongModel yundongModel;
	private String maxstep;
	private List<Map<String, String>> listmap;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragement_jiribushu, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
		initData();
		initProcessor();

	}

	/**
	 * 初始化视图
	 */
	private void initView() {
          
		txt_jinribushu = (TextView) getView().findViewById(R.id.txt_jinribushu);// 步数

		txt_jinribushu_state = (TextView) getView().findViewById(R.id.txt_jinribushu_state);// 签到状态
		yh_txt_daily_djz = (TextView) getView().findViewById(R.id.yh_txt_daily_djz);// 显示周
		yh_txt_daily_time = (TextView) getView().findViewById(R.id.yh_txt_daily_time);// 显示日期
		yh_txt_daily_title = (TextView) getView().findViewById(R.id.yh_txt_daily_title);// 提示标题
		yh_txt_daily_message = (TextView) getView().findViewById(R.id.yh_txt_daily_message);// 提示内容
		yh_txt_daily_can_sign_time = (TextView) getView().findViewById(R.id.yh_txt_daily_can_sign_time);// 签到时间
		yh_relativelayout_daily_sign=(RelativeLayout) getView().findViewById(R.id.yh_relativelayout_daily_sign);//点击签到
		if (step!=null) {
			txt_jinribushu.setText(step);
		}
		
	}

	private void initData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.YunDongInfo.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				yundongModel = ExampleApplication.getInstance().getGson().fromJson(result, YunDongModel.class);
				if (yundongModel.getContent().getMessage().getDjz()!=null) {
					bindData(yundongModel.getContent().getMessage());
				}else{
					Toast.makeText(activity, "暂无运动数据", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(activity, "坚持运动失败", Toast.LENGTH_SHORT).show();
			}
		});
	}
     String bz;
	private void bindData(YunDongModel.ContentBean.MessageBean bean) {
		yh_txt_daily_djz.setText(bean.getDjz());
		yh_txt_daily_time.setText(bean.getXqj()+bean.getRq());
		yh_txt_daily_can_sign_time.setText("00:00-24:00");
		yh_txt_daily_title.setText(bean.getTitle());
		yh_txt_daily_message.setText(bean.getDescribe());
		bz=bean.getBz();
		if (bz.equals("0")) {
			 
		}else{
			if (bz.equals("-1")) {
				
			}else{
				 txt_jinribushu_state.setText("已签到");
			}
		}
		maxstep=bean.getSportNumber();
		
		yh_relativelayout_daily_sign.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Integer.valueOf(step)>=Integer.valueOf(maxstep)) {
					//大于最大步数才可以签到
					submitYunDongData();
				}else{
					Toast.makeText(activity, "今日步数未达标", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	// 提交数据
	private void submitYunDongData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		params.put("total", "7000");
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.YunDongInfo.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject=new JSONObject(result);
					if (jsonObject.getBoolean("status")) {
						Toast.makeText(activity, "坚持运动提交成功" + result, Toast.LENGTH_SHORT).show();
						txt_jinribushu_state.setText("已签到");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "数据提交失败,请稍后重试", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 处理器
	 */
	private void initProcessor() {
		// 目标步数
		if (JiBuUtils.isSupportStepCountSensor(activity)) {
			//当满足条件才查询
			 listmap = ExampleApplication.getInstance().getSqliteHelper()
					.rawQuery("select stepSum from step where userid=?", new String[] { Constants.number });
			
			if (listmap.size() > 0) {
				step =listmap.get(0).get("stepSum");
				txt_jinribushu.setText(step);
				if (Integer.valueOf(step)>= 10) {
					txt_jinribushu_state.setText("今日步数达到7000步可以签到");
					txt_jinribushu_state.setTextColor(getResources().getColor(R.color.button));

				}

			}
			// delayHandler = new Handler(this);
			/**
			 * 开启计步服务
			 */
			// Intent intent = new Intent(activity, JiBuSerever.class);
			// isBind = activity.bindService(intent, conn,
			// Context.BIND_AUTO_CREATE);
			// activity.startService(intent);
		} else {
			txt_jinribushu_state.setTextColor(getResources().getColor(R.color.color_red));
			txt_jinribushu_state.setText("抱歉你的设备暂不支持计步功能");

		}
	}

	
}
