//package com.example.app3.chat;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v7.widget.RecyclerView;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.app3.base.BaseRecyclerViewActivity;
//import com.example.app3.view.MyTitleView;
//import yh.app.appstart.lg.R;
//
//import org.androidpn.push.Constants;
//
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import yh.app.logTool.Log;
//import yh.app.tool.SqliteHelper;
//
///**
// * Created by Administrator on 2017/10/11.
// */
//
//public class ChatActivity extends BaseRecyclerViewActivity {
//
//    @BindView(R.id.chat_title)
//    MyTitleView chatTitle;
//    @BindView(R.id.chat_rec)
//    RecyclerView chatRec;
//    @BindView(R.id.chat_et)
//    EditText chatEt;
//    @BindView(R.id.chat_tv_Send)
//    TextView chatTvSend;
//    private ChatAdapter adapter;
//
//    private List<Map<String, String>> chatList;
//    private String frient_id;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_chat;
//    }
//
//    @Override
//    protected void setTitle(Context context) {
//
//    }
//
//    @Override
//    protected void loadRecyclerViewData(Context context) {
//
//    }
//
//    @Override
//    protected void init(Context context) {
//        frient_id = getIntent().getExtras().getString("fqr");
//        chatList=  new SqliteHelper().rawQuery("select * from lt where userid=? and friend_id=?", Constants.number, frient_id);
//        Log.d("zsp",chatList.size()+"");
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        // TODO: add setContentView(...) invocation
//        ButterKnife.bind(this);
//    }
//}
