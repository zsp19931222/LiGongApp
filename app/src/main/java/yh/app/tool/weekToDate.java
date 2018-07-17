package yh.app.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import yh.app.wisdomclass.GetTime_djz_xqj;

/**
 * 包 名:yh.app.tool 类 名:weekToDate.java 功 能:对日期进行处理
 * 
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class weekToDate
{
	private static Date startDay;
	private static Date endDay;
	private static Context context;

	public weekToDate(Context context)
	{
		weekToDate.context = context;
	}

	public weekToDate(String startDay, String endDay)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			weekToDate.startDay = sdf.parse(startDay);
			weekToDate.endDay = sdf.parse(endDay);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	// 返回当前时间的星期数
	public static int getDayOfWeekNum(Date date)
	{
		int weekNum = dayofweek(date);
		return weekNum;
	}

	// 返回当前时间的周数
	public static int getCurrentWeekNum(Date date)
	{
		int currentWeek = 0;
		if (startDay.after(date))
		{
			currentWeek = 1;
		} else
		{
			currentWeek = weeksBetweenTwoDates(startDay, date);
		}
		return currentWeek;
	}

	/*
	 * 根据起始时间和结束时间来计算本学期的总共的周数
	 */
	public static int totalWeek()
	{
		SQLiteDatabase db = new SqliteHelper().getRead();
		Cursor c = db.rawQuery("select starttime,endtime from nowterm", null);
		Date s = null, e = null;
		while (c.moveToFirst())
		{
			try
			{
				startDay = s = new SimpleDateFormat("yyyy-MM-dd").parse(c.getString(0));
				endDay = e = new SimpleDateFormat("yyyy-MM-dd").parse(c.getString(1));
			} catch (ParseException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			break;
		}
		c.close();
		db.close();
		int weeks = weeksBetweenTwoDates(s, e);
		return weeks;
	}

	/*
	 * 根据周数和星期数计算出具体的时间
	 */
	public static String getDate(int weekNum, int dayOfWeek)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		int dayweek = dayofweek(startDay);// 获得当前日期是一个星期的第几天
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, 7 * (weekNum - 1) + dayOfWeek - dayweek);
		String date = "";
		if (cal.get(Calendar.DAY_OF_MONTH) == 1)
		{
			date = String.valueOf(cal.get(Calendar.MONTH) + 1) + "月";
		} else
		{
			date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
		}
		return date;
	}

	/*
	 * 根据给定周次。计算这一周包含的7天的时间
	 */
	public static List<String> OneWeekDays(int weekNum)
	{
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 1; i <= 7; i++)
		{
			list.add(getDate(weekNum, i));
		}
		return list;
	}

	/*
	 * 根据给定周次。计算该周前缀时间
	 */
	public static String MouthWeek(int weekNum)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDay);
		int dayweek = dayofweek(startDay);// 获得当前日期是一个星期的第几天
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.add(Calendar.DATE, 7 * (weekNum - 1) + 1 - dayweek);
		String date = String.valueOf(cal.get(Calendar.MONTH) + 1) + "月";
		return date;
	}

	/*
	 * 根据起始时间和结束时间来计算本学期的总共的周数
	 */
	public static int weeksBetweenTwoDates(Date from, Date to)
	{
		int weeks = 0;
		try
		{
			return new GetTime_djz_xqj("", "").daysBetween(from, to);
		} catch (ParseException e)
		{
			return 0;
		}
	}

	/*
	 * 根据年份来判断这年中的每个月有几天
	 * 
	 * @param year
	 * 
	 * @result int[]
	 */
	public static int[] dayOfMonth(int year)
	{
		int[] days = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
		if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
		{
			days[1] = 29;
		}
		return days;
	}

	/*
	 * 根据年份来判断这年总的天数
	 * 
	 * @param year
	 * 
	 * @result int
	 */
	public static int getYearDays(int year)
	{
		int day = 365;
		if ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0))
		{
			day = 366;
		}
		return day;
	}

	/*
	 * 根据年、月、日来计算从这年的一月一号起到这年的某月某日的总的天数
	 * 
	 * @param year
	 * 
	 * @param month
	 * 
	 * @param day
	 * 
	 * @result int
	 */
	public static int sumDay(int year, int month, int day)
	{
		int sum = 0;
		for (int i = 0; i < month - 1; i++)
		{
			sum += dayOfMonth(year)[i];
		}
		sum += day;
		return sum;
	}

	/*
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * 
	 * @param date2
	 * 
	 * @return
	 */
	public static int daysBetweenTwoDates(Date from, Date to)
	{
		if (from.after(to))
		{
			Date temp = to;
			to = from;
			from = temp;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		long start = cal.getTimeInMillis();
		cal.setTime(to);
		long end = cal.getTimeInMillis();
		long between_days = (end - start) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days)) + 1;
	}

	/*
	 * 将以星期天为一周的第一天转化为以星期一为一周的第一天
	 */
	public static int dayofweek(Date date)
	{
		int sum = 0;
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		switch (day.get(Calendar.DAY_OF_WEEK))
		{
		case Calendar.MONDAY:
			sum = 1;
			break;
		case Calendar.TUESDAY:
		{
			sum = 2;
			break;
		}
		case Calendar.WEDNESDAY:
		{
			sum = 3;
			break;
		}
		case Calendar.THURSDAY:
		{
			sum = 4;
			break;
		}
		case Calendar.FRIDAY:
		{
			sum = 5;
			break;
		}
		case Calendar.SATURDAY:
		{
			sum = 6;
			break;
		}
		case Calendar.SUNDAY:
		{
			sum = 7;
			break;
		}
		default:
			break;
		}
		return sum;
	}

	public static Date getStartDay()
	{
		return startDay;
	}

	public static void setStartDay(Date startDay)
	{
		weekToDate.startDay = startDay;
	}

	public static Date getEndDay()
	{
		return endDay;
	}

	public static void setEndDay(Date endDay)
	{
		weekToDate.endDay = endDay;
	}
}
