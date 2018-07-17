package yh.app.quanzitool;

import java.util.Random;

import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.contacts.UserDetail;
import yh.app.tool.ViewClickEffect;
import 云华.智慧校园.工具.RHelper;

@SuppressLint("InflateParams")
public class InflaterView
{
	private ImageView image;
	private TextView name;
	private Context mContext;
	private String lxr_name;
	private String tag;
	private Context context;
	private String userid;

	public InflaterView(Context mContext, String lxr_name, String tag,String userid)
	{
		this.mContext = mContext;
		this.lxr_name = lxr_name;
		this.tag = tag;
		this.context=mContext;
		this.userid=tag;//这里传的为后台数据的userid
	}

	public InflaterView()
	{
	}

	// public Handler handler = new Handler()
	// {
	// @Override
	// public void handleMessage(android.os.Message msg)
	// {
	// mView.getBackground().clearColorFilter();
	// Intent intent = new Intent();
	// intent.setAction("yh.app.function.liaotianjiemian");
	// intent.setPackage(ExampleApplication.getAppPackage());
	// intent.putExtra("fqr", tag);
	// intent.putExtra("name", lxr_name);
	// mContext.startActivity(intent);
	// };
	// };

	public View addLIXInflater(ViewGroup parent,String imgurl)
	{
		LayoutInflater inFlater = LayoutInflater.from(mContext);
		View mView = inFlater.inflate(R.layout.quanzi_liaotian_group_list_sub, parent, false);
		image = (ImageView) mView.findViewById(R.id.quanzi_lxr_img);
		name = (TextView) mView.findViewById(R.id.quanzi_lxr_name);
		name.setText(lxr_name);
		//联系人头像
		GlideLoadUtils.getInstance().glideLoad(context,imgurl,image,R.drawable.q1);
//		image.setBackgroundResource(new RHelper().getDrawable(mContext, ("tx" + (new Random().nextInt(8) + 1))));
		
		mView.setTag(tag);
		//点击聊天
		mView.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (Constants.number.equals(userid)) {
					Intent intentuserdetail=new Intent(context,UserDetail.class);
					intentuserdetail.putExtra("hybh", userid);
					context.startActivity(intentuserdetail);
				}else{
					ViewClickEffect.doEffect(v, 200, mContext, "yh.app.function.liaotianjiemian", ExampleApplication.getAppPackage(), new String[][]
							{
									{
											"friend_id", tag
									},
									{
											"name", lxr_name
									}
							});
				}
				
			}
		});
		return mView;
	}

	

	public View addTITLEInflater(String szm, ViewGroup parent)
	{
		LayoutInflater inFlater = LayoutInflater.from(mContext);
		View mView = inFlater.inflate(R.layout.quanzi_liaotian_group_title, parent, false);
		((TextView) mView.findViewById(R.id.quanzi_liaotian_group_title_text)).setText(szm);
		return mView;
	}
}
