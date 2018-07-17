package 云华.智慧校园.功能;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import yh.app.tool.SqliteHelper;
import yh.app.uiengine.Login1;
import yh.app.uiengine.home;

import com.example.app3.HomePageActivity;
import com.example.app4.activity.BindingOtherActivity;
import com.example.app4.activity.BindingPhoneActivity;
import com.example.app4.activity.StartActivity;
import com.example.app4.util.DefaultUtil;
import com.example.jpushdemo.ApnsStart;

import org.androidpn.push.Constants;

public class LoginOut {
    public void doLoginOut(Context context) {

        SQLiteDatabase db = new SqliteHelper().getWrite();
        try {
//			db.execSQL("delete from term");
//			db.execSQL("delete from userp");
//			db.execSQL("delete from user");
//			db.execSQL("delete from nowterm");
            context.deleteDatabase("mydb.db");
            new SqliteHelper().execSQL("insert into ydy values('1')");
            new ApnsStart(context).clearAlias();
        } catch (Exception e) {
        }
        db.close();
        Intent intent = new Intent(context, BindingOtherActivity.class);
        intent.putExtra("universityName", DefaultUtil.getDefaultSchool());
        Constants.xxmc = DefaultUtil.getDefaultSchool();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        ((Activity) context).startActivity(intent);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((Activity) context).finish();


    }
}
