//package yh.app.tool;
//
//import android.app.Activity;
//import android.graphics.drawable.Drawable;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.DynamicDrawableSpan;
//import android.text.style.ImageSpan;
//import android.util.DisplayMetrics;
//
//public class AddEmotion extends Activity
//{
//	public AddEmotion()
//	{
//		getwh();
//	}
//
//	public void addEmotion_id(int Resources, String name)
//	{
//		try
//		{
//			Drawable d = getResources().getDrawable(Resources);
//			addEmotion_Drawable(d, name);
//		} catch (Exception e)
//		{
//		}
//	}
//
//	public SpannableString addEmotion_Drawable(Drawable d, String name)
//	{
//		try
//		{
//			d.setBounds(0, 0, 50, 50);
//			ImageSpan imageSpan = new ImageSpan(d, DynamicDrawableSpan.ALIGN_BOTTOM);
//			SpannableString spannableString = new SpannableString("[" + name + "]");
//			spannableString.setSpan(imageSpan, 0, name.length() + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//			return spannableString;
//		} catch (Exception e)
//		{
//			return null;
//		}
//	}
//
//	public void getwh()
//	{
//		DisplayMetrics dm = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(dm);
//	}
//}
