package cc.mrbird.scapp.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "sc_goods")
public class Goods implements Serializable {
    private static final long serialVersionUID = 400066840871805705L;
    @Id
    @Column(name = "goods_id")
    @ExportConfig(value = "ID")
    private String goods_id;
    @ExportConfig(value = "商品名称")
    @Column(name = "goods_name")
    private String goods_name;
    @ExportConfig(value = "优先级")
    @Column(name = "goods_num")
    private String goods_num;
    @ExportConfig(value = "添加时间")
    @Column(name = "goods_addtime")
    private String goods_addtime;
    @ExportConfig(value = "修改时间")
    @Column(name = "goods_updatetime")
    private String goods_updatetime;
    @Column(name = "merchant_id")
    @ExportConfig(value = "商户")
    private String merchant_id;
    @Column(name = "goods_remarks")
    @ExportConfig(value = "备注")
    private String goods_remarks;
    @ExportConfig(value = "修改用户")
    @Column(name = "goods_updateuser")
    private String goods_updateuser;
    @ExportConfig(value = "活动")
    @Column(name = "goods_activity")
    private String goods_activity;
    @Column(name = "goods_bigimge")
    private String goods_bigimge;
    @Column(name = "goods_littleimge")
    private String goods_littleimge;
    @ExportConfig(value = "内容")
    @Column(name = "goods_intro")
    private String goods_intro;
    @ExportConfig(value = "单位")
    @Column(name = "goods_units")
    private String goods_units;
    @Column(name = "goods_price")
    @ExportConfig(value = "价格")
    private String goods_price;
    @ExportConfig(value = "商品类型ID")
    @Column(name = "goodstype_id")
    private String goodstype_id;

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getGoods_addtime() {
        return goods_addtime;
    }

    public void setGoods_addtime(String goods_addtime) {
        this.goods_addtime = goods_addtime;
    }

    public String getGoods_updatetime() {
        return goods_updatetime;
    }

    public void setGoods_updatetime(String goods_updatetime) {
        this.goods_updatetime = goods_updatetime;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getGoods_remarks() {
        return goods_remarks;
    }

    public void setGoods_remarks(String goods_remarks) {
        this.goods_remarks = goods_remarks;
    }

    public String getGoods_updateuser() {
        return goods_updateuser;
    }

    public void setGoods_updateuser(String goods_updateuser) {
        this.goods_updateuser = goods_updateuser;
    }

    public String getGoods_activity() {
        return goods_activity;
    }

    public void setGoods_activity(String goods_activity) {
        this.goods_activity = goods_activity;
    }

    public String getGoods_bigimge() {
        return goods_bigimge;
    }

    public void setGoods_bigimge(String goods_bigimge) {
        this.goods_bigimge = goods_bigimge;
    }

    public String getGoods_littleimge() {
        return goods_littleimge;
    }

    public void setGoods_littleimge(String goods_littleimge) {
        this.goods_littleimge = goods_littleimge;
    }

    public String getGoods_intro() {
        return goods_intro;
    }

    public void setGoods_intro(String goods_intro) {
        this.goods_intro = goods_intro;
    }

    public String getGoods_units() {
        return goods_units;
    }

    public void setGoods_units(String goods_units) {
        this.goods_units = goods_units;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoodstype_id() {
        return goodstype_id;
    }

    public void setGoodstype_id(String goodstype_id) {
        this.goodstype_id = goodstype_id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("goods_id", goods_id)
                .add("merchant_id", merchant_id)
                .add("goods_num", goods_num)
                .add("goods_name", goods_name)
                .add("goods_remarks", goods_remarks)
                .add("goods_addtime", goods_addtime)
                .add("goods_updatetime", goods_updatetime)
                .add("goods_updateuser", goods_updateuser)
                .add("goods_activity", goods_activity)
                .add("goods_bigimge", goods_bigimge)
                .add("goods_littleimge", goods_littleimge)
                .add("goods_intro", goods_intro)
                .add("goods_units", goods_units)
                .add("goods_price", goods_price)
                .add("goodstype_id", goodstype_id)
                .toString();
    }
}
