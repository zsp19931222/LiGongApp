package com.example.app4.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Utils;
import com.example.app3.view.MyTitleView;
import com.example.app4.adapter.ContactAdapter;
import com.example.app4.adapter.HeaderAdapterWrapper1;
import com.example.app4.entity.AreaEntity;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.PlaceNamesUtil;
import com.example.app4.view.LetterView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.logTool.Log;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.JsonReaderHelper;

/**
 * Created by Administrator on 2018/3/7 0007.
 * 地区选择
 */

public class AreaActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.area_title)
    MyTitleView areaTitle;
    @BindView(R.id.area_rec)
    RecyclerView areaRec;
    @BindView(R.id.area_letter_view)
    LetterView areaLetterView;

    private List<String> mContactList; // 学校列表（转换成拼音）
    private List<String> characterList; // 字母列表
    private List<Map<String, String>> contactNames;
    private ContactAdapter contactAdapter;
    private LinearLayoutManager layoutManager;

    private ImageView head_area_refresh_image;
    private Animation operatingAnim;
    private TextView head_area_current_position_text;

    private String locationStr = DefaultUtil.getDefaultArea();//定位

    @Override
    protected int getLayoutId() {
        return R.layout.activity_area;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

        characterList = new ArrayList<>();
        mContactList = new ArrayList<>();
        contactNames = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        simulatedData();
    }

    @Override
    protected void init(Context context) {
        locationStr = DefaultUtil.getDefaultArea();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * 模拟数据
     */
    private void simulatedData() {
        AreaEntity areaEntity = GsonImpl.get().toObject(JsonReaderHelper.getJosn(activity, "province.json"), AreaEntity.class);
        for (int i = 0; i < areaEntity.getAllTagsList().size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", areaEntity.getAllTagsList().get(i).getName());
            map.put("FRIEND_ID", "");
            map.put("handimg", "");
            map.put("userid", "");
            map.put("szm", "");
            contactNames.add(map);
        }
        setRecyclerView(this);
    }

    @SuppressLint("SetTextI18n")
    private void setRecyclerView(final Context context) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < contactNames.size(); i++) {
            if (contactNames.get(i) != null) {
                String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                map.put(pinyin, contactNames.get(i).get("name"));
                mContactList.add(pinyin);
            }
        }
        Collections.sort(mContactList, new ContactComparator());
        characterList = new ArrayList<>();
        for (int i = 0; i < mContactList.size(); i++) {
            String name = mContactList.get(i);
            String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
            if (!characterList.contains(character)) {
                if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                    characterList.add(character);
                }
            }
        }
        areaLetterView.initView(characterList);
        contactAdapter = new ContactAdapter(context, contactNames);
        HeaderAdapterWrapper1 headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);
        View headView = LayoutInflater.from(context).inflate(R.layout.head_area, null, false);
        head_area_current_position_text = headView.findViewById(R.id.head_area_current_position_text);
        head_area_refresh_image = headView.findViewById(R.id.head_area_refresh_image);
        LinearLayout head_area_refresh_lin = headView.findViewById(R.id.head_area_refresh_lin);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper1.addHeaderView(headView);

        areaRec.setLayoutManager(layoutManager);
        areaRec.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        areaRec.setAdapter(headerAdapterWrapper1);
        areaLetterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(contactAdapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });

        head_area_current_position_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new MessageEvent(TagUtil.ChooseAreaTag, locationStr));
            }
        });

        head_area_refresh_lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocate();
                startRefreshImageAnim();
            }
        });
        head_area_current_position_text.setText("定位位置：" + locationStr);
    }

    /**
     * 开始刷新图片旋转动画
     */
    private void startRefreshImageAnim() {
        if (operatingAnim == null) {
            operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate_anim);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
        }
        head_area_refresh_image.startAnimation(operatingAnim);
    }

    /**
     * 停止刷新图片旋转动画
     */
    private void stopRefreshImageAnim() {
        head_area_refresh_image.clearAnimation();
    }

    /**
     * 定位
     */
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private void startLocate() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setTimeOut(10 * 1000);
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == 161) {//定位成功
                locationStr = PlaceNamesUtil.dealPlaceNames(location.getProvince());
            } else {
                locationStr = DefaultUtil.getDefaultArea();
                ToastUtils.Toast(AreaActivity.this, HintTool.LocationFail);
            }
            head_area_current_position_text.setText("定位位置：" + locationStr);
            DefaultUtil.setDefaultArea(locationStr);
            stopRefreshImageAnim();
        }
    }

    @OnClick(R.id.area_cancel_rel)
    public void onViewClicked() {
        finish();
        overridePendingTransition(0, R.anim.bottom_pop_out);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        switch (event.getTag()) {
            case TagUtil.ChooseAreaTag:
                finish();
                overridePendingTransition(0, R.anim.bottom_pop_out);
                break;
        }
    }
}
