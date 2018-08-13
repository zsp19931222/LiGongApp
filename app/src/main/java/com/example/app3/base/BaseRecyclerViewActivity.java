package com.example.app3.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app4.base.CompatStatusBarActivity;
import com.yhkj.cqgyxy.R;

import butterknife.ButterKnife;
import yh.app.activitytool.ActivityPortrait;

/**
 * Created by Administrator on 2017/9/21.
 */

public abstract class BaseRecyclerViewActivity extends CompatStatusBarActivity {
    public Activity activity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBar();
        activity = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        init(this);
        setTitle(this);
        loadRecyclerViewData(this);

    }


    protected abstract int getLayoutId();


    public void startAcivity(Class classNamse) {
        startActivity(new Intent(activity, classNamse));
    }

    protected abstract void setTitle(Context context);

    protected abstract void loadRecyclerViewData(Context context);
    protected abstract void init(Context context);

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBar() {
        setImmersiveStatusBar(true,getResources().getColor(R.color.white));
//        StatusBarUtil.setColor(this,
//                getResources().getColor(R.color.white), 1);
    }
}
