package yh.app.baiduMap;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;

public class Tools
{
	/**
	 * 导航
	 */
	public void walkNavigation(double pt_start_x, double pt_start_y, double pt_end_x, double pt_end_y, OnGetRoutePlanResultListener getRoutePlanResultListener)
	{
		LatLng pt_start = new LatLng(pt_start_x, pt_start_y);
		LatLng pt_end = new LatLng(pt_end_x, pt_end_y);

		// 构建 route搜索参数
		/**
		 * busStrategyType(RouteParaOption.EBusStrategyType busStrategyType)
		 * 路线检索策略枚举（公交必选） 时间短 bus_time_first 少换乘 bus_transfer_little
		 * 少步行bus_walk_little 不坐地铁 bus_no_subway 推荐路 bus_recommend_way
		 */
		// RouteParaOption para = new
		// RouteParaOption().startPoint(pt_start).startName("天安门").endPoint(pt_end).endName("百度大厦").busStrategyType(EBusStrategyType.bus_recommend_way);

		// RouteParaOption para = new RouteParaOption()
		// .startName("天安门").endName("百度大厦").busStrategyType(EBusStrategyType.bus_recommend_way);

		// RouteParaOption para = new RouteParaOption()
		// .startPoint(pt_start).endPoint(pt_end).busStrategyType(EBusStrategyType.bus_recommend_way);

		try
		{
			/**
			 * openBaiduMapTransitRoute(RouteParaOption para, Context context)
			 * 调起百度地图公交路线检索页面
			 */
			RoutePlanSearch search = RoutePlanSearch.newInstance();
			PlanNode p_start = PlanNode.withLocation(pt_start);
			PlanNode p_end = PlanNode.withLocation(pt_end);
			search.setOnGetRoutePlanResultListener(getRoutePlanResultListener);
			search.walkingSearch(new WalkingRoutePlanOption().from(p_start).to(p_end));
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}