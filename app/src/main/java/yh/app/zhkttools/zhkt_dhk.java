package yh.app.zhkttools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.androidpn.push.Constants;

import com.yhkj.cqgyxy.R;
@SuppressLint("InflateParams")
public class zhkt_dhk
{
	private LinearLayout dhk_layout;
	private Context context;
	private ProgressBar jdt;
	private String ktbh;

	public zhkt_dhk(Context context, LinearLayout dhk_layout, String ktbh)
	{
		this.dhk_layout = dhk_layout;
		this.context = context;
		this.ktbh = ktbh;
	}

	// 学生对话框
	public void dhk_student(String mz, String fssj, String fsnr)
	{
		View dhk = LayoutInflater.from(context).inflate(R.layout.zhkt_xszt_item, null);
		add_dhk(mz, fssj, fsnr, dhk);
	}

	// 老师对话框
	public void dhk_teacher(String mz, String fssj, String fsnr)
	{
		View dhk = LayoutInflater.from(context).inflate(R.layout.zhkt_jszt_item, null);
		add_dhk(mz, fssj, fsnr, dhk);
	}

	private void add_dhk(String mz, String fssj, String fsnr, View dhk)
	{
		((TextView) dhk.findViewById(R.id.mz)).setText(mz);
		((TextView) dhk.findViewById(R.id.sj)).setHint(fssj);
		((TextView) dhk.findViewById(R.id.nr)).setText(fsnr);
		jdt = (ProgressBar) dhk.findViewById(R.id.dhkjdt);
		dhk_layout.addView(dhk);
		if (Constants.usertype == 1)
		{
//			new zhkt_handler().saveztnr(handler, ktbh, jsbh, xsbh, fsnr);
		}
	}

	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			if (msg.what == 1)
			{
			}
		}
	};
}
