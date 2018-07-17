//package yh.app.listeners;
//
//import java.util.Calendar;
//import java.util.HashMap;
//import org.androidpn.push.Constants;
//import org.jivesoftware.smack.Chat;
//import org.jivesoftware.smack.ChatManagerListener;
//import org.jivesoftware.smack.MessageListener;
//import org.jivesoftware.smack.packet.Message;
//
//import yh.app.imfra.IMActivity;
//import yh.app.imfra.friendsFragment;
//
//
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Handler;
///**
// * 包	名:yh.app.listeners
// * 类	名:MyChatMessageListrners.java
// * 功	能:获取聊天内容
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//public class MyChatMessageListrners implements ChatManagerListener {
//	public static Handler mHandler=null;
//	public static HashMap<String, Chat> chats=new HashMap<String, Chat>();
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
//	public void chatCreated(Chat arg0, boolean arg1) {
//		// TODO Auto-generated method stub
//		String[] user=arg0.getParticipant().split("/");
//		chats.put(user[0], arg0);
//		arg0.addMessageListener(new MessageListener() {
//			public void processMessage(Chat arg3, Message arg4) {
//				//arg3.sendMessage("dingding....."+arg4.getBody());
//				String[] str=arg3.getParticipant().split("/");
//				if(Constants.IMstate.equals("liebiao")){
//				
//					for (int i=0;i<friendsFragment.childArray.size();i++) {
//						for(int j=0;j<friendsFragment.childArray.get(i).size();j++){
//						if(friendsFragment.childArray.get(i).get(j).get("id").equals(str[0]))
//						{
//							friendsFragment.childArray.get(i).get(j).put("message", "��");
//							SQLiteDatabase db=new SqliteHelper().getWrite();
//							db.execSQL("insert into talks(fromuser,tofromuser,message,datetime,states) values(?,?,?,?,?)",new Object[]{str[0],Constants.number,arg4.getBody(),getDate(),0});
//						    db.close();
//							break;
//						}
//						}
//					}
//					android.os.Message msg=new android.os.Message();
//					msg.what=1;
//					mHandler.sendMessage(msg);
//				}
//				else 
//				{
//					if(!IMActivity.user.equals(str[0])&&(!arg4.getBodies().equals("null")))
//					{
//						for (int i=0;i<friendsFragment.childArray.size();i++) {
//							for(int j=0;j<friendsFragment.childArray.get(i).size();j++){
//								if(friendsFragment.childArray.get(i).get(j).get("id").equals(str[0]))
//								{
//									friendsFragment.childArray.get(i).get(j).put("message","��");
//									SQLiteDatabase db=new SqliteHelper().getWrite();
//									db.execSQL("insert into talks(fromuser,tofromuser,message,datetime,states) values(?,?,?,?,?)",new Object[]{str[0],Constants.number,arg4.getBody(),getDate(),0});
//								    db.close();
//									break;
//								}
//							}
//						}
//					}
//					//String[] str=arg3.getParticipant().split("/");
//					if(arg4.getThread().equals(IMActivity.theardid)){
//					android.os.Message msg=new android.os.Message();
//					msg.what=1;
//					msg.obj=arg4.getBody()+"/"+arg4.getFrom();
//					mHandler.sendMessage(msg);
//					}
//				}
//			}
//		});
//	}
//}
