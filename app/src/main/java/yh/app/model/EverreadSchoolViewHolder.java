package yh.app.model;

import java.io.Serializable;

import android.widget.TextView;
import yh.tool.widget.ClearEditText;

/**
 * 亲属信息录入
 * 保存动态生成的View实例
 */

public class EverreadSchoolViewHolder implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    private TextView text_everreadschool_starttime;//开始时间
    private TextView text_everreadschool_endtime;//结束时间
    private ClearEditText ct_everreadschool_schoolname;//学校名称
    private ClearEditText ct_everreadschool_duties;//担任职务
    private ClearEditText ct_everreadschool_witness;//证明人

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getText_everreadschool_starttime() {
        return text_everreadschool_starttime;
    }

    public void setText_everreadschool_starttime(TextView text_everreadschool_starttime) {
        this.text_everreadschool_starttime = text_everreadschool_starttime;
    }

    public TextView getText_everreadschool_endtime() {
        return text_everreadschool_endtime;
    }

    public void setText_everreadschool_endtime(TextView text_everreadschool_endtime) {
        this.text_everreadschool_endtime = text_everreadschool_endtime;
    }

    public ClearEditText getCt_everreadschool_schoolname() {
        return ct_everreadschool_schoolname;
    }

    public void setCt_everreadschool_schoolname(ClearEditText ct_everreadschool_schoolname) {
        this.ct_everreadschool_schoolname = ct_everreadschool_schoolname;
    }

    public ClearEditText getCt_everreadschool_duties() {
        return ct_everreadschool_duties;
    }

    public void setCt_everreadschool_duties(ClearEditText ct_everreadschool_duties) {
        this.ct_everreadschool_duties = ct_everreadschool_duties;
    }

    public ClearEditText getCt_everreadschool_witness() {
        return ct_everreadschool_witness;
    }

    public void setCt_everreadschool_witness(ClearEditText ct_everreadschool_witness) {
        this.ct_everreadschool_witness = ct_everreadschool_witness;
    }
}
