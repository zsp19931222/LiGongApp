package com.yunhuakeji.app.utils;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

public class HealthStepTools
{
	private Context instance;
	private SensorStepListener listener;

	private SensorManager mSensorManager;

	public HealthStepTools(Context instance)
	{
		this.instance = instance;
	}

	public void setSensorStepListener(SensorStepListener listener)
	{
		this.listener = listener;
	}

	public interface SensorStepListener
	{
		void onSensorChanged(SensorEvent event);
	}

	private class SensorListener implements SensorEventListener
	{
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy)
		{
			// TODO Auto-generated method stub
			System.out.println(accuracy);
		}

		@Override
		public void onSensorChanged(SensorEvent event)
		{
			// count += event.values[0];
			listener.onSensorChanged(event);
		}
	}

	public void registerSensor(int sensorType)
	{
		mSensorManager = (SensorManager) instance.getSystemService(Activity.SENSOR_SERVICE);
		// sensorType is either Sensor.TYPE_STEP_COUNTER or Sensor.TYPE_STEP_DETECTOR
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null)
		{
			mSensorManager.registerListener(new SensorListener(), mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);
		} else if (mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR) != null)
		{
			mSensorManager.registerListener(new SensorListener(), mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR), SensorManager.SENSOR_DELAY_NORMAL);
		} else
		{
			Toast.makeText(instance, "该设备不支持记步", Toast.LENGTH_SHORT).show();
		}
	}
}
