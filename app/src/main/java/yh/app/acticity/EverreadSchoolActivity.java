package yh.app.acticity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.system.ErrnoException;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.controller.controller.view.TimePickerView;
import yh.app.model.EducationModel;
import yh.app.model.EverreadSchoolViewHolder;
import yh.app.model.FamilyModel;
import yh.app.model.KinsfokkViewHolder;
import yh.app.tool.Ticket;
import yh.app.utils.Utils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

public class EverreadSchoolActivity extends WhiteActivity implements View.OnClickListener {

	private Button btnEverreadOver;
	private RelativeLayout rlEverreadschoolAdd;
	private RelativeLayout rlEverreadschoolRemove;
	private LinearLayout lyEverreadschoolAddview;
	private TimePickerView timePickerView;

	private int mark = 0;
	private View everreadSchollView;
	private List<EverreadSchoolViewHolder> ls_vh;
	private EverreadSchoolViewHolder viewHolder;
	private RelativeLayout rlEverreadschoolStarttime;
	private TextView textEverreadschoolStarttime;
	private RelativeLayout rlEverreadschoolEndtime;
	private TextView textEverreadschoolEndtime;
	private ClearEditText ctEverreadschoolSchoolname;
	private ClearEditText ctEverreadschoolDuties;
	private ClearEditText ctEverreadschoolWitness;
	private TextView txtBack;

	private String fid;
	private EducationModel educationModel;
	private LoadDiaog diaog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_everread_school);
		initView();
		getJiaoYuData();
	}

	private void initView() {

		btnEverreadOver = (Button) findViewById(R.id.btn_everread_over);
		btnEverreadOver.setOnClickListener(this);
		rlEverreadschoolAdd = (RelativeLayout) findViewById(R.id.rl_everreadschool_add);
		rlEverreadschoolAdd.setOnClickListener(this);
		rlEverreadschoolRemove = (RelativeLayout) findViewById(R.id.rl_everreadschool_remove);
		rlEverreadschoolRemove.setOnClickListener(this);
		lyEverreadschoolAddview = (LinearLayout) findViewById(R.id.ly_everreadschool_addview);
		ls_vh = new ArrayList<>();

		txtBack = (TextView) findViewById(R.id.txt_back);
		txtBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		diaog = new LoadDiaog(this);
	}

	private void dismess() {
		if (diaog.isShowing()) {
			diaog.dismiss();
		}
	}

	private void addView(int mak) {

		everreadSchollView = LayoutInflater.from(this).inflate(R.layout.item_everreadschool, null, false);
		int viewcount = lyEverreadschoolAddview.getChildCount();
		rlEverreadschoolStarttime = (RelativeLayout) everreadSchollView.findViewById(R.id.rl_everreadschool_starttime);
		rlEverreadschoolStarttime.setOnClickListener(this);
		textEverreadschoolStarttime = (TextView) everreadSchollView.findViewById(R.id.text_everreadschool_starttime);
		rlEverreadschoolEndtime = (RelativeLayout) everreadSchollView.findViewById(R.id.rl_everreadschool_endtime);
		rlEverreadschoolEndtime.setOnClickListener(this);
		textEverreadschoolEndtime = (TextView) everreadSchollView.findViewById(R.id.text_everreadschool_endtime);
		ctEverreadschoolSchoolname = (ClearEditText) everreadSchollView.findViewById(R.id.ct_everreadschool_schoolname);
		ctEverreadschoolDuties = (ClearEditText) everreadSchollView.findViewById(R.id.ct_everreadschool_duties);
		ctEverreadschoolWitness = (ClearEditText) everreadSchollView.findViewById(R.id.ct_everreadschool_witness);
		if (viewcount < listsize) {
			textEverreadschoolStarttime.setText(educationModel.getContent().get(mark).getKssj());
			textEverreadschoolEndtime.setText(educationModel.getContent().get(mark).getJssj());
			ctEverreadschoolSchoolname.setText(educationModel.getContent().get(mark).getXxmc());
			ctEverreadschoolDuties.setText(educationModel.getContent().get(mark).getZw());
			ctEverreadschoolWitness.setText(educationModel.getContent().get(mark).getZmr());

		}
		lyEverreadschoolAddview.addView(everreadSchollView);
		saveViewInstance(mark);
		dismess();

	}

	private void removeView() {
		// 得到LinearLayout 子view个数
		int viewcount = lyEverreadschoolAddview.getChildCount();
		if (viewcount - 1 > 0) {
			// count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
			lyEverreadschoolAddview.removeViewAt(viewcount - 1);
			if (ls_vh.size() > 0) {
				ls_vh.remove(ls_vh.size() - 1);
			}

		} else {
			Toast.makeText(this, R.string.str_everreadschool_toast, Toast.LENGTH_SHORT).show();
		}
		// if (listsize == viewcount) {
		// if
		// (educationModel.getContent().get(educationModel.getContent().size() -
		// 1).getId() != null) {
		// deleteView(educationModel.getContent().get(educationModel.getContent().size()
		// - 1).getId());
		// }
		// } else {
		// if (viewcount - 1 > 0) {
		// // count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
		// lyEverreadschoolAddview.removeViewAt(viewcount - 1);
		// if (ls_vh.size() > 0) {
		// ls_vh.remove(ls_vh.size() - 1);
		// dismess();
		// }
		//
		// } else {
		// Toast.makeText(this, R.string.str_everreadschool_toast,
		// Toast.LENGTH_SHORT).show();
		// }
		// }

	}

	private void deleteView(String fid) {
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("params", fid);
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.DELECTDUCATION.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						int viewcount = lyEverreadschoolAddview.getChildCount();
						if (viewcount - 1 > 0) {
							// count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
							lyEverreadschoolAddview.removeViewAt(viewcount - 1);
							if (ls_vh.size() > 0) {
								ls_vh.remove(ls_vh.size() - 1);
								dismess();
							}

						} else {
							Toast.makeText(EverreadSchoolActivity.this, R.string.str_kinfolk_toast, Toast.LENGTH_SHORT)
									.show();
						}
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onMyError(VolleyError error) {

			}
		});

	}

	/**
	 * 初始化时间选择器
	 */
	private void initTimeSection(final TextView tv) {
		timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);

		timePickerView.setTime(new Date());
		timePickerView.setCyclic(false);
		timePickerView.setCancelable(true);
		timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
			@Override
			public void onTimeSelect(Date date) {
				tv.setText(Utils.getTime(date));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_everreadschool_add:
			// 添加试图
			mark++;
			addView(mark);

			break;
		case R.id.rl_everreadschool_remove:
			// 移除视图
			removeView();
			break;
		case R.id.rl_everreadschool_starttime:
			// 开始是时间
			initTimeSection(textEverreadschoolStarttime);
			timePickerView.show();
			break;
		case R.id.rl_everreadschool_endtime:
			// 结束时间
			initTimeSection(textEverreadschoolEndtime);
			timePickerView.show();
			break;

		case R.id.btn_everread_over:
			// 完成录入

			try {
				submitJiaoYuData();
			} catch (Exception e) {
				Toast.makeText(EverreadSchoolActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			break;
		}
	}

	/**
	 * 获得教育信息
	 */
	private void getJiaoYuData() {
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.GETEDUCATIONIFO.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {

				try {

					educationModel = ExampleApplication.getInstance().getGson().fromJson(result, EducationModel.class);
					bindData();

				} catch (Exception e) {
					mark += 1;
					addView(mark);
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				mark += 1;
				addView(mark);
			}
		});
	}

	/**
	 * 绑定亲属信息
	 */
	int listsize;

	private void bindData() throws Exception {
		listsize = educationModel.getContent().size();
		if (listsize > 0) {
			for (int i = 0; i < listsize; i++) {
				mark = i;
				fid = educationModel.getContent().get(i).getId();
				addView(i);

			}
		} else {
			mark += 1;
			addView(mark);
		}

	}

	/**
	 * 封装教育信息
	 */
	private String setFamilyData() {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = null;
		for (EverreadSchoolViewHolder holder : ls_vh) {
			if (TextUtils.isEmpty(holder.getText_everreadschool_starttime().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getText_everreadschool_endtime().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_everreadschool_schoolname().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_everreadschool_duties().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_everreadschool_witness().getText().toString())) {
				return "";
			}
			try {
				jsonObject = new JSONObject();
				jsonObject.put("id", holder.getId());
				jsonObject.put("kssj", holder.getText_everreadschool_starttime().getText().toString());
				jsonObject.put("jssj", holder.getText_everreadschool_endtime().getText().toString());
				jsonObject.put("ddxx", holder.getCt_everreadschool_schoolname().getText().toString());
				jsonObject.put("zw", holder.getCt_everreadschool_duties().getText().toString());
				jsonObject.put("zmr", holder.getCt_everreadschool_witness().getText().toString());
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		return jsonArray.toString();
	}

	String pa;

	private void submitJiaoYuData() throws Exception {
		try {
			pa = URLEncoder.encode(setFamilyData(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (TextUtils.isEmpty(pa)) {
			Toast.makeText(EverreadSchoolActivity.this, "必填项不能未空", Toast.LENGTH_SHORT).show();
			return;
		}
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("params", setFamilyData());
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.SETENUCATIONIFO.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						overTask();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void onMyError(VolleyError error) {

			}
		});
	}

	/**
	 * 完成任务
	 */
	private void overTask() {
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("gnid", "10001");
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.OVERTASK.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						startActivity(new Intent(EverreadSchoolActivity.this, WelcomeStudentActivity.class));
						Toast.makeText(EverreadSchoolActivity.this, "恭喜你已完善个人信息", Toast.LENGTH_SHORT).show();
						dismess();
						finish();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					Toast.makeText(EverreadSchoolActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
					dismess();
				}
			}

			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(EverreadSchoolActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
				dismess();
			}
		});
	}

	/**
	 * 保存动态控件
	 *
	 * @param inflatView
	 */
	private void saveViewInstance(int mak) {
		viewHolder = new EverreadSchoolViewHolder();
		try {
			viewHolder.setId(educationModel.getContent().get(mak).getId());
		} catch (Exception e) {
		}

		viewHolder.setText_everreadschool_starttime(textEverreadschoolStarttime);// 开始时间
		viewHolder.setText_everreadschool_endtime(textEverreadschoolEndtime);// 结束时间
		viewHolder.setCt_everreadschool_schoolname(ctEverreadschoolSchoolname);// 学校名称
		viewHolder.setCt_everreadschool_witness(ctEverreadschoolWitness);// 证明人
		viewHolder.setCt_everreadschool_duties(ctEverreadschoolDuties);// 职务
		ls_vh.add(viewHolder);
	}
}
