package yh.app.fragment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.google.gson.Gson;
import com.yunhuakeji.app.utils.DateString;
import com.yunhuakeji.app.utils.DefaultDailyHelper;
import com.yunhuakeji.app.utils.JsonTools;
import com.yunhuakeji.app.utils.LocalDateFileHelper;
import com.yunhuakeji.app.utils.MD5Helper;
import com.yhkj.cqgyxy.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidpn.push.Constants;
import yh.app.model.JianKangZaoCanModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 健康早餐
 * 
 * @author anmin
 *
 */
public class JianKangZaoChanFragment extends Fragment {
	private JSONObject jso;

	private LocalDateFileHelper localDateFileHelper;

	private static final String BREAKED_NO = "0";

	private static final String BREAKED_YES = "1";

	private static final String FILE_NAME = MD5Helper
			.MD5(new DateString("yyyy-MM-dd").DateToString(new Date()) + "breakfast");

	private DefaultDailyHelper helper;

	public final String canSignTime = "";
	
	
	
	

	private Activity activity;

	private TextView yh_txt_daily_djz;// 第几周
	private TextView yh_txt_daily_time;// 今日日期
	private TextView yh_txt_daily_message;// 温馨提示
	private TextView yh_txt_daily_can_sign_time;// 签到时间
	private TextView yh_txt_daily_sign_msg;// 签到状态
	private ImageView img_anshigiqi;// 点击签到
	private TextView yh_txt_daily_title;// 提示标题
	private RelativeLayout yh_relativelayout_daily_sign;
	private String bz;
	private JianKangZaoCanModel jiankangModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_jiankanzaochan, container, false);
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
		yh_txt_daily_djz = (TextView) getView().findViewById(R.id.yh_txt_daily_djz);// 第几周
		yh_txt_daily_time = (TextView) getView().findViewById(R.id.yh_txt_daily_time);// 今日日期
		yh_txt_daily_message = (TextView) getView().findViewById(R.id.yh_txt_daily_message);// 温馨提示
		yh_txt_daily_can_sign_time = (TextView) getView().findViewById(R.id.yh_txt_daily_can_sign_time);// 签到时间
		yh_txt_daily_sign_msg = (TextView) getView().findViewById(R.id.yh_txt_daily_sign_msg);// 签到状态
		yh_txt_daily_title = (TextView) getView().findViewById(R.id.yh_txt_daily_title);
		img_anshigiqi = (ImageView) getView().findViewById(R.id.img_anshigiqi);
		img_anshigiqi.setBackgroundResource(R.drawable.breakwc);
		yh_relativelayout_daily_sign = (RelativeLayout) getView().findViewById(R.id.yh_relativelayout_daily_sign);

		
	}
	String djz=null;
	// "http://192.168.1.101/WDDXSERVER/homeapp/getHaveBreakfastInfo"
	private void initData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getHaveBreakfastInfo.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				jiankangModel = ExampleApplication.getInstance().getGson().fromJson(result, JianKangZaoCanModel.class);
				
				
				if (jiankangModel.getContent().getMessage().getDjz()!=null) {
					binData(jiankangModel.getContent().getMessage());
//					Constants.ticket=jiankangModel.getContent().getTicket();
				}else{
					Toast.makeText(activity, "暂无可签到早餐", Toast.LENGTH_SHORT).show();
				}
				
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, "获取早餐数据失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 绑定数据
	 */
	
	private void binData(JianKangZaoCanModel.ContentBean.MessageBean bean) {
		bz=bean.getBz();
		yh_txt_daily_djz.setText(bean.getDjz());
		 yh_txt_daily_time.setText(bean.getXqj()+bean.getRq());
		 yh_txt_daily_sign_msg.setText(bean.getStatus());
		 yh_txt_daily_title.setText(bean.getTitle());
		 yh_txt_daily_message.setText(bean.getDescribe());
		 yh_txt_daily_can_sign_time.setText("签到时间"+bean.getStarttime()+"-"+bean.getEndtime());
		 if (bz.equals("0")) {
			 img_anshigiqi.setBackgroundResource(R.drawable.breakwc);
		}else{
			if (bz.equals("-1")) {
				 img_anshigiqi.setBackgroundResource(R.drawable.breakwc);
			}else{
				 img_anshigiqi.setBackgroundResource(R.drawable.breakyc);
			}
		}
		 
		// 点击签到
			yh_relativelayout_daily_sign.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 if (bz.equals("0")) {
						 submitBreakInof();
					 }else{
						 Toast.makeText(activity, "不能签到了", Toast.LENGTH_SHORT).show();
					 }
					
				}
			});

	}

	/**
	 * 提交用户信息
	 */
	private void submitBreakInof() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.ZaoCanQianDao.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				img_anshigiqi.setBackground(getResources().getDrawable(R.drawable.breakyc));
				Toast.makeText(activity, R.string.str_jiankanzaochan_qiandao_succes + result, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, R.string.str_jiankanzaochan_qiandao_error, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	

	 public void setBreakUI(JSONObject jso)
	 {
	 localDateFileHelper.setLocalString(jso.toString());
	
	 String breakfastMessage = "";
	 String breakfastTime = "";
	
	 View yh_view_breakfast_sign_button =
	 LayoutInflater.from(activity).inflate(R.layout.yh_view_breakfast_sign_button,
	 (RelativeLayout)
	 helper.getViews().get(R.id.yh_relativelayout_daily_sign), false);
	
	 if (BREAKED_NO.equals(JsonTools.getString(jso, new String[]
	 { "bz" })[0]))
	 {
	 breakfastMessage = JsonTools.getString(jso, "", new String[]
	 { "status" })[0];
//	 yh_view_breakfast_sign_button.setOnClickListener(this);
	 yh_view_breakfast_sign_button.findViewById(R.id.yh_button_breakfast_sign_button).setBackgroundResource(R.drawable.breakwc);
	 } else if (BREAKED_YES.equals(JsonTools.getString(jso, new String[]
	 { "bz" })[0]))
	 {
	 breakfastMessage = JsonTools.getString(jso, "", new String[]
	 { "status" })[0];
	 breakfastTime = JsonTools.getString(jso, "", new String[]
	 { "time" })[0];
	 yh_view_breakfast_sign_button.setOnClickListener(null);
	 yh_view_breakfast_sign_button.findViewById(R.id.yh_button_breakfast_sign_button).setBackgroundResource(R.drawable.breakyc);
	 }
	
	 helper.doDefaultHelper(JsonTools.getString(jso, "", new String[]
	 { "djz" })[0], JsonTools.getString(jso, "", new String[]
	 { "xqj" })[0] + " " + JsonTools.getString(jso, "", new String[]
	 { "rq" })[0],
	 String.format(this.getResources().getString(R.string.yh_breakfast_default_sign_time),
	 new Object[]
	 { JsonTools.getString(jso, "", new String[]
	 { "starttime" })[0] + " " + JsonTools.getString(jso, "", new String[]
	 { "endtime" })[0] }), yh_view_breakfast_sign_button, breakfastTime,
	 breakfastMessage, JsonTools.getString(jso, "", new String[]
	 { "title" })[0], JsonTools.getString(jso, "", new String[]
	 { "describe" })[0]);
	 }

}
