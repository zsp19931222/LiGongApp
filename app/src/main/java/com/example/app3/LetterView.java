package com.example.app3;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class LetterView extends LinearLayout
{
private Context context;
    public LetterView(Context context, AttributeSet attrs, int defStyleAttr)
    {
	super(context, attrs, defStyleAttr);
	// TODO Auto-generated constructor stub
    }

    public LetterView(Context context, AttributeSet attrs)
    {
	super(context, attrs);
	// TODO Auto-generated constructor stub
    }

    public LetterView(Context context)
    {
	super(context);
	// TODO Auto-generated constructor stub
	init();
    }

    private void init()
    {
    }
    
}
