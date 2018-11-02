package cc.mrbird.scapp.service;

import cc.mrbird.scapp.domain.AccessToken;
import cc.mrbird.scapp.domain.TemplateMessage;
import cc.mrbird.scapp.domain.Wxuser;

import java.util.List;
import java.util.Map;


public interface WxuserService {


    /**
     * 小程序登陆后台接口使用框架为Springboot+maven+JPA
     * 需要将前端code 发送到该处，使用code 换取openid 和 session_key
     * 本例中接口地址为http://127.0.0.1：8080/log/in
     *
     * @param code 前台通过wx.login函数success微信服务器回传的code（有效期5min）
     *             本例中前台格式为 code: res.code
     * @return json格式数据 如果成功返回值为{ userid:XX}
     */

/*
     Map<String, Object> login(
            String code, String user_nickname, String user_gender, String user_province, String user_city, String user_country, String user_deptpic, String user_phonebrand,
            String user_phonemodel, String user_wxversion, String user_pixelRatio, String user_screenWidth, String user_screenHeight, String user_windowWidth, String user_windowHeight, String user_language
            , String user_system, String user_platform, String user_fontSizeSetting, String user_SDKVersion, String encryptedData, String vi) throws Exception;
*/

    /**
     * 得到数量
     *
     * @param map
     * @return
     */
    int getCount(Map map) throws Exception;



    /**
     * 得到所有用户信息
     *
     * @param
     * @return
     */
    Map getList(Wxuser wxuser) throws Exception;

    /**
     * 添加用户信息
     *
     * @param wxuser
     * @return
     */
    int add(Wxuser wxuser) throws Exception;

    /**
     * 根据openid获取用户信息
     *
     * @param openid
     * @return
     */
    List<Wxuser> getByOpenid(String openid) throws Exception;

    /**
     * 修改用户信息
     *
     * @param wxuser
     * @return
     */
    int update(Wxuser wxuser);

    /**
     * h5登陆
     *
     * @return
     */
    Map<String, Object> H5login(String code) throws Exception;

    List<AccessToken> getAccessToken(AccessToken accessToken) throws Exception;

    String gettemplatemessage(TemplateMessage accessToken)
            throws Exception;
}
