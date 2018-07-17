package com.example.jpushdemo.body;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.android.volley.VolleyError;
import com.example.jpushdemo.ExampleApplication;

import org.androidpn.push.Constants;
import org.json.JSONObject;

import java.util.Map;
import java.util.UUID;

import yh.app.function.FriendCycle;
import yh.app.function.liaotianjiemian;
import yh.app.mymessage.tzggxq;
import yh.app.quanzi.NewFriend;
import yh.app.quanzi.tjhyxxfk;
import yh.app.quanzitool._圈子聊天工具;
import yh.app.quanzitool.pinyin;
import yh.app.tool.CurrentActivity;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import com.example.app3.HomePageActivity;

import yh.app.uiengine.home;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.web.WebActivity;
import 云华.智慧校园.工具.ClassNameHelper;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具._链接地址导航;

public class Receiver
{
	public static final String CHAT_CLASS = liaotianjiemian.class.getName();
	public static final String CHAT_LIST_CLASS = FriendCycle.class.getName();
	public static final String HOME_CLASS = home.class.getName();
	
	public interface IGetMessage
	{
		void callBack(JSONObject data);
	}
private String url;
	public void getMessageByID(final Context context, final JSONObject jso, final IGetMessage getMessage)
	{
		try
		{
			Map<String, String> parm = MapTools.buildMap(new String[][]
			{
					{
							"id", jso.getString("id")
					},
					{
							"ticket", jso.getString("ticket")
					},
					{
							"code", jso.getString("code")
					},
					{
							"userid", Constants.number
					}
			});
			VolleyRequest.RequestPost(_链接地址导航.PushServer.getMessageByID.getUrl(), parm, new VolleyInterface()
			{
				@Override
				public void onMySuccess(String result)
				{
					try
					{
						JSONObject jso = new JSONObject(result);
						if (Constants.NETWORK_REQUEST_SUCCESS.equals(jso.getString("code")))
						{
							doSave(jso.getJSONObject("content").getString("code"), getBodyByBundle(jso.getJSONObject("content")));
							doDeal(jso.getJSONObject("content"));
							if (getMessage != null)
							{
								getMessage.callBack(jso);
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onMyError(VolleyError error)
				{
				}
			});
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	public boolean doSave(String code, Body body)
	{
		if (body != null)
		{
			if (code.equals(BodyChat.CHAT_TEXT))
			{
				return saveChat((BodyChat) body);
			} else if (code.equals(BodyChat.CHAT_ADD))
			{
				return saveAdd((BodyAdd) body);
			} else if (code.equals(BodyChat.CHAT_READD))
			{
				return saveReadd((BodyReadd) body);
			} else if (code.equals(BodyPush.PUSH_TEXT) || code.equals(BodyPush.PUSH_URL))
			{
				return savePush((BodyPush) body);
			} else if (code.equals(BodyPush.PUSH_TEACH))
			{
				saveTeachPush((BodyPush) body);
			}
		}
		return false;
	}

	private boolean saveTeachPush(BodyPush body)
	{

		if (new SqliteHelper().rawQuery("select count(*) as num from client_notice_newest where userid=? and function_id = ? and n_send_time > ?", Constants.number, body.getFunc_id(), body.getFssj()).get(0).get("num").equals("0"))
		{
			try
			{
				saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
				{
						body.getId(), Constants.number, BodyPush.READ_NO, new SqliteHelper().rawQuery("select classname from classlist where userid=? and id=?", Constants.number, body.getFunc_id()).get(0).get("classname"), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket()
				});
			} catch (Exception e)
			{
				saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
				{
						body.getId(), Constants.number, BodyPush.READ_NO, body.getTitle(), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket()
				});
			}
		}
		
		return saveToDB("insert into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
		{
				body.getId(), Constants.number, BodyPush.READ_NO, body.getTitle(), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket(),body.getnPicPath()
		});

	}

	public boolean saveChat(BodyChat body)
	{
		if (isFriend(body.getFqr()))
		{
//			List<Map<String, String>> result = new SqliteHelper().rawQuery("select * from friend where FRIEND_ID=? and userid = ?", body.getFqr(), Constants.number);
//			String hymc = null;
//			if (result != null && result.size() > 0)
//			{
//				hymc = result.get(0).get("name");
//			} else
//			{
//				return false;
//			}
//			saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
//			{
//				body.getFqr(), Constants.number, BodyAdd.DEAL_NOREAD, hymc, body.getMessage(), body.getFqr(), "", body.getFssj(), body.getCode(), body.getTicket()
//			});
//			return saveToDB("insert into lt(fqr, message, fssj, code, userid, friend_id, isread, jsr) values(?, ?, ?, ?, ?, ?, ?, ?)", new Object[]
//			{
//				body.getFqr(), body.getMessage(), body.getFssj(), body.getCode(), body.getUserid(), body.getFqr(), body.getIsRead(), body.getUserid()
//			});
			
			new _圈子聊天工具().saveOtherSendMessage(body.getId(), body.getFqr(), body.getMessage(), body.getFssj(), body.getTicket(), BodyAdd.DEAL_NOREAD);
		}
		return false;
	}

	public boolean saveAdd(BodyAdd body)
	{
		if (new SqliteHelper().rawQuery("select count(*) as num from client_notice_newest where userid=? and function_id = ? and n_send_time > ?", Constants.number, body.getFqr(), body.getFssj()).get(0).get("num").equals("0"))
		{
			saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
			{
					UUID.randomUUID().toString(), Constants.number, BodyAdd.DEAL_NOREAD, "好友消息", body.getFjnr(), BodyAdd.DEAL_FUNCTION_ID, "", body.getFssj(), body.getCode(), body.getTicket()
			});
		}
		return saveToDB("replace into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread) values(?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
		{
				body.getId(), Constants.number, body.getCode(), body.getFqr(), body.getFqrname(), Constants.number, "", body.getFjnr(), body.getFssj(), "", BodyAdd.DEAL_NO, BodyAdd.DEAL_NOREAD
		});
	}

	public boolean saveReadd(BodyReadd body)
	{
		if (BodyAdd.DEAL_SFTY_YES.equals(body.getSfty()))
		{
			saveToDB("replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)", new Object[]
			{
					body.getFqr(), body.getFqrname(), IsNull.isNotNull(body.getFqrname()) ? pinyin.getAllLetter(body.getFqrname()).substring(0, 1) : "#", Constants.number
			});
		}
		return saveToDB("update addFriend set deal=? where id=?", new Object[]
		{
				body.getDeal(), body.getId()
		});
	}

	public boolean savePush(BodyPush body)
	{
		if (new SqliteHelper().rawQuery("select count(*) as num from client_notice_newest where userid=? and function_id = ? and n_send_time > ?", Constants.number, body.getFunc_id(), body.getFssj()).get(0).get("num").equals("0"))
		{
			try
			{
				saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
				{
						body.getId(), Constants.number, BodyPush.READ_NO, new SqliteHelper().rawQuery("select function_name from button where userid=? and functionid=?", Constants.number, body.getFunc_id()).get(0).get("function_name"), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket()
				});
			} catch (Exception e)
			{
				saveToDB("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
				{
						body.getId(), Constants.number, BodyPush.READ_NO, body.getTitle(), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket()
				});
			}
		}
		return saveToDB("insert into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket,n_picpath) values(?,?,?,?,?,?,?,?,?,?,?)", new Object[]
		{
				body.getId(), Constants.number, BodyPush.READ_NO, body.getTitle(), body.getMessage(), body.getFunc_id(), body.getUrl(), body.getFssj(), body.getCode(), body.getTicket(),body.getnPicPath()
		});
	}

	public void doDeal(JSONObject bundle)
	{
		String code = JsonTools.getString(bundle, "code");
		if (code != null)
		{
			if (code.equals(BodyChat.CHAT_TEXT))
			{
				dealChat(bundle);
			} else if (code.equals(BodyChat.CHAT_ADD))
			{
				dealAdd(bundle);
			} else if (code.equals(BodyChat.CHAT_READD))
			{
				dealReadd(bundle);
			} else if (code.equals(BodyPush.PUSH_TEXT) || code.equals(BodyPush.PUSH_URL) || code.equals(BodyPush.PUSH_TEACH))
			{
				dealPush(bundle);
			}
		}
	}

	public void dealChat(JSONObject bundle)
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals(CHAT_CLASS))
		{
			Message msg = new Message();
			Bundle b = new Bundle();
			b.putString("message", JsonTools.getString(bundle, "message"));
			b.putString("fssj", JsonTools.getString(bundle, "fssj"));
			msg.setData(b);
			Constants.ChatHandler.sendMessage(msg);
		} else if (cls.equals(CHAT_LIST_CLASS))
		{
			Constants.sq_main_handler.sendMessage(new Message());
		} else if (cls.equals(HOME_CLASS))
		{
//			FunctionList.sq.setVisibility(View.VISIBLE);
			//发送消息通知
			Constants.pushHandler.sendEmptyMessage(0);
		}
	}

	public void dealAdd(JSONObject bundle)
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals(CHAT_LIST_CLASS))
		{
			// Constants.sq_main_handler.sendMessage(new Message());
		} else if (cls.equals(HOME_CLASS))
		{
			// FunctionList.sq.setVisibility(View.VISIBLE);
		}
		Constants.pushHandler.sendEmptyMessage(0);
	}

	public void dealReadd(JSONObject bundle)
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals(CHAT_LIST_CLASS))
		{
			// Constants.sq_main_handler.sendMessage(new Message());
		} else if (cls.equals(HOME_CLASS))
		{
			// FunctionList.sq.setVisibility(View.VISIBLE);
		}
		Constants.pushHandler.sendEmptyMessage(0);
	}

	public void dealPush(JSONObject bundle)
	{
		String cls = ClassNameHelper.getCurrentClassName(CurrentActivity.NOW_ACTIVITY);
		if (cls.equals(HOME_CLASS))
		{
			// Message msg = new Message();
			// msg.obj = JsonTools.getString(bundle, "id");
			// Constants.pushHandler.sendMessage(msg);
			Constants.pushHandler.sendEmptyMessageDelayed(1, 1 * 1000);
		}
	}

	public void open(Bundle bundle, Context context)
	{
		try
		{
			JSONObject jso = new JSONObject(bundle.getString("cn.jpush.android.EXTRA"));
			String code = JsonTools.getString(jso, "code");
			if (code != null)
			{
				if (code.equals(BodyChat.CHAT_TEXT))
				{
					//聊天
					openChat(jso, context);
				} else if (code.equals(BodyChat.CHAT_ADD))
				{
					//添加好友
					openAdd(jso, context);
				} else if (code.equals(BodyChat.CHAT_READD))
				{
					//确认添加好友
					openReadd(jso, context);
				} else if (code.equals(BodyPush.PUSH_TEXT) || code.equals(BodyPush.PUSH_TEACH))
				{
					//文本消息
					openPushText(jso, context);
				} else if (code.equals(BodyPush.PUSH_URL))
				{
					//推送地址
					openPushURL(jso, context);
				}
			}
		} catch (Exception e)
		{
		}
	}

	public void openChat(JSONObject bundle, Context context)
	{
		Intent intent = new Intent(context,liaotianjiemian.class);
		String friend_id = new SqliteHelper().rawQuery("select t1.FRIEND_ID from friend as t1,lt as t2 where t2.id=? and t1.friend_id=t2.fqr", JsonTools.getString(bundle, "id")).get(0).get("FRIEND_ID");
//		intent.setAction(CHAT_CLASS);
//		intent.setPackage(ExampleApplication.getAppPackage());
		intent.putExtra("friend_id", friend_id);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void openAdd(JSONObject bundle, Context context)
	{
		Intent intent = new Intent(context,NewFriend.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void openReadd(JSONObject bundle, Context context)
	{
		Intent intent = new Intent(context,tjhyxxfk.class);
		intent.putExtra("message", JsonTools.getString(bundle, "message"));
		intent.putExtra("friend_id", JsonTools.getString(bundle, "fqr"));
		intent.putExtra("name", JsonTools.getString(bundle, "name"));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void openPushText(JSONObject bundle, Context context)
	{
		Intent intent = new Intent(context,tzggxq.class);
		intent.putExtra("id", JsonTools.getString(bundle, "id"));
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}

	public void openPushURL(JSONObject bundle, Context context)
	{
		
		try
		{
			Intent intent = new Intent(context,WebActivity.class);
			intent.putExtra("id", JsonTools.getString(bundle, "id"));
			intent.putExtra("code", JsonTools.getString(bundle, "code"));
			intent.putExtra("ticket", JsonTools.getString(bundle, "ticket"));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Map<String, String> getCallBackParames(String userid, String id)
	{
		return MapTools.buildMap(new String[][]
		{
				{
						"userid", userid
				},
				{
						"id", id
				},
				{
						"ticket", Ticket.getPushTicket(Constants.number, Constants.code)
				}
		});
	}

	private boolean isFriend(String fqr)
	{
		return (!new SqliteHelper().rawQuery("select count(*) num from (select FRIEND_ID from mrfz where FRIEND_ID=? and userid=? union select FRIEND_ID from friend where FRIEND_ID=? and userid=? )", new String[]
		{
				fqr, Constants.number, fqr, Constants.number
		}).get(0).get("num").equals("0"));
	}

	private boolean saveToDB(String sql, Object... cs)
	{
		return new SqliteHelper().execSQL(sql, cs);
	}

	public Body getBodyByBundle(JSONObject bundle)
	{
		try
		{
			JSONObject jso = bundle;
			switch (bundle.getString("code"))
			{
			case BodyChat.CHAT_TEXT:
				return new BodyChat(jso.get("fqr").toString(), jso.get("message").toString(), jso.get("fssj").toString(), jso.get("code").toString(), "false", Constants.number,jso.getString("ticket"),jso.getString("id"));
			case BodyChat.CHAT_ADD:
				// return new BodyAdd(jso.get("fqr").toString(), jso.get("fjnr").toString(), jso.get("fssj").toString(), BodyAdd.DEAL_NO, jso.get("id").toString(), Constants.number);
				return ExampleApplication.getInstance().getGson().fromJson(bundle.toString(), BodyAdd.class);
			case BodyChat.CHAT_READD:
				String deal = "";
				if (jso.get("sfty").toString().equals("1"))
					deal = BodyAdd.DEAL_AGREE;
				else
					deal = BodyAdd.DEAL_DISAGREE;
				return new BodyReadd(jso.get("fqr").toString(), jso.get("sfty").toString(), jso.get("fssj").toString(), deal, jso.get("id").toString(), Constants.number, jso.getString("fqrname"));
			case BodyPush.PUSH_TEXT:
				return new BodyPush(jso.getString("n_id"), jso.getString("n_message"), jso.getString("n_send_time"), "false", Constants.number, jso.getString("code"), jso.getString("n_url"), jso.getString("function_id"), jso.getString("n_title"), jso.getString("n_ticket"),jso.getString("n_picpath"));
			case BodyPush.PUSH_URL:
				return new BodyPush(jso.getString("n_id"), jso.getString("n_message"), jso.getString("n_send_time"), "false", Constants.number, jso.getString("code"), jso.getString("n_url"), jso.getString("function_id"), jso.getString("n_title"), jso.getString("n_ticket"),jso.getString("n_picpath"));
			case BodyPush.PUSH_TEACH:
				return new BodyPush(jso.getString("n_id"), jso.getString("n_message"), jso.getString("n_send_time"), "false", Constants.number, jso.getString("code"), "", jso.getString("xkkh"), jso.getString("n_title"), jso.getString("n_ticket"),jso.getString("n_picpath"));
			default:
				break;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}