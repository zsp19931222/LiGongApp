package yh.app.calendar;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;

import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;
import 云华.智慧校园.工具.DateHelper;
import 云华.智慧校园.工具.JsonTools;

@SuppressLint("SimpleDateFormat")
public class CalendarHelper {
    public String getWeekNum(int year, int month, int dayFrom, int dayTo) {
        List<Map<String, String>> term = new SqliteHelper().rawQuery("select starttime,endtime from sysj where userid = ? and ((starttime <= ? and endtime >= ?) or (starttime <= ? and endtime >= ?))",
                Constants.number,
                getStringToDate(year, month, dayFrom),
                getStringToDate(year, month, dayFrom),
                getStringToDate(year, month, dayTo),
                getStringToDate(year, month, dayTo));
        if (term == null || term.size() == 0) {
            return "";
        } else {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(getDateToString(term.get(0).get("starttime")));
            int i1 = c1.get(Calendar.WEEK_OF_YEAR);
            c1.setTime(getDateToString(year, month, dayTo));
            int i2 = c1.get(Calendar.WEEK_OF_YEAR);
            if (i1 == i2)
                return "" + 1;
            return "" + DateHelper.weeksBetween(getDateToString(term.get(0).get("starttime")), getDateToString(year, month, dayFrom));
        }
    }

    private String getStringToDate(int year, int month, int day) {
        return new SimpleDateFormat("yyyy-MM-dd").format(getDateToString(year, month, day));
    }

    private Date getDateToString(int year, int month, int day) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            return new Date();
        }
    }

    private Date getDateToString(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public void dealJJR(JSONArray jsa, SQLiteDatabase db, Handler handler) {
        try {
            for (int i = 0; i < jsa.length(); i++) {
                JSONArray jsaTX = jsa.getJSONObject(i).getJSONArray("txlist");
                for (int j = 0; j < jsaTX.length(); j++) {
                    String[] TXparames = JsonTools.getString(jsaTX.getJSONObject(j), new String[]
                            {
                                    "TXID", "JJRID", "FJRQ", "TXRQ", "MS"
                            });
                    db.execSQL("replace into tx(txid,jjrid,jjrsj,txsj,ms) values(?,?,?,?,?)", TXparames);
                }
                String[] JJRparames = JsonTools.getString(jsa.getJSONObject(i), new String[]
                        {
                                "JJRID", "KSRQ", "JSRQ", "JJRMC", "MS", "LX"
                        });
                db.execSQL("replace into jjr(jjrid,kssj,jssj,jjrmc,ms,jjrlx) values(?,?,?,?,?,?)", JJRparames);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        handler.sendMessage(new Message());
    }
}
