package yh.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yhkj.cqgyxy.R;

import yh.app.utils.WebUtils;

/**
 * 职业评测
 * 
 * @author anmin
 *
 */
public class EvaluationFragment extends Fragment {
	private LinearLayout web_layout;
	private WebUtils webutils;
	private Activity activity;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View view=inflater.inflate(R.layout.fragment_evaluation, container, false);
		
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity=getActivity();
		initView();
	}
	/**
	 * 初始化视图
	 */
	private void initView(){
		web_layout=(LinearLayout) getView().findViewById(R.id.web_layout);
		webutils=new WebUtils(activity);
		webutils.onWebLoad(web_layout, "http://192.168.1.101/WDDXSERVER/professionalAssessment/getExaminationWithApp?userid=11204050220&ticket=fb41ef12-a445-46e6-a528-3022fcfed063");
	}
	
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		webutils.webPause();

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		webutils.webResume();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		webutils.webDestroy();
	}
}
