package yh.app.function;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.example.app3.tool.JSONTool;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.yhkj.cqgyxy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import rx.Observer;
import yh.app.CampusMap.CampusMapTools;
import yh.app.activitytool.ActivityPortrait;
import yh.app.baiduMap.POITools;
import yh.app.baiduMap.RoutePlanTools;
import yh.app.maptools.MapPopupWindow;
import yh.app.tool.MAP_AT;
import yh.app.utils.TopBarHelper;
import yh.app.utils.TopBarHelper.OnClickLisener;
import 云华.智慧校园.工具.JsonTools;
import 云华.智慧校园.自定义控件.MapCampusPopwindow;

/**
 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡
 */
@SuppressWarnings("unused")
public class CampusMap extends ActivityPortrait {

    public static final int DAOHANG = 10010;

    public static final int XIANLU = 10020;
    public static final int XIANLU_DETAIL = 10021;

    public static final int XIANLU_BUS = 10001;
    public static final int XIANLU_BUS_SUCCESS = 100011;

    public static final int XIANLU_WALK = 10002;
    public static final int XIANLU_WALK_SUCCESS = 100021;

    public static final int XIANLU_DRIVER = 10003;
    public static final int XIANLU_DRIVER_SUCCESS = 100031;

    // 定位相关
    private static boolean isMyLocal = false;
    private static boolean isFresh = true;
    private static LatLng currentLocal;
    private LocationClient mLocClient;
    private MyLocationListenner myListener;
    private static MapView mMapView; // Map控件
    private static BaiduMap mBaiduMap;
    private static Context mContext;
    private FrameLayout layout;
    public static ReadWriteLock lock = new ReentrantReadWriteLock();
    private Button xianlu, daohang, end;
    private static final int map_marker_type_building = 1;
    private static final int map_marker_type_custom_point = 2;

    public static List<Map<String, String>> xqzb = new ArrayList<>();
    public static boolean addXQZB = true;

    private LinearLayout xlxq_layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        SDKInitializer.initialize(CampusMap.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baidu_map);
        Map<String, String> map = new HashMap<>();
        map.put("MAP_NAME", this.getResources().getString(R.string.xndt_wdwz));
        xqsj.add(map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("MAP_NAME", "附近美食");
        xqsj.add(map2);
        Map<String, String> map3 = new HashMap<>();
        map3.put("MAP_NAME", "公交查询");
        xqsj.add(map3);
        initView();
        isMyLocal = false;
        mContext = this;
        isFresh = true;
        layout = findViewById(R.id.map_layout);
        // zuobiao = new ArrayList<double[]>();
        // 地图初始化
        mMapView = findViewById(R.id.bmapView);
        new RxPermissions(this).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION).subscribe(observer);

        getZuoBiao();
        setTopBar();
        initView();
        // 测试
        // test();
    }

    Observer observer = new Observer<Boolean>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(CampusMap.this, "定位权限获取失败，无法准确定位", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNext(Boolean o) {
            if (o) {
                mBaiduMap = mMapView.getMap();
                myListener = new MyLocationListenner();
                // 开启定位图层
                mBaiduMap.setMyLocationEnabled(true);
                // 定位初始化
                mLocClient = new LocationClient(CampusMap.this);
                mLocClient.registerLocationListener(myListener);
                LocationClientOption option = new LocationClientOption();
                option.setOpenGps(true); // 打开gps
                option.setCoorType("bd09ll"); // 设置坐标类型
                option.setScanSpan(1000);
                mLocClient.setLocOption(option);
                mLocClient.start();
                initOverlay();
                MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(17f);
                mBaiduMap.animateMapStatus(u);
                mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng point) {
                        if (mMarker != null) {
                            mBaiduMap.hideInfoWindow();
                            mMarker = null;
                        }
                    }

                    @Override
                    public boolean onMapPoiClick(MapPoi arg0) {
                        // 地图POI点信息
                        return false;
                    }
                });
            } else {
                Toast.makeText(CampusMap.this, "定位权限获取失败，无法准确定位", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private void test() {
        new POITools(this).nearly(2000, "理工大学公交站", 0, currentLocal, new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                new POITools(mContext).detail("5d1898b5f221c48a7ab55cf0", new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        PoiDetailResult pdr = (PoiDetailResult) msg.obj;
                        System.out.println("" + "type:" + pdr.getType() + "\n" + "address:" + pdr.getAddress() + "\n" + "");
                    }
                });
            }
        });
    }

    WalkingRouteOverlay routeOverlayWalk;
    TransitRouteOverlay routeOverlaytran;
    private TopBarHelper tbh;

    private void setTopBar() {
        tbh = new TopBarHelper(this, findViewById(R.id.topbar_layout)).setTitle("校内地图");
        // tbh.getExtraView().setVisibility(View.INVISIBLE);
        tbh.setOnClickLisener(new OnClickLisener() {
            @Override
            public void setRightOnClick() {
                finish();
            }

            @Override
            public void setLeftOnClick() {
                finish();
            }

            @Override
            public void setExtraOnclick() {
                lock.readLock().lock();
                try {
                    new MapCampusPopwindow(tbh.getExtraView(), mContext, new CampusMapTools().getCampusLayout(mContext, xqsj, (ViewGroup) ((Activity) mContext).findViewById(R.id.map_layout1)), tbh.getLayout()).doit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                lock.readLock().unlock();
            }
        });
        tbh.getExtraView().setBackgroundResource(R.drawable.local_button);
    }

    private void initView() {
        daohang = findViewById(R.id.daohang);
        xianlu = findViewById(R.id.xianlu);
        end = findViewById(R.id.end);
        xlxq_layout = findViewById(R.id.xlxq_layout);
        daohang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("yh.app.baiduMap.UI_daohang");
                intent.setPackage(getPackageName());
                intent.putExtra("lat", currentLocal.latitude);
                intent.putExtra("lng", currentLocal.longitude);
                startActivityForResult(intent, DAOHANG);
                // startActivity(intent);
            }
        });
        xianlu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("yh.app.baiduMap.UI_xianlu");
                intent.setPackage(getPackageName());
                intent.putExtra("lat", currentLocal.latitude);
                intent.putExtra("lng", currentLocal.longitude);
                startActivityForResult(intent, XIANLU);
            }
        });
        end.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rpt.deleteOverlay();
                show(true, false, false);
            }
        });
        show(true, false, false);
    }

    private JSONArray jsa_jzw;
    private List<Map<String, String>> xqsj = new ArrayList<>();
    // boolean add
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = msg.getData().getString("date");
            result= JSONTool.jsonString(result);
            try {
                jsa_jzw = new JSONArray(result);
                for (int i = 0; i < jsa_jzw.length(); i++) {
                    try {
                        JSONObject jso = jsa_jzw.getJSONObject(i);
                        if (jso.getString("MAP_TYPE").equals("1"))
                            setZuoBiao(Double.valueOf(jso.getString("MAP_LNG")), Double.valueOf(jso.getString("MAP_LAT")), jso.getString("MAP_NAME"), jso.getString("MAP_CONTENT"), jso.getString("MAP_ID"), R.drawable.map_marker_48px, map_marker_type_building);
                        else if (jso.getString("MAP_TYPE").equals("0")) {
                            lock.writeLock().lock();
                            try {
                                xqsj.add(xqsj.size() - 1, JsonTools.getMapBtJsonObject(jso));
                                if (addXQZB)
                                    xqzb.add(JsonTools.getMapBtJsonObject(jso));
                            } catch (Exception e) {
                            }
                            lock.writeLock().unlock();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                addXQZB = false;
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    };

    private void getZuoBiao() {
        MAP_AT m = new MAP_AT(handler);
        m.executeOnExecutor(Executors.newCachedThreadPool());
    }

    public class myLocat implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation arg0) {
            new MyLocationData.Builder().accuracy(arg0.getRadius()).direction(100).latitude(arg0.getLatitude()).longitude(arg0.getLongitude()).build();
        }
    }

    private LatLng LocLL = null;

    public static void myLocal(String map_name, LatLng ll) {
        if (map_name.equals("我的位置")) {
            isMyLocal = true;
            // 刷新地图
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(currentLocal);
            mBaiduMap.animateMapStatus(u);
        } else if (map_name.equals("附近美食")) {
            new POITools(mContext).nearly(2000, "美食", 0, currentLocal, nearly_handler);
        } else if (map_name.equals("公交查询")) {
            Intent intent = new Intent();
            intent.setAction("yh.app.baiduMap.NearBus");
            intent.setPackage(mContext.getPackageName());
            mContext.startActivity(intent);
        } else if (map_name != null && ll != null) {
            isMyLocal = false;
            // 刷新地图
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
        }
    }

    private static final int sdfdsf = 1000;
    @SuppressLint("HandlerLeak")
    public static Handler nearly_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            PoiResult poiList = (PoiResult) msg.obj;
            List<PoiInfo> list = poiList.getAllPoi();
            for (int i = 0; i < list.size(); i++) {
                BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_red_48px);
                LatLng llA = list.get(i).location;
                Bundle info = new Bundle();
                info.putSerializable("info", sdfdsf + i + "");
                info.putInt("type", map_marker_type_custom_point);
                info.putString("name", list.get(i).name);
                info.putString("address", list.get(i).address);
                info.putString("tel", list.get(i).phoneNum);
                OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
                Marker mMarkerA;
                mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
                mMarkerA.setExtraInfo(info);
            }
        }

        ;
    };

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            currentLocal = new LatLng(location.getLatitude(), location.getLongitude());
            if (isFresh) {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null)
                    return;
                MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius()).direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                if (LocLL == null) {
                    LocLL = ll;
                }
                // 刷新地图
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                MapStatusUpdate u2 = MapStatusUpdateFactory.zoomTo(16.777777f);
                mBaiduMap.animateMapStatus(u2);
                mBaiduMap.animateMapStatus(u);
                isFresh = false;
            } else {
            }
        }
    }

    public void init(View v) {
        if (LocLL != null) {
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(LocLL);
            mBaiduMap.animateMapStatus(u);
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    private Marker mMarker = null; // 记录按下的Marker
    BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(R.drawable.map_marker_48px);

    public void initOverlay() {
        LatLng southwest = new LatLng(39.92235, 116.380338);
        LatLng northeast = new LatLng(39.947246, 116.414977);
        LatLngBounds bounds = new LatLngBounds.Builder().include(northeast).include(southwest).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(bounds.getCenter());
        mBaiduMap.setMapStatus(u);
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getExtraInfo().getInt("type") == map_marker_type_building) {
                    String bh = marker.getExtraInfo().getString("info");
                    try {
                        JSONObject jso = null;
                        for (int i = 0; i < jsa_jzw.length(); i++) {
                            JSONObject jso_temp = jsa_jzw.getJSONObject(i);
                            if (jso_temp.getString("MAP_ID").equals(bh)) {
                                jso = jso_temp;
                                break;
                            }
                        }
                        if (jso != null)
                            new MapPopupWindow(layout, mContext, jso.getString("MAP_NAME"), jso.getString("MAP_CONTENT"), jso.getString("MAP_REMARK")).doit();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (marker.getExtraInfo().getInt("type") == map_marker_type_custom_point) {
                    new MapPopupWindow(layout, mContext, marker.getExtraInfo().getString("name"), "地址:" + marker.getExtraInfo().getString("address") + "\n" + "电话:" + marker.getExtraInfo().getString("tel"), marker.getExtraInfo().getString("name")).doit();
                }
                return true;
            }
        });
    }

    private void setZuoBiao(double Lng, double Lat, String name, String content, String bh, int resource, int type) {
        BitmapDescriptor bdA = BitmapDescriptorFactory.fromResource(resource);
        LatLng llA = new LatLng(Lat, Lng);
        Bundle info = new Bundle();
        info.putSerializable("info", bh);
        info.putInt("type", type);
        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA).zIndex(9).draggable(true);
        Marker mMarkerA;
        mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
        mMarkerA.setExtraInfo(info);
    }

    private void show(boolean isShowDaohang, boolean isShowMessage, boolean isShowCloseButton) {
        if (isShowDaohang)
            findViewById(R.id.DaohangAndXianluLayout).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.DaohangAndXianluLayout).setVisibility(View.GONE);

        if (isShowMessage)
            findViewById(R.id.sc).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.sc).setVisibility(View.GONE);

        if (isShowCloseButton)
            findViewById(R.id.end).setVisibility(View.VISIBLE);
        else
            findViewById(R.id.end).setVisibility(View.GONE);

    }

    private RoutePlanTools rpt;
    private boolean isShowDaohang = true, isShowMessage = false, isShowCloseButton = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (rpt == null)
            rpt = new RoutePlanTools(CampusMap.this, xlxq_layout);
        switch (resultCode) {
            case DAOHANG:
                break;
            case XIANLU_BUS:
                rpt.bus(mBaiduMap, new LatLng(data.getDoubleExtra("lat1", 0), data.getDoubleExtra("lng1", 0)), new LatLng(data.getDoubleExtra("lat2", 0), data.getDoubleExtra("lng2", 0)));
                show(false, true, true);
                break;
            case XIANLU_WALK:
                rpt.walk(mBaiduMap, new LatLng(data.getDoubleExtra("lat1", 0), data.getDoubleExtra("lng1", 0)), new LatLng(data.getDoubleExtra("lat2", 0), data.getDoubleExtra("lng2", 0)));
                show(false, true, true);
                break;
            case XIANLU_DRIVER:
                rpt.driving(mBaiduMap, new LatLng(data.getDoubleExtra("lat1", 0), data.getDoubleExtra("lng1", 0)), new LatLng(data.getDoubleExtra("lat2", 0), data.getDoubleExtra("lng2", 0)));
                show(false, true, true);
                break;
            case XIANLU_BUS_SUCCESS:
                break;
            default:
                break;
        }
    }

    // @Override
    // public boolean onKeyDown(int keyCode, KeyEvent event)
    // {
    // if (mMarker != null && keyCode == KeyEvent.KEYCODE_BACK &&
    // event.getAction() == KeyEvent.ACTION_DOWN)
    // {
    // mBaiduMap.hideInfoWindow();
    // mMarker = null;
    // return false;
    // } else
    // {
    // this.finish();
    // }
    // return super.onKeyDown(keyCode, event);
    // }
}