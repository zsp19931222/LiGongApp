package yh.app.notification;

import yh.app.tool.SqliteHelper;

public class NotificationTools
{
	public static String getNotificationType(String id)
	{
		return new SqliteHelper().rawQuery("select type from tzgg where tzggid='" + id + "'").get(0).get("type");
	}

	public static String getNotificationURL(String id)
	{
		return new SqliteHelper().rawQuery("select url from tzgg where tzggid='" + id + "'").get(0).get("url");
	}
}
