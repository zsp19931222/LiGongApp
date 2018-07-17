package yh.app.imfra;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import yh.app.appstart.lg.R;
public class GroupFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.main_crcle_group, container,false);
		
		return view;
	}
}
