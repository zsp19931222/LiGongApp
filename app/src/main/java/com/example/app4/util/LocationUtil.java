package com.example.app4.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.smartclass.eventbus.MessageEvent;
import com.example.smartclass.util.TagUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import rx.Observer;

/**
 * Created by Administrator on 2018/8/8 0008.
 * 定位服务
 */

public class LocationUtil {
    public Context context;

    private LocationClient mLocationClient = null;
    private BDAbstractLocationListener myListener = new MyLocationListener();

    public LocationUtil(Context context) {
        this.context = context;
    }

    /**
     * 开始定位
     */
    public void startLocate() {
        new RxPermissions((Activity) context).
                request(
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).
                subscribe(observer);
    }

    /**
     * 停止定位
     */
    private void stopLocate() {
        mLocationClient.unRegisterLocationListener(myListener);
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                if (location.getLocType() == 161) {//定位成功
                    double latitude = location.getLatitude();    //获取纬度信息
                    double longitude = location.getLongitude();    //获取经度信息
                    String country = location.getCountry();    //获取国家
                    String province = location.getProvince();    //获取省份
                    String city = location.getCity();    //获取城市
                    String district = location.getDistrict();    //获取区县
                    String street = location.getStreet();    //获取街道信息
                    String streetnum=location.getStreetNumber();//获取街道号码

                    JSONObject object = new JSONObject();
                    object.put("latitude", latitude + "");
                    object.put("longitude", longitude + "");
                    object.put("country", country);
                    object.put("province", province);
                    object.put("city", city);
                    object.put("district", district);
                    object.put("street", street);
                    object.put("streetnum", streetnum);
                    Log.d(TAG, "onReceiveLocation: "+object.toString());
                    EventBus.getDefault().post(new MessageEvent(TagUtil.locateResultTag, object.toString()));
                } else {
                    EventBus.getDefault().post(new MessageEvent(TagUtil.locateResultTag, "定位失败"));
                }
            } catch (Exception e) {
                EventBus.getDefault().post(new MessageEvent(TagUtil.locateResultTag, "定位失败"));
            }
            stopLocate();
        }
    }

    private static final String TAG = "LocationUtil";


    private Observer<Boolean> observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context, "权限获取失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                mLocationClient = new LocationClient(context);     //声明LocationClient类
                mLocationClient.registerLocationListener(myListener);    //注册监听函数
                LocationClientOption option = new LocationClientOption();
                option.setTimeOut(10 * 1000);
                option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
                );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
                option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
                option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
                option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
                option.setOpenGps(true);//可选，默认false,设置是否使用gps
                option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
                option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
                option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
                mLocationClient.setLocOption(option);
                //开启定位
                mLocationClient.start();
            } else {
                Toast.makeText(context, "权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
