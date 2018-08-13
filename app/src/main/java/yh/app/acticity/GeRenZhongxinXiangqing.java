package yh.app.acticity;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
 
import yh.app.tool.SqliteDBCLose;
import yh.app.tool.SqliteHelper;
import yh.app.uiengine.HomeSetting;
import yh.tool.widget.IntegrationActivity;

//个人中心详情
public class GeRenZhongxinXiangqing extends IntegrationActivity {
	private LinearLayout home4_shezhi_layout;
	private String usertype;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home4);
		getUserType();
		initView();
	}

	private void initView() {
		// 个人中心
		home4_shezhi_layout = (LinearLayout) findViewById(R.id.home4_shezhi_layout);
		// 逻辑模块
		HomeSetting hs = new HomeSetting(home4_shezhi_layout, this, usertype);
		hs.setting();
	}

	private void getUserType() {
		SQLiteDatabase db = null;
		Cursor c = null;
		try {
			db = new SqliteHelper().getRead();
			c = db.rawQuery("select usertype from usertype where userid=?", new String[] { Constants.number });
			if (c.moveToFirst()) {
				usertype = c.getString(0);
			}
		} catch (Exception e) {
		} finally {
			new SqliteDBCLose(db, c).close();
		}

	}
}
