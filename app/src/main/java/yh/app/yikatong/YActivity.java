package yh.app.yikatong;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import yh.app.activitytool.FragmentActivityPortrait;

import org.androidpn.push.Constants;

import yh.app.time.JudgeDate;

import yh.app.appstart.lg.R;

import yh.app.time.ScreenInfo;
import yh.app.time.WheelMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 包 名:yh.app.yikatong 类 名:YActivity.java 功 能:一卡通主界面
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class YActivity extends FragmentActivityPortrait {
    private ViewPager mTabPager;
    private TextView t1, t2, t3, start, end;
    private Button chuxun;
    private RelativeLayout r1, r2;
    private mingxi mingxi;
    private fenyue fenyue;
    private zongji zongji;
    private AT mTask = null;
    private List<Map<String, String>> mingxilist = null;
    private List<Map<String, String>> fenyuelist = null;
    private List<Map<String, String>> zongjilist = null;
    private fenyueAdapter fenyueadaoter = null;
    private mingxiAdapter mingxiadapter = null;
    private zongjiAdapter zongjiadapter = null;
    WheelMain wheelMain;
    EditText txttime;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Handler mHandler;
    private ArrayList<Fragment> fragmentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 标题栏去除
        setContentView(R.layout.main_yikatong);
        mingxilist = new ArrayList<Map<String, String>>();
        Map<String, String> title1 = new HashMap<String, String>();
        title1.put("time", "发生时间");
        title1.put("moneny", "发生金额");
        title1.put("type", "类型");
        title1.put("place", "商户");
        mingxilist.add(title1);
        fenyuelist = new ArrayList<Map<String, String>>();
        Map<String, String> title2 = new HashMap<String, String>();
        title2.put("time", "时间");
        title2.put("moneny", "金额");
        title2.put("type", "类型");
        title2.put("shu", "总消费次数");
        fenyuelist.add(title2);
        zongjilist = new ArrayList<Map<String, String>>();
        mTabPager = (ViewPager) findViewById(R.id.main_viewpager);
        mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
        chuxun = (Button) findViewById(R.id.yikatong_chaxun);
        chuxun.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                if (Constants.isNetworkAvailable(YActivity.this)) {
                    mTask = new AT();
                    mTask.execute("11203080104");
                } else {
                    Toast.makeText(getApplicationContext(), "网络环境异常，请检查网络连接！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        t1 = (TextView) findViewById(R.id.tv_tab_mingxi);
        t2 = (TextView) findViewById(R.id.tv_tab_fenyue);
        t3 = (TextView) findViewById(R.id.tv_tab_zongji);
        start = (TextView) findViewById(R.id.start_time);
        end = (TextView) findViewById(R.id.end_time);
        r1 = (RelativeLayout) findViewById(R.id.mstart);
        r2 = (RelativeLayout) findViewById(R.id.mend);
        Calendar calendar = Calendar.getInstance();
        start.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "");
        end.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "");
        r1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = LayoutInflater.from(YActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(YActivity.this);
                wheelMain = new WheelMain(timepickerview);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = start.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year, month, day);
                new AlertDialog.Builder(YActivity.this).setTitle("开始时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newdate = wheelMain.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                        Date s = null, e = null;
                        try {
                            s = sdf.parse(newdate);
                            e = sdf.parse(end.getText().toString());
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        boolean flag = e.before(s);
                        if (flag) {
                            Toast.makeText(getApplicationContext(), "开始时间不能大于结束时间", Toast.LENGTH_SHORT).show();
                        } else {
                            start.setText(wheelMain.getTime());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        r2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                LayoutInflater inflater = LayoutInflater.from(YActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(YActivity.this);
                wheelMain = new WheelMain(timepickerview);
                wheelMain.screenheight = screenInfo.getHeight();
                String time = end.getText().toString();
                Calendar calendar = Calendar.getInstance();
                if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
                    try {
                        calendar.setTime(dateFormat.parse(time));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                wheelMain.initDateTimePicker(year, month, day);
                new AlertDialog.Builder(YActivity.this).setTitle("结束时间").setView(timepickerview).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newdate = wheelMain.getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                        Date s = null, e = null;
                        try {
                            s = sdf.parse(start.getText().toString());
                            e = sdf.parse(newdate);
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        boolean flag = e.before(s);
                        if (flag) {
                            Toast.makeText(getApplicationContext(), "结束时间不能小于开始时间", Toast.LENGTH_SHORT).show();
                        } else {
                            end.setText(wheelMain.getTime());
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
            }
        });
        t1.setOnClickListener(new MyOnClickListener(0));
        t2.setOnClickListener(new MyOnClickListener(1));
        t3.setOnClickListener(new MyOnClickListener(2));
        mingxi = new mingxi();
        fenyue = new fenyue();
        zongji = new zongji();
        if (Constants.isNetworkAvailable(YActivity.this)) {
            mTask = new AT();
            mTask.execute(Constants.number);
        } else {
            Toast.makeText(getApplicationContext(), "网络环境异常，请检查网络连接！", Toast.LENGTH_SHORT).show();
        }
        mingxiadapter = new mingxiAdapter(this, mingxilist);
        mingxi.setAdapter(mingxiadapter);
        fenyueadaoter = new fenyueAdapter(this, fenyuelist);
        fenyue.setAdapter(fenyueadaoter);
        zongjiadapter = new zongjiAdapter(this, zongjilist);
        zongji.setAdapter(zongjiadapter);
        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(mingxi);
        fragmentsList.add(fenyue);
        fragmentsList.add(zongji);
        mTabPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentsList));
        mTabPager.setCurrentItem(0);
        mHandler = new Handler() {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 1:
                        mingxiadapter.notifyDataSetChanged();
                        fenyueadaoter.notifyDataSetChanged();
                        zongjiadapter.notifyDataSetChanged();
                }
            }
        };
    }

    /**
     * ͷ��������
     */
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            mTabPager.setCurrentItem(index);
        }
    }

    ;

    /*
     * ҳ���л�����(ԭ����:D.Winter)
     */
    public class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            switch (arg0) {
                case 0:
                    t1.setTextColor(Color.rgb(0, 100, 0));
                    t2.setTextColor(Color.rgb(0, 0, 0));
                    t3.setTextColor(Color.rgb(0, 0, 0));
                    break;
                case 1:
                    t2.setTextColor(Color.rgb(0, 100, 0));
                    t1.setTextColor(Color.rgb(0, 0, 0));
                    t3.setTextColor(Color.rgb(0, 0, 0));
                    break;
                case 2:
                    t3.setTextColor(Color.rgb(0, 100, 0));
                    t1.setTextColor(Color.rgb(0, 0, 0));
                    t2.setTextColor(Color.rgb(0, 0, 0));
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    class AT extends AsyncTask<String, Integer, String> {
        public boolean setAction(int action) {
            return true;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost("http://202.202.144.30:8080/MobileCampusSystem/campusCardRecord");
                List<NameValuePair> parames = new ArrayList<NameValuePair>();
                if (params.length > 1) {
                    parames.add(new BasicNameValuePair("stuId", params[0]));
                    parames.add(new BasicNameValuePair("start", params[1]));
                    parames.add(new BasicNameValuePair("" +
                            "", params[2]));
                } else {
                    parames.add(new BasicNameValuePair("error", "ffdf"));
                }
                hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
                HttpResponse hr = hc.execute(hp);
                String result = null;
                if (hr.getStatusLine().getStatusCode() == 200) {
                    result = EntityUtils.toString(hr.getEntity());
                }
                if (hc != null) {
                    hc.getConnectionManager().shutdown();
                }
                return result;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... progresses) {
        }

        @Override
        protected void onPostExecute(String result) {
            mingxilist.clear();
            fenyuelist.clear();
            zongjilist.clear();
            String backlogJsonStrTmp = result.replace("\\", "");
            String jsonstr = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
            JSONObject bigtemp = null;
            String tempRes = null;
            JSONArray array = null;
            try {
                bigtemp = new JSONObject(jsonstr);
                tempRes = bigtemp.getString("detailRecords");
                array = new JSONArray(tempRes);
                for (int i = 0; i < array.length(); i++) {
                    Map<String, String> oneminxi = new HashMap<String, String>();// 存放所有成绩查询的信息
                    JSONObject temp = (JSONObject) array.get(i);
                    oneminxi.put("time", temp.getString("time"));
                    oneminxi.put("moneny", temp.getString("useMoney"));
                    oneminxi.put("type", temp.getString("type"));
                    oneminxi.put("place", temp.getString("merchant"));
                    mingxilist.add(oneminxi);
                }
                tempRes = bigtemp.getString("monthRecords");
                array = new JSONArray(tempRes);
                for (int i = 0; i < array.length(); i++) {
                    Map<String, String> onefenyue = new HashMap<String, String>();// 存放所有成绩查询的信息
                    JSONObject temp = (JSONObject) array.get(i);
                    onefenyue.put("time", temp.getString("time"));
                    onefenyue.put("moneny", temp.getString("money"));
                    onefenyue.put("type", temp.getString("type"));
                    onefenyue.put("shu", temp.getString("frequency"));
                    fenyuelist.add(onefenyue);
                }
                tempRes = bigtemp.getString("totalRecords");
                JSONObject temp = new JSONObject(tempRes);
                Map<String, String> onezongji1 = new HashMap<String, String>();
                Map<String, String> onezongji2 = new HashMap<String, String>();
                Map<String, String> onezongji3 = new HashMap<String, String>();
                Map<String, String> onezongji4 = new HashMap<String, String>();
                onezongji1.put("text1", "消费总笔数");
                onezongji2.put("text1", "充值总笔数");
                onezongji3.put("text1", "消费总金额");
                onezongji4.put("text1", "充值总金额");
                onezongji1.put("text2", temp.getString("totalConsumers"));
                onezongji2.put("text2", temp.getString("totalRecharge"));
                onezongji3.put("text2", temp.getString("totalConsumersMoney"));
                onezongji4.put("text2", temp.getString("totalRechargeMoney"));
                zongjilist.add(onezongji1);
                zongjilist.add(onezongji2);
                zongjilist.add(onezongji3);
                zongjilist.add(onezongji4);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            android.os.Message msg = new android.os.Message();
            msg.what = 1;
            mHandler.sendMessage(msg);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
