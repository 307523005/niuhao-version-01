package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "qrtz_cron")
public class Cron implements Serializable {

    private static final long serialVersionUID = 400066840871805707L;


    @Id
    @Column(name = "cron_id")
    @ExportConfig(value = "cron表达式ID")
    private String cron_id;

    @Column(name = "cron_num")
    @ExportConfig(value = "优先级")
    private String cron_num;
    @Column(name = "cron_content")
    @ExportConfig(value = "表达式内容")
    private String cron_content;

    @Column(name = "cron_remarks")
    @ExportConfig(value = "备注")
    private String cron_remarks;

    public String getCron_id() {
        return cron_id;
    }

    public void setCron_id(String cron_id) {
        this.cron_id = cron_id;
    }

    public String getCron_num() {
        return cron_num;
    }

    public void setCron_num(String cron_num) {
        this.cron_num = cron_num;
    }

    public String getCron_content() {
        return cron_content;
    }

    public void setCron_content(String cron_content) {
        this.cron_content = cron_content;
    }

    public String getCron_remarks() {
        return cron_remarks;
    }

    public void setCron_remarks(String cron_remarks) {
        this.cron_remarks = cron_remarks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("cron_id", cron_id)
                .add("cron_num", cron_num)
                .add("cron_content", cron_content)
                .add("cron_remarks", cron_remarks)
                .toString();
    }
}