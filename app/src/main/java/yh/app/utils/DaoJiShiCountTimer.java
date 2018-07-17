package yh.app.utils;

import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 倒计时类
 * 
 * @author anmin
 *
 */
public class DaoJiShiCountTimer extends CountDownTimer {

	public static final int TIME_COUNT = 30000;// 倒计时总时间

	public TextView btn_default;
	private String endStrRid;// 倒计时玩完显示内容
	private Button btn_endstr;
	private PopupWindow window;

	/**
	 * 参数 millisInFuture 倒计时总时间（如30s,60S，120s等） 参数 countDownInterval
	 * 渐变时间（每次倒计1s） 参数 btn_default 当前播放倒计时赋值控件 参数 endStrRid 倒计时结束后，按钮对应显示的文字 参数
	 * btn_endstr 播放过后赋值的控件
	 */
	public DaoJiShiCountTimer(long millisInFuture, long countDownInterval, TextView btn_default, String endStrRid,
			Button btn_endstr, PopupWindow window) {
		super(millisInFuture, countDownInterval);
		this.btn_default = btn_default;
		this.endStrRid = endStrRid;
		this.btn_endstr = btn_endstr;
		this.window = window;
		

	}

	/**
	 * 参数上面有注释
	 */
	public DaoJiShiCountTimer(TextView btn_default, String endStrRid) {
		super(TIME_COUNT, 1000);
		this.btn_default = btn_default;
		this.endStrRid = endStrRid;
		

	}

	/**
	 * 计时完成时调用
	 */
	@Override
	public void onFinish() {

		btn_endstr.setText(endStrRid);
		btn_endstr.setEnabled(true);
		if (window != null) {
			window.dismiss();
		}

	}

	/**
	 * 计时过程中调用
	 */
	@Override
	public void onTick(long arg0) {

		btn_default.setEnabled(false);// 动画过程中设置按钮不可点击避免重复执行动画
		// 每隔一秒修改一次UI
		btn_default.setText(arg0 / 1000 + "");

		// 设置透明度渐变动画
		final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		// 设置动画持续时间
		alphaAnimation.setDuration(1000);
		btn_default.startAnimation(alphaAnimation);

		// 设置缩放渐变动画
		final ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 2f, 0.5f, 2f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimation.setDuration(1000);
		btn_default.startAnimation(scaleAnimation);

	}

}
