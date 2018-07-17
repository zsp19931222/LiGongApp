package com.yunhuakeji.app;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.yunhuakeji.app.utils.Constants;
import com.yunhuakeji.app.utils.DefaultTopBar;
import com.yunhuakeji.app.utils.DpPx;
import com.yunhuakeji.app.utils.JsonTools;
import com.yunhuakeji.app.utils.MapTools;
import com.yunhuakeji.app.utils.PostHelper;
import com.yunhuakeji.app.utils.PostHelper.IPostExecute;
import com.yunhuakeji.app.utils.URLAssist;
import com.yunhuakeji.app.R.drawable;
import com.yunhuakeji.app.R.id;
import com.yunhuakeji.app.R.layout;
import com.yunhuakeji.app.R.string;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 目标详情
 *
 * @author anmin
 *
 */
public class TargetDetailActivity extends YhActivity implements View.OnClickListener {

	private TextView yh_txt_target_detail_activity_des;
	private TextView yh_txt_target_detail_activity_look_all;
	private TextView yh_txt_target_detail_activity_today_sign;
	private LinearLayout yh_linearlayout_target_detail_activity_signed_user_image_layout;
	private LinearLayout yh_linearlayout_target_detail_activity_explain_layout;

	public static final int TARGET_SIGN_NO = 0;
	public static final int TARGET_SIGN_YES = 1;

	private boolean isAll = false;
	private String userid;
	private String bz;
	private String ticket;
	private String targetID;

	@Override
	protected void initActivity() {
		setContentView(R.layout.yh_target_detail_activity);
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
	protected void initView() {
		// 目标描述
		yh_txt_target_detail_activity_des = (TextView) findViewById(R.id.yh_txt_target_detail_activity_des);
		// 目标描述
		yh_txt_target_detail_activity_look_all = (TextView) findViewById(R.id.yh_txt_target_detail_activity_look_all);
		// 签到人数
		yh_txt_target_detail_activity_today_sign = (TextView) findViewById(
				R.id.yh_txt_target_detail_activity_today_sign);

		yh_linearlayout_target_detail_activity_explain_layout = (LinearLayout) findViewById(
				R.id.yh_linearlayout_target_detail_activity_explain_layout);

		// 签到人数头像
		yh_linearlayout_target_detail_activity_signed_user_image_layout = (LinearLayout) findViewById(
				R.id.yh_linearlayout_target_detail_activity_signed_user_image_layout);

	}

	@Override
	protected void initData() {
		String url=URLAssist.TEST._目标详情.getUrl();
		// userid = getIntent().getStringExtra("userid");
		// ticket = getIntent().getStringExtra("ticket");
		userid = "11204050220";
		ticket = "ba5bc1fb-3f21-42a3-ac5a-5ea9e6819f50";
		targetID = getIntent().getStringExtra("targetID");
		bz = getIntent().getStringExtra("bz");

		// "http://192.168.1.101:80/WDDXSERVER/target/getTargetDetails?userid=11204050220&targetID=10003&ticket=8dd44f40-82ce-4800-b964-e0c254f6c768&bz=1"
		PostHelper helper = new PostHelper(URLAssist.TEST._目标详情.getUrl(), MapTools.buildMap(
				new String[][] { { "userid", userid }, { "targetID", targetID }, { "ticket", ticket }, { "bz", bz } }));
		helper.doPostExecute(new IPostExecute() {
			@Override
			public void onPostExecute(Object result) {
				// TODO Auto-generated method stub
				if (result != null && "40001".equals(
						JsonTools.getString(JsonTools.createJsonObject(result.toString()), new String[] { "code" }))) {
					JSONObject jso = JsonTools.createJsonObject(result.toString());
					System.out.println(jso);

					yh_txt_target_detail_activity_des
							.setText(getContext().getResources().getString(R.string.yh_txt_test_long_text));

					for (int i = 0; i < yh_linearlayout_target_detail_activity_signed_user_image_layout.getChildCount()
							- 1; i++) {
						yh_linearlayout_target_detail_activity_signed_user_image_layout.getChildAt(i)
								.setBackgroundResource(R.drawable.breakbg);

						yh_linearlayout_target_detail_activity_signed_user_image_layout.getChildAt(i)
								.setVisibility(View.VISIBLE);
					}

					yh_txt_target_detail_activity_today_sign.setText(String.format(
							getResources().getString(R.string.yh_txt_total_join_num),
							yh_linearlayout_target_detail_activity_signed_user_image_layout.getChildCount() - 1));

					for (int i = 0; i < 10; i++) {
						View item = LayoutInflater.from(getContext()).inflate(
								R.layout.yh_target_detail_activity_item_target_explain,
								yh_linearlayout_target_detail_activity_explain_layout, false);

						((TextView) item.findViewById(R.id.yh_txt_target_detail_item_num)).setText("" + (i + 1));
						((TextView) item.findViewById(R.id.yh_txt_target_detail_item_message)).setText(
								"图锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷氐锟斤拷锟斤拷夭锟?平锟斤拷锟截诧拷,APP/UI锟截诧拷,H5锟截诧拷.锟斤拷锟斤拷锟斤拷锟斤拷Banner锟截诧拷专注png锟斤拷锟斤拷夭陌锟斤拷锟?000锟斤拷锟斤拷锟绞︼拷锟斤拷锟?0锟斤拷锟斤拷锟斤拷效锟斤拷,锟斤拷锟斤拷锟斤拷瞥锟斤拷锟狡凤拷锟狡凤拷锟街帮拷锟叫斤拷锟?");
						((TextView) item.findViewById(R.id.yh_txt_target_detail_activity_item_sign))
								.setText("1锟斤拷签锟斤拷");
						((TextView) item.findViewById(R.id.yh_txt_target_detail_item_data))
								.setText(Html.fromHtml("锟斤拷锟揭的达拷学锟斤拷"));

						if (new Random().nextBoolean()) {
							((TextView) item.findViewById(R.id.yh_txt_target_detail_activity_item_sign))
									.setTag(TARGET_SIGN_YES);
						} else {
							((TextView) item.findViewById(R.id.yh_txt_target_detail_activity_item_sign))
									.setTag(TARGET_SIGN_NO);
						}

						clickSignedButton(item.findViewById(R.id.yh_txt_target_detail_activity_item_sign));

						((TextView) item.findViewById(R.id.yh_txt_target_detail_activity_item_sign))
								.setOnClickListener(TargetDetailActivity.this);

						yh_linearlayout_target_detail_activity_explain_layout.addView(item);
					}
				}
			}
		});

	}

	@Override
	protected void initAction() {
		yh_txt_target_detail_activity_look_all.setOnClickListener(this);
	}

	private void clickSignedButton(View view) {
		Drawable drawable = null;

		if (TARGET_SIGN_YES == (Integer) view.getTag()) {
			drawable = getResources().getDrawable(R.drawable.breakyc);
		} else {
			drawable = getResources().getDrawable(R.drawable.breakwc);
		}

		drawable.setBounds(0, 0, new DpPx(getContext()).getDpToPx(48), new DpPx(getContext()).getDpToPx(48));

		((TextView) view).setCompoundDrawables(null, drawable, null, null);
	}

	private void clickLookAll() {
		if (!isAll) {
			yh_txt_target_detail_activity_des.setMaxLines(999);

			yh_txt_target_detail_activity_look_all
					.setText(getContext().getResources().getString(R.string.yh_txt_look_fold_describe));
		} else {
			yh_txt_target_detail_activity_des.setMaxLines(4);

			yh_txt_target_detail_activity_look_all
					.setText(getContext().getResources().getString(R.string.yh_txt_look_complete_describe));
		}
		isAll = !isAll;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.yh_txt_target_detail_activity_look_all) {

			clickLookAll();
		} else if (v.getId() == R.id.yh_txt_target_detail_activity_item_sign) {
			if ((Integer) v.getTag() != TARGET_SIGN_YES) {
				tijiao(v);
			}
		}
	}

	public void tijiao(final View v) {
		v.setVisibility(View.GONE);
		((LinearLayout) v.getParent()).findViewById(R.id.yh_progbar_target_detail_activity_item_progbar)
				.setVisibility(View.VISIBLE);
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.obj = v;
				handler.sendMessage(msg);
			}
		}, 2 * 1000);
	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			View v = (View) msg.obj;
			v.setVisibility(View.VISIBLE);
			((LinearLayout) v.getParent()).findViewById(R.id.yh_progbar_target_detail_activity_item_progbar)
					.setVisibility(View.GONE);
			v.setTag(TARGET_SIGN_YES);
			clickSignedButton(v);
		};
	};
}