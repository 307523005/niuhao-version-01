package cc.mrbird.job.controller;

import cc.mrbird.common.annotation.Limit;
import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.LimitType;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.job.domain.AdvertisingMessage;
import cc.mrbird.job.service.AdvertisingMessageService;
import cc.mrbird.system.domain.User;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 广告留言
 */
@Controller
public class AdvertisingMessageController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingMessageService advertisingMessageService;


    /**
     * 添加广告留言
     *
     * @param advertisingMessage
     * @return
     * @throws Exception
     */
    @Limit(key = "addAdvertisingMessage", period = 10, count = 2, name = "addAdvertisingMessage", prefix = "addAdvertisingMessage", limitType = LimitType.IP)
    @RequestMapping(value = "scapp/addAdvertisingMessage")
    @ResponseBody
    public ResponseBo addAdvertisingMessage(AdvertisingMessage advertisingMessage) throws Exception {
        try {
            this.advertisingMessageService.addAdvertisingMessage(advertisingMessage);
            return ResponseBo.ok("新增广告留言成功！");
        } catch (Exception e) {
            log.error("新增广告留言失败", e);
            return ResponseBo.error("新增广告留言失败，请联系网站管理员！");
        }
    }

    @RequestMapping(value = "scapp/getAdvertisingMessageByadvertisingId")
    @ResponseBody
    public ResponseBo getAdvertisingMessageByadvertisingId(AdvertisingMessage advertisingMessage) throws Exception {
        try {
            List<AdvertisingMessage> list = this.advertisingMessageService.getAdvertisingMessageByadvertisingId(advertisingMessage);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("获取广告留言失败", e);
            return ResponseBo.error("获取广告留言失败，请联系网站管理员！");
        }
    }

    @RequestMapping("advertisingmessage")
    @RequiresPermissions("advertisingmessage:list")
    public String advertisingMessage() {
        return "job/advertisingmessage/advertisingmessage";
    }

    @RequestMapping("advertisingmessage/list")
    @RequiresPermissions("advertisingmessage:list")
    @ResponseBody
    public Map<String, Object> advertisingList(QueryRequest request, AdvertisingMessage advertisingMessage) {
        User user = RequestUtils.currentLoginUser();
        advertisingMessage.setMerchantId(user.getMerchantId());
        PageInfo<AdvertisingMessage> pageInfo = advertisingMessageService.PageList(request, advertisingMessage);
        return getDataTable(pageInfo);
    }

    @RequestMapping("advertisingmessage/getadvertisingmessage")
    @ResponseBody
    public ResponseBo getadvertisingmessage(String advmessageId) {
        try {
            AdvertisingMessage advertisingMessage = this.advertisingMessageService.findAdvertisingmessage(advmessageId);
            return ResponseBo.ok(advertisingMessage);
        } catch (Exception e) {
            log.error("获取广留言信息失败", e);
            return ResponseBo.error("获取留言失败，请联系网站管理员！");
        }
    }

    @Log("删除广告留言信息")
    @RequiresPermissions("advertisingmessage:delete")
    @RequestMapping("advertisingmessage/delete")
    @ResponseBody
    public ResponseBo deleteAdvertisingMessage(String ids) {
        try {
            this.advertisingMessageService.deleteAdvertisingMessage(ids);
            return ResponseBo.ok("删除广告留言信息成功！");
        } catch (Exception e) {
            log.error("删除广告留言信息失败", e);
            return ResponseBo.error("删除广告留言信息失败，请联系网站管理员！");
        }
    }

    @Log("回复广告留言")
    @RequiresPermissions("advertisingmessage:update")
    @RequestMapping("advertisingmessage/update")
    @ResponseBody
    public ResponseBo replyAdvertisingMessage(AdvertisingMessage advertisingMessage) {
        try {
            User user = RequestUtils.currentLoginUser();
            advertisingMessage.setAdvmessageUpdateuser(user.getUsername());
            this.advertisingMessageService.replyAdvertisingMessage(advertisingMessage);
            return ResponseBo.ok("回复广告留言成功！");
        } catch (Exception e) {
            log.error("回复广告留言失败", e);
            return ResponseBo.error("回复广告留言失败，请联系网站管理员！");
        }
    }
}
