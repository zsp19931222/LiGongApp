package com.example.app3.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.example.app3.entity.FileModel;
import com.example.app3.eventbus.MyEventBus;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.DownloadManager;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okserver.OkDownload;
import com.lzy.okserver.download.DownloadListener;
import com.lzy.okserver.download.DownloadTask;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import rx.Observer;

/**
 * Created by Administrator on 2017/11/24 0024.
 *
 * 下载文件类
 */

public class DownLoadUtil {
    private Context context;
    private List<FileModel> datas;
    private DownloadTask task;
    private OkDownload okDownload;
    private List<Progress> progressList;


    public DownLoadUtil(Context context, List<FileModel> datas) {
        this.context = context;
        this.datas = datas;

        okDownload = OkDownload.getInstance();
        okDownload.setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ydxy/");
        okDownload.getThreadPool().setCorePoolSize(3);
        //从数据库中恢复数据
        progressList = DownloadManager.getInstance().getAll();
        OkDownload.restore(progressList);
        for (int i = 0; i < progressList.size(); i++) {
            task = okDownload.getTask(progressList.get(i).tag);
            EventBus.getDefault().post(new MyEventBus(progressList.get(i)));
        }
    }

    /**
     * 获取权限
     */
    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                for (FileModel data : datas) {
                    GetRequest<File> request = OkGo.<File>get(data.url);
                    //这里第一个参数是tag，代表下载任务的唯一标识
                    OkDownload.request(data.url, request)
                            .priority(data.priority)//
                            .extra1(data)//
                            .register(new LogDownloadListener())
                            .start();
                }
            } else {
                Toast.makeText(context, "SD卡下载权限被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    };
    /**
     * 下载文件
     */
    public void start() {
        new RxPermissions((Activity) context).request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(observer);
        Toast.makeText(context, "已添加到下载任务", Toast.LENGTH_SHORT).show();
    }
    /**
     * 重新下载
     */
    public void restart() {
        task.restart();
    }

    /**
     * 暂停
     */
    public void pause() {
        task.pause();
    }

    /**
     * 下载过程监听
     */
    public class LogDownloadListener extends DownloadListener {

        public LogDownloadListener() {
            super("LogDownloadListener");
        }

        @Override
        public void onStart(Progress progress) {
            EventBus.getDefault().post(new MyEventBus(progress));
        }

        @Override
        public void onProgress(Progress progress) {
            EventBus.getDefault().post(new MyEventBus(progress));
        }

        @Override
        public void onError(Progress progress) {
            EventBus.getDefault().post(new MyEventBus(progress));
            progress.exception.printStackTrace();
        }

        @Override
        public void onFinish(File file, Progress progress) {
            EventBus.getDefault().post(new MyEventBus(progress));
        }

        @Override
        public void onRemove(Progress progress) {
            EventBus.getDefault().post(new MyEventBus(progress));
        }
    }
}
