//package yh.app.imfra;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import org.androidpn.push.Constants;
//import org.jivesoftware.smack.Roster;
//import org.jivesoftware.smack.XMPPException;
//import org.jivesoftware.smack.packet.Presence;
//
//import yh.app.activitytool.ActivityPortrait;
//import  com.yhkj.cqswzyxy.ActivityManager;
//import  com.yhkj.cqswzyxy.MainActivity;
//
//import  com.yhkj.cqswzyxy.R;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.app.Activity;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//@SuppressWarnings("unused")
//public class DealfriendActivity extends ActivityPortrait {
//	private Handler mHandler = new Handler() {
//		public void handleMessage(android.os.Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case 1:
//				myadapter.notifyDataSetChanged();
//				break;
//			}
//		}
//	};
//	private MyAdapter myadapter = null;
//	private ListView listview 	= null;
//	private ArrayList<HashMap<String, Object>> listItem = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_dealfriend);
//		listItem = new ArrayList<HashMap<String, Object>>();
//		listview = (ListView) findViewById(R.id.listDiary);
//		Dataloading();
//		myadapter = new MyAdapter(this, listItem);
//		listview.setAdapter(myadapter);
//	}
//
//	private boolean Dataloading() {
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		Cursor result = db.rawQuery(
//				"select * from friendapplication where tofromuser='"
//						+ Constants.number + "'", null);
//		result.moveToFirst();
//		while (!result.isAfterLast()) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			String name = result.getString(3);
//			map.put("id", result.getString(0));
//			map.put("name", name);
//			map.put("form", result.getString(1));
//			listItem.add(map);
//			result.moveToNext();
//		}
//		db.close();
//		return true;
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.dealfriend, menu);
//		return true;
//	}
//
//	private class MyAdapter extends BaseAdapter {
//		private LayoutInflater mInflater;
//		private ArrayList<HashMap<String, Object>> listIt;
//
//		public MyAdapter(Context context,
//				ArrayList<HashMap<String, Object>> list) {
//			this.listIt = list;
//			this.mInflater = LayoutInflater.from(context);
//		}
//
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
//			ViewHolder hoder = null;
//			if (arg1 == null) {
//				arg1 = mInflater.inflate(R.layout.dealfriend_sub, null);
//				hoder = new ViewHolder();
//				hoder.index = arg0;
//				hoder.add_fri_title = (TextView) arg1
//						.findViewById(R.id.textChild);
//				hoder.accpt = (Button) arg1.findViewById(R.id.accpt);
//				hoder.refuse = (Button) arg1.findViewById(R.id.refuse);
//				hoder.accpt.setTag(arg0);
//				hoder.accpt.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View button) {
//						// TODO Auto-generated method stub
//						Button temp = (Button) button;
//						sendpresence(1, listIt.get((Integer) temp.getTag())
//								.get("form").toString(),
//								listIt.get((Integer) temp.getTag()).get("id")
//										.toString(),
//								listIt.get((Integer) temp.getTag()).get("name")
//										.toString(), (Integer) temp.getTag());
//					}
//				});
//				hoder.refuse.setTag(arg0);
//				hoder.refuse.setOnClickListener(new View.OnClickListener() {
//
//					@Override
//					public void onClick(View button) {
//						// TODO Auto-generated method stub
//						Button temp = (Button) button;
//						sendpresence(2, listIt.get((Integer) temp.getTag())
//								.get("form").toString(),
//								listIt.get((Integer) temp.getTag()).get("id")
//										.toString(),
//								listIt.get((Integer) temp.getTag()).get("name")
//										.toString(), (Integer) temp.getTag());
//					}
//				});
//				arg1.setTag(hoder);
//			} else {
//				hoder = (ViewHolder) arg1.getTag();
//			}
//			hoder.add_fri_title.setText(listIt.get(arg0).get("name").toString()
//					+ "�������Ϊ���ѣ�");
//			return arg1;
//		}
//	}
//
//	public void sendpresence(int i, String from, String id, String name,
//			int index) {
//		Presence presence = null;
//		if (i == 1) {
//			presence = new Presence(Presence.Type.subscribed);// ͬ����subscribed
//																// �ܾ���unsubscribe
//		} else {
//			presence = new Presence(Presence.Type.unsubscribe);// ͬ����subscribed
//																// �ܾ���unsubscribe
//		}
//		// Roster roster =
//		// org.androidpn.client.Constants.xmppManager.getConnection().getRoster();
//		// roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
//		// try {
//		// roster.createEntry(from+"@"+org.androidpn.client.Constants.xmppManager.getConnection().getServiceName(),name,new
//		// String[]{"����"});
//		// } catch (XMPPException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		presence.setTo(from
//				+ "@"
//				+ org.androidpn.client.Constants.xmppManager.getConnection()
//						.getServiceName());// ���շ�jid
//		presence.setFrom(Constants.number
//				+ "@"
//				+ org.androidpn.client.Constants.xmppManager.getConnection()
//						.getServiceName());// ���ͷ�jid
//		org.androidpn.client.Constants.xmppManager.getConnection().sendPacket(
//				presence);// connection�����Լ���XMPPConnection����
//		SQLiteDatabase db = new SqliteHelper().getWrite();
//		db.execSQL("delete from friendapplication where FA='" + id + "'");
//		listItem.remove(index);
//		android.os.Message msg = new android.os.Message();
//		msg.what = 1;
//		mHandler.sendMessage(msg);
//		((MainActivity) ActivityManager.mactivity).getCrcleFragment()
//				.setaddBadge();
//	}
//
//	private final class ViewHolder {
//		public TextView add_fri_title;
//		public Button accpt, refuse;
//		public int index;
//	}
//
//}
