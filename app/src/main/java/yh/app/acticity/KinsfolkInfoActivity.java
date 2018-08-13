package yh.app.acticity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.adapter.ControllerAdapter;
import org.androidpn.push.Constants;
import yh.app.controller.controller.view.ProvinceView;
import yh.app.model.ControllerModel;
import yh.app.model.FamilyModel;
import yh.app.model.KinsfokkViewHolder;
import yh.app.model.UtilsModel;
import yh.app.tool.Ticket;
import yh.app.utils.LogUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 亲属信息
 */
public class KinsfolkInfoActivity extends WhiteActivity implements View.OnClickListener {

	private RelativeLayout rlKinsfolkAdd;
	private Button btnKinsfolkNext;
	private LinearLayout lyKinsfolkAddview;
	private RelativeLayout rlKinsfolkRemove;
	private int mark = 0;
	private View kinsfolkView;
	private List<KinsfokkViewHolder> ls_vh;
	private KinsfokkViewHolder viewHolder;
	private ClearEditText ctKinsfolkName;
	private ClearEditText ctKinsfolkNexus;
	private ClearEditText ctKinsfolkPhonenumber;
	private ClearEditText ctKinsfolkAge;
	private ClearEditText ctKinsfolkWorkunit;
	private ClearEditText ctKinsfolkJob;
	private RelativeLayout rlKinsfolkPoliticalstatus;
	private TextView txtKinsfolkPoliticalstatus;

	private ProvinceView provinceView;// 城市选择器
	private PopupWindow controllerWindow;
	private TextView txtCancel;
	private TextView txtSubmit;
	private RelativeLayout rl_out;
	private ListView lvController;
	private ControllerAdapter adapter;
	private ControllerModel controllerModel;
	private List<ControllerModel> list;

	private FamilyModel familModel;
	private String fid;
	private LoadDiaog diaog;

	private TextView txt_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kinsfolk_info);
		initView();
		getFamilyData();

	}

	public void back(View view) {
		finish();
	}

	private void initView() {
		txt_back = (TextView) findViewById(R.id.txt_back);
		txt_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		rlKinsfolkAdd = (RelativeLayout) findViewById(R.id.rl_kinsfolk_add);
		rlKinsfolkAdd.setOnClickListener(this);
		btnKinsfolkNext = (Button) findViewById(R.id.btn_kinsfolk_next);
		btnKinsfolkNext.setOnClickListener(this);
		lyKinsfolkAddview = (LinearLayout) findViewById(R.id.ly_kinsfolk_addview);
		rlKinsfolkRemove = (RelativeLayout) findViewById(R.id.rl_kinsfolk_remove);
		rlKinsfolkRemove.setOnClickListener(this);
		ls_vh = new ArrayList<>();
		diaog = new LoadDiaog(this);

	}

	private void dismess() {
		if (diaog.isShowing()) {
			diaog.dismiss();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_kinsfolk_next:
			// 下一步
			try {
				submitFamilyData();
			} catch (Exception e) {
				Toast.makeText(KinsfolkInfoActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}

			break;
		case R.id.rl_kinsfolk_add:
			// 添加
			mark++;
			addView(mark);

			break;
		case R.id.rl_kinsfolk_remove:
			removeView();
			break;

		case R.id.rl_kinsfolk_politicalstatus:
			// 政治面貌选择
			showWindow(rlKinsfolkPoliticalstatus, txtKinsfolkPoliticalstatus, UtilsModel.POLITICALSTATUS);
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
	 * 获得亲属信息
	 */
	private void getFamilyData() {
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.GETRELATIVES.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					familModel = ExampleApplication.getInstance().getGson().fromJson(result, FamilyModel.class);
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
				dismess();
			}
		});
	}

	/**
	 * 绑定亲属信息
	 */
	int listsize;

	private void bindData() throws Exception {
		listsize = familModel.getContent().size();
		if (listsize > 0) {

			for (int i = 0; i < listsize; i++) {
				mark = i;
				fid = familModel.getContent().get(i).getId();
				addView(i);

			}
		} else {
			mark += 1;
			addView(mark);
			dismess();
		}

	}

	/**
	 * 封装亲属信息
	 */
	String qsinfo;

	private String setFamilyData() {

		JSONArray jsonArray =new JSONArray();
		JSONObject jsonObject = null;
		for (KinsfokkViewHolder holder : ls_vh) {
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_workunit().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_phonenumber().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_nexus().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_name().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_age().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getCt_kinsfolk_job().getText().toString())) {
				return "";
			}
			if (TextUtils.isEmpty(holder.getTxt_kinsfolk_politicalstatus().getText())) {
				return "";
			}
			
			try {
				jsonObject = new JSONObject();
				jsonObject.put("id", holder.getId());
				jsonObject.put("dddw", holder.getCt_kinsfolk_workunit().getText().toString());
				jsonObject.put("lxdh", holder.getCt_kinsfolk_phonenumber().getText().toString());
				jsonObject.put("gx", holder.getCt_kinsfolk_nexus().getText().toString());
				jsonObject.put("xm", holder.getCt_kinsfolk_name().getText().toString());
				jsonObject.put("nl", holder.getCt_kinsfolk_age().getText().toString());
				jsonObject.put("zw", holder.getCt_kinsfolk_job().getText().toString());
				jsonObject.put("zzmm", holder.getTxt_kinsfolk_politicalstatus().getText());
				jsonArray.put(jsonObject);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
		}
		
		return jsonArray.toString();
	}

	String pa;

	private void submitFamilyData() throws Exception {
		
		
		try {
			pa = URLEncoder.encode(setFamilyData(), "utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		if (TextUtils.isEmpty(pa)) {
			Toast.makeText(KinsfolkInfoActivity.this, "必填项不能未空", Toast.LENGTH_SHORT).show();
			return;
		}
		diaog.show();
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("params", setFamilyData());
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.SETRELATIVES.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						startActivity(new Intent(KinsfolkInfoActivity.this, EverreadSchoolActivity.class));
						dismess();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onMyError(VolleyError error) {

			}
		});
	}

	String c;

	private void addView(int mak) {

		kinsfolkView = LayoutInflater.from(this).inflate(R.layout.item_kinsfolk, null, false);
		int viewcount = lyKinsfolkAddview.getChildCount();
		rlKinsfolkPoliticalstatus = (RelativeLayout) kinsfolkView.findViewById(R.id.rl_kinsfolk_politicalstatus);
		rlKinsfolkPoliticalstatus.setOnClickListener(this);
		ctKinsfolkName = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_name);//
		ctKinsfolkNexus = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_nexus);
		ctKinsfolkPhonenumber = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_phonenumber);
		ctKinsfolkAge = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_age);
		ctKinsfolkWorkunit = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_workunit);
		ctKinsfolkJob = (ClearEditText) kinsfolkView.findViewById(R.id.ct_kinsfolk_job);
		txtKinsfolkPoliticalstatus = (TextView) kinsfolkView.findViewById(R.id.txt_kinsfolk_politicalstatus);
		if (viewcount < listsize) {
			ctKinsfolkJob.setText(familModel.getContent().get(mak).getZw());
			ctKinsfolkAge.setText(familModel.getContent().get(mak).getNl());
			ctKinsfolkName.setText(familModel.getContent().get(mak).getXm());
			ctKinsfolkNexus.setText(familModel.getContent().get(mak).getGx());
			ctKinsfolkPhonenumber.setText(familModel.getContent().get(mak).getLxdh());
			ctKinsfolkWorkunit.setText(familModel.getContent().get(mak).getDddw());
			txtKinsfolkPoliticalstatus.setText(familModel.getContent().get(mak).getZZmm());

		}

		lyKinsfolkAddview.addView(kinsfolkView);

		saveViewInstance(mark);

		dismess();

	}

	private void removeView() {
		int viewcount = lyKinsfolkAddview.getChildCount();
		if (viewcount - 1 > 0) {
			// count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
			lyKinsfolkAddview.removeViewAt(viewcount - 1);
			if (ls_vh.size() > 0) {
				ls_vh.remove(ls_vh.size() - 1);
			}

		} else {
			Toast.makeText(this, R.string.str_kinfolk_toast, Toast.LENGTH_SHORT).show();
		}
		// 第一次判断网络返回的条是否和当前的一样
		// if (listsize==viewcount) {
		// if
		// (familModel.getContent().get(familModel.getContent().size()-1).getId()!=null)
		// {
		// deleteView(familModel.getContent().get(familModel.getContent().size()-1).getId());
		// }
		// }else{
		// if (viewcount - 1 > 0) {
		// // count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
		// lyKinsfolkAddview.removeViewAt(viewcount - 1);
		// if (ls_vh.size() > 0) {
		// ls_vh.remove(ls_vh.size() - 1);
		// dismess();
		// }
		//
		// } else {
		// Toast.makeText(this, R.string.str_kinfolk_toast,
		// Toast.LENGTH_SHORT).show();
		// }
		// }

		//

	}

	private void deleteView(String fid) {
		String ticket = Ticket.getFunctionTicket("20170601");
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", ticket);
		params.put("params", fid);
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.DELECTRELATIVES.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					// 40001
					JSONObject jsonObject = new JSONObject(result);

					if (Constants.NETWORK_REQUEST_SUCCESS.equals(jsonObject.getString("code"))) {
						int viewcount = lyKinsfolkAddview.getChildCount();
						if (viewcount - 1 > 0) {
							// count-2>0用来判断当前linearlayout子view数多于2个，即还有我们点add增加的button
							lyKinsfolkAddview.removeViewAt(viewcount - 1);
							if (ls_vh.size() > 0) {
								ls_vh.remove(ls_vh.size() - 1);
								dismess();
							}

						} else {
							Toast.makeText(KinsfolkInfoActivity.this, R.string.str_kinfolk_toast, Toast.LENGTH_SHORT)
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
	 * 保存动态控件
	 * 
	 * @param inflatView
	 */
	private void saveViewInstance(int fid) {
		viewHolder = new KinsfokkViewHolder();
		try {
			viewHolder.setId(familModel.getContent().get(fid).getId());

		} catch (Exception e) {
			LogUtils.LogShow("KinsfolkinfoActivity", e.toString());
		}
		viewHolder.setCt_kinsfolk_name(ctKinsfolkName);// 姓名
		viewHolder.setCt_kinsfolk_nexus(ctKinsfolkNexus);// 关系
		viewHolder.setCt_kinsfolk_phonenumber(ctKinsfolkPhonenumber);// 电话
		viewHolder.setCt_kinsfolk_age(ctKinsfolkAge);// 年龄
		viewHolder.setCt_kinsfolk_workunit(ctKinsfolkWorkunit);// 工作单位所在区域
		viewHolder.setCt_kinsfolk_job(ctKinsfolkJob);// 职位
		viewHolder.setTxt_kinsfolk_politicalstatus(txtKinsfolkPoliticalstatus);// 政治面貌
		ls_vh.add(viewHolder);

	}

	/**
	 * 政治面貌选择
	 * 
	 * @param view
	 * @param textView
	 * @param name
	 */
	private void showWindow(View view, final TextView textView, String... name) {
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
		list = new ArrayList<>();
		if (name.length > 0) {
			for (String n : name) {
				controllerModel = new ControllerModel();
				controllerModel.setName(n);
				list.add(controllerModel);
			}
		}

		adapter = new ControllerAdapter(this, list);
		lvController.setAdapter(adapter);
		lvController.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(KinsfolkInfoActivity.this, list.get(position).getName(), Toast.LENGTH_SHORT).show();
				textView.setText(list.get(position).getName());
				if (controllerWindow.isShowing()) {
					controllerWindow.dismiss();
				}
			}
		});

	}

}
