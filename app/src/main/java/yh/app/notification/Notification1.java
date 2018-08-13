package yh.app.notification;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import org.androidpn.push.Constants;

import com.yhkj.cqgyxy.R;
@SuppressWarnings(
{
		"unused", "deprecation"
})
public class Notification1
{
	private static final String NOTIFICATION_SERVICE = Context.NOTIFICATION_SERVICE;
	public static final int NOTIFICATION_FLAG = 1;
	public static final int NOTIFICATION = 0;

	public static final String NOTIFICATION_TYPE_TEXT = "101";
	public static final String NOTIFICATION_TYPE_URL = "102";

	public static final String NOTIFICATION_TYPE_CHAT_MESSAGE = "201";
	public static final String NOTIFICATION_TYPE_CHAT_ADDFRIEND = "202";
	public static final String NOTIFICATION_TYPE_CHAT_READDFRIEND = "203";

	public static final int NOTIFICATION_ALL = 10;

	public static final int NTIFICATION_TYPE_CHAT = new Random().nextInt(1000);
	public static final int NTIFICATION_TYPE_PUSH = new Random().nextInt(1000);

	/**
	 * @param title
	 * @param message
	 * @param action
	 *            点击跳转到哪一个action
	 * @param packageName
	 *            需要跳转到的程序包名
	 * @param isOpenLights
	 *            是否开启呼吸灯
	 * @param isOpenSound
	 *            是否开启声音提醒
	 * @param isOpenVibrate
	 *            是否开启震动
//	 * @param notificationType
//	 *            推送展示类型，NOTIFICATION_FLAG在常驻任务栏显示推送信息，NOTIFICATION不在常驻任务栏显示推送信息
//	 *            ，单独显示
	 */
	public static void notification_FLAG_ONGOING_EVENT(String id, String title, String message, String action, String packageName, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate, int type)
	{
		buildNotification_push(id, title, message, action, packageName, isOpenLights, isOpenSound, isOpenVibrate, type);
		// 新建推送实例
		// Notification notification = buildNotification(title, message, action,
		// packageName, isOpenLights, isOpenSound, isOpenVibrate);
		// showNotification(notification, notificationType);
	}

	public static void notification_FLAG_ONGOING_EVENT(NotificationItem notificationItem, int type)
	{
		buildNotification_push(notificationItem.getId(), notificationItem.getTitle(), notificationItem.getMessage(), notificationItem.getAction(), notificationItem.getPackageName(), notificationItem.isOpenLights(), notificationItem.isOpenSound(), notificationItem.isOpenVibrate(), type);
		// 新建推送实例
		// Notification notification =
		// buildNotification(notificationItem.getTitle(),
		// notificationItem.getMessage(), notificationItem.getAction(),
		// notificationItem.getPackageName(), notificationItem.isOpenLights(),
		// notificationItem.isOpenSound(), notificationItem.isOpenVibrate());
		// showNotification(notification,
		// notificationItem.getNotificationType());
	}

	/**
	 * 根据通知栏id取消通知栏展示
	 * 
	 * @param notificationType
	 *            NOTIFICATION_ALL取消所有通知栏，NOTIFICATION取消非常驻通知栏,
	 *            NOTIFICATION_FLAG取消常驻通知栏
	 */
	public static void cancel(int notificationType)
	{
		cancelNotification(notificationType);
	}

	private static void showNotification(Notification notification, int notificationType)
	{
		// 定义NotificationManager
		NotificationManager mNotificationManager = (NotificationManager) Constants.App_Context.getSystemService(NOTIFICATION_SERVICE);
		mNotificationManager.notify(notificationType, notification);
	}

	private static ReadWriteLock lock = new ReentrantReadWriteLock();

	public static void buildNotification_push(String id, String title, String message, String action, String packageName, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate, int type)
	{
		lock.writeLock().lock();
		try
		{
			// 创建一个Notification
			Notification notification = new Notification();
			// 设置通知 消息 图标
			notification.icon = R.drawable.xxtb;
			// 设置发出消息的内容
			notification.tickerText = message;
			// 设置发出通知的时间
			notification.when = System.currentTimeMillis();

			notification.flags = Notification.FLAG_AUTO_CANCEL;

			// 设置显示通知时的默认的发声、振动、Light效果
			notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;// 振动

			// Notification notification = new
			// Notification(R.drawable.ic_launcher,"有新的消息",
			// System.currentTimeMillis());

			Intent intent = new Intent();
			if (Notification1.NOTIFICATION_TYPE_TEXT.equals(NotificationTools.getNotificationType(id)))
			{
				intent.setAction(action);
				intent.setPackage(packageName);
				intent.putExtra("id", id);
			} else if (Notification1.NOTIFICATION_TYPE_URL.equals(NotificationTools.getNotificationType(id)))
			{
				intent.setAction(action);
				intent.setPackage(packageName);
				Bundle b = new Bundle();
				intent.putExtra("id", id);
				intent.putExtra("url", NotificationTools.getNotificationURL(id));
			}
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// 3步：PendingIntent android系统负责维护
			PendingIntent pendingIntent = PendingIntent.getActivity(Constants.App_Context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// 4步：设置更加详细的信息
//			notification.setLatestEventInfo(Constants.App_Context, title, message, pendingIntent);
			// 5步：使用notificationManager对象的notify方法 显示Notification消息 需要制定
			// Notification的标识
			NotificationManager mNotificationManager = (NotificationManager) Constants.App_Context.getSystemService(NOTIFICATION_SERVICE);
			mNotificationManager.notify(type, notification);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		lock.writeLock().unlock();
	}

	public static void buildNotification(String id, String title, String message, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate, int type, IntentHelper helper)
	{
		lock.writeLock().lock();
		try
		{
			// 创建一个Notification
			Notification notification = new Notification();
			// 设置通知 消息 图标
			notification.icon = R.drawable.xxtb;
			// 设置发出消息的内容
			notification.tickerText = message;
			// 设置发出通知的时间
			notification.when = System.currentTimeMillis();

			notification.flags = Notification.FLAG_AUTO_CANCEL;

			// 设置显示通知时的默认的发声、振动、Light效果
			notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND;// 振动

			// Notification notification = new
			// Notification(R.drawable.ic_launcher,"有新的消息",
			// System.currentTimeMillis());

			Intent intent = helper.setIntent();
			// 3步：PendingIntent android系统负责维护
			PendingIntent pendingIntent = PendingIntent.getActivity(Constants.App_Context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			// 4步：设置更加详细的信息
//			notification.setLatestEventInfo(Constants.App_Context, title, message, pendingIntent);
			// 5步：使用notificationManager对象的notify方法 显示Notification消息 需要制定
			// Notification的标识
			NotificationManager mNotificationManager = (NotificationManager) Constants.App_Context.getSystemService(NOTIFICATION_SERVICE);
			mNotificationManager.notify(type, notification);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		lock.writeLock().unlock();
	}

	public interface IntentHelper
	{
		Intent setIntent();
	}

	private static Notification buildNotification(String title, String message, String action, String packageName, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate)
	{
		// 定义通知栏展现的内容信息
		long when = System.currentTimeMillis();
		NotificationCompat.Builder builder = new NotificationCompat.Builder(Constants.App_Context).setContent(buildRemoteViews(title, message, action, packageName)).setWhen(when).setSmallIcon(R.drawable.xxtb);
		Notification notification = builder.build();
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		setLightsVibrateSound(notification, isOpenLights, isOpenSound, isOpenVibrate);
		notification.flags |= Notification.FLAG_ONGOING_EVENT;
		return notification;
	}

	private static void setLightsVibrateSound(Notification notification, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate)
	{
		if (isOpenLights)
		{
			setLights(notification);
		}
		if (isOpenVibrate)
		{
			setVibrate(notification);
		}
		if (isOpenSound)
		{
			setSound(notification);
		}
	}

	private static RemoteViews buildRemoteViews(String title, String message, String action, String packageName)
	{
		RemoteViews rv = new RemoteViews(Constants.App_Context.getPackageName(), R.layout.push_main_layout);
		rv.setTextViewText(R.id.title, title);
		rv.setTextViewText(R.id.message, message);
		rv.setImageViewResource(R.id.push_image, R.drawable.xxtb);
		rv.setOnClickPendingIntent(R.id.kbcx, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, "yh.app.coursetable.TableDemoActivity", packageName));
		rv.setOnClickPendingIntent(R.id.cjcx, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, "yh.app.score.Stu_Grade_Query_Activity", packageName));
		rv.setOnClickPendingIntent(R.id.push_layout, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, action, packageName));
		rv.setOnClickPendingIntent(R.id.title, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, action, packageName));
		rv.setOnClickPendingIntent(R.id.message, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, action, packageName));
		rv.setOnClickPendingIntent(R.id.push_image, new PendingIntentHelper().buildPendingIntent(Constants.App_Context, action, packageName));
		return rv;
	}

	private static void setLights(Notification notification)
	{
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.defaults = notification.defaults | Notification.DEFAULT_LIGHTS;
		notification.flags = notification.flags | Notification.FLAG_SHOW_LIGHTS;
	}

	private static void setVibrate(Notification notification)
	{
		notification.ledARGB = 0xff00ff00;
		notification.ledOnMS = 300;
		notification.ledOffMS = 1000;
		notification.defaults = notification.defaults | Notification.DEFAULT_LIGHTS;
		notification.flags = notification.flags | Notification.DEFAULT_VIBRATE;
	}

	private static void setSound(Notification notification)
	{
		notification.defaults |= Notification.DEFAULT_SOUND;
	}

	private static void cancelNotification(int notificationType)
	{
		NotificationManager notificationManager = (NotificationManager) Constants.App_Context.getSystemService(NOTIFICATION_SERVICE);
		if (notificationType == NOTIFICATION_ALL)
			notificationManager.cancelAll();
		else
			notificationManager.cancel(notificationType);
	}

	private static void cancelNotificationById(int Id)
	{
		NotificationManager notificationManager = (NotificationManager) Constants.App_Context.getSystemService(NOTIFICATION_SERVICE);
		notificationManager.cancel(Id);
	}
}
