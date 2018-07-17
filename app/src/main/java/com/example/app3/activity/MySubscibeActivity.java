package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.AllSubscibeEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.view.MyTitleView;

import org.androidpn.push.Constants;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.appstart.lg.R;
import yh.app.tool.MD5;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/9/21.
 * <p>
 * 我的订阅
 */

public class MySubscibeActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.myreport_title)
    MyTitleView myreportTitle;
    @BindView(R.id.myreport_rec)
    RecyclerView myreportRec;
    @BindView(R.id.no_data_lin)
    LinearLayout noDataLin;

    private LoadDiaog loadDiaog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_report;
    }

    @Override
    protected void setTitle(Context context) {
//        myreportTitle.setRightImage(R.drawable.quanzi_serch_img);
        myreportTitle.setTitle(context.getResources().getString(R.string.title_activity_mysubscibe), context);
        myreportTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private QuickAdapter adapter;

    @Override
    protected void loadRecyclerViewData(final Context context) {
        getNetWorkData(context);
    }

    @Override
    protected void init(Context context) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

    private void showDiaog(Context context) {
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
    }

    /**
     * 获取网络订阅数据
     *
     * @param context
     */
    private void getNetWorkData(final Context context) {
        showDiaog(context);
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.全部订阅消息列表v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    loadRecyclerViewData(context, result);
                } else {
                    noDataLin.setVisibility(View.VISIBLE);
                    myreportRec.setVisibility(View.GONE);
                    if (loadDiaog.isShowing()) {
                        loadDiaog.dismiss();
                    }
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                noDataLin.setVisibility(View.VISIBLE);
                myreportRec.setVisibility(View.GONE);
            }
        });
    }

    private TextView checkText;

    /**
     * 加载到RecyclerView
     *
     * @param context
     * @param result  返回json数据
     */
    private void loadRecyclerViewData(final Context context, String result) {
        AllSubscibeEntity entity = GsonImpl.get().toObject(result, AllSubscibeEntity.class);
        adapter = new QuickAdapter<AllSubscibeEntity.ContentBean>(entity.getContent()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_subscibe;
            }

            @Override
            public void convert(VH holder, final AllSubscibeEntity.ContentBean data, int position) {
                holder.setImageView((Activity) context, R.id.item_subscibe_image, data.getTs_icon());
                holder.setTextView(R.id.item_subscibe_text_name, data.getTs_name());
                holder.setTextView(R.id.item_subscibe_explain, data.getTs_describe());
                final TextView textView = holder.setTextView(R.id.item_subscibe_checkbox, "");
                if (data.getState().equals("1")) {
                    textView.setBackgroundResource(R.drawable.subscibe_true);
                } else {
                    textView.setBackgroundResource(R.drawable.subscibe_false);
                }
                if (loadDiaog.isShowing()) {
                    loadDiaog.dismiss();
                }
                holder.setTextView(R.id.item_subscibe_checkbox, "").setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeSubscibeNetWrk(context, textView, data);
                    }
                });
            }
        };
        myreportRec.setLayoutManager(new LinearLayoutManager(context));
        myreportRec.setAdapter(adapter);
    }

    /**
     * 修改订阅情况
     *
     * @param checkBox 点击的Checkbox
     * @param data     解析的数据
     * @param context
     */
    private void changeSubscibe(final Context context, final CheckBox checkBox, final AllSubscibeEntity.ContentBean data) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            }
        });
    }

    /**
     * 通知服务器修改情况
     *
     * @param context
     * @param checkBox
     * @param data
     */
    private void changeSubscibeNetWrk(final Context context, final TextView checkBox, final AllSubscibeEntity.ContentBean data) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        },
                        {
                                "state", data.getState()
                        },
                        {
                                "function_id", data.getTs_id()
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.修改订阅情况v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    if (data.getState().equals("1")) {
                        data.setState("0");
                        checkBox.setBackgroundResource(R.drawable.subscibe_false);
                    } else {
                        data.setState("1");
                        checkBox.setBackgroundResource(R.drawable.subscibe_true);
                    }
                } else {
                    ToastUtils.Toast(context, HintTool.CHANGEFAIL);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                ToastUtils.Toast(context, HintTool.CHANGEFAIL);
            }
        });
    }

}
