/**
 * 
 */
package 云华.智慧校园.工具;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

/**
 * Title: TextViewTools.java Description:
 * 
 * @author 阙海林 Company 云华科技
 * @lastchange 2016年12月15日
 * @date 2016年12月15日
 * @version 1.0.1
 */
public class TextViewTools
{
	public void setIndent(TextView textview, String text)
	{
		SpannableStringBuilder span = new SpannableStringBuilder("缩进" + text);
		span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
		textview.setText(span);
	}
}
