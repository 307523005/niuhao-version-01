package cc.mrbird.scapp.controller;

import cc.mrbird.common.util.CheckoutUtil;
import cc.mrbird.common.util.WXDefinedChars;
import cc.mrbird.scapp.domain.AccessToken;
import cc.mrbird.scapp.domain.TemplateMessage;
import cc.mrbird.scapp.domain.Wxuser;
import cc.mrbird.scapp.service.WxuserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2017-12-25 微信授权登陆
 *
 * @author Administrator
 */
@Controller
public class WxuserController {
    @Resource
    private WxuserService wxuserService;

    /**
     * 用户进入h5
     *
     * @param
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/scapp/H5login")
    public Map<String, Object> H5login(String code) throws Exception {
        Map<String, Object> h5login = wxuserService.H5login(code);
        return h5login;
    }

    /**
     * 根据uid得到用户头像
     *
     * @param user_uid
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/scapp/getUserPic")
    public Map<String, Object> getUserPic(String user_uid) throws Exception {
        Map resObjectMap = new HashMap();
        Wxuser wxuser = new Wxuser();
        wxuser.setUser_uid(user_uid);
        Map list = wxuserService.getList(wxuser);
        return list;
    }


    /**
     * 得到AccessToken
     *
     * @param accessToken
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/scapp/getAccessToken")
    public List<AccessToken> getAccessToken(AccessToken accessToken)
            throws Exception {
        Map map = new HashMap();
        List<AccessToken> accessToken2 = wxuserService
                .getAccessToken(accessToken);
        System.out.println(accessToken2.get(0).getGZHaccess_token());
        return accessToken2;

    }

    /**
     * 发送小程序模板通知
     *
     * @param templatemessage
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/scapp/gettemplatemessage")
    public String gettemplatemessage(TemplateMessage templatemessage)
            throws Exception {
        Map map = new HashMap();
        templatemessage.setTemplateMessage_id(1);
        String templatemessageList = wxuserService
                .gettemplatemessage(templatemessage);
        return templatemessageList;
    }
    @ResponseBody
    @RequestMapping(value = "/scapp/requestCodeUrl")
    public String requestCodeUrl(TemplateMessage templatemessage)
            throws Exception {
        // 生成给微信的h5链接
        String requestCodeUrl = WXDefinedChars.getRequestCodeUrl("https://www.niuxinghao.top/apphtml/scapp/index.html?merchant_id=c5c05b29ee4c");
        return requestCodeUrl;
    }

    /**
     * 公众号绑定域名， 微信消息接收和token验证
     *
     * @param model
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/scapp/hello")
    public void hello(Model model, HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter print;
        System.out.println("-------------------");
        if (isGet) {
            // 微信加密签名
            String signature = request.getParameter("signature");
            // 时间戳
            String timestamp = request.getParameter("timestamp");
            // 随机数
            String nonce = request.getParameter("nonce");
            // 随机字符串
            String echostr = request.getParameter("echostr");
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            if (signature != null
                    && CheckoutUtil.checkSignature(signature, timestamp, nonce)) {
                try {
                    print = response.getWriter();
                    print.write(echostr);
                    print.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
