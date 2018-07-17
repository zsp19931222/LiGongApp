package yh.app.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * 
 * 包	名:yh.app.banner
 * 类	名:MyNewListview.java
 * 功	能:继承于ListView
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class MyNewListview  extends ListView {

    public MyNewListview(Context context) {

        super(context);

    }



    public MyNewListview(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public MyNewListview(Context context, AttributeSet attrs,int defStyle) {

        super(context, attrs, defStyle);

    }

        

    @Override

    /**

     * ��д�÷������ﵽʹListView��ӦScrollView��Ч��

     */

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

        MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }

}
