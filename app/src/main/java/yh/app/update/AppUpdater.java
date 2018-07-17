package yh.app.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import org.androidpn.push.Constants;

public class AppUpdater extends AsyncTask<String, Integer, String> {
    private ProgressDialog mProgressDialog = null;
    private String urls = null;
    private final String names = "zhxy.apk";
    private boolean cancelUpdate = false;
    private Context context;

    public AppUpdater(ProgressDialog mProgressDialog, String urls, Context context) {
        this.context = context;
        this.urls = urls;
        this.mProgressDialog = mProgressDialog;
        this.mProgressDialog.setTitle("提示信息");
        this.mProgressDialog.setMessage("正在下载中，请稍后......");
        this.mProgressDialog.setCancelable(false);
        this.mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    }

    private HttpURLConnection getDownloadConnection() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urls);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-type", "application/x-java-serialized-object");
            conn.setRequestMethod("POST");
        } catch (Exception e) {
        }
        return conn;
    }

    private File getSaveFile() {
        String path = null;
        // if
        // (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()))
        // {
        // path = Environment.getExternalStorageDirectory() + "/";
        // }
        int mVersion = Build.VERSION.SDK_INT;
        if (mVersion >= 24) {
            path = context.getFilesDir().getPath();
        } else {
            path = Environment.getExternalStorageDirectory() + "/";
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(path, names);
    }

    private void excuteInstall(String filePath) {
        try {
            new File(filePath).mkdirs();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            int mVersion = Build.VERSION.SDK_INT;
            if (mVersion >= 24) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, "yh.app.appstart.lg.fileprovider", new File(filePath));
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            // 创建连接
            HttpURLConnection conn = getDownloadConnection();
            conn.connect();
            // 获取文件大小
            int length = conn.getContentLength();
            // 创建输入流
            InputStream is = conn.getInputStream();
            FileOutputStream fos = new FileOutputStream(getSaveFile());
            int count = 0;
            // 缓存
            byte buf[] = new byte[1024];
            // 写入到文件中
            do {
                int numread = is.read(buf);
                count += numread;
                // 计算进度条位置
                int progress = (int) (((float) count / length) * 100);
                publishProgress(progress);
                // 更新进度
                if (numread <= 0) {
                    final File apkfile = getSaveFile();
                    if (!apkfile.exists()) {
                        break;
                    }
                    new Timer().schedule(new TimerTask() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            excuteInstall(apkfile.toString());
                        }
                    }, 1000);

                    break;
                }
                // 写入文件
                fos.write(buf, 0, numread);
            } while (!cancelUpdate);// 点击取消就停止下载.

            fos.close();
            is.close();
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        mProgressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        mProgressDialog.dismiss();
    }

}
