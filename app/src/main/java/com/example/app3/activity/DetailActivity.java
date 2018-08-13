package com.example.app3.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.tool.TimeTool;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.SqliteHelper;

/**
 * Created by Administrator on 2017/9/29.
 */

public class DetailActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.detail_title)
    MyTitleView detailTitle;
    @BindView(R.id.detail_text_message)
    TextView detailTextMessage;
    @BindView(R.id.detail_text_from)
    TextView detailTextFrom;
    @BindView(R.id.detail_text_time)
    TextView detailTextTime;
    @BindView(R.id.detail_text_title)
    TextView detailTextTitle;

    private String n_id;
    private String title, text_title, message, time;
    private List<Map<String, String>> map, new_push_functionList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void setTitle(Context context) {
        detailTitle.setTitle(title, context);
        detailTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        n_id = getIntent().getExtras().getString("id");
        map = new SqliteHelper().rawQuery("select * from client_notice where n_id=?", n_id);
        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", n_id);
        new SqliteHelper().rawQuery("update client_notice_messagelist set read=? where n_id=?", "true", n_id);
        new_push_functionList = new SqliteHelper().rawQuery("select * from new_push_function where ts_id=?", map.get(0).get("function_id"));
        time = TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(map.get(0).get("n_send_time"), "yyyy-MM-dd HH:mm:ss")), "yyyy年MM月dd日");
        try {
            title = new_push_functionList.get(0).get("ts_name");
        } catch (Exception e) {
            title = "消息";
        }
        text_title = map.get(0).get("n_title");
        message = map.get(0).get("n_message");
        detailTextTitle.setText(text_title);
        detailTextMessage.setText(message);
        detailTextTime.setText(time);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
