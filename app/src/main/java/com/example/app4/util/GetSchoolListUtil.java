package com.example.app4.util;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jpushdemo.ExampleApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import yh.app.tool.SqliteHelper;

/**
 * Created by Administrator on 2018/3/29 0029.
 */

public class GetSchoolListUtil {
    /**
     * 将assets目录下的文件拷贝到sd上
     *
     * @return 存储数据库的地址
     */

    private static String CopySqliteFileFromRawToDatabases() throws IOException {// 复制和加载区域数据库中的数据

        // 第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/<db_name>

        File dir = new File("data/data/" + ExampleApplication.getContext().getPackageName() + "/databases");

        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }

        File file = new File(dir, "schools.db");
        InputStream inputStream = null;
        OutputStream outputStream = null;

        //通过IO流的方式，将assets目录下的数据库文件，写入到SD卡中。
        if (!file.exists()) {
            try {
                file.createNewFile();

                inputStream = ExampleApplication.getContext().getClass().getClassLoader().getResourceAsStream("assets/" + "schools.db");
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;

                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();

            } finally {

                if (outputStream != null) {

                    outputStream.flush();
                    outputStream.close();

                }
                if (inputStream != null) {
                    inputStream.close();
                }

            }

        }

        return file.getPath();

    }

    /**
     * 根据sql获取相关数据
     */
    public static List<Map<String, String>> getSchoolData(String sql, List<Map<String, String>> dataMap) {
        SQLiteDatabase sqLiteDatabase;
        try {
            sqLiteDatabase = SQLiteDatabase.openDatabase(CopySqliteFileFromRawToDatabases(), null, SQLiteDatabase.OPEN_READONLY);
            dataMap.addAll(new SqliteHelper().rawQuery(sql, sqLiteDatabase));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
}
