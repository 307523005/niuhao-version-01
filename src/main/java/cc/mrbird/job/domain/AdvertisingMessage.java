package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_advertisingmessage")
public class AdvertisingMessage implements Serializable {
    private static final long serialVersionUID = 400066840871805711L;
    @Id
    @Column(name = "advmessage_id")
    @ExportConfig(value = "评论ID")
    private String advmessageId;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户码")
    private String merchantId;

    @Column(name = "advertising_id")
    private String advertisingId;

    @Column(name = "wxuser_id")
    private String wxuserId;

    @Column(name = "advmessage_content")
    @ExportConfig(value = "评论内容")
    private String advmessageContent;

    @Column(name = "advmessage_addtime")
    @ExportConfig(value = "时间")
    private String advmessageAddtime;

    @Column(name = "advmessage_authorcontent")
    @ExportConfig(value = "回复内容")
    private String advmessageAuthorcontent;

    @Column(name = "advmessage_updateuser")
    @ExportConfig(value = "操作用户")
    private String advmessageUpdateuser;

    @Column(name = "advmessage_show")
    private int advmessageShow;
    @Override
    public String toString() {
        return "AdvertisingMessage{" +
                "advmessageId=" + advmessageId +
                ", merchantId='" + merchantId + '\'' +
                ", advertisingId='" + advertisingId + '\'' +
                ", wxuserId='" + wxuserId + '\'' +
                ", advmessageContent=" + advmessageContent +
                ", advmessageAddtime='" + advmessageAddtime + '\'' +
                ", advmessageAuthorcontent='" + advmessageAuthorcontent + '\'' +
                ", advmessageUpdateuser='" + advmessageUpdateuser + '\'' +
                ", advmessageShow='" + advmessageShow + '\'' +
                '}';
    }
    public String getAdvmessageId() {
        return advmessageId;
    }

    public void setAdvmessageId(String advmessageId) {
        this.advmessageId = advmessageId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAdvertisingId() {
        return advertisingId;
    }

    public void setAdvertisingId(String advertisingId) {
        this.advertisingId = advertisingId;
    }

    public String getWxuserId() {
        return wxuserId;
    }

    public void setWxuserId(String wxuserId) {
        this.wxuserId = wxuserId;
    }

    public String getAdvmessageContent() {
        return advmessageContent;
    }

    public void setAdvmessageContent(String advmessageContent) {
        this.advmessageContent = advmessageContent;
    }

    public String getAdvmessageAddtime() {
        return advmessageAddtime;
    }

    public void setAdvmessageAddtime(String advmessageAddtime) {
        this.advmessageAddtime = advmessageAddtime;
    }

    public String getAdvmessageAuthorcontent() {
        return advmessageAuthorcontent;
    }

    public void setAdvmessageAuthorcontent(String advmessageAuthorcontent) {
        this.advmessageAuthorcontent = advmessageAuthorcontent;
    }

    public String getAdvmessageUpdateuser() {
        return advmessageUpdateuser;
    }

    public void setAdvmessageUpdateuser(String advmessageUpdateuser) {
        this.advmessageUpdateuser = advmessageUpdateuser;
    }

    public int getAdvmessageShow() {
        return advmessageShow;
    }

    public void setAdvmessageShow(int advmessageShow) {
        this.advmessageShow = advmessageShow;
    }
}
