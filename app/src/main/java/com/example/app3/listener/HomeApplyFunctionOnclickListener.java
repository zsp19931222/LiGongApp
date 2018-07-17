package com.example.app3.listener;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.app3.tool.Tool;

import java.util.List;
import java.util.Map;

import 云华.智慧校园.工具._功能跳转;

;

public class HomeApplyFunctionOnclickListener implements OnItemClickListener {
    private List<Map<String, String>> data;
    private Context context;


    public HomeApplyFunctionOnclickListener(Context context, List<Map<String, String>> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!Tool.isFastDoubleClick()) {
            try {
                new _功能跳转().Jump(context, data.get(position));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}