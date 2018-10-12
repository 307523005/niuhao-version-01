package cc.mrbird.job.controller;

import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.shiro.RequestUtils;
import cc.mrbird.common.util.FileUtils;
import cc.mrbird.job.domain.Advertising;
import cc.mrbird.job.service.AdvertisingService;
import cc.mrbird.system.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.google.common.io.ByteStreams;

/**
 * 广告
 */
@Controller
public class AdvertisingController extends BaseController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AdvertisingService advertisingService;

    // @Log("查看广告")
    @RequestMapping("advertising")
    @RequiresPermissions("advertising:list")
    public String advertising() {
        return "job/advertising/advertising";
    }

    //@Log("查看广告列表")
    @RequestMapping("advertising/list")
    @RequiresPermissions("advertising:list")
    @ResponseBody
    public Map<String, Object> advertisingList(QueryRequest request, Advertising advertising) {
        User user = RequestUtils.currentLoginUser();
        String merchantId = user.getMerchantId();
        log.info("---merchantId--"+merchantId);
        advertising.setMerchant_id(user.getMerchantId());
        PageInfo<Advertising> pageInfo = this.advertisingService.PageList(request, advertising);
        return getDataTable(pageInfo);
    }

/*    @Log("新增字典 ")
    //@RequiresPermissions("advertising:add")
    @RequestMapping("advertising/add")
    @ResponseBody
    public ResponseBo addadvertising(Advertising advertising) {
        try {
            this.advertisingService.addAdvertising(advertising);
            return ResponseBo.ok("新增字典成功！");
        } catch (Exception e) {
            log.error("新增字典失败", e);
            return ResponseBo.error("新增字典失败，请联系网站管理员！");
        }
    }*/

    //@Log("删除广告")
    @RequiresPermissions("advertising:delete")
    @RequestMapping("advertising/delete")
    @ResponseBody
    public ResponseBo deleteadvertisings(String ids) {
        try {
            this.advertisingService.deleteAdvertising(ids);
            return ResponseBo.ok("删除广告成功！");
        } catch (Exception e) {
            log.error("删除广告失败", e);
            return ResponseBo.error("删除广告失败，请联系网站管理员！");
        }
    }

    //@Log("修改广告 ")
    @RequiresPermissions("advertising:update")
    @RequestMapping("advertising/update")
    @ResponseBody
    public ResponseBo updateadvertising(Advertising advertising) {
        log.info("update--+"+advertising.getAdvertising_name());
        try {
            User user = RequestUtils.currentLoginUser();
            advertising.setAdvertising_updateuser(user.getUsername());
            this.advertisingService.updateAdvertising(advertising);
            return ResponseBo.ok("修改广告成功！");
        } catch (Exception e) {
            log.error("修改广告失败", e);
            return ResponseBo.error("修改广告失败，请联系网站管理员！");
        }
    }


    /*   @RequestMapping(value= "paperTitle", method=RequestMethod.POST)
       @RequiresPermissions("advertising:list")
       @ResponseBody
       public void advertisings(@RequestParam("paperTitle") String paperTitle) throws Exception {
           System.out.println("+paperTitle++"+paperTitle);
       }*/

    /**
     * 添加广告
     *
     * @param advertising
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "advertising/add")
    @RequiresPermissions("advertising:add")
    @ResponseBody
    public ResponseBo addAdvertising(Advertising advertising) throws Exception {
        try {
            User user = RequestUtils.currentLoginUser();
            advertising.setAdvertising_updateuser(user.getUsername());
            advertising.setMerchant_id(user.getMerchantId());
            this.advertisingService.addAdvertising(advertising);
            System.out.println("+advertising_content++" + advertising.getAdvertising_content());
            return ResponseBo.ok("新增广告成功！");
        } catch (Exception e) {
            log.error("新增广告失败", e);
            return ResponseBo.error("新增广告失败，请联系网站管理员！");
        }
    }

    @RequestMapping("advertising/getadvertising")
    @ResponseBody
    public ResponseBo getadvertising(Long advertising_id) {
        try {
            Advertising advertising = this.advertisingService.findAdvertising(advertising_id);
            return ResponseBo.ok(advertising);
        } catch (Exception e) {
            log.error("获取广告信息失败", e);
            return ResponseBo.error("获取广告信息失败，请联系网站管理员！");
        }
    }

    /**
     * 商城根据id获取广告
     * @param advertising_id
     * @return
     */
    @RequestMapping("scapp/scappgetadvertising")
    @ResponseBody
    public ResponseBo scappgetadvertising(Long advertising_id) {
        try {
            Advertising advertising = this.advertisingService.findAdvertising(advertising_id);
            return ResponseBo.ok(advertising);
        } catch (Exception e) {
            log.error("获取广告信息失败", e);
            return ResponseBo.error("获取广告信息失败，请联系网站管理员！");
        }
    }

    /**
     * 商城根据商户id获取广告列表
     * @param merchant_id
     * @return
     */
    @RequestMapping("scapp/scappGetAdvertisingByMerchant_id")
    @ResponseBody
    public ResponseBo scappGetAdvertisingByMerchant_id(String merchant_id) {
        System.out.println(merchant_id+"merchant_idmerchant_id");
        try {
            List<Advertising> list = this.advertisingService.scappGetAdvertisingByMerchant_id(merchant_id);
            return ResponseBo.ok(list);
        } catch (Exception e) {
            log.error("获取广告信息失败", e);
            return ResponseBo.error("获取广告信息失败，请联系网站管理员！");
        }
    }
    @RequestMapping("advertising/excel")
    @ResponseBody
    public ResponseBo advertisingExcel(Advertising advertising) {
        try {
            List<Advertising> list = this.advertisingService.findAllAdvertising(null,advertising);
            return FileUtils.createExcelByPOIKit("任务表", list, Advertising.class);
        } catch (Exception e) {
            log.error("导出任务信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("advertising/csv")
    @ResponseBody
    public ResponseBo advertisingCsv(Advertising advertising) {
        try {
            List<Advertising> list = this.advertisingService.findAllAdvertising(null,advertising);
            return FileUtils.createCsv("任务表", list, Advertising.class);
        } catch (Exception e) {
            log.error("导出任务信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * kindeditor上传文件
     *
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws FileUploadException
     */
    @RequestMapping(value = "/fileUpload"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException,
            FileUploadException {
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + "images22/";
        User user = RequestUtils.currentLoginUser();
        //String savePath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        System.out.println("--***fileUpload***getRealPath****-" +savePath);
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/images22/";
        System.out.println("--*****fileUpload*getContextPath****-" + saveUrl);
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        String dirName = user.getMerchantId();
        extMap.put(dirName, "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

        // 最大文件大小
        long maxSize = 1000000;

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
        if (!extMap.containsKey(dirName)) {
            return getError("目录名不正确。");
        }
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String ymd = sdf.format(new Date());
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

    @RequestMapping(value = "/fileManager"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public void fileManager(HttpServletRequest request,
                            HttpServletResponse response) throws ServletException, IOException {

        System.out.println("----fileManager---");
        ServletContext application = request.getSession().getServletContext();
        ServletOutputStream out = response.getOutputStream();
        // 根目录路径，可以指定绝对路径，比如 /var/www/attached/
        String rootPath = application.getRealPath("/") + "images22/";
        User user = RequestUtils.currentLoginUser();
        // String rootPath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        // 根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
        String rootUrl = request.getContextPath() + "/images22/";
        // 图片扩展名
        String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};

        String dirName = request.getParameter("dir");
        if (dirName != null) {
            if (!Arrays.<String>asList(
                    new String[]{"image", "flash", "media", "file"})
                    .contains(dirName)) {
                out.println("Invalid Directory name.");
                return;
            }
            rootPath += dirName + "/";
            rootUrl += dirName + "/";
            File saveDirFile = new File(rootPath);
            if (!saveDirFile.exists()) {
                saveDirFile.mkdirs();
            }
        }
        // 根据path参数，设置各路径和URL
        String path = request.getParameter("path") != null ? request
                .getParameter("path") : "";
        String currentPath = rootPath + path;
        String currentUrl = rootUrl + path;
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0,
                    currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0,
                    str.lastIndexOf("/") + 1) : "";
        }

        // 排序形式，name or size or type
        String order = request.getParameter("order") != null ? request
                .getParameter("order").toLowerCase() : "name";

        // 不允许使用..移动到上一级目录
        if (path.indexOf("..") >= 0) {
            out.println("Access is not allowed.");
            return;
        }
        // 最后一个字符不是/
        if (!"".equals(path) && !path.endsWith("/")) {
            out.println("Parameter is not valid.");
            return;
        }
        // 目录不存在或不是目录
        File currentPathFile = new File(currentPath);
        if (!currentPathFile.isDirectory()) {
            out.println("Directory does not exist.");
            return;
        }
        // 遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if (file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (file.isFile()) {
                    String fileExt = fileName.substring(
                            fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes)
                            .contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime",
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file
                                .lastModified()));
                fileList.add(hash);
            }
        }

        if ("size".equals(order)) {
            Collections.sort(fileList, new SizeComparator());
        } else if ("type".equals(order)) {
            Collections.sort(fileList, new TypeComparator());
        } else {
            Collections.sort(fileList, new NameComparator());
        }
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("moveup_dir_path", moveupDirPath);
        msg.put("current_dir_path", currentDirPath);
        msg.put("current_url", currentUrl);
        msg.put("total_count", fileList.size());
        msg.put("file_list", fileList);
        response.setContentType("application/json; charset=UTF-8");
        String msgStr = objectMapper.writeValueAsString(msg);
        out.println(msgStr);
    }

    class NameComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filename"))
                        .compareTo((String) hashB.get("filename"));
            }
        }
    }

    class SizeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                if (((Long) hashA.get("filesize")) > ((Long) hashB
                        .get("filesize"))) {
                    return 1;
                } else if (((Long) hashA.get("filesize")) < ((Long) hashB
                        .get("filesize"))) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }

    class TypeComparator implements Comparator {
        public int compare(Object a, Object b) {
            Hashtable hashA = (Hashtable) a;
            Hashtable hashB = (Hashtable) b;
            if (((Boolean) hashA.get("is_dir"))
                    && !((Boolean) hashB.get("is_dir"))) {
                return -1;
            } else if (!((Boolean) hashA.get("is_dir"))
                    && ((Boolean) hashB.get("is_dir"))) {
                return 1;
            } else {
                return ((String) hashA.get("filetype"))
                        .compareTo((String) hashB.get("filetype"));
            }
        }
    }
}
