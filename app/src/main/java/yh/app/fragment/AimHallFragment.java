package yh.app.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import yh.app.appstart.lg.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.androidpn.push.Constants;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.utils.WebUtils;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 目标广场
 * 
 * @author anmin
 *
 */
public class AimHallFragment extends Fragment {
	private LinearLayout wed_viwe;
	private Activity activity;
	private WebView webView;
	private WebUtils webutils;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_aimhall, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		getTicket();
		initView();
	}

	// 获得票据
	private void getTicket() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), params,
				new VolleyInterface() {

					@Override
					public void onMySuccess(String result) {
						// TODO Auto-generated method stub
						if (null != result) {
							try {
								JSONObject object = new JSONObject(result);
								Constants.ticket = object.getString("ticket");
								webutils.onWebLoad(wed_viwe, _链接地址导航.WDDXSERVER.mubiaoguangchang +Constants.number
										+ "&ticket=" + Constants.ticket);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					@Override
					public void onMyError(VolleyError error) {
						// TODO Auto-generated method stub

					}
				});
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		wed_viwe = (LinearLayout) getView().findViewById(R.id.web_view);
		webutils = new WebUtils(activity);

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webutils.webPause();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		webutils.webResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webutils.webDestroy();
	}
}
