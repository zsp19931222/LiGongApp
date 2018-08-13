package yh.app.quanzitool;

import yh.app.function.liaotianjiemian;import com.yhkj.cqgyxy.R;
import 云华.智慧校园.工具.DateTools;

import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LiaoTianMessage
{
	public View addMessateMy(Context mContext, LinearLayout layout, String content, Date standardTime, Date fssj, boolean isShowPB)
	{

		LayoutInflater inflater = LayoutInflater.from(mContext);
		View mView = inflater.inflate(R.layout.quanzi_layout_liaotian_item_my, layout, false);
		TextView message = (TextView) mView.findViewById(R.id.message);
		message.setText(content);
		layout.addView(mView);
		if (Double.valueOf(DateTools.getTimeDifference(standardTime, fssj)) / 60 > 3.0)
		{
			((liaotianjiemian) mContext).standardTime = DateTools.DateToStringYMDHMS(fssj);
			mView.findViewById(R.id.time).setVisibility(View.VISIBLE);
			((TextView) mView.findViewById(R.id.time)).setText(DateTools.DateToStringYMDHM(fssj));
		}
		if (isShowPB)
			mView.findViewById(R.id.pb).setVisibility(View.VISIBLE);
		else
			mView.findViewById(R.id.pb).setVisibility(View.GONE);
		return mView;
	}

	public View addMessateOther(Context mContext, LinearLayout layout, String content, Date standardTime, Date fssj)
	{
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View mView = inflater.inflate(R.layout.quanzi_layout_liaotian_item_other, layout, false);
		TextView message = (TextView) mView.findViewById(R.id.message);
		message.setText(content);
		layout.addView(mView);
		double d = Double.valueOf(DateTools.getTimeDifference(standardTime, fssj)) / 60.0;
		if (d > 3.0)
		{
			((liaotianjiemian) mContext).standardTime = DateTools.DateToStringYMDHMS(fssj);
			mView.findViewById(R.id.time).setVisibility(View.VISIBLE);
			((TextView) mView.findViewById(R.id.time)).setText(DateTools.DateToStringYMDHM(fssj));
		}
		return mView;
	}
}
