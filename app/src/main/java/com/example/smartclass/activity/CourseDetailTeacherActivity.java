package com.example.smartclass.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.entity.ClassStateEntity;
import com.example.smartclass.entity.NoEvaluateContentEntity;
import com.example.smartclass.entity.StateEntity;
import com.example.smartclass.entity.TodayCourseRequestEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.logTool.Log;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2018/1/5 0005.
 * <p>
 * 教师课堂详情
 */

public class CourseDetailTeacherActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.cd_title)
    MyTitleView cdTitle;
    @BindView(R.id.cd_course_text)
    TextView cdCourseText;
    @BindView(R.id.cd_course_people_num_text)
    TextView cdCoursePeopleNumText;
    @BindView(R.id.cd_course_lin)
    LinearLayout cdCourseLin;
    @BindView(R.id.cd_time_text)
    TextView cdTimeText;
    @BindView(R.id.cd_site_text)
    TextView cdSiteText;
    @BindView(R.id.cd_other_text)
    TextView cdOtherText;
    @BindView(R.id.cd_signal_text)
    TextView cdSignalText;
    @BindView(R.id.cd_rec)
    RecyclerView cdRec;
    @BindView(R.id.cd_parent_lin)
    LinearLayout cdParentLin;

    private String courseID;//课程ID 上个页面传过来
    private int week;

    private TodayCourseRequestEntity.ContentBean.KclbBean contentBean;

    private String state = "1";//课堂点名状态

    private static final String TAG = "CourseDetailTeacherActi";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_details_teacher;
    }

    @Override
    protected void setTitle(Context context) {
        cdTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "course_detail_teacher"), LayoutEntity.class);
        QuickAdapter quickAdapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(entity.getAllTagsList()) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_course_detail;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, int position) {
                holder.setTextView(R.id.item_cd_text, data.getTxt());
                holder.setImageView((Activity) context, R.id.item_cd_image, new RHelper().getDrawable(context, data.getPic_default())).setVisibility(View.VISIBLE);
                holder.setRelativeLayout(R.id.item_cd_rel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            try {
                                Intent intent = null;
                                switch (data.getTxt()) {
                                    case "发起点名":
                                        classState();
                                        break;
                                    case "课堂评学":
                                        switch (contentBean.getSTATE()) {
                                            case BeanState.DM_State.ZZS://正在上课
                                                ToastUtils.Toast(context, HintTool.ComeEarly);
                                                break;
                                            case BeanState.DM_State.YJS://已经完成上课
                                                intent = new Intent(data.getCls());
                                                intent.putExtra("title", "课堂评学");
                                                intent.putExtra("url", UrlUtil.TeachersEvaluate + courseID + "&userid=" + Constants.number);
                                                break;
                                            case BeanState.DM_State.JYS://未上课
                                                ToastUtils.Toast(context, HintTool.ComeEarly);
                                                break;
                                        }
                                        break;
                                    case "点名记录":
                                        switch (contentBean.getSTATE()) {
                                            case BeanState.DM_State.ZZS://正在上课
                                                ToastUtils.Toast(context, HintTool.CourseNotComplete);
                                                break;
                                            case BeanState.DM_State.YJS://已经完成上课
                                                intent = new Intent(data.getCls());
                                                intent.putExtra("courseID", courseID);
                                                intent.putExtra("state", state);
                                                intent.putExtra("courseName", contentBean.getKCMC());
                                                break;
                                            case BeanState.DM_State.JYS://未上课
                                                ToastUtils.Toast(context, HintTool.CourseNotStarted);
                                                break;
                                        }

                                        break;
                                    case "班级成员":
                                        intent = new Intent(data.getCls());
                                        intent.putExtra("kcbh", contentBean.getKCBH());
                                        break;
                                    default:
                                        intent = new Intent(data.getCls());
                                        break;
                                }
                                if (intent != null) {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                android.util.Log.i(TAG, "onClick: ");
                            }
                        }
                    }
                });
            }

        };
        cdRec.setLayoutManager(new LinearLayoutManager(context));
        cdRec.setAdapter(quickAdapter);
    }

    @Override
    protected void init(Context context) {
        try {
            courseID = getIntent().getExtras().getString("courseID");
            week = getIntent().getExtras().getInt("week");
            List<TodayCourseRequestEntity.ContentBean.KclbBean> dataList = (List<TodayCourseRequestEntity.ContentBean.KclbBean>) getIntent().getSerializableExtra("data");
            contentBean = dataList.get(0);
            setInformation();
            getState();
            getNoEvaluateContent();
        } catch (Exception e) {
            Log.d("zsp", e.toString());
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置基本信息
     */
    @SuppressLint("SetTextI18n")
    private void setInformation() {
        cdCourseText.setText(contentBean.getKCMC() + "");
        cdCoursePeopleNumText.setText(contentBean.getNUM() + "人");
        cdTimeText.setText(contentBean.getYR() + " " + weekSwitch(week) + " " + contentBean.getKSSJ() + "-" + contentBean.getJSSJ() + "节");
        cdSiteText.setText(contentBean.getJSMC() + "");

    }

    private String weekSwitch(int week) {
        String returnStr = "";
        switch (week) {
            case 1:
                returnStr = "周一";
                break;
            case 2:
                returnStr = "周二";
                break;
            case 3:
                returnStr = "周三";
                break;
            case 4:
                returnStr = "周四";
                break;
            case 5:
                returnStr = "周五";
                break;
            case 6:
                returnStr = "周六";
                break;
            case 7:
                returnStr = "周日";
                break;

        }
        return returnStr;
    }

    /**
     * 获取点名课程状态
     */
    Map<String, String> stateMap;

    private void getState() {
        showLoad(HintTool.Loading);
        if (stateMap == null) {
            stateMap = new HashMap<>();
        }
        stateMap.put("zhktid", courseID);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(stateMap, UrlUtil.GetState, TagUtil.GetStateTag);
    }

    /**
     * 获取未评次数
     */
    Map<String, String> NoEvaluateContentMap;

    private void getNoEvaluateContent() {
        showLoad(HintTool.Loading);
        if (NoEvaluateContentMap == null) {
            NoEvaluateContentMap = new HashMap<>();
        }
        NoEvaluateContentMap.put("kcbh", contentBean.getKCBH());
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(NoEvaluateContentMap, UrlUtil.NoEvaluateContent, TagUtil.NoEvaluateContentTag);
    }

    /**
     * 获取课程状态
     */
    private Map<String, String> classStateMap = new HashMap<>();

    private void classState() {
        if (classStateMap == null) {
            classStateMap = new HashMap<>();
        }
        classStateMap.put("zhktid", courseID);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(classStateMap, UrlUtil.ClassState, TagUtil.ClassStateTag);
    }

    @SuppressLint("SetTextI18n")
    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.GetStateTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    StateEntity stateEntity = GsonImpl.get().toObject(result, StateEntity.class);
                    state = stateEntity.getContent().getZt();
                }
                dismissLoad();
                break;
            case TagUtil.NoEvaluateContentTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    NoEvaluateContentEntity stateEntity = GsonImpl.get().toObject(result, NoEvaluateContentEntity.class);
                    if (contentBean.getYJX() < 0) {
                        cdOtherText.setText(Html.fromHtml("未评学次数：" + "<u>" + stateEntity.getContent() + "</u>"));
                    } else {
                        cdOtherText.setText(Html.fromHtml("总课时：" + contentBean.getZKS() + "  已进行：" + contentBean.getYJX() + "  未评学次数：" + "<u>" + stateEntity.getContent() + "</u>"));
                    }
                }
                dismissLoad();
                break;
            case TagUtil.ClassStateTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    ClassStateEntity classStateEntity = GsonImpl.get().toObject(result, ClassStateEntity.class);
                    switch (classStateEntity.getContent().getKczt()) {
                        case BeanState.DM_State.ZZS://正在上课
                            Intent intent = new Intent(this, CallDetailActivity.class);
                            intent.putExtra("courseID", courseID);
                            intent.putExtra("state", state);
                            intent.putExtra("courseName", contentBean.getKCMC());
                            startActivity(intent);
                            break;
                        case BeanState.DM_State.YJS://已经完成上课
                            ToastUtils.Toast(this, HintTool.CourseComplete);
                            break;
                        case BeanState.DM_State.JYS://未上课
                            ToastUtils.Toast(this, HintTool.CourseNotStarted);
                            break;
                    }
                }
                break;
        }
    }

    @OnClick(R.id.cd_other_text)
    public void onViewClicked() {
        if (!Tool.isFastDoubleClick()) {
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra("title", "未评记录");
            intent.putExtra("url", UrlUtil.NoEvaluateTeacher + contentBean.getKCBH() + "&userid=" + Constants.number);
            startActivity(intent);
        }
    }
}
