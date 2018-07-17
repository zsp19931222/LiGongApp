package com.yunhuakeji.app;

import java.util.Date;
import java.util.HashMap;

import com.yunhuakeji.app.utils.DateString;
import com.yunhuakeji.app.utils.LocalDateFileHelper;
import com.yunhuakeji.app.utils.MD5Helper;
import com.yunhuakeji.app.utils.StepCounterService;
import com.yunhuakeji.app.utils.StepDetector;
import com.yunhuakeji.app.R;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class HealthActivity extends YhActivity implements View.OnClickListener
{
	private RelativeLayout yh_button_health_sign;

	private TextView yh_txt_health_msg, yh_txt_break_fast_num;

	private LocalDateFileHelper localDateFileHelper;

	private View healthNumLayout;

	public static final String FILE_NAME = MD5Helper.MD5(new DateString("yyyy-MM-dd").DateToString(new Date()) + "health");

	private int total_step = 0; // 走的总步数

	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			countStep();
			setHealthNumTextView(total_step);
			localDateFileHelper.setLocalString("" + total_step);
		};
	};

	/**
	 * 实际的步数
	 */
	private void countStep()
	{
		total_step = StepDetector.CURRENT_SETP;
	}

	private Handler setBreakInfo = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			try
			{
			} catch (Exception e)
			{
			}
		}
	};

	public void setHealthNumTextView(int num)
	{
		num = num + 7000 - 180;
		if (num >= 7000)
		{
			yh_txt_break_fast_num.setTextColor(this.getResources().getColor(R.color.button));
			yh_txt_health_msg.setText("今日步数已达到7000，签到完成");
		} else
		{
			yh_txt_break_fast_num.setTextColor(0xff999999);
			yh_txt_health_msg.setText("今日步数达到7000将自动完成签到");
		}
		yh_txt_break_fast_num.setText("" + num);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if (v.getId() == R.id.yh_relativelayout_daily_sign)
		{
//			new ThreadPoolManage().addNewPostTask("http://192.168.1.11:8080/WDDXSERVER/homeapp/getHaveBreakfastInfo?userid=11204050220", new HashMap<String, String>(), setBreakInfo);
		}
	}

	@Override
	public void initActivity()
	{
		// TODO Auto-generated method stub
		setContentView(R.layout.yh_home_breakfast_activity);
	}

	@Override
	public void initView()
	{
		// TODO Auto-generated method stub
		localDateFileHelper = new LocalDateFileHelper(this, FILE_NAME);

		setHealthNumTextView(StepDetector.CURRENT_SETP);

		yh_button_health_sign = (RelativeLayout) findViewById(R.id.yh_relativelayout_daily_sign);
		yh_txt_health_msg = (TextView) findViewById(R.id.yh_txt_daily_sign_msg);

		yh_button_health_sign.removeAllViews();

		healthNumLayout = LayoutInflater.from(this).inflate(R.layout.yh_health_number_layout, yh_button_health_sign, false);

		yh_button_health_sign.addView(healthNumLayout);

		yh_txt_break_fast_num = (TextView) healthNumLayout.findViewById(R.id.yh_txt_health_num);

	}

	@Override
	public void initData()
	{
		// TODO Auto-generated method stub
		new Thread(new Runnable()
		{
			public void run()
			{
				// TODO Auto-generated method stub
				while (true)
				{
					try
					{
						Thread.sleep(300);
					} catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
					}
					String temp = getContext().getClass().getName() + HealthActivity.class.getName();
					System.out.println(temp);
					if (StepCounterService.FLAG && getContext().getClass().getName().equals(HealthActivity.class.getName()))
					{
						handler.sendEmptyMessage(0);// 通知主线程
					}
				}
			}
		}).start();
	}

	@Override
	public void initAction()
	{
		// TODO Auto-generated method stub

	}

}