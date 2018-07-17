package 云华.智慧校园.工具;

import java.util.Locale;

import android.view.View;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import yh.app.logTool.Log;
 

public class ViewUtil
{
    public void logViewRealWH(final View view, final String tag)
    {
	view.getViewTreeObserver().addOnGlobalFocusChangeListener(new OnGlobalFocusChangeListener()
	{

	    @Override
	    public void onGlobalFocusChanged(View oldFocus, View newFocus)
	    {
		// TODO Auto-generated method stub
		int w = view.getHeight();
		int h = view.getWidth();
		Log.d(String.format(Locale.CHINA, tag + "  控件宽度:%d   控件高度:%d", w, h));
	    }
	});
    }
}