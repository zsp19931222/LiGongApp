package yh.app.baiduMap;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.overlayutil.TransitRouteOverlay;
import com.baidu.mapapi.overlayutil.WalkingRouteOverlay;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteLine.DrivingStep;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.SuggestAddrInfo;
import com.baidu.mapapi.search.route.TransitRouteLine.TransitStep;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine.WalkingStep;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import yh.app.appstart.lg.R;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import yh.app.tool.ToastShow;

public class RoutePlanTools {
    private RoutePlanSearch mSearch = RoutePlanSearch.newInstance();
    private BaiduMap mBaiduMap;
    private PlanNode stNode;
    private PlanNode enNode;
    private Context context;
    private LinearLayout layout;

    public RoutePlanTools(Context context, LinearLayout layout) {
        this.layout = layout;
        this.context = context;
        setListener();
    }

    public RoutePlanTools bus(BaiduMap mBaiduMap, LatLng from, LatLng to) {
        this.mBaiduMap = mBaiduMap;
        mSearch.transitSearch((new TransitRoutePlanOption()).from(PlanNode.withLocation(from)).city("重庆").to(PlanNode.withLocation(to)));
        return this;
    }

    public RoutePlanTools walk(BaiduMap mBaiduMap, LatLng from, LatLng to) {
        this.mBaiduMap = mBaiduMap;
        mSearch.walkingSearch(new WalkingRoutePlanOption().from(PlanNode.withLocation(from)).to(PlanNode.withLocation(to)));
        return this;
    }

    public RoutePlanTools driving(BaiduMap mBaiduMap, LatLng from, LatLng to) {
        this.mBaiduMap = mBaiduMap;
        mSearch.drivingSearch(new DrivingRoutePlanOption().from(PlanNode.withLocation(from)).to(PlanNode.withLocation(to)));
        return this;
    }

    public void destroy() {
        try {
            mSearch.destroy();
        } catch (Exception e) {
        }
    }

    private RoutePlanTools setListener() {
        mSearch.setOnGetRoutePlanResultListener(listener);
        return this;
    }

    private DrivingRouteOverlay driving;
    private WalkingRouteOverlay walk;
    private TransitRouteOverlay transit;

    public void deleteOverlay() {
        try {
            if (walk != null)
                walk.removeFromMap();
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (driving != null)
                driving.removeFromMap();
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            if (transit != null)
                transit.removeFromMap();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void addMessage(List<String> msg) {
        for (int i = 0; i < msg.size(); i++) {
            TextView t = new TextView(context);
            t.setText("  " + (i + 1) + "、" + msg.get(i));
            layout.addView(t);
        }
        ((Activity) context).findViewById(R.id.sc).setVisibility(View.VISIBLE);
    }

    private OnGetRoutePlanResultListener listener = new OnGetRoutePlanResultListener() {
        @Override
        public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

        }

        @Override
        public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

        }

        @Override
        public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

        }

        @Override
        public void onGetWalkingRouteResult(WalkingRouteResult result) {
            // 步行线路
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                SuggestAddrInfo s = result.getSuggestAddrInfo();
                boolean start_b = false, end_b = false;
                PoiInfo start = null;
                try {
                    start = s.getSuggestStartNode().get(0);
                    start_b = true;
                } catch (Exception e) {
                    start_b = false;
                }
                // TransitRouteLine
                PoiInfo end = null;
                try {
                    end = s.getSuggestEndNode().get(0);// TransitRouteLine
                    end_b = true;
                } catch (Exception e) {
                    end_b = false;
                }
                WalkingRoutePlanOption t = new WalkingRoutePlanOption();
                if (start_b) {
                    t.from(PlanNode.withLocation(start.location));
                } else {
                    t.from(stNode);
                }
                if (end_b) {
                    t.to(PlanNode.withLocation(end.location));
                } else {
                    t.to(enNode);
                }
                mSearch.walkingSearch(t);
                return;
            } else if (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                deleteOverlay();
                walk = new WalkingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(walk);
                walk.setData(result.getRouteLines().get(0));
                walk.addToMap();
                walk.zoomToSpan();
                addMessage(getWalkLinesMessage(result.getRouteLines().get(0).getAllStep()));
            }
        }

        private List<String> getWalkLinesMessage(List<WalkingStep> steps) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < steps.size(); i++) {
                list.add(steps.get(i).getInstructions());
            }
            return list;
        }

        @Override
        public void onGetTransitRouteResult(TransitRouteResult result) {
            // 公交线路
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                SuggestAddrInfo s = result.getSuggestAddrInfo();
                boolean start_b = false, end_b = false;
                PoiInfo start = null;
                try {
                    start = s.getSuggestStartNode().get(0);
                    start_b = true;
                } catch (Exception e) {
                    start_b = false;
                }
                // TransitRouteLine
                PoiInfo end = null;
                try {
                    end = s.getSuggestEndNode().get(0);// TransitRouteLine
                    end_b = true;
                } catch (Exception e) {
                    end_b = false;
                }
                TransitRoutePlanOption t = new TransitRoutePlanOption();
                if (start_b) {
                    t.from(PlanNode.withLocation(start.location));
                } else {
                    t.from(stNode);
                }
                if (end_b) {
                    t.to(PlanNode.withLocation(end.location));
                } else {
                    t.to(enNode);
                }
                mSearch.transitSearch(t.city("全国"));
                return;
            } else if (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                deleteOverlay();
                transit = new TransitRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(transit);
                transit.setData(result.getRouteLines().get(0));
                transit.addToMap();
                transit.zoomToSpan();
                addMessage(getTransitLinesMessage(result.getRouteLines().get(0).getAllStep()));
            }
        }

        private List<String> getTransitLinesMessage(List<TransitStep> steps) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < steps.size(); i++) {
                list.add(steps.get(i).getInstructions());
            }
            return list;
        }

        @Override
        public void onGetDrivingRouteResult(DrivingRouteResult result) {
            // 驾车线路
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
                // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
                SuggestAddrInfo s = result.getSuggestAddrInfo();
                boolean start_b = false, end_b = false;
                PoiInfo start = null;
                try {
                    start = s.getSuggestStartNode().get(0);
                    start_b = true;
                } catch (Exception e) {
                    start_b = false;
                }
                // TransitRouteLine
                PoiInfo end = null;
                try {
                    end = s.getSuggestEndNode().get(0);// TransitRouteLine
                    end_b = true;
                } catch (Exception e) {
                    end_b = false;
                }
                DrivingRoutePlanOption t = new DrivingRoutePlanOption();
                if (start_b) {
                    t.from(PlanNode.withLocation(start.location));
                } else {
                    t.from(stNode);
                }
                if (end_b) {
                    t.to(PlanNode.withLocation(end.location));
                } else {
                    t.to(enNode);
                }
                mSearch.drivingSearch(new DrivingRoutePlanOption());
                return;
            } else if (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
                new ToastShow().show(context, "找不到线路，请换个名称试试");
            } else if (result.error == SearchResult.ERRORNO.NO_ERROR) {
                deleteOverlay();
                driving = new DrivingRouteOverlay(mBaiduMap);
                mBaiduMap.setOnMarkerClickListener(driving);
                driving.setData(result.getRouteLines().get(0));
                driving.addToMap();
                driving.zoomToSpan();
                addMessage(getLinesMessage(result.getRouteLines().get(0).getAllStep()));
            }
        }

        private List<String> getLinesMessage(List<DrivingStep> steps) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < steps.size(); i++) {
                list.add(steps.get(i).getInstructions());
            }
            return list;
        }

    };
}
