package 云华.智慧校园.工具;

import java.text.NumberFormat;

public class DecimalTools
{
	public static String getPercentage(double num, int accurateTo)
	{
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMaximumFractionDigits(accurateTo);
		return nf.format(num);
	}
}
