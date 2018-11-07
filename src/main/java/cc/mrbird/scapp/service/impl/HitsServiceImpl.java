package cc.mrbird.scapp.service.impl;

import cc.mrbird.common.config.RedisUtils;
import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import cc.mrbird.scapp.service.HitsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 公众号登陆获取openid
 *
 * @author Administrator
 */
@Service
public class HitsServiceImpl implements HitsService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AdvertisingService advertisingService;

    @Override
    public Future<Map<String, Object>> addHitsAdvertising(String advertising_id, String cip) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        String key = advertising_id + "_" + cip;
        boolean b = redisUtils.hHasKey("advertising_intraday", key);
        if (b) {
            Object intraday = redisUtils.hget("advertising_intraday", advertising_id);
            log.info(intraday.toString());
            map.put("advertising_intraday", intraday);
            return new AsyncResult<>(map);
        }
        redisUtils.hset("advertising_intraday", key, key);
        Object intraday = redisUtils.hget("advertising_intraday", advertising_id);
        double hincr = 0;
        int i = 0;
        if (intraday != null) {
            hincr = redisUtils.hincr("advertising_intraday", advertising_id, 1);
            i = new Double(hincr).intValue();
            map.put("advertising_intraday", i);
            log.info("addHitsAdvertising1");
        } else {
            Advertising advertising = advertisingService.findAdvertising(Long.valueOf(advertising_id));
            advertising.getAdvertisingHits();
            redisUtils.hSet("advertising_intraday", advertising_id, Integer.valueOf(advertising.getAdvertisingHits()));
            hincr = redisUtils.hincr("advertising_intraday", advertising_id, 1);
            i = new Double(hincr).intValue();
            map.put("advertising_intraday", i);
            log.info("addHitsAdvertising2");
        }

        return new AsyncResult<>(map);
    }

}
