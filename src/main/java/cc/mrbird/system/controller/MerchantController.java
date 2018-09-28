package cc.mrbird.system.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.common.util.JsonUtils;
import cc.mrbird.system.domain.Merchant;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.MerchantService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;

/**
 * 商户
 */
@Controller
public class MerchantController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MerchantService merchantService;

    // @Log("查看商户")
    @RequestMapping("merchant")
    @RequiresPermissions("merchant:list")
    public String merchant() {
        return "system/merchant/merchant";
    }

    //@Log("查看商户列表")
    @RequestMapping("merchant/list")
    @RequiresPermissions("merchant:list")
    @ResponseBody
    public Map<String, Object> merchantList(QueryRequest request, Merchant merchant) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--"+merchantId);
        PageInfo<Merchant> pageInfo = this.merchantService.PageList(request, merchant);
        return getDataTable(pageInfo);
    }


    //@Log("删除商户")
    @RequiresPermissions("merchant:delete")
    @RequestMapping("merchant/delete")
    @ResponseBody
    public ResponseBo deletemerchants(String ids) {
        try {
            this.merchantService.deleteMerchant(ids);
            return ResponseBo.ok("删除商户成功！");
        } catch (Exception e) {
            log.error("删除商户失败", e);
            return ResponseBo.error("删除商户失败，请联系网站管理员！");
        }
    }

    //@Log("修改商户 ")
    @RequiresPermissions("merchant:update")
    @RequestMapping("merchant/update")
    @ResponseBody
    public ResponseBo updatemerchant(Merchant merchant) {
        log.info("update--+"+ JsonUtils.toJson(merchant));
        try {
            this.merchantService.updateMerchant(merchant);
            return ResponseBo.ok("修改商户成功！");
        } catch (Exception e) {
            log.error("修改商户失败", e);
            return ResponseBo.error("修改商户失败，请联系网站管理员！");
        }
    }


    /**
     * 添加商户
     *
     * @param merchant
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "merchant/add")
    @RequiresPermissions("merchant:add")
    @ResponseBody
    public ResponseBo addMerchant(Merchant merchant) throws Exception {
        try {
            this.merchantService.addMerchant(merchant);
            return ResponseBo.ok("新增商户成功！");
        } catch (Exception e) {
            log.error("新增商户失败", e);
            return ResponseBo.error("新增商户失败，请联系网站管理员！");
        }
    }

    @RequestMapping("merchant/getmerchant")
    @ResponseBody
    public ResponseBo getmerchant(String merchant_id) {
        try {
            Merchant merchant = this.merchantService.findMerchant(merchant_id);
            return ResponseBo.ok(merchant);
        } catch (Exception e) {
            log.error("获取商户信息失败", e);
            return ResponseBo.error("获取商户信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("scapp/getmerchant")
    @ResponseBody
    public ResponseBo getmerchantscapp(String merchant_id) {
        try {
            Merchant merchant = this.merchantService.findMerchant(merchant_id);
            return ResponseBo.ok(merchant);
        } catch (Exception e) {
            log.error("获取商户信息失败", e);
            return ResponseBo.error("获取商户信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("merchant/excel")
    @ResponseBody
    public ResponseBo merchantExcel(Merchant merchant) {
        try {
            List<Merchant> list = this.merchantService.findAllMerchant(null,merchant);
            return FileUtils.createExcelByPOIKit("商户信息表", list, Merchant.class);
        } catch (Exception e) {
            log.error("导出商户信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("merchant/csv")
    @ResponseBody
    public ResponseBo merchantCsv(Merchant merchant) {
        try {
            List<Merchant> list = this.merchantService.findAllMerchant(null,merchant);
            return FileUtils.createCsv("商户信息表", list, Merchant.class);
        } catch (Exception e) {
            log.error("导出商户信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

}
