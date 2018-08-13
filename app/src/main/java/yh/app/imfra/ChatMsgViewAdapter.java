
package yh.app.imfra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.yhkj.cqgyxy.R;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
@SuppressLint("SimpleDateFormat")
@SuppressWarnings("unused")
public class ChatMsgViewAdapter extends BaseAdapter {
	
	//ListView��ͼ��������IMsgViewType��
	public static interface IMsgViewType
	{
		//�Է���4����Ϣ
		int IMVT_COM_MSG = 0;
		//�Լ��������Ϣ
		int IMVT_TO_MSG = 1;
	}
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> data;
    private Context             context;  
    private LayoutInflater      mInflater;
    private Date                lastdate   = null;
    private Date                nowdate    = null;
    private DateFormat          dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    //��ȡListView�������
    @Override
	public int getCount() {
        return data.size();
    }

    //��ȡ��
    @Override
	public Object getItem(int position) {
        return data.get(position);
    }

    //��ȡ���ID
    @Override
	public long getItemId(int position) {
        return position;
    }

    //��ȡ�������
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsgEntity entity = data.get(position);
	 	
	 	if (entity.getMsgType())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}

	//��ȡ���������
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	//��ȡView
    @Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ChatMsgEntity entity = data.get(position);
    	
    	boolean isComMsg = entity.getMsgType();
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (isComMsg)
			  {
	    		  //����ǶԷ���4����Ϣ������ʾ����������
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			  }else{
				  //������Լ��������Ϣ������ʾ����������
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  }
	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent  = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg   = isComMsg;
			  
			  convertView.setTag(viewHolder);
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	    String []f=entity.getDate().split(" ");
	    try {
			nowdate=dateformat.parse(entity.getDate());
			if((lastdate==null)||(nowdate.getTime()-lastdate.getTime()>120000))
			{
				entity.setNewSession();
				lastdate=nowdate;
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(entity.getNewSession()){
	         viewHolder.tvSendTime.setText(entity.getDate());
	         viewHolder.tvSendTime.setVisibility(View.VISIBLE);
	    }
	    else
	    {
	    	viewHolder.tvSendTime.setVisibility(View.GONE);
	    }
	    viewHolder.tvUserName.setText(entity.getName()+"("+f[1]+")");
	    viewHolder.tvContent.setText(entity.getText());
	    
	    return convertView;
    }
    
    //ͨ��ViewHolder��ʾ�������
    static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
    
}
