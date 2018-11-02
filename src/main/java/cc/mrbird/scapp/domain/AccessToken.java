package cc.mrbird.scapp.domain;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
@Table(name = "tb_accesstoken")
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 400066840871805708L;
    @Id
    @Column(name = "AccessToken_id")
    private int AccessToken_id;

    // 获取到的凭证
    @Column(name = "GZHaccess_token")
    private String GZHaccess_token;
    // 获取到的凭证
    @Column(name = "XCXaccess_token")
    private String XCXaccess_token;

    // 凭证有效时间，单位：秒
    @Transient//表中不存在字段
    private int GZHexpires_in;
    // 凭证有效时间，单位：秒
    @Transient
    private int XCXexpires_in;
    // 获取到的凭证
    @Transient
    private String access_token;
    // 凭证有效时间，单位：秒
    @Transient
    private int expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getGZHaccess_token() {
        return GZHaccess_token;
    }

    public void setGZHaccess_token(String gZHaccess_token) {
        GZHaccess_token = gZHaccess_token;
    }

    public int getGZHexpires_in() {
        return GZHexpires_in;
    }

    public void setGZHexpires_in(int gZHexpires_in) {
        GZHexpires_in = gZHexpires_in;
    }

    public String getXCXaccess_token() {
        return XCXaccess_token;
    }

    public void setXCXaccess_token(String xCXaccess_token) {
        XCXaccess_token = xCXaccess_token;
    }

    public int getXCXexpires_in() {
        return XCXexpires_in;
    }

    public void setXCXexpires_in(int xCXexpires_in) {
        XCXexpires_in = xCXexpires_in;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("AccessToken_id", AccessToken_id)
                .add("GZHaccess_token", GZHaccess_token)
                .add("XCXaccess_token", XCXaccess_token)
                .add("GZHexpires_in", GZHexpires_in)
                .add("XCXexpires_in", XCXexpires_in)
                .add("access_token", access_token)
                .add("expires_in", expires_in)
                .toString();
    }
}
