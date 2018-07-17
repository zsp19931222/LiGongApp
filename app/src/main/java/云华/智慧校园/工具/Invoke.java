package 云华.智慧校园.工具;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 反射工具类
 * 
 * @author 云华科技
 * @date 2016年10月9日
 */
public class Invoke
{
	public Method[] getAllMethod(Class<?> cls)
	{
		return cls.getMethods();
	}

	public Field[] getAllField(Class<?> cls)
	{
		return cls.getFields();
	}

	public List<String> getGetterName(Class<?> cls)
	{
		List<String> list = new ArrayList<String>();
		Field[] fs = getAllField(cls);
		for (int i = 0; i < fs.length; i++)
		{
			getMethodNameByParame(fs[i].getName(), "get");
		}
		return list;
	}

	public List<String> getSetterName(Class<?> cls)
	{
		List<String> list = new ArrayList<String>();
		Field[] fs = getAllField(cls);
		for (int i = 0; i < fs.length; i++)
		{
			getMethodNameByParame(fs[i].getName(), "set");
		}
		return list;
	}

	public Method getMethod(Class<?> cls, String MName, Object[] parames)
	{
		Method method = null;
		Class<?>[] classes = null;
		if (parames != null)
		{
			classes = new Class<?>[parames.length];
			for (int i = 0; i < parames.length; i++)
			{
				classes[i] = parames[i].getClass();
			}
		}
		try
		{
			method = cls.getDeclaredMethod(MName, classes);
		} catch (Exception e)
		{
		}
		return method;
	}

	public Object doMethod(Class<?> cls, Method method, Object[] parames)
	{
		try
		{
			Object o = method.invoke(cls.newInstance(), parames);
			return o;
		} catch (Exception e)
		{
			return null;
		}
	}

	public Object doMethod(Class<?> cls, String MName, Object[] parames)
	{
		try
		{
			Object o = getMethod(cls, MName, parames).invoke(cls.newInstance(), parames);
			return o;
		} catch (Exception e)
		{
			return null;
		}
	}

	private String getMethodNameByParame(String parameName, String meth)
	{
		return "set" + parameName.substring(0, 1).toUpperCase(Locale.CHINA) + parameName.substring(1);
	}
}