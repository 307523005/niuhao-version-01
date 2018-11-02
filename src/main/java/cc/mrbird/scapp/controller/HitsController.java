package cc.mrbird.scapp.controller;

import cc.mrbird.common.config.RedisUtils;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import cc.mrbird.scapp.service.HitsService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import static sun.security.krb5.Confounder.intValue;

/**
 * 点击量
 */
@Controller
public class HitsController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private HitsService hitsService;

    @RequestMapping("scapp/addHitsAdvertising")
    @ResponseBody
    public Map<String, Object> addHitsAdvertising(String advertising_id, String cip) throws Exception {

        Future<Map<String, Object>> mapFuture = hitsService.addHitsAdvertising(advertising_id, cip);

        return mapFuture.get();
    }

/*    @RequestMapping("scapp/getHitsAdvertising")
    @ResponseBody
    public Map<String, Object> getHitsAdvertising(String advertising_id) {
        Object intraday = redisUtils.hget("advertising_intraday", advertising_id);
        Map<String, Object> map = null;
        map.put("advertising_intraday",intraday);
        return map;
    }*/

   /* @RequestMapping("scapp/hDelAdvertising")
    @ResponseBody
    public Map<String, Object> hDelAdvertising(String advertising_id) {
        Object hget = redisUtils.hget("advertising-ntraday", advertising_id);
        log.info(hget.toString());
        redisUtils.hdel("advertising-ntraday", advertising_id);
        Map<String, Object> map = new HashMap<String, Object>();
        return map;
    }*/
}
