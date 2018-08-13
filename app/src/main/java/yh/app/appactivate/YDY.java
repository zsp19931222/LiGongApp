//package yh.app.appactivate;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.support.v4.view.ViewPager.OnPageChangeListener;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import yh.app.IntentTools.IntentTools;
//import yh.app.activitytool.ActivityPortrait;
//import com.yhkj.cqgyxy.R;
//import yh.app.tool.SqliteHelper;
//import yh.app.uiengine.Login;
//import yh.app.uiengine.Login1;
//import com.example.app3.HomePageActivity;
//
//public class YDY extends ActivityPortrait
//{
//	private ViewPager viewPager;
//	private List<ImageView> imgList = new ArrayList<ImageView>();
//	private PagerAdapter mPagerAdapter;
//	private LinearLayout xyd_layout;
//	private Button button;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		initLayout();
//		initYdy();
//	}
//
//	private void initYdy()
//	{
//		try
//		{
//			if (new SqliteHelper().rawQuery("select * from ydy").size() == 0)
//			{
//				initView();
//				initAdater();
//				initViewPager();
//				initAction();
//				new SqliteHelper().execSQL("insert into ydy values('1')");
//			} else
//			{
//				start();
//			}
//		} catch (Exception e)
//		{
//			start();
//		}
//	}
//
//	private void initLayout()
//	{
//		
//		setContentView(R.layout.ydy);
//	}
//
//	private void start()
//	{
//		if (new SqliteHelper().rawQuery("select * from userp").size() > 0)
//		{
//			AppStart.initUserLoginInfo();
//			Intent intenthome=new Intent(this,home.class);
//			startActivity(intenthome);
//			finish();
////			IntentTools.intent(this, "com.example.app3.HomePageActivity", getPackageName(), true, true, null);
//		} else
//		{
//			Intent intentlogin=new Intent(this,Login1.class);
//			startActivity(intentlogin);
//			finish();
////			IntentTools.intent(this, "yh.app.uiengine.Login1", getPackageName(), true, true, null);
//		}
//	}
//
//	private void initAction()
//	{
//		button.setOnClickListener(new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				start();
//			}
//		});
//
//	}
//
//	private void initView()
//	{
//		viewPager = (ViewPager) findViewById(R.id.viewpager);
//		int img_id = R.drawable.ydy1;
//		for (int i = 0; i < 4; i++)
//		{
//			ImageView img = new ImageView(this);
//			img.setBackgroundResource(img_id + i);
//			imgList.add(img);
//		}
//		xyd_layout = (LinearLayout) findViewById(R.id.xyd_layout);
//		button = (Button) findViewById(R.id.button1);
//	}
//
//	private void initAdater()
//	{
//		mPagerAdapter = new PagerAdapter()
//		{
//
//			@Override
//			public void destroyItem(ViewGroup container, int position, Object object)
//			{
//				container.removeView(imgList.get(position));
//			}
//
//			@Override
//			public Object instantiateItem(ViewGroup container, int position)
//			{
//				View view = imgList.get(position);
//				container.addView(view);
//				return view;
//			}
//
//			@Override
//			public boolean isViewFromObject(View arg0, Object arg1)
//			{
//				return arg0 == arg1;
//			}
//
//			@Override
//			public int getCount()
//			{
//				return imgList.size();
//			}
//
//			@Override
//			public int getItemPosition(Object object)
//			{
//				return POSITION_NONE;
//			}
//		};
//	}
//
//	private void initViewPager()
//	{
//		viewPager.setAdapter(mPagerAdapter);
//		viewPager.addOnPageChangeListener(new OnPageChangeListener()
//		{
//
//			@Override
//			public void onPageSelected(int arg0)
//			{
//				reset();
//				((ImageView) (xyd_layout.getChildAt(arg0))).setBackgroundResource(R.drawable.ydy_xyd_white);
//				if (arg0 == imgList.size() - 1)
//				{
//					button.setVisibility(View.VISIBLE);
//				} else
//					button.setVisibility(View.INVISIBLE);
//			}
//
//			@Override
//			public void onPageScrolled(int arg0, float arg1, int arg2)
//			{
//
//			}
//
//			@Override
//			public void onPageScrollStateChanged(int arg0)
//			{
//
//			}
//		});
//	}
//
//	private void reset()
//	{
//		for (int i = 0; i < imgList.size(); i++)
//		{
//			((ImageView) (xyd_layout.getChildAt(i))).setBackgroundResource(R.drawable.ydy_xyd_gray);
//		}
//
//	}
//
//}
