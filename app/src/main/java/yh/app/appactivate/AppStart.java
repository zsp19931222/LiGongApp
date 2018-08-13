//package yh.app.appactivate;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//import java.util.concurrent.Executors;
//
//import com.example.app3.Login;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.ActivityInfo;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//import android.widget.Toast;
//import yh.app.activitytool.ActivityPortrait;
//import com.yhkj.cqgyxy.R;
//import org.androidpn.push.Constants;
//import yh.app.tool.GetConfig;
//import yh.app.tool.LoginAT;
//import yh.app.tool.SqliteHelper;
////import yh.app.uiengine.Login1;
//import com.example.app3.HomePageActivity;
//import yh.app.update.AppUpdateManager;
//import yh.app.utils.DataCleanManger;
//import yh.app.utils.UpdateChecker;
//import 云华.智慧校园.工具.RSAApi;
//import 云华.智慧校园.工具._链接地址导航;
//
///**
// * 包 名: com.yhkj.cqswzyxy 类 名:AppStart.java 功 能:程序入口函数,负责程序升级
// * 
// * @author 云华科技
// * @version 1.0
// * @date 2015-7-29
// */
//public class AppStart extends ActivityPortrait {
//	/** 效果图 */
//	private ProgressDialog mProgressDialog;
//	/** 确认更新按钮 */
//	private Button button;
//	private static Context context;
//    private UpdateChecker updateChecker;
//	private Handler mHandler = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			_intent();
//		}
//	};
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// 透明状态栏
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Window window = getWindow();
//			// Translucent status bar
//			window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//		}
//
//		super.onCreate(savedInstanceState);
//		context = this;
//		updateChecker=new UpdateChecker(this);
//		welcome();
//		setScreenInch(this);
//		initController();
//		// this.startService(new Intent(this,yh.app.services.Service2.class));
//		// this.startService(new Intent(this,yh.app.services.Service1.class));
//		start();
//		
//
//	}
//	
//	 /**
//     * 判断是否第一次运行
//     */
//    private void firstRun(){
//        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE);
//        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        if (isFirstRun)
//        {
//            editor.putBoolean("isFirstRun", false);
//            editor.commit();
//            //第一次运行
//            DataCleanManger.cleanDatabases(context);
//
//        } else
//        {
//            //非第一次运行
//        }
//    }
//
//	/**
//	 * 设置屏幕尺寸
//	 */
//	public void setScreenInch(Context context) {
//		Constants.screenInch = 5;// new DpPx(context).getScreenSizeOfDevice();
//		if (Constants.screenInch >= 7)
//			ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
//		else if (Constants.screenInch < 7)
//			ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
//	}
//
//	private void welcome() {
//		setContentView(R.layout.appstart_lg);
//		((RelativeLayout) findViewById(R.id.appstart)).setBackgroundResource(R.drawable.xxhyy);
//		findViewById(R.id.xxlogo).setBackgroundResource(R.drawable.xxlogo);
//	}
//
//	private void start() {
//		if (!this.isNetworkAvailable()) {
//			Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();
//			_intent();
//		} else {
//			Checkupdate(_链接地址导航.UIA.更新.getUrl() + new GetConfig(this).getVersion());
//		}
//	}
//
//	private void _intent() {
//		if (new SqliteHelper().rawQuery("select * from ydy").size() == 0) {
//			Timer timer = new Timer();
//			timer.schedule(new TimerTask() {
//				@Override
//				public void run() {
//
//					Intent intentydy = new Intent(context, YDY.class);
//					context.startActivity(intentydy);
//					finish();
//					return;
//				}
//			}, 1 * 1000);
//		} else {
//			login.sendMessage(new Message());
//			
//
//			return;
//		}
//	}
//
//	private Handler login = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			LoginAT at = new LoginAT(new Handler() {
//				@Override
//				public void handleMessage(Message msg) {
//					if (msg.what == 1) {
//						Intent intenthome = new Intent(AppStart.this, home.class);
//						AppStart.this.startActivity(intenthome);
//						finish();
//					} else {
//
//						Intent intentlgin = new Intent(AppStart.this, Login.class);
//						AppStart.this.startActivity(intentlgin);
//						finish();
//					}
//				};
//			}, AppStart.this, false);
//			List<Map<String, String>> result = new SqliteHelper().rawQuery("select userid, password from userinfo");
//			if (result != null && result.size() == 1)
//				at.doLoginNotSec(result.get(0).get("userid"), result.get(0).get("password"));
//			else {
//				Intent intentlgin = new Intent(AppStart.this, Login.class);
//				AppStart.this.startActivity(intentlgin);
//				finish();
//			}
//		}
//	};
//
//	public static boolean initUserLoginInfo() {
//		try {
//			List<Map<String, String>> UserLoginInfo = new SqliteHelper().rawQuery("select user,userp from userp");
//			Constants.number = UserLoginInfo.get(0).get("user");
//			Constants.code = RSAApi.getDecryptSecurity(UserLoginInfo.get(0).get("userp"));
//			return true;
//		} catch (Exception e) {
//			return false;
//		}
//	}
//
//	private void initController() {
//		button = (Button) findViewById(R.id.btn_update);
//		mProgressDialog = new ProgressDialog(AppStart.this);
//	}
//
//	/** 下载更新包 */
//	public void Checkupdate(String url) {
////		BaseVolleyRequest.RequestGet(url, new VolleyInterface() {
////			
////			@Override
////			public void onMySuccess(String result) {
////				try {
////					result = result.replace("\\", "");
////					result = result.substring(1, result.length() - 1);
////					JSONObject jsonObject=new JSONObject(result);
////					if(jsonObject.getBoolean("update")){
////						updateChecker.setCheckUrl(jsonObject.getString("url"));
////						updateChecker.showUpdateDialog();
////					} 
////				} catch (JSONException e) {
////					// TODO Auto-generated catch block
////					e.printStackTrace();
////				}
////			}
////			
////			@Override
////			public void onMyError(VolleyError error) {
////				
////			}
////		});
//		
//		AppUpdateManager appUpdateManager = new AppUpdateManager(mHandler, button, mProgressDialog, this);
//		appUpdateManager.executeOnExecutor(Executors.newCachedThreadPool(), url);
//	}
//}