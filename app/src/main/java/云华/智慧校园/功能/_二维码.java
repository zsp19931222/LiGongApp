package 云华.智慧校园.功能;

import java.util.Random;

import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

public class _二维码
{
	public static final int PHOTO_PIC = new Random().nextInt(10000);

	public Bitmap build(String content, int sizePx, Context context)
	{
		try
		{
			return EncodingHandler.createQRCode(content, sizePx, context);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void scan(Context context)
	{
		try
		{
			Intent intent3 = new Intent(context, CaptureActivity.class);
			((Activity) context).startActivityForResult(intent3, PHOTO_PIC);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
