package yh.app.toweb;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yhkj.cqgyxy.R;

//OA主模块
public class OA_MainActivity extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View app = inflater.inflate(R.layout.oa_main, container,false);
		return app;
	}
	
	@Override
	public void onResume() {
		super.onResume();
	};
}