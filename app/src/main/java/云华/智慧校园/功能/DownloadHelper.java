package 云华.智慧校园.功能;

import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.example.jpushdemo.ExampleApplication;
import com.yunhuakeji.app.utils.Downloadbroader;
import com.yunhuakeji.app.utils.FileHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件下载类
 */
public class DownloadHelper {
    private static DownloadManager manager;

    private static String FileDownloadPath;

    /**
     * 初始化，负责构建帮助类
     *
     * @param application
     */
    public static void init(Context application) {
        if (manager == null) {
            manager = (DownloadManager) application.getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
            FileDownloadPath = application.getExternalFilesDir(null).getPath();
            new File(FileDownloadPath).mkdirs();
        }
    }

    /**
     * 下载文件
     *
     * @param context     上下文
     * @param downloadUrl 下载地址
     * @param fileName    文件名称
     * @return
     */
    public static long startDownload(final Context context, String downloadUrl, String fileName) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        request.setTitle(fileName);

        try {
            // 设置下载文件的保存位置
            getManager(context);
            android.util.Log.e("test", FileDownloadPath);
            File saveFile = new File(FileDownloadPath, fileName);
            request.setDestinationUri(Uri.fromFile(saveFile));
            final long id = getManager(context).enqueue(request);
            context.registerReceiver(new Downloadbroader(new FileHelper.OnFileDownloadCompleteLisener() {
                @Override
                public void onFileDownloadCompleteLisener(String filePath, String fileName) throws Exception {
                    DownloadManager.Query myDownloadQuery = new DownloadManager.Query();
                    myDownloadQuery.setFilterById(id);
                    Cursor cursor = getManager(context).query(myDownloadQuery);
                    while (cursor.moveToFirst()) {
                        int fileNameIdx = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);

                        String realFileName = cursor.getString(fileNameIdx);
                        Toast.makeText(context, "文件已保存:Android/data/" + ExampleApplication.getAppPackage() + "/files/" + fileName, Toast.LENGTH_SHORT).show();
//                        String prefix = realFileName.substring(realFileName.lastIndexOf(".") + 1);
//                        String type = initFileInstallMap().get(prefix);
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//
//                        intent.setDataAndType(FileProvider.getUriForFile(context, "yh.app.appstart.lg.fileprovider", new File(realFileName)), type);
//                        context.startActivity(intent);
                        return;
                    }
                }
            }, id, Environment.getExternalStorageDirectory().getPath(), fileName), new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            return id;
        } catch (Exception e) {
            android.util.Log.e("test", "test", e);
        }
        return 0;
    }

    /**
     * 取消下载
     *
     * @param context    上下文
     * @param downloadId 下载编号
     */
    public static void cancleDownload(Context context, long downloadId) {
        getManager(context).remove(downloadId);
    }

    private static DownloadManager getManager(Context application) {
        if (manager == null) {
            init(application.getApplicationContext());
        }
        return manager;
    }

    private static Map<String, String> map;

    public synchronized static Map<String, String> initFileInstallMap() {
        if (map == null) {
            map = new HashMap<>();
            map.put("3gp", "video/3gpp");
            map.put("apk", "application/vnd.android.package-archive");
            map.put("asf", "video/x-ms-asf");
            map.put("avi", "video/x-msvideo");
            map.put("bin", "application/octet-stream");
            map.put("bmp", "image/bmp");
            map.put("c", "text/plain");
            map.put("class", "application/octet-stream");
            map.put("conf", "text/plain");
            map.put("cpp", "text/plain");
            map.put("doc", "application/msword");
            map.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            map.put("xls", "application/vnd.ms-excel");
            map.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            map.put("exe", "application/octet-stream");
            map.put("gif", "image/gif");
            map.put("gtar", "application/x-gtar");
            map.put("gz", "application/x-gzip");
            map.put("h", "text/plain");
            map.put("htm", "text/html");
            map.put("html", "text/html");
            map.put("jar", "application/java-archive");
            map.put("java", "text/plain");
            map.put("jpeg", "image/jpeg");
            map.put("jpg", "image/jpeg");
            map.put("js", "application/x-javascript");
            map.put("log", "text/plain");
            map.put("m3u", "audio/x-mpegurl");
            map.put("m4a", "audio/mp4a-latm");
            map.put("m4b", "audio/mp4a-latm");
            map.put("m4p", "audio/mp4a-latm");
            map.put("m4u", "video/vnd.mpegurl");
            map.put("m4v", "video/x-m4v");
            map.put("mov", "video/quicktime");
            map.put("mp2", "audio/x-mpeg");
            map.put("mp3", "audio/x-mpeg");
            map.put("mp4", "video/mp4");
            map.put("mpc", "application/vnd.mpohun.certificate");
            map.put("mpe", "video/mpeg");
            map.put("mpeg", "video/mpeg");
            map.put("mpg", "video/mpeg");
            map.put("mpg4", "video/mp4");
            map.put("mpga", "audio/mpeg");
            map.put("msg", "application/vnd.ms-outlook");
            map.put("ogg", "audio/ogg");
            map.put("pdf", "application/pdf");
            map.put("png", "image/png");
            map.put("pps", "application/vnd.ms-powerpoint");
            map.put("ppt", "application/vnd.ms-powerpoint");
            map.put("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
            map.put("prop", "text/plain");
            map.put("rc", "text/plain");
            map.put("rmvb", "audio/x-pn-realaudio");
            map.put("rtf", "application/rtf");
            map.put("sh", "text/plain");
            map.put("tar", "application/x-tar");
            map.put("tgz", "application/x-compressed");
            map.put("txt", "text/plain");
            map.put("wav", "audio/x-wav");
            map.put("wma", "audio/x-ms-wma");
            map.put("wmv", "audio/x-ms-wmv");
            map.put("wps", "application/vnd.ms-works");
            map.put("xml", "text/plain");
            map.put("z", "application/x-compress");
            map.put("zip", "application/x-zip-compressed");
            map.put("", "*/*");
        }
        return map;
    }
}
