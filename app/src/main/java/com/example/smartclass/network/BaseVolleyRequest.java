package com.example.smartclass.network;

import com.android.volley.VolleyError;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.smartclass.activity.SCMainActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.AuthenticationUtil;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;

import java.util.Map;

import yh.app.tool.MD5;
import yh.app.tool.Ticket;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/1/18 0018.
 */

public class BaseVolleyRequest {
    public void VolleyRequest(Map<String, String> map, String Url, final String Tag) {
        map.put("userid", Constants.number);
        map.put("function_id", SCMainActivity.function_id);
        map.put("ticket", Ticket.getFunctionTicket(SCMainActivity.function_id));
        VolleyRequest.RequestPost(Url, map, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
//                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {//访问成功
//                    EventBus.getDefault().post(new MessageEvent(Tag, result));
//                } else if (JSONTool.Code(result).equals(JSONTool.NoData)) {
//                    EventBus.getDefault().post(new MessageEvent(Tag, HintTool.NoData));
//                } else {

//                    EventBus.getDefault().post(new MessageEvent(Tag, HintTool.REQUESTFAIL));
//                }
                if (JSONTool.Code(result).equals(JSONTool.SUCCESS)) {//访问成功
                    EventBus.getDefault().post(new MessageEvent(Tag, result));
                } else {
                    EventBus.getDefault().post(new MessageEvent(Tag, HintTool.REQUESTFAIL));
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                EventBus.getDefault().post(new MessageEvent(Tag, HintTool.NetWorkError));
            }
        });
    }

}
