package 云华.智慧校园.工具;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
public class _空白填页
{
	public View addView(Context context, View hideView, ViewGroup parent, int picture, String text)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.zhkt_jtmykl, parent, false);
		if (hideView != null)
			hideView.setVisibility(View.GONE);
		view.findViewById(R.id.img).setBackgroundResource(picture);
		((TextView) view.findViewById(R.id.text)).setText(text);
		parent.addView(view);
		return view;
	}

	public View addView(Context context, View hideView, ViewGroup parent, Drawable picture, String text)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.zhkt_jtmykl, parent, false);
		hideView.setVisibility(View.GONE);
		view.findViewById(R.id.img).setBackground(picture);
		((TextView) view.findViewById(R.id.text)).setText(text);
		parent.addView(view);
		return view;
	}

	public View addView(Context context, View hideView, ViewGroup parent, Bitmap picture, String text)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.zhkt_jtmykl, parent, false);
		hideView.setVisibility(View.GONE);
		((ImageView) view.findViewById(R.id.img)).setImageBitmap(picture);
		((TextView) view.findViewById(R.id.text)).setText(text);
		parent.addView(view);
		return view;
	}
}
