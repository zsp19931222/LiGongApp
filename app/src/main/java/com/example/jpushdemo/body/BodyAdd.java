package com.example.jpushdemo.body;

public class BodyAdd extends Body
{
	public final static String DEAL_AGREE = "agree";
	public final static String DEAL_DISAGREE = "disagree";
	public final static String DEAL_NO = "nodeal";
	public final static String DEAL_NOREAD = "false";
	public final static String DEAL_FUNCTION_ID = "20170414120036905";
	public final static String ADD = "202";
	public final static String DEAL_SFTY_YES = "1";

	/**
	 * id : 2017-04-14 09:37:29561 message : 杨瑜:请求添加好友 fqrname : 杨瑜 title : 好友验证信息 isreceive : 1 fqr : 20141028 fjnr : 杨瑜:请求添加好友 ticket : 1c26d51e-596b-438a-bf76-287d85f30053 fssj : 2017-04-14 09:37:29 code : 202 jsr : 20141029
	 */

	private String id;
	private String message;
	private String fqrname;
	private String title;
	private String isreceive;
	private String fqr;
	private String fjnr;
	private String ticket;
	private String fssj;
	private String code;
	private String jsr;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getFqrname()
	{
		return fqrname;
	}

	public void setFqrname(String fqrname)
	{
		this.fqrname = fqrname;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getIsreceive()
	{
		return isreceive;
	}

	public void setIsreceive(String isreceive)
	{
		this.isreceive = isreceive;
	}

	public String getFqr()
	{
		return fqr;
	}

	public void setFqr(String fqr)
	{
		this.fqr = fqr;
	}

	public String getFjnr()
	{
		return fjnr;
	}

	public void setFjnr(String fjnr)
	{
		this.fjnr = fjnr;
	}

	public String getTicket()
	{
		return ticket;
	}

	public void setTicket(String ticket)
	{
		this.ticket = ticket;
	}

	public String getFssj()
	{
		return fssj;
	}

	public void setFssj(String fssj)
	{
		this.fssj = fssj;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getJsr()
	{
		return jsr;
	}

	public void setJsr(String jsr)
	{
		this.jsr = jsr;
	}

}