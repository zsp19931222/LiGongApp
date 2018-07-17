package 云华.智慧校园.工具;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.os.Bundle;

public class BundleTools
{
	public static Bundle getBundleToMap(Map<String, String> map)
	{
		Bundle bundle = new Bundle();
		Iterator<String> keys = MapTools.getMapKeys(map);
		while (keys.hasNext())
		{
			String key = keys.next();
			bundle.putString(key, map.get(key));
		}
		return bundle;
	}

	public static String[] getBundleValues(Bundle bundle, String... name)
	{
		String[] values = null;
		if (name != null && name.length > 0)
		{
			values = new String[name.length];
			for (int i = 0; i < name.length; i++)
			{
				try
				{
					values[i] = bundle.getString(name[i]);
				} catch (Exception e)
				{
				}
			}
		}
		return values;
	}

	public static String[] getBundleKeys(Bundle bundle)
	{
		List<String> keyList = new ArrayList<String>();
		Iterator<String> keys = bundle.keySet().iterator();
		while (keys.hasNext())
		{
			keyList.add(keys.next());
		}
		int size = keyList.size();
		try
		{
			return keyList.toArray(new String[size]);
		} catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
}
