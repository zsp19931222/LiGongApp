//package yh.app.imfra;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//import org.androidpn.push.Constants;
//import org.jivesoftware.smack.ChatManager;
//import org.jivesoftware.smack.Connection;
//import org.jivesoftware.smack.Roster;
//import org.jivesoftware.smack.RosterEntry;
//import org.jivesoftware.smack.RosterGroup;
//import org.jivesoftware.smack.RosterListener;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smack.packet.Message;
//import org.jivesoftware.smack.packet.Presence;
//import org.jivesoftware.smackx.OfflineMessageManager;
//
//import yh.app.listeners.MyChatMessageListrners;
//import  com.yhkj.cqswzyxy.ActivityManager;
//import yh.app.tool.BadgeView;
//
//import  com.yhkj.cqswzyxy.R;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.ExpandableListView.OnChildClickListener;
//import android.widget.ImageView;
//import android.widget.TextView;
///**
// * 
// * 包	名:yh.app.imfra
// * 类	名:friendsFragment.java
// * 功	能:好友列表
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//@SuppressWarnings("unused")
//public class friendsFragment extends Fragment {
//	public static List<String>  groupArray=null;
//	public final int DIALOG_DELETE = 1;
//	public static 	List<List<HashMap<String, Object>>> childArray=null;
//    private 		ChatManager 				chatmanager;
//    private static 	int 						jian=0;
//    private 		int 						now_action_group;
//    private 		int 						now_action_child;
//    private 		ExpandableListView  		expandableListView_one;
//    private 		ExpandableListViewaAdapter 	exadapter;
//    private 		LayoutInflater 				mInflater;
//    private 		Roster 						roster		=null;
//    private 		Connection 					connection	=null;
//    public static 	Handler 					mHandler	=null;
//    
//	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
//		View view=inflater.inflate(R.layout.main_cycle_class, container,false);
//		expandableListView_one = (ExpandableListView)view.
//				findViewById(R.id.class_expandableListView);
//		if(!org.androidpn.client.Constants.xmppManager.isConnected()){
//			Toast.makeText(ActivityManager.mactivity, "数据加载中！",
//				    Toast.LENGTH_SHORT).show();
//			//org.androidpn.client.Constants.xmppManager.connect();
//			return view;
//		}
//        connection=org.androidpn.client.Constants.xmppManager.getConnection();
//		Constants.IMstate="liebiao";
//		roster = connection.getRoster();
//		roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
//		roster.addRosterListener(new RosterListener() {
//			@Override
//			public void presenceChanged(Presence arg0) {
//				// TODO Auto-generated method stub
//				String[]s=arg0.getFrom().split("/");
//				for (int i=0;i<childArray.size();i++) {
//					for(int j=0;j<childArray.get(i).size();j++){
//					if(childArray.get(i).get(j).get("id").equals(s[0]))
//					{
//						RosterEntry temp= roster.getEntry(s[0]);
//						HashMap<String, Object> map=childArray.get(i).get(j);
//						if(arg0.getStatus()==null)
//						{
//							map.put("friends", temp.getName()+"(离线)");
//						}
//						else{
//							map.put("friends", temp.getName()+"("+arg0.getStatus()+")");
//						}
//						childArray.get(i).set(j, map);
//						break;
//					}
//					}
//				}
//				android.os.Message msg=new android.os.Message();
//				msg.what=1;
//				msg.obj="����";
//				mHandler.sendMessage(msg);
//			}
//			
//			@Override
//			public void entriesUpdated(Collection<String> arg0) {
//				// TODO Auto-generated method stub
//				int a=0;
//			}
//			
//			@Override
//			public void entriesDeleted(Collection<String> arg0) {
//				// TODO Auto-generated method stub
//				int a=0;
//			}
//			@Override
//			public void entriesAdded(Collection<String> arg0) {
//				// TODO Auto-generated method stub
//				groupArray.clear();
//				childArray.clear();
//				LoadDate(roster);
//				android.os.Message msg=new android.os.Message();
//				msg.what=1;
//				mHandler.sendMessage(msg);
//			}
//		});
//		chatmanager=connection.getChatManager();
//		if(jian<1){
//		chatmanager.addChatListener(new MyChatMessageListrners());
//		jian++;
//		}
//		
//		if(childArray==null){
//		groupArray =new ArrayList<String>();
//	    childArray = new ArrayList<List<HashMap<String, Object>>>();
//		LoadDate(roster);
//		}
//		
//		
//		exadapter=new ExpandableListViewaAdapter(ActivityManager.mactivity);
//		expandableListView_one.setAdapter(exadapter);
//		expandableListView_one.setOnChildClickListener(new OnChildClickListener() {
//			
//			@Override
//			public boolean onChildClick(ExpandableListView arg0, View arg1, int arg2,
//					int arg3, long arg4) {
//				// TODO Auto-generated method stub
//				String f=childArray.get(arg2).get(arg3).get("id").toString();
//				if(!IMActivity.user.equals(f))
//				{
//					IMActivity.user=f;
//					IMActivity.chat=MyChatMessageListrners.chats.get(f);
//					if(IMActivity.chat==null)
//					{
//						IMActivity.chat=chatmanager.createChat(f,null);
//					}
//					IMActivity.theardid=IMActivity.chat.getThreadID();
//				}
//				for (int i=0;i<friendsFragment.childArray.size();i++) {
//					for(int j=0;j<friendsFragment.childArray.get(i).size();j++){
//					if(friendsFragment.childArray.get(i).get(j).get("id").equals(f))
//					{
//						friendsFragment.childArray.get(i).get(j).put("message", "已");
//						break;
//					}
//				}
//				}
//				Constants.IMstate="IM";
//				MyChatMessageListrners.mHandler=IMActivity.mHandler;
//				Intent intent = new Intent();
//				intent.setClass(ActivityManager.mactivity, IMActivity.class);
//				intent.putExtra("user",f);
//				startActivityForResult(intent, 0);
//				return false;
//			}
//		});
//		expandableListView_one.setOnItemLongClickListener(deleteLongClickListener);
//		
//		Thread rec= new Thread()
//		{
//			public void run() {
//				try {
//				OfflineMessageManager offlineManager = new OfflineMessageManager(connection);
//				Iterator<Message> itMes = null;
//				try {
//					itMes = offlineManager.getMessages();
//				} catch (XMPPException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}  
//				while (itMes.hasNext()) {
//					
//				 Message message = itMes.next();
//				 String[] str=  message.getFrom().split("/");
//				for (int i=0;i<childArray.size();i++) {
//					for(int j=0;j<childArray.get(i).size();j++){
//						if(childArray.get(i).get(j).get("id").equals(str[0])){
//							break;
//						}
//					}
//				}
//				android.os.Message msg=new android.os.Message();
//				msg.what=1;
//				mHandler.sendMessage(msg);
//				 }
//				try {
//					offlineManager.deleteMessages();
//				} catch (XMPPException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}  //�ϱ��������ѻ�ȡ����ɾ����������ݣ���Ȼ�´ε�¼�����»�ȡ
//				Presence presence = new Presence(Presence.Type.available);//��ʱ���ϱ��û�״̬
//				connection.sendPacket(presence);
//					sleep(1);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		};
//		//rec.start();
//		
//		
//		//Presence presence = new Presence(Presence.Type.available);//��ʱ���ϱ��û�״̬
//		//connection.sendPacket(presence);
//		mHandler = new Handler() {  
//            public void handleMessage(android.os.Message msg) {  
//                super.handleMessage(msg);  
//                switch (msg.what) {  
//                case 1:
//                	exadapter.notifyDataSetChanged();
//                    break;
//                case 2:
//                	
//                }  
//            }  
//        };
//        MyChatMessageListrners.mHandler=mHandler;
//		return view;  
//	}
//	public void deleteDiary() { 
//			try {
//				RosterEntry nowentry= roster.getEntry(childArray.get(now_action_group).get(now_action_child).get("id").toString());
//				roster.removeEntry(nowentry);
//				childArray.get(now_action_group).remove(now_action_child);
//				android.os.Message msg=new android.os.Message();
//				msg.what=1;
//				mHandler.sendMessage(msg);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//	}
//	OnItemLongClickListener deleteLongClickListener = new OnItemLongClickListener() {
//
//		@SuppressWarnings("deprecation")
//		@Override
//		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
//				int arg2, long arg3) {
//			// TODO Auto-generated method stub
//			ViewHolder nowviewholder=(ViewHolder)arg1.getTag();
//			now_action_group= nowviewholder.fatherid;
//			now_action_child=nowviewholder.childid;
//			ActivityManager.mactivity.showDialog(DIALOG_DELETE);
//			return false;
//		}
//	};
//	private boolean LoadDate(Roster roster) {
//		
//		RosterGroup temp=null;
//		List<HashMap<String, Object>> Child1=null;
//		Collection<RosterGroup> rosterGroup = roster.getGroups();
//		Iterator<RosterGroup> i = rosterGroup.iterator();
//		while (i.hasNext()) 
//		{
//			temp=i.next();
//			groupArray.add(temp.getName());
//			Collection<RosterEntry> it = temp.getEntries();
//		    Child1 = new ArrayList<HashMap<String, Object>>();
//			for(RosterEntry rosterEnter:it){  
//				HashMap<String, Object> map = new HashMap<String, Object>();
//			    String c= rosterEnter.getUser();
//			    Presence presence= roster.getPresence(c);
//			    map.put("id", rosterEnter.getUser());
//			    map.put("message", "已");
//			    if(presence.getStatus()==null)
//			    {
//			    	map.put("friends", rosterEnter.getName()+"(离线)");
//			    }
//			    else{
//			        map.put("friends", rosterEnter.getName()+"("+presence.getStatus()+")");
//			    }
//			    Child1.add(map);
//			}
//			childArray.add(Child1);
//		}
//		return true;
//	}
//	class ExpandableListViewaAdapter extends BaseExpandableListAdapter {
//        Activity activity;
//        public  ExpandableListViewaAdapter(Activity a)  
//            {  
//                activity  = a;
//                mInflater = LayoutInflater.from(activity);
//            }  
//       /*-----------------Child */
//        @Override
//        public Object getChild(int groupPosition, int childPosition) {
//            // TODO Auto-generated method stub
//            return childArray.get(groupPosition).get(childPosition);
//        }
// 
//        @Override
//        public long getChildId(int groupPosition, int childPosition) {
//            // TODO Auto-generated method stub
//            return childPosition;
//        }
// 
//        @Override
//        public View getChildView(int groupPosition, int childPosition,
//                boolean isLastChild, View convertView, ViewGroup parent) {
//             
//            ViewHolder hoder;
//			if(convertView==null)
//			{
//				convertView=mInflater.inflate(R.layout.main_cycle_class_child, null);
//				hoder=new ViewHolder();
//				hoder.textview   = (TextView )convertView.findViewById(R.id.textChild);
//				hoder.imageview  = (ImageView)convertView.findViewById(R.id.main_cycle_class_child_image);
//				hoder.fatherid   = groupPosition;
//				hoder.childid    = childPosition;
//				convertView.setTag(hoder);
//			}
//			else
//			{
//				hoder=(ViewHolder)convertView.getTag();
//			}
//			hoder.textview.setText(childArray.get(groupPosition).get(childPosition).get("friends").toString());
//			if(childArray.get(groupPosition).get(childPosition).get("message").toString().equals("��"))
//			{
//				//hoder.imageview.setVisibility(View.VISIBLE);
//				BadgeView badge = new BadgeView(ActivityManager.mactivity, hoder.textview);
//				//badge.setText("1");
//				badge.show();
//			}
//			else
//			{
//				//hoder.imageview.setVisibility(View.INVISIBLE);
//			}
//			return convertView;
//        }
// 
//        @Override
//        public int getChildrenCount(int groupPosition) {
//            // TODO Auto-generated method stub
//            return childArray.get(groupPosition).size();
//        }
//       /* ----------------------------Group */
//        @Override
//        public Object getGroup(int groupPosition) {
//            // TODO Auto-generated method stub
//            return getGroup(groupPosition);
//        }
// 
//        @Override
//        public int getGroupCount() {
//            // TODO Auto-generated method stub
//            return groupArray.size();
//        }
// 
//        @Override
//        public long getGroupId(int groupPosition) {
//            // TODO Auto-generated method stub
//            return groupPosition;
//        }
// 
//        @Override
//        public View getGroupView(int groupPosition, boolean isExpanded,
//                View convertView, ViewGroup parent) {
//           if (convertView != null) {   
//               TextView textview=(TextView)convertView.findViewById(R.id.textGroup);
//               textview.setText(groupArray.get(groupPosition));
//           } else {   
//        	   convertView=mInflater.inflate(R.layout.main_cycle_class_ngroup, null);
//        	   TextView textview=(TextView)convertView.findViewById(R.id.textGroup);
//               textview.setText(groupArray.get(groupPosition));
//           }   
//           return convertView;
//        }
// 
//        @Override
//        public boolean hasStableIds() {
//            // TODO Auto-generated method stub
//            return false;
//        }
// 
//        @Override
//        public boolean isChildSelectable(int groupPosition, int childPosition) 
//        {
//            // TODO Auto-generated method stub
//            return true;
//        }
//    }
//	private String getDate() {
//        Calendar c   = Calendar.getInstance();
//        String year  = String.valueOf(c.get(Calendar.YEAR));
//        String month = String.valueOf(c.get(Calendar.MONTH));
//        String day   = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
//        String hour  = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
//        String mins  = String.valueOf(c.get(Calendar.MINUTE));
//        StringBuffer sbBuffer = new StringBuffer();
//        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
//        return sbBuffer.toString();
//    }
//	private final class ViewHolder{
//		public TextView  textview;
//		public String    user;
//		public ImageView imageview;
//		public int       fatherid;
//		public int       childid;
//	}
//}
