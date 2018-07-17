package yh.app.tool;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.app3.entity.PersonEntity;
import com.example.app3.tool.JSONTool;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;

import org.androidpn.push.Constants;

import yh.app.utils.GsonImpl;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.RSAApi;
import 云华.智慧校园.工具.RSAEncrypt;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件.MyProgressbar;

/**
 * 包 名:yh.app.tool 类 名:yh.app.tool.AT 功 能:该类主要是负责从网络获取数据,并且存放到sqlite
 *
 * @author 阙海林
 * @version 1.0
 * @date 2015/7/29
 */
public class LoginAT {
    private Handler handler;
    private MyProgressbar progressbar;
    private boolean isShowProgressbar;
    private Context context;

    public LoginAT(Handler handler, Context context, boolean isShowProgressbar) {
        this.context = context;
        this.isShowProgressbar = isShowProgressbar;
        if (isShowProgressbar) {
            progressbar = new MyProgressbar(context, "", "");
            progressbar.show();
        }
        this.handler = handler;
    }

    public void doLoginOther(final String userid, final String appid, final String childSecret) {
        final Map<String, String> params = new HashMap<>();
        params.put("imei", SystemUtil.getIMEI(context));
        params.put("model", SystemUtil.getSystemModel());
        params.put("ip", "127.0.0.1");
        params.put("equipmentSystem", SystemUtil.getSystemVersion());
        params.put("sblx", "android");
        params.put("childSecret", childSecret);
        params.put("appid", appid);
        params.put("userid", userid);

        VolleyRequest.RequestPost(_链接地址导航.UIA.登录2.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {

                    JSONArray jso = new JSONArray(result);
                    if ("true".equals(jso.getJSONObject(0).getString("islogin"))) {
                        new LoginAT(null, null, false)._login(jso);
                        Constants.number = userid;
                        Constants.code = MD5.MD5(childSecret);

                        String a = "";
                        try {
                            String tem = null;
                            // 所有后面更新走名文不走加密
                            tem = RSAApi.getEncryptSecurity(String.format(userid + "<@.%s.@>" + childSecret, new Object[]
                                    {
                                            new Random().nextInt(200000)
                                    }));
                            a = URLEncoder.encode(tem, "utf-8");
                        } catch (

                                Exception e) {
                            // TODO: handle exception
                        }

                        new SqliteHelper().execSQL("delete from userinfo");
                        new SqliteHelper().execSQL("insert or replace into userinfo(userid, password) values(?, ?)", new Object[]
                                {
                                        userid, a
                                });
                        new SqliteHelper().execSQL("delete from userp");
                        new SqliteHelper().execSQL("insert into userp(user, userp) values(?, ?)", new Object[]
                                {
                                        userid, Constants.code
                                });
                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putString("msg", result);
                        msg.setData(data);
                        login.handleMessage(msg);
                    } else {

                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onMyError(VolleyError error) {

            }
        });
    }

    public void doLoginSec(String userid, String password) {
        String a = "";
        try {
            String tem = null;
            // MD5.MD5(password)
            Constants.code = MD5.MD5(password);
            // 所有后面更新走名文不走加密
            if ("重庆工商职业学院".equals(Constants.xxmc)
                    || "重庆三峡学院".equals(Constants.xxmc)
                    || "南宁职业技术学院".equals(Constants.xxmc)
                    || "本地".equals(Constants.xxmc)
                    || "重庆工商大学".equals(Constants.xxmc)
                    || "四川广安职业技术学院".equals(Constants.xxmc)
                    || "重庆工程职业技术学院".equals(Constants.xxmc)
                    || "重庆邮电大学移通学院".equals(Constants.xxmc)
                    ) {
                tem = RSAApi.getEncryptSecurity(String.format(userid + "<@.%s.@>" + password, new Object[]
                        {
                                new Random().nextInt(200000)
                        }));
            } else {
                tem = RSAApi.getEncryptSecurity(String.format(userid + "<@.%s.@>" + MD5.MD5(password), new Object[]
                        {
                                new Random().nextInt(200000)
                        }));
            }

            a = URLEncoder.encode(tem, "utf-8");
        } catch (

                Exception e) {
            // TODO: handle exception
        }

        new SqliteHelper().execSQL("delete from userinfo");
        new SqliteHelper().execSQL("insert or replace into userinfo(userid, password) values(?, ?)", new Object[]
                {
                        userid, a
                });
        Constants.number = userid;
        new SqliteHelper().execSQL("delete from userp");
        new SqliteHelper().execSQL("insert into userp(user, userp) values(?, ?)", new Object[]
                {
                        userid, MD5.MD5(password)
                });

        new ThreadPoolManage().addNewPostTask(_链接地址导航.UIA.登录.getUrl(), MapTools.buildMap(new String[][]
                {
                        {
                                "a", a
                        }
                }), login);
    }

    public void doLoginNotSec(String userid, String a) {
        try {
            Constants.number = userid;
            Constants.code = new SqliteHelper().rawQuery("select userp from userp").get(0).get("userp");
            new ThreadPoolManage().addNewPostTask(_链接地址导航.UIA.登录.getUrl(), MapTools.buildMap(new String[][]
                    {
                            {
                                    "a", a
                            }
                    }), login);
        } catch (Exception e) {
            if (isShowProgressbar)
                progressbar.cancel();
            login.sendEmptyMessage(0);
        }
    }

    public Handler login = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            Message msg2 = new Message();
            msg2.obj = new String[]
                    {
                            "账号或密码错误"
                    };

            try {
                JSONArray jsa = new JSONArray(msg.getData().getString("msg"));

                msg2.obj = JsonTools.getString(jsa.getJSONObject(0), "账号或密码错误", new String[]
                        {
                                "message"
                        });
                if (jsa.getJSONObject(0).getString("usertype").equals("4")) {
                    // 是游客账号
                    if (jsa.getJSONObject(0).getString("islogin").equals("true")) {
                        msg2.what = 2;
                        handler.sendMessage(msg2);
                    } else {
                        msg2.what = 0;
                        handler.sendMessage(msg2);
                    }
                } else {
                    // 非游客账号
                    _login(jsa);
                    if (jsa.getJSONObject(0).getString("islogin").equals("true")) {
                        msg2.what = 1;
                        handler.sendMessage(msg2);
                    } else {
                        msg2.what = 0;
                        handler.sendMessage(msg2);
                    }
                }

                if (isShowProgressbar)
                    progressbar.cancel();

            } catch (Exception e) {
                if (isShowProgressbar)
                    progressbar.cancel();
                msg2.what = 0;
                msg2.obj = new String[]
                        {
                                "账号或密码错误"
                        };
                handler.sendMessage(msg2);
            }
        }

        ;
    };

    public int _login(JSONArray jsa) {
        Object[] array = null;
        try {
            Constants.user_type = jsa.getJSONObject(0).getString("usertype");
            new SqliteHelper().execSQL(String.format("insert into usertype(userid,usertype) values('%s',%s)", Constants.number, jsa.getJSONObject(0).getString("usertype")));
            Constants.user_type = jsa.getJSONObject(0).getString("usertype");
        } catch (Exception e) {
        }
        try {
            String[] zdmc = new String[]
                    {
                            "USERNAME", "DH", "QQ", "TXDZ", "NC", "ZYMC", "XBDM", "BMMC", "BJDM", "SR"
                    };
            String result = "";
            for (int i = 0; i < jsa.getJSONObject(0).getJSONArray("userinfo").length(); i++) {
                String code = jsa.getJSONObject(0).getJSONArray("userinfo").get(i).toString();
                String en = new String(RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile()), Base64.decode(code.getBytes("utf-8"), Base64.DEFAULT)));
                result += en.trim();
            }
            result = URLDecoder.decode(result, "utf-8");
            array = JsonTools.getString(new JSONObject(result), zdmc);
            for (int i = 0; i < array.length; i++) {
                array[i] = unicodeToUtf8(array[i].toString());
            }
        } catch (Exception e) {
            return 0;
        }
        try {
            new SqliteHelper().execSQL("delete from user");
            new SqliteHelper().execSQL("insert into user(userid,name,telphone,qq,faceaddress,nc,zy,sex,bm,bj,birthday) values('" + Constants.number + "',?,?,?,?,?,?,?,?,?,?)", array);
        } catch (Exception e) {
        }

        return 1;
    }

    /**
     * @param theString
     * @return String
     */
    public static String unicodeToUtf8(String theString) {
        char aChar;
        if (theString == null) {
            return "";
        }
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = theString.charAt(x++);
            if (aChar == '\\') {
                aChar = theString.charAt(x++);
                if (aChar == 'u') {
                    // Read the xxxx
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = theString.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't')
                        aChar = 't';
                    else if (aChar == 'r')
                        aChar = 'r';
                    else if (aChar == 'n')
                        aChar = 'n';
                    else if (aChar == 'f')
                        aChar = 'f';
                    outBuffer.append(aChar);
                }
            } else
                outBuffer.append(aChar);
        }
        return outBuffer.toString();
    }
}