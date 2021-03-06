package yh.app.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.yunhuakeji.app.R;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import org.androidpn.push.Constants;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.utils.WebUtils;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 运动报告
 * 
 * @author anmin
 *
 */
public class YunDongBaoGaoFragment extends Fragment {
	private Activity activity;
	private WebUtils webutil;
	private LinearLayout web_layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_yundongbaogao, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
//		onWebLoada();

	}

	private void initView() {
		web_layout = (LinearLayout) getView().findViewById(R.id.web_layout);
		webutil = new WebUtils(activity);
		 webutil.onWebLoad(web_layout,"http://192.168.1.101/WDDXSERVER/homeapp/getEchartsForSports?ticket=7c6b4dd5-4e00-4ec8-b832-e2e9aabf25aa&userid=11204050220");
	}
	
	// 加载课程签到报告
		private void onWebLoada() {
			Map<String, String> map = new HashMap<>();
			map.put("userid", Constants.number);
			VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), map,
					new VolleyInterface() {

						@Override
						public void onMySuccess(String result) {
							// TODO Auto-generated method stub
							try {
								JSONObject jsonObject = new JSONObject(result);
								String ticket = jsonObject.getString("ticket");
								webutil.onWebLoad(web_layout, _链接地址导航.WDDXSERVER.jiankanbaogoa.getUrl() + ticket
										+ "&userid=" + Constants.number);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						@Override
						public void onMyError(VolleyError error) {
							// TODO Auto-generated method stub

						}
					});
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			webutil.webPause();

		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			webutil.webResume();
		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			webutil.webDestroy();
		}
}
