package yh.app.tool;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateHelper
{
	public static int getDayBetweenDay(Date start, Date end)
	{
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(start);
		c2.setTime(end);
		if (c1.get(Calendar.YEAR) != c1.get(Calendar.YEAR))
		{
			return getDayOfYear(c1.get(Calendar.YEAR)) - getDayOfYear(c1.get(Calendar.DAY_OF_YEAR)) + c2.get(Calendar.DAY_OF_YEAR);
		}
		return c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
	}

	public static int getDayBetweenDay(String start, String end)
	{
		return getDayBetweenDay(DateHelper.StringToDate(start, "yyyy-MM-dd"), DateHelper.StringToDate(end, "yyyy-MM-dd"));
	}

	public static Date StringToDate(String date, String lx)
	{
		try
		{
			return new SimpleDateFormat(lx).parse(date);
		} catch (ParseException e)
		{
			return null;
		}
	}

	/** ����ĳ��ĳ�µ�һ�������ڼ� */
	public static int getDyt(String nian, String yue)
	{
		Date dyt = new Date();
		try
		{
			dyt = new SimpleDateFormat("yyyy-MM-dd").parse(nian + "-" + yue + "-" + "01");
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		Calendar c1 = Calendar.getInstance();
		c1.setTime(dyt);
		return c1.get(Calendar.DAY_OF_WEEK);
	}

	/** ��ȡ��ǰ������ */
	public static int getDayNum(int nian, int yue)
	{
		if (getDayOfYear(nian) == 366 && yue == 2)
		{
			return 29;
		} else
		{
			return myts[yue - 1];
		}
	}

	private static int myts[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static int getDayOfYear(int nian)
	{
		if ((nian % 4 == 0 && nian % 100 != 0) || (nian % 400 == 0))
		{
			return 366;
		} else
			return 365;
	}
}
