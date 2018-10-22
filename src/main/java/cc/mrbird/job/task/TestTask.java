package cc.mrbird.job.task;

import cc.mrbird.common.annotation.CronTag;
import cc.mrbird.common.config.MailUtil;
import cc.mrbird.job.domain.EmailMessage;
import cc.mrbird.job.domain.Job;
import cc.mrbird.job.service.JobService;
import cc.mrbird.system.domain.Merchant;
import cc.mrbird.system.service.MerchantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@CronTag("testTask")
public class TestTask {
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private MerchantService merchantService;
    @Autowired
    private JobService jobService;


    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void test(String params) {
        log.info("我是带参数的test方法，正在被执行，参数为：{}", params);
    }

    public void test1(String params) {
        log.info("我是带参数的test1方法，正在被执行");
    }

    public void test2(String params) {
        log.info("异常测试test2方法，正在被执行");
    }

    /**
     * 提醒邮件
     * @param params
     */
    public void sendMail(String params) {
        Job jobByParams = jobService.findJobByParams(params);
        Merchant merchant = merchantService.findMerchant(jobByParams.getMerchant_id());
        EmailMessage message = new EmailMessage();
        message.setMerchant_id(jobByParams.getMerchant_id());
        message.setMerchant_mail(merchant.getMerchant_mail());
        message.setMessage("您好，您有一条消息：" + jobByParams.getJob_name() + " 等待处理");
        mailUtil.sendMessageMail(message, "测试消息通知", "email/email.ftl", merchant.getMerchant_mail());
        log.info("sendMail方法，正在被执行");
    }

}
