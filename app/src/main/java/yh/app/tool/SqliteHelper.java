package yh.app.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.androidpn.push.Constants;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import yh.app.db.MyImdb;

public class SqliteHelper {
    public SQLiteDatabase getRead() {
        return new MyImdb(Constants.App_Context, "mydb.db", Constants.SqliteVersion).getReadableDatabase();
    }

    public SQLiteDatabase getWrite() {
        return new MyImdb(Constants.App_Context, "mydb.db", Constants.SqliteVersion).getWritableDatabase();
    }

    public boolean execSQL(String sql) {
        SQLiteDatabase db = null;
        try {
            db = new SqliteHelper().getWrite();
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            new SqliteDBCLose(db, null).close();
        }
    }

    public boolean execSQL(String sql, Object... cs) {
        SQLiteDatabase db = null;
        try {
            db = new SqliteHelper().getWrite();
            db.execSQL(sql, cs);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            new SqliteDBCLose(db, null).close();
        }
    }

    public boolean execSQL(String sql, List<Object[]> cs) {
        SQLiteDatabase db = null;
        try {
            db = new SqliteHelper().getWrite();
            for (int i = 0, length = cs.size(); i < length; i++) {
                db.execSQL(sql, cs.get(i));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            new SqliteDBCLose(db, null).close();
        }
        return false;
    }

    public List<Map<String, String>> rawQuery(SQLiteDatabase db, String sql) {
        List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[]{});
            String[] s = c.getColumnNames();
            while (c.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < s.length; i++) {
                    map.put(s[i], c.getString(i));
                }
                retVal.add(map);
            }
        } catch (Exception e) {
        } finally {
            new SqliteDBCLose(db, c).close();
        }
        return retVal;
    }

    public List<Map<String, String>> rawQuery(String sql, String... parames) {
        List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new SqliteHelper().getRead();
            c = db.rawQuery(sql, parames);
            String[] s = c.getColumnNames();
            while (c.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < s.length; i++) {
                    map.put(s[i], c.getString(i));
                }
                retVal.add(map);
            }
        } catch (Exception e) {
            Log.d(TAG, "rawQuery: "+e);
        } finally {
            new SqliteDBCLose(db, c).close();
        }
        return retVal;
    }

    private static final String TAG = "SqliteHelper";
    public List<Map<String, String>> rawQuery(String sql) {
        List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new SqliteHelper().getRead();
            c = db.rawQuery(sql, new String[]{});
            String[] s = c.getColumnNames();
            while (c.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < s.length; i++) {
                    map.put(s[i], c.getString(i));
                }
                retVal.add(map);
            }
        } catch (Exception e) {
        } finally {
            new SqliteDBCLose(db, c).close();
        }
        return retVal;
    }

    public List<Map<String, String>> rawQuery(String sql,SQLiteDatabase db) {
        List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
        Cursor c = null;
        try {
            c = db.rawQuery(sql, new String[]{});
            String[] s = c.getColumnNames();
            while (c.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < s.length; i++) {
                    map.put(s[i], c.getString(i));
                }
                retVal.add(map);
            }
        } catch (Exception e) {
        } finally {
            new SqliteDBCLose(db, c).close();
        }
        return retVal;
    }

    public List<Map<String, String>> rawQuery(Cursor c) {
        List<Map<String, String>> retVal = new ArrayList<Map<String, String>>();
        String[] s = c.getColumnNames();
        while (c.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            for (int i = 0; i < s.length; i++) {
                map.put(s[i], c.getString(i));
            }
            retVal.add(map);
        }
        try {
            c.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return retVal;
    }

    public boolean insert(String sql, List<Object[]> parames) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = new SqliteHelper().getRead();
            for (int i = 0; parames != null && i < parames.size(); i++)
                db.execSQL(sql, parames.get(i));
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            new SqliteDBCLose(db, c).close();
        }
    }
}