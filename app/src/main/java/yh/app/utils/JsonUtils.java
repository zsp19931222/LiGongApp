package yh.app.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * json 获取工具
 */

public class JsonUtils {

    private static BufferedReader bufferedReader;
    private static AssetManager assetManager;

    public synchronized static String getJosn(Context context, String fileName)
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
