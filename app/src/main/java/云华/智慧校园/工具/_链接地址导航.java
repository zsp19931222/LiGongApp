package 云华.智慧校园.工具;

import org.androidpn.push.Constants;

public class _链接地址导航 {
    public static final String dc = ConfigTools.loadProperties(Constants.xx + "dc");
    private static final String uia = ConfigTools.loadProperties(Constants.xx + "uia");
    private static final String push = ConfigTools.loadProperties(Constants.xx + "push");
    private static final String wddx = ConfigTools.loadProperties(Constants.xx + "wddx");
    private static final String ydyx = ConfigTools.loadProperties(Constants.xx + "ydyx");
    private static final String groupchat = ConfigTools.loadProperties(Constants.xx + "groupchat");

    private static final String wddxserver = ConfigTools.loadProperties("testwddxserver");
    private static final String testpushserver = ConfigTools.loadProperties("testpushserver");
    public static final String addString = "yunhua";

    public static enum DC {
        成绩查询(URL.score), 通讯录(URL.tongxunlu), 时间(URL.term),

        当前时间(URL.nowterm), 课表查询(URL.kbcx), 发起点名(URL.fqdm),

        学生点名(URL.xsdm), 课堂学生列表(URL.ktxslb), 已点名学生列表(URL.ydmxslb),

        提交点名(URL.tjdm), 校内地图(URL.map), 点名次数(URL.dmcs),

        点名班级(URL.dmbj), 教师小纸条学生名单(URL.jsxztxsmd), 教师对话框内容(URL.jsdhknr),

        学生对话框内容(URL.xsdhknr), 教师列表(URL.jslb), 学生评教(URL.xspj),

        获取学生评教(URL.getxspj), 获取教师评教(URL.getjspj), 发送纸条(URL.fszt),

        所有学生点名结果(URL.syxsdmjg), 圈子好友列表(URL.qzhylb), 圈子默认列表(URL.qzmrlb), 保存课堂信息(URL.saveKtxx),

        添加好友(URL.tjhy), 删除好友(URL.schy), 是否同意添加好友(URL.sftytjhy), 搜索好友(URL.sshy), 节假日(URL.jjr), 所有时间(URL.sysj), 获取广告栏(URL.getAD), 提交反馈意见v3(URL.submitFeedbackV3), getMessageByID(URL.getMessageByID),teachPush(URL.teachPush);

        private String url;

        private DC(String url) {
            this.url = url;
        }

        public String getUrl() {
            return dc + url;
        }
    }

    public static enum UIA {
        修改密码(URL.password), 修改资料(URL.setting), 登录(URL.logon), 登录2(URL.logon2), 登录_老版本(URL.login), 下载页面(URL.download_page_old), 下载页面无参数(URL.download_page),

        网页跳转(URL.webview), 功能列表(URL.function), 功能列表_老版本(URL.function_old), 更新(URL.CheckUpdate), 应用管理(URL.manageFunction),

        修改应用(URL.submaiManaageFuntion), 修改应用V3(URL.submaiManaageFuntionV3), 订阅消息列表v3(URL.subscibeListFuntionV3), 全部订阅消息列表v3(URL.allSubscibeListFuntionV3),

        修改订阅情况v3(URL.changeSubscibeFuntionV3), 功能列表_v3(URL.function_v3), 常用功能列表_v3(URL.function_cyyy_v3), 常用功能列表_v31(URL.function_cyyy_v31), 推送功能列表(URL.getpushfunction), 办公电话(URL.bgdh), 获取个人中心URL(URL.getGrzxUrl), gongnengjieshao(URL.gongnengjieshao), webticketurl(URL.webgetTicket);

        private String url;

        private UIA(String url) {
            this.url = url;
        }

        public String getUrl() {
            return uia + url;
        }
    }

    public static enum PUSH {
        聊天接口(URL.fslt), 聊天反馈接口(URL.fslt), 离线聊天接口(URL.lxlt),

        推送反馈接口(URL.pushreceive), 推送离线接口(URL.pushoffine),

        添加好友反馈接口(URL.tjhyfk), 添加好友离线接口(URL.lxtjhy),

        确认添加好友反馈接口(URL.qrtjhyfk), 确认添加好友离线接口(URL.lxqrtjhy),

        离线接口(URL.lxjk);
        private String url;

        private PUSH(String url) {
            this.url = url;
        }

        public String getUrl() {
            if ("重庆三峡学院".equals(Constants.xxmc)) {
                return dc + url;
            } else {
                return push + url;
            }
        }

    }

    public static enum WDDXSERVER {

        webticketurl(URL.webgetTicket), weburl_shujubaogao(URL.webgetShujubaogao),

        appticketurl(URL.appTicket),

        webqiandaobaogao(URL.webgetQianDaoBaoGao), kechengqiandaoinfo(URL.getQiandao),

        sumitqiandao(URL.submitQiandao),

        xuexijihua(URL.xuexiejihua), wodechenji(URL.wodechenji),

        guakeyujin(URL.gakeyujin), jiankanbaogoa(URL.jiankangbaogao),

        guiqindata(URL.anshiguiqindata), guiqinqiandao(URL.sumguiqiandata),

        getmubiaokutitle(URL.mubiaokuTitle),

        getmubiaocontent(URL.mubiaokuContent),

        mubiaoguangchang(URL.weburl_mubiaoguangchang), getuserinfo(URL.getUserinfo),

        submitider(URL.getfeeback), gongnengjieshao(URL.gongnengjieshao),
        getHaveBreakfastInfo(URL.getHaveBreakfastInfo),

        updataNickName(URL.updataNickName), updataQQ(URL.updataQQ), updataPhoneNumber(URL.updataPhoneNumber),

        MuBiaoDetail(URL._目标详情), ZhiZhuMuBiao(URL.zizhumubiao), ChuangJianMuBiao(URL.createTarg), mubiaoqiandao(
                URL.target_qd), FengXinagMuBiao(URL.fengxiang_mubiao), ZaoCanQianDao(URL.setHaveBreakfastInfo), YunDongInfo(URL.getJianChiYunDongInfo), YunDonQianDao(URL.setJianChiYunDongQianDao), MoreZhiZhuMuBiao(URL.moreZhiZhuMuBiao);

        private String url;

        private WDDXSERVER(String url) {
            this.url = url;
        }

        public String getUrl() {
            return wddx + url;
        }
    }

    public static enum PushServer {
        getMessageByID(URL.getMessageByID),

        teachPush(URL.teachPush);

        private String url;

        private PushServer(String url) {
            this.url = url;
        }

        public String getUrl() {
            if ("重庆三峡学院".equals(Constants.xxmc)) {
                return dc + url;
            } else {
                return push + url;
            }
        }
    }

    public static enum GroupChat {
        getXSBJLB(URL.getXSBJLB),

        getUserDetail(URL.getUserDetail), teachPush(URL.teachPush), getXSBJLBXQ(URL.getXSBJLBXQ);

        private String url;

        private GroupChat(String url) {
            this.url = url;
        }

        public String getUrl() {
            return groupchat + url;
        }
    }


    public static enum Ydyx {
        STUDENTINFO(URL.getstudentinfo), RXZBLB(URL.getyrxlb), NOTICE(URL.getytzgg), GETBASICINFO(URL.getbasicinfo), SETBASICINFO(URL.setbasicinfo),

        GETRELATIVES(URL.getrelativesinfo), SETRELATIVES(URL.setrelativesinfo), GETEDUCATIONIFO(URL.geteducationinfo), SETENUCATIONIFO(URL.seteducationinfo),

        SENDCODE(URL.sendcode), JIANYIAN(URL.jianyancode), OVERTASK(URL.overTask), WSYX(URL.wsyx), YXXC(URL.yxxc), GETUSERINFO(URL.userifno), UPDATAPHONENUMBER(URL.setupdataphonenumber),

        DELECTRELATIVES(URL.delectRelativesinfo), DELECTDUCATION(URL.delectDucationinfo), JYWEBURL(URL.jyweb_url), USERINFOURL(URL.userifno_url);
        private String url;

        private Ydyx(String url) {
            this.url = url;
        }

        public String getUrl() {
            return ydyx + url;
        }
    }

    private class URL {
        private static final String syxsdmjg = "/DC/zhkt/getAllxsdmjg.action";
        private static final String fszt = "/DC/zhkt/saveZtnr.action";
        private static final String score = "/DC/score/getlist.action";
        private static final String login = "/UIA/login/login.action";
        private static final String logon = "/UIA/login/logon.action";
        private static final String logon2 = "/UIA/login/logon_ltpa.action";
        private static final String tongxunlu = "/DC/txl_teacher/getlist.action";
        private static final String term = "/DC/calendar/list.action";
        private static final String nowterm = "/DC/calendar/nowterm.action";
        private static final String webview = "/UIA/rout/geturl.action";
        private static final String kbcx = "/DC/Course/list.action";
        private static final String fqdm = "/DC/zhkt/fqdm.action";
        private static final String xsdm = "/DC/zhkt/xsdm.action";
        private static final String ktxslb = "/DC/zhkt/ktxs.action";
        // 应用
        private static final String function = "/UIA/function/lists.action";
        private static final String function_v3 = "/UIA/v3/function/function_list.action";
        private static final String function_cyyy_v31 = "/UIA/v3/function/function_home_list.action";
        //        private static final String function_cyyy_v3 = "/UIA/function/getcyyy.action";
        private static final String function_cyyy_v3 = "/UIA/v3/function/function_home_list.action";
        private static final String function_old = "/UIA/function/getlist.action";
        private static final String download_page = "/UIA/?";
        private static final String download_page_old = "/UIA/?userid=";
        private static final String ydmxslb = "/DC/zhkt/yyydmxs.action";
        private static final String tjdm = "/DC/zhkt/tjdmjg.action";
        private static final String CheckUpdate = "/UIA/update/updateVersion.action?mobileType=android&VersionCode=";
        private static final String map = "/DC/map/getlist.action";
        private static final String dmcs = "/DC/zhkt/getDmcs.action";
        private static final String dmbj = "/DC/zhkt/dmbj.action";
        private static final String jsxztxsmd = "/DC/zhkt/getJsztxsmd.action";
        private static final String jsdhknr = "/DC/zhkt/getJsdhknr.action";
        private static final String xsdhknr = "/DC/zhkt/getXsdhknr.action";
        private static final String xspj = "/DC/zhkt/saveXspj.action";
        private static final String getxspj = "/DC/zhkt/getXspjnr.action";
        private static final String getjspj = "/DC/zhkt/getXspjnr.action";
        private static final String saveKtxx = "/DC/zhkt/saveKtxx.action";
        private static final String pushreceive = "/push/receive.action";
        private static final String pushoffine = "/push/offine.action";
        private static final String setting = "/UIA/user/updateuser.action";
        private static final String password = "/UIA/user/updatepassword.action";
        private static final String jslb = "/DC/zhkt/getKtjsbh.action";
        private static final String qzhylb = "/DC/sq/hqhylb.action";
        private static final String qzmrlb = "/DC/sq/hqmrfz.action";
        private static final String sshy = "/DC/sq/msrcx.action";
        private static final String schy = "/DC/sq/schy.action";
        private static final String tjhy = "/DC/sq/tjhy.action";
        private static final String sftytjhy = "/DC/sq/yzhy.action";
        private static final String jjr = "/DC/calendar/jjr.action";
        private static final String sysj = "/DC/calendar/termlist.action";
        //3.0提交反馈意见
        private static final String submitFeedbackV3 = "/DC/grzx/suggestions.action";

        private static final String fslt = "/PushServer/chat/sendChat.action";
        private static final String lxlt = "/PushServer/chat/offineSend.action";
        private static final String tjhyfk = "/PushServer/chat/receiveAdd.action";
        private static final String lxtjhy = "/PushServer/chat/offineAdd.action";
        private static final String qrtjhyfk = "/PushServer/chat/receiveReadd.action";
        private static final String lxqrtjhy = "/PushServer/chat/offineReadd.action";
        private static final String lxjk = "/PushServer/offine/all.action";
        private static final String getAD = "/DC/homepage/getAD.action";

        private static final String getMessageByID = "/PushServer/message/getMessageByID.action";
        //推送功能列表
        private static final String getpushfunction = "/UIA/function/getpushfunction.action";
        public static final String getXSBJLB = "/GroupChat/Contacts/getXSBJLB.action";
        // 获取用户信息
        public static final String getUserDetail = "/GroupChat/Contacts/getUserDetail.action";

        // 获取班级详情
        public static final String getXSBJLBXQ = "/GroupChat/Contacts/getXSBJLBXQ.action";
        // 教师推送
        public static final String teachPush = "/PushServer/teach/push.action";

        /**
         * 票据
         */
        // 原生 ticket
        private static final String appTicket = "/WDDXSERVER/ticket/createTicket.action";
        // web ticket
        private static final String webgetTicket = "/WDDXSERVER/ticket/createAppTicket.action";

        /**
         * 数据
         */
        // 数据报告
        private static final String webgetShujubaogao = "/WDDXSERVER/homePage.jsp?userid=";
        // 课程签到报告
        private static final String webgetQianDaoBaoGao = "/WDDXSERVER/homeapp/getSTATForKCQD.action?ticket=";
        // 课程签到列表
        private static final String getQiandao = "/WDDXSERVER/homeapp/getKCQDLB.action";
        // 课程签到
        private static final String submitQiandao = "/WDDXSERVER/homeapp/setKCQD.action";
        // 学习计划
        private static final String xuexiejihua = "/WDDXSERVER/homeapp/getXxjhWithApp.action?ticket=";
        // 我的成绩
        private static final String wodechenji = "/WDDXSERVER/homeapp/getCjWithApp.action?ticket=";
        // 挂科预警
        private static final String gakeyujin = "/WDDXSERVER/homeapp/getGkWithApp.action?ticket=";
        // 健康报告
        private static final String jiankangbaogao = "/WDDXSERVER/homeapp/getEchartsForSports.action?ticket=";
        // 目标广场
        private static final String weburl_mubiaoguangchang = "/WDDXSERVER/targetSquare/mySquare.jsp?userid=";

        // 按时归寝 参数 userid ticket
        private static final String anshiguiqindata = "/WDDXSERVER/homeapp/getGetBackInfo.action";
        // 提交归寝签到时间 参数u serid ticket
        private static final String sumguiqiandata = "/WDDXSERVER/homeapp/setGetBack.action";
        // 目标库标题
        private static final String mubiaokuTitle = "/WDDXSERVER/target/getTargetBaseTitleWithApp.action";
        // 目标库任务列表
        private static final String mubiaokuContent = "/WDDXSERVER/target/getTarget.action";

        // 个人中心得到用户信息
        private static final String getUserinfo = "/WDDXSERVER/mine/getStudentInfo.action";
        // 修改昵称
        private static final String updataNickName = "/WDDXSERVER/mine/updateStudentPetName.action";
        // 修改联系电话
        public static final String updataPhoneNumber = "/WDDXSERVER/mine/updateStudentPhoneNumber.action";
        // 修改QQ
        public static final String updataQQ = "/WDDXSERVER/mine/updateStudentQQ.action";

        // 提交意见反馈
        private static final String getfeeback = "/WDDXSERVER/mine/feedbackSuggestions.action";
        // 关于我们功能介绍
        private static final String gongnengjieshao = "/WDDXSERVER/mine/introduce.action?userid=";
        // 自主目标
        private static final String zizhumubiao = "/WDDXSERVER/target/GetMineTarget.action";
        // 目标详情
        private static final String _目标详情 = "/WDDXSERVER/target/getTargetDetails.action";
        // 创建目标
        private static final String createTarg = "/WDDXSERVER/target/newAddTarget_student.action";
        // 目标签到
        private static final String target_qd = "/WDDXSERVER/target/target_QD.action";
        //目标查看更多
        private static final String moreZhiZhuMuBiao = "/WDDXSERVER/target/GetMineTarget.action";

        // 分享目标
        private static final String fengxiang_mubiao = "/WDDXSERVER/target/shareTarget.action";

        // 获得应用管理数据
        private static final String manageFunction = "/UIA/function/allFunction.action";
        // 提交应用修改
        private static final String submaiManaageFuntion = "/UIA/function/updateDisplay.action";
        // 3.0提交应用修改
        private static final String submaiManaageFuntionV3 = "/UIA/function/updatecyyy.action";
        // 3.0获取订阅消息列表
        private static final String subscibeListFuntionV3 = "/UIA/function/getpushfunction.action";
        // 3.0获取全部订阅消息列表
        private static final String allSubscibeListFuntionV3 = "/UIA/function/getUserSubscibePushFunction.action";
        //3.0修改订阅情况
        private static final String changeSubscibeFuntionV3 = "/UIA/function/setpushfunction.action";
        //3.0办公电话连接
        private static final String bgdh = "/bgdh/#/";
        //3.0获取个人中心URL
        private static final String getGrzxUrl = "/UIA/function/getGrzxUrl.action";


        /**
         * 健康早餐信息获取接口
         */
        private static final String getHaveBreakfastInfo = "/WDDXSERVER/homeapp/getHaveBreakfastInfo.action";
        // 健康早餐签到
        private static final String setHaveBreakfastInfo = "/WDDXSERVER/homeapp/setHaveBreakfast.action";
        // 获得坚持运动数据
        private static final String getJianChiYunDongInfo = "/WDDXSERVER/homeapp/getSportsInfo.action";
        // 坚持运动签到
        private static final String setJianChiYunDongQianDao = "/WDDXSERVER/homeapp/setSportsInfo.action";

        /**************************************************************************************************
         *
         *                                           迎新系统
         *
         * *************************************************************************************************
         */

        /**
         * 入学准备模块
         * -- userid ticket
         */
        //用户信息 参数--
        private static final String getstudentinfo = "/ydyx/rxzb/rxzb_xsxx.action";
        //已交金额 参数--
        private static final String getyjje = "/ydyx/rxzb/rxzb_yjje.action";
        //寝室状态 参数--
        private static final String getyqsstate = "/ydyx/rxzb/rxzb_qszt.action";
        //入学准备列表  参数--
        private static final String getyrxlb = "/ydyx/rxzb/rxzb_rxzblb.action";
        //通知通告
        private static final String getytzgg = "/ydyx/rxzb/rxzb_tzgg.action";
        //改变信息状态
        private static final String getytchenagestate = "/ydyx/rxzb2_updatexxcjzt.action";

        /**
         * 学生信息录入
         */

        //保存学生基本信息 userid,ticket,params
        private static final String setbasicinfo = "/ydyx/rxzb2/rxzb2_saveXsjbxx.action";
        //查询学生基本信息
        private static final String getbasicinfo = "/ydyx/rxzb2/rxzb2_getXsjbxx.action";
        //查询学生亲属信息
        private static final String getrelativesinfo = "/ydyx/rxzb2/rxzb2_getXsqsxx.action";
        //保存学生亲属信息 userid,ticket,params
        private static final String setrelativesinfo = "/ydyx/rxzb2/rxzb2_saveXsqsxx.action";
        //查询学生教育信息
        private static final String geteducationinfo = "/ydyx/rxzb2/rxzb2_getXscdxx.action";
        //保存学生教育信息
        private static final String seteducationinfo = "/ydyx/rxzb2/rxzb2_savaXscdxx.action";
        //发送验证码 参数 userid ticket mob
        private static final String sendcode = "/TicketService/sms/sendSMS";
        //验证验证码 userid ticket yzm
        private static final String jianyancode = "/TicketService/sms/checkSMS";
        //完成任务调用接口
        private static final String overTask = "/ydyx/rxzb/rxzb_setrxzblb.action";
        //删除学生亲属信息 ticket params &userid /ydyx/rxzb2/rxzb2_deleteXsqsxx
        private static final String delectRelativesinfo = "/ydyx/rxzb2/rxzb2_deleteXsqsxx";
        //删除学生教育信息
        private static final String delectDucationinfo = "/ydyx/rxzb2/rxzb2_deleteXscdxx";

        /**
         * 报到流程
         */
        //网上迎新
        private static final String wsyx = "/ydyx/bdlc/bdlc_getWSYXLB.action";
        //迎新现场
        private static final String yxxc = "/ydyx/bdlc/bdlc_getYXXC.action";
        /**
         * 个人资料
         */
        private static final String userifno = "/ydyx/tyjk/tyjk_getXsjbxx";
        /**
         * 采集手机号码
         */

        private static final String setupdataphonenumber = "/ydyx/rxzb2/rxzb2_updatePhoneNUmber.action";

        /**
         * web 接口
         * userid,web ticket
         */
        //电子通知书
        private static final String getzs = "/ydyx/grzl/grzl_dztzs.action";

        //学费查询
        private static final String gettuitioninfo = "/ydyx/grzl/grzl_xfcx.action";
        //寝室查询
        private static final String getqsinfo = "/ydyx/grzl/grzl_xfcx.action";
        //EMS查询
        private static final String getems = "/ydyx/grzl/grzl_ems.action";
        //保险购买
        private static final String getbxgm = "/ydyx/bdlc/bdlc_skipBxgm.action";
        //报道交通
        private static final String getbdjt = "/ydyx/rxzb2/rxzb2_skipBdjt.action";
        //报道交通信息采集
        private static final String getbdjtcjinfo = "/ydyx/rxzb2/rxzb2_saveBdjt.action";
        //绿色通道
        private static final String getlstd = "/ydyx/rxzb2/rxzb2_skipLstd.action";
        //绿色通道信息采集
        private static final String getlstdinfocj = "/ydyx/rxzb2/rxzb2_bllstd.action";
        //新生接待
        private static final String getxsjd = "/ydyx/rxzb2/rxzb2_xsjd.action";
        //生活用品
        private static final String getshyp = "/ydyx/bdlc/bdlc_skipShyp.action";
        //修改生活用品购买状态 userid,ticket,state State:是否购买（是、否）
        private static final String getshypstate = "/ydyx/bdlc/bdlc_shyp.action";
        //财务缴费
        private static final String getcwjf = "/ydyx/bdlc_skipJxfzcj.action";
        //军训服装采集 userid,ticket,json
        private static final String getjfcj = "/ydyx/bdlc/bdlc_jxfzcj.action";

        //学生基本信息（顶部） userid,ticket,json
        private static final String getxsjbinfo = "/ydyx/rxzb/rxzb_xsxx.action";

        //入学功能列表完成时调用接口 参数function_id ticket userid
        private static final String setover = "/ydyx/rxzb/rxzb_setrxzblb.action";

        private static final String jyweb_url = "/ydyx/rxzb/rxzb_skipyxc.action?";

        private static final String userifno_url = "/ydyx/rxzb2/rxzb2_dxrz.action";


    }

}