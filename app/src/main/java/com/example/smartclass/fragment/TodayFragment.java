package com.example.smartclass.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.smartclass.activity.CourseDetailStudentActivity;
import com.example.smartclass.activity.CourseDetailTeacherActivity;
import com.example.smartclass.base.BaseFragment;
import com.example.smartclass.bean.WeekBean;
import com.example.smartclass.entity.TodayCourseDateRequestEntity;
import com.example.smartclass.entity.TodayCourseIDEntity;
import com.example.smartclass.entity.TodayCourseRequestEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.popupwindow.WeekPop;
import com.example.smartclass.util.AuthenticationUtil;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import com.example.smartclass.view.MyItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yhkj.cqgyxy.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.JsonReaderHelper;
import 云华.智慧校园.工具.RHelper;
import 云华.智慧校园.工具.RandomTools;

/**
 * Created by Administrator on 2018/1/4 0004.
 * 今日课程Fragment
 */

public class TodayFragment extends BaseFragment {
    @BindView(R.id.rel_sc_today_time)
    RelativeLayout relScTodayTime;
    @BindView(R.id.rec_sc_today)
    RecyclerView recScToday;
    @BindView(R.id.refresh_sc_today)
    SmartRefreshLayout refreshScToday;
    Unbinder unbinder;
    @BindView(R.id.text_sc_today_class_hour)
    TextView textScTodayClassHour;
    @BindView(R.id.text_sc_today_week)
    TextView textScTodayWeek;
    @BindView(R.id.rel_sc_today_week)
    RelativeLayout relScTodayWeek;
    @BindView(R.id.rec_sc_today_weeks)
    RecyclerView recScTodayWeeks;
    @BindView(R.id.text_sc_no_data)
    TextView textScNoData;
    @BindView(R.id.rel_sc_today_parent)
    RelativeLayout relScTodayParent;
    @BindView(R.id.rel_sc_today_shade)
    RelativeLayout relScTodayShade;
    @BindView(R.id.refresh_sc_today_text)
    TextView refreshScTodayText;
    @BindView(R.id.refresh_sc_today_text_iamge)
    ImageView refreshScTodayTextIamge;
    private QuickAdapter courseAdapter;
    private List<TodayCourseRequestEntity.ContentBean.KclbBean> courseList;
    private List<TodayCourseRequestEntity.ContentBean.KclbBean> transmitList;//传递给下一页面的参数
    private QuickAdapter weekQuickAdapter;

    private int now_week = 1;//当前星期几
    private int now_weeks = 1;//当前周数
    private int now_choise_weeks = 1;//当前选中周数
    private int total_weeks = 0;//总共多少周

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sc_today;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);

        courseList = new ArrayList<>();
        transmitList = new ArrayList<>();

        refreshScToday.setEnableRefresh(true);
        refreshScToday.setEnableLoadmore(false);
        refreshScToday.setOnRefreshListener(new DropRefreshListener());
        getDateRequestData();
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
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    @OnClick(R.id.rel_sc_today_week)
    public void onViewClicked() {
        if (total_weeks != 0) {
//            relScTodayShade.setVisibility(View.VISIBLE);
//            startAlphaAnimation(relScTodayShade);
            WeekPop weekPop = new WeekPop(getActivity(), total_weeks, now_weeks, now_choise_weeks);
            weekPop.showPopupWindow(relScTodayParent);//点击出现周数选择框
//            weekPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                @Override
//                public void onDismiss() {
//                    //清除动画
//                    relScTodayShade.clearAnimation();
//                    relScTodayShade.setVisibility(View.GONE);
//                }
//            });
        }
    }


    public void startAlphaAnimation(View imageView) {
        /**
         * @param fromAlpha 开始的透明度，取值是0.0f~1.0f，0.0f表示完全透明， 1.0f表示和原来一样
         * @param toAlpha 结束的透明度，同上
         */
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 0.4f);
        //设置动画持续时长
        alphaAnimation.setDuration(250);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(true);
        //设置动画结束之后的状态是否是动画开始时的状态，true，表示是保持动画开始时的状态
        alphaAnimation.setFillBefore(true);
        //开始动画
        imageView.startAnimation(alphaAnimation);

    }

    /**
     * 加载课程RecyclerView数据
     *
     * @param result 返回数据
     */
    @SuppressLint("SetTextI18n")
    private void setCourseRecyclerView(final Context context, final String result) {
        TodayCourseRequestEntity courseRequestEntity = GsonImpl.get().toObject(result, TodayCourseRequestEntity.class);
        int today_class_hour = courseRequestEntity.getContent().getKs();
        textScTodayClassHour.setText("当日总课时：" + today_class_hour);
        courseList.clear();
        courseList.addAll(courseRequestEntity.getContent().getKclb());
        if (courseAdapter == null) {
            courseAdapter = new QuickAdapter<TodayCourseRequestEntity.ContentBean.KclbBean>(courseList) {
                @Override
                public int getLayoutId(int viewType) {
                    return R.layout.item_sc_today;
                }

                @Override
                public void convert(VH holder, final TodayCourseRequestEntity.ContentBean.KclbBean data, int position) {
                    switch (data.getSTATE()) {
                        case BeanState.DM_State.ZZS://正在上

                            holder.setRelativeLayout(R.id.rel_item_sc_today_course).setBackground(ContextCompat.getDrawable(context, R.drawable.border_shade_51cbf3));
                            holder.setTextView(R.id.text_item_sc_today_course, data.getKCMC()).setTextColor(ContextCompat.getColor(context, R.color.white));
                            holder.setTextView(R.id.text_item_sc_today_course_num, "课程编号:" + data.getKCBH()).setTextColor(ContextCompat.getColor(context, R.color.white));
                            holder.setTextView(R.id.text_item_sc_today_course_time, data.getKSSJ() + "~" + data.getJSSJ() + "节课  " + data.getSKSJ()).setTextColor(ContextCompat.getColor(context, R.color.white));
                            holder.setTextView(R.id.text_item_sc_today_course_site, data.getJSMC()).setTextColor(ContextCompat.getColor(context, R.color.white));
                            holder.setTextView(R.id.text_item_sc_today_course_people, data.getNUM() + "人").setTextColor(ContextCompat.getColor(context, R.color.white));
                            holder.setImageView(context,R.id.image_item_sc_today_course_time,R.drawable.tec_ind_shiajin);
                            holder.setImageView(context,R.id.image_item_sc_today_course_site,R.drawable.tec_ind_adr);
                            break;
                        case BeanState.DM_State.JYS://将要上

                            holder.setRelativeLayout(R.id.rel_item_sc_today_course).setBackground(ContextCompat.getDrawable(context, R.drawable.border_shade_ffffff));
                            holder.setTextView(R.id.text_item_sc_today_course, data.getKCMC()).setTextColor(ContextCompat.getColor(context, R.color.color_dark_333333));
                            holder.setTextView(R.id.text_item_sc_today_course_num, "课程编号:" + data.getKCBH()).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                            holder.setTextView(R.id.text_item_sc_today_course_time, data.getKSSJ() + "~" + data.getJSSJ() + "节课  " + data.getSKSJ()).setTextColor(ContextCompat.getColor(context, R.color.color_7));
                            holder.setTextView(R.id.text_item_sc_today_course_site, data.getJSMC()).setTextColor(ContextCompat.getColor(context, R.color.color_7));
                            holder.setTextView(R.id.text_item_sc_today_course_people, data.getNUM() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_7));
                            holder.setImageView(context,R.id.image_item_sc_today_course_time,R.drawable.tec_ind_shiajin);
                            holder.setImageView(context,R.id.image_item_sc_today_course_site,R.drawable.tec_ind_adr);
                            break;
                        case BeanState.DM_State.YJS://已上

                            holder.setRelativeLayout(R.id.rel_item_sc_today_course).setBackground(ContextCompat.getDrawable(context, R.drawable.border_shade_f6f6f6));
                            holder.setTextView(R.id.text_item_sc_today_course, data.getKCMC()).setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                            holder.setTextView(R.id.text_item_sc_today_course_num, "课程编号:" + data.getKCBH()).setTextColor(ContextCompat.getColor(context, R.color.color_b1b1b1));
                            holder.setTextView(R.id.text_item_sc_today_course_time, data.getKSSJ() + "~" + data.getJSSJ() + "节课  " + data.getSKSJ()).setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));
                            holder.setTextView(R.id.text_item_sc_today_course_site, data.getJSMC()).setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));
                            holder.setTextView(R.id.text_item_sc_today_course_people, data.getNUM() + "人").setTextColor(ContextCompat.getColor(context, R.color.color_bbbbbb));
                            holder.setImageView(context,R.id.image_item_sc_today_course_time,R.drawable.tec_ind_shiajin1);
                            holder.setImageView(context,R.id.image_item_sc_today_course_site,R.drawable.tec_ind_js);
                            break;
                    }

                    holder.setRelativeLayout(R.id.rel_item_sc_today_course).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                transmitList.clear();
                                try {
                                    transmitList.add(new TodayCourseRequestEntity.ContentBean.KclbBean(data.getJSXM(), data.getSTATE(), data.getKCBH(), data.getZKS(), data.getJSMC(), data.getSKSJ(), data.getJSSJ(), data.getKSSJ(), data.getKCMC(), data.getNUM(), data.getYR(), data.getYJX()));
                                } catch (Exception e) {
                                    transmitList.add(new TodayCourseRequestEntity.ContentBean.KclbBean(data.getJSXM(), data.getSTATE(), data.getKCBH(), data.getZKS(), data.getJSMC(), data.getSKSJ(), data.getJSSJ(), data.getKSSJ(), data.getKCMC(), data.getNUM(), data.getYR(), -1));
                                }
                                getCourseID(data.getKCBH(), now_weeks + "", now_week + "", data.getKSSJ());
                            }
                        }
                    });
                }
            };
            recScToday.setLayoutManager(new LinearLayoutManager(context));
            recScToday.addItemDecoration(new MyItemDecoration(context));
            recScToday.setAdapter(courseAdapter);
        } else {
            courseAdapter.notifyDataSetChanged();
        }
        refreshScToday.finishRefresh();
    }

    /**
     * 加载星期RecyclerView数据
     */
    private void setWeeksRecyclerView(final Context context) {
        List<WeekBean> weekBeanList = new ArrayList<>();
        weekBeanList.add(new WeekBean("周日", "Sun", 7));
        weekBeanList.add(new WeekBean("周一", "Mon", 1));
        weekBeanList.add(new WeekBean("周二", "Tue", 2));
        weekBeanList.add(new WeekBean("周三", "Wed", 3));
        weekBeanList.add(new WeekBean("周四", "Thu", 4));
        weekBeanList.add(new WeekBean("周五", "Fri", 5));
        weekBeanList.add(new WeekBean("周六", "Sat", 6));
        weekQuickAdapter = new QuickAdapter<WeekBean>(weekBeanList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_sc_today_week;
            }

            @Override
            public void convert(final VH holder, final WeekBean data, final int position) {
                if (now_week == data.getId()) {
                    holder.setTextView(R.id.text_sc_today_week_num, data.getWeek()).setTextColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
                    holder.setTextView(R.id.text_sc_today_week_eng, data.getEng()).setTextColor(ContextCompat.getColor(context, R.color.color_blue_3da8f5));
                } else {
                    holder.setTextView(R.id.text_sc_today_week_num, data.getWeek()).setTextColor(ContextCompat.getColor(context, R.color.color_somber));
                    holder.setTextView(R.id.text_sc_today_week_eng, data.getEng()).setTextColor(ContextCompat.getColor(context, R.color.color_somber));
                }
                holder.setLinearLayout(R.id.text_sc_today_week).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        now_week = data.getId();
                        weekQuickAdapter.notifyDataSetChanged();
                        getCourseRequestData(now_weeks, now_week);
                    }
                });
            }
        };
        recScTodayWeeks.setLayoutManager(new GridLayoutManager(context, 7));
        recScTodayWeeks.setAdapter(weekQuickAdapter);
    }

    /**
     * 获取学校周数、星期数，当前课时数
     */
    public void getDateRequestData() {
        showLoad(HintTool.Loading);
        Map<String, String> params = new HashMap<>();
        params.put("date", TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy/MM/dd"));
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(params, UrlUtil.DQZC, TagUtil.DQZCTag);
    }

    /**
     * 获取当日课程列表
     *
     * @param djz 第几周
     * @param xqj 星期几
     */
    private Map<String, String> courseMap;

    private void getCourseRequestData(int djz, int xqj) {
        showLoad(HintTool.Loading);
        if (courseMap == null) {
            courseMap = new HashMap<>();
        }
        courseMap.put("djz", djz + "");
        courseMap.put("xqj", xqj + "");
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(courseMap, UrlUtil.Course, TagUtil.CourseListTag);
    }


    /**
     * 获取课堂ID
     *
     * @param kcbh        课程编号
     * @param djj         第几节
     * @param xqj         星期几
     * @param djz         第几周
     */
    private Map<String, String> courseIDMap;

    private void getCourseID(String kcbh, String djz, String xqj, String djj) {
        showLoad(HintTool.Loading);
        if (courseIDMap == null) {
            courseIDMap = new HashMap<>();
        }
        courseIDMap.put("kcbh", kcbh);
        courseIDMap.put("djz", djz);
        courseIDMap.put("xqj", xqj);
        courseIDMap.put("djj", djj);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(courseIDMap, UrlUtil.CourseID, TagUtil.CourseIDTag);
    }

    /**
     * 刷新
     */

    private class DropRefreshListener implements OnRefreshListener {

        @Override
        public void onRefresh(RefreshLayout refreshlayout) {
            refreshScTodayText.setText(getRandomString(getActivity()));
            AnimationDrawable refreshingAnimation = (AnimationDrawable) refreshScTodayTextIamge.getBackground();
            refreshingAnimation.start();
            getCourseRequestData(now_weeks, now_week);
        }
    }

    /**
     * 随机获取刷新提示语
     */
    private int showStringIndex = 0;

    private String getRandomString(Context context) {
        String showString;
        try {
            JSONArray jsa = new JSONArray(JsonReaderHelper.getJosn(context, "MJRefreshState.json"));
            showStringIndex = RandomTools.getRandomNumber(jsa.length(), showStringIndex);
            showString = jsa.getString(showStringIndex);
        } catch (Exception e) {
            showString = context.getResources().getString(R.string.refreshing);
        }
        return showString;
    }

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

        String result;
        switch (event.getTag()) {

            case TagUtil.ChoiceWeeks://弹出框返回选择的周数
                now_weeks = (int) event.getMessage();
                textScTodayWeek.setText("第\t" + now_weeks + "\t周");
                getCourseRequestData(now_weeks, now_week);
                break;
            case TagUtil.DQZCTag://获取时间
                result = (String) event.getMessage();
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(getActivity(), HintTool.REQUESTFAIL);
                } else {
                    TodayCourseDateRequestEntity entity = GsonImpl.get().toObject(result, TodayCourseDateRequestEntity.class);

                    now_week = entity.getContent().getXqj();
                    now_weeks = entity.getContent().getDqzc();
                    now_choise_weeks = entity.getContent().getDqzc();
                    total_weeks = entity.getContent().getMaxZC();
                    textScTodayWeek.setText("第\t" + now_weeks + "\t周");

                    setWeeksRecyclerView(getActivity());
                    getCourseRequestData(now_weeks, now_week);
                }
                break;
            case TagUtil.CourseIDTag://获取课堂ID

                result = (String) event.getMessage();
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(getActivity(), HintTool.NoCourseID);
                } else {
                    TodayCourseIDEntity courseIDEntity = GsonImpl.get().toObject(result, TodayCourseIDEntity.class);
                    String courseID = courseIDEntity.getContent().getZhktid();
                    Intent intent;
                    if (AuthenticationUtil.getIdentity().equals(AuthenticationUtil.Teacher)) {
                        intent = new Intent(getActivity(), CourseDetailTeacherActivity.class);
                    } else {
                        intent = new Intent(getActivity(), CourseDetailStudentActivity.class);
                    }
                    intent.putExtra("data", (Serializable) transmitList);
                    intent.putExtra("courseID", courseID);
                    intent.putExtra("week", now_week);
                    startActivity(intent);
                }
                break;
            case TagUtil.CourseListTag://获取课堂列表
                result = (String) event.getMessage();
                switch (result) {
                    case HintTool.REQUESTFAIL:
                        textScNoData.setText(HintTool.NoData);
                        textScNoData.setVisibility(View.VISIBLE);
                        recScToday.setVisibility(View.GONE);
                        textScTodayClassHour.setText("当日总课时：0");
                        refreshScToday.finishRefresh();
                        break;
                    default:
                        textScNoData.setVisibility(View.GONE);
                        recScToday.setVisibility(View.VISIBLE);
                        setCourseRecyclerView(getActivity(), result);
                        break;
                }
                break;
            case TagUtil.AutoRefreshTag:
                getCourseRequestData(now_weeks, now_week);
                break;
        }
        dismissLoad();
    }

}
