package 云华.智慧校园.工具;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.androidpn.push.Constants;
import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.app3.tool.Tool;
import com.example.jpushdemo.ExampleApplication;
import com.example.smartclass.activity.SCMainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import yh.app.tool.Ticket;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.app.web.WeiBoWebActicity;
import yh.sina.weibo.sdk.demo.WBAuthActivity;
import yh.tool.widget.LoadDiaog;

public class _功能跳转 {

    public static final String weiboURL = "http://m.weibo.cn/";
    private LoadDiaog diaog;

    public void Jump(Context context, Map<String, String> parames) {
        if (!parames.get("FunctionID").equals("20180415")) {
            diaog = new LoadDiaog(context);
            diaog.show();
            if (parames == null) {
                Toast.makeText(context, "功能异常", Toast.LENGTH_SHORT).show();
                if (diaog.isShowing()) {
                    diaog.dismiss();
                }
            } else if (parames.get("function_type").equals("1") || parames.get("function_type").equals("10001")) {
//                if (parames.get("cls").equals("yh.app.function.WisdomClass")) {
//                    Intent intent = new Intent(context, SCMainActivity.class);
//                    intent.putExtra("function_id", parames.get("FunctionID"));
//                    intent.putExtra("user_id", Constants.number);
//                    context.startActivity(intent);
//                    if (diaog.isShowing()) {
//                        diaog.dismiss();
//                    }
//                } else {
                toApp(200, context, parames);
//                }
            } else if (parames.get("function_type").equals("2")) {
                toWebApp(200, context, parames);
            }
        } else {
            try {
                Intent intent = new Intent();
                PackageManager packageManager = context.getPackageManager();
                intent = packageManager.getLaunchIntentForPackage("com.seeyon.mobile.android");
                intent.setAction("android.intent.action.VIEW");
                context.startActivity(intent);
            } catch (Exception e) {
                Uri uri = Uri.parse("http://msf.ctbu.edu.cn/update/199/M1V6.0.1_GongShangDaXue_20161014.apk");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        }
    }

    /**
     * 跳到原生
     *
     * @param time
     * @param context
     * @param parames
     */
    private void toApp(int time, Context context, Map<String, String> parames) {
        Intent intent = new Intent();
        if (WBAuthActivity.class.getName().equals(parames.get("cls"))) {
            intent.setAction(WeiBoWebActicity.class.getName());
            intent.setPackage(ExampleApplication.getAppPackage());
            intent.putExtra("url", weiboURL);
        } else {
            intent.setAction(parames.get("cls")); // 功能类名
            intent.setPackage(ExampleApplication.getAppPackage());
        }
        intent.putExtra("function_id", parames.get("FunctionID")); // 功能id
        intent.putExtra("title", parames.get("name")); // 功能名称
        intent.putExtra("user_id", Constants.number);
        context.startActivity(intent);
        if (diaog.isShowing()) {
            diaog.dismiss();
        }
    }

    private void toWebApp(int time, final Context context, final Map<String, String> parames) {

        Map<String, String> map = new HashMap<>();
        map.put("userid", Constants.number);
        map.put("function_id", parames.get("FunctionID"));
        map.put("Ticket".toLowerCase(Locale.CHINA), Ticket.getFunctionTicket(parames.get("FunctionID")));
        VolleyRequest.RequestPost(_链接地址导航.UIA.网页跳转.getUrl(), map, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                JSONArray array = null;
                try {
                    array = new JSONArray(result);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                String url = new RSAHelper().decode(array);

                Intent intent = new Intent(context, BrowserActivity.class);
//                intent.setAction(parames.get("cls"));
                intent.setPackage(ExampleApplication.getAppPackage());
                intent.putExtra("url", url);
                intent.putExtra("function_id", parames.get("FunctionID"));
                intent.putExtra("title", parames.get("name"));
                context.startActivity(intent);
                if (diaog.isShowing()) {
                    diaog.dismiss();
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                Toast.makeText(context, "系统异常", Toast.LENGTH_SHORT).show();
                if (diaog.isShowing()) {
                    diaog.dismiss();
                }
            }
        });
    }

}