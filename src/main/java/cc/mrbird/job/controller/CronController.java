package cc.mrbird.job.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.job.domain.Cron;
import cc.mrbird.job.service.CronService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 表达式
 */
@Controller
public class CronController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CronService cronService;

    // @Log("查看表达式")
    @RequestMapping("cron")
    @RequiresPermissions("cron:list")
    public String cron() {
        return "system/cron/cron";
    }

    //@Log("查看表达式列表")
    @RequestMapping("cron/list")
    //@RequiresPermissions("cron:list")
    @ResponseBody
    public Map<String, Object> cronList(QueryRequest request, Cron cron) {
        // User user = RequestUtils.currentLoginUser();
        PageInfo<Cron> pageInfo = this.cronService.PageList(request, cron);
        return getDataTable(pageInfo);
    }


}
