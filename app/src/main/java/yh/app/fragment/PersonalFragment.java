package yh.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yh.app.acticity.Set_Activity;
import yh.app.acticity.UserInfoActivity;
import yh.app.model.StudentInfoModel;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.PicTools;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 个人中心
 * 
 * @author anmin
 *
 */
public class PersonalFragment extends Fragment implements OnClickListener {
	private Activity activity;
	private View personalView;
	private RelativeLayout rl_personal_info;// 个人中心
	private RelativeLayout rl_personal_share;// 我的分享
	private RelativeLayout rl_persona_set;// 设置
	// 数据报告
	private RelativeLayout rl_personal_shuubaogao;
	private static ImageView img_personal_head;// 用户头像
	private static TextView txt_personal_username;// 用户昵称
	private TextView txt_personal_userId;// 用户账号

	private String url = null;
	private String share_url = null;// 我的分享
	private StudentInfoModel studentInfoModel;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		personalView = inflater.inflate(R.layout.activity_gerenzhonxin, container, false);
		return personalView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
		initData();
		initWebView();

	}

	@SuppressWarnings("unused")
	private void initView() {
		rl_personal_info = (RelativeLayout) personalView.findViewById(R.id.rl_personal_info);
		rl_personal_info.setOnClickListener(this);
		rl_personal_share = (RelativeLayout) personalView.findViewById(R.id.rl_personal_share);
		rl_personal_share.setOnClickListener(this);
		rl_persona_set = (RelativeLayout) personalView.findViewById(R.id.rl_persona_set);
		rl_persona_set.setOnClickListener(this);
		rl_personal_shuubaogao = (RelativeLayout) personalView.findViewById(R.id.rl_personal_shuubaogao);
		rl_personal_shuubaogao.setOnClickListener(this);

		img_personal_head = (ImageView) personalView.findViewById(R.id.img_personal_head);
		txt_personal_userId = (TextView) personalView.findViewById(R.id.txt_personal_userId);
		txt_personal_username = (TextView) personalView.findViewById(R.id.txt_personal_username);

		txt_personal_username.setText(Constants.name);// 昵称
		txt_personal_userId.setText("我的账号:" + Constants.number);
		new PicTools(activity).setImageViewBackground(img_personal_head, "face");
		if (true || Constants.user_type.equals("2")) {
			// 教师账号隐藏
			rl_personal_shuubaogao.setVisibility(View.GONE);
		} else {
			rl_personal_shuubaogao.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 加载数据报告
	 */
	private void initWebView() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject = new JSONObject(result);
					String web_ticket = jsonObject.getString("ticket");
					if (null != web_ticket) {
						// 数据报告
						url = _链接地址导航.WDDXSERVER.weburl_shujubaogao.getUrl() + Constants.number + "&ticket="
								+ web_ticket;
						// 我的风险
						share_url = _链接地址导航.WDDXSERVER.mubiaoguangchang.getUrl() + Constants.number + "&ticket="
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

	/**
	 * 加载用户信息
	 */
	private void initData() {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getuserinfo.getUrl(), map, new VolleyInterface() {
			@Override
			public void onMySuccess(String result) {

				// 成功
				if (!TextUtils.isEmpty(result)) {
					String code = null;
					try {
						JSONObject jsonObject = new JSONObject(result);
						code = jsonObject.getString("code");
						Constants.ticket = jsonObject.getJSONObject("content").getString("ticket");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (code.equals(Constants.NETWORK_REQUEST_SUCCESS)) {
						studentInfoModel = ExampleApplication.getInstance().getGson().fromJson(result,
								StudentInfoModel.class);
						bindData(studentInfoModel.getContent().getMessage());
					}

				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// 失败
				System.out.println(error);
			}
		});
	}

	/**
	 * 绑定数据 给控件赋值
	 */
	private void bindData(StudentInfoModel.ContentBean.MessageBean messageBean) {
		if (null != messageBean.getFaceaddress()) {
			// 用户头像
			GlideLoadUtils.getInstance().glideLoad(activity, messageBean.getFaceaddress(),img_personal_head,R.drawable.q1);
		}

		txt_personal_username.setText(Constants.name);// 昵称
		txt_personal_userId.setText("我的账号:" + Constants.number);
		new PicTools(activity).setImageViewBackground(img_personal_head, "face");

	}

	public static void resultData(Intent data) {
		try {

			txt_personal_username.setText(data.getStringExtra("nc"));
			img_personal_head.setImageBitmap((Bitmap) data.getParcelableExtra("img"));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.rl_personal_info:
			// 跳转到个人中心
			Intent intentgetrenzhongxi = new Intent(activity, UserInfoActivity.class);
			activity.startActivity(intentgetrenzhongxi);
			break;
		case R.id.rl_personal_share:
			// 跳转到我的分享
			if (null != share_url) {
				Intent intentshare = new Intent(activity, BrowserActivity.class);
				intentshare.putExtra("url", share_url);
				activity.startActivity(intentshare);
			}
			break;
		case R.id.rl_persona_set:
			// 跳转到设置页面
			Intent intentset = new Intent(activity, Set_Activity.class);
			activity.startActivity(intentset);
			break;
		case R.id.rl_personal_shuubaogao:
			if (null != url) {
				Intent intentweb = new Intent(activity, BrowserActivity.class);
				intentweb.putExtra("url", url);
				activity.startActivity(intentweb);
			}

			break;

		}
	}
}
