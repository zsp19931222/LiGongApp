package yh.app.zhkttools;

import android.os.Handler;

public interface zhkt_handler_interface
{
	/** 发起点名 */
	void fqdm(Handler mHandler, String xkkh, String djz, String xqj, String djj, String dmcs, String userid);

	/** 学生点名 */
	void xsdm(Handler mHandler, String xh, String xkkh);

	/** 点名学生列表 */
	void dmxslb(Handler mHandler, String xkkh);

	/** 已点名学生列表 */
	void ydmxslb(Handler mHandler, String xkkh);

	/** 提交点名 */
	void tjdm(Handler mHandler, String ktdmid, String tjlist);

	/** 点名次数学生 */
	void dmcs_xs(Handler mHandler, String xkkh, String djz, String xqj, String djj, String xh);

	/** 点名次数老师 */
	void dmcs(Handler mHandler, String xkkh, String djz, String xqj, String djj);

	/** 点名班级 */
	void dmbj(Handler mHandler, String xkkh);

	/** 教师纸条学生名单 */
	void jsxztxsmd(Handler mHandler, String ktbh);

	/** 教师纸条内容 */
	void jsztnr(Handler mHandler, String ktbh, String xsbh);

	/** 学生纸条内容 */
	void xsztnr(Handler mHandler, String ktbh, String xsbh);

	/** 学生发送评价 */
	void xspj(Handler mHandler, String ktbh, String pjbz1, String pjbz2, String pjbz3, String pjbz4, String xsjy, String bz);

	/** 获取学生评价内容 */
	void getxspj(Handler mHandler, String ktbh);

	/** 老师获取评价 */
	void getjspj(Handler mHandler, String ktbh);

	/** 保存课堂信息 */
	void savektxx(Handler mHandler, String xkkh, String djz, String xqj, String djj);

	/** 发送纸条 */
	void saveztnr(Handler mHandler, String ktbh, String jsbh, String xsbh, String ztnr);

	/** 获取当前课所有学生点名结果 */
	void getsyxsdmjg(Handler mHandler, String xkkh, String djz, String xqj, String djj, int djc);
}
