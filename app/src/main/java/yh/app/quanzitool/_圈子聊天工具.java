package yh.app.quanzitool;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.example.jpushdemo.body.BodyAdd;
import com.example.jpushdemo.body.BodyChat;

import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;

public class _圈子聊天工具
{
	public void saveMySendMessage(String id, String friend_id, String message, String fssj)
	{
		List<Map<String, String>> result = new SqliteHelper().rawQuery("select * from friend where FRIEND_ID=? and userid = ?", friend_id, Constants.number);
		String hymc = null;
		if (result != null && result.size() > 0)
		{
			hymc = result.get(0).get("name");
		} else
		{
			return;
		}
		new SqliteHelper().execSQL("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
		{
				UUID.randomUUID().toString(), Constants.number, BodyAdd.DEAL_NOREAD, hymc, message, friend_id, "", fssj, BodyChat.CHAT_TEXT, ""
		});
		new SqliteHelper().execSQL("insert into lt(id,fqr,jsr,friend_id,message,fssj,userid,ticket,isread) values(?,?,?,?,?,?,?,?,?)", new Object[]
		{
				id, Constants.number, friend_id, friend_id, message, fssj, Constants.number, "", "true"
		});
	}

	public void saveOtherSendMessage(String id, String friend_id, String message, String fssj, String ticket, String isread)
	{
		List<Map<String, String>> result = new SqliteHelper().rawQuery("select * from friend where FRIEND_ID=? and userid = ?", friend_id, Constants.number);
		String hymc = null;
		if (result != null && result.size() > 0)
		{
			hymc = result.get(0).get("name");
		} else
		{
			return;
		}
		new SqliteHelper().execSQL("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
		{
				UUID.randomUUID().toString(), Constants.number, BodyAdd.DEAL_NOREAD, hymc, message, friend_id, "", fssj, BodyChat.CHAT_TEXT, ""
		});
		new SqliteHelper().execSQL("replace into lt(id,fqr,jsr,friend_id,message,fssj,userid,ticket,isread) values(?,?,?,?,?,?,?,?,?)", new Object[]
		{
				id, friend_id, Constants.number, friend_id, message, fssj, Constants.number, ticket, isread
		});
	}
}
