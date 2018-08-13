package yh.app.contacts;

import org.json.JSONObject;

import com.example.jpushdemo.body.BodyPush;
import com.example.jpushdemo.helper.Receiver;
import com.yhkj.cqgyxy.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import yh.app.activitytool.ActivityPortrait;

import org.androidpn.push.Constants;

import yh.app.logTool.Log;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import 云华.智慧校园.功能.TextViewCountListener;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;

public class SendNotificationByTeacher extends ActivityPortrait implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_notification_by_teacher);
        findViewById(R.id.quanzi_qrtjhy_sfyz_back).setOnClickListener(this);
        findViewById(R.id.quanzi_qrtjhy_sfyz_fs).setOnClickListener(this);
        ((EditText) findViewById(R.id.title)).addTextChangedListener(new TextViewCountListener((EditText) findViewById(R.id.title), (TextView) findViewById(R.id.title_num), 50));
        ((EditText) findViewById(R.id.message)).addTextChangedListener(new TextViewCountListener((EditText) findViewById(R.id.message), (TextView) findViewById(R.id.message_num), 100));
    }

    private String url;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quanzi_qrtjhy_sfyz_fs:
                if (((TextView) findViewById(R.id.title)).getText() == null || !IsNull.isNotNull(((TextView) findViewById(R.id.title)).getText().toString())) {
                    Toast.makeText(this, "标题或消息不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message msg = new Message();
                msg.obj = this;
                ThreadPoolManage manage = new ThreadPoolManage();
                manage.setCustomProgressDialog(this);
                manage.addNewPostTask(_链接地址导航.PushServer.teachPush.getUrl(), MapTools.buildMap(new String[][]
                        {
                                {
                                        "userid", Constants.number
                                },
                                {
                                        "ticket", Ticket.getFunctionTicket("20150120")
                                },
                                {
                                        "title", ((TextView) findViewById(R.id.title)).getText().toString()
                                },
                                {
                                        "function_id", "20150120"
                                },
                                {
                                        "message", ((TextView) findViewById(R.id.message)).getText().toString()
                                },
                                {
                                        "xkkh", getIntent().getStringExtra("function_id")
                                }
                        }), new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        try {
                            JSONObject jso = new JSONObject(msg.getData().getString("msg"));
                            if (jso.getString("code").equals("40001")) {

                                JSONObject jso1 = jso.getJSONObject("content");
//                                if (new SqliteHelper().rawQuery("select count(*) as num from client_notice_newest where userid=? and function_id = ? and n_send_time > ?", Constants.number, getIntent().getStringExtra("function_id"), jso.getString("time")).get(0).get("num").equals("0")) {
//                                    new SqliteHelper().execSQL("REPLACE into client_notice_newest(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
//                                            {
//                                                    jso1.getString("id"), Constants.number, BodyPush.READ_YES, new SqliteHelper().rawQuery("select classname as name from classlist where id = ? and userid = ?", getIntent().getStringExtra("function_id"), Constants.number).get(0).get("name"), ((EditText) findViewById(R.id.message)).getText().toString(), getIntent().getStringExtra("function_id"), "", jso.getString("time"), jso1.getString("code"), jso1.getString("ticket")
//                                            });
//                                }
                                new SqliteHelper().execSQL("insert or REPLACE into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]
                                        {
                                                jso1.getString("id"), Constants.number, BodyPush.READ_YES, ((EditText) findViewById(R.id.title)).getText().toString(), ((EditText) findViewById(R.id.message)).getText().toString(), getIntent().getStringExtra("function_id"), "", jso.getString("time"), jso1.getString("code"), jso1.getString("ticket")
                                        });

                                Log.d("zsp",  new SqliteHelper().execSQL("insert or REPLACE into client_notice(n_id,userid,read,n_title,n_message,function_id,n_url,n_send_time,code,n_ticket) values(?,?,?,?,?,?,?,?,?,?)", new Object[]{jso1.getString("id"), Constants.number, BodyPush.READ_YES, ((EditText) findViewById(R.id.title)).getText().toString(), ((EditText) findViewById(R.id.message)).getText().toString(), getIntent().getStringExtra("function_id"), "", jso.getString("time"), jso1.getString("code"), jso1.getString("ticket")})+"");

                                Toast.makeText(SendNotificationByTeacher.this, "消息发送成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(SendNotificationByTeacher.this, new JSONObject(msg.getData().getString("msg")).getString("message"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            Toast.makeText(SendNotificationByTeacher.this, "消息发送失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                });
                break;
            case R.id.quanzi_qrtjhy_sfyz_back:
                finish();
                break;
            default:
                break;
        }
    }
}