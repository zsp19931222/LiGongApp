package yh.app.acticity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;
import com.yunhuakeji.app.utils.IsNull;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.adapter.HomeFragmentPagerAdapter;
import yh.app.fragment.FlowFragment;
import yh.app.fragment.PersonalDataFragment;
import yh.app.fragment.WelcomeStudentFragment;
import yh.app.listeners.DialogOnClickListener;
import yh.app.model.WelcomeStudentModel;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.view.WarnDialog;
import yh.app.view.WarnDialog.HideType;
import yh.tool.widget.MViewPage;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

public class WelcomeStudentActivity extends WhiteActivity implements View.OnClickListener {

    private LinearLayout lyBottom;
    private TextView txtReady;
    private TextView txtFlow;
    private TextView txtPersonalData;
    private MViewPage vpWelcomeStudent;

    private HomeFragmentPagerAdapter pagerAdapter;
    private WelcomeStudentFragment welcomeStudentFragment;
    private FlowFragment flowFragment;
    private PersonalDataFragment personalDataFragment;
    private List<Fragment> fragmentList;

    private WarnDialog handDialog;// 提示消息未完成
    private List<WelcomeStudentModel.ContentBean> welcomeStudentModels;
    private WelcomeStudentModel welcomeStudentModel;
    String FunctionID;
    private SqliteHelper sqhelper;
    private static String STATE_GNZT_FINISH = "1";
    boolean canScroll = true;
    private String wwcgnmc = null, wwcgncls = null, wwcgnurl = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_welcome_student);
        initView();
        initData();
        initUsserData();
    }

    private void initView() {
        FunctionID = getIntent().getStringExtra("FunctionID");
        txtReady = findViewById(R.id.txt_ready);
        txtReady.setOnClickListener(this);
        txtFlow = findViewById(R.id.txt_flow);
        txtFlow.setOnClickListener(this);
        txtPersonalData = findViewById(R.id.txt_personal_data);
        txtPersonalData.setOnClickListener(this);
        vpWelcomeStudent = findViewById(R.id.vp_welcome_student);
        handDialog = new WarnDialog(this);

    }

    /**
     * 初始化数据
     */
    public void initData() {
        welcomeStudentFragment = new WelcomeStudentFragment();
        flowFragment = new FlowFragment();
        personalDataFragment = new PersonalDataFragment();

        fragmentList = new ArrayList<>();
        fragmentList.add(welcomeStudentFragment);
        fragmentList.add(flowFragment);
        fragmentList.add(personalDataFragment);

        pagerAdapter = new HomeFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        vpWelcomeStudent.setOffscreenPageLimit(3);// 设置缓存多少个
        vpWelcomeStudent.setAdapter(pagerAdapter);
        vpWelcomeStudent.setCurrentItem(0);// 默认显示页

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
/**
 * 加载本地网络数据
 */
    // @Deprecated
    // private void initThisRXZBData()
    // {
    // List<Map<String, String>> listmap = sqhelper.rawQuery("select rxzbdata
    // from rxzb where userid=?", new String[]
    // {
    // Constants.number
    // });
    // if (listmap.size() > 0)
    // {
    //
    // String appljson = listmap.get(0).get("rxzbdata");
    // if (null != appljson)
    // {
    // try
    // {
    // welcomeStudentModel =
    // ExampleApplication.getInstance().getGson().fromJson(appljson,
    // WelcomeStudentModel.class);
    // welcomeStudentModels = welcomeStudentModel.getContent();
    // // 控制主页是否可以滑动
    // if (welcomeStudentModels.get(0).getGnzt().equals("1") &&
    // welcomeStudentModels.get(1).getGnzt().equals("1"))
    // {
    // initinitProcessor();
    // vpWelcomeStudent.setScrollble(true);
    //
    // } else
    // {
    // vpWelcomeStudent.setScrollble(false);
    // }
    // } catch (Exception e)
    // {
    //
    // }
    // }
    // }
    //
    // }

    /**
     * 入学准备功能列表数据
     */
    @SuppressLint("ShowToast")
    private void initUsserData() {
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("ticket", Ticket.getFunctionTicket(FunctionID));
        VolleyRequest.RequestPost(_链接地址导航.Ydyx.RXZBLB.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                try {
                    canScroll = true;
                    welcomeStudentModel = ExampleApplication.getInstance().getGson().fromJson(result, WelcomeStudentModel.class);
                    welcomeStudentModels = welcomeStudentModel.getContent();

                    // 控制主页是否可以滑动
                    if (canScroll(new JSONObject(result).getJSONArray("content"))) {
                        initinitProcessor();
                        vpWelcomeStudent.setScrollble(true);

                    } else {
                        vpWelcomeStudent.setScrollble(false);
                    }

                } catch (Exception e) {
                }

            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });

    }

    private boolean canScroll(JSONArray jsa) throws Exception {
        canScroll = true;
        wwcgnmc = null;
        wwcgncls = null;
        wwcgnurl = null;
        for (int i = 0; jsa != null && i < jsa.length(); i++) {
            JSONObject jso = jsa.getJSONObject(i);
            if (jso.has("gnyxj") && jso.has("gnzt") && Integer.valueOf(jso.getString("gnyxj")) < 1) {
                if (STATE_GNZT_FINISH.equals(jso.getString("gnzt"))) {
                    canScroll = canScroll && true;
                } else {
                    if (!IsNull.isNotNull(wwcgnmc, wwcgncls, wwcgnurl)) {
                        wwcgnmc = jso.getString("gnmc");
                        wwcgncls = jso.getString("cls");
                        wwcgnurl = jso.getString("url");
                    }
                    canScroll = canScroll && false;
                }
            }
        }
        return canScroll;
    }

    /**
     * 提示跳转到相应的类
     */
    private void hindDiog() {
        handDialog.setlConfirmTextStyle("完善" + wwcgnmc, 0, 0);
        handDialog.setDialogButton(HideType.CANCEL);
        handDialog.setContent("还没有完善" + wwcgnmc);
        handDialog.showWindow(txtFlow);
        handDialog.setDialogOnClickListener(new DialogOnClickListener() {

            @Override
            public void Submit() {
                // // TODO Auto-generated method stub
                // if (!wwcgncls.equals("yh.app.web.Web"))
                // {
                // // 原生
                // Map<String, String> parames = new HashMap<>();
                // parames.put("cls", classname);
                // parames.put("name", wwcgnmc);
                // parames.put("function_type", "1");
                // new _功能跳转().Jump(WelcomeStudentActivity.this, parames);
                // } else
                // {
                // // web
                toWeb(wwcgnurl);
                // }

                handDialog.dismiss();
            }

            @Override
            public void Cancel() {

            }
        });

    }

    /**
     * 跳转web
     *
     * @param url
     */
    private void toWeb(String url) {
        String ticket = Ticket.getFunctionTicket(FunctionID);
        try {
            Intent intent = new Intent(WelcomeStudentActivity.this, BrowserActivity.class);
            String addString = "?";
            if (url.contains("?")) {
                addString = "&";
            }
            intent.putExtra("url", url + addString + "userid=" + Constants.number + "&ticket=" + ticket);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(WelcomeStudentActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 处理器
     */
    private void initinitProcessor() {

        vpWelcomeStudent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                onChangerState(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 恢复所以按钮状态
     */
    private void onRecover() {
        txtFlow.setTextColor(getResources().getColor(R.color.color_black));

        txtPersonalData.setTextColor(getResources().getColor(R.color.color_black));

        txtReady.setTextColor(getResources().getColor(R.color.color_black));

    }

    /**
     * 改变底部按钮状态
     */
    private void onChangerState(int state) {
        onRecover();
        switch (state) {
            case 0:
                txtReady.setTextColor(getResources().getColor(R.color.color_bleu));
                break;
            case 1:
                txtFlow.setTextColor(getResources().getColor(R.color.color_bleu));
                break;
            case 2:
                txtPersonalData.setTextColor(getResources().getColor(R.color.color_bleu));
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_ready:
                vpWelcomeStudent.setCurrentItem(0, true);
                break;
            case R.id.txt_flow:
                try {
                    // if (!welcomeStudentModels.get(0).getGnzt().equals("1"))
                    // {
                    // hindDiog(welcomeStudentModels.get(0).getGnmc(),
                    // welcomeStudentModels.get(0).getUrl(),
                    // welcomeStudentModels.get(0).getIos_notice(),
                    // txtFlow);
                    // } else if
                    // (!welcomeStudentModels.get(1).getGnzt().equals("1"))
                    // {
                    // hindDiog(welcomeStudentModels.get(1).getGnmc(),
                    // welcomeStudentModels.get(1).getCls(),
                    // welcomeStudentModels.get(1).getIos_notice(), txtFlow);
                    // }
                    if (!canScroll) {
                        hindDiog();
                    } else {
                        vpWelcomeStudent.setCurrentItem(1, true);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
            case R.id.txt_personal_data:
                try {
                    // if (!welcomeStudentModels.get(0).getGnzt().equals("1"))
                    // {
                    // hindDiog();
                    // } else if
                    // (!welcomeStudentModels.get(1).getGnzt().equals("1"))
                    // {
                    // hindDiog();
                    // } else
                    if (!canScroll) {
                        hindDiog();
                    } else {
                        vpWelcomeStudent.setCurrentItem(2, true);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }

                break;
        }
    }

    @Override
    protected void onResume() {

        super.onResume();
        initUsserData();
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case TagUtil.ChooseAreaTag:
                finish();
                break;
        }
    }
}
