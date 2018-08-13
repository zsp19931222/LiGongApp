package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.entity.MainWidgetEntity;
import com.example.app4.fragment.HomePageFragment;
import com.example.app4.fragment.MessageFragment;
import com.example.app4.fragment.MySelfFragment;
import com.example.app4.presenter.LoginUtil;
import com.example.app4.presenter.MainActivityPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.CameraUtil;
import com.example.smartclass.activity.BrowserActivity;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.yhkj.cqgyxy.R;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.tool.widget.DragPointView;
import 云华.智慧校园.工具.IsNull;

/**
 * Created by Administrator on 2018/4/16 0016.
 * 4.0 主页
 */

public class MainActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
    @BindView(R.id.main_image1)
    ImageView mainImage1;
    @BindView(R.id.main_text1)
    TextView mainText1;
    @BindView(R.id.main_lin1)
    LinearLayout mainLin1;
    @BindView(R.id.main_rel1)
    RelativeLayout mainRel1;
    @BindView(R.id.main_image2)
    ImageView mainImage2;
    @BindView(R.id.main_text2)
    TextView mainText2;
    @BindView(R.id.main_lin2)
    LinearLayout mainLin2;
    @BindView(R.id.main_rel2)
    RelativeLayout mainRel2;
    @BindView(R.id.main_image3)
    ImageView mainImage3;
    @BindView(R.id.main_text3)
    TextView mainText3;
    @BindView(R.id.main_lin3)
    LinearLayout mainLin3;
    @BindView(R.id.main_rel3)
    RelativeLayout mainRel3;
    @BindView(R.id.main_image4)
    ImageView mainImage4;
    @BindView(R.id.main_text4)
    TextView mainText4;
    @BindView(R.id.main_lin4)
    LinearLayout mainLin4;
    @BindView(R.id.main_rel4)
    RelativeLayout mainRel4;
    @BindView(R.id.main_image5)
    ImageView mainImage5;
    @BindView(R.id.main_text5)
    TextView mainText5;
    @BindView(R.id.main_lin5)
    LinearLayout mainLin5;
    @BindView(R.id.main_rel5)
    RelativeLayout mainRel5;
    @BindView(R.id.main_potinview1)
    DragPointView mainPotinview1;
    @BindView(R.id.main_potinview2)
    DragPointView mainPotinview2;
    @BindView(R.id.main_potinview3)
    DragPointView mainPotinview3;
    @BindView(R.id.main_potinview4)
    DragPointView mainPotinview4;
    @BindView(R.id.main_potinview5)
    DragPointView mainPotinview5;


    private HomePageFragment homePageFragment;
    private MessageFragment messageFragment;
    private MySelfFragment mySelfFragment;


    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private static final String TAG = "MainActivity";

    private List<MainWidgetEntity.ContentBean> contentBeans;

    private MainActivityPresenter preserter;
    private int index = 0;//选择下标

    private int pointViewIndex = 0;//消息提示控件下标

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main4;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {

        Log.d(TAG, "init: " + new SqliteHelper().rawQuery("select * from navigation_list_json").get(0).get("json"));
        MainWidgetEntity adEntity = GsonImpl.get().toObject(new SqliteHelper().rawQuery("select * from navigation_list_json").get(0).get("json"), MainWidgetEntity.class);
        contentBeans = adEntity.getContent();

        initOtherView();
        initPointView();
        initHomePage();

        fragmentManager = getSupportFragmentManager();
        preserter = new MainActivityPresenter(context);
        preserter.getOfflineMessage();

        initWidget();

        initNumButton();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case TagUtil.CheckNumTag:
                initNumButton();
                break;
            case HintTool.DialogDismiss:
                if (ActivityUtil.isForeground(this))
                    EventBus.getDefault().post(new MessageEvent(HintTool.CancelRequest, HintTool.CancelRequest));
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().post(new MessageEvent(HintTool.CloseStartPage, ""));
    }

    @SuppressLint("SetTextI18n")
    private void initNumButton() {
        List<Map<String, String>> maps = new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_look=? ", "false");
        Log.d(TAG, "initNumButton: " + maps);
        int size = new SqliteHelper().rawQuery("select * from client_notice_messagelist where n_look=? ", "false").size();
        switch (pointViewIndex) {
            case 0:
                mainPotinview1.setText(size + "");
                break;
            case 1:
                mainPotinview2.setText(size + "");
                break;
            case 2:
                mainPotinview3.setText(size + "");
                break;
            case 3:
                mainPotinview4.setText(size + "");
                break;
            case 4:
                mainPotinview5.setText(size + "");
                break;
        }
        Log.d(TAG, "initNumButton: " + size);
    }


    @OnClick({R.id.main_rel1, R.id.main_rel2, R.id.main_rel3, R.id.main_rel4, R.id.main_rel5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_rel1:
                index = 0;
                break;
            case R.id.main_rel2:
                index = 1;
                break;
            case R.id.main_rel3:
                index = 2;
                break;
            case R.id.main_rel4:
                index = 3;
                break;
            case R.id.main_rel5:
                index = 4;
                break;
        }
        initWidget();
    }

    /**
     * 初始化基本控件
     */
    private void initWidget() {
        final MainWidgetEntity.ContentBean bean = contentBeans.get(index);
        switch (index) {
            case 0:
                if (bean.getClass_type() == 0) {
                    preserter.intentWidget(this, bean);
                } else {
                    initFragmentOrView(bean, mainText1, mainImage1);
                }
                break;
            case 1:
                if (bean.getClass_type() == 0) {
                    preserter.intentWidget(this, bean);
                } else {
                    initFragmentOrView(bean, mainText2, mainImage2);
                }
                break;
            case 2:
                if (bean.getClass_type() == 0) {
                    preserter.intentWidget(this, bean);
                } else {
                    initFragmentOrView(bean, mainText3, mainImage3);
                }
                break;
            case 3:
                if (bean.getClass_type() == 0) {
                    preserter.intentWidget(this, bean);
                } else {
                    initFragmentOrView(bean, mainText4, mainImage4);
                }
                break;
            case 4:
                if (bean.getClass_type() == 0) {
                    preserter.intentWidget(this, bean);
                } else {
                    initFragmentOrView(bean, mainText5, mainImage5);
                }
                break;

        }
    }

    /**
     * 判断加载fragment
     */
    private void initFragmentOrView(MainWidgetEntity.ContentBean bean, TextView view, ImageView imageView) {
        initTextColor();
        view.setTextColor(Color.parseColor("#3da8f5"));
        GlideLoadUtils.getInstance().glideLoad(this, bean.getPitch_img(), imageView, R.drawable.frist_pushdefaule);
        initFragment(bean);
    }

    /**
     * 初始化消息提示控件
     */

    private void initPointView() {
        for (int i = 0; i < contentBeans.size(); i++) {
            if (contentBeans.get(i).getClass_type() == 2) {
                pointViewIndex = i;
            }
        }
    }

    /**
     * 初始化其他控件
     */
    private void initOtherView() {
        if (contentBeans.size() == 4) {
            mainRel4.setVisibility(View.VISIBLE);
        } else if (contentBeans.size() == 5) {
            mainRel4.setVisibility(View.VISIBLE);
            mainRel5.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化主页
     */
    private void initHomePage() {
        for (int i = 0; i < contentBeans.size(); i++) {
            if (contentBeans.get(i).getState() == 1) {
                index = i;
            }
        }
    }

    /**
     * 初始化加载fragment
     */
    private void initFragment(MainWidgetEntity.ContentBean bean) {
        switch (bean.getClass_type()) {
            case 1:
                initHomePageFragment();
                break;
            case 2:
                initMessageFragment();
                break;
            case 3:
                initMyselfFragment();
                break;
        }
    }

    private void initTextColor() {
        mainText1.setTextColor(Color.parseColor("#666666"));
        mainText2.setTextColor(Color.parseColor("#666666"));
        mainText3.setTextColor(Color.parseColor("#666666"));
        mainText4.setTextColor(Color.parseColor("#666666"));
        mainText5.setTextColor(Color.parseColor("#666666"));

        for (int i = 0; i < contentBeans.size(); i++) {
            MainWidgetEntity.ContentBean bean = contentBeans.get(i);
            switch (i) {
                case 0:
                    mainText1.setText(bean.getTitle());
                    GlideLoadUtils.getInstance().glideLoad(this, bean.getDiy_img(), mainImage1, R.drawable.frist_pushdefaule);
                    break;
                case 1:
                    mainText2.setText(bean.getTitle());
                    GlideLoadUtils.getInstance().glideLoad(this, bean.getDiy_img(), mainImage2, R.drawable.frist_pushdefaule);
                    break;
                case 2:
                    mainText3.setText(bean.getTitle());
                    GlideLoadUtils.getInstance().glideLoad(this, bean.getDiy_img(), mainImage3, R.drawable.frist_pushdefaule);
                    break;
                case 3:
                    mainText4.setText(bean.getTitle());
                    GlideLoadUtils.getInstance().glideLoad(this, bean.getDiy_img(), mainImage4, R.drawable.frist_pushdefaule);
                    break;
                case 4:
                    mainText5.setText(bean.getTitle());
                    GlideLoadUtils.getInstance().glideLoad(this, bean.getDiy_img(), mainImage5, R.drawable.frist_pushdefaule);
                    break;
            }
        }
    }

    /**
     * 隐藏-显示首页fragment
     */
    private void initHomePageFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (homePageFragment == null) {
                homePageFragment = new HomePageFragment();
                fragmentTransaction.add(R.id.main_frame, homePageFragment);
            } else {
                fragmentTransaction.show(homePageFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }


    /**
     * 隐藏-显示消息fragment
     */
    private void initMessageFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (messageFragment == null) {
                messageFragment = new MessageFragment();
                fragmentTransaction.add(R.id.main_frame, messageFragment);
            } else {
                fragmentTransaction.show(messageFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }
    }

    /**
     * 隐藏-显示我的fragment
     */
    private void initMyselfFragment() {
        try {
            fragmentTransaction = fragmentManager.beginTransaction();
            hideFragment(fragmentTransaction);
            if (mySelfFragment == null) {
                mySelfFragment = new MySelfFragment();
                fragmentTransaction.add(R.id.main_frame, mySelfFragment);
            } else {
                fragmentTransaction.show(mySelfFragment);
            }
            fragmentTransaction.commit();
        } catch (Exception e) {
            Log.d(TAG, e.toString());

        }
    }

    /**
     * 去除（隐藏）所有的Fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            //隐藏方法
            transaction.hide(homePageFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
        if (mySelfFragment != null) {
            transaction.hide(mySelfFragment);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // 获取二维码内容
            String url = data.getExtras().getString("result");
            if (IsNull.isNotNull(url)) {
                // 提取二维码中的用户编号
//                url = new URLHelper().getParames(url, "userid");
                if (IsNull.isNotNull(url)) {
//                    Intent intent = new Intent(this, FriendDetailActivity.class);
//                    intent.putExtra("fqr", url);
//                    this.startActivity(intent);
                    Intent intent;
                    if (url.startsWith("http")) {
                        intent = new Intent(this, BrowserActivity.class);
                        intent.putExtra("url", url);
                        intent.putExtra("function_id", "");
                        intent.putExtra("title", "");
                    } else {
                        intent = new Intent(this, QRResultActivity.class);
                        intent.putExtra("result", url);
                    }
                    startActivity(intent);
                }
            }
        } catch (Exception e) {
        }
        switch (requestCode) {
            // 相机
            case CameraUtil.CAMERA_REQUEST:
                switch (resultCode) {
                    case -1:// -1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory() + "/hand.jpg");// 保存图片
                        if (file.exists()) {
                            // 对相机拍照照片进行裁剪
                            mySelfFragment.cameraUtil.photoClip(Uri.fromFile(file));
                        }
                }
                break;

            case CameraUtil.PHOTO_REQUEST:// 从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    // 对相册取出照片进行裁剪
                    mySelfFragment.cameraUtil.photoClip(uri);
                }
                break;
            // 裁剪图片
            case CameraUtil.PHOTO_CLIP:
                if (data != null) {
                    // 完成
                    mySelfFragment.cameraUtil.getWRITE_EXTERNAL_STORAGE();
                }
                break;
        }
    }
    //--------------使用onKeyDown()双击退出--------------

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.Toast(this, "再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
//                System.exit(0);
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LoginUtil.unregisterJpush();
    }

}
