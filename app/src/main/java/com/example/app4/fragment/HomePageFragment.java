package com.example.app4.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.app3.activity.DetailActivity;
import com.example.app3.activity.PushBrowserActivity;
import com.example.app3.base_recyclear_adapter.NormalAdapterWrapper;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app4.base.BaseFragment;
import com.example.app4.bean.HomePageRecBean;
import com.example.app4.entity.ApplicationEntity;
import com.example.app4.entity.HomePageWidgetEntity;
import com.example.app4.onclick.CheckMore;
import com.example.app4.presenter.HomePageFragmentPresenter;
import com.example.app4.util.NoDataViewUtil;
import com.example.app4.view.FunctionAppItemDecorationHorizontal;
import com.example.app4.view.FunctionAppItemDecorationVertical;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.app.model.DAModel;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DensityUtil;
import yh.app.utils.GsonImpl;
import 云华.智慧校园.工具.CodeManage;

/**
 * Created by Administrator on 2018/4/16 0016.
 * <p>
 * 首页fragment
 */

public class HomePageFragment extends BaseFragment {
    @BindView(R.id.refresh_head_text)
    TextView refreshHeadText;
    @BindView(R.id.refresh_head_iamge)
    ImageView refreshHeadIamge;
    @BindView(R.id.home_apply_rec)
    RecyclerView homeApplyRec;
    @BindView(R.id.content_view)
    LinearLayout contentView;
    @BindView(R.id.refresh_view)
    SmartRefreshLayout refreshView;
    @BindView(R.id.home_apply_txt_title)
    TextView homeApplyTxtTitle;
    Unbinder unbinder;


    private ConvenientBanner<DAModel.ContentBean> convenientBanner;//banner控件
    private RecyclerView functionRecyclerView;//应用列表RecyclerView


    private List<HomePageWidgetEntity.ContentBean> content;

    private NormalAdapterWrapper normalAdapterWrapper;
    private HomePageFragmentPresenter presenter;

    private List<HomePageRecBean> pageRecBeans = new ArrayList<>();

    private List<DAModel.ContentBean> bannerList = new ArrayList<>();

    private boolean isFirst = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void initView() {
        presenter = new HomePageFragmentPresenter(getContext());
        init(getContext());
        showAppList(getContext());
        homeApplyTxtTitle.setText(Constants.xxmc);
        presenter.showWidget();
        presenter.getApplicationList();
        presenter.getBanner();
        refreshView.setOnRefreshListener(new DropRefreshListener());
        showData();
    }


    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.ShowWidgetTag:
                presenter.saveWidget(result);
                showData();
                break;
            case TagUtil.GetBannerTag:
                try {
                    DAModel damoel = ExampleApplication.getInstance().getGson().fromJson(result, DAModel.class);
                    bannerList.clear();
                    bannerList.addAll(damoel.getContent());
                    presenter.setBanner(convenientBanner, bannerList);
                } catch (Exception ignored) {

                }
                break;
            case TagUtil.HomePageRefreshTag:
                showAppList(getContext());
                break;
            case TagUtil.getApplicationListTag:
                presenter.saveApplication(result);
                presenter.getApplicationClassify();
                showAppList(getContext());
                break;
            case TagUtil.getApplicationClassifyTag:
                presenter.saveApplicationClassify(result);
                if (myRefreshLayout != null) {
                    myRefreshLayout.finishRefresh();
                }
                break;
            case "获取网页成功":
                dismissLoad();
                break;
            case HintTool.Receive_Push_Message:
                showData();
                break;
            case HintTool.REQUESTFAIL:
                myRefreshLayout.finishRefresh();
                break;
            default:
                break;
        }
        if (bannerList.size() == 0) {
            presenter.setDefaultBanner(convenientBanner, bannerList);
        }
        new NoDataViewUtil<>(pageRecBeans, headView, getContext()).showNoDataView(R.drawable.wk, "在这里，你将看到与你有关的服务");
        dismissLoad();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        if (rootView != null) {
            unbinder = ButterKnife.bind(this, rootView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("InflateParams")
    private void init(final Context context) {

        QuickAdapter widgetAdapter = new QuickAdapter<HomePageRecBean>(pageRecBeans) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.home_apply_item_message_vector;
            }

            @Override
            public void convert(VH holder, final HomePageRecBean data, final int position) {
                holder.setLinearLayout(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.GONE);
                holder.setLinearLayout(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.GONE);
                holder.setLinearLayout(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.GONE);
                holder.setLinearLayout(R.id.view_home_apply_message_item_linear).setVisibility(View.GONE);
                switch (data.getM_type()) {
                    case HomePageFragmentPresenter.MESSAGE_TYPE_LARGE_PIC:
                        holder.setLinearLayout(R.id.view_home_apply_message_item_large_pic1).setVisibility(View.VISIBLE);

                        holder.setTextView(R.id.large_txt_home_item_titleview, data.getM_group());
                        holder.setTextView(R.id.large_txt_home_item_from, data.getM_from());
                        holder.setTextView(R.id.large_txt_home_item_date, TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(data.getM_time(), "yyyy-MM-dd HH:mm:ss")), "HH:mm"));

                        holder.setTextView(R.id.large_pic_txt_title, data.getM_title());
                        holder.setTextView(R.id.large_pic_txt_message, data.getM_message());

                        holder.setImageView((Activity) context, R.id.large_pic_image, data.getM_image(), R.drawable.frist_pushdefaule, 0);
                        if ("false".equals(data.isM_read())) {
                            holder.setImageView(context, R.id.large_gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.VISIBLE);
                        } else {
                            holder.setImageView(context, R.id.large_gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.GONE);
                        }
                        holder.setTextView(R.id.large_pic_text_more, "查看更多").setOnClickListener(new CheckMore(data, context, presenter));
                        holder.setLinearLayout(R.id.large_pic_lin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Tool.isFastDoubleClick()) {
                                    Intent intent = null;
                                    switch (data.getM_code()) {
                                        case CodeManage.TEXT_PUSH:
                                            intent = new Intent(context, DetailActivity.class);
                                            intent.putExtra("id", data.getM_id());
                                            break;
                                        case CodeManage.URL_PUSH:
                                            intent = new Intent(context, PushBrowserActivity.class);
                                            try {
                                                intent.putExtra("url", new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", data.getM_function_id()).get(0).get("n_url"));
                                            } catch (Exception e) {
                                                intent.putExtra("url", "");
                                            }
                                            intent.putExtra("title", "消息");
                                            break;
                                    }
                                    if (intent != null) {
                                        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", data.getM_id());
                                        startActivity(intent);
                                        presenter.checkID(data.getM_id());
                                    }
                                }
                            }
                        });
                        break;
                    case HomePageFragmentPresenter.MESSAGE_TYPE_SMALL_PIC:
                        holder.setLinearLayout(R.id.view_home_apply_message_item_small_pic1).setVisibility(View.VISIBLE);
                        holder.setTextView(R.id.small_txt_home_item_titleview, data.getM_group());
                        holder.setTextView(R.id.small_txt_home_item_from, data.getM_from());
                        holder.setTextView(R.id.small_txt_home_item_date, TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(data.getM_time(), "yyyy-MM-dd HH:mm:ss")), "HH:mm"));
                        holder.setTextView(R.id.small_pic_txt_title, data.getM_title());
                        holder.setTextView(R.id.small_pic_txt_message, data.getM_message());
                        holder.setImageView((Activity) context, R.id.small_pic_image, data.getM_image(), R.drawable.frist_pushdefaule, 0);
                        if ("false".equals(data.isM_read())) {
                            holder.setImageView(context, R.id.small_gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.VISIBLE);
                        } else {
                            holder.setImageView(context, R.id.small_gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.GONE);
                        }
                        holder.setTextView(R.id.small_pic_text_more, "查看更多").setOnClickListener(new CheckMore(data, context, presenter));

                        holder.setLinearLayout(R.id.small_pic_lin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Tool.isFastDoubleClick()) {
                                    Intent intent = null;
                                    switch (data.getM_code()) {
                                        case CodeManage.TEXT_PUSH:
                                            intent = new Intent(context, DetailActivity.class);
                                            intent.putExtra("id", data.getM_id());
                                            break;
                                        case CodeManage.URL_PUSH:
                                            intent = new Intent(context, PushBrowserActivity.class);
                                            try {
                                                intent.putExtra("url", new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", data.getM_function_id()).get(0).get("n_url"));
                                            } catch (Exception e) {
                                                intent.putExtra("url", "");
                                            }
                                            intent.putExtra("title", "消息");
                                            break;
                                    }
                                    if (intent != null) {
                                        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", data.getM_id());
                                        startActivity(intent);
                                        presenter.checkID(data.getM_id());
                                    }
                                }
                            }
                        });
                        break;
                    case HomePageFragmentPresenter.MESSAGE_TYPE_NO_PIC:
                        holder.setLinearLayout(R.id.view_home_apply_message_item_no_pic1).setVisibility(View.VISIBLE);
                        holder.setTextView(R.id.txt_home_item_titleview, data.getM_group());
                        if ("false".equals(data.isM_read())) {
                            holder.setImageView(context, R.id.gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.VISIBLE);
                        } else {
                            holder.setImageView(context, R.id.gridview_home_item_img_xyd, R.drawable.circle_red).setVisibility(View.GONE);
                        }
                        holder.setTextView(R.id.txt_home_item_from, data.getM_from());
                        holder.setTextView(R.id.txt_home_item_date, TimeTool.TimeStamp2date(Long.valueOf(TimeTool.date2TimeStamp(data.getM_time(), "yyyy-MM-dd HH:mm:ss")), "HH:mm"));
                        holder.setTextView(R.id.no_pic_txt_title, data.getM_title());
                        holder.setTextView(R.id.no_pic_txt_message, data.getM_message());
                        holder.setTextView(R.id.no_pic_text_more, "查看更多").setOnClickListener(new CheckMore(data, context, presenter));

                        holder.setLinearLayout(R.id.no_pic_lin).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!Tool.isFastDoubleClick()) {
                                    Intent intent = null;
                                    switch (data.getM_code()) {
                                        case CodeManage.TEXT_PUSH:
                                            intent = new Intent(context, DetailActivity.class);
                                            intent.putExtra("id", data.getM_id());
                                            break;
                                        case CodeManage.URL_PUSH:
                                            intent = new Intent(context, PushBrowserActivity.class);
                                            try {
                                                intent.putExtra("url", new SqliteHelper().rawQuery("select * from client_notice where function_id=? order by n_send_time desc", data.getM_function_id()).get(0).get("n_url"));
                                            } catch (Exception e) {
                                                intent.putExtra("url", "");
                                            }
                                            intent.putExtra("title", "消息");
                                            break;
                                    }
                                    if (intent != null) {
                                        new SqliteHelper().rawQuery("update client_notice set read=? where n_id=?", "true", data.getM_id());
                                        new SqliteHelper().rawQuery("update client_notice_messagelist set read=? where n_id=?", "true", data.getM_id());
                                        startActivity(intent);
                                        presenter.checkID(data.getM_id());
                                    }
                                }
                            }
                        });
                        break;
                    case HomePageFragmentPresenter.MESSAGE_TYPE_Widget:
                        holder.setLinearLayout(R.id.view_home_apply_message_item_linear).setVisibility(View.VISIBLE);
                        LinearLayout linearLayout = holder.setLinearLayout(R.id.item_linearlayout);
                        linearLayout.removeAllViews();
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                        params.setMargins(DensityUtil.dip2px(context, 10), 0, 0, 0);
                        for (int i = 0; i < content.get(position).getLx().size(); i++) {
                            final ImageView imageView = new ImageView(context);
                            imageView.setLayoutParams(params);
                            params.weight = 1;
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            linearLayout.addView(imageView);
                            GlideLoadUtils.getInstance().glideLoad(context, content.get(position).getLx().get(i).getImg(), imageView, R.drawable.pushlist_default);
                            final int finalI = i;
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (content.get(position).getLx().get(finalI).getType() == 2) {
                                        showLoad("玩命加载中");
                                    }
                                    presenter.intentWidget(context, content.get(position).getLx().get(finalI));
                                }
                            });
                        }
                        break;
                    default:
                        break;
                }
            }
        };

        headView = LayoutInflater.from(context).inflate(R.layout.head_fragment_homepage, null, false);
        footView = LayoutInflater.from(context).inflate(R.layout.foot_fragment_hompage, null, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        footView.setLayoutParams(params);

        normalAdapterWrapper = new NormalAdapterWrapper(widgetAdapter);
        normalAdapterWrapper.addHeaderView(headView);
        normalAdapterWrapper.addFooterView(footView);
        homeApplyRec.setLayoutManager(new LinearLayoutManager(context));
        homeApplyRec.setAdapter(normalAdapterWrapper);
        findHeadViewID(headView);
    }

    View footView;
    View headView;

    private void showData() {
        pageRecBeans.clear();
        try {
            List<Map<String, String>> maps = new SqliteHelper().rawQuery("select * from homepage_widget_json");
            HomePageWidgetEntity widgetEntity = GsonImpl.get().toObject(maps.get(0).get("json"), HomePageWidgetEntity.class);
            content = widgetEntity.getContent();
            for (int i = 0; i < content.size(); i++) {
                pageRecBeans.add(0, new HomePageRecBean(HomePageFragmentPresenter.MESSAGE_TYPE_Widget, "", "", "", "", "", "", "", "", "", "", "", ""));
            }
        } catch (Exception e) {
            Log.d(TAG, "showData: " + e);
        }
        presenter.getPushData();
        pageRecBeans = presenter.getShowData(pageRecBeans);
        normalAdapterWrapper.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isFirst) {
            showData();
            showAppList(getContext());
        }
        isFirst = false;
    }

    /**
     * 应用消息展示
     */
    private List<ApplicationEntity.ContentBean> applistContentBeans;

    private QuickAdapter appListAdapter;

    private void showAppList(final Context context) {
        try {
            ApplicationEntity applicationEntity = GsonImpl.get().toObject(new SqliteHelper().rawQuery("select * from homepage_application_json").get(0).get("json"), ApplicationEntity.class);

            if (applistContentBeans == null) {
                applistContentBeans = new ArrayList<>();
                applistContentBeans = applicationEntity.getContent();
                appListAdapter = new QuickAdapter<ApplicationEntity.ContentBean>(applistContentBeans) {
                    @Override
                    public int getLayoutId(int viewType) {
                        return R.layout.view_textview_home_apply_item;
                    }

                    @Override
                    public void convert(VH holder, final ApplicationEntity.ContentBean data, final int position) {
                        holder.setImageView((Activity) context, R.id.home_apply_item_img_image, data.getImg(), R.drawable.ico_load_little, 0);
                        holder.setTextView(R.id.home_apply_item_txt_function_name, data.getTitle());
                        holder.setLinearLayout(R.id.linearLayout1).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data.getPower() == 1 && data.getType() == 2) {
                                    showLoad("玩命加载中");
                                }
                                presenter.intentApplication(context, data);
                            }
                        });
                    }
                };
                functionRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));

                functionRecyclerView.addItemDecoration(new FunctionAppItemDecorationHorizontal(context));
                functionRecyclerView.addItemDecoration(new FunctionAppItemDecorationVertical(context));
                functionRecyclerView.setAdapter(appListAdapter);
            } else {
                applistContentBeans.clear();
                applistContentBeans.addAll(applicationEntity.getContent());
                appListAdapter.notifyDataSetChanged();
            }

        } catch (Exception ignored) {
            Log.d(TAG, "showAppList: " + ignored.toString());
        }
    }

    /**
     * 刷新
     */
    private RefreshLayout myRefreshLayout;

    public class DropRefreshListener implements OnRefreshListener {
        @Override
        public void onRefresh(final RefreshLayout refreshlayout) {
            refreshHeadText.setText(presenter.getRandomString());
            AnimationDrawable refreshingAnimation = (AnimationDrawable) refreshHeadIamge.getBackground();
            refreshingAnimation.start();
            presenter.getApplicationClassify();
            presenter.showWidget();
            presenter.getBanner();
            presenter.getApplicationList();
            myRefreshLayout = refreshlayout;
        }
    }

    @OnClick(R.id.home_apply_img_ewm)
    public void onViewClicked() {
        presenter.QRCode();
//        new ShareUtil(getActivity()).share();
//        Intent intent = new Intent(getActivity(), BrowserActivity.class);
//        getActivity().startActivity(intent);
//        new LocationUtil(getContext()).startLocate();
    }

    private void findHeadViewID(View view) {
        convenientBanner = view.findViewById(R.id.convenientBanner);
        functionRecyclerView = view.findViewById(R.id.functionRecyclerView);
        functionRecyclerView.setFocusableInTouchMode(false);
        functionRecyclerView.requestFocus();
    }

    private static final String TAG = "HomePageFragment";
}
