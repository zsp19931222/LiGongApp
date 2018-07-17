/**
 * 
 */
package 云华.智慧校园.工具;

import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;

/**
 * Title: MessageDataBaseFresh.java Description:
 * 
 * @author 阙海林 Company 云华科技
 * @lastchange 2016年12月14日
 * @date 2016年12月14日
 * @version 1.0.1
 */
public class MessageDataBaseFresh
{
	public static boolean freshPush(String function_id)
	{
		if (!new SqliteHelper().rawQuery("select count(*) as num from tzgg where userid=? and func_id=?", new String[]
		{
				Constants.number, function_id
		}).get(0).get("num").equals("0"))
			return new SqliteHelper().execSQL("REPLACE INTO newest_message (id ,userid ,type ,unread_num ,title ,message ,date)SELECT t.func_id, t.userid,t.type,(SELECT count( * ) FROM tzgg as t1 WHERE t1.func_id = t.func_id AND t1.userid = t.userid AND isread = 'false') ,t.title,t.message,t.fssj FROM  tzgg AS t  WHERE func_id = ? AND userid = ? AND fssj = (SELECT max(fssj) FROM tzgg as t2 WHERE t2.func_id = t.func_id AND t2.userid = t.userid)", new Object[]
			{
					function_id, Constants.number
			});
		else
		{
			return new SqliteHelper().execSQL("delete from newest_message where userid=? and id=?", new Object[]
			{
					Constants.number, function_id
			});
		}
	}

	public static boolean freshChat(String hybh)
	{
		return new SqliteHelper().execSQL("replace into newest_message (id ,userid ,type ,unread_num ,title ,message ,date) select t1.fqr,t1.userid,t1.type,(select count(*) from chat t2),t1.fqrname,t1.fjnr,t1.fssj from chat as t1 where t1.userid=? and fqr=? and fssj=(select max(fssj) from chat t3 where t3.userid=t1.userid and t3.fqr = t1.fqr)", new Object[]
		{
				Constants.number, hybh
		});
	}

	public static boolean freshAdd()
	{
		return new SqliteHelper().execSQL("replace into newest_message(id,userid,type,unread_num,title,message,date) select ?,t1.userid,t1.type,(select count(*) from addfriend t2 where t2.userid=t1.userid and t2.isread = 'false'),t1.fqrname,t1.fjnr,t1.fssj from addfriend t1 where t1.userid=? and t1.fssj=(select max(fssj) from addfriend t2 where userid=t1.userid)", new Object[]
		{
				CodeManage.CHAT_ADD_FUNCTION_ID, Constants.number
		});
	}
}