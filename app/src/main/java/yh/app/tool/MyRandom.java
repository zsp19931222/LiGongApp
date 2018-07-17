package yh.app.tool;

import java.util.Random;

public class MyRandom
{
	public static String MyRandom()
	{
		Random r = new Random();
		return String.valueOf(r.nextInt(10000));
	}
}
