package yh.app.coursetable;

import java.util.List;
import java.util.Map;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.yhkj.cqgyxy.R;

import yh.app.tool.DpPx;

@SuppressLint("InflateParams")
public class TableSttingPop
{
	private PopupWindow mPopupWindow;
	private ListView listView;

	public void show(View view, Context context, List<Map<String, String>> list, final OnClickLisenler mOnClickLisenler)
	{
		View popView = LayoutInflater.from(context).inflate(R.layout.listview_pop, null);
		listView = (ListView) popView.findViewById(R.id.list_pop);

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				mOnClickLisenler.setmOnClickLisenler(view);
				mPopupWindow.dismiss();
			}
		});

		listView.setAdapter(new SimpleAdapter(context, list, R.layout.textview, new String[]
		{
				"data"
		}, new int[]
		{
				R.id.textview
		}));

		mPopupWindow = new PopupWindow(popView, new DpPx(context).getDpToPx(120), LayoutParams.WRAP_CONTENT, true);

		mPopupWindow.setTouchable(true);
		mPopupWindow.setOutsideTouchable(true);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

		mPopupWindow.showAsDropDown(view);
	}

	public interface OnClickLisenler
	{
		void setmOnClickLisenler(View view);
	}
}
