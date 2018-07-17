package yh.app.model;

import java.io.Serializable;

import android.widget.TextView;
import yh.tool.widget.ClearEditText;

/**
 * 亲属信息录入
 * 保存动态生成的View实例
 */

public class KinsfokkViewHolder implements Serializable {
    private static final long serialVersionUID = 1L;
    public String id;
    private ClearEditText ct_kinsfolk_name;//姓名
    private ClearEditText ct_kinsfolk_nexus;//关系
    private ClearEditText ct_kinsfolk_phonenumber;//联系电话
    private ClearEditText ct_kinsfolk_age;//年龄
    private ClearEditText ct_kinsfolk_workunit;//工作单位
    private ClearEditText ct_kinsfolk_job;//职位
    private TextView txt_kinsfolk_politicalstatus;//政治面貌


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ClearEditText getCt_kinsfolk_name() {
        return ct_kinsfolk_name;
    }

    public void setCt_kinsfolk_name(ClearEditText ct_kinsfolk_name) {
        this.ct_kinsfolk_name = ct_kinsfolk_name;
    }

    public ClearEditText getCt_kinsfolk_nexus() {
        return ct_kinsfolk_nexus;
    }

    public void setCt_kinsfolk_nexus(ClearEditText ct_kinsfolk_nexus) {
        this.ct_kinsfolk_nexus = ct_kinsfolk_nexus;
    }

    public ClearEditText getCt_kinsfolk_phonenumber() {
        return ct_kinsfolk_phonenumber;
    }

    public void setCt_kinsfolk_phonenumber(ClearEditText ct_kinsfolk_phonenumber) {
        this.ct_kinsfolk_phonenumber = ct_kinsfolk_phonenumber;
    }

    public ClearEditText getCt_kinsfolk_age() {
        return ct_kinsfolk_age;
    }

    public void setCt_kinsfolk_age(ClearEditText ct_kinsfolk_age) {
        this.ct_kinsfolk_age = ct_kinsfolk_age;
    }

    public ClearEditText getCt_kinsfolk_workunit() {
        return ct_kinsfolk_workunit;
    }

    public void setCt_kinsfolk_workunit(ClearEditText ct_kinsfolk_workunit) {
        this.ct_kinsfolk_workunit = ct_kinsfolk_workunit;
    }


    public ClearEditText getCt_kinsfolk_job() {
        return ct_kinsfolk_job;
    }

    public void setCt_kinsfolk_job(ClearEditText ct_kinsfolk_job) {
        this.ct_kinsfolk_job = ct_kinsfolk_job;
    }

    public TextView getTxt_kinsfolk_politicalstatus() {
        return txt_kinsfolk_politicalstatus;
    }

    public void setTxt_kinsfolk_politicalstatus(TextView txt_kinsfolk_politicalstatus) {
        this.txt_kinsfolk_politicalstatus = txt_kinsfolk_politicalstatus;
    }
}
