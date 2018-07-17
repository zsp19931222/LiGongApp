package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import android.widget.TextView;import yh.app.appstart.lg.R;
import yh.app.tool.JsonListMap;
import yh.app.tool.MyRandom;
import yh.app.tool.SqliteHelper;
import yh.app.wisdomclass.ShakeListener;
import yh.app.wisdomclass.ShakeListener.OnShakeListener;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * 包 名:yh.app.wisdomclass 类 名:yh.app.wisdomclass.classContent.java 功
 * 能:智慧二级界面,分为两类:老师和学生
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-9-9
 */
public class classContent extends ActivityPortrait
{
	String yanzhengma = MyRandom.MyRandom();
	private LinearLayout fenlei_pack;// 装载四个分类布局
	private LinearLayout layout;
	private LinearLayout Title1;
	private LinearLayout Title2;
	private LinearLayout Title3;
	private TextView fenlei[];
	private String fenlei_text[] = { "点名", "字条", "评教", "备注" };
	private LinearLayout fenleiLayout[];
	private TextView title1;
	private TextView DianMingNum;
	private ShakeListener mShakeListener = null;
	private int time = 30;
	private int Current_layout = 0;
	private int HORIZONTAL = 0x00000000;
	private int VERTICAL = 0x00000001;
	private int CENTER = 0x11;
	private boolean isQiandao = false;
	/** 0未到 1 */
	private int stuState = 0;
	private List<Integer> jm = new ArrayList<Integer>();
	private List<Map<String, String>> stuList = new ArrayList<Map<String, String>>();
	private JSONArray stuJSONArray;
	private String xkkh;
	private String djz;
	private String xqj;
	private String djj;
	private zhktAT AT;
	private List<Map<String, Object>> xsdmzt = new ArrayList<Map<String, Object>>();
	private List<Map<String, Object>> tjlist = new ArrayList<Map<String, Object>>();
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		// Constants.usertype = 2;
		SQLiteDatabase db = new SqliteHelper().getRead();
		Cursor c = db.rawQuery("select usertype from usertype where userid=?", new String[] { Constants.number });
		if (c.moveToFirst())
		{
			Constants.usertype = c.getInt(0);
		}
		c.close();
		db.close();
		context = this;
		fenlei_pack = new LinearLayout(this);
		layout = new LinearLayout(this);
		Title1 = new LinearLayout(this);
		Title2 = new LinearLayout(this);
		Title3 = new LinearLayout(this);
		layout.setOrientation(VERTICAL);
		layout.setBackgroundColor(Color.WHITE);
		title1 = new TextView(this);
		fenlei = new TextView[4];
		fenleiLayout = new LinearLayout[7];
		DianMingNum = new TextView(this);
		for (int i = 0; i < 4; i++)
		{
			fenlei[i] = new TextView(this);
		}
		Intent intent = getIntent();
		xkkh = intent.getStringExtra("xkkh");
		djz = intent.getStringExtra("djz");
		djj = intent.getStringExtra("djj");
		xqj = intent.getStringExtra("xqj");
		for (int i = 0; i < 7; i++)
		{
			fenleiLayout[i] = new LinearLayout(this);
			fenleiLayout[i].setOrientation(LinearLayout.VERTICAL);
		}
		layout.addView(Title1);
		layout.addView(Title2);
		layout.addView(Title3);
		if (Constants.usertype == 1)
		{
			fenlei_pack.addView(fenleiLayout[0]);
			Current_layout = 0;
		} else if (Constants.usertype == 2)
		{
			AT = new zhktAT(3, mHandler);
			AT.execute(xkkh);
			fenlei_pack.addView(fenleiLayout[4]);
			Current_layout = 4;
		}
		this.setContentView(layout);
		title1();
		title2();
		layout.addView(fenlei_pack);
		if (Constants.usertype == 1)
		{
			jm.add(0);
			jm.add(1);
			jm.add(2);
			jm.add(3);
			jm.add(5);
			mShakeListener = new ShakeListener(this);
			mShakeListener.setOnShakeListener(new LSshakeLitener(yanzhengma));
			setLayout00();
			setLayout02();
		} else if (Constants.usertype == 2)
		{
			jm.add(4);
			jm.add(1);
			jm.add(2);
			jm.add(3);
		}
		setLayout1();
		setLayout20();
		setLayout3();
	}

	public void title1()
	{
		Title1.setGravity(CENTER);
		Title1.setOrientation(HORIZONTAL);
		Title1.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() * 46 / 580));
		Title1.setGravity(Gravity.CENTER);
		Title1.setBackgroundColor(Color.parseColor("#00A3E8"));
		ImageView image_back = new ImageView(this);
		image_back.setBackgroundResource(R.drawable.icon_back);
		RelativeLayout rel = new RelativeLayout(this);
		rel.addView(image_back, new RelativeLayout.LayoutParams(getScreenHeight() * 25 / 580, getScreenHeight() * 25 / 580));
		rel.setGravity(Gravity.CENTER);
		rel.setBackgroundResource(R.drawable.biankuang_r);
		Title1.addView(rel, getScreenHeight() * 50 / 580, getScreenHeight() * 46 / 580);
		Title1.addView(title1);
		title1.setText("智慧课堂");
		title1.setPadding(getScreenWidth() / 4 + 50, 0, getScreenWidth() / 4 + 50, 0);
		title1.setTextColor(0xFFFFFFFF);
		title1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, getScreenHeight() * 46 / 580));
		title1.setGravity(Gravity.CENTER);
		title1.setTextSize(18);
		TextView t = new TextView(this);
		Title1.addView(t, new RelativeLayout.LayoutParams(getScreenHeight() * 25 / 580, getScreenHeight() * 25 / 580));
		rel.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (Constants.usertype == 1)
				{
					mShakeListener.stop();
				}
				finish();
			}
		});
	}

	public void title2()
	{
		Title2.setBackgroundColor(Color.WHITE);
		Title2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(getScreenWidth() / 4, getScreenHeight() / 12);
		for (int i = 0; i < 4; i++)
		{
			fenlei[i].setGravity(CENTER);
			fenlei[i].setText(fenlei_text[i]);
			fenlei[i].setTextColor(0xFFAAAAAA);
			fenlei[i].setTextSize(19);
			fenlei[i].setId(i);
			fenlei[i].setBackgroundResource(R.drawable.biankuang1);
			Title2.addView(fenlei[i], lp);
			fenlei[i].setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					for (int j = 0; j < 4; j++)
					{
						fenlei[j].setBackgroundResource(R.drawable.biankuang1);
						fenlei[j].setTextColor(0xFFAAAAAA);
					}
					v.setBackgroundResource(R.drawable.biankuang_press);
					fenlei[v.getId()].setTextColor(0xFF27BDFD);
					fenlei_pack.removeAllViews();
					if (v.getId() == 0 && Constants.usertype == 1)
						fenlei_pack.addView(fenleiLayout[v.getId()]);
					else if (v.getId() == 0 && Constants.usertype == 2)
						fenlei_pack.addView(fenleiLayout[4]);
					else
						fenlei_pack.addView(fenleiLayout[v.getId()]);
					Current_layout = v.getId();
				}
			});
		}
		fenlei[0].setBackgroundResource(R.drawable.biankuang_press);
		fenlei[0].setTextColor(0xFF27BDFD);
		Intent intent = getIntent();
		String s = intent.getStringExtra("title");
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundResource(R.drawable.biankuang_4);
		layout.setOrientation(HORIZONTAL);
		layout.setGravity(CENTER);
		tv = new TextView(this);
		tv.setText(s);
		tv.setTextColor(0xFF666666);
		tv.setTextSize(15);
		tv.setGravity(Gravity.CENTER);
		tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, getScreenHeight() / 15));
		layout.addView(tv);
		Title3.addView(layout, new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 12));
	}

	TextView tv;
	TextView daojishi;
	EditText input;
	TextView tishi;
	ImageView image;

	/** 学生点名界面 */
	public void setLayout00()
	{
		DianMingNum.setText("第一次点名");
		DianMingNum.setTextColor(0xFF27BDFC);
		DianMingNum.setTextSize(25);
		DianMingNum.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 12));
		DianMingNum.setBackgroundResource(R.drawable.biankuang_4);
		DianMingNum.setGravity(Gravity.CENTER);
		fenleiLayout[0].addView(DianMingNum);
		LinearLayout daojis = new LinearLayout(this);
		daojis.setOrientation(LinearLayout.HORIZONTAL);
		daojis.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		daojishi = new TextView(this);
		TextView daojishi1 = new TextView(this);
		daojishi.setText("30");
		daojishi.setTextSize(60);
		daojishi.setTextColor(0xFF27BDFD);
		daojishi1.setText("秒");
		daojishi1.setTextSize(15);
		daojishi1.setTextColor(0xFF27BDFD);
		daojishi1.setGravity(Gravity.BOTTOM);
		daojis.addView(daojishi);
		daojis.addView(daojishi1);
		// daojis.setGravity(Gravity.CENTER);
		fenleiLayout[0].setGravity(Gravity.CENTER);
		fenleiLayout[0].addView(daojis, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, getScreenHeight() / 580 * 120));
		tishi = new TextView(this);
		tishi.setText("请输入该数字(" + yanzhengma + ")后摇动你的手机:");
		tishi.setTextColor(0xFF666666);
		tishi.setTextSize(13);
		tishi.setGravity(Gravity.CENTER);
		tishi.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 18));
		fenleiLayout[0].addView(tishi, new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 18));
		LinearLayout input_layout = new LinearLayout(this);
		input_layout.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 10));
		input_layout.setGravity(Gravity.CENTER);
		input = new EditText(this);
		input.setHint("请输入提示数字");
		input.setBackgroundResource(R.drawable.text_bg);
		input_layout.addView(input, new LinearLayout.LayoutParams(getScreenWidth() / 4 * 3, getScreenHeight() / 15));
		fenleiLayout[0].addView(input_layout);
		LinearLayout cuowu = new LinearLayout(this);
		cuowu.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 20));
		cuowu.setOrientation(LinearLayout.HORIZONTAL);
		cuowu.setGravity(Gravity.CENTER);
		TextView cuowu_text = new TextView(this);
		cuowu.addView(cuowu_text);
		cuowu_text.setText("签到状态");
		cuowu_text.setTextColor(0xFF999999);
		fenleiLayout[0].addView(cuowu);
		LinearLayout ima_layout = new LinearLayout(this);
		ima_layout.setOrientation(LinearLayout.HORIZONTAL);
		ima_layout.setGravity(Gravity.CENTER);
		image = new ImageView(this);
		image.setBackgroundResource(R.drawable.yaoyiyao_07);
		ima_layout.addView(image, new LinearLayout.LayoutParams(getScreenWidth() / 3, getScreenWidth() / 3));
		fenleiLayout[0].addView(ima_layout);
	}

	int yaoNum = 0;
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 1:
				stuState = 1;
			}
		}
	};
	Runnable runnable = new Runnable()
	{
		@Override
		public void run()
		{
			
			if (Constants.usertype == 1)
			{
				try
				{
					handler.postDelayed(this, 1000);
					time -= 1;
					daojishi.setText("" + time);
					if (daojishi.getText().equals("0"))
					{
						Toast.makeText(getApplicationContext(), "点名失败,快去联系老师吧", Toast.LENGTH_SHORT).show();
						handler.removeCallbacks(runnable);
					}
					if (stuState == 1)
					{
						isQiandao = true;
					}
					if (stuState == 0)
					{
						// fenlei_pack.removeAllViews();
						// fenlei_pack.addView(fenleiLayout[5]);
						Toast.makeText(getApplicationContext(), "点名失败,快联系老师吧", Toast.LENGTH_SHORT).show();
						handler.removeCallbacks(runnable);
					}
					// if (isQiandao == true)
					// {
					// fenlei_pack.removeAllViews();
					// fenlei_pack.addView(fenleiLayout[5]);
					// handler.removeCallbacks(runnable);
					// }
				} catch (Exception e)
				{
					Toast.makeText(getApplicationContext(), "点名失败,快去联系老师吧", Toast.LENGTH_SHORT).show();
				}
			} else if (Constants.usertype == 2)
			{
				handler.postDelayed(this, 1000);
				time -= 5;
				// 刷新学生点名列表
				// ????
				if (time <= 0)
				{
					handler.removeCallbacks(runnable);
				}
			}
		}
	};
	int dianmingNum = 1;
	/** 学生列表按钮 */
	List<Button> stu;
	private List<ImageView> state_image;
	TextView stuNum1;
	/** 老师点名界面 */
	private String[] dmcsArray = new String[] { "第一次点名", "第二次点名", "第三次点名", "第四次点名" };
	private int dmcsjsq = 0;

	public void setLayout01()
	{
		fenleiLayout[4].removeAllViews();
		fenleiLayout[4].removeAllViewsInLayout();
		state_image = new ArrayList<ImageView>();
		this.stu = new ArrayList<Button>();
		LinearLayout title = new LinearLayout(this);
		title.setOrientation(LinearLayout.HORIZONTAL);
		title.setGravity(Gravity.CENTER);
		ImageView arrow_l = new ImageView(this);
		final TextView dianming = new TextView(this);
		ImageView arrow_r = new ImageView(this);
		title.addView(arrow_l);
		title.addView(dianming, new LinearLayout.LayoutParams(getScreenWidth() / 3 * 2, LayoutParams.WRAP_CONTENT));
		title.addView(arrow_r);
		arrow_l.setBackgroundResource(R.drawable.arrow_l_blue);
		arrow_r.setBackgroundResource(R.drawable.arrow_r_blue);
		arrow_l.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (dmcsjsq > 0)
				{
					dmcsjsq--;
					dianming.setText(dmcsArray[dmcsjsq]);
				}
			}
		});
		arrow_r.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (dmcsjsq < 3)
				{
					dmcsjsq++;
					dianming.setText(dmcsArray[dmcsjsq]);
				}
			}
		});
		dianming.setText(dmcsArray[0]);
		dianming.setTextColor(0xFF11A6E7);
		dianming.setGravity(Gravity.CENTER);
		dianming.setTextSize(25);
		LinearLayout l = new LinearLayout(this);
		l.setGravity(Gravity.CENTER);
		stuNum1 = new TextView(this);
		l.addView(stuNum1);
		stuNum1.setText("应到" + stuList.size() + "人\t\t实到" + 0 + "人");
		stuNum1.setTextSize(17);
		fenleiLayout[4].addView(title);
		fenleiLayout[4].addView(l);
		for (int i = 0; i < stuList.size(); i++)
		{
			Button stu = new Button(this);
			stu.setText(stuList.get(i).get("XM").toString());
			stu.setId(i);
			stu.setTextSize(14);
			stu.setTag(stuList.get(i).get("XH").toString());
			stu.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View arg0)
				{
					showPopupWindow(arg0.getId());
				}
			});
			this.stu.add(stu);
		}
		ScrollView sc = new ScrollView(this);
		sc.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 580 * 300));
		RelativeLayout layout1 = new RelativeLayout(this);
		sc.addView(layout1);
		for (int i = 0; i < stu.size(); i++)
		{
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(getScreenWidth() / 340 * 75, getScreenHeight() / 580 * 50);
			lp.leftMargin = getScreenWidth() / 340 * 60 / 5 * (i % 4) + getScreenWidth() / 340 * 75 * (i % 4) + getScreenWidth() / 340 * 60 / 5;
			lp.topMargin = getScreenHeight() / 25 + getScreenHeight() / 580 * 50 * (i / 4) + getScreenHeight() / 580 * 13 * (i / 4);
			this.stu.get(i).setBackgroundResource(R.drawable.zhkt_dm_no);
			layout1.addView(this.stu.get(i), lp);
			RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(getScreenWidth() / 340 * 10, getScreenHeight() / 580 * 10);
			lp2.leftMargin = lp.leftMargin + getScreenWidth() / 340 * 70;
			lp2.topMargin = lp.topMargin - getScreenWidth() / 340 * 5;
			ImageView image = new ImageView(this);
			state_image.add(image);
			image.setBackgroundResource(R.drawable.dianming_no);
			layout1.addView(image, lp2);
		}
		fenleiLayout[4].addView(sc);
		LinearLayout llayout = new LinearLayout(this);
		LinearLayout.LayoutParams dmlp = new LinearLayout.LayoutParams(getScreenWidth() / 340 * 99 * 3 / 2, getScreenHeight() / 580 * 32 * 3 / 2);
		Button ksdm = new Button(this);
		Button tjdm = new Button(this);
		ksdm.setText("开始点名");
		ksdm.setOnClickListener(new fqdm());
		tjdm.setText("提交");
		tjdm.setOnClickListener(new tjdm());
		llayout.setGravity(Gravity.CENTER);
		llayout.setPadding(0, 10, 0, 0);
		llayout.setOrientation(LinearLayout.HORIZONTAL);
		llayout.addView(ksdm, dmlp);
		llayout.addView(tjdm, dmlp);
		fenleiLayout[4].addView(llayout, new LinearLayout.LayoutParams(getScreenWidth(), LayoutParams.WRAP_CONTENT));
	}

	/** 学生签到成功界面 */
	public void setLayout02()
	{
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		layout.setGravity(Gravity.CENTER);
		ImageView image = new ImageView(this);
		image.setBackgroundResource(R.drawable.test);
		layout.addView(image, new LinearLayout.LayoutParams(getScreenWidth() / 3 * 2, getScreenWidth() / 3 * 2 / 4 * 6));
		TextView tishi = new TextView(this);
		tishi.setText("签到成功");
		tishi.setTextSize(30);
		fenleiLayout[5].addView(layout, new LinearLayout.LayoutParams(getScreenWidth(), LayoutParams.MATCH_PARENT));
	}

	public void setLayout1()
	{
		ScrollView sc = new ScrollView(this);
		final LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundColor(0xFFFFFFFF);
		layout.setOrientation(VERTICAL);
		sc.setBackgroundColor(0xFFFFFFFF);
		sc.addView(layout);
		fenleiLayout[1].addView(sc, new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 3 * 2 - 20));
		LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.HORIZONTAL);
		layout2.setGravity(Gravity.CENTER);
		final EditText input = new EditText(this);
		Button fasong = new Button(this);
		fasong.setBackgroundResource(R.drawable.fillet);
		fasong.setText("发送");
		fasong.setTextColor(0xffffffff);
		fasong.setGravity(Gravity.CENTER);
		layout2.addView(input, new LinearLayout.LayoutParams(getScreenWidth() / 4 * 3 - 10, getScreenHeight() / 14));
		layout2.addView(fasong, new LinearLayout.LayoutParams(getScreenWidth() / 4 - 10, getScreenHeight() / 14));
		fenleiLayout[1].addView(layout2);
		layout2.setBackgroundResource(R.drawable.t_biankuang);
		fasong.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}

	LinearLayout.LayoutParams lp;
	int[] pingjia = new int[] { 0, 0, 0, 0 };
	String text[] = new String[] { "课堂互动性", "教学和理性", "评价标准3", "评价标准4" };
	String jianyi_text;
	/** 评价界面 */
	int title1_size = 18;
	int title2_size = 15;

	public void setLayout20()
	{
		lp = new LinearLayout.LayoutParams(getScreenHeight() / 580 * 23, getScreenHeight() / 580 * 23);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		fenleiLayout[2].addView(layout, new LinearLayout.LayoutParams(getScreenWidth(), LayoutParams.MATCH_PARENT));
		TextView xzpj = new TextView(this);
		xzpj.setText("请选择您对这堂课的评价:");
		xzpj.setPadding(20, 0, 0, 0);
		xzpj.setTextSize(title1_size);
		xzpj.setTextColor(0xFF414141);
		layout.addView(xzpj);
		final ImageView image[] = new ImageView[20];
		LinearLayout choose_score[] = new LinearLayout[4];
		TextView choose_Text[] = new TextView[4];
		final EditText jianyi_text = new EditText(this);
		for (int i = 0; i < 4; i++)
		{
			choose_score[i] = new LinearLayout(this);
			choose_Text[i] = new TextView(this);
			choose_Text[i].setText(text[i]);
			choose_Text[i].setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth() / 3, getScreenHeight() / 580 * 23));
			choose_Text[i].setTextSize(title2_size);
			choose_Text[i].setTextColor(0xFF414141);
			choose_score[i].setPadding(50, 20, 0, 10);
			choose_score[i].addView(choose_Text[i]);
			layout.addView(choose_score[i]);
		}
		for (int i = 0; i < 20; i++)
		{
			image[i] = new ImageView(this);
			image[i].setBackgroundResource(R.drawable.pingjia_no);
			image[i].setId(i);
			TextView add_zreo = new TextView(this);
			choose_score[i / 5].addView(image[i], lp);
			choose_score[i / 5].addView(add_zreo, 15, getScreenHeight() / 1000);
			image[i].setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					
					int num = v.getId();
					classContent.this.jianyi_text = jianyi_text.getText().toString();
					pingjia[num / 5] = num % 5 + 1;
					int startNum = num / 5 * 5;
					int endNum = num / 5 * 5 + 4;
					for (int i = startNum; i <= endNum; i++)
					{
						if (i <= num)
							image[i].setBackgroundResource(R.drawable.pingjia);
						else
							image[i].setBackgroundResource(R.drawable.pingjia_no);
					}
				}
			});
		}
		TextView jianyi = new TextView(this);
		jianyi.setText("给教师的建议或意见(非必填):");
		jianyi.setPadding(0, 10, 0, 10);
		jianyi.setTextSize(title2_size);
		jianyi.setTextColor(0xff000000);
		layout.addView(jianyi);
		LinearLayout jianyi_layout = new LinearLayout(this);
		jianyi_layout.setGravity(Gravity.CENTER);
		jianyi_text.setHint("点击这里输入(0/140);");
		jianyi_text.setTextSize(size);
		jianyi_text.setBackgroundResource(R.drawable.text_bg);
		jianyi_text.setGravity(Gravity.TOP);
		jianyi_layout.addView(jianyi_text, (int) (getScreenWidth() * 0.9), getScreenHeight() / 5);
		layout.addView(jianyi_layout, new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 5));
		CheckBox hintName = new CheckBox(this);
		hintName.setText("匿名提交");
		hintName.setPadding(10, 20, 0, 20);
		hintName.setTextColor(0xFF676767);
		hintName.setButtonDrawable(getResources().getDrawable(R.drawable.wisdom_select));
		layout.addView(hintName);
		Button tijiao = new Button(this);
		tijiao.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth() - 100, getScreenHeight() / 20));
		tijiao.setText("提交评价");
		tijiao.setGravity(Gravity.CENTER);
		tijiao.setBackgroundResource(R.drawable.fillet);
		tijiao.setTextColor(0xFFFFFFFF);
		tijiao.setPadding(50, 20, 50, 20);
		tijiao.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				v.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
				v.getBackground().clearColorFilter();
				setLayout21();
				fenlei_pack.removeAllViews();
				fenlei_pack.addView(fenleiLayout[6]);
			}
		});
		LinearLayout tj_layout = new LinearLayout(this);
		tj_layout.setOrientation(LinearLayout.HORIZONTAL);
		tj_layout.addView(tijiao, new LinearLayout.LayoutParams(getScreenWidth() / 4 * 3, LayoutParams.MATCH_PARENT));
		tj_layout.setGravity(Gravity.CENTER);
		layout.addView(tj_layout);
	}

	public void setLayout21()
	{
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		TextView jieguo_text = new TextView(this);
		jieguo_text.setText("您对这堂课的评价结果是:");
		jieguo_text.setTextSize(title1_size);
		layout.addView(jieguo_text);
		TextView[] jieguofenlei = new TextView[4];
		for (int i = 0; i < 4; i++)
		{
			LinearLayout l = new LinearLayout(this);
			l.setOrientation(LinearLayout.HORIZONTAL);
			jieguofenlei[i] = new TextView(this);
			jieguofenlei[i].setText(text[i]);
			jieguofenlei[i].setGravity(Gravity.CENTER);
			l.setPadding(20, 20, 0, 10);
			;
			l.addView(jieguofenlei[i], getScreenWidth() / 3, getScreenHeight() / 580 * 23);
			for (int j = 0; j < pingjia[i]; j++)
			{
				ImageView image = new ImageView(this);
				image.setBackgroundResource(R.drawable.pingjia);
				l.addView(image, new LinearLayout.LayoutParams(getScreenHeight() / 580 * 23, getScreenHeight() / 580 * 23));
				TextView add_zreo = new TextView(this);
				l.addView(add_zreo, 15, getScreenHeight() / 1000);
			}
			layout.addView(l);
		}
		TextView jianyi = new TextView(this);
		jianyi.setText("给教师的建议或意见:");
		jianyi.setPadding(20, 0, 0, 0);
		jianyi.setTextSize(title2_size);
		layout.addView(jianyi);
		TextView jianyi_text = new TextView(this);
		jianyi_text.setText(this.jianyi_text);
		layout.addView(jianyi_text);
		fenleiLayout[6].addView(layout);
	}

	public void setLayout3()
	{
		ScrollView sc = new ScrollView(this);
		final LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundColor(0xFFEEF3FA);
		layout.setOrientation(LinearLayout.VERTICAL);
		sc.setBackgroundColor(0xFFEEF3FA);
		sc.addView(layout);
		fenleiLayout[3].addView(sc, new LinearLayout.LayoutParams(getScreenWidth(), getScreenHeight() / 4 * 3 - 40));
		LinearLayout layout2 = new LinearLayout(this);
		layout2.setOrientation(LinearLayout.HORIZONTAL);
		layout2.setGravity(Gravity.CENTER);
		final EditText input = new EditText(this);
		Button fasong = new Button(this);
		fasong.setBackgroundResource(R.drawable.fillet);
		fasong.setText("发送");
		fasong.setTextColor(0xffffffff);
		fasong.setGravity(Gravity.CENTER);
		layout2.setGravity(Gravity.BOTTOM);
		layout2.addView(input, new LinearLayout.LayoutParams(getScreenWidth() / 4 * 3 - 10, LayoutParams.WRAP_CONTENT));
		layout2.addView(fasong, new LinearLayout.LayoutParams(getScreenWidth() / 4 - 10, LayoutParams.WRAP_CONTENT));
		fenleiLayout[3].addView(layout2);
		fasong.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				
			}
		});
	}

	int noText = 0;
	Vibrator mVibrator;

	class LSshakeLitener implements OnShakeListener
	{
		private String yanzhengma = "";

		public LSshakeLitener(String yanzhengma)
		{
			this.yanzhengma = yanzhengma;
		}

		@Override
		public void onShake()
		{
			if (input.getText() != null && Current_layout == 0 && yanzhengma.equals(input.getText().toString()))
			{
				zhktAT AT = new zhktAT(1, hhandler);
				AT.execute(new String[] { Constants.number, xkkh });
				if (yaoNum == 0)
				{
					yaoNum++;
					mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					long[] pattern = { 100, 400, 100, 400 }; // 停止 开启 停止 开启
					mVibrator.vibrate(pattern, -1); // 开始震动
					Toast.makeText(getApplicationContext(), "摇了一摇!", Toast.LENGTH_SHORT).show();
				}
				handler.postDelayed(runnable, 1000);
				mShakeListener.stop();
			} else if (Current_layout == 0 && (input.getText() != null || !input.getText().equals("")) && !input.getText().equals(yanzhengma))
			{
				noText++;
				Toast.makeText(getApplicationContext(), "提示数字错误", Toast.LENGTH_SHORT).show();
			} else if (noText == 0 && Current_layout == 0)
			{
				noText++;
				Toast.makeText(getApplicationContext(), "请输入提示数字", Toast.LENGTH_SHORT).show();
			}
		}
	}

	int size = 15;

	public LinearLayout addMessage(String name, String date, String content, int type)
	{
		LinearLayout layout = new LinearLayout(this);
		LinearLayout msgName = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		msgName.setOrientation(LinearLayout.HORIZONTAL);
		TextView name_text = new TextView(this);
		TextView date_text = new TextView(this);
		name_text.setText(name);
		date_text.setText(date);
		name_text.setTextColor(0xFF393939);
		date_text.setTextColor(0xFF9A9A9A);
		name_text.setTextSize(size);
		date_text.setTextSize(size - 1);
		if (type == 0)
		{
			msgName.setGravity(Gravity.LEFT);
			msgName.addView(name_text, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			name_text.setPadding(50, 0, 20, 0);
			msgName.addView(date_text, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			name_text.setGravity(Gravity.CENTER);
			date_text.setGravity(Gravity.LEFT);
		} else
		{
			msgName.setGravity(Gravity.RIGHT);
			msgName.addView(date_text, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			name_text.setPadding(20, 0, 50, 0);
			msgName.addView(name_text, new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			name_text.setGravity(Gravity.CENTER);
			date_text.setGravity(Gravity.RIGHT);
		}
		ImageView image_l = new ImageView(this);
		image_l.setBackgroundResource(R.drawable.liaotian_qipao_l);
		layout.addView(msgName);
		if (type == 1)
			layout.addView(showMessage_me(content));
		else
			layout.addView(showMessage_other(content));
		layout.setPadding(0, 10, 0, 10);
		return layout;
	}

	/**
	 * 返回屏幕宽度
	 * 
	 * @return 屏幕宽度
	 */
	public int getScreenWidth()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width = dm.widthPixels;
		return width;
	}

	public RelativeLayout showMessage_other(String content)
	{
		final RelativeLayout rel = new RelativeLayout(this);
		ImageView image_tl = new ImageView(this);
		RelativeLayout.LayoutParams lp_tl = new RelativeLayout.LayoutParams(getScreenHeight() * 9 / 580, getScreenHeight() * 9 / 580);
		image_tl.setBackgroundResource(R.drawable.message_tl);
		lp_tl.leftMargin = getScreenHeight() / 580 * 60;
		lp_tl.topMargin = getScreenHeight() / 580 * 10 + 4;
		rel.addView(image_tl, lp_tl);
		final ImageView image_tr = new ImageView(this);
		final TextView message_text = new TextView(this);
		message_text.setPadding(10, 10, 10, 10);
		message_text.setTextColor(0xFFFFFFFF);
		message_text.setTextSize(15);
		message_text.setText(content);
		RelativeLayout.LayoutParams lp_messate_text = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		message_text.setBackgroundColor(0xFF35C3FF);
		message_text.setMinHeight(getScreenHeight() * 38 / 580);
		message_text.setMaxWidth(getScreenHeight() * (320 - 60 - 9 - 4) / 580);
		lp_messate_text.leftMargin = getScreenHeight() * 9 / 580 + getScreenHeight() / 580 * 60;
		lp_messate_text.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5;
		rel.addView(message_text, lp_messate_text);
		final ImageView image_br = new ImageView(this);
		ViewTreeObserver vto2 = message_text.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{
				message_text.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				RelativeLayout.LayoutParams lp_br = new RelativeLayout.LayoutParams(getScreenHeight() * 27 / 580, message_text.getHeight() - getScreenHeight() * 27 / 580);
				image_br.setBackgroundColor(0xFF35C3FF);
				lp_br.leftMargin = getScreenHeight() / 580 * (60 + 9) + message_text.getWidth();
				lp_br.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5 + getScreenHeight() * 27 / 580;
				rel.addView(image_br, lp_br);
				RelativeLayout.LayoutParams lp_tr = new RelativeLayout.LayoutParams(getScreenHeight() * 27 / 580, getScreenHeight() * 27 / 580);
				image_tr.setBackgroundResource(R.drawable.message_tr);
				lp_tr.leftMargin = getScreenHeight() / 580 * (60 + 9) + message_text.getWidth();
				lp_tr.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5;
				rel.addView(image_tr, lp_tr);
			}
		});
		return rel;
	}

	RelativeLayout.LayoutParams lp_messate_text;

	public RelativeLayout showMessage_me(String content)
	{
		final RelativeLayout rel = new RelativeLayout(this);
		final ImageView image_tl = new ImageView(this);
		ImageView image_tr = new ImageView(this);
		RelativeLayout.LayoutParams lp_tr = new RelativeLayout.LayoutParams(getScreenHeight() * 9 / 580, getScreenHeight() * 9 / 580);
		image_tr.setBackgroundResource(R.drawable.o_m_r);
		lp_tr.leftMargin = getScreenHeight() / 580 * 321;
		lp_tr.topMargin = getScreenHeight() / 580 * 10 + 4;
		rel.addView(image_tr, lp_tr);
		final TextView message_text = new TextView(this);
		message_text.setPadding(10, 10, 10, 10);
		message_text.setMaxWidth(getScreenWidth() / 220 * 150);
		message_text.setTextColor(0xFFFFFFFF);
		message_text.setTextSize(15);
		message_text.setText(content);
		lp_messate_text = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		message_text.setBackgroundColor(0xFFFF9933);
		message_text.setMinHeight(getScreenHeight() * 38 / 580);
		lp_messate_text.leftMargin = getScreenHeight() * 9 / 580 + getScreenHeight() / 580 * 60;
		lp_messate_text.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5;
		rel.addView(message_text, lp_messate_text);
		final ImageView image_br = new ImageView(this);
		ViewTreeObserver vto2 = message_text.getViewTreeObserver();
		vto2.addOnGlobalLayoutListener(new OnGlobalLayoutListener()
		{
			@Override
			public void onGlobalLayout()
			{
				message_text.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				RelativeLayout.LayoutParams lp_br = new RelativeLayout.LayoutParams(getScreenHeight() * 27 / 580, message_text.getHeight() - getScreenHeight() * 27 / 580);
				image_br.setBackgroundColor(0xFFFF9933);
				int width = message_text.getWidth();
				lp_br.leftMargin = getScreenHeight() / 580 * (321 - 27) - width;
				lp_br.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5 + getScreenHeight() * 27 / 580;
				rel.addView(image_br, lp_br);
				rel.removeView(message_text);
				lp_messate_text.leftMargin = getScreenHeight() / 580 * 321 - width;
				rel.addView(message_text);
				RelativeLayout.LayoutParams lp_tl = new RelativeLayout.LayoutParams(getScreenHeight() * 27 / 580, getScreenHeight() * 27 / 580);
				image_tl.setBackgroundResource(R.drawable.o_m_l);
				lp_tl.leftMargin = getScreenHeight() / 580 * (321 - 27) - message_text.getWidth();
				lp_tl.topMargin = getScreenHeight() / 580 * 10 + getScreenHeight() / 580 * 5;
				rel.addView(image_tl, lp_tl);
			}
		});
		return rel;
	}

	/**
	 * 返回屏幕高度
	 * 
	 * @return 屏幕高度
	 */
	public int getScreenHeight()
	{
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int height = dm.heightPixels;
		return height;
	}

	PopupWindow popupWindow;

	public void showPopupWindow(int currentStu)
	{
		// 一个自定义的布局，作为显示的内容
		View contentView = LayoutInflater.from(classContent.this).inflate(R.layout.zhkt_pop, null);
		popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setAnimationStyle(R.style.PopupAnimation);
		popupWindow.setTouchInterceptor(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.buttom_biankuang));
		popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
		LinearLayout zhkt_dq = (LinearLayout) contentView.findViewById(R.id.zhkt_dq);
		LinearLayout zhkt_cd = (LinearLayout) contentView.findViewById(R.id.zhkt_cd);
		LinearLayout zhkt_zt = (LinearLayout) contentView.findViewById(R.id.zhkt_zt);
		LinearLayout zhkt_kk = (LinearLayout) contentView.findViewById(R.id.zhkt_kk);
		LinearLayout zhkt_bj = (LinearLayout) contentView.findViewById(R.id.zhkt_bj);
		LinearLayout zhkt_sj = (LinearLayout) contentView.findViewById(R.id.zhkt_sj);
		zhkt_dq.setOnClickListener(new myClick1(currentStu));
		zhkt_cd.setOnClickListener(new myClick2(currentStu));
		zhkt_zt.setOnClickListener(new myClick2(currentStu));
		zhkt_kk.setOnClickListener(new myClick2(currentStu));
		zhkt_bj.setOnClickListener(new myClick2(currentStu));
		zhkt_sj.setOnClickListener(new myClick2(currentStu));
	}

	private String ktdmid = null;
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 0:
				Toast.makeText(getApplicationContext(), "操作失败,请重试", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				isQiandao = true;
				Toast.makeText(getApplicationContext(), "签到成功", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "发起点名成功", Toast.LENGTH_SHORT).show();
				ktdmid = msg.getData().getString("ktdmid");
				Timer t = new Timer();
				t.schedule(new TimerTask()
				{
					@Override
					public void run()
					{
						ydmxslb y = new ydmxslb(handler2);
						y.execute(xkkh, Constants.number);
					}
				}, 5000);
				break;
			case 3:
				getKTXSLB(msg.getData().toString());
				setLayout01();
				// 开启刷新已点名学生
				Toast.makeText(getApplicationContext(), "获取学生列表成功", Toast.LENGTH_SHORT).show();
				break;
			case 5:
				isFlesh = false;
				tjjsq++;
				if (tjjsq == tjcs_i)
				{
					Toast.makeText(getApplicationContext(), "提交点名信息成功", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			default:
				break;
			}
		}
	};
	private boolean isFlesh = true;
	Handler handler2 = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			try
			{
				String str = null;
				JSONArray jsa = null;
				try
				{
					str = msg.getData().getString("ydmxslb");
					jsa = new JSONArray(str);
				} catch (Exception e)
				{
					
					return;
				}
				xsdmzt = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < jsa.length(); i++)
				{
					for (int stunum = 0; stunum < stu.size(); stunum++)
					{
						if (jsa.getJSONObject(i).getString("xh").equals(stu.get(stunum).getTag().toString()))
						{
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("xh", jsa.getJSONObject(i).getString("xh"));
							map.put("dmzt", "1");
							xsdmzt.add(map);
							stu.get(stunum).setBackgroundResource(R.drawable.zhkt_dm_yes);
							break;
						}
					}
				}
				stuNum1.setText("应到" + stuList.size() + "人\t\t实到" + xsdmzt.size() + "人");
				if (isFlesh)
				{
					final Timer t = new Timer();
					t.schedule(new TimerTask()
					{
						@Override
						public void run()
						{
							ydmxslb y = new ydmxslb(handler2);
							y.execute(xkkh, Constants.number);
							t.cancel();
						}
					}, 5000);
				}
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	};

	class myClick1 implements View.OnClickListener
	{
		int i = 0;

		public myClick1(int j)
		{
			i = j;
		}

		@Override
		public void onClick(View v)
		{
			
			state_image.get(i).setBackgroundResource(R.drawable.dianming_yes);
			stu.get(i).setBackgroundResource(R.drawable.zhkt_dm_yes);
			popupWindow.dismiss();
		}
	}

	class myClick2 implements View.OnClickListener
	{
		int i = 0;

		public myClick2(int j)
		{
			i = j;
		}

		@Override
		public void onClick(View v)
		{
			
			state_image.get(i).setBackgroundResource(R.drawable.dianming_no);
			stu.get(i).setBackgroundResource(R.drawable.zhkt_dm_no);
			popupWindow.dismiss();
		}
	}

	class myClick3 implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			
			popupWindow.dismiss();
		}
	}

	class fqdm implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			zhktAT AT = new zhktAT(2, mHandler);
			// xkkh djz xqj djj dmcs jsbh
			AT.execute(new String[] { xkkh, djz, xqj, djj.split("-")[0], String.valueOf(dmcsjsq), Constants.number });
		}
	}

	class xsdm implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			
			zhktAT AT = new zhktAT(1, hhandler);
			AT.execute(new String[] { Constants.number, xkkh });
		}
	}

	class tjdm implements View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			for (int j = 0; j < stu.size(); j++)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < xsdmzt.size(); i++)
				{
					if (xsdmzt.get(i).get("xh").toString().equals(stu.get(j).getTag().toString()))
					{
						tjlist.add(xsdmzt.get(i));
						break;
					} else
					{
						map.put("xh", stu.get(j).getTag().toString());
						map.put("dmzt", "0");
						if (i == xsdmzt.size() - 1)
						{
							tjlist.add(map);
						}
					}
				}
			}
			List<Map<String, Object>> maplist = new ArrayList<Map<String, Object>>();
			tjcs_i = (tjlist.size() - 1) / 10 + 1;
			for (int i = 1; i <= tjlist.size(); i++)
			{
				maplist.add(tjlist.get(i - 1));
				if (i / 10 == 0)
				{
					zhktAT AT = new zhktAT(5, mHandler);
					AT.execute(ktdmid, new JsonListMap().MapToJson(maplist));
					maplist = new ArrayList<Map<String, Object>>();
				}
			}
		}
	}

	private int tjcs_i;
	private int tjjsq = 0;

	private void getKTXSLB(String result)
	{
		result = result.replace("Bundle", "");
		result = result.substring(9, result.length() - 2);
		try
		{
			stuJSONArray = new JSONArray(result);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i < stuJSONArray.length(); i++)
		{
			JSONObject jsO;
			Map<String, String> map = new HashMap<String, String>();
			try
			{
				jsO = stuJSONArray.getJSONObject(i);
				map.put("XH", jsO.getString("XH"));
				map.put("XM", jsO.getString("XM"));
				stuList.add(map);
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
	}

	Handler hhandler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what)
			{
			case 1:
				stuState = 1;
				fenlei_pack.removeAllViews();
				fenlei_pack.addView(fenleiLayout[5]);
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
		{
			if (Constants.usertype == 1)
			{
				mShakeListener.stop();
			}
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	};
}
