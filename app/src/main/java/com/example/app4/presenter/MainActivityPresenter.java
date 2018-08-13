package com.example.app4.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.entity.PersonEntity;
import com.example.app3.entity.TXLEntity;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.tool.Tool;
import com.example.app4.activity.UpDateActivity;
import com.example.app4.entity.MainWidgetEntity;
import com.example.app4.tool.SqliteSaveTool;
import com.example.app4.util.FunctionIntentUtil;
import com.example.app4.util.MapUtil;
import com.example.jpushdemo.ExampleApplication;
import com.example.jpushdemo.body.BodyAdd;
import com.example.jpushdemo.helper.Receiver;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import yh.app.quanzitool.pinyin;
import yh.app.tool.FunctionAT;
import yh.app.tool.GetConfig;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Term;
import yh.app.tool.Ticket;
import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.RSAHelper;
import 云华.智慧校园.工具._链接地址导航;

public class MainActivityPresenter {
    private Context context;
    private Map<String, String> parameterMap;

    public MainActivityPresenter(Context context) {
        this.context = context;
//        new Term().doit();
        initContants();
        FunctionAT.getPushFunctionList(null);
        new FunctionAT(new Handler()).getFunctionList();
//        getGrzxUrl();
        parameterMap = MapUtil.getMap(parameterMap);
        update();
//        FunctionAT.getAllFunction_v3(new FunctionAT.RequestStateLisener() {
//            @Override
//            public void getRequestState(boolean isSuccess) {
//                if (isSuccess) {
//                    FunctionAT.getUserCYYYFunction(new FunctionAT.RequestStateLisener() {
//                        @Override
//                        public void getRequestState(boolean isSuccess) {
//                            if (isSuccess) {
//                                EventBus.getDefault().post(new MessageEvent(TagUtil.HomePageRefreshTag, ""));
//                            } else {
//                                EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
//                            }
//                        }
//                    });
//                } else {
//                    EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.REQUESTFAIL));
//                }
//            }
//        });
    }

    /**
     * 获取个人中心URL
     */
    private List<PersonEntity.ContentBean> content;

    private void getGrzxUrl() {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        }

                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.获取个人中心URL.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {
                    PersonEntity entity = GsonImpl.get().toObject(result, PersonEntity.class);
                    content = entity.getContent();
                    for (int i = 0; i < content.size(); i++) {
                        if ("1".equals(content.get(i).getNATIVE_ACCESS())) {//有链接
                            new SqliteHelper().execSQL("insert or replace into getGrzxUrl(name, url) values(?, ?)", content.get(i).getFUNCTION_GROUP_NAME(), content.get(i).getPAGE_ADDRESS());
                        }
                    }
                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    /**
     * 初始化通讯录数据
     */
    private void initContants() {
        // TODO Auto-generated method stub
        Map<String, String> params = MapTools.buildMap(new String[][]{{"userid", Constants.number},
                {"function_id", "20150120"}, {"ticket", Ticket.getFunctionTicket("20150120")}});
        VolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    if (IsNull.isNotNull(result)) {
                        TXLEntity entity = GsonImpl.get().toObject(result, TXLEntity.class);
                        if (JSONTool.SUCCESS.equals(entity.getCode())) {
                            for (int i = 0; i < entity.getContent().size(); i++) {
                                new SqliteHelper().execSQL("insert or replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)",
                                        entity.getContent().get(i).getHYBH(),
                                        entity.getContent().get(i).getHYBZ(),
                                        IsNull.isNotNull(entity.getContent().get(i).getHYBZ()) ? pinyin
                                                .getAllLetter(entity.getContent().get(i).getHYBZ())
                                                .substring(0, 1) : "#",
                                        Constants.number);
                                new SqliteHelper().execSQL("insert into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread,m_deal) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", entity.getContent().get(i).getHYBH(), Constants.number, "", entity.getContent().get(i).getHYBH(), entity.getContent().get(i).getHYBZ(), Constants.number, "", new SqliteHelper().rawQuery("select * from user").get(0).get("name"), TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), "", BodyAdd.DEAL_AGREE, "true", "true");
                            }
                        }
                    }
//					categoryContactsViewUI();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

    /**
     * 获取离线消息
     */
    public void getOfflineMessage() {
        // 消息选项
        VolleyRequest.RequestPost(_链接地址导航.PUSH.离线接口.getUrl(), Receiver.getCallBackParames(Constants.jpushID, ""), new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    JSONObject jso = new JSONObject(result);
                    if (Constants.NETWORK_REQUEST_SUCCESS.equals(jso.getString("code"))) {
                        SqliteSaveTool.saveOffLineData(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    /**
     * 控件跳转
     */
    public void intentWidget(final Context context, final MainWidgetEntity.ContentBean lxBean) {
        new FunctionIntentUtil<>(lxBean, context).intent2();
    }

    /**
     * APP更新
     */
    private void update() {
        VolleyRequest.RequestPost(_链接地址导航.UIA.更新.getUrl() + new GetConfig(context).getVersion(), new HashMap<String, String>(), new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                try {
                    result = result.replace("\\", "");
                    result = result.substring(1, result.length() - 1);
                    final JSONObject jso = new JSONObject(result);
                    if (jso.has("update") && jso.getBoolean("update")) {
                        Activity activity = (Activity) context;
                        Intent intent = new Intent(context, UpDateActivity.class);
                        intent.putExtra("updateURL", jso.getString("url"));
                        activity.startActivity(intent);
                        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                    }
                } catch (Exception ignored) {

                }
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    private static final String TAG = "MainActivityPresenter";
}
