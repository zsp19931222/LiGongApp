package 云华.智慧校园.工具;

import java.lang.reflect.Field;

import android.content.Context;

import com.yunhuakeji.app.utils.ScreenHelper;
import yh.app.appstart.lg.R;

public class RHelper {
    public int getColor(Context context, String name) {
        try {
            R.color cls = (R.color) Class.forName(context.getPackageName() + ".R$color").newInstance();
            Field f = cls.getClass().getField(name);
            return f.getInt(f.getName());
        } catch (Exception e) {
            return R.color.button;
        }
    }

    public int getId(Context context, String name) {
        try {
            R.id cls = (R.id) Class.forName(context.getPackageName() + ".R$id").newInstance();
            Field f = cls.getClass().getField(name);
            return f.getInt(f.getName());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getDrawable(Context context, String name) {
        try {
            R.drawable cls = (R.drawable) Class.forName(context.getPackageName() + ".R$drawable").newInstance();
            Field f = cls.getClass().getField(name);
            return f.getInt(f.getName());
        } catch (Exception e) {
            return 0;
        }
    }

    public int getLayout(Context context, String name) {
        try {
            R.layout cls = (R.layout) Class.forName(context.getPackageName() + ".R$layout").newInstance();
            Field f = cls.getClass().getField(name);
            return f.getInt(f.getName());
        } catch (Exception e) {
            return 0;
        }
    }
}
