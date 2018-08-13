package yh.app.function;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.FriendDetailActivity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.TimeTool;
import com.example.app3.view.MyTitleView;
import com.example.jpushdemo.body.BodyAdd;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import yh.app.activitytool.ActivityPortrait;
import yh.app.quanzitool.LiaoTianMessage;
import yh.app.quanzitool.UserTools;
import yh.app.quanzitool._圈子聊天工具;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.utils.DefaultTopBar;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import 云华.智慧校园.工具.DateTools;
import 云华.智慧校园.工具.NullTools;
import 云华.智慧校园.工具._全角_半角;
import 云华.智慧校园.工具._链接地址导航;

public class liaotianjiemian extends ActivityPortrait {
    private EditText input;
    private Button fs;
    private String name;
    public LinearLayout layout;
    private String friend_id;
    public ScrollView sc;
    public Context mContext;
    public String standardTime = "2014-01-01 00:00:00";
    private MyTitleView myTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        EventBus.getDefault().register(this);

        this.setContentView(R.layout.quanzi_liaotianjiemian);
        mContext = this;
        getMessage();
        init();
        sc.post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(View.FOCUS_DOWN);
            }
        });
        showMessage(new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=?", new String[]
                {
                        Constants.number, friend_id
                }));

        read();
    }

    private void setTopBar(String name) {
        new DefaultTopBar(mContext).doit(name);
    }

    private void init() {
        try {
            standardTime = new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=?").get(0).get("fssj");
        } catch (Exception e) {

        }

        sc = (ScrollView) findViewById(R.id.quanzi_lt_listlayout_sc);
        Constants.dqjm = "ltjm";
        layout = (LinearLayout) findViewById(R.id.quanzi_lt_layout);
        final Intent intent = getIntent();
        friend_id = intent.getStringExtra("friend_id");
//        name = new UserTools().getFriendName(friend_id);
        name = intent.getStringExtra("hyName");
//        setTopBar(name);
        Constants.dqltr = friend_id;
        fs = (Button) findViewById(R.id.quanzi_lt_fsbutton);
        input = (EditText) findViewById(R.id.quanzi_liaotian_input);
        fs.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fs();
            }
        });
        getdate();

        myTitleView = (MyTitleView) findViewById(R.id.quanzi_lt_title);
        myTitleView.setTitle(name, mContext);
        myTitleView.setLeftListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        myTitleView.setRightListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(mContext, FriendDetailActivity.class);
                intent1.putExtra("fqr", friend_id);
                mContext.startActivity(intent1);
            }
        });
    }

    public void read() {
        new SqliteHelper().execSQL("update lt set isread='true' where userid=? and friend_id =?", new Object[]
                {
                        Constants.number, friend_id
                });
    }

    public void fs() {

        try {
            if (input == null || input.getText() == null || input.getText().toString().equals("")) {
                Toast.makeText(mContext, "请输入聊天内容", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            return;
        }
        final String message = _全角_半角.to全角(input.getText().toString());
        final View view = new LiaoTianMessage().addMessateMy(mContext, layout, message, DateTools.StringToDateYMDHM(standardTime), new Date(), true);
        sc.post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(View.FOCUS_DOWN);
            }
        });

        Map<String, String> map = new HashMap<>();
        map.put("jsr", friend_id);
        map.put("fqr", Constants.number);
        map.put("message", message);
        map.put("userid", Constants.number);
        map.put("ticket", Ticket.getPushTicket(Constants.number, Constants.code));
        VolleyRequest.RequestPost(_链接地址导航.PUSH.聊天接口.getUrl(), map, new VolleyInterface() {

            @Override
            public void onMySuccess(String result) {
                // TODO Auto-generated method stub
                try {
                    JSONObject jso = new JSONObject();
                    JSONObject json = new JSONObject(result);
                    String results = json.getString("code");
                    if (NullTools.isNotNull(result)) {
                        if (results.equals("40001")) {
                            view.findViewById(R.id.pb).setVisibility(View.GONE);
                            new _圈子聊天工具().saveMySendMessage(UUID.randomUUID().toString(), friend_id, message, json.getString("time"));
                            ((TextView) view.findViewById(R.id.time)).setText(DateTools.DateToStringYMDHM(DateTools.StringToDateYMDHM(json.getString("time"))));
                            new SqliteHelper().execSQL("insert or replace into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread,m_deal) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", new Object[]
                                    {
                                            friend_id, Constants.number, "", friend_id, name, Constants.number, "", new SqliteHelper().rawQuery("select * from user").get(0).get("name"), TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), "", BodyAdd.DEAL_AGREE, BodyAdd.DEAL_NOREAD, "false"
                                    });
                        } else {
                            view.findViewById(R.id.pb).setVisibility(View.GONE);
                            view.findViewById(R.id.img).setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception e) {
                    view.findViewById(R.id.pb).setVisibility(View.GONE);
                    view.findViewById(R.id.img).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub
                Toast.makeText(liaotianjiemian.this, "发送失败请稍后重试", Toast.LENGTH_SHORT).show();
            }
        });

        input.setText("");
//		new ThreadPoolManage().addPostTask(_链接地址导航.PUSH.聊天接口.getUrl(), MapTools.buildMap(new String[][]
//		{
//				{
//						"jsr", friend_id
//				},
//				{
//						"fqr", Constants.number
//				},
//				{
//						"message", message
//				},
//				{
//						"userid", Constants.number
//				},
//				{
//						"ticket", Ticket.getPushTicket(Constants.number, Constants.code)
//				}
//		}), new Handler()
//		{
//			@Override
//			public void handleMessage(Message msg)
//			{
//				try
//				{
//					JSONObject jso = new JSONObject();
//					JSONObject json = new JSONObject(msg.getData().getString("msg"));
//					String result = json.getString("code");
//					if (NullTools.isNotNull(result))
//					{
//						if (result.equals("40001"))
//						{
//							view.findViewById(R.id.pb).setVisibility(View.GONE);
//							new _圈子聊天工具().saveMySendMessage(UUID.randomUUID().toString(), friend_id, message, json.getString("time"));
//							((TextView) view.findViewById(R.id.time)).setText(DateTools.DateToStringYMDHM(DateTools.StringToDateYMDHM(json.getString("time"))));
//						} else
//						{
//							view.findViewById(R.id.pb).setVisibility(View.GONE);
//							view.findViewById(R.id.img).setVisibility(View.VISIBLE);
//						}
//					}
//				} catch (Exception e)
//				{
//					view.findViewById(R.id.pb).setVisibility(View.GONE);
//					view.findViewById(R.id.img).setVisibility(View.VISIBLE);
//				}
//
//			}
//		});
//		input.setText("");
    }

    private List<Map<String, String>> getdate() {
        return new SqliteHelper().rawQuery("select fsr,message from lt where userid=? order by fssj desc");
    }

    private void showMessage(List<Map<String, String>> list) {
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).get("jsr").equals(Constants.number)) {
                new LiaoTianMessage().addMessateOther(liaotianjiemian.this, layout, list.get(i).get("message"), DateTools.StringToDateYMDHM(standardTime), DateTools.StringToDateYMDHM(list.get(i).get("fssj")));
            } else {
                new LiaoTianMessage().addMessateMy(mContext, layout, list.get(i).get("message"), DateTools.StringToDateYMDHM(standardTime), DateTools.StringToDateYMDHM(list.get(i).get("fssj")), false);
            }
    }

    public void getMessage() {
        Constants.ChatHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                try {
                    super.handleMessage(msg);
                    Bundle b = msg.getData();
                    String message = b.getString("message");
                    new LiaoTianMessage().addMessateOther(mContext, layout, message, DateTools.StringToDateYMDHM(standardTime), new Date());
                    read();
                    sc.post(new Runnable() {
                        @Override
                        public void run() {
                            sc.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(mContext.getApplicationContext(), "正在启动服务,请稍等", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Subscribe
    public void onEventMainThread(MyEventBus event) {
        if (event.getMsg().equals(HintTool.Receive_Push_Message)) {//接受到推送更新数据
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    sc.post(new Runnable() {
                        @Override
                        public void run() {
                            sc.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                    showMessage(new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=? and isread=?", new String[]
                            {
                                    Constants.number, friend_id, "false"
                            }));

                    read();
                }
            });
        } else if (event.getMsg().equals(HintTool.Close_Chat)) {
            finish();
        }
    }
}