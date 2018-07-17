package 云华.智慧校园.工具;

public class NullTools
{
	public static boolean isNotNull(String... params)
	{
		try
		{
			for (int i = 0; i < params.length; i++)
			{
				if (params[i] == null || params[i].equals(""))
					return false;
			}
			return true;
		} catch (Exception e)
		{
			return false;
		}
	}
}
