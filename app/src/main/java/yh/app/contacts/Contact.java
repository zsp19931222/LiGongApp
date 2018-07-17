// package yh.app.contacts;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.androidpn.push.Constants;
// import org.apache.http.HttpResponse;
// import org.apache.http.NameValuePair;
// import org.apache.http.client.HttpClient;
// import org.apache.http.client.entity.UrlEncodedFormEntity;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.impl.client.DefaultHttpClient;
// import org.apache.http.message.BasicNameValuePair;
// import org.apache.http.params.CoreConnectionPNames;
// import org.apache.http.protocol.HTTP;
// import org.apache.http.util.EntityUtils;
// import org.json.JSONArray;
// import org.json.JSONException;
// import org.json.JSONObject;
//
// import yh.app.activitytool.ActivityPortrait;
// import  com.yhkj.cqswzyxy.R;
// import yh.app.tool.Ticket;
// import android.os.AsyncTask;
// import android.os.Bundle;
// import android.view.Window;
// import android.widget.ListView;
// import android.widget.SimpleAdapter;
// import android.widget.TextView;
// import android.widget.Toast;
//
// public class Contact extends ActivityPortrait
// {
// private ListView list;
// private TextView re;
//
// @Override
// protected void onCreate(Bundle savedInstanceState)
// {
// super.onCreate(savedInstanceState);
// // 标题栏去除
// setContentView(R.layout.contact_main);
//
// initView();
// initAdater();
// }
//
// private void initAdater()
// {
// SimpleAdapter a = new SimpleAdapter(this, data, resource, from, to);
// }
//
// private void initView()
// {
// list = (ListView) findViewById(R.id.listContact);
// re = (TextView) findViewById(R.id.refresh_contact);
// }
//
// class AT extends AsyncTask<String, String, String>
// {
//
// private String contactUrl = Constants.tongxunluurl;
//
// @Override
// protected String doInBackground(String... params)
// {
// List<NameValuePair> parames = new ArrayList<NameValuePair>();
// parames.add(new BasicNameValuePair("userid", Constants.number));
// parames.add(new BasicNameValuePair("function_id", "20150121"));
// parames.add(new BasicNameValuePair("ticket",
// Ticket.getFunctionTicket("20150121")));
// String result = null;
// try
// {
// HttpClient hc = new DefaultHttpClient();
// HttpPost hp = new HttpPost(contactUrl);
// hp.setEntity(new UrlEncodedFormEntity(parames, HTTP.UTF_8));
// // 响应超时
// hc.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
// // 读取超时
// hc.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 15000);
// HttpResponse hr = hc.execute(hp);//
//
// if (hr.getStatusLine().getStatusCode() == 200)
// { //
// result = EntityUtils.toString(hr.getEntity());
// }
// if (hc != null)
// {
// hc.getConnectionManager().shutdown();
// }
// return result;
// } catch (Exception e)
// {
// e.printStackTrace();
// return result;
// }
// }
//
// @Override
// protected void onPostExecute(String result)
// {
// if(result == null || result.equals("") || "null".equals(result))
// {
// return;
// }
// result = result.replace("\\", "");
// result = result.substring(1, result.length());
// JSONArray array = null;
//
// try
// {
// array = new JSONArray(jsonstr);
// } catch (JSONException e)
// {
// e.printStackTrace();
// }
// if (array.length() > 0)
// {
// db.execSQL("delete from contacts");
// }
//
// for (int i = 0; i < array.length(); i++)
// {
// try
// {
// JSONObject temp = (JSONObject) array.get(i);
//
// Object[] o = new Object[] { getData(temp, "TXL_ID"), getData(temp,
// "TXL_SJH"), getData(temp, "TXL_ZJHM"), getData(temp, "TXL_NAME"),
// getData(temp, "TXL_PID"), getData(temp, "ISLEAF"), };
// db.execSQL("insert into
// contacts(id,tell_sj,tell_zj,department_name,department_pid,isleaf)
// values(?,?,?,?,?,?)", o);
//
// } catch (JSONException e)
// {
// e.printStackTrace();
// }
// }
//
// db.close();
// search();
//
// android.os.Message msg = new android.os.Message();
// msg.what = 1;
// mHandler.sendMessage(msg);
// } catch (Exception e)
// {
// Toast.makeText(getApplicationContext(), "网络异常，请刷新试试!",
// Toast.LENGTH_SHORT).show();
// return;
// }
//
// }
// }
