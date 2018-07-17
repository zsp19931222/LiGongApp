package 云华.智慧校园.自定义适配器;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyChatAdapter extends BaseAdapter
{
	private List<View> holders;

	public MyChatAdapter(ChatViewHolder viewHolders)
	{
		this.holders = viewHolders.getViewHolders();
	}

	@Override
	public int getCount()
	{
		return holders.size();
	}

	@Override
	public Object getItem(int position)
	{
		return holders.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return holders.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return holders.get(position);
	}

	public interface ChatViewHolder
	{
		List<View> getViewHolders();
	}

	public void addFirstView(List<View> views)
	{
		holders.addAll(0, views);
	}
	
	public void addLastView(List<View> views)
	{
		holders.addAll(views);
	}
}
