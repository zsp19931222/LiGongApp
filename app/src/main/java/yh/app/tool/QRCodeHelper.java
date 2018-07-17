package yh.app.tool;

import org.json.JSONObject;

import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import 云华.智慧校园.工具.CodeManage;

public class QRCodeHelper {
    public void getQRCodeImage() {

    }

    public void scanQRCode(Context context) {
        Intent intent = new Intent();
        intent.setAction(CaptureActivity.class.getName());
        intent.setPackage(context.getPackageName());
        intent.putExtra("btn","nobtn");
        ((Activity) context).startActivityForResult(intent, CodeManage.QR_REQUEST_CODE);
    }

    public void scanQRCode1(Context context) {
        Intent intent = new Intent();
        intent.setAction(CaptureActivity.class.getName());
        intent.setPackage(context.getPackageName());
        intent.putExtra("btn","btn");
        ((Activity) context).startActivityForResult(intent, CodeManage.QR_REQUEST_CODE1);
    }

    public void dealResult(Context context, String result) {
        try {
            JSONObject jso = new JSONObject(result);
            if (CodeManage.NET_SUCCESS.equals(jso.getString("code"))) {
                JSONObject jso2 = jso.getJSONObject("content");
            } else {
                Toast.makeText(context, jso.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(context, "数据异常", Toast.LENGTH_SHORT).show();
        }
    }
}