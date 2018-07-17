package com.example.smartclass.network;

import com.example.smartclass.activity.SCMainActivity;

import org.androidpn.push.Constants;

import yh.app.tool.SqliteHelper;
import yh.app.tool.Ticket;
import 云华.智慧校园.工具.ConfigTools;

/**
 * Created by Administrator on 2018/1/18 0018.
 * <p>
 * 智慧课堂地址管理
 */

public class UrlUtil {
    private static final String BaseUrl =ConfigTools.loadProperties(Constants.xx + "smartclassapp")+"/zhkt/";
    private static final String BaseUrl2 = ConfigTools.loadProperties(Constants.xx + "smartclassapp")+"/zhkt/";
    /**
     * 首页时间信息获取
     */
    public static final String DQZC = BaseUrl + "dqzc";

    /**
     * 根据第几周、星期几获取该天的课程列表
     */
    public static final String Course = BaseUrl + "course";

    /**
     * 智慧课堂获取课程详情
     */
    public static final String CourseDetail = BaseUrl + "course/detail";

    /**
     * 获取该堂课的智慧课堂id
     */
    public static final String CourseID = BaseUrl + "zhktid";

    /**
     * 上课学生列表以及到勤状态状态
     */
    public static final String StudentAndStateList = BaseUrl + "xsdmjg";

    /**
     * 获取验证码
     */
    public static final String GetCode = BaseUrl + "fqdm";

    /**
     * 获取课堂点名状态
     */
    public static final String GetState = BaseUrl + "dqdmzt";

    /**
     * 学生签到
     */
    public static final String SignIn = BaseUrl + "xsdm";

    /**
     * 教师提交点名
     */
    public static final String SubmitCall = BaseUrl + "tjdm";

    /**
     * 修改学生状态
     */
    public static final String ChangeState = BaseUrl + "xgdm";

    /**
     * 获取点名状态
     */
    public static final String GetCanSubmitCallState = BaseUrl + "dmzt";

    /**
     * 未评次数
     */
    public static final String NoEvaluateContent = BaseUrl + "pj/kcwpjsl";

    /**
     * 获取班级成员列表
     */
    public static final String GetStudentList = BaseUrl2 + "bjcy";


    /**
     * 获取学生详情
     */
    public static final String GetStudentMessage = BaseUrl2 + "ryxq";

    /**
     * 教师-我的授课
     */
    public static final String MyClass = BaseUrl2 + "kclb";

    /**
     * 教师-课程状态
     */
    public static final String ClassState = BaseUrl2 + "skzt";


    private static final String WebBaseUrl = ConfigTools.loadProperties(Constants.xx + "smartclassweb")+"/zhktweb/";
    private static final String WebBaseParameter = "function_id=" + SCMainActivity.function_id + "&ticket=" + Ticket.getFunctionTicket(SCMainActivity.function_id);

    /**
     * 学生端点名记录
     */
    public static final String CallRecordStudent = WebBaseUrl + "#/dmjls?" + WebBaseParameter + "&zhktid=";

    /**
     * 教师端点名记录
     */
    public static final String CallRecordTeacher = WebBaseUrl + "#/dmxq?" + WebBaseParameter + "&zhktid=";

    /**
     * 教师端授课详情列表
     */
    public static final String TeachDetailTeacher = WebBaseUrl + "#/skxxlb?" + WebBaseParameter + "&kcbh=";

    /**
     * 学生端授课详情列表
     */
    public static final String TeachDetailStudent = WebBaseUrl + "#/skxxstu_lb2?" + WebBaseParameter + "&kcbh=";


    /**
     * 教师端未评记录
     */
    public static final String NoEvaluateTeacher = WebBaseUrl + "#/wpjl?" + WebBaseParameter + "&kcbh=";

    /**
     * 学生端未评记录
     */
    public static final String NoEvaluateStudent = WebBaseUrl + "#/wpjlstu?" + WebBaseParameter + "&kcbh=";

    /**
     * 教师评学
     */
    public static final String TeachersEvaluate = BaseUrl2 + "pj/pjym?" + WebBaseParameter + "&zhktid=";

    /**
     * 学生评教
     */
    public static final String StudentsEvaluate = BaseUrl2 + "pj/pjym?" + WebBaseParameter + "&zhktid=";


}
