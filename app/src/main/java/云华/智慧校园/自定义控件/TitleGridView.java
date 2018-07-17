package 云华.智慧校园.自定义控件;

import org.json.JSONArray;

import com.yunhuakeji.app.utils.JsonTools;
import yh.app.appstart.lg.R;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import yh.app.tool.DpPx;

public class TitleGridView
{
	private TextView titleView;
	private GridView girdView;
	private View titleGirdView;
	private Context context;

	public TitleGridView(final Context context, View titleGirdView)
	{
		this.context = context;
		this.titleGirdView = titleGirdView;
		this.girdView = (GridView) titleGirdView.findViewById(R.id.gridview_home_item_gridview);
		this.titleView = (TextView) titleGirdView.findViewById(R.id.txt_home_item_titleview);

		girdView.getViewTreeObserver().addOnGlobalFocusChangeListener(new OnGlobalFocusChangeListener()
		{
			@Override
			public void onGlobalFocusChanged(View oldFocus, View newFocus)
			{
				if (girdView.getAdapter() == null || girdView.getAdapter().getCount() == 0)
				{
					TitleGridView.this.titleGirdView.setVisibility(View.GONE);
					return;
				}
				LinearLayout.LayoutParams lp = (LayoutParams) girdView.getLayoutParams();
				lp.height = ((View) girdView.getAdapter().getItem(0)).getHeight() * ((girdView.getAdapter().getCount() - 1) / girdView.getNumColumns() + 1) + new DpPx(context).getDpToPx(20);
				girdView.setLayoutParams(lp);
			}
		});
	}

	public TitleGridView setTitle(String title)
	{
		titleView.setText(title);
		return this;
	}

	public TitleGridView setAdapter(BaseAdapter adapter)
	{
		girdView.setAdapter(adapter);
		return this;
	}

	public TitleGridView setOnItemViewClick(OnItemClickListener listener)
	{
		girdView.setOnItemClickListener(listener);
		return this;
	}

	public TitleGridView setOnItemViewClick(final JSONArray jsa)
	{
		girdView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				try
				{
					Intent intent = new Intent();
					String action = JsonTools.getString(JsonTools.getJSONObject(jsa, position), "", new String[]
							{
									"function_action"
							})[0];
					if (action.equals(""))
					{
						Toast.makeText(context, "功能启动失败，请重试", Toast.LENGTH_SHORT).show();
						return;
					}
					intent.setAction(JsonTools.getString(JsonTools.getJSONObject(jsa, position), "", new String[]
							{
									"function_action"
							})[0]);
					intent.setPackage(context.getPackageName());
					context.startActivity(intent);
				} catch (Exception e)
				{
					Toast.makeText(context, "功能启动失败，请重试", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return this;
	}

	public TitleGridView setNumColumns(int numColumns)
	{
		getGirdView().setNumColumns(numColumns);
		return this;
	}

	public GridView getGirdView()
	{
		return girdView;
	}

	public View getTitleGirdView()
	{
		return titleGirdView;
	}

}