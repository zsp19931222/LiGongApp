package 云华.智慧校园.自定义控件;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView
{

	public MyTextView(Context context)
	{
		super(context);
	}

	public MyTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void setText(CharSequence text, BufferType type)
	{
		super.setText(ToDBC(text.toString()), type);
	}

	private String ToDBC(String input)
	{
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == 12288)
			{
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

}