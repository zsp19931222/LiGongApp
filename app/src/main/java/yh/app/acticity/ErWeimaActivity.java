package yh.app.acticity;


import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import yh.app.appstart.lg.R;

import yh.app.utils.ErWeiMaUtil;
import yh.tool.widget.BuleActivity;

public class ErWeimaActivity extends BuleActivity {

    private ImageView imgErweimaTiaoxinma;
    private ImageView imgErweimaErweima;
    private TextView txt_back;
    private String ewm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_er_weima);
        ewm=getIntent().getStringExtra("ewm");
        initView();
    }

   

    private void initView() {
        imgErweimaTiaoxinma = (ImageView) findViewById(R.id.img_erweima_tiaoxinma);
        imgErweimaErweima = (ImageView) findViewById(R.id.img_erweima_erweima);
        txt_back=(TextView) findViewById(R.id.txt_back);
        txt_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
        if (ewm==null) {
			return;
		}
        
        try {
            imgErweimaTiaoxinma.setImageBitmap(ErWeiMaUtil.creatBarcode(this,ewm,400,150,false));
            imgErweimaErweima.setImageBitmap(ErWeiMaUtil.createQRImage(ewm, 200,200));
        }catch (Exception e){
        	Toast.makeText(this, "图形生成出错了", Toast.LENGTH_SHORT).show();
        }





    }
}
