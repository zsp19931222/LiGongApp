package com.example.jpushdemo.body;

public class BodyPush extends Body
{
	public final static String PUSH_TEXT = "101";
	public final static String PUSH_URL = "102";
	public final static String PUSH_TEACH = "401";

	public final static String READ_YES = "true";
	public final static String READ_NO = "false";

	private String id, message, fssj, isread, userid, code, url, func_id, title, ticket,n_picpath;

	public BodyPush(String id, String message, String fssj, String isread, String userid, String code, String url, String func_id, String title, String ticket,String n_picpath)
	{
		this.id = id;
		this.message = message;
		this.fssj = fssj;
		this.isread = isread;
		this.userid = userid;
		this.code = code;
		this.url = url;
		this.func_id = func_id;
		this.title = title;
		this.ticket = ticket;
		this.n_picpath=n_picpath;
	}

	public BodyPush(String[] parames)
	{
		this.id = parames[0];
		this.message = parames[1];
		this.fssj = parames[2];
		this.isread = parames[3];
		this.userid = parames[4];
		this.code = parames[5];
		this.url = parames[6];
		this.func_id = parames[7];
		this.title = parames[8];
		this.ticket = parames[9];
		
	}

	public String getId()
	{
		return id;
	}

	public String getMessage()
	{
		return message;
	}

	public String getFssj()
	{
		return fssj;
	}

	public String getIsread()
	{
		return isread;
	}

	public String getUserid()
	{
		return userid;
	}

	public String getCode()
	{
		return code;
	}

	public String getUrl()
	{
		return url;
	}

	public String getFunc_id()
	{
		return func_id;
	}

	public String getTitle()
	{
		return title;
	}

	public String getTicket()
	{
		return ticket;
	}
	
	public String getnPicPath(){
		return n_picpath;
	}

}
