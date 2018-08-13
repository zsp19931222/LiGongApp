package yh.app.acticity;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidpn.push.Constants;

import yh.app.model.StudentInfoModel;
import yh.app.stu_info.RoundImageView;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.uiengine.QRCode;
import yh.app.utils.UploadUtil.OnUploadProcessListener;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.IntegrationActivity;
import 云华.智慧校园.功能.LoginOut;
import 云华.智慧校园.工具.PicTools;
import 云华.智慧校园.工具._链接地址导航;

public class UserInfoActivity extends IntegrationActivity implements OnClickListener, OnUploadProcessListener {
    RelativeLayout lyPersonaTitle;
    RelativeLayout rlUserinfoHandimg;
    RelativeLayout rlUserinfoNickname;
    RelativeLayout rlUserinfoPhone;
    RelativeLayout rlUserinfoQq;
    RelativeLayout rlUserinfoErweima;
    Button btnPersonaOut;
    TextView txtUserinfoNickname;
    TextView txtUserinfoSection;
    TextView txtUserinfoMajor;
    TextView txtUserinfoPhone;
    TextView txtUserinfoQq;
    TextView txtUserinfoBirthday;
    RoundImageView imgUserinfoUserphoto;

    private StudentInfoModel studentInfoModel;
    /**
     * 记录是修改的那个模块 用于接收返回值
     */
    // 昵称
    private final int RESULT_NICKNAME = 0;
    // 电话号码
    private final int RESULT_PHONENUMBER = 1;
    // QQ
    private final int RESULT_QQ = 2;
    // 相册
    private final int PHOTO_REQUEST = 3;
    // 相机
    private final int CAMERA_REQUEST = 4;
    // 裁剪图片
    private final int PHOTO_CLIP = 5;

    // 标识是那个地方传过去的
    private final int USERINFO_CODE = 100;

    private LinearLayout ly_main;


    private String nickename = null, phonenumber = null, qq = null;


    private TextView txt_username;
    private TextView txt_userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        getUserInfo();
    }

    // 退出当前
    public void back(View view) {
        finish();
    }

    private void initView() {
        // 主布局
        ly_main = (LinearLayout) findViewById(R.id.ly_main);

        // 昵称
        lyPersonaTitle = (RelativeLayout) findViewById(R.id.ly_persona_title);
        lyPersonaTitle.setOnClickListener(this);
        // 头像编辑
        rlUserinfoHandimg = (RelativeLayout) findViewById(R.id.rl_userinfo_handimg);
        rlUserinfoHandimg.setOnClickListener(this);
        // 修改昵称
        rlUserinfoNickname = (RelativeLayout) findViewById(R.id.rl_userinfo_nickname);
        // rlUserinfoNickname.setOnClickListener(this);
        // 修改电话号码
        rlUserinfoPhone = (RelativeLayout) findViewById(R.id.rl_userinfo_phone);
//		rlUserinfoPhone.setOnClickListener(this);
        // 修改qq
        rlUserinfoQq = (RelativeLayout) findViewById(R.id.rl_userinfo_qq);
//		rlUserinfoQq.setOnClickListener(this);
        // 二维码
        rlUserinfoErweima = (RelativeLayout) findViewById(R.id.rl_userinfo_erweima);
        rlUserinfoErweima.setOnClickListener(this);

        // 退出登录
        btnPersonaOut = (Button) findViewById(R.id.btn_persona_out);
        btnPersonaOut.setOnClickListener(this);

        txtUserinfoNickname = (TextView) findViewById(R.id.txt_userinfo_nickname);
        txtUserinfoBirthday = (TextView) findViewById(R.id.txt_userinfo_birthday);
        txtUserinfoMajor = (TextView) findViewById(R.id.txt_userinfo_major);
        txtUserinfoPhone = (TextView) findViewById(R.id.txt_userinfo_phone);
        txtUserinfoQq = (TextView) findViewById(R.id.txt_userinfo_qq);
        txtUserinfoSection = (TextView) findViewById(R.id.txt_userinfo_section);
        imgUserinfoUserphoto = (RoundImageView) findViewById(R.id.img_userinfo_userphoto);

        txt_userid = (TextView) findViewById(R.id.txt_userid);
        txt_username = (TextView) findViewById(R.id.txt_username);


        new PicTools(this).setImageViewBackground(imgUserinfoUserphoto, "face");
        txtUserinfoNickname.setText(Constants.name);// 昵称
        txt_username.setText(Constants.name);
        txt_userid.setText(Constants.number);
        txtUserinfoBirthday.setText(getDate("birthday"));
        txtUserinfoMajor.setText(getDate("zy"));
        txtUserinfoPhone.setText(getDate("telphone"));
        txtUserinfoQq.setText(getDate("qq"));
        txtUserinfoSection.setText(getDate("bm"));

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

    /**
     * 绑定数据 给控件赋值
     */
    String s;

    private void bindData(StudentInfoModel.ContentBean.MessageBean messageBean) {
        if (messageBean != null) {
            s = messageBean.getFaceaddress();
            // 用户头像
            GlideLoadUtils.getInstance().glideLoad(this, messageBean.getFaceaddress(),imgUserinfoUserphoto,R.drawable.q1);
            if (!TextUtils.isEmpty(Constants.name)) {
                txtUserinfoNickname.setText(Constants.name);// 昵称
            } else {
                txtUserinfoNickname.setText(messageBean.getNc());
            }

            if (TextUtils.isEmpty(messageBean.getZymc())) {
                txtUserinfoMajor.setText(getDate("zy"));
            } else {
                txtUserinfoMajor.setText(messageBean.getZymc());// 专业
            }

            if (TextUtils.isEmpty(messageBean.getSr())) {
                txtUserinfoBirthday.setText(getDate("birthday"));
            } else {
                txtUserinfoBirthday.setText(messageBean.getSr());// 生日
            }

            if (TextUtils.isEmpty(messageBean.getXy())) {
                txtUserinfoSection.setText(getDate("bm"));// 学校部门
            } else {
                txtUserinfoSection.setText(messageBean.getXy());// 学校
            }

            if (TextUtils.isEmpty(messageBean.getLxdh())) {
                txtUserinfoPhone.setText(getDate("telphone"));
            } else {
                txtUserinfoPhone.setText(messageBean.getLxdh());// 电话
            }
            if (TextUtils.isEmpty(messageBean.getQq())) {
                txtUserinfoQq.setText(getDate("qq"));
            } else {
                txtUserinfoQq.setText(messageBean.getQq());// qq
            }

            new SqliteHelper().execSQL("update user set qq=?,telphone=?,nc=? where userid=?",
                    new Object[]{txtUserinfoQq.getText().toString(), txtUserinfoPhone.getText().toString(),
                            txtUserinfoNickname.getText().toString(), Constants.number});
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ly_persona_title:
                break;
            case R.id.rl_userinfo_handimg:
                Intent intentupdatauserinfo = new Intent(this, UpdataUserInfoActivity.class);
                startActivity(intentupdatauserinfo);
                break;
            case R.id.rl_userinfo_nickname:
                // 修改昵称
                nickename = txtUserinfoNickname.getText().toString();
                Intent updatenickeintent = new Intent(this, UpdateNickNameActivity.class);
                updatenickeintent.putExtra("nickename", nickename);
                startActivityForResult(updatenickeintent, USERINFO_CODE);
                break;
            case R.id.rl_userinfo_phone:
                // 修改电话号码
                phonenumber = txtUserinfoPhone.getText().toString();
                Intent updatephoneintent = new Intent(this, UpdatePhoneActivity.class);
                updatephoneintent.putExtra("phonenumber", phonenumber);
                startActivityForResult(updatephoneintent, USERINFO_CODE);

                break;
            case R.id.rl_userinfo_qq:
                // 修改QQ号
                qq = txtUserinfoQq.getText().toString();
                Intent updateqqinintent = new Intent(this, UpdateQQActivity.class);
                updateqqinintent.putExtra("qq", qq);
                startActivityForResult(updateqqinintent, USERINFO_CODE);
                break;
            case R.id.rl_userinfo_erweima:
                // 跳转到二维码页面
                Intent erweimaintent = new Intent(this, QRCode.class);
                startActivity(erweimaintent);
                break;
            case R.id.btn_persona_out:
                // 退出登陆
                new LoginOut().doLoginOut(this);
                break;


        }
    }


//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		// 判断是修改头像还是修改其他内容
//		if (requestCode == USERINFO_CODE) {
//			switch (resultCode) {
//			case RESULT_NICKNAME:
//				if (null != data) {
//					String nickename = data.getStringExtra("nickename");
//					txtUserinfoNickname.setText(nickename);
//				}
//
//				break;
//			case RESULT_PHONENUMBER:
//				if (null != data) {
//					String phonenumber = data.getStringExtra("phonenumber");
//					txtUserinfoPhone.setText(phonenumber);
//				}
//
//				break;
//			case RESULT_QQ:
//				if (null != data) {
//					String qq = data.getStringExtra("qq");
//					txtUserinfoQq.setText(qq);
//				}
//
//				new SqliteHelper()
//						.execSQL("update user set qq=?,telphone=?,nc=? where userid=?",
//								new Object[] { txtUserinfoQq.getText().toString(),
//										txtUserinfoPhone.getText().toString(), txtUserinfoNickname.getText().toString(),
//										Constants.number });
//				break;
//			}
//		} else {
//			
//		}
//	}

    @Override
    public void onUploadDone(int responseCode, String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUploadProcess(int uploadSize) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initUpload(int fileSize) {
        // TODO Auto-generated method stub

    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        getUserInfo();
        super.onResume();
    }

}
