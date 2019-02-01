package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import cc.mrbird.common.domain.QueryRequest;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_advertising")
public class Advertising extends QueryRequest implements Serializable{
    private static final long serialVersionUID = -400066840871805701L;
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "advertising_id")
    @ExportConfig(value = "商户ID")
    private Long advertisingId;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户码")
    private String merchantId;
    @Column(name = "advertising_content")
    //@ExportConfig(value = "内容")
    private String advertisingContent;
    @Column(name = "advertising_addtime")
    @ExportConfig(value = "添加时间")
    private String advertisingAddtime;
    @Column(name = "advertising_updatetime")
    @ExportConfig(value = "修改时间")
    private String advertisingUpdatetime;
    @Column(name = "advertising_updateuser")
    @ExportConfig(value = "操作用户")
    private String advertisingUpdateuser;
    @Column(name = "advertising_name")
    @ExportConfig(value = "广告名称")
    private String advertisingName;
    @Column(name = "advertising_remarks")
    @ExportConfig(value = "备注")
    private String advertisingRemarks;
    @Column(name = "advertising_title")
    @ExportConfig(value = "标题")
    private String advertisingTitle;
    @Column(name = "advertising_hits")
    @ExportConfig(value = "点击量")
    private String advertisingHits;

    @Column(name = "advertisingtype_id")
    @ExportConfig(value = "广告类型")
    private String advertisingTypeId;

    @Transient
    @ExportConfig(value = "广告类型名称")
    private String advertisingTypeName;


    @Override
    public String toString() {
        return "Advertising{" +
                "advertisingId=" + advertisingId +
                ", advertisingName='" + advertisingName + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", advertisingContent='" + advertisingContent + '\'' +
                ", advertisingAddtime=" + advertisingAddtime +
                ", advertisingUpdatetime='" + advertisingUpdatetime + '\'' +
                ", advertisingUpdateuser='" + advertisingUpdateuser + '\'' +
                ", advertisingRemarks='" + advertisingRemarks + '\'' +
                ", advertisingTitle='" + advertisingTitle + '\'' +
                ", advertisingHits=" + advertisingHits +
                ", advertisingTypeId=" + advertisingTypeId +
                ", advertisingTypeName=" + advertisingTypeName +
                '}';
    }

    public Long getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(Long advertisingId) {
        this.advertisingId = advertisingId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }


    public String getAdvertisingContent() {
        return advertisingContent;
    }

    public void setAdvertisingContent(String advertisingContent) {
        this.advertisingContent = advertisingContent;
    }

    public String getAdvertisingAddtime() {
        return advertisingAddtime;
    }

    public void setAdvertisingAddtime(String advertisingAddtime) {
        this.advertisingAddtime = advertisingAddtime;
    }

    public String getAdvertisingUpdatetime() {
        return advertisingUpdatetime;
    }

    public void setAdvertisingUpdatetime(String advertisingUpdatetime) {
        this.advertisingUpdatetime = advertisingUpdatetime;
    }

    public String getAdvertisingUpdateuser() {
        return advertisingUpdateuser;
    }

    public void setAdvertisingUpdateuser(String advertisingUpdateuser) {
        this.advertisingUpdateuser = advertisingUpdateuser;
    }

    public String getAdvertisingName() {
        return advertisingName;
    }

    public void setAdvertisingName(String advertisingName) {
        this.advertisingName = advertisingName;
    }

    public String getAdvertisingRemarks() {
        return advertisingRemarks;
    }

    public void setAdvertisingRemarks(String advertisingRemarks) {
        this.advertisingRemarks = advertisingRemarks;
    }

    public String getAdvertisingTitle() {
        return advertisingTitle;
    }

    public void setAdvertisingTitle(String advertisingTitle) {
        this.advertisingTitle = advertisingTitle;
    }

    public String getAdvertisingHits() {
        return advertisingHits;
    }

    public void setAdvertisingHits(String advertisingHits) {
        this.advertisingHits = advertisingHits;
    }

    public String getAdvertisingTypeId() {
        return advertisingTypeId;
    }

    public void setAdvertisingTypeId(String advertisingTypeId) {
        this.advertisingTypeId = advertisingTypeId;
    }

    public String getAdvertisingTypeName() {
        return advertisingTypeName;
    }

    public void setAdvertisingTypeName(String advertisingTypeName) {
        this.advertisingTypeName = advertisingTypeName;
    }
}
