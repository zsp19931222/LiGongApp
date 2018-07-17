package yh.app.view;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

public class MySettionView extends View implements View.OnTouchListener {

	public MySettionView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			
		}
		
		return false;
	}

}
