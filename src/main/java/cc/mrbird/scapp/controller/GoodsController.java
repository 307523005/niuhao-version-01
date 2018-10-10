package cc.mrbird.scapp.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUploadUtil;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.scapp.domain.Goods;
import cc.mrbird.scapp.service.GoodsService;
import cc.mrbird.system.domain.User;
import com.github.pagehelper.PageInfo;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
@Controller
public class GoodsController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GoodsService goodsService;
    // @Log("查看商品")
    @RequestMapping("goods")
    @RequiresPermissions("goods:list")
    public String goods() {
        return "scappht/pagemanagement/goods/goods";
    }

    //@Log("查看商品列表")
    @RequestMapping("goods/list")
    @RequiresPermissions("goods:list")
    @ResponseBody
    public Map<String, Object> goodsList(QueryRequest request, Goods goods) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--" + merchantId);
        goods.setMerchant_id(user.getMerchantId());
        PageInfo<Goods> pageInfo = this.goodsService.PageList(request, goods);
        return getDataTable(pageInfo);
    }
    //@Log("删除商品")
    @RequiresPermissions("goods:delete")
    @RequestMapping("goods/delete")
    @ResponseBody
    public ResponseBo deletegoodss(String ids) {
        try {
            this.goodsService.deleteGoods(ids);
            return ResponseBo.ok("删除商品成功！");
        } catch (Exception e) {
            log.error("删除商品失败", e);
            return ResponseBo.error("删除商品失败，请联系网站管理员！");
        }
    }

    //@Log("修改商品 ")
    @RequiresPermissions("goods:update")
    @RequestMapping("goods/update")
    @ResponseBody
    public ResponseBo updategoods(Goods goods) {
        try {
            User user = RequestUtils.currentLoginUser();
            goods.setGoods_updateuser(user.getUsername());
            this.goodsService.updateGoods(goods);
            return ResponseBo.ok("修改商品成功！");
        } catch (Exception e) {
            log.error("修改商品失败", e);
            return ResponseBo.error("修改商品失败，请联系网站管理员！");
        }
    }

    /**
     * 添加商品
     *
     * @param goods
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "goods/add")
    @RequiresPermissions("goods:add")
    @ResponseBody
    public ResponseBo addGoods(Goods goods) throws Exception {
        try {
            User user = RequestUtils.currentLoginUser();
            goods.setGoods_updateuser(user.getUsername());
            goods.setMerchant_id(user.getMerchantId());
            this.goodsService.addGoods(goods);
            return ResponseBo.ok("新增商品成功！");
        } catch (Exception e) {
            log.error("新增商品失败", e);
            return ResponseBo.error("新增商品失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/getgoods")
    @ResponseBody
    public ResponseBo getgoods(String goods_id) {
        try {
            Goods goods = this.goodsService.findGoods(goods_id);
            return ResponseBo.ok(goods);
        } catch (Exception e) {
            log.error("获取商品信息失败", e);
            return ResponseBo.error("获取商品信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("scapp/scappGetGoodsBygoods_id")
    @ResponseBody
    public ResponseBo scappGetGoodsBygoods_id(String goods_id) {
        try {
            Goods goods = this.goodsService.findGoods(goods_id);
            return ResponseBo.ok(goods);
        } catch (Exception e) {
            log.error("获取商品信息失败", e);
            return ResponseBo.error("获取商品信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("scapp/scappGetGoods")
    @ResponseBody
    public ResponseBo scappGetGoods(String merchant_id,String goodstype_id) {
        try {
            List<Goods> list = this.goodsService.getGoods(merchant_id,goodstype_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("导出商品信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/excel")
    @ResponseBody
    public ResponseBo goodsExcel(Goods goods) {
        try {
            List<Goods> list = this.goodsService.findAllGoods(null, goods);
            return FileUtils.createExcelByPOIKit("商品表", list, Goods.class);
        } catch (Exception e) {
            log.error("导出商品信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/csv")
    @ResponseBody
    public ResponseBo goodsCsv(Goods goods) {
        try {
            List<Goods> list = this.goodsService.findAllGoods(null, goods);
            return FileUtils.createCsv("商品表", list, Goods.class);
        } catch (Exception e) {
            log.error("导出商品信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goods/checkgoods_name")
    @ResponseBody
    public boolean checkgoods_name(String goods_name, String oldgoods_name) {
        if (StringUtils.isNotBlank(oldgoods_name) && goods_name.equalsIgnoreCase(oldgoods_name)) {
            return true;
        }
        User user = RequestUtils.currentLoginUser();
        Goods result = this.goodsService.findByName(goods_name, user.getMerchantId());
        return result == null;
    }

    /**
     * sc上传文件
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws FileUploadException
     */
    @RequestMapping(value = "goods/fileUpload"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            FileUploadException {
        User user = RequestUtils.currentLoginUser();
        return FileUploadUtil.fileUploadImages(request, response, "scimages",  "goods", user,640,356);//260.220
    }

    private Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }


}
