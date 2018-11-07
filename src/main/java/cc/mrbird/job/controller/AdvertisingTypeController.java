package cc.mrbird.job.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUploadUtil;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.job.domain.AdvertisingType;
import cc.mrbird.job.service.AdvertisingTypeService;
import cc.mrbird.system.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 广告类型
 */
@Controller
public class AdvertisingTypeController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingTypeService advertisingtypeService;

    // @Log("查看广告类型")
    @RequestMapping("advertisingtype")
    @RequiresPermissions("advertisingtype:list")
    public String advertisingtype() {
        return "job/ggappht/advertisingtype/advertisingtype";
    }

    //@Log("查看广告类型列表")
    @RequestMapping("advertisingtype/list")
    @RequiresPermissions("advertisingtype:list")
    @ResponseBody
    public Map<String, Object> advertisingtypeList(QueryRequest request, AdvertisingType advertisingtype) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--" + merchantId);
        advertisingtype.setMerchant_id(user.getMerchantId());
        PageInfo<AdvertisingType> pageInfo = this.advertisingtypeService.PageList(request, advertisingtype);
        return getDataTable(pageInfo);
    }

/*    @Log("新增字典 ")
    //@RequiresPermissions("advertisingtype:add")
    @RequestMapping("advertisingtype/add")
    @ResponseBody
    public ResponseBo addadvertisingtype(AdvertisingType advertisingtype) {
        try {
            this.advertisingtypeService.addAdvertisingType(advertisingtype);
            return ResponseBo.ok("新增字典成功！");
        } catch (Exception e) {
            log.error("新增字典失败", e);
            return ResponseBo.error("新增字典失败，请联系网站管理员！");
        }
    }*/

    //@Log("删除广告类型")
    @RequiresPermissions("advertisingtype:delete")
    @RequestMapping("advertisingtype/delete")
    @ResponseBody
    public ResponseBo deleteadvertisingtypes(String ids) {
        try {
            this.advertisingtypeService.deleteAdvertisingType(ids);
            return ResponseBo.ok("删除广告类型成功！");
        } catch (Exception e) {
            log.error("删除广告类型失败", e);
            return ResponseBo.error("删除广告类型失败，请联系网站管理员！");
        }
    }

    //@Log("修改广告类型 ")
    @RequiresPermissions("advertisingtype:update")
    @RequestMapping("advertisingtype/update")
    @ResponseBody
    public ResponseBo updateadvertisingtype(AdvertisingType advertisingtype) {
        log.info("update--+" + advertisingtype.getAdvertisingtype_name());
        try {
            User user = RequestUtils.currentLoginUser();
            advertisingtype.setAdvertisingtype_updateuser(user.getUsername());
            this.advertisingtypeService.updateAdvertisingType(advertisingtype);
            return ResponseBo.ok("修改广告类型成功！");
        } catch (Exception e) {
            log.error("修改广告类型失败", e);
            return ResponseBo.error("修改广告类型失败，请联系网站管理员！");
        }
    }


    /*   @RequestMapping(value= "paperTitle", method=RequestMethod.POST)
       @RequiresPermissions("advertisingtype:list")
       @ResponseBody
       public void advertisingtypes(@RequestParam("paperTitle") String paperTitle) throws Exception {
       }*/

    /**
     * 添加广告类型
     *
     * @param advertisingtype
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "advertisingtype/add")
    @RequiresPermissions("advertisingtype:add")
    @ResponseBody
    public ResponseBo addAdvertisingType(AdvertisingType advertisingtype) throws Exception {
        try {
            User user = RequestUtils.currentLoginUser();
            advertisingtype.setAdvertisingtype_updateuser(user.getUsername());
            advertisingtype.setMerchant_id(user.getMerchantId());
            this.advertisingtypeService.addAdvertisingType(advertisingtype);
            return ResponseBo.ok("新增广告类型成功！");
        } catch (Exception e) {
            log.error("新增广告类型失败", e);
            return ResponseBo.error("新增广告类型失败，请联系网站管理员！");
        }
    }

    @RequestMapping("advertisingtype/getadvertisingtype")
    @ResponseBody
    public ResponseBo getadvertisingtype(Long advertisingtype_id) {
        try {
            AdvertisingType advertisingtype = this.advertisingtypeService.findAdvertisingType(advertisingtype_id);
            return ResponseBo.ok(advertisingtype);
        } catch (Exception e) {
            log.error("获取广告类型信息失败", e);
            return ResponseBo.error("获取广告类型信息失败，请联系网站管理员！");
        }
    }

    /**
     * 商城根据id获取广告类型
     *
     * @param advertisingtype_id
     * @return
     */
    @RequestMapping("scapp/scappgetadvertisingtype")
    @ResponseBody
    public ResponseBo scappgetadvertisingtype(Long advertisingtype_id) {
        try {
            AdvertisingType advertisingtype = this.advertisingtypeService.findAdvertisingType(advertisingtype_id);
            return ResponseBo.ok(advertisingtype);
        } catch (Exception e) {
            log.error("获取广告类型信息失败", e);
            return ResponseBo.error("获取广告类型信息失败，请联系网站管理员！");
        }
    }

    /**
     * 商城根据商户id获取广告类型列表
     *
     * @param merchant_id
     * @return
     */
    @RequestMapping("scapp/scappGetAdvertisingTypeByMerchant_id")
    @ResponseBody
    public ResponseBo scappGetAdvertisingTypeByMerchant_id(String merchant_id) {
        try {
            List<AdvertisingType> list = this.advertisingtypeService.scappGetAdvertisingTypeByMerchant_id(merchant_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("获取广告类型信息失败", e);
            return ResponseBo.error("获取广告类型信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("advertisingtype/checkadvertisingtype_name")
    @ResponseBody
    public boolean checkadvertisingName(String advertisingtype_name, String oldadvertisingtype_name) {
        if (StringUtils.isNotBlank(oldadvertisingtype_name) && advertisingtype_name.equalsIgnoreCase(oldadvertisingtype_name)) {
            return true;
        }
        User user = RequestUtils.currentLoginUser();
        boolean result = this.advertisingtypeService.findByName(advertisingtype_name, user.getMerchantId());
        return result;
    }
    @RequestMapping("advertisingtype/excel")
    @ResponseBody
    public ResponseBo advertisingtypeExcel(AdvertisingType advertisingtype) {
        try {
            List<AdvertisingType> list = this.advertisingtypeService.findAllAdvertisingType(advertisingtype);
            return FileUtils.createExcelByPOIKit("任务表", list, AdvertisingType.class);
        } catch (Exception e) {
            log.error("导出任务信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("advertisingtype/csv")
    @ResponseBody
    public ResponseBo advertisingtypeCsv(AdvertisingType advertisingtype, QueryRequest request) {
        try {
            List<AdvertisingType> list = this.advertisingtypeService.findAllAdvertisingType(advertisingtype);
            return FileUtils.createCsv("任务表", list, AdvertisingType.class);
        } catch (Exception e) {
            log.error("导出任务信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
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
    @RequestMapping(value = "advertisingtype/fileUpload"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            FileUploadException {
        User user = RequestUtils.currentLoginUser();
        return FileUploadUtil.fileUploadImages(request, response, "scimages",  "advertisingtype", user,60,60);
    }
}
