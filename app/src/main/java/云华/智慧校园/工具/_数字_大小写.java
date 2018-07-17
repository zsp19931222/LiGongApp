package 云华.智慧校园.工具;

public class _数字_大小写
{
	private static final String[] Capital = new String[]
	{
			"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
	};

	/**
	 * 最大两位数
	 * 
	 * @param num
	 * @return
	 */
	public String getCapital(int num)
	{
		StringBuffer sb = new StringBuffer();
		if (num < 100)
		{
			if (num / 10 > 1)
			{
				sb.append(Capital[num / 10] + "十");
			} else if (num / 10 == 1)
			{
				sb.append("十");
			}
			if (num % 10 > 0)
			{
				sb.append(Capital[num % 10]);
			}
			return sb.toString();
		} else
			return null;
	}
}
