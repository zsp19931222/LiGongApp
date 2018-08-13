package yh.app.fragment;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.acticity.PersnaldataDetailsActivity;
import yh.app.model.RxzbUserInfoModel;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;


public class PersonalDataFragment extends Fragment implements View.OnClickListener {


    private TextView txtPersonaldataUserdetails;
    private ImageView imgPersonaldataHand;
    private TextView txtPersonaldataUserinifname;
    private TextView txtPersonaldataSchool;
    private TextView txtWelcomestudentMajor;
    private RelativeLayout lyPersonaldataJiaoshichaxun;
    private RelativeLayout lyPersonaldataXuefeichaxun;
    private RelativeLayout lyPersonaldataEmschaxun;
    private RelativeLayout lyPersonaldataDianzitongzishu;
    private Activity activity;

    public PersonalDataFragment() {
    }
    /**
	 * 用户信息
	 */
	private String FunctionID;
	private RxzbUserInfoModel userInfoModel;
	private String ewmContent;
	private Drawable nav_right;
	private SqliteHelper sqhelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_data, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        initView();
        getUseinfo();
    }

    private void initView() {
    	sqhelper = ExampleApplication.getInstance().getSqliteHelper();
    	FunctionID = activity.getIntent().getStringExtra("FunctionID");
        //点击查看用户详情
        txtPersonaldataUserdetails = (TextView) getView().findViewById(R.id.txt_personaldata_userdetails);
        txtPersonaldataUserdetails.setOnClickListener(this);
        //用户头像
        imgPersonaldataHand = (ImageView) getView().findViewById(R.id.img_personaldata_hand);
        //用户名称
        txtPersonaldataUserinifname = (TextView) getView().findViewById(R.id.txt_personaldata_userinifname);
        //学校
        txtPersonaldataSchool = (TextView) getView().findViewById(R.id.txt_personaldata_school);
        //专业
        txtWelcomestudentMajor = (TextView) getView().findViewById(R.id.txt_welcomestudent_major);
        //寝室查询
        lyPersonaldataJiaoshichaxun = (RelativeLayout) getView().findViewById(R.id.ly_personaldata_jiaoshichaxun);
        lyPersonaldataJiaoshichaxun.setOnClickListener(this);
        //学费查询
        lyPersonaldataXuefeichaxun = (RelativeLayout) getView().findViewById(R.id.ly_personaldata_xuefeichaxun);
        lyPersonaldataXuefeichaxun.setOnClickListener(this);
        //EMS查询
        lyPersonaldataEmschaxun = (RelativeLayout) getView().findViewById(R.id.ly_personaldata_emschaxun);
        lyPersonaldataEmschaxun.setOnClickListener(this);
        //电子通知书
        lyPersonaldataDianzitongzishu = (RelativeLayout) getView().findViewById(R.id.ly_personaldata_dianzitongzishu);
        lyPersonaldataDianzitongzishu.setOnClickListener(this);
        
    }
    
	/**
	 * 加载本地用户数据网络数据
	 */
	private void initThisUserinfoData() {
		List<Map<String, String>> listmap = sqhelper.rawQuery("select  userinfodata from userinfo where userid=?",
				new String[] { Constants.number });
		if (listmap.size() > 0) {

			String appljson = listmap.get(0).get("userinfodata");
			if (null != appljson) {
				try {
					userInfoModel = ExampleApplication.getInstance().getGson().fromJson(appljson,
							RxzbUserInfoModel.class);
					bindUserInfoData(userInfoModel.getContent());
				} catch (Exception e) {
					

				}
			}
		}

	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_personaldata_userdetails:
                //查看用户详情
                Intent intentuserdetails=new Intent(activity, PersnaldataDetailsActivity.class);
                intentuserdetails.putExtra("FunctionID", FunctionID);
                startActivity(intentuserdetails);
               
                break;

            case R.id.ly_personaldata_jiaoshichaxun:
                //寝室查询
            	toWeb(userInfoModel.getContent().getQscx());
                break;

            case R.id.ly_personaldata_xuefeichaxun:
                //学费查询
            	toWeb(userInfoModel.getContent().getXfcx());
                break;

            case R.id.ly_personaldata_emschaxun:
                //EMS查询
            	toWeb(userInfoModel.getContent().getEmscx());
                break;

            case R.id.ly_personaldata_dianzitongzishu:
                //电子通知书
            	toWeb(userInfoModel.getContent().getDztzscx());
                break;
        }
    }
 
    
	/**
	 * 获得学生信息
	 */
	private void getUseinfo() {
		if (FunctionID == null) {
			return;
		}
		Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket(FunctionID));
		VolleyRequest.RequestPost(_链接地址导航.Ydyx.STUDENTINFO.getUrl(), params, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				try {

					userInfoModel = ExampleApplication.getInstance().getGson().fromJson(result,
							RxzbUserInfoModel.class);
					bindUserInfoData(userInfoModel.getContent());
				} catch (Exception e) {
					initThisUserinfoData();
				}

			}

			@Override
			public void onMyError(VolleyError error) {
				initThisUserinfoData();
			}
		});
	}
	
	
	/**
	 * 
	 * 绑定用户数据
	 * 
	 * @param infoModel
	 */
	private void bindUserInfoData(RxzbUserInfoModel.ContentBean infoModel) throws Exception {
		ewmContent = infoModel.getEwm();
		// 修改用户性别图标
		if (infoModel.getXbdm().equals("2")) {
			nav_right = getResources().getDrawable(R.drawable.ico_woman);
		} else {
			nav_right = getResources().getDrawable(R.drawable.ico_man);
		}

		nav_right.setBounds(0, 0, nav_right.getMinimumWidth(), nav_right.getMinimumHeight());
		// 左上右下
		txtPersonaldataUserinifname.setCompoundDrawables(null, null, nav_right, null);
		txtPersonaldataUserinifname.setText(infoModel.getXm());
		txtPersonaldataSchool.setText(infoModel.getXymc());
		txtWelcomestudentMajor.setText(infoModel.getZymc());

	}

	
	/**
	 * 跳转web
	 * 
	 * @param url
	 */
	private void toWeb(String url) {
		String ticket = Ticket.getFunctionTicket(FunctionID);
		try {
			Intent intent = new Intent(activity, BrowserActivity.class);
			String addString = "?";
			if (url.contains("?")) {
				addString = "&";
			}
			intent.putExtra("url", url + addString + "userid=" + Constants.number + "&ticket=" + ticket);
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(activity, "系统出错了", Toast.LENGTH_SHORT).show();
		}

	}
}
