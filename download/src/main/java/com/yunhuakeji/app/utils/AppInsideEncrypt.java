package com.yunhuakeji.app.utils;

public class AppInsideEncrypt
{
	public static int[] setHealthSetp(int setp)
	{
		return new int[] {setp / StepDetector.CURRENT_SETP_ROMDAN, setp % StepDetector.CURRENT_SETP_ROMDAN};
	}
}