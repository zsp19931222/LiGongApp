package com.example.jpushdemo.helper;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;
import yh.app.function.FriendCycle;
import yh.app.quanzi.lxr.AddFriendHelper;
import yh.app.quanzitool.pinyin;
import yh.app.tool.CurrentActivity;
import yh.app.tool.SqliteHelper;
import yh.app.utils.FunctionList;
import yh.app.utils.NotificationManager;
import 云华.智慧校园.工具.ClassNameHelper;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class JPushReceiverTools
{
	private String content, extra, id, code, fssj, fqr, title, url, func_id, ticket, name, sfty;

	public JPushReceiverTools(Bundle bundle)
	{
		initDate(bundle);
	}

	private void initDate(Bundle bundle)
	{
		content = bundle.getString("cn.jpush.android.ALERT");
		extra = bundle.getString("cn.jpush.android.EXTRA");
		title = bundle.getString("cn.jpush.android.NOTIFICATION_CONTENT_TITLE");
		JSONObject jso;
		try
		{
			jso = new JSONObject(extra);
			id = getDate(jso, "messageID");
			code = getDate(jso, "code");
			if (code.equals("102"))
			{
				url = getDate(jso, "url");
			}
			fssj = getDate(jso, "fssj");
			fqr = getDate(jso, "fqr");
			func_id = getDate(jso, "func_id");
			ticket = getDate(jso, "ticket");
			name = getDate(jso, "fqrname");
			sfty = getDate(jso, "sfty");
		} catch (JSONException e)
		{
		}
	}

	private String getDate(JSONObject jso, String zd)
	{
		try
		{
			String result = jso.getString(zd);
			if (result == null)
				return " ";
			else
				return result;
		} catch (Exception e)
		{
			return " ";
		}
	}

	private static Object obj = new Object();

	public void doit()
	{
		synchronized (obj)
		{
			if (code.equals("201"))
			{
				if (!new SqliteHelper().rawQuery("select count(*) num from (select FRIEND_ID from mrfz where FRIEND_ID=? and userid=? union select FRIEND_ID from friend where FRIEND_ID=? and userid=? )", new String[]
				{
						fqr, Constants.number, fqr, Constants.number
				}).get(0).get("num").equals("0"))
					chat();
			} else if (code.equals("202"))
			{
				addFriend();
				receiveAddFriend();
			} else if (code.equals("203"))
			{
				NoticeAddFriend();
				receiveAddFriend();
			} else if (code.equals("101") || code.equals("102"))
			{
				saveNotification();
				if (pushNotification())
					receiveNotification();
				push();
			} else if (code.equals("301"))
			{
				try
				{
					new DoPush().force(ticket, CurrentActivity.NOW_ACTIVITY, title, content);
				} catch (Exception e)
				{
				}

			}
		}
	}

	private void receiveAddFriend()
	{
		new ThreadPoolManage().addPostTask(_链接地址导航.PUSH.添加好友反馈接口.getUrl(), MapTools.buildMap(new String[][]
		{
				{
						"id", id
				}
		}), new Handler());
	}

	private void receiveNotification()
	{
		NotificationManager.receive(id);
	}

	private boolean pushNotification()
	{
//		try
//		{
//			String action = null;
//			if (code.equals("101"))
//			{
//				action = "yh.app.mymessage.tzggxq";
//			} else if (code.equals("102"))
//			{
//				action = "yh.app.web.Web";
//			}
//			if (IsNull.isNotNull(action))
//				Notification1.buildNotification_push(id, title, content, action, ExampleApplication.getAppPackage(), true, true, true, Notification1.NTIFICATION_TYPE_PUSH);
//			else
//				return false;
//		} catch (Exception e)
//		{
//			return false;
//		}
		Message msg = new Message();
		Bundle b = new Bundle();
		b.putString("func_id", func_id);
		msg.setData(b);
		Constants.pushHandler.sendMessage(msg);
		return true;
	}

	private void saveNotification()
	{
		String sql = String.format("insert into tzgg(tzggid,message ,fssj ,isread ,userid ,type ,url,func_id ,bjzd ,fqbm,title ) values('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s')", new Object[]
		{
				id, content, fssj, "false", Constants.number, code, url, func_id, " ", " ", title
		});
		new SqliteHelper().execSQL(sql);
	}

	private void push()
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals("yh.app.mymessage.Message") && func_id.equals(ActivityPortrait.function_id))
		{
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("func_id", func_id);
			b.putString("title", title);
			b.putString("bjzd", "");
			b.putString("tzggid", id);
			b.putString("url", url);
			b.putString("fqbm", " ");
			b.putString("fssj", fssj);
			b.putString("message", content);
			b.putString("type", code);
			b.putString("isread", "false");
			msg.setData(b);
			yh.app.mymessage.Message.getPush.sendMessage(msg);
		}
	}

	private void chat()
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals("yh.app.function.liaotianjiemian"))
		{
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("message", content);
			b.putString("fssj", fssj);
			msg.setData(b);
			Constants.ChatHandler.sendMessage(msg);
			saveMessage(fqr, content, fssj, "true");
		} else
		{
			String friendName = "";
			if (FunctionList.sq != null)
			{
				FunctionList.sq.setVisibility(View.VISIBLE);
			}
			try
			{
				friendName = new SqliteHelper().rawQuery("select name from friend where friend_id=? and userid=? union select name from mrfz where friend_id=? and userid=?", new String[]
				{
						fqr, Constants.number, fqr, Constants.number
				}).get(0).get("name");
			} catch (Exception e1)
			{
			}

			if (IsNull.isNotNull(friendName))
			{
//				saveMessage(fqr, content, fssj, "false");
//				Notification1.buildNotification(id, friendName, friendName + ":" + content, true, true, true, Notification1.NTIFICATION_TYPE_CHAT, new IntentHelper()
//				{
//					@Override
//					public Intent setIntent()
//					{
//						Intent intent = new Intent();
//						intent.putExtra("friend_id", fqr);
//						intent.setAction("yh.app.function.liaotianjiemian");
//						intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
//						return intent;
//					}
//				});
				if (null != Constants.sq_main_handler)
				{
					Message msg = new Message();
					msg.what = FriendCycle.SENT_CHAT;
					msg.obj = fqr;
					Constants.sq_main_handler.sendMessage(msg);
				}
			}
		}
		Received();
	}

	private void addFriend()
	{
//		if (IsNull.isNotNull(name))
//			Notification1.buildNotification(func_id, "好友请求", name + "请求添加您为好友", true, true, true, Notification1.NTIFICATION_TYPE_CHAT, new IntentHelper()
//			{
//				@Override
//				public Intent setIntent()
//				{
//					Intent intent = new Intent();
//					intent.setAction("yh.app.quanzi.sftjhy");
//					intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
//					intent.putExtra("message", content);
//					intent.putExtra("friend_id", fqr);
//					intent.putExtra("name", name);
//					return intent;
//				}
//			});
	}

	@SuppressWarnings("unused")
	private String notification_content = "";

	private void NoticeAddFriend()
	{
		if (IsNull.isNotNull(name))
		{
			if ("1".equals(sfty))
			{
				notification_content = name + "与您成为好友,快开始聊天吧。";
				new AddFriendHelper().addFriend(fqr, name, pinyin.getSpells(name).substring(0, 1), Constants.number);
			} else if ("4".equals(sfty))
			{
				notification_content = name + "拒绝了您的请求。";
			}
//			Notification1.buildNotification(func_id, "验证信息", notification_content, true, true, true, Notification1.NTIFICATION_TYPE_CHAT, new IntentHelper()
//			{
//				@Override
//				public Intent setIntent()
//				{
//					Intent intent = new Intent();
//					intent.setAction("yh.app.quanzi.tjhyxxfk");
//					intent.setPackage(CurrentActivity.NOW_ACTIVITY.getPackageName());
//					intent.putExtra("friend_id", fqr);
//					intent.putExtra("message", notification_content);
//					intent.putExtra("name", name);
//					return intent;
//				}
//			});
		}

	}

	private void Received()
	{
		new ThreadPoolManage().addPostTask(_链接地址导航.PUSH.聊天反馈接口.getUrl(), MapTools.buildMap(new String[][]
		{
				{
						"id", id
				},
				{
						"userid", Constants.number
				}
		}), new Handler());
	}

	private void saveMessage(String fqr, String message, String fssj, String isread)
	{
		new SqliteHelper().execSQL("insert into lt(fqr, jsr, message, fssj, code, userid, friend_id,isread) values(?, ?, ?, ?, ?, ?, ?, ?)", new Object[]
		{
				fqr, Constants.number, message, fssj, code, Constants.number, fqr, isread
		});
	}

	class MyAt extends AsyncTask<String, String, String>
	{
		@Override
		protected String doInBackground(String... params)
		{
			return null;
		}

	}
}
