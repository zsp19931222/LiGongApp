package com.example.app4.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.app3.adapter.dragrecyclear.common.DividerItemDecoration;
import com.example.app3.tool.ContactComparator;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.app3.tool.Utils;
import com.example.app4.adapter.ContactAdapter;
import com.example.app4.adapter.HeaderAdapterWrapper1;
import com.example.app4.api.GetAppUrl;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.DefaultUtil;
import com.example.app4.util.GetSchoolListUtil;
import com.example.app4.util.PlaceNamesUtil;
import com.example.app4.view.LetterView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

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
import yh.app.tool.SqliteHelper;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/2/27 0027.
 * 学校展示页面
 */

public class ShowSchoolActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.search_school_location_text)
    TextView searchSchoolLocationText;
    @BindView(R.id.search_school_search_text)
    TextView searchSchoolSearchText;
    @BindView(R.id.search_school_lin)
    LinearLayout searchSchoolLin;
    @BindView(R.id.search_school_rec)
    RecyclerView searchSchoolRec;
    @BindView(R.id.search_school_letter_view)
    LetterView searchSchoolLetterView;

    private List<String> mContactList; // 学校列表（转换成拼音）
    private List<String> characterList; // 字母列表
    private List<Map<String, String>> contactNames;
    private Map<String, String> map;
    private ContactAdapter contactAdapter;
    private LinearLayoutManager layoutManager;

    private HeaderAdapterWrapper1 headerAdapterWrapper1;

    private String area = DefaultUtil.getDefaultArea();//选中地点
    private int localVersion = -1;//本地版本

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_school;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {
    }


    @Override
    protected void init(Context context) {
        startLocate();
        searchSchoolLocationText.setText(area);
        characterList = new ArrayList<>();
        mContactList = new ArrayList<>();
        contactNames = new ArrayList<>();
        layoutManager = new LinearLayoutManager(context);
        getSchoolList();
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

    @Override
    protected void onPause() {
        super.onPause();
        mLocationClient.unRegisterLocationListener(myListener);
    }

    /**
     * 获取学校列表数据
     */
    private Map<String, String> getSchoolListMap;

    private void getSchoolList() {

        try {
            contactNames=GetSchoolListUtil.getSchoolData("select xxmcpy as szm, xxmc as name, xxbh as FRIEND_ID, szd as userid, xxtb as handimg from schools where szd like '%" + area + "%'",contactNames);
            localVersion = Integer.valueOf(new SqliteHelper().rawQuery("select * from school_list").get(0).get("version"));
        } catch (Exception ignored) {
            localVersion = -1;//初始version参数传-1
            showLoad(HintTool.Loading);
        }

        if (getSchoolListMap == null) {
            getSchoolListMap = new HashMap<>();
        }
        getSchoolListMap.put("version", localVersion + "");
        setRecyclerView(this);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(getSchoolListMap, GetAppUrl.DC.getSchoolList.getUrl(), TagUtil.GetSchoolListTag);
    }

    private void setRecyclerView(final Context context) {
        map = new HashMap<>();
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
        searchSchoolLetterView.initView(characterList);
        contactAdapter = new ContactAdapter(context, contactNames);
        headerAdapterWrapper1 = new HeaderAdapterWrapper1(contactAdapter, contactNames);
        View headView = LayoutInflater.from(context).inflate(R.layout.cm_header, null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 0);
        headView.setLayoutParams(params);
        headerAdapterWrapper1.addHeaderView(headView);

        searchSchoolRec.setLayoutManager(layoutManager);
        searchSchoolRec.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        searchSchoolRec.setAdapter(headerAdapterWrapper1);
        searchSchoolLetterView.setCharacterListener(new LetterView.CharacterClickListener() {
            @Override
            public void clickCharacter(String character) {
                layoutManager.scrollToPositionWithOffset(contactAdapter.getScrollPosition(character), 0);
            }

            @Override
            public void clickArrow() {
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
    }


    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        Object result = event.getMessage();
        if (ActivityUtil.isForeground(this))
            switch (event.getTag()) {
            case TagUtil.GetSchoolListTag:
                String s = (String) result;
                if (!s.equals(HintTool.REQUESTFAIL)) {
//                    GetSchoolListEntity getSchoolListEntity = GsonImpl.get().toObject(s, GetSchoolListEntity.class);
//                    int version = getSchoolListEntity.getContent().getVersion();
//                    if (version != localVersion) {// 根据version判断是否需要更新本地数据库
//                        List<GetSchoolListEntity.ContentBean.ListsBean> lists = getSchoolListEntity.getContent().getLists();
//                        for (int i = 0; i < lists.size(); i++) {
//                            new SqliteHelper().rawQuery("insert or replace into school_list(version,xxmc,xxbh,szd,xxtb) values(?,?,?,?,?)"
//                                    , version + ""
//                                    , lists.get(i).getXxmc()
//                                    , lists.get(i).getXxbh()
//                                    , lists.get(i).getSzd()
//                                    , lists.get(i).getXxtb()
//                            );
//                        }
//                    }

                }
                refreshSchool(area);
                break;
            case TagUtil.ChooseAreaTag:
                area = (String) result;
                refreshSchool(area);
                break;
        }
        dismissLoad();
    }

    @OnClick({R.id.search_school_location_rel, R.id.search_school_search_text})
    public void onViewClicked(View view) {
        if (!Tool.isFastDoubleClick()) {
            Intent intent = null;
            switch (view.getId()) {
                case R.id.search_school_location_rel:
                    intent = new Intent(this, AreaActivity.class);
                    overridePendingTransition(R.anim.bottom_pop_in, 0);
                    break;
                case R.id.search_school_search_text:
                    intent = new Intent(this, SearchSchoolActivity.class);
                    break;
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    }

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    /**
     * 定位
     */
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

        @Override
        public void onReceiveLocation(BDLocation location) {
            String localArea;
            if (location.getLocType() == 161) {//定位成功
                localArea = PlaceNamesUtil.dealPlaceNames(location.getProvince());
            } else {
                localArea = DefaultUtil.getDefaultArea();
                ToastUtils.Toast(ShowSchoolActivity.this, HintTool.LocationFail);
            }
            DefaultUtil.setDefaultArea(localArea);
            refreshSchool(localArea);
        }
    }


    /**
     * 刷新地区
     */
    private void refreshSchool(String area) {
        searchSchoolLocationText.setText(area);
        contactNames.clear();
        mContactList.clear();
        characterList.clear();
        try {
            contactNames=GetSchoolListUtil.getSchoolData("select xxmcpy as szm, xxmc as name, xxbh as FRIEND_ID, szd as userid, xxtb as handimg from schools where szd like '%" + area + "%'",contactNames);
            headerAdapterWrapper1.ref();
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < contactNames.size(); i++) {
                if (contactNames.get(i).get("name") != null) {
                    String pinyin = Utils.getPingYin(contactNames.get(i).get("name"));
                    map.put(pinyin, contactNames.get(i).get("name"));
                    mContactList.add(pinyin);
                }
            }
            Collections.sort(mContactList, new ContactComparator());
            for (int i = 0; i < mContactList.size(); i++) {
                String name = mContactList.get(i);
                String character = (name.charAt(0) + "").toUpperCase(Locale.ENGLISH);
                if (!characterList.contains(character)) {
                    if (character.hashCode() >= "A".hashCode() && character.hashCode() <= "Z".hashCode()) { // 是字母
                        characterList.add(character);
                    }
                }
            }
            searchSchoolLetterView.initView(characterList);
        } catch (Exception ignored) {

        }
    }

}
