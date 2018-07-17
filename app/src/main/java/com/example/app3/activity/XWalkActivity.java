//package com.example.app3.activity;
//
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.widget.Toast;
//
//import com.example.app3.childview.DownloadPopupWindow;
//import yh.app.appstart.lg.R;
//import org.xwalk.core.XWalkDownloadListener;
//import org.xwalk.core.XWalkSettings;
//import org.xwalk.core.XWalkView;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class XWalkActivity extends org.xwalk.core.XWalkActivity {
//    private XWalkView xWalkView;
//
//    @Override
//    protected void onXWalkReady() {
//        xWalkView.load(getIntent().getStringExtra("url"), null);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.xwalk_est);
//        xWalkView = (XWalkView) findViewById(R.id.xwalk);
//        initXWalk();
//    }
//
//    public void initXWalk() {
//        XWalkSettings settings = new XWalkSettings(this);
//        xWalkView.setDownloadListener(new XWalkDownloadListener(this) {
//            @Override
//            public void onDownloadStart(final String s, String s1, String s2, String s3, long l) {
////                Log.d(new PermissionManager().checkPermission(XWalkActivity.this, "android.permission.CAMERA") + "");
//                // 由于安卓机制，不允许在UI线程访问网络,所以开一个线程访问
//                final String url = s;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Message msg = new Message();
//                            msg.obj = getDownloadInfo(url);
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        } catch (Exception e) {
//                            handler.sendEmptyMessage(0);
//                        }
//                    }
//                }).start();
//            }
//
//        });
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (xWalkView != null) {
//            xWalkView.onDestroy();
//        }
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
//                new DownloadPopupWindow(XWalkActivity.this, map.get("name") + "", map.get("url") + "", (int) map.get("size")).show(xWalkView);
//            } else {
//                Toast.makeText(XWalkActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };
//
//    /**
//     * 获取网络文件的大小,名称,和下载地址(下载地址和形参传送的下载地址一样)
//     *
//     * @param url 下载地址
//     * @return
//     * @throws Exception
//     */
//    public static Map<String, Object> getDownloadInfo(String url) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//        URL mUrl = new URL(url);
//        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
//        conn.setConnectTimeout(5 * 1000);
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Accept-Encoding", "identity");
//        conn.setRequestProperty("Referer", url);
//        conn.setRequestProperty("Charset", "UTF-8");
//        conn.setRequestProperty("Connection", "Keep-Alive");
//        conn.connect();
//        int responseCode = conn.getResponseCode();
//        // 判断请求是否成功处理
//        if (responseCode == 200) {
//            map.put("size", conn.getContentLength());
//            map.put("url", url);
//            map.put("name", getFileName(conn.getHeaderFields()));
//            if (map.get("name") == null || "".equals(map.get("name"))) {
//                map.put("name", conn.getURL().getFile().substring(conn.getURL().getFile().lastIndexOf('/') + 1));
//            }
//            if (map.get("name") == null || "".equals(map.get("name"))) {
//                throw new Exception("文件名字解析失败");
//            }
//        }
//        conn.disconnect();
//        return map;
//    }
//
//    private static String getFileName(Map<String, List<String>> hf) throws Exception {
//        Iterator<String> keys = hf.keySet().iterator();
//        String filename = null;
//        while (keys.hasNext()) {
//            String keyString = keys.next();
//            if ("Content-Disposition".equals(keyString)) {
//                List<String> lineString = hf.get(keyString);
//                for (int i = 0; i < lineString.size(); i++) {
//                    if (lineString.get(i).indexOf("filename") > 0) {
//                        String result = lineString.get(i).substring(
//                                lineString.get(i).indexOf("filename")
//                                        + "filename".length());
//                        filename = result.substring(result.indexOf("=") + 1);
//                        if (filename != null && filename.contains("''")) {
//                            filename = URLDecoder.decode(
//                                    filename.split("''")[1],
//                                    filename.split("''")[0]);
//                        }
//                    }
//                }
//            }
//        }
//        return filename;
//    }
//}
