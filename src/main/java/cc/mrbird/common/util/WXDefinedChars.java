package cc.mrbird.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WXDefinedChars {

    /**
     * wxappid(微信分配的公众账号ID)版图名片
     */
    //public static final String WXGZAPPID = "wx634dd6d06cb2c543";
    public static final String WXGZAPPID = "wxa3bb017e1916e881";
    /**
     * 微信分配的公众账号 secret 版图名片
     */
    //public static final String WXGZSECRET = "e02225967110be340ae58f52b791a32c";
    public static final String WXGZSECRET = "8d6d3757e762433c1fa2cd46aa2b7db7";
    /**
     * 小程序AppID(微信分配的小程序ID)
     */
    //public static final String XCXAPPID = "wxc7e6e91df796cd7f";
    public static final String XCXAPPID = "wx9e0b33bc4e024498";
    /**
     * 小程序app secret
     */
    //public static final String XCXSECRET = "e2b091fd7a999862d7e24b7f96ef10df";
    public static final String XCXSECRET = "0e365344a8c8a23157ca64e5f65bafea";
    /**
     * 商户号id微信支付分配的商户号 1486753972
     */
    public static final String MCH_ID = "1486753972";
    /**
     * API支付密匙 01CD44266BD33E20ECE737CB06505075//商户密钥
     */
    public static final String API_Key = "01CD44266BD33E20ECE737CB06505075";
    /**
     * 调用接口的机器Ip地址
     */
    public static final String CREATE_IP = "127.0.0.1";
    /**
     * p12证书文件目录
     */
    public static final String P12cerfile = "apiclient_cert.p12";

    /**
     * 商品简单描述 格式:商家名称商品
     */
    public static final String BODY = "bantu";
    /**
     * 商家名称
     */
    public static final String CompanyName = "bantu";
    /**
     * 图片地址前端
     */
    public static final String HEAD_URL = "https://hiwego.admin.pleaz.cn:8082/wx/get?url=";
    /**
     * 用于进入小程序获取用户openid的接口网址
     * https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&
     * js_code=JSCODE&grant_type=authorization_code
     */
    public static final String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    /**
     * H5用户同意授权，获取code
     */
    public static final String wx_GetCodeByOauth2 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * H5 获取code后，请求以下链接获取access_token，openid
     */
    public static final String GZOPENID = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * H5拉取用户信息
     */
    public static final String getUidMessage = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    /**
     * H5页面请求路径
     */
    public static final String HTML5PATH0 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_0.html?k=";
    public static final String HTML5PATH1 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_1.html?k=";
    public static final String HTML5PATH2 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_2.html?k=";
    public static final String HTML5PATH3 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_3.html?k=";
    public static final String HTML5PATH4 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_4.html?k=";
    public static final String HTML5PATH5 = "http://192.168.2.108:8080/bantu/html/bantuH5/temp_5.html?k=";

    /**
     * orc上传图片图片存储路径
     */
    //public static final String OCRimageURL = "E:/tomcat/apache-tomcat-7.0.78/webapps/bantu/";
    public static final String OCRimageURL = "E:\\专业软件\\tomcat\\apache-tomcat-7.0.78\\webapps\\bantu/";
    /**
     * 生成二维码用图片路径
     */
    public static final String QRcodePathOne = "http://192.168.2.108:8080/bantu/QRcode/";
    /**
     * logo图片路径
     */
    //public static final String QRcodePathTwo = "E:\\tomcat\\apache-tomcat-7.0.78\\webapps\\bantu\\images\\logo.png";
    public static final String QRcodePathTwo = "E:\\专业软件\\tomcat\\apache-tomcat-7.0.78\\webapps\\bantu\\images\\logo.png";
    /**
     * /**
     * OCR百度账户设置APPID/AK/SK 获取图片文字用
     */
    public static final String OCRAPP_ID = "10801531";
    public static final String OCRAPI_KEY = "5t8gGnvgvK1t8CXpX5cIgrLE";
    public static final String OCRSECRET_KEY = "qTrC2QDiMGphoAa153ZfZmOTyA2fGy7b";
    /**
     * 微信公众号推送消息获取access_token
     */
    public static final String getWeiXinAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";


    /**
     * 小程序生成获取用户openid的接口的字符串
     *
     * @param code
     * @return
     */
    public static String getOpenidXCX(String code) {
        return String.format(Web_access_tokenhttps, XCXAPPID, XCXSECRET, code);
    }

    /**
     * 微信公众号推送消息获取access_token
     *
     * @return
     */
    public static String getGZHAccessToken() {
        return String.format(getWeiXinAccessToken, WXGZAPPID, WXGZSECRET);
    }

    /**
     * 小程序推送消息获取access_token
     *
     * @return
     */
    public static String getXCXccessToken() {
        return String.format(getWeiXinAccessToken, XCXAPPID, XCXSECRET);
    }

    /**
     * 用户进入h5用 生成用于获取Code
     *
     * @param redirectUrl
     * @return
     */
    public static String getRequestCodeUrl(String redirectUrl) {
        String encode = null;
        try {
            // 使用 urlEncode 对链接进行处理
            encode = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return String.format(wx_GetCodeByOauth2, WXGZAPPID, encode,
                "snsapi_userinfo", "bantu_state");
    }

    /**
     * H5获取code后，请求以下链接获取access_token,openid
     *
     * @param code
     * @return
     */
    public static String getH5Openid(String code) {
        return String.format(GZOPENID, WXGZAPPID, WXGZSECRET, code);
    }

    /**
     * H5拉取用户信息
     *
     * @param
     * @return
     */
    public static String getUidMessage(String access_token) {
        return String.format(getUidMessage, access_token, XCXAPPID);
    }

    /**
     * 发放普通红包
     */
    public final static String RED_PACK = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
    /**
     * 统一下单，支付
     */
    public final static String PRE_PAY = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 退款
     */
    public final static String REFUND = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 企业付款
     */
    public final static String TRANSFERS = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
}
