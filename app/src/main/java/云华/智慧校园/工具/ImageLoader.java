package 云华.智慧校园.工具;

import com.bumptech.glide.Glide;

import android.content.Context;
import android.widget.ImageView;

import com.example.app3.utils.GlideLoadUtils;
import yh.app.appstart.lg.R;

import yh.app.acticity.TargetDetailActivity;

public class ImageLoader
{
    private Context context;

    public ImageLoader(Context context)
    {
	this.context = context;
    }

    public void loadUrlImage(String url)
    {
	Glide.with(context).load(url).placeholder(R.drawable.ico_load_little).error(R.drawable.error);
    }

    public void loadUrlImage(String url, ImageView view)
    {
//	Glide.with(context).load(url).placeholder(R.drawable.ico_load_little).error(R.drawable.error).into(view);
        GlideLoadUtils.getInstance().glideLoad(context,url,view,R.drawable.ico_load_little);

    }
}