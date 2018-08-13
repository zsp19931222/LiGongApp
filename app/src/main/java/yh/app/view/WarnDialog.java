package yh.app.view;



import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.app.listeners.DialogOnClickListener;

/**
 * 保险协议弹出框
 */

public class WarnDialog implements View.OnClickListener {
    private View dialogview;
    private Context context;
    private LayoutInflater inflater;
    private ImageView imgDialogWarn;
    private TextView txtDialogConternt;
    private TextView txtDialogCancel;
    private TextView txtDialogSubmit;
    private DialogOnClickListener dialogOnClickListener;
    private PopupWindow dialogWindow;
    private LinearLayout lyDialogOut;
    //内容行数
    private int lineCount;
    /**
     * 选择需要显示的按钮
     */
    public enum HideType {
        CANCEL,
        CONFIRM,
        ALL,
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_dialog_cancel:
                //取消
              dialogOnClickListener.Cancel();
                break;
            case R.id.txt_dialog_submit:
               //确定
                dialogOnClickListener.Submit();
                break;
            case R.id.ly_dialog_out:
                //点击外部消失
                dismiss();
                break;
        }
    }

    public void setDialogOnClickListener(DialogOnClickListener dialogOnClickListener){
        this.dialogOnClickListener=dialogOnClickListener;
    }

    public WarnDialog(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        initView();
    }

    private void initView() {
        dialogview = inflater.inflate(R.layout.popwindow_dialog, null, false);
        imgDialogWarn = (ImageView) dialogview.findViewById(R.id.img_dialog_warn);
        txtDialogConternt = (TextView) dialogview.findViewById(R.id.txt_dialog_conternt);

        txtDialogCancel = (TextView) dialogview.findViewById(R.id.txt_dialog_cancel);
        txtDialogCancel.setOnClickListener(this);
        txtDialogSubmit = (TextView) dialogview.findViewById(R.id.txt_dialog_submit);
        txtDialogSubmit.setOnClickListener(this);
        lyDialogOut = (LinearLayout)dialogview.findViewById(R.id.ly_dialog_out);
        lyDialogOut.setOnClickListener(this);

        ViewTreeObserver viewTreeObserver=txtDialogConternt.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                lineCount= txtDialogConternt.getLineCount();
                if (lineCount<=1){
                    txtDialogConternt.setGravity(Gravity.CENTER);
                }

                return true;
            }
        });





    }





    /**
     * 弹出对话框
     *
     * @param view
     */
    public void showWindow(View view) {

        //设置窗体大小
        dialogWindow = new PopupWindow(dialogview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        dialogWindow.setFocusable(true);
        dialogWindow.setOutsideTouchable(false);
        dialogWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        dialogWindow.showAtLocation(view, Gravity.CENTER, 0, 50);
        dialogWindow.setBackgroundDrawable(new ColorDrawable());


    }

    /**
     * 隐藏弹出框
     */
    public void dismiss(){
        if (dialogWindow.isShowing()){
            dialogWindow.dismiss();
        }
    }

    /**
     * 设置对话框内容
     * @param contrnt
     */
    public void setContent(String contrnt) {
        if (TextUtils.isEmpty(contrnt)) {
            txtDialogConternt.setText(context.getResources().getString(R.string.str_dialog_content));
        }else {
            txtDialogConternt.setText(contrnt);
        }
    }

    /**
     * 设置取消按钮属性
     * @param s
     * @param textcolor 字体颜色
     * @param bgDrawable 背景
     */
    public void setCancelTextStyle(String s,int textcolor,int bgDrawable){
         if (!TextUtils.isEmpty(s)){
             txtDialogCancel.setText(s);
         }else{
             txtDialogCancel.setText(context.getString(R.string.pickerview_cancel));
         }

         if (textcolor!=0){
             txtDialogCancel.setTextColor(textcolor);
         }

         if (bgDrawable!=0){
             txtDialogCancel.setBackgroundColor(bgDrawable);
         }
    }



    /**
     * 设置确定按钮属性
     * @param s
     * @param textcolor
     * @param bgDrawable
     */
    public void setlConfirmTextStyle(String s,int textcolor,int bgDrawable){
        if (!TextUtils.isEmpty(s)){
        	txtDialogSubmit.setText(s);
        }else{
        	txtDialogSubmit.setText(context.getString(R.string.pickerview_cancel));
        }

        if (textcolor!=0){
        	txtDialogSubmit.setTextColor(textcolor);
        }

        if (bgDrawable!=0){
        	txtDialogSubmit.setBackgroundColor(bgDrawable);
        }
    }

    /**
     * 设置对话框标题栏图标
     * @param img
     */
    public void setDialogTitleImage(int img){
        if (img!=0){
            imgDialogWarn.setBackgroundResource(img);
        }else {
            imgDialogWarn.setBackgroundResource(R.drawable.ico_warn);
        }

    }

    /**
     * 控制对话框底部按钮显示
     * @param type
     */
    public void setDialogButton(HideType type){
        switch (type){
            case CANCEL:
                //只显示取消按钮
                txtDialogCancel.setVisibility(View.GONE);
                txtDialogSubmit.setBackground(ContextCompat.getDrawable(context,R.drawable.selector_radius_bottom_blue_click));
                break;
            case CONFIRM:
                //只显示确定按钮
                txtDialogSubmit.setVisibility(View.GONE);
                txtDialogSubmit.setBackground(ContextCompat.getDrawable(context,R.drawable.selector_radius_bottom_gray_click));
                break;
            case ALL:
                //都不显示
                txtDialogCancel.setVisibility(View.GONE);
                txtDialogSubmit.setVisibility(View.GONE);
                break;
            default:

                break;
        }
    }

}
