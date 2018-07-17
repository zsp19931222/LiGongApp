package 云华.智慧校园.工具;

import android.os.Environment;

public class CodeManage
{
    public final static String SUCCESS = "1";
    public final static String ERROR = "0";

    public final static String APP_Messsage_Group_ID = "6E3F2094F6621BB648973716C89DE925";//应用消息ID

    public final static String Subscibe_Message_Group_ID = "3DCF47BA72E843149D4DBBDE45D058C3";//订阅消息ID




    // 存储路径
    public final static String PATH_MAIN = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/";
    public final static String path_resources = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/resources/";
    public final static String path_resources_image = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/resources/image/";
    public final static String path_resources_ttf = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/resources/ttf/";
    public final static String path_resources_custom = Environment.getExternalStorageDirectory() + "/YunhuaTechnology/resources/custom/";

    public  final static String TEXT_PUSH="101";//txtpush消息
    public  final static String URL_PUSH="102";//urlpush消息

    // 聊天代码
    public final static String CHAT_TEXT = "201";

    // 添加好友代码
    public final static String CHAT_ADD = "202";
    public final static String CHAT_ADD_FUNCTION_ID = "20161213103904226";

    // 确认添加好友
    public final static String CHAT_READD = "203";
    public final static String CHAT_READD_FUNCTION_ID = "20161213103904226";

    // 好友处理
    public final static String DEAL_AGREE = "1";
    public final static String DEAL_DISAGREE = "4";
    public final static String DEAL_NO = "-1";

    public final static String TEACHER_NOTIFICATION = "401";

    public final static String NET_SUCCESS = "40001";

    /**
     * 二维码代码
     */
    public final static int QR_REQUEST_CODE = 0x9527;
    public final static int QR_REQUEST_CODE1 = 0x9537;

    /**
     * 二维码返回类型代码
     */
    public final static int QR_RESULT_CODE = 0x9528;
    public final static int QR_RESULT_CODE1 = 0x9538;

    /**
     * 二维码挑战类型：web
     */
    public final static int QR_RESULT_TYPE_WEB = 0x9529;

    /**
     * 二维码挑战类型：添加好友
     */
    public final static int QR_RESULT_TYPE_ADD_FRIEND = 0x952a;
    
    /**
     * 二维码扫描完成返回代码
     */
    public final static int FILECHOOSER_RESULTCODE = 1;
}