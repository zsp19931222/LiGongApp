package yh.app.acticity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.app3.activity.BrowserActivity;
import com.example.jpushdemo.ExampleApplication;
import com.yhkj.cqgyxy.R;

import org.androidpn.push.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import yh.app.adapter.PersnaldataDetailsAdapter;
import yh.app.model.PersnaldataDetailsModel;
import yh.app.tool.Ticket;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;
import yh.tool.widget.MyListview;
import yh.tool.widget.WhiteActivity;
import 云华.智慧校园.工具._链接地址导航;

public class PersnaldataDetailsActivity extends WhiteActivity {

    private ImageView imgPersnaldatadeTailsHand;
    private TextView txtPersnaldataDetailsRefilling;
    private MyListview lvPersnaldataDetails;

    private PersnaldataDetailsModel persnaldataDetailsModel;
    private List<PersnaldataDetailsModel.ContentBean> list;
    private PersnaldataDetailsAdapter persnaldataDetailsAdapter;
    
    
    
    private TextView txtPersnaldatadetailsName;
    private TextView txtPersnaldatadetailsNameXb;
    private TextView txtPersnaldatadetailsNameCs;
    private TextView txtPersnaldatadetailsNameXy;
    private TextView txtPersnaldatadetailsNameZy;
    private TextView txtPersnaldatadetailsNameBj;
    private TextView txtPersnaldatadetailsNameKsh;
    private TextView txtPersnaldatadetailsNameXh;
    private TextView txtPersnaldatadetailsNameSfz;
    private TextView txtPersnaldatadetailsNameLqsf;
    private TextView txt_back;
    
    
    private String FunctionID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persnaldata_details);
        initView();
        initData();
        initProcessor();
    }

    public void back(View view) {
        finish();
    }

    private void initView() {
    	
    	FunctionID = getIntent().getStringExtra("FunctionID");
        imgPersnaldatadeTailsHand = (ImageView) findViewById(R.id.img_persnaldatade_tails_hand);
        txtPersnaldataDetailsRefilling = (TextView) findViewById(R.id.txt_persnaldata_details_refilling);
        
        
        
        txtPersnaldatadetailsName = (TextView) findViewById(R.id.txt_persnaldatadetails_name);
        txtPersnaldatadetailsNameXb = (TextView) findViewById(R.id.txt_persnaldatadetails_name_xb);
        txtPersnaldatadetailsNameCs = (TextView) findViewById(R.id.txt_persnaldatadetails_name_cs);
        txtPersnaldatadetailsNameXy = (TextView) findViewById(R.id.txt_persnaldatadetails_name_xy);
        txtPersnaldatadetailsNameZy = (TextView) findViewById(R.id.txt_persnaldatadetails_name_zy);
        txtPersnaldatadetailsNameBj = (TextView) findViewById(R.id.txt_persnaldatadetails_name_bj);
        txtPersnaldatadetailsNameKsh = (TextView) findViewById(R.id.txt_persnaldatadetails_name_ksh);
        txtPersnaldatadetailsNameXh = (TextView) findViewById(R.id.txt_persnaldatadetails_name_xh);
        txtPersnaldatadetailsNameSfz = (TextView) findViewById(R.id.txt_persnaldatadetails_name_sfz);
        txtPersnaldatadetailsNameLqsf = (TextView) findViewById(R.id.txt_persnaldatadetails_name_lqsf);
       txt_back=(TextView) findViewById(R.id.txt_back);
       txt_back.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	});
    }
    

    private void initData(){
    	Map<String, String> params = new HashMap<>();
		params.put("userid", Constants.number);
		params.put("ticket", Ticket.getFunctionTicket("20170601"));
		params.put("function_id", "20170601");
    	VolleyRequest.RequestPost(_链接地址导航.Ydyx.GETUSERINFO.getUrl(), params, new VolleyInterface() {
			
			@Override
			public void onMySuccess(String result) {
				
				try {
					persnaldataDetailsModel=ExampleApplication.getInstance().getGson().fromJson(result, PersnaldataDetailsModel.class);
					 bindData(persnaldataDetailsModel.getContent());
					
				} catch (Exception e) {
					
				}
			}
			
			@Override
			public void onMyError(VolleyError error) {
				Toast.makeText(PersnaldataDetailsActivity.this, "系统异常", Toast.LENGTH_SHORT).show();
			}
		});
    	
       
       

    }
    
    private void bindData(PersnaldataDetailsModel.ContentBean bean) throws Exception{
    	txtPersnaldatadetailsName.setText(bean.getXm());
    	txtPersnaldatadetailsNameXb.setText(bean.getXbmc());
    	txtPersnaldatadetailsNameCs.setText(bean.getCsny());
    	txtPersnaldatadetailsNameXy.setText(bean.getBmmc());
    	txtPersnaldatadetailsNameZy.setText(bean.getZymc());
    	txtPersnaldatadetailsNameBj.setText(bean.getBjmc());
    	txtPersnaldatadetailsNameKsh.setText(bean.getKsh());
    	txtPersnaldatadetailsNameXh.setText(bean.getXh());
    	txtPersnaldatadetailsNameSfz.setText(bean.getSfzh());
    	txtPersnaldatadetailsNameLqsf.setText(bean.getLqsfmc());
    	
    }

    private void initProcessor(){
        txtPersnaldataDetailsRefilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//重新完善个人信息
            	toWeb(_链接地址导航.Ydyx.USERINFOURL.getUrl());
//             startActivity(new Intent(PersnaldataDetailsActivity.this,UserInfoCodeActivity.class));
            }
        });
    }
    
    
	/**
	 * 跳转web
	 * 
	 * @param url
	 */
	private void toWeb(String url) {
		String ticket = Ticket.getFunctionTicket(FunctionID);
		try {
			Intent intent = new Intent(PersnaldataDetailsActivity.this, BrowserActivity.class);
			String addString = "?";
			if (url.contains("?")) {
				addString = "&";
			}
			intent.putExtra("url", url + addString + "userid=" + Constants.number + "&ticket=" + ticket);
			startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(PersnaldataDetailsActivity.this, "系统出错了", Toast.LENGTH_SHORT).show();
		}

	}
}
