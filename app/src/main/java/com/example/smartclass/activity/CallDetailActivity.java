package com.example.smartclass.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.app3.base_recyclear_adapter.HeaderProductLayout;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.adapter.SimpleAdapter;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.entity.GetCallStateEntity;
import com.example.smartclass.entity.GetCodeEntity;
import com.example.smartclass.entity.StudentListEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.popupwindow.ChangePop;
import com.example.smartclass.popupwindow.HintPopup;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;

/**
 * Created by Administrator on 2018/1/9 0009.
 * 点名详情
 */

public class CallDetailActivity extends BaseRecyclerViewActivity {


    @BindView(R.id.cd_title)
    MyTitleView cdTitle;
    @BindView(R.id.cd_rec)
    RecyclerView cdRec;
    @BindView(R.id.cd_people_detail_lin)
    LinearLayout cdPeopleDetailLin;
    @BindView(R.id.cd_submit_text)
    TextView cdSubmitText;
    @BindView(R.id.cd_start_text)
    TextView cdStartText;
    @BindView(R.id.cd_parent_rel)
    RelativeLayout cdParentRel;

    private HeaderProductLayout mHeaderView;


    private SimpleAdapter simpleAdapter;
    private Context context;
    int imageY = 0;

    private List<StudentListEntity.ContentBean> studentList;

    private TextView courseNameText;
    private TextView coursePeopleNumText;
    private TextView percentText;
    private TextView arriveNumText;
    private TextView leaveNumText;
    private TextView lateNumText;
    private TextView leaveEarlierNumText;
    private TextView truantNumText;

    private int arriveNum = 0;//到勤人数
    private int leaveNum = 0;//请假人数
    private int lateNum = 0;//迟到人数
    private int leaveEarlierNum = 0;//早退人数
    private int truantNum = 0;//旷课人数
    private int totalNum = 0;//总人数

    private int index = 0;//点击修改学生状态下标

    private String courseID;//课程ID

    private String courseName;//课程名称
    private String studentState;//保存学生修改状态


    private boolean isSignFinish = false;//签到时间是否结束
    private boolean isSignFirst = false;//是否第一次发起签到


    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_detail;
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
        showLoad(HintTool.Loading);
        getStudentList();
        getCanSubmitCallState(false);
    }

    @Override
    protected void init(Context context) {
        this.context = context;
        courseID = getIntent().getExtras().getString("courseID");
        courseName = getIntent().getExtras().getString("courseName");
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

    @OnClick({R.id.cd_submit_text, R.id.cd_start_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cd_submit_text:
                getCanSubmitCallState(true);
                break;
            case R.id.cd_start_text:
                if (!isSignFirst) {
                    HintPopup hintPopup = new HintPopup(this, cdParentRel, "确定再次发起点名？\n再次点名会覆盖上次记录", 0, HintTool.Confirm);
                    hintPopup.showPopupWindow(cdParentRel);
                } else {
                    getCode();
                    showLoad(HintTool.StartCall);
                }
                break;
        }
    }


    /**
     * 设置RecyclerView数据
     */
    private void setRecyclerViewData() {
        simpleAdapter = new SimpleAdapter(studentList, context);
        cdRec.setLayoutManager(new GridLayoutManager(context, 2));
        mHeaderView = new HeaderProductLayout(this);
        findHeaderViewID();
        simpleAdapter.addHeaderView(mHeaderView);
        simpleAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {//长按修改
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isSignFirst) {
                    index = position;
                    ChangePop changePop = new ChangePop(context, view);
                    changePop.showPopupWindow(view);
                }
                return false;
            }
        });
        simpleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {//跳转学生详情页面
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!isSignFirst) {
                    index = position;
                    Intent intent = new Intent(context, PersonalDetailActivity.class);
                    intent.putExtra("state", studentList.get(index).getState());
                    intent.putExtra("xh", studentList.get(index).getXh());
                    context.startActivity(intent);
                }
            }
        });
        cdRec.setAdapter(simpleAdapter);
        cdRec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] location = new int[2];
                cdPeopleDetailLin.getLocationOnScreen(location);
                int y = location[1];
                imageY = y;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (mHeaderView == null) return;
                int getTop = mHeaderView.getDistanceY();
                if (getTop <= imageY) {
                    cdPeopleDetailLin.setVisibility(View.VISIBLE);
                } else {
                    cdPeopleDetailLin.setY(0);
                    cdPeopleDetailLin.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * 绑定头部控件ID
     */

    private void findHeaderViewID() {

        courseNameText = mHeaderView.findViewById(R.id.cd_course_name_text);
        coursePeopleNumText = mHeaderView.findViewById(R.id.cd_course_people_num_text);

        percentText = mHeaderView.findViewById(R.id.cd_percent_text);

        arriveNumText = mHeaderView.findViewById(R.id.cd_arrive_num_text);
        leaveNumText = mHeaderView.findViewById(R.id.cd_leave_num_text);
        lateNumText = mHeaderView.findViewById(R.id.cd_late_num_text);
        leaveEarlierNumText = mHeaderView.findViewById(R.id.cd_leave_earlier_num_text);
        truantNumText = mHeaderView.findViewById(R.id.cd_truant_num_text);

        showView();
    }

    /**
     * 获取学生以及初始状态列表
     */
    private Map<String, String> studentListMap;

    private void getStudentList() {
        if (studentListMap == null) {
            studentListMap = new HashMap<>();
        }
        studentListMap.put("zhktid", courseID);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(studentListMap, UrlUtil.StudentAndStateList, TagUtil.StudentListTag);
    }

    /**
     * 获取验证码
     */
    private Map<String, String> codeMap;

    private void getCode() {
        isSignFirst = false;
        if (codeMap == null) {
            codeMap = new HashMap<>();
        }
        codeMap.put("zhktid", courseID);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(codeMap, UrlUtil.GetCode, TagUtil.GetCodeTag);
    }

    /**
     * 提交点名
     */
    private Map<String, String> submitCallMap;

    private void submitCall() {
        showLoad(HintTool.SUBMITING);
        if (submitCallMap == null) {
            submitCallMap = new HashMap<>();
        }
        submitCallMap.put("zhktid", courseID);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(submitCallMap, UrlUtil.SubmitCall, TagUtil.SubmitCallTag);
    }

    /**
     * 刷新学生到勤状态列表
     */
    private Map<String, String> refreshStudentStateMap;

    private void refreshStudentState() {
        if (!isSignFinish) {
            if (refreshStudentStateMap == null) {
                refreshStudentStateMap = new HashMap<>();
            }
            refreshStudentStateMap.put("zhktid", courseID);
            BaseVolleyRequest request = new BaseVolleyRequest();
            request.VolleyRequest(refreshStudentStateMap, UrlUtil.StudentAndStateList, TagUtil.GetRefreshStudentStateTag);
        }
    }

    private Map<String, String> changeStateMap;

    /**
     * 修改点名
     *
     * @param state 修改状态
     * @param xsid  学生ID
     */

    private void changeState(String xsid, String state) {
        showLoad(HintTool.SUBMITING);
        if (changeStateMap == null) {
            changeStateMap = new HashMap<>();
        }
        changeStateMap.put("zhktid", courseID);
        changeStateMap.put("xsid", xsid);
        changeStateMap.put("state", state);
        BaseVolleyRequest request = new BaseVolleyRequest();
        request.VolleyRequest(changeStateMap, UrlUtil.ChangeState, TagUtil.ChangeStateTag);
    }


    /**
     * 获取能否提交点名状态
     */
    private String callStateResult = null;
    private Map<String, String> callStateMap = new HashMap<>();

    private void getCanSubmitCallState(final boolean submit) {
        callStateMap.put("zhktid", courseID);
        VolleyRequest.RequestPost(UrlUtil.GetCanSubmitCallState, callStateMap, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {//访问成功
                    GetCallStateEntity callStateEntity = GsonImpl.get().toObject(result, GetCallStateEntity.class);
                    callStateResult = callStateEntity.getContent().getState();
                    switch (callStateResult) {
                        case BeanState.DM_State.WDM://未点名
                            isSignFirst = true;
                            cdStartText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.white));
                            cdStartText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_3df4ff));
                            cdSubmitText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.color_gray_999999));
                            cdSubmitText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_f5f5f5));
                            break;
                        case BeanState.DM_State.YDM://已点名
                            isSignFirst = false;
                            cdStartText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.color_gray_999999));
                            cdStartText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_f5f5f5));
                            cdSubmitText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.color_gray_999999));
                            cdSubmitText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_f5f5f5));
                            break;
                        case BeanState.DM_State.ZZDM://正在点名
                            isSignFirst = false;
                            cdStartText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.color_gray_999999));
                            cdStartText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_f5f5f5));
                            cdSubmitText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.white));
                            cdSubmitText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_3df4ff));
                            break;
                        case BeanState.DM_State.ZZDJS://正在倒计时
                            isSignFirst = false;
                            cdStartText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.color_gray_999999));
                            cdStartText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_f5f5f5));
                            cdSubmitText.setTextColor(ContextCompat.getColor(CallDetailActivity.this, R.color.white));
                            cdSubmitText.setBackground(ContextCompat.getDrawable(CallDetailActivity.this, R.drawable.gradient_3df4ff));
                            Intent intent = new Intent(CallDetailActivity.this, VerificationCodeActivity.class);
                            intent.putExtra("time", callStateEntity.getContent().getSysj());
                            intent.putExtra("code", callStateEntity.getContent().getYzm());
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            break;
                    }
                    if (submit && callStateResult.equals(BeanState.DM_State.ZZDM)) {
                        submitCall();
                    }
                } else {
                    callStateResult = HintTool.REQUESTFAIL;
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                callStateResult = HintTool.REQUESTFAIL;
            }
        });
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = (String) event.getMessage();
        switch (event.getTag()) {
            case TagUtil.ChangeState://修改点名
                studentState = result;
                switch (result) {
                    case BeanState.ArriveCondition.DQ:
                        changeState(studentList.get(index).getXh(), BeanState.DM_State.DQ);
                        break;
                    case BeanState.ArriveCondition.QJ:
                        changeState(studentList.get(index).getXh(), BeanState.DM_State.QJ);
                        break;
                    case BeanState.ArriveCondition.CD:
                        changeState(studentList.get(index).getXh(), BeanState.DM_State.CD);
                        break;
                    case BeanState.ArriveCondition.ZT:
                        changeState(studentList.get(index).getXh(), BeanState.DM_State.ZT);
                        break;
                    case BeanState.ArriveCondition.KK:
                        changeState(studentList.get(index).getXh(), BeanState.DM_State.KK);
                        break;
                }
                break;
            case TagUtil.StudentListTag://获取学生列表
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    StudentListEntity studentListEntity = GsonImpl.get().toObject(result, StudentListEntity.class);
                    studentList = studentListEntity.getContent();
                    totalNum = studentList.size();
                    peopleNumCount();
                    setRecyclerViewData();
                }
                break;
            case TagUtil.GetCodeTag://获取验证码
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    GetCodeEntity codeEntity = GsonImpl.get().toObject(result, GetCodeEntity.class);
                    Intent intent = new Intent(CallDetailActivity.this, VerificationCodeActivity.class);
                    intent.putExtra("time", codeEntity.getContent().getCzsj());
                    intent.putExtra("code", codeEntity.getContent().getYzm());
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
                break;
            case TagUtil.RefreshStudentStateTag://开始刷新和结束刷新
                if (result.equals(HintTool.StartSignIn)) {
                    isSignFinish = false;
                    refreshStudentState();
                } else if (result.equals(HintTool.EndSignIn)) {
                    isSignFinish = true;
                    cdStartText.setTextColor(ContextCompat.getColor(this, R.color.color_gray_999999));
                    cdStartText.setBackground(ContextCompat.getDrawable(this, R.drawable.gradient_f5f5f5));
                    cdSubmitText.setTextColor(ContextCompat.getColor(this, R.color.white));
                    cdSubmitText.setBackground(ContextCompat.getDrawable(this, R.drawable.gradient_3df4ff));

                }
                break;
            case TagUtil.GetRefreshStudentStateTag://刷新学生到勤状态
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    studentList.clear();
                    studentList.addAll(GsonImpl.get().toObject(result, StudentListEntity.class).getContent());
                    totalNum = studentList.size();
                    simpleAdapter.notifyDataSetChanged();
                    peopleNumCount();
                    showView();
                    refreshStudentState();
                }
                break;
            case HintTool.Confirm:
                getCode();
                showLoad(HintTool.StartCall);
                break;
            case TagUtil.SubmitCallTag:
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.SUBMITFail);
                } else {
                    cdStartText.setTextColor(ContextCompat.getColor(this, R.color.color_gray_999999));
                    cdStartText.setBackground(ContextCompat.getDrawable(this, R.drawable.gradient_f5f5f5));
                    cdSubmitText.setTextColor(ContextCompat.getColor(this, R.color.color_gray_999999));
                    cdSubmitText.setBackground(ContextCompat.getDrawable(this, R.drawable.gradient_f5f5f5));
                    ToastUtils.Toast(this, HintTool.SUBMITSUCCESS);
                    getStudentList();
                }
                break;
            case TagUtil.ChangeStateTag://更改学生状态
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.Change_Fail);
                } else {
                    switch (studentState) {
                        case BeanState.ArriveCondition.DQ:
                            studentList.get(index).setState(BeanState.DM_State.DQ);
                            break;
                        case BeanState.ArriveCondition.QJ:
                            studentList.get(index).setState(BeanState.DM_State.QJ);
                            break;
                        case BeanState.ArriveCondition.CD:
                            studentList.get(index).setState(BeanState.DM_State.CD);
                            break;
                        case BeanState.ArriveCondition.ZT:
                            studentList.get(index).setState(BeanState.DM_State.ZT);
                            break;
                        case BeanState.ArriveCondition.KK:
                            studentList.get(index).setState(BeanState.DM_State.KK);
                            break;
                    }
                    simpleAdapter.notifyDataSetChanged();
                    peopleNumCount();
                    showView();
                }
                break;

        }

        dismissLoad();

    }


    /**
     * 到勤情况人数计算
     */
    private void peopleNumCount() {
        arriveNum = 0;
        leaveNum = 0;
        lateNum = 0;
        leaveEarlierNum = 0;
        truantNum = 0;
        for (int i = 0; i < studentList.size(); i++) {
            switch (studentList.get(i).getState()) {
                case BeanState.DM_State.DQ:
                    arriveNum++;
                    break;
                case BeanState.DM_State.QJ:
                    leaveNum++;
                    break;
                case BeanState.DM_State.CD:
                    lateNum++;
                    break;
                case BeanState.DM_State.ZT:
                    leaveEarlierNum++;
                    break;
                case BeanState.DM_State.KK:
                    truantNum++;
                    break;
            }
        }
    }

    /**
     * 数据展示
     */

    @SuppressLint("SetTextI18n")
    private void showView() {
        courseNameText.setText(courseName + "");

        coursePeopleNumText.setText("共" + totalNum + "人");

        try {
            double f1 = new BigDecimal((float) arriveNum / totalNum).setScale(2, RoundingMode.FLOOR).doubleValue();
            if (((float) arriveNum / totalNum * 100) > 0 && ((float) arriveNum / totalNum * 100) < 1) {
                percentText.setText(1 + "");
            } else {
                percentText.setText((int) (f1 * 100) + "");
            }
        } catch (Exception e) {
            Log.d(TAG, "showView: " + e);
        }

        arriveNumText.setText("到勤：" + arriveNum + "人");
        leaveNumText.setText("请假：" + leaveNum + "人");
        lateNumText.setText("迟到：" + lateNum + "人");
        leaveEarlierNumText.setText("早退：" + leaveEarlierNum + "人");
        truantNumText.setText("旷课：" + truantNum + "人");
    }

    private static final String TAG = "CallDetailActivity";
}
