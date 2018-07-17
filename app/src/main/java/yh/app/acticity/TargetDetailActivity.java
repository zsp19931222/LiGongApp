package yh.app.acticity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.adapter.MuBiaoXiangQinAdapter;

import org.androidpn.push.Constants;
import yh.app.model.MuBiaodetailModel;
import yh.app.stu_info.RoundImageView;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.MyListview;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 目标库详情
 * 
 * @author anmin
 *
 */
public class TargetDetailActivity extends YhActivity implements OnClickListener {
	private TextView yh_txt_target_detail_activity_des;
	private TextView yh_txt_target_detail_activity_look_all;
	private TextView yh_txt_target_detail_activity_today_sign;
	private LinearLayout yh_linearlayout_target_detail_activity_signed_user_image_layout;
	private LinearLayout yh_linearlayout_target_detail_activity_explain_layout;
	private ImageView img_mubiao_fengxing;// 分享目标

	private MyListview listview_targetdetai;

	private String userid;
	private String bz;
	private String ticket;
	private String targetID;

	private MuBiaodetailModel mubiaoModel;
	private List<MuBiaodetailModel.ContentBean.MessageBean.DetailsBean> list_mubioa;
	private MuBiaoXiangQinAdapter mubiaoAdapter;

	private JSONObject jsonObject2, jsonObject, jsonObject3;
	private JSONArray array2, array;
	private JSONArray sign = null;
	private String qiandaoJson;
	private String hc_id;

	@Override
	protected void initActivity() {
		// TODO Auto-generated method stub
		setContentView(R.layout.yh_target_detail_activity);
	}

	@Override
	protected void initView() {
		// TODO Auto-generated method stub

		// 目标描述
		yh_txt_target_detail_activity_des = (TextView) findViewById(R.id.yh_txt_target_detail_activity_des);
		// 目标描述
		yh_txt_target_detail_activity_look_all = (TextView) findViewById(R.id.yh_txt_target_detail_activity_look_all);
		// 签到人数
		yh_txt_target_detail_activity_today_sign = (TextView) findViewById(
				R.id.yh_txt_target_detail_activity_today_sign);
		// 装填数据
		listview_targetdetai = (MyListview) findViewById(R.id.listview_targetdetai);
		// yh_linearlayout_target_detail_activity_explain_layout =
		// (LinearLayout) findViewById(
		// R.id.yh_linearlayout_target_detail_activity_explain_layout);
		img_mubiao_fengxing = (ImageView) findViewById(R.id.img_mubiao_fengxing);

		// 签到人数头像
		yh_linearlayout_target_detail_activity_signed_user_image_layout = (LinearLayout) findViewById(
				R.id.yh_linearlayout_target_detail_activity_signed_user_image_layout);

		userid = getIntent().getStringExtra("userid");
		ticket = getIntent().getStringExtra("ticket");
		targetID = getIntent().getStringExtra("targetID");
		bz = getIntent().getStringExtra("bz");

		// targetID="100096";
	}

	/**
	 * 退出当前
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	@Override
	protected void initData() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", userid);
		params.put("targetID", targetID);
		params.put("ticket", ticket);
		params.put("bz", bz);
		// TODO Auto-generated method stub
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.MuBiaoDetail.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				System.out.print(result);
				if (null != result) {
					mubiaoModel = ExampleApplication.getInstance().getGson().fromJson(result, MuBiaodetailModel.class);
					list_mubioa = mubiaoModel.getContent().getMessage().getDetails();
					mubiaoAdapter = new MuBiaoXiangQinAdapter(TargetDetailActivity.this, list_mubioa);
					listview_targetdetai.setAdapter(mubiaoAdapter);
					Constants.ticket = mubiaoModel.getContent().getTicket();
					bindData(mubiaoModel.getContent().getMessage());

				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(), "暂无可签到目标", Toast.LENGTH_SHORT).show();
			}
		});

	}

	private void bindData(MuBiaodetailModel.ContentBean.MessageBean bean) {
		// 目标详情
		yh_txt_target_detail_activity_des.setText(bean.getT_describe());
		yh_txt_target_detail_activity_today_sign.setText("共" + bean.getSign_count() + "人");// 签到总共人数
		// 添加签到人头像
		for (int i = 0; i < bean.getSign_img().size(); i++) {
			RoundImageView handimg = new RoundImageView(this);
			GlideLoadUtils.getInstance().glideLoad(TargetDetailActivity.this,bean.getSign_img().get(i),handimg,R.drawable.error);
//			Glide.with(TargetDetailActivity.this).load(bean.getSign_img().get(i)).into(handimg);
			yh_linearlayout_target_detail_activity_signed_user_image_layout.addView(handimg);
		}
		mubiaoAdapter.setOnQianDao(this);

	}

	@Override
	protected void initAction() {
		// TODO Auto-generated method stub
		// 分享目标按钮
		img_mubiao_fengxing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intentfengxiangmubiao = new Intent(TargetDetailActivity.this, FengXiangMuBiaoActivity.class);
				intentfengxiangmubiao.putExtra("targetID", targetID);
				startActivity(intentfengxiangmubiao);
			}
		});
	}

	/**
	 * 封装json数据
	 * 
	 * @return
	 */
	

	private void getQianDaoJson(int position) {

		jsonObject3 = new JSONObject();

		array2 = new JSONArray();

		array = new JSONArray();

		try {

			for (int i = 0; i < list_mubioa.size(); i++) {
				hc_id = list_mubioa.get(i).getC_id();

				CheckBox checkBox = (CheckBox) listview_targetdetai.getChildAt(i).findViewById(R.id.checkbox_qiandao);

				if (checkBox.isChecked()) {
					// 当与点击c_id不相同就添加进去
					jsonObject2 = new JSONObject();
					jsonObject2.put("c_id", hc_id);
					jsonObject2.put("sign", "1");
					sign = array.put(jsonObject2);
				} else {
					// item
					jsonObject = new JSONObject();
					jsonObject.put("c_id", hc_id);
					jsonObject.put("sign", "0");
					sign = array.put(jsonObject);
				}
			}

			jsonObject3.accumulate("sign", sign);
			jsonObject3.accumulate("target_id", targetID);
			array2.put(jsonObject3);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		qiandaoJson = array2.toString();

		setQianDao(qiandaoJson);
	}
	private void setQianDao(String json) {
		// 取掉引号
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		params.put("items", json);
		// 链接地址导航.WDDXSERVER.mubiaoqiandao.getUrl()
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.mubiaoqiandao.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(result)) {
					try {
						JSONObject jsonObject = new JSONObject(result);
						if (jsonObject.getBoolean("status")) {
							Toast.makeText(TargetDetailActivity.this,
									R.string.str_jiankanzaochan_qiandao_succes + result, Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(TargetDetailActivity.this, R.string.str_jiankanzaochan_qiandao_error, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Object tag = v.getTag();
		switch (v.getId()) {
		case R.id.checkbox_qiandao:
			Toast.makeText(TargetDetailActivity.this, "可以签到", Toast.LENGTH_SHORT).show();

			if (tag != null && tag instanceof Integer) { // 解决问题：如何知道你点击的按钮是哪一个列表项中的，通过Tag的position
				int position = (Integer) tag;
				CheckBox checkBox = (CheckBox) listview_targetdetai.getChildAt(position).findViewById(R.id.checkbox_qiandao);
				if (list_mubioa.get(position).getCan_sign().equals("0")) {
					// 已经签到禁止点击
					checkBox.setChecked(false);
					checkBox.setEnabled(false);
					Toast.makeText(TargetDetailActivity.this, "不能签到", Toast.LENGTH_SHORT).show();

				} else {
					if (list_mubioa.get(position).getC_completion().equals("0")) {
						// 可以签到
						getQianDaoJson(position);
						Toast.makeText(TargetDetailActivity.this, "可以签到", Toast.LENGTH_SHORT).show();
					} else {
						checkBox.setChecked(false);
						checkBox.setEnabled(false);
						// 不能签到栏
						Toast.makeText(TargetDetailActivity.this, "不能签到", Toast.LENGTH_SHORT).show();
					}
				}

			}
			break;

		}

	}

}
