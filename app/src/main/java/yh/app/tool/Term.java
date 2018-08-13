package yh.app.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.example.app4.api.OkHttpUtil;
import com.example.app4.tool.UserMessageTool;
import com.example.app4.util.IsNull;
import com.example.app4.util.IsNullUtil;

import android.annotation.SuppressLint;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.androidpn.push.Constants;

import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 包 名:yh.app.tool 类 名:yh.app.tool.Term.java 功 能:获取时间信息 1.action=0所有时间信息
 * 2.action=1现在时间信息
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-9-8
 */
public class Term {
    private static String function_id = "20150101";
    public static boolean refresh = true;

    public Term() {
    }

    public void doit() {
        myAt1 at = new myAt1();
        at.executeOnExecutor(Executors.newCachedThreadPool());
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            nowMyAt2 at = new nowMyAt2();
            at.executeOnExecutor(Executors.newCachedThreadPool());
        }
    };

    @SuppressLint("HandlerLeak")
    private static Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                new ThreadPoolManage().addNewPostTask(_链接地址导航.DC.所有时间.getUrl(), MapTools.buildMap(new String[][]
                        {
                                {
                                        "userid", Constants.number
                                },
                                {
                                        "ticket", Ticket.getFunctionTicket("20150101")
                                },
                                {
                                        "function_id", "20150101"
                                }
                        }), handler3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private static Handler handler3 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                String temp = msg.getData().getString("msg").replace("\\", "");
                String s = temp.substring(1, temp.length() - 1);
                JSONArray jsa = new JSONArray(s);
                SqliteHelper helper = new SqliteHelper();
                for (int i = 0; i < jsa.length(); i++) {
                    helper.execSQL("replace into sysj(xn ,xq ,starttime ,endtime ,userid ) values(?,?,?,?,?)", jsa.getJSONObject(i).getString("YEAR"), jsa.getJSONObject(i).getInt("TERM") + "", jsa.getJSONObject(i).getString("STARTTIME"), jsa.getJSONObject(i).getString("ENDTIME"), Constants.number);
                }
                List<Map<String, String>> term = new SqliteHelper().rawQuery("select xn,xq from nowterm");
                if (term != null && term.size() > 0) {
                    List<Map<String, String>> kb = new SqliteHelper().rawQuery("select * from KC where XH='" + Constants.number + "' and XN='" + term.get(0).get("xn") + "' and XQ='" + term.get(0).get("xq") + "'");
                    if (kb == null || kb.size() == 0) {
                        KBAT mTask = new KBAT(Constants.number, term.get(0).get("xn"), Integer.valueOf(term.get(0).get("xq")), null, null);
                        mTask.execute();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    class myAt1 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                String s = getNowterm();
                return s;
            } catch (Exception e) {
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            Message msg = new Message();
            if (result == null || result.equals("") || result.equals("[]") || result.equals("{}")) {
                msg.what = 0;
            } else {
                saveNowTerm(result);
                msg.what = 1;
            }
            handler.sendMessage(msg);
        }

    }

    private static class nowMyAt2 extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                return getTerm();
            } catch (Exception e) {
                return "";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                result = result.replace("\\", "");
                result = result.substring(1, result.length() - 1);
                addTerm(result);
                handler2.sendMessage(new Message());
            } catch (Exception e) {

            }
        }

    }

    static String ticket;
    static String url;
    static String term = "";

    public static String getTerm() {

        ticket = Ticket.getFunctionTicket(function_id);
        Map<String, String> parames = new HashMap<>();
        parames.put("userid", Constants.number);
        parames.put("ticket", ticket);
        parames.put("function_id", function_id);
        VolleyRequest.RequestPost(_链接地址导航.DC.时间.getUrl(), parames, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                term = result;
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });


//		HttpClient hc = null;
//		try
//		{
//			List<NameValuePair> parames = new ArrayList<NameValuePair>();
//			parames.add(new BasicNameValuePair("userid", Constants.number));
//			parames.add(new BasicNameValuePair("ticket", ticket));
//			parames.add(new BasicNameValuePair("function_id", function_id));
//			// if (action == 0)
//			 url = _链接地址导航.DC.时间.getUrl();
//			// url = Constants.termurl;
//			// else if (action == 1)
//			// url = Constants.nowterm;
//			HttpParams httpParameters = new BasicHttpParams();
//			hc = new DefaultHttpClient(httpParameters);
//			HttpPost hp = new HttpPost(url);
//			hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
//			// 设置连接超时
//			HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
//			// 设置响应超时
//			HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
//			HttpResponse hr = hc.execute(hp);
//			if (hr.getStatusLine().getStatusCode() == 200)
//			{
//				result = EntityUtils.toString(hr.getEntity());
//			}
//			hc.getConnectionManager().shutdown();
//		} catch (Exception e)
//		{
//			return null;
//		}
        return term;
    }

    public String getNowterm() {
        String result = "";
        HttpClient hc = null;
        try {
            List<NameValuePair> parames = new ArrayList<NameValuePair>();
            String url = "";
            parames.add(new BasicNameValuePair("userid", Constants.number));
            parames.add(new BasicNameValuePair("ticket", Ticket.getFunctionTicket(function_id)));
            parames.add(new BasicNameValuePair("function_id", function_id));
//                 url = Constants.nowterm;
            url = _链接地址导航.DC.当前时间.getUrl();
            HttpParams httpParameters = new BasicHttpParams();
            hc = new DefaultHttpClient(httpParameters);
            HttpPost hp = new HttpPost(url);
            hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
            // 设置连接超时
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10 * 1000);
            // 设置响应超时
            HttpConnectionParams.setSoTimeout(httpParameters, 10 * 1000);
            HttpResponse hr = hc.execute(hp);
            if (hr.getStatusLine().getStatusCode() == 200) {
                result = EntityUtils.toString(hr.getEntity());
                Log.d("nowterm", "getNowterm: " + result);
            } else {
                getTerm();
            }
            hc.getConnectionManager().shutdown();
        } catch (Exception e) {

        }
        return result;

    }

    public void saveNowTerm(String result) {
        SQLiteDatabase db = null;
        try {
            db = new SqliteHelper().getWrite();
            if (IsNullUtil.isNotNull(result)) {
                result = result.replace("\\", "");
                result = result.substring(1, result.length() - 1);
                JSONArray mJSArray = null;
                try {
                    mJSArray = new JSONArray(result);
                } catch (JSONException e) {
                }
                db.execSQL("delete from nowterm");
                for (int i = 0; i < mJSArray.length(); i++) {
                    db.execSQL("replace into nowterm values(" + getintDate(mJSArray, i, "TERM") + ",?,?,?)", new String[]
                            {
                                    getDate(mJSArray, i, "YEAR"), getDate(mJSArray, i, "STARTTIME"), getDate(mJSArray, i, "ENDTIME")
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                new SqliteDBCLose(db, null).close();
            } catch (SQLException e) {
            } catch (Exception e) {
            }
        }
    }

    public static void addTerm(String data) {
        SQLiteDatabase db = null;
        try {
            db = new SqliteHelper().getWrite();
            JSONArray mJSArray;
            try {
                mJSArray = new JSONArray(data);
            } catch (JSONException e) {

                return;
            }

            db.execSQL("delete from term");
            for (int i = 0; i < mJSArray.length(); i++) {
                db.execSQL("replace into term values(?,?,?,?,?)", new String[]
                        {
                                getDate(mJSArray, i, "XN"), getDate(mJSArray, i, "XQ"), getDate(mJSArray, i, "STARTTIME"), getDate(mJSArray, i, "ENDTIME"), Constants.number
                        });
            }
        } catch (Exception e) {

        } finally {
            try {
                new SqliteDBCLose(db, null).close();
            } catch (SQLException e) {
            } catch (Exception e) {
            }
        }
    }

    public int getintDate(JSONArray jsa, int i, String str) {
        JSONObject j = null;
        try {
            j = jsa.getJSONObject(i);
        } catch (JSONException e) {

        }
        int s = 0;
        try {
            s = j.getInt(str);
        } catch (JSONException e) {

        }
        return s;
    }

    private static String getDate(JSONArray jsa, int i, String str) {
        JSONObject j = null;
        try {
            j = jsa.getJSONObject(i);
        } catch (JSONException e1) {
        }
        String s = null;
        try {
            s = j.getString(str);
        } catch (JSONException e) {

        }
        return s;
    }
}
