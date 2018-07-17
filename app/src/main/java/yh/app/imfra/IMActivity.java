//package yh.app.imfra;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import org.androidpn.push.Constants;
//import org.jivesoftware.smack.Chat;
//import org.jivesoftware.smack.ChatManager;
//import org.jivesoftware.smack.MessageListener;
//import org.jivesoftware.smack.XMPPException;
//
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.listeners.MyChatMessageListrners;
//import  com.yhkj.cqswzyxy.ActivityManager;
//import  com.yhkj.cqswzyxy.MainActivity;
//
//import  com.yhkj.cqswzyxy.R;
//
//import java.util.Calendar;
//
//import android.widget.ImageButton;
//import android.app.Activity;
//import android.content.Intent;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ListView;
///**
// * 
// * 包	名:yh.app.imfra
// * 类	名:IMActivity.java
// * 功	能:好友聊天界面
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//@SuppressWarnings("unused")
//public class IMActivity extends ActivityPortrait {
//	private 		Button 							mBtnSend=null;
//	private 		ImageButton 					mBtnBack=null;
//	private 		EditText 						mEditTextContent=null;
//	private 		Collection<MessageListener>		messlister=null;
//	private 		ChatManager 					chatmanager=null;
//	private 		ChatMsgViewAdapter 				mAdapter;
//	private 		ListView 						mListView;
//	private 		List<ChatMsgEntity> 			mDataArrays = new ArrayList<ChatMsgEntity>();
//	public static 	Chat							chat=null;
//	public static 	String 							user="";
//	public static 	Handler 						mHandler=null;
//	public static	String 							theardid="";
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.chat);
//		ActivityManager.imactivity=this;
//		initView();
//		initData();
//		chatmanager=Constants.xmppManager.getConnection().getChatManager();
//        SQLiteDatabase db=new SqliteHelper().getWrite();
//		Cursor result =db.rawQuery("select * from talks where fromuser='"+IMActivity.user+"' and states=0",null);
//		result.moveToFirst();
//		while(!result.isAfterLast())
//		{
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(result.getString(4));
//			entity.setName(result.getString(1));
//			entity.setMsgType(true);
//			entity.setText(result.getString(3));
//			mDataArrays.add(entity);
//			mAdapter.notifyDataSetChanged();
//			db.execSQL("update talks set states=1 where fromuser='"+IMActivity.user+"' and talksid="+result.getInt(0));
//			result.moveToNext();
//		}
//		db.close();
//		
//        mBtnSend.setOnClickListener(new OnClickListener() {
//			public void onClick(View arg0) {
//				try {
//		        	IMActivity.chat.sendMessage(mEditTextContent.getText().toString());
//		        	send();
//				} catch (XMPPException e) {
//					// TODO Auto-generated catch block
//				//	e.printStackTrace();
//				}
//           }
//		});
//         mHandler = new Handler() {  
//            public void handleMessage(android.os.Message msg) {  
//                super.handleMessage(msg);  
//                switch (msg.what) {  
//                case 1:  
//                	String []f=msg.obj.toString().split("/");
//                	ChatMsgEntity entity = new ChatMsgEntity();
//        			entity.setDate(getDate());
//        			entity.setName(f[1]);
//        			entity.setMsgType(true);
//        			entity.setText(f[0]);
//        			mDataArrays.add(entity);
//        			mAdapter.notifyDataSetChanged();
//        			mListView.setSelection(mListView.getCount() - 1); 
//                    break;  
//                }  
//            }  
//        };
//        MyChatMessageListrners.mHandler=mHandler;
//	}
//	private String getDate() {
//        Calendar c = Calendar.getInstance();
//        String year = String.valueOf(c.get(Calendar.YEAR));
//        String month = String.valueOf(c.get(Calendar.MONTH));
//        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//        String mins = String.valueOf(c.get(Calendar.MINUTE));
//        StringBuffer sbBuffer = new StringBuffer();
//        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
//        return sbBuffer.toString();
//    }
//	private void initView() {
//		mListView = (ListView) findViewById(R.id.friend_chat_listview);
//		mBtnBack = (ImageButton) findViewById(R.id.friend_chat_back);
//		//mBtnBack.setOnClickListener(new BackListener(FriendChatActivity.this));
//		mBtnSend = (Button) findViewById(R.id.friend_chat_btn_send);
//		
//		mEditTextContent = (EditText) findViewById(R.id.friend_chat_et_sendmessage);
//	}
//	private void initData() {
//		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
//		mListView.setAdapter(mAdapter);
//	}
//	private void send()
//	{
//		String contString = mEditTextContent.getText().toString();
//		if (contString.length() > 0)
//		{
//			ChatMsgEntity entity = new ChatMsgEntity();
//			entity.setDate(getDate());
//			entity.setName("我");
//			entity.setMsgType(false);
//			entity.setText(contString);
//			mDataArrays.add(entity);
//			mAdapter.notifyDataSetChanged();
//			mEditTextContent.setText("");
//			mListView.setSelection(mListView.getCount() - 1);
//		}
//	}
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//	@Override  
//    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//        	Intent intent = new Intent();
//        	MyChatMessageListrners.mHandler=friendsFragment.mHandler;
//			intent.setClass(IMActivity.this,MainActivity.class);
//			startActivityForResult(intent, 0);
//            return true;  
//        } else  
//            return super.onKeyDown(keyCode, event);  
//    }
//}
