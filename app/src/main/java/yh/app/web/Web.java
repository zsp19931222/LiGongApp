//package yh.app.web;
//
//import android.annotation.SuppressLint;
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.webkit.WebSettings;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.app3.activity.XWalkActivity;
//import com.example.app3.childview.DownloadPopupWindow;
//import com.example.app3.view.MyTitleView;
//import com.yhkj.cqgyxy.R;
//
//import org.androidpn.push.Constants;
//import org.xwalk.core.XWalkDownloadListener;
//import org.xwalk.core.XWalkResourceClient;
//import org.xwalk.core.XWalkSettings;
//import org.xwalk.core.XWalkUIClient;
//import org.xwalk.core.XWalkView;
//
//import java.util.Map;
//
//import yh.app.logTool.Log;
//import yh.app.notification.Notification1;
//import yh.app.tool.QRCodeHelper;
//import yh.app.tool.SqliteHelper;
//import yh.app.tool.Ticket;
//import 云华.智慧校园.工具.CodeManage;
//import 云华.智慧校园.工具.MessageDataBaseFresh;
//
//@TargetApi(Build.VERSION_CODES.LOLLIPOP)
//@SuppressLint("SetJavaScriptEnabled")
//public class Web extends XWalkActivity {
//    private TextView title;
//    private XWalkView webview;
//    private Intent i_getvalue;
//    private ProgressBar web_bar;
//    private MyTitleView myTitleView;
//
//
//    @Override
//    protected void onXWalkReady() {
//        super.onXWalkReady();
//        setWebView();
//        String action = i_getvalue.getAction();
//        try {
//            if (Intent.ACTION_VIEW.equals(action)) {
//                Uri uri = i_getvalue.getData();
//                if (uri != null) {
//                    webview.loadUrl(uri.getQueryParameter("url"));
//                }
//            } else {
//                webview.loadUrl(i_getvalue.getStringExtra("url"));
//            }
//        } catch (Exception e) {
//            // Toast.makeText(this, "功能异常,请重试", Toast.LENGTH_SHORT).show();
//            finish();
//        }
//        try {
//            if (i_getvalue.getStringExtra("id") != null) {
//                new SqliteHelper().execSQL("update tzgg set isread='true' where userid=? and tzggid=?", new Object[]
//                        {
//                                Constants.number, i_getvalue.getStringExtra("id")
//                        });
//                Notification1.cancel(Notification1.NOTIFICATION_ALL);
//                MessageDataBaseFresh.freshPush(getFunc_id(i_getvalue.getStringExtra("id")));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.web);
//        i_getvalue = getIntent();
//        i_getvalue.getExtras();
//        initView();
//        initTopBar();
//    }
//
//    private String getFunc_id(String id) {
//        return new SqliteHelper().rawQuery("select func_id from tzgg where tzggid='" + id + "'").get(0).get("func_id");
//    }
//
////    private TopBarHelper tbh;
//
//    private void initTopBar() {
////        tbh = new DefaultTopBar(this).doit(Ticket.getFunctionName(i_getvalue.getStringExtra("function_id")));
//        myTitleView.setLeftListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        myTitleView.setTitle(Ticket.getFunctionName(i_getvalue.getStringExtra("function_id")), this);
//    }
//
//    /**
//     * 根据what值判断状态
//     */
//    public Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 1) {
//                Map<String, Object> map = (Map<String, Object>) msg.obj;
//                new DownloadPopupWindow(Web.this, map.get("name") + "", map.get("url") + "", (int) map.get("size")).show(webview);
//            } else {
//                Toast.makeText(Web.this, "下载失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    private void setWebView() {
//        XWalkSettings settings = webview.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setUseWideViewPort(true);// 设定支持viewport
//        settings.setLoadWithOverviewMode(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setSupportZoom(false);// 设定支持缩放
//        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        webview.setDownloadListener(new XWalkDownloadListener(this) {
//            @Override
//            public void onDownloadStart(final String s, String s1, String s2, String s3, long l) {
////                Log.d(new PermissionManager().checkPermission(XWalkActivity.this, "android.permission.CAMERA") + "");
//                // 由于安卓机制，不允许在UI线程访问网络,所以开一个线程访问
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Message msg = new Message();
//                            msg.obj = XWalkActivity.getDownloadInfo(s);
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            handler.sendEmptyMessage(0);
//                        }
//                    }
//                }).start();
//            }
//        });
//        webview.setResourceClient(new XWalkResourceClient(webview) {
//            @Override
//            public boolean shouldOverrideUrlLoading(XWalkView view, String url) {
//                // 理工迎新系统 问卷调查关闭链接
//                if (url.equals("http://www.close.com/")) {
//                    finish();
//                    return true;
//                }
//                if (url.startsWith("tel:")){
//                    Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse(url));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    return true;
//                }
//                if (url.startsWith("map:")) {
//                    // 地图webAPI
//                } else if (url.startsWith("yhqrcode://")) {
//                    QRCodeHelper helper = new QRCodeHelper();
//                    helper.scanQRCode(Web.this);
//                    return true;
//                } else {
//                    webview.loadUrl(url);
//                }
//                return true;
//            }
//
//            @Override
//            public void onProgressChanged(XWalkView view, int progressInPercent) {
//                super.onProgressChanged(view, progressInPercent);
//                web_bar.setProgress(progressInPercent);
//            }
//        });
//        webview.setUIClient(new XWalkUIClient(webview) {
//            @Override
//            public void onPageLoadStarted(XWalkView view, String url) {
//                super.onPageLoadStarted(view, url);
//            }
//
//            @Override
//            public void onReceivedTitle(XWalkView view, String title) {
//                super.onReceivedTitle(view, title);
//                if (title.contains("/") || title.contains("?") || title.contains("&"))
////                    tbh.setTitle(Ticket.getFunctionName(i_getvalue.getStringExtra("function_id")));
//                    myTitleView.setTitle(Ticket.getFunctionName(i_getvalue.getStringExtra("function_id")), Web.this);
//                else
////                    tbh.setTitle(title);
//                    myTitleView.setTitle(title, Web.this);
//
//            }
//
//            @Override
//            public void onPageLoadStopped(XWalkView view, String url, LoadStatus status) {
//                super.onPageLoadStopped(view, url, status);
//            }
//        });
//    }
//
//    private void initView() {
//        webview = (XWalkView) findViewById(R.id.webView12312);
//        title = (TextView) findViewById(R.id.topbar_title);
//        myTitleView = (MyTitleView) findViewById(R.id.web_title);
//        // 进度条
//        web_bar = (ProgressBar) findViewById(R.id.web_bar);
//    }
//
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        super.onDestroy();
//        if (webview != null) {
//            webview.onDestroy();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        // 二维码
//        if (requestCode == CodeManage.QR_REQUEST_CODE && resultCode == CodeManage.QR_RESULT_CODE && data != null) {
//            String result = data.getStringExtra("result");
//            webview.loadUrl("http://msf.ctbu.edu.cn/MV/yxbl/yxbl_index.action?userid=" + Constants.number + "&stuid=" + result);
//            Log.d(result);
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (webview != null) {
//            webview.pauseTimers();
//            webview.onHide();
//        }
//    }
//}
