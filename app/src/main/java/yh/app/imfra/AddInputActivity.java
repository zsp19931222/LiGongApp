//package yh.app.imfra;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import org.androidpn.client.Constants;
//import org.jivesoftware.smack.Roster;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smackx.Form;
//import org.jivesoftware.smackx.ReportedData;
//import org.jivesoftware.smackx.ReportedData.Row;
//import org.jivesoftware.smackx.search.UserSearchManager;
//
//import yh.app.activitytool.ActivityPortrait;
//import yh.app.coursetable.TableDemoActivity;
//import yh.app.listeners.MyChatMessageListrners;
//import  com.yhkj.cqswzyxy.MainActivity;
//
//import  com.yhkj.cqswzyxy.R;
//import  com.yhkj.cqswzyxy.R;
//import  com.yhkj.cqswzyxy.R;
//import  com.yhkj.cqswzyxy.R;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.view.KeyEvent;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
///**
// * 
// * 包	名:yh.app.imfra
// * 类	名:AddInputActivity.java
// * 功	能:显示搜索到的好友
// *
// * @author 	云华科技
// * @version	1.0
// * @date	2015-7-29
// */
//@SuppressWarnings("unused")
//public class AddInputActivity extends ActivityPortrait {
//
//	EditText edit=null;
//	Button button=null;
//	private Roster roster=null;
//	private Handler mHandler=null;
//	private MyAdapter myadapter=null;
//	private ListView listview=null;
//	static ArrayList<String> users;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.add_friend3);
//		edit=(EditText)findViewById(R.id.search_friends);
//		button=(Button)findViewById(R.id.search_friends_button);
//		listview=(ListView)findViewById(R.id.listDiary);
//		myadapter=new MyAdapter(this);
//		listview.setAdapter(myadapter);
//		button.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				if(edit.getText().toString()==""){
//					new AlertDialog.Builder(AddInputActivity.this)    
//		            .setTitle("��ʾ")
//		            .setMessage("���ѿ���Ϊ�գ�")
//		            .setPositiveButton("ȷ��", null)
//		            .show();
//					return;
//				}
//				else if(!org.androidpn.push.Constants.isNetworkAvailable(AddInputActivity.this))
//				{
//					new AlertDialog.Builder(AddInputActivity.this)    
//		            .setTitle("��ʾ")
//		            .setMessage("��ݴ���ʧ�ܣ��������绷����")
//		            .setPositiveButton("ȷ��", null)
//		            .show();
//					return;
//				}
//				else if(Constants.xmppManager.isConnected())
//				{
//					myadapter.setlist(searchUsers(edit.getText().toString()));
//					android.os.Message msg=new android.os.Message();
//					msg.what=1;
//					mHandler.sendMessage(msg);
//					return;
//				}
//				else
//				{
//					new AlertDialog.Builder(AddInputActivity.this)    
//		            .setTitle("��ʾ")
//		            .setMessage("����ʧ�ܣ�Ҳ�����������⣡")
//		            .setPositiveButton("ȷ��", null)
//		            .show();
//					return;
//				}
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
//		private ArrayList<String> listIt;
//        public MyAdapter(Context context) {
//           listIt=new ArrayList<String>();
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
//		public boolean setlist(ArrayList<String> listIt) {
//			this.listIt=listIt;
//			return true;
//		}
//		@Override
//		public View getView(int arg0, View arg1, ViewGroup arg2) {
//			// TODO Auto-generated method stub
//			ViewHolder hoder=null;
//			if(arg1==null)
//			{
//				arg1=mInflater.inflate(R.layout.mian_cycle_addfri_child, null);
//				hoder=new ViewHolder();
//				hoder.index=arg0;
//				hoder.textChild=(TextView)arg1.findViewById(R.id.textChild);
//				hoder.imageview1=(ImageView)arg1.findViewById(R.id.imageView1);
//				arg1.setTag(hoder);
//			}
//			else
//			{
//				hoder=(ViewHolder)arg1.getTag();
//			}
//			hoder.imageview1.setTag(arg0);
//			String[]s=listIt.get(arg0).split(",");
//			hoder.imageview1.setOnClickListener(new View.OnClickListener() {
//				
//				@Override
//				public void onClick(View image) {
//					// TODO Auto-generated method stub
//					roster = Constants.xmppManager.getConnection().getRoster();
//					roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
//					String[] s=users.get((Integer)image.getTop()).split(",");
//					try {
//						roster.createEntry(s[0]+"@"+Constants.xmppManager.getConnection().getServiceName(),s[1],new String[]{"����"});
//					} catch (XMPPException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					Toast.makeText(getApplicationContext(), "�ѷ�������",
//						     Toast.LENGTH_SHORT).show();
//				}
//			});
//			hoder.textChild.setText(s[1]+"("+s[0]+")");
//			return arg1;
//		}
//	}
//	private final class ViewHolder
//	{
//		public TextView textChild;
//		public ImageView imageview1;
//		public String shouye_xinwen_imgfurl;
//		public int index;
//	}
//	 public static ArrayList<String> searchUsers(String user) {  
//		        users = new ArrayList<String>();  
//		        UserSearchManager usm = new UserSearchManager(Constants.xmppManager.getConnection());  
//		        Form searchForm = null;  
//		        try {
//		        	String searchfrom="search."  
//			                   +Constants.xmppManager.getConnection().getServiceName();
//		            searchForm = usm.getSearchForm(searchfrom);  
//		           Form answerForm = searchForm.createAnswerForm();  
//		            answerForm.setAnswer("Username", true);  
//		           answerForm.setAnswer("search", user);  
//		            ReportedData data = usm.getSearchResults(answerForm, searchfrom);  
//		            // column:jid,Username,Name,Email  
//		            Iterator<Row> it = data.getRows();  
//		            Row row = null;
//		            while (it.hasNext()) {  
//		                row = it.next();  
//		                // Log.d("UserName",  
//		               // �����ڣ����з���,UserNameһ���ǿգ����������������裬һ���ǿ�  
//		                users.add(row.getValues("Username").next().toString()+","+row.getValues("Name").next().toString());  
//		            }  
//		        } catch (XMPPException e) {  
//		            // TODO Auto-generated catch block  
//		           e.printStackTrace();  
//		        }  
//		       return users;  
//		    }  
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.add_input, menu);
//		return true;
//	}
//	@Override  
//    public boolean onKeyDown(int keyCode, KeyEvent event) {  
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
//        	Intent intent = new Intent();
//        	MyChatMessageListrners.mHandler=friendsFragment.mHandler;
//			intent.setClass(AddInputActivity.this,MainActivity.class);
//			startActivityForResult(intent, 0);
//            return true;  
//        } else  
//            return super.onKeyDown(keyCode, event);  
//    }
//}
