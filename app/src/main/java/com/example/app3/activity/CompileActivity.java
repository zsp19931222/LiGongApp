package com.example.app3.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app3.adapter.adapter.DescHolder;
import com.example.app3.adapter.adapter.SectionedSpanSizeLookup;
import com.example.app3.adapter.dragrecyclear.adapter.EntityAdapter;
import com.example.app3.adapter.dragrecyclear.adapter.RecyclerAdapter;
import com.example.app3.adapter.dragrecyclear.bean.DragBean;
import com.example.app3.adapter.dragrecyclear.helper.MyItemTouchCallback;
import com.example.app3.adapter.dragrecyclear.helper.OnRecyclerItemClickListener;
import com.example.app3.tool.HintTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.presenter.HomePageFragmentPresenter;
import com.example.app4.tool.UserMessageTool;
import com.example.entity.MoreEntity;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;

import org.greenrobot.eventbus.Subscribe;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.appstart.lg.R;
import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.tool.widget.LoadDiaog;

/**
 * Created by Administrator on 2017/9/7.
 * <p>
 * 编辑界面
 *
 * @author 周劭鹏
 */

public class CompileActivity extends BaseRecyclerViewActivity implements MyItemTouchCallback.OnDragListener {

    @BindView(R.id.complie_rel_cancle)
    RelativeLayout complieRelCancle;
    @BindView(R.id.complie_rel_complete)
    RelativeLayout complieRelComplete;
    @BindView(R.id.complie_rel_title)
    RelativeLayout complieRelTitle;
    @BindView(R.id.comilp_text_app)
    TextView comilpTextApp;
    @BindView(R.id.compile_rec_app)
    RecyclerView compileRecApp;
    @BindView(R.id.compile_lin_appTitle)
    LinearLayout compileLinAppTitle;
    @BindView(R.id.complie_rev)
    RecyclerView complieRev;
    private EntityAdapter mAdapter;
    private RecyclerAdapter appAdapter;
    private ItemTouchHelper itemTouchHelper;
    private MoreEntity entity;
    private List<DragBean> dragList = new ArrayList<>();
    private List<String> functionIDList = new ArrayList<>();


    private String data;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_compile;
    }

    @Override
    protected void setTitle(Context context) {

    }

    @Override
    protected void loadRecyclerViewData(Context context) {
    }

    @Override
    protected void init(Context context) {
        setImmersiveStatusBar(true,getResources().getColor(R.color.color_f6));

        getData(this);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.changeApplicationListTag:
                new HomePageFragmentPresenter(this).getApplicationList();
                break;
            case TagUtil.getApplicationClassifyTag:
                dismissLoad();
                finish();
                break;
            case HintTool.REQUESTFAIL:
                ToastUtils.Toast(this, result);
                dismissLoad();
                break;
        }
    }

    /**
     * 第一个RecyclerView数据加载
     *
     * @param context
     */
    private void loadAppRecyclerView(final Context context) {
        entity = GsonImpl.get().toObject(data, MoreEntity.class);
        List<Map<String, String>> maps = new SqliteHelper().rawQuery("select * from compile_data");
        for (int i = 0; i < maps.size(); i++) {
            dragList.add(new DragBean(
                    maps.get(i).get("url"),
                    maps.get(i).get("name"),
                    Integer.valueOf(maps.get(i).get("section")),
                    Integer.valueOf(maps.get(i).get("position")),
                    maps.get(i).get("functionID")
            ));
        }
        appAdapter = new RecyclerAdapter(dragList);
        compileRecApp.setHasFixedSize(true);
        compileRecApp.setAdapter(appAdapter);
        compileRecApp.setLayoutManager(new GridLayoutManager(context, 4));

        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(appAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(compileRecApp);

        appAdapter.setItemClickListener(new RecyclerAdapter.MyItemClickListener() {//删除应用
            @Override
            public void onItemClick(View view, int position) {
                try {
                    entity.getAllTagsList().get(dragList.get(position).getSection()).getTagInfoList().get(dragList.get(position).getPosition()).setIsSelected(true);
                    appAdapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();
                    dragList.remove(position);
                } catch (Exception e) {

                }

            }
        });

        appAdapter.setChangePositionListener(new RecyclerAdapter.OnChangePositionListener() {
            @Override
            public void onChange(List<DragBean> dragBeen) {
                dragList = dragBeen;
            }
        });

        compileRecApp.addOnItemTouchListener(new OnRecyclerItemClickListener(compileRecApp) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                itemTouchHelper.startDrag(vh);
            }

            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                DragBean item = dragList.get(vh.getLayoutPosition());
                Toast.makeText(context, " " + item.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 第二个RecyclerView数据加载
     *
     * @param context
     */
    private void loadRecyclerView(final Context context) {
        mAdapter = new EntityAdapter(context);

        entity = GsonImpl.get().toObject(data, MoreEntity.class);
        mAdapter.setData(entity);
        for (int i = 0; i < dragList.size(); i++) {
            try {
                entity.getAllTagsList().get(dragList.get(i).getSection()).getTagInfoList().get(dragList.get(i).getPosition()).setIsSelected(false);
                mAdapter.notifyDataSetChanged();
            } catch (Exception e) {

            }

        }
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        //设置header
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
        complieRev.setLayoutManager(manager);
        complieRev.setAdapter(mAdapter);

        mAdapter.setOnItemClickLiniser(new EntityAdapter.onItemClickLiniser() {
            @Override
            public void onClick(final DescHolder holder, final int section, final int position) {//添加应用
                try {
                    holder.addRel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dragList.size() <= 10) {
                                if (entity.getAllTagsList().get(section).getTagInfoList().get(position).isIsSelected()) {
                                    entity.getAllTagsList().get(section).getTagInfoList().get(position).setIsSelected(false);
                                    dragList.add(new DragBean(entity.getAllTagsList().get(section).getTagInfoList().get(position).getLatlon(), entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagName(), section, position, entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagId()));
                                    appAdapter.notifyDataSetChanged();
                                    mAdapter.notifyDataSetChanged();
                                }
                            } else {
                                ToastUtils.Toast(context, "首页应用最多显示11个哟~");
                            }
                        }
                    });
                } catch (Exception e) {
                }

            }
        });

    }

    @OnClick({R.id.complie_rel_cancle, R.id.complie_rel_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.complie_rel_cancle:
                finish();
                break;
            case R.id.complie_rel_complete:
                try {
                    showLoad("正在上传，清稍后...");
                    Gson gson = new Gson();
                    if (functionIDList.size() != 0) {
                        functionIDList.removeAll(functionIDList);
                    }
                    for (int i = 0; i < dragList.size(); i++) {
                        functionIDList.add(dragList.get(i).getFunctionID());
                    }
                    final String functionJson = gson.toJson(functionIDList);//上传服务器的数据,排序后的
                    Log.d("zsp", functionJson.length() + "    ///" + functionIDList.size());

                    changeApplicationList(URLEncoder.encode(functionJson, "utf-8"));
                } catch (Exception e) {

                }

                break;
        }
    }

    @Override
    public void onFinishDrag() {
    }

    private void getData(Activity activity) {
        try {
            data = new SqliteHelper().rawQuery("select * from applicationclassify_json").get(0).get("json");
            if (data != null) {
                loadAppRecyclerView(activity);
                loadRecyclerView(activity);
            }
        }catch (Exception ignored){

        }

    }

    private void changeApplicationList(String functionlist) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", UserMessageTool.getUserId());
        map.put("password", UserMessageTool.getPassWord());
        map.put("functionlist", functionlist);
        OkHttpUtil.OkHttpPost(GetAppUrl.Show.changeApplicationList.getUrl(), map, TagUtil.changeApplicationListTag,this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
