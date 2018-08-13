package yh.app.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
/**
 * 
 * 包 名:yh.app.progressdialog 类 名:CustomProgressDialog.java 功 能:进度条组件
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class CustomProgressDialog extends Dialog
{
	@SuppressWarnings("unused")
	private Context context = null;
	TextView tvMsg;
	private static CustomProgressDialog customProgressDialog = null;

	public CustomProgressDialog(Context context)
	{
		super(context);
		this.context = context;
	}

	public CustomProgressDialog(Context context, int theme)
	{
		super(context, theme);
	}

	public static CustomProgressDialog createDialog(Context context)
	{
		customProgressDialog = new CustomProgressDialog(context, R.style.CustomProgressDialog);
		customProgressDialog.setContentView(R.layout.customprogressdialog);
		customProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
		return customProgressDialog;
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{

		if (customProgressDialog == null)
		{
			return;
		}
//		ImageView imageView = (ImageView) customProgressDialog.findViewById(R.id.loadingImageView);
//		AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
//		animationDrawable.start();
	}

	/**
	 * 
	 * [Summary] setTitile 标题
	 * 
	 * @param strTitle
	 * @return
	 *
	 */
	public CustomProgressDialog setTitile(String strTitle)
	{
		return customProgressDialog;
	}

	/**
	 * 
	 * [Summary] setMessage 提示内容
	 * 
	 * @param strMessage
	 * @return
	 *
	 */
	public CustomProgressDialog setMessage(String strMessage)
	{
		tvMsg = (TextView) customProgressDialog.findViewById(R.id.id_tv_loadingmsg);

		if (tvMsg != null)
		{
			tvMsg.setText(strMessage);
		}else{
			tvMsg.setText(R.string.loadcontent);
		}

		return customProgressDialog;
	}
}
