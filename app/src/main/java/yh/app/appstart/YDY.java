package yh.app.appstart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import yh.app.activitytool.ActivityPortrait;

import com.example.app3.HomePageActivity;
import com.example.app3.tool.HintTool;
import com.example.app4.activity.BindingOtherActivity;
import com.example.app4.activity.BindingPhoneActivity;
import com.example.app4.util.DefaultUtil;
import com.yhkj.cqgyxy.R;

import yh.app.tool.SqliteHelper;
import yh.app.uiengine.Login1;

import com.example.app3.HomePageActivity;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

public class YDY extends ActivityPortrait {
    private ViewPager viewPager;
    private List<ImageView> imgList = new ArrayList<ImageView>();
    private PagerAdapter mPagerAdapter;
    private LinearLayout xyd_layout;
    private TextView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initYdy();
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().post(new MessageEvent(HintTool.CloseStartPage, ""));
    }
    private void initYdy() {
        try {
            if (new SqliteHelper().rawQuery("select * from ydy").size() == 0) {
                initView();
                initAdater();
                initViewPager();
                initAction();
                new SqliteHelper().execSQL("insert into ydy values('1')");
            } else {
                start();
            }
        } catch (Exception e) {
            start();
        }
    }

    private void initLayout() {

        setContentView(R.layout.ydy);
    }

    private void start() {
        Intent intentlogin = new Intent(this, BindingOtherActivity.class);
        intentlogin.putExtra("universityName", DefaultUtil.getDefaultSchool());
        startActivity(intentlogin);
        finish();
    }

    private void initAction() {
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });

    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        int img_id = R.drawable.ydy1;
        for (int i = 0; i < 4; i++) {
            ImageView img = new ImageView(this);
            img.setBackgroundResource(img_id + i);
            imgList.add(img);
        }
        xyd_layout = (LinearLayout) findViewById(R.id.xyd_layout);
        button = (TextView) findViewById(R.id.button1);
    }

    private void initAdater() {
        mPagerAdapter = new PagerAdapter() {

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imgList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = imgList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return imgList.size();
            }

            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };
    }

    private void initViewPager() {
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                reset();
                ((ImageView) (xyd_layout.getChildAt(arg0))).setBackgroundResource(R.drawable.ydy_xyd_white);
                if (arg0 == imgList.size() - 1) {
                    button.setVisibility(View.VISIBLE);
                } else
                    button.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void reset() {
        for (int i = 0; i < imgList.size(); i++) {
            ((ImageView) (xyd_layout.getChildAt(i))).setBackgroundResource(R.drawable.ydy_xyd_gray);
        }

    }

}
