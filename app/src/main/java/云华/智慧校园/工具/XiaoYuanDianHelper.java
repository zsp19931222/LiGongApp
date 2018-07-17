package 云华.智慧校园.工具;

import yh.tool.widget.DragPointView;

public class XiaoYuanDianHelper
{
	public static void setText(DragPointView text, String num)
	{
		int number = 0;
		try
		{
			number = Integer.valueOf(num);
		} catch (Exception e)
		{
			// TODO: handle exception
		}
		if (number > 0 && text != null)
		{
			text.setText(num + "");
		} else if (text != null)
		{
			text.setText(null);
		}
	}
}
