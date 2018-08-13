package 云华.智慧校园.自定义控件;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import 云华.智慧校园.工具.ViewTools;import com.yhkj.cqgyxy.R;

public class _侧边导航栏
{
	public static final String[] words =
	{
			"↑", "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
	};

	public View show(Context context, ViewGroup parent)
	{
		View view = ViewTools.getView(context, R.layout.sidebar_word, parent);
		LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
		for (int i = 0; i < words.length; i++)
		{
			TextView tView = new TextView(context);
			tView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1));
			tView.setPadding(10, 0, 10, 0);
			tView.setText(words[i]);
			layout.addView(tView);
		}
		return view;
	}

	public Map<String, Integer> getWordsMap()
	{
		Map<String, Integer> map = new HashMap<>();
		map.put(words[0], 0);
		for (int i = 1; i < words.length; i++)
		{
			map.put(words[i], -1);
		}
		return map;
	}
}