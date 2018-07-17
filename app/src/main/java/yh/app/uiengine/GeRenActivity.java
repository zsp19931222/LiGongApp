package yh.app.uiengine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.utils.GlideLoadUtils;
import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import yh.app.acticity.Set_Activity;
import yh.app.acticity.UserInfoActivity;
import yh.app.model.StudentInfoModel;
import yh.app.stu_info.RoundImageView;
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.PicTools;
import 云华.智慧校园.工具._链接地址导航;

public class GeRenActivity implements OnClickListener {
    private View mainView;
    /**
     * 个人资料
     */
    private static RoundImageView img_personal_head;// 头像
    private static TextView txt_personal_username;// 昵称
    private TextView txt_personal_userId;// 用户id
    private RelativeLayout rl_personal_info;
    // 我的分享
    private RelativeLayout rl_personal_share;
    // 数据报告
    private RelativeLayout rl_personal_shuubaogao;
    // 设置
    private RelativeLayout rl_persona_set;

    private Activity activity;
    private String url = null;
    private String share_url = null;// 我的分享
    private StudentInfoModel studentInfoModel;

    private ImageView img_peronal_set;

    /**
     * @param context
     */

    public GeRenActivity(Activity context) {
        this.activity = context;
        mainView = LayoutInflater.from(context).inflate(R.layout.activity_gerenzhonxin, null, false);
        initView();
        getTicket();
        initWebView();
    }

    public View getMainView() {
        return mainView;
    }

    @SuppressWarnings("unused")
    private void initView() {
        Constants.name = getDate("name");

        img_personal_head = (RoundImageView) mainView.findViewById(R.id.img_personal_head);
        txt_personal_userId = (TextView) mainView.findViewById(R.id.txt_personal_userId);
        txt_personal_username = (TextView) mainView.findViewById(R.id.txt_personal_username);
        img_peronal_set = (ImageView) mainView.findViewById(R.id.img_peronal_set);
        img_peronal_set.setOnClickListener(this);


        txt_personal_username.setText(Constants.name);// 昵称
        txt_personal_userId.setText("我的账号:" + Constants.number);
        new PicTools(activity).setImageViewBackground(img_personal_head, "face");

        rl_persona_set = (RelativeLayout) mainView.findViewById(R.id.rl_persona_set);
        rl_persona_set.setOnClickListener(this);

        rl_personal_info = (RelativeLayout) mainView.findViewById(R.id.rl_personal_info);
        rl_personal_info.setOnClickListener(this);

        rl_personal_share = (RelativeLayout) mainView.findViewById(R.id.rl_personal_share);
        rl_personal_share.setOnClickListener(this);

        rl_personal_shuubaogao = (RelativeLayout) mainView.findViewById(R.id.rl_personal_shuubaogao);
        rl_personal_shuubaogao.setOnClickListener(this);
        if (true || Constants.user_type.equals("2")) {
            //教师账号隐藏
            rl_personal_shuubaogao.setVisibility(View.GONE);
        } else {
            rl_personal_shuubaogao.setVisibility(View.VISIBLE);
        }

    }

    @SuppressLint("SimpleDateFormat")
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
     * 获得票据
     */
    private void getTicket() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.appticketurl.getUrl(), map, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                System.out.print(result);
                initData();
            }

            @Override
            public void onMyError(VolleyError error) {
            }
        });
    }

    /**
     * 加载用户信息
     */
    private void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        map.put("ticket", Constants.ticket);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.getuserinfo.getUrl(), map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {

                // 成功
                if (!TextUtils.isEmpty(result)) {
                    String code = null;
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        code = jsonObject.getString("code");
//						Constants.ticket = jsonObject.getJSONObject("content").getString("ticket");
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    studentInfoModel = ExampleApplication.getInstance().getGson().fromJson(result,
                            StudentInfoModel.class);
                    Constants.ticket = studentInfoModel.getContent().getTicket();
                    bindData(studentInfoModel.getContent().getMessage());


                }

            }

            @Override
            public void onMyError(VolleyError error) {
                // 失败
                System.out.println(error);
            }
        });
    }

    /**
     * 加载数据报告
     */
    private void initWebView() {
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        VolleyRequest.RequestPost(_链接地址导航.WDDXSERVER.webticketurl.getUrl(), params, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String web_ticket = jsonObject.getString("ticket");
                    if (null != web_ticket) {
                        // 数据报告
                        url = _链接地址导航.WDDXSERVER.weburl_shujubaogao.getUrl() + Constants.number + "&ticket="
                                + web_ticket;
                        // 我的风险
                        share_url = _链接地址导航.WDDXSERVER.mubiaoguangchang.getUrl() + Constants.number + "&ticket="
                                + web_ticket;

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }

        });
    }

    public static void resultData(Intent data) {
        try {

            txt_personal_username.setText(data.getStringExtra("nc"));
            img_personal_head.setImageBitmap((Bitmap) data.getParcelableExtra("img"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 绑定数据 给控件赋值
     */
    private void bindData(StudentInfoModel.ContentBean.MessageBean messageBean) {
        if (null != messageBean.getFaceaddress()) {
            // 用户头像

            GlideLoadUtils.getInstance().glideLoad(activity, messageBean.getFaceaddress(),img_personal_head,R.drawable.ico_load_little);

        }

        txt_personal_username.setText(Constants.name);// 昵称
        txt_personal_userId.setText("我的账号:" + Constants.number);
        new PicTools(activity).setImageViewBackground(img_personal_head, "face");

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.img_peronal_set:
                Intent intentset = new Intent(activity, Set_Activity.class);
                activity.startActivity(intentset);
//			Intent intent=new Intent(activity,DemoActivity.class);
//			activity.startActivity(intent);
                break;
            case R.id.rl_personal_info:
//			 Intent intentgetrenzhongxi = new Intent(activity,
//			 GeRenZhongxinXiangqing.class);
//			 activity.startActivity(intentgetrenzhongxi);

                Intent intentgetrenzhongxi = new Intent(activity, UserInfoActivity.class);
                activity.startActivity(intentgetrenzhongxi);
                break;
            case R.id.rl_personal_shuubaogao:
                if (null != url) {
                    Intent intentweb = new Intent(activity, BrowserActivity.class);
                    intentweb.putExtra("url", url);
                    activity.startActivity(intentweb);
                }

                break;
            case R.id.rl_personal_share:
                if (null != share_url) {
                    Intent intentshare = new Intent(activity, BrowserActivity.class);
                    intentshare.putExtra("url", share_url);
                    activity.startActivity(intentshare);
                }

                break;

            default:
                break;
        }
    }


}
