package yh.app.tool;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
/**
 * 
 * 包	名:yh.app.tool
 * 类	名:MyViewPager.java
 * 功	能:自定义ViewPager适配器
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class MyViewPager extends ViewPager {

	 private boolean scrollble=true;

	  public MyViewPager(Context context){
	    super(context);
	  }

	  public MyViewPager(Context context, AttributeSet attrs) {
	    super(context, attrs);
	  }


	  @Override
	  public boolean onTouchEvent(MotionEvent ev) {
	    if (!scrollble) {
	      return true;
	    }
	    return super.onTouchEvent(ev);
	  }


	  public boolean isScrollble() {
	    return scrollble;
	  }

	  public void setScrollble(boolean scrollble) {
	    this.scrollble = scrollble;
	  }
}
