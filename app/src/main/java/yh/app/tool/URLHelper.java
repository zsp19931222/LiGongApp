package yh.app.tool;

public class URLHelper
{
	public String getParames(String url, String name)
	{
		String[] PString = url.split("\\?");
		if (url.length() > 1)
		{
			for (int i = 0, length = PString[1].split("&").length; i < length; i++)
			{
				if (PString[1].split("&")[i].split("=")[0].equals(name))
				{
					return PString[1].split("&")[i].split("=")[1];
				}
			}
		}
		return "";
	}
}
