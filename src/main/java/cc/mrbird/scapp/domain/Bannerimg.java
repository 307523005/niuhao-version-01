package cc.mrbird.scapp.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sc_bannerimg")
public class Bannerimg implements Serializable {
    private static final long serialVersionUID = 400066840871805706L;
    @Id
    @Column(name = "bannerimg_id")
    @ExportConfig(value = "轮播图ID")
    private String bannerimg_id;
    @Column(name = "bannerimg_num")
    @ExportConfig(value = "优先级")
    private String bannerimg_num;
    @Column(name = "bannerimg_name")
    @ExportConfig(value = "轮播图名称")
    private String bannerimg_name;
    @Column(name = "bannerimg_imgurl")
    private String bannerimg_imgurl;
    @Column(name = "bannerimg_htmlurl")
    private String bannerimg_htmlurl;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户ID")
    private String merchant_id;
    @Column(name = "bannerimg_addtime")
    @ExportConfig(value = "添加时间")
    private String bannerimg_addtime;
    @Column(name = "bannerimg_updatetime")
    @ExportConfig(value = "修改时间")
    private String bannerimg_updatetime;
    @Column(name = "bannerimg_updateuser")
    @ExportConfig(value = "修改用户")
    private String bannerimg_updateuser;
    @Column(name = "bannerimg_remarks")
    @ExportConfig(value = "备注")
    private String bannerimg_remarks;

    public String getBannerimg_id() {
        return bannerimg_id;
    }

    public void setBannerimg_id(String bannerimg_id) {
        this.bannerimg_id = bannerimg_id;
    }

    public String getBannerimg_num() {
        return bannerimg_num;
    }

    public void setBannerimg_num(String bannerimg_num) {
        this.bannerimg_num = bannerimg_num;
    }

    public String getBannerimg_imgurl() {
        return bannerimg_imgurl;
    }

    public void setBannerimg_imgurl(String bannerimg_imgurl) {
        this.bannerimg_imgurl = bannerimg_imgurl;
    }

    public String getBannerimg_htmlurl() {
        return bannerimg_htmlurl;
    }

    public void setBannerimg_htmlurl(String bannerimg_htmlurl) {
        this.bannerimg_htmlurl = bannerimg_htmlurl;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getBannerimg_addtime() {
        return bannerimg_addtime;
    }

    public void setBannerimg_addtime(String bannerimg_addtime) {
        this.bannerimg_addtime = bannerimg_addtime;
    }

    public String getBannerimg_updatetime() {
        return bannerimg_updatetime;
    }

    public void setBannerimg_updatetime(String bannerimg_updatetime) {
        this.bannerimg_updatetime = bannerimg_updatetime;
    }

    public String getBannerimg_updateuser() {
        return bannerimg_updateuser;
    }

    public void setBannerimg_updateuser(String bannerimg_updateuser) {
        this.bannerimg_updateuser = bannerimg_updateuser;
    }

    public String getBannerimg_remarks() {
        return bannerimg_remarks;
    }

    public void setBannerimg_remarks(String bannerimg_remarks) {
        this.bannerimg_remarks = bannerimg_remarks;
    }

    public String getBannerimg_name() {
        return bannerimg_name;
    }

    public void setBannerimg_name(String bannerimg_name) {
        this.bannerimg_name = bannerimg_name;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bannerimg_id", bannerimg_id)
                .add("bannerimg_num", bannerimg_num)
                .add("bannerimg_imgurl", bannerimg_imgurl)
                .add("bannerimg_htmlurl", bannerimg_htmlurl)
                .add("merchant_id", merchant_id)
                .add("bannerimg_addtime", bannerimg_addtime)
                .add("bannerimg_updatetime", bannerimg_updatetime)
                .add("bannerimg_updateuser", bannerimg_updateuser)
                .add("bannerimg_remarks", bannerimg_remarks)
                .add("bannerimg_name", bannerimg_name)
                .toString();
    }
}
