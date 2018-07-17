package yh.app.uiengine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import yh.app.appstart.lg.R;

import org.androidpn.push.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import yh.app.activitytool.ActivityPortrait;
import yh.app.tool.MD5;
import yh.app.tool.Ticket;
import yh.app.tool.ToastShow;
import yh.app.utils.DefaultTopBar;
import yh.tool.widget.LoadDiaog;
import 云华.智慧校园.工具.ThreadPoolManage;
import 云华.智慧校园.工具._链接地址导航;
import 云华.智慧校园.自定义控件.MyDiaLog;

public class Password extends ActivityPortrait {
    private EditText new1, new2, old;
    private TextView new1_tv, new2_tv, old_tv;
    private Button tj;
    private CheckBox checkBox;
    private Context context;
    private static final int MAX_LENGTH = 16;

    private RelativeLayout oldRel, new1Rel, new2Rel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_passoword);
        ButterKnife.bind(this);
        initView();
        initAction();
    }

    //    MyDiaLog mdl = new MyDiaLog();
    LoadDiaog loadDiaog;

    private void initAction() {
        tj.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canChangePassword()) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userid", Constants.number);
                    map.put("oldpassword", MD5.MD5(old.getText().toString()));
                    map.put("newpassword", MD5.MD5(new1.getText().toString()));
                    map.put("ticket", Ticket.getLoginTicket(Constants.number, Constants.code));
                    // AllATSSS at = new AllATSSS(_链接地址导航.UIA.修改密码.getUrl(),
                    // handler, map, AllATSSS.POST);
                    // at.executeOnExecutor(Executors.newCachedThreadPool());
                    loadDiaog = new LoadDiaog(context);
                    loadDiaog.setTitle("提交中，请稍后...");
                    loadDiaog.show();
                    new ThreadPoolManage().addPostTask(_链接地址导航.UIA.修改密码.getUrl(), map, handler);
                } else
                    new ToastShow().show(context, "修改失败");
            }
        });
        old.addTextChangedListener(new myTextWatcher(old_tv, old));
        new1.addTextChangedListener(new myTextWatcher(new1_tv, new1));
        new2.addTextChangedListener(new myTextWatcher(new2_tv, new2));
        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isShowPassword(!isChecked);
            }
        });
    }

    private void initView() {
//        new DefaultTopBar(this).doit("修改密码");
        new1 = (EditText) findViewById(R.id.new1);
        new2 = (EditText) findViewById(R.id.new2);
        old = (EditText) findViewById(R.id.old);
        new1_tv = (TextView) findViewById(R.id.new1_tv);
        new2_tv = (TextView) findViewById(R.id.new2_tv);
        old_tv = (TextView) findViewById(R.id.old_tv);
        oldRel = (RelativeLayout) findViewById(R.id.old_rel_clear);
        new1Rel = (RelativeLayout) findViewById(R.id.new1_rel_clear);
        new2Rel = (RelativeLayout) findViewById(R.id.new2_rel_clear);
        isShowPassword(true);
        context = this;
        checkBox = (CheckBox) findViewById(R.id.isShowPassword);
        tj = (Button) findViewById(R.id.tj);
    }

    private void isShowPassword(boolean isShowPassword) {
        if (isShowPassword) {
            new1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            new2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            old.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            new1.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            new2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            old.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        Selection.setSelection(new1.getText(), new1.getText().length());
        Selection.setSelection(new2.getText(), new2.getText().length());
        Selection.setSelection(old.getText(), old.getText().length());
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (loadDiaog.isShowing()) {
                loadDiaog.dismiss();
            }
            try {
                JSONObject jso = new JSONObject(msg.getData().getString("msg"));
                if (jso.getBoolean("boolean")) {
                    new ToastShow().show(context, "修改成功");
//                    ((Activity) context).finish();
                    Intent intent = new Intent();
                    intent.setAction(Login1.class.getName());
                    intent.setPackage(context.getPackageName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else
                    new ToastShow().show(context, "修改失败");
            } catch (JSONException e) {
                new ToastShow().show(context, "修改失败");
            } catch (Exception e) {
                new ToastShow().show(context, "修改失败");
            }
        }

        ;
    };

    private boolean canChangePassword() {
        if (new1.getText().toString().length() >= 6 && new1.getText().toString().equals(new2.getText().toString())) {
            return true;
        } else if (!new1.getText().toString().equals(new2.getText().toString())) {
            new ToastShow().show(this, "两次密码不一样");
        } else if (new1.getText().toString().length() < 6) {
            new ToastShow().show(this, "密码长度小于6位");
        } else {
            new ToastShow().show(this, "修改失败");
        }
        return false;
    }

    @OnClick({R.id.old_rel_clear, R.id.new1_rel_clear, R.id.new2_rel_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.old_rel_clear:
                old.setText("");
                break;
            case R.id.new1_rel_clear:
                new1.setText("");
                break;
            case R.id.new2_rel_clear:
                new2.setText("");
                break;
        }
    }

    class myTextWatcher implements TextWatcher {
        private TextView textView;
        private EditText editText;

        public myTextWatcher(TextView textView, EditText editText) {
            this.textView = textView;
            this.editText = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            textView.setHint(editText.getText().toString().length() + "/" + MAX_LENGTH);
        }

    }
}