package yh.app.quanzitool;

import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;

public class UserTools
{
	public String getFriendName(String friend_id)
	{
		try
		{
			return new SqliteHelper().rawQuery("select name from friend where friend_id=? and userid=? union select name from mrfz where friend_id=? and userid=?", new String[]
			{
					friend_id, Constants.number, friend_id, Constants.number
			}).get(0).get("name");
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		return "好友";
	}
}
