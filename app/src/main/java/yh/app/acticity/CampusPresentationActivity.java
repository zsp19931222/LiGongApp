package yh.app.acticity;



import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import yh.app.appstart.lg.R;

import yh.tool.widget.WhiteActivity;

/**
 * 初认识校园
 */
public class CampusPresentationActivity extends WhiteActivity implements View.OnClickListener{

    private RelativeLayout rlCampuspresentationSchollsurvey;
    private RelativeLayout rlCampuspresentationSchollhuaxi;
    private RelativeLayout rlCampuspresentationSchollliangjiang;
    private RelativeLayout rlCampuspresentationSchollphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campus_presentation);
        initView();
    }

    public void back(View view){
        finish();
    }

    private void initView() {
        //校园概况
        rlCampuspresentationSchollsurvey = (RelativeLayout) findViewById(R.id.rl_campuspresentation_schollsurvey);
        rlCampuspresentationSchollsurvey.setOnClickListener(this);
        //花溪校区一览
        rlCampuspresentationSchollhuaxi = (RelativeLayout) findViewById(R.id.rl_campuspresentation_schollhuaxi);
        rlCampuspresentationSchollhuaxi.setOnClickListener(this);
        //两江校区一览
        rlCampuspresentationSchollliangjiang = (RelativeLayout) findViewById(R.id.rl_campuspresentation_schollliangjiang);
        rlCampuspresentationSchollliangjiang.setOnClickListener(this);
        //常用电话
        rlCampuspresentationSchollphone = (RelativeLayout) findViewById(R.id.rl_campuspresentation_schollphone);
        rlCampuspresentationSchollphone.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
           switch (v.getId()){
               case R.id.rl_campuspresentation_schollsurvey:
                   //校园概况
                   Toast.makeText(this, "校园概况", Toast.LENGTH_SHORT).show();
                   break;

               case R.id.rl_campuspresentation_schollhuaxi:
                   //花溪校区一览
                   Toast.makeText(this, "花溪校区一览", Toast.LENGTH_SHORT).show();
                   break;
               case R.id.rl_campuspresentation_schollliangjiang:
                   //两江校区一览
                   Toast.makeText(this, "两江校区一览", Toast.LENGTH_SHORT).show();
                   break;
               case R.id.rl_campuspresentation_schollphone:
                   //常用电话
                   Toast.makeText(this, "常用电话", Toast.LENGTH_SHORT).show();
                   break;
           }
    }
}
