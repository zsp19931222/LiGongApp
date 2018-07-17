package com.baidu.navi.sdkdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.baidu.navisdk.adapter.BaiduNaviManager.NaviInitListener;
import com.baidu.navisdk.adapter.BaiduNaviManager.RoutePlanListener;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BNDemoMainActivity {

    public static List<Activity> activityList = new LinkedList<Activity>();

    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";

    private String mSDCardPath = null;

    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static final String SHOW_CUSTOM_ITEM = "showCustomItem";
    public static final String RESET_END_NODE = "resetEndNode";
    public static final String VOID_MODE = "voidMode";

    // @Override
    // protected void onCreate(Bundle savedInstanceState)
    // {
    // // TODO Auto-generated method stub
    // super.onCreate(savedInstanceState);
    // requestWindowFeature(Window.FEATURE_NO_TITLE);
    // activityList.add(this);
    //
    // setContentView(R.layout.mao_daohang_detail);
    //
    // BNOuterLogUtil.setLogSwitcher(true);
    //
    // initListener();
    // if (initDirs())
    // {
    // initNavi();
    // }
    // initView();
    // new Timer().schedule(new TimerTask()
    // {
    // @Override
    // public void run()
    // {
    // handler.sendMessage(new Message());
    // }
    // }, 3000);
    // }

    private Context context;
    private BNRoutePlanNode sNode;
    private BNRoutePlanNode eNode;

    public BNDemoMainActivity() {

    }

    public BNDemoMainActivity(Context context, BNRoutePlanNode sNode, BNRoutePlanNode eNode) {
        this.sNode = sNode;
        this.eNode = eNode;
        this.context = context;
    }

    public void doit() {

        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        activityList.add((Activity) context);

        // setContentView(R.layout.mao_daohang_detail);

        BNOuterLogUtil.setLogSwitcher(true);

        initListener();
        if (initDirs()) {
            initNavi();
        }
        // initView();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendMessage(new Message());
            }
        }, 500);

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            initListener();
        }

        ;
    };

    // private void initView()
    // {
    // load = (TextView) findViewById(R.id.load);
    // new Timer().schedule(new TimerTask()
    // {
    //
    // @Override
    // public void run()
    // {
    // loadNum++;
    // if (loadNum < 6)
    // loadSring.append(".");
    // if (loadNum == 6)
    // {
    // loadNum = 0;
    // loadSring = new StringBuffer("正在开启导航.");
    // }
    // load_handler.sendMessage(new Message());
    // }
    // }, 300, 300);
    // }

    private void initListener() {
        if (BaiduNaviManager.isNaviInited()) {
            routeplanToNavi(CoordinateType.BD09LL);
        }

    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    public void showToastMsg(final String msg) {
        // BNDemoMainActivity.this.runOnUiThread(new Runnable()
        // {
        //
        // @Override
        // public void run()
        // {
        // Toast.makeText(BNDemoMainActivity.this, msg,
        // Toast.LENGTH_SHORT).show();
        // }
        // });
    }

    private void initNavi() {
        BaiduNaviManager.getInstance().init((Activity) context, mSDCardPath, APP_FOLDER_NAME, new NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
            }

            public void initSuccess() {
                initSetting();
            }

            public void initStart() {
            }

            public void initFailed() {
                Toast.makeText(context, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, null);

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void routeplanToNavi(CoordinateType coType) {
        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator((Activity) context, list, BaiduNaviManager.RoutePlanPreference.ROUTE_PLAN_MOD_MIN_TIME, true, new DemoRoutePlanListener(sNode));
        }
    }

    public class DemoRoutePlanListener implements RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
			 * 设置途径点以及resetEndNode会回调该接口
			 */

            for (Activity ac : activityList) {

                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {

                    return;
                }
            }
            Intent intent = new Intent(context, BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            context.startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(context, "算路失败", Toast.LENGTH_SHORT).show();
            ((Activity) context).finish();
        }
    }

    private void initSetting() {
        BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

}
