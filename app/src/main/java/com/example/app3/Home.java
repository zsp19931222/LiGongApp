package com.example.app3;

import android.os.Handler;
import android.os.Message;

import com.example.app3.PullToRefreshLayout.OnRefreshListener;
import com.example.app3.base.BaseActivity;
import com.yhkj.cqgyxy.R;

import org.json.JSONArray;

import 云华.智慧校园.工具.JsonReaderHelper;

public class Home extends BaseActivity
{
    private PullToRefreshLayout refresh_view;

    private JSONArray jsa;

    @Override
    protected void initView()
    {
	// TODO Auto-generated method stub
    }

    @Override
    protected void initActivityView()
    {
	// TODO Auto-generated method stub
	setContentView(R.layout.activity_home);
    }

    @Override
    protected void initData()
    {
	// TODO Auto-generated method stub
	try
	{
	    jsa = new JSONArray(JsonReaderHelper.getJosn(this, "MJRefreshState.json"));
	} catch (Exception e)
	{
	}
    }

    @Override
    protected void initAction()
    {
	// TODO Auto-generated method stub
	refresh_view = (PullToRefreshLayout) findViewById(R.id.refresh_view);
	refresh_view.setOnRefreshListener(new MyListener());
    }

    public class MyListener implements OnRefreshListener
    {

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
	    // 下拉刷新操作
	    new Handler()
	    {
		@Override
		public void handleMessage(Message msg)
		{
		    // 千万别忘了告诉控件刷新完毕了哦！
		    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
		}
	    }.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
	    // 加载操作
	    new Handler()
	    {
		@Override
		public void handleMessage(Message msg)
		{
		    // 千万别忘了告诉控件加载完毕了哦！
		    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
		}
	    }.sendEmptyMessageDelayed(0, 5000);
	}
    }
}