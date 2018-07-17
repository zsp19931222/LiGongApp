package yh.tool.widget;

import java.util.List;

/**
 * 
 * 包 名:yh.tool.widget 类 名:activitys.java 功 能:程序Activity信息设置
 *
 * @author 云华科技
 * @version 1.0
 * @date 2015-7-29
 */
public class activitys
{
	private String title;// 模块名称
	private String layout;// 模块布局文件
	private String className;// 模块类名
	private int type;// 模块类型
	private String packageName;// 模块包名
	private List<keyValue> pram;// 参数列表

	public String getPackageName()
	{
		return packageName;
	}

	public void setPackageName(String packageName)
	{
		this.packageName = packageName;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLayout()
	{
		return layout;
	}

	public void setLayout(String layout)
	{
		this.layout = layout;
	}

	public String getClassName()
	{
		return className;
	}

	public void setClassName(String className)
	{
		this.className = className;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public List<keyValue> getPram()
	{
		return pram;
	}

	public void setPram(List<keyValue> pram)
	{
		this.pram = pram;
	}
}
