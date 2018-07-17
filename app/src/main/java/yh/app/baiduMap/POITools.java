package yh.app.baiduMap;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import yh.app.function.CampusMap;

public class POITools
{
	private Handler handler;
	private PoiSearch poiSearch = PoiSearch.newInstance();
	@SuppressWarnings("unused")
	private Context context;

	public POITools(Context context)
	{
		this.context = context;
		poiSearch.setOnGetPoiSearchResultListener(poiListener);
	}

	public void nearly(int radius, String keyname, int pageNum, LatLng latLng, Handler handler)
	{
		this.handler = handler;
		poiSearch.searchNearby(new PoiNearbySearchOption().radius(radius).keyword(keyname).pageNum(pageNum).location(latLng).sortType(PoiSortType.distance_from_near_to_far));
	}

	public void city(String keyname, int pageNum, String city, Handler handler)
	{
		this.handler = handler;
		poiSearch.searchInCity(new PoiCitySearchOption().keyword(keyname).pageNum(pageNum).city(city));
	}

	public void detail(String uid, Handler handler)
	{
		this.handler = handler;
		poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid(uid));
	}

	OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener()
	{
		@Override
		public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

		}

		@Override
		public void onGetPoiResult(PoiResult result)
		{
			// 获取POI检索结果
			if (result.error == SearchResult.ERRORNO.NO_ERROR)
			{
				Message msg = new Message();
				msg.obj = result;
				msg.what = CampusMap.XIANLU;
				handler.sendMessage(msg);
			} else
			{
			}
		}

		@Override
		public void onGetPoiDetailResult(PoiDetailResult result)
		{
			synchronized (UI_xianlu.o)
			{
				Message msg = new Message();
				msg.obj = result;
				msg.what = CampusMap.XIANLU_DETAIL;
				handler.sendMessage(msg);
			}
		}
	};
}
