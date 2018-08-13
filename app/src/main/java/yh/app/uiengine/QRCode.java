package yh.app.uiengine;

import com.example.app3.BitmapUtil;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import yh.app.acticity.AboutActity;
import yh.app.activitytool.QRCodeActivity;
import org.androidpn.push.Constants;
import com.yhkj.cqgyxy.R;
import yh.app.stu_info.RoundImageView;
import yh.app.tool.DpPx;
import yh.app.tool.PackAganmanger;
import yh.app.utils.DefaultTopBar;
import 云华.智慧校园.功能._二维码;
import 云华.智慧校园.工具.PicTools;
import 云华.智慧校园.工具._链接地址导航;

public class QRCode extends QRCodeActivity
{
	private ImageView ewm;
//	private RoundImageView handimg;
//	private PackAganmanger packAganmanger;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ewm_layout);
		initView();
		// new _二维码().scan(this);
	}

	private void initView()
	{
//		packAganmanger = new PackAganmanger(QRCode.this);
		try
		{
			((TextView) findViewById(R.id.version_name)).setText(PackAganmanger.getAppName()+": " + PackAganmanger.getAppVersionName());
		} catch (Exception e)
		{
		}
		((ImageView)findViewById(R.id.touxiang)).setImageBitmap(BitmapUtil.getRoundedCornerBitmap(BitmapUtil.getBitmap(this, R.drawable.xxtb), new DpPx(this).getDpToPx(4)));
//		((TextView) findViewById(R.id.name)).setText(Constants.name);
		new DefaultTopBar(this).doit("我的名片");
		ewm = (ImageView) findViewById(R.id.ewm);
		ewm.setImageBitmap(new _二维码().build(_链接地址导航.UIA.下载页面.getUrl() + Constants.number, 256, this));
//		handimg=(RoundImageView) findViewById(R.id.touxiang);
//		new PicTools(this).setImageViewBackground(handimg, "face");
	}
}
