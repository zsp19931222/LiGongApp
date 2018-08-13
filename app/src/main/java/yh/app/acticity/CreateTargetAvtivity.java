package yh.app.acticity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yunhuakeji.app.utils.JsonTools;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 创建目标
 * 
 * @author anmin
 *
 */
public class CreateTargetAvtivity extends YhActivity {

	private LinearLayout yh_target_create_explain_layout;

	private ScrollView yh_sc_target_create;

	private JSONObject submitJson;

	private EditText yh_txt_target_create_name, yh_txt_target_create_describe;

	@Override
	public void initActivity() {
		setContentView(R.layout.yh_target_create_activity_layout);
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		yh_target_create_explain_layout = (LinearLayout) findViewById(R.id.yh_target_create_explain_layout);

		yh_txt_target_create_name = (EditText) findViewById(R.id.yh_txt_target_create_name);
		yh_txt_target_create_describe = (EditText) findViewById(R.id.yh_txt_target_create_describe);

		yh_sc_target_create = (ScrollView) findViewById(R.id.yh_sc_target_create);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 2; i++) {
			addExplain();
		}
	}

	// 退出当前
	public void back(View view) {
		finish();
	}

	@Override
	public void initAction() {
		// TODO Auto-generated method stub
		findViewById(R.id.yh_target_create_add_new_explain).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (yh_target_create_explain_layout.getChildCount() < 20) {
					addExplain();
				} else {
					Toast.makeText(getContext(), "最大20个分解项", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 提交目标
		findViewById(R.id.yh_button_submit).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitJson = new JSONObject();

				String yh_txt_target_create_name = CreateTargetAvtivity.this.yh_txt_target_create_name.getText()
						.toString();
				String yh_txt_target_create_describe = CreateTargetAvtivity.this.yh_txt_target_create_describe.getText()
						.toString();

				if (IsNull.isNotNull(yh_txt_target_create_name, yh_txt_target_create_describe)) {
					getTitleJson(yh_txt_target_create_name, yh_txt_target_create_describe);
				} else {
					Toast.makeText(getContext(), "目标名称或目标描述为空，请检查", Toast.LENGTH_SHORT).show();

					yh_sc_target_create.scrollTo(0, 0);
					return;
				}

				// int y =
				// findViewById(R.id.yh_target_create_activity_layout_head).getHeight()
				// +
				// findViewById(R.id.yh_target_create_activity_title).getHeight()
				// - new DpPx(getContext()).getDpToPx(40);

				JsonTools.accumulate(submitJson, "detail", new JSONArray());

				for (int i = 0; i < yh_target_create_explain_layout.getChildCount(); i++) {
					String yh_create_target_item_name, yh_create_target_item_sign_count, yh_create_target_item_data,
							yh_create_target_item_data_url;

					yh_create_target_item_name = ((TextView) yh_target_create_explain_layout.getChildAt(i)
							.findViewById(R.id.yh_create_target_item_name)).getText().toString();
					yh_create_target_item_sign_count = ((TextView) yh_target_create_explain_layout.getChildAt(i)
							.findViewById(R.id.yh_create_target_item_sign_count)).getText().toString();
					yh_create_target_item_data = ((TextView) yh_target_create_explain_layout.getChildAt(i)
							.findViewById(R.id.yh_create_target_item_data)).getText().toString();
					yh_create_target_item_data_url = ((TextView) yh_target_create_explain_layout.getChildAt(i)
							.findViewById(R.id.yh_create_target_item_data_url)).getText().toString();

					if (!IsNull.isNotNull(yh_create_target_item_name)) {
						Toast.makeText(getContext(), String.format("第%s项有必填项为空，请检查", i + 1), Toast.LENGTH_SHORT).show();

						// yh_sc_target_create.scrollTo(0, y);
						return;
					} else {
						getContentJson(yh_create_target_item_name, yh_create_target_item_sign_count,
								yh_create_target_item_data, yh_create_target_item_data_url);
						// y +=
						// yh_target_create_explain_layout.getChildAt(i).getHeight();
					}
				}

				// TODO
//				CreateTargetAvtivity.this.yh_txt_target_create_describe.setText(submitJson.toString());
				// 目标json数据
				submitTarget(submitJson.toString());
//				Toast.makeText(getContext(), submitJson.toString(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 提交目标
	 * 
	 * @param json
	 */
	private void submitTarget(String json) {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Constants.ticket);
		params.put("items", json);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.ChuangJianMuBiao.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				if (TextUtils.isEmpty(result)) {
					return;
				}
				try {
					JSONObject jsonObject=new JSONObject(result);
					if (jsonObject.getBoolean("status")) {
						Toast.makeText(CreateTargetAvtivity.this, "创建目标成功"+result, Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(CreateTargetAvtivity.this, "创建目标失败"+result, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				finish();
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(CreateTargetAvtivity.this, "创建目标失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void getTitleJson(String yh_txt_target_create_name, String yh_txt_target_create_describe) {
		JsonTools.accumulate(submitJson, "name", yh_txt_target_create_name);
		JsonTools.accumulate(submitJson, "describe", yh_txt_target_create_describe);
	}

	public void getContentJson(String yh_create_target_item_name, String yh_create_target_item_sign_count,
			String yh_create_target_item_data, String yh_create_target_item_data_url) {
		JSONObject jso = new JSONObject();

		JsonTools.accumulate(jso, "name", yh_create_target_item_name);
		JsonTools.accumulate(jso, "daynum",
				IsNull.isNotNull(yh_create_target_item_sign_count) ? yh_create_target_item_sign_count : "-1");
		JsonTools.accumulate(jso, "resourcename",
				IsNull.isNotNull(yh_create_target_item_data) ? yh_create_target_item_data : "");
		JsonTools.accumulate(jso, "resource",
				IsNull.isNotNull(yh_create_target_item_data_url) ? yh_create_target_item_data_url : "");

		JsonTools.getJSONArray(submitJson, "detail").put(jso);
	}

	private void addExplain() {
		View item = LayoutInflater.from(this).inflate(R.layout.yh_target_create_activity_layout_item,
				yh_target_create_explain_layout, false);
		((TextView) ((LinearLayout) item).getChildAt(0))
				.setText(String.format(this.getResources().getString(R.string.yh_target_target_position),
						yh_target_create_explain_layout.getChildCount() + 1));
		yh_target_create_explain_layout.addView(item);
	}

}
