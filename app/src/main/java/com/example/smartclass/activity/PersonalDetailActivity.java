package com.example.smartclass.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.LayoutEntity;
import com.example.app3.tool.AddView;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app3.view.MyTitleView;
import com.example.app4.api.OkHttpUtil;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.entity.GetStudentMessageEntity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.network.BaseVolleyRequest;
import com.example.smartclass.network.UrlUtil;
import com.example.smartclass.popupwindow.ConditionPopup;
import com.example.smartclass.popupwindow.HintPopup;
import com.example.smartclass.popupwindow.InstructorPhoneNumPopup;
import com.example.smartclass.util.BeanState;
import com.example.smartclass.util.TagUtil;
import yh.app.appstart.lg.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.tool.NetWork;
import yh.app.utils.DensityUtil;
import yh.app.utils.FileUtils;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.RHelper;

/**
 * Created by Administrator on 2018/1/8 0008.
 * 人员详情
 */

public class PersonalDetailActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.pd_title)
    MyTitleView pdTitle;
    @BindView(R.id.pd_name_text)
    TextView pdNameText;
    @BindView(R.id.pd_sex_image)
    ImageView pdSexImage;
    @BindView(R.id.pd_name_lin)
    LinearLayout pdNameLin;
    @BindView(R.id.pd_college_text)
    TextView pdCollegeText;
    @BindView(R.id.pd_class_text)
    TextView pdClassText;
    @BindView(R.id.pd_name_rel)
    RelativeLayout pdNameRel;
    @BindView(R.id.pd_xx_rel)
    LinearLayout pdXxRel;
    @BindView(R.id.pd_rec)
    RecyclerView pdRec;
    @BindView(R.id.pd_xx_cut_line)
    ImageView pdXxCutLine;
    @BindView(R.id.pd_message_rel)
    RelativeLayout pdMessageRel;
    @BindView(R.id.pd_phone_rel)
    RelativeLayout pdPhoneRel;
    @BindView(R.id.pd_parent_rel)
    RelativeLayout pdParentRel;
    @BindView(R.id.pd_bh_text)
    TextView pdBhText;
    @BindView(R.id.pd_head_image)
    ImageView pdHeadImage;

    private QuickAdapter quickAdapter;
    private List<LayoutEntity.AllTagsListBean> allTagsListBeans;

    private String state;

    private String xh;//学生学号

    private GetStudentMessageEntity.ContentBean content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_detail;
    }

    @Override
    protected void setTitle(Context context) {
        pdTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        getStudentMessage();
    }

    @Override
    protected void init(Context context) {
        state = getIntent().getExtras().getString("state");
        xh = getIntent().getExtras().getString("xh");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.pd_message_rel, R.id.pd_phone_rel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pd_message_rel:
                ToastUtils.Toast(this, "正在建设中");
                break;
            case R.id.pd_phone_rel:
                if (!content.getLxdh().equals("")) {
                    HintPopup hintPopup = new HintPopup(PersonalDetailActivity.this, pdPhoneRel, content.getLxdh(), R.drawable.ry_tc_dh, "呼叫");
                    hintPopup.showPopupWindow(pdPhoneRel);
                }
                break;
        }
    }

    /**
     * 查询学生信息
     */
    private Map<String, String> messageMap;

    private void getStudentMessage() {
        showLoad(HintTool.Loading);
        if (messageMap == null) {
            messageMap = new HashMap<>();
        }
        messageMap.put("xh", xh);
//        BaseVolleyRequest request = new BaseVolleyRequest();
//        request.VolleyRequest(messageMap, UrlUtil.GetStudentMessage, TagUtil.GetStudentMessageTag);
        OkHttpUtil.OkHttpPost(UrlUtil.GetStudentMessage, messageMap, TagUtil.GetStudentMessageTag,this);
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        dismissLoad();
        switch (event.getTag()) {
            case "呼叫":
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + event.getMessage()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case "修改状态":
                allTagsListBeans.get(2).setExplain((String) event.getMessage());
                switch ((String) event.getMessage()) {
                    case BeanState.ArriveCondition.DQ:
                        state = BeanState.DM_State.DQ;
                        break;
                    case BeanState.ArriveCondition.QJ:
                        state = BeanState.DM_State.QJ;
                        break;
                    case BeanState.ArriveCondition.CD:
                        state = BeanState.DM_State.CD;
                        break;
                    case BeanState.ArriveCondition.ZT:
                        state = BeanState.DM_State.ZT;
                        break;
                    case BeanState.ArriveCondition.KK:
                        state = BeanState.DM_State.KK;
                        break;
                }
                quickAdapter.notifyDataSetChanged();
                break;
            case TagUtil.GetStudentMessageTag:
                String result = (String) event.getMessage();
                if (result.equals(HintTool.REQUESTFAIL)) {
                    ToastUtils.Toast(this, HintTool.REQUESTFAIL);
                } else {
                    GetStudentMessageEntity messageEntity = GsonImpl.get().toObject(result, GetStudentMessageEntity.class);
                    content = messageEntity.getContent();
                    setData();
                    setRecyclerView(this);
                }
                break;
        }
    }

    private void setData() {
        try {
            pdNameText.setText(content.getXm());
            pdBhText.setText(content.getXh());
            pdCollegeText.setText(content.getXymc());
            pdClassText.setText(content.getBjmc());
            GlideLoadUtils.getInstance().glideLoad(this, content.getTx(), pdHeadImage, R.drawable.np);
            if (content.getXbmc().equals("男")) {
                pdSexImage.setBackgroundResource(R.drawable.ico_man);
            } else {
                pdSexImage.setBackgroundResource(R.drawable.ico_woman);
            }
        } catch (Exception ignored) {

        }

    }

    private void setRecyclerView(final Context context) {
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.width = DensityUtil.dip2px(context, 22);
        layoutParams.height = DensityUtil.dip2px(context, 22);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.setMargins(DensityUtil.dip2px(context, 18), 0, 0, 0);
        LayoutEntity entity = GsonImpl.get().toObject(FileUtils.readJsonFile(context, "personal_detail"), LayoutEntity.class);
        allTagsListBeans = entity.getAllTagsList();
        quickAdapter = new QuickAdapter<LayoutEntity.AllTagsListBean>(allTagsListBeans) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_course_detail;
            }

            @Override
            public void convert(VH holder, final LayoutEntity.AllTagsListBean data, int position) {
                if (data.getType().equals("fill_view")) {
                    holder.setRelativeLayout(R.id.item_cd_fill_rel).addView(AddView.addView(context, data.getLayout(), 15));
                    holder.setRelativeLayout(R.id.item_cd_rel).setVisibility(View.GONE);
                } else {

                    switch (data.getTxt()) {
                        case "辅导员":
                            holder.setImageView(context, R.id.item_cd_more_image, R.drawable.myself_button_right_3x);

                            holder.setTextView(R.id.item_cd_num_text, content.getFdy()).setVisibility(View.VISIBLE);
                            break;
                        case "课程状态":
                            holder.setImageView(context, R.id.item_cd_more_image, R.drawable.myself_button_right_3x);

                            switch (state) {
                                case BeanState.DM_State.WD:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.WD).setVisibility(View.VISIBLE);
                                    break;
                                case BeanState.DM_State.DQ:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.DQ).setVisibility(View.VISIBLE);
                                    break;
                                case BeanState.DM_State.QJ:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.QJ).setVisibility(View.VISIBLE);
                                    break;
                                case BeanState.DM_State.CD:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.CD).setVisibility(View.VISIBLE);
                                    break;
                                case BeanState.DM_State.ZT:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.ZT).setVisibility(View.VISIBLE);
                                    break;
                                case BeanState.DM_State.KK:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.KK).setVisibility(View.VISIBLE);
                                    break;
                                default:
                                    holder.setTextView(R.id.item_cd_num_text, BeanState.ArriveCondition.WD).setVisibility(View.VISIBLE);
                                    break;
                            }
                            break;
                        case "手机信号":
                            if (NetWork.getNetWorkType(context) == 1) {
                                holder.setTextView(R.id.item_cd_num_text, "WIFI").setVisibility(View.VISIBLE);
                            } else {
                                holder.setTextView(R.id.item_cd_num_text, "4G").setVisibility(View.VISIBLE);
                            }
                            break;
                        case "手机型号":
                            try {
                                holder.setTextView(R.id.item_cd_num_text, android.os.Build.BRAND).setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                holder.setTextView(R.id.item_cd_num_text, "未知").setVisibility(View.VISIBLE);
                            }
                            break;
                        case "当前位置":
                            holder.setTextView(R.id.item_cd_num_text, "重庆").setVisibility(View.VISIBLE);
                            break;
                        default:
                            holder.setImageView(context, R.id.item_cd_more_image, R.drawable.myself_button_right_3x);

                            break;
                    }
                    holder.setTextView(R.id.item_cd_text, data.getTxt());
                    holder.setImageView(context, R.id.item_cd_image, new RHelper().getDrawable(context, data.getPic_default())).setLayoutParams(layoutParams);
                    holder.setImageView(context, R.id.item_cd_image, new RHelper().getDrawable(context, data.getPic_default())).setVisibility(View.VISIBLE);
                    holder.setRelativeLayout(R.id.item_cd_rel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                switch (data.getTxt()) {
                                    case "辅导员":
                                        InstructorPhoneNumPopup instructorPhoneNumPopup = new InstructorPhoneNumPopup(context, content.getFdy(), content.getFdydh());
                                        instructorPhoneNumPopup.showPopupWindow(pdParentRel);
                                        break;
                                    case "课程状态":
                                        ConditionPopup conditionPopup = new ConditionPopup(context);
                                        conditionPopup.showPopupWindow(pdParentRel);
                                        break;
                                    default:
                                        try {
                                            Intent intent = new Intent(data.getCls());
                                            startActivity(intent);
                                        } catch (Exception e) {

                                        }
                                        break;
                                }
                            }
                        }
                    });
                }
            }
        };
        pdRec.setLayoutManager(new LinearLayoutManager(context));
        pdRec.setAdapter(quickAdapter);
    }
}
