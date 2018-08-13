package com.example.app3.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.entity.FileModel;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.utils.DownLoadUtil;
import com.example.app3.view.NumberProgressBar;
import com.lzy.okgo.model.Progress;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/24 0024.
 *
 * 下载测试
 */

public class Test extends BaseRecyclerViewActivity {
    @BindView(R.id.down)
    Button down;
    @BindView(R.id.number_bar)
    NumberProgressBar numberBar;
    private List<FileModel> fileModels;
    private FileModel fileModel;
    private DownLoadUtil downLoadUtil;
    private Progress progress;


    @Override
    protected int getLayoutId() {
        return R.layout.test_file;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        EventBus.getDefault().register(this);
        numberBar.setMax(100);
        fileModels = new ArrayList<>();
        fileModel = new FileModel();
        fileModel.name = "";
        fileModel.url = "https://staticlive.douyucdn.cn/upload/client/douyu_client_2_0v1_2_0.apk";
        fileModels.add(fileModel);
        downLoadUtil = new DownLoadUtil(this, fileModels);
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
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe(priority = 100)
    public void onEventMainThread(MyEventBus event) {
        progress = (Progress) event.getMsg();
        numberBar.setProgress((int) (progress.fraction * 100));
        switch (progress.status) {
            case Progress.NONE:
                down.setText("下载");
                break;
            case Progress.PAUSE:
                down.setText("继续");
                break;
            case Progress.ERROR:
                down.setText("出错");
                break;
            case Progress.WAITING:
                down.setText("等待");
                break;
            case Progress.FINISH:
                down.setText("重新下载");
                break;
            case Progress.LOADING:
                down.setText("暂停");
                break;
        }
    }

    private String text;
    @OnClick(R.id.down)
    public void onViewClicked() {
        text=down.getText().toString();
        switch (text){
            case "下载":
                downLoadUtil.start();
                break;
            case "暂停":
                downLoadUtil.pause();
                break;
            case "重新下载":
                downLoadUtil.restart();
                break;
            case "继续":
                downLoadUtil.start();
                break;
        }
    }
}
