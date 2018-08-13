package yh.app.coursetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;

import yh.app.activitytool.ActivityPortrait;

import org.androidpn.push.Constants;

import yh.app.logTool.Log;
import yh.app.progressdialog.CustomProgressDialog;
import yh.app.tool.DateString;
import yh.app.tool.DpPx;
import yh.app.tool.KBAT;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.tool.weekToDate;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.工具.ActivityHelper;
import 云华.智慧校园.工具.DateHelper;
import 云华.智慧校园.工具.PopupWindowHelper;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel;
import 云华.智慧校园.自定义控件.DiaLogOkAndCancel.OnButtonClickLisener;
import yh.app.coursetable.TableSttingPop.OnClickLisenler;
//import yh.app.notification.Notification1.IntentHelper;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yhkj.cqgyxy.R;

/**
 * 包 名:yh.app.coursetable 类 名:yh.app.coursetable.TableDemoActivity
 * 功能:课表Demo显示,并且获取数据
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015/7/29
 */
@SuppressLint("InflateParams")
@SuppressWarnings("unused")
public class TableDemoActivity extends ActivityPortrait {
    private LinearLayout layout;
    private Context mContext;
    private KBAT mTask = null;
    private String starttime, endtime;
    //	CustomProgressDialog c = null;
    Spinner spiner = null;
    private View currentview = null;
    String[] tiaomuids = null;
    private String XN;
    int XQ = 0;
    public Handler mHandler = null;
    private List<Map<String, String>> popList = new ArrayList<>();

    private boolean isFresh = false;

    private weekToDate weektodate = null;
    private TextView nowmonth = null;
    private TextView oneday_date = null;
    private TextView tuesday_date = null;
    private TextView wednesday_date = null;
    private TextView thursday_date = null;
    private TextView friday_date = null;
    private TextView saturday_date = null;
    private TextView monday_date = null;
    private List<theclass> classes1 = null;
    private List<theclass> classes2 = null;
    private List<theclass> classes3 = null;
    private List<theclass> classes4 = null;
    private List<theclass> classes5 = null;
    private List<theclass> classes6 = null;
    private List<theclass> classes7 = null;
    private LinearLayout ll1 = null;
    private LinearLayout ll2 = null;
    private LinearLayout ll3 = null;
    ;
    private LinearLayout ll4 = null;
    private LinearLayout ll5 = null;
    private LinearLayout ll6 = null;
    private LinearLayout ll7 = null;
    private int colors[] =
            {
                    Color.parseColor("#6dc0e2"), Color.parseColor("#e69c9d"), Color.parseColor("#96ce5f"), Color.parseColor("#8dabe7"), Color.parseColor("#eebf6f"), Color.parseColor("#B0B0B0"), Color.parseColor("#FF4500"), Color.parseColor("#FFB6C1"), Color.parseColor("#EE6AA7"), Color.parseColor("#EEA2AD"), Color.parseColor("#9F79EE"), Color.parseColor("#CD5555"), Color.parseColor("#BCD2EE"), Color.parseColor("#B23AEE"), Color.parseColor("#7D9EC0"), Color.parseColor("#EE9A00"), Color.parseColor("#00868B"), Color.parseColor("#0000FF"), Color.parseColor("#CD0000"), Color.parseColor("#87CEEB"), Color.parseColor("#76EEC6"), Color.parseColor("#20B2AA"), Color.parseColor("#218868"), Color.parseColor("#104E8B")
            };
    private LinearLayout kbcx_bk_layout;
    private PopupWindowHelper poph;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kbmain);
        SQLiteDatabase db1 = null;
        Cursor c1 = null;
        try {
            db1 = new SqliteHelper().getRead();
            c1 = db1.rawQuery("select * from nowterm", null);
            if (c1.getCount() == 0) {
                Toast.makeText(this, "暂无时间信息,请稍后重试", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } catch (Exception e) {
        } finally {
            new SqliteDBCLose(db1, c1);
        }
        initTopbar();
        initPopList();
        mContext = this;
        kbcx_bk_layout = (LinearLayout) findViewById(R.id.kbcx_kb_layout);
        layout = (LinearLayout) findViewById(R.id.kbcx);
        nowmonth = (TextView) findViewById(R.id.nowmonth);
        oneday_date = (TextView) findViewById(R.id.oneday_date);
        tuesday_date = (TextView) findViewById(R.id.tuesday_date);
        wednesday_date = (TextView) findViewById(R.id.wednesday_date);
        thursday_date = (TextView) findViewById(R.id.thursday_date);
        friday_date = (TextView) findViewById(R.id.friday_date);
        saturday_date = (TextView) findViewById(R.id.saturday_date);
        monday_date = (TextView) findViewById(R.id.monday_date);
        classes1 = new ArrayList<theclass>();
        classes2 = new ArrayList<theclass>();
        classes3 = new ArrayList<theclass>();
        classes4 = new ArrayList<theclass>();
        classes5 = new ArrayList<theclass>();
        classes6 = new ArrayList<theclass>();
        classes7 = new ArrayList<theclass>();
//		c = CustomProgressDialog.createDialog(TableDemoActivity.this);
//		c.setTitile("网络连接");
//		c.setMessage("课表更新中...");
//		c.onWindowFocusChanged(true);
//		c.setCancelable(false);
        List<Map<String, String>> term = new SqliteHelper().rawQuery("select xn,xq,starttime,endtime from nowterm");
        if (term != null && term.size() > 0) {
            XQ = Integer.valueOf(term.get(0).get("xq"));
            XN = term.get(0).get("xn");
            starttime = term.get(0).get("starttime");
            endtime = term.get(0).get("endtime");
            weektodate = new weekToDate(term.get(0).get("starttime"), term.get(0).get("endtime"));
        }
        Date nowdate = new Date();
        int nowweek = DateHelper.weeksBetween(DateString.stringToDate("yyyy-MM-dd", starttime), new Date());
        dateupdate(nowweek);
        String[] weeksAll = getWeeks(weekToDate.totalWeek());
        spiner = (Spinner) findViewById(R.id.course_weeks);
        ArrayAdapter<String> Arrayadapter = new ArrayAdapter<String>(this, R.layout.spiner_check_style, weeksAll);
        Arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(Arrayadapter);
        if (nowweek - 1 >= Arrayadapter.getCount()) {
            spiner.setPrompt("第" + (Arrayadapter.getCount() - 1) + "周");
            spiner.setSelection(Arrayadapter.getCount() - 1, true);
        } else if (nowweek == 0) {
            spiner.setPrompt("第" + nowweek + "周");
            spiner.setSelection(nowweek, true);
        } else {
            spiner.setPrompt("第" + (nowweek - 1) + "周");
            spiner.setSelection((nowweek - 1), true);
        }
        spiner.getSelectedItemPosition();
        spiner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                arg0.setVisibility(View.VISIBLE);
                android.os.Message msg = new android.os.Message();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        ll7 = (LinearLayout) findViewById(R.id.ll7);
        Removell();
        coursedate();
        for (int i = 1; i < 8; i++) {
            switch (i) {
                case 1:
                    setClasses(ll1, classes1);
                    break;
                case 2:
                    setClasses(ll2, classes2);
                    break;
                case 3:
                    setClasses(ll3, classes3);
                    break;
                case 4:
                    setClasses(ll4, classes4);
                    break;
                case 5:
                    setClasses(ll5, classes5);
                    break;
                case 6:
                    setClasses(ll6, classes6);
                    break;
                case 7:
                    setClasses(ll7, classes7);
                    break;
                default:
                    break;
            }
        }
        mHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        dateupdate(spiner.getSelectedItemPosition() + 1);
                        Removell();
                        coursedate();
                        for (int i = 1; i < 8; i++) {
                            switch (i) {
                                case 1:
                                    setClasses(ll1, classes1);
                                    break;
                                case 2:
                                    setClasses(ll2, classes2);
                                    break;
                                case 3:
                                    setClasses(ll3, classes3);
                                    break;
                                case 4:
                                    setClasses(ll4, classes4);
                                    break;
                                case 5:
                                    setClasses(ll5, classes5);
                                    break;
                                case 6:
                                    setClasses(ll6, classes6);
                                    break;
                                case 7:
                                    setClasses(ll7, classes7);
                                    break;
                                default:
                                    break;
                            }
                        }
//					if (c.isShowing())
//					{
//						c.cancel();
//					}
                        break;
                    case 2:
                        break;
                }
            }
        };
        freshCourse(true);
    }

    private void initPopList() {
        // TODO Auto-generated method stub
        Map<String, String> map1 = new HashMap<String, String>(), map2 = new HashMap<String, String>(), map3 = new HashMap<String, String>();
        map1.put("data", "刷新");
        map1.put("type", "1");
        map2.put("data", "添加");
        map2.put("type", "2");
        map3.put("data", "删除");
        map3.put("type", "3");
        popList.add(map1);
        popList.add(map2);
        popList.add(map3);
    }

    private void initTopbar() {
        // // TODO Auto-generated method stub
        // new
        // DefaultTopBar(this).doit(Ticket.getFunctionName(getIntent().getStringExtra("function_id"))).setExtra(R.drawable.icon_more,
        // true).getExtraView().setOnClickListener(new OnClickListener()
        // {
        // @Override
        // public void onClick(View v)
        // {
        // new TableSttingPop().show(v, TableDemoActivity.this, popList, new
        // OnClickLisenler()
        // {
        // @Override
        // public void setmOnClickLisenler(View view)
        // {
        // if (((TextView)
        // view.findViewById(R.id.textview)).getText().toString().equals("添加"))
        // {
        // addCourse();
        // } else if (((TextView)
        // view.findViewById(R.id.textview)).getText().toString().equals("删除"))
        // {
        // deleteCourse();
        // } else if (((TextView)
        // view.findViewById(R.id.textview)).getText().toString().equals("刷新"))
        // {
        // freshCourse(true);
        // }
        // }
        // });
        // }
        // });
        final TopBarHelper helper = new TopBarHelper(this, findViewById(R.id.topbar_layout));
        Button right = (Button) ((LinearLayout) helper.getRightView()).getChildAt(0);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) right.getLayoutParams();
        lp.width = lp.height = new DpPx(this).getDpToPx(30);
        right.setLayoutParams(lp);
        right.setBackgroundResource(R.drawable.shuaxin);
        helper.setTitle(Ticket.getFunctionName(getIntent().getStringExtra("function_id"))).setExtra(R.drawable.icon_more, true).setOnClickLisener(new OnClickLisener() {
            @Override
            public void setRightOnClick() {
                // TODO Auto-generated method stub
                freshCourse(true);
            }

            @Override
            public void setLeftOnClick() {
                // TODO Auto-generated method stub
                finish();
            }

            @Override
            public void setExtraOnclick() {
                // TODO Auto-generated method stub
                new TableSttingPop().show(helper.getExtraView(), TableDemoActivity.this, popList, new OnClickLisenler() {
                    @Override
                    public void setmOnClickLisenler(View view) {
                        if (((TextView) view.findViewById(R.id.textview)).getText().toString().equals("添加")) {
                            addCourse();
                        } else if (((TextView) view.findViewById(R.id.textview)).getText().toString().equals("删除")) {
                            deleteCourse();
                        } else if (((TextView) view.findViewById(R.id.textview)).getText().toString().equals("刷新")) {
                            freshCourse(true);
                        }
                    }
                });
            }
        });
    }

    public void addCourse() {
        final PopupWindowHelper poph = new PopupWindowHelper().setPopupWindow(mContext, LayoutInflater.from(mContext).inflate(R.layout.course_add, null), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).showAtLocation(layout, Gravity.TOP | Gravity.START, 0, 0);
        poph.getContentView().findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                poph.dismiss();
            }
        });
        poph.getContentView().findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new SqliteHelper().execSQL("insert into customKC(KBID ,KCID ,XH ,XN ,XQ ,JSXM ,JSMC ,COURSE ,XQJ ,SJD ,JSSJD ,DSZ ,QSZ ,JSZ ,COLOR ,userid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new Random().nextInt(), new Random().nextInt() + "", Constants.number, XN, XQ, ((TextView) poph.getContentView().findViewById(R.id.jsmc)).getText().toString(), ((TextView) poph.getContentView().findViewById(R.id.skdd)).getText().toString(), ((TextView) poph.getContentView().findViewById(R.id.kcmc)).getText().toString(), Integer.valueOf(((TextView) poph.getContentView().findViewById(R.id.xqj)).getText().toString()), Integer.valueOf(((TextView) poph.getContentView().findViewById(R.id.jieci1)).getText().toString()), Integer.valueOf(((TextView) poph.getContentView().findViewById(R.id.jieci2)).getText().toString()) + 1, "", Integer.valueOf(((TextView) poph.getContentView().findViewById(R.id.zhouci1)).getText().toString()), Integer.valueOf(((TextView) poph.getContentView().findViewById(R.id.zhouci2)).getText().toString()), new Random().nextInt(colors.length - 1), Constants.number);
                    freshCourse(false);
                } catch (Exception ignored) {

                }
                poph.dismiss();
            }
        });
        new TopBarHelper(mContext, poph.getContentView().findViewById(R.id.topbar_layout)).setTitle("课程添加").setOnClickLisener(new OnClickLisener() {

            @Override
            public void setRightOnClick() {
                // TODO Auto-generated method stub
                new ActivityHelper().goHomeActivity(mContext);
            }

            @Override
            public void setLeftOnClick() {
                // TODO Auto-generated method stub
                poph.dismiss();
            }

            @Override
            public void setExtraOnclick() {
                // TODO Auto-generated method stub

            }
        });
        isFresh = true;
    }

    public void deleteCourse() {
        final PopupWindowHelper poph = new PopupWindowHelper().setPopupWindow(mContext, LayoutInflater.from(mContext).inflate(R.layout.course_delete, null), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT).showAtLocation(layout, Gravity.TOP | Gravity.START, 0, 0);

        List<Map<String, String>> list = new SqliteHelper().rawQuery("select * from customKC where userid=?", new String[]
                {
                        Constants.number
                });
        final LinearLayout layout = (LinearLayout) poph.getContentView().findViewById(R.id.course_delete_linearlayout);
        for (int i = 0; i < list.size(); i++) {
            final View view = LayoutInflater.from(mContext).inflate(R.layout.course_delete_item, layout, false);
            ((TextView) view.findViewById(R.id.kcmc)).setText(list.get(i).get("COURSE"));
            ((TextView) view.findViewById(R.id.zc)).setText("第" + list.get(i).get("QSZ") + "-" + list.get(i).get("JSZ") + "周");
            ((TextView) view.findViewById(R.id.jc)).setText("第" + list.get(i).get("SJD") + "-" + (Integer.valueOf(list.get(i).get("JSSJD")) - 1) + "节");
            ((TextView) view.findViewById(R.id.xqj)).setText(list.get(i).get("XQJ"));
            layout.addView(view);
            view.setTag(list.get(i).get("KCID"));
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DiaLogOkAndCancel().buldeDialog(mContext, "是否删除", "是否删除该课程", "取消", "确定", new OnButtonClickLisener() {

                        @Override
                        public void setButton2ClickLisener(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            layout.removeView(view);
                            new SqliteHelper().execSQL("delete from customKC where kcid=?", new Object[]
                                    {
                                            view.getTag().toString()
                                    });
                            freshCourse(false);
                            dialog.dismiss();
                        }

                        @Override
                        public void setButton1ClickLisener(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            dialog.dismiss();
                        }
                    });
                }
            });
        }

        new TopBarHelper(mContext, poph.getContentView().findViewById(R.id.topbar_layout)).setTitle("课程删除").setOnClickLisener(new OnClickLisener() {
            @Override
            public void setRightOnClick() {
                // TODO Auto-generated method stub
                new ActivityHelper().goHomeActivity(mContext);
            }

            @Override
            public void setLeftOnClick() {
                // TODO Auto-generated method stub
                freshCourse(false);
                poph.dismiss();
            }

            @Override
            public void setExtraOnclick() {
                // TODO Auto-generated method stub

            }
        });
    }

    private void freshCourse(boolean byNet) {
        if (Constants.isNetworkAvailable(TableDemoActivity.this) && byNet) {
            mTask = new KBAT(Constants.number, XN, XQ, mHandler, mContext);
            mTask.executeOnExecutor(Executors.newCachedThreadPool());
        } else {
            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }

    private boolean dateupdate(int nowweek) {
        List<String> weekdate = weekToDate.OneWeekDays(nowweek);
        nowmonth.setText(weekToDate.MouthWeek(nowweek));
        oneday_date.setText(weekdate.get(0));
        tuesday_date.setText(weekdate.get(1));
        wednesday_date.setText(weekdate.get(2));
        thursday_date.setText(weekdate.get(3));
        friday_date.setText(weekdate.get(4));
        saturday_date.setText(weekdate.get(5));
        monday_date.setText(weekdate.get(6));
        return true;
    }

    private boolean Removell() {
        ll1.removeAllViews();
        ll2.removeAllViews();
        ll3.removeAllViews();
        ll4.removeAllViews();
        ll5.removeAllViews();
        ll6.removeAllViews();
        ll7.removeAllViews();
        classes1.clear();
        classes2.clear();
        classes3.clear();
        classes4.clear();
        classes5.clear();
        classes6.clear();
        classes7.clear();
        return true;
    }

    private boolean Iseven(int a) {
        if (a % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }

    // 课程 日期开始
    private boolean coursedate() {
        SQLiteDatabase db = new SqliteHelper().getWrite();
        int a = spiner.getSelectedItemPosition() + 1;
        Cursor result = null;
        if (Iseven(a)) {
            result = db.rawQuery("select * from KC where XH='" + Constants.number + "' and XN='" + XN + "' and XQ='" + XQ + "' and QSZ<=" + a + " and JSZ>=" + a + " and DSZ<>'单' union select * from customKC where XH='" + Constants.number + "' and XN='" + XN + "' and XQ='" + XQ + "' and QSZ<=" + a + " and JSZ>=" + a, null);
        } else {
            result = db.rawQuery("select * from KC where XH='" + Constants.number + "' and XN='" + XN + "' and XQ='" + XQ + "' and QSZ<=" + a + " and JSZ>=" + a + " and DSZ<>'双' union select * from customKC where XH='" + Constants.number + "' and XN='" + XN + "' and XQ='" + XQ + "' and QSZ<=" + a + " and JSZ>=" + a, null);
        }
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int XQ = result.getInt(8);
            switch (XQ) {
                case 1:
                    classes1.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 2:
                    classes2.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 3:
                    classes3.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 4:
                    classes4.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 5:
                    classes5.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 6:
                    classes6.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                case 7:
                    classes7.add(new theclass(result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7), result.getInt(8), result.getInt(9), result.getInt(10), result.getString(11), result.getInt(12), result.getInt(13), result.getString(1), result.getInt(14)));
                    break;
                default:
                    break;
            }
            result.moveToNext();
        }

        db.close();
        sort();
        return true;
    }

    private boolean setClasses(LinearLayout ll, List<theclass> classes) {
        int currentj = 1;
        theclass clas = null;
        if (classes.size() == 0) {
            return true;
        }
        kbcx_bk_layout.setGravity(Gravity.START | Gravity.TOP);
        for (int i = 0; i < classes.size(); i++) {
            clas = classes.get(i);
            int kong = clas.SJD - currentj;
            if (kong >= 1) {
                setNoClass(ll, kong, 0);
                currentj = clas.SJD + 1;
            }
            if (kong < 0) {
                setreClass(clas.COURSE, clas.XKKH, clas.SJD, clas.XH, clas.XN, clas.XQ, clas.XQJ);
            } else {
                setClass(ll, clas.COURSE, clas.JSMC, clas.QSZ + "-" + clas.JSZ, "9:50-11:25", clas.JSSJD - clas.SJD, clas.color, clas.XKKH, clas.SJD, clas.XH, clas.XN, clas.XQ, clas.XQJ);
                currentj = clas.JSSJD;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    private boolean sort() {
        Collections.sort(classes1, new SortBySJD());
        Collections.sort(classes2, new SortBySJD());
        Collections.sort(classes3, new SortBySJD());
        Collections.sort(classes4, new SortBySJD());
        Collections.sort(classes5, new SortBySJD());
        Collections.sort(classes6, new SortBySJD());
        Collections.sort(classes7, new SortBySJD());
        return true;
    }

    public String[] getWeeks(int week) {
        String[] weeks = new String[week];
        for (int i = 1; i <= week; i++) {
            weeks[i - 1] = "第" + i + "周";
        }
        return weeks;
    }

    /**
     * ���ÿγ̵ķ���
     *
     * @param ll
     * @param title   �γ����
     * @param place   �ص�
     * @param last    ʱ��
     * @param time    �ܴ�
     * @param classes ����
     * @param color   ����ɫ
     */
    @SuppressLint("NewApi")
    void setClass(LinearLayout ll, String title, String place, String last, String time, int classes, int color, String kcid, int SJD, String XH, String XN, String XQ, int XQJ) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.height = dip2px(this, classes * 49);
        View view = LayoutInflater.from(this).inflate(R.layout.course_week_item, null);
        view.setLayoutParams(params);
//        view.setMinimumHeight(dip2px(this, classes * 49));
        GradientDrawable gd = new GradientDrawable();// 创建drawable
        gd.setColor(colors[color]);
        gd.setCornerRadius(5);
        gd.setStroke(1, colors[color]);
        view.setBackground(gd);
        ((TextView) view.findViewById(R.id.tiaomuid)).setText(kcid + "," + SJD + "," + XH + "," + XN + "," + XQ + "," + XQJ + "," + title);
        ((TextView) view.findViewById(R.id.title)).setText(title);
        ((TextView) view.findViewById(R.id.place)).setText(place);
        view.setOnClickListener(new OnClickClassListener());
        TextView blank1 = new TextView(this);
        blank1.setHeight(dip2px(this, classes));
        ll.addView(blank1);
        ll.addView(view);
        currentview = view;
    }

    @SuppressLint("NewApi")
    void setreClass(String title, String kcid, int SJD, String XH, String XN, String XQ, int XQJ) {
        String temp = ((TextView) currentview.findViewById(R.id.tiaomuid)).getText().toString();
        ((TextView) currentview.findViewById(R.id.tiaomuid)).setText(temp + ";" + kcid + "," + SJD + "," + XH + "," + XN + "," + XQ + "," + XQJ + "," + title);
    }

    /**
     * �����޿Σ��հ٣�
     *
     * @param ll
     * @param classes �޿εĽ���ȣ�
     * @param color
     */
    void setNoClass(LinearLayout ll, int classes, int color) {
        TextView blank = new TextView(this);
        if (color == 0)
            blank.setMinHeight(dip2px(this, classes * 50));
        ll.addView(blank);
    }

    class OnClickClassListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            String tiaomuid = (String) ((TextView) v.findViewById(R.id.tiaomuid)).getText();
            tiaomuids = tiaomuid.split(";");
            String[] tiaomunames = new String[tiaomuids.length];
            for (int i = 0; i < tiaomunames.length; i++) {
                String[] temp = tiaomuids[i].split(",");
                tiaomunames[i] = temp[temp.length - 1];
            }
            if (tiaomuids.length == 1) {
                sendcourse(tiaomuid);
            } else {
                new AlertDialog.Builder(TableDemoActivity.this).setTitle("请点击选择").setItems(tiaomunames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendcourse(tiaomuids[which]);
                    }
                }).show();
            }
        }
    }

    private boolean sendcourse(String tiaomuid) {
        String weeks = "";
        String[] cha = tiaomuid.split(",");
        SQLiteDatabase db = new SqliteHelper().getWrite();
        String sql = String.format("select * from KC where KCID='%s' and SJD ='%s' and XH ='%s' and XN='%s' and XQ='%s' and XQJ='%s' union select * from customKC where userid='%s' and kcid='%s'", new Object[]
                {
                        cha[0], Integer.parseInt(cha[1]), cha[2], cha[3], cha[4], cha[5], Constants.number, cha[0]
                });
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            weeks += "第";
            if (result.getString(11).equals("单")) {
                for (int i = Integer.parseInt(result.getString(12)); i <= Integer.parseInt(result.getString(13)); i++) {
                    if (i % 2 != 0) {
                        weeks += " " + i + " ";
                    }
                }
            } else if (result.getString(11).equals("双")) {
                for (int i = Integer.parseInt(result.getString(12)); i <= Integer.parseInt(result.getString(13)); i++) {
                    if (i % 2 == 0) {
                        weeks += " " + i + " ";
                    }
                }
            } else {
                weeks += result.getString(12) + "-" + result.getString(13);
            }
            weeks += "周 ";
            result.moveToNext();
        }
        result.moveToFirst();
        Intent intent = new Intent(TableDemoActivity.this, coureActivity.class);
        intent.putExtra("course_name", result.getString(7));
        intent.putExtra("course_teacher", result.getString(5));
        intent.putExtra("course_time", "第" + result.getString(9) + "-" + (Integer.valueOf(result.getString(10)) - 1) + "节");
        intent.putExtra("course_week", weeks);
        intent.putExtra("course_palece", result.getString(6));
        db.close();
        new zskb().doit(mContext, layout, intent);
        return true;
    }

    @SuppressWarnings("rawtypes")
    class SortBySJD implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            theclass s1 = (theclass) o1;
            theclass s2 = (theclass) o2;
            if (s1.SJD > s2.SJD)
                return 1;
            else if (s1.SJD == s2.SJD) {
                return 0;
            }
            return -1;
        }
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}