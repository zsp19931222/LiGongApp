package com.example.smartclass.util;

/**
 * Created by Administrator on 2018/1/18 0018.
 * 学生到勤情况
 */
public class BeanState {

    public static class DM_State {

        /** *****************************到勤状态********************************************** */
        /**
         * 道勤
         */
        public static final String DQ = "0";

        /**
         * 请假
         */
        public static final String QJ = "1";

        /**
         * 旷课
         */
        public static final String KK = "2";

        /**
         * 迟到
         */
        public static final String CD = "3";

        /**
         * 早退
         */
        public static final String ZT = "4";

        /**
         * 未到（初始状态）
         */
        public static final String WD = "5";

        /*******************************点名状态***********************************************/
        /**
         * 未点名
         */
        public static final String WDM = "0";

        /**
         * 已点名
         */
        public static final String YDM = "1";

        /**
         * 正在点名
         */
        public static final String ZZDM = "2";

        /**
         * 正在倒计时
         */
        public static final String ZZDJS = "3";

        /**
         * 已签到
         */
        public static final String YQD = "4";

        /*******************************上课状态***********************************************/
        /**
         * 正在上课
         */
        public static final String ZZS = "0";

        /**
         * 将要上
         */
        public static final String JYS = "1";

        /**
         * 已经上
         */
        public static final String YJS = "2";

    }

    public static class LOCK {
        /**
         * 未加锁状态代码
         */
        public static final String NO = "0";

        /**
         * 加锁状态代码
         */
        public static final String YES = "1";
    }

    public static class UserType {

        /**
         * 用户类型，学生
         */
        public static final String STUDENT = "1";

        /**
         * 用户类型，教师
         */
        public static final String TEACHER = "2";
    }

    /**
     * 到勤情况
     */
    public static class ArriveCondition {
        /**
         * 到勤
         */
        public static final String WD = "未到";

        /**
         * 到勤
         */
        public static final String DQ = "到勤";

        /**
         * 请假
         */
        public static final String QJ = "请假";

        /**
         * 旷课
         */
        public static final String KK = "旷课";

        /**
         * 迟到
         */
        public static final String CD = "迟到";

        /**
         * 早退
         */
        public static final String ZT = "早退";
    }
}
