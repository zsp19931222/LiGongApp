package 云华.智慧校园.工具;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class JsonReaderHelper
{
	private static BufferedReader bufferedReader;
	private static AssetManager assetManager;

	public static String getJosn(Context context, String fileName)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			if (assetManager == null)
			{
				assetManager = context.getAssets();
			}
			bufferedReader = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null)
			{
				sb.append(lineTxt);
			}
			bufferedReader.close();
			String result = sb.toString();
			return result;
		} catch (Exception e)
		{
			return null;
		}

	}
}
