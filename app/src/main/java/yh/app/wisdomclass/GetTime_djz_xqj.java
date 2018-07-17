package yh.app.wisdomclass;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class GetTime_djz_xqj
{
	private String start;
	private String end;
	public GetTime_djz_xqj(String start, String end)
	{
		this.start = start;
		this.end = end;
	}
	public int getDJZ()
	{
		// java.util.Calendar cal = java.util.Calendar.getInstance();
		// cal.setTime(new Date(System.currentTimeMillis()));
		// int currentWeek = cal.get(java.util.Calendar.WEEK_OF_YEAR);
		// int end = 0;
		// try
		// {
		// cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(this.end));
		// end = cal.get(java.util.Calendar.WEEK_OF_YEAR);
		// long endWeek = ((new
		// SimpleDateFormat("yyyy-MM-dd").parse(this.end).getTime() + new
		// SimpleDateFormat("yyyy-MM-dd").parse(this.start).getTime())/86400000/7);
		// if(currentWeek > endWeek)
		// return 0;
		// cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(start));
		// return currentWeek - cal.get(java.util.Calendar.WEEK_OF_YEAR) + 1;
		// } catch (ParseException e)
		// {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// return 0;
		// }
		try
		{
			return daysBetween(new SimpleDateFormat("yyyy-MM-dd").parse(start), new Date());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			return 0;
		}
	}
	public int daysBetween(Date smdate, Date bdate) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24) / 7 + 1;
		return Integer.parseInt(String.valueOf(between_days));
	}
	public int getXQJ()
	{
		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(new Date(System.currentTimeMillis()));
		return cal.get(java.util.Calendar.DAY_OF_WEEK);
	}
}
