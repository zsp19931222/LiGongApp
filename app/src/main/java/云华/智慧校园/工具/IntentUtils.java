package 云华.智慧校园.工具;

import java.util.Iterator;
import java.util.Map;

import android.content.Intent;

public class IntentUtils
{
    public static void addExtraByMap(Intent intent, Map<String, String> param)
    {
	if (intent == null || param == null || param.size() == 0)
	{
	    return;
	}
	Iterator<String> keys = param.keySet().iterator();
	while (keys.hasNext())
	{
	    String name = keys.next();
	    intent.putExtra(name, param.get(name));
	}
    }
}
