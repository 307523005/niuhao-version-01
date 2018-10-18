package cc.mrbird.scapp.controller;

import cc.mrbird.common.config.RedisUtils;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUploadUtil;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.scapp.domain.Bannerimg;
import cc.mrbird.scapp.service.BannerimgService;
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

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 轮播图
 */
@Controller
public class BannerimgController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private BannerimgService bannerimgService;
    /*@Autowired
    private RedisUtils redisUtils;

    @RequestMapping("scapp/redisTestSet")
    @ResponseBody
    public ResponseBo redisTestSet(String a,String b,String c ) {
        redisUtils.hSet(a, b, c);
        redisUtils.expire(a,20);
       *//* for (int i=0;i<20000;i++){
            String value= "test-3-"+String.valueOf(i);
            String key= "test-3-"+String.valueOf(i);
            redisUtils.set(key,value);
        }*//*

        return ResponseBo.ok("redisTest");
    }
    @RequestMapping("scapp/redisTestGet")
    @ResponseBody
    public ResponseBo redisTestGet(String a,String b ) {
        String o = redisUtils.hGet(a, b);
        return ResponseBo.ok(o);
    }*/


    // @Log("查看轮播图")
    @RequestMapping("bannerimg")
    @RequiresPermissions("bannerimg:list")
    public String bannerimg() {
        return "scappht/pagemanagement/bannerimg/bannerimg";
    }

    //@Log("查看轮播图列表")
    @RequestMapping("bannerimg/list")
    @RequiresPermissions("bannerimg:list")
    @ResponseBody
    public Map<String, Object> bannerimgList(QueryRequest request, Bannerimg bannerimg) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--" + merchantId);
        bannerimg.setMerchant_id(user.getMerchantId());
        PageInfo<Bannerimg> pageInfo = this.bannerimgService.PageList(request, bannerimg);
        return getDataTable(pageInfo);
    }

    //@Log("删除轮播图")
    @RequiresPermissions("bannerimg:delete")
    @RequestMapping("bannerimg/delete")
    @ResponseBody
    public ResponseBo deletebannerimgs(String ids) {
        try {
            this.bannerimgService.deleteBannerimg(ids);
            return ResponseBo.ok("删除轮播图成功！");
        } catch (Exception e) {
            log.error("删除轮播图失败", e);
            return ResponseBo.error("删除轮播图失败，请联系网站管理员！");
        }
    }

    //@Log("修改轮播图 ")
    @RequiresPermissions("bannerimg:update")
    @RequestMapping("bannerimg/update")
    @ResponseBody
    public ResponseBo updatebannerimg(Bannerimg bannerimg) {
        log.info("update--+" + bannerimg.getBannerimg_name());
        try {
            User user = RequestUtils.currentLoginUser();
            bannerimg.setBannerimg_updateuser(user.getUsername());
            this.bannerimgService.updateBannerimg(bannerimg);
            return ResponseBo.ok("修改轮播图成功！");
        } catch (Exception e) {
            log.error("修改轮播图失败", e);
            return ResponseBo.error("修改轮播图失败，请联系网站管理员！");
        }
    }

    /**
     * 添加轮播图
     *
     * @param bannerimg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "bannerimg/add")
    @RequiresPermissions("bannerimg:add")
    @ResponseBody
    public ResponseBo addBannerimg(Bannerimg bannerimg) throws Exception {
        try {
            User user = RequestUtils.currentLoginUser();
            bannerimg.setBannerimg_updateuser(user.getUsername());
            bannerimg.setMerchant_id(user.getMerchantId());
            this.bannerimgService.addBannerimg(bannerimg);
            return ResponseBo.ok("新增轮播图成功！");
        } catch (Exception e) {
            log.error("新增轮播图失败", e);
            return ResponseBo.error("新增轮播图失败，请联系网站管理员！");
        }
    }

    @RequestMapping("bannerimg/getbannerimg")
    @ResponseBody
    @RequiresPermissions("bannerimg:list")
    public ResponseBo getbannerimg(String bannerimg_id) {
        try {
            Bannerimg bannerimg = this.bannerimgService.findBannerimg(bannerimg_id);
            return ResponseBo.ok(bannerimg);
        } catch (Exception e) {
            log.error("获取轮播图信息失败", e);
            return ResponseBo.error("获取轮播图信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("scapp/getBannerimgTop3")
    @ResponseBody
    public ResponseBo getBannerimgTop3(String merchant_id) {
        try {
            List<Bannerimg> list = this.bannerimgService.getBannerimgTop3(merchant_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("导出轮播图信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }
    @RequestMapping("bannerimg/excel")
    @ResponseBody
    @RequiresPermissions("bannerimg:list")
    public ResponseBo bannerimgExcel(Bannerimg bannerimg,QueryRequest request) {
        try {
            List<Bannerimg> list = this.bannerimgService.findAllBannerimg(request, bannerimg);
            return FileUtils.createExcelByPOIKit("轮播图表", list, Bannerimg.class);
        } catch (Exception e) {
            log.error("导出轮播图信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("bannerimg/csv")
    @ResponseBody
    @RequiresPermissions("bannerimg:list")
    public ResponseBo bannerimgCsv(Bannerimg bannerimg,QueryRequest request) {
        try {
            List<Bannerimg> list = this.bannerimgService.findAllBannerimg(request, bannerimg);
            return FileUtils.createCsv("轮播图表", list, Bannerimg.class);
        } catch (Exception e) {
            log.error("导出轮播图信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("bannerimg/checkbannerimg_name")
    @ResponseBody
    public boolean checkbannerimg_name(String bannerimg_name, String oldbannerimg_name) {
        if (StringUtils.isNotBlank(oldbannerimg_name) && bannerimg_name.equalsIgnoreCase(oldbannerimg_name)) {
            return true;
        }
        User user = RequestUtils.currentLoginUser();
        Bannerimg result = this.bannerimgService.findByName(bannerimg_name, user.getMerchantId());
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
    @RequestMapping(value = "bannerimg/fileUpload"/*, method = RequestMethod.POST*/)
    @ResponseBody
    @RequiresPermissions("bannerimg:add")
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            FileUploadException {
        User user = RequestUtils.currentLoginUser();
        /*ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + "scimages/";
        User user = RequestUtils.currentLoginUser();
        //String savePath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/scimages/";
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        String dirName = user.getMerchantId();
        extMap.put(dirName, "gif,jpg,jpeg,png,bmp,.jpg");
        *//*extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");*//*

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
        *//*if (!extMap.containsKey(dirName)) {
            return getError("目录名不正确。");
        }*//*
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
       *//* SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());*//*
        String ymd = "bannerimg";
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
            if (!Arrays.asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                return getError("上传文件扩展名是不允许的扩展名。\n只允许"
                        + extMap.get(dirName) + "格式。");

            }
            //检查宽高

            try {
                BufferedImage image = ImageIO.read(file.getInputStream());

                if (image != null) {//如果image=null 表示上传的不是图片格式
                    System.out.println("获取图片宽度，单位px"+image.getWidth());//获取图片宽度，单位px
                    System.out.println("获取图片高度，单位px"+image.getHeight());//获取图片高度，单位px
                    if (image.getHeight() > maxSize) {

                        return getError("上传文件大小超过限制。");

                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
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
        return null;*/
        return FileUploadUtil.fileUploadImages(request, response,  "scimages", "bannerimg", user,640,326);
    }

   /* private Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }*/


}
