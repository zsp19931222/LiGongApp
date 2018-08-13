//package com.example.app3.activity;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.util.Util;
//import com.example.app3.adapter.adapter.DescHolder;
//import com.example.app3.adapter.adapter.EntityAdapter;
//import com.example.app3.adapter.adapter.SectionedSpanSizeLookup;
//import com.example.app3.adapter.dragrecyclear.bean.DragBean;
//import com.example.app3.eventbus.MyEventBus;
//import com.example.app3.tool.Tool;
//import com.example.app3.utils.GlideLoadUtils;
//import com.example.entity.MoreEntity;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import com.yhkj.cqgyxy.R;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.tool.SqliteHelper;
//import yh.app.utils.DensityUtil;
//import yh.app.utils.GsonImpl;
//import yh.tool.widget.LoadDiaog;
//import 云华.智慧校园.工具._功能跳转;
//
///**
// * Created by Administrator on 2017/9/18.
// */
//
//public class MoreActivity extends ActivityPortrait {
//    @BindView(R.id.more_relative_return)
//    RelativeLayout moreRelativeReturn;
//    @BindView(R.id.more_text_search)
//    TextView moreTextSearch;
//    @BindView(R.id.more_lin_title)
//    LinearLayout moreLinTitle;
//    @BindView(R.id.more_image_cutLine1)
//    ImageView moreImageCutLine1;
//    @BindView(R.id.more_lin_appIcon)
//    LinearLayout moreLinAppIcon;
//    @BindView(R.id.more_text_compile)
//    TextView moreTextCompile;
//    @BindView(R.id.more_lin_list)
//    LinearLayout moreLinList;
//    @BindView(R.id.more_image_cutLine2)
//    ImageView moreImageCutLine2;
//    @BindView(R.id.more_rec)
//    RecyclerView moreRec;
//    @BindView(R.id.more_rel_parent)
//    RelativeLayout moreRelParent;
//
//    private int moreLinAppIconWidth;
//
//    private EntityAdapter mAdapter;
//    private List<DragBean> dragList = new ArrayList<>();
//    private SQLiteDatabase database = new SqliteHelper().getRead();
//
//    private boolean isFirst = true;
//
//    private LoadDiaog loadDiaog;
//    private List<Map<String, String>> applyList, appList;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_more);
//        ButterKnife.bind(this);
//        EventBus.getDefault().register(this);//注册EventBus
//        showDiaog(this);
//
//    }
//
//    private void initData() {
////        applyList.clear();
//        //FixMe 这里有时候从数据库查出来可能会为0，就是没有数据
//        applyList = new ArrayList<>();
//        applyList.addAll(new SqliteHelper().rawQuery("select function_icon as FUNCTION_FACE,function_name as FUNCTION_NAME,function_type as function_type,function_cls as cls, function_name as name from new_apply_function where function_display_homepage=? order by cast(function_display_homepage_px as number) asc", FUNCTION_DISPLAY_HOMEPAGE_YES));
//    }
//
//    private void showDiaog(Context context) {
//        loadDiaog = new LoadDiaog(context);
//        loadDiaog.show();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        handler.sendEmptyMessageDelayed(0, 500);
//        if (dragList != null) {
//            dragList.removeAll(dragList);
//        } else {
//            dragList = new ArrayList<>();
//        }
//
//        if (isFirst) {
//            initData();
//        } else {
//            SharedPreferences preferences = getSharedPreferences("myData", MODE_PRIVATE);
//            String json = preferences.getString("myData", null);
//            if (json != null) {
//                Gson gson = new Gson();
//                Type type = new TypeToken<List<DragBean>>() {
//                }.getType();
//                dragList = gson.fromJson(json, type);
//            }
//        }
//        loadRecyclerView(MoreActivity.this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);//反注册EventBus
//    }
//
//    @SuppressLint("HandlerLeak")
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 0:
//                    moreLinAppIconWidth = moreLinAppIcon.getWidth();
//                    loadLocationData(MoreActivity.this);
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 加载recyclerview数据
//     *
//     * @param context
//     */
//    public static final String FUNCTION_DISPLAY_HOMEPAGE_YES = "1";
//
//    private void loadRecyclerView(final Context context) {
//        if (mAdapter == null) {
//            mAdapter = new EntityAdapter(context);
//            final MoreEntity entity = GsonImpl.get().toObject(query(database), MoreEntity.class);
//            mAdapter.setData(entity);
//            GridLayoutManager manager = new GridLayoutManager(context, 4);
//            //设置header
//            manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));
//            moreRec.setLayoutManager(manager);
//            moreRec.setAdapter(mAdapter);
//            mAdapter.setOnItemClickLiniser(new EntityAdapter.onItemClickLiniser() {
//                @Override
//                public void onClick(DescHolder holder, final int section, final int position) {
//                    holder.layout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            try {
//                                if (!Tool.isFastDoubleClick()) {
//                                    Map<String, String> map = new HashMap<>();
//                                    map.put("FUNCTION_FACE", entity.getAllTagsList().get(section).getTagInfoList().get(position).getLatlon());
//                                    map.put("FUNCTION_NAME", entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagName());
//                                    map.put("function_type", entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagType());
//                                    map.put("cls", entity.getAllTagsList().get(section).getTagInfoList().get(position).getLat());
//                                    map.put("name", entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagName());
//                                    map.put("FunctionID", entity.getAllTagsList().get(section).getTagInfoList().get(position).getTagId());
//                                    new _功能跳转().Jump(context, map);
//                                }
//                            } catch (Exception e) {
//
//                            }
//                        }
//                    });
//                }
//
//            });
//        }
//    }
//
//    /**
//     * 加载本地数据
//     *
//     * @param context
//     */
//
//    private void loadLocationData(Context context) {
//        moreLinAppIcon.removeAllViews();
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(DensityUtil.dip2px(context, 6), 0, DensityUtil.dip2px(context, 6), 0);
//        int itemW = moreLinAppIconWidth / 7;
//        params.width = itemW - DensityUtil.dip2px(context, 15);
//        params.height = itemW - DensityUtil.dip2px(context, 15);
//        if (dragList != null) {
//            if (dragList.size() <= 6) {
//                for (int i = 0; i < dragList.size(); i++) {//动态添加图片
//                    ImageView imageView = new ImageView(context);
//                    imageView.setLayoutParams(params);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 20));
//                    layoutParams.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
//                    imageView.setLayoutParams(layoutParams);
//                    moreLinAppIcon.addView(imageView);
//                    GlideLoadUtils.getInstance().glideLoad(context, dragList.get(i).getUrl(), imageView, R.drawable.ico_load_little);
//                }
//            } else {
//                for (int i = 0; i < 6; i++) {
//                    ImageView imageView = new ImageView(context);
//                    imageView.setLayoutParams(params);
//                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(context, 20), DensityUtil.dip2px(context, 20));
//                    layoutParams.setMargins(DensityUtil.dip2px(context, 5), 0, DensityUtil.dip2px(context, 5), 0);
//                    imageView.setLayoutParams(layoutParams);
//                    moreLinAppIcon.addView(imageView);
//                    GlideLoadUtils.getInstance().glideLoad(context, dragList.get(i).getUrl(), imageView, R.drawable.ico_load_little);
//                }
//                LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                ImageView imageView = new ImageView(context);
//                params1.width = DensityUtil.dip2px(context, 15);
//                params1.height = DensityUtil.dip2px(context, 4);
//                imageView.setLayoutParams(params1);
//                moreLinAppIcon.addView(imageView);
//                GlideLoadUtils.getInstance().glideLoadLocal(context, R.drawable.more_more, imageView);
//            }
//        }
//        loadDiaog.dismiss();
//    }
//
//    @OnClick({R.id.more_relative_return, R.id.more_text_compile})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.more_relative_return:
//                finish();
//                break;
//            case R.id.more_text_compile:
//                Intent intent = new Intent(MoreActivity.this, CompileActivity.class);
//                intent.putExtra("locaData", allJsonObject.toString());
//                startActivity(intent);
//                break;
//        }
//    }
//
//    private boolean have = false;//是否有当前function_group_id
//    private List<String> groupIDList = new ArrayList<>();//存function_group_id来当数据判断标示
//    private List<String> groupnameList = new ArrayList<>();//存function_group_id来当数据判断标示
//
//    JSONObject allJsonObject = new JSONObject();
//
//    public String query(SQLiteDatabase db) {
//        Cursor c = queryTheCursor(db);
//        JSONArray allTagsList = new JSONArray();
//        Cursor cursor = null;
//        while (c.moveToNext()) {
//            if (groupIDList.size() == 0) {
//                groupIDList.add(c.getString(c.getColumnIndex("function_group_id")));
//                groupnameList.add(c.getString(c.getColumnIndex("function_group_name")));
//            } else {
//                for (int i = 0; i < groupIDList.size(); i++) {
//                    if (groupIDList.get(i).equals(c.getString(c.getColumnIndex("function_group_id")))) {
//                        have = true;
//                        break;
//                    } else {
//                        have = false;
//                    }
//                }
//                if (!have) {
//                    groupnameList.add(c.getString(c.getColumnIndex("function_group_name")));
//                    groupIDList.add(c.getString(c.getColumnIndex("function_group_id")));
//                }
//            }
//        }
//        try {
//            JSONObject tagsName = null;
//            JSONArray jsonArray = null;
//            for (int i = 0; i < groupIDList.size(); i++) {
//                if (appList == null) {
//                    appList = new ArrayList<>();
//                } else {
//                    appList.clear();
//                }
//                appList.addAll(new SqliteHelper().rawQuery("select * from new_apply_function where function_group_id=?", groupIDList.get(i)));
//                tagsName = new JSONObject();
//                tagsName.put("tagsName", groupnameList.get(i));
//                tagsName.put("tagsId", groupIDList.get(i));
//                jsonArray = new JSONArray();
//                for (int j = 0; j < appList.size(); j++) {
//                    if (!appList.get(j).get("function_type").equals("10001")) {
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.put("tagName", appList.get(j).get("function_name"));
//                        jsonObject.put("latlon", appList.get(j).get("function_icon"));
//                        jsonObject.put("isSelected", true);
//                        jsonObject.put("tagId", appList.get(j).get("function_id"));
//                        jsonObject.put("lat", appList.get(j).get("function_cls"));
//                        jsonObject.put("tagType", appList.get(j).get("function_type"));
//                        jsonArray.put(jsonObject);
//                    }
//                }
//                cursor = conditionCursor(db, groupIDList.get(i));
//                if (jsonArray.length() != 0) {
//                    tagsName.put("tagInfoList", jsonArray);
//                    allTagsList.put(tagsName);
//                }
//            }
//            if (isFirst) {
//                for (int j = 0; j < applyList.size(); j++) {
//                    for (int i = 0; i < groupIDList.size(); i++) {
//                        position = -1;
//                        cursor = conditionCursor(db, groupIDList.get(i));
//                        while (cursor.moveToNext()) {
//                            if (!cursor.getString(cursor.getColumnIndex("function_type")).equals("10001")) {
//                                position++;
//                                if (cursor.getString(cursor.getColumnIndex("function_name")).equals(applyList.get(j).get("FUNCTION_NAME")) && cursor.getString(cursor.getColumnIndex("function_icon")).equals(applyList.get(j).get("FUNCTION_FACE"))) {
//                                    dragList.add(new DragBean(cursor.getString(cursor.getColumnIndex("function_icon")), cursor.getString(cursor.getColumnIndex("function_name")), i, position, cursor.getString(cursor.getColumnIndex("function_id"))));
//                                }
//                            }
//                        }
//                    }
//                }
//                SharedPreferences.Editor editor = getSharedPreferences("myData", MODE_PRIVATE).edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(dragList);
//                editor.putString("myData", json);
//                editor.commit();
//                loadLocationData(MoreActivity.this);
//                isFirst = false;
//            }
//            allJsonObject.put("allTagsList", allTagsList);
//        } catch (JSONException e) {
//            throw new RuntimeException(e);
//        }
//        cursor.close();
//        c.close();
//        return allJsonObject.toString();
//    }
//
//    private int position = -1;
//
//    /**
//     * 查询本地数据
//     *
//     * @param db
//     */
//    public Cursor queryTheCursor(SQLiteDatabase db) {
//        Cursor c = db.rawQuery("SELECT * FROM new_apply_function", null);
//        return c;
//    }
//
//    /**
//     * 查询本地数据根据条件查询
//     *
//     * @param db
//     * @param function_group_id
//     */
//    private Cursor conditionCursor(SQLiteDatabase db, String function_group_id) {
//        Cursor c = db.rawQuery("SELECT * FROM new_apply_function where function_group_id=" + function_group_id, null);
//        return c;
//    }
//
//    @Subscribe
//    public void onEventMainThread(MyEventBus event) {
//    }
//
//    @OnClick(R.id.more_text_search)
//    public void onViewClicked() {
//        Intent intent = new Intent(this, SearchAppActivity.class);
//        startActivity(intent);
//    }
//}
