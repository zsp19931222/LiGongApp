package com.example.jpushdemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.webkit.CookieManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.example.app3.tool.Utils;
import com.example.app3.view.DisplayToast;
import com.example.smartclass.broadcast_receiver.NetworkChange;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.mob.MobSDK;
import com.tencent.smtt.sdk.QbSdk;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.androidpn.push.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;
import yh.app.appstart.AppStart;
import yh.app.tool.DeleteFile;
import yh.app.tool.SqliteHelper;
import yh.app.utils.LogUtils;

/**
 * For developer startup JPush SDK 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class ExampleApplication extends Application {
    private boolean voice_bool;
    private boolean vibrate_bool;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    private static Context context;
    public static String IMAGE_FUNCTION_PATH, IMAGE_FACE_PATH, FILE_PATH;
    private Gson gson;
    private static ExampleApplication sInstance; // 当前对象
    private RequestQueue requestQueue;
    private SqliteHelper sqliteHelper;
    public static CookieManager cookieManager;

    private static enum 学校 {
        本地("local"),
        理工("lg"), 农大("nd"), 重二师("c2s"), 三峡("sx"), 三峡医高专("sxygz"), 商职("sz"), 广安("ga"),

        房地产("fdc"), 云华科技("yhkj"), 重庆工商大学("cqgs"), 建工("jg"), 重庆邮电大学移通学院("cqytxy"), 电大("cqdd"), 南宁("nnzyjsxy"), 工程学院("gcxy");
        private String s;

        private 学校(String s) {
            this.s = s;
        }

        public String getValue() {
            return s;
        }

        public String getXXMC() {
            if (s.equals("lg")) {
                return "重庆理工大学";
            } else if (s.equals("nd")) {
                return "四川农业大学";
            } else if (s.equals("c2s")) {
                return "重庆第二师范学院";
            } else if (s.equals("sx")) {
                return "重庆三峡学院";
            } else if (s.equals("sxygz")) {
                return "重庆三峡医药高等专科学校";
            } else if (s.equals("sz")) {
                return "重庆商务职业学院";
            } else if (s.equals("ga")) {
                return "四川广安职业技术学院";
            } else if (s.equals("fdc")) {
                return "重庆房地产职业学院";
            } else if (s.equals("cqgs")) {
                return "重庆工商大学";
            } else if (s.equals("jg")) {
                return "重庆建筑工程学院";
            } else if ("cqytxy".equals(s)) {
                return "重庆邮电大学移通学院";
            } else if ("yhkj".equals(s)) {
                return "重庆云华科技有限公司";
            } else if ("cqdd".equals(s)) {
                return "重庆工商职业学院";
            } else if ("nnzyjsxy".equals(s)) {
                return "南宁职业技术学院";
            } else if ("gcxy".equals(s)) {
                return "重庆工程职业技术学院";
            } else if ("local".equals(s)) {
                return "本地";
            }
            return null;
        }
    }

    public static void DeleteWJJALL() {
        new DeleteFile().DeleteWJJALL("/yhdownload/");
        restart();
    }

    /**
     * 获取上下文
     *
     * @return Context
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        initOkHttp();
        initX5();
        initOkGo();
        initToast();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(NetworkChange.getInstance(), filter);

        context = this;
        cookieManager = CookieManager.getInstance();

        ExampleApplication.sInstance = this;


        IMAGE_FUNCTION_PATH = this.getFilesDir().getPath() + "/function/";
        IMAGE_FACE_PATH = this.getFilesDir().getPath() + "/face/";

        initJPush();

        LogUtils.isLog = true;
        Constants.App_Context = this;
        Constants.App_Context = this;
        // 数据版本好
        Constants.SqliteVersion = 221;
        CrashHandler.getInstance().init(this);
        // 选择当前学校服务器
        this.initXX(学校.理工);

        @SuppressLint("WrongConstant") SharedPreferences sharedPreferences = getSharedPreferences("voice_vibrate", Context.MODE_APPEND);

        voice_bool = sharedPreferences.getBoolean("voice_bool", true);
        vibrate_bool = sharedPreferences.getBoolean("vibrate_bool", true);
        setSoundAndVibrate(voice_bool, vibrate_bool, context);


        //7.0相机调试
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }

        com.example.app3.utils.LogUtils.init(context);

        try {
            Utils.initPinyin(getResources().getAssets().open("duoyinzi.txt"));//多音字处理类初始化
        } catch (IOException e) {
            e.printStackTrace();
        }
        initShareSDK();
    }

    public static synchronized ExampleApplication getInstance() {
        return sInstance;
    }

    /**
     * 获得程序包名
     *
     * @return
     */
    public static String getAppPackage() {
        return getInstance().getPackageName();
    }

    /**
     * Gson
     *
     * @return 返回Gson 对象
     */
    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }

        return gson;
    }

    /**
     * Volley
     *
     * @return 返回RequestQueue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        return requestQueue;
    }

    /**
     * SqliteHelper
     *
     * @return SqliteHelper 对象
     */
    public SqliteHelper getSqliteHelper() {
        if (sqliteHelper == null) {
            sqliteHelper = new SqliteHelper();
        }
        return sqliteHelper;
    }

    private void initXX(学校 name) {
        Constants.xx = name.getValue();
        Constants.xxmc = name.getXXMC();
    }

    private static void restart() {
        Intent intent = new Intent();
        intent.setAction(AppStart.class.getName());
        intent.setPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void setSoundAndVibrate(boolean isOpenSound, boolean isOpenVibrate, Context context) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
        if (isOpenVibrate && !isOpenSound) {//只有振动
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        } else if (isOpenSound && !isOpenVibrate) {//只有声音
            builder.notificationDefaults = Notification.DEFAULT_SOUND;
        } else if (isOpenSound && isOpenVibrate) {//两个都有
            builder.notificationDefaults = Notification.DEFAULT_ALL;
        } else {//只有呼吸灯
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    /**
     * shareSDK初始化
     */
    private void initShareSDK() {
        MobSDK.init(this);
    }

    /**
     * 激光推送初始化
     */
    private void initJPush() {
        // 极光推送
        SDKInitializer.initialize(this);
        yh.app.logTool.Log.isPrintLog = true; // 是否打印日志
        ApnsStart apnsStart = new ApnsStart(this);
        apnsStart.start();
        JPushInterface.setDebugMode(false);
        JPushInterface.resumePush(context);
        JPushInterface.init(this);
    }

    /**
     * 腾讯X5内核初始化
     */
    private void initX5() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("onViewInitFinished", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.setDownloadWithoutWifi(true);
        QbSdk.initX5Environment(getApplicationContext(), cb);


    }

    /**
     * OkHttp初始化
     */
    private void initOkHttp() {
        //OKHttp相关设置
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor("TAG"))//拦截器设置
                .connectTimeout(30, TimeUnit.SECONDS)//超时设置10秒
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * OkGo初始化
     */
    private void initOkGo() {
        OkGo.getInstance().init(this);
    }

    /**
     * 初始化Toast
     */
    private void initToast() {
        DisplayToast.getInstance().init(getApplicationContext());
    }
    /**
     * 内存检测工具初始化
     * */
//    private void initLeak(){
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//    }
}