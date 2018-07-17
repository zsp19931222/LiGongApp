package yh.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import yh.app.tool.DeleteFile;

import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * 包 名:yh.app.db 类 名:MyImdb 功 能:sqlite数据库建表
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class MyImdb extends SQLiteOpenHelper {
    public static Context context;

    public MyImdb(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public MyImdb(String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public MyImdb(Context context, String name, int version) {
        super(context, name, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // cdwlyktzx
        arg0.execSQL(
                "CREATE TABLE if not exists user(userid TEXT primary key,name varchar,birthday varchar,sex varchar,ethnic varchar,sfz_number varchar,bj varchar,qq varchar,email varchar,telphone varchar,zy varchar,bm varchar,nj varchar,faceaddress varchar,sch varchar,state varchar,nc varchar);");// �û���
        arg0.execSQL(
                "create table if not exists button(userid text,FunctionID text, name text,type integer,cls text,pkg text, key text ,face text,px integer,function_tybj text,constraint pk_button primary key (userid,functionid));");
        arg0.execSQL("create table if not exists myDiary(date text primary key,content text,type text,userid text);");
        arg0.execSQL("create table if not exists userlogin(userid text primary key, usernumber text,tag integer);");
        arg0.execSQL(
                "CREATE TABLE if not exists talks(talksid INTEGER primary key,fromuser TEXT,tofromuser TEXT,message TEXT,datetime TEXT,states integer,userid text);");
        arg0.execSQL("CREATE TABLE if not exists term(xn text,xq text,starttime text,endtime text,userid);");
        arg0.execSQL(
                "CREATE TABLE if not exists woclat(woclatid TEXT primary key,name varchar,state varchar,userid TEXT);");
        arg0.execSQL(
                "CREATE TABLE if not exists tonzhi(tonzhiid text primary key,title varchar,message vachar, tofunction text,states text,time text,messagetype text,userid text);");// ֪ͨ��
        arg0.execSQL(
                "CREATE TABLE if not exists news(newsid varchar primary key,title varchar,context TEXT,datetime TEXT,imgurl TEXT);");
        arg0.execSQL(
                "CREATE TABLE if not exists KC(KBID INTEGER primary key,KCID TEXT,XH TEXT,XN varchar,XQ varchar,JSXM varchar,JSMC varchar,COURSE TEXT,XQJ integer,SJD integer,JSSJD integer,DSZ TEXT,QSZ integer,JSZ integer,COLOR integer,userid text);");// �α�
        arg0.execSQL("CREATE TABLE if not exists RL(KBID INTEGER primary key,Start varchar,End varchar);");
        arg0.execSQL(
                "CREATE TABLE if not exists friendapplication(FA varchar primary key,fromuser varchar,tofromuser varchar,name varchar);");
        arg0.execSQL("CREATE TABLE if not exists userinfo(userid TEXT primary key,password text);");
        arg0.execSQL("CREATE TABLE if not exists usercontent(userID TEXT primary key);");
        arg0.execSQL("CREATE TABLE if not exists nowterm(xq TEXT,xn text, starttime text , endtime text);");
        arg0.execSQL("CREATE TABLE if not exists mything(content TEXT,nyr text,time text,state text,userid text);");
        arg0.execSQL("CREATE TABLE if not exists usertype(userid text primary key,usertype integer);");
        arg0.execSQL("CREATE TABLE if not exists userp(user text primary key,userp text);");
        arg0.execSQL(
                "CREATE TABLE if not exists friend(FRIEND_ID text primary key,name text,szm text,userid text,handimg text);");
        arg0.execSQL(
                "CREATE TABLE if not exists lt(id text,fqr text,jsr text,message text,fssj text,code text,userid text,friend_id text,isread text,ticket text,primary key(id,userid));");
        arg0.execSQL(
                "CREATE TABLE if not exists tzgg(tzggid text primary key,message text,fssj text,isread text,userid text,type text,url text,func_id text,bjzd text,fqbm text, title text);");
        arg0.execSQL("CREATE TABLE if not exists mrfz  (FRIEND_ID text,name text,szm text,userid text,handimg text);");
        arg0.execSQL("CREATE TABLE if not exists ltbq(bqid text primary key,bqdyzd text);");
        arg0.execSQL("CREATE TABLE if not exists dacs(js text);");
        arg0.execSQL("CREATE TABLE if not exists ydy(userid text);");
        arg0.execSQL(
                "CREATE TABLE if not exists xzt(ztbh text primary key, ktbh text, jsbh text, ztnr text, fssj text,fsrbh text);");

        arg0.execSQL(
                "CREATE TABLE if not exists jjr(jjrid text primary key,kssj text,jssj text,jjrmc text,ms text,jjrlx text);");
        arg0.execSQL("CREATE TABLE if not exists tx(txid text primary key,jjrid text,jjrsj text,txsj text,ms text);");

        arg0.execSQL(
                "CREATE TABLE if not exists customKC(KBID INTEGER primary key,KCID TEXT,XH TEXT,XN varchar,XQ varchar,JSXM varchar,JSMC varchar,COURSE TEXT,XQJ integer,SJD integer,JSSJD integer,DSZ TEXT,QSZ integer,JSZ integer,COLOR integer,userid text);");// �α�

        arg0.execSQL("CREATE TABLE if not exists function_version(version text);");

        arg0.execSQL(
                "CREATE TABLE if not exists newest_message(id text,userid text,type text,unread_num integer,title text,message text,date text,primary key(id,userid));");

        arg0.execSQL(
                "CREATE TABLE if not exists addFriend(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,faceaddress text, deal text,isread text,m_deal text,primary key(fqr,jsr));");
        arg0.execSQL(
                "CREATE TABLE if not exists addFriendList(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,faceaddress text, deal text,isread text,primary key(fqr,jsr));");

        arg0.execSQL(
                "CREATE TABLE if not exists chat(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,isread text,primary key(id,userid));");

        arg0.execSQL(
                "CREATE TABLE if not exists classlist(id text, classname text,count text, userid text,primary key(id,userid));");

        arg0.execSQL("CREATE TABLE if not exists sysj(xn text,xq text,starttime text,endtime text,userid text);");

        arg0.execSQL(
                "CREATE TABLE if not exists classmember(kcid text,userid text,name text,faceaddress text,usertype text,sex_id text,pinyin text,primary key(kcid,userid));");

        /**
         * 211版本新增 消息存储表和最新消息存储表
         */
        arg0.execSQL(
                "create table if not exists client_notice(n_id text not null,userid text not null,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,n_picpath text,n_from text,primary key (n_id, userid));");
        arg0.execSQL(
                "create table if not exists client_notice_newest(n_id text not null,userid text not null,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,primary key (function_id, userid));");

        /**
         * 用于存储应用模块数据
         */
        arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid text primary key)");
        /**
         * 时间格式为年-月-日
         */
        arg0.execSQL("CREATE TABLE if not exists step(userid text,date text, stepSum text,primary key(userid,date))");

        arg0.execSQL(
                "CREATE TABLE if not exists appmanage(function_id text primary key,userid text,function_name text)");

        /**
         //		 * 2017 7 5 保存迎新系统入学准备网络数据
         //		 */
        arg0.execSQL("CREATE TABLE if not exists rxzb(userid text primary key,rxzbdata text)");
//		
//		/**
//		 * 2017 7 5 保存个人资料
//		 */
        arg0.execSQL("CREATE TABLE if not exists userinfo(userid text primary key,userinfobdata text)");

        /**
         * 保存个人中心跳转url
         * */
        arg0.execSQL("CREATE TABLE if not exists getGrzxUrl("
                + "name text,"
                + "url text,"
                + "primary key(name))");

        /**
         * 保存未读的好友请求条数
         * */
        arg0.execSQL("CREATE TABLE if not exists hyqqNum("
                + "fqr text,"
                + "isread text,"
                + "primary key(fqr))");

        arg0.execSQL("CREATE TABLE if not exists rxzbgn(userid  text primary key,tagid text,gndate text,endtime text,staretime)");

        /**
         * 消息页面对消息进行分类
         * */
        arg0.execSQL("CREATE TABLE if not exists messageList("
                + "m_id text,"
                + "m_classify text,"
                + "m_group text,"
                + "m_code text,"
                + "m_read text,"
                + "m_from text,"
                + "m_time text,"
                + "m_image text,"
                + "m_title text,"
                + "m_message text,"
                + "m_function_id text,"
                + "m_unread text,"
                + "m_top text,"
                + "m_del text,"
                + "m_group_id text,"
                + "primary key(m_group_id))");

        /**
         * 首页消息归类
         * */
        arg0.execSQL("CREATE TABLE if not exists homeMessageList("
                + "m_type text,"
                + "m_classify text,"
                + "m_group text,"
                + "m_code text,"
                + "m_read text,"
                + "m_from text,"
                + "m_time text,"
                + "m_image text,"
                + "m_title text,"
                + "m_message text,"
                + "m_id text,"
                + "m_function_id text,"
                + "m_group_id text,"
                + "primary key(m_group_id))");

        // 新版本推送功能表
        arg0.execSQL("CREATE TABLE if not exists new_push_function("
                + "userid text,"
                + "ts_id text,"            // 推送功能ID
                + "ts_name text,"        // 推送功能名称
                + "ts_group text,"        // 推送功能分组
                + "ts_callback text,"    // 推送功能回调地址
                + "ts_icon text,"        // 推送功能图标
                + "ts_state text,"        // 推送功能状态(1-已订阅，0-未订阅)
                + "primary key(userid,ts_id))");
        // 新版本推送功能分组表
        arg0.execSQL("CREATE TABLE if not exists new_push_group("
                + "userid text,"
                + "ts_group_id text,"    // 推送分组ID
                + "ts_group_name text,"    // 推送分组名称
                + "ts_group_icon text,"    // 推送分组图标
                + "primary key(userid,ts_group_id))");
        // 新版本应用功能表
        arg0.execSQL("CREATE TABLE if not exists new_apply_function("
                + "userid text,"
                + "function_id text,"        // 应用功能ID
                + "function_name text,"        // 应用功能名称
                + "function_cls text,"        // 应用功能类名
                + "function_pkg text,"        // 应用功能包名
                + "function_icon text,"        // 应用功能图标地址
                + "function_px text,"        // 应用功能排序
                + "function_type text,"        // 应用功能类型
                + "function_key text,"        // 应用功能集成key
                + "function_group_id text,"    // 应用功能分组ID
                + "function_group_name text,"    // 应用功能分组名称
                + "function_display_homepage text," // 应用功能分组是否显示在首页
                + "function_display_homepage_px text," // 应用功能在首页的排序
                + "primary key(userid,function_id))");

        /**
         * 储存学校列表数据
         * */
        arg0.execSQL("CREATE TABLE if not exists school_list("
                + "version text,"//版本号
                + "xxmc text,"//学校名称
                + "xxbh text,"        // 学校编号
                + "szd text,"        // 所在地
                + "xxtb text,"        // 学校图标
                + "primary key(version,xxbh))");

        update_Sql_220_to_221(arg0);

        new DeleteFile().DeleteWJJALL("yhdownload");
    }

    /**
     * APP4.0-平台
     */
    private void update_Sql_220_to_221(SQLiteDatabase sqLiteDatabase) {
        //存储获取密码(密文)
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists password4("
                        + "password text,"//获取的密码
                        + "phone text,"//手机号
                        + "schoolname text,"//学校名称
                        + "schoolId text,"//学校ID
                        + "primary key(phone))");

        //保存用户基本信息
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists userinfo4("
                        + "wx text,"
                        + "lxdh text,"
                        + "xb text,"
                        + "rxnf text,"
                        + "txdz text,"
                        + "userid text,"
                        + "zydm text,"
                        + "xydm text,"
                        + "sfzh text,"
                        + "username text,"
                        + "bjdm text,"
                        + "sr text,"
                        + "usertype text,"
                        + "tsid text,"
                        + "primary key(userid))");

        //首页控件
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists homepage_widget_json("
                        + "json text,"
                        + "primary key(json))");
        //导航栏
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists navigation_list_json("
                        + "json text,"
                        + "primary key(json))");
        //我的列表
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists myself_list_json("
                        + "json text,"
                        + "primary key(json))");
        //首页控件列表
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists homepage_application_json("
                        + "json text,"
                        + "primary key(json))");
        //应用分类列表
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists applicationclassify_json("
                        + "json text,"
                        + "primary key(json))");

//编辑页面拖动数据
        sqLiteDatabase.execSQL(
                "CREATE TABLE if not exists compile_data("
                        + "url text,"
                        + "name text,"
                        + "section integer,"
                        + "position integer,"
                        + "functionID text not null,"
                        + "primary key(functionID))");

        //消息页面推送消息存储
        sqLiteDatabase.execSQL(
                "create table if not exists client_notice_messagelist(" +
                        "n_id text not null," +
                        "userid text not null," +
                        "read text," +
                        "n_title text," +
                        "n_message text," +
                        "function_id text," +
                        "n_url text," +
                        "n_send_time text," +
                        "code text," +
                        "n_ticket text," +
                        "n_picpath text," +
                        "n_look text," +
                        "n_group_id text," +
                        "primary key (n_id, userid));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {
        update_Sql_101_to_102(arg0, oldVersion, newVersion);
        update_Sql_102_to_103(arg0, oldVersion, newVersion);
        update_Sql_103_to_104(arg0, oldVersion, newVersion);
        update_Sql_104_to_105(arg0, oldVersion, newVersion);
        update_Sql_105_to_106(arg0, oldVersion, newVersion);
        update_Sql_106_to_107(arg0, oldVersion, newVersion);
        update_Sql_107_to_108(arg0, oldVersion, newVersion);
        update_Sql_108_to_109(arg0, oldVersion, newVersion);
        update_Sql_109_to_110(arg0, oldVersion, newVersion);
        update_Sql_110_to_111(arg0, oldVersion, newVersion);
        update_Sql_111_to_112(arg0, oldVersion, newVersion);
        update_Sql_113_to_114(arg0, oldVersion, newVersion);
        update_Sql_114_to_115(arg0, oldVersion, newVersion);
        update_Sql_115_to_116(arg0, oldVersion, newVersion);
        update_Sql_116_to_117(arg0, oldVersion, newVersion);
        update_Sql_116_to_201(arg0, oldVersion, newVersion);
        update_Sql_201_to_202(arg0, oldVersion, newVersion);
        update_Sql_202_to_203(arg0, oldVersion, newVersion);
        update_Sql_203_to_204(arg0, oldVersion, newVersion);
        update_Sql_204_to_205(arg0, oldVersion, newVersion);
        update_Sql_205_to_206(arg0, oldVersion, newVersion);
        update_Sql_206_to_207(arg0, oldVersion, newVersion);
        update_Sql_207_to_208(arg0, oldVersion, newVersion);
        update_Sql_208_to_209(arg0, oldVersion, newVersion);
        update_Sql_209_to_210(arg0, oldVersion, newVersion);
        update_Sql_210_to_211(arg0, oldVersion, newVersion);
        update_Sql_211_to_212(arg0, oldVersion, newVersion);
        update_Sql_212_to_213(arg0, oldVersion, newVersion);
        update_Sql_213_to_214(arg0, oldVersion, newVersion);
        update_Sql_214_to_215(arg0, oldVersion, newVersion);
        update_Sql_215_to_216(arg0, oldVersion, newVersion);
        update_Sql_216_to_217(arg0, oldVersion, newVersion);
        update_Sql_217_to_218(arg0, oldVersion, newVersion);
        update_Sql_218_to_219(arg0, oldVersion, newVersion);
        update_Sql_219_to_220(arg0, oldVersion);
        for (int i = oldVersion; i < newVersion; i++) {
            switch (i) {
                case 220:
                    update_Sql_220_to_221(arg0);
                    break;
            }
        }
    }


    /**
     * 2017-6-23 消息详情添加图片地址
     * client_notice
     */
    private void update_Sql_219_to_220(SQLiteDatabase arg0, int arg1) {
        if (arg1 < 220) {
            // 新版本推送功能表
            arg0.execSQL("CREATE TABLE if not exists new_push_function("
                    + "userid text,"
                    + "ts_id text,"        // 推送功能ID
                    + "ts_name text,"    // 应用功能名称
                    + "ts_group text,"    // 应用功能分组
                    + "ts_callback text,"    // 应用功能回调地址
                    + "ts_icon text,"    // 应用功能图标
                    + "ts_state text,"        // 推送功能状态
                    + "primary key(userid,ts_id))");
            // 新版本推送功能分组表
            arg0.execSQL("CREATE TABLE if not exists new_push_function("
                    + "userid text,"
                    + "ts_group_id text,"    // 推送分组ID，推送功能表外键(new_push_function)
                    + "ts_group_name text,"    // 推送分组名称
                    + "ts_group_icon text," // 推送分组图标
                    + "primary key(userid,ts_group_id))");
            // 新版本应用功能表
            arg0.execSQL("CREATE TABLE if not exists new_apply_function("
                    + "userid text,"
                    + "function_id text,"        // 应用功能ID
                    + "function_name text,"        // 应用功能名称
                    + "function_cls text,"        // 应用功能类名
                    + "function_pkg text,"        // 应用功能包名
                    + "function_icon text,"        // 应用功能图标地址
                    + "function_px text,"        // 应用功能排序
                    + "function_type text,"        // 应用功能类型
                    + "function_key text,"        // 应用功能集成key
                    + "function_group_id text,"    // 应用功能分组ID
                    + "function_group_name text,"    // 应用功能分组名称
                    + "function_display_homepage text," // 应用功能分组是否显示在首页
                    + "primary key(userid,function_id))");
        }
    }

    /**
     * 2017-6-23 消息详情添加图片地址
     * client_notice
     */
    private void update_Sql_218_to_219(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 < 219) {
            // 向 client_notice 添加消息图片地址字段
            arg0.execSQL("alter table client_notice add  n_picpath default");
            arg0.execSQL("CREATE TABLE IF NOT EXISTS rxzb(userid text primary(userid),rxzbdata text)");
            arg0.execSQL("CREATE TABLE if not exists userinfo(userid text primary key,userinfobdata text)");
            arg0.execSQL("CREATE TABLE if not exists rxzbgn(userid  text primary key,tagid text,gndate text,endtime text,staretime)");
        }
    }

    /**
     * 2017-5-19
     *
     * @param arg0
     * @param arg1
     * @param arg2
     */
    private void update_Sql_217_to_218(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 218) {
            // 101
            arg0.execSQL("alter table tzgg add type text");
            arg0.execSQL("alter table tzgg add url  text");
            arg0.execSQL("update tzgg set type='101'");
            // 102
            arg0.execSQL("alter table tzgg add func_id text");
            arg0.execSQL("alter table tzgg add bjzd  text");
            arg0.execSQL("alter table tzgg add fqbm  text");
            // 103
            arg0.execSQL("alter table button add function_tybj text");
            // 104
            arg0.execSQL("alter table tzgg add title text");
            // 105
            arg0.execSQL(
                    "CREATE TABLE if not exists xzt(ztbh text primary key, ktbh text, jsbh text, ztnr text, fssj text,fsrbh text);");
            // 106
            dropTable(arg0, "lt");
            arg0.execSQL(
                    "CREATE TABLE if not exists lt(fqr text,jsr text,message text,fssj text,code text,userid text);");
            // 107
            arg0.execSQL("alter table user add nc varchar");
            // 108
            arg0.execSQL("alter table lt add friend_id text");
            // 109
            arg0.execSQL(
                    "CREATE TABLE if not exists jjr(jjrid text primary key,kssj text,jssj text,jjrmc text,ms text,jjrlx text);");
            arg0.execSQL(
                    "CREATE TABLE if not exists tx(txid text primary key,jjrid text,jjrsj text,txsj text,ms text);");
            // 110
            arg0.execSQL("alter table lt add isread text");
            // 111
            dropTable(arg0, "jjr");
            dropTable(arg0, "tx");
            arg0.execSQL(
                    "CREATE TABLE if not exists jjr(jjrid text primary key,kssj text,jssj text,jjrmc text,ms text,jjrlx text);");
            arg0.execSQL(
                    "CREATE TABLE if not exists tx(txid text primary key,jjrid text,jjrsj text,txsj text,ms text);");
            // 113
            arg0.execSQL(
                    "CREATE TABLE if not exists customKC(KBID INTEGER primary key,KCID TEXT,XH TEXT,XN varchar,XQ varchar,JSXM varchar,JSMC varchar,COURSE TEXT,XQJ integer,SJD integer,JSSJD integer,DSZ TEXT,QSZ integer,JSZ integer,COLOR integer,userid text);");// �α�
            // 114
            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, fqr text, fjnr text, fssj text, deal text, userid text);");// �α�
            // 115
            arg0.execSQL("drop table button");
            arg0.execSQL("CREATE TABLE if not exists function_version(version text);");
            arg0.execSQL(
                    "create table if not exists button(userid text,FunctionID text, name text,type integer,cls text,pkg text, key text ,face text,px integer,function_tybj text,constraint pk_button primary key (userid,functionid));");

            // 116
            arg0.execSQL("CREATE TABLE if not exists sysj(xn text,xq text,starttime text,endtime text,userid text);");
            // 201
            dropTable(arg0, "addFriend");
            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text, deal text,isread text,primary key(id,userid));");
            // 202
            arg0.execSQL(
                    "CREATE TABLE if not exists chat(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,isread text,primary key(id,userid));");
            // 203
            arg0.execSQL(
                    "CREATE TABLE if not exists classlist(id text, classname text,count text, userid text,primary key(id,userid));");
            // 204
            arg0.execSQL(
                    "CREATE TABLE if not exists classmember(kcid text,userid text,name text,faceaddress text,usertype text,sex_id text,primary key(kcid,userid));");
            // 205
            arg0.execSQL("alter table classmember add pinyin text");
            // 206
            arg0.execSQL("alter table addFriend add faceaddress text");
            // 208
            dropTable(arg0, "addFriend");

            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,faceaddress text, deal text,isread text,primary key(fqr,jsr));");
            // 209
            // 应用管理表格,function_face 图片路径，function_id 应用ID，type_name 组名
            // function_name 本地图片地址pic_pass_local
            arg0.execSQL(
                    "CREATE TABLE if not exists yhappmanagprivate(function_face text,function_id text,unchange varchar(20),type_name varchar(20), type_id text,function_name varchar(50),pic_pass_local text)");
            // 应用分组表 组名 type_name function_id 应用id type_id 分组id
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid varchar(50)primary key)");
            // 210
            dropTable(arg0, "yhappmanagprivate");
            dropTable(arg0, "yhappmanagpublic");
            arg0.execSQL(
                    "CREATE TABLE if not exists yhappmanagprivate(function_face text,function_id text,unchange varchar(20),type_name varchar(20), type_id text,function_name varchar(50),pic_pass_local text)");
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid varchar(50)primary key)");

            // 211
            arg0.execSQL(
                    "create table if not exists client_notice(n_id text not null,userid text not null,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,primary key (n_id, userid));");
            arg0.execSQL(
                    "create table if not exists client_notice_newest(n_id text not null,userid text not null,title_name text,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,primary key (function_id, userid));");
            // 212
            dropTable(arg0, "yhappmanagprivate");
            dropTable(arg0, "yhappmanagpublic");
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid text primary key)");
            // 213
            dropTable(arg0, "lt");
            arg0.execSQL(
                    "CREATE TABLE if not exists lt(fqr text,jsr text,message text,fssj text,code text,userid text,friend_id text,isread text,ticket text);");
            // 214
            dropTable(arg0, "lt");
            arg0.execSQL(
                    "CREATE TABLE if not exists lt(id text,fqr text,jsr text,message text,fssj text,code text,userid text,friend_id text,isread text,ticket text,primary key(id,userid));");

            // 217
            arg0.execSQL("alter table friend add handimg text");
            // 218
            arg0.execSQL("alter table mrfz add handimg text");
        }
    }

    private void update_Sql_216_to_217(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 217) {
            arg0.execSQL("alter table friend add handimg text");
        }
    }

    private void update_Sql_213_to_214(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 214) {
            dropTable(arg0, "lt");
            arg0.execSQL(
                    "CREATE TABLE if not exists lt(id text,fqr text,jsr text,message text,fssj text,code text,userid text,friend_id text,isread text,ticket text,primary key(id,userid));");
        }
    }

    private void update_Sql_212_to_213(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 213) {
            dropTable(arg0, "lt");
            arg0.execSQL(
                    "CREATE TABLE if not exists lt(fqr text,jsr text,message text,fssj text,code text,userid text,friend_id text,isread text,ticket text);");
        }
    }

    private void update_Sql_215_to_216(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 212) {
            dropTable(arg0, "appmanage");
            arg0.execSQL(
                    "CREATE TABLE if not exists appmanage(function_id text primary key,userid text,function_name text)");
        }
    }

    private void update_Sql_214_to_215(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 212) {
            arg0.execSQL("CREATE TABLE if not exists appmanage(function_id text,userid text primary key)");
        }
    }

    private void update_Sql_211_to_212(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 212) {
            dropTable(arg0, "yhappmanagprivate");
            dropTable(arg0, "yhappmanagpublic");
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid text primary key)");
        }
    }

    private void update_Sql_210_to_211(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 211) {
            arg0.execSQL(
                    "create table if not exists client_notice(n_id text not null,userid text not null,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,primary key (n_id, userid));");
            arg0.execSQL(
                    "create table if not exists client_notice_newest(n_id text not null,userid text not null,title_name text,read text,n_title text,n_message text,function_id text,n_url text,n_send_time text,code text,n_ticket text,primary key (function_id, userid));");
        }
    }

    private void update_Sql_209_to_210(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 210) {
            dropTable(arg0, "yhappmanagprivate");
            dropTable(arg0, "yhappmanagpublic");
            arg0.execSQL(
                    "CREATE TABLE if not exists yhappmanagprivate(function_face text,function_id text,unchange varchar(20),type_name varchar(20), type_id text,function_name varchar(50),pic_pass_local text)");
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid varchar(50)primary key)");
        }
    }

    private void update_Sql_208_to_209(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 209) {
            // 应用管理表格,function_face 图片路径，function_id 应用ID，type_name 组名
            // function_name 本地图片地址pic_pass_local
            arg0.execSQL(
                    "CREATE TABLE if not exists yhappmanagprivate(function_face text,function_id text,unchange varchar(20),type_name varchar(20), type_id text,function_name varchar(50),pic_pass_local text)");
            // 应用分组表 组名 type_name function_id 应用id type_id 分组id
            arg0.execSQL("CREATE TABLE if not exists yhappmanagpublic(appjson text,userid varchar(50)primary key)");
        }
    }

    private void update_Sql_207_to_208(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        if (arg1 <= 208) {
            dropTable(arg0, "addFriend");
            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,faceaddress text, deal text,isread text,primary key(fqr,jsr));");
        }
    }

    private void update_Sql_206_to_207(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        if (arg1 <= 206)
            arg0.execSQL("alter table addFriend add faceaddress text");
    }

    private void update_Sql_205_to_206(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
        if (arg1 <= 205)
            arg0.execSQL("alter table classmember add pinyin text");
    }

    private void update_Sql_204_to_205(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 204)
            arg0.execSQL(
                    "CREATE TABLE if not exists classmember(kcid text,userid text,name text,faceaddress text,usertype text,sex_id text,primary key(kcid,userid));");
    }

    private void update_Sql_116_to_117(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 116) {
            arg0.execSQL("CREATE TABLE if not exists sysj(xn text,xq text,starttime text,endtime text,userid text);");
        }
    }

    private void update_Sql_203_to_204(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 203)
            arg0.execSQL(
                    "CREATE TABLE if not exists classlist(id text, classname text,count text, userid text,primary key(id,userid));");
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    private void update_Sql_202_to_203(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 202)
            arg0.execSQL(
                    "CREATE TABLE if not exists chat(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text,isread text,primary key(id,userid));");
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    private void update_Sql_201_to_202(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 201) {
            dropTable(arg0, "addFriend");
            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, userid text,type text, fqr text,fqrname text,jsr text,jsrname text, fjnr text, fssj text, deal text,isread text,primary key(id,userid));");
        }
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     */
    private void update_Sql_116_to_201(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 201) {
            arg0.execSQL(
                    "CREATE TABLE if not exists newest_message(id text,userid text,type text,unread_num integer,title text,message text,date text,primary key(id,userid));");
        }
    }

    private void update_Sql_115_to_116(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 115) {
            arg0.execSQL("drop table button");
            arg0.execSQL("CREATE TABLE if not exists function_version(version text);");
            arg0.execSQL(
                    "create table if not exists button(userid text,FunctionID text, name text,type integer,cls text,pkg text, key text ,face text,px integer,function_tybj text,constraint pk_button primary key (userid,functionid));");
        }
    }

    private void update_Sql_114_to_115(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 114) {
            arg0.execSQL(
                    "CREATE TABLE if not exists addFriend(id text, fqr text, fjnr text, fssj text, deal text, userid text);");// �α�
        }
    }

    private void update_Sql_113_to_114(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 113) {
            arg0.execSQL(
                    "CREATE TABLE if not exists customKC(KBID INTEGER primary key,KCID TEXT,XH TEXT,XN varchar,XQ varchar,JSXM varchar,JSMC varchar,COURSE TEXT,XQJ integer,SJD integer,JSSJD integer,DSZ TEXT,QSZ integer,JSZ integer,COLOR integer,userid text);");// �α�
        }
    }

    private void update_Sql_111_to_112(SQLiteDatabase arg0, int arg1, int arg2) {
        if (arg1 <= 111) {
            dropTable(arg0, "jjr");
            dropTable(arg0, "tx");
            arg0.execSQL(
                    "CREATE TABLE if not exists jjr(jjrid text primary key,kssj text,jssj text,jjrmc text,ms text,jjrlx text);");
            arg0.execSQL(
                    "CREATE TABLE if not exists tx(txid text primary key,jjrid text,jjrsj text,txsj text,ms text);");
        }
    }

    private void update_Sql_110_to_111(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 110)
                arg0.execSQL("alter table lt add isread text");
        } catch (Exception e) {
        }
    }

    private void update_Sql_109_to_110(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 109) {
                arg0.execSQL(
                        "CREATE TABLE if not exists jjr(jjrid text primary key,kssj text,jssj text,jjrmc text,ms text,jjrlx text);");
                arg0.execSQL(
                        "CREATE TABLE if not exists tx(txid text primary key,jjrid text,jjrsj text,txsj text,ms text);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_108_to_109(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 108) {
                arg0.execSQL("alter table lt add friend_id text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_107_to_108(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 107) {
                arg0.execSQL("alter table user add nc varchar");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_106_to_107(SQLiteDatabase arg0, int arg1, int arg2) {

        try {
            if (arg1 <= 106) {
                dropTable(arg0, "lt");
                arg0.execSQL(
                        "CREATE TABLE if not exists lt(fqr text,jsr text,message text,fssj text,code text,userid text);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void update_Sql_105_to_106(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 105) {
                arg0.execSQL(
                        "CREATE TABLE if not exists xzt(ztbh text primary key, ktbh text, jsbh text, ztnr text, fssj text,fsrbh text);");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_104_to_105(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 104) {
                arg0.execSQL("alter table tzgg add title text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_103_to_104(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 103) {
                arg0.execSQL("alter table button add function_tybj text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_102_to_103(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 102) {
                arg0.execSQL("alter table tzgg add func_id text");
                arg0.execSQL("alter table tzgg add bjzd  text");
                arg0.execSQL("alter table tzgg add fqbm  text");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update_Sql_101_to_102(SQLiteDatabase arg0, int arg1, int arg2) {
        try {
            if (arg1 <= 101) {
                arg0.execSQL("alter table tzgg add type text");
                arg0.execSQL("alter table tzgg add url  text");
                arg0.execSQL("update tzgg set type='101'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropTable(SQLiteDatabase db, String table) {
        try {
            db.execSQL("drop table " + table);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}