package yh.app.acticity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.adapter.ControllerAdapter;
import org.androidpn.push.Constants;
import yh.app.controller.controller.view.ProvinceView;
import yh.app.controller.controller.view.TimePickerView;
import yh.app.model.BasicModel;
import yh.app.model.ControllerModel;
import yh.app.model.ProvincesModel;
import yh.app.model.UtilsModel;
import yh.app.tool.Ticket;
import yh.app.utils.JsonUtils;
import yh.app.utils.Utils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

/***
 * 个人信息完善
 */
public class DataInputActivity extends WhiteActivity implements View.OnClickListener {

	private ClearEditText etDatainputQqandweixin;
	private ClearEditText etDatainputEmail;
	private RelativeLayout rlDatainputJoinPartyTime;
	private TextView txtDatainputJoninPartyTime;
	private RelativeLayout rlDatainputHealthStarus;
	private TextView txtDatainputHealthStatuse;
	private RelativeLayout rlDatainputBloodType;
	private TextView txtDatainputBloodType;
	private RelativeLayout rlDatainputNodeType;
	private TextView txtDatainputNodeType;
	private RelativeLayout rlDatainputNodeAddress;
	private RelativeLayout rlDatainputNativePlace;
	private TextView txtDatainputNativePlace;
	private Button btnDatainputNext;
	private TextView txt_back;

	private TimePickerView timePickerView;
	private ProvinceView provinceView;

	private ControllerAdapter adapter;
	private List<ControllerModel> list;
	private ControllerModel controllerModel;
	private List<ProvincesModel.ProvincesListBean> provinList;

	/**
	 * 其他单项选择器
	 */
	private PopupWindow controllerWindow, provincewindow;
	private TextView txtCancel;
	private TextView txtSubmit;
	private RelativeLayout rl_province_out;
	private ListView lvController;
	private ProvincesModel provincesModel;
	private RelativeLayout rl_out;

	/**
	 * 基本数据
	 */
	private BasicModel basicmodel;
	private BasicModel.ContentBean basicmodelbean;
	private boolean isupdata = false;
	private LoadDiaog diaog;
	private boolean isemail=true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data_input);
		initView();
		getBasicInfo();
	}

	private void initView() {
		provinceView = new ProvinceView(this);
		txt_back = (TextView) findViewById(R.id.txt_back);
		txt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// qq/微信号
		etDatainputQqandweixin = (ClearEditText) findViewById(R.id.et_datainput_qqandweixin);
		// 邮箱
		etDatainputEmail = (ClearEditText) findViewById(R.id.et_datainput_email);
		// 入党团时间点击
		rlDatainputJoinPartyTime = (RelativeLayout) findViewById(R.id.rl_datainput_join_party_time);
		rlDatainputJoinPartyTime.setOnClickListener(this);
		// 入党团时间文本
		txtDatainputJoninPartyTime = (TextView) findViewById(R.id.txt_datainput_jonin_party_time);
		// 健康状态点击
		rlDatainputHealthStarus = (RelativeLayout) findViewById(R.id.rl_datainput_health_starus);
		rlDatainputHealthStarus.setOnClickListener(this);
		// 健康状态文本
		txtDatainputHealthStatuse = (TextView) findViewById(R.id.txt_datainput_health_statuse);
		// 血型点击
		rlDatainputBloodType = (RelativeLayout) findViewById(R.id.rl_datainput_blood_type);
		rlDatainputBloodType.setOnClickListener(this);
		// 血型文本
		txtDatainputBloodType = (TextView) findViewById(R.id.txt_datainput_blood_type);
		// 户口性质点击
		rlDatainputNodeType = (RelativeLayout) findViewById(R.id.rl_datainput_node_type);
		rlDatainputNodeType.setOnClickListener(this);
		// 户口性质文本
		txtDatainputNodeType = (TextView) findViewById(R.id.txt_datainput_node_type);

		// 籍贯点击
		rlDatainputNativePlace = (RelativeLayout) findViewById(R.id.rl_datainput_native_place);
		rlDatainputNativePlace.setOnClickListener(this);
		// 籍贯文本
		txtDatainputNativePlace = (TextView) findViewById(R.id.txt_datainput_native_place);
		// 下一步
		btnDatainputNext = (Button) findViewById(R.id.btn_datainput_next);
		btnDatainputNext.setOnClickListener(this);

		list = new ArrayList<>();
		initTimeSection();
		provincesModel = ExampleApplication.getInstance().getGson().fromJson(JsonUtils.getJosn(this, "city.json"),
				ProvincesModel.class);
		provinList = provincesModel.getProvincesList();

		diaog = new LoadDiaog(this);

	}

	private void dismess() {
		if (diaog.isShowing()) {
			diaog.dismiss();
		}
	}

	/**
	 * 查询基本数据
	 */
	private void getBasicInfo() {
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));

		VolleyRequest.RequestPost(_链接地址导航.Ydyx.GETBASICINFO.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					basicmodel = ExampleApplication.getInstance().getGson().fromJson(result, BasicModel.class);
					bindBasicData();
				} catch (Exception e) {
					dismess();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				dismess();
				Toast.makeText(DataInputActivity.this, "异常", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 绑定基础数据
	 */
	private void bindBasicData() throws Exception {
		etDatainputQqandweixin.setText(basicmodel.getContent().getQq());
		etDatainputEmail.setText(basicmodel.getContent().getDzyx());
		txtDatainputJoninPartyTime.setText(basicmodel.getContent().getRdtsj());
		txtDatainputHealthStatuse.setText(basicmodel.getContent().getJkzk());
		txtDatainputBloodType.setText(basicmodel.getContent().getXx());
		txtDatainputNodeType.setText(basicmodel.getContent().getHkxz());
		txtDatainputNativePlace.setText(basicmodel.getContent().getJg());
		dismess();
	}

	/**
	 * 转化成jons数组
	 */
	String json;
	String qq;
	String jkzk;
	String hkxz;
	String rdtsj;
	String xx;
	String dzyx;

	private String setBasicData() {

		qq = etDatainputQqandweixin.getText().toString();
		jkzk = txtDatainputHealthStatuse.getText().toString();
		hkxz = txtDatainputNodeType.getText().toString();
		rdtsj = txtDatainputJoninPartyTime.getText().toString();
		xx = txtDatainputBloodType.getText().toString();
		dzyx = etDatainputEmail.getText().toString();
		if (TextUtils.isEmpty(qq)) {
			return "";
		}
		if (!Utils.isEmail(dzyx)) {
			etDatainputEmail.setShakeAnimation();
			isemail=false;
			Toast.makeText(DataInputActivity.this, "邮箱格式错误", Toast.LENGTH_SHORT).show();
			return"";
		}

		JSONObject object = new JSONObject();
		try {
			object.put("qq", qq);
			object.put("jkzk", jkzk);
			object.put("hkxz", hkxz);
			object.put("rdtsj", rdtsj);
			object.put("xx", xx);
			object.put("dzyx", dzyx);
			// 如果网络有城市信息去网络段如果没有取本地
			if (isupdata) {
				object.put("jg", provinceView.getProvinceData());

			} else {
				try {
					object.put("jg", basicmodel.getContent().getJgdm());
				} catch (Exception e) {
					// TODO: handle exception500103
					object.put("jg", "500103");
				}
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();

	}

	/**
	 * 提交修改数据
	 */
	String pa;

	private void submitBasicData() {

		try {
			pa = URLEncoder.encode(setBasicData(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		if (TextUtils.isEmpty(pa)) {
			if (isemail) {
				Toast.makeText(DataInputActivity.this, "必填项不能未空", Toast.LENGTH_SHORT).show();
			}
			
			return;
		}
		diaog.show();
		Map<String, String> params = new HashMap<>();

		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("params", pa);

		VolleyRequest.RequestPost(_链接地址导航.Ydyx.SETBASICINFO.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						startActivity(new Intent(DataInputActivity.this, KinsfolkInfoActivity.class));
						dismess();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					dismess();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				dismess();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_datainput_join_party_time:
			// 入党团时间选择
			timePickerView.show();
			break;

		case R.id.rl_datainput_health_starus:
			// 健康状态选择
			showWindow(rlDatainputBloodType, txtDatainputHealthStatuse, null, UtilsModel.HEALTHY);
			break;

		case R.id.rl_datainput_blood_type:
			// 血型选择
			showWindow(rlDatainputBloodType, txtDatainputBloodType, null, UtilsModel.BLOOD);
			break;

		case R.id.rl_datainput_node_type:
			// 户口性质选择
			showWindow(rlDatainputBloodType, txtDatainputNodeType, null, UtilsModel.NODE);
			break;

		case R.id.rl_datainput_native_place:
			// 籍贯选择
			isupdata = true;
			provinceView.showWindow(btnDatainputNext, txtDatainputNativePlace);
			// showWindow(rlDatainputBloodType, txtDatainputNativePlace,
			// provinList);
			break;
		case R.id.btn_datainput_next:
			// 下一步操作
			submitBasicData();
			break;

		case R.id.rl_out:
			// 点击外部消失
			if (controllerWindow.isShowing()) {
				controllerWindow.dismiss();
			}

		case R.id.txt_cancel:
			// 弹出框取消
			if (controllerWindow.isShowing()) {
				controllerWindow.dismiss();
			}
			break;
		case R.id.txt_submit:
			// 弹出框完成按钮
			if (controllerWindow.isShowing()) {
				controllerWindow.dismiss();
			}
			break;

		}
	}

	/**
	 * 初始化时间选择器
	 */
	private void initTimeSection() {
		timePickerView = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);

		timePickerView.setTime(new Date());
		timePickerView.setCyclic(false);
		timePickerView.setCancelable(true);
		timePickerView.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
			@Override
			public void onTimeSelect(Date date) {
				txtDatainputJoninPartyTime.setText(Utils.getTime(date));
			}
		});
	}

	private void showWindow(View view, final TextView textView, List<ProvincesModel.ProvincesListBean> provinList,
			String... name) {
		View window = LayoutInflater.from(this).inflate(R.layout.popwindow_controller, null, false);

		// 设置窗体大小
		controllerWindow = new PopupWindow(window, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);

		controllerWindow.setFocusable(true);
		controllerWindow.setOutsideTouchable(false);
		controllerWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		controllerWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

		txtCancel = (TextView) window.findViewById(R.id.txt_cancel);
		txtCancel.setOnClickListener(this);

		txtSubmit = (TextView) window.findViewById(R.id.txt_submit);
		txtSubmit.setOnClickListener(this);

		rl_out = (RelativeLayout) window.findViewById(R.id.rl_out);
		rl_out.setOnClickListener(this);

		lvController = (ListView) window.findViewById(R.id.lv_controller);
		if (list != null) {
			list.clear();
		}
		if (name.length > 0) {
			for (String n : name) {
				controllerModel = new ControllerModel();
				controllerModel.setName(n);
				list.add(controllerModel);
			}
		} else {
			if (provinList.size() > 0) {
				for (int i = 0; i < provinList.size(); i++) {
					controllerModel = new ControllerModel();
					controllerModel.setName(provinList.get(i).getName());
					list.add(controllerModel);
				}
			}

		}

		adapter = new ControllerAdapter(this, list);
		lvController.setAdapter(adapter);
		lvController.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(DataInputActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
				textView.setText(list.get(position).getName());
				if (controllerWindow.isShowing()) {
					controllerWindow.dismiss();
				}
			}
		});

	}

}
