package com.yunhuakeji.app.utils;

import com.yunhuakeji.app.utils.FileHelper.OnFileDownloadCompleteLisener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

@SuppressWarnings("unused")
public class Downloadbroader extends BroadcastReceiver {
    private String filePath;
    private String fileName;
    private OnFileDownloadCompleteLisener FileDownloadCompleteLisener;

    public Downloadbroader(OnFileDownloadCompleteLisener FileDownloadCompleteLisener, long DownloadID, String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.FileDownloadCompleteLisener = FileDownloadCompleteLisener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getIntExtra("msg", 0) == 1) {
            Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
        } else {
            try {
                FileDownloadCompleteLisener.onFileDownloadCompleteLisener(filePath, fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}