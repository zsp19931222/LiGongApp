package yh.app.tool;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.yhkj.cqgyxy.R;
public class ToastShow
{
	public static Toast toast;

	public void show(Context context, String content)
	{
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
	}

	public void showImageToast(Context context, String content, int parent)
	{
		try
		{
			toast.cancel();
		} catch (Exception e)
		{
		}
		View view = LayoutInflater.from(context).inflate(R.layout.toast, ((ViewGroup) ((Activity) context).findViewById(parent)), false);
		TextView tv = (TextView) view.findViewById(R.id.content);
		tv.setHint(content);
		toast = new Toast(context);
		toast.setGravity(Gravity.CENTER, 0, -40);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(view);
		toast.show();
	}

}
