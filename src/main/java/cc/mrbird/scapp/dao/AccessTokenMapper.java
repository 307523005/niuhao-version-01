package cc.mrbird.scapp.dao;


import cc.mrbird.scapp.domain.AccessToken;

import java.util.List;

public interface AccessTokenMapper {
	/**
	 * 
	 * @return
	 */
	public List<AccessToken>  getList();
	/**
	 * 
	 * @param accessToken
	 * @return
	 */
	public int update(AccessToken accessToken);
}
