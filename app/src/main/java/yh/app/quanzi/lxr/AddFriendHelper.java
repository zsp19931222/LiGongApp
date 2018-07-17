package yh.app.quanzi.lxr;

import yh.app.tool.SqliteHelper;

public class AddFriendHelper
{
	public void addFriend(String friend_id, String name, String szm, String userid)
	{
		// FRIEND_ID text pr primary key,name text,szm text,userid text
		new SqliteHelper().execSQL("insert into friend( FRIEND_ID,name ,szm ,userid )", new Object[]
		{
				friend_id, name, szm, userid
		});
	}
}
