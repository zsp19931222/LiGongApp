package yh.app.stu_info;

import yh.app.appstart.lg.R;
import org.androidpn.push.Constants;
import yh.app.tool.SqliteHelper;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 
 * 包 名:yh.app.stu_info 类 名:Stu_info_setup.java 功 能:获取个人的各项数据,并且展示到TextView
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class Stu_info_setup extends Activity
{
	TextView stu_id = null;
	TextView stu_name = null;
	TextView stu_xueyuan = null;
	TextView stu_zhuanye = null;
	TextView stu_banji = null;
	EditText stu_tell = null;
	EditText stu_xinqing = null;
	ImageButton return_pre = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stu_info_setup);
		stu_id = (TextView) findViewById(R.id.stu_id);
		stu_name = (TextView) findViewById(R.id.stu_name);
		stu_xueyuan = (TextView) findViewById(R.id.stu_xueyuan);
		stu_zhuanye = (TextView) findViewById(R.id.stu_zhuanye);
		stu_banji = (TextView) findViewById(R.id.stu_banji);
		SQLiteDatabase db = new SqliteHelper().getWrite();
		Cursor result = db.rawQuery("select * from user where userid='" + Constants.number + "'", null);
		result.moveToFirst();
		while (!result.isAfterLast())
		{
			stu_id.setText(result.getString(0));
			stu_name.setText(result.getString(1));
			stu_xueyuan.setText(result.getString(11));
			stu_zhuanye.setText(result.getString(10));
			stu_banji.setText(result.getString(6));
			// stu_xinqing.setText("好心情");
			result.moveToNext();
		}
		db.close();

		return_pre = (ImageButton) findViewById(R.id.return_pre); // 获得返回按钮
		return_pre.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stu_info_setup, menu);
		return true;
	}

}
