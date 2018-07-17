package 云华.智慧校园.工具;

import java.util.Random;

public class RandomTools
{
    /**
     * 生成一个随机数，长度一定，不足添0占位
     * 
     * @param range
     * @return
     */
    public static String getRandomNumberAndSeat(int range)
    {
	int length = String.valueOf(range).length();
	int ran = new Random().nextInt(range);
	String string = "";
	if (String.valueOf(ran).length() < length)
	{
	    for (int i = 0; i < length - String.valueOf(ran).length(); i++)
	    {
		string += "0";
	    }
	}
	return string + ran;
    }

    /**
     * 生成一个随机数，不添0占位
     * 
     * @param range
     * @return
     */
    public static int getRandomNumber(int range, int outSide)
    {
	int random = new Random().nextInt(range);
	if (random == outSide)
	{
	    random = getRandomNumber(range, outSide);
	}
	return new Random().nextInt(range);
    }

    /**
     * 生成一个随机数，不添0占位
     * 
     * @param range
     * @return
     */
    public static int getRandomNumber(int range, int... outSide)
    {
	int random = new Random().nextInt(range);
	if (inSide(random, outSide))
	{
	    random = getRandomNumber(range, outSide);
	}
	return new Random().nextInt(range);
    }

    private static boolean inSide(int random, int... outSide)
    {
	if (outSide == null || outSide.length == 0)
	{
	    return false;
	} else
	{
	    for (int i = 0; i < outSide.length; i++)
	    {
		if (outSide[i] == random)
		{
		    return false;
		}
	    }
	    return true;
	}
    }
}
