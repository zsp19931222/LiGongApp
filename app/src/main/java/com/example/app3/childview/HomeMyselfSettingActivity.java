package com.example.app3.childview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.app3.adapter.BaseListAdapter;
import com.example.app3.base.BaseActivity;
import com.yunhuakeji.app.utils.JsonReaderHelper;
import yh.app.appstart.lg.R;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.utils.TopbarHelper_v3;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.RHelper;

public class HomeMyselfSettingActivity extends BaseActivity {

    /**
     * 顶部导航栏
     */
    @BindView(R.id.topbar_v3)
    LinearLayout topbarV3;

    @BindView(R.id.home_myself_setting_listview)
    ListView homeMyselfSettingListview;

    private JSONArray data;
    private TopbarHelper_v3 topbarHelper_v3;

    @Override
    protected void initActivityView() {
        setContentView(R.layout.activity_home_myself_setting);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        topbarHelper_v3 = new TopbarHelper_v3(this, topbarV3);
        topbarHelper_v3.setTitle("设置");
    }

    @Override
    protected void initData() {
        try {
            data = new JSONArray(JsonReaderHelper.getJosn(this, "meself.json"));
        } catch (Exception e) {

        }
    }

    @Override
    protected void initAction() {
        topbarHelper_v3.setOnClickListener(null);
        try {
            homeMyselfSettingListview.setAdapter(new BaseListAdapter(this, homeMyselfSettingListview, data));
        } catch (Exception e) {
        }
    }
}
