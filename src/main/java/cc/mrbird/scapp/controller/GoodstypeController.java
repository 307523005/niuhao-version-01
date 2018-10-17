package cc.mrbird.scapp.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUploadUtil;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.scapp.domain.Goodstype;
import cc.mrbird.scapp.service.GoodstypeService;
import cc.mrbird.system.domain.User;
import com.github.pagehelper.PageInfo;
import com.google.common.io.ByteStreams;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 商品类型
 */
@Controller
public class GoodstypeController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private GoodstypeService goodstypeService;
    // @Log("查看商品类型")
    @RequestMapping("goodstype")
    @RequiresPermissions("goodstype:list")
    public String goodstype() {
        return "scappht/pagemanagement/goodstype/goodstype";
    }

    //@Log("查看商品类型列表")
    @RequestMapping("goodstype/list")
    @RequiresPermissions("goodstype:list")
    @ResponseBody
    public Map<String, Object> goodstypeList(QueryRequest request, Goodstype goodstype) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--" + merchantId);
        goodstype.setMerchant_id(user.getMerchantId());
        PageInfo<Goodstype> pageInfo = this.goodstypeService.PageList(request, goodstype);
        return getDataTable(pageInfo);
    }

    //@Log("删除商品类型")
    @RequiresPermissions("goodstype:delete")
    @RequestMapping("goodstype/delete")
    @ResponseBody
    public ResponseBo deletegoodstypes(String ids) {
        try {
            this.goodstypeService.deleteGoodstype(ids);
            return ResponseBo.ok("删除商品类型成功！");
        } catch (Exception e) {
            log.error("删除商品类型失败", e);
            return ResponseBo.error("删除商品类型失败，请联系网站管理员！");
        }
    }

    //@Log("修改商品类型 ")
    @RequiresPermissions("goodstype:update")
    @RequestMapping("goodstype/update")
    @ResponseBody
    public ResponseBo updategoodstype(Goodstype goodstype) {
        try {
            User user = RequestUtils.currentLoginUser();
            goodstype.setGoodstype_updateuser(user.getUsername());
            this.goodstypeService.updateGoodstype(goodstype);
            return ResponseBo.ok("修改商品类型成功！");
        } catch (Exception e) {
            log.error("修改商品类型失败", e);
            return ResponseBo.error("修改商品类型失败，请联系网站管理员！");
        }
    }

    /**
     * 添加商品类型
     *
     * @param goodstype
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "goodstype/add")
    @RequiresPermissions("goodstype:add")
    @ResponseBody
    public ResponseBo addGoodstype(Goodstype goodstype) throws Exception {
        try {
            User user = RequestUtils.currentLoginUser();
            goodstype.setGoodstype_updateuser(user.getUsername());
            goodstype.setMerchant_id(user.getMerchantId());
            this.goodstypeService.addGoodstype(goodstype);
            return ResponseBo.ok("新增商品类型成功！");
        } catch (Exception e) {
            log.error("新增商品类型失败", e);
            return ResponseBo.error("新增商品类型失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goodstype/getgoodstype")
    @ResponseBody
    public ResponseBo getgoodstype(String goodstype_id) {
        try {
            Goodstype goodstype = this.goodstypeService.findGoodstype(goodstype_id);
            return ResponseBo.ok(goodstype);
        } catch (Exception e) {
            log.error("获取商品类型信息失败", e);
            return ResponseBo.error("获取商品类型信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("scapp/scappGetGoodstype")
    @ResponseBody
    public ResponseBo scappGetGoodstype(String merchant_id) {
        try {
            List<Goodstype> list = this.goodstypeService.getGoodstype(merchant_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("导出商品类型信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }
    @RequestMapping("goodstype/excel")
    @ResponseBody
    public ResponseBo goodstypeExcel(Goodstype goodstype,QueryRequest request) {
        try {
            List<Goodstype> list = this.goodstypeService.findAllGoodstype(request, goodstype);
            return FileUtils.createExcelByPOIKit("商品类型表", list, Goodstype.class);
        } catch (Exception e) {
            log.error("导出商品类型信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goodstype/csv")
    @ResponseBody
    public ResponseBo goodstypeCsv(Goodstype goodstype,QueryRequest request) {
        try {
            List<Goodstype> list = this.goodstypeService.findAllGoodstype(request, goodstype);
            return FileUtils.createCsv("商品类型表", list, Goodstype.class);
        } catch (Exception e) {
            log.error("导出商品类型信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goodstype/checkgoodstype_name")
    @ResponseBody
    public boolean checkgoodstype_name(String goodstype_name, String oldgoodstype_name) {
        if (StringUtils.isNotBlank(oldgoodstype_name) && goodstype_name.equalsIgnoreCase(oldgoodstype_name)) {
            return true;
        }
        User user = RequestUtils.currentLoginUser();
        Goodstype result = this.goodstypeService.findByName(goodstype_name, user.getMerchantId());
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
    @RequestMapping(value = "goodstype/fileUpload"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            FileUploadException {
        User user = RequestUtils.currentLoginUser();
        return FileUploadUtil.fileUploadImages(request, response, "scimages",  "goodstype", user,60,60);
    }



}
