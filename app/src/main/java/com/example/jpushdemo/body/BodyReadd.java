package com.example.jpushdemo.body;

public class BodyReadd extends Body
{
	private String fqr, sfty, fssj, deal, id, userid, fqrname;

	public BodyReadd(String fqr, String sfty, String fssj, String deal, String id, String userid, String fqrname)
	{
		this.fqr = fqr;
		this.sfty = sfty;
		this.fssj = fssj;
		this.deal = deal;
		this.id = id;
		this.userid = userid;
		this.fqrname = fqrname;
	}

	public String getFqrname()
	{
		return fqrname;
	}

	public String getId()
	{
		return id;
	}

	public String getFqr()
	{
		return fqr;
	}

	public String getSfty()
	{
		return sfty;
	}

	public String getFssj()
	{
		return fssj;
	}

	public String getDeal()
	{
		if (sfty.equals("1"))
		{
			deal = BodyAdd.DEAL_AGREE;
		} else
		{
			deal = BodyAdd.DEAL_DISAGREE;
		}
		return deal;
	}

	public String getUserid()
	{
		return userid;
	}

}