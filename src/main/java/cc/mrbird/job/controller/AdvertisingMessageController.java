package cc.mrbird.job.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.job.domain.AdvertisingMessage;
import cc.mrbird.job.service.AdvertisingMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 广告评论
 */
@Controller
public class AdvertisingMessageController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingMessageService advertisingMessageService;


    /**
     * 添加广告评论
     *
     * @param advertisingMessage
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "scapp/addAdvertisingMessage")
    @ResponseBody
    public ResponseBo addAdvertisingMessage(AdvertisingMessage advertisingMessage) throws Exception {
        try {
            this.advertisingMessageService.addAdvertisingMessage(advertisingMessage);
            return ResponseBo.ok("新增广告评论成功！");
        } catch (Exception e) {
            log.error("新增广告评论失败", e);
            return ResponseBo.error("新增广告评论失败，请联系网站管理员！");
        }
    }
    @RequestMapping(value = "scapp/getAdvertisingMessageByadvertisingId")
    @ResponseBody
    public ResponseBo getAdvertisingMessageByadvertisingId(AdvertisingMessage advertisingMessage) throws Exception {
        try {
            List<AdvertisingMessage> list = this.advertisingMessageService.getAdvertisingMessageByadvertisingId(advertisingMessage);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("获取广告评论失败", e);
            return ResponseBo.error("获取广告评论失败，请联系网站管理员！");
        }
    }

}
