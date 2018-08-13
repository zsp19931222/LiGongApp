package yh.app.quanzitool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.jpushdemo.ExampleApplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import yh.app.tool.SqliteHelper;import com.yhkj.cqgyxy.R;
import yh.app.tool.ViewClickEffect;

public class InitLiaotianList
{
	private Context mContext;
	private List<Map<String, String>> maplist2 = new ArrayList<Map<String, String>>();
	LinearLayout quanzi_group_layout;

	public InitLiaotianList(Context mContext, View mView)
	{
		this.mContext = mContext;
		quanzi_group_layout = (LinearLayout) mView.findViewById(R.id.quanzi_liaotian_list);
		LinearLayout group_add_friend = (LinearLayout) mView.findViewById(R.id.quanzi_group_add);
		LinearLayout mrfz = (LinearLayout) mView.findViewById(R.id.quanzi_group_bm);
		doInit();
		initAction(group_add_friend, mrfz);
	}

	private void initAction(LinearLayout group_add_friend, LinearLayout mrfz)
	{
		group_add_friend.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				ViewClickEffect.doEffect(v, 200, mContext, "yh.app.quanzi.sshy", ExampleApplication.getAppPackage(), new String[][]
				{});
			}
		});
		mrfz.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				ViewClickEffect.doEffect(v, 200, mContext, "yh.app.quanzi.mrfz", ExampleApplication.getAppPackage(), new String[][]
				{});
			}
		});
	}

	public void doInit()
	{
		// StringToListMap stm = new StringToListMap();
		// maplist = stm.toListMap(s1);
		SQLiteDatabase db = new SqliteHelper().getWrite();
		maplist2 = getListMap(db);
		String SZM = "";
		String nowSZM = "";
		for (int i = 0; i < maplist2.size(); i++)
		{
			InflaterView iv = new InflaterView(mContext, maplist2.get(i).get("NAME"), maplist2.get(i).get("FRIEND_ID"),maplist2.get(i).get("userid"));
			SZM = maplist2.get(i).get("SZM");
			if (SZM.equals("") || !SZM.equals(nowSZM))
			{
				nowSZM = SZM;
				addTitle(iv, nowSZM);
			}
			quanzi_group_layout.addView(iv.addLIXInflater(quanzi_group_layout,maplist2.get(i).get("HANDIMG")));
		}
		db.close();
	}

	private void addTitle(InflaterView iv, String szm)
	{
		quanzi_group_layout.addView(iv.addTITLEInflater(szm, quanzi_group_layout));
	}

	private List<Map<String, String>> getListMap(SQLiteDatabase db)
	{
		Cursor c = db.rawQuery("select * from friend order by szm ASC", null);
		List<Map<String, String>> maplList = new ArrayList<Map<String, String>>();
		while (c.moveToNext())
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put("FRIEND_ID", c.getString(0));
			map.put("NAME", c.getString(1));
			map.put("SZM", c.getString(2));
			map.put("HANDIMG",c.getString(3));
			maplList.add(map);
		}
		return maplList;
	}
}