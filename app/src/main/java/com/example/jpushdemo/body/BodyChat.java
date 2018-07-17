package com.example.jpushdemo.body;

public class BodyChat extends Body
{
	public final static String CHAT_TEXT = "201";
	public final static String CHAT_ADD = "202";
	public final static String CHAT_READD = "203";

	public final static String[] names = new String[]
	{
			"fqr", "message", "fssj", "code", "isRead", "userid"
	};

	private String id, fqr, message, fssj, code, isRead, userid, ticket;

	public BodyChat(String... parames)
	{
		if (parames != null && parames.length == 8)
		{
			this.fqr = parames[0];
			this.message = parames[1];
			this.fssj = parames[2];
			this.code = "201";
			this.isRead = parames[4];
			this.userid = parames[5];
			this.ticket = parames[6];
			this.id = parames[7];
		}
	}

	public String getId()
	{
		return id;
	}

	public String getTicket()
	{
		return ticket;
	}

	public String getUserid()
	{
		return userid;
	}

	public String getFqr()
	{
		return fqr;
	}

	public String getMessage()
	{
		return message;
	}

	public String getIsRead()
	{
		return isRead;
	}

	public String getFssj()
	{
		return fssj;
	}

	public String getCode()
	{
		return code;
	}

	public String[] getAll()
	{
		return new String[]
		{
				fqr, message, fssj, code, isRead
		};
	}
}