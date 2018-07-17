//package yh.app.imfra;
//
//import java.util.HashMap;
//
//import org.androidpn.push.Constants;
//
//import  com.yhkj.cqswzyxy.ActivityManager;
//import  com.yhkj.cqswzyxy.MainActivity;
//import yh.app.tool.BadgeView;
//
//import  com.yhkj.cqswzyxy.R;
//
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
///**
// * 
// * 包	名:yh.app.imfra
// * 类	名:AddfriendFragment.java
// * 功	能:添加好友界面
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//@SuppressWarnings("unused")
//public class AddfriendFragment extends Fragment {
//	private RelativeLayout button1=null;
//	private RelativeLayout button2=null;
//	private RelativeLayout button3=null;
//	private RelativeLayout button4=null;
//	private ImageView add_teacher_friends_img=null;
//	private Handler mHandler=null;
//	private SQLiteDatabase db=null;
//	private Cursor result=null;
//	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
//		View view=inflater.inflate(R.layout.add_friends2, container,false);
//		add_teacher_friends_img=(ImageView)view.findViewById(R.id.add_teacher_friends_img);
//		button1 =(RelativeLayout)view.findViewById(R.id.add_student_friends);
//		button1.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(); 
//				intent.setClass(ActivityManager.mactivity,AddInputActivity.class);
//				startActivity(intent);
//			}
//		});
//		button2 =(RelativeLayout)view.findViewById(R.id.add_teacher_friends);
//        button2.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(); 
//				intent.setClass(ActivityManager.mactivity,DealfriendActivity.class);
//				startActivity(intent);
//			}
//		});
//        button3 =(RelativeLayout)view.findViewById(R.id.create_group);
//        button3.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent(); 
//				intent.setClass(ActivityManager.mactivity,AddInputActivity.class);
//				startActivity(intent);
//			}
//		});
//        button4 =(RelativeLayout)view.findViewById(R.id.add_group);
//        button4.setOnClickListener(new View.OnClickListener() {
//	
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(ActivityManager.mactivity,AddInputActivity.class);
//				startActivity(intent);
//			}
//		});
//        mHandler = new Handler() {  
//            public void handleMessage(android.os.Message msg) {  
//                super.handleMessage(msg);  
//                switch (msg.what) {  
//                case 1:
//                	db=new SqliteHelper().getWrite();
//            		result =db.rawQuery("select * from friendapplication where tofromuser='"+Constants.number+"'",null);
//            		result.moveToFirst();
//            		int a=result.getCount();
//            		BadgeView badge = new BadgeView(ActivityManager.mactivity, add_teacher_friends_img);
//            		badge.setText(a+"");
//            		badge.show();
//            		db.close();
//                    break;
//                case 2:
//                }  
//            }  
//        };
//        Badge();
//		return view;
//	}
//	public void Badge() {
//		android.os.Message msg=new android.os.Message();
//		msg.what=1;
//		if(mHandler!=null){
//		mHandler.sendMessage(msg);
//		}
//	}
//}
