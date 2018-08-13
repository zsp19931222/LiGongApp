package yh.app.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.acticity.AnShiGuiQingActivity;
import yh.app.acticity.BreakfastActivity;
import yh.app.acticity.CourseSignActivity;
import yh.app.acticity.HealthActivity2;
import yh.app.acticity.TargetDetailActivity;
import yh.app.acticity.ZiZhuMuBiaoMoreActivity;
import yh.app.adapter.ZhiZhuMuBiaoAdapter;
import yh.app.model.ZiZhuMuBiaoModel;
import yh.app.model.ZiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean;
import yh.app.services.JiBuSerever;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.MyListview;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 我的目标
 * 
 * @author anmin
 *
 */
public class AimFragment extends Fragment implements OnClickListener, Handler.Callback
{
	private TextView txt_home_study_plan;// 学习计划
	private TextView txt_home_mygrade;// 我的成绩
	private TextView txt_home_guakejinbao;// 挂科预警
	private TextView txt_home_course_singir;// 课程签到
	private TextView txt_home_breakfast;// 健康早餐
	private TextView txt_home_exercise;// 坚持运动
	private TextView txt_home_guiqing;// 按时归寝
	private TextView txt_home_more;// 查看更多
	private MyListview listview_aim;// 自主目标
	//上下文对象
	private Activity activity;
	//数据装填
	private ZiZhuMuBiaoModel zizhuModel;
	ZiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean bean;
	private List<ZiZhuMuBiaoModel.ContentBean.MessageBean.OngoingBean> list;
	private List<ZiZhuMuBiaoModel.ContentBean.MessageBean> list_detail;
	private ZhiZhuMuBiaoAdapter zizhuAdapter;

	// 计步
	private Messenger mGetReplyMessenger = new Messenger(new Handler(this));
	private Messenger messenger;
	private String step;//步数
	private boolean isBind = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.fragment_aim, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		activity = getActivity();
		initView();
		initData();
		// 开启计步
		startStepServer();
	}

	/**
	 * 初始化视图
	 */
	private void initView()
	{
		// 学习计划
		txt_home_study_plan = (TextView) getView().findViewById(R.id.txt_home_study_plan);
		txt_home_study_plan.setOnClickListener(this);
		// 我的成绩
		txt_home_mygrade = (TextView) getView().findViewById(R.id.txt_home_mygrade);
		txt_home_mygrade.setOnClickListener(this);
		// 挂科预警
		txt_home_guakejinbao = (TextView) getView().findViewById(R.id.txt_home_guakejinbao);
		txt_home_guakejinbao.setOnClickListener(this);
		// 课程签到
		txt_home_course_singir = (TextView) getView().findViewById(R.id.txt_home_course_singir);
		txt_home_course_singir.setOnClickListener(this);
		// 健康早餐
		txt_home_breakfast = (TextView) getView().findViewById(R.id.txt_home_breakfast);
		txt_home_breakfast.setOnClickListener(this);
		// 坚持运动
		txt_home_exercise = (TextView) getView().findViewById(R.id.txt_home_exercise);
		txt_home_exercise.setOnClickListener(this);
		// 按时归寝
		txt_home_guiqing = (TextView) getView().findViewById(R.id.txt_home_guiqing);
		txt_home_guiqing.setOnClickListener(this);
		// 查看更多
		txt_home_more = (TextView) getView().findViewById(R.id.txt_home_more);
		txt_home_more.setOnClickListener(this);
		// 自主目标
		listview_aim = (MyListview) getView().findViewById(R.id.listview_aim);
		
		
		listview_aim.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(activity, list.get(arg2).getT_name(), Toast.LENGTH_SHORT).show();
				Intent intentmubiaomu = new Intent(activity, TargetDetailActivity.class);
				intentmubiaomu.putExtra("userid", Constants.number);
				intentmubiaomu.putExtra("ticket", Constants.ticket);
				intentmubiaomu.putExtra("targetID", list.get(arg2).getT_id());
				intentmubiaomu.putExtra("bz", "1");
				startActivity(intentmubiaomu);
			}
		});

	}

	private void initData()
	{
		list=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
        	bean=new OngoingBean();
        	bean.setStatus_name("进行中");
        	bean.setStatus("0");
        	bean.setT_name("自主目标");
        	 list.add(bean);
		}
       
		zizhuAdapter=new ZhiZhuMuBiaoAdapter(activity, list);
		listview_aim.setAdapter(zizhuAdapter);
		
		
		Map<String, String> maps = new HashMap<>();
		maps.put("userid", Constants.number);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.appticketurl.getUrl(), maps, new VolleyInterface()
		{

			@Override
			public void onMySuccess(String result)
			{
				// TODO Auto-generated method stub
				String ticket = null;
				try
				{
					JSONObject jsonObject = new JSONObject(result);
					ticket = jsonObject.getString("ticket");
					if (null != ticket)
					{
						getMubiao(ticket);
						Constants.ticket = ticket;
					}

					// Toast.makeText(activity, "成功票据" + ticket,
					// 0).show();
				} catch (JSONException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onMyError(VolleyError error)
			{
				// TODO Auto-generated method stub
				// Toast.makeText(activity, "失败票据", 0).show();
			}
		});

	}

	/**
	 * 得到目标任务
	 */
	private void getMubiao(String ticke)
	{
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", ticke);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.ZhiZhuMuBiao.getUrl(), map, new VolleyInterface()
		{

			@Override
			public void onMySuccess(String result)
			{
				// TODO Auto-generated method stub
				zizhuModel=ExampleApplication.getInstance().getGson().fromJson(result, ZiZhuMuBiaoModel.class);
				list=zizhuModel.getContent().getMessage().getOngoing();
				zizhuAdapter=new ZhiZhuMuBiaoAdapter(activity, list);
				listview_aim.setAdapter(zizhuAdapter);
			}

			@Override
			public void onMyError(VolleyError error)
			{
				// TODO Auto-generated method stub
				// Toast.makeText(activity, "失败", 0).show();
			}
		});
		
	}

	@Override
	public void onDestroy()
	{
		if (isBind)
		{
			activity.unbindService(conn);
		}
		super.onDestroy();
	}

	/**
	 * 跳转到webView
	 * 
	 * @param url
	 */
	private void gotoWebView(String url)
	{
		Intent intentxuexijihua = new Intent(activity, BrowserActivity.class);
		intentxuexijihua.putExtra("url", url);
		startActivity(intentxuexijihua);
	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch (arg0.getId())
		{
		case R.id.txt_home_study_plan:
			// 学习计划
			gotoWebView(_链接地址导航.WDDXSERVER.xuexijihua.getUrl() + Constants.ticket + "&userid=" + Constants.ticket);
			Toast.makeText(activity, "学习计划", 0).show();
			break;
		case R.id.txt_home_mygrade:
			// 我的成绩
			gotoWebView(_链接地址导航.WDDXSERVER.wodechenji.getUrl() + Constants.ticket + "&userid=" + Constants.ticket);
			Toast.makeText(activity, "我的成绩", 0).show();
			break;
		case R.id.txt_home_guakejinbao:
			// 挂科预警
			gotoWebView(_链接地址导航.WDDXSERVER.guakeyujin.getUrl() + Constants.ticket + "&userid=" + Constants.ticket);
			Toast.makeText(activity, "挂科预警", 0).show();

			break;
		case R.id.txt_home_course_singir:
			Toast.makeText(activity, "课程签到", 0).show();
			Intent intentcoursesingir = new Intent(activity, CourseSignActivity.class);
			startActivity(intentcoursesingir);
			// 课程签到
			break;
		case R.id.txt_home_breakfast:
			// 健康早餐
			Toast.makeText(activity, "健康早餐", 0).show();
			Intent intentbreakfast = new Intent(activity, BreakfastActivity.class);
			startActivity(intentbreakfast);
			break;
		case R.id.txt_home_exercise:
			// 坚持运动
			Toast.makeText(activity, "坚持运动", 0).show();
			Intent intentexercise = new Intent(activity, HealthActivity2.class);
			intentexercise.putExtra("step", step);
			startActivity(intentexercise);
			break;
		case R.id.txt_home_guiqing:
			// 按时归寝
			Toast.makeText(activity, "按时归寝", 0).show();
			Intent intentguiqing = new Intent(activity, AnShiGuiQingActivity.class);
			startActivity(intentguiqing);
			break;
		case R.id.txt_home_more:
			// 查看更多
			Toast.makeText(activity, "查看更多", 0).show();
			Intent intentMore = new Intent(activity, ZiZhuMuBiaoMoreActivity.class);
			startActivity(intentMore);
			break;

		}
	}

	/**
	 * *******************************************************************
	 * 
	 * 计步相关
	 * 
	 * *******************************************************************
	 */

	private void startStepServer()
	{
		Intent intent = new Intent(activity, JiBuSerever.class);
		isBind = activity.bindService(intent, conn, Context.BIND_AUTO_CREATE);
		activity.startService(intent);
	}

	@Override
	public boolean handleMessage(Message msg)
	{
		// TODO Auto-generated method stub
		switch (msg.what)
		{
		case Constants.MSG_FROM_SERVER:
			step = msg.getData().getString("step");
			break;
		}
		return false;
	}

	/**
	 * 用于查询应用服务（application Service）的状态的一种interface， 更详细的信息可以参考Service 和
	 * context.bindService()中的描述，
	 * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
	 */
	ServiceConnection conn = new ServiceConnection()
	{
		/**
		 * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
		 * 
		 * @param name
		 *            实际所连接到的Service组件名称
		 * @param service
		 *            服务的通信信道的IBind，可以通过Service访问对应服务
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			try
			{
				messenger = new Messenger(service);
				Message msg = Message.obtain(null, Constants.MSG_FROM_CLIENT);
				msg.replyTo = mGetReplyMessenger;
				messenger.send(msg);
			} catch (RemoteException e)
			{
				e.printStackTrace();
			}
		}

		/**
		 * 当与Service之间的连接丢失的时候会调用该方法， 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
		 * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
		 * 
		 * @param name
		 *            丢失连接的组件名称
		 */
		@Override
		public void onServiceDisconnected(ComponentName name)
		{

		}

	};
}
