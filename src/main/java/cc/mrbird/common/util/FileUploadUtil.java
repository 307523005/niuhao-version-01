package cc.mrbird.common.util;

import cc.mrbird.system.domain.User;
import com.google.common.io.ByteStreams;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FileUploadUtil {
    /**
     * 单文件
     *
     * @param request
     * @param response
     * @param savePatha
     * @param savePathc
     * @param user
     * @param widthSize
     * @param heightSize
     * @return
     */
    public static Map<String, Object> fileUploadImages(HttpServletRequest request, HttpServletResponse response, String savePatha, String savePathc, User user, int widthSize, int heightSize) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String newFileName2 = df2.format(new Date());
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + savePatha + "/" + newFileName2 + "/";
        //String savePath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        System.out.println("--***fileUpload***getRealPath****-" + savePath);
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/" + savePatha + "/" + newFileName2 + "/";
        System.out.println("--*****fileUpload*getContextPath****-" + saveUrl);
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("extension", "gif,jpg,jpeg,png,bmp");//扩展名
        // 最大文件大小
        long maxSize = 3000000;

        response.setContentType("text/html; charset=UTF-8");

        if (!ServletFileUpload.isMultipartContent(request)) {
            return getError("请选择图片。");
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
        String dirName = user.getMerchantId();
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
        String ymd = savePathc;
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
                return getError("上传图片大小超过限制。");
            }

            // 检查扩展名
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if (Arrays.asList(extMap.get("extension").split(",")).contains(fileExt)) {
                //检查宽高，在通过上一步扩展名验证后进行
                try {
                    BufferedImage image = ImageIO.read(file.getInputStream());
                    if (image != null) {//如果image=null 表示上传的不是图片格式
                        System.out.println("获取图片宽度，单位px" + image.getWidth());//获取图片宽度，单位px
                        System.out.println("获取图片高度，单位px" + image.getHeight());//获取图片高度，单位px
                        if (image.getWidth() > widthSize || image.getHeight() > heightSize) {
                            return getError("上传图片宽高超过限制。");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return getError("上传图片扩展名是不允许的扩展名。\n只允许"
                        + extMap.get(dirName) + "格式。");
            }


            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;

            try {

                File uploadedFile = new File(savePath, newFileName);

                ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));

            } catch (Exception e) {

                return getError("上传图片失败。");

            }
            Map<String, Object> msg = new HashMap<String, Object>();
            msg.put("error", 0);
            msg.put("url", saveUrl + newFileName);
            return msg;
        }
        return null;
    }

    private static Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }

    /**
     * 多文件
     *
     * @param request
     * @param response
     * @param savePatha
     * @param savePathc
     * @param user
     * @param widthSize
     * @param heightSize
     * @return
     */
    public static Map<String, Object> moreFileUploadImages(HttpServletRequest request, HttpServletResponse response, String savePatha, String savePathc, User user, int widthSize, int heightSize) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String newFileName2 = df2.format(new Date());
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + savePatha + "/" + newFileName2 + "/";
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/" + savePatha + "/" + newFileName2 + "/";
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("images", "gif,jpg,jpeg,png,bmp");//扩展名
         /*extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");*/
        // 最大文件大小
        long maxSize = 3000000;

        response.setContentType("text/html; charset=UTF-8");

        if (!ServletFileUpload.isMultipartContent(request)) {
            return getError("请选择图片。");
        }
        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            uploadDir.mkdirs();
        }
        // 检查目录写权限
        if (!uploadDir.canWrite()) {
            return getError("上传目录没有写权限。");
        }

        String dirName = user.getMerchantId();
        if (dirName == null) {
            dirName = "image";
        }
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
        savePath += savePathc + "/";
        saveUrl += savePathc + "/";
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
            List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                    .getFiles(fileName);
            MultipartFile file = null;
            Map<String, Object> msg = new HashMap<String, Object>();
            List questionList = new ArrayList<>();
            for (int i = 0; i < files.size(); i++) {
                file = files.get(i);
                // 检查文件大小
                if (file.getSize() > maxSize) {
                    return getError("上传图片大小超过限制。");
                }
                // 检查扩展名
                String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
                //如果时图片类型
                if (Arrays.asList(extMap.get("images").split(",")).contains(fileExt)) {
                    //检查宽高，在通过上一步扩展名验证后进行
                    try {
                        BufferedImage image = ImageIO.read(file.getInputStream());
                        if (image != null) {//如果image=null 表示上传的不是图片格式
                            System.out.println("获取图片宽度，单位px" + image.getWidth());//获取图片宽度，单位px
                            System.out.println("获取图片高度，单位px" + image.getHeight());//获取图片高度，单位px
                            if (image.getWidth() > widthSize || image.getHeight() > heightSize) {
                                return getError("上传图片宽高超过限制。");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    return getError("上传图片扩展名是不允许的扩展名。\n只允许"
                            + extMap.get(dirName) + "格式。");
                }

                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
                try {
                    File uploadedFile = new File(savePath, newFileName);
                    ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));

                } catch (Exception e) {

                    return getError("上传图片失败。");

                }
                //返回服务器存储的路径
                questionList.add(saveUrl + newFileName);
            }
            msg.put("error", 0);//上传成功
            msg.put("url", questionList);
            System.out.println("***msg***" + msg.toString());
            return msg;
        }
        return null;
    }

    /**
     * 备用
     *
     * @param request
     * @param response
     * @param savePatha
     * @param savePathc
     * @param user
     * @return
     */
    public static Map<String, Object> fileUpload(HttpServletRequest request, HttpServletResponse response, String savePatha, String savePathc, User user) {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd");
        String newFileName2 = df2.format(new Date());
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + savePatha + "/" + newFileName2 + "/";
        //String savePath ="d:/niuhao-images/"+user.getUsername()+"/images/";
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/" + savePatha + "/" + newFileName2 + "/";
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("extension", "gif,jpg,jpeg,png,bmp");//扩展名
        /*extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");*/

        // 最大文件大小
        long maxSize = 300000;

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
        String dirName = user.getMerchantId();
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
        String ymd = savePathc;
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
            if (!Arrays.asList(extMap.get("extension").split(",")).contains(fileExt)) {
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
            msg.put("url", saveUrl + newFileName);
            return msg;
        }
        return null;
    }
}
