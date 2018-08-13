package yh.app.quanzi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
@SuppressLint("InflateParams")
public class Tools_lt_item
{
	private Context mContext;
	private String message;
	@SuppressWarnings("unused")
	private String fssj;

	public Tools_lt_item(Context mContext, String message, String fssj)
	{
		this.mContext = mContext;
		this.fssj = fssj;
		this.message = message;
	}

	public View addLtItemOther()
	{
		LayoutInflater inFlater = LayoutInflater.from(mContext);
		View mView = inFlater.inflate(R.layout.quanzi_layout_liaotian_item_other, null);
		TextView text = (TextView) mView.findViewById(R.id.message);
		text.setText(message);
		return mView;
	}
}
