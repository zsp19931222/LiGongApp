package com.yunhuakeji.app.utils;

public class TicketHelper
{
	public static void setTicket(String ticket)
	{
		if (IsNull.isNotNull(ticket))
		{
			Constants.ticket = ticket;
		}
	}
}