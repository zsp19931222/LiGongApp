package com.example.app3.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.app3.tool.GlideRoundTransform;
import com.example.app3.utils.GlideLoadUtils;
import com.example.app3.view.MyTitleView;
import com.example.smartclass.base.BaseRecyclerViewActivity;
import com.example.smartclass.eventbus.MessageEvent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import yh.app.tool.SqliteHelper;
import yh.app.utils.DensityUtil;
import yh.app.utils.GetWindowsWH;
import 云华.智慧校园.工具._链接地址导航;

/**
 * Created by Administrator on 2017/9/21.
 */

public class QRActivity extends BaseRecyclerViewActivity {
    @BindView(R.id.qrcode_title)
    MyTitleView qrcodeTitle;
    @BindView(R.id.qrcode_image_head)
    ImageView qrcodeImageHead;
    @BindView(R.id.qrcode_text_name)
    TextView qrcodeTextName;
    @BindView(R.id.qrcode_image_sex)
    ImageView qrcodeImageSex;
    @BindView(R.id.qrcode_iamge_major)
    ImageView qrcodeIamgeMajor;
    @BindView(R.id.qrcode_text_major)
    TextView qrcodeTextMajor;
    @BindView(R.id.qrcode_lin_major)
    LinearLayout qrcodeLinMajor;
    @BindView(R.id.qrcode_image_qr)
    ImageView qrcodeImageQr;
    @BindView(R.id.qrcode_text_college)
    TextView qrcodeTextCollege;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr;
    }

    @Override
    protected void setTitle(Context context) {
        qrcodeTitle.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadRecyclerViewData(Context context) {

    }

    @Override
    protected void init(Context context) {
        getUserInfo(this);

    }

    @Override
    @Subscribe
    public void onEventMainThread(MessageEvent event) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }




    /**
     * 生成二维码
     *
     * @param content 生成规则
     * @param width   二维码宽度
     * @param height  二维码高度
     */
    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二维码添加Logo
     *
     * @param qrBitmap   二维码bitmap
     * @param logoBitmap logo bitmap
     */
    private Bitmap addLogo(Bitmap qrBitmap, Bitmap logoBitmap) {
        int qrBitmapWidth = qrBitmap.getWidth();
        int qrBitmapHeight = qrBitmap.getHeight();
        int logoBitmapWidth = logoBitmap.getWidth();
        int logoBitmapHeight = logoBitmap.getHeight();
        Bitmap blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(blankBitmap);
        canvas.drawBitmap(qrBitmap, 0, 0, null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        float scaleSize = 1.0f;
        while ((logoBitmapWidth / scaleSize) > (qrBitmapWidth / 5) || (logoBitmapHeight / scaleSize) > (qrBitmapHeight / 5)) {
            scaleSize *= 2;
        }
        float sx = 1.0f / scaleSize;
        canvas.scale(sx, (sx), qrBitmapWidth / 2, qrBitmapHeight / 2);
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) / 2, (qrBitmapHeight - logoBitmapHeight) / 2, null);
        canvas.restore();
        return blankBitmap;
    }

    private List<Map<String, String>> applyList;

    /**
     * 获取本地用户信息
     */
    public void getUserInfo(Context context) {
        applyList = new SqliteHelper().rawQuery("select * from userinfo4");
        qrcodeTextName.setText(applyList.get(0).get("username"));
        if ("1".equals(applyList.get(0).get("xb"))) {
            Glide.with(context).load(R.drawable.sex_male_2x).error(R.drawable.error).into(qrcodeImageSex);
        } else {
            Glide.with(context).load(R.drawable.sex_famale_2x).error(R.drawable.error).into(qrcodeImageSex);
        }
        qrcodeTextCollege.setText(applyList.get(0).get("xydm"));
        qrcodeTextMajor.setText(applyList.get(0).get("zydm"));
        if (!GlideLoadUtils.isDestroy((Activity) context)) {
            Glide.with(context)
                    .load(applyList.get(0).get("txdz"))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String s, Target<GlideDrawable> target, boolean b) {
                            int w = GetWindowsWH.GetW(QRActivity.this);
                            Bitmap bitmap = generateBitmap(_链接地址导航.UIA.下载页面.getUrl() + Constants.number, w / 3 * 2, w / 3 * 2);
                            Bitmap logoBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.np);
                            Bitmap qrBitmap = addLogo(bitmap, logoBitmap);
                            handler.sendMessage(handler.obtainMessage(0, qrBitmap));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable glideDrawable, String s, Target<GlideDrawable> target, boolean b, boolean b1) {
                            new getUserImageLogo().executeOnExecutor(Executors.newCachedThreadPool());
                            return false;
                        }
                    })
                    .error(R.drawable.np)
                    .transform(new GlideRoundTransform(context, 10))
                    .into(qrcodeImageHead);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Bitmap qrBitmap = (Bitmap) msg.obj;
                    qrcodeImageQr.setImageBitmap(qrBitmap);
                    break;
            }
        }
    };

    private class getUserImageLogo extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Bitmap logoBitmap = Glide.with(QRActivity.this)
                        .load(applyList.get(0).get("txdz"))
                        .asBitmap() //必须
                        .centerCrop()
                        .transform(new GlideRoundTransform(QRActivity.this, 10))
                        .error(R.drawable.np)
                        .into(DensityUtil.dip2px(QRActivity.this, 35), DensityUtil.dip2px(QRActivity.this, 35))
                        .get();
                int w = GetWindowsWH.GetW(QRActivity.this);
                Bitmap bitmap = generateBitmap(_链接地址导航.UIA.下载页面.getUrl() + Constants.number, w / 3 * 2, w / 3 * 2);
                Bitmap qrBitmap = addLogo(bitmap, logoBitmap);
                handler.sendMessage(handler.obtainMessage(0, qrBitmap));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
