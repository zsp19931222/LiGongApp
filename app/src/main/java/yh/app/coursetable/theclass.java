package yh.app.coursetable;
/**
 * 
 * 包	名:yh.app.coursetable
 * 类	名:theclass.java
 * 功	能:存放上课教室的信息
 *
 * @author 	云华科技
 * @version	1.0
 * @date	2015-7-29
 */
public class theclass {
	public String 	JSXM;
	public String 	JSMC;
	public String 	COURSE;
	public String 	DSZ;
	public String 	XKKH;
	public String 	XH;
	public String 	XN;
	public String 	XQ;
	public int 		color;
	public int 		QSZ;
	public int 		JSZ;
	public int 		XQJ;
	public int 		SJD;
	public int 		JSSJD;
	public theclass(String XH,String XN,String XQ,String JSXM,String JSMC,String COURSE,int XQJ,int SJD,int JSSJD,String DSZ,int QSZ,int JSZ,String XKKH,int color)
	{
		this.XH=XH;
		this.XN=XN;
		this.XQ=XQ;
		this.JSXM=JSXM;
		this.JSMC=JSMC;
		this.COURSE=COURSE;
		this.XQJ=XQJ;
		this.SJD=SJD;
		this.JSSJD=JSSJD;
		this.DSZ=DSZ;
		this.QSZ=QSZ;
		this.JSZ=JSZ;
		this.XKKH=XKKH;
		this.color=color;
	}
}
