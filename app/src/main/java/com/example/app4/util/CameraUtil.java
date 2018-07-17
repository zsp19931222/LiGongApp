package com.example.app4.util;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.app3.HomePageActivity;
import com.example.app3.net_utils.MultiPartStack;
import com.example.app3.net_utils.MultiPartStringRequest;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app4.api.GetAppUrl;
import com.example.app4.entity.UserFaceUrlEntity;
import com.example.app4.entity.UserMessageEntity;
import com.example.app4.tool.UserMessageTool;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import yh.app.tool.MD5;
import yh.app.tool.SqliteHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.UploadUtil;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2018/4/20 0020.
 * 相机相关
 */

public class CameraUtil implements View.OnClickListener {


    /**
     * 修改头像弹出框
     *
     * @param view
     */
    private Context context;
    private Activity activity;

    private PopupWindow handphotowindow;
    private LinearLayout lyPopwindButton;
    private Button btnPopwindhandimPhotograph;
    private Button btnPopwindhandimAlbum;
    private Button btnPopwindhandimCancel;
    private RelativeLayout rlPopwindhandimOut;
    private static final int QQ_CODE = 2;

    // 相册
    public static final int PHOTO_REQUEST = 3;
    // 相机
    public static final int CAMERA_REQUEST = 4;
    // 裁剪图片
    public static final int PHOTO_CLIP = 5;
    public static final int PHONENUMBER_CODE = 1;
    private static RequestQueue mSingleQueue;
    private ProgressDialog pd;
    private View window;

    public CameraUtil(Context context) {
        this.context = context;
        activity = (Activity) context;
        mSingleQueue = Volley.newRequestQueue(activity, new MultiPartStack());
        window = LayoutInflater.from(activity).inflate(R.layout.popwind_update_handimg, null, false);
        // 设置窗体大小
        handphotowindow = new PopupWindow(window, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, true);
    }

    public void showUpdateHandPhoto(View view) {


        handphotowindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        handphotowindow.setBackgroundDrawable(new ColorDrawable());
        handphotowindow.setOutsideTouchable(false);
        handphotowindow.setFocusable(true);// 点击外部消失
        window.setFocusableInTouchMode(true);
        handphotowindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        lyPopwindButton = window.findViewById(R.id.ly_popwind_button);
        lyPopwindButton.setOnClickListener(this);
        btnPopwindhandimPhotograph = window.findViewById(R.id.btn_popwindhandim_photograph);
        btnPopwindhandimPhotograph.setOnClickListener(this);
        btnPopwindhandimAlbum = window.findViewById(R.id.btn_popwindhandim_album);
        btnPopwindhandimAlbum.setOnClickListener(this);
        btnPopwindhandimCancel = window.findViewById(R.id.btn_popwindhandim_cancel);
        btnPopwindhandimCancel.setOnClickListener(this);
        rlPopwindhandimOut = window.findViewById(R.id.rl_popwindhandim_out);
        rlPopwindhandimOut.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_popwindhandim_out:
                if (handphotowindow.isShowing()) {
                    handphotowindow.dismiss();
                }
                break;
            case R.id.btn_popwindhandim_cancel:
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
    private Observer<Boolean> observer = new Observer<Boolean>() {
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

    private void setWindowDismiss() {
        if (handphotowindow.isShowing()) {
            handphotowindow.dismiss();
        }
    }

    private File file;
    private Bitmap photo;

    /**
     * 上传图片到服务器
     */
    public void toUploadFile(Bitmap photos, File filepath) {
        this.photo = photos;
        file = filepath;
        pd = ProgressDialog.show(activity, "", "正在上传文件...");
        pd.show();
        Map<String, File> files = new HashMap<>();
        files.put("file", filepath);
///storage/emulated/0/hand.jpg
        Map<String, String> params = new HashMap<>();
        params.put("userid", UserMessageTool.getUserId());
        params.put("usertype", UserMessageTool.getType());
        params.put("ticket", MD5.MD5(_链接地址导航.addString + UserMessageTool.getUserId() + UserMessageTool.getPassWordUnencrypted()));

        String uri = _链接地址导航.UIA.修改资料.getUrl();
        addPutUploadFileRequest(GetAppUrl.uploadingFaceUrl, files, params, mResonseListenerString, mErrorListener, null);

    }

    private Response.Listener<String> mResonseListenerString = new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (JSONTool.Code(response).equals(JSONTool.SUCCESS)) {
                UserFaceUrlEntity userMessageEntity = GsonImpl.get().toObject(response, UserFaceUrlEntity.class);
                uploadingUserFaceUrl(userMessageEntity.getContent().getUrl());
            } else {
                EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.Change_Fail));
            }
        }
    };

    /**
     * 上传用户头像url
     */
    private void uploadingUserFaceUrl(final String url) {
        Map<String, String> map = new HashMap<>();
        map.put("userid", UserMessageTool.getUserId());
        map.put("usertype", UserMessageTool.getType());
        map.put("url", url);
        map.put("xxbh", UserMessageTool.getXXBH());
        map.put("mob", UserMessageTool.getPhone());
        VolleyRequest.RequestPost(GetAppUrl.Show.saveFaceUrl.getUrl(), map, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                setWindowDismiss();
                EventBus.getDefault().post(new MessageEvent(TagUtil.UploadingHeadImageTag, "file:///" + "/storage/emulated/0/hand.jpg"));
                new SqliteHelper().rawQuery("update userinfo4 set txdz=? where userid=?", url, UserMessageTool.getUserId());
            }

            @Override
            public void onMyError(VolleyError error) {
                EventBus.getDefault().post(new MessageEvent(HintTool.REQUESTFAIL, HintTool.Change_Fail));
            }
        });
    }

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {

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

    private static void addPutUploadFileRequest(final String url,
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

    private File filepath;

    /**
     * 获取写入权限
     */
    public void getWRITE_EXTERNAL_STORAGE() {
        new RxPermissions(activity).
                request(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                subscribe(WRITE_EXTERNAL_STORAGE_Observer);
    }

    /**
     * 提示权限
     */
    private Observer<Boolean> WRITE_EXTERNAL_STORAGE_Observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            pdDisMiss();
            ToastUtils.Toast(context, "获取权限失败");
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                try {
                    photo = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uritempFile));
//                    获得图片路径
                    filepath = UploadUtil.saveFile(photo, Environment.getExternalStorageDirectory().toString(), "hand.jpg");
                    // 上传照片
                    toUploadFile(photo, filepath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                pdDisMiss();
                ToastUtils.Toast(context, "获取权限失败");
            }
        }
    };

    /****
     * 调用系统自带切图工具对图片进行裁剪 微信也是
     *
     * @param uri
     */
    public Uri uritempFile;

    public void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
//        intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        activity.startActivityForResult(intent, PHOTO_CLIP);
    }

    private void pdDisMiss() {
        if (pd.isShowing()) {
            pd.dismiss();
        }
    }
}
