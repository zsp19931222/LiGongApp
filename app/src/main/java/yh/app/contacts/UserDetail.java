package yh.app.contacts;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import yh.app.activitytool.ActivityPortrait;
import org.androidpn.push.Constants;

import yh.app.tool.ImageAtNotSave;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DefaultTopBar;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具.ViewTools;
import 云华.智慧校园.工具._链接地址导航;

public class UserDetail extends ActivityPortrait {
	private String type = "";

	public static final String IS_FRIEND = "isfriend";
	public static final String NOT_FRIEND = "notfriend";
	/**
	 * 性别代码 男
	 */
	public static final String MALE = "1";
	/**
	 * 性别代码 女
	 */
	public static final String FEMALE = "2";
	private String hybh;
	private TextView send;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_friend_detail);
		new DefaultTopBar(UserDetail.this).doit("好友信息");
		hybh = getIntent().getStringExtra("hybh");
		getData(Constants.number, getIntent().getStringExtra("hybh"));
		send = (TextView) findViewById(R.id.button);
		if (IsNull.isNotNull(getIntent().getStringExtra("fjnr"))) {
			((TextView) findViewById(R.id.contact_friend_detail_extra_content))
					.setText(getIntent().getStringExtra("fjnr"));
		} else {
			findViewById(R.id.contact_friend_detail_extra_content_layout).setVisibility(View.GONE);
		}
		if (!new SqliteHelper().rawQuery("select count(*) as num from friend where userid=? and FRIEND_ID=?",
				Constants.number, getIntent().getStringExtra("hybh")).get(0).get("num").equals("0")) {
			// 是好友
			type = IS_FRIEND;
			if (Constants.number.equals(hybh)) {
	              //如果是自己就隐藏按钮
					send.setVisibility(View.GONE);
				}
			send.setText("发送消息");

		} else {

			if (Constants.number.equals(hybh)) {
              //如果是自己就隐藏按钮
				send.setVisibility(View.GONE);
			}
			send.setText("添加好友");

			type = NOT_FRIEND;
		}
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				switch (type) {
				case IS_FRIEND:
					intent.setAction(yh.app.function.liaotianjiemian.class.getName());
					intent.putExtra("friend_id", getIntent().getStringExtra("hybh"));
					intent.setPackage(getPackageName());
					UserDetail.this.startActivity(intent);
					break;
				case NOT_FRIEND:
					intent.setAction(yh.app.quanzi.sfyz.class.getName());
					intent.putExtra("xh", getIntent().getStringExtra("hybh"));
					intent.putExtra("xm", ((TextView) findViewById(R.id.username)).getText());
					intent.setPackage(getPackageName());
					UserDetail.this.startActivity(intent);
					break;
				default:
					break;
				}

			}
		});
	}

	private void getData(String userid, String hybh) {
		new ThreadPoolManage().addNewPostTask(_链接地址导航.GroupChat.getUserDetail.getUrl(),
				MapTools.buildMap(new String[][] { { "hybh", hybh }, { "userid", userid } }), handler);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {

			try {
				JSONObject result = new JSONObject(msg.getData().getString("msg"));
				if (result.getString("code").equals("40001")) {
					JSONObject data = result.getJSONObject("content");
					new ImageAtNotSave(UserDetail.this, (ImageView) findViewById(R.id.face),
							data.getString("faceaddress")).execute();
					((TextView) findViewById(R.id.username)).setText(data.getString("name"));
					((TextView) findViewById(R.id.zhanghao)).setText(data.getString("userid"));
					setSex(((ImageView) findViewById(R.id.sex)), data.getString("sex_id"));
					setView((LinearLayout) findViewById(R.id.contact_friend_detail_layout), "部门",
							data.getString("department_name"));
					setView((LinearLayout) findViewById(R.id.contact_friend_detail_layout), "职业",
							data.getString("usertype"));
				}
			} catch (Exception e) {
			}
		};
	};

	private void setSex(ImageView sex, String sexType) {
		if (MALE.equals(sexType)) {
			sex.setBackgroundResource(R.drawable.sex_male);
		} else if (FEMALE.equals(sexType)) {
			sex.setBackgroundResource(R.drawable.sex_female);
		}
	}

	private void setView(LinearLayout layout, String name, String value) {
		if (!IsNull.isNotNull(value)) {
			return;
		}
		View item = ViewTools.getView(this, R.layout.wdbjxqjm_item, layout);
		((TextView) item.findViewById(R.id.name)).setText(name);
		((TextView) item.findViewById(R.id.value)).setText(value);
		layout.addView(item);
	}
}