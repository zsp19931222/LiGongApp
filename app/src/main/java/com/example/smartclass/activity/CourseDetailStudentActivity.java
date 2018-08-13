package com.example.smartclass.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.example.smartclass.entity.NoEvaluateContentEntity;
import com.example.smartclass.entity.StateEntity;
import com.example.smartclass.entity.TodayCourseRequestEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.popupwindow.HintPopup;
import com.example.smartclass.popupwindow.InputVerificationCodePopup;
import com.example.smartclass.popupwindow.SignSuccessPopup;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.logTool.Log;
import yh.app.tool.NetWork;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2018/1/5 0005.
 * 学生课堂详情
 */

public class CourseDetailStudentActivity extends BaseRecyclerViewActivity {

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
    @BindView(R.id.cd_teacher_text)
    TextView cdTeacherText;
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

    private boolean isFirst = true;//是否第一次进入页面

    private static final String TAG = "CourseDetailStudentActi";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course_details_student;
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
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "course_detail_student"), LayoutEntity.class);
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
                                    case "签到":
                                        isFirst = false;
                                        getState();
                                        break;
                                    case "课堂评教":
                                        switch (contentBean.getSTATE()) {
                                            case BeanState.DM_State.ZZS://正在上课
                                                ToastUtils.Toast(context, HintTool.ComeEarly);
                                                break;
                                            case BeanState.DM_State.YJS://已经完成上课
                                                intent = new Intent(data.getCls());
                                                intent.putExtra("title", "课堂评教");
                                                intent.putExtra("url", UrlUtil.StudentsEvaluate + courseID + "&userid=" + Constants.number);
                                                break;
                                            case BeanState.DM_State.JYS://未上课
                                                ToastUtils.Toast(context, HintTool.ComeEarly);
                                                break;
                                        }
                                        break;
                                    case "点名记录":
                                        intent = new Intent(data.getCls());
                                        intent.putExtra("title", "点名记录");
                                        intent.putExtra("url", UrlUtil.CallRecordStudent + courseID + "&userid=" + Constants.number);
                                        break;
                                    case "课堂纸条":
                                        intent = new Intent(data.getCls());
                                        intent.putExtra("friend_id", "20050050");
                                        intent.putExtra("hyName", "任课教师");
                                        break;
                                    default:
                                        intent = new Intent(data.getCls());
                                        break;

                                }

                                if (intent != null) {
                                    startActivity(intent);
                                }
                            } catch (Exception e) {
                                android.util.Log.d(TAG, "onClick: " + e);
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
        cdTeacherText.setText(contentBean.getJSXM());
        if (NetWork.getNetWorkType(this) == 0) {
            cdSignalText.setText("当前信号连接：4G");
        } else if (NetWork.getNetWorkType(this) == 1) {
            cdSignalText.setText("当前信号连接：WIFI");
        } else {
            cdSignalText.setText("当前无信号连接");
        }
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


    private Map<String, String> signInMap;

    /**
     * 学生签到
     *
     * @param code 学生输入的验证码
     */
    private void signIn(String code) {
        showLoad(HintTool.Signing);
        if (signInMap == null) {
            signInMap = new HashMap<>();
        }
        signInMap.put("zhktid", courseID);
        signInMap.put("yzm", code);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(signInMap, UrlUtil.SignIn, TagUtil.SignInTag);
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

    @SuppressLint("SetTextI18n")
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.NetWorkChanged:
                if (result.equals(HintTool.NoNetWork)) {
                    cdSignalText.setText("当前无信号连接");
                } else {
                    cdSignalText.setText("当前信号连接：" + result);
                }
                break;
            case TagUtil.InputCodeTag://签到请求
                signIn(result);
                break;
            case TagUtil.SignInTag://签到结果返回
                if (result.equals(HintTool.REQUESTFAIL)) {
                    HintPopup hintPopup = new HintPopup(this, cdParentLin, HintTool.SignIDFail, new RHelper().getDrawable(this, "zhkt_t_fail"), HintTool.Retype);
                    hintPopup.showPopupWindow(cdParentLin);
                } else {
                    SignSuccessPopup successPopup = new SignSuccessPopup(this);
                    successPopup.showPopupWindow(cdParentLin);
                    cdCoursePeopleNumText.setText("已点名");
                    cdCoursePeopleNumText.setTextColor(ContextCompat.getColor(this, R.color.color_0f5ff8));
                }
                dismissLoad();
                break;
            case TagUtil.GetStateTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    StateEntity stateEntity = GsonImpl.get().toObject(result, StateEntity.class);
                    switch (stateEntity.getContent().getZt()) {
                        case BeanState.DM_State.WDM:
                            cdCoursePeopleNumText.setText("未点名");
                            cdCoursePeopleNumText.setTextColor(ContextCompat.getColor(this, R.color.color_0f5ff8));
                            if (!isFirst) {
                                ToastUtils.Toast(this, HintTool.NotSign);
                            }
                            break;
                        case BeanState.DM_State.YDM:
                            cdCoursePeopleNumText.setText("已点名");
                            cdCoursePeopleNumText.setTextColor(ContextCompat.getColor(this, R.color.color_0f5ff8));
                            if (!isFirst) {
                                ToastUtils.Toast(this, HintTool.SignComplete);
                            }
                            break;
                        case BeanState.DM_State.ZZDM:
                            cdCoursePeopleNumText.setText("正在点名");
                            cdCoursePeopleNumText.setTextColor(ContextCompat.getColor(this, R.color.color_0f5ff8));
                            if (!isFirst) {
                                InputVerificationCodePopup codePopup = new InputVerificationCodePopup(this, stateEntity.getContent().getSysj() * 1000);
                                codePopup.showPopupWindow(cdParentLin);
                            }
                            break;
                        case BeanState.DM_State.YQD:
                            cdCoursePeopleNumText.setText("已点名");
                            cdCoursePeopleNumText.setTextColor(ContextCompat.getColor(this, R.color.color_0f5ff8));
                            if (!isFirst) {
                                ToastUtils.Toast(this, HintTool.SignIn);
                            }
                            break;
                    }
                }
                dismissLoad();
                break;
            case HintTool.Retype:
                getState();
                break;
            case TagUtil.NoEvaluateContentTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    NoEvaluateContentEntity stateEntity = GsonImpl.get().toObject(result, NoEvaluateContentEntity.class);
                    if (contentBean.getYJX() < 0) {
                        cdOtherText.setText(Html.fromHtml("未评教次数：" + "<u>" + stateEntity.getContent() + "</u>"));
                    } else {
                        cdOtherText.setText(Html.fromHtml("总课时：" + contentBean.getZKS() + "  已进行：" + contentBean.getYJX() + "  未评教次数：" + "<u>" + stateEntity.getContent() + "</u>"));
                    }
                }
                dismissLoad();
                break;
        }
    }

    @OnClick(R.id.cd_other_text)
    public void onViewClicked() {
        if (!Tool.isFastDoubleClick()) {
            Intent intent = new Intent(this, BrowserActivity.class);
            intent.putExtra("title", "未评记录");
            intent.putExtra("url", UrlUtil.NoEvaluateStudent + contentBean.getKCBH() + "&userid=" + Constants.number);
            startActivity(intent);
        }
    }
}
