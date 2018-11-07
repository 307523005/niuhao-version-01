package cc.mrbird.job.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "t_advertisingtype")
public class AdvertisingType implements Serializable {
    private static final long serialVersionUID = 400066840871805710L;
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "advertisingtype_id")
    @ExportConfig(value = "广告ID")
    private Long advertisingtype_id;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户码")
    private String merchant_id;
    @Column(name = "advertisingtype_addtime")
    @ExportConfig(value = "添加时间")
    private String advertisingtype_addtime;
    @Column(name = "advertisingtype_updatetime")
    @ExportConfig(value = "修改时间")
    private String advertisingtype_updatetime;
    @Column(name = "advertisingtype_updateuser")
    @ExportConfig(value = "操作用户")
    private String advertisingtype_updateuser;
    @Column(name = "advertisingtype_name")
    @ExportConfig(value = "广告类型名称")
    private String advertisingtype_name;
    @Column(name = "advertisingtype_remarks")
    @ExportConfig(value = "备注")
    private String advertisingtype_remarks;

    @Column(name = "advertisingtype_num")
    @ExportConfig(value = "优先级")
    private String advertisingtype_num;
    @Column(name = "advertisingtype_imageurl")
    @ExportConfig(value = "图片路径")
    private String advertisingtype_imageurl;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("advertisingtype_id", advertisingtype_id)
                .add("advertisingtype_name", advertisingtype_name)
                .add("merchant_id", merchant_id)
                .add("advertisingtype_addtime", advertisingtype_addtime)
                .add("advertisingtype_updatetime", advertisingtype_updatetime)
                .add("advertisingtype_updateuser", advertisingtype_updateuser)
                .add("advertisingtype_remarks", advertisingtype_remarks)
                .add("advertisingtype_num", advertisingtype_num)
                .add("advertisingtype_imageurl", advertisingtype_imageurl)
                .toString();
    }

    public Long getAdvertisingtype_id() {
        return advertisingtype_id;
    }

    public void setAdvertisingtype_id(Long advertisingtype_id) {
        this.advertisingtype_id = advertisingtype_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getAdvertisingtype_addtime() {
        return advertisingtype_addtime;
    }

    public void setAdvertisingtype_addtime(String advertisingtype_addtime) {
        this.advertisingtype_addtime = advertisingtype_addtime;
    }

    public String getAdvertisingtype_updatetime() {
        return advertisingtype_updatetime;
    }

    public void setAdvertisingtype_updatetime(String advertisingtype_updatetime) {
        this.advertisingtype_updatetime = advertisingtype_updatetime;
    }

    public String getAdvertisingtype_updateuser() {
        return advertisingtype_updateuser;
    }

    public void setAdvertisingtype_updateuser(String advertisingtype_updateuser) {
        this.advertisingtype_updateuser = advertisingtype_updateuser;
    }

    public String getAdvertisingtype_name() {
        return advertisingtype_name;
    }

    public void setAdvertisingtype_name(String advertisingtype_name) {
        this.advertisingtype_name = advertisingtype_name;
    }

    public String getAdvertisingtype_remarks() {
        return advertisingtype_remarks;
    }

    public void setAdvertisingtype_remarks(String advertisingtype_remarks) {
        this.advertisingtype_remarks = advertisingtype_remarks;
    }

    public String getAdvertisingtype_num() {
        return advertisingtype_num;
    }

    public void setAdvertisingtype_num(String advertisingtype_num) {
        this.advertisingtype_num = advertisingtype_num;
    }

    public String getAdvertisingtype_imageurl() {
        return advertisingtype_imageurl;
    }

    public void setAdvertisingtype_imageurl(String advertisingtype_imageurl) {
        this.advertisingtype_imageurl = advertisingtype_imageurl;
    }
}
