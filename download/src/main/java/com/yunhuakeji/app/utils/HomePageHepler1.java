package com.yunhuakeji.app.utils;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.yunhuakeji.app.R;
import com.yunhuakeji.app.TargetDetailActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "UseSparseArrays", "InflateParams" })
public class HomePageHepler1 implements View.OnClickListener
{
	Context context;
	private JSONArray jsa;

	private static final String TARGET_STATE_MORE = "查看更多";

	private static final String TARGET_STATE_TITLE = "自主目标";
	/**
	 * 每日目标
	 * @param context
	 * @param parent
	 * @param data
	 * @throws Exception
	 */
	public void setPublicView(Context context, LinearLayout parent, String data) throws Exception
	{
		jsa = new JSONArray(data);
		for (int i = 0; i < jsa.length(); i++)
		{
			Map<Integer, View> map = new HashMap<Integer, View>();
			JSONObject jso = jsa.getJSONObject(i);
			for (int j = 0; j < jso.getJSONArray("list").length(); j++)
			{
				View item = LayoutInflater.from(context).inflate(R.layout.yh_item_function_layout_no_biankuang, null, false);
				if (i == 0)
				{
					int wh = new DpPx(context).getDpToPx(100);
					AbsListView.LayoutParams lp = new AbsListView.LayoutParams(wh, wh);
					item.setLayoutParams(lp);
				}
				((TextView) item.findViewById(R.id.txt_function_layout)).setText(jso.getJSONArray("list").getJSONObject(j).getString("function_name"));
				item.findViewById(R.id.img_function_layout).setBackgroundResource(new ROutHelper().getDrawable(R.class, jso.getJSONArray("list").getJSONObject(j).getString("function_face")));
				map.put(j, item);
			}
			TitleGridView titleGridView = new TitleGridView(context, parent);
			titleGridView.setOnItemViewClick(JsonTools.getJSONObject(jsa, i).getJSONArray("list"));
			int NumColumns = 4;
			if (jso.getJSONArray("list").length() < 4)
				NumColumns = jso.getJSONArray("list").length();
			parent.addView(titleGridView.setAdapter(jso.getString("name"), new Home1ItemAdapter(map)).setNumColumns(NumColumns).getTitleGirdView());
		}
	}

	public void setPrivateView(Context context, LinearLayout parent, String data) throws Exception
	{
		parent.addView(setPrivateViewItem(context, parent, TARGET_STATE_TITLE, TARGET_STATE_MORE, 16, 16, 0xff393939, 0xffff0000, 0 + ""));

		JSONArray jsonOngoing = new JSONObject(data).getJSONObject("content").getJSONArray("ongoing");

		for (int i = 0; i < jsonOngoing.length(); i++)
		{
			String leftText 	= jsonOngoing.getJSONObject(i).getString("t_name");
			String rightText 	= jsonOngoing.getJSONObject(i).getString("status_name");
			String id 			= jsonOngoing.getJSONObject(i).getString("t_id");

			parent.addView(setPrivateViewItem(context, parent, leftText, rightText, 14, 14, 0xff393939, 0xff00ff00, id));
		}

		JSONArray jsonComplete = new JSONObject(data).getJSONObject("content").getJSONArray("completed");

		for (int i = 0;i < jsonComplete.length();i++)
		{
			String leftText 	= jsonComplete.getJSONObject(i).getString("t_name");
			String rightText 	= jsonComplete.getJSONObject(i).getString("status_name");
			String id 			= jsonComplete.getJSONObject(i).getString("t_id");

			parent.addView(setPrivateViewItem(context, parent, leftText, rightText, 14, 14, 0xffd0d0d0, 0xff0000ff, id));
		}
	}

	public View createPrivateViewItem(Context context, LinearLayout parent)
	{
		return LayoutInflater.from(context).inflate(R.layout.yh_view_double_textview_left_right, parent, false);
	}
	/**
	 * 自主目标
	 * @param context
	 * @param parent
	 * @param leftText
	 * @param rightText
	 * @param leftTextSize
	 * @param rightTextSize
	 * @param leftTextColor
	 * @param rightTextColor
	 * @param id
	 * @return
	 */
	private View setPrivateViewItem(Context context, LinearLayout parent, String leftText, String rightText, int leftTextSize, int rightTextSize, int leftTextColor, int rightTextColor, String id)
	{
		this.context=context;
		View view = createPrivateViewItem(context, parent);

		TextView left = ((TextView) view.findViewById(R.id.txt_view_double_textview_left));
		TextView right = ((TextView) view.findViewById(R.id.txt_view_double_textview_right));

		right.setText(rightText);
		right.setTextColor(rightTextColor);
		right.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightTextSize);

		left.setText(leftText);
		left.setTextColor(leftTextColor);
		left.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftTextSize);

		view.setOnClickListener(this);
		view.setTag(id);

		return view;
	}

	@Override
	public void onClick(View v)
	{
		if(v.getTag() != null && !v.getTag().toString().equals("0"))
		{
			Toast.makeText(context, "查看更多", 0).show();
			Intent intent = new Intent();
			intent.putExtra("id", v.getTag().toString());
			intent.setAction(TargetDetailActivity.class.getName());
			intent.setPackage(v.getContext().getPackageName());
			v.getContext().startActivity(intent);

		}
	}
}