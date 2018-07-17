package yh.app.wisdomclass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import yh.app.tool.JsonListMap;import yh.app.appstart.lg.R;
import yh.tool.widget.LoadDiaog;

@SuppressLint(
{
		"InflateParams", "ClickableViewAccessibility"
})
public class zhkt_jsdmxs implements View.OnClickListener
{
	private List<Map<String, String>> temp, list;
	private LinearLayout layout;
	private Context context;
	private List<View> ViewList = new ArrayList<View>();
	private int dqxszt;
	private View dqView;
	private TextView sdrs, cdrs, kkrs, qjrs;
	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			super.handleMessage(msg);
			try
			{
				px(new JSONArray(msg.getData().get("msg").toString()), "" + msg.what);
			} catch (JSONException e)
			{
			}
		};
	};

	public zhkt_jsdmxs(LinearLayout layout, Context context, TextView sdrs, TextView cdrs, TextView kkrs, TextView qjrs, List<Map<String, String>> temp)
	{
		this.temp = temp;
		this.layout = layout;
		this.context = context;
		this.sdrs = sdrs;
		this.cdrs = cdrs;
		this.kkrs = kkrs;
		this.qjrs = qjrs;
	}

	public void px(JSONArray jsa, String dqdmcs)
	{
		List<Map<String, String>> pxList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList0 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList1 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList2 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList3 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList4 = new ArrayList<Map<String, String>>();
		List<Map<String, String>> pxList5 = new ArrayList<Map<String, String>>();
		for (int i = 0; i < jsa.length(); i++)
		{
			try
			{
				// 到勤(1), 未到(0), 迟到(2), 早退(3), 病假(4), 事假(5);
				JSONObject jso = jsa.getJSONObject(i);
				if (jso.getString("DJC").equals(dqdmcs))
					if (jso.getString("DMZT").equals("0"))
					{
						setMap(pxList0, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					} else if (jso.getString("DMZT").equals("1"))
					{
						setMap(pxList1, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					} else if (jso.getString("DMZT").equals("2"))
					{
						setMap(pxList2, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					} else if (jso.getString("DMZT").equals("3"))
					{
						setMap(pxList3, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					} else if (jso.getString("DMZT").equals("4"))
					{
						setMap(pxList4, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					} else if (jso.getString("DMZT").equals("5"))
					{
						setMap(pxList5, jso.getString("XH"), jso.getString("STUDENT_NAME"), jso.getString("DMZT"));
					}
			} catch (Exception e)
			{
			}
			// if (step == 0)
			// {
			// try
			// {
			// JSONObject jso = jsa.getJSONObject(i);
			// if (!jso.getString("DMZT").equals("1") &&
			// jso.getString("DJC").equals(dqdmcs))
			// {
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("XH", jso.getString("XH"));
			// map.put("XM", jso.getString("STUDENT_NAME"));
			// map.put("ZT", jso.getString("DMZT"));
			// pxList.add(map);
			// }
			// } catch (JSONException e)
			// {
			// }
			// } else
			// {
			// try
			// {
			// JSONObject jso = jsa.getJSONObject(i);
			// if (jso.getString("DMZT").equals("1") &&
			// jso.getString("DJC").equals(dqdmcs))
			// {
			// Map<String, String> map = new HashMap<String, String>();
			// map.put("XH", jso.getString("XH"));
			// map.put("XM", jso.getString("STUDENT_NAME"));
			// map.put("ZT", "1");
			// pxList.add(map);
			// }
			// } catch (JSONException e)
			// {
			// }
			// }
		}
		pxList.addAll(pxList0);
		pxList.addAll(pxList2);
		pxList.addAll(pxList3);
		pxList.addAll(pxList4);
		pxList.addAll(pxList5);
		pxList.addAll(pxList1);
		this.list = pxList;
		doit(pxList);
	}

	private void setMap(List<Map<String, String>> pxList, String xh, String xm, String dmzt)
	{
		synchronized (dmzt)
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("XH", xh);
			map.put("XM", xm);
			map.put("ZT", dmzt);
			pxList.add(map);
		}
	}

	public void fresh()
	{
		int num = ViewList.size();
		for (int i = 0; i < num; i++)
		{
			TextView text = (TextView) ViewList.get(i).findViewById(R.id.zhkt_dmxszt_sub_button);
			ImageView img = (ImageView) ViewList.get(i).findViewById(R.id.zhkt_dmxszt_sub_img);
			text.setBackgroundResource(R.drawable.zhkt_dm_no);
			img.setBackgroundResource(R.drawable.dianming_no);
		}
	}

	public void doit(List<Map<String, String>> pxList)
	{
		// 已点名学生个数
		int sdrs_num = 0;
		int cdrs_num = 0;
		int qjrs_num = 0;
		int kkrs_num = 0;
		layout.removeAllViews();
		ViewList = new ArrayList<View>();
		int num = 0;
		for (int i = 0; i <= (pxList.size() - 1) / 4; i++)
		{
			if (num >= pxList.size())
				break;
			LayoutInflater inflaterLayout = LayoutInflater.from(context);
			View row = inflaterLayout.inflate(R.layout.zhkt_ktdmxszt_row, null);
			for (int j = 1; j <= 4; j++)
			{
				if (num >= pxList.size())
					break;
				LayoutInflater inflater = LayoutInflater.from(context);
				final View view = inflater.inflate(R.layout.zhkt_ktdmxszt, null);
				ViewList.add(view);
				view.setTag(num);
				TextView text = (TextView) view.findViewById(R.id.zhkt_dmxszt_sub_button);
				ImageView img = (ImageView) view.findViewById(R.id.zhkt_dmxszt_sub_img);
				text.setText(pxList.get(i * 4 + j - 1).get("XM"));
				if (pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.到勤.getValue()))
				{
					text.setBackgroundResource(R.drawable.zhkt_dm_yes);
					img.setBackgroundResource(R.drawable.dianming_yes);
					sdrs_num++;
				} else if (pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.早退.getValue()) || pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.未到.getValue()))
				{
					text.setBackgroundResource(R.drawable.zhkt_dm_no);
					img.setBackgroundResource(R.drawable.dianming_no);
					kkrs_num++;
				} else if (pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.事假.getValue()))
				{
					text.setBackgroundResource(R.drawable.zhkt_dm_no);
					img.setBackgroundResource(R.drawable.dianming_sj);
					qjrs_num++;
				} else if (pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.病假.getValue()))
				{
					text.setBackgroundResource(R.drawable.zhkt_dm_no);
					img.setBackgroundResource(R.drawable.dianming_bj);
					qjrs_num++;
				} else if (pxList.get(i * 4 + j - 1).get("ZT").equals("" + _点名状态.迟到.getValue()))
				{
					text.setBackgroundResource(R.drawable.zhkt_dm_no);
					img.setBackgroundResource(R.drawable.dianming_cd);
					cdrs_num++;
				}
				view.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						xs_num = Integer.valueOf(v.getTag().toString());
						showpop(v);
						dqView = v;
					}
				});
				switch (j)
				{
				case 1:
					RelativeLayout rel1 = (RelativeLayout) row.findViewById(R.id.zhkt_dmxszt_layout1);
					rel1.addView(view);
					break;
				case 2:
					RelativeLayout rel2 = (RelativeLayout) row.findViewById(R.id.zhkt_dmxszt_layout2);
					rel2.addView(view);
					break;
				case 3:
					RelativeLayout rel3 = (RelativeLayout) row.findViewById(R.id.zhkt_dmxszt_layout3);
					rel3.addView(view);
					break;
				case 4:
					RelativeLayout rel4 = (RelativeLayout) row.findViewById(R.id.zhkt_dmxszt_layout4);
					rel4.addView(view);
					break;
				default:
					break;
				}
				num++;
			}
			layout.addView(row);
		}
		sdrs.setHint("到勤:" + sdrs_num + "");
		cdrs.setHint("迟到:" + cdrs_num + "");
		qjrs.setHint("请假:" + qjrs_num + "");
		kkrs.setHint("未到:" + kkrs_num + "");
		this.list = pxList;
		
	}

	public void dmks()
	{
		doit(temp);
	}

	private int xs_num;

	public void refresh(List<Map<String, String>> ydmxslb, List<Map<String, String>> list) // "XH"
	{
		sdrs.setHint("到勤:" + ydmxslb.size() + "");
		for (int i = 0; i < ydmxslb.size(); i++)
		{
			for (int j = 0; j < temp.size(); j++)
			{
				String xh1 = temp.get(j).get("XH");
				String xh2 = ydmxslb.get(i).get("xh");
				if (xh2.equals(xh1))
				{
					temp.get(j).put("ZT", "1");
					View view = ViewList.get(j);
					TextView text = (TextView) view.findViewById(R.id.zhkt_dmxszt_sub_button);
					ImageView img = (ImageView) view.findViewById(R.id.zhkt_dmxszt_sub_img);
					text.setBackgroundResource(R.drawable.zhkt_dm_yes);
					img.setBackgroundResource(R.drawable.dianming_yes);
					break;
				}
			}
		}
	}

	public String getTjList(int index)
	{
		List<Map<String, String>> tjlist = new ArrayList<Map<String, String>>();
		int num = 0;
		while (num < 25)
		{
			if (index * 25 + num >= temp.size())
				break;
			Map<String, String> map = new HashMap<String, String>();
			map.put("xh", temp.get(index * 25 + num).get("XH"));
			map.put("dmzt", temp.get(index * 25 + num).get("ZT"));
			tjlist.add(map);
			num++;
		}
		return new JsonListMap().MapToJsonString(tjlist);
	}

	public int gettjdmcs(List<Map<String, String>> list)
	{
		return (list.size() - 1) / 25 + 1;
	}

	private void setView(View v, String zt)
	{
		try
		{
			TextView text = (TextView) v.findViewById(R.id.zhkt_dmxszt_sub_button);
			ImageView img = (ImageView) v.findViewById(R.id.zhkt_dmxszt_sub_img);
			if (zt.equals("1"))
			{
				text.setBackgroundResource(R.drawable.zhkt_dm_yes);
				img.setBackgroundResource(R.drawable.dianming_yes);
			} else
			{
				text.setBackgroundResource(R.drawable.zhkt_dm_no);
				switch (zt)
				{
				case "0":
					img.setBackgroundResource(R.drawable.dianming_no);
					break;
				case "2":
					img.setBackgroundResource(R.drawable.dianming_cd);
					break;
				case "3":
					img.setBackgroundResource(R.drawable.dianming_zt);
					break;
				case "4":
					img.setBackgroundResource(R.drawable.dianming_bj);
					break;
				case "5":
					img.setBackgroundResource(R.drawable.dianming_sj);
					break;
				default:
					break;
				}

			}
			popupWindow.dismiss();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	private enum _点名状态
	{
		到勤(1), 未到(0), 迟到(2), 早退(3), 病假(4), 事假(5);
		int i = 0;

		private _点名状态(int i)
		{
			this.i = i;
		}

		public int getValue()
		{
			return i;
		}
	}

	@Override
	public void onClick(View v)
	{
		try
		{
			int cdrs_num = 0;
			int qjrs_num = 0;
			int kkrs_num = 0;
			int sdrs_num = 0;
			switch (v.getId())
			{
			case R.id.zhkt_dq:
				dqxszt = 1;
				break;
			case R.id.zhkt_cd:
				dqxszt = 2;
				break;
			case R.id.zhkt_zt:
				dqxszt = 3;
				break;
			case R.id.zhkt_kk:
				dqxszt = 0;
				break;
			case R.id.zhkt_bj:
				dqxszt = 4;
				break;
			case R.id.zhkt_sj:
				dqxszt = 5;
				break;
			default:
				break;
			}
			list.get(xs_num).put("ZT", "" + dqxszt);
			setView(dqView, "" + dqxszt);
			for (int i = 0; i < list.size(); i++)
			{
				if (list.get(i).get("ZT").equals("" + _点名状态.到勤.getValue()))
				{
					sdrs_num++;
				} else if (list.get(i).get("ZT").equals("" + _点名状态.未到.getValue()) || list.get(i).get("ZT").equals("" + _点名状态.早退.getValue()))
				{
					kkrs_num++;
				} else if (list.get(i).get("ZT").equals("" + _点名状态.迟到.getValue()))
				{
					cdrs_num++;
				} else if (list.get(i).get("ZT").equals("" + 4) || list.get(i).get("ZT").equals("" + 5))
				{
					qjrs_num++;
				}
			}
			sdrs.setHint("实到:" + sdrs_num + "");
			qjrs.setHint("请假:" + qjrs_num + "");
			cdrs.setHint("迟到:" + cdrs_num + "");
			kkrs.setHint("未到:" + kkrs_num + "");
		} catch (Exception e)
		{
		}
	}

	private PopupWindow popupWindow;

	private void showpop(View view)
	{
		if (zhkt.canChange)
		{
			View contentView = LayoutInflater.from(context).inflate(R.layout.zhkt_pop, null);
			popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
			popupWindow.setTouchable(true);
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			popupWindow.setTouchInterceptor(new OnTouchListener()
			{
				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					return false;
				}
			});
			popupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.buttom_biankuang));
			popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
			contentView.findViewById(R.id.zhkt_dq).setOnClickListener(this);
			contentView.findViewById(R.id.zhkt_cd).setOnClickListener(this);
			contentView.findViewById(R.id.zhkt_zt).setOnClickListener(this);
			contentView.findViewById(R.id.zhkt_kk).setOnClickListener(this);
			contentView.findViewById(R.id.zhkt_bj).setOnClickListener(this);
			contentView.findViewById(R.id.zhkt_sj).setOnClickListener(this);
		}
	}
}
