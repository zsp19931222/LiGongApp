package yh.app.wisdomclass;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.tool.AllATSSS;
import yh.app.tool.DateString;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具._链接地址导航;

public class StudentSendZt
{
	public void set(final Context context, final View view, final ZHKTLslb zhktLslb, final String ktbh)
	{
		view.findViewById(R.id.quanzi_lt_fsbutton).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				send(context, zhktLslb, ((EditText) view.findViewById(R.id.quanzi_liaotian_input)).getText().toString(), ktbh);
				((EditText) view.findViewById(R.id.quanzi_liaotian_input)).setText("");
			}
		});
	}

	private void send(Context context, ZHKTLslb zhktLslb, String nr, String ktbh)
	{
		if (zhktLslb.getLSBH()!=null) {
		zhktLslb.addItem(setItem(context, nr));
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", Constants.number);
		map.put("function_id", "20150116");
		map.put("ticket", Ticket.getFunctionTicket("20150116"));
		map.put("ktbh", ktbh);
		map.put("jsbh", zhktLslb.getLSBH());
		map.put("xsbh", Constants.number);
		map.put("ztnr", nr);
		map.put("fsrbh", Constants.number);
		new AllATSSS(_链接地址导航.DC.发送纸条.getUrl(), new Handler(), map, AllATSSS.POST).execute();
		}
	}

	private View setItem(Context context, String nr)
	{
		View view = LayoutInflater.from(context).inflate(R.layout.zhkt_xzt_xs, null, false);
		((TextView) view.findViewById(R.id.mc)).setText(Constants.name);
		((TextView) view.findViewById(R.id.rq)).setText(new DateString("yyyy-MM-dd hh:dd:ss").DateToString(new Date()));
		((TextView) view.findViewById(R.id.nr)).setText(nr);
		return view;
	}
}
