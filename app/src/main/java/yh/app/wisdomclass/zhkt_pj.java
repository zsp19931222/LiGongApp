package yh.app.wisdomclass;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import yh.app.activitytool.ActivityPortrait;
import 云华.智慧校园.工具.IsNull;import com.yhkj.cqgyxy.R;

public class zhkt_pj extends ActivityPortrait
{
	private Context context;

	public zhkt_pj(Context context)
	{
		this.context = context;
	}

	public void xspj(LinearLayout layout, JSONArray jso)
	{
		for (int i = 0; i < 4; i++)
		{
			try
			{
				freshPJ((LinearLayout) layout.getChildAt(i + 1), Integer.valueOf(jso.getJSONObject(0).getString("PJBZ" + (i + 1))));
			} catch (NumberFormatException e)
			{
			} catch (JSONException e)
			{
			}
		}
		try
		{
			TextView v = (TextView) layout.findViewById(R.id.zhkt_pj_text);
			String xsjy = jso.getJSONObject(0).getString("XSJY");
			if (!IsNull.isNotNull(xsjy))
				v.setText("");
			else
				v.setText(xsjy);
//			layout.addView(v);
		} catch (JSONException e)
		{
		}
	}

	public void xstjpj(LinearLayout layout, int[] df, String pjnr)
	{
		for (int i = 0; i < 4; i++)
		{
			freshPJ((LinearLayout) layout.getChildAt(i + 1), df[i]);
		}
		((TextView) layout.getChildAt(6)).setText(pjnr);
	}

	public void getjspj(LinearLayout layout, JSONArray jsa)
	{
		int pj[] = new int[4];
		for (int i = 0; i < jsa.length(); i++)
		{
			try
			{
				pj[0] += Integer.valueOf(jsa.getJSONObject(i).getString("PJBZ1"));
				pj[1] += Integer.valueOf(jsa.getJSONObject(i).getString("PJBZ2"));
				pj[2] += Integer.valueOf(jsa.getJSONObject(i).getString("PJBZ3"));
				pj[3] += Integer.valueOf(jsa.getJSONObject(i).getString("PJBZ4"));
			} catch (NumberFormatException e)
			{
				e.printStackTrace();
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
		}
		for (int i = 0; i < 4; i++)
		{
			freshPJ((LinearLayout) layout.getChildAt(i + 1), pj[i] / jsa.length());
		}
		for (int i = 0; i < jsa.length(); i++)
		{
			View v = null;
			if (i % 2 == 0)
			{
				v = LayoutInflater.from(context).inflate(R.layout.zhkt_pj_item1, null);
			} else
			{
				v = LayoutInflater.from(context).inflate(R.layout.zhkt_pj_item2, null);
			}
			try
			{
				String xsjy = jsa.getJSONObject(i).getString("XSJY");
				if (!IsNull.isNotNull("XSJY"))
					((TextView) v.findViewById(R.id.nr)).setText("");
				else
					((TextView) v.findViewById(R.id.nr)).setText(xsjy);
				layout.addView(v);
			} catch (JSONException e)
			{
			}
		}
	}

	private void freshPJ(LinearLayout layout, int df)
	{
		for (int i = 1; i <= df; i++)
		{
			layout.getChildAt(i).setBackgroundResource(R.drawable.pingjia);
		}
	}
}
