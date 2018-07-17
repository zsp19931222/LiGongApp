package yh.app.appactivate;
//package  com.yhkj.cqswzyxy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.androidpn.push.Constants;
//import org.jivesoftware.smack.packet.Presence;
//
//import  com.yhkj.cqswzyxy.R;
//
//import yh.app.activitytool.ActivityPortrait;
//
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.HorizontalScrollView;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.AdapterView.OnItemClickListener;
///**
// * 
// * 包	名: com.yhkj.cqswzyxy
// * 类	名:MessageListActivity.java
// * 功	能:通知
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//public class MessageListActivity extends ActivityPortrait {
//	private ListView listview=null;
//	private ArrayList<HashMap<String, Object>> listItem=null;
//	private MyAdapter myadapter=null;
//	private Handler mHandler=null;
//	private SQLiteDatabase db=null;
//	private Cursor result=null;
//	private ViewHolder hoder;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_message_list);
//		listItem = new ArrayList<HashMap<String, Object>>(); 
//		listview=(ListView)findViewById(R.id.listDiary);
//		db=new SqliteHelper().getWrite();
//		result =db.rawQuery("select * from tonzhi",null);
//		result.moveToFirst();
//		while(!result.isAfterLast())
//		{
//			
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("id",result.getString(0));
//		    map.put("title",result.getString(1));
//		    map.put("message", result.getString(2));
//		    map.put("datetime", result.getString(3));
//		    map.put("states", result.getString(4));
//		    listItem.add(map);
//			//db.execSQL("update tonzhi set states='��' where tonzhiid="+result.getInt(0));
//			result.moveToNext();
//		}
//		db.close();
//		myadapter=new MyAdapter(this,listItem);
//		listview.setAdapter(myadapter);
//		listview.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				String title=listItem.get(arg2).get("title").toString();
//				String message=listItem.get(arg2).get("message").toString();
//				String datetime=listItem.get(arg2).get("datetime").toString();
//				String state=listItem.get(arg2).get("states").toString();
//				/*
//				for (int i=0;i<listItem.size();i++) {
//					if(listItem.get(i).get("id").equals(listItem.get(arg2).get("id").toString()))
//					{
//						listItem.get(i).put("states", "��");
//						break;
//					}
//				}*/
//				android.os.Message msg=new android.os.Message();
//				msg.what=1;
//				mHandler.sendMessage(msg);
//				Intent intent = new Intent();
//				intent.setClass(MessageListActivity.this, MessageActivity.class);
//				intent.putExtra("title",title);
//				intent.putExtra("message",message);
//				intent.putExtra("datetime",datetime);
//				intent.putExtra("states",state);
//				startActivityForResult(intent, 0);
//			}
//		});
//		mHandler = new Handler() {  
//            public void handleMessage(android.os.Message msg) {  
//                super.handleMessage(msg);  
//                switch (msg.what) {  
//                case 1:
//                	myadapter.notifyDataSetChanged();
//                    break;
//                case 2:
//                	
//                }  
//            }  
//        };  
//	}
//	private class MyAdapter extends BaseAdapter
//	{
//		private LayoutInflater mInflater;
//		private ArrayList<HashMap<String, Object>> listIt;
//        public MyAdapter(Context context,ArrayList<HashMap<String, Object>> list) { 
//           this.listIt=list;
//           this.mInflater=LayoutInflater.from(context);
//        } 
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return listIt.size();
//		}
//
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int arg0, View arg1, ViewGroup arg2) {
//			// TODO Auto-generated method stub
//			if(arg1==null)
//			{
//				arg1=mInflater.inflate(R.layout.main_message_sub, null);
//				hoder=new ViewHolder();
//				hoder.index=arg0;
//				hoder.texttitle=(TextView)arg1.findViewById(R.id.notification_time);
//				hoder.textcontent=(TextView)arg1.findViewById(R.id.notification_content);
//				hoder.texttime=(TextView)arg1.findViewById(R.id.diarylist_sub_time);
//				hoder.imageview=(ImageView)arg1.findViewById(R.id.main_message_image);
//				hoder.texttype=(TextView)arg1.findViewById(R.id.message_type);
//				hoder.imagekuai=(ImageView)arg1.findViewById(R.id.main_message_kai_image);
//				hoder.imagekuai.setTag(arg0);
//				hoder.imagekuai.setOnClickListener(new View.OnClickListener() {
//					@Override
//					public void onClick(View arg) {
//						// TODO Auto-generated method stub
//						db=new SqliteHelper().getWrite();
//						
//						ImageView a=(ImageView)arg;
//						/*
//						a.setImageDrawable(getResources().getDrawable(
//								R.drawable.guan));*/
//						if(listItem.get((Integer)a.getTag()).get("states").toString().equals("δ"))
//						{
//							listItem.get((Integer)a.getTag()).put("states", "��");
//							db.execSQL("update tonzhi set states='��' where tonzhiid="+Integer.parseInt(listItem.get((Integer)a.getTag()).get("id").toString()));
//						}
//						else
//						{
//							listItem.get((Integer)a.getTag()).put("states", "δ");
//							db.execSQL("update tonzhi set states='δ' where tonzhiid="+Integer.parseInt(listItem.get((Integer)a.getTag()).get("id").toString()));
//						}
//						db.close();
//						android.os.Message msg=new android.os.Message();
//						msg.what=1;
//						mHandler.sendMessage(msg);
//					}
//				});
//				arg1.setTag(hoder);
//			}
//			else
//			{
//				hoder=(ViewHolder)arg1.getTag();
//			}
//			hoder.texttitle.setText(listIt.get(arg0).get("title").toString());
//			hoder.textcontent.setText(listIt.get(arg0).get("message").toString());
//			hoder.texttime.setText(listIt.get(arg0).get("datetime").toString());
//			hoder.texttype.setText("(ѧ��֪ͨ)");
//			
//			if(listItem.get(arg0).get("states").toString().equals("δ"))
//			{
//				hoder.imagekuai.setImageDrawable(getResources().getDrawable(
//						R.drawable.guan));
//			}
//			else
//			{
//				hoder.imagekuai.setImageDrawable(getResources().getDrawable(
//						R.drawable.kai));
//			}
//			return arg1;
//		}
//		
//	}
//	private final class ViewHolder
//	{
//		public TextView texttitle;
//		public TextView textcontent;
//		public TextView texttime;
//		public ImageView imageview;
//		public TextView texttype;
//		public ImageView imagekuai;
//		public int index;
//	}
//	@Override  
//    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//        	Intent intent = new Intent(); 
//			intent.setClass(MessageListActivity.this,MainActivity.class);
//			startActivityForResult(intent, 0);
//            return true;  
//        } else  
//            return super.onKeyDown(keyCode, event);  
//    }
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//	// TODO Auto-generated method stub
//	if(requestCode == 0 && resultCode == RESULT_OK) {
//	 finish();
//	 }
//	}
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.message_list, menu);
//		return true;
//	}
//
//}
