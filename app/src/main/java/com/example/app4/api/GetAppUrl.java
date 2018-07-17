package com.example.app4.api;

/**
 * Created by Administrator on 2018/3/23 0023.
 * 平台api
 */

public class GetAppUrl {

    private static String baseURL = "http://192.168.0.108:8089";

    private static final String dc = baseURL + "/dc";
    private static final String uia = "http://login.i.cqut.edu.cn" + "/uia";
    private static final String show = "http://data.i.cqut.edu.cn" + "/show";


    public enum DC {
        getSchoolList(GetAppUrl.getSchoolList);

        DC(String url) {
            this.url = url;
        }

        private String url;

        public String getUrl() {
            return dc + url;
        }
    }

    public enum UIA {
        getPassWord(GetAppUrl.getPassWord),
        getVerificationCode(GetAppUrl.getVerificationCode),
        verifyVerificationCode(GetAppUrl.verifyVerificationCode),
        verifyID(GetAppUrl.verifyID),
        verifyIDCard(GetAppUrl.verifyIDCard),
        getUserMessage(GetAppUrl.getUserMessage),
        verifyOldPhone(GetAppUrl.verifyOldPhone),
        bindNewPhone(GetAppUrl.bindNewPhone),
        getNewPhoneVerificationCode(GetAppUrl.getNewPhoneVerificationCode),
        verifyOldPhoneNoUsed(GetAppUrl.verifyOldPhoneNoUsed),
        uploadingPhoneMessage(GetAppUrl.uploadingPhoneMessage),
        otherRegister(GetAppUrl.otherRegister),
        getVerificationCodeOther(GetAppUrl.getVerificationCodeOther),
        verifyVerificationCodeOther(GetAppUrl.verifyVerificationCodeOther),
        verifyIdentification(GetAppUrl.verifyIdentification),
        changePassword(GetAppUrl.changePassword),
        forgetPasswordGetCode(GetAppUrl.forgetPasswordGetCode),
        forgetPasswordVerificationCode(GetAppUrl.forgetPasswordVerificationCode),
        forgetPasswordSetting(GetAppUrl.forgetPasswordSetting),
        VerificationPasssword(GetAppUrl.VerificationPasssword),
        getNewUserVerificationCode(GetAppUrl.getNewUserVerificationCode),
        newUserVerifyVerificationCode(GetAppUrl.newUserVerifyVerificationCode),
        newUserUserInfo(GetAppUrl.newUserUserInfo),
        newUserSetPassword(GetAppUrl.newUserSetPassword),
        ;

        UIA(String url) {
            this.url = url;
        }

        private String url;

        public String getUrl() {
            return uia + url;
        }
    }

    public enum Show {
        widgetHomePage(GetAppUrl.widgetHomePage),
        getNavigationList(GetAppUrl.getNavigationList),
        getBannerList(GetAppUrl.getBannerList),
        getADImage(GetAppUrl.getADImage),
        getMySelfList(GetAppUrl.getMySelfList),
        getApplicationList(GetAppUrl.getApplicationList),
        getApplicationClassify(GetAppUrl.getApplicationClassify),
        changeApplicationList(GetAppUrl.changeApplicationList),
        functionList(GetAppUrl.functionList),
        saveFaceUrl(GetAppUrl.saveFaceUrl),
        getURL(GetAppUrl.getURL),

        ;

        Show(String url) {
            this.url = url;
        }

        private String url;

        public String getUrl() {
            return show + url;
        }
    }

    /**
     * 获取App数据show
     */
    private static final String widgetHomePage = "/login/widget/show";//获取首页控件
    private static final String getNavigationList = "/login/navigation";//获取导航栏
    private static final String getBannerList = "/login/banner";//获取banner
    private static final String getADImage = "/login/start";//获取广告图片
    private static final String getMySelfList = "/login/my/widget";//获取我的页面列表
    private static final String getApplicationClassify = "/login/application/class";//获取应用分类
    private static final String changeApplicationList = "/login/change/class";//修改首页应用排序
    private static final String functionList = "/function/push/list";//获取推送列表
    private static final String saveFaceUrl = "/operate/face";//保存用户头像


    /**
     * 登录uia
     */
    private static final String getSchoolList = "/update/school/list";//获取学校列表
    private static final String getPassWord = "/login/password";//获取学校列表
    private static final String getVerificationCode = "/login/getyz";//获取验证码
    private static final String verifyVerificationCode = "/login/dxyz";//验证验证码
    private static final String verifyID = "/login/stuid";//验证学/工号
    private static final String verifyIDCard = "/login/sfzh";//验证身份证号后六位
    private static final String getUserMessage = "/login/usermes";//获取用户基本信息
    private static final String verifyOldPhone = "/login/oldmob";//验证原手机号
    private static final String bindNewPhone = "/login/newmob";//绑定新手机
    private static final String getNewPhoneVerificationCode = "/login/modifyTel";//新手机获取验证码
    private static final String verifyOldPhoneNoUsed = "/login/changemob";//旧手机不可使用身份验证
    private static final String uploadingPhoneMessage = "/login/mob/message";//上传手机信息
    private static final String getApplicationList = "/login/application";//获取首页应用列表
    private static final String otherRegister = "/login/zhyz";//其他登录-验证账号密码
    private static final String getVerificationCodeOther = "/login/message";//其他登录-获取验证码
    private static final String verifyVerificationCodeOther = "/login/check/message";//其他登录-验证验证码
    private static final String verifyIdentification = "/login/zhdl/zhyz";//验证数字化校园
    private static final String changePassword = "/user/reset/password";//修改密码
    private static final String getURL = "/function/url";//获取网页跳转URL
    private static final String forgetPasswordGetCode = "/user/forget/password";//忘记密码获取验证码
    private static final String forgetPasswordVerificationCode= "/user/check/yzm";//忘记密码验证验证码
    private static final String forgetPasswordSetting= "/user/set/newpassword";//忘记密码设置新密码
    private static final String VerificationPasssword= "/login/check/old/password";//验证密码
    private static final String getNewUserVerificationCode= "/newuser/get/yzm";//新用户登录获取验证码
    private static final String newUserVerifyVerificationCode= "/newuser/check/yzm";//新用户登录验证验证码
    private static final String newUserUserInfo= "/newuser/check/userinfo";//新用户登录验证身份信息
    private static final String newUserSetPassword= "/newuser/set/password";//新用户登录设置密码


    /**
     * 其他页面
     * */
    public static final String uploadingFaceUrl="http://file.i.cqut.edu.cn/fs/fileupload";//上传用户头像
}
