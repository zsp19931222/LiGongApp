package yh.app.notification;

public class NotificationItem
{
	private String title;
	private String message;
	private String action;
	private String packageName;
	private boolean isOpenLights;
	private boolean isOpenSound;
	private boolean isOpenVibrate;
	private int notificationType;
	private String id;

	public NotificationItem(String id, String title, String message, String action, String packageName, boolean isOpenLights, boolean isOpenSound, boolean isOpenVibrate, int notificationType)
	{
		setId(id);
		setTitle(title);
		setMessage(message);
		setAction(action);
		setPackageName(packageName);
		setOpenLights(isOpenLights);
		setOpenVibrate(isOpenVibrate);
		setOpenSound(isOpenSound);
		setNotificationType(notificationType);
	}

	public String getId()
	{
		return id;
	}

	private void setId(String id)
	{
		this.id = id;
	}

	public String getTitle()
	{
		return title;
	}

	private void setTitle(String title)
	{
		this.title = title;
	}

	public String getMessage()
	{
		return message;
	}

	private void setMessage(String message)
	{
		this.message = message;
	}

	public String getAction()
	{
		return action;
	}

	private void setAction(String action)
	{
		this.action = action;
	}

	public String getPackageName()
	{
		return packageName;
	}

	private void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public boolean isOpenLights()
	{
		return isOpenLights;
	}

	private void setOpenLights(boolean isOpenLights)
	{
		this.isOpenLights = isOpenLights;
	}

	public boolean isOpenSound()
	{
		return isOpenSound;
	}

	private void setOpenSound(boolean isOpenSound)
	{
		this.isOpenSound = isOpenSound;
	}

	public boolean isOpenVibrate()
	{
		return isOpenVibrate;
	}

	private void setOpenVibrate(boolean isOpenVibrate)
	{
		this.isOpenVibrate = isOpenVibrate;
	}

	public int getNotificationType()
	{
		return notificationType;
	}

	private void setNotificationType(int notificationType)
	{
		this.notificationType = notificationType;
	}

}
