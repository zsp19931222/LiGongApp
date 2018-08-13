package com.example.app4.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.AddView;
import com.example.app3.tool.GlideCircleTransform;
import com.example.app3.tool.HintTool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.base.BaseFragment;
import com.example.app4.entity.MySelfEntity;
import com.example.app4.presenter.MySelfPresenter;
import com.example.app4.util.ActivityUtil;
import com.example.app4.util.CameraUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.yhkj.cqgyxy.R;
import yh.app.tool.SqliteHelper;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;

/**
 * Created by Administrator on 2018/4/16 0016.
 * <p>
 * 我的Fragment
 */

public class MySelfFragment extends BaseFragment {
    @BindView(R.id.myself_text_name)
    TextView myselfTextName;
    @BindView(R.id.myself_iamge_sex)
    ImageView myselfIamgeSex;
    @BindView(R.id.myself_text_collage)
    TextView myselfTextCollage;
    @BindView(R.id.myself_text_major)
    TextView myselfTextMajor;
    @BindView(R.id.myself_image_userHead)
    ImageView myselfImageUserHead;
    @BindView(R.id.myself_lin_head)
    LinearLayout myselfLinHead;
    @BindView(R.id.myself_rec_list)
    RecyclerView myselfRecList;
    Unbinder unbinder;

    private Context context;
    public CameraUtil cameraUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_myself;
    }


    @Override
    protected void initView() {
        context = getContext();
        cameraUtil = new CameraUtil(context);
        try {
            List<Map<String, String>> userInfoMaps = new SqliteHelper().rawQuery("select * from userinfo4");
            myselfTextName.setText(userInfoMaps.get(0).get("username"));
            if ("1".equals(userInfoMaps.get(0).get("xb"))) {
                Glide.with(context).load(R.drawable.sex_male_2x)
                        .error(R.drawable.error).into(myselfIamgeSex);
            } else {
                Glide.with(context).load(R.drawable.sex_famale_2x)
                        .error(R.drawable.error).into(myselfIamgeSex);
            }
            myselfTextCollage.setText(userInfoMaps.get(0).get("xydm"));
            myselfTextMajor.setText(userInfoMaps.get(0).get("zydm"));
            GlideLoadUtils.getInstance().glideLoadCircle(context, userInfoMaps.get(0).get("txdz"), myselfImageUserHead, R.drawable.q1);
        } catch (Exception ignored) {

        }
        showRecyclerView();
    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.UploadingHeadImageTag:
                Glide.with(activity)
                        .load(result)
                        .error(R.drawable.q1)
                        .placeholder(R.drawable.q1)
                        .transform(new GlideCircleTransform(activity))
                        .skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(myselfImageUserHead);
                break;
            case HintTool.REQUESTFAIL:
                if (ActivityUtil.isForeground(getContext()))
                    ToastUtils.Toast(context, result);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showRecyclerView() {
        MySelfEntity entity;
        try {
            entity = GsonImpl.get().toObject(new SqliteHelper().rawQuery("select * from myself_list_json").get(0).get("json"), MySelfEntity.class);
        } catch (Exception e) {
            entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "meself2"), MySelfEntity.class);
        }
        QuickAdapter adapter = new QuickAdapter<MySelfEntity.AllTagsListBean>(entity.getAllTagsList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_myself;
            }

            @Override
            public void convert(VH holder, final MySelfEntity.AllTagsListBean data, final int position) {
                if (data.getLayout().equals("fill_view")) {
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).setVisibility(View.VISIBLE);
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).addView(AddView.addView(context, data.getView(), 15));
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.GONE);
                } else {
                    holder.setRelativeLayout(R.id.item_archives_lin_fill).setVisibility(View.GONE);
                    holder.setLinearLayout(R.id.item_archives_lin_nofill).setVisibility(View.VISIBLE);
                    holder.setTextView(R.id.myself_txt_title, data.getTitle());
                    holder.setImageView(getActivity(), R.id.myself_img_face, data.getImg());
                    new MySelfPresenter(context).intentWidget(context, holder.setLinearLayout(R.id.item_archives_lin_nofill), data);
                }
            }
        };
        myselfRecList.setLayoutManager(new LinearLayoutManager(context));
        myselfRecList.setAdapter(adapter);
    }

    @OnClick(R.id.myself_lin_head)
    public void onViewClicked() {
        cameraUtil.showUpdateHandPhoto(myselfLinHead);
    }
}
