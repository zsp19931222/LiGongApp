package com.example.app3.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.utils.DownloadService;
import com.example.app3.utils.MToast;
import com.example.app3.view.NumberProgressBar;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/11/7.
 */

public class  UpDateActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.update_content)
    TextView updateContent;
    private boolean isBindService;
    String updateURL;


    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            DownloadService.DownloadBinder binder = (DownloadService.DownloadBinder) service;
            DownloadService downloadService = binder.getService();

            //接口回调，下载进度
            downloadService.setOnProgressListener(new DownloadService.OnProgressListener() {
                @Override
                public void onProgress(float fraction) {
                    numberBar.setProgress((int) (fraction * 100));

                    //判断是否真的下载完成进行安装了，以及是否注册绑定过服务
                    if (fraction == DownloadService.UNBIND_SERVICE && isBindService) {
                        unbindService(conn);
                        isBindService = false;
                        MToast.shortToast("下载完成！");
                        finish();
                    }
                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(UpDateActivity.this, "下载失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                if (updateURL != null) {
                    Intent intent = new Intent(UpDateActivity.this, DownloadService.class);
                    intent.putExtra(DownloadService.BUNDLE_KEY_DOWNLOAD_URL, updateURL);
                    isBindService = bindService(intent, conn, BIND_AUTO_CREATE);
                }
            } else {
                Toast.makeText(UpDateActivity.this, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @BindView(R.id.number_bar)
    NumberProgressBar numberBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {
        String s="2.如果出现内部无法更新情况，请<font color='#FF0000'><big>点击下载</big></font>安装";
        updateContent.setText(Html.fromHtml(s));

    }

    @Override
    protected void init(Context context) {
        updateURL = getIntent().getExtras().getString("url");
        new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.update_content)
    public void onViewClicked() {
        Uri uri = Uri.parse(_链接地址导航.UIA.下载页面.getUrl() + Constants.number);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
