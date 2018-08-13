package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.SubscibeEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.MD5;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/9/20.
 * <p>
 * 订阅消息页面
 *
 */

public class SubscibeActivity extends ActivityPortrait {
    @BindView(R.id.subscibe_title)
    MyTitleView subscibeTitle;
    @BindView(R.id.subscibe_text_search)
    TextView subscibeEdSearch;
    @BindView(R.id.subscibe_lin_search)
    LinearLayout subscibeLinSearch;
    @BindView(R.id.subscibe_rec)
    RecyclerView subscibeRec;

    private QuickAdapter adapter;
    private LoadDiaog loadDiaog;

    private String titleStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscibe);
        ButterKnife.bind(this);
        setTitle(this);
        showDiaog(this);
        getNetWorkData(this);
    }
    /**
     * title设置
     */
    private void setTitle(Context context) {
        titleStr = context.getResources().getString(R.string.title_activity_subscibe);
        subscibeTitle.setTitle(titleStr, context);
        subscibeTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void showDiaog(Context context) {
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
    }

    /**
     * RecyclerView数据加载
     */
    private void loadRecyclerViewData(final Context context, String result) {
        SubscibeEntity entity = GsonImpl.get().toObject(result, SubscibeEntity.class);
        for (int i = 0; i < entity.getContent().size(); i++) {
            if (entity.getContent().get(i).getTs_group_name().equals(titleStr)) {
                adapter = new QuickAdapter<SubscibeEntity.ContentBean.ListBean>(entity.getContent().get(i).getList()) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.item_subscribe;
                    }

                    @Override
                    public void convert(VH holder, SubscibeEntity.ContentBean.ListBean data, int position) {
                        holder.forbidSideslip();
                        holder.setImageView((Activity) context, R.id.item_subscribe_image, data.getTs_icon());
                        holder.setTextView(R.id.item_subscribe_text_name, data.getTs_name());
                        if (loadDiaog.isShowing()) {
                            loadDiaog.dismiss();
                        }
                    }
                };
            }
        }

        subscibeRec.setLayoutManager(new LinearLayoutManager(context));
        subscibeRec.setAdapter(adapter);
    }

    /**
     * 获取网络数据
     */
    private void getNetWorkData(final Context context) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.订阅消息列表v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String code = jsonObject.getString("code");
                    if (code.equals("40001")) {//访问成功
                        loadRecyclerViewData(context, result);
                    } else {
                        ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                }

            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

}
