package 云华.智慧校园.工具;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

@SuppressLint("SimpleDateFormat")
public class DateTools
{
	
	

	/**
	 * 计算时间
	 * 
	 * @param fstiem
	 * @return
	 */
	static String date;
	static String ft;
	public static String getTime(final String fstiem) {
		ft = fstiem;
		Date dNow = new Date();// 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault()); // 设置时间格式
		//拿到当前时间
		String nowtime = sdf.format(dNow);
		//得到年月日
		String[] strs = fstiem.split(" ");
		String year = strs[0];
		//得到日
		String[] strsd = year.split("-");
		String day = strsd[2];
		
		//计算是否为今天
		int d = Integer.valueOf(nowtime) - Integer.valueOf(day);
		switch (d) {
		case 0:
			// 今天
			date = "今天 "+ft;
			break;

		case 1:
			// 昨天
			date = "昨天 "+ft;
			break;
		case 2:
			// 前天
			date = "前天 "+ft;
			break;

		default:
			date = ft;
			break;

		}

		return date;
	}
	
	
	/**
	 * 计算消息时间
	 * 
	 * @param fstiem
	 * @return
	 */
	static String msageDate;
	static String msagefs;
	static String msageyear;
	public static String getMessageTime(final String fstiem) {
		msageyear=fstiem;
		Date dNow = new Date();// 当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault()); // 设置时间格式
		//拿到当前时间
		String nowtime = sdf.format(dNow);
		//得到年月日
		String[] strs = fstiem.split(" ");
		String year = strs[0];
		msagefs = strs[1];
		//得到日
		String[] strsd = year.split("-");
		String day = strsd[2];
		
		//计算是否为今天
		int d = Integer.valueOf(nowtime) - Integer.valueOf(day);
		switch (d) {
		case 0:
			// 今天
			msageDate = "今天 "+msagefs;
			break;

		case 1:
			// 昨天
			msageDate = "昨天 "+msagefs;
			break;
		case 2:
			// 前天
			msageDate = "前天 "+msagefs;
			break;

		default:
			msageDate = msageyear;
			break;

		}

		return msageDate;
	}
	
	/**
	 * 
	 * @param from
	 * @param to
	 * @return 返回两个时间的时间差，单位为毫秒
	 */
	public static long getTimeDifference(Date from, Date to)
	{
		try
		{

			long diff = to.getTime() - from.getTime();// 这样得到的差值是微秒级别
			return diff / 1000;
		} catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * 获取日期字符串,今天,昨天或者日期
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	public static String getDayString(Date from, Date to)
	{
		if (getBetweenDays(from, to) == 0)
			return "今天";
		else if (getBetweenDays(from, to) == 1)
			return "昨天";
		else
			return DateToStringYMD(to);
	}
	public static String DateToStringYMD(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static int getBetweenDays(Date from, Date to)
	{
		return (int) (getTimeDifference(from, to) / 1000 / 60 / 60 / 24);
	}
	
	public static String DateToString(Date date, String pattern)
	{
		return new SimpleDateFormat(pattern).format(date);
	}

	public static Date StringToDate(String date, String pattern)
	{
		try
		{
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e)
		{
			return new Date();
		}
	}

	public static String DateToStringYMDHMS(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static Date StringToDateYMDHMS(String date)
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		} catch (ParseException e)
		{
			return new Date();
		}
	}

	public static String DateToStringYMDHM(Date date)
	{
		return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
	}

	public static Date StringToDateYMDHM(String date)
	{
		try
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(date);
		} catch (ParseException e)
		{
			return new Date();
		}
	}
}
