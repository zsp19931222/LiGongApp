package com.example.app3.childview;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.app3.adapter.BaseListAdapter;
import com.example.app3.entity.PersonEntity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.net_utils.MultiPartStack;
import com.example.app3.net_utils.MultiPartStringRequest;
import com.example.app3.tool.GlideCircleTransform;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.Tool;
import com.example.app3.utils.GlideLoadUtils;
import com.example.smartclass.eventbus.MessageEvent;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import rx.Observer;
import yh.app.acticity.TargetDetailActivity;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.ToastUtils;
import 云华.智慧校园.工具.JsonReaderHelper;
import 云华.智慧校园.工具._链接地址导航;


public class HomeMyselfView implements View.OnClickListener {

    private ListView list;
    private View myselfView;
    private Activity activity;

    private TextView name;
    private TextView collage;
    private TextView major;
    private ImageView userHead;
    private ImageView sex;

    private LinearLayout myself_lin_head;

    private List<PersonEntity.ContentBean> content;
    private List<Map<String, String>> mapList;

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

    private ProgressDialog pd;
    private Bitmap photo;

    private static RequestQueue mSingleQueue;


    public HomeMyselfView(View myselfView, Activity activity) {
        this.myselfView = myselfView;
        this.activity = activity;
    }

    public void initView() {
        // TODO Auto-generated method stub
        list = myselfView.findViewById(R.id.myself_listview_list);
        name = myselfView.findViewById(R.id.myself_text_name);
        collage = myselfView.findViewById(R.id.myself_text_collage);
        major = myselfView.findViewById(R.id.myself_text_major);
        userHead = myselfView.findViewById(R.id.myself_image_userHead);
        sex = myselfView.findViewById(R.id.myself_iamge_sex);
        myself_lin_head = myselfView.findViewById(R.id.myself_lin_head);

        myself_lin_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        myself_lin_head.setOnClickListener(this);

        mapList = new ArrayList<>();

        mSingleQueue = Volley.newRequestQueue(activity, new MultiPartStack());
    }

    public void initData() {
        // TODO Auto-generated method stub
        mapList = new SqliteHelper().rawQuery("select * from getGrzxUrl");
        try {
            if ("2".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {//代表教师
                if (Constants.xxmc.equals("重庆第二师范学院") || Constants.xxmc.equals("南宁职业技术学院")) {
                    list.setAdapter(new BaseListAdapter(activity, list, new JSONArray(JsonReaderHelper.getJosn(activity, "meself.json"))));
                } else {
                    list.setAdapter(new BaseListAdapter(activity, list, new JSONArray(JsonReaderHelper.getJosn(activity, "meself2.json"))));
                }
            } else if ("1".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {//代表学生
                list.setAdapter(new BaseListAdapter(activity, list, new JSONArray(JsonReaderHelper.getJosn(activity, "meself_student.json"))));
            }
        } catch (Exception e) {
            try {
                list.setAdapter(new BaseListAdapter(activity, list, new JSONArray(JsonReaderHelper.getJosn(activity, "meself.json"))));
            } catch (JSONException e1) {
            }
        }
        getUserInfo(activity);
    }


    public void initAction() {
        // TODO Auto-generated method stub
        list.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent intent = null;
                JSONObject jso = null;
                String cls = view.getTag().toString();
                intent = new Intent(cls);
                if (!Tool.isFastDoubleClick()) {
                    try {
                        if ("1".equals(new SqliteHelper().rawQuery("select * from usertype").get(0).get("usertype"))) {
                            jso = new JSONArray(JsonReaderHelper.getJosn(activity, "meself_student.json")).getJSONObject(position);
                            if (cls.equals("com.example.app3.activity.BrowserActivity")) {
                                intent.putExtra("url", new SqliteHelper().rawQuery("select * from getGrzxUrl where name=?", jso.getString("txt")).get(0).get("url"));
                                intent.putExtra("title", jso.getString("txt"));
                            }
                        } else {
                            if (cls.equals("com.example.app3.activity.BrowserActivity")) {
                                if (Constants.xxmc.equals("重庆第二师范学院")) {
                                    intent.putExtra("url", "http://183.230.3.19:50045/YHADP2/page/collegeSitu/teacher_app/teacher_main_details2.jsp?gh=" + Constants.number);
                                } else if (Constants.xxmc.equals("南宁职业技术学院")) {
                                    intent.putExtra("url", "http://bigdata.ncvt.net:18080/YHADP2/page/collegeSitu/teacher_app/teacher_main_details.jsp?gh=" + Constants.number);
                                }
                            }
                        }
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        if (cls.equals("com.example.app3.activity.SettinActivity")) {
                            activity.startActivity(intent);
                        }
                    }
                }

            }
        });
    }

    public ListView getListView() {
        return list;
    }

    private List<Map<String, String>> applyList;

    /**
     * 获取本地用户信息
     */
    public void getUserInfo(Context context) {
        try {
            applyList = new SqliteHelper().rawQuery("select * from user");
            name.setText(applyList.get(0).get("name"));
            if ("1".equals(applyList.get(0).get("sex"))) {
                Glide.with(context).load(R.drawable.sex_male_2x)
                        .error(R.drawable.error).into(sex);
            } else {
                Glide.with(context).load(R.drawable.sex_famale_2x)
                        .error(R.drawable.error).into(sex);
            }
            collage.setText(applyList.get(0).get("bm"));
            major.setText(applyList.get(0).get("zy"));
//            Glide.with(context)
//                    .load(applyList.get(0).get("faceaddress"))
//                    .error(R.drawable.q1)
//                    .placeholder(R.drawable.q1)
//                    .transform(new GlideCircleTransform(activity))
//                    .into(userHead)
//            ;
            GlideLoadUtils.getInstance().glideLoadCircle(context, applyList.get(0).get("faceaddress"), userHead, R.drawable.q1);

        } catch (Exception e) {

        }
    }

    /**
     * 修改头像弹出框
     *
     * @param view
     */


    private void showUpdateHandPhoto(View view) {
        View window = LayoutInflater.from(activity).inflate(R.layout.popwind_update_handimg, null, false);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myself_lin_head:
                showUpdateHandPhoto(myself_lin_head);
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

        }
    }

    /**
     * 从系统相机
     */
    private void getCamera() {
        new RxPermissions(activity).
                request(Manifest.permission.CAMERA).
                subscribe(observer);
    }

    /**
     * 提示权限
     */
    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(activity, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri imageUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", new File(Environment.getExternalStorageDirectory(), "hand.jpg"));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                }
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hand.jpg")));
                activity.startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(activity, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /***
     * 相册选择
     */
    private void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, PHOTO_REQUEST);
    }

    public void setWindowDismiss() {
        if (handphotowindow.isShowing()) {
            handphotowindow.dismiss();
        }
    }

    private File file;

    /**
     * 上传图片到服务器
     */
    public void toUploadFile(Bitmap photos, File filepath) {
        this.photo = photos;
        file = filepath;
        pd = ProgressDialog.show(activity, "", "正在上传文件...");
        pd.show();
        Map<String, File> files = new HashMap<>();
        files.put("userface", filepath);
///storage/emulated/0/hand.jpg
        Map<String, String> params = new HashMap<>();
        params.put("userid", Constants.number);
        params.put("ticket", MD5.MD5(_链接地址导航.addString + Constants.number + Constants.code));

        String uri = _链接地址导航.UIA.修改资料.getUrl();
        addPutUploadFileRequest(uri, files, params, mResonseListenerString, mErrorListener, null);

    }

    Response.Listener<String> mResonseListenerString = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            String backlogJsonStrTmp = response.replace("\\", "");
            response = backlogJsonStrTmp.substring(1, backlogJsonStrTmp.length() - 1);
            try {
                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("boolean");
                if (success) {
                    ToastUtils.Toast(activity, HintTool.Change_Success);
                    Glide.with(activity)
                            .load("file:///" + "/storage/emulated/0/hand.jpg")
                            .error(R.drawable.q1)
                            .placeholder(R.drawable.q1)
                            .transform(new GlideCircleTransform(activity))
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(userHead);
                } else {
                    ToastUtils.Toast(activity, HintTool.Change_Fail);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                ToastUtils.Toast(activity, HintTool.Change_Fail);
            }
        }
    };

    Response.ErrorListener mErrorListener = new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (error != null) {
                if (error.networkResponse != null) {
                    ToastUtils.Toast(activity, HintTool.Change_Fail);
                }
            }
        }
    };

    public static void addPutUploadFileRequest(final String url,
                                               final Map<String, File> files, final Map<String, String> params,
                                               final Response.Listener<String> responseListener, final Response.ErrorListener errorListener,
                                               final Object tag) {
        if (null == url || null == responseListener) {
            return;
        }

        MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.POST, url, responseListener, errorListener) {

            @Override
            public Map<String, File> getFileUploads() {
                return files;
            }

            @Override
            public Map<String, String> getStringUploads() {
                return params;
            }

        };

        mSingleQueue.add(multiPartRequest);
    }
}