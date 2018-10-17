package cc.mrbird.job.domain;

import java.io.Serializable;

public class EmailMessage implements Serializable {
    private String merchant_id;
    private String merchant_mail;
    private String message;

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getMerchant_mail() {
        return merchant_mail;
    }

    public void setMerchant_mail(String merchant_mail) {
        this.merchant_mail = merchant_mail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
