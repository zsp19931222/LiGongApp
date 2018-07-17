package yh.app.appstart.lg;
//package  com.yhkj.cqswzyxy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.androidpn.push.Constants;
//
//import  com.yhkj.cqswzyxy.R;
//
//import yh.app.activitytool.ActivityPortrait;
//
//import android.os.Bundle;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//
///**
// * 
// * 包 名: com.yhkj.cqswzyxy 
// * 类 名:MessageActivity.java 
// * 功 能:通知
// * 
// * @author 云华科技
// * @version 1.0
// * @date 2015-7-29
// */
//public class MessageActivity extends ActivityPortrait
//{
//
//	private ListView list;
//	private RelativeLayout rel_back;
//	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		//  // 标题栏去除
//		setContentView(R.layout.activity_client_pn_main);
//
//		initView();
//		getData();
//		showListView();
//	}
//
//	private void getData()
//	{
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor c = db.rawQuery("select * from tonzhi order by time desc", null);
//		while (c.moveToNext())
//		{
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("img", R.drawable.ic_launcher_nd);
//			map.put("title", c.getString(1).toString());
//			map.put("message", c.getString(2).toString());
//			map.put("time", c.getString(4));
//			dataList.add(map);
//		}
//		c.close();
//		db.close();
//	}
//
//	private void showListView()
//	{
//		SimpleAdapter Adater = new SimpleAdapter(this, dataList, R.layout.activity_client_pn_sub, new String[] { "img", "title", "message", "time" }, new int[] { R.id.pn_image, R.id.pn_title, R.id.pn_content, R.id.pn_time });
//		list.setAdapter(Adater);
//	}
//
//	private void initView()
//	{
//		list = (ListView) findViewById(R.id.pn_list);
//		rel_back = (RelativeLayout) findViewById(R.id.pn_back);
//	}
//
//}
