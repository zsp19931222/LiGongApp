package yh.app.acticity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yh.app.tool.PackAganmanger;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 关于
 * 
 * @author anmin
 *
 */
public class AboutActity extends IntegrationActivity {

	private ImageView img_about_logo;// 应用图标
	private TextView txt_about_version;// 版本号

	private RelativeLayout rl_about_function;// 功能介绍

	private PackAganmanger packAganmanger;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
		initData();
		initWebView();
	}

	public void back(View v) {
		finish();
	}

	private void initView() {
		img_about_logo = (ImageView) findViewById(R.id.img_about_logo);

		txt_about_version = (TextView) findViewById(R.id.txt_about_version);
		rl_about_function = (RelativeLayout) findViewById(R.id.rl_about_function);
		packAganmanger = new PackAganmanger(AboutActity.this);
		rl_about_function.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (null!=url) {
//					Intent intentjieshao = new Intent(AboutActity.this, Web.class);
//					intentjieshao.putExtra("url", url);
//					startActivity(intentjieshao);
				}
				
			}
		});
	}

	private void initData() {
		img_about_logo.setImageDrawable(packAganmanger.getAppIcon());
		txt_about_version.setText(packAganmanger.getAppName() + packAganmanger.getAppVersionName());
	}

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
								url = _链接地址导航.WDDXSERVER.gongnengjieshao.getUrl() + Constants.number + "&ticket="
										+ web_ticket;

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
}
