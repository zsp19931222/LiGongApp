package yh.tool.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import com.example.app3.tool.HintTool;
import com.example.smartclass.eventbus.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import com.yhkj.cqgyxy.R;

public class LoadDiaog extends Dialog implements DialogInterface.OnDismissListener

{
	private TextView txt_load_content;

	public LoadDiaog(Context context, int theme)
	{
		super(context, theme);
		initView();
	}

	public LoadDiaog(Context context)
	{
		super(context, R.style.Dialog);
		this.setCanceledOnTouchOutside(true);
		this.setCancelable(true);
		this.setOnDismissListener(this);
		initView();
	}

	private void initView()
	{
		setContentView(R.layout.layout_dialog);
		txt_load_content = findViewById(R.id.txt_load_content);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		if (null != title)
		{
			txt_load_content.setText(title);
		} else
		{
			txt_load_content.setText(R.string.loadcontent);
		}

		super.setTitle(title);
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		EventBus.getDefault().post(new MessageEvent(HintTool.DialogDismiss,HintTool.DialogDismiss));
	}
}
