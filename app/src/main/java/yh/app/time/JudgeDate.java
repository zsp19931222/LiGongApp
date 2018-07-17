package yh.app.time;

import java.text.SimpleDateFormat;
/**
 * 
 * 包	名:yh.app.time
 * 类	名:JudgeDate.java
 * 功	能:判断日期
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class JudgeDate {

	/**
      * �ж��Ƿ�Ϊ�Ϸ�������ʱ���ַ�
      * @param str_input
      * @param str_input
      * @return boolean;���Ϊtrue,�����Ϊfalse
      */
	public static  boolean isDate(String str_input,String rDateFormat){
		if (!isNull(str_input)) {
	         SimpleDateFormat formatter = new SimpleDateFormat(rDateFormat);
	         formatter.setLenient(false);
	         try {
	             formatter.format(formatter.parse(str_input));
	         } catch (Exception e) {
	             return false;
	         }
	         return true;
	     }
		return false;
	}
	public static boolean isNull(String str){
		if(str==null)
			return true;
		else
			return false;
	}
}