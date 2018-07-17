package 云华.智慧校园.工具;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateHelper
{
	public static int weeksBetween(Date start, Date time)
	{
		if (getDayOfWeek(time) >= getDayOfWeek(start))
		{
			return (int) ((time.getTime() - start.getTime()) / (1000 * 3600 * 24) / 7 + 1);
		} else
			return (int) ((time.getTime() - start.getTime()) / (1000 * 3600 * 24) / 7 + 2);
	}

	public static int getDayOfWeek(Date time)
	{
		if (time == null)
			return 0;
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(time);
		int weeknum = c.get(Calendar.DAY_OF_WEEK);
		if (weeknum == 1)
			weeknum = 7;
		else
			weeknum -= 1;
		return weeknum;
	}

	public static int daysBetween(Date smdate, Date bdate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24) + 1;
		return Integer.parseInt(String.valueOf(between_days));
	}
	
}
