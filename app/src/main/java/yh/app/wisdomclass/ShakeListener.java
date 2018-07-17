package yh.app.wisdomclass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
 

/**
 * 
 * �� ��:com.example.shake �� ��:com.example.shake.ShakeListener.java ��
 * ��:ҡһҡ������
 * 
 * @author �ƻ��Ƽ�
 * @version 1.0
 * @date 2015-8-17
 */
public class ShakeListener implements SensorEventListener
{
	String TAG = "ShakeListener";
	// �ٶ���ֵ����ҡ���ٶȴﵽ��ֵ���������
	private static final int SPEED_SHRESHOLD = 3000;
	// ���μ���ʱ����
	private static final int UPTATE_INTERVAL_TIME = 70;
	// ������������
	private SensorManager sensorManager;
	// ������
	private Sensor sensor;
	// ������Ӧ������
	private OnShakeListener onShakeListener;
	// ������
	private Context mContext;
	// �ֻ���һ��λ��ʱ������Ӧ���
	private float lastX;
	private float lastY;
	private float lastZ;
	// �ϴμ��ʱ��
	private long lastUpdateTime;

	// ������
	public ShakeListener(Context c)
	{
		// ��ü������
		mContext = c;
		start();
	}

	// ��ʼ
	public void start()
	{
		sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
		if (sensorManager != null)
		{
			sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		}
		if (sensor != null)
		{
			sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);
		}
	}

	public void stop()
	{
		sensorManager.unregisterListener(this);
	}

	public void setOnShakeListener(OnShakeListener listener)
	{
		onShakeListener = listener;
	}

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		long currentUpdateTime = System.currentTimeMillis();
		long timeInterval = currentUpdateTime - lastUpdateTime;
		if (timeInterval < UPTATE_INTERVAL_TIME)
			return;
		lastUpdateTime = currentUpdateTime;
		float x = event.values[0];
		float y = event.values[1];
		float z = event.values[2];
		float deltaX = x - lastX;
		float deltaY = y - lastY;
		float deltaZ = z - lastZ;
		lastX = x;
		lastY = y;
		lastZ = z;
		double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / timeInterval * 10000;
		if (speed >= SPEED_SHRESHOLD)
		{
			onShakeListener.onShake();
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}

	public interface OnShakeListener
	{
		public void onShake();
	}
}