package yh.app.yikatong;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.yhkj.cqgyxy.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * 
 * 包 名:yh.app.yikatong 类 名:fenyue.java 功 能:一卡通分月查询
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class fenyue extends Fragment
{
    private View mMainView;
    private ListView listview;
    private fenyueAdapter fenyueadapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	LayoutInflater inflater = getActivity().getLayoutInflater();
	mMainView = inflater.inflate(R.layout.yktlist, (ViewGroup) getActivity().findViewById(R.id.main_viewpager), false);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

	ViewGroup p = (ViewGroup) mMainView.getParent();
	if (p != null)
	{
	    p.removeAllViewsInLayout();
	}
	listview = (ListView) mMainView.findViewById(R.id.listView1);
	listview.setAdapter(fenyueadapter);
	return mMainView;
    }

    public void setAdapter(fenyueAdapter fenyue)
    {
	this.fenyueadapter = fenyue;
    }

    @Override
    public void onDestroy()
    {
	// TODO Auto-generated method stub
	super.onDestroy();
    }

    @Override
    public void onPause()
    {
	// TODO Auto-generated method stub
	super.onPause();
    }

    @Override
    public void onResume()
    {
	// TODO Auto-generated method stub
	super.onResume();
    }

    @Override
    public void onStart()
    {
	// TODO Auto-generated method stub
	super.onStart();
    }

    @Override
    public void onStop()
    {
	// TODO Auto-generated method stub
	super.onStop();
    }
}