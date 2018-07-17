package yh.app.appstart;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.Login;
import com.example.app3.activity.UpDateActivity;
import com.example.app4.activity.MainActivity;
import com.tbruyelle.rxpermissions.RxPermissions;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import rx.Observer;
import yh.app.activitytool.ActivityPortrait;
import yh.app.activitytool.BreakApp;
import yh.app.tool.GetConfig;
import yh.app.tool.LoginAT;
import yh.app.tool.NetWork;
import yh.app.tool.SqliteHelper;
import yh.app.utils.UpdateChecker;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel.OnButtonClickLisener;

/**
 * 包 名: com.yhkj.cqswzyxy 类 名:AppStart.java 功 能:程序入口函数,负责程序升级
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
@SuppressLint("HandlerLeak")
@TargetApi(Build.VERSION_CODES.KITKAT)
public class AppStart extends Activity {
    /**
     * 效果图
     */
    private ProgressDialog mProgressDialog;
    /**
     * 确认更新按钮
     */
    private Button button;
    private static Context context;
    private UpdateChecker updateChecker;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 12312) {
                start();
                return;
            }
            _intent();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        // 透明状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            // Translucent status bar
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        super.onCreate(savedInstanceState);
        // ExampleApplication.init();
        context = this;
        // setContentView(new TitleViewPager(this));
        updateChecker = new UpdateChecker(this);
        welcome();
        setScreenInch(this);
        initController();
        // this.startService(new Intent(this, yh.app.services.Service2.class));
        // this.startService(new Intent(this, yh.app.services.Service1.class));

        // mHandler.sendEmptyMessageDelayed(12312, 600);

        start();
    }
    /**
     * 设置屏幕尺寸
     */
    public void setScreenInch(Context context) {
        Constants.screenInch = 5;// new DpPx(context).getScreenSizeOfDevice();
        if (Constants.screenInch >= 7)
            ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        else if (Constants.screenInch < 7)
            ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    private void welcome() {
        setContentView(R.layout.appstart_lg);
//        (findViewById(R.id.appstart)).setBackgroundResource(R.drawable.xxhyy);
        findViewById(R.id.xxlogo).setBackgroundResource(R.drawable.xxlogo);
    }

    private void start() {
        if (!NetWork.isNetworkAvailable(this)) {
            Toast.makeText(getApplicationContext(), "无网络连接", Toast.LENGTH_SHORT).show();
            _intent();
        } else {
            Checkupdate(_链接地址导航.UIA.更新.getUrl() + new GetConfig(this).getVersion());
        }
    }

    private String appid = null;
    private String childSecret = null;
    private String userid = null;

    private void _intent() {
        Constants.number = "";
        Uri uri = getIntent().getData();
        Bundle bundle = getIntent().getExtras();
        if (uri != null) {
            appid = uri.getQueryParameter("appid");
            childSecret = uri.getQueryParameter("childSecret");
            userid = uri.getQueryParameter("userid");
        } else if (bundle != null) {
            appid = bundle.getString("appid");
            childSecret = bundle.getString("childSecret");
            userid = bundle.getString("userid");
        }

        if (IsNull.isNotNull(appid, childSecret, userid)) {
            // Intent intent = new Intent(context, Login.class);
            // intent.putExtra("appid", appid);
            // intent.putExtra("childSecret", childSecret);
            // intent.putExtra("userid", userid);
            // startActivity(intent);
            // finish();

            // final Map<String, String> params = new HashMap<>();
            // params.put("imei", SystemUtil.getIMEI(this));
            // params.put("model", SystemUtil.getSystemModel());
            // params.put("ip", "127.0.0.1");
            // params.put("equipmentSystem", SystemUtil.getSystemVersion());
            // params.put("sblx", "android");
            // params.put("childSecret", childSecret);
            // params.put("appid", appid);
            // params.put("userid", userid);

            new LoginAT(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
//                        Intent intenthome = new Intent(AppStart.this, HomePageActivity.class);
                        Intent intenthome = new Intent(AppStart.this, MainActivity.class);
                        AppStart.this.startActivity(intenthome);
                        finish();

                    } else {
                        Intent intenthome = new Intent(AppStart.this, Login.class);
                        AppStart.this.startActivity(intenthome);
                        finish();

                    }
                }
            }, context, false).doLoginOther(userid, appid, childSecret);
        } else if (new SqliteHelper().rawQuery("select * from ydy").size() == 0) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    Intent intentydy = new Intent(context, YDY.class);
                    context.startActivity(intentydy);
                    finish();
                    return;
                }
            }, 1 * 1000);
        } else {
            login.sendMessage(new Message());

            return;
        }
    }

    private Handler login = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            LoginAT at = new LoginAT(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (msg.what == 1) {
//                        Intent intenthome = new Intent(AppStart.this, HomePageActivity.class);
                        Intent intenthome = new Intent(AppStart.this, MainActivity.class);
                        AppStart.this.startActivity(intenthome);
                        finish();
                    } else {
                        Intent intentlgin = new Intent(AppStart.this, Login.class);
                        AppStart.this.startActivity(intentlgin);
                        finish();
                    }
                }
            }, AppStart.this, false);
            List<Map<String, String>> result = new SqliteHelper().rawQuery("select * from userinfo");
            if (result != null && result.size() == 1)
                at.doLoginNotSec(result.get(0).get("userid"), result.get(0).get("password"));
            else {
                // Intent intentlgin = new Intent(AppStart.this, Login1.class);
                Intent intentlgin = new Intent(AppStart.this, Login.class);
                AppStart.this.startActivity(intentlgin);
                finish();
            }
        }
    };

    public static boolean initUserLoginInfo() {
        try {
            List<Map<String, String>> UserLoginInfo = new SqliteHelper().rawQuery("select user,userp from userp");
            Constants.number = UserLoginInfo.get(0).get("user");
            Constants.code = RSAApi.getDecryptSecurity(UserLoginInfo.get(0).get("userp"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void initController() {
        button = (Button) findViewById(R.id.btn_update);
        mProgressDialog = new ProgressDialog(AppStart.this);
    }

    /**
     * 下载更新包
     */
    String updateURL;

    public void Checkupdate(String url) {

        VolleyRequest.RequestPost(url, new HashMap<String, String>(), new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                try {
                    result = result.replace("\\", "");
                    result = result.substring(1, result.length() - 1);
                    final JSONObject jso = new JSONObject(result);
                    if (jso.has("update") && jso.getBoolean("update")) {
                        updateURL = jso.getString("url");
                        button.setVisibility(View.VISIBLE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // if(true)
                                if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_MOBILE)
                                    new DiaLogOkAndCancel().buldeDialog(context, "提示", "当前为移动网络，是否继续下载", "继续下载", "取消", new OnButtonClickLisener() {
                                        @Override
                                        public void setButton1ClickLisener(DialogInterface dialog, int which) {
                                            dialog.dismiss();
//                                            AppUpdater updater = new AppUpdater(mProgressDialog, updateURL, context);
//                                            updater.executeOnExecutor(Executors.newCachedThreadPool());
                                            new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
                                        }

                                        @Override
                                        public void setButton2ClickLisener(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            {
                                                ((Activity) context).finish();
                                                BreakApp.closeAPP();
                                            }
                                        }

                                    });
                                else if (NetWork.getNetWorkType(context) == NetWork.NET_TYPE_WIFI) {
//                                    AppUpdater updater = new AppUpdater(mProgressDialog, updateURL, context);
//                                    updater.executeOnExecutor(Executors.newCachedThreadPool());
                                    new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);


                                }
                            }
                        });
                    } else {
                        _intent();
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub
                Intent intenthome = new Intent(AppStart.this, Login.class);
                AppStart.this.startActivity(intenthome);
                finish();


            }
        });
    }

    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "下载失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent = new Intent(context, UpDateActivity.class);
                intent.putExtra("url", updateURL);
                startActivity(intent);
            } else {
                Toast.makeText(context, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    };
}