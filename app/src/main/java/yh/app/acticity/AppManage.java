package yh.app.acticity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.app3.tool.JSONTool;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.adapter.OtherApplyManageAdapter;
import org.androidpn.push.Constants;
 
import yh.app.model.AppManageModel;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.IntegrationActivity;
import yh.tool.widget.LoadDiaog;
import yh.tool.widget.MyListview;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 应用管理
 * 
 * @author anmin
 *
 */
public class AppManage extends IntegrationActivity {
	private MyListview list_apply_otherapp;

	private String ticket;
	private static OtherApplyManageAdapter otherappdatapter;
	private AppManageModel appmodel;
	private List<AppManageModel.OTHERAPPBean> otherlist;// 推荐应用集合

	private SqliteHelper helper;
    private LoadDiaog diaog;
	private JSONArray functionarray;
	private TextView txt_appmanaga;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appmanage);
		initView();
		initFunctionData();
	}

	public void back(View view) {
		if (Constants.isAppManageUpdata) {
			submitManageApp();
		}else{
			finish();
		}
//		helper.execSQL("delete  from appmanage where userid=?",new Object[] { Constants.number});
		
	}

	/**
	 * 初始化视图
	 */
	private void initView() {
		list_apply_otherapp = (MyListview) findViewById(R.id.list_apply_otherapp);
		helper = ExampleApplication.getInstance().getSqliteHelper();
		txt_appmanaga=(TextView) findViewById(R.id.txt_appmanaga);
		
		
		txt_appmanaga.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				submitManageApp();
			}
		});
		diaog=new LoadDiaog(this);
		ticket = MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code);
	}

	private void submitManageApp() {
		txt_appmanaga.setEnabled(false);
		diaog.show();
		functionarray = new JSONArray();
		List<Map<String, String>> listmap = helper.rawQuery("select  function_id from appmanage where userid=?",
				new String[] { Constants.number });
		if (listmap.size() > 0) {
			for (int i = 0; i < listmap.size(); i++) {
				functionarray.put(listmap.get(i).get("function_id"));
			}
		}

		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", ticket);
		params.put("function_id", functionarray.toString());
		VolleyRequest.RequestPost(_链接地址导航.UIA.修改应用.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				String json=null;
				if (null!=result) {
//					json=result.replace("\\", "");
//					json = json.substring(1, json.length() - 1);
					json= JSONTool.jsonString(result);
					try {
						JSONObject jsonObject=new JSONObject(json);
						String code=jsonObject.getString("code");
						if(Constants.NETWORK_REQUEST_SUCCESS.equals(code)){
							Constants.isSubmit=true;
							finish();
//							Toast.makeText(AppManage.this, "修改成功", Toast.LENGTH_SHORT).show();
							if (diaog.isShowing()) {
								diaog.dismiss();
							}
							txt_appmanaga.setEnabled(true);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
				
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(AppManage.this, "修改失败", Toast.LENGTH_SHORT).show();
			}
		});

	}

	// 刷新数据
	public static void getDataAdapter() {
		otherappdatapter.notifyDataSetChanged();
	}

	/**
	 * 加载应用网络数据
	 */
	private void initFunctionData() {
		// String ticket = MD5.MD5(_链接地址导航.addString + Constants.number +
		// Constants.code);
		diaog.show();
		final Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", ticket);
		map.put("Version", "0");

		VolleyRequest.RequestPost(_链接地址导航.UIA.应用管理.getUrl(), map, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {
					String json = null;
//					// 替换反斜杠
//					json = result.replace("\\", "");
//					// 取掉引号
//					json = json.substring(1, json.length() - 1);
					json= JSONTool.jsonString(result);

					if (null != json) {
						doSuccess(json);
					}
				} catch (Exception e) {
					Toast.makeText(AppManage.this, "网络异常", Toast.LENGTH_SHORT).show();
					finish();
				}
				
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub
				// initThisApplyData();
			}
		});
	}

	// 数据绑定
	public void doSuccess(String s) {
		appmodel = ExampleApplication.getInstance().getGson().fromJson(s, AppManageModel.class);
		otherlist = appmodel.getOTHER_APP();
		otherappdatapter = new OtherApplyManageAdapter(this, otherlist);
		list_apply_otherapp.setAdapter(otherappdatapter);
		if (diaog.isShowing()) {
			diaog.dismiss();
		}
//		// 添加我的应用数据
//				ExampleApplication.getInstance().getSqliteHelper().execSQL("delete from yhappmanagpublic where userid=?",
//						new Object[] { Constants.number });
//				ExampleApplication.getInstance().getSqliteHelper().execSQL(
//						"insert into yhappmanagpublic(appjson,userid) values(?,?)", new Object[] { s, Constants.number });
	
	}
}
