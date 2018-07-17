package yh.app.baiduMap;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import android.content.Context;

public class LocationUtil
{
	private static LocationUtil mInstance;
	private BDLocation mLocation = null;
	private MLocation mBaseLocation = new MLocation();

	public static LocationUtil getInstance(Context context)
	{
		if (mInstance == null)
		{
			mInstance = new LocationUtil(context);
		}
		return mInstance;
	}

	public BDLocationListener myListener = new MyLocationListener();
	private LocationClient mLocationClient;

	public LocationUtil(Context context)
	{
		mLocationClient = new LocationClient(context.getApplicationContext());
		initParams();
		mLocationClient.registerLocationListener(myListener);
	}

	public void startMonitor()
	{
		if (!mLocationClient.isStarted())
		{
			mLocationClient.start();
		}
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.requestLocation();
		} else
		{
		}
	}

	public void stopMonitor()
	{
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.stop();
		}
	}

	public BDLocation getLocation()
	{
		return mLocation;
	}

	public MLocation getBaseLocation()
	{
		return mBaseLocation;
	}

	private void initParams()
	{
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		// option.setPriority(LocationClientOption.NetWorkFirst);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		option.setScanSpan(5 * 1000);// 设置发起定位请求的间隔时间为5000ms
		mLocationClient.setLocOption(option);
	}

	public String getLocationInfo()
	{
		if (mLocation != null)
		{
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(mLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(mLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(mLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(mLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(mLocation.getRadius());
			sb.append("\ncity : ");
			sb.append(mLocation.getCity());
			if (mLocation.getLocType() == BDLocation.TypeGpsLocation)
			{
				sb.append("\nspeed : ");
				sb.append(mLocation.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(mLocation.getSatelliteNumber());
			} else if (mLocation.getLocType() == BDLocation.TypeNetWorkLocation)
			{
				sb.append("\naddr : ");
				sb.append(mLocation.getAddrStr());
			}
			return sb.toString();
		}
		return "";
	}

	public class MyLocationListener implements BDLocationListener
	{
		@Override
		public void onReceiveLocation(BDLocation location)
		{
			if (location == null)
			{
				return;
			}
			mLocation = location;
			mBaseLocation.latitude = mLocation.getLatitude();
			mBaseLocation.longitude = mLocation.getLongitude();

			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			sb.append("\ncity : ");
			sb.append(location.getCity());
			if (location.getLocType() == BDLocation.TypeGpsLocation)
			{
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
			{
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
		}

		public void onReceivePoi(BDLocation poiLocation)
		{
		}
	}

	public class MLocation
	{
		public double latitude;
		public double longitude;
	}
}