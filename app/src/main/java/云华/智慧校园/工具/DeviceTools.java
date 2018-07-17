package 云华.智慧校园.工具;

import java.util.Locale;
import java.util.UUID;

import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class DeviceTools
{
	public static String getAndroidID(Context context)
	{
		return Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);
	}

	public static String getDriverID(Context context)
	{
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	public String getID(Context context)
	{
		String androidID = getAndroidID(context);
		if (androidID != null)
			return androidID;
		else if (androidID == null)
		{
			String driverID = getDriverID(context);
			if (driverID != null)
				return driverID;
		}
		return UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.getDefault());
	}
}