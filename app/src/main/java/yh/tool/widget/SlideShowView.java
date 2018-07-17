package yh.tool.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import yh.app.model.DAModel;
import 云华.智慧校园.自定义控件.SQ_AdaptImageView;

/**
 * ViewPager实现的轮播图广告自定义视图； 既支持自动轮播页面也支持手势滑动切换页面,可以动态设置图片的张数
 */
@SuppressLint("HandlerLeak")
public class SlideShowView extends FrameLayout {

    private boolean isAutoPlay = true;
    private List<DAModel.ContentBean> imageUris;// 广告数据
    private List<ImageView> imageViewsList;// 广告图片
    private List<TextView> dotViewsList;// 广告介绍
    private LinearLayout mLinearLayout;// 装填广告数据
    private ImageView imageView;
    private ViewPager mViewPager;
    private int currentItem = 0;
    private ScheduledExecutorService scheduledExecutorService;

    private onDAClickListener onDAClickListener;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            mViewPager.setCurrentItem(currentItem, false);
        }

    };

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub

        initUI(context);
        if (!(imageUris.size() <= 0)) {

            setImageUris(imageUris);
        }

//         //开始进入开始播放
        if (isAutoPlay) {
            startPlay();
        }

    }

    /**
     * 初始化view
     *
     * @param context
     */
    private void initUI(Context context) {
        imageViewsList = new ArrayList<>();
        dotViewsList = new ArrayList<>();
        imageUris = new ArrayList<>();
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    /**
     * 加载网络图片
     *
     * @param imageuris
     */
    public void setImageUris(List<DAModel.ContentBean> imageuris) {
        // 得到传过来的数据
        this.imageUris = imageuris;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(5, 0, 0, 0);
        for (int i = 0; i < imageuris.size(); i++) {
            imageView = new SQ_AdaptImageView(getContext());
            imageView.setScaleType(ScaleType.FIT_XY);// 铺满屏幕
            imageView.setBackgroundResource(R.drawable.xxhome1);// 默认图片
            GlideLoadUtils.getInstance().glideLoad(getContext(), imageuris.get(i).getImg(),imageView,R.drawable.xxhome1);


            imageViewsList.add(imageView);

            TextView viewDot = new TextView(getContext());
            viewDot.setTextColor(getResources().getColor(R.color.white));
            if (i == 0) {
                // 焦点前状态
                viewDot.setText(imageuris.get(i).getBanner_id());
                onClick(i);
            } else {
                // 非焦点前状态
                viewDot.setText(null);
            }

            viewDot.setLayoutParams(lp);
            dotViewsList.add(viewDot);
            mLinearLayout.addView(viewDot);

        }
        mViewPager.setFocusable(true);
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(new MyPageChangeListener());
    }

    /**
     * 开始播放
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4, TimeUnit.SECONDS);
    }

    /**
     * 广告图片点击事件
     *
     * @param i
     */
    private void onClick(final int i) {

        imageView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String url = (String) imageUris.get(i).getImg();

                if (null != url) {
                    Intent intentweb = new Intent(getContext(), BrowserActivity.class);
                    intentweb.putExtra("url", url);
                    getContext().startActivity(intentweb);
                }
            }
        });
    }

    @SuppressWarnings("unused")
    private void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < dotViewsList.size(); i++) {
            if (i == selectItems) {
                // 选择状态
                dotViewsList.get(i).setText(imageUris.get(i).getBanner_id());
                onClick(i);
            } else {
                // 非焦点前状态
                dotViewsList.get(i).setText(null);
            }
        }
    }

    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

    }

    private class MyPageChangeListener implements OnPageChangeListener {


        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

            switch (arg0) {
                case 1:
                    stopPlay();
                    isAutoPlay = false;
                    //滑动中
                    break;
                case 2:
                    //滑动滑动完成
                    isAutoPlay = true;
                    startPlay();
                    break;
                case 0:
//				stopPlay();
                    //什么都没做
                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                        mViewPager.setCurrentItem(0, false);
                    } else if (mViewPager.getCurrentItem() == 0 && !isAutoPlay) {
                        mViewPager.setCurrentItem(mViewPager.getAdapter().getCount() - 1, false);
                    }
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int pos) {
            // TODO Auto-generated method stub
            setImageBackground(pos % imageUris.size());

        }

    }

    private class SlideShowTask implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (mViewPager) {
                currentItem = (currentItem + 1) % imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }

    }

    @SuppressWarnings("unused")
    private void destoryBitmaps() {

        for (int i = 0; i < imageViewsList.size(); i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {

                drawable.setCallback(null);
            }
        }
    }

    interface onDAClickListener {
        /**
         * 单击事件
         *
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }

}
