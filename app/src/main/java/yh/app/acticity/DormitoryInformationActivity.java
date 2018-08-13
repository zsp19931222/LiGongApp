package yh.app.acticity;



import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yhkj.cqgyxy.R;

import yh.tool.widget.WhiteActivity;

public class DormitoryInformationActivity extends WhiteActivity {

    private TextView txtDormitorySchoolname;
    private TextView txtDormitoryClassroom;
    private TextView txtDormitoryNumber;
    private TextView txtDormitoryType;
    private TextView txtDormitoryBednumber;
    private TextView txtDormitoryShoufeibiaozun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dormitory_information);
        initView();
    }
    public void back(View view){
        finish();
    }

    private void initView() {
        //校区名称
        txtDormitorySchoolname = (TextView) findViewById(R.id.txt_dormitory_schoolname);
        //校栋名称
        txtDormitoryClassroom = (TextView) findViewById(R.id.txt_dormitory_classroom);
        //寝室编号
        txtDormitoryNumber = (TextView) findViewById(R.id.txt_dormitory_number);
        //寝室类型
        txtDormitoryType = (TextView) findViewById(R.id.txt_dormitory_type);
        //床位号
        txtDormitoryBednumber = (TextView) findViewById(R.id.txt_dormitory_bednumber);
        //收费标准
        txtDormitoryShoufeibiaozun = (TextView) findViewById(R.id.txt_dormitory_shoufeibiaozun);
    }
}
