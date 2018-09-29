package cc.mrbird.scapp.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
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

    @RequestMapping("scapp/getGoodstypeTop3")
    @ResponseBody
    public ResponseBo getGoodstypeTop3(String merchant_id) {
        try {
            List<Goodstype> list = this.goodstypeService.getGoodstypeTop3(merchant_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("导出商品类型信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }
    @RequestMapping("goodstype/excel")
    @ResponseBody
    public ResponseBo goodstypeExcel(Goodstype goodstype) {
        try {
            List<Goodstype> list = this.goodstypeService.findAllGoodstype(null, goodstype);
            return FileUtils.createExcelByPOIKit("商品类型表", list, Goodstype.class);
        } catch (Exception e) {
            log.error("导出商品类型信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("goodstype/csv")
    @ResponseBody
    public ResponseBo goodstypeCsv(Goodstype goodstype) {
        try {
            List<Goodstype> list = this.goodstypeService.findAllGoodstype(null, goodstype);
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
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + "scimages/";
        User user = RequestUtils.currentLoginUser();
        //String savePath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        System.out.println("--***fileUpload***getRealPath****-" + savePath);
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/scimages/";
        System.out.println("--*****fileUpload*getContextPath****-" + saveUrl);
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        String dirName = user.getMerchantId();
        extMap.put(dirName, "gif,jpg,jpeg,png,bmp,.jpg");
        /*extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");*/

        // 最大文件大小
        long maxSize = 100000;

        response.setContentType("text/html; charset=UTF-8");

        if (!ServletFileUpload.isMultipartContent(request)) {
            return getError("请选择文件。");
        }
        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            uploadDir.mkdirs();
            // return getError("上传目录不存在。");
        }
        // 检查目录写权限
        if (!uploadDir.canWrite()) {
            return getError("上传目录没有写权限。");
        }

        //String dirName = request.getParameter("dir");

        if (dirName == null) {
            dirName = "image";
        }
        /*if (!extMap.containsKey(dirName)) {
            return getError("目录名不正确。");
        }*/
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
       /* SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());*/
        String ymd = "goodstype";
        savePath += ymd + "/";
        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");


        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Iterator item = multipartRequest.getFileNames();
        while (item.hasNext()) {

            String fileName = (String) item.next();

            MultipartFile file = multipartRequest.getFile(fileName);


            // 检查文件大小

            if (file.getSize() > maxSize) {

                return getError("上传文件大小超过限制。");

            }

            // 检查扩展名

            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            System.out.println(fileExt+"---fileExt-----");
            if (!Arrays.asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                return getError("上传文件扩展名是不允许的扩展名。\n只允许"
                        + extMap.get(dirName) + "格式。");

            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

            try {

                File uploadedFile = new File(savePath, newFileName);

                ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));

            } catch (Exception e) {

                return getError("上传文件失败。");

            }
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("error", 0);
            System.out.println("saveUrl--" + saveUrl);
            msg.put("url", saveUrl + newFileName);
            return msg;
        }
        return null;
    }

    private Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }


}
