package yh.app.acticity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.yhkj.cqgyxy.R;

import yh.tool.widget.LoadDiaog;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

public class JiYuActivity extends WhiteActivity {

    private TextView btnState;
    private String FunctionID;
    private WebView jy_webview;
    private LoadDiaog diaog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ji_yu);
       FunctionID=getIntent().getStringExtra("function_id");
        initView();
        setWebsetts();
    }

    private void initView() {
    	diaog=new LoadDiaog(this);
    	diaog.show();
        btnState = (TextView) findViewById(R.id.btn_state);
        jy_webview=(WebView) findViewById(R.id.jy_web);
        btnState.setEnabled(false);
        
        btnState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent=new Intent(JiYuActivity.this, WelcomeStudentActivity.class);
            	intent.putExtra("FunctionID", FunctionID);
                startActivity(intent);
                finish();
            }
        });
        String url = _链接地址导航.Ydyx.JYWEBURL.getUrl()+"a=a";
        jy_webview.loadUrl(url);
        
     
    }
    
    @SuppressLint("SetJavaScriptEnabled")
	private void setWebsetts(){
    	WebSettings settings=jy_webview.getSettings();
    	//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
    	settings.setJavaScriptEnabled(true);  

    	

    	//设置自适应屏幕，两者合用
    	settings.setUseWideViewPort(true); //将图片调整到适合webview的大小 
    	settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

    	//缩放操作
    	settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
    	settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
    	settings.setDisplayZoomControls(false); //隐藏原生的缩放控件

    	//其他细节操作
    	settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存 
    	settings.setAllowFileAccess(true); //设置可以访问文件 
    	settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口 
    	settings.setLoadsImagesAutomatically(true); //支持自动加载图片
    	settings.setDefaultTextEncodingName("utf-8");//设置编码格式
    	
    	
    	   jy_webview.setWebViewClient(new WebViewClient(){
               @Override
               public boolean shouldOverrideUrlLoading(WebView view, String url) {
                   view.loadUrl(url);
               return true;
               }
           });
    	   //监听当前网页进度
    	jy_webview.setWebChromeClient(new WebChromeClient(){
    		@Override
    		public void onProgressChanged(WebView view, int newProgress) {
    			
    			super.onProgressChanged(view, newProgress);
    			if (newProgress>=0) {
					if (diaog.isShowing()) {
						diaog.dismiss();
					}
					btnState.setEnabled(true);
				}
    		}
    	});
    }
    
    

    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	jy_webview.destroy();
    }
    
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	jy_webview.onPause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	jy_webview.onResume();
    }
    

}
