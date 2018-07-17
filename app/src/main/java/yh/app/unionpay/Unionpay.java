package yh.app.unionpay;

import com.android.volley.VolleyError;
import com.unionpay.UPPayAssistEx;

import android.content.Context;
import android.text.TextUtils;
import yh.app.utils.VolleyInterface;
import yh.app.utils.VolleyRequest;

public class Unionpay {

	private static String tn;
	// 0 - 启动银联正式环境
	// 1 - 连接银联测试环境
	private final static String serverMode = "01";

	/***
	 * 
	 * 银联支付
	 * 
	 */

	public Unionpay(final Context context) {
		VolleyRequest.RequestPost("http://101.231.204.84:8091/sim/getacptn", null, new VolleyInterface() {

			@Override
			public void onMySuccess(String result) {
				// TODO Auto-generated method stub
				if (!TextUtils.isEmpty(result)) {

					tn = result;

					UPPayAssistEx.startPay(context, null, null, tn, serverMode);

				} else {

				}
			}

			@Override
			public void onMyError(VolleyError error) {
				// TODO Auto-generated method stub

			}
		});

	}
}
