package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_advertising")
public class Advertising implements Serializable {
    private static final long serialVersionUID = 400066840871805701L;
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "advertising_id")
    @ExportConfig(value = "ID")
    private Long advertising_id;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户码")
    private String merchant_id;
    @Column(name = "advertising_content")
    //@ExportConfig(value = "内容")
    private String advertising_content;
    @Column(name = "advertising_addtime")
    @ExportConfig(value = "添加时间")
    private String advertising_addtime;
    @Column(name = "advertising_updatetime")
    @ExportConfig(value = "修改时间")
    private String advertising_updatetime;
    @Column(name = "advertising_updateuser")
    @ExportConfig(value = "操作用户")
    private String advertising_updateuser;
    @Column(name = "advertising_name")
    @ExportConfig(value = "广告名称")
    private String advertising_name;
    @Column(name = "advertising_remarks")
    @ExportConfig(value = "备注")
    private String advertising_remarks;
    @Column(name = "advertising_title")
    @ExportConfig(value = "标题")
    private String advertising_title;
    @Column(name = "advertising_hits")
    @ExportConfig(value = "点击量")
    private String advertising_hits;

    public Long getAdvertising_id() {
        return advertising_id;
    }

    public void setAdvertising_id(Long advertising_id) {
        this.advertising_id = advertising_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getAdvertising_content() {
        return advertising_content;
    }

    public void setAdvertising_content(String advertising_content) {
        this.advertising_content = advertising_content;
    }

    public String getAdvertising_addtime() {
        return advertising_addtime;
    }

    public void setAdvertising_addtime(String advertising_addtime) {
        this.advertising_addtime = advertising_addtime;
    }

    public String getAdvertising_updatetime() {
        return advertising_updatetime;
    }

    public void setAdvertising_updatetime(String advertising_updatetime) {
        this.advertising_updatetime = advertising_updatetime;
    }

    public String getAdvertising_updateuser() {
        return advertising_updateuser;
    }

    public void setAdvertising_updateuser(String advertising_updateuser) {
        this.advertising_updateuser = advertising_updateuser;
    }

    public String getAdvertising_name() {
        return advertising_name;
    }

    public void setAdvertising_name(String advertising_name) {
        this.advertising_name = advertising_name;
    }

    public String getAdvertising_remarks() {
        return advertising_remarks;
    }

    public void setAdvertising_remarks(String advertising_remarks) {
        this.advertising_remarks = advertising_remarks;
    }

    public String getAdvertising_title() {
        return advertising_title;
    }

    public void setAdvertising_title(String advertising_title) {
        this.advertising_title = advertising_title;
    }

    public String getAdvertising_hits() {
        return advertising_hits;
    }

    public void setAdvertising_hits(String advertising_hits) {
        this.advertising_hits = advertising_hits;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("advertising_id", advertising_id)
                .add("advertising_name", advertising_name)
                .add("merchant_id", merchant_id)
                .add("advertising_content", advertising_content)
                .add("advertising_addtime", advertising_addtime)
                .add("advertising_updatetime", advertising_updatetime)
                .add("advertising_updateuser", advertising_updateuser)
                .add("advertising_remarks", advertising_remarks)
                .add("advertising_title", advertising_title)
                .add("advertising_hits", advertising_hits)
                .toString();
    }
}
