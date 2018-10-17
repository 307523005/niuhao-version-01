package cc.mrbird.job.task;

import cc.mrbird.common.annotation.CronTag;
import cc.mrbird.common.config.MailUtil;
import cc.mrbird.job.domain.EmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@CronTag("testTask")
public class TestTask {
    @Autowired
    private MailUtil mailUtil;
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}" , params);
    }

    public void test1() {
        log.info("我是不带参数的test1方法，正在被执行");
    }
    public void test2() {
        log.info("异常测试test2方法，正在被执行");
    }
    public void sendMail() {
        EmailMessage message = new EmailMessage();
        message.setMerchant_id("c5c05b29ee4c");
        message.setMerchant_mail("307523005@qq.com");
        message.setMessage("您好，您有一条广告等待处理");
        mailUtil.sendMessageMail(message, "测试消息通知", "email/email.ftl","307523005@qq.com");
        log.info("sendMail方法，正在被执行");
    }

}
