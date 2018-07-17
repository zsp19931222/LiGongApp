package yh.app.calendar;

import java.util.Date;

import org.json.JSONArray;

import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import yh.app.activitytool.ActivityPortrait;

import org.androidpn.push.Constants;

import yh.app.tool.DateString;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ViewClickEffect;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.工具.DefaultPost;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件.YearAndMonthChooser;
import 云华.智慧校园.自定义控件._日历控件;

import android.app.Activity;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 包 名:yh.app.calendar
 * 类 名:Calendar.java
 * 功 能:日历activity
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-8-4
 */
public class Canlendar extends ActivityPortrait {
    private Context context;
    private LinearLayout calendarLayout;
    private CalendarDate calendarDate = new CalendarDate();
    private LinearLayout logLayout;
    private int year = DateString.getYear(new Date()), month = DateString.getMonth(new Date());
    private SQLiteDatabase db = new SqliteHelper().getWrite();
    private YearAndMonthChooser datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        changeDiary();
        initWeekChangeView();
        initTopBar();
        initHoliday();
    }

    public void showDatePickerDialog() {
        datePickerDialog = new YearAndMonthChooser(this, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Canlendar.this.year = year;
                Canlendar.this.month = monthOfYear + 1;
                Canlendar.this.changeDiary();
            }
        }, year, month - 1, 1);
        datePickerDialog.show();
    }

    private void initHoliday() {
        new DefaultPost().doPost(_链接地址导航.DC.节假日.getUrl(), getIntent().getStringExtra("function_id"), new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SQLiteDatabase db = new SqliteHelper().getWrite();
                try {
                    new CalendarHelper().dealJJR(new JSONArray(msg.getData().getString("msg")), db, handler);
                } catch (Exception e) {
                } finally {
                    db.close();
                }
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            changeDiary();
        }

        ;
    };

    private void init() {
        setContentView(R.layout.calendar_main_layout);
        context = this;
        logLayout = (LinearLayout) findViewById(R.id.log_layout);
        calendarLayout = (LinearLayout) findViewById(R.id.calendar_layout);
        findViewById(R.id.date).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void setDateTitle() {
        if (month < 10)
            ((TextView) findViewById(R.id.date)).setText(year + " - 0" + month);
        else
            ((TextView) findViewById(R.id.date)).setText(year + " - " + month);
    }

    private void initTopBar() {
        final TopBarHelper tbh = new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle(Ticket.getFunctionName(getIntent().getStringExtra("function_id")));
        tbh.setExtra(R.drawable.quanzi_tjhy, true);
        tbh.setOnClickLisener(new OnClickLisener() {
            @Override
            public void setRightOnClick() {
//				Intent intent = new Intent();
//				intent.setAction("com.example.app3.HomePageActivity");
//				intent.setPackage(context.getPackageName());
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);
                ((Activity) context).finish();
            }

            @Override
            public void setLeftOnClick() {
                finish();
            }

            @Override
            public void setExtraOnclick() {
                String str = calendarDate.click_year + "-" + calendarDate.click_month + "-" + calendarDate.click_day;
                ViewClickEffect.doEffect(tbh.getExtraView(), 200, Canlendar.this, "yh.app.mydiary.edit_diary", ExampleApplication.getAppPackage(), new String[][]
                        {
                                {
                                        "date", str
                                },
                                {
                                        "diaryType", String.valueOf(1)
                                }
                        });
            }
        });

    }

    private void initWeekChangeView() {
        findViewById(R.id.l).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDate = new CalendarDate();
                if (month == 1) {
                    month = 12;
                    year--;
                } else
                    month--;
                findViewById(R.id.ms).setVisibility(View.GONE);
                changeDiary();
            }
        });
        findViewById(R.id.r).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarDate = new CalendarDate();
                if (month == 12) {
                    month = 1;
                    year++;
                } else
                    month++;
                findViewById(R.id.ms).setVisibility(View.GONE);
                changeDiary();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            db.close();
        } catch (Exception e) {
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        changeDiary();
    }

    private void changeDiary() {
        setDateTitle();
        new _日历控件(context, calendarLayout, logLayout, calendarDate, db).buildCalendar(year, month);
    }
}
