package yh.app.activitytool;

import android.content.Intent;
import android.widget.Toast;
import 云华.智慧校园.功能._二维码;

public class QRCodeActivity extends ActivityPortrait
{
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK)
			if (requestCode == _二维码.PHOTO_PIC)
			{
				String result = data.getExtras().getString("result");
				Toast.makeText(this, "解析结果：" + result, Toast.LENGTH_SHORT).show();
			}
	}
}
