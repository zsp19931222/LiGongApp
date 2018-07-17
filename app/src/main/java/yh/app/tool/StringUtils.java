package yh.app.tool;

import android.text.TextUtils;

/**
 * Title: StringUtils.java Description: 字符串操作类
 * 
 * @author 阙海林 Company 云华科技
 * @lastchange 2016年12月12日
 * @date 2016年12月12日
 * @version 1.0.1
 */
public class StringUtils {
	/**
	 * 判断字符串是否在规定字符串数组以内
	 * 
	 * @param str
	 * @param others
	 *            规定字符串数组
	 * @return
	 */
	public static boolean inString(String str, String... others) {
		if (str == null || others == null)
			return false;
		for (int i = 0; others != null && str != null && i < others.length; i++) {
			if (str.equals(others[i])) {
				return true;
			}
		}
		return false;
	}

	public static boolean isPhoneNumber(String number) {
		String num = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(number)) {
			return false;
		} else {
			// matches():字符串是否在给定的正则表达式匹配
			return number.matches(num);
		}
	}
}