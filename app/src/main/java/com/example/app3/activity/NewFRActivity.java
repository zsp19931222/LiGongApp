package com.example.app3.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.base.BaseRecyclerViewActivity;
import com.example.app3.base_recyclear_adapter.HeaderAdapterWrapper;
import com.example.app3.base_recyclear_adapter.QuickAdapter;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.Tool;
import com.example.app3.view.MyTitleView;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyAdd;
import com.tbruyelle.rxpermissions.RxPermissions;
import yh.app.appstart.lg.R;
import com.yunhuakeji.app.utils.MapTools;
import com.zxing.activity.CaptureActivity;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import yh.app.alipay.Base64;
import yh.app.quanzitool.pinyin;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.tool.URLHelper;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/10/11.
 * <p>
 * 新的朋友页面
 */

public class NewFRActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.newfr_title)
    MyTitleView newfrTitle;
    @BindView(R.id.newfr_rec)
    RecyclerView newfrRec;

    private QuickAdapter adapter;

    private List<Map<String, String>> list;
    private HeaderAdapterWrapper headerAdapterWrapper;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_newfr;
    }

    @Override
    protected void setTitle(Context context) {
        newfrTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(final Context context) {
        if (list == null) {
            list = new ArrayList<>();
            list.addAll(new SqliteHelper().rawQuery("select * from addFriendList where userid=? order by deal desc", Constants.number));
        }
        adapter = new QuickAdapter<Map<String, String>>(list) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_newfr;
            }

            @Override
            public void convert(VH holder, final Map<String, String> data, int position) {
                holder.setImageView((Activity) context, R.id.item_newfr_image, data.get("faceaddress"), R.drawable.np, 0);
                holder.setTextView(R.id.item_newfr_text_name, data.get("fqrname"));
                try {
                    holder.setTextView(R.id.item_newfr_text_introduce, URLDecoder.decode(data.get("fjnr"), "utf-8"));
                }catch (Exception e){
                }
                if (BodyAdd.DEAL_NO.equals(data.get("deal"))) {
                    holder.setTextView(R.id.item_newfr_text_state, "接受").setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!Tool.isFastDoubleClick()) {
                                agree(context, data.get("fqr"));
                            }
                        }
                    });
                } else if (BodyAdd.DEAL_DISAGREE.equals(data.get("deal"))) {
                    holder.setTextView(R.id.item_newfr_text_state, "等待验证").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.item_newfr_text_state, "等待验证").setBackground(null);
                } else {
                    holder.setTextView(R.id.item_newfr_text_state, "已接受").setTextColor(ContextCompat.getColor(context, R.color.color_gray_999999));
                    holder.setTextView(R.id.item_newfr_text_state, "已接受").setBackground(null);

                }
                holder.setRelativeLayout(R.id.item_newfr_rel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!Tool.isFastDoubleClick()) {
                            Intent intent = new Intent(context, FriendDetailActivity.class);
                            intent.putExtra("fqr", data.get("fqr"));
                            context.startActivity(intent);
                        }
                    }
                });
                holder.setTextView(R.id.item_newfr_text_del, "删除").setOnClickListener(new View.OnClickListener() {//删除好友
                    @Override
                    public void onClick(View v) {
                        new SqliteHelper().rawQuery("delete from addFriendList where fqr=?", data.get("fqr"));
                        list.clear();
                        list.addAll(new SqliteHelper().rawQuery("select * from addFriendList where userid=? order by deal desc", Constants.number));
                        headerAdapterWrapper.notifyDataSetChanged();
                    }
                });

            }

        };
        headerAdapterWrapper = new HeaderAdapterWrapper(adapter);
        View headView = LayoutInflater.from(context).inflate(R.layout.newfr_head, null, false);
        LinearLayout searchView =  headView.findViewById(R.id.head_address_lin_search);
        LinearLayout sys =  headView.findViewById(R.id.head_address_lin_sys);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转搜索页面
                if (!Tool.isFastDoubleClick()) {
                    Intent intent = new Intent(context, SearchActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转扫描页面
                if (!Tool.isFastDoubleClick()) {
                    new RxPermissions((Activity) context).
                            request(Manifest.permission.CAMERA).
                            subscribe(observer);
                }
            }
        });
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        headView.setLayoutParams(params);
        headerAdapterWrapper.addHeaderView(headView);
        newfrRec.setLayoutManager(new LinearLayoutManager(context));
        newfrRec.setAdapter(headerAdapterWrapper);
    }

    @Override
    protected void init(Context context) {
        List<Map<String, String>> maps = new SqliteHelper().rawQuery("select fqr from hyqqNum");
        for (int i = 0; i < maps.size(); i++) {
            new SqliteHelper().rawQuery("update hyqqNum set isread=? and fqr=? ", "true", maps.get(i).get("fqr"));
        }
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
        if (list != null) {
            list.clear();
            list.addAll(new SqliteHelper().rawQuery("select * from addFriendList where userid=? order by deal desc", Constants.number));
            headerAdapterWrapper.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.Receive_Push_Message)) {
            if (list != null) {
                list.clear();
                list.addAll(new SqliteHelper().rawQuery("select * from addFriendList where userid=? order by deal desc", Constants.number));
                headerAdapterWrapper.notifyDataSetChanged();
            }
        }
    }

    /**
     * 提示权限
     */
    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(NewFRActivity.this, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent3 = new Intent(NewFRActivity.this, CaptureActivity.class);
                startActivityForResult(intent3, 0);
            } else {
                Toast.makeText(NewFRActivity.this, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /**
     * 同意添加好友
     */
    private void agree(final Context context, final String hybh) {
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
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)", new Object[]
                            {
                                    hybh, fqrname, IsNull.isNotNull(fqrname) ? pinyin.getAllLetter(fqrname).substring(0, 1) : "#", Constants.number
                            });
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("update addFriend set deal=? where fqr=? and userid=?", new Object[]
                            {
                                    BodyAdd.DEAL_AGREE, hybh, Constants.number
                            });
                    ExampleApplication.getInstance().getSqliteHelper().execSQL("update addFriendList set deal=? where fqr=? and userid=?", new Object[]
                            {
                                    BodyAdd.DEAL_AGREE, hybh, Constants.number
                            });
                    new ToastShow().show(context, HintTool.PASS_VERIFY);
                    if (list != null) {
                        list.clear();
                    }
                    list.addAll(new SqliteHelper().rawQuery("select * from addFriendList where userid=? order by deal desc", Constants.number));
                    headerAdapterWrapper.notifyDataSetChanged();
                } else {
                    new ToastShow().show(context, context.getResources().getString(R.string.str_error_data));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                new ToastShow().show(context, context.getResources().getString(R.string.str_error_data));
            }
        });
    }


    /**
     * 删除好友
     */
//    private void delFr(final Context context, final String hybh, final String name) {
//        Map<String, String> param = MapTools.buildMap(new String[][]
//                {
//
//                        {
//                                "hybh", hybh
//                        },
//                        {
//                                "userid", Constants.number
//                        },
//                        {
//                                "function_id", "20150120"
//                        },
//                        {
//                                "ticket", Ticket.getFunctionTicket("20150120")
//                        },
//                });
//        BaseVolleyRequest.RequestPost(_链接地址导航.DC.删除好友.getUrl(), param, new VolleyInterface() {
//            @Override
//            public void onMySuccess(String result) {
//                if (JSONTool.SUCCESS.equals(JSONTool.Code(result))) {
//                    ToastUtils.Toast(context, HintTool.Del_Success);
//                    new SqliteHelper().execSQL("delete  from addfriend where fqr=?", hybh);
//                    new SqliteHelper().execSQL("delete  from friend where FRIEND_ID=?", hybh);
//                    new SqliteHelper().execSQL("delete  from messageList where m_group=?", name);
//                    EventBus.getDefault().post(new MyEventBus(HintTool.Close_Chat));
//                    if (list != null) {
//                        list.clear();
//                        list.addAll(new SqliteHelper().rawQuery("select * from addfriend where userid=? order by deal desc", Constants.number));
//                        headerAdapterWrapper.notifyDataSetChanged();
//                    }
//                } else {
//                    ToastUtils.Toast(context, HintTool.Del_Fail);
//                }
//            }
//
//            @Override
//            public void onMyError(VolleyError error) {
//                ToastUtils.Toast(context, HintTool.Del_Fail);
//            }
//        });
//    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // 获取二维码内容
            String url = data.getExtras().getString("result");
            if (IsNull.isNotNull(url)) {
                // 提取二维码中的用户编号
                url = new URLHelper().getParames(url, "userid");
                if (IsNull.isNotNull(url)) {
                    Intent intent = new Intent(this, FriendDetailActivity.class);
                    intent.putExtra("fqr", url);
                    this.startActivity(intent);
                } else {
                }
            }
        } catch (Exception e) {
        }
    }
}
