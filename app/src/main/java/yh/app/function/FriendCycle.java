package yh.app.function;

import java.util.ArrayList;
import java.util.List;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.function.UI._圈子_消息;import yh.app.appstart.lg.R;
import yh.app.quanzi.mrfz;
import yh.app.utils.TopBarHelper;
import 云华.智慧校园.工具.ActivityHelper;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class FriendCycle extends ActivityPortrait implements View.OnClickListener
{
	private PagerAdapter mPagerAdapter;
	private List<View> mViewList = new ArrayList<View>();
	private ViewPager mViewPager;

	private LinearLayout layout_button1, layout_button2, layout_button3;
	public static int SENT_CHAT = 0x00;
	public static int SENT_ADDFRIEND = 0x01;
	public static int SENT_READDFRIEND = 0x02;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.quanzi);
		initView();
		initViewPager();
		initEvent();
		layout_button1.getChildAt(0).setBackgroundResource(R.drawable.quanzi_xiaoxi2);
		((TextView) layout_button1.getChildAt(1)).setTextColor(0xFF11B7F3);
		setTopBar("社区");
	}

	private int currentItem = 0;

	private void initEvent()
	{
		layout_button1.setOnClickListener(this);
		layout_button2.setOnClickListener(this);
		layout_button3.setOnClickListener(this);
		mViewPager.addOnPageChangeListener(new OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int arg0)
			{
				currentItem = mViewPager.getCurrentItem();
				reset();
				switch (currentItem)
				{
				case 0:
					layout_button1.getChildAt(0).setBackgroundResource(R.drawable.quanzi_xiaoxi2);
					((TextView) layout_button1.getChildAt(1)).setTextColor(0xFF11B7F3);
					mViewPager.setCurrentItem(0);
					break;
				case 1:
					layout_button2.getChildAt(0).setBackgroundResource(R.drawable.quanzi_fenxiang2);
					((TextView) layout_button2.getChildAt(1)).setTextColor(0xFF11B7F3);
					mViewPager.setCurrentItem(1);
					break;
				case 2:
					layout_button3.getChildAt(0).setBackgroundResource(R.drawable.quanzi_tongxunlu2);
					((TextView) layout_button3.getChildAt(1)).setTextColor(0xFF11B7F3);
					mViewPager.setCurrentItem(2);
					break;
				default:
					break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

		});
		mView3.findViewById(R.id.mrfz).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(setDefaultIntent(setIntent(FriendCycle.this, mrfz.class, null)));
			}
		});

		Constants.sq_main_handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				new _圈子_消息(FriendCycle.this, mView1).setChatedMessage();
			}
		};
	}

	private void setTopBar(String name)
	{
		new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle(name).setExtra(R.drawable.zhaoren, true).setOnClickLisener(new TopBarHelper.OnClickLisener()
		{
			@Override
			public void setRightOnClick()
			{
				new ActivityHelper().goHomeActivity(FriendCycle.this);
			}

			@Override
			public void setLeftOnClick()
			{
				FriendCycle.this.finish();
			}

			@Override
			public void setExtraOnclick()
			{
				Intent intent = new Intent();
				intent.setAction("yh.app.quanzi.tjhy.Quanzi_UI_tjhy");
				intent.setPackage(FriendCycle.this.getPackageName());
				startActivity(intent);
			}
		});
	}

	private View mView1 = null;
	private View mView2 = null;
	private View mView3 = null;

	private void initViewPager()
	{
		LayoutInflater inFlater = LayoutInflater.from(this);

		mView1 = inFlater.inflate(R.layout.quanzi_layout_xiaoxi, null);
		mView2 = inFlater.inflate(R.layout.zwkf, null);
		mView3 = inFlater.inflate(R.layout.quanzi_mrfz, null);

		new yh.app.quanzi.lxr.Contact(this, mView3).getContact(getIntent().getStringExtra("function_id"));

		mViewList.add(mView1);
		mViewList.add(mView2);
		mViewList.add(mView3);

		new _圈子_消息(this, mView1).setChatedMessage();

		mPagerAdapter = new PagerAdapter()
		{
			@Override
			public void destroyItem(ViewGroup container, int position, Object object)
			{
				container.removeView(mViewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				View view = mViewList.get(position);
				container.addView(view);
				return view;
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public int getCount()
			{
				return mViewList.size();
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
	}

	private void initView()
	{
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		layout_button1 = (LinearLayout) findViewById(R.id.xx);
		layout_button2 = (LinearLayout) findViewById(R.id.dt);
		layout_button3 = (LinearLayout) findViewById(R.id.txl);
	}

	public void clickViewPager(int i)
	{
		reset();
		switch (i)
		{
		case 1:
			layout_button1.getChildAt(0).setBackgroundResource(R.drawable.quanzi_tongxunlu2);
			((TextView) layout_button1.getChildAt(1)).setTextColor(0xFF11B7F3);
			break;
		case 2:
			layout_button2.getChildAt(0).setBackgroundResource(R.drawable.quanzi_tongxunlu2);
			((TextView) layout_button2.getChildAt(1)).setTextColor(0xFF11B7F3);
			break;
		case 3:
			layout_button3.getChildAt(0).setBackgroundResource(R.drawable.quanzi_tongxunlu2);
			((TextView) layout_button3.getChildAt(1)).setTextColor(0xFF11B7F3);
			break;
		default:
			break;
		}
		mViewPager.setCurrentItem(i);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.xx:
			clickViewPager(0);
			break;
		case R.id.dt:
			clickViewPager(1);
			break;
		case R.id.txl:
			clickViewPager(2);
			break;
		default:
			break;
		}
	}

	private void reset()
	{
		layout_button1.getChildAt(0).setBackgroundResource(R.drawable.quanzi_xiaoxi);
		layout_button2.getChildAt(0).setBackgroundResource(R.drawable.quanzi_fenxiang);
		layout_button3.getChildAt(0).setBackgroundResource(R.drawable.quanzi_tongxunlu);
		((TextView) layout_button1.getChildAt(1)).setTextColor(0xFF696969);
		((TextView) layout_button2.getChildAt(1)).setTextColor(0xFF696969);
		((TextView) layout_button3.getChildAt(1)).setTextColor(0xFF696969);
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
		new _圈子_消息(this, mView1).setChatedMessage();
	}
}
