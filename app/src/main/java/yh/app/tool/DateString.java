package yh.app.tool;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public class DateString
{
	private String leixing;

	public DateString(String leixing)
	{
		this.leixing = leixing;
	}

	public String DateToString(Date date)
	{
		SimpleDateFormat df = new SimpleDateFormat(leixing);
		return df.format(date);
	}

	public Date StringToDate(String string)
	{
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(leixing);
			return df.parse(string);
		} catch (ParseException e)
		{
			return null;
		}
	}

	public static String DateToString()
	{
		SimpleDateFormat df = new SimpleDateFormat("MM-dd hh:mm:ss");
		return df.format(new Date());
	}

	public static Date stringToDate(String p, String time)
	{
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(p);
			return df.parse(time);
		} catch (ParseException e)
		{
			return null;
		}
	}

	public static int getDayOfMonth(int year, int month)
	{
		Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
		try
		{
			aCalendar.setTime(new SimpleDateFormat("yyyy-MM").parse(year + "-" + month));
			int day = aCalendar.getActualMaximum(Calendar.DATE);
			System.out.println(day);
			return day;
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}

	public static int getDayOfMonth(int year, int month, int day)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		try
		{
			c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
			return c.get(Calendar.DAY_OF_WEEK);
		} catch (ParseException e)
		{
			return 0;
		}
	}

	public static int getYear(Date date)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}

	public static int getMonth(Date date)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static long compareDate(Date date1, Date date2)
	{
		return date1.getTime() - date2.getTime();
	}

	public static long compareDateYMDHMS(String date1, String date2)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try
		{
			return df.parse(date1).getTime() - df.parse(date2).getTime();
		} catch (ParseException e)
		{
			return 0;
		}
	}

	public static long compareDateYMD(String date1, String date2)
	{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			return df.parse(date1).getTime() - df.parse(date2).getTime();
		} catch (ParseException e)
		{
			return 0;
		}
	}

	public static Calendar getDate(int year, int month, int day)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(year, month, day);
		return c;
	}

	public static String getDateString(int year, int month, int day)
	{
		Calendar c = Calendar.getInstance(Locale.CHINA);
		c.set(year, month, day);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(c.getTime());
	}
}
