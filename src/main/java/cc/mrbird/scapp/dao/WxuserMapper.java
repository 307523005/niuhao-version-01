package cc.mrbird.scapp.dao;


import cc.mrbird.scapp.domain.Wxuser;

import java.util.List;
import java.util.Map;

public interface WxuserMapper {

	/**
	 * 得到数量
	 * 
	 * @param map
	 * @return
	 */
	public int getCount(Map map);



	/**
	 * 得到所有用户信息
	 * 
	 * @param
	 * @return
	 */
	public List<Wxuser> getList(Wxuser wxuser);
	

	/**
	 * 添加用户信息
	 * 
	 * @param wxuser
	 * @return
	 */
	public int add(Wxuser wxuser);

	/**
	 * 修改用户信息
	 * 
	 * @param wxuser
	 * @return
	 */
	public int update(Wxuser wxuser);

	/**
	 * 根据openid获取用户信息
	 * 
	 * @param openid
	 * @return
	 */
	public List<Wxuser> getByOpenid(String openid);

	/**
	 * 根据user_uid获取用户信息
	 * 
	 * @param user_uid
	 * @return
	 */
	public List<Wxuser> getByUser_uid(String user_uid);
}
