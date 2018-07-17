package com.example.app3.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.app3.TitlePagerViewModel;

import java.util.ArrayList;
import java.util.List;

import yh.app.tool.DpPx;

/**
 * <p>
 * 由于eclipse无法使用tabview,故自定义控件实现该功能
 * </p>
 * 
 * @author Administrator
 *
 */
public class TitleViewPager extends LinearLayout implements OnClickListener
{
    public static final int TOP_TITLE = 1;
    public static final int BUTTOM_TITLE = 0;
    private int local = BUTTOM_TITLE;
    private ViewPager pagerView;
    private LinearLayout top_title, buttom_title;
    private Context context;
    private List<View> ViewPagerItems = new ArrayList<>();
    private ViewChangeListener listener;
    private int count = 0;

    public TitleViewPager(Context context, AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
	this.context = context;
	init();
    }

    public TitleViewPager(Context context, AttributeSet attrs)
    {
	super(context, attrs);
	init();
	this.context = context;
    }

    public TitleViewPager(Context context)
    {
	super(context);
	this.context = context;

	setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
	init();
    }

    @Override
    public void onClick(View v)
    {
	if (listener != null)
	{
	    listener.addViewChangeListener(v, (int) v.getTag());
	}
    }

    private void init()
    {
	initView();
	initAction();
    }

    private void initView()
    {
	setOrientation(LinearLayout.VERTICAL);

	top_title = new LinearLayout(context);
	LinearLayout.LayoutParams top_title_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, new DpPx(context).getDpToPx(48));
	top_title.setLayoutParams(top_title_lp);
	top_title.setOrientation(LinearLayout.HORIZONTAL);
	addView(top_title);

	pagerView = new ViewPager(context);
	LinearLayout.LayoutParams pagerView_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
	pagerView.setLayoutParams(pagerView_lp);
	addView(pagerView);

	buttom_title = new LinearLayout(context);
	LinearLayout.LayoutParams buttom_title_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, new DpPx(context).getDpToPx(48));
	buttom_title.setLayoutParams(buttom_title_lp);
	buttom_title.setOrientation(LinearLayout.HORIZONTAL);
	addView(buttom_title);

	setTitleLocal(local);
    }

    private void initAction()
    {
	pagerView.setAdapter(new PagerAdapter()
	{
	    @Override
	    public void destroyItem(ViewGroup container, int position, Object object)
	    {
		container.removeView(ViewPagerItems.get(position));
	    }

	    @Override
	    public Object instantiateItem(ViewGroup container, int position)
	    {
		return ViewPagerItems.get(position);
	    }

	    @Override
	    public boolean isViewFromObject(View view, Object object)
	    {
		return view == object;
	    }

	    @Override
	    public int getCount()
	    {
		return ViewPagerItems.size();
	    }
	});
	pagerView.addOnPageChangeListener(new OnPageChangeListener()
	{

	    @Override
	    public void onPageSelected(int position)
	    {
		if (listener != null)
		{
		    listener.addViewChangeListener(BUTTOM_TITLE == local ? buttom_title.getChildAt(position) : top_title.getChildAt(position), position);
		}
	    }

	    @Override
	    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	    {

	    }

	    @Override
	    public void onPageScrollStateChanged(int state)
	    {

	    }
	});
    }

    /**
     * 设置导航栏位置
     * 
     * @param local
     */
    public void setTitleLocal(int local)
    {
	this.local = local;
	if (BUTTOM_TITLE == local)
	{
	    buttom_title.setVisibility(View.VISIBLE);
	    top_title.setVisibility(View.GONE);
	} else
	{
	    top_title.setVisibility(View.VISIBLE);
	    buttom_title.setVisibility(View.GONE);
	}
    }

    public LinearLayout getTitleView()
    {
	return (LinearLayout) (BUTTOM_TITLE == local ? buttom_title : top_title);
    }

    public void addItem(TitlePagerViewModel item) throws Exception
    {
	if (item.getPager() == null || item.getTitle() == null)
	{
	    throw new Exception("子控件不能为空");
	}
	View title = item.getTitle();
	View pager = item.getPager();

	title.setOnClickListener(this);
	title.setTag(ViewPagerItems.size());
	LinearLayout.LayoutParams title_lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
	if (local == BUTTOM_TITLE)
	{
	    buttom_title.addView(title,title_lp);
	}

	ViewPagerItems.add(pager);
	
	pagerView.getAdapter().notifyDataSetChanged();
    }

    public interface ViewChangeListener
    {
	void addViewChangeListener(View view, int index);
    }

    public void setViewChangeListener(ViewChangeListener listener)
    {
	this.listener = listener;
    }
}