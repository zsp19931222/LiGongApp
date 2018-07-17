import java.util.Map;

import android.os.AsyncTask;
import 云华.智慧校园.工具.ThreadPoolManage;

public abstract class MyNetDataHelper extends AsyncTask<String, Integer, String> {

	public MyNetDataHelper(String url, Map<String, String> param) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String doInBackground(String... params) {
		return null;
	}

	public abstract void setPostExecute(String result);

	@Override
	protected void onPostExecute(String result) {
		setPostExecute(result);
	}

	public void excute() {
		execute();
	}
}
