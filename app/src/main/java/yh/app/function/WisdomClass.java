package yh.app.function;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import yh.app.activitytool.ActivityPortrait;

import org.androidpn.push.Constants;

import yh.app.tool.SqliteDBCLose;

import yh.app.appstart.lg.R;

import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.weekToDate;
import yh.app.utils.DefaultTopBar;

import android.widget.TextView;

import yh.app.wisdomclass.zhkt;
import 云华.智慧校园.工具._空白填页;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * 包 名:yh.app.function 类 名:yh.app.function.WisdomClass.java 功 能:智慧课堂首页
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-9-18
 */
@SuppressLint("InflateParams")
public class WisdomClass extends ActivityPortrait {
    private LinearLayout Title2;
    private LinearLayout.LayoutParams lp[];
    private int djz = 0;
    private int xqj = 0;
    private String week_item[] = new String[]
            {
                    "一", "二", "三", "四", "五", "六", "日"
            };
    private List<Map<String, String>> courseList = new ArrayList<Map<String, String>>();
    private LinearLayout spinner_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhkt_class_context);
        SQLiteDatabase db1 = null;
        Cursor c1 = null;
        try {
            db1 = new SqliteHelper().getRead();
            c1 = db1.rawQuery("select * from nowterm", null);
            if (c1.getCount() == 0) {
                Toast.makeText(this, "暂无时间信息,请重试", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
        } finally {
            new SqliteDBCLose(db1, c1);
        }
        spinner_content = (LinearLayout) findViewById(R.id.spinner_content);
        Title2 = new LinearLayout(this);
        contentLayout = (LinearLayout) findViewById(R.id.zhkt_content);
        lp = new LinearLayout.LayoutParams[4];
        lp[0] = new LinearLayout.LayoutParams(getScreenWidth() / 2 - 30, getScreenHeight() / 580 * 44);
        lp[1] = new LinearLayout.LayoutParams(getScreenWidth() / 6 + 10, getScreenHeight() / 580 * 44);
        lp[2] = new LinearLayout.LayoutParams(getScreenWidth() / 6 + 10, getScreenHeight() / 580 * 44);
        lp[3] = new LinearLayout.LayoutParams(getScreenWidth() / 6 + 10, getScreenHeight() / 580 * 44);
        title1();
        title2();
//		new MyProgressbar(this).show("测试标题", "测试内容");
    }

    public void title1() {
        new DefaultTopBar(this).doit("智慧课堂");
    }

    private String[] djz_array, xqj_array;
    private ArrayAdapter<String> djz_ada, xqj_ada;

    public void title2() {
        Title2.setGravity(Gravity.CENTER);
        Title2.setOrientation(LinearLayout.HORIZONTAL);
        Title2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        // Title2.setBackgroundColor(Color.parseColor("#27BDFC"));
        Title2.setBackgroundColor(this.getResources().getColor(R.color.button_press));
        LayoutInflater inflater = LayoutInflater.from(this);
        init();
        djz = weekToDate.getCurrentWeekNum(new Date());
        View sp_layout = inflater.inflate(R.layout.zhkt_spinner, null);
        Spinner sp1 = (Spinner) sp_layout.findViewById(R.id.djz_sp);
        Spinner sp2 = (Spinner) sp_layout.findViewById(R.id.xqj_sp);
        sp1.setAdapter(djz_ada);
        sp2.setAdapter(xqj_ada);
        Title2.addView(sp_layout);
        spinner_content.addView(Title2);
        if (djz >= djz_array.length) {
            sp1.setSelection(djz_array.length - 1);
        } else
            sp1.setSelection(djz);

        xqj = weekToDate.getDayOfWeekNum(new Date());
        sp2.setSelection(xqj - 1);
        sp1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                djz = arg2;
                doit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        sp2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                xqj = arg2 + 1;
                doit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    private void doit() {
        num = 0;
        courseList = null;
        courseList = new ArrayList<Map<String, String>>();
        getTime();
        // contentLayout.removeAllViews();
        // contentLayout.removeAllViewsInLayout();
        Content();
    }

    private void init() {
        djz_array = new String[weekToDate.totalWeek()];
        xqj_array = new String[]
                {
                        "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"
                };
        for (int i = 0; i < djz_array.length; i++) {
            djz_array[i] = "第" + (i) + "周";
        }
        djz_ada = new ArrayAdapter<String>(this, R.layout.spiner_check_style, djz_array);

        xqj_ada = new ArrayAdapter<String>(this, R.layout.spiner_check_style, xqj_array);
        djz_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        xqj_ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public View kbtyView = null;

    public void Content() {
        contentLayout.removeAllViews();
        contentLayout.removeAllViewsInLayout();
        if (courseList.size() == 0) {
            findViewById(R.id.kbcy_content).setVisibility(View.VISIBLE);
            if (kbtyView == null)
                kbtyView = new _空白填页().addView(this, findViewById(R.id.sc), (ViewGroup) findViewById(R.id.kbcy_content), R.drawable.zhkt_mk, "智慧课堂没课了，去做点有意义的事吧~");
            else
                findViewById(R.id.sc).setVisibility(View.GONE);
            return;
        }
        for (int i = 0; i < courseList.size(); i++) {
            findViewById(R.id.sc).setVisibility(View.VISIBLE);
            findViewById(R.id.kbcy_content).setVisibility(View.GONE);
            contentLayout.setGravity(Gravity.TOP);
            ContentList(courseList.get(i).get("kcmc").toString(), courseList.get(i).get("djj").toString(), "教师", courseList.get(i).get("lsmz").toString(), courseList.get(i).get("xkkh").toString());
        }
    }

    int num = 0;
    String date;
    LinearLayout contentLayout;

    public void ContentList(final String cName, final String cNum, final String cTeacher, final String cTName, final String xkkh) {
        int text_size = 15;
        LinearLayout content = new LinearLayout(this);
        contentLayout.addView(content);
        content.setOrientation(LinearLayout.HORIZONTAL);
        content.setBackgroundResource(R.drawable.biankuang_4);
        content.setId(num);
        num++;
        final TextView className = new TextView(this);
        final TextView classNum = new TextView(this);
        TextView teacher = new TextView(this);
        TextView teacherName = new TextView(this);
        content.addView(className, lp[0]);
        content.setPadding(20, 0, 0, 0);
        content.addView(classNum, lp[1]);
        content.addView(teacher, lp[2]);
        content.addView(teacherName, lp[3]);
        className.setText("《" + cName + "》");
        className.setEllipsize(TruncateAt.END);
        className.setSingleLine(true);
        className.setPadding(0, 0, 10, 0);
        className.setGravity(Gravity.CENTER);
        className.setTextColor(Color.BLACK);
        className.setTextSize(text_size);
        className.setGravity(Gravity.CENTER_VERTICAL);
        classNum.setText(cNum);
        classNum.setGravity(Gravity.CENTER);
        classNum.setTextColor(Color.GRAY);
        classNum.setTextSize(text_size);
        classNum.setGravity(Gravity.CENTER_VERTICAL);
        teacher.setText(cTeacher);
        teacher.setGravity(Gravity.CENTER);
        teacher.setTextColor(Color.GRAY);
        teacher.setTextSize(text_size);
        teacher.setGravity(Gravity.CENTER_VERTICAL);
        teacherName.setText(cTName);
        teacherName.setGravity(Gravity.START);
        teacherName.setTextColor(Color.GRAY);
        teacherName.setTextSize(text_size);
        teacherName.setGravity(Gravity.CENTER_VERTICAL);
        content.setTag(xkkh + ";" + cNum);
        //列表点击时间
        content.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.putExtra("time", "第" + djz + "周  星期" + week_item[xqj - 1] + "  ");
                intent.putExtra("classname", courseList.get(v.getId()).get("kcmc").toString());
                intent.putExtra("djz", String.valueOf(djz));
                intent.putExtra("xqj", String.valueOf(xqj));
                intent.putExtra("djj", v.getTag().toString().split(";")[1].split("-")[0]);
                intent.putExtra("xkkh", v.getTag().toString().split(";")[0]);
                intent.setClass(WisdomClass.this, zhkt.class);
                startActivity(intent);
            }
        });
    }

    public void getTime() {
        try {
            courseList = new SqliteHelper().rawQuery("select xh,JSXM lsmz,KCID xkkh,COURSE kcmc,sjd||'-'||(jssjd-1)||'节' djj from KC where xh=? and XQJ=? and qsz <=? and jsz >=? and (dsz=(case ?%2 when 1 then '单' when 0 then '双' end) or dsz = 'null') order by SJD        ", new String[]
                    {
                            Constants.number, xqj + "", djz + "", djz + "", djz + ""
                    });
            List<Map<String, String>> time = new SqliteHelper().rawQuery("select starttime,endtime from nowterm");
            new weekToDate(time.get(0).get("starttime"), time.get(0).get("endtime"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "功能异常,请重试", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }
}
