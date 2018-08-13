package com.example.app3.childview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import java.util.Locale;

import 云华.智慧校园.功能.DownloadHelper;

/**
 * 文件下载弹出图层
 */
public class DownloadPopupWindow {
    private Context context;
    private String fileName;
    private String url;
    private double fileSize;
    private static PopupWindow window;

    public DownloadPopupWindow(Context context, String fileName, String url, double fileSize) {
        this.context = context;
        this.fileName = fileName;
        this.url = url;
        this.fileSize = fileSize;
        if (window != null) {
            window.dismiss();
            window = null;
        }
    }

    /**
     * 构造并且显示这个popwindow
     *
     * @param view
     */
    public void show(View view) {
        View layout = LayoutInflater.from(context).inflate(R.layout.pop_download, null, false);
        window = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setTouchable(true);
        window.setOutsideTouchable(true);
        window.setFocusable(true);
        window.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

        window.setAnimationStyle(R.style.PopupAnimation);

        ((TextView) layout.findViewById(R.id.name)).setText(fileName);
        ((TextView) layout.findViewById(R.id.size)).setText(switchSize(fileSize));
        layout.findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadHelper.startDownload(context, url, fileName);
                window.dismiss();
            }
        });

        layout.findViewById(R.id.view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                window.dismiss();
            }
        });
        window.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    /**
     * 文件bit大小转换为常用大小计量单位
     *
     * @param fileSize
     * @return
     */
    // FIXME 这个函数要单独提出去作为一个工具类函数
    public static String switchSize(double fileSize) {
        String append;
        double switchSize = 0;
        if (fileSize < 1024) {
            switchSize = fileSize;
            append = "B";
        } else if (fileSize < 1024 * 1024) {
            switchSize = fileSize / 1024;
            append = "KB";
        } else if (fileSize < 1024 * 1024 * 1024) {
            switchSize = fileSize / 1024 / 1024;
            append = "MB";
        } else {
            switchSize = fileSize / 1024 / 1024 / 1024;
            append = "GB";
        }
        return String.format(Locale.CHINA, "%.2f", new Object[]{switchSize}) + append;
    }
}
