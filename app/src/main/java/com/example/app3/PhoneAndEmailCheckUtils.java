package com.example.app3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneAndEmailCheckUtils
{

    public static boolean checkPhoneOrEmail(String str)
    {
	return isPhoneLegal(str) || checkEmail(str);
    }

    /**
     * 大陆号码或香港号码均可
     */
    public static boolean isPhoneLegal(String str)
    {
	try
	{
	    return isChinaPhoneLegal(str) || isHKPhoneLegal(str);
	} catch (Exception e)
	{
	    return false;
	}

    }

    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数 18+除1和4的任意数
     * 17+除9的任意数 147
     */
    private static boolean isChinaPhoneLegal(String str)
    {
	String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
	Pattern p = Pattern.compile(regExp);
	Matcher m = p.matcher(str);
	return m.matches();
    }

    /**
     * 香港手机号码8位数，5|6|8|9开头+7位任意数
     */
    private static boolean isHKPhoneLegal(String str)
    {
	String regExp = "^(5|6|8|9)\\d{7}$";
	Pattern p = Pattern.compile(regExp);
	Matcher m = p.matcher(str);
	return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */

    public static boolean checkEmail(String email)
    {
	boolean flag = false;
	try
	{
	    String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern regex = Pattern.compile(check);
	    Matcher matcher = regex.matcher(email);
	    flag = matcher.matches();
	} catch (Exception e)
	{
	    flag = false;
	}
	return flag;
    }

}