package cc.mrbird.scapp.service.impl;

import cc.mrbird.common.util.HttpUtils;
import cc.mrbird.common.util.RoundUtil;
import cc.mrbird.common.util.SendTemplateUtil;
import cc.mrbird.common.util.WXDefinedChars;
import cc.mrbird.scapp.dao.AccessTokenMapper;
import cc.mrbird.scapp.dao.TemplateMessageMapper;
import cc.mrbird.scapp.dao.WxuserMapper;
import cc.mrbird.scapp.domain.AccessToken;
import cc.mrbird.scapp.domain.TemplateMessage;
import cc.mrbird.scapp.domain.Wxuser;
import cc.mrbird.scapp.service.WxuserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公众号登陆获取openid
 *
 * @author Administrator
 */
@Service
public class WxuserServiceImpl implements WxuserService {
    @Autowired
    private WxuserMapper wxuserDao;
    @Autowired
    private AccessTokenMapper accessTokenDao;
    @Autowired
    private TemplateMessageMapper templateMessageDao;

    /**
     * h5登陆
     */
    @Override
    public Map<String, Object> H5login(String code) throws Exception {
        String nowTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());// new Date()为获取当前系统时间
        System.out.println("-----redirectUrl----" + code);
        Map map = new HashMap();
        // 根据code得到access_token,openid
        String getH5OpenidUrl = WXDefinedChars.getH5Openid(code);
        String rec = HttpUtils.sendGet(getH5OpenidUrl);
        JSONObject json = JSON.parseObject(rec);
        System.out.println("------------json------" + json);
        // 获取回执的openid
        // 获取access_token
        String gzhopenid = json.getString("openid");
        String access_token = json.getString("access_token");
        // 获取微信用户详细信息
        String uidMessageUrl = WXDefinedChars.getUidMessage(access_token);
        String recTwo = HttpUtils.sendGet(uidMessageUrl);
        JSONObject jsonTwo = JSON.parseObject(recTwo);
        String nickname = jsonTwo.getString("nickname").toString();
        String sex = jsonTwo.getString("sex").toString();
        String province = jsonTwo.getString("province").toString();
        String city = jsonTwo.getString("city").toString();
        String country = jsonTwo.getString("country").toString();
        String headimgurl = jsonTwo.getString("headimgurl").toString();
        String unionid = jsonTwo.getString("unionid").toString();
        if (unionid != null) {
            // 根据openid获取用户信息
            List<Wxuser> wxuser = wxuserDao.getByOpenid(unionid);
            if (wxuser.size() > 0) {
                map.put("userid", wxuser.get(0).getUser_uid());
                // 修改用户信息
                Wxuser wxuserUpdate = new Wxuser();
                wxuserUpdate.setUser_nickname(nickname);
                wxuserUpdate.setUser_gender(sex);
                wxuserUpdate.setUser_province(province);
                wxuserUpdate.setUser_city(city);
                wxuserUpdate.setUser_country(country);
                wxuserUpdate.setUser_deptpic(headimgurl);
                wxuserUpdate.setUser_logindtime(nowTime);
                wxuserUpdate.setUser_unionid(unionid);
                wxuserUpdate.setUser_gzhopenid(gzhopenid);
                wxuserDao.update(wxuserUpdate);
            } else {
                // 随机生成uid
                String user_uid = RoundUtil.getUUID16();
                /*
				 * nickname, sex, province, city, country, openid, headimgurl,
				 * nowTime, nowTime, "", "", "", user_uid,"", "","", "","",
				 * "","", "","", "",unionid
				 */
                Wxuser wxusers = new Wxuser();
                wxusers.setUser_uid(user_uid);
                wxusers.setUser_unionid(unionid);
                wxusers.setUser_gzhopenid(gzhopenid);
                wxusers.setUser_registeredtime(nowTime);
                wxusers.setUser_logindtime(nowTime);
                // 添加新用户信息
                wxuserDao.add(wxusers);
                map.put("userid", user_uid);
            }
        }
        return map;
    }


    /**
     * 通过HttpGet类发送GET请求并获取返回信息
     *
     * @param
     * @return
     */
/*	public String httpGet(String path) throws Exception {
		if (path == null) {
			return null;
		}
		String rec = null;
		HttpGet get = new HttpGet(path);
		try {
			HttpResponse response = HttpClients.createDefault().execute(get);
			HttpEntity entity = response.getEntity();
			rec = EntityUtils.toString(entity);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rec;
	}*/
    @Override
    public int getCount(Map map) {
        // TODO Auto-generated method stub
        return wxuserDao.getCount(map);
    }

    @Override
    public Map getList(Wxuser wxuser) {
        // TODO Auto-generated method stub
        Map map = new HashMap();
        List<Wxuser> list = wxuserDao.getList(wxuser);
        if (list.size() > 0) {
            map.put("user_deptpic", list.get(0).getUser_deptpic());
        } else {
            map.put("user_deptpic", 404);
        }
        return map;
    }

    @Override
    public List<Wxuser> getByOpenid(String openid) {
        // TODO Auto-generated method stub
        return wxuserDao.getByOpenid(openid);
    }

    @Override
    public int update(Wxuser wxuser) {
        // TODO Auto-generated method stub
        return wxuserDao.update(wxuser);
    }

    @Override
    public int add(Wxuser wxuser) throws Exception {
        // TODO Auto-generated method stub
        return wxuserDao.add(wxuser);
    }

    @Override
    public List<AccessToken> getAccessToken(AccessToken accessToken)
            throws Exception {
        // TODO Auto-generated method stub
        return accessTokenDao.getList();
    }

    @Override
    public String gettemplatemessage(TemplateMessage templateMessage)
            throws Exception {
        // TODO Auto-generated method stub、
        List<TemplateMessage> byId = templateMessageDao.getById(String
                .valueOf(templateMessage.getTemplateMessage_id()));
        SendTemplateUtil.SendTemplates("ow1c95cNcEuukFbO5Q6sXtYjA50Y",
                "1517553958810", accessTokenDao.getList().get(0)
                        .getXCXaccess_token(), byId);
        return "";
    }

/*    @Override
    public List<Wxuser> getPageList(Map map) throws Exception {
        // TODO Auto-generated method stub
        return wxuserDao.getPageList(map);
    }*/

/*	@Override
	public PageUtil getPage(Map map) throws Exception {
		// TODO Auto-generated method stub
		List<Wxuser> pageList = wxuserDao.getPageList(map);
		Object object = map.get("rows");
		Object object2 = map.get("page");
		return new PageUtil(pageList, wxuserDao.getCount(map));
	}*/

}
