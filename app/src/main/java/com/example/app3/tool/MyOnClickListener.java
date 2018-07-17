package com.example.app3.tool;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Administrator on 2017/9/27.
 */

public class MyOnClickListener implements View.OnClickListener {
    private String cls;
    private Context context;

    public MyOnClickListener(String cls, Context context) {

        this.cls = cls;
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        if (!Tool.isFastDoubleClick()) {
            try {
                Intent intent = new Intent(cls);
                context.startActivity(intent);
            } catch (Exception e) {

            }
        }
    }
}
