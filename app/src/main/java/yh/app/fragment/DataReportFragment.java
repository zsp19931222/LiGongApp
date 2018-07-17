package yh.app.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import yh.app.appstart.lg.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * 数据报告
 * 
 * @author anmin
 *
 */
public class DataReportFragment extends Fragment {

	// 数据报告
	private LinearLayout weblayout;
	private WebUtils webutil;
	private Activity activity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.layout_shujubaogao, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
		initWebView();
	}

	private void initView() {
		// 数据报告
		weblayout = (LinearLayout) getView().findViewById(R.id.web_shujubaogao);
		webutil = new WebUtils(getActivity());
	}

	/**
	 * 加载数据报告
	 */
	private void initWebView() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), params,
				new VolleyInterface() {

					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						try {
							JSONObject jsonObject = new JSONObject(result);
							String web_ticket = jsonObject.getString("ticket");
							if (null != web_ticket) {
								// 数据报告
								webutil.onWebLoad(weblayout, _链接地址导航.WDDXSERVER.weburl_shujubaogao.getUrl()
										+ Constants.number + "&ticket=" + web_ticket + "");
							}
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
	public void onResume() {
		// TODO Auto-generated method stub
		webutil.webResume();
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		webutil.webPause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		webutil.webDestroy();
		super.onDestroy();
	}

}
