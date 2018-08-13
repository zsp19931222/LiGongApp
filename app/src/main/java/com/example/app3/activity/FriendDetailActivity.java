package com.example.app3.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.entity.FrRrquestEntity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.popupwindow.HintPopup;
import com.example.app3.tool.GlideRoundTransform;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app3.view.MyTitleView;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyAdd;
import com.yunhuakeji.app.utils.MapTools;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.function.liaotianjiemian;
import yh.app.logTool.Log;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/11.
 * <p>
 * 好友详情页面
 */

public class FriendDetailActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.fr_rel_parent)
    RelativeLayout frRelParent;

    private class FRBead {
        private String str1;

        public FRBead(String str1, String str2) {
            this.str1 = str1;
            this.str2 = str2;
        }

        public String getStr2() {
            return str2;
        }

        public void setStr2(String str2) {
            this.str2 = str2;
        }

        public String getStr1() {
            return str1;
        }

        public void setStr1(String str1) {
            this.str1 = str1;
        }

        private String str2;
    }

    @BindView(R.id.fr_title)
    MyTitleView frTitle;
    @BindView(R.id.fr_image_userHead)
    ImageView frImageUserHead;
    @BindView(R.id.fr_text_message)
    TextView frTextMessage;
    @BindView(R.id.fr_text_messageHint)
    TextView frTextMessageHint;
    @BindView(R.id.fr_view_cutline)
    View frViewCutline;
    @BindView(R.id.fr_rec)
    RecyclerView frRec;
    @BindView(R.id.fr_text_name)
    TextView frTextName;
    @BindView(R.id.fr_iamge_sex)
    ImageView frIamgeSex;
    @BindView(R.id.fr_text_code)
    TextView frTextCode;
    @BindView(R.id.fr_text_agree)
    TextView frTextAgree;
    @BindView(R.id.fr_text_del)
    TextView frTextDel;

    private String hybh;//传过来的好友id

    private String deal;//是否是好友

    private QuickAdapter adapter;

    private String sexMan = "1";//男

    private List<FRBead> list;

    private LoadDiaog loadDiaog;
    private boolean canClick = false;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_friend_request;
    }

    @Override
    protected void setTitle(Context context) {
        frTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

        getFrMessage(context, hybh);
    }

    @Override
    protected void init(Context context) {
        hybh = getIntent().getExtras().getString("fqr");

        new SqliteHelper().execSQL("update hyqqNum set isread='true' where fqr=?", hybh);

        list = new ArrayList<>();
        list.add(new FRBead("学院", "学院"));
        list.add(new FRBead("性别", "性别"));
        list.add(new FRBead("职位", "职位"));
        loadDiaog = new LoadDiaog(context);
        loadDiaog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            deal = new SqliteHelper().rawQuery("select * from addFriend where fqr=?", hybh).get(0).get("deal");
            if (BodyAdd.DEAL_NO.equals(deal)) {//等待验证
                frTextMessage.setVisibility(View.VISIBLE);
                frTextMessageHint.setVisibility(View.VISIBLE);
                frTextAgree.setText("通过验证");
                frTextDel.setVisibility(View.GONE);
            } else if (BodyAdd.DEAL_DISAGREE.equals(deal)) {
                frTitle.setTitle("好友信息", this);
                frTextMessage.setVisibility(View.GONE);
                frTextMessageHint.setVisibility(View.GONE);
                frTextAgree.setText("等待验证");
                frTextDel.setVisibility(View.GONE);
            } else {//验证通过
                frTitle.setTitle("好友信息", this);
                frTextMessage.setVisibility(View.GONE);
                frTextMessageHint.setVisibility(View.GONE);
                frTextAgree.setText("发送消息");
                frTextDel.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            frTitle.setTitle("好友信息", this);
            frTextMessage.setVisibility(View.GONE);
            frTextMessageHint.setVisibility(View.GONE);
            frTextAgree.setText("添加好友");
            frTextDel.setVisibility(View.GONE);
        }
    }

    private String url;

    /**
     * 获取好友信息
     *
     * @param hybh 好友ID
     */
    private void getFrMessage(final Context context, String hybh) {
        Log.d("zsp", _链接地址导航.GroupChat.getUserDetail.getUrl());
        Map<String, String> param = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", Ticket.getFunctionTicket("20150120")
                        },
                        {
                                "hybh", hybh
                        }
                });
        Log.d("zsp", Constants.number + "  ticket=" + Ticket.getFunctionTicket("20150120") + "    hybh=" + hybh);
        VolleyRequest.RequestPost(_链接地址导航.GroupChat.getUserDetail.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    loadRecyclerViewData(context, result);
                    canClick = true;
                } else {
                    ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                    canClick = false;
                }
                if (loadDiaog != null) {
                    loadDiaog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                ToastUtils.Toast(context, HintTool.REQUESTFAIL);
                canClick = false;
                if (loadDiaog != null) {
                    loadDiaog.dismiss();
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadRecyclerViewData(final Context context, String result) {
        FrRrquestEntity entity = GsonImpl.get().toObject(result, FrRrquestEntity.class);
        final FrRrquestEntity.ContentBean bean = entity.getContent();
        GlideLoadUtils.getInstance().glideLoadBorderRadius(context, bean.getFaceaddress(), frImageUserHead, R.drawable.pushlist_default, 5);
        frTextName.setText(bean.getName());
        if (sexMan.equals(bean.getSex_id())) {
            Glide.with(context).load(R.drawable.sex_male_2x).transform(new GlideRoundTransform(context, 0)).error(R.drawable.error).into(frIamgeSex);
        } else {
            Glide.with(context).load(R.drawable.sex_famale_2x).transform(new GlideRoundTransform(context, 0)).error(R.drawable.error).into(frIamgeSex);
        }
        if (bean.getUsertype().equals("教师")) {
            frTextCode.setText("工号：" + bean.getUserid());
        } else {
            frTextCode.setText("学号：" + bean.getUserid());
        }
        frTextMessageHint.setText("附加消息：我是" + bean.getName());
        if (list != null) {
            list.clear();
        }
        list.add(new FRBead("学院", bean.getDepartment_name()));
        list.add(new FRBead("性别", bean.getSex_name()));
        list.add(new FRBead("职位", bean.getUsertype()));
        adapter = new QuickAdapter<FRBead>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_archives;
            }

            @Override
            public void convert(VH holder, FRBead data, int position) {
                holder.setTextView(R.id.item_archives_text_name, data.getStr1());
                holder.setTextView(R.id.item_archives_text_num, data.getStr2());
                holder.setImageView((Activity) context, R.id.item_archives_iamge_icon, "").setVisibility(View.GONE);
                holder.setImageView((Activity) context, R.id.item_archives_iamge_head, "").setVisibility(View.GONE);
            }

        };
        frRec.setLayoutManager(new LinearLayoutManager(context));
        frRec.setAdapter(adapter);

    }

    @OnClick({R.id.fr_text_agree, R.id.fr_text_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fr_text_agree:
                try {
                    deal = new SqliteHelper().rawQuery("select * from addFriend where fqr=?", hybh).get(0).get("deal");
                    if (frTextAgree.getText().toString().equals("添加好友") || BodyAdd.DEAL_DISAGREE.equals(deal)) {
                        Intent intent = new Intent(FriendDetailActivity.this, VerifyActivity.class);
                        intent.putExtra("name", frTextName.getText().toString().trim());
                        intent.putExtra("code", hybh);
                        if (canClick)
                            startActivity(intent);
                    } else {
                        if (BodyAdd.DEAL_NO.equals(deal)) {//通过验证
                            agree(FriendDetailActivity.this);
                        } else {//发送消息
                            EventBus.getDefault().post(new MyEventBus(HintTool.Close_Chat));//接受到消息通知更新
                            Intent intent = new Intent(FriendDetailActivity.this, liaotianjiemian.class);
                            intent.putExtra("friend_id", hybh);
                            intent.putExtra("hyName", frTextName.getText().toString());

                            startActivity(intent);
                        }
                    }
                } catch (Exception e) {
                    Intent intent = new Intent(FriendDetailActivity.this, VerifyActivity.class);
                    intent.putExtra("name", frTextName.getText().toString().trim());
                    intent.putExtra("code", hybh);
                    if (canClick)
                        startActivity(intent);
                }

                break;
            case R.id.fr_text_del:
                HintPopup hintPopup = new HintPopup(FriendDetailActivity.this, frRelParent, HintTool.Del_Friend);
                hintPopup.showPopupWindow(frRelParent);
                break;
        }
    }


    /**
     * 同意添加好友
     */
    private void agree(final Context context) {
        List<Map<String, String>> dataResult = new SqliteHelper().rawQuery("select fqrname from addFriend where fqr = ? and userid = ?", hybh, Constants.number);

        final String fqrname = dataResult == null || dataResult.size() == 0 ? hybh : dataResult.get(0).get("fqrname");


        Map<String, String> param = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", Ticket.getFunctionTicket("20150120")
                        },
                        {
                                "hybh", hybh
                        },
                        {
                                "hyzt", BodyAdd.DEAL_SFTY_YES
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.DC.是否同意添加好友.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)", hybh, fqrname, IsNull.isNotNull(fqrname) ? pinyin.getAllLetter(fqrname).substring(0, 1) : "#", Constants.number);
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("update addFriend set deal=? where fqr=? and userid=?", BodyAdd.DEAL_AGREE, hybh, Constants.number);
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("update addFriendList set deal=? where fqr=? and userid=?", BodyAdd.DEAL_AGREE, hybh, Constants.number);
                    new ToastShow().show(context, HintTool.PASS_VERIFY);
                    frTextDel.setVisibility(View.VISIBLE);
                    frTextAgree.setText("发送消息");
                } else {
                    new ToastShow().show(context, context.getResources().getString(R.string.str_error_data));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                new ToastShow().show(context, context.getResources().getString(R.string.str_error_data));
                canClick = false;
            }
        });
    }


    /**
     * 删除好友
     */
    private void delFr(final Context context, final String hybh) {
        Map<String, String> param = MapTools.buildMap(new String[][]
                {

                        {
                                "hybh", hybh
                        },
                        {
                                "userid", Constants.number
                        },
                        {
                                "function_id", "20150120"
                        },
                        {
                                "ticket", Ticket.getFunctionTicket("20150120")
                        },
                });
        VolleyRequest.RequestPost(_链接地址导航.DC.删除好友.getUrl(), param, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
                    ToastUtils.Toast(context, HintTool.Del_Success);
                    new SqliteHelper().execSQL("delete  from addFriend where fqr=?", hybh);
                    new SqliteHelper().execSQL("delete  from addFriendList where fqr=?", hybh);
                    new SqliteHelper().execSQL("delete  from friend where FRIEND_ID=?", hybh);
                    new SqliteHelper().execSQL("delete  from lt where friend_id=?", hybh);
                    new SqliteHelper().execSQL("delete  from messageList where m_group=?", frTextName.getText().toString());
                    EventBus.getDefault().post(new MyEventBus(HintTool.Close_Chat));
                    finish();
                } else {
                    ToastUtils.Toast(context, HintTool.Del_Fail);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                ToastUtils.Toast(context, HintTool.Del_Fail);
            }
        });
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.Del_Friend)) {//确定删除好友
            delFr(FriendDetailActivity.this, hybh);
        } else if (event.getMsg().equals(HintTool.Receive_Push_Message)) {
            try {
                deal = new SqliteHelper().rawQuery("select * from addFriend where fqr=?", hybh).get(0).get("deal");
                if (BodyAdd.DEAL_NO.equals(deal)) {//等待验证
                    frTextMessage.setVisibility(View.VISIBLE);
                    frTextMessageHint.setVisibility(View.VISIBLE);
                    frTextAgree.setText("通过验证");
                    frTextDel.setVisibility(View.GONE);
                } else if (BodyAdd.DEAL_DISAGREE.equals(deal)) {
                    frTitle.setTitle("好友信息", this);
                    frTextMessage.setVisibility(View.GONE);
                    frTextMessageHint.setVisibility(View.GONE);
                    frTextAgree.setText("等待验证");
                    frTextDel.setVisibility(View.GONE);
                } else {//验证通过
                    frTitle.setTitle("好友信息", this);
                    frTextMessage.setVisibility(View.GONE);
                    frTextMessageHint.setVisibility(View.GONE);
                    frTextAgree.setText("发送消息");
                    frTextDel.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                frTitle.setTitle("好友信息", this);
                frTextMessage.setVisibility(View.GONE);
                frTextMessageHint.setVisibility(View.GONE);
                frTextAgree.setText("添加好友");
                frTextDel.setVisibility(View.GONE);
            }
        }
    }
}
