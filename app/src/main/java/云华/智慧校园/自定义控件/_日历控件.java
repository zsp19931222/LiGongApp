package 云华.智慧校园.自定义控件;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yh.app.calendar.CalendarDate;

import com.yhkj.cqgyxy.R;

import yh.app.calendar.CalendarHelper;
import yh.app.calendar.Canlendar;

import org.androidpn.push.Constants;

import yh.app.mydiary.edit_diary;
import yh.app.tool.DateString;
import yh.app.tool.SqliteHelper;
import yh.app.tool.weekToDate;
import 云华.智慧校园.工具._农历;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class _日历控件 implements OnClickListener {
    private String[] weekStrings = DateFormatSymbols.getInstance(Locale.CHINA).getWeekdays();

    private OnClickListener listener;

    private LinearLayout calendarLayout;

    private LinearLayout logLayout;
    private Context context;
    private int month;
    private int year;
    private int dayTotalNum;
    private int FrontTo = 0;
    private TextView oldView;
    private CalendarDate calendarDate;
    private SQLiteDatabase db;

    public _日历控件(Context context, LinearLayout calendarLayout, LinearLayout logLayout, CalendarDate calendarDate, SQLiteDatabase db) {
        this.db = db;
        this.calendarLayout = calendarLayout;
        this.logLayout = logLayout;
        this.context = context;
        this.calendarDate = calendarDate;
        logLayout.removeAllViews();
        listener = setOnClickListener();
        init();
    }

    private void init() {
        for (int i = 0; i < calendarLayout.getChildCount(); i++) {
            calendarLayout.getChildAt(i).setVisibility(View.VISIBLE);
            LinearLayout week = (LinearLayout) calendarLayout.getChildAt(i).findViewById(R.id.layout);
            for (int j = 0; j < week.getChildCount() - 2; j++) {
                week.getChildAt(j + 2).setVisibility(View.VISIBLE);
                LinearLayout day = (LinearLayout) (week).getChildAt(j + 2);
                for (int k = 0; k < day.getChildCount(); k++) {
                    day.findViewById(R.id.day_num_layout).setBackgroundResource(R.drawable.touming);
                    day.findViewById(R.id.day_num).setBackgroundResource(R.drawable.touming);
                    ((TextView) day.findViewById(R.id.day_num)).setTextColor(0xff000000);
                    day.findViewById(R.id.day_num).setVisibility(View.INVISIBLE);
                    day.findViewById(R.id.day_num_layout).setVisibility(View.INVISIBLE);
                    day.findViewById(R.id.holiday).setVisibility(View.INVISIBLE);
                    ((TextView) day.findViewById(R.id.holiday)).setText("");
                    day.findViewById(R.id.wdtsbj).setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    /**
     * 获取日历每一行日期
     *
     * @return
     */
    public int[] getFromAndTo() {
        if (FrontTo == 0) {
            FrontTo = 7 - getFirstDayOfWeek(year, month) + 2;
            if (FrontTo > 7) {
                FrontTo = 1;
            } else {
                FrontTo = 7 - getFirstDayOfWeek(year, month) + 2;
            }
            return new int[]
                    {
                            1, FrontTo
                    };
        } else {

            int[] arr = new int[]
                    {
                            FrontTo + 1, FrontTo + 7
                    };
            FrontTo = FrontTo + 7;
            return arr;
        }
    }

    public int getFirstDayOfWeek(int year, int month) {
        return DateString.getDayOfMonth(year, month, 1);
    }

    public void getDayOfMonth() {
        dayTotalNum = DateString.getDayOfMonth(year, month);
    }

    public void buildCalendar(int year, int month) {
        init(year, month);
        getDayOfMonth();
        for (int i = 0; i < 6; i++) {
            int[] fromAndTo = getFromAndTo();
            calendarLayout.getChildAt(i).setVisibility(View.VISIBLE);
            buildCalendarWeek(calendarLayout.getChildAt(i), fromAndTo[0], fromAndTo[1]);
        }
    }

    private void init(int year, int month) {
        this.month = month;
        this.year = year;
        calendarDate.click_year = year;
        calendarDate.click_month = month;
    }

    private boolean isCurrent = false;

    private String getWeekNumber(int year, int month, int dayFrom, int dayTo) {
        Log.d("zso",".........."+new CalendarHelper().getWeekNum(year, month, dayFrom, dayTo));
        return new CalendarHelper().getWeekNum(year, month, dayFrom, dayTo);
    }

    public void buildCalendarWeek(View view, int from, int to) {
        isCurrent = false;
        int startDay = 0;
        int num = 0;
        if (to - from != 6) {
            startDay = 7 - to;
        }
//		if (from != to)
//			((TextView) ((LinearLayout) view.findViewById(R.id.layout)).findViewById(R.id.weekNum)).setText(getWeekNumber(year, month, from + 1, to));
//		else
        if (from!=to) {
            ((TextView) ((LinearLayout) view.findViewById(R.id.layout)).findViewById(R.id.weekNum)).setText(getWeekNumber(year, month, from + 1, to));
        }else {
            ((TextView) ((LinearLayout) view.findViewById(R.id.layout)).findViewById(R.id.weekNum)).setText(getWeekNumber(year, month, from, to));
        }
        for (int i = startDay; i < 7; i++) {
            if (from <= dayTotalNum) {
                buildCalendarDay(((LinearLayout) view.findViewById(R.id.layout)).getChildAt(i + 2), from + num);
                num++;
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public void buildCalendarDay(View view, int dayNum) {
        Cursor result = null;
        try {
            result = db.rawQuery("select date from myDiary where userid=? and date like ?", new String[]
                    {
                            Constants.number, new SimpleDateFormat("yyyy-M-d").format(new SimpleDateFormat("yyyy-M-d").parse(year + "-" + month + "-" + dayNum)) + " %"
                    });
        } catch (ParseException e1) {
        }
        try {
            if (dayNum <= dayTotalNum) {
                ((TextView) view.findViewById(R.id.day_num)).setText("" + dayNum);
                view.setVisibility(View.VISIBLE);
                view.findViewById(R.id.day_num).setVisibility(View.VISIBLE);
                view.findViewById(R.id.day_num_layout).setVisibility(View.VISIBLE);
                if (dayNum == 1) {
                    oldView = (TextView) view.findViewById(R.id.day_num);
                    view.findViewById(R.id.day_num).setBackgroundResource(R.drawable.round);
                    ((TextView) view.findViewById(R.id.day_num)).setTextColor(0xFFFFFFFF);
                    showDiary(dayNum);
                    calendarDate.click_day = dayNum;
                } else if (year == DateString.getYear(new Date()) && month == DateString.getMonth(new Date()) && dayNum == DateString.getDay(new Date())) {
                    view.findViewById(R.id.day_num_layout).setBackgroundResource(R.drawable.rili_noterm);
                    calendarDate.click_day = dayNum;
                    showDiary(dayNum);
                    oldView.setBackgroundResource(R.drawable.touming);
                    oldView.setTextColor(0xff000000);
                }
                if (result == null || result.getCount() == 0) {
                    view.findViewById(R.id.wdtsbj).setVisibility(View.INVISIBLE);
                } else {
                    view.findViewById(R.id.wdtsbj).setVisibility(View.VISIBLE);
                }

                try {
                    view.findViewById(R.id.holiday).setVisibility(View.VISIBLE);
                    set农历(view, dayNum);
                } catch (Exception e) {
                }
                view.setTag(dayNum);
                view.setOnClickListener(this);

            } else
                view.setVisibility(View.INVISIBLE);
        } catch (Exception e) {

        }
    }

    private void set农历(View view, int dayNum) {
        TextView holiday = (TextView) view.findViewById(R.id.holiday);
        String time = addZreo(year) + "-" + addZreo(month) + "-" + addZreo(dayNum);
        List<Map<String, String>> jjr = new SqliteHelper().rawQuery(db.rawQuery("select kssj,jssj,jjrmc,ms,jjrlx from jjr as a where ? >= a.kssj and ? <= a.jssj union select ?,?,(case  when ?=starttime then '开学' when ?=endtime and xq =2  then '暑假'when ?=endtime and xq =1  then '寒假' end),'','' from sysj where starttime=? or endtime=?", new String[]
                {
                        time, time, time, time, time, time, time, time, time
                }));
        if (jjr == null || jjr.size() == 0) {
            try {
                Calendar c = Calendar.getInstance(Locale.CHINA);
                c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + dayNum));
                holiday.setHint(new _农历(c).toString());
            } catch (ParseException e) {
            }
        } else {
            // view.setBackgroundResource(R.drawable.biankuang_buttom_red);
            if (jjr.get(0).get("kssj").equals(addZreo(year) + "-" + addZreo(month) + "-" + addZreo(dayNum)) || dayNum == 1)
                holiday.setText(jjr.get(0).get("jjrmc"));
            else {
                try {
                    Calendar c = Calendar.getInstance(Locale.CHINA);
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + dayNum));
                    holiday.setText(new _农历(c).toString());
                } catch (Exception e) {
                }
            }
            holiday.setTextColor(context.getResources().getColor(R.color.redColor));
        }
    }

    private String addZreo(int i) {
        if (i < 10)
            return "0" + i;
        else
            return "" + i;
    }

    public OnClickListener setOnClickListener() {
        return new OnClickListener() {

            @Override
            public void setOnClickListener(View view) {
                if (oldView != null) {
                    oldView.setTextColor(0xFF000000);
                    oldView.setBackgroundResource(R.drawable.touming);
                }
                calendarDate.click_day = (Integer) view.getTag();
                showDiary((Integer) view.getTag());
                oldView = (TextView) view.findViewById(R.id.day_num);
                view.findViewById(R.id.day_num).setBackgroundResource(R.drawable.round);
                ((TextView) view.findViewById(R.id.day_num)).setTextColor(0xFFFFFFFF);
                List<Map<String, String>> list = new SqliteHelper().rawQuery("select ms from jjr where ?>=kssj and ?<=jssj", new String[]
                        {
                                DateString.getDateString(year, month - 1, Integer.valueOf(((TextView) view.findViewById(R.id.day_num)).getText().toString())), DateString.getDateString(year, month - 1, Integer.valueOf(((TextView) view.findViewById(R.id.day_num)).getText().toString()))
                        });
                if (list != null && list.size() > 0) {
                    ((Activity) context).findViewById(R.id.ms).setVisibility(View.VISIBLE);
                    ((TextView) ((Activity) context).findViewById(R.id.ms)).setText(list.get(0).get("ms"));
                } else
                    ((Activity) context).findViewById(R.id.ms).setVisibility(View.GONE);
            }
        };

    }

    public void showDiary(int day) {
        logLayout.removeAllViews();
        List<Map<String, String>> date = null;
        try {
            date = new SqliteHelper().rawQuery(db.rawQuery("select date,content from myDiary where date like ? and userid=?", new String[]
                    {
                            new SimpleDateFormat("yyyy-M-d").format(new SimpleDateFormat("yyyy-M-d").parse(year + "-" + month + "-" + day)) + " %", Constants.number
                    }));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date != null && date.size() != 0) {
            // diary_number diary_date_item diary_info_item
            for (int i = 0; i < date.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.diary_iterm, logLayout, false);
                view.setTag(date.get(i).get("date"));
                if (i < 10)
                    ((TextView) view.findViewById(R.id.diary_number)).setText("0" + (i + 1));
                else
                    ((TextView) view.findViewById(R.id.diary_number)).setText((i + 1));
                try {
                    ((TextView) view.findViewById(R.id.diary_date_item)).setText(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date.get(i).get("date"))));
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                ((TextView) view.findViewById(R.id.diary_info_item)).setText(date.get(i).get("content"));
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, edit_diary.class);
                        intent.putExtra("date", v.getTag().toString());
                        intent.putExtra("diaryType", 2 + "");
                        context.startActivity(intent);
                    }
                });
                logLayout.addView(view);
            }
        }
    }

    @Override
    public void onClick(View v) {
        listener.setOnClickListener(v);
    }

    public interface OnClickListener {
        void setOnClickListener(View v);
    }
}