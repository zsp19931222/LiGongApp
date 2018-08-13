package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.JsonListMap;import com.yhkj.cqgyxy.R;
import yh.app.tool.SqliteHelper;
import yh.app.tool.ToastShow;
import yh.app.utils.DaoJiShiCountTimer;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import yh.app.wisdomclass.ShakeListener.OnShakeListener;
import yh.app.zhkttools.zhkt_handler;
import yh.app.zhkttools.zhkt_handler_interface;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.ScrollViewTools;

/**
 * 智慧课题学生点名
 * 
 * @author 云华科技
 * @date 2017年5月8日
 */
public class zhkt extends ActivityPortrait implements View.OnClickListener {
	private PagerAdapter mPagerAdapter;
	private List<View> mViewList = new ArrayList<View>();
	private LinearLayout zhkt_dm_layout;
	private LinearLayout zhkt_zt_layout;
	private LinearLayout zhkt_pj_layout;
	private LinearLayout zhkt_bz_layout;
	private TextView zhkt_sjyzm, zhkt_djs, dmcs_tv, zhkt_ts;
	private EditText zhkt_yzm_input;
	private android.support.v4.view.ViewPager mViewPager;
	private ShakeListener mShakeListener = null;
	private int yzm;
	private Context context;
	private LinearLayout xslayout;
	private String ktdmid;
	private String ktbh;
	private ImageView img_l, img_r, zhkt_yytp;
	private zhkt_handler_interface interFace;
	private StringBuffer teacher_list = new StringBuffer();
	private ZHKTLslb zhktLslb;
	public static boolean canChange = false;
	private LinearLayout[] pj;
	// 保存学生评价分数
	private int[] pjdf = new int[4];
	private LoadDiaog diaog;
	private DaoJiShiCountTimer daojishiTime;
	private PopupWindow diaojishiwindow;
	private TextView txt_daojishinum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhkt_home);
		diaog = new LoadDiaog(this);
		SQLiteDatabase db = new SqliteHelper().getRead();
		Cursor c = db.rawQuery("select usertype from usertype where userid=?", new String[] { Constants.number });
		if (c.moveToFirst()) {
			Constants.usertype = c.getInt(0);
		}
		c.close();
		db.close();
		context = this;
		initHandlerInterface();
		init();
		initView();
		initViewPager();
		initAction(Constants.usertype);
		
	}
	
	/**
	 * 倒计时弹出窗
	 * @param view
	 */
	private void showUpdateHandPhoto(View view) {
		View window = LayoutInflater.from(this).inflate(R.layout.popupwindow_layout, null, false);

		// 设置窗体大小
		diaojishiwindow = new PopupWindow(window, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);

		diaojishiwindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		diaojishiwindow.setBackgroundDrawable(new ColorDrawable());
		diaojishiwindow.setOutsideTouchable(false);
		diaojishiwindow.setFocusable(true);// 点击外部消失
		window.setFocusableInTouchMode(true);
		diaojishiwindow.showAtLocation(view, Gravity.CENTER, 0, 0);
		//开始播放倒计时
		txt_daojishinum=(TextView) window.findViewById(R.id.txt_daojishinum);
		daojishiTime=new DaoJiShiCountTimer(30*1000, 1*1000, txt_daojishinum, "发起点名",fqdm,diaojishiwindow);
		daojishiTime.start();
		
		

	}

	private void initHandlerInterface() {
		interFace = new zhkt_handler().getInterface();
	}

	private String xkkh = null;
	private String djz = null;
	private String djj = null;
	private String xqj = null;
	private String time = null;
	private String classname = null;

	private void init() {
		Intent intent = getIntent();
		xkkh = intent.getStringExtra("xkkh");
		djz = intent.getStringExtra("djz");
		djj = intent.getStringExtra("djj").split("-")[0];
		xqj = intent.getStringExtra("xqj");
		time = intent.getStringExtra("time");
		classname = intent.getStringExtra("classname");
		interFace.savektxx(getktbh, xkkh, djz, xqj, djj);
		barHelper = new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle("智慧课堂")
				.setOnClickLisener(new OnClickLisener() {
					@Override
					public void setRightOnClick() {
						tcbjzd = "home";
						if (!dmIng) {
							try {
								mShakeListener.stop();
							} catch (Exception e) {
							}
							close();
						} else {
							dialog("点名还未结束");
						}
					}

					@Override
					public void setLeftOnClick() {
						tcbjzd = "finish";
						if (!dmIng) {
							try {
								mShakeListener.stop();
							} catch (Exception e) {
							}
							close();
						} else {
							dialog("点名还未结束");
						}
					}

					@Override
					public void setExtraOnclick() {
						if (Constants.usertype == 1) {
							zhktLslb = new ZHKTLslb(context, teacher_list,
									(ListView) mView2.findViewById(R.id.zhkt_zt_layout), null, mView2,
									Constants.usertype + "");
							zhktLslb.getJslb(ktbh);
						}
						// new ZHKTLslb(context, teacher_list, (ListView)
						// mView2.findViewById(R.id.zhkt_zt_layout), null,
						// mView2).getJslb(ktbh);
					}
				});
	}

	private void dialog(String message) {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(message);
		builder.setTitle("提示");
		builder.setPositiveButton("仍要退出", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (Constants.usertype == 1)
					mShakeListener.stop();
				zhkt.this.close();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private void close() {
		if ("home".equals(tcbjzd)) {
//			Intent intent = new Intent();
//			intent.setAction("com.example.app3.HomePageActivity");
//			intent.setPackage(zhkt.this.getPackageName());
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			finish();
		} else if ("finish".equals(tcbjzd)) {
			finish();
		}
	}

	// 退出标记字段
	private String tcbjzd = "";
	private TopBarHelper barHelper;

	private void initAction(int i) {
		zhkt_dm_layout.setOnClickListener(this);
		zhkt_zt_layout.setOnClickListener(this);
		zhkt_pj_layout.setOnClickListener(this);
		zhkt_bz_layout.setOnClickListener(this);
		if (i == 2) {
			fqdm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					if (isFQDM && dqxzdmcs == dqdmcs) {
						diaog.show();
						
						fqdm.setEnabled(false);//设置不可点击
						interFace.fqdm(fqdm_handler, xkkh, djz, xqj, djj, "" + (dqdmcs + 1), Constants.number);
					} else if (isFQDM && dqxzdmcs != dqdmcs) {
						Toast.makeText(context, "请先切换到第" + (dqdmcs + 1) + "次点名", Toast.LENGTH_SHORT).show();
					}
				}
			});
			// 提交点名
			tjdm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					
					if (isTJDM && dqxzdmcs == dqdmcs){
						diaog.show();
						for (int i = 0; i < jsdmxs.gettjdmcs(ydmxslbList); i++) {
							String s = jsdmxs.getTjList(i);
							interFace.tjdm(tjdm_handler, ktdmid, s);
						}
						tjdm.setEnabled(false);
					}else if (isTJDM && dqxzdmcs == dqdmcs) {
						Toast.makeText(context, "请先切换到第" + (dqdmcs + 1) + "次点名", Toast.LENGTH_SHORT).show();
					}

				}
			});
			img_l.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (isFQDM) {
						if (dqxzdmcs >= 1) {
							dqxzdmcs--;
							dmcs_tv.setText("第 " + (dqxzdmcs + 1) + " 次点名");
							interFace.getsyxsdmjg(jsdmxs.handler, xkkh, djz, xqj, djj, dqxzdmcs + 1);
						}
					} else if (!isFQDM) {
						new ToastShow().show(context, "请提交点名后重试");
					}
				}
			});
			img_r.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (dqxzdmcs < dqdmcs) {
						dqxzdmcs++;
						dmcs_tv.setText("第 " + (dqxzdmcs + 1) + " 次点名");
						if (dqxzdmcs == dqdmcs) {
							jsdmxs.dmks();
						} else {
							interFace.getsyxsdmjg(jsdmxs.handler, xkkh, djz, xqj, djj, dqxzdmcs + 1);
						}
					}
				}
			});
		} else if (i == 1) {
			zhkt_yzm_input.setHint("请输入验证码");
		}
	}

	private int dqxzdmcs = 0;
	private int currentItem = 0;
	View mView1 = null;
	View mView2 = null;
	View mView3 = null;
	View mView4 = null;
	View zhkt_xsydm = null;
	private zhkt_jsdmxs jsdmxs;
	private Button fqdm, tjdm;
	private TextView ydrs, sdrs, cdrs, qjrs, dmts, kkrs;
	private boolean isTJDM = false, isFQDM = true;
	private LinearLayout jsxztxsmd_layout;
	private String str_yzm;

	@SuppressLint("InflateParams")
	private void initViewPager() {
		LayoutInflater inFlater = LayoutInflater.from(this);

		// 学生
		if (Constants.usertype == 1) {
			mView1 = inFlater.inflate(R.layout.zhkt_dm_xs, null);// 点名
			mView2 = inFlater.inflate(R.layout.zhkt_xszt, null);// 纸条
			mView3 = inFlater.inflate(R.layout.zhkt_xspj, null);// 评价
			mView4 = inFlater.inflate(R.layout.zhkt_xsbz, null);// 备注

			ScrollViewTools.inputScrollViewDown((ScrollView) mView1.findViewById(R.id.sc),
					mView1.findViewById(R.id.inner));
			pj = new LinearLayout[] { (LinearLayout) mView3.findViewById(R.id.zhkt_pj1),
					(LinearLayout) mView3.findViewById(R.id.zhkt_pj2),
					(LinearLayout) mView3.findViewById(R.id.zhkt_pj3),
					(LinearLayout) mView3.findViewById(R.id.zhkt_pj4) };

			zhkt_sjyzm = (TextView) mView1.findViewById(R.id.zhkt_sjyzm);
			// 点名验证码输入框
			zhkt_yzm_input = (EditText) mView1.findViewById(R.id.zhkt_yzm);
			zhkt_djs = (TextView) mView1.findViewById(R.id.zhkt_djs);
			zhkt_ts = (TextView) mView1.findViewById(R.id.zhkt_ts);// 错误提示
			zhkt_yytp = (ImageView) mView1.findViewById(R.id.zhkt_yytp);// 摇晃图片
			
			dmcs_tv = (TextView) mView1.findViewById(R.id.dmcs);
			img_l = (ImageView) mView1.findViewById(R.id.zhkt_l);
			img_r = (ImageView) mView1.findViewById(R.id.zhkt_r);
			//学生端隐藏左右切换按钮
			img_l.setVisibility(View.GONE);
			img_r.setVisibility(View.GONE);
			
			
			yzm = new Random().nextInt(10000);
			str_yzm = "请输入提示数字<font color='#27BDFC'>(" + yzm + ")</font>后摇动你的手机";

			zhkt_sjyzm.setText(Html.fromHtml(str_yzm));
             //点名班级
			interFace.dmbj(dmbj_handler, xkkh);
			//得到点名次数
			interFace.dmcs(dmcsxc_handler, xkkh, djz, xqj, djj);
			mShakeListener = new ShakeListener(this);
			mShakeListener.setOnShakeListener(new LSshakeLitener());
			mShakeListener.start();
			zhkt_yzm_input.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
					// 输入中

				}

				@Override
				public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

				}

				@Override
				public void afterTextChanged(Editable arg0) {
					// 输入后
					if (!arg0.toString().equals(String.valueOf(yzm))) {
						if (arg0.length() <= 0) {
							zhkt_ts.setText("");
						} else {
							zhkt_ts.setText("验证码输入错误");
						}

					} else {

						zhkt_ts.setText("");
					}

				}
			});
			// new ZHKTLslb(context, teacher_list, (ListView)
			// mView2.findViewById(R.id.zhkt_zt_layout), null,
			// mView2).getJslb(ktbh);
		} else if (Constants.usertype == 2) {
			// 教师
			mView1 = inFlater.inflate(R.layout.zhkt_dm_js, null);
			mView2 = inFlater.inflate(R.layout.zhkt_jszt, null);
			mView3 = inFlater.inflate(R.layout.zhkt_jspj, null);
			mView4 = inFlater.inflate(R.layout.zhkt_jsbz, null);

			jsxztxsmd_layout = (LinearLayout) mView2.findViewById(R.id.layout);
			xslayout = (LinearLayout) mView1.findViewById(R.id.zhkt_dm_js_xslb_layout);
			fqdm = (Button) mView1.findViewById(R.id.zhkt_fqdm_button);
			tjdm = (Button) mView1.findViewById(R.id.zhkt_tjdm_button);
			tjdm.setBackgroundColor(Color.GRAY);
			ydrs = (TextView) mView1.findViewById(R.id.zhkt_dm_js_ydrs);
			sdrs = (TextView) mView1.findViewById(R.id.zhkt_dm_js_sdrs);
			cdrs = (TextView) mView1.findViewById(R.id.zhkt_dm_js_cdrs);
			qjrs = (TextView) mView1.findViewById(R.id.zhkt_dm_js_qjrs);
			kkrs = (TextView) mView1.findViewById(R.id.zhkt_dm_js_kkrs);
			dmcs_tv = (TextView) mView1.findViewById(R.id.dmcs);
			diaog.show();
			//得到点名次数
			interFace.dmcs(dmcs_handler, xkkh, djz, xqj, djj);
			img_l = (ImageView) mView1.findViewById(R.id.zhkt_l);
			img_r = (ImageView) mView1.findViewById(R.id.zhkt_r);
		}
		mViewList.add(mView1);
		mViewList.add(mView2);
		mViewList.add(mView3);
		mViewList.add(mView4);
		mPagerAdapter = new PagerAdapter() {
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mViewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				View view = mViewList.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mViewList.size();
			}

			@Override
			public int getItemPosition(Object object) {
				return POSITION_NONE;
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				currentItem = mViewPager.getCurrentItem();
				reset();
				if (currentItem == 1 && (teacher_list == null || teacher_list.toString().equals(""))) {
					try {
						if (Constants.usertype == 1) {
							zhktLslb = new ZHKTLslb(context, teacher_list,
									(ListView) mView2.findViewById(R.id.zhkt_zt_layout), null, mView2,
									Constants.usertype + "");
							zhktLslb.getJslb(ktbh);
							new StudentSendZt().set(context, mView2, zhktLslb, ktbh);
						} else if (Constants.usertype == 2) {

						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (currentItem == 1) {
					barHelper.setExtra(R.drawable.shuaxin, true);
				} else
					barHelper.setExtra(R.drawable.shuaxin, false);
				switch (currentItem) {
				case 0:
					zhkt_dm_layout.setBackgroundResource(R.drawable.biankuang_buttom_blue);
					break;
				case 1:
					zhkt_zt_layout.setBackgroundResource(R.drawable.biankuang_buttom_blue);
					break;
				case 2:
					zhkt_pj_layout.setBackgroundResource(R.drawable.biankuang_buttom_blue);
					break;
				case 3:
					zhkt_bz_layout.setBackgroundResource(R.drawable.biankuang_buttom_blue);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	Handler getktbh = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 999) {
				ktbh = msg.getData().getString("ktbh");
				if (Constants.usertype == 1) {
					interFace.getxspj(getxspj, ktbh);
				} else if (Constants.usertype == 2) {
					// 教师获得学生字条
					new ZHKTJSZTtools(context, jsxztxsmd_layout).getStudentList(ktbh);
					interFace.getjspj(getxspj, ktbh);
				}
			}
		}
	};

	private TextView zhkt_kcmc, txt_classname;

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.zhkt_viewpager);
		zhkt_dm_layout = (LinearLayout) findViewById(R.id.zhkt_dm_layout);
		zhkt_zt_layout = (LinearLayout) findViewById(R.id.zhkt_zt_layout);
		zhkt_pj_layout = (LinearLayout) findViewById(R.id.zhkt_pj_layout);
		zhkt_bz_layout = (LinearLayout) findViewById(R.id.zhkt_bz_layout);
		zhkt_dm_layout.setBackgroundResource(R.drawable.biankuang_buttom_blue);
		zhkt_kcmc = (TextView) findViewById(R.id.zhkt_sj_kcmc);
		txt_classname = (TextView) findViewById(R.id.txt_classname);

		zhkt_kcmc.setText(time);
		txt_classname.setText("《" + classname + "》");// 课程名称
	}

	private void reset() {
		zhkt_dm_layout.setBackgroundResource(R.drawable.biankuang_buttom);
		zhkt_zt_layout.setBackgroundResource(R.drawable.biankuang_buttom);
		zhkt_pj_layout.setBackgroundResource(R.drawable.biankuang_buttom);
		zhkt_bz_layout.setBackgroundResource(R.drawable.biankuang_buttom);
	}

	@Override
	public void onClick(View v) {
		reset();
		switch (v.getId()) {
		case R.id.zhkt_dm_layout:
			v.setBackgroundResource(R.drawable.biankuang_buttom_blue);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.zhkt_zt_layout:
			v.setBackgroundResource(R.drawable.biankuang_buttom_blue);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.zhkt_pj_layout:
			v.setBackgroundResource(R.drawable.biankuang_buttom_blue);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.zhkt_bz_layout:
			v.setBackgroundResource(R.drawable.biankuang_buttom_blue);
			mViewPager.setCurrentItem(3, false);
			break;
		default:
			break;
		}
	}

	public Handler returnmap = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				if (msg.what == 0) {
					new ToastShow().show(context, "纸条获取失败");
					return;
				}
				new JSONArray(msg.getData().get("msg").toString());
				switch (msg.what) {
				case 8:

					break;
				case 9:

					break;
				case 10:

					break;
				default:
					new ToastShow().show(context, "纸条获取失败");
					break;
				}
			} catch (JSONException e) {
				new ToastShow().show(context, "纸条获取失败");
			}
		}
	};

	private int djs = 30;
	Handler yyy_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				if (msg.what == 0) {
					new ToastShow().show(context, "点名失败,快去联系老师吧,Good luck!!!");
					xsydm(0);
					isDm = false;
					return;
				}
				if (msg.what == 1) {
					new ToastShow().show(context, "亲,点名成功,专心上课吧");
					isDm = false;
					xsydm(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	};

	Handler dmbj_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				if (msg.what == 0) {
//					new ToastShow().show(context, "还未发起点名");
					isDm = true;
					return;
				}
				Bundle b = msg.getData();
				djs = Integer.valueOf(b.getString("sysj"));
				zhkt_djs.setText("" + djs);
				sxdjs();
				isDm = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	Handler djs_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				if (isDm)
					if (djs > 0) {
						if (msg.what == 0) {
							new ToastShow().show(context, "点名失败,快去联系老师吧");
							return;
						} else if (msg.what == 1) {
							zhkt_djs.setText("" + djs);
							sxdjs();
						}
					} else
						new ToastShow().show(context, "时间过了,快去找老师吧");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	private void sxdjs() {
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				if (isDm && djs > 0)// 正在获取学生是否成功
				{
					djs--;
					Message msg = new Message();
					msg.what = 1;
					djs_handler.sendMessage(msg);
				} else // 失败
				{
					Message msg = new Message();
					msg.what = 0;
					djs_handler.sendMessage(msg);
				}
			}
		}, 1000);
	}

	@SuppressLint("InflateParams")
	public void xsydm(int dmzt) {
		LayoutInflater inFlater = LayoutInflater.from(this);
		zhkt_xsydm = inFlater.inflate(R.layout.zhkt_xsydm, null);
		switch (dmzt) {
		case 0:
			zhkt_xsydm.findViewById(R.id.img).setBackgroundResource(R.drawable.dianming_error);
			break;
		case 1:
			zhkt_xsydm.findViewById(R.id.img).setBackgroundResource(R.drawable.dianming_success);
			break;
		default:
			break;
		}
		mViewList = new ArrayList<View>();
		mViewList.add(zhkt_xsydm);
		mViewList.add(mView2);
		mViewList.add(mView3);
		mViewList.add(mView4);
		mViewPager.removeViewAt(0);
		mViewPager.getAdapter().notifyDataSetChanged();
		dmts = (TextView) zhkt_xsydm.findViewById(R.id.dmts);
		dmts.setText(dmtsString);
	}

	class LSshakeLitener implements OnShakeListener {
		private Vibrator mVibrator;

		@Override
		public void onShake() {
			if (isDm && currentItem == 0) {
				String s = "_";
				try {
					s = zhkt_yzm_input.getText().toString();
				} catch (Exception e) {

				}
				if (s.equals(String.valueOf(yzm))) {
					mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
					long[] pattern = { 100, 400, 100, 400 };
					mVibrator.vibrate(pattern, -1);
					// Toast.makeText(getApplicationContext(), "摇了一摇!",
					// Toast.LENGTH_SHORT).show();
					mShakeListener.stop();
					interFace.xsdm(xsdm_handler, Constants.number, xkkh);
				} else {

					zhkt_ts.setText("验证码输入错误");
				}
			}
		}
	}

	private boolean dmIng = false;
	public Handler fqdm_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				if (msg.what == 2) {
					dmIng = true;
					new ToastShow().show(context, "发起点名成功");
					ktdmid = msg.getData().getString("ktdmid");
					interFace.ydmxslb(ydmxslb_handler, xkkh);
					fqdm.setBackgroundColor(Color.GRAY);
					isFQDM = false;
					fqdm.setEnabled(true);
					
					showUpdateHandPhoto(fqdm);
					if (diaog.isShowing()) {
						diaog.dismiss();
					}
				} else {
					new ToastShow().show(context, "发起点名失败");
					if (diaog.isShowing()) {
						diaog.dismiss();
					}
				}
			} catch (Exception e) {
			}
		};
	};
	private boolean isDm = true;
	private String dmtsString = "";
	Handler xsdm_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				isDm = false;
				if (msg.what == 1) {
					dmtsString = "点名成功\n再次点名请退出后再重新进入哟";
					xsydm(1);
				} else {
					dmtsString = "点名失败,快去联系老师吧";
					xsydm(0);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};

	List<Map<String, String>> ydmxslbList = new ArrayList<Map<String, String>>(),
			dmxslbList = new ArrayList<Map<String, String>>();
	public Handler dmxslb_handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				String s = msg.getData().getString("ktxslb");
//				ExampleApplication.getInstance().getGson().fromJson(s, ZHKTStudentModel.);
				ydmxslbList = new JsonListMap().JsonToMap(new JSONArray(s), "0", new String[] { "XH", "XM", "ZT" });
				
				jsdmxs = new zhkt_jsdmxs(xslayout, context, sdrs, cdrs, kkrs, qjrs, ydmxslbList);
				jsdmxs.doit(ydmxslbList);
				ydrs.setHint("应到:" + ydmxslbList.size() + "");
				if (diaog.isShowing()) {
					diaog.dismiss();
				}
			} catch (JSONException e) {
				new ToastShow().show(context, "获取学生列表失败");
			}
		}

	};
	
	/** 轮训已点名学生列表 */
	private boolean shuldRefresh = true;
	public Handler ydmxslb_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				if (msg.what == 0) {
					new ToastShow().show(context, "时间已经到了");
					shuldRefresh = false;
					isTJDM = true;
					canChange = true;
					tjdm.setBackgroundResource(R.drawable.zhkt_dm_js_tjdm);
					
					return;
				}
				isTJDM = false;
				shuldRefresh = true;
				Bundle b = msg.getData();
				List<Map<String, String>> ydmlist = new ArrayList<Map<String, String>>();
				try {
					ydmlist = new JsonListMap().JsonToMap(new JSONArray(b.get("yyydmxs").toString()), "0", "xh");
					jsdmxs.refresh(ydmlist, ydmxslbList);
				} catch (JSONException e) {
				}
				if (shuldRefresh) {
					final Timer t = new Timer();
					t.schedule(new TimerTask() {
						@Override
						public void run() {
							interFace.ydmxslb(ydmxslb_handler, xkkh);
							this.cancel();
							t.cancel();
						}
					}, 1000);
				}
			} catch (Exception e) {
			}
		};
	};

	/** 提交点名 */
	public Handler tjdm_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			canChange = false;
			super.handleMessage(msg);
			try {
				if (msg.what == 0) {
					new ToastShow().show(context, "点名提交失败,请重试");
					shuldRefresh = true;
					if (diaog.isShowing()) {
						diaog.dismiss();
					}
					return;
				} else if (msg.what == 5) {
					tjcs++;
					if (tjcs == jsdmxs.gettjdmcs(ydmxslbList)) {
						shuldRefresh = false;
						new ToastShow().show(context, "点名提交成功");
						dqxzdmcs = dqdmcs = dqdmcs + 1;
						dmcs_tv.setText("第 " + (dqxzdmcs + 1) + " 次点名");
						tjcs = 0;
						interFace.dmxslb(dmxslb_handler, xkkh);
						dmIng = false;
						isFQDM = true;
						isTJDM = false;
						fqdm.setBackgroundResource(R.drawable.zhkt_dm_js_ksdm);
						tjdm.setBackgroundColor(Color.GRAY);
						tjdm.setEnabled(true);
						if (diaog.isShowing()) {
							diaog.dismiss();
						}
					}
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
	private int tjcs = 0;
	private int dqdmcs;
	/** 课堂点名学生列表 */
	public Handler dmcs_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				dqxzdmcs = dqdmcs = msg.what;
				dmcs_tv.setText("第 " + (dqdmcs + 1) + " 次点名");
				//得到学生列表
				interFace.dmxslb(dmxslb_handler, xkkh);
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
	
	
	/** 课堂点名学生列表 */
	public Handler dmcsxc_handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			super.handleMessage(msg);
			try {
				dqxzdmcs =msg.what;
				dmcs_tv.setText("第 " + (dqxzdmcs + 1) + " 次点名");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	};
	
	

	Handler getxspj = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (Constants.usertype == 1)
				try {
					JSONArray json = new JSONArray(msg.getData().getString("msg"));
					if (json.length() != 0) {
						freshView3();
						new zhkt_pj(context).xspj((LinearLayout) (mView3.findViewById(R.id.zhkt_xsypj)), json);
						mViewPager.getAdapter().notifyDataSetChanged();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			else if (Constants.usertype == 2) {
				try {
					JSONArray json = new JSONArray(msg.getData().getString("msg"));
					if (json.length() != 0) {
						new zhkt_pj(context).getjspj((LinearLayout) mView3.findViewById(R.id.zhkt_jspj), json);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}

	};

	@SuppressLint("InflateParams")
	private void freshView3() {
		mViewList = new ArrayList<View>();
		mView3 = LayoutInflater.from(context).inflate(R.layout.zhkt_xsypj, null);
		mViewList.add(mView1);
		mViewList.add(mView2);
		mViewList.add(mView3);
		mViewList.add(mView4);
	}

	public void close(View v) {
		if (!dmIng) {
			try {
				mShakeListener.stop();
			} catch (Exception e) {
			}
			finish();
		} else {
			Toast.makeText(context, "点名还未结束", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		tcbjzd = "finish";
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if (!dmIng) {
				try {
					mShakeListener.stop();
				} catch (Exception e) {
				}
				this.finish();
			} else {
				dialog("点名还未结束");
			}
		}
		return true;
	}

	public void pj(View v) {
		if (Constants.usertype == 1) {
			int tag = Integer.valueOf(v.getTag().toString());
			int djl = tag % 10;
			int djh = tag / 10;
			pjdf[djh - 1] = djl;
			try {
				for (int i = 1; i <= djl; i++) {
					pj[djh - 1].getChildAt(i).setBackgroundResource(R.drawable.pingjia);
				}
				for (int i = djl + 1; i < 6; i++) {
					pj[djh - 1].getChildAt(i).setBackgroundResource(R.drawable.pingjia_no);
				}
			} catch (Exception e) {
				new ToastShow().show(this, "评价失败,请重试");
			}
		}
	}

	Handler tjpj = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			freshView3();
			if (msg.what == 11) {
				new zhkt_pj(context).xstjpj((LinearLayout) (mView3.findViewById(R.id.zhkt_xsypj)), pjdf, pjnr);
				mViewPager.getAdapter().notifyDataSetChanged();
			}
		}

	};

	public void fszt(View v) {
		if (Constants.usertype == 1) {
			// String nr = ((EditText)
			// mView2.findViewById(R.id.quanzi_liaotian_input)).getText().toString();
			// new zhkt_dhk(this, ((LinearLayout)
			// mView2.findViewById(R.id.zhkt_zt_layout)),
			// ktbh).dhk_student(Constants.name, new DateString("yyyy-MM-dd
			// HH:mm:ss").DateToString(new Date()), nr);
		}
	}

	private String pjnr;

	public void tjpj(View v) {
		pjnr = ((EditText) mView3.findViewById(R.id.zhkt_pj_text)).getText().toString();
		interFace.xspj(tjpj, ktbh, "" + pjdf[0], "" + pjdf[1], "" + pjdf[2], "" + pjdf[3], pjnr, "");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		canChange = false;
	}
}