package yh.app.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/16 0016.
 */

public class ToastUtils {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void Toast(Context context, String message) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    message,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
    }
}
