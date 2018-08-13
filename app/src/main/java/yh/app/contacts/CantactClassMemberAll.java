package yh.app.contacts;

import java.util.Locale;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.yhkj.cqgyxy.R;

import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DefaultTopBar;
import 云华.智慧校园.自定义适配器.MyBJXQAdapter;

public class CantactClassMemberAll extends ActivityPortrait
{
	private GridView gridView;
	private EditText seach;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_class_member_all);

		initView();
		initUI();
		initAction();
	}

	private void initAction()
	{
		// TODO Auto-generated method stub
		seach.addTextChangedListener(new TextWatcher()
		{
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after)
			{
			}

			@Override
			public void afterTextChanged(Editable s)
			{
				// TODO Auto-generated method stub
				JSONArray jsa = new JSONArray(new SqliteHelper().rawQuery("select * from classmember where kcid=? and (userid like ? or name like ? or pinyin like ?) order by usertype desc", getIntent().getStringExtra("kcid"), "%" + s.toString() + "%", "%" + s.toString() + "%", "%" + s.toString().toLowerCase(Locale.CHINA) + "%"));
				gridView.setAdapter(new MyBJXQAdapter(jsa, CantactClassMemberAll.this));
				if (s == null || s.length() == 0 || s.toString().equals(""))
				{
					initUI();
				}
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();
				intent.setAction(yh.app.contacts.UserDetail.class.getName());
				intent.putExtra("hybh", view.getTag().toString());
				intent.setPackage(CantactClassMemberAll.this.getPackageName());
				CantactClassMemberAll.this.startActivity(intent);
			}
		});
	}

	private void initUI()
	{
		JSONArray jsa = new JSONArray(new SqliteHelper().rawQuery("select * from classmember where kcid=? order by usertype desc", getIntent().getStringExtra("kcid")));
		gridView.setAdapter(new MyBJXQAdapter(jsa, this));
	}

	private void initView()
	{
		new DefaultTopBar(this).doit(getIntent().getStringExtra("title"));
		gridView = (GridView) findViewById(R.id.gridView);
		seach = (EditText) findViewById(R.id.input);
	}
}
