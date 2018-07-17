package yh.app.utils;

import yh.app.appstart.lg.R;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;


/**
 * 倒计时工具类
 */

public class CountDownUtil extends CountDownTimer {
    private String endstring;//计时完成显示的内容
    private TextView tv;//显示内容的控件
    private Context context;

    /**
     * 总时间
     *
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     *                          渐变时间
     */
    public CountDownUtil(Context context,long millisInFuture, long countDownInterval, String endstring, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.context=context;
        this.endstring = endstring;
        this.tv = tv;
    }

    /**
     * 计时过程中调用
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        tv.setEnabled(false);// 动画过程中设置按钮不可点击避免重复执行动画
        tv.setText(millisUntilFinished / 1000 + "s");
    }

    /**
     * 计时完成是调用
     */
    @Override
    public void onFinish() {
        tv.setText(endstring);
        tv.setTextColor(context.getResources().getColor(R.color.color_bleu));
        tv.setEnabled(true);
    }
}
