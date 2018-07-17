package yh.app.quanzitool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.LinearLayout;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import yh.tool.widget.LoadDiaog;

public class InitMrfz
{
	private LinearLayout layout;
	private Context mContext;
	private List<Map<String, String>> maplist = new ArrayList<Map<String, String>>();
   
	public InitMrfz(LinearLayout layout, Context mContext)
	{
		this.layout = layout;
		this.mContext = mContext;
		
	}

	public void doInit(String tab)
	{
		maplist = getListMap(tab);
		String SZM = "";
		String nowSZM = "";
		for (int i = 0; i < maplist.size(); i++)
		{
			InflaterView iv = new InflaterView(mContext, maplist.get(i).get("name"), maplist.get(i).get("FRIEND_ID"),maplist.get(i).get("userid"));
			SZM = maplist.get(i).get("szm");
			if (SZM.equals("") || !SZM.equals(nowSZM))
			{
				nowSZM = SZM;
				addTitle(iv, nowSZM);
			}
			layout.addView(iv.addLIXInflater(layout,maplist.get(i).get("handimg")));
		}
		
	}

	private void addTitle(InflaterView iv, String szm)
	{
		layout.addView(iv.addTITLEInflater(szm, layout));
	}

	private List<Map<String, String>> getListMap(String tab)
	{
		return new SqliteHelper().rawQuery("select * from " + tab + " where userid=? order by szm ASC", Constants.number);
	}
}
