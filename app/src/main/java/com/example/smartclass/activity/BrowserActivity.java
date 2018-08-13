package com.example.smartclass.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.app3.MJavascriptInterface;
import com.example.app3.childview.DownloadPopupWindow;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app3.view.MyTitleView;
import com.example.app3.web.utils.X5WebView;
import com.example.app4.base.CompatStatusBarActivity;
import com.example.app4.util.IsNull;
import com.example.app4.util.IsNullUtil;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;

import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import yh.app.activitytool.ActivityPortrait;
import yh.app.logTool.Log;
import yh.app.tool.QRCodeHelper;
import 云华.智慧校园.功能.LoginOut;
import 云华.智慧校园.工具.CodeManage;

public class BrowserActivity extends CompatStatusBarActivity implements OnClickListener {
    /**
     * 作为一个浏览器的示例展示出来，采用android+web的模式
     */
    private X5WebView mWebView;
    private ViewGroup mViewParent;
    private ImageButton mBack;
    private ImageButton mForward;
    private ImageButton mExit;
    private ImageButton mHome;
    private ImageButton mMore;
    private MyTitleView myTitleView;
    /**
     * 对话框
     */
    private PopupWindow handphotowindow;
    private LinearLayout lyPopwindButton;
    private Button btnPopwindhandimPhotograph;
    private Button btnPopwindhandimAlbum;
    private Button btnPopwindhandimCancel;
    private RelativeLayout rlPopwindhandimOut;
    private WebChromeClient.FileChooserParams mFileChooserParams;
    private ValueCallback<Uri[]> mUploadMsgFive;
    private String mAcceptType, VERSION_TAG;
    private final int QQ_CODE = 2;

    // 相册
    private final int PHOTO_REQUEST = 3;
    // 相机
    private final int CAMERA_REQUEST = 4;
    // 裁剪图片
    private final int PHOTO_CLIP = 5;
    private final int PHONENUMBER_CODE = 1;
    private Bitmap photo;
    private File filepath;
//    private Button mGo;
//    private EditText mUrl;

    private String mHomeUrl;
    private static final String TAG = "SdkDemo";
    private static final int MAX_LENGTH = 14;
    private boolean mNeedTestPage = false;

    private final int disable = 120;
    private final int enable = 255;

    private ProgressBar mPageLoadingProgressBar = null;

    private ValueCallback<Uri> uploadFile;

    private URL mIntentUrl;

    private Uri imageUri;
//    private SerializableMapUtil serializableMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setImmersiveStatusBar(true, getResources().getColor(R.color.white));

        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
//        serializableMap = (SerializableMapUtil) bundle.get("map");
        mHomeUrl = bundle.getString("url");
//        mHomeUrl = "http://192.168.0.103:8080/yxxt/teacher.html?#/home";

        Log.d("zsp", mHomeUrl);
        if (intent != null) {
            try {
                mIntentUrl = new URL(intent.getData().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {

            } catch (Exception e) {
            }
        }
        //
        try {
            if (Integer.parseInt(Build.VERSION.SDK) >= 11) {
                getWindow()
                        .setFlags(
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                                android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
            }
        } catch (Exception e) {
        }

		/*
         * getWindow().addFlags(
		 * android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 */
        setContentView(R.layout.activity_browser);
        mViewParent = (ViewGroup) findViewById(R.id.webView1);

        initBtnListenser();

        mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);

    }

    private void changGoForwardButton(WebView view) {
        if (view.canGoBack())
            mBack.setAlpha(enable);
        else
            mBack.setAlpha(disable);
        if (view.canGoForward())
            mForward.setAlpha(enable);
        else
            mForward.setAlpha(disable);
        if (view.getUrl() != null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
            mHome.setAlpha(disable);
            mHome.setEnabled(false);
        } else {
            mHome.setAlpha(enable);
            mHome.setEnabled(true);
        }
    }

    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        // ProgressBar(getApplicationContext(),
        // null,
        // android.R.attr.progressBarStyleHorizontal);
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.color_progressbar));
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {

        mWebView = new X5WebView(this, null);

        mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT));

        initProgressBar();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url);
                if (uri.getScheme().equals("jsAndroid")) {
                    if (uri.getAuthority().equals("scan")) {
                        android.util.Log.d("zsp", "shouldOverrideUrlLoading: js调用了Android的方法");
                    }
                }
                // 关闭页面
                if (url.equals("http://www.close.com/")) {
                    finish();
                    return true;
                }
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return true;
                }
                if (url.startsWith("map:")) {
                    // 地图webAPI
                } else if (url.startsWith("yhqrcode://")) {
                    QRCodeHelper helper = new QRCodeHelper();
                    helper.scanQRCode(BrowserActivity.this);
                    return true;
                } else {
                    mWebView.loadUrl(url);
                }
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
                mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
                if (Integer.parseInt(Build.VERSION.SDK) >= 16)
                    changGoForwardButton(view);
                /* mWebView.showLog("test Log"); */
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {


            /**
             * 进度条设置
             * */
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mPageLoadingProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (mPageLoadingProgressBar.getVisibility() == View.INVISIBLE)
                        mPageLoadingProgressBar.setVisibility(View.VISIBLE);
                    mPageLoadingProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(webView, newProgress);
            }

            /**
             * title设置
             * */
            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);
                myTitleView.setTitle(s, BrowserActivity.this);
            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2,
                                       JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            CustomViewCallback callback;

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2,
                                     JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                return super.onJsAlert(null, arg1, arg2, arg3);
            }

            /**
             * 5.0+
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
                mFileChooserParams = fileChooserParams;
                mUploadMsgFive = valueCallback;
                showUpdateHandPhoto(mWebView);
//                openImageChooserActivity();
                return true;
            }

            // Android > 4.1.1 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType, String capture) {
                uploadFile = uploadMsg;
                showUpdateHandPhoto(mWebView);

            }

            // 3.0 + 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                        String acceptType) {
                uploadFile = uploadMsg;
                mAcceptType = acceptType;
                VERSION_TAG = acceptType;
                showUpdateHandPhoto(mWebView);

            }

            // Android < 3.0 调用这个方法
            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                uploadFile = uploadMsg;
                showUpdateHandPhoto(mWebView);
            }

        });
        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(final String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Message msg = new Message();
                            msg.obj = getDownloadInfo(arg0);
                            msg.what = 1;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            handler.sendEmptyMessage(0);
                        }
                    }
                }).start();
            }
        });


        WebSettings webSetting = mWebView.getSettings();

        webSetting.setPluginsEnabled(true);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容

        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.LOAD_NORMAL);//webView 加载网页不显示图片问题
        }
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);
        String[] images = new String[]{""};
        mWebView.addJavascriptInterface(new MJavascriptInterface(this, images), "jsAndroid");
        long time = System.currentTimeMillis();
        if (mIntentUrl == null) {
            mWebView.loadUrl(mHomeUrl);
        } else {
            mWebView.loadUrl(mIntentUrl.toString());
        }
        TbsLog.d("time-cost", "cost time: "
                + (System.currentTimeMillis() - time));
        CookieSyncManager.createInstance(this);
        CookieSyncManager.getInstance().sync();
    }

    private void initBtnListenser() {
        mBack = findViewById(R.id.btnBack1);
        mForward = findViewById(R.id.btnForward1);
        mExit = findViewById(R.id.btnExit1);
        mHome = findViewById(R.id.btnHome1);
        myTitleView = findViewById(R.id.xweb_title);
        myTitleView.setRightImage(R.drawable.close_black);
        myTitleView.setRightListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        myTitleView.setTitle(getIntent().getExtras().getString("title"), this);
        myTitleView.setLeftListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack()) {
                    mWebView.goBack();
                    if (Integer.parseInt(Build.VERSION.SDK) >= 16)
                        changGoForwardButton(mWebView);
                } else
                    finish();
            }
        });
//        mGo = (Button) findViewById(R.id.btnGo1);
//        mUrl = (EditText) findViewById(R.id.editUrl1);
        mMore = findViewById(R.id.btnMore);
        if (Integer.parseInt(Build.VERSION.SDK) >= 16) {
            mBack.setAlpha(disable);
            mForward.setAlpha(disable);
            mHome.setAlpha(disable);
        }
        mHome.setEnabled(false);

        mBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoBack())
                    mWebView.goBack();
            }
        });

        mForward.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null && mWebView.canGoForward())
                    mWebView.goForward();
            }
        });

//        mGo.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                String url = mUrl.getText().toString();
//                mWebView.loadUrl(url);
//                mWebView.requestFocus();
//            }
//        });

        mMore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(BrowserActivity.this, "not completed",
                        Toast.LENGTH_LONG).show();
            }
        });


        mHome.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mWebView != null)
                    mWebView.loadUrl(mHomeUrl);
            }
        });

        mExit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Process.killProcess(Process.myPid());
            }
        });
    }

    boolean[] m_selected = new boolean[]{true, true, true, true, false,
            false, true};

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                if (Integer.parseInt(Build.VERSION.SDK) >= 16)
                    changGoForwardButton(mWebView);
                return true;
            } else
                return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri = data == null || resultCode != RESULT_OK ? null
                : data.getData();
        // 二维码
        if (resultCode == CodeManage.QR_RESULT_CODE && data != null) {
            String result = data.getStringExtra("result");
            if (IsNullUtil.isNotNull(result)) {
                String call = "javascript:receiveMsgFromNative(\"" + result + "\")";
                mWebView.loadUrl(call);
            }
            Log.d(result);
        } else if (resultCode == CodeManage.QR_RESULT_CODE1 && data != null) {
            String result = data.getStringExtra("result");
            if (IsNullUtil.isNotNull(result)) {
                String call = "javascript:receiveMsgFromNative(\"" + result + "\")";
                mWebView.loadUrl(call);
            }
        }
        if (resultCode == RESULT_OK) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            switch (requestCode) {
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
                case PHOTO_REQUEST:
                    // 对相册取出照片进行裁剪
                    photoClip(uri);
                    break;
                case PHOTO_CLIP:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            photo = extras.getParcelable("data");
                            try {
                                if (null == uploadFile && null == mUploadMsgFive) return;
                                if (mUploadMsgFive != null) {
                                    mUploadMsgFive.onReceiveValue(new Uri[]{bitmap2uri(BrowserActivity.this, photo)});
                                    mUploadMsgFive = null;
                                    setWindowDismiss();
                                } else if (uploadFile != null) {
                                    uploadFile.onReceiveValue(bitmap2uri(BrowserActivity.this, photo));
                                    uploadFile = null;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            reSet();
        }
        if (null == uploadFile) {
            return;
        }
    }

    /**
     * bitmap转uri
     */
    public static Uri bitmap2uri(Context c, Bitmap b) {
        File path = new File(c.getCacheDir() + File.separator + System.currentTimeMillis() + ".jpg");
        try {
            OutputStream os = new FileOutputStream(path);
            b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
            return Uri.fromFile(path);
        } catch (Exception ignored) {
        }
        return null;
    }

    private void reSet() {
        if (uploadFile != null) {
            uploadFile.onReceiveValue(null);
            uploadFile = null;
        }
        if (mUploadMsgFive != null) {
            mUploadMsgFive.onReceiveValue(null);
            mUploadMsgFive = null;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent == null || mWebView == null || intent.getData() == null)
            return;
        mWebView.loadUrl(intent.getData().toString());
    }

    @Override
    protected void onDestroy() {
        if (mTestHandler != null)
            mTestHandler.removeCallbacksAndMessages(null);
        if (mWebView != null)
            mWebView.destroy();
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static final int MSG_OPEN_TEST_URL = 0;
    public static final int MSG_INIT_UI = 1;
    private final int mUrlStartNum = 0;
    private int mCurrentUrl = mUrlStartNum;
    @SuppressLint("HandlerLeak")
    private Handler mTestHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_OPEN_TEST_URL:
                    if (!mNeedTestPage) {
                        return;
                    }

                    String testUrl = "file:///sdcard/outputHtml/html/"
                            + Integer.toString(mCurrentUrl) + ".html";
                    if (mWebView != null) {
                        mWebView.loadUrl(testUrl);
                    }

                    mCurrentUrl++;
                    break;
                case MSG_INIT_UI:
                    init();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    /**
     * 根据what值判断状态
     */
    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 1) {
                    Map<String, Object> map = (Map<String, Object>) msg.obj;
                    new DownloadPopupWindow(BrowserActivity.this, map.get("name") + "", map.get("url") + "", (int) map.get("size")).show(mWebView);
                } else {
                    Toast.makeText(BrowserActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {

            }

        }
    };

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
     * 从系统相机选择图片来源
     */
    public void getCamera() {
        new RxPermissions(this).
                request(Manifest.permission.CAMERA).
                subscribe(observer);
    }

    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(BrowserActivity.this, "相机权限打开失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (Build.VERSION.SDK_INT >= 24) {
                    Uri imageUri = FileProvider.getUriForFile(BrowserActivity.this, BrowserActivity.this.getApplicationContext().getPackageName() + ".provider", new File(Environment.getExternalStorageDirectory(), "hand.jpg"));
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                }
                // 下面这句指定调用相机拍照后的照片存储的路径
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hand.jpg")));
                imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "hand.jpg"));
                BrowserActivity.this.startActivityForResult(intent, CAMERA_REQUEST);
            } else {
                Toast.makeText(BrowserActivity.this, "相机权限打开失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    /***
     * 相册选择
     */
    public void getPicFromPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        this.startActivityForResult(intent, PHOTO_REQUEST);
    }


    /****
     * 调用系统自带切图工具对图片进行裁剪 微信也是
     *
     * @param uri
     */
    public void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        this.startActivityForResult(intent, PHOTO_CLIP);
    }

    public void setWindowDismiss() {
        if (handphotowindow.isShowing()) {
            handphotowindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
     * 获取网络文件的大小,名称,和下载地址(下载地址和形参传送的下载地址一样)
     *
     * @param url 下载地址
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getDownloadInfo(String url) throws Exception {
        Map<String, Object> map = new HashMap<>();
        URL mUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept-Encoding", "identity");
        conn.setRequestProperty("Referer", url);
        conn.setRequestProperty("Charset", "UTF-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.connect();
        int responseCode = conn.getResponseCode();
        // 判断请求是否成功处理
        if (responseCode == 200) {
            map.put("size", conn.getContentLength());
            map.put("url", url);
//            map.put("name", getFileName(conn.getHeaderFields()));
            if (map.get("name") == null || "".equals(map.get("name"))) {
                map.put("name", conn.getURL().getFile().substring(conn.getURL().getFile().lastIndexOf('/') + 1));
            }
            if (map.get("name") == null || "".equals(map.get("name"))) {
                throw new Exception("文件名字解析失败");
            }
        }
        conn.disconnect();
        return map;
    }

    @Subscribe
    public void onEventMainThread(MessageEvent event) {
        String result = "";
        switch (event.getTag()) {
            case TagUtil.ShareSuccessTag:
                result = "javascript:receiveMsgFromNative(\"" + 1 + "\")";
                break;
            case TagUtil.ShareCancelTag:
                result = "javascript:receiveMsgFromNative(\"" + 2 + "\")";
                break;
            case TagUtil.ShareErrorTag:
                result = "javascript:receiveMsgFromNative(\"" + 3 + "\")";
                break;
        }
        mWebView.loadUrl(result);
    }
}
