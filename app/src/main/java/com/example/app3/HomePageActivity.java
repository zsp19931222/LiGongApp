package com.example.app3;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.example.app3.activity.FriendDetailActivity;
import com.example.app3.base.BaseActivity;
import com.example.app3.childview.HomeApplyView;
import com.example.app3.childview.HomeMessageView;
import com.example.app3.childview.HomeMyselfView;
import com.example.app3.entity.TXLEntity;
import com.example.app3.eventbus.MyEventBus;
import com.example.app3.listener.MyListener;
import com.example.app3.tool.HintTool;
import com.example.app3.tool.JSONTool;
import com.example.app3.tool.TimeTool;
import com.example.jpushdemo.body.BodyAdd;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import rx.Observer;
import yh.app.logTool.Log;
import yh.app.quanzitool.pinyin;
import yh.app.tool.FunctionAT;
import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import yh.app.tool.URLHelper;
import yh.app.utils.GsonImpl;
import yh.app.utils.ToastUtils;
import yh.app.utils.UploadUtil;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.DragPointView;
import 云华.智慧校园.工具.IsNull;
import 云华.智慧校园.工具.MapTools;
import 云华.智慧校园.工具.ViewUtil;
import 云华.智慧校园.工具._链接地址导航;

public class HomePageActivity extends BaseActivity implements OnClickListener, MyListener {
    /**
     * 首页底部几个控制按钮
     */
    private TextView home_txt_buttom_sy, home_txt_buttom_xx, home_txt_buttom_wd;
    private ImageView home_txt_buttom_sy_image, home_txt_buttom_xx_image, home_txt_buttom_wd_image;
    private RelativeLayout home_txt_buttom_sy_rel, home_txt_buttom_xx_rel, home_txt_buttom_wd_rel;

    private ImageView home_image_buttom_hint;
    private DragPointView home_txt_buttom_xx_num;
    /**
     * 首页几个页面的游标
     */
    private static final int INDEX_BUTTOM_SY = 0, INDEX_BUTTOM_XX = 1, INDEX_BUTTOM_WD = 2;
    // 主页的切换控件
    private LinearLayout home_linear;
    // 首页各个页面
    private View view_home_sy, view_home_xx, view_home_wd;
    // 当前页面
    private int currentIndex = INDEX_BUTTOM_SY;

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        FunctionAT.getPushFunctionList(null);
        new FunctionAT(new Handler()).getFunctionList();

        home_txt_buttom_sy = findViewById(R.id.home_txt_buttom_sy);
        home_txt_buttom_xx = findViewById(R.id.home_txt_buttom_xx);
        home_txt_buttom_wd = findViewById(R.id.home_txt_buttom_wd);

        home_txt_buttom_sy_image = findViewById(R.id.home_txt_buttom_sy_image);
        home_txt_buttom_xx_image = findViewById(R.id.home_txt_buttom_xx_image);
        home_txt_buttom_wd_image = findViewById(R.id.home_txt_buttom_wd_image);

        home_txt_buttom_sy_rel = findViewById(R.id.home_txt_buttom_sy_rel);
        home_txt_buttom_xx_rel = findViewById(R.id.home_txt_buttom_xx_rel);
        home_txt_buttom_wd_rel = findViewById(R.id.home_txt_buttom_wd_rel);

        home_linear = findViewById(R.id.home_linear);

        home_image_buttom_hint = findViewById(R.id.home_image_buttom_hint);
        home_txt_buttom_xx_num = findViewById(R.id.home_txt_buttom_xx_num);

        try {
            setHomeButtomClickEffic(INDEX_BUTTOM_SY);
        } catch (Exception e) {

        }
        home_linear.addView(initViewApply());
    }

    @Override
    protected void initActivityView() {
        setContentView(R.layout.activity_home_layout);
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initAction() {
        home_txt_buttom_sy_rel.setOnClickListener(this);
        home_txt_buttom_xx_rel.setOnClickListener(this);
        home_txt_buttom_wd_rel.setOnClickListener(this);
    }

    private void setHomeButtomClickEffic(int index) {
        switch (index) {
            case INDEX_BUTTOM_SY:
                home_txt_buttom_sy.setTextColor(Color.parseColor("#3da8f5"));
                Glide.with(this).load(R.drawable.home_button_buttom_sy_y).placeholder(R.drawable.home_button_buttom_sy_y).into(home_txt_buttom_sy_image);
                home_txt_buttom_xx.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_xx_n).placeholder(R.drawable.home_button_buttom_xx_n).into(home_txt_buttom_xx_image);
                home_txt_buttom_wd.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_wd_n).placeholder(R.drawable.home_button_buttom_wd_n).into(home_txt_buttom_wd_image);
                break;
            case INDEX_BUTTOM_XX:
                home_txt_buttom_sy.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_sy_n).placeholder(R.drawable.home_button_buttom_sy_n).into(home_txt_buttom_sy_image);
                home_txt_buttom_xx.setTextColor(Color.parseColor("#3da8f5"));
                Glide.with(this).load(R.drawable.home_button_buttom_xx_y).placeholder(R.drawable.home_button_buttom_xx_y).into(home_txt_buttom_xx_image);
                home_txt_buttom_wd.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_wd_n).placeholder(R.drawable.home_button_buttom_wd_n).into(home_txt_buttom_wd_image);
                break;
            case INDEX_BUTTOM_WD:
                home_txt_buttom_sy.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_sy_n).placeholder(R.drawable.home_button_buttom_sy_n).into(home_txt_buttom_sy_image);
                home_txt_buttom_xx.setTextColor(Color.parseColor("#666666"));
                Glide.with(this).load(R.drawable.home_button_buttom_xx_n).placeholder(R.drawable.home_button_buttom_xx_n).into(home_txt_buttom_xx_image);
                home_txt_buttom_wd.setTextColor(Color.parseColor("#3da8f5"));
                Glide.with(this).load(R.drawable.home_button_buttom_wd_y).placeholder(R.drawable.home_button_buttom_wd_y).into(home_txt_buttom_wd_image);
            default:
                break;
        }
    }


    private HomeApplyView applyView;

    private View initViewApply() {
        if (view_home_sy == null) {
            applyView = new HomeApplyView(getContext(), LayoutInflater.from(getContext()).inflate(R.layout.view_home_apply, home_linear, false));
            applyView.initView();
            applyView.initData();
            applyView.initAction();
            view_home_sy = applyView.getHomeApplyView();
            new ViewUtil().logViewRealWH(view_home_sy, "首页");
        }
        return view_home_sy;
    }

    private HomeMessageView messageActivity;

    private View initViewMessage() {
        if (view_home_xx == null) {

            view_home_xx = LayoutInflater.from(getContext()).inflate(R.layout.view_home_message, home_linear, false);
            messageActivity = new HomeMessageView(view_home_xx, getContext());
            messageActivity.initView();
            messageActivity.initData();
            messageActivity.initAction();
            new ViewUtil().logViewRealWH(view_home_xx, "消息");
        }
        return view_home_xx;
    }

    HomeMyselfView myselfActivity;

    private View initViewMyself() {
        if (view_home_wd == null) {
            view_home_wd = LayoutInflater.from(getContext()).inflate(R.layout.activity_myself, home_linear, false);
            myselfActivity = new HomeMyselfView(view_home_wd, this);
            myselfActivity.initView();
            myselfActivity.initData();
            myselfActivity.initAction();
            new ViewUtil().logViewRealWH(view_home_wd, "我的");
        }
        return view_home_wd;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_txt_buttom_sy_rel:
                setHomeButtomClickEffic(INDEX_BUTTOM_SY);
                changeView(INDEX_BUTTOM_SY);
                break;
            case R.id.home_txt_buttom_xx_rel:
                setHomeButtomClickEffic(INDEX_BUTTOM_XX);
                changeView(INDEX_BUTTOM_XX);
                break;
            case R.id.home_txt_buttom_wd_rel:

                setHomeButtomClickEffic(INDEX_BUTTOM_WD);
                changeView(INDEX_BUTTOM_WD);
                break;
            default:
                break;
        }
    }

    private void changeView(int index) {
        if (index != currentIndex) {
            currentIndex = index;
            home_linear.removeAllViews();
            switch (index) {
                case INDEX_BUTTOM_SY:
                    home_linear.addView(initViewApply());
                    break;
                case INDEX_BUTTOM_XX:
                    home_linear.addView(initViewMessage());
                    break;
                case INDEX_BUTTOM_WD:
                    home_linear.addView(initViewMyself());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }

    @Override
    protected void onResume() {
        super.onResume();
        initContants();
        if (!applyView.isFirst) {
            applyView.freshApplYList();
            applyView.freshMessageList();
        }
        if (messageActivity != null) {
            if (!messageActivity.isFirst) {
                messageActivity.freshMessageList(false);
            }
            messageActivity.freshTXL();
        }
        home_txt_buttom_xx_num.setText((new SqliteHelper().rawQuery("select * from client_notice where read=?", "false").size() + new SqliteHelper().rawQuery("select * from lt where isread=?", "false").size() + new SqliteHelper().rawQuery("select * from addfriend where deal=?   and isread=?", BodyAdd.DEAL_NO, "false").size()) + "");
    }

    @Subscribe(priority = 100)
    public void onEventMainThread(MyEventBus event) {
        int num = (new SqliteHelper().rawQuery("select read from client_notice where read=?", "false").size() + new SqliteHelper().rawQuery("select isread from lt where isread=?", "false").size() + new SqliteHelper().rawQuery("select deal,isread from addfriend where deal=?   and isread=?", BodyAdd.DEAL_NO, "false").size());
        Log.d("zsp", TimeTool.TimeStamp2date(System.currentTimeMillis(), "HH:mm:sss") + ">>>>>>>" + num);
        home_txt_buttom_xx_num.setText(num + "");
        if (event.getMsg().equals(HintTool.Receive_Push_Message)) {//接受到推送更新数据
            applyView.freshMessageList();
            if (messageActivity != null) {
                messageActivity.freshMessageList(true);
            }
            messageActivity.freshTXL();
        } else if (event.getMsg().equals(HintTool.Del_Message)) {
            applyView.freshMessageList();
        }
    }

    // 相册
    private final int PHOTO_REQUEST = 3;
    // 相机
    private final int CAMERA_REQUEST = 4;
    // 裁剪图片
    private final int PHOTO_CLIP = 6;
    private final int PHONENUMBER_CODE = 1;

    private ProgressDialog pd;
    private Bitmap photo;
    private File filepath;

    /**
     * 首页二维码扫面返回函数
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // 获取二维码内容
            String url = data.getExtras().getString("result");
            if (IsNull.isNotNull(url)) {
                // 提取二维码中的用户编号
                url = new URLHelper().getParames(url, "userid");
                if (IsNull.isNotNull(url)) {
                    Intent intent = new Intent(this, FriendDetailActivity.class);
                    intent.putExtra("fqr", url);
                    this.startActivity(intent);
                } else {
                }
            }
        } catch (Exception e) {
        }

        switch (requestCode) {
            // 相机
            case CAMERA_REQUEST:
                switch (resultCode) {
                    case -1:// -1表示拍照成功
                        File file = new File(Environment.getExternalStorageDirectory() + "/hand.jpg");// 保存图片
                        if (file.exists()) {
                            // 对相机拍照照片进行裁剪
                            photoClip(Uri.fromFile(file));
                        }
                }
                break;

            case PHOTO_REQUEST:// 从相册取
                if (data != null) {
                    Uri uri = data.getData();
                    // 对相册取出照片进行裁剪
                    photoClip(uri);
                }
                break;
            // 裁剪图片
            case PHOTO_CLIP:
                if (data != null) {
                    // 完成
                    getWRITE_EXTERNAL_STORAGE();
                }
                // 上传完成将照片写入imageview与用户进行交互
                myselfActivity.setWindowDismiss();
                break;
        }
    }

    /**
     * 获取写入权限
     */
    public void getWRITE_EXTERNAL_STORAGE() {
        new RxPermissions(this).
                request(Manifest.permission.WRITE_EXTERNAL_STORAGE).
                subscribe(observer);
    }

    /**
     * 提示权限
     */
    private Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
            ToastUtils.Toast(HomePageActivity.this, "获取权限失败");
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                try {
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
//                    获得图片路径
                    filepath = UploadUtil.saveFile(photo, Environment.getExternalStorageDirectory().toString(), "hand.jpg");
                    // 上传照片
                    myselfActivity.toUploadFile(photo, filepath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.Toast(HomePageActivity.this, "获取权限失败");
            }
        }
    };

    /****
     * 调用系统自带切图工具对图片进行裁剪 微信也是
     *
     * @param uri
     */
    public Uri uritempFile;

    public void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
//        intent.putExtra("return-data", true);

        //裁剪后的图片Uri路径，uritempFile为Uri类变量
        uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, PHOTO_CLIP);
    }

    /**
     * 初始化通讯录数据
     */
    private void initContants() {
        // TODO Auto-generated method stub
        Map<String, String> params = MapTools.buildMap(new String[][]{{"userid", Constants.number},
                {"function_id", "20150120"}, {"ticket", Ticket.getFunctionTicket("20150120")}});
        VolleyRequest.RequestPost(_链接地址导航.DC.圈子好友列表.getUrl(), params, new VolleyInterface() {
            @Override
            public void onMySuccess(String result) {
                try {
                    if (IsNull.isNotNull(result)) {
                        TXLEntity entity = GsonImpl.get().toObject(result, TXLEntity.class);
                        if (JSONTool.SUCCESS.equals(entity.getCode())) {
                            for (int i = 0; i < entity.getContent().size(); i++) {
                                new SqliteHelper().execSQL("insert or replace into friend(FRIEND_ID,name,szm,userid) values(?,?,?,?)",
                                        entity.getContent().get(i).getHYBH(),
                                        entity.getContent().get(i).getHYBZ(),
                                        IsNull.isNotNull(entity.getContent().get(i).getHYBZ()) ? pinyin
                                                .getAllLetter(entity.getContent().get(i).getHYBZ())
                                                .substring(0, 1) : "#",
                                        Constants.number);
                                new SqliteHelper().execSQL("insert into addFriend(id,userid,type,fqr,fqrname,jsr,jsrname,fjnr,fssj,faceaddress,deal,isread,m_deal) values(?,?,?,?,?,?,?,?,?,?,?,?,?)", entity.getContent().get(i).getHYBH(), Constants.number, "", entity.getContent().get(i).getHYBH(), entity.getContent().get(i).getHYBZ(), Constants.number, "", new SqliteHelper().rawQuery("select * from user").get(0).get("name"), TimeTool.TimeStamp2date(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"), "", BodyAdd.DEAL_AGREE, "true", "true");
                            }
                        }
                    }
//					categoryContactsViewUI();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }

            @Override
            public void onMyError(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    public void re(boolean t) {
        Log.d("zsp", TimeTool.TimeStamp2date(System.currentTimeMillis(), "HH:mm:sss") + "<<<<<<<" + (new SqliteHelper().rawQuery("select read from client_notice where read=?", "false").size() + new SqliteHelper().rawQuery("select isread from lt where isread=?", "false").size() + new SqliteHelper().rawQuery("select deal,isread from addfriend where deal=?   and isread=?", BodyAdd.DEAL_NO, "false").size()));
    }

    //--------------使用onKeyDown()双击退出--------------

    //记录用户首次点击返回键的时间
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - firstTime > 2000) {
                ToastUtils.Toast(this, "再按一次退出程序");
                firstTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}