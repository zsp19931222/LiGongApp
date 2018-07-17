package com.example.app3.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.tool.AddView;
import com.example.app3.tool.GlideRoundTransform;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;
import yh.app.appstart.lg.R;
import com.yunhuakeji.app.utils.MapTools;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.logTool.Log;
import yh.app.tool.PackAganmanger;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/9/21.
 * <p>
 * 关于我们
 */

public class AboutUsActivity extends BaseRecyclerViewActivity {

    @BindView(R.id.aboutus_title)
    MyTitleView aboutusTitle;
    @BindView(R.id.aboutus_image_logo)
    ImageView aboutusImageLogo;
    @BindView(R.id.aboutus_rec)
    RecyclerView aboutusRec;
    @BindView(R.id.aboutus_version)
    TextView aboutusVersion;
    private QuickAdapter adapter;

    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    /**
     * 设置title
     */
    @Override
    protected void setTitle(Context context) {
        aboutusTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 加载数据
     *
     * @param context
     */
    private Intent intent;

    @Override
    protected void loadRecyclerViewData(final Context context) {

        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(this, "about_us"), LayoutEntity.class);
        adapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_archives;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, final int position) {
                if (data.getType().equals("fill_view")) {
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).addView(AddView.addView(context, data.getLayout(), 15));
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.GONE);
                } else {
                    holder.setTextView(R.id.item_archives_text_name, data.getTxt());
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (!Tool.isFastDoubleClick()) {

                                Intent intent = new Intent(context, WebActivity.class);
                                intent.putExtra("url", url);
                                Log.d("zsp", url);
                                intent.putExtra("title", data.getTxt());
                                context.startActivity(intent);
                            }
                        } catch (Exception e) {

                        }


                    }
                });
            }

        };
        aboutusRec.setLayoutManager(new LinearLayoutManager(context));
        aboutusRec.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void init(final Context context) {
        Glide.with(context).load(R.drawable.xxtb).transform(new GlideRoundTransform(context, 10)).into(aboutusImageLogo);
        PackAganmanger packAganmanger=new PackAganmanger(context);
        aboutusVersion.setText(packAganmanger.getAppName()+"：V"+ packAganmanger.getAppVersionCode());
        Map<String, String> param = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.webticketurl.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String ticket = jsonObject.getString("ticket");
                    url = _链接地址导航.UIA.gongnengjieshao.getUrl() + Constants.number + "&ticket=" + ticket;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
