package yh.app.uiengine;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.bumptech.glide.ListPreloader.PreloadSizeProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.MD5;import com.yhkj.cqgyxy.R;
import yh.app.tool.SettingTools;
import yh.app.tool.SqliteHelper;
import yh.app.tool.StringUtils;
import yh.app.tool.ToastShow;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.FileTools;
import 云华.智慧校园.工具._链接地址导航;

/**
 * 修改个人资料
 * 
 * @author anmin
 *
 */
@SuppressWarnings("unused")
public class ChangeSetting extends ActivityPortrait {
	private File file = null;
	public EditText nc, dh, qq;
	private Context context;
	String str_nc, str_dh, str_qq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_edit);
		context = this;
		initTopbar();
		initView();
		initAction();
	}

	private void initAction() {
		// 编辑头像
		findViewById(R.id.img).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, 1);
			}
		});
		findViewById(R.id.tj).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				str_nc = nc.getText().toString();
				str_dh = dh.getText().toString();
				str_qq = qq.getText().toString();
				if (!StringUtils.isPhoneNumber(str_dh)) {
					new ToastShow().show(context, "电话格式错误");
					return;
				}
				
				new SettingTools(FileTools.saveFile(tx_bitmap, Constants.number + ".png", "tx"), handler, context)
						.executeOnExecutor(Executors.newCachedThreadPool(), nc.getText().toString(),
								dh.getText().toString(), qq.getText().toString());
			}
		});
	}

	public Handler handler = new Handler()
	{
		@Override
		public void handleMessage(android.os.Message msg)
		{
			try
			{
				if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean"))
				{
					new ToastShow().show(context, "修改成功");
					Intent data = new Intent();
					data.putExtra("qq", qq.getText().toString());
					data.putExtra("dh", dh.getText().toString());
					data.putExtra("nc", nc.getText().toString());
					// update user set qq=?,nc=?,telphone=? where userid=?
					Bitmap t_bitmap = tx_bitmap;
					data.putExtra("img", t_bitmap);
					new SqliteHelper().execSQL("update user set qq=?,telphone=?,nc=? where userid=?", new Object[]
					{
							qq.getText().toString(), dh.getText().toString(), nc.getText().toString(), Constants.number
					});
					HomeSetting.resultData(data);
					finish();
				} else
					new ToastShow().show(context, "修改失败");
			} catch (JSONException e)
			{
			}

		};
	};

	public String getListMapString(List<Map<String, String>> list, String name) {
		try {
			return list.get(0).get(name);
		} catch (Exception e) {
			return "";
		}
	}

	private ImageView tx;

	private void initView() {
		List<Map<String, String>> list = new SqliteHelper()
				.rawQuery("select nc,qq,telphone from user where userid='" + Constants.number + "'");
		nc = ((EditText) findViewById(R.id.nc));
		dh = ((EditText) findViewById(R.id.dh));
		qq = ((EditText) findViewById(R.id.qq));

		dh.setText(getListMapString(list, "telphone"));
		nc.setText(getListMapString(list, "nc"));
		qq.setText(getListMapString(list, "qq"));
		tx = ((ImageView) findViewById(R.id.img));

	}

	private void initTopbar() {
		new DefaultTopBar(this).doit("编辑资料");
	}

	private Bitmap tx_bitmap;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 1 && resultCode == RESULT_OK) {
			Uri mUri = data.getData();
			if (null == mUri)
				return;
			Intent intent = new Intent();
			intent.setAction("com.android.camera.action.CROP");
			intent.setDataAndType(mUri, "image/*");// mUri是已经选择的图片Uri
			intent.putExtra("crop", "true");
			intent.putExtra("aspectX", 1);// 裁剪框比例
			intent.putExtra("aspectY", 1);
			intent.putExtra("outputX", 150);// 输出图片大小
			intent.putExtra("outputY", 150);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, 0);
		} else if (requestCode == 0 && resultCode == RESULT_OK) {

			try {
				tx_bitmap = (Bitmap) data.getParcelableExtra("data");
				tx.setImageBitmap(tx_bitmap);
			}

			catch (Exception e) {
				new ToastShow().show(this, "发生内部错误，请重试");
			}
		}

	}
}
