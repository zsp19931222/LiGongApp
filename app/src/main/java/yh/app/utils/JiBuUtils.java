package yh.app.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
/**
 * �Ʋ�������
 * @author anmin
 *
 */
public class JiBuUtils {
	/**
	 * �����豸�Ƿ�֧�ּƚi
	 *
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	public static boolean isSupportStepCountSensor(Context context) {
		// ��ȡ��������������ʵ��
		SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
		Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
		Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		return countSensor != null || detectorSensor != null;
	}
}
