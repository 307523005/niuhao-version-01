package cc.mrbird.common.config;

import cc.mrbird.common.domain.ValidateCodeProperties;
import cc.mrbird.common.shiro.ShiroProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * application.yml中配置参数
 */
@Configuration
@ConfigurationProperties(prefix = "niuhaoone")//就是绑定application.properties中的属性
public class NiuhaoProperies {

    private ShiroProperties shiro = new ShiroProperties();

    private ValidateCodeProperties validateCode = new ValidateCodeProperties();
    //时间类型参数在前端页面的展示格式，默认格式为 yyyy-MM-dd HH:mm:ss
    private String timeFormat;/*= "yyyy-MM-dd HH:mm:ss"*/
    //是否开启 AOP 日志，默认开启
    private boolean openAopLog /*= true*/;
    private String ip2regionUrl;

    public ShiroProperties getShiro() {
        return shiro;
    }

    public void setShiro(ShiroProperties shiro) {
        this.shiro = shiro;
    }

    public ValidateCodeProperties getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(ValidateCodeProperties validateCode) {
        this.validateCode = validateCode;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public boolean isOpenAopLog() {
        return openAopLog;
    }

    public void setOpenAopLog(boolean openAopLog) {
        this.openAopLog = openAopLog;
    }

    public String getIp2regionUrl() {
        return ip2regionUrl;
    }

    public void setIp2regionUrl(String ip2regionUrl) {
        this.ip2regionUrl = ip2regionUrl;
    }
}
