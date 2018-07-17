package yh.tool.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class MonitorScrollView extends ScrollView {
	private OnScrollListener onScrollListener;
	/**
	 * 主要是用在用户手指离开MonitorScrollView，
	 * MonitorScrollView还在继续滑动，我们用来保存Y的距离，然后做比较
	 */
	private int lastScrollY;

	public MonitorScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MonitorScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public MonitorScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 设置滚动接口
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	
    
	@Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if (onScrollListener != null) {
        	onScrollListener.onScroll(this,x,y,oldx,oldy);
        }
    }
	
	


	/**
	 * 
	 * 滚动的回调接口
	 * 
	 * 
	 * 
	 */
	public interface OnScrollListener {
		/**
		 * 回调方法， 返回MonitorScrollView滑动的Y方向距离
		 * 
		 * @param scrollY
		 *            
		 */
		public void onScroll(MonitorScrollView view,int x, int y, int oldx, int oldy);
	}

}
