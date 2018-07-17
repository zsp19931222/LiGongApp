package com.example.app3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PullableLinearLayout extends LinearLayout implements Pullable
{
    private boolean canDown = false;
    private boolean canUp = false;

    public PullableLinearLayout(Context context)
    {
	super(context);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs)
    {
	super(context, attrs);
    }

    public PullableLinearLayout(Context context, AttributeSet attrs, int defStyle)
    {
	super(context, attrs, defStyle);
    }
    
    public void setCanDown(boolean canDown)
    {
        this.canDown = canDown;
    }

    public void setCanUp(boolean canUp)
    {
        this.canUp = canUp;
    }

    @Override
    public boolean canPullDown()
    {
	if (getScrollY() == 0)
	    return canDown;
	else
	    return false;
    }

    @Override
    public boolean canPullUp()
    {
	if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
	    return canUp;
	else
	    return false;
    }
}
