package yh.app.tool;

import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.example.app3.childview.HomeApplyView;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.tool.UserMessageTool;
import com.example.smartclass.eventbus.MessageEvent;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.logTool.Log;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.CodeManage;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class FunctionAT {
    private static Handler handler;

    public FunctionAT(Handler handler) {
        this.handler = handler;
    }

    public void getFunctionList() {
        new ThreadPoolManage().addPostTask(_链接地址导航.UIA.功能列表_老版本.getUrl(), MapTools.buildMap(new String[][]
                {
                        {
                                "userid", UserMessageTool.getUserId()
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + Constants.code)
                        },
                        {
                                "Version", "-1"
                        }
                }), function);
        // new
        // ThreadPoolManage().addNewPostTask("http://192.168.0.141:8081/UIA/function/getlist.action",
        // MapTools.buildMap(new String[][]
        // {
        // {
        // "userid", "20141029"
        // },
        // {
        // "ticket", MD5.MD5(_链接地址导航.addString + "20141029" + MD5.MD5("123456"))
        // },
        // {
        // "Version", getFunctionVersion()
        // }
        // }), function);
    }

    private String getFunctionVersion() {
        // try
        // {
        // return new SqliteHelper().rawQuery("select version from
        // function_version").get(0).get("version");
        // } catch (Exception e)
        // {
        // }
        return "0";
    }

    private static Handler function = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            try {
                JSONObject array = new JSONObject(msg.getData().getString("msg"));
                setResult(array);
                new Term().doit();
                // home.MU.fresh();
            } catch (Exception e) {
            }
            if (handler != null) {
                handler.sendEmptyMessage(0);
            }
        }

        ;
    };

    public static void setResult(JSONObject array) {
        try {
            List<Map<String, String>> functionList = new ArrayList<Map<String, String>>();
            for (int i = 0; i < array.length(); i++) {
                Map<String, String> function = new HashMap<String, String>();
                if (getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != null && getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID") != "") {
                    function.put("function_id", getFunctionDate(array, "functionrow_" + i, "FUNCTION_ID"));
                    function.put("function_name", getFunctionDate(array, "functionrow_" + i, "FUNCTION_NAME"));
                    function.put("function_type", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYPE"));
                    function.put("class_name", getFunctionDate(array, "functionrow_" + i, "CLASS_NAME"));
                    function.put("package_name", getFunctionDate(array, "functionrow_" + i, "PACKAGE_NAME"));
                    function.put("integerate_key", new JSONArray(getFunctionDate(array, "functionrow_" + i, "INTEGRATE_KEY")).get(0).toString());
                    function.put("function_face", getFunctionDate(array, "functionrow_" + i, "FUNCTION_FACE"));
                    function.put("px", getFunctionDate(array, "functionrow_" + i, "PX"));
                    function.put("function_tybj", getFunctionDate(array, "functionrow_" + i, "FUNCTION_TYBJ"));
                    functionList.add(function);
                }
            }
            new SqliteHelper().execSQL("delete from button");
            for (int i = 0; i < functionList.size(); i++) {
                try {

                    new SqliteHelper().execSQL("insert or replace into button(userid,FunctionID , name ,type ,cls ,pkg , key  ,face ,px ,function_tybj ) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
                            {
                                    Constants.number, functionList.get(i).get("function_id"), functionList.get(i).get("function_name"), functionList.get(i).get("function_type"), functionList.get(i).get("class_name"), functionList.get(i).get("package_name"), functionList.get(i).get("integerate_key"), functionList.get(i).get("function_face"), Integer.valueOf(functionList.get(i).get("px")), functionList.get(i).get("function_tybj")
                            });
                    new SqliteHelper().execSQL("insert or replace into function_version(version) values(?)", new Object[]
                            {
                                    array.getString("VERSION_NUM")
                            });
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getFunctionDate(JSONObject JSobject, String rownum, String ziduan) {
        String str = "";
        try {
            str = JSobject.getJSONObject(rownum).getString(ziduan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static void getAllFunction(final RequestStateLisener lisener) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", UserMessageTool.getUserId()
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + Constants.code)
                        },
                        {
                                "Version", "-1"
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.功能列表.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    lisener.getRequestState(true);
                } catch (Exception e) {
                    lisener.getRequestState(false);
                }

            }

            @Override
            public void onMyError(VolleyError error) {
                lisener.getRequestState(false);
            }
        });
    }

    /**
     * v3版本获取所有功能
     *
     * @param lisener
     */
    public static void getAllFunction_v3(final RequestStateLisener lisener) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", UserMessageTool.getUserId()
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + Constants.code)
                        },
                        {
                                "Version", "-1"
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.功能列表_v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.ReLogin)){
                    EventBus.getDefault().post(new MessageEvent(HintTool.ReLogin, HintTool.ReLogin));
                }else {
                    boolean RequestState = false;
                    SQLiteDatabase database = new SqliteHelper().getWrite();

                    try {
                        database.execSQL("delete from new_apply_function where userid=?", new Object[]
                                {
                                        UserMessageTool.getUserId()
                                });
                        JSONObject jso = new JSONObject(result);
                        if (CodeManage.NET_SUCCESS.equals(jso.getString("code"))) {
                            JSONArray jsonArray = jso.getJSONObject("content").getJSONArray("function_list");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                database.execSQL("replace into new_apply_function(" + "userid," + "function_id," + "function_name," + "function_cls," + "function_pkg," + "function_icon," + "function_px," + "function_type," + "function_key," + "function_group_id," + "function_group_name," + "function_display_homepage)" + "values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
                                        {
                                                UserMessageTool.getUserId(), item.getString("FUNCTION_ID"), item.getString("FUNCTION_NAME"), item.getString("CLASS_NAME"), item.getString("PACKAGE_NAME"), item.getString("FUNCTION_FACE"), item.getString("PX"), item.getString("FUNCTION_TYPE"), item.getString("INTEGRATE_KEY"), item.getString("TYPE_ID"), item.getString("TYPE_NAME"), HomeApplyView.FUNCTION_DISPLAY_HOMEPAGE_NO
                                        });
                            }
                            RequestState = true;
                        } else {
                            RequestState = false;
                        }
                    } catch (Exception e) {
                        RequestState = false;
                    } finally {
                        try {
                            database.close();
                        } catch (Exception e2) {
                            // TODO: handle exception
                        }
                    }
                    if (lisener != null) {
                        lisener.getRequestState(RequestState);
                    }
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (lisener != null) {
                    lisener.getRequestState(false);
                }
            }
        });
    }

    /**
     * v3版本获取常用功能
     *
     * @param lisener
     */
    public static void getUserCYYYFunction(final RequestStateLisener lisener) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", Constants.number
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code)
                        },
                        {
                                "Version", "-1"
                        }
                });
        VolleyRequest.RequestPost(_链接地址导航.UIA.常用功能列表_v3.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                if (JSONTool.Code(result).equals(JSONTool.ReLogin)) {
                    EventBus.getDefault().post(new MessageEvent(HintTool.ReLogin, HintTool.ReLogin));
                } else {
                    boolean RequestState = false;
                    SQLiteDatabase database = new SqliteHelper().getWrite();
                    try {
                        JSONObject jso = new JSONObject(result);
                        if (CodeManage.NET_SUCCESS.equals(jso.getString("code"))) {
                            JSONArray jsonArray = jso.getJSONArray("content");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                database.execSQL("update new_apply_function set function_display_homepage=?,function_display_homepage_px=? where function_id=?", new Object[]
                                        {
                                                HomeApplyView.FUNCTION_DISPLAY_HOMEPAGE_YES, item.getString("PX"), item.getString("FUNCTION_ID")
                                        });
                            }
                            RequestState = true;
                        } else {
                            RequestState = false;
                        }
                    } catch (Exception e) {
                        RequestState = false;
                    } finally {
                        try {
                            database.close();
                        } catch (Exception e2) {
                        }
                    }
                    if (lisener != null) {
                        lisener.getRequestState(RequestState);
                    }
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                lisener.getRequestState(false);
            }
        });
    }

    /**
     * 数据类型:功能
     */
    private static final String PUSH_FUNCTION_TYPE_FUNCTION = "function";
    /**
     * 数据类型:分组
     */
    private static final String PUSH_FUNCTION_TYPE_GROUP = "group";

    public static void getPushFunctionList(final RequestStateLisener lisener) {
        Map<String, String> params = MapTools.buildMap(new String[][]
                {
                        {
                                "userid", UserMessageTool.getUserId()
                        },
                        {
                                "ticket", MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + UserMessageTool.getPassWordUnencrypted())
                        },
                        {
                                "Version", "-1"
                        }
                });
//        VolleyRequest.RequestPost(_链接地址导航.UIA.推送功能列表.getUrl(), params, new VolleyInterface() {
        VolleyRequest.RequestPost(GetAppUrl.Show.functionList.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                boolean dealResult = false;
                SQLiteDatabase database = new SqliteHelper().getWrite();
                try {
                    // 数据转换为json
                    JSONObject JsonResult = new JSONObject(result);
                    if (CodeManage.NET_SUCCESS.equals(JsonResult.getString("code"))) {
                        JSONArray pushList = JsonResult.getJSONArray("content");
                        for (int i = 0; i < pushList.length(); i++) {
                            JSONObject item1 = pushList.getJSONObject(i);
                            // 如果子项为功能
                            if (PUSH_FUNCTION_TYPE_FUNCTION.equals(item1.getString("bjzd"))) {
                                database.execSQL("replace into new_push_function(userid,ts_id,ts_name,ts_group,ts_callback,ts_icon,ts_state) values(?,?,?,?,?,?,?)",
                                        new Object[]{
                                                Constants.number,
                                                item1.getString("ts_id"),
                                                item1.getString("ts_name"),
                                                item1.getString("ts_group"),
                                                "",
                                                item1.getString("ts_icon"),
                                                item1.getString("state")
                                        });
                            }
                            // 如果子项为分组
                            else if (PUSH_FUNCTION_TYPE_GROUP.equals(item1.getString("bjzd"))) {
                                database.execSQL("replace into new_push_group(userid,ts_group_id,ts_group_name,ts_group_icon) values(?,?,?,?)",
                                        new Object[]{
                                                Constants.number,
                                                item1.getString("ts_group_id"),
                                                item1.getString("ts_group_name"),
                                                item1.getString("ts_group_icon"),
                                        });
                                JSONArray list2 = item1.getJSONArray("list");
                                for (int j = 0; j < list2.length(); j++) {
                                    JSONObject item2 = list2.getJSONObject(j);
                                    database.execSQL("replace into new_push_function(userid,ts_id,ts_name,ts_group,ts_callback,ts_icon,ts_state) values(?,?,?,?,?,?,?)",
                                            new Object[]{
                                                    Constants.number,
                                                    item2.getString("ts_id"),
                                                    item2.getString("ts_name"),
                                                    item2.getString("ts_group"),
                                                    "",
                                                    item2.getString("ts_icon"),
                                                    item2.getString("state")
                                            });
                                }
                            }
                        }
                    }
                    List<Map<String, String>> list = new SqliteHelper().rawQuery("select * from new_push_function");
                    Log.e(list.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (database != null)
                        database.close();
                }
                if (lisener != null) {
                    lisener.getRequestState(dealResult);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                if (lisener != null) {
                    lisener.getRequestState(false);
                }
            }
        });
    }

    public interface RequestStateLisener {
        void getRequestState(boolean isSuccess);
    }
}