package yh.app.zhkttools;

import android.os.Handler;
import yh.app.wisdomclass.zhktAT;

public class zhkt_handler
{
	public zhkt_handler_interface getInterface()
	{
		return achiveInterface(new zhkt_handler_interface()
		{
			@Override
			public void fqdm(Handler mHandler, String xkkh, String djz, String xqj, String djj, String dmcs, String userid)
			{
				zhktAT AT = new zhktAT(2, mHandler);
				AT.execute(new String[]
				{
						xkkh, djz, xqj, djj, dmcs, userid
				});
			}

			@Override
			public void xsdm(Handler mHandler, String xh, String xkkh)
			{
				zhktAT AT = new zhktAT(1, mHandler);
				AT.execute(new String[]
				{
						xh, xkkh
				});
			}

			@Override
			public void dmxslb(Handler mHandler, String xkkh)
			{
				zhktAT AT = new zhktAT(3, mHandler);
				AT.execute(new String[]
				{
						xkkh
				});
			}

			@Override
			public void ydmxslb(Handler mHandler, String xkkh)
			{
				zhktAT AT = new zhktAT(4, mHandler);
				AT.execute(new String[]
				{
						xkkh
				});
			}

			@Override
			public void tjdm(Handler mHandler, String ktdmid, String tjlist)
			{
				zhktAT AT = new zhktAT(5, mHandler);
				AT.execute(new String[]
				{
						ktdmid, tjlist
				});
			}

			@Override
			public void dmcs(Handler mHandler, String xkkh, String djz, String xqj, String djj)
			{
				zhktAT AT = new zhktAT(6, mHandler);
				AT.execute(new String[]
				{
						xkkh, djz, xqj, djj
				});
			}

			@Override
			public void dmbj(Handler mHandler, String xkkh)
			{
				zhktAT AT = new zhktAT(7, mHandler);
				AT.execute(new String[]
				{
						xkkh
				});
			}

			@Override
			public void jsxztxsmd(Handler mHandler, String ktbh)
			{
				zhktAT AT = new zhktAT(8, mHandler);
				AT.execute(new String[]
				{
						ktbh
				});
			}

			@Override
			public void jsztnr(Handler mHandler, String ktbh, String xsbh)
			{
				zhktAT AT = new zhktAT(9, mHandler);
				AT.execute(new String[]
				{
						ktbh, xsbh
				});
			}

			@Override
			public void xsztnr(Handler mHandler, String ktbh, String xsbh)
			{
				zhktAT AT = new zhktAT(10, mHandler);
				AT.execute(new String[]
				{
						ktbh, xsbh
				});
			}

			@Override
			public void xspj(Handler mHandler, String ktbh, String pjbz1, String pjbz2, String pjbz3, String pjbz4, String xsjy, String bz)
			{
				zhktAT AT = new zhktAT(11, mHandler);
				AT.execute(new String[]
				{
						ktbh, pjbz1, pjbz2, pjbz3, pjbz4, xsjy, "1", bz
				});
			}

			@Override
			public void getxspj(Handler mHandler, String ktbh)
			{
				zhktAT AT = new zhktAT(12, mHandler);
				AT.execute(new String[]
				{
						ktbh
				});
			}

			@Override
			public void getjspj(Handler mHandler, String ktbh)
			{
				zhktAT AT = new zhktAT(13, mHandler);
				AT.execute(new String[]
				{
						ktbh
				});
			}

			@Override
			public void saveztnr(Handler mHandler, String ktbh, String jsbh, String xsbh, String ztnr)
			{
				zhktAT AT = new zhktAT(14, mHandler);
				AT.execute(new String[]
				{
						ktbh, jsbh, xsbh, ztnr
				});
			}

			@Override
			public void savektxx(Handler mHandler, String xkkh, String djz, String xqj, String djj)
			{
				zhktAT AT = new zhktAT(999, mHandler);
				AT.execute(new String[]
				{
						xkkh, djz, xqj, djj
				});
			}

			@Override
			public void getsyxsdmjg(Handler mHandler, String xkkh, String djz, String xqj, String djj, int djc)
			{
				zhktAT AT = new zhktAT(15, mHandler, djc);
				AT.execute(new String[]
				{
						xkkh, djz, xqj, djj
				});
			}

			@Override
			public void dmcs_xs(Handler mHandler, String xkkh, String djz, String xqj, String djj, String xh)
			{
				zhktAT AT = new zhktAT(16, mHandler);
				AT.execute(new String[]
				{
						xkkh, djz, xqj, djj, xh
				});
			}
		});
	}

	private zhkt_handler_interface achiveInterface(zhkt_handler_interface handler_interface)
	{
		return handler_interface;
	}
}