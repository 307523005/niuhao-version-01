package cc.mrbird.system.domain;

import cc.mrbird.common.annotation.ExportConfig;
import com.google.common.base.MoreObjects;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "t_merchant")
public class Merchant implements Serializable {

	private static final long serialVersionUID = 400066840871805702L;

	@Id
	@Column(name = "merchant_id")//商户码唯一
	private String merchant_id;

	@Column(name = "merchant_name")
	@ExportConfig(value = "商户名称")
	private String merchant_name;

	@Column(name = "merchant_phone")
	private String merchant_phone;

	@Column(name = "merchant_mail")
	private String merchant_mail;

	@Column(name = "merchant_corp")//法人
	private String merchant_corp;

	@Column(name = "merchant_addr")
	private String merchant_addr;

	@Column(name = "merchant_information")
	private String merchant_information;//工商信息（号）

	@Column(name = "merchant_addtime")
	@ExportConfig(value = "创建时间")
	private String merchant_addtime;
	@Column(name = "merchant_region")
	@ExportConfig(value = "地区")
	private String merchant_region;


	public String getMerchant_id() {
		return merchant_id;
	}

	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public String getMerchant_phone() {
		return merchant_phone;
	}

	public void setMerchant_phone(String merchant_phone) {
		this.merchant_phone = merchant_phone;
	}

	public String getMerchant_mail() {
		return merchant_mail;
	}

	public void setMerchant_mail(String merchant_mail) {
		this.merchant_mail = merchant_mail;
	}

	public String getMerchant_corp() {
		return merchant_corp;
	}

	public void setMerchant_corp(String merchant_corp) {
		this.merchant_corp = merchant_corp;
	}

	public String getMerchant_addr() {
		return merchant_addr;
	}

	public void setMerchant_addr(String merchant_addr) {
		this.merchant_addr = merchant_addr;
	}

	public String getMerchant_information() {
		return merchant_information;
	}

	public void setMerchant_information(String merchant_information) {
		this.merchant_information = merchant_information;
	}

	public String getMerchant_addtime() {
		return merchant_addtime;
	}

	public void setMerchant_addtime(String merchant_addtime) {
		this.merchant_addtime = merchant_addtime;
	}

	public String getMerchant_region() {
		return merchant_region;
	}

	public void setMerchant_region(String merchant_region) {
		this.merchant_region = merchant_region;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("merchant_id", merchant_id)
				.add("merchant_name", merchant_name)
				.add("merchant_phone", merchant_phone)
				.add("merchant_mail", merchant_mail)
				.add("merchant_corp", merchant_corp)
				.add("merchant_addr", merchant_addr)
				.add("merchant_information", merchant_information)
				.add("merchant_addtime", merchant_addtime)
				.add("merchant_region", merchant_region)
				.toString();
	}

}