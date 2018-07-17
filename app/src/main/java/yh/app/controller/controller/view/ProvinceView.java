package yh.app.controller.controller.view;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.example.jpushdemo.ExampleApplication;
import yh.app.appstart.lg.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import yh.app.controller.controller.model.CityModel;
import yh.app.controller.controller.model.DistrictModel;
import yh.app.controller.controller.model.ProvinceModel;
import yh.app.controller.controller.province.ArrayWheelAdapter;
import yh.app.controller.controller.province.OnWheelChangedListener;
import yh.app.controller.controller.province.WheeProvincelView;
import yh.app.controller.controller.server.XmlParserHandler;
import yh.app.model.ProvincesModel;
import yh.app.utils.JsonUtils;

/**
 * 省市区控件
 */

public class ProvinceView implements View.OnClickListener ,OnWheelChangedListener{
    private Context context;
    private LayoutInflater inflater;
    private View view;
    private Button btnCancel;
    private TextView tvTitle;
    private Button btnSubmit;
    private TextView textView;


    /**
     * 所有省
     */
    protected String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    protected String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    protected String mCurrentCityName;
    /**
     * 当前区的名称
     */
    protected String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    protected String mCurrentZipCode = "";


    private PopupWindow window;
    private WheeProvincelView province;
    private WheeProvincelView city;
    private WheeProvincelView district;

    public ProvinceView(Activity context) {
        this.context=context;
        inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.pickerview_options, null, false);
        initView(view);
        setUpData();
    }

    private void initView(View view) {
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        province = (WheeProvincelView) view.findViewById(R.id.province);
        city = (WheeProvincelView)view.findViewById(R.id.city);
        district = (WheeProvincelView) view.findViewById(R.id.district);
    }

    private void setUpData() {
        initProvinceDatas();
        province.setViewAdapter(new ArrayWheelAdapter<>(context, mProvinceDatas));
        // 设置可见条目数量
        province.setVisibleItems(10);
        city.setVisibleItems(10);
        district.setVisibleItems(10);
        updateCities();
        updateAreas();
    }
    /**
     * 解析省市区的XML数据
     */

    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset =context.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            //*/ 初始化默认选中的省、市、区
            if (provinceList != null && !provinceList.isEmpty()) {
                mCurrentProviceName = provinceList.get(0).getName();
                List<CityModel> cityList = provinceList.get(0).getCityList();
                if (cityList != null && !cityList.isEmpty()) {
                    mCurrentCityName = cityList.get(0).getName();
                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
                    mCurrentDistrictName = districtList.get(0).getName();
                    mCurrentZipCode = districtList.get(0).getZipcode();
                }
            }
            //*/
            mProvinceDatas = new String[provinceList.size()];
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = provinceList.get(i).getName();
                List<CityModel> cityList = provinceList.get(i).getCityList();
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).getName();
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    String[] distrinctNameArray = new String[districtList.size()];
                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.getName();
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
    public void showWindow(View btn,TextView txtview) {
       this.textView=txtview;
        //设置窗体大小
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        window.setFocusable(true);
        window.setOutsideTouchable(false);
        window.setAnimationStyle(android.R.style.Animation_InputMethod);
        window.showAtLocation(btn, Gravity.BOTTOM, 0, 0);
        // 添加change事件
        province.addChangingListener(this);
        // 添加change事件
        city.addChangingListener(this);
        // 添加change事件
        district.addChangingListener(this);

    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = province.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[] { "" };
        }
        city.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
        city.setCurrentItem(0);
        updateAreas();
    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = city.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mDistrictDatasMap.get(mCurrentCityName);

        if (areas == null) {
            areas = new String[] { "" };
        }
        district.setViewAdapter(new ArrayWheelAdapter<String>(context, areas));
        district.setCurrentItem(0);
    }

    public void dismiss() {
        if (window.isShowing()) {
            window.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                dismiss();
                break;
            case R.id.btnSubmit:
                textView.setText("");
                textView.setText(mCurrentProviceName+mCurrentCityName+mCurrentDistrictName);
                dismiss();
                break;
        }
    }


    public void setTitle(String title){
        if (!TextUtils.isEmpty(title)){
            tvTitle.setText(title);
        }
    }



    @Override
    public void onChanged(WheeProvincelView wheel, int oldValue, int newValue) {
        if (wheel == province) {
            updateCities();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        } else if (wheel == city) {
            updateAreas();
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        } else if (wheel == district) {
            mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
            mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
        }
    }


    public String getProvinceData(){
        return mCurrentZipCode;
    }
}
