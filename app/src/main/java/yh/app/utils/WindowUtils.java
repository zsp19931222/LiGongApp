package yh.app.utils;

import android.content.Context;
import android.view.WindowManager;

public class WindowUtils {
	WindowManager manager;
	public WindowUtils(Context context) {
		 manager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	}
	
	@SuppressWarnings("deprecation")
	public int getHeight(){
		return manager.getDefaultDisplay().getHeight();
	}
	
	@SuppressWarnings("deprecation")
	public int getWidth(){
		return manager.getDefaultDisplay().getWidth();
	}
	
}
