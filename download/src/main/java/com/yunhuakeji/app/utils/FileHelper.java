package com.yunhuakeji.app.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.UUID;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;

public class FileHelper {
    /**
     * 文件存在时覆盖
     */
    public static final int REPLACE_MODE = 0x00000001;

    /**
     * 文件存在时重命名
     */
    public static final int RENAME_MODE = 0x00000010;

    public void doSaveByFileName(Context context, String url, String fileName, int saveMode, OnFileDownloadCompleteLisener FileDownloadCompleteLisener) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        String path = context.getApplicationContext().getFilesDir().toString();
        request.setDestinationInExternalFilesDir(context, path, fileName);
        context.registerReceiver(new Downloadbroader(FileDownloadCompleteLisener, downloadManager.enqueue(request), path, fileName), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    public void doSaveNoFileName(Context context, String url, int saveMode, OnFileDownloadCompleteLisener FileDownloadCompleteLisener) {
        doSaveByFileName(context, url, UUID.randomUUID().toString(), saveMode, FileDownloadCompleteLisener);
    }

    public interface OnFileDownloadCompleteLisener {
        void onFileDownloadCompleteLisener(String filePath, String fileName) throws Exception;
    }

    public static File createFile(String path, String fileName) {
        File file = new File(path, fileName);
        if (!exists(path, fileName)) {
            try {
                file.createNewFile();
            } catch (Exception e) {
            }
        }
        return file;
    }

    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    public static boolean exists(String path, String fileName) {
        return new File(path + "/" + fileName) != null && new File(path + "/" + fileName).exists();
    }

    public static String getFileContent(File file) {
        try {
            StringBuffer sb = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                sb.append(lineTxt);
            }
            bufferedReader.close();
            String result = sb.toString();
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean write(File file, String content) {
        BufferedWriter bf = null;
        try {
            bf = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bf.write(content);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            close(bf);
        }
    }

    private static void close(BufferedWriter bf) {
        try {
            bf.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}