package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "qrtz_method")
public class Method implements Serializable {

    private static final long serialVersionUID = 400066840871805704L;


    @Id
    @Column(name = "method_id")
    @ExportConfig(value = "定时器方法ID")
    private String method_id;

    @Column(name = "method_name")
    @ExportConfig(value = "方法名称")
    private String method_name;

    @Column(name = "method_remarks")
    @ExportConfig(value = "备注")
    private String method_remarks;

    public String getMethod_id() {
        return method_id;
    }

    public void setMethod_id(String method_id) {
        this.method_id = method_id;
    }

    public String getMethod_name() {
        return method_name;
    }

    public void setMethod_name(String method_name) {
        this.method_name = method_name;
    }

    public String getMethod_remarks() {
        return method_remarks;
    }

    public void setMethod_remarks(String method_remarks) {
        this.method_remarks = method_remarks;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("method_id", method_id)
                .add("method_name", method_name)
                .add("method_remarks", method_remarks)
                .toString();
    }
}