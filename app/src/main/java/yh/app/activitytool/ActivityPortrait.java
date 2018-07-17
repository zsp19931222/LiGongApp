package yh.app.activitytool;

import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.example.app3.utils.StatusBarCompat;
import yh.app.appstart.lg.R;

import yh.app.BroadcastReceiver.RegisterReceiver;

import org.androidpn.push.Constants;

import yh.app.notification.Notification1;
import yh.app.tool.CurrentActivity;
import yh.app.tool.NetWork;
import yh.app.tool.ToastShow;
import yh.app.utils.StatusBarUtil;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ScrollViewTools;

/**
 * 包 名:yh.app.activitytool 类 名:ActivityPortrait.java 功 能:继承与Activity,但强制设置为竖屏
 *
 * @author 阙海林
 * @version 1.0
 * @date 2015-7-29
 */
public class ActivityPortrait extends FragmentActivity {
    public static int pmfx = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static String function_id = "";
    public boolean hasFocus = false;
    public ScrollView sc;
    public float sc_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(R.style.NoBlackLine);
        function_id = "";
        Constants.dqltr = "";
        Constants.dqjm = "";
        super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		StatusBarCompat.compat(this, getResources().getColor(R.color.color_f6));
    }

    @Override
    public void setContentView(int layoutResID) {
        // TODO Auto-generated method stub
        super.setContentView(layoutResID);
        setStatusBar();
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBar() {
        StatusBarUtil.setColor(this,
                getResources().getColor(R.color.button), 1);
    }

    public void setfunction_id(String function_id) {
        ActivityPortrait.function_id = function_id;
    }

    @Override
    protected void onResume() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(pmfx);
        }
        CurrentActivity.NOW_ACTIVITY = this;
        RegisterReceiver.registerHomeKeyReceiver(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        checkNetwork();
        sc = (ScrollView) this.findViewById(R.id.sc);
        ScrollViewTools.inputScrollViewDown(sc, findViewById(R.id.inner));
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        this.hasFocus = hasFocus;
    }

    @Override
    protected void onPause() {
        RegisterReceiver.unregisterHomeKeyReceiver(this);
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Notification1.cancel(Notification1.NOTIFICATION_ALL);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Notification1.cancel(Notification1.NOTIFICATION_ALL);
    }

    private boolean checkNetwork() {
        if (!isNetworkAvailable()) {
            new ToastShow().show(this, "当前无网络连接");
            return false;
        } else
            return true;
    }

    public void getNetworkState() {

    }

    public String getFunctionID() {
        try {
            return getIntent().getStringExtra("function_id");
        } catch (Exception e) {
            return "";
        }

    }

    public Intent setIntent(Context context, Class<? extends Activity> c, Map<String, String> parames) {
        Intent intent = new Intent(context, c);
        if (parames != null) {
            Iterator<String> keys = MapTools.getMapKeys(parames);
            while (keys.hasNext()) {
                String name = keys.next();
                intent.putExtra(name, parames.get(name));
            }
        }
        return intent;
    }

    public Intent setDefaultIntent(Intent intent) {
        intent.putExtra("function_id", getFunctionID());
        return intent;
    }

    public boolean isNetworkAvailable() {
        return NetWork.isNetworkAvailable(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    public void setPMFX(String pmfx) {
        if (pmfx.equals("heng"))
            ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        else if (pmfx.equals("shu")) {
            ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        } else
            ActivityPortrait.pmfx = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
}
