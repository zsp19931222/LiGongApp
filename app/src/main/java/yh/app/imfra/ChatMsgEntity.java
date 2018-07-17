package yh.app.imfra;
/**
 * 
 * 包	名:yh.app.imfra
 * 类	名:ChatMsgEntity.java
 * 功	能:
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
@SuppressWarnings("unused")
public class ChatMsgEntity {
    
	private static final String TAG = ChatMsgEntity.class.getSimpleName();
    //����
    private String name;
    //����
    private String date;
    //��������
    private String text;
    //�Ƿ�Ϊ�Է���������Ϣ
    private boolean isComMeg = true;
    private boolean isNewSession=false;

    public String getName() {
        return name;
    }
    public void setNewSession() {
    	isNewSession=true;
	}
    public boolean getNewSession() {
		return isNewSession;
	}
    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMeg;
    }

    public void setMsgType(boolean isComMsg) {
    	isComMeg = isComMsg;
    }

    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMeg = isComMsg;
    }
}
