package yh.app.acticity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.androidpn.push.Constants;
import yh.app.model.StudentInfoModel;
import yh.app.stu_info.RoundImageView;
import yh.app.tool.SettingTools;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.tool.StringUtils;
import yh.app.tool.ToastShow;
import yh.app.uiengine.GeRenActivity;
import yh.app.utils.UploadUtil;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.ClearEditText;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.工具.PicTools;
import 云华.智慧校园.工具._链接地址导航;

public class UpdataUserInfoActivity extends IntegrationActivity implements OnClickListener {

	private LinearLayout rlUpdataHandimg;
	private RoundImageView imgUpdatauserinfoHandimg;
	private TextView txtUpdatauserinfoName;
	private TextView txtUpdatauserinfoUserid;
	private ClearEditText edUpdatauserinfoPhonenumber;
	private ClearEditText edUpdatauserinfoQq;
	private Button btn_updatainfo_ok;
	private StudentInfoModel studentInfoModel;
	private String qq = null;
	private String phonenumber = null;
	/**
	 * 对话框
	 */
	private PopupWindow handphotowindow;
	private LinearLayout lyPopwindButton;
	private Button btnPopwindhandimPhotograph;
	private Button btnPopwindhandimAlbum;
	private Button btnPopwindhandimCancel;
	private RelativeLayout rlPopwindhandimOut;
	private final int QQ_CODE = 2;

	// 相册
	private final int PHOTO_REQUEST = 3;
	// 相机
	private final int CAMERA_REQUEST = 4;
	// 裁剪图片
	private final int PHOTO_CLIP = 5;
	private final int PHONENUMBER_CODE = 1;

	// 标识是那个地方传过去的
	private final int USERINFO_CODE = 100;
	private ProgressDialog pd;
	private Bitmap photo;
	private File filepath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_updata_user_info);
		initView();
		getUserInfo();
	}

	private void initView() {
		rlUpdataHandimg = (LinearLayout) findViewById(R.id.rl_updata_handimg);
		rlUpdataHandimg.setOnClickListener(this);
		imgUpdatauserinfoHandimg = (RoundImageView) findViewById(R.id.img_updatauserinfo_handimg);
		txtUpdatauserinfoName = (TextView) findViewById(R.id.txt_updatauserinfo_name);
		txtUpdatauserinfoUserid = (TextView) findViewById(R.id.txt_updatauserinfo_userid);
		edUpdatauserinfoPhonenumber = (ClearEditText) findViewById(R.id.ed_updatauserinfo_phonenumber);
		edUpdatauserinfoQq = (ClearEditText) findViewById(R.id.ed_updatauserinfo_qq);
		btn_updatainfo_ok = (Button) findViewById(R.id.btn_updatainfo_ok);
		btn_updatainfo_ok.setOnClickListener(this);

		new PicTools(this).setImageViewBackground(imgUpdatauserinfoHandimg, "face");
		txtUpdatauserinfoName.setText(Constants.name);
		txtUpdatauserinfoUserid.setText(Constants.number);
	}

	/**
	 * 关闭当前
	 * 
	 * @param view
	 */
	public void back(View view) {

		finish();

	}

	/**
	 * 修改头像弹出框
	 * 
	 * @param view
	 */
	private void showUpdateHandPhoto(View view) {
		View window = LayoutInflater.from(this).inflate(R.layout.popwind_update_handimg, null, false);

		// 设置窗体大小
		handphotowindow = new PopupWindow(window, ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, true);

		handphotowindow.setAnimationStyle(android.R.style.Animation_InputMethod);
		handphotowindow.setBackgroundDrawable(new ColorDrawable());
		handphotowindow.setOutsideTouchable(false);
		handphotowindow.setFocusable(true);// 点击外部消失
		window.setFocusableInTouchMode(true);
		handphotowindow.showAtLocation(view, Gravity.CENTER, 0, 0);

		lyPopwindButton = (LinearLayout) window.findViewById(R.id.ly_popwind_button);
		lyPopwindButton.setOnClickListener(this);
		btnPopwindhandimPhotograph = (Button) window.findViewById(R.id.btn_popwindhandim_photograph);
		btnPopwindhandimPhotograph.setOnClickListener(this);
		btnPopwindhandimAlbum = (Button) window.findViewById(R.id.btn_popwindhandim_album);
		btnPopwindhandimAlbum.setOnClickListener(this);
		btnPopwindhandimCancel = (Button) window.findViewById(R.id.btn_popwindhandim_cancel);
		btnPopwindhandimCancel.setOnClickListener(this);
		rlPopwindhandimOut = (RelativeLayout) window.findViewById(R.id.rl_popwindhandim_out);
		rlPopwindhandimOut.setOnClickListener(this);

	}

	/**
	 * 获取用户信息
	 */
	private void getUserInfo() {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getuserinfo.getUrl(), map, new VolleyInterface() {
			@Override
			public void onMySuccess(String result) {
				// System.out.print(result);
				// 成功
				if (!TextUtils.isEmpty(result)) {
					String code = null;
					try {
						JSONObject jsonObject = new JSONObject(result);
						code = jsonObject.getString("code");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (code.equals(Constants.NETWORK_REQUEST_SUCCESS)) {
						studentInfoModel = ExampleApplication.getInstance().getGson().fromJson(result,
								StudentInfoModel.class);
						Constants.ticket = studentInfoModel.getContent().getTicket();
						bindData(studentInfoModel.getContent().getMessage());
					}

				}

			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void bindData(StudentInfoModel.ContentBean.MessageBean messageBean) {
		if (messageBean != null) {
			// 用户头像
//			Glide.with(this).load(messageBean.getFaceaddress()).error(R.drawable.error).placeholder(R.drawable.ico_load_little).into(imgUpdatauserinfoHandimg);
			GlideLoadUtils.getInstance().glideLoad(this,messageBean.getFaceaddress(),imgUpdatauserinfoHandimg,R.drawable.ico_load_little);

			if (!TextUtils.isEmpty(Constants.name)) {
				txtUpdatauserinfoName.setText(Constants.name);// 昵称
			} else {
				txtUpdatauserinfoName.setText(messageBean.getNc());
			}

			if (TextUtils.isEmpty(messageBean.getLxdh())) {
				edUpdatauserinfoPhonenumber.setText(getDate("telphone"));
			} else {
				edUpdatauserinfoPhonenumber.setText(messageBean.getLxdh());// 电话
			}
			if (TextUtils.isEmpty(messageBean.getQq())) {
				edUpdatauserinfoQq.setText(getDate("qq"));
			} else {
				edUpdatauserinfoQq.setText(messageBean.getQq());// qq
			}
		}

	}

	private String getDate(String zd) {
		String date = "";
		try {
			SQLiteDatabase db = null;
			Cursor c = null;
			try {
				db = new SqliteHelper().getRead();
				c = db.rawQuery("select " + zd + " from user where userid='" + Constants.number + "'", null);
				while (c.moveToNext()) {
					if (zd.equals("birthday")) {
						date = new SimpleDateFormat("yyyy年MM月dd日")
								.format(new SimpleDateFormat("yyyyMMdd").parse(c.getString(0).toString()));
						String array[] = c.getString(0).toString().split("/");
						String n = array[0];
						String y = array[1];
						String r = array[2];
						date = n + "年" + y + "月" + r + "日";
					} else
						date = c.getString(0).toString();
				}
			} catch (Exception e) {
			} finally {
				try {
					new SqliteDBCLose(db, c).close();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (date == null || date.equals("-")) {
				date = "";
			}
		} catch (Exception e) {
			date = "-";
		}
		return date;
	}

	/**
	 * 提交修改电话号码 提交数据
	 */
	private void submitPhoneNumber(final String phonenumber) {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		map.put("phonenumber", phonenumber);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.updataPhoneNumber.getUrl(), map, new VolleyInterface() {
			@Override
			public void onMySuccess(String result) {
				// 成功
				Intent intentphonenumber = new Intent();
				intentphonenumber.putExtra("phonenumber", phonenumber);
				setResult(PHONENUMBER_CODE, intentphonenumber);
			}

			@Override
			public void onMyError(VolleyError error) {
				// 失败
				Toast.makeText(UpdataUserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();

			}
		});
	}

	/**
	 * 提交修改QQ 提交数据
	 */
	private void submitQQ(final String qq) {
		Map<String, String> map = new HashMap<>();
		map.put("userid", Constants.number);
		map.put("ticket", Constants.ticket);
		map.put("QQ", qq);
		VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.updataQQ.getUrl(), map, new VolleyInterface() {
			@Override
			public void onMySuccess(String result) {
				// 成功
				Intent intentqq = new Intent();
				intentqq.putExtra("qq", qq);
				setResult(QQ_CODE, intentqq);
				Toast.makeText(UpdataUserInfoActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onMyError(VolleyError error) {
				// 失败
				Toast.makeText(UpdataUserInfoActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.rl_updata_handimg:
			showUpdateHandPhoto(rlUpdataHandimg);
			break;
		case R.id.rl_popwindhandim_out:
			if (handphotowindow.isShowing()) {
				handphotowindow.dismiss();
			}
			break;

		case R.id.btn_popwindhandim_cancel:
			// 取消
			// 取消
			if (handphotowindow.isShowing()) {
				handphotowindow.dismiss();
			}
			break;

		case R.id.btn_popwindhandim_photograph:
			getCamera();
			break;
		case R.id.btn_popwindhandim_album:
			// 相册
			getPicFromPhoto();
			break;

		case R.id.btn_updatainfo_ok:
			phonenumber = edUpdatauserinfoPhonenumber.getText().toString();
			qq = edUpdatauserinfoQq.getText().toString();
			
			if (StringUtils.isPhoneNumber(phonenumber)){
                submitPhoneNumber(phonenumber);
            }else {
                //晃动窗体
            	edUpdatauserinfoPhonenumber.setShakeAnimation();
            }
			if(!TextUtils.isEmpty(qq)){
				submitQQ(qq);	
			}

			
			break;

		}
	}

	/***
	 * 相册选择
	 */
	private void getPicFromPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		startActivityForResult(intent, PHOTO_REQUEST);
	}

	/**
	 * 从系统相机选择图片来源
	 */
	private void getCamera() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 下面这句指定调用相机拍照后的照片存储的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hand.jpg")));
		startActivityForResult(intent, CAMERA_REQUEST);
	}

	/****
	 * 调用系统自带切图工具对图片进行裁剪 微信也是
	 *
	 * @param uri
	 */
	private void photoClip(Uri uri) {
		// 调用系统中自带的图片剪裁
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, PHOTO_CLIP);
	}

	/**
	 * 上传图片到服务器
	 */
	private void toUploadFile(Bitmap photos) {
		pd = ProgressDialog.show(this, "", "正在上传文件...");
		pd.show();

		Intent data = new Intent();
		data.putExtra("qq", edUpdatauserinfoQq.getText().toString());
		data.putExtra("dh", edUpdatauserinfoPhonenumber.getText().toString());
		data.putExtra("nc", "");
		Bitmap t_bitmap = photos;
		data.putExtra("img", t_bitmap);

		new SettingTools(filepath, handler, UpdataUserInfoActivity.this).executeOnExecutor(
				Executors.newCachedThreadPool(), "", edUpdatauserinfoPhonenumber.getText().toString(),
				edUpdatauserinfoQq.getText().toString());

	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			try {
				if (new JSONObject(msg.getData().getString("msg")).getBoolean("boolean")) {
					new ToastShow().show(UpdataUserInfoActivity.this, "修改成功");
					if (pd.isShowing()) {
						pd.dismiss();
					}

					Intent data = new Intent();
					data.putExtra("qq", edUpdatauserinfoQq.getText().toString());
					data.putExtra("dh", edUpdatauserinfoPhonenumber.getText().toString());
					data.putExtra("nc", "");
					Bitmap t_bitmap = photo;
					data.putExtra("img", t_bitmap);
					new SqliteHelper().execSQL("update user set qq=?,telphone=?,nc=? where userid=?",
							new Object[] { edUpdatauserinfoQq.getText().toString(),
									edUpdatauserinfoPhonenumber.getText().toString(), "", Constants.number });
					GeRenActivity.resultData(data);
					imgUpdatauserinfoHandimg.setImageBitmap(photo);
					// finish();
				} else
					new ToastShow().show(UpdataUserInfoActivity.this, "修改失败");
			} catch (JSONException e) {
			}

		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		// 相机
		case CAMERA_REQUEST:
			switch (resultCode) {
			case -1:// -1表示拍照成功
				File file = new File(Environment.getExternalStorageDirectory() + "/hand.jpg");// 保存图片
				if (file.exists()) {
					// 对相机拍照照片进行裁剪
					photoClip(Uri.fromFile(file));
				}
			}
			break;

		case PHOTO_REQUEST:// 从相册取
			if (data != null) {
				Uri uri = data.getData();
				// 对相册取出照片进行裁剪
				photoClip(uri);

			}
			break;
		// 裁剪图片
		case PHOTO_CLIP:
			// 完成
			if (data != null) {

				Bundle extras = data.getExtras();
				if (extras != null) {
					photo = extras.getParcelable("data");
					try {
						// 获得图片路径
						filepath = UploadUtil.saveFile(photo, Environment.getExternalStorageDirectory().toString(),
								"hand.jpg");

						// 上传照片
						toUploadFile(photo);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// 上传完成将照片写入imageview与用户进行交互
					imgUpdatauserinfoHandimg.setImageBitmap(photo);
					if (handphotowindow.isShowing()) {
						handphotowindow.dismiss();
					}
				}
			}
			break;
		}
	}
}
