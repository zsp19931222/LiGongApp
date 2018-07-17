package yh.app.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.jpushdemo.ExampleApplication;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;
import yh.app.acticity.HealthActivity2;
import org.androidpn.push.Constants;

import yh.app.appstart.lg.R;
/**
 * 计步器服务
 * 
 * @author anmin
 *
 */
public class JiBuSerever extends Service implements SensorEventListener {

	private SensorManager sensorManager;// 定义传感管理器
	// 计步传感器类型 0-counter 1-detector
	private static int stepSensor = -1;
	private boolean hasRecord = false;
	private int hasStepCount = 0;
	private int prviousStepCount = 0;
	private int CURRENT_SETP=0;// 步数
	private static String CURRENTDATE = "";// 当前时间
	private static String TODAYTIME="";//数据库时间
	// 默认为3秒进行一次存储
	private static int duration = 3000;
	private BroadcastReceiver mBatInfoReceiver;// 通知广播
	private Messenger messenger = new Messenger(new MessenerHandler());

	private NotificationCompat.Builder builder;
	private NotificationManager nm;
	private TimeCount time;
	private boolean isSend = true;// 是否发送通知
	private boolean isAdd=false;

	/**
	 * 发送步数
	 * 
	 * @author anmin
	 *
	 */
	@SuppressLint("HandlerLeak")
	private class MessenerHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.MSG_FROM_CLIENT:
				try {
					Messenger messenger = msg.replyTo;
					Message replyMsg = Message.obtain(null, Constants.MSG_FROM_SERVER);
					Bundle bundle = new Bundle();
					bundle.putInt("step", CURRENT_SETP);
					replyMsg.setData(bundle);
					messenger.send(replyMsg);
					System.out.print(replyMsg.toString());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				break;
			default:
				super.handleMessage(msg);
			}
		}
	}

	/**
	 * 创建服务
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initBroadcastReceiver();
		new Thread(new Runnable() {
			public void run() {
				startStepDetector();
				

			}
		}).start();
		// 初始化数据
//		initTodayData();
		isAdd=true;
		startTimeCount();// 开启计时器
		//获得当前时间
		CURRENTDATE = getTodayDate();
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	/**
	 * 传感器回调返回步数
	 */
	@Override
	public void onSensorChanged(SensorEvent arg0) {
		// TODO Auto-generated method stub
		if (stepSensor == 0) {
			int tempStep = (int) arg0.values[0];
			if (!hasRecord) {
				hasRecord = true;
				hasStepCount = tempStep;
			} else {
				int thisStepCount = tempStep - hasStepCount;
				CURRENT_SETP += (thisStepCount - prviousStepCount);
				prviousStepCount = thisStepCount;

			}
		} else if (stepSensor == 1) {

			if (arg0.values[0] == 1.0) {
				CURRENT_SETP++;
			}

		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return messenger.getBinder();
	}

	/**
	 * 开启计时
	 */
	private void startTimeCount() {
		
		time = new TimeCount(duration, 1000);
		time.start();
	}

	/**
	 * 初始化数据
	 */
	private void initTodayData() {
		isSend = true;
		isAdd=true;
		
		// 删除数据库数据
		ExampleApplication.getInstance().getSqliteHelper().execSQL("delete from step");
	}

	/**
	 * 保存当天步数
	 */
	private void save() {
		
		selectStep();
       //添加累计步数
		ExampleApplication.getInstance().getSqliteHelper().execSQL("replace into step(userid,date,stepSum) values(?,?,?)",
				new Object[] { Constants.number, getTodayDate(), CURRENT_SETP });
		
		if (isSend) {
			if (CURRENT_SETP>=10) {
				// 发送通知签到成功
				workPlanNotification();
				//让通知只发送一次
				isSend = false;
			}
			
		}

//		Toast.makeText(getApplicationContext(), CURRENT_SETP + "", Toast.LENGTH_SHORT).show();
	}
	/**
	 * 查询步数
	 */
	private void  selectStep(){
		//当服务重启的时候才查询添加步数
		if (isAdd) {
			List<Map<String, String>> listmap = ExampleApplication.getInstance().getSqliteHelper()
					.rawQuery("select * from step where userid=?", new String[] { Constants.number });
			
			if (listmap.size() > 0) {
				for (int i = 0; i < listmap.size(); i++) {
					
					TODAYTIME=listmap.get(i).get("date");
					//如果数据库时间与当前不服清空当前数据
					if (TODAYTIME.equals(CURRENTDATE)) {
						CURRENT_SETP = Integer.valueOf(listmap.get(0).get("stepSum")) + CURRENT_SETP;
						isAdd=false;
						
					}else{
						initTodayData();
					}
				}
			}
			
		}
		
	}

	/**
	 * 监听晚上0点变化初始化数据
	 */
	@SuppressLint("SimpleDateFormat")
	private void isNewDay() {
		String time = "00:00";
		if (time.equals(new SimpleDateFormat("HH:mm").format(new Date())) || !CURRENTDATE.equals(getTodayDate())) {

			// 日期变更初始化数据
			initTodayData();
		}
	}

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	private String getTodayDate() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 通知签到
	 */
	private void workPlanNotification() {
		builder = new NotificationCompat.Builder(this);
		builder.setPriority(Notification.PRIORITY_MIN);
		// 点击跳转到签到页面
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, HealthActivity2.class), 0);
		builder.setContentIntent(contentIntent);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("恭喜你今日任务达标,请继续保持");
		builder.setContentTitle("今日运动达标,步数" + CURRENT_SETP + " 步");
		// 设置不可清除
		builder.setOngoing(false);
		builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
		builder.setAutoCancel(true);
		Notification notification = builder.build();
		startForeground(0, notification);
		/**
		 * 震动
		 */
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		long[] vibrate = { 0, 100, 200, 300 };
		notification.vibrate = vibrate;

		/**
		 * 铃声
		 */
		notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION); //
		// 系统默认铃声
		notification.defaults = Notification.DEFAULT_SOUND;
		nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.notify(100, notification);
	}

	/**
	 * 注册广播
	 */
	private void initBroadcastReceiver() {
		final IntentFilter filter = new IntentFilter();
		// 屏幕灭屏广播
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		// 关机广播
		filter.addAction(Intent.ACTION_SHUTDOWN);
		// 屏幕亮屏广播
		filter.addAction(Intent.ACTION_SCREEN_ON);
		// 屏幕解锁广播
		// filter.addAction(Intent.ACTION_USER_PRESENT);
		// 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
		// example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
		// 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
		filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
		// 监听日期变化
		filter.addAction(Intent.ACTION_DATE_CHANGED);
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIME_TICK);

		mBatInfoReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(final Context context, final Intent intent) {
				String action = intent.getAction();
				if (Intent.ACTION_SCREEN_ON.equals(action)) {
				} else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
					// 60秒一存储
					duration = 60000;
					save();

				} else if (Intent.ACTION_USER_PRESENT.equals(action)) {
					save();
					// 3秒一存储
					duration = 3000;
				} else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
					// 保存一次
					save();
				} else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
					save();
				} else if (Intent.ACTION_DATE_CHANGED.equals(action)) {
					// 日期变化步数重置为0
					save();
//					isNewDay();
				} else if (Intent.ACTION_TIME_CHANGED.equals(action)) {
					// 时间变化步数重置为0

					save();
//					isNewDay();
				} else if (Intent.ACTION_TIME_TICK.equals(action)) {
					// 日期变化步数重置为0

					save();
//					isNewDay();
				}
			}
		};
		registerReceiver(mBatInfoReceiver, filter);
	}

	/**
	 * 获取传感器实例
	 */
	private void startStepDetector() {
		if (sensorManager != null) {
			sensorManager = null;
		}
		// 获取传感器管理器的实例
		sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
		// android4.4以后可以使用计步传感器
		int VERSION_CODES = Build.VERSION.SDK_INT;
		if (VERSION_CODES >= 19) {
			addCountStepListener();
		} else {
			//
		}
	}

	/**
	 * 添加传感器监听 注册计步传感器
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	@SuppressLint("InlinedApi")
	private void addCountStepListener() {
		// 得到计步传感器 系统提供了两种传感器
		Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		if (countSensor != null) {
			stepSensor = 0;
			sensorManager.registerListener(JiBuSerever.this, countSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else if (detectorSensor != null) {
			stepSensor = 1;
			sensorManager.registerListener(JiBuSerever.this, detectorSensor, SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			// 上面两种都没得
		}
		
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// 取消前台进程
		stopForeground(true);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		return super.onUnbind(intent);
	}

	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// 如果计时器正常结束，则开始计步
			time.cancel();
			save();
			startTimeCount();
		}

		@Override
		public void onTick(long millisUntilFinished) {

		}

	}
}
