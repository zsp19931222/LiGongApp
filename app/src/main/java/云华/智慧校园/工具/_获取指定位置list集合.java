package 云华.智慧校园.工具;

import java.util.ArrayList;
import java.util.List;

public class _获取指定位置list集合 
{
	public static <T> List<T> getList(List<T> list, int page_size, int page_num)
	{
		List<T> tempList = new ArrayList<T>();
		for (int i = 0; i < page_size; i++)
		{
			if(page_num * page_size + i >= list.size())
				return tempList;
			tempList.add(list.get(page_num * page_size + i));
		}
		return tempList;
	}
}
