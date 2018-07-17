package com.example.app3;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import yh.app.utils.ToastUtils;

public class BitmapUtil {
    /**
     * 获取圆角图片
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = null;
        try {
            if (bitmap == null)
                return null;
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (OutOfMemoryError e) {
            System.gc();
            output = null;
        }
        return output;
    }

    /**
     * 根据ID转换Bitmap
     *
     * @param context
     * @param id
     * @return
     */
    public static Bitmap getBitmap(Context context, int id) {
        Drawable drawable = context.getResources().getDrawable(id);
        return getBitmap(drawable);
    }

    /**
     * Drawable转换为bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap getBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof ColorDrawable) {
            Rect rect = drawable.getBounds();
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;
            int color = ((ColorDrawable) drawable).getColor();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            canvas.drawARGB(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 质量压缩方法。为了防止内存图片过大导致的异常，图片文件最大为100kb
     *
     * @param image   图片
     * @param maxSize 图片文件大小，单位kb
     * @return
     */
    public static Bitmap compressBitmapByQuality(Bitmap image, long maxSize) {
        if (maxSize > 100) {
            maxSize = 100;
        } else if (maxSize < 0) {
            maxSize = 10;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = 100;
        int perDecreate = 10;
        do {
            if (quality < 0) {
                break;
            }
            baos.reset();
            image.compress(Bitmap.CompressFormat.JPEG, quality, baos);
            Log.e("gsdx", "质量:" + quality + "  大小:" + (baos.toByteArray().length / 1024));
            quality -= perDecreate;
        } while (baos.toByteArray().length / 1024 > maxSize);

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        try {
            baos.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bitmap;
    }

    /**
     * 等比例调整图片大小
     *
     * @param image
     * @return
     */
    public static Bitmap comp(Bitmap image, float resultWidth) {
        float imageWidth = image.getWidth();
        float imageHeight = image.getHeight();
        float hh = imageHeight / (imageWidth / resultWidth);// 这里设置高度为800f
        float ww = resultWidth;// 这里设置宽度为480f
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        // 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
        if (baos.toByteArray().length / 1024 > 1024) {
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 80, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        // float hh = 800f;// 这里设置高度为800f
        // float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) { // 如果高度高的话根据高度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be; // 设置缩放比例
        // newOpts.inPreferredConfig = Config.RGB_565;//降低图片从ARGB888到RGB565

        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);

        return compressBitmapByQuality(bitmap, 100);// 压缩好比例大小后再进行质量压缩

        // return bitmap;
    }

    /**
     * 模糊图片的具体方法
     *
     * @param context 上下文对象
     * @param image   需要模糊的图片
     * @return 模糊处理后的图片
     */
    public static Bitmap blurBitmap(Context context, Bitmap image, float blurRadius) {
        int BITMAP_SCALE = 1;
        // 计算图片缩小后的长宽
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        // 将缩小后的图片做为预渲染的图片
        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        // 创建一张渲染后的输出图片
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        // 创建RenderScript内核对象
        RenderScript rs = RenderScript.create(context);
        // 创建一个模糊效果的RenderScript的工具对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        // 由于RenderScript并没有使用VM来分配内存,所以需要使用Allocation类来创建和分配内存空间
        // 创建Allocation对象的时候其实内存是空的,需要使用copyTo()将数据填充进去
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);

        // 设置渲染的模糊程度, 25f是最大模糊度
        blurScript.setRadius(blurRadius);
        // 设置blurScript对象的输入内存
        blurScript.setInput(tmpIn);
        // 将输出数据保存到输出内存中
        blurScript.forEach(tmpOut);

        // 将数据填充到Allocation中
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }

    // 保存图片到手机指定目录
    public static void savaBitmap(Context context,String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = Environment.getExternalStorageDirectory().getCanonicalPath() + "/MyImg";
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                imgName = filePath + "/" + imgName;
                fos = new FileOutputStream(imgName);
                fos.write(bytes);
                File f = new File(imgName);//通知更新系统图库
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(f);
                intent.setData(uri);
                context.sendBroadcast(intent);
                ToastUtils.Toast(context,"图片已保存到系统相册");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(context, "请检查SD卡是否可用", Toast.LENGTH_SHORT).show();
        }
    }
}
