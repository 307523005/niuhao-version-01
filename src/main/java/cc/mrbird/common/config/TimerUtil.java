package cc.mrbird.common.config;

import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 功能描述: 定时任务工具类
 *
 * @auther: 钮豪
 * @date: 2018/11/1 14:01
 */
@Component
@EnableScheduling//快速开启定时任务调度功能
public class TimerUtil {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AdvertisingService advertisingService;

    /**
     * 每天凌晨3点同步点击量到数据库
     */
    @Scheduled(cron = "0 0 3 * * ?")//每天凌晨3点触发
    //@Scheduled(fixedDelay = 3000)//每3s执行1次
    public void backUpData() throws Exception{

        List<Advertising> allAdvertising = advertisingService.findAllAdvertising();
        for (Advertising advertising : allAdvertising) {
            Long advertising_id = advertising.getAdvertisingId();
            Object intraday = redisUtils.hget("advertising_intraday", advertising_id.toString());
            if (intraday != null) {
                Advertising advertisings = new Advertising();
                advertisings.setAdvertisingHits(intraday.toString());
                advertisings.setAdvertisingId(advertising_id);
                advertisingService.updateAdvertisingRedis(advertisings);
                log.info("同步点击量:" + advertising_id.toString());
            }
        }
        redisUtils.remove("advertising_intraday");
    }

}