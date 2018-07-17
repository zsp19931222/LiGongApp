package com.example.app4.onclick;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.app3.activity.AllMessageList;
import com.example.app3.activity.ListActivity;
import com.example.app3.tool.Tool;
import com.example.app4.bean.HomePageRecBean;
import com.example.app4.presenter.HomePageFragmentPresenter;

/**
 * Created by Administrator on 2018/4/27 0027.
 */

public class CheckMore implements View.OnClickListener {

    private HomePageRecBean data;
    private Context context;
    private HomePageFragmentPresenter presenter;

    public CheckMore(HomePageRecBean data, Context context, HomePageFragmentPresenter presenter) {
        this.data = data;
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void onClick(View v) {
        if (!Tool.isFastDoubleClick()) {
            Intent intent = null;
            switch (data.getM_classify()) {
                case "group":
                    intent = new Intent(context, ListActivity.class);
                    intent.putExtra("function_id", data.getM_function_id());
                    presenter.checkGroup(data.getM_group_id());
                    break;
                case "function":
                    intent = new Intent(context, AllMessageList.class);
                    intent.putExtra("function_id", data.getM_function_id());
                    presenter.checkFuncation(data.getM_function_id());
                    break;
            }
            if (intent != null) {
                context.startActivity(intent);
            }
        }
    }
}
