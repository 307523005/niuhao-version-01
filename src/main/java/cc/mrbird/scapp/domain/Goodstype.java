package cc.mrbird.scapp.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sc_goodstype")
public class Goodstype implements Serializable {
    private static final long serialVersionUID = 400066840871805703L;
    @Id
    @Column(name = "goodstype_id")
    @ExportConfig(value = "ID")
    private String goodstype_id;
    @Column(name = "goodstype_name")
    @ExportConfig(value = "")
    private String goodstype_name;
    @Column(name = "goodstype_num")
    @ExportConfig(value = "")
    private String goodstype_num;
    @Column(name = "goodstype_addtime")
    @ExportConfig(value = "")
    private String goodstype_addtime;
    @Column(name = "goodstype_updatetime")
    @ExportConfig(value = "")
    private String goodstype_updatetime;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户")
    private String merchant_id;
    @Column(name = "goodstype_remarks")
    @ExportConfig(value = "")
    private String goodstype_remarks;
    @Column(name = "goodstype_imageurl")
    @ExportConfig(value = "")
    private String goodstype_imageurl;
    @Column(name = "goodstype_htmlurl")
    @ExportConfig(value = "")
    private String goodstype_htmlurl;
    @Column(name = "goodstype_updateuser")
    @ExportConfig(value = "")
    private String goodstype_updateuser;

    public String getGoodstype_id() {
        return goodstype_id;
    }

    public void setGoodstype_id(String goodstype_id) {
        this.goodstype_id = goodstype_id;
    }

    public String getGoodstype_name() {
        return goodstype_name;
    }

    public void setGoodstype_name(String goodstype_name) {
        this.goodstype_name = goodstype_name;
    }

    public String getGoodstype_num() {
        return goodstype_num;
    }

    public void setGoodstype_num(String goodstype_num) {
        this.goodstype_num = goodstype_num;
    }

    public String getGoodstype_addtime() {
        return goodstype_addtime;
    }

    public void setGoodstype_addtime(String goodstype_addtime) {
        this.goodstype_addtime = goodstype_addtime;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getGoodstype_remarks() {
        return goodstype_remarks;
    }

    public void setGoodstype_remarks(String goodstype_remarks) {
        this.goodstype_remarks = goodstype_remarks;
    }

    public String getGoodstype_imageurl() {
        return goodstype_imageurl;
    }

    public void setGoodstype_imageurl(String goodstype_imageurl) {
        this.goodstype_imageurl = goodstype_imageurl;
    }

    public String getGoodstype_updatetime() {
        return goodstype_updatetime;
    }

    public void setGoodstype_updatetime(String goodstype_updatetime) {
        this.goodstype_updatetime = goodstype_updatetime;
    }

    public String getGoodstype_htmlurl() {
        return goodstype_htmlurl;
    }

    public void setGoodstype_htmlurl(String goodstype_htmlurl) {
        this.goodstype_htmlurl = goodstype_htmlurl;
    }

    public String getGoodstype_updateuser() {
        return goodstype_updateuser;
    }

    public void setGoodstype_updateuser(String goodstype_updateuser) {
        this.goodstype_updateuser = goodstype_updateuser;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("goodstype_id", goodstype_id)
                .add("merchant_id", merchant_id)
                .add("goodstype_num", goodstype_num)
                .add("goodstype_name", goodstype_name)
                .add("goodstype_imageurl", goodstype_imageurl)
                .add("goodstype_htmlurl", goodstype_htmlurl)
                .add("goodstype_remarks", goodstype_remarks)
                .add("goodstype_addtime", goodstype_addtime)
                .add("goodstype_updatetime", goodstype_updatetime)
                .add("goodstype_updateuser", goodstype_updateuser)
                .toString();
    }
}
