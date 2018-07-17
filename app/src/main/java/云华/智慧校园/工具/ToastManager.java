//package 云华.智慧校园.工具;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
//import yh.app.web.Web;
//import 云华.智慧校园.功能.DownloadHelper;
//
///**
// * Created by Administrator on 2017/9/21.
// */
//
//public class ToastManager {
//    public static Toast toast;
//
//    public static void show(Context context, String message) {
//        getToast(context, message).show();
//    }
//
//    private synchronized static Toast getToast(Context context, String message) {
//        try {
//            if (toast != null) {//通过反射技术，从toast对象中获取mTN对象
//                Field field = toast.getClass().getDeclaredField("mTN");
//                field.setAccessible(true);
//                Object obj = field.get(toast);
//                //从TN对象中获得show方法
//                Method method = obj.getClass().getDeclaredMethod("show", null);
//                //调用TN对象的show方法来显示Toast信息提示框
//                method.invoke(obj, null);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (toast == null) {
//            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//        }
//        return toast;
//    }
//}
